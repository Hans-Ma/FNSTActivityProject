package cn.org.njsoft.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import cn.org.njsoft.model.Activity;
import cn.org.njsoft.model.SignIn;
import cn.org.njsoft.model.User;
import cn.org.njsoft.service.ActivityService;
import cn.org.njsoft.service.ExcelService;
import cn.org.njsoft.service.SignInService;
import cn.org.njsoft.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 2015/12/10 处理Excel的Action 用于： 1、将指定格式的公会会员信息 Excel 文件导入系统。
 * 2、在系统界面结束活动，可生成本次活动的详细信息报表。
 * 
 * @see cn.org.njsoft.action#ExcelAction
 * @author YXF
 *
 */
@Scope("request")
@Controller("excelAction")
public class ExcelAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	// File对象，目的是获取页面上传的文件
	private File excelFile;
	private List<User> userList = null;
	// 因为在数据库中已经存在，而没有插入成功的数据
	private List<Integer> repeatAddNum = new ArrayList<Integer>();

	/**
	 * 2015/12/12 从index.jsp页面跳转到leadExcel.jsp,获取活动的名字
	 * 
	 * @see cn.org.njsoft.dao.ExcelAction#readExcel(String)
	 * @author YXF
	 */
	private List<Activity> activityList = new ArrayList<Activity>();
	private List<Activity> tempActivityList = new ArrayList<Activity>();// 临时存放activity的list

	/**
	 * 从leadExcel.jsp传来的属性，搜索条件，用来生产报表
	 */
	private int actid;// 活动id
	private int ifgetGift;// 是否获取礼品
	private List<SignIn> signInList = new ArrayList<SignIn>();

	@Autowired
	@Qualifier("excelServiceImpl")
	private ExcelService excelService;

	@Autowired
	@Qualifier("activityServiceImpl")
	private ActivityService activityService;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Autowired
	@Qualifier("signInServiceImpl")
	private SignInService signInService;

	HttpServletResponse response = ServletActionContext.getResponse();
	HttpServletRequest request = ServletActionContext.getRequest();

	/**
	 * 2015/12/12 从leadExcel.jsp传来的属性，搜索条件，用来生产报表
	 * 
	 * @see cn.org.njsoft.dao.ExcelAction#readExcel(String)
	 * @author YXF
	 */
	public String addUserByExcel() throws Exception {
		User user = (User) request.getSession().getAttribute("user");// 判断用户是否登录
		if (user != null) { // 如果用户不为空
			/* 防止alert出现乱码问题 */
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter writer = response.getWriter();

			// 获取上传文件的路径
			String path = excelFile.getAbsolutePath();
			// 通过excel获取用户
			userList = excelService.readExcel(path);
			if (userList != null&&userList.size()>0) {
				// 重复添加的list，存放的是重复的条数
				repeatAddNum = userService.addUserByList(userList);
				// 将与数据库重复的数据所属的行数放在tips里面
				StringBuffer tips = new StringBuffer();
				for (int i = 0; i < repeatAddNum.size(); i++) {
					tips.append(repeatAddNum.get(i));
					// 如果重复的条数只有一条，“、”不显示
					if (++i < repeatAddNum.size()) {
						tips.append("、");
					}
				}
				// 成功添加的条数
				int successAddNum = userList.size() - repeatAddNum.size();
				// 根据不同的情况，提示不同的信息
				if (successAddNum == userList.size()) {
					writer.print("<script>alert('提示：成功导入了" + successAddNum
							+ "条数据');history.go(-1);</script>");
				} else if (successAddNum > 0) {
					writer.print("<script>alert('提示：成功导入了" + successAddNum
							+ "条数据,您导入的第" + tips
							+ "条数据已存在于数据库，请核对');history.go(-1)</script>");
				} else if (successAddNum == 0) {
					writer.print("<script>alert('提示：您导入的" + repeatAddNum.size()
							+ "条数据原先已存在于数据库，请核对！');history.go(-1)</script>");
				}
			} else {
				  writer.print(
				  "<script>alert('提示：该文档中没有数据');history.go(-1)</script>");
			}
		} else {
			return "input"; // 错误，返回input
		}
		return null;
	}

	/**
	 * 2015/12/12 从index.jsp页面跳转到leadExcel.jsp,获取活动的名字
	 * 
	 * @see cn.org.njsoft.dao.ExcelAction#readExcel(String)
	 * @author YXF
	 */
	public String jumpToLeadExcel() throws Exception {
		User user = (User) request.getSession().getAttribute("user");// 判断用户是否登录
		if (user != null) { // 如果用户不为空
			// 获取当前的时间
			Date dateNow = new Date();
			// 获取所有的活动
			tempActivityList = activityService.getAllActivity();
			if (tempActivityList != null) {
				// 将activity从tempActivityList循环取出
				for (int i = 0; i < tempActivityList.size(); i++) {
					// 比较当前时间与活动的结束时间，看活动是否结束，如果结束，则将此活动加到activityList中
					if (dateNow.after(tempActivityList.get(i).getActEndTime())) {
						activityList.add(tempActivityList.get(i));
					}
				}
			}
			// 将activityList放入Session
			request.getSession().setAttribute("activityList", activityList);
			return SUCCESS;
		} else {
			return "input"; // 错误，返回input
		}
	}

	/**
	 * 2015/12/12 根据前台传来的数据，生成非会员报表
	 * @see cn.org.njsoft.dao.ExcelAction#exportUnnumExcel()
	 * @author YXF
	 */
	public String exportUnnumExcel() throws Exception {
		// 根据前台传过来的属性，获取signInList
		signInList = signInService.selectUnnumSignin(actid);
		if (signInList.size() > 0) {
			// 活动的名字
			String activityName = signInList.get(0).getSignAct().getActName();
			// 生成excel的名字
			String excelName = activityName + "_非会员";
			// 属性signInList，generateExcel读取signInList的内容生成excel文件
			excelService.generateExcel(signInList, excelName);
		} else {
			response.getWriter().print(
					"<script>alert('提示：您所查找的数据不存在！');history.go(-1)</script>");
		}

		return null;
	}

	/**
	 * 
	 * 2015/12/12 查询会员的签到信息 根据前台传来的数据，生成报表
	 * 
	 * @see cn.org.njsoft.dao.ExcelAction#readExcel(String)
	 * @author YXF
	 * 
	 */
	public String exportNumExcel() throws Exception {
		// 生成excel的名字
		String excelName = null;
		// 根据前台传过来的属性，获取signInList
		signInList = signInService.selectNumSignin(actid, ifgetGift);
		if (signInList.size() > 0) {
			// 活动的名字
			String activityName = signInList.get(0).getSignAct().getActName();
			// 领取了礼品
			if (ifgetGift == 2) {
				excelName = activityName + "_会员_已领取礼品";
			}
			// 礼品品未被选中
			if (ifgetGift == 0) {
				excelName = activityName + "_会员";
			}
			// 未领取奖品
			if (ifgetGift == 1) {
				excelName = activityName + "_会员_未领取礼品";
			}
			// 属性signInList，generateExcel读取signInList的内容生成excel文件
			excelService.generateExcel(signInList, excelName);
		} else {
			response.getWriter().print(
					"<script>alert('提示：您所查找的数据不存在！');history.go(-1)</script>");
		}
		return null;
	}

	/**
	 * 2015/12/12 使用的众多的get set 方法
	 * 
	 * @see cn.org.njsoft.dao.ExcelAction#readExcel(String)
	 * @author YXF
	 */
	public File getExcelFile() { // get方法
		return excelFile;
	}

	public void setExcelFile(File excelFile) {// set方法
		this.excelFile = excelFile;
	}

	public List<User> getUserList() { // get方法
		return userList;
	}

	public void setUserList(List<User> userList) {// set方法
		this.userList = userList;
	}

	public ExcelService getExcelService() { // get方法
		return excelService;
	}

	public void setExcelService(ExcelService excelService) {// set方法
		this.excelService = excelService;
	}

	public UserService getUserService() { // get方法
		return userService;
	}

	public void setUserService(UserService userService) {// set方法
		this.userService = userService;
	}

	public List<Activity> getActivityList() { // get方法
		return activityList;
	}

	public void setActivityList(List<Activity> activityList) {// set方法
		this.activityList = activityList;
	}

	public int getActid() { // get方法
		return actid;
	}

	public void setActid(int actid) {// set方法
		this.actid = actid;
	}

	public int getIfgetGift() { // get方法
		return ifgetGift;
	}

	public void setIfgetGift(int ifgetGift) {// set方法
		this.ifgetGift = ifgetGift;
	}

}
