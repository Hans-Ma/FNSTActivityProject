package cn.org.njsoft.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.org.njsoft.model.User;
/**
 * 2015/12/8
 * UserDao接口
 * @see cn.org.njsoft.dao#UserDao
 * @author MaYue
 */
@Service
public interface UserDao {
	public boolean Islogin(User user); //是否可登录
	public User login(User user); //登录
	public boolean checkUser(User user); //检测注册用户是否已存在
	public boolean isStrNum(String str);//判断是否讲String转化成整数
	//按照起点和偏移量判断是否有返回值
	public List<User> userSelectByPage(String hql, int start, int end);
	public List<User> userSelect(); //查询所有用户方法
	public boolean userDeleteById(int userId);//逻辑删除用户
	public boolean addUser(User user);//单个添加用户
	public User userGetById(User user);//查询单个用户方法,会员登录首页用
	public User userGetById(int userId);//通过id查询用户
	public User userSelectById(String userId);//通过id查询用户
	public ArrayList<Integer> addUserByList(List<User> userList);//通过list批量增加user
	public boolean updateUser(User user);//返回是否更新user
	public boolean userUpdatePassword(User user);//会员在页面上修改自己的密码

}
