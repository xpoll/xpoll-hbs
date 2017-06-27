package cn.blmdz.web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.blmdz.common.redis.JedisExecutor;
import cn.blmdz.common.redis.RedisBaseDao;
import cn.blmdz.web.entity.QxxAlbum;
import redis.clients.jedis.Transaction;

@Repository
public class AlbumDao extends RedisBaseDao<QxxAlbum> {

	private final static int select = 1;
	
	@Autowired
	public AlbumDao(JedisExecutor jedisExecutor) {
		super(jedisExecutor, select);
	}

	@Override
	protected void create(Transaction t, QxxAlbum obj) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void update(Transaction t, QxxAlbum obj) {
		// TODO Auto-generated method stub
	}

}