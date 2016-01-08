package cn.org.njsoft.action;

import java.io.ByteArrayInputStream;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import cn.org.njsoft.util.RandomNumUtil;
/**
 * 2015/12/12
 * 登录页面的验证码的action
 * @see cn.org.njsoft.action#RandomAction
 * @author JAQ
 *
 */
@SuppressWarnings("serial")
public class RandomAction extends ActionSupport{
	private ByteArrayInputStream inputStream;   
	@Override
	/**
	 * 2015/12/12
	 * 登录页面的验证码
	 * @see cn.org.njsoft.action#RandomAction
	 * @author JAQ
	 *
	 */
	public String execute() throws Exception{   
		HttpServletRequest request = ServletActionContext.getRequest();
		RandomNumUtil rdnu=RandomNumUtil.Instance();   //实例化RandomNumUtil工具类
		this.setInputStream(rdnu.getImage());//取得带有随机字符串的图片   	
		request.getSession().setAttribute("random", rdnu.getVerificationCodeValue());//添加session会话,取得随机字符串放入HttpSession
		return "success"; //正确，返回success
	}   
	public void setInputStream(ByteArrayInputStream inputStream) {   //get方法
		this.inputStream = inputStream;   
	}   
	public ByteArrayInputStream getInputStream() {   //set方法
		return inputStream;   
	} 
}
