package cn.blmdz.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;


class Shop {
    private Integer num = 100;
    private Integer product = 123;
    
    public void buy(JedisPool pool) {
        String key = "product_" + product;
        String value = ShopCardTest.lock(pool, 5000L, 50000, key);
        if (value == null) {
            System.out.println(Thread.currentThread().getName() + " 未获得锁超时。。" + ShopCardTest.sdf.format(new Date()));
            return;
        }
        System.out.println(Thread.currentThread().getName() + " 获得了锁" + ( -- num) + "个, " + value + ", " + ShopCardTest.sdf.format(new Date()));
        ShopCardTest.unlock(pool, key, value);
    }
}

class ThreadShop extends Thread {
    private Shop shop;
    private JedisPool pool;

    public ThreadShop(Shop shop, JedisPool pool) {
        this.shop = shop;
        this.pool = pool;
    }

    @Override
    public void run() {
        shop.buy(pool);
    }
}
@Slf4j
public class ShopCardTest {
    public static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");

    public static void main(String[] args) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(200);
        config.setMaxIdle(8);
        config.setMaxWaitMillis(1000 * 100);
        config.setTestOnBorrow(true);
        JedisPool pool = new JedisPool(config, "blmdz.cn", 6379, 1000, "xpoll@blmdz.cn");
        
        System.out.println("开始抢: " + sdf.format(new Date()));
        Shop shop = new Shop();
        for (int i = 0; i < 50; i++) {
            ThreadShop threadShop = new ThreadShop(shop, pool);
            threadShop.start();
        }
    }
    
    /**
     * @param key 锁key
     * @param timeout 获取锁超时时间 ms
     * @param lockTime 锁有效期 m
     * @return
     */
    public static String lock(JedisPool pool, Long timeout, Integer lockTime, String key) {
        timeout += System.currentTimeMillis();
        Jedis jedis = null;
        lockTime = lockTime/1000;
        try {
            jedis = pool.getResource();
            while(System.currentTimeMillis() < timeout) {
                String value = UUID.randomUUID().toString();
                Long a = System.currentTimeMillis();
                if (jedis.setnx(key, value) == 1) {
                    jedis.expire(key, lockTime);
                    log.info("add lock key: " + key + ", value: " + value + ", time: " + (System.currentTimeMillis() - a));
                    return value;
                }
                if (jedis.ttl(key) == -1) jedis.expire(key, lockTime);
                log.info("add lock timeout key: " + key + ", time: " + (System.currentTimeMillis() - a));

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            
        } catch (Exception e) {
            return null;
        } finally {
            if (jedis != null) jedis.close();
        }
        return null;
    }
    
    public static void unlock(JedisPool pool, String key, String value) {
        Jedis jedis = null;
        Long a = System.currentTimeMillis();
        try {
            jedis = pool.getResource();
            jedis.watch(key);

            if (value.equals(jedis.get(key))) {
                Transaction transaction = jedis.multi();
                transaction.del(key);
                transaction.exec();
            }
            jedis.unwatch();
        } finally {
            if (jedis != null) jedis.close();
        }
        log.info("remove lock key: " + key + ", value: " + value + ", time: " + (System.currentTimeMillis() - a));
    }
}
