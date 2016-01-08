package cn.org.njsoft.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.org.njsoft.model.PageDivided;
import cn.org.njsoft.model.User;
import cn.org.njsoft.service.UserService;

/**
 * 2015/12/8
 * 与用户表相关的Action
 * @see cn.org.njsoft.action#UserAction
 * @author MY
 */
@Scope("request")
@Controller("userAction")
public class UserAction extends ActionSupport implements SessionAware,ServletResponseAware {
	private static final long serialVersionUID = 1L;
	private List<User> list;
	private String userId;
	private String end;
	private Map<String,Object> sessionMap;
	User user = new User();
	// 创建全局分页对象
	private PageDivided pageDivided = new PageDivided();

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	HttpServletResponse response=ServletActionContext.getResponse();
	HttpServletRequest request = ServletActionContext.getRequest();
	/**
	 * 管理员查询用户
	 * 2015/12/12
	 * @author MaYue
	 * @throws Exception
	 */
	public String select() throws Exception {
		User user = (User) request.getSession().getAttribute("user");//判断用户是否登录
		if(user != null){ //如果用户不为空
			//判断end为空
			if ("".equals(end)||end==null) {
				//end为空查历史session中有没有set过end
				if(ActionContext.getContext().getSession().get("end")!=null && !"".equals(ActionContext.getContext().getSession().get("end"))){
					//如果set过end，把end取出来交给pageDivided
					pageDivided.setEnd((int) ActionContext.getContext().getSession().get("end"));
				} else {
					//如果没有set过end，end设置默认值为5
					pageDivided.setEnd(5);
				}
			//如果end不为为空	
			} else {
				sessionMap.put("end", Integer.parseInt(this.end));
				pageDivided.setEnd((int) ActionContext.getContext().getSession().get("end"));
			}
			//如果不查询所有用户的情况
			pageDivided.setTotal(userService.userSelect().size());// 用户信息总的条数
			//用户信息总的页数，用于可以分为多少页
			pageDivided.setTotalPage(pageDivided.getTotal() % pageDivided.getEnd() > 0 ? pageDivided.getTotal() / pageDivided.getEnd() + 1 : pageDivided.getTotal() / pageDivided.getEnd());
			//起始点
			pageDivided.setStart((pageDivided.getCurrentPage() - 1) * pageDivided.getEnd());
			list = userService.userSelectByPageAndById(userId, pageDivided.getStart(), pageDivided.getEnd());
			return SUCCESS;
		}else{
			return "input";		//错误，返回input	
		}
	}
	
	/**
	 * 管理员逻辑删除用户
	 * @author MaYue
	 * @throws Exception
	 */
	public String delete() throws Exception {
		if(userService.userDeleteById(Integer.parseInt(this.userId))){	//逻辑删除用户		
			return SUCCESS;		//正确，返回success	
		}
		return null;
	}
	
	/**
	 * 2015/12/12
	 * 单个添加用户
	 * @author JAQ
	 * @return String
	 * @throws Exception
	 */
	public String addUser() throws Exception {
		User user1 = (User) request.getSession().getAttribute("user");//判断用户是否登录	
		if(user1 != null){//用户已登录
			if (userService.IscheckUser(user) &&  userService.addUser(user)){//该用户名是否可以添加		
				return SUCCESS;	//正确，返回success	
			} else{
				return INPUT;
			}
		}
		return ERROR;//错误，返回input
	}
	/**
	 * 查询用户详细信息，管理员界面使用
	 * @throws exception
	 * @author MaYue
	 * @return String success
	 */
	public String detail() throws Exception{
		user = userService.userSelectById(userId);//用户信息的详细查询
		return SUCCESS;//正确，返回success	
	}

	/**
	 * 用户详细信息带入页面，管理员页面使用
	 * @throws exception
	 * @author MaYue
	 * @return String success
	 */
	public String updatePrepare() throws Exception{
		user = userService.userSelectById(userId);
		return SUCCESS;
	}
	/**
	 * 用户详细信息查询，会员登录页面用
	 * @throws exception
	 * @author JAQ
	 */
	public String detailInformation() throws Exception{
		User user1 = (User) request.getSession().getAttribute("user");//判断用户是否登录	
		if(user1 != null){//用户已登录
			user = userService.userGetById(user);//用户信息的详细查询
			return SUCCESS;//正确，返回success		
		}else{
			return INPUT;
		}
	}	
	/**
	 * 用户详细信息带入页面
	 * @throws exception
	 * @author JAQ
	 */
	public String updatePassword() throws Exception{
		String password = user.getUserPassword();
		System.out.println(user.getUserId() + "  1");

		user.setUserId(user.getUserId());
		user = userService.userGetById(user);//用户信息的详细查询
		user.setUserPassword(password);
		if(userService.userUpdatePassword(user)){
			return "success";
		}
		return null;
		
	}
	/**
	 * 管理员修改用户
	 * 15/12/18
	 * @throws exception
	 * @author MaYue
	 * @return String success
	 */
	public String update() throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset = utf-8");
		if (userService.userUpdate(user)){
			return SUCCESS;
		}else{
			return INPUT;
		}
	}

	public User getUser() {//get方法
		return user;
	}
	public void setUser(User user) {//set方法
		this.user = user;
	}
	public List<User> getList() {//get方法
		return list;
	}
	public void setList(List<User> list) {//set方法
		this.list = list;
	}
	public String getUserId() {//get方法
		return userId;
	}
	public void setUserId(String userId) {//set方法
		this.userId = userId;
	}
	public UserService getUserService() {//get方法
		return userService;
	}
	public void setUserService(UserService userService) {//set方法
		this.userService = userService;
	}
	public PageDivided getPageDivided() {//get方法
		return pageDivided;
	}
	public void setPageDivided(PageDivided pageDivided) {//set方法
		this.pageDivided = pageDivided;
	}
	public String getEnd() {//get方法
		return end;
	}
	public void setEnd(String end) {//set方法
		this.end = end;
	}
	@Override
	public void setSession(Map<String, Object> session) {//get方法
		this.sessionMap = session;
	}
	@Override
	public void setServletResponse(HttpServletResponse response) {//set方法
		this.response = response;
	}
}
