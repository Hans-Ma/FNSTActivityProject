package cn.org.njsoft.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.org.njsoft.dao.SignInDao;
import cn.org.njsoft.model.Activity;
import cn.org.njsoft.model.SignIn;
import cn.org.njsoft.service.SignInService;
/**
 * 2015/12/10
 * 签到的Service的实现类
 * @see cn.org.njsoft.service.impl#SignInServiceImpl
 * @author DB
 *
 */
@Scope("singleton")
@Service("signInServiceImpl")
public class SignInServiceImpl implements SignInService{
	
	@Autowired
	@Qualifier("signInDaoImpl")
	private SignInDao signInDao;
	/**
	 * 2015/12/14
	 * 签到检查
	 * @see cn.org.njsoft.dao.SignInService#check(int )
	 * @author DB
	 * 
	 */
	@Override
	public int check(int signActId) {	//活动签到时间，活动开始时间与当前时间比较	
		
			if (signInDao.check(signActId)!=null){
				Activity act=signInDao.check(signActId);
				
				//获取当前时间
				
				Date dateNow=new Date();
				@SuppressWarnings("unused")
				DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				//下面比较时间
			        try {
			        	Date dtSST = act.getSignStartTime();//签到时间
			        	Date dtAST = act.getActStartTime();//活动开始时间
			        	Date dtAET = act.getActEndTime();//活动结束时间
			            if (dateNow.before(dtSST)) {
			                return 0;//签到没开始，不可签到，action里check=0
			            } else if (dateNow.after(dtAST)&&dateNow.before(dtAET)) {
			                return 1;//活动开始，用户迟到，仍可签到，action里check=1
			            } else if (dateNow.after(dtAET)){
			                return 2;//活动结束，不可签到，action里check=2
			            }
			        } catch (Exception exception) {
			            exception.printStackTrace();
			        }
			        
				
			}
			return 3;//签到成功，action里check=3		
	}
	/**
	 * 2015/12/16
	 * 添加签到信息
	 * @see cn.org.njsoft.dao.SignInService#addSignIn(int ,int ,int )
	 * @author DB
	 * 
	 */
	@Override
	public void addSignIn(int signActId,int signState,int userId) {//传入活动ID，用户ID，签到状态
		signInDao.addSignIn(signActId,signState, userId);//向签到表里插入记录
	}
	/**
	 * 2015/12/16
	 * 管理员添加签到信息
	 * @see cn.org.njsoft.dao.SignInService#addSignInForAdmin(int ,int ,int )
	 * @author DB
	 * 
	 */
	@Override
	public void addSignInForAdmin(int signActId,int signState,int userId) {//传入活动ID，用户ID，签到状态
		// TODO Auto-generated method stub
		signInDao.addSignInForAdmin(signActId,signState, userId);//向签到表里插入记录
	}
	
	/**	 
	 * 2015/12/18
	 * 按照条件查询数据库， 生成报表。
	 * @see cn.org.njsoft.dao.SignInService#selectNumSignin(int ,int ,int )
	 * @author YXF
	 *
	 */
	@Override
	public List<SignIn> selectNumSignin(int actid, int ifgetGift){
		return signInDao.selectNumSignin(actid,ifgetGift);
	}

/**
 * 2015/12/19
 * 统计已签到的人数
 * @see cn.org.njsoft.dao.SignInService#signSelectSignInUserByActId(int )
 * @author JAQ
 * 
 */
	@Override
	public int signSelectSignInUserByActId(int signActId) {
		if (signInDao.signSelectSignInUserByActId(signActId) != 0){
			return signInDao.signSelectSignInUserByActId(signActId);
		}else{
			return 0;
		}

	
	}
	/**
	 * 2015/12/19
	 * 统计迟到签到的人数
	 * @see cn.org.njsoft.dao.SignInService#signSelectLateUserByActId(int )
	 * @author JAQ
	 * 
	 */
	@Override
	public int signSelectLateUserByActId(int signActId) {
		if (signInDao.signSelectLateUserByActId(signActId) != 0){
			return signInDao.signSelectLateUserByActId(signActId);
		}else{
			return 0;
		}
	}


	/**	 
	 * 2015/12/18
	 * 按照条件查询数据库， 生成报表。
	 * @see cn.org.njsoft.dao.SignInService#selectUnnumSignin(int )
	 * @author YXF
	 *
	 */
	@Override
	public List<SignIn> selectUnnumSignin(int actid) {
		// TODO Auto-generated method stub
		return signInDao.selectUnnumSignin(actid);
	}
	
	@Override

	public boolean isSignIn(int signActId, int userId) {
		// TODO Auto-generated method stub
		return signInDao.isSignIn(signActId, userId);
	}
	

	/**
	 * 2015/12/21 统计已领礼品的人数
	 * 
	 * @see cn.org.njsoft.dao.SignInService#signSelectSignInUserByActId(int )
	 * @author JAQ
	 * 
	 */
	@Override
	public int giftSelectSignInUserByActId(int signActId) {
		if (signInDao.giftSelectSignInUserByActId(signActId) != 0) {
			return signInDao.giftSelectSignInUserByActId(signActId);
		} else {
			return 0;
		}

	}

	/**
	 * 2015/12/21 
	 * 判斷是否領取了獎品，返回0，说明未领取奖
	 * @see cn.org.njsoft.dao.SignInService#isGet(int,int )
	 * @author DB
	 * 
	 */
	@Override
	public int isGet(int signActId, int userId) {
		// TODO Auto-generated method stub
		return signInDao.isGet(signActId, userId);
	}
	/**
	 * 2015/12/21  
	 * 更新签到表礼品状态记录
	 * @see cn.org.njsoft.dao.SignInService#updateGiftState(int,int )
	 * @author DB
	 * 
	 */
	@Override
	public void updateGiftState(int signActId, int userId,int giftState) {
		// TODO Auto-generated method stub
		signInDao.updateGiftState(signActId, userId,giftState);
	}
	/**
	 * 2015/12/21 
	 * 判断身份是否验证成功，若返回true，验证成功
	 * @see cn.org.njsoft.dao.SignInService#signInCheck(int,int )
	 * @author DB
	 * 
	 */
	@Override
	public boolean signInCheck(int signActId, int userId) {
		// TODO Auto-generated method stub
		if(signInDao.signInCheck(signActId, userId)==1){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 2015/12/21 
	 * signActId和userId确定SignId
	 * @see cn.org.njsoft.dao.SignInService#addSignGift(int,int )
	 * @author DB
	 * 
	 */
	@Override
	public void addSignGift(int giftId,int signActId, int userId) {
		// TODO Auto-generated method stub
		signInDao.addSignGift(giftId,signActId, userId);
		
	}

	/**	
	 * 2015/12/22
	 * 已签到会员的查询
	 * @see cn.org.njsoft.dao.SignInService#userSignInSelect()
	 * @author JAQ
	 */
	@Override
	public List<SignIn> userSignInSelect(int actId){
		if (signInDao.userSignInSelect(actId) != null){
			List<SignIn> list = signInDao.userSignInSelect(actId);
			return list;
		}
		return null;		
	}
	
	/**
	 * 2015/12/22
	 * 已签到会员的分页查询
	 * @see cn.org.njsoft.dao.SignInService#activitySelectByPage( int, int)
	 * @author JAQ
	 * 
	 */	
	@Override
	public List<SignIn> userSignInSelectByPage(int start,int actId, int end) {
		String hql;
		hql="from SignIn u where u.signAct.actId="+actId;
		return signInDao.userSignInSelectByPage(hql,start,end,actId);
	}
	/**
	 * 2015/12/23
	 * 审核通过，通过actId和userId设置SignIn表中signCheck状态为1
	 * @see cn.org.njsoft.dao.SignInService#updateSignCheckByActId(int,int)
	 * @author YXF
	 * 
	 */	
	@Override
	public boolean updateSignCheckByActId(int actId,int userId) {
		return signInDao.updateSignCheckByActId(actId,userId);
	}


}
