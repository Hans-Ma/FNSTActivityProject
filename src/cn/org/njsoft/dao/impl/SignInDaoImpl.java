package cn.org.njsoft.dao.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import cn.org.njsoft.dao.ActivityDao;
import cn.org.njsoft.dao.GiftDao;
import cn.org.njsoft.dao.SignInDao;
import cn.org.njsoft.dao.UserDao;
import cn.org.njsoft.model.Activity;
import cn.org.njsoft.model.Gift;
import cn.org.njsoft.model.SignGift;
import cn.org.njsoft.model.SignIn;
import cn.org.njsoft.model.User;
/**
 * 2015/12/11
 * SignInDao接口的实现类
 * @see cn.org.njsoft.dao.impl#SignInDaoImpl
 * @author DB
 */
@Service
public class SignInDaoImpl extends HibernateDaoSupport implements SignInDao{
	
	@Autowired
	@Qualifier("activityDaoImpl")
	private ActivityDao activityDao;
	@Autowired
	@Qualifier("userDaoImpl")
	private UserDao userDao;
	
	@Autowired
	@Qualifier("giftDaoImpl")
	private GiftDao giftDao;
	
	/**
	 * 2015/12/13
	 * 根据签到的活动ID来查询活动信息
	 * @see cn.org.njsoft.dao.SignInDao#check(int)
	 * @author DB
	 */
	@SuppressWarnings("unchecked")
	public Activity check(int signActId) {//检测注册用户是否可以签到，是否提前，是否迟到，发送验证码
		try{
			String queryString="from Activity a where a.actId=?";		
			Object[] values={signActId};
			List<Activity> list=super.getHibernateTemplate().find(queryString,values);	
			if(list.size()>0){
				Activity act=list.get(0);
				return act;//返回活动，用作判断活动时间与当前时间比较
			}else{
				return null;//返回null
			}			
		}catch(Exception e){
			e.printStackTrace();
			return null;//异常，返回null
		}
	}	
	/**
	 * 2015/12/8
	 * 检验字符串是否可以转化称整数类型
	 * @see  cn.org.njsoft.dao.SignInDao#isStrNum(String)
	 * @author JAQ
	 */
	public boolean isStrNum(String str) {
		if(str == null || str == ""){ //判断字符串是都为空
			return true; //字符串为空返回true
		}
		else{
			try {
				Integer.parseInt(str);//如果可以转化为int型
				return true;//返回true
			} catch (NumberFormatException e) {
				return false;//异常，返回false
			}
		}
	}
	/**
	 * 2015/12/13
	 * @see cn.org.njsoft.dao.SignInDao#addSignIn(int, int, int)
	 * @author DB
	 * 向签到表里插入记录
	 */
	@Override
	public void addSignIn(int signActId,int signState,int userId) {//向签到表里插入记录
		SignIn signIn=new SignIn();
		try{
			//获取用来插入signIn表的activity
			Activity activity =activityDao.activitySelectById(signActId);
			
			//获取用来插入signIn表的user
			User user =userDao.userGetById(userId);
			
			signIn.setSignAct(activity);
			signIn.setSignState(signState);//1正常签到，2签到迟到
			signIn.setSignUser(user);
			//signIn.setGiftState(0);
			//signIn.setSignCheck(0);

			Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());//获取系统当前时间
			//生成当前时间作为签到时间插入signIn表

			signIn.setSignTime(currentTimestamp);//设置签到时间
			getHibernateTemplate().save(signIn);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}	
	/**
	 * 2015/12/13
	 * @see cn.org.njsoft.dao.SignInDao#addSignIn(int, int, int)
	 * @author DB
	 * 向签到表里插入记录
	 */
	@Override
	public void addSignInForAdmin(int signActId,int signState,int userId) {//向签到表里插入记录
		// TODO Auto-generated method stub
		SignIn signIn=new SignIn();
		try{
			//获取用来插入signIn表的activity
			Activity activity =activityDao.activitySelectById(signActId);
			
			//获取用来插入signIn表的user
			User user =userDao.userGetById(userId);
			
			signIn.setSignAct(activity);
			signIn.setSignState(signState);//1正常签到，2签到迟到
			signIn.setSignUser(user);
			signIn.setGiftState(2);//管理员设置改用户该活动未领奖品
			signIn.setSignCheck(1);//管理员免身份验证
			
			Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());//获取系统当前时间
			//生成当前时间作为签到时间插入signIn表
			
			signIn.setSignTime(currentTimestamp);//设置签到时间
			getHibernateTemplate().save(signIn);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}	
	
	/**
	 * 2015/12/18
	 * 按照条件查询数据库， 生成报表。
	 * @see cn.org.njsoft.dao.SignInDao#selectSignin(int, int, int)
	 * @author YXF
	 */
	@SuppressWarnings("unchecked")
	public List<SignIn> selectNumSignin(int actid, int ifgetGift) {
		String queryString;
		List<SignIn> list = null;
			// ifgetGift为1，领取礼物
			if (ifgetGift == 1) {
				Object[] values = { actid, 1};
				queryString = "from SignIn a where a.signAct.actId=? and a.giftState=? and a.signUser.userType.typeId=2";
				list = super.getHibernateTemplate().find(queryString, values);
			}
			// ifgetGift为2，未领取礼物
			if (ifgetGift == 2) {
				Object[] values = { actid, 2};
				queryString = "from SignIn a where a.signAct.actId=? and a.giftState=? and a.signUser.userType.typeId=2";
				list = super.getHibernateTemplate().find(queryString, values);
			}
			// ifgetGift为0，未选择礼品
			if (ifgetGift == 0) {
				Object[] values = {actid};
				queryString = "from SignIn a where  a.signUser.userType.typeId=2 and a.signAct.actId=?";
				list = super.getHibernateTemplate().find(queryString, values);
			}
		return list;//返回当前结果
	}

	
	/**
	 * 2015/12/18
	 * 通过活动id搜寻非会员签到信息。
	 * @see cn.org.njsoft.dao.SignInDao#selectUnnumSignin(int)
	 * @author YXF
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SignIn> selectUnnumSignin(int actid) {
	try{
		List<SignIn> list = null;
		String queryString = "from SignIn a where a.signUser.userType.typeId= 3 and a.signAct.actId=?";
		Object[] values = {actid};
		list = super.getHibernateTemplate().find(queryString, values);
		return list;//返回当前结果
	}catch(Exception e){
		e.printStackTrace();
		return null;//异常，返回false
	}
	}
	
	/**
	 * 2015/12/18
	 * 按照条件查询数据库。
	 * @see cn.org.njsoft.dao.SignInDao#selectSigninByPage(int,int,int)
	 * @author MaYue
	 */
	@Override
	public List<SignIn> selectSigninByPage(final String hql, final int start, final int end) {
		@SuppressWarnings("unchecked")
		List<SignIn> list = getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query;
				query = session.createQuery(hql);
				if (end != 0) {//分页的end的值
					query.setFirstResult(start);//设置分页的起始值
					query.setMaxResults(end);//设置分页的终止值
				}
				List<SignIn> list = query.list();//查询结果存放在list里
				return list;//返回当前结果
			}
		});
		return list;//返回当前结果
	}

	/**
	 * 2015/12/19
	 * 统计已签到的人数
	 * @see  cn.org.njsoft.dao.SignInDao#signSelectSignInUserByActId(int)
	 * @author JAQ
	 */

	@Override
	public int signSelectSignInUserByActId(int signActId) {
		try{			
			String queryString="from SignIn a where a.signAct.actId=? and a.signState=1";	
			Object[] values={signActId};
			@SuppressWarnings("unchecked")
			List<Activity> list=super.getHibernateTemplate().find(queryString, values);
			if(list != null){//查询结果不为空
				return list.size();	//返回查询结果
			}else{
				return 0;//否则，返回0
			}		
		}catch(Exception e){
			e.printStackTrace();
			return 0;//异常，返回0
		}
	}
	/**
	 * 2015/12/19
	 * 统计迟到签到的人数
	 * @see  cn.org.njsoft.dao.SignInDao#signSelectLateUserByActId(int)
	 * @author JAQ
	 */
	@Override
	public int signSelectLateUserByActId(int signActId) {
		try{			
			String queryString="from SignIn a where a.signAct.actId=? and a.signState=2";	
			Object[] values={signActId};
			@SuppressWarnings("unchecked")
			List<Activity> list=super.getHibernateTemplate().find(queryString, values);
			if(list != null){//查询结果不为空
				return list.size();	//返回查询结果
			}else{
				return 0;//否则，返回0
			}		
		}catch(Exception e){
			e.printStackTrace();
			return 0;//异常，返回0
		}
	}
	/**
	 * 2015/12/21 
	 * 返回签到状态，返回true，表明已签过
	 * @see cn.org.njsoft.dao.SignInDao#isSignIn(int,int )
	 * @author DB
	 * 
	 */
	@Override
	public boolean isSignIn(int signActId, int userId) {
		try{
			String queryString="from SignIn s where s.signAct=? and s.signUser=? ";
			Activity signAct=new Activity();
			signAct.setActId(signActId);
			User user=new User();
			user.setUserId(userId);
			Object[] values={signAct,user};
			@SuppressWarnings("unchecked")
			List<SignIn> list=super.getHibernateTemplate().find(queryString,values);	
			if(list.size()>0){
				return true;//返回true，表明已签过
			}else{
				return false;//否则，返回false，还未签到
			}			
		}catch(Exception e){
			e.printStackTrace();
			return false;//异常，返回false
		}
	}
	
	/**
	 * 2015/12/21
	 * 统计已领礼品的人数
	 * @see cn.org.njsoft.dao.SignInService#signSelectSignInUserByActId(int )
	 * @author JAQ
	 * 
	 */
		@Override
		public int giftSelectSignInUserByActId(int signActId) {
			try{			
				String queryString="from SignIn a where a.signAct.actId=? and a.giftState=1";	
				Object[] values={signActId};
				@SuppressWarnings("unchecked")
				List<Activity> list=super.getHibernateTemplate().find(queryString, values);
				if(list != null){//查询结果不为空
					return list.size();	//返回查询结果
				}else{
					return 0;//否则，返回0
				}		
			}catch(Exception e){
				e.printStackTrace();
				return 0;//异常，返回0
			}
		}
	/**
	* 
	* 2015/12/22
	* 活动信息查询
    * @see cn.org.njsoft.dao.SignInDao#userSignInSelect()
	* @author JAQ
	*/
	@Override
	public List<SignIn> userSignInSelect(int actId) {
		try{
			String queryString="from SignIn u where u.signAct.actId=?";
			Object[] values={actId};
			@SuppressWarnings("unchecked")
			List<SignIn> list=super.getHibernateTemplate().find(queryString, values);	//查询所有活动的信息
			if(list != null){	//查询结果不为空
				return  list;//返回查询结果list
			}else{
				return null;//否则，返回null
			}			
		}catch(Exception e){
			e.printStackTrace();
			return null;//如果异常，返回null
		}
	}
	/**	 
	 * 2015/12/22
	 * 已亲到会员的分页查询
	 * @see cn.org.njsoft.dao.SignInDao#userSignInSelectByPage(int, int)
	 * @author JAQ
	 */
	@Override
	public List<SignIn> userSignInSelectByPage(final String hql, final int start, final int end, int actId) {
		@SuppressWarnings("unchecked")
		List<SignIn> list = getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query;
				query = session.createQuery(hql);
				if (end != 0) {
					query.setFirstResult(start);//分页的起始页
					query.setMaxResults(end);//分页的一页几行数据
				}
				List<Activity> list = query.list();//返回查询结果
				return list;//返回查询结果
			}
		});
		return list;//返回查询结果
	}

	/**
	 * 2015/12/23
	 * 审核通过，通过actId和userId设置SignIn表中signCheck状态为1
	 * @see cn.org.njsoft.dao.SignInDao#updateSignCheckByActId(int,int)
	 * @author YXF
	 * 
	 */	
	@Override
	public boolean updateSignCheckByActId(int actId, int userId) {
		boolean flag=false;
			try{
				String queryString="from SignIn s where s.signAct.actId = "+actId+" and s.signUser.userId= "+userId+"";
				@SuppressWarnings("unchecked")
				List<SignIn> list=super.getHibernateTemplate().find(queryString);
				list.get(0).setSignCheck(1);;
				this.getHibernateTemplate().update(list.get(0));
				flag=true;
			}catch(Exception e){
				e.printStackTrace();
				
			}
		return flag;
	}

	/**
	 * 2015/12/21 
	 * 返回禮品領取狀態，0未領取，1領取
	 * @see cn.org.njsoft.dao.SignInDao#isGet(int,int )
	 * @author DB
	 * 
	 */
	@Override
	public int isGet(int signActId, int userId) {
		try{
			String queryString="from SignIn s where s.signAct.actId = "+signActId+" and s.signUser.userId= "+userId+"";
			@SuppressWarnings("unchecked")
			List<SignIn> list=super.getHibernateTemplate().find(queryString);
			if(list.get(0).getGiftState()==1){
				return 1; //1領取
			}else{
				return 0; //0未領取
			}			
		}catch(Exception e){
			e.printStackTrace();
			return 0;//异常，返回0
		}
	}

	/**
	 * 2015/12/21 
	 * 更新签到表礼品状态记录
	 * @see cn.org.njsoft.dao.SignInDao#updateGiftState(int,int,int )
	 * @author DB
	 * 
	 */
	@Override
	public void updateGiftState(int signActId, int userId,int giftState) {
		try{
			String queryString="from SignIn s where s.signAct.actId = "+signActId+" and s.signUser.userId= "+userId+"";
			@SuppressWarnings("unchecked")
			List<SignIn> list=super.getHibernateTemplate().find(queryString);
			list.get(0).setGiftState(giftState);
			this.getHibernateTemplate().update(list.get(0));		
		}catch(Exception e){
			e.printStackTrace();
			
		}
		

	}
	/**
	 * 2015/12/21 
	 * 判断身份是否验证成功，若返回1，验证成功
	 * @see cn.org.njsoft.dao.SignInDao#signInCheck(int,int )
	 * @author DB
	 * 
	 */
	@Override
	public int signInCheck(int signActId, int userId) {
		try{
			String queryString="from SignIn s where s.signAct.actId = "+signActId+" and s.signUser.userId= "+userId+"";
			@SuppressWarnings("unchecked")
			List<SignIn> list=super.getHibernateTemplate().find(queryString);
			if(list.get(0).getSignCheck()==1){
				return 1; //验证成功
			}else{
				return 0; //验证不成功
			}			
		}catch(Exception e){
			e.printStackTrace();
			return 0;//异常，返回0
		}
	
	}
	/**
	 * 2015/12/21 
	 * GiftId;signActId和userId确定SignId
	 * @see cn.org.njsoft.dao.SignInDao#addSignGift(int,int )
	 * @author DB
	 * 
	 */
	@Override
	public void addSignGift(int giftId,int signActId, int userId) {
		SignGift signGift=new SignGift();
		try{
			//获取用来插入signgift表的signIn
			SignIn signIn =signInSelect(signActId,userId);
			
			//获取用来插入signgift表的gift
			Gift gift =giftDao.giftSelect(giftId);
			
			signGift.setLinkSignId(signIn);
			signGift.setLinkGiftId(gift);
			getSession().flush();
		    getSession().clear();
		    getHibernateTemplate().merge(signGift);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 2015/12/21 
	 * 通过活动id和用户Id,搜寻签到信息
	 * @see cn.org.njsoft.dao.SignInDao#signInSelect(int,int )
	 * @author DB
	 * 
	 */
	@Override
	public SignIn signInSelect(int signActId, int userId) {
		try {
			String queryString = "from SignIn s where s.signAct.actId = "
					+ signActId + " and s.signUser.userId= " + userId + "";
			@SuppressWarnings("unchecked")
			List<SignIn> signInList =super.getHibernateTemplate().find(
					queryString);

			return signInList.get(0);

		} catch (Exception e) {
			e.printStackTrace();
			return null;// 异常，返回null
		}
	}
	

}
