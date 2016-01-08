package cn.org.njsoft.service;

import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import cn.org.njsoft.model.SignIn;
/**
 * 2015/12/10
 * 签到的Service
 * @see cn.org.njsoft.service#SignInService
 * @author DB
 *
 */
@Scope("singleton")
@Service("signInService")
public interface SignInService {
	public int check(int signActId);//活动签到时间，活动开始时间与当前时间比较
	public void addSignIn(int signActId, int signState,int userId); //向签到表里插入记录	
	public List<SignIn> selectNumSignin(int actid, int ifgetGift);//查询会员信息签到
	public int signSelectSignInUserByActId(int signActId);//统计已签到的人数
	public int signSelectLateUserByActId(int signActId);//统计迟到签到的人数
	public int giftSelectSignInUserByActId(int signActId);//统计已领礼品的人数
	public List<SignIn> selectUnnumSignin(int actid);//查询非会员信息签到
	public boolean isSignIn(int signActId, int userId);//查询该会员此活动是否签到
	public int isGet(int signActId, int userId);//查询该会员此活动是否领取奖品
	public boolean signInCheck(int signActId, int userId);//判断身份是否验证成功，若返回true，验证成功
	public void updateGiftState(int signActId, int userId, int giftState);// 更新签到表礼品状态记录
	public void addSignGift(int giftId,int signActId, int userId); //插入signgift表记录,GiftId;signActId和userId确定SignId
	public List<SignIn> userSignInSelect(int actId);//已签到会员的查询
	public List<SignIn> userSignInSelectByPage(int start,int actId, int end);//已签到会员的分页查询
	public boolean updateSignCheckByActId(int actId,int userId);//审核通过，通过actId和userId设置SignIn表中signCheck状态为1
	public void addSignInForAdmin(int signActId, int signState, int userId);//管理员向签到表里插入记录，免验证，设置未领奖品
}
