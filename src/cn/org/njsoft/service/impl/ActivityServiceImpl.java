package cn.org.njsoft.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import cn.org.njsoft.dao.ActivityDao;
import cn.org.njsoft.model.Activity;
import cn.org.njsoft.service.ActivityService;
/**
 * 2015/12/10
 * 登录的Service的实现类
 * @see cn.org.njsoft.service.impl#ActivityServiceImpl
 * @author JAQ
 *
 */
@Scope("singleton")
@Service("activityServiceImpl")
public class ActivityServiceImpl implements ActivityService{
	@Autowired
	private ActivityDao activityDao;
	/**	
	 * 2015/12/14
	 * 活动查询
	 * @see cn.org.njsoft.dao.ActivityService#activitySelect()
	 * @author JAQ
	 */
	@Override
	public List<Activity> activitySelect(){
		if (activityDao.activitySelect() != null){
			List<Activity> list = activityDao.activitySelect();
			return list;
		}
		return null;		
	}
	/**	
	 * 2015/12/14
	 * 未签到活动查询
	 * @see cn.org.njsoft.dao.ActivityService#activityUnsign()
	 * @author DB
	 */
	@Override
	public List<Activity> activityUnsign(int userId){
		if (activityDao.activityUnsign(userId) != null){
			List<Activity> list = activityDao.activityUnsign(userId);
			return list;
		}
		return null;		
	}
	/**
	 * 2015/12/14
	 * 活动分页查询
	 * @see cn.org.njsoft.dao.ActivityService#activitySelectByPage( int, int)
	 * @author JAQ
	 * 
	 */	
	@Override
	public List<Activity> activitySelectByPage( int start, int end) {
		String hql;
		hql="from Activity u ";
		return activityDao.activitySelectByPage(hql,start,end);
	}
	/**
	 * 2015/12/14
	 * 添加活动
	 * @see cn.org.njsoft.dao.ActivityService#addActivity(Activity )
	 * @author JAQ
	 */
	@Override
	public boolean addActivity(Activity activity) {
		if (activityDao.addActivity(activity)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 2015/12/17
	 * 用户信息查询,推荐礼品数量
	 * @see cn.org.njsoft.dao.ActivityService#userSelectToGetGiftNum()
	 * @author DB
	 */
	@Override
	public int userSelectToGetGiftNum() {
		if (activityDao.userSelectToGetGiftNum()!=0){
			return activityDao.userSelectToGetGiftNum();
		}else{
			return 0;
		}
	}
	/**
	 * 2015/12/16
	 * 活动详细查询
	 * @see cn.org.njsoft.dao.ActivityService#activityDetailSelect()
	 * @author JAQ
	 */
	@Override
	public Activity activityDetailSelect(int actid){
		if (activityDao.activitySelectById(actid) != null){
			Activity activity = activityDao.activitySelectById(actid);
			return activity;
		}
		return null;
	}
	
	/**
	 * 2015/12/16
	 * 获取所有的activity信息
	 * @see cn.org.njsoft.dao.ActivityService#getAllActivity()
	 * @author ?
	 */
	@Override
	public List<Activity> getAllActivity() {
		return activityDao.getAllActivity();
	}
	
	/**
	 * 2015/12/21
	 * 保存活动参加率，动态修改活动参加率的值
	 * @see cn.org.njsoft.dao.ActivityService#saveActPercent()
	 * @author JAQ
	 */
	@Override
	public boolean saveActPercent(Activity activity) {
		if (activityDao.saveActPercent(activity)){
			return activityDao.saveActPercent(activity);
		}
		return false;
	}
	/**
	 * 2015/12/14
	 * 未签到活动分页查询
	 * @see cn.org.njsoft.dao.ActivityService#activityUnsignByPage( int,int, int)
	 * @author DB
	 * 
	 */
	@Override
	public List<Activity> activityUnsignByPage(int start, int end, int userId) {
		String hql;
		hql="from Activity a where a not in (select signAct from SignIn s where s.signUser.userId="+userId+")";
		return activityDao.activityUnsignByPage(hql,start,end);
	}
	/**	
	 * 2015/12/14
	 * 签到活动查询
	 * @see cn.org.njsoft.dao.ActivityService#activitySign()
	 * @author DB
	 */
	@Override
	public List<Activity> activitySign(int userId) {
		if (activityDao.activitySign(userId) != null){
			List<Activity> list = activityDao.activitySign(userId);
			return list;
		}
		return null;
	}
	/**
	 * 2015/12/14
	 * 签到活动分页查询
	 * @see cn.org.njsoft.dao.ActivityService#activitySignByPage( int,int, int)
	 * @author DB
	 * 
	 */
	@Override
	public List<Activity> activitySignByPage(int start, int end, int userId) {
		String hql;
		hql="select signAct from SignIn s where s.signUser.userId="+userId+"";
		return activityDao.activitySignByPage(hql,start,end);
	}			
	/**
	 * 2015/12/14
	 * 签到却没验证分页查询
	 * @see cn.org.njsoft.dao.ActivityService#activitySignUnCheckedByPage( int,int, int)
	 * @author DB
	 * 
	 */
	@Override
	public List<Activity> activitySignUnCheckedByPage(int start, int end, int userId) {
		// TODO Auto-generated method stub
		String hql;
		hql="select signAct from SignIn s where s.signCheck=0 and s.signUser.userId="+userId+"";
		return activityDao.activitySignByPage(hql,start,end);
	}			
}
