package cn.org.njsoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import cn.org.njsoft.dao.UserDao;
import cn.org.njsoft.model.User;
import cn.org.njsoft.service.LoginService;
/**
 * 2015/12/10
 * 登录的Service的实现类
 * @see cn.org.njsoft.service.impl#LoginServiceImpl
 * @author JAQ
 *
 */
@Scope("singleton")
@Service("loginServiceImpl")
public class LoginServiceImpl implements LoginService{
	
	@Autowired
	private UserDao userDao;
	/**
	 * 2015/12/8
	 * 登录功能
	 * @see cn.org.njsoft.service#LoginService
	 * @author Administrator
	 */
	@Override
	public boolean login(User user) {		
		if(user.getUserName()!=null ||user.getUserName()!="" || user.getUserPassword()!="" || user.getUserPassword()!=null){
			if (userDao.login(user)!=null){
				return true;
			}
		}
		return false;
		
	}
}
