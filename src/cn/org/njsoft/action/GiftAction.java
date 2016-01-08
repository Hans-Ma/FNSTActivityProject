package cn.org.njsoft.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.org.njsoft.model.Activity;
import cn.org.njsoft.model.Gift;
import cn.org.njsoft.model.PageDivided;
import cn.org.njsoft.model.SignGift;
import cn.org.njsoft.model.SignIn;
import cn.org.njsoft.model.User;
import cn.org.njsoft.service.ActivityService;
import cn.org.njsoft.service.GiftService;
import cn.org.njsoft.service.SignInService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 2015/12/8
 * 礼品的Action
 * @see cn.org.njsoft.action#GiftAction
 * @author JAQ
 *
 */
@SuppressWarnings("serial")
@Scope("request")
@Controller("giftAction")
public class GiftAction extends ActionSupport implements SessionAware {
	
	Gift gift = new Gift();  //实例化Gift类
	Activity activity = new Activity(); //实例化Activity类
	private String actId;  //活动ID的属性
	List<Activity> list; //list集合，用户礼品的添加，活动下拉列表自动更新数据库里的活动
	
	List<Gift> giftList;//list集合，用户礼品的分页
	private String end;
	private Map<String,Object> session;
	// 创建全局分页对象
	private PageDivided pageDivided = new PageDivided();
	private int giftNum;
	
	private int signActId;//由领取奖品页面前台传入活动的ID，named by author DB
	private int isGet;//传入前台控制隐藏域显示
	private int userId;//由领取奖品页面前台传入用户ID，为空是用户领取，不为空则是管理员手动代领
	//signGiftList
	List<SignGift> signGiftList = new ArrayList<SignGift>();
	List<SignIn>  signInList = new ArrayList<SignIn>(); 
	private int giftId;
	private int giftKind;
	private int kind;//前台判断添加礼品的种类个数
	
	@Autowired
	@Qualifier("giftServiceImpl")
	private GiftService giftService; 
	
	@Autowired
	@Qualifier("signInServiceImpl")
	private SignInService signInService; 
	
	@Autowired
	@Qualifier("activityServiceImpl")
	private ActivityService activityService;
	
	HttpServletRequest request = ServletActionContext.getRequest();
	
	/**
	 *添加礼品前的准备
	 * @see cn.org.njsoft.action#GiftAction
	 * @author JAQ
	 */
	public String addGiftReady() throws Exception {
		User user = (User) request.getSession().getAttribute("user");//判断用户是否登录	
		if(user != null){ //用户已登录
			if(activity.getActId() != 0 || activity.getActGiftNumber() != 0){
				int userNum = activityService.userSelectToGetGiftNum();		
				giftNum =  (int) ((int)userNum*0.88);	 //推荐活动的礼品数量是会员数量的1.2倍
				giftKind = activity.getActGiftNumber();
				giftNum = giftNum / giftKind;		
				kind = activity.getActGiftNumber();
				return "success"; //正确，返回success
			}else{
				return "input";		//错误，返回input	
			}
		}else{
			return "input";		//错误，返回input	
		}		
	}
	
	/**
	 * 2015/12/23
	 *补发礼品前的准备
	 * @see cn.org.njsoft.action#GiftAction
	 * @author JAQ
	 */
	public String addGiftReadyTwo() throws Exception {
		User user = (User) request.getSession().getAttribute("user");//判断用户是否登录
		if(user != null){ //用户已登录
			list = giftService.activitySelect();//添加礼品
			return "success"; //正确，返回success
		}else{
			return "input";		//错误，返回input	
		}		
	}
	/**
	 * 2015/12/13
	 *添加礼品
	 * @see cn.org.njsoft.action#GiftAction
	 * @author JAQ
	 */
	public String addGift() throws Exception {
		
		if (giftService.addGift(gift)){	//添加礼品
			return "success"; //正确，返回success		
		} else{
			return "input";//错误，返回input
		}
	}
	/**
	 * 2015/12/13
	 *添加补发的礼品
	 * @see cn.org.njsoft.action#GiftAction
	 * @author JAQ
	 */
	public String addGiftTwo() throws Exception {
		giftNum = giftService.getNumByActId(gift.getGiftAct().getActId()).size();
		gift.setGiftNum(giftNum);
		if (giftService.addGift(gift)){	//添加补发的礼品
			return "success"; //正确，返回success		
		} else{
			return "input";//错误，返回input
		}
	}


	/**
	 * 2015/12/17
	 * 查询礼品
	 * @see cn.org.njsoft.action#GiftAction
	 * @author MaYue
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
				session.put("end", Integer.parseInt(this.end));
				pageDivided.setEnd((int) ActionContext.getContext().getSession().get("end"));
			}
			//如果不查询所有用户的情况
			pageDivided.setTotal(giftService.giftSelect().size());// 用户信息总的条数
			//用户信息总的页数，用于可以分为多少页
			pageDivided.setTotalPage(pageDivided.getTotal() % pageDivided.getEnd() > 0 ? pageDivided.getTotal() / pageDivided.getEnd() + 1 : pageDivided.getTotal() / pageDivided.getEnd());
			//起始点
			pageDivided.setStart((pageDivided.getCurrentPage() - 1) * pageDivided.getEnd());
			list = giftService.activitySelect();
			ActionContext.getContext().getSession().put("actId", this.actId);
			giftList = giftService.giftSelectByPageAndById(this.actId, pageDivided.getStart(), pageDivided.getEnd());
			return SUCCESS;//正确，返回success
		}else{
			return "input";		//错误，返回input	
		}
	}
	
	/**
	 * 2015/12/21
	 * 通过活动id查询活动所有礼品
	 * @see cn.org.njsoft.action#GiftAction
	 * @author YXF
	 */
	public String getAllGiftByActid() throws Exception {
		// 通过活动id查询活动所有礼品
		giftList=giftService.getAllGiftByActid(Integer.parseInt(actId));
		//将giftList放入Session中以便giftList.jsp中礼品的下拉选项一直存在
		ServletActionContext.getRequest().getSession().setAttribute("giftList", giftList);
		ActionContext.getContext().getSession().put("actId",Integer.parseInt(actId));
		return SUCCESS;//正确，返回success
	}
	
	
	/**
	 * 2015/12/21
	 * 通过活动id查询该活动礼品种类
	 * @see cn.org.njsoft.action#GiftAction
	 * @author YXF
	 */ 
	public String showGiftCondition() throws Exception {
		int actid;
		ServletActionContext.getRequest().getSession().setAttribute("giftId", giftId);
		if (ActionContext.getContext().getSession().get("actId")!=null) {
			actid=(int)ActionContext.getContext().getSession().get("actId");
		}else {
			actid=Integer.parseInt("actId");
		}
		//获取的总的条数
		List<SignGift> totaList = giftService.getSignGiftByActidAndGiftId(giftId,actid);
		// 通过活动id查询活动所有礼品
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
			session.put("end", Integer.parseInt(this.end));
			pageDivided.setEnd((int) ActionContext.getContext().getSession().get("end"));
		}
		//如果不查询所有用户的情况
		pageDivided.setTotal(totaList.size());// 用户信息总的条数
		//用户信息总的页数，用于可以分为多少页
		pageDivided.setTotalPage(pageDivided.getTotal() % pageDivided.getEnd() > 0 ? pageDivided.getTotal() / pageDivided.getEnd() + 1 : pageDivided.getTotal() / pageDivided.getEnd());
		//起始点
		pageDivided.setStart((pageDivided.getCurrentPage() - 1) * pageDivided.getEnd());
		signGiftList=giftService.getSignGiftByActidAndGiftId(giftId,actid, pageDivided.getStart(), pageDivided.getEnd());
		return SUCCESS;//正确，返回success
	}
	
	/**
	 * 2015/12/21
	 * 通过活动id获取签到但未收到礼品的信息
	 * @see cn.org.njsoft.action#getNumByActid
	 * @author YXF
	 */ 
	public String getNumByActId() throws Exception {
		
		// 通过活动id查询活动所有礼品
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
			session.put("end", Integer.parseInt(this.end));
			pageDivided.setEnd((int) ActionContext.getContext().getSession().get("end"));
		}
		//如果不查询所有用户的情况
		pageDivided.setTotal(giftService.getNumByActId(Integer.parseInt(actId)).size());// 用户信息总的条数
		//用户信息总的页数，用于可以分为多少页
		pageDivided.setTotalPage(pageDivided.getTotal() % pageDivided.getEnd() > 0 ? pageDivided.getTotal() / pageDivided.getEnd() + 1 : pageDivided.getTotal() / pageDivided.getEnd());
		//起始点
		pageDivided.setStart((pageDivided.getCurrentPage() - 1) * pageDivided.getEnd());
		signInList=giftService.getNumByPageAndByActId(Integer.parseInt(actId), pageDivided.getStart(), pageDivided.getEnd());
		return SUCCESS;//正确，返回success
	}
	
	/**
	 * 2015/12/10
	 * 领取奖品
	 * @see cn.org.njsoft.action.GiftAction#giftGet
	 * @author DB
	 *
	 */
	//获取session里的User
		User user=(User) ActionContext.getContext().getSession().get("user");//获取用户登录信息
	
	public String giftGet() throws Exception {
		if (userId == 0) {// 等于0，表明由前台传入用户Id为空,用户签到
			userId = user.getUserId();// 取出session里用户Id
		}
		if (signInService.signInCheck(signActId, userId)) {//作弊检查
			if (signInService.isGet(signActId, userId) == 0) {//返回0，说明未领取奖
				List<Gift> giftList=giftService.getGiftTypes(signActId);//返回礼品表礼品种类
				for(int i = 0;i<giftList.size();i++){
				if(giftService.updateGiftRest(giftList.get(i).getGiftId())>=0){// 更新礼品表礼品剩余数量
					int giftState=1;
					signInService.updateGiftState(signActId, userId,giftState);// 更新签到表礼品状态记录，领取成功
					signInService.addSignGift(giftList.get(i).getGiftId(),signActId, userId);// 插入signgift表记录,
										//GiftId;signActId和userId确定SignId
					isGet = 0;// 领取成功！
					break;
				}
				if(i==giftList.size()-1){
				if(giftService.updateGiftRest(giftList.get(giftList.size()-1).getGiftId())<0){// 礼品表最后一个礼品剩余数量都小于0
					int giftState=2;
					signInService.updateGiftState(signActId, userId,giftState);// 更新签到表礼品状态记录，奖品发完了
					isGet = 3;// 奖品发完了！
				}
				}
				}
				
				
			} else if (signInService.isGet(signActId, userId) == 1) {
				isGet = 1;// 已经领取！
			}
		}else{
			isGet = 2;//身份验证不成功，需要验证手机号，指纹等
		}
			return "userGiftForUser";//普通用户
			
		}
		
	/**
	 * 2015/12/10
	 * 管理员发放奖品
	 * @see cn.org.njsoft.action.GiftAction#giftGetForAdmin
	 * @author DB
	 *
	 */
	public String giftGetForAdmin() throws Exception {
		List<Gift> giftList = giftService.getGiftTypes(signActId);// 返回礼品表礼品种类
		for (int i = 0; i < giftList.size(); i++) {
			if (giftService.updateGiftRest(giftList.get(i).getGiftId()) >= 0) {// 更新礼品表礼品剩余数量
				int giftState = 1;
				signInService.updateGiftState(signActId, userId, giftState);// 更新签到表礼品状态记录，领取成功
				signInService.addSignGift(giftList.get(i).getGiftId(),
						signActId, userId);// 插入signgift表记录,
				// GiftId;signActId和userId确定SignId
				isGet = 0;// 领取成功！
				break;
			}
			if (i == giftList.size() - 1) {
				if (giftService.updateGiftRest(giftList
						.get(giftList.size() - 1).getGiftId()) < 0) {// 礼品表最后一个礼品剩余数量都小于0
					isGet = 3;// 奖品发完了！
				}
			}
		}
		return "userGift";// 管理员

	}
	
	public List<SignIn> getSignInList() {
		return signInList;
	}
	public void setSignInList(List<SignIn> signInList) {
		this.signInList = signInList;
	}


	public void setGiftList(List<Gift> giftList) {//set方法
		this.giftList = giftList;
	}
	public Gift getGift() { //get方法
		return gift;
	}
	public void setGift(Gift gift) {//set方法
		this.gift = gift;
	}
	public GiftService getGiftService() { //get方法
		return giftService;
	}
	public void setGiftService(GiftService giftService) {//set方法
		this.giftService = giftService;
	}
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
	public String getActId() { //get方法
		return actId;
	}
	public void setActId(String actId) {//set方法
		this.actId = actId;
	}
	public String getEnd() { //get方法
		return end;
	}
	public void setEnd(String end) {//set方法
		this.end = end;
	}
	public PageDivided getPageDivided() { //get方法
		return pageDivided;
	}
	public void setPageDivided(PageDivided pageDivided) {//set方法
		this.pageDivided = pageDivided;
	}	
	public List<Gift> getGiftList() { //get方法
		return giftList;
	}
	@Override
	public void setSession(Map<String, Object> session) {//set方法
		this.session = session;
	}
	public int getGiftNum() { //get方法
		return giftNum;
	}
	public void setGiftNum(int giftNum) {//set方法
		this.giftNum = giftNum;
	}
	public ActivityService getActivityService() { //get方法
		return activityService;
	}
	public void setActivityService(ActivityService activityService) {//set方法
		this.activityService = activityService;
	}


	public int getSignActId() {
		return signActId;
	}


	public void setSignActId(int signActId) {
		this.signActId = signActId;
	}


	


	public int getIsGet() {
		return isGet;
	}


	public void setIsGet(int isGet) {
		this.isGet = isGet;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}		
	public List<SignGift> getSignGiftList() {//get方法
		return signGiftList;
	}
	public void setSignGiftList(List<SignGift> signGiftList) {//set方法
		this.signGiftList = signGiftList;
	}	
	public int getGiftId() {//get方法
		return giftId;
	}
	public void setGiftId(int giftId) {//set方法
		this.giftId = giftId;
	}
	public int getGiftKind() {//get方法
		return giftKind;
	}
	public void setGiftKind(int giftKind) {//set方法
		this.giftKind = giftKind;
	}
	public int getKind() {//get方法
		return kind;
	}
	public void setKind(int kind) {//set方法
		this.kind = kind;
	}
	
}
