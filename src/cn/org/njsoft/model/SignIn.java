package cn.org.njsoft.model;

import java.util.Date;
import java.util.Set;
/**
 * 2015/12/6
 * SignIn表的model类
 * @see cn.org.njsoft.model#SignIn
 * @author JAQ
 *
 */
public class SignIn {
	private int signId;//签到的ID
	private Set<SignGift> signGift;//one这头设置集合
	private User signUser;//作为外键   mang 这头设置实体类，作外键
	private Activity signAct;//作为外键   mang 这头设置实体类，作外键
	private int signState;//签到的状态，1为已签到，0为未签到,2为迟到
	private int giftState;//礼品状态，1为已经领礼品，0为未领礼品
	private Date signTime;//签到时间
	private int signCheck;	//签到检验，防作弊状态，1为正常，0为异常
	public int getSignId() {//get方法
		return signId;
	}
	public void setSignId(int signId) {//set方法
		this.signId = signId;
	}
	public User getSignUser() {//get方法
		return signUser;
	}
	public void setSignUser(User signUser) {//set方法
		this.signUser = signUser;
	}
	public Activity getSignAct() {//get方法
		return signAct;
	}
	public void setSignAct(Activity signAct) {//set方法
		this.signAct = signAct;
	}
	public int getSignState() {//get方法
		return signState;
	}
	public void setSignState(int signState) {//set方法
		this.signState = signState;
	}
	public int getGiftState() {//get方法
		return giftState;
	}
	public void setGiftState(int giftState) {//set方法
		this.giftState = giftState;
	}
	public Date getSignTime() {//get方法
		return signTime;
	}
	public void setSignTime(java.util.Date tDate) {//set方法
		this.signTime = tDate;
	}
	public int getSignCheck() {//get方法
		return signCheck;
	}
	public void setSignCheck(int signCheck) {//set方法
		this.signCheck = signCheck;
	}
	public Set<SignGift> getSignGift() {//get方法
		return signGift;
	}
	public void setSignGift(Set<SignGift> signGift) {//set方法
		this.signGift = signGift;
	}
	


}
