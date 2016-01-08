package cn.org.njsoft.model;

import java.util.Set;
/**
 * 2015/12/6
 * gift表的model类
 * @see cn.org.njsoft.model#Gift
 * @author JAQ
 *
 */
public class Gift {
	private int giftId;//礼品的ID
	private String giftName;//礼品的名称
	private int giftNum;//礼品的数量
	private Activity giftAct;//作为外键   mang 这头设置实体类，作外键
	private Set<SignGift> signGift;//one这头设置集合
	private int giftRest;//礼品剩余量
	
	public int getGiftId() {//get方法
		return giftId;
	}
	public void setGiftId(int giftId) {//set方法
		this.giftId = giftId;
	}
	public String getGiftName() {//get方法
		return giftName;
	}
	public void setGiftName(String giftName) {//set方法
		this.giftName = giftName;
	}
	public int getGiftNum() {//get方法
		return giftNum;
	}
	public void setGiftNum(int giftNum) {//set方法
		this.giftNum = giftNum;
	}
	public Activity getGiftAct() {//get方法
		return giftAct;
	}
	public void setGiftAct(Activity giftAct) {//set方法
		this.giftAct = giftAct;
	}
	public Set<SignGift> getSignGift() {//get方法
		return signGift;
	}
	public void setSignGift(Set<SignGift> signGift) {//set方法
		this.signGift = signGift;
	}
	public int getGiftRest() {//get方法
		return giftRest;
	}
	public void setGiftRest(int giftRest) {//set方法
		this.giftRest = giftRest;
	}
}
