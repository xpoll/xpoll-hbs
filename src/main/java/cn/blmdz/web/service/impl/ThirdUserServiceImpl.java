package cn.blmdz.web.service.impl;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.blmdz.web.dao.ThirdUserDao;
import cn.blmdz.web.entity.QxxThirdUser;
import cn.blmdz.web.enums.StatusType;
import cn.blmdz.web.model.ThirdUser;
import cn.blmdz.web.service.ThirdUserService;

@Service
public class ThirdUserServiceImpl implements ThirdUserService {

	@Autowired
	private ThirdUserDao thirdUserDao;
	
	@Override
	public void recording(ThirdUser tuser) {
		
		QxxThirdUser qtuser = thirdUserDao.findByThirdUserId(tuser.getThird(), tuser.getThirdUserId());
		Date date = new Date();
		
		if (qtuser == null) {
			qtuser = new QxxThirdUser();
			BeanUtils.copyProperties(tuser, qtuser);

			qtuser.setStatus(StatusType.OK.value());
			qtuser.setCid(0L);
			qtuser.setUid(0L);
			qtuser.setCdate(date);
			qtuser.setUdate(date);
			
			thirdUserDao.create(qtuser);
		}
		
		tuser.setId(qtuser.getId());
		// 每次调用更新
		qtuser.setNick(tuser.getAvatar());
		qtuser.setAvatar(tuser.getAvatar());
		qtuser.setUdate(date);
		
		thirdUserDao.update(qtuser);
	}

}
