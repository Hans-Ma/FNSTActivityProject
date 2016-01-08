package cn.org.njsoft.action;

import java.net.URLEncoder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import cn.org.njsoft.model.User;
import cn.org.njsoft.service.LoginService;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 2015/12/8
 * 登录的Action
 * @see cn.org.njsoft.action#LoginAction
 * @author JAQ
 *
 */
@SuppressWarnings("serial")

@Scope("request")
@Controller("loginAction")
public class LoginAction extends ActionSupport {
	User user = new User(); //实例化User类
	private String rand;  //为验证码所用
	
	@Autowired
	@Qualifier("loginServiceImpl")
	private LoginService loginService;
	/**
	 * 2015/12/8
	 * 登录的Action
	 * @see cn.org.njsoft.action#LoginAction
	 * @author JAQ
	 *
	 */
	@Override
	public String execute() throws Exception {		
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		String yanzhengma = this.getRand().toLowerCase();//将验证码字符串全部转换成小写  	
		String random = ((String) request.getSession().getAttribute("random")).toLowerCase();//获取session	 
	    if(!yanzhengma.equals(random)){  
	    	request.getSession().setAttribute("errorMessage", "验证码错误，请核实后重新输入");  
	          return "input";  //错误，返回input
	    }else{
			if (loginService.login(user)){//用户存在，可以登录
				HttpSession session = request.getSession();
				session.setAttribute("user", user);//添加user的session会话	
				request.getSession().setAttribute("username", user.getUserTrueName());//添加user的真实姓名的session会话		
				@SuppressWarnings("deprecation")
				String usernamestr = URLEncoder.encode(user.getUserName());  //转码
				Cookie cookie = new Cookie("username", usernamestr);//设置userName的cookie,用户前台中文编码的转化
				response.addCookie(cookie);	//设置userName的cookie,火狐浏览器，用户前台中文编码的转化	
				
				if(user.getUserType().getTypeId() == 1){//管理员身份
					return "behind";//管理员身份，正确，返回behind
				} else if(user.getUserType().getTypeId() == 2){//非管理员身份
					return "success";//非管理员身份，正确，返回success	
				}else{
					return "input";
				}
			} else{				
				return "input";//错误，返回input
			}
	    }
	}
	public String getRand() {     //get方法
		 return rand;   
	}   
	 public void setRand(String rand) {  //set方法 
		 this.rand = rand;   
	}   
	public User getUser() {  //get方法
		return user;
	}
	public void setUser(User user) {//set方法
		this.user = user;
	}	
	public LoginService getLoginService() {  //get方法
		return loginService;
	}
	public void setLoginService(LoginService loginService) {//set方法
		this.loginService = loginService;
	}		
}
