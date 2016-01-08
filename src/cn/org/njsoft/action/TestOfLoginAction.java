package cn.org.njsoft.action;

import junit.framework.Assert;

import org.junit.Test;

import cn.org.njsoft.dao.UserDao;
import cn.org.njsoft.dao.impl.UserDaoImpl;
import cn.org.njsoft.model.User;
/**
 * 2015/12/25
 * 单元测试
 * @see cn.org.njsoft.action#LoginAction
 * @author JAQ
 *
 */
public class TestOfLoginAction {	
	/**
	 * 测试正确的登录  用户名和密码为真
	 */
	@Test 
	public void testIsStrNumSuccess(){//ctrl+shift+r截图
		String bString="1234";
		UserDao userDao = new UserDaoImpl();
		boolean flag = userDao.isStrNum(bString);
		System.out.println(flag);
		Assert.assertEquals(true, flag);	 		
	}	
	
	@Test 
	public void testIsLoginSuccess(){//ctrl+shift+r截图
		User user = new User();
		user.setUserName("admin");
		user.setUserPassword("123");
		UserDao userDao = new UserDaoImpl();
		boolean flag = userDao.Islogin(user);
		System.out.println(flag);
		Assert.assertEquals(true, flag);	
	}
}
