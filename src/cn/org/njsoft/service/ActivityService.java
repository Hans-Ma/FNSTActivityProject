package cn.org.njsoft.service;

import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import cn.org.njsoft.model.Activity;
/**
 * 2015/12/10
 * 活动管理的Service
 * @see cn.org.njsoft.service#ActivityService
 * @author JAQ
 *
 */
@Scope("singleton")
@Service("activityService")
public interface ActivityService {	
	public List<Activity> activitySelect(); //查询活动
	public boolean addActivity(Activity activity); //添加活动
	public int userSelectToGetGiftNum();//给活动推荐礼品数量
	public List<Activity> activitySelectByPage(int start, int end);//分页
	public Activity activityDetailSelect(int actid);//活动详细信息查询
	public List<Activity> getAllActivity();//获取所有的Activity信息	
	public boolean saveActPercent(Activity activity); //保存活动参加率，动态修改活动参加率的值
	public List<Activity> activityUnsignByPage(int start, int end, int userId);//未签到过的活动的分页
	public List<Activity> activityUnsign(int userId);//查询未签到活动
	public List<Activity> activitySign(int userId);//查询签到活动
	public List<Activity> activitySignByPage(int start, int end, int userId);//签到过的活动的分页
	List<Activity> activitySignUnCheckedByPage(int start, int end, int userId);//签到却没验证分页查询
}
