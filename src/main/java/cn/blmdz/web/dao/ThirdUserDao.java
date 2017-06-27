package cn.blmdz.web.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.common.base.Preconditions;

import cn.blmdz.common.redis.JedisExecutor;
import cn.blmdz.common.redis.RedisBaseDao;
import cn.blmdz.common.util.KeyUtils;
import cn.blmdz.web.entity.QxxThirdUser;
import cn.blmdz.web.enums.ThirdChannel;
import redis.clients.jedis.Transaction;

@Repository
public class ThirdUserDao extends RedisBaseDao<QxxThirdUser> {

	private final static int select = 1;
	
	@Autowired
	public ThirdUserDao(JedisExecutor jedisExecutor) {
		super(jedisExecutor, select);
	}

	public QxxThirdUser findByUserId(final Long userId, final Long id) {
		return findIndexMore(String.valueOf(userId), id, indexUserId());
	}
	
	public List<QxxThirdUser> findByUserId(final Long userId) {
		return findIndexMore(String.valueOf(userId), indexUserId());
	}
	
	public QxxThirdUser findByThirdUserId(ThirdChannel type, String thirdUserId) {
		return findIndexOne(thirdUserId, indexTypeUserId(type));
	}
	
	@Override
	protected void create(Transaction t, QxxThirdUser obj) {
		obj.setId(newId());
		// 外键 userId
		if (obj.getUserId() != null) t.hset(indexUserId(), newIndexMore(obj.getUserId(), null, obj.getId(), indexUserId()), String.valueOf(obj.getId()));
		// 外键 channel+thirdUserId
		t.hset(indexTypeUserId(obj.getThird()), obj.getThirdUserId(),String.valueOf(obj.getId()));
		// 创建
		t.hmset(KeyUtils.entityId(QxxThirdUser.class, obj.getId()), stringHashMapper.toHash(obj));
	}

	@Override
	protected void update(Transaction t, QxxThirdUser obj) {
		QxxThirdUser old = findById(obj.getId());
		Preconditions.checkArgument(old != null, "update primary data empty");
		// 外键 userId
		t.hset(indexUserId(), newIndexMore(obj.getUserId(), old.getId(), obj.getId(), indexUserId()), String.valueOf(obj.getId()));
		// 更新
		t.hmset(KeyUtils.entityId(QxxThirdUser.class, obj.getId()), stringHashMapper.toHash(obj));
		
	}
	
	/**
	 * 外键 userId
	 */
	private String indexUserId() {
		return indexMore("userId");
	}
	
	/**
	 * 外键 channel+thirdUserId
	 */
	private String indexTypeUserId(ThirdChannel type) {
		return indexOne(type.code() + "UserId");
	}
}
