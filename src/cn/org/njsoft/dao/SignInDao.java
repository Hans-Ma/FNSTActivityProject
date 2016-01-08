package cn.org.njsoft.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.org.njsoft.model.Activity;
import cn.org.njsoft.model.SignIn;
/**
 * 2015/12/10
 * SignInDao接口
 * @see cn.org.njsoft.dao#SignInDao
 * @author DB
 *
 */
@Service
public interface SignInDao {
	public Activity check(int signActId); //检测注册用户是否可以签到，是否提前，是否迟到，发送验证码
	public void addSignIn(int signActId,int signState,int userId);//向签到表里插入记录	
	public List<SignIn> selectSigninByPage(String hql, int start, int end);//按照分页条件查询签到。
	public List<SignIn> selectNumSignin(int actid, int ifgetGift);//通过活动id搜寻非会员签到信息。
	public List<SignIn> selectUnnumSignin(int actid);//通过活动id搜寻非会员签到信息
	public int signSelectSignInUserByActId(int signActId);//统计已签到的人数
	public int signSelectLateUserByActId(int signActId);//统计迟到签到的人数
	public int giftSelectSignInUserByActId(int signActId);//统计已领礼品的人数
	public boolean isSignIn(int signActId, int userId);//判断是否签到
	public int isGet(int signActId, int userId);//返回禮品領取狀態，0未領取，1領取
	public void updateGiftState(int signActId, int userId,int giftState); //更新签到表礼品状态记录
	public int signInCheck(int signActId, int userId);//判断身份是否验证成功，若返回1，验证成功
	public void addSignGift(int giftId,int signActId, int userId);//GiftId;signActId和userId确定SignId
	public SignIn signInSelect(int signActId,int userId);//通过活动id和用户Id,搜寻签到信息
	public List<SignIn> userSignInSelect(int actId);//已签到会员的查询
	public List<SignIn> userSignInSelectByPage( String hql,  int start,int end,int actId);//已签到会员的分页查询
	public boolean updateSignCheckByActId(int actId, int userId);//审核通过，通过actId和userId设置SignIn表中signCheck状态为1
	public void addSignInForAdmin(int signActId, int signState, int userId);//管理员向签到表里插入记录，免验证，设置未领奖品

}
