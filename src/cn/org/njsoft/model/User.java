package cn.org.njsoft.model;

import java.util.Set;
/**
 * 2015/12/6
 * User表的model类
 * @see cn.org.njsoft.model#User
 * @author JAQ
 *
 */
public class User {
	private int userId;//用户ID
	private Set<SignIn> signInUser;//one这头设置集合
	private String userName;//用户名称
	private String userSex;//性别
	private int userAge;//年龄
	private String userTrueName;//真是姓名
	private String userPhone;//电话号码
	private String userMail;	//邮箱
	private String userPassword;//密码
	private int userState;	//状态
	private UserType userType; //作为外键   mang 这头设置实体类，作外键
	
	
	public int getUserId() {//get方法
		return userId;
	}
	public void setUserId(int userId) {//set方法
		this.userId = userId;
	}
	public Set<SignIn> getSignInUser() {//get方法
		return signInUser;
	}
	public void setSignInUser(Set<SignIn> signInUser) {//set方法
		this.signInUser = signInUser;
	}
	public String getUserName() {//get方法
		return userName;
	}
	public void setUserName(String userName) {//set方法
		this.userName = userName;
	}
	public String getUserSex() {//get方法
		return userSex;
	}
	public void setUserSex(String userSex) {//set方法
		this.userSex = userSex;
	}
	public int getUserAge() {//get方法
		return userAge;
	}
	public void setUserAge(int userAge) {//set方法
		this.userAge = userAge;
	}
	public String getUserTrueName() {//get方法
		return userTrueName;
	}
	public void setUserTrueName(String userTrueName) {//set方法
		this.userTrueName = userTrueName;
	}
	public String getUserMail() {//get方法
		return userMail;
	}
	public void setUserMail(String userMail) {//set方法
		this.userMail = userMail;
	}
	public String getUserPassword() {//get方法
		return userPassword;
	}
	public void setUserPassword(String userPassword) {//set方法
		this.userPassword = userPassword;
	}
	public int getUserState() {//get方法
		return userState;
	}
	public void setUserState(int userState) {//set方法
		this.userState = userState;
	}
	public UserType getUserType() {//get方法
		return userType;
	}
	public void setUserType(UserType userType) {//set方法
		this.userType = userType;
	}
	public String getUserPhone() {//get方法
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;//set方法
	}		
}
