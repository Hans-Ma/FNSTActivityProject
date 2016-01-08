package cn.org.njsoft.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Service;

import cn.org.njsoft.dao.ActivityDao;
import cn.org.njsoft.model.Activity;
import cn.org.njsoft.model.Gift;
import cn.org.njsoft.model.User;
/**
 * 2015/12/10
 * ActivityDao接口的实现类
 * @see cn.org.njsoft.dao.impl#ActivityDaoImpl
 * @author JAQ
 *
 */
@Service
public class ActivityDaoImpl extends HibernateDaoSupport implements ActivityDao{
	/**	 
	 * 2015/12/14
	 * 活动信息分页查询
	 * @see cn.org.njsoft.dao.ActivityDao#activitySelectByPage(String, int, int)
	 * @author JAQ
	 * 
	 */
	@Override
	public List<Activity> activitySelectByPage(final String hql, final int start, final int end) {
		@SuppressWarnings("unchecked")
		List<Activity> list = getHibernateTemplate().executeFind(new HibernateCallback() {
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
	 * 
	 * 2015/12/14
	 * 活动信息查询
	 * @see cn.org.njsoft.dao.ActivityDao#activitySelect()
	 * @author JAQ
	 */
	@Override
	public List<Activity> activitySelect() {
		try{
			String queryString="from Activity u";
			@SuppressWarnings("unchecked")
			List<Activity> list=super.getHibernateTemplate().find(queryString);	//查询所有活动的信息
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
	 * 
	 * 2015/12/14
	 * 未签到活动信息查询
	 * @see cn.org.njsoft.dao.ActivityDao#activityUnsign(int)
	 * @author DB
	 */
	@Override
	public List<Activity> activityUnsign(int userId) {
		try{
			String queryString="from Activity a where a not in (select signAct from SignIn s where s.signUser.userId="+userId+")";
			@SuppressWarnings("unchecked")
			List<Activity> list=super.getHibernateTemplate().find(queryString);	//查询所有活动的信息
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
	 * 2015/12/14
	 * 未签到活动信息分页查询
	 * @see cn.org.njsoft.dao.ActivityDao#activityUnsignByPage(final String , final int, final int)
	 * @author DB
	 * 
	 */
	@Override
	public List<Activity> activityUnsignByPage(final String hql, final int start, final int end) {
		@SuppressWarnings("unchecked")
		List<Activity> list = getHibernateTemplate().executeFind(new HibernateCallback() {
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
	 * 
	 * 2015/12/16
	 * 根据活动ID查询活动表
	 * @see cn.org.njsoft.dao.ActivityDao#addGift(Gift)
	 * @author JAQ
	 */
	public Activity activitySelectById(int actId) {
		try{	
			Activity activity = (Activity)super.getHibernateTemplate().get(Activity.class, actId);//根据ID查询活动的详细信息
			if(activity != null){//查询结果不为空
				return activity;	//返回查询结果
			}else{
				return null;//否则，返回null
			}		
		}catch(Exception e){
			e.printStackTrace();
			return null;//如果异常，返回null
		}
	}

	/**
	 * 2015/12/15
	 * 添加活动
	 * @see cn.org.njsoft.dao.GiftDao#addGift(Gift)
	 * @author JAQ
	 */
	public boolean addActivity(Activity activity) {
		try{
			String percent = "0.8";//后期需要动态修改
			activity.setPercent(percent);//是指活动的参加率
			if(getHibernateTemplate().save(activity) != null){//查询结果不为空
				return true; //返回true
			}else{
				return false; //否则返回false
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;//如果异常，返回false
		}
	}
	
	/**	 
	 * 2015/12/18
	 * 用户信息查询,推荐礼品数量
	 * @see cn.org.njsoft.dao.GiftDao#addGift(Gift)
	 * @author JAQ	 
	 */
	@Override
	public int userSelectToGetGiftNum() {
		try{
			String queryString="from User u where u.userState = 1 ";//查询User表
			@SuppressWarnings("unchecked")
			List<User> list=super.getHibernateTemplate().find(queryString);//查询User表
			return list.size();		//返回user表的有多少条数据
		}catch(Exception e){
			e.printStackTrace();
			return 0;//如果异常，返回0
		}
	}
	
	/**
	 * 2015/12/15
	 * 获取所有的activity信息
	 * @see cn.org.njsoft.dao.GiftDao#addGift(Gift)
	 * @author JAQ
	 */
	@SuppressWarnings("unchecked")
	public List<Activity> getAllActivity() {
		try{			
			String queryString="from Activity a ";	
			List<Activity> list=super.getHibernateTemplate().find(queryString);
			if(list != null){
				return list;	
			}else{
				return null;
			}		
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 2015/12/21
	 * 保存活动参加率，动态修改活动参加率的值
	 * @see cn.org.njsoft.dao.ActivityDao#saveActPercent()
	 * @author JAQ
	 */
	@Override
	public boolean saveActPercent(Activity activity) {
		try{			
			super.getHibernateTemplate().update(activity);
			return true;		
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 
	 * 2015/12/14
	 * 签到活动信息查询
	 * @see cn.org.njsoft.dao.ActivityDao#activitySign(int)
	 * @author DB
	 */
	@Override
	public List<Activity> activitySign(int userId) {
		try{
			String queryString="select signAct from SignIn s where s.signUser.userId="+userId+"";
			@SuppressWarnings("unchecked")
			List<Activity> list=super.getHibernateTemplate().find(queryString);	//查询所有活动的信息
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
	 * 2015/12/14
	 * 签到活动信息分页查询
	 * @see cn.org.njsoft.dao.ActivityDao#activitySignByPage(final String , final int, final int)
	 * @author DB
	 * 
	 */
	@Override
	public List<Activity> activitySignByPage(final String hql, final int start, final int end) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Activity> list = getHibernateTemplate().executeFind(new HibernateCallback() {
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
}
