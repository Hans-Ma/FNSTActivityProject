package cn.org.njsoft.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import cn.org.njsoft.dao.UserDao;
import cn.org.njsoft.model.User;
import cn.org.njsoft.model.UserType;
/**
 * 2015/12/10
 * UserDao接口的实现类
 * @see cn.org.njsoft.dao.impl#UserDaoImpl
 * @author MY
 *
 */
@Service
public class UserDaoImpl extends HibernateDaoSupport implements UserDao{
	/**
	 * 2015/12/8
	 * 用户登录
	 * @see cn.org.njsoft.dao.UserDao#login(User)
	 * @author JAQ
	 */
	@SuppressWarnings("unchecked")
	public User login(User user) {
		try{
			String queryString="from User u where u.userName=? and u.userPassword=? and u.userState=1";
			Object[] values={user.getUserName().trim(),user.getUserPassword().trim()};
			List<User> list=super.getHibernateTemplate().find(queryString,values);	
			if(list.size()>0){
				user.setUserId(list.get(0).getUserId());
				user.setUserTrueName(list.get(0).getUserTrueName());
				user.setUserType(list.get(0).getUserType());
				user.setUserMail(list.get(0).getUserMail());
				return list.get(0);
			}else{
				return null;//否则，返回null
			}			
		}catch(Exception e){
			e.printStackTrace();
			return null;//异常，返回null
		}
	}
		
	/**
	 * 2015/12/8
	 * 检测注册用户是否已存在
	 * @see cn.org.njsoft.dao.UserDao#checkUser(User)
	 * @author JAQ
	 */
	@SuppressWarnings("unchecked")
	public boolean checkUser(User user){
		try{
			String queryString="from User u where u.userState = 1 and u.userName= ?";
			Object[] values={user.getUserName().trim()};		
			List<User> list=super.getHibernateTemplate().find(queryString,values);
			if(list.size()>0){
				return false; //存在，不可注册
			}else{
				return true; //不存在，可以注册
			}			
		}catch(Exception e){
			e.printStackTrace();
			return false;//异常，返回false
		}
	}
	/**
	 * 2015/12/8
	 * 检验字符串是否可以转化称整数类型
	 * @see  cn.org.njsoft.dao.UserDao#isStrNum(String)
	 * @author JAQ
	 */
	public boolean isStrNum(String str) {
		if(str == null || str == ""){  //判断字符串是都为空
			return true; //字符串为空返回true
		}
		else{
			try {
				Integer.parseInt(str);//如果可以转化为int型
				return true;//返回true
			} catch (NumberFormatException e) {
				return false;//异常，返回false
			}
		}
	}
	/**
	 * 2015/12/11
	 * 通过usertypeid获取UserType
	 * @see cn.org.njsoft.dao.UserDao#getUserTypeById(int)
	 * @author YXF
	 */
	public UserType getUserTypeById(int usertypeid) {
		UserType userType = null ;//定义属性
		try{
			userType = (UserType)super.getHibernateTemplate().get(UserType.class, usertypeid);
		}catch(Exception e){
			e.printStackTrace();			
		}
		if (userType!=null) {
			return userType;//返回当前结果
		}else {
			return null;
		}
	}

	/**
	 * 2015/12/13
	 * 用户信息查询并分页
	 * @author MaYue
	 * @see cn.org.njsoft.dao.UserDao#userSelectByPage(final String , final int , final int)
	 */
	@Override
	public List<User> userSelectByPage(final String hql, final int start, final int end) {
		@SuppressWarnings("unchecked")
		List<User> list = getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query;
				query = session.createQuery(hql);
				if (end != 0) {//分页的end的值
					query.setFirstResult(start);//设置分页的起始值
					query.setMaxResults(end);//设置分页的终止值
				}
				List<User> list = query.list();//查询结果存放在list里
				return list;//返回当前结果
			}
		});
		return list;//返回当前结果
	}

	/**
	 * 2015/12/13
	 * 用户信息查询
	 * @author MaYue
	 * @see cn.org.njsoft.dao.UserDao#userSelect()
	 */
	@Override
	public List<User> userSelect() {
		try{
			String queryString="from User u where u.userState = 1";
			@SuppressWarnings("unchecked")
			List<User> list=super.getHibernateTemplate().find(queryString);
			return list;	//返回当前结果	
		}catch(Exception e){
			e.printStackTrace();
			return null;//异常，返回null
		}
	}
	
	/**
	 * 2015/12/15
	 * 用户信息逻辑删除
	 * @author MaYue
	 * @see cn.org.njsoft.dao.UserDao#userDeleteById(int )
	 */
	@Override
	public boolean userDeleteById(int userId) {
		try{	
			User user=(User) getHibernateTemplate().get(User.class, userId);
			user.setUserState(0);	
			getHibernateTemplate().saveOrUpdate(user);//逻辑删除用户
			return true;	//返回当前结果	
		}catch(Exception e){
			e.printStackTrace();
			return false;//异常，返回false
		}
	}
	/**
	 * 按照用户的id查询用户，然后扔给前台
	 * 2015/12/15
	 * @author DB
	 * @see cn.org.njsoft.dao.UserDao#userGetById(int)	 
	 */
	@Override
	public User userGetById(int userId) {//根据ID查询用户信息
		try {
			User user = (User) super.getHibernateTemplate().get(User.class, userId);//根据ID查询用户信息
			return user;	//返回当前结果	
		} catch (Exception e) {
			e.printStackTrace();//异常，返回null
		}
		return null;//返回null
	}
	/**
	 * 2015/12/15
	 * 单个添加用户
	 * @see cn.org.njsoft.dao.UserDao#addUser(User)
	 * @author JAQ
	 */
	@Override
	public boolean addUser(User user) {
		try{	
			user.setUserType(getUserTypeById(user.getUserType().getTypeId()));
			if(getHibernateTemplate().save(user) != null){
				return true;//增加成功，返回true
			}else{
				return false;//失败，返回false
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;//异常，返回false
		}
	}

	/**
	 * 按照用户的id查询用户，然后扔给前台
	 * 2015/12/15
	 * @author JAQ
	 * @see cn.org.njsoft.dao.UserDao#userGetById(int)	 
	 */
	@Override
	public User userGetById(User user) {//根据ID查询用户信息
		try{
			String queryString="from User u where u.userId=?";
			Object[] values={user.getUserId()};
			@SuppressWarnings("unchecked")
			List<User> list=super.getHibernateTemplate().find(queryString,values);	
			if(list.size()>0){
				user.setUserId(list.get(0).getUserId());
				user.setUserType(list.get(0).getUserType());
				user.setUserTrueName(list.get(0).getUserTrueName());//设置查询出来的user的真实姓名
				user.setUserType(list.get(0).getUserType());//设置查询出来的user的用户类型
				user.setUserMail(list.get(0).getUserMail());//设置查询出来的user的邮箱
				user.setUserSex(list.get(0).getUserSex());//设置查询出来的user的性别
				user.setUserPhone(list.get(0).getUserPhone());//设置查询出来的user的手机号
				user.setUserAge(list.get(0).getUserAge());//设置查询出来的user的年龄
				return list.get(0);//返回结果
			}else{
				return null;//否则，返回null
			}			
		}catch(Exception e){
			e.printStackTrace();
			return null;//异常，返回null
		}
	}

	
	/**
	 * 2015/12/17
	 * 按照用户的id查询用户，然后扔给前台
	 * @author MaYue
	 * @see cn.org.njsoft.dao.UserDao#userSelectById(String)	
	 * 
	 */
	@Override
	public User userSelectById(String userId) {
		try{//根据ID查询用户信息
			User user=(User) getHibernateTemplate().get(User.class, Integer.parseInt(userId));
			return user;//返回当前结果
		}catch(Exception e){
			e.printStackTrace();
			return null;//异常，返回null
		}
	}
	
	
	/**
	 * 2015/12/12
	 * 通过list批量添加user
	 * @see cn.org.njsoft.dao.UserDao#addUserByList(List<User>)
	 * @author YXF
	 */
	public ArrayList<Integer> addUserByList(List<User> userList) {
		//存放的是在数据库已存放的第几条数据
		ArrayList<Integer> failAddNum = new ArrayList<Integer>();
		UserType userType = null;
		User user = null;
		int usertypeid;
		try {
			//逐一取出数据
			for (int i = 0; i < userList.size(); i++) {
				userType = new UserType();
				//获取第i条数据
				user = userList.get(i);
				// 判断该用户的用户名（用户工号ID）是否已在数据库存在，不存在放回为true
				if (checkUser(user)) {
					//获取usertypeid
					usertypeid = user.getUserType().getTypeId();
					userType = getUserTypeById(usertypeid);
					user.setUserType(userType);
					//注册该用户
					getHibernateTemplate().save(userList.get(i));
				} else {
					//如果该用户已经在，在failAddNum放入它在表中第几行
					failAddNum.add(i + 1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();//异常，返回null
		}
		return failAddNum;//返回当前结果
	}

	/**
	 * 2015/12/18
	 * 更新用户
	 * @author MaYue
	 * @see cn.org.njsoft.dao.UserDao#userSelectById(String)	
	 */
	@Override
	public boolean updateUser(User user) {
		try{
			user.setUserType(getUserTypeById(user.getUserType().getTypeId()));
			super.getHibernateTemplate().update(user);
			return true;		
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 2015/12/19
	 * 会员在页面上修改自己的密码
	 * @author JAQ
	 * @see cn.org.njsoft.dao.UserDao#userUpdatePassword(User)	
	 */
	@Override
	public boolean userUpdatePassword(User user) {
		try{			
			super.getHibernateTemplate().update(user);
			return true;		
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 2015/12/25
	 *单元测试
	 * @author JAQ
	 * @see cn.org.njsoft.dao.UserDao#userUpdatePassword(User)	
	 */
	@Override
	public boolean Islogin(User user) {
		String sql = "from User u where u.userName="+user.getUserName() + " and u.userPassword="+ user.getUserPassword()+" and u.userState=1";
		if(user.getUserName()!=null ||user.getUserName()!="" || user.getUserPassword()!="" || user.getUserPassword()!=null){
			if (IsTologin(sql)!=null){
				return true;
			}
		}
		return false;
		
	}
	/**
	 * 2015/12/25
	 * 测试用户登录
	 * @see cn.org.njsoft.dao.UserDao#login(User)
	 * @author JAQ
	 */
	@SuppressWarnings("unchecked")
	public User IsTologin(String  sql) {
		try{		
			List<User> list=super.getHibernateTemplate().find(sql);	
			if(list.size()>0){
				return list.get(0);
			}else{
				return null;//否则，返回null
			}			
		}catch(Exception e){
			e.printStackTrace();
			return null;//异常，返回null
		}
	}

}
