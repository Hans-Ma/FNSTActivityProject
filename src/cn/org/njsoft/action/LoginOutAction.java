package cn.org.njsoft.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import cn.org.njsoft.model.User;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 2015/12/9
 * 退出登录的Action
 * @see cn.org.njsoft.action#LoginOutAction
 * @author JAQ
 *
 */
@SuppressWarnings("serial")
public class LoginOutAction extends ActionSupport {
	User user = new User(); //实例化User类
	@Override
	/**
	 * 2015/12/9
	 * 退出登录
	 * @see cn.org.njsoft.action#LoginOutAction
	 * @author JAQ
	 */
	public String execute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.getSession().removeAttribute("user");//消除user
		return SUCCESS;//正确，返回success	
	}

	public User getUser() {//get方法
		return user;
	}
	public void setUser(User user) {//set方法
		this.user = user;
	}			
}
