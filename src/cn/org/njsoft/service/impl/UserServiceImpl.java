package cn.org.njsoft.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.org.njsoft.dao.UserDao;
import cn.org.njsoft.model.User;
import cn.org.njsoft.service.UserService;

/**
 * 2015/12/10
 * 有关用户的 Service的实现类
 * @see cn.org.njsoft.service.impl#UserServiceImpl
 * @author MaYue
 *
 */
@Scope("singleton")
@Service("userServiceImpl")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	@Override
	/**
	 * 2015/12/13
	 * 通过list批量添加user
	 * @see cn.org.njsoft.service#UserService
	 * @author YXF
	 */
	public ArrayList<Integer> addUserByList(List<User> userList) {
		return userDao.addUserByList(userList);		
	}
	
	/**
	 * 2015/12/14
	 * 用户分页查询
	 * @see cn.org.njsoft.service#UserService
	 * @author MY
	 */
	@Override
	public List<User> userSelectByPageAndById(String userId, int start, int end) {
		String hql;
		if ("".equals(userId) || userId == null) {
			hql ="from User u where u.userState=1";
		} else {
			hql="from User u where u.userState=1 and (u.userId='"+ userId +"' or u.userName='"+ userId +"' or u.userTrueName='"+ userId +"')";
		}
		return userDao.userSelectByPage(hql,start,end);
	}
	/**
	 * 2015/12/14
	 * 用户查询
	 * @see cn.org.njsoft.service#UserService
	 * @author MY
	 */
	@Override
	public List<User> userSelect() {
		try {
			List<User> list = userDao.userSelect();
			return list;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 2015/12/17
	 * 用户逻辑删除
	 * @see cn.org.njsoft.service#UserService
	 * @author MY
	 */
	@Override
	public boolean userDeleteById(int id) {
		if(userDao.userDeleteById(id)){
			return true;	
		}
		return false;
	}
	
	/**
	 * 2015/12/17
	 * 添加单个用户
	 * @see cn.org.njsoft.service#UserService
	 * @author JAQ
	 */
	@Override
	public boolean addUser(User user) {	
		user.setUserState(1);
		if (userDao.addUser(user)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 2015/12/17
	 * Ajax验证用户名是否已存在
	 * @see cn.org.njsoft.service#UserService
	 * @author JAQn
	 */
	@Override
	public boolean IscheckUser(User user) {		
		if (userDao.checkUser(user)) 
			return true; //可以注册
		return false;
	}
	/**
	 * 2015/12/17
	 * 根据ID查询用户信息
	 * @see cn.org.njsoft.service#UserService
	 * @author MaYue
	 */
	@Override
	public User userSelectById(String userId) {
		try {
			return userDao.userSelectById(userId);
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 2015/12/17
	 * 根据ID查询用户信息
	 * @see cn.org.njsoft.service#UserService
	 * @author JAQ
	 */
	@Override
	public User userGetById(User user) {
		try {
			return userDao.userGetById( user);
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 2015/12/17
	 * 根据user更新用户信息
	 * @see cn.org.njsoft.service#UserService
	 * @return boolean
	 * @author MaYue
	 */
	@Override
	public boolean userUpdate(User user) {
		user.setUserState(1);
		if (userDao.updateUser(user)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 2015/12/17
	 * 根据user更新用户信息
	 * @see cn.org.njsoft.service#UserService
	 * @return boolean
	 * @author JAQ
	 */
	@Override
	public boolean userUpdatePassword(User user) {
		if (userDao.userUpdatePassword(user)){
			return true;
		}else{
			return false;
		}
	}

}
