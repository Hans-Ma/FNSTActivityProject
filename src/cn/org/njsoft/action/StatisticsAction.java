package cn.org.njsoft.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.org.njsoft.model.PageDivided;
import cn.org.njsoft.model.SignIn;
import cn.org.njsoft.service.StatisticService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 2015/12/21
 * 与signIn表相关的Action
 * @see cn.org.njsoft.action#UserAction
 * @author MY
 */

@Scope("request")
@Controller("statisticsAction")
public class StatisticsAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	//用于会员用户
	private List<SignIn> list;
	private String actid;
	private String ifgetGift;
	private String end;
	
	//用于非会员用户
	private List<SignIn> list2;
	private String actid2;
	private String end2;
	
	private Map<String,Object> sessionMap;
	
	// 创建全局分页对象
	private PageDivided pageDivided = new PageDivided();
	private PageDivided pageDivided2 = new PageDivided();
	
	@Autowired
	@Qualifier("statisticServiceImpl")
	private StatisticService statisticService;
	
	/**
	 * 2015/12/19
	 * @author MaYue
	 * @return String SUCCESS
	 * @throws Exception
	 * @see {@link StatisticsAction}.select()
	 */
	public String select() throws Exception {
		//判断end为空
		if ("".equals(end)||end==null) {
			//end为空查历史session中有没有set过end
			if(ActionContext.getContext().getSession().get("end")!=null && !"".equals(ActionContext.getContext().getSession().get("end"))){
				//如果set过end，把end取出来交给pageDivided
				pageDivided.setEnd((int) ActionContext.getContext().getSession().get("end"));
			} else {
				//如果没有set过end，end设置默认值为5
				pageDivided.setEnd(5);
			}
		//如果end不为为空	
		} else {
			sessionMap.put("end", Integer.parseInt(this.end));
			pageDivided.setEnd((int) ActionContext.getContext().getSession().get("end"));
		}
		//如果不查询所有签到的情况
		pageDivided.setTotal(statisticService.signInSelect(actid, ifgetGift, 2).size());// 签到信息总的条数
		//签到信息总的页数，用于可以分为多少页
		pageDivided.setTotalPage(pageDivided.getTotal() % pageDivided.getEnd() > 0 ? pageDivided.getTotal() / pageDivided.getEnd() + 1 : pageDivided.getTotal() / pageDivided.getEnd());
		//起始点
		pageDivided.setStart((pageDivided.getCurrentPage() - 1) * pageDivided.getEnd());
		list = statisticService.signInSelectByPageByActidAndByGift(actid,ifgetGift, pageDivided.getStart(), pageDivided.getEnd());
		return SUCCESS;
	}

	/**
	 * 2015/12/19
	 * @author MaYue
	 * @return String SUCCESS
	 * @throws Exception
	 * @see {@link StatisticsAction}.select2()
	 */
	public String select2() throws Exception {
		//判断end为空
		if ("".equals(end2)||end2==null) {
			//end为空查历史session中有没有set过end
			if(ActionContext.getContext().getSession().get("end2")!=null && !"".equals(ActionContext.getContext().getSession().get("end2"))){
				//如果set过end，把end取出来交给pageDivided
				pageDivided2.setEnd((int) ActionContext.getContext().getSession().get("end2"));
			} else {
				//如果没有set过end，end设置默认值为5
				pageDivided2.setEnd(5);
			}
		//如果end不为为空	
		} else {
			sessionMap.put("end2", Integer.parseInt(this.end2));
			pageDivided2.setEnd((int) ActionContext.getContext().getSession().get("end2"));
		}
		//如果不查询所有签到的情况
		pageDivided2.setTotal(statisticService.signInUnMemSelect(actid).size());// 签到信息总的条数
		//签到信息总的页数，用于可以分为多少页
		pageDivided2.setTotalPage(pageDivided2.getTotal() % pageDivided2.getEnd() > 0 ? pageDivided2.getTotal() / pageDivided2.getEnd() + 1 : pageDivided2.getTotal() / pageDivided2.getEnd());
		//起始点
		pageDivided2.setStart((pageDivided2.getCurrentPage() - 1) * pageDivided2.getEnd());
		list2 = statisticService.signInUnMemSelectByPageByActidAndByGift(actid, pageDivided2.getStart(), pageDivided2.getEnd());
		return SUCCESS;
	}
	/**
	 * get\set方法
	 * 2015/12/19
	 * @author MaYue
	 * Auto set methods
	 */
	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Map<String, Object> getSessionMap() {
		return sessionMap;
	}

	public void setSessionMap(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}

	public PageDivided getPageDivided() {
		return pageDivided;
	}

	public void setPageDivided(PageDivided pageDivided) {
		this.pageDivided = pageDivided;
	}

	public StatisticService getStatisticService() {
		return statisticService;
	}

	public void setStatisticService(StatisticService statisticService) {
		this.statisticService = statisticService;
	}

	public List<SignIn> getList() {
		return list;
	}

	public void setList(List<SignIn> list) {
		this.list = list;
	}

	public String getActid() {
		return actid;
	}

	public void setActid(String actid) {
		this.actid = actid;
	}

	public String getIfgetGift() {
		return ifgetGift;
	}

	public void setIfgetGift(String ifgetGift) {
		this.ifgetGift = ifgetGift;
	}

	public PageDivided getPageDivided2() {
		return pageDivided2;
	}

	public void setPageDivided2(PageDivided pageDivided2) {
		this.pageDivided2 = pageDivided2;
	}

	public List<SignIn> getList2() {
		return list2;
	}

	public void setList2(List<SignIn> list2) {
		this.list2 = list2;
	}

	public String getActid2() {
		return actid2;
	}

	public void setActid2(String actid2) {
		this.actid2 = actid2;
	}

	public String getEnd2() {
		return end2;
	}

	public void setEnd2(String end2) {
		this.end2 = end2;
	}
	
}
