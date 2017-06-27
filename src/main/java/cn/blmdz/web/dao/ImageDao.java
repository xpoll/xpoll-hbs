package cn.blmdz.web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.blmdz.common.redis.JedisExecutor;
import cn.blmdz.common.redis.RedisBaseDao;
import cn.blmdz.web.entity.QxxImage;
import redis.clients.jedis.Transaction;

@Repository
public class ImageDao extends RedisBaseDao<QxxImage> {

	private final static int select = 1;
	
	@Autowired
	public ImageDao(JedisExecutor jedisExecutor) {
		super(jedisExecutor, select);
	}

	@Override
	protected void create(Transaction t, QxxImage obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void update(Transaction t, QxxImage obj) {
		// TODO Auto-generated method stub
		
	}
}