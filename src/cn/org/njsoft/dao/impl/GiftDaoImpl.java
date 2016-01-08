package cn.org.njsoft.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import cn.org.njsoft.dao.GiftDao;
import cn.org.njsoft.model.Activity;
import cn.org.njsoft.model.Gift;
import cn.org.njsoft.model.SignGift;
import cn.org.njsoft.model.SignIn;
/**
 * 2015/12/10
 * GiftDao接口的实现类
 * @see cn.org.njsoft.dao.impl#GiftDaoImpl
 * @author JAQ
 *
 */
@Service
public class GiftDaoImpl extends HibernateDaoSupport implements GiftDao{
	/**
	 * 2015/12/10
	 * 添加礼品
	 * @see cn.org.njsoft.dao.GiftDao#addGift(Gift)
	 * @author JAQ
	 */
	public boolean addGift(Gift gift) {
		try{			
			gift.setGiftRest(gift.getGiftNum()); //设置gift的剩余量的 初始值是gift的数量
			Activity activity = activitySelectById(gift.getGiftAct().getActId());	//根据ID查询活动
			gift.setGiftAct(activity);//上面的查询结果给Gift类里的Activity类型的外键设值
			if(getHibernateTemplate().save(gift) != null){//插入结果不为空
				return true;//返回true
			}else{
				return false;//否则，返回false
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;//异常，返回false
		}
	}
	/**
	 * 2015/12/10
	 * 根据活动ID查询活动表，添加礼品时设置礼品model里的signGift的值
	 * @see cn.org.njsoft.dao.GiftDao#activitySelectById(int)
	 * @author JAQ
	 */
	@SuppressWarnings("unchecked")
	public Activity activitySelectById(int actId) {
		try{			
			String queryString="from Activity a where a.actId = ?";	
			Object[] values={actId};
			List<Activity> list=super.getHibernateTemplate().find(queryString, values);
			if(list != null){//查询结果不为空
				return list.get(0);	//返回查询结果
			}else{
				return null;//否则，返回null
			}		
		}catch(Exception e){
			e.printStackTrace();
			return null;//异常，返回null
		}
	}


/**
	 * 2015/12/10
	 * 查询活动表，添加礼品时动态修改下拉列表的值
	 * @see cn.org.njsoft.dao.GiftDao#activitySelect()
	 * @author JAQ
	 */
	@SuppressWarnings("unchecked")
	public List<Activity> activitySelect() {
		try{	
			@SuppressWarnings("unused")
			Date dateNow=new Date();
			String queryString="from Activity a ";	//查询活动表
			List<Activity> list=super.getHibernateTemplate().find(queryString);
			if(list != null){//查询结果不为空
				return list;	//返回当前结果
			}else{
				return null;//否则，返回null
			}		
		}catch(Exception e){
			e.printStackTrace();
			return null;//异常，返回null
		}
	}
	/**
	 * 2015/12/17
	 * 礼品信息查询
	 * @author MaYue
	 * @see cn.org.njsoft.dao.GiftDao#giftSelectByPage(final String, final int, final int)
	 */
	@Override
	public List<Gift> giftSelectByPage(final String hql, final int start, final int end) {
		@SuppressWarnings("unchecked")
		List<Gift> list = getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query;
				query = session.createQuery(hql);
				if (end != 0) {//分页的end的值
					query.setFirstResult(start);//设置分页的起始值
					query.setMaxResults(end);//设置分页的终止值
				}
				List<Gift> list = query.list();//查询结果存放在list里
				return list;//返回当前结果
			}
		});
		return list;//返回当前结果
	}

	/**
	 * 2015/12/17
	 * 所有礼品信息查询
	 * @author MaYue
	 * @see cn.org.njsoft.dao.GiftDao#giftSelect()
	 */
	@Override
	public List<Gift> giftSelect() {
		try{
			String queryString="from Gift";//查询gift表的全部信息
			@SuppressWarnings("unchecked")
			List<Gift> list=super.getHibernateTemplate().find(queryString);//查询gift表的全部信息
			return list;	//返回当前结果	
		}catch(Exception e){
			e.printStackTrace();
			return null;//异常，返回false
		}
	}

	/**
	 * 2015/12/21
	 * 通过活动id查询活动所有礼品
	 * @see cn.org.njsoft.dao.GiftDao# getAllGiftByActid(actId)
	 * @author YXF
	 */
	@Override
	public List<Gift> getAllGiftByActid(int actId) {
		try{
			String queryString="from Gift a where a.giftAct.actId=?";//查询gift表的全部信息
			@SuppressWarnings("unchecked")
			List<Gift> giftList=super.getHibernateTemplate().find(queryString, actId);//查询gift表的全部信息
			return giftList;	//返回当前结果	
		}catch(Exception e){
			e.printStackTrace();
			return null;//异常，返回false
		}
	}

	/**	 
	 * 2015/12/21
	 * 根据活动ID查询所有礼品
	 * @see cn.org.njsoft.dao.GiftDao#giftSelectByActID(int)
	 * @author JAQ	 
	 */
	@Override
	public int giftSelectByActID(int actId) {
		try{
			int num = 0;
			String queryString="from Gift u where u.giftAct.actId = ? ";//查询Gift表
			Object[] values={actId};		//系统当前活动ID
			@SuppressWarnings("unchecked")
			List<Gift> list=super.getHibernateTemplate().find(queryString, values);//查询User表
			for(int i=0; i<list.size(); i++){
				num = num + list.get(i).getGiftNum();
			}
			return num;		//返回user表的有多少条数据
		}catch(Exception e){
			e.printStackTrace();
			return 0;//如果异常，返回0
		}
	}
	/**	 
	 * 2015/12/21
	 * 根据活动ID查询并返回礼品表所有礼品种类
	 * @see cn.org.njsoft.dao.GiftDao#getGiftTypes(int)
	 * @author DB	 
	 */
	@Override
	public List<Gift> getGiftTypes(int signActId) {
		try{
			String queryString="from Gift u where u.giftAct.actId = ? ";//查询Gift表
			Object[] values={signActId};		
			@SuppressWarnings("unchecked")
			List<Gift> list=super.getHibernateTemplate().find(queryString, values);//查询Gift表
			return list;		//返回Gift表的有多少种礼品
		}catch(Exception e){
			e.printStackTrace();
			return null;//如果异常，返回null
		}
	}
	/**	 
	 * 2015/12/21
	 * 更新礼品表礼品剩余数量,并返回剩余量
	 * @see cn.org.njsoft.dao.GiftDao#updateGiftRest(int)
	 * @author DB	 
	 */
	@Override
	public int updateGiftRest(int giftId) {
		try{
			String queryString="from Gift u where u.giftId = ? ";//查询Gift表
			Object[] values={giftId};		
			@SuppressWarnings("unchecked")
			List<Gift> giftList= super.getHibernateTemplate().find(queryString, values);//查询Gift表
			giftList.get(0).setGiftRest(giftList.get(0).getGiftRest()-1);
			this.getHibernateTemplate().update(giftList.get(0));
			return giftList.get(0).getGiftRest();		//返回Gift表该礼品剩余量
		}catch(Exception e){
			e.printStackTrace();
			return 0;//如果异常，返回0
		}
	}
	/**	 
	 * 2015/12/21
	 * 查询该礼品信息
	 * @see cn.org.njsoft.dao.GiftDao#giftSelect(int)
	 * @author DB	 
	 */
	@Override
	public Gift giftSelect(int giftId) {
		try{
			String queryString="from Gift u where u.giftId = ? ";//查询Gift表
			Object[] values={giftId};		
			@SuppressWarnings("unchecked")
			List<Gift> giftList= super.getHibernateTemplate().find(queryString, values);//查询Gift表
			return giftList.get(0);		
		}catch(Exception e){
			e.printStackTrace();
			return null;//如果异常，返回null
		}

	}
	

	/**	 
	 * 2015/12/21
	 * 根据活动ID和礼品ID查询所有礼品分配
	 * @see cn.org.njsoft.dao.GiftDao#getSignGiftByActidAndGiftId(giftId,actid)
	 * @author YXF	 
	 */
	@Override
	public List<SignGift> getSignGiftByActidAndGiftId(int giftId, int actId) {
		try{
			String queryString="from SignGift a where a.linkSignId.signAct.actId=? and a.linkGiftId.giftId=?";//查询gift表的全部信息
			Object[] values={actId,giftId};		//活动ID和礼物ID
			@SuppressWarnings("unchecked")
			List<SignGift> signGiftList=super.getHibernateTemplate().find(queryString, values);//查询gift表的全部信息
			return signGiftList;	//返回当前结果	
		}catch(Exception e){
			e.printStackTrace();
			return null;//异常，返回false
		}
	}
	/**	 
	 * 2015/12/21
	 * 分页查询，根据活动ID和礼品ID查询所有礼品分配
	 * @see cn.org.njsoft.dao.GiftDao#getSignGiftByActidAndGiftId(giftId,actid,start, end)
	 * @author YXF	 
	 */
	@Override
	public List<SignGift> getSignGiftByActidAndGiftId(final int giftId,final int actid,
			final int start,final int end) {
		final String hql="from SignGift a where a.linkSignId.signAct.actId="+actid+" and a.linkGiftId.giftId="+giftId+"";
		@SuppressWarnings("unchecked")
		List<SignGift> signGiftlist = getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query;
				query = session.createQuery(hql);
				if (end != 0) {//分页的end的值
					query.setFirstResult(start);//设置分页的起始值
					query.setMaxResults(end);//设置分页的终止值
				}
				List<SignGift> signGiftlist = query.list();//查询结果存放在list里
				return signGiftlist;//返回当前结果
			}
		});
		return signGiftlist;//返回当前结果
	}
	
	/**	 
	 * 2015/12/22
	 * 分页查询，根据活动ID查询所有礼品分配查询已签到但未收到礼品的会员信息
	 * @see cn.org.njsoft.dao.GiftDao#getNumByPageAndByActId(actid, start,end)
	 * @author YXF	 
	 */
	@Override
	public List<SignIn> getNumByPageAndByActId(final int actid,final int start,final int end) {
		final String hql="from SignIn a where  a.giftState=2 and  a.signAct.actId="+actid+"";
		@SuppressWarnings("unchecked")
		List<SignIn> SignInList = getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query;
				query = session.createQuery(hql);
				if (end != 0) {//分页的end的值
					query.setFirstResult(start);//设置分页的起始值
					query.setMaxResults(end);//设置分页的终止值
				}
				List<SignIn> SignInList = query.list();//查询结果存放在list里
				return SignInList;//返回当前结果
			}
		});
		return SignInList;//返回当前结果
	}
	
	
	
	/**	 
	 * 2015/12/22
	 * 根据活动ID查询所有礼品分配查询已签到但未收到礼品的会员信息
	 * @see cn.org.njsoft.dao.GiftDao#getNumByActId(actid)
	 * @author YXF	 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SignIn> getNumByActId(int actid) {
	try{
		List<SignIn> list = null;
		String queryString = "from SignIn a where a.giftState=2 and a.signAct.actId=?";
		Object[] values = {actid};
		list = super.getHibernateTemplate().find(queryString, values);
		return list;//返回当前结果
	}catch(Exception e){
		e.printStackTrace();
		return null;//异常，返回false
	}
	}
}


