package cn.org.njsoft.action;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.org.njsoft.model.Activity;
import cn.org.njsoft.model.User;
import cn.org.njsoft.service.ActivityService;
import cn.org.njsoft.service.SignInService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.org.njsoft.model.PageDivided;
/**
 * 2015/12/10
 * 活动的Action
 * @see cn.org.njsoft.action#ActivityAction
 * @author JAQ
 * 
 */
@SuppressWarnings("serial")
@Scope("request")
@Controller("activityAction")
public class ActivityAction extends ActionSupport {
	Activity activity = new Activity();     //活动类的实例化
	private String end;  //分页的，一页多少行
	private int size = 1;   
	private Map<String,Object> sessionMap;
	private PageDivided pageDivided = new PageDivided();
	private List<Activity> list;  //活动的分页查询 的list
	private long time = 0;  //活动开始的倒计时的时间
	private long endtime = 0;//活动结束的倒计时的时间
	private long signtime = 0;//活动开始签到的倒计时时间
	private int userId;//手动签到，从会员列表传到活动列表的用户ID

	private int totalUserNum;//用户总人数
	private int signNumber;//已签到的人数
	private int unSignNumber;//未签到的人数
	private float signPercent;//未签到的人数
	private int lateSignNumber;//迟到签到的人数
	private int giftNumber;//已签到的人数
	private int unGiftNumber;//未签到的人数
	

	@Autowired
	@Qualifier("activityServiceImpl")
	private ActivityService activityService;
	
	@Autowired
	@Qualifier("signInServiceImpl")
	private SignInService signInService;

	HttpServletResponse response=ServletActionContext.getResponse();
	HttpServletRequest request = ServletActionContext.getRequest();
	//获取session里的User
	User user=(User) ActionContext.getContext().getSession().get("user");//获取用户登录信息
	/**
	 * 2015/12/15
	 * 分页查询活动
	 * @see cn.org.njsoft.action#ActivityAction
	 * @author JAQ
	 */
	public String activitySelect() throws Exception {
		User user = (User) request.getSession().getAttribute("user");//判断用户是否登录
		if(user != null){ //如果用户不为空
			if ("".equals(end)||end==null) {	//判断end为空
				if(end!=null && !"".equals(end)){			
					pageDivided.setEnd(Integer.parseInt(end));//如果set过end，把end取出来交给pageDivided
				} else {
					pageDivided.setEnd(5);	//如果没有set过end，end设置默认值为5
				}
			} else {
				pageDivided.setEnd(Integer.parseInt(end));
			}
			//如果不查询所有用户的情况
			pageDivided.setTotal(activityService.activitySelect().size());// 用户信息总的条数
			//用户信息总的页数，用于可以分为多少页
			pageDivided.setTotalPage(pageDivided.getTotal() % pageDivided.getEnd() > 0 ? pageDivided.getTotal() / pageDivided.getEnd() + 1 : pageDivided.getTotal() / pageDivided.getEnd());
			//起始点
			pageDivided.setStart((pageDivided.getCurrentPage() - 1) * pageDivided.getEnd());
			list = activityService.activitySelectByPage( pageDivided.getStart(), pageDivided.getEnd());	
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间格式的转化
			for(int i=0;i<list.size(); i++ ){//因为时间在前台jsp页面输出，格式变化，所以需要时间类型的转化
				String startTime = dateFormat.format(list.get(i).getActStartTime());//活动开始时间的格式转化
				String endTime = dateFormat.format(list.get(i).getActEndTime());//活动结束时间的格式转化
				String signStart = dateFormat.format(list.get(i).getSignStartTime());//活动开始签到时间的格式转化
				list.get(i).setStartTime(startTime);//转化后的活动开始时间set值
				list.get(i).setEndTime(endTime);//转化后的活动开始时间set值	
				list.get(i).setSignStart(signStart);//转化后的活动开始时间set值
			}
			return "success";  //正确，返回success
		}else{
			return "input";		//错误，返回input	
		}
	}
	/**
	 * 2015/12/15
	 * 分页查询未签到活动
	 * @see cn.org.njsoft.action#ActivityAction
	 * @author DB
	 */
	public String activityUnsign() throws Exception {
		User user1 = (User) request.getSession().getAttribute("user");//判断用户是否登录
		if(user1 != null){ //如果用户不为空
			if (userId ==0) {// 等于0，表明由前台传入用户Id为空,用户签到
				userId = user.getUserId();//取出session里用户Id
			}
			if ("".equals(end)||end==null) {	//判断end为空
				if(end!=null && !"".equals(end)){			
					pageDivided.setEnd(Integer.parseInt(end));//如果set过end，把end取出来交给pageDivided
				} else {
					pageDivided.setEnd(5);	//如果没有set过end，end设置默认值为5
				}
			} else {
				pageDivided.setEnd(Integer.parseInt(end));
			}
			//如果不查询所有用户的情况
			pageDivided.setTotal(activityService.activityUnsign(userId).size());// 用户信息总的条数
			//用户信息总的页数，用于可以分为多少页
			pageDivided.setTotalPage(pageDivided.getTotal() % pageDivided.getEnd() > 0 ? pageDivided.getTotal() / pageDivided.getEnd() + 1 : pageDivided.getTotal() / pageDivided.getEnd());
			//起始点
			pageDivided.setStart((pageDivided.getCurrentPage() - 1) * pageDivided.getEnd());
			list = activityService.activityUnsignByPage( pageDivided.getStart(), pageDivided.getEnd(),userId);	
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间格式的转化
			for(int i=0;i<list.size(); i++ ){//因为时间在前台jsp页面输出，格式变化，所以需要时间类型的转化
				String startTime = dateFormat.format(list.get(i).getActStartTime());//活动开始时间的格式转化
				String endTime = dateFormat.format(list.get(i).getActEndTime());//活动结束时间的格式转化
				String signStart = dateFormat.format(list.get(i).getSignStartTime());//活动开始签到时间的格式转化
				list.get(i).setStartTime(startTime);//转化后的活动开始时间set值
				list.get(i).setEndTime(endTime);//转化后的活动开始时间set值	
				list.get(i).setSignStart(signStart);//转化后的活动开始时间set值
			}
			return "success";  //正确，返回success
		}else{
			return "input";		//错误，返回input	
		}
	}
	/**
	 * 2015/12/15
	 * 分页查询签到活动
	 * @see cn.org.njsoft.action#ActivityAction
	 * @author DB
	 */
	public String activitySign() throws Exception {
		User user1 = (User) request.getSession().getAttribute("user");//判断用户是否登录
		if(user1 != null){ //如果用户不为空
		
			if (userId ==0) {// 等于0，表明由前台传入用户Id为空,用户签到
				userId = user.getUserId();//取出session里用户Id
			}
			if ("".equals(end)||end==null) {	//判断end为空
				if(end!=null && !"".equals(end)){			
					pageDivided.setEnd(Integer.parseInt(end));//如果set过end，把end取出来交给pageDivided
				} else {
					pageDivided.setEnd(5);	//如果没有set过end，end设置默认值为5
				}
			} else {
				pageDivided.setEnd(Integer.parseInt(end));
			}
			//如果不查询所有用户的情况
			pageDivided.setTotal(activityService.activitySign(userId).size());// 用户信息总的条数
			//用户信息总的页数，用于可以分为多少页
			pageDivided.setTotalPage(pageDivided.getTotal() % pageDivided.getEnd() > 0 ? pageDivided.getTotal() / pageDivided.getEnd() + 1 : pageDivided.getTotal() / pageDivided.getEnd());
			//起始点
			pageDivided.setStart((pageDivided.getCurrentPage() - 1) * pageDivided.getEnd());
			list = activityService.activitySignByPage( pageDivided.getStart(), pageDivided.getEnd(),userId);	
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间格式的转化
			for(int i=0;i<list.size(); i++ ){//因为时间在前台jsp页面输出，格式变化，所以需要时间类型的转化
				String startTime = dateFormat.format(list.get(i).getActStartTime());//活动开始时间的格式转化
				String endTime = dateFormat.format(list.get(i).getActEndTime());//活动结束时间的格式转化
				String signStart = dateFormat.format(list.get(i).getSignStartTime());//活动开始签到时间的格式转化
				list.get(i).setStartTime(startTime);//转化后的活动开始时间set值
				list.get(i).setEndTime(endTime);//转化后的活动开始时间set值	
				list.get(i).setSignStart(signStart);//转化后的活动开始时间set值
			}
			return "success";  //正确，返回success
		}
		else{
			return "input";		//错误，返回input	
		}
	}
	/**
	 * 2015/12/15
	 * 分页查询签到却没验证活动
	 * @see cn.org.njsoft.action#activitySignUnChecked
	 * @author DB
	 */
	public String activitySignUnChecked() throws Exception {
		User user1 = (User) request.getSession().getAttribute("user");//判断用户是否登录
		if(user1 != null){ //如果用户不为空
			
			if (userId ==0) {// 等于0，表明由前台传入用户Id为空,用户签到
				userId = user.getUserId();//取出session里用户Id
			}
			if ("".equals(end)||end==null) {	//判断end为空
				if(end!=null && !"".equals(end)){			
					pageDivided.setEnd(Integer.parseInt(end));//如果set过end，把end取出来交给pageDivided
				} else {
					pageDivided.setEnd(5);	//如果没有set过end，end设置默认值为5
				}
			} else {
				pageDivided.setEnd(Integer.parseInt(end));
			}
			//如果不查询所有用户的情况
			pageDivided.setTotal(activityService.activitySign(userId).size());// 用户信息总的条数
			//用户信息总的页数，用于可以分为多少页
			pageDivided.setTotalPage(pageDivided.getTotal() % pageDivided.getEnd() > 0 ? pageDivided.getTotal() / pageDivided.getEnd() + 1 : pageDivided.getTotal() / pageDivided.getEnd());
			//起始点
			pageDivided.setStart((pageDivided.getCurrentPage() - 1) * pageDivided.getEnd());
			list = activityService.activitySignUnCheckedByPage( pageDivided.getStart(), pageDivided.getEnd(),userId);	
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间格式的转化
			for(int i=0;i<list.size(); i++ ){//因为时间在前台jsp页面输出，格式变化，所以需要时间类型的转化
				String startTime = dateFormat.format(list.get(i).getActStartTime());//活动开始时间的格式转化
				String endTime = dateFormat.format(list.get(i).getActEndTime());//活动结束时间的格式转化
				String signStart = dateFormat.format(list.get(i).getSignStartTime());//活动开始签到时间的格式转化
				list.get(i).setStartTime(startTime);//转化后的活动开始时间set值
				list.get(i).setEndTime(endTime);//转化后的活动开始时间set值	
				list.get(i).setSignStart(signStart);//转化后的活动开始时间set值
			}
			return "success";  //正确，返回success
		}
		else{
			return "input";		//错误，返回input	
		}
	}
	/**
	 * 2015/12/15
	 * 添加活动的准备
	 * @see cn.org.njsoft.action#ActivityAction
	 * @author JAQ
	 */
	public String addActivityReady() throws Exception {
		User user = (User) request.getSession().getAttribute("user");//判断用户是否登录
		if(user != null){ //如果用户不为空
//			int userNum = activityService.userSelectToGetGiftNum();	
//			int giftNum =  (int) ((int)userNum*1.2);	 //推荐的礼品数量是会员数量的1.2倍
//			activity.setActGiftNumber(giftNum);
			return "success";	//正确，返回success	
		}else{
			return "input";		//错误，返回input	
		}
	}
	
	/**
	 * 2015/12/15
	 * 添加活动
	 * @see cn.org.njsoft.action#ActivityAction
	 * @author JAQ
	 */
	public String addActivity() throws Exception {	

		String actStartTime = request.getParameter("actStartTime"); //获取活动开始时间
		String actEndTime = request.getParameter("actEndTime");//获取活动结束时间
		String signStartTime = request.getParameter("signStartTime");//获取活动开始签到时间
		
		String year1 = actStartTime.substring(6, 10);  //活动开始时间的年分
		String monthday1 = actStartTime.substring(0, 5);//活动开始时间的月份和日
		String hour1 = actStartTime.substring(11, 16);//活动开始时间的小时和分
		String startTimeString = year1 + "-" + monthday1+ " " + hour1 + ":00";//活动开始时间的String类型
				
		String year2 = actEndTime.substring(6, 10); //活动结束时间的年分
		String monthday2 = actEndTime.substring(0, 5);//活动结束时间的月份和日
		String hour2 = actEndTime.substring(11, 16);//活动结束时间的小时和分
		String actEndTimeString = year2 + "-" + monthday2+ " " + hour2 + ":00";//活动结束时间的String类型
			
		String year3 = signStartTime.substring(6, 10); //活动开始签到时间的年分
		String monthday3 = signStartTime.substring(0, 5);//活动开始签到时间的月份和日
		String hour3 = signStartTime.substring(11, 16);//活动开始签到时间的小时和分
		String signStartTimeString = year3 + "-" + monthday3+ " " + hour3 + ":00";//活动开始签到时间的String类型
			
		Timestamp startTime = new Timestamp(System.currentTimeMillis());
		startTime = Timestamp.valueOf(startTimeString);//将字符串转换成Timestamp格式
			
		Timestamp endTime = new Timestamp(System.currentTimeMillis());
		endTime = Timestamp.valueOf(actEndTimeString);//将字符串转换成Timestamp格式
			
		Timestamp signStart = new Timestamp(System.currentTimeMillis());
		signStart = Timestamp.valueOf(signStartTimeString);//将字符串转换成Timestamp格式
		
		activity.setActStartTime(startTime);//set活动开始时间的值
		activity.setActEndTime(endTime);//set活动结束时间的值
		activity.setSignStartTime(signStart);	//set活动开始签到时间的值	
		if (activityService.addActivity(activity)){	
			return "success";//正确，返回success		
		}
		return "input";//错误，返回input
	 
	}	
	/**
	 * 2015/12/15
	 * 活动详细查询
	 * @see cn.org.njsoft.action#ActivityAction
	 * @author JAQ
	 */
	public String activityDetailSelect() throws Exception {		
		activity = activityService.activityDetailSelect(activity.getActId());	//根据id查询该活动的详细信息
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间格式的转化
		String startTime = dateFormat.format(activity.getActStartTime());//时间格式的转化,输出到前台
		String endTime = dateFormat.format(activity.getActEndTime());//活动结束时间的转化,输出到前台
		String signStart = dateFormat.format(activity.getSignStartTime());//活动签到时间的转化,输出到前台
		activity.setStartTime(startTime);  //活动开始时间的类型转化，保存转化后的值
		activity.setEndTime(endTime);	 //活动结束时间的类型转化，保存转化后的值
		activity.setSignStart(signStart); //活动开始签到时间的类型转化，保存转化后的值

		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());//获取系统当前时间
		String currentTime =  dateFormat.format(currentTimestamp);//系统当前时间转化为string类型	
		Date now = dateFormat.parse(currentTime); //系统当前时间
		Date startt=dateFormat.parse(startTime); //活动开始时间
		Date endt = dateFormat.parse(endTime);
		Date sign = dateFormat.parse(signStart);//活动开始签到时间
		time=startt.getTime()-now.getTime(); //系统当前时间和活动开始时间的时间差，用于倒计时
		endtime = endt.getTime()-now.getTime();//活动结束的倒计时的时间
		signtime = sign.getTime()-now.getTime();//活动开始签到的倒计时时间
		
		time = time/1000;//时间差的单位换位“秒”
		if(time<=0){	//如果活动已结束,倒计时的时间为0
			time = 0;
		}
		endtime = endtime/1000;//时间差的单位换位“秒”
		if(endtime<=0){	//如果活动已结束,倒计时的时间为0
			endtime = 0;
		}
		signtime = signtime/1000;//时间差的单位换位“秒”
		if(signtime<=0){	//如果活动已结束,倒计时的时间为0
			signtime = 0;
		}
		HttpSession session = request.getSession();
		session.setAttribute("time", time);//添加session会话，把活动开始倒计时time传到前台
		session.setAttribute("endtime", endtime);//添加session会话，把活动开始倒计时time传到前台
		session.setAttribute("signtime", signtime);//添加session会话，把活动开始倒计时time传到前台
		totalUserNum = activityService.userSelectToGetGiftNum();	//获取用户数量
		signNumber= signInService.signSelectSignInUserByActId(activity.getActId());
		lateSignNumber= signInService.signSelectLateUserByActId(activity.getActId());
		unSignNumber=totalUserNum - signNumber - lateSignNumber ;
		giftNumber = signInService.giftSelectSignInUserByActId(activity.getActId());
		signPercent = (float)signNumber/(float)totalUserNum;
		unGiftNumber = signNumber - giftNumber;
		
		String persent = String.valueOf(signPercent);
		activity.setPercent(persent);//保存活动参加率，动态修改活动参加率的值
		activityService.saveActPercent(activity);//保存活动参加率，动态修改活动参加率的值
		return "success";	 //正确，返回success
	}

	/**
	 * get,set方法	
	 * @return
	 */
	public List<Activity> getList() { //get方法
		return list;
	}
	public void setList(List<Activity> list) {//set方法
		this.list = list;
	}
	public Activity getActivity() { //get方法
		return activity;
	}
	public void setActivity(Activity activity) {//set方法
		this.activity = activity;
	}
	public ActivityService getActivityService() { //get方法
		return activityService;
	}
	public void setActivityService(ActivityService activityService) {//set方法
		this.activityService = activityService;
	}
	public String getEnd() { //get方法
		return end;
	}
	public void setEnd(String end) {//set方法
		this.end = end;
	}
	public int getSize() { //get方法
		return size;
	}
	public void setSize(int size) {//set方法
		this.size = size;
	}
	public Map<String, Object> getSessionMap() { //get方法
		return sessionMap;
	}
	public void setSessionMap(Map<String, Object> sessionMap) {//set方法
		this.sessionMap = sessionMap;
	}
	public PageDivided getPageDivided() { //get方法
		return pageDivided;
	}
	public void setPageDivided(PageDivided pageDivided) {//set方法
		this.pageDivided = pageDivided;
	}
	public long getTime() { //get方法
		return time;
	}
	public void setTime(long time) {//set方法
		this.time = time;
	}
	public long getEndtime() { //get方法
		return endtime;
	}
	public void setEndtime(long endtime) {//set方法
		this.endtime = endtime;
	}
	public long getSigntime() { //get方法
		return signtime;
	}
	public void setSigntime(long signtime) {//set方法
		this.signtime = signtime;

	}

	public int getUserId() {//get方法
		return userId;
	}

	public void setUserId(int userId) {//set方法
		this.userId = userId;

	}
	public int getTotalUserNum() {//get方法
		return totalUserNum;
	}
	public void setTotalUserNum(int totalUserNum) {//set方法
		this.totalUserNum = totalUserNum;
	}
	public int getSignNumber() {//get方法
		return signNumber;
	}
	public void setSignNumber(int signNumber) {//set方法
		this.signNumber = signNumber;
	}
	public int getUnSignNumber() {//get方法
		return unSignNumber;
	}
	public void setUnSignNumber(int unSignNumber) {//set方法
		this.unSignNumber = unSignNumber;
	}
	public int getLateSignNumber() {//get方法
		return lateSignNumber;
	}
	public void setLateSignNumber(int lateSignNumber) {//set方法
		this.lateSignNumber = lateSignNumber;
	}

	public int getGiftNumber() {//get方法
		return giftNumber;
	}

	public void setGiftNumber(int giftNumber) {//set方法
		this.giftNumber = giftNumber;
	}

	public int getUnGiftNumber() {//get方法
		return unGiftNumber;
	}

	public void setUnGiftNumber(int unGiftNumber) {//set方法
		this.unGiftNumber = unGiftNumber;

	}

	public float getSignPercent() {//get方法
		return signPercent;
	}

	public void setSignPercent(float signPercent) {//set方法
		this.signPercent = signPercent;
	}

	public SignInService getSignInService() {//get方法
		return signInService;
	}

	public void setSignInService(SignInService signInService) {//set方法
		this.signInService = signInService;

	}	
	
}
