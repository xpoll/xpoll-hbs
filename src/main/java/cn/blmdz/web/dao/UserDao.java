package cn.blmdz.web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import cn.blmdz.common.redis.JedisExecutor;
import cn.blmdz.common.redis.RedisBaseDao;
import cn.blmdz.common.util.KeyUtils;
import cn.blmdz.web.entity.QxxUser;
import redis.clients.jedis.Transaction;

@Repository
public class UserDao extends RedisBaseDao<QxxUser> {

	private final static int select = 1;
	
	@Autowired
	public UserDao(JedisExecutor jedisExecutor) {
		super(jedisExecutor, select);
	}
	
	public QxxUser findByOwner(final String owner) {
		return findIndexOne(owner, indexOwner());
	}
	
	@Override
	protected void create(Transaction t, QxxUser user) {
		user.setId(newId());
		// 外键一对一 owner
		t.hset(indexOwner(), user.getOwner(), String.valueOf(user.getId()));
		// 创建
		t.hmset(KeyUtils.entityId(QxxUser.class, user.getId()), stringHashMapper.toHash(user));
	}
	
	@Override
	protected void update(Transaction t, QxxUser user) {
		QxxUser old = findById(user.getId());
		Preconditions.checkArgument(old != null, "update primary data empty");
		// 外键一对一 owner
		if (!Objects.equal(old.getOwner(), user.getOwner())) {
			t.hdel(indexOwner(), old.getOwner());
			t.hset(indexOwner(), user.getOwner(), String.valueOf(user.getId()));
		}
		// 更新
		t.hmset(KeyUtils.entityId(QxxUser.class, user.getId()), stringHashMapper.toHash(user));
	}

	/**
	 * 外键一对一 owner
	 */
	protected String indexOwner() {
		return indexOne("owner");
	}
}
