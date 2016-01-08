package cn.org.njsoft.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.org.njsoft.model.User;
import cn.org.njsoft.service.LoginService;
import cn.org.njsoft.service.UserService;

import com.opensymphony.xwork2.ActionSupport;
/**
 * 2015/12/9
 * 注册的Action
 * @see cn.org.njsoft.action#AjaxCheckAction
 * @author JAQ
 */
@SuppressWarnings("serial")
@Scope("request")
@Controller("ajaxLoginAction")
public class AjaxLoginAction extends ActionSupport {
	User user = new User(); //实例化User类
	private String rand;  //为验证码所用
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService; //UserService类型的属性

	@Autowired
	@Qualifier("loginServiceImpl")
	private LoginService loginService;
	@Override
	public String execute() throws Exception {
		HttpServletResponse response=ServletActionContext.getResponse();//response实例化
		PrintWriter out = response.getWriter();	
		HttpServletRequest request = ServletActionContext.getRequest();
		String yanzhengma = this.getRand().toLowerCase();//将验证码字符串全部转换成小写  	
		String random = ((String) request.getSession().getAttribute("random")).toLowerCase();//获取session	 
	    if(!yanzhengma.equals(random)){  
	    	out.print(0); //验证码错误
			out.flush();//强制请求清空缓冲区
			out.close();//关闭输出流
	    }else{
			if (loginService.login(user)){//用户成功登录
				out.print(1);//userName存在，不可以注册
				out.flush();//强制请求清空缓冲区
				out.close();//关闭输出流
			} else{				
				out.print(2);//用户登录失败
				out.flush();//强制请求清空缓冲区
				out.close();//关闭输出流
			}
	    }
		return null;
	}
	/**
	 * get，set 方法
	 * @return
	 */
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public String getRand() {     //get方法
		 return rand;   
	}   
	 public void setRand(String rand) {  //set方法 
		 this.rand = rand;   
	}   
	public LoginService getLoginService() {  //get方法
		return loginService;
	}
	public void setLoginService(LoginService loginService) {//set方法
		this.loginService = loginService;
	}
}