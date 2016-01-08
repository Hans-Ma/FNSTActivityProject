package cn.org.njsoft.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.org.njsoft.model.User;
/**
 * 2015/12/10
 * 用户管理的Service
 * @see cn.org.njsoft.service#UserService
 * @author Administrator
 *
 */
@Scope("singleton")
@Service("userService")
public interface UserService {
	ArrayList<Integer> addUserByList(List<User> userList);//通过list批量增加user
	public List<User> userSelectByPageAndById(String userid, int start, int end);//用户信息分页查询
	public List<User> userSelect();//查询所有用户
	public boolean userDeleteById(int id);//逻辑删除某一个用户
	public boolean addUser(User user);//单个添加用户
	public boolean IscheckUser(User user);// Ajax验证用户名是否已存在
	public User userSelectById(String userId);//通过id查询一个user对象
	public User userGetById(User user);//通过id查询一个user对象
	public boolean userUpdate(User user);//通过传入user对象返回是否更新
	public boolean userUpdatePassword(User user);//会员在页面上修改自己的密码
}
