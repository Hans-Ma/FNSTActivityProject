package cn.org.njsoft.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.org.njsoft.model.Activity;
/**
 * 2015/12/10
 * ActivityDao接口
 * @see cn.org.njsoft.dao#ActivityDao
 * @author JAQ
 *
 */
@Service
public interface ActivityDao {
	public boolean addActivity(Activity activity); //添加活动
	public List<Activity> activitySelectByPage(final String hql, final int start, final int end);//活动分页查询
	public List<Activity> activitySelect();//活动查询
	public Activity activitySelectById(int actId);//根据Id查询活动，活动详细信息查询
	public int userSelectToGetGiftNum();//推荐礼品数量
	public List<Activity> getAllActivity();//获取所有的activity信息
	public boolean saveActPercent(Activity activity); //保存活动参加率，动态修改活动参加率的值
	public List<Activity> activityUnsign(int userId);//未签到活动查询
	public List<Activity> activityUnsignByPage(String hql, int start, int end);//未签到活动分页查询
	public List<Activity> activitySign(int userId);//已签到活动查询
	public List<Activity> activitySignByPage(String hql, int start, int end);//签到活动分页查询
}
