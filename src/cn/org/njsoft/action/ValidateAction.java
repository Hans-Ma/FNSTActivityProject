package cn.org.njsoft.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.org.njsoft.model.Activity;
import cn.org.njsoft.model.User;
import cn.org.njsoft.service.ActivityService;
import cn.org.njsoft.service.SignInService;
import cn.org.njsoft.util.SendEmail;

import com.opensymphony.xwork2.ActionContext;

/**
 * 2015/12/23 防作弊的Action
 * 
 * @see cn.org.njsoft.action#ValidateAction
 * @author YXF
 * 
 */
@Scope("request")
@Controller("validateAction")
public class ValidateAction {
	Activity activity;
	private int flag;// 当flag为1时,邮件发送成功,4邮件发送失败,3邮件验证成功，2邮箱验证失败
	private String verify;// 用户输入的验证码
	private String validatecode;// 邮箱验证码

	@Autowired
	@Qualifier("signInServiceImpl")
	private SignInService signInService;

	@Autowired
	@Qualifier("activityServiceImpl")
	private ActivityService activityService;

	HttpServletRequest request = ServletActionContext.getRequest();
	// 获取session里的User
	User user = (User) ActionContext.getContext().getSession().get("user");// 获取用户登录信息

	/**
	 * 2015/12/23 从signInCheck.jsp跳转到验证页面validate.jsp页面
	 * 
	 * @see cn.org.njsoft.action#JumpToValite
	 * @author YXF
	 */
	public String JumpToValite() throws Exception {
		// 通过actId获取activity
		activity = activityService.activityDetailSelect(activity.getActId());
		// 将activity放入Session中，以便后面取用
		request.getSession().setAttribute("activity", activity);
		return "success";
	}

	/**
	 * 2015/12/23 发送防作弊的验证邮件
	 * 
	 * @see cn.org.njsoft.action#sendEmail
	 * @author YXF
	 */
	public String sendEmail() throws Exception {
		// 6位自然数
		int num = 0;
		// 随机产生的6位验证码
		while (num < 100000) {
			num = (int) (Math.random() * 1000000);
		}
		// 发送到邮箱的验证码
		validatecode = String.valueOf(num);
		// 发送邮件
		if (SendEmail.sendMail(user.getUserMail(), validatecode)) {
			// 邮件发送成功将sentEmail设为1
			flag = 1;
		}else {
			// 邮件发送失败
			flag = 4;
		}
		
		return "success";
	}

	/**
	 * 2015/12/23 验证用户输入的验证信息是否正确
	 * 
	 * @see cn.org.njsoft.action#checkEmailValidate
	 * @author YXF
	 */
	public String checkEmailValidate() throws Exception {
		Activity activity1 = (Activity) request.getSession().getAttribute(
				"activity");
		// 用户输入与验证码符合
		if (validatecode.equals(verify)) {
			// 修改signCheck状态
			if (signInService.updateSignCheckByActId(activity1.getActId(),
					user.getUserId())) {
				// 用户输入与验证码符合,验证成功
				flag = 3;
			}
		} else {
			// 用户输入与验证码不符合
			flag = 2;
		}
		return "success";
	}

	public String getVerify() {
		return verify;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public String getValidatecode() {
		return validatecode;
	}

	public void setValidatecode(String validatecode) {
		this.validatecode = validatecode;
	}

}
