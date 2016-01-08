package test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import cn.org.njsoft.dao.UserDao;
import cn.org.njsoft.dao.impl.UserDaoImpl;
import cn.org.njsoft.model.User;

/**
 * 测试UserDaoImpl类中的几组方法
 * @author MY
 */
public class UserTest{
	
	/**
	 * 测试取所有值的时候List是否为空
	 */
	@Test
	public void userSelectTest(){
		boolean flag = true;
		UserDao ud= new UserDaoImpl();
		List<User> result = ud.userSelect();
		if(result.size()>0){
			flag = true;
		}
	
        // 判断方法的返回结果
        Assert.assertEquals(true, flag);
        // 第一个参数是期望值，第二个参数是要验证的值
    }
	/*
	*//**
	 * 测试取出从start开始偏移量为5的时候List的size是否为5
	 *//*
	@Test
	public void userSelectByPageTest(){
		String hql = "from User u where u.userState=1";
		int start = 1;
		int end = 5;
		UserDao ud = new UserDaoImpl();
		List<User> result = ud.userSelectByPage(hql, start, end);
		int lines = result.size();
		Assert.assertEquals(5, lines);
	}
	
//	public void userUpdateTest(){
//		UserDao ud = new UserDaoImpl();
//		User user = new User();
//		user.se
//	}
*/}
