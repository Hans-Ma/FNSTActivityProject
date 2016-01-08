package cn.org.njsoft.model;
/**
 * 2015/12/6
 * SignGift表的model类
 * @see cn.org.njsoft.model#SignGift
 * @author JAQ
 *
 */
public class SignGift {
	private int signGiftId;//签到礼品情况表的ID
	private SignIn linkSignId;//作为外键   mang 这头设置实体类，作外键，与SignIn表关联
	private Gift linkGiftId;//作为外键   mang 这头设置实体类，作外键，与Gift表关联
	
	public int getSignGiftId() {//get方法
		return signGiftId;
	}
	public void setSignGiftId(int signGiftId) {//set方法
		this.signGiftId = signGiftId;
	}
	public SignIn getLinkSignId() {//get方法
		return linkSignId;
	}
	public void setLinkSignId(SignIn linkSignId) {//set方法
		this.linkSignId = linkSignId;
	}
	public Gift getLinkGiftId() {//get方法
		return linkGiftId;
	}
	public void setLinkGiftId(Gift linkGiftId) {//set方法
		this.linkGiftId = linkGiftId;
	}


}
