package cn.org.njsoft.model;

import java.util.Date;
import java.util.Set;
/**
 * 2015/12/6
 * activity表的model类
 * @see cn.org.njsoft.model#Activity
 * @author JAQ
 *
 */
public class Activity {
	private int actId;//活动ID
	private Set<SignIn> signInAct;//one这头设置集合，和SignIn表的关联
	private Set<Gift> GiftAct;//one这头设置集合，和Gift表的关联
	private Date actStartTime;//活动开始时间
	private Date actEndTime;//活动结束时间
	private String actAddress;//活动地点
	private String actContent;//活动的内容
	private String actName;//活动的名称
	private int actGiftNumber;//活动礼品的种类
	private Date signStartTime;//活动开始签到的时间
	private String percent;//活动参加率
	private String StartTime;//活动开始时间的string类型，用于转化时间类型，输出到前台页面上
	private String EndTime;//活动结束时间的string类型，用于转化时间类型，输出到前台页面上
	private String signStart;//活动开始签到时间的string类型，用于转化时间类型，输出到前台页面上
	public int getActId() { //get方法
		return actId;
	}
	public void setActId(int actId) {//set方法
		this.actId = actId;
	}
	public Set<SignIn> getSignInAct() { //get方法
		return signInAct;
	}
	public void setSignInAct(Set<SignIn> signInAct) {//set方法
		this.signInAct = signInAct;
	}
	
	public String getActAddress() { //get方法
		return actAddress;
	}
	public void setActAddress(String actAddress) {//set方法
		this.actAddress = actAddress;
	}
	public String getActContent() { //get方法
		return actContent;
	}
	public void setActContent(String actContent) {//set方法
		this.actContent = actContent;
	}
	public String getActName() { //get方法
		return actName;
	}
	public void setActName(String actName) {//set方法
		this.actName = actName;
	}
	public int getActGiftNumber() { //get方法
		return actGiftNumber;
	}
	public void setActGiftNumber(int actGiftNumber) {//set方法
		this.actGiftNumber = actGiftNumber;
	}
	public String getPercent() { //get方法
		return percent;
	}
	public void setPercent(String percent) {//set方法
		this.percent = percent;
	}
	public Set<Gift> getGiftAct() { //get方法
		return GiftAct;
	}
	public void setGiftAct(Set<Gift> giftAct) {//set方法
		GiftAct = giftAct;
	}
	public Date getActStartTime() { //get方法
		return  actStartTime;
	}
	public void setActStartTime(Date actStartTime) {//set方法
		this.actStartTime = actStartTime;
	}
	public Date getSignStartTime() { //get方法
		return  signStartTime;
	}
	public void setSignStartTime(Date signStartTime) {//set方法
		this.signStartTime = signStartTime;
	}
	public Date getActEndTime() { //get方法
		return  actEndTime;
	}
	public void setActEndTime(Date actEndTime) {//set方法
		this.actEndTime = actEndTime;
	}
	public String getStartTime() { //get方法
		return StartTime;
	}
	public void setStartTime(String startTime) {//set方法
		StartTime = startTime;
	}
	public String getEndTime() { //get方法
		return EndTime;
	}
	public void setEndTime(String endTime) {//set方法
		EndTime = endTime;
	}
	public String getSignStart() { //get方法
		return signStart;
	}
	public void setSignStart(String signStart) {//set方法
		this.signStart = signStart;
	}
	
}
