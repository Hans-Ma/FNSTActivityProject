package cn.org.njsoft.action;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import cn.org.njsoft.model.PageDivided;
import cn.org.njsoft.model.SignIn;
import cn.org.njsoft.model.User;
import cn.org.njsoft.service.SignInService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 2015/12/10
 * 签到的Action
 * @see cn.org.njsoft.action#SignInAction
 * @author DB
 *
 */
@SuppressWarnings("serial")
@Scope("request")
@Controller("signInAction")
public class SignInAction extends ActionSupport {
	
	private int signActId;//由前台传入活动的ID
	private int check;//传入前台控制隐藏域显示
	private int userId;//由前台传入用户ID，为空是用户签到，不为空则是管理员手动签到
	private String end;  //分页的，一页多少行
	private PageDivided pageDivided = new PageDivided();
	private List<SignIn> list;  //活动的分页查询 的list
	SignIn signIn = new SignIn();
	
	@Autowired
	@Qualifier("signInServiceImpl")
	private SignInService signInService;

	//获取session里的User
	User user=(User) ActionContext.getContext().getSession().get("user");//获取用户登录信息	
	/**
	 * 2015/12/10
	 * 签到检查
	 * @see cn.org.njsoft.action#SignInAction
	 * @author DB
	 *
	 */
	public String check() throws Exception {
//		if (userId ==0) {// 等于0，表明由前台传入用户Id为空,用户签到
//			userId = user.getUserId();//取出session里用户Id,实际没用到
//		}
		if(user.getUserType().getTypeId()==1){//给管理员手动签到的方法
			if (signInService.check(signActId) == 0) {
				check = 0;// 签到还未开始！
			} else if (signInService.check(signActId) == 1) {

				// 数据库中signState的0代表未签到，1正常签到，2签到迟到。在signIn表中插入数据
				int signState = 2;

				if (signInService.isSignIn(signActId, userId)) {
					check = 4;// 不要重复 签到
				} else {
					signInService.addSignInForAdmin(signActId, signState, userId);// 添加签到记录
					check = 1;// 活动已开始，您迟到了！在signIn表中插入数据
				}

			} else if (signInService.check(signActId) == 2) {
				check = 2;// 活动已结束，不能签到！
			} else {

				int signState = 1;
				if (signInService.isSignIn(signActId, userId)) {
					check = 4;// 不要重复 签到
				} else {
					signInService.addSignInForAdmin(signActId, signState, userId);// 添加签到记录
					check = 3;// 签到成功，在signIn表中插入数据
				}
				

			}
			return "forAdmin";
		}else{//给普通用户签到的方法
			if (signInService.check(signActId) == 0) {
				check = 0;// 签到还未开始！
			} else if (signInService.check(signActId) == 1) {

				// 数据库中signState的0代表未签到，1正常签到，2签到迟到。在signIn表中插入数据
				int signState = 2;

				if (signInService.isSignIn(signActId, userId)) {
					check = 4;// 不要重复 签到
				} else {
					signInService.addSignIn(signActId, signState, userId);// 添加签到记录
					check = 1;// 活动已开始，您迟到了！在signIn表中插入数据
				}

			} else if (signInService.check(signActId) == 2) {
				check = 2;// 活动已结束，不能签到！
			} else {

				int signState = 1;
				if (signInService.isSignIn(signActId, userId)) {
					check = 4;// 不要重复 签到
				} else {
					signInService.addSignIn(signActId, signState, userId);// 添加签到记录
					check = 3;// 签到成功，在signIn表中插入数据
				}
				

			}
			return "forUser";
		}
		
	}
	/**
	 * 2015/12/21
	 * 分页查询活动
	 * @see cn.org.njsoft.action#SignInAction
	 * @author JAQ
	 */
	public String userSignInSelect() throws Exception {
		if ("".equals(end)||end==null) {	//判断end为空
			if(end!=null && !"".equals(end)){			
				pageDivided.setEnd(Integer.parseInt(end));//如果set过end，把end取出来交给pageDivided
			} else {
				pageDivided.setEnd(5);	//如果没有set过end，end设置默认值为5
			}
		} else {
			pageDivided.setEnd(Integer.parseInt(end));
		}
		//如果不查询所有用户的情况
		pageDivided.setTotal(signInService.userSignInSelect(signIn.getSignAct().getActId()).size());// 用户信息总的条数
		//用户信息总的页数，用于可以分为多少页
		pageDivided.setTotalPage(pageDivided.getTotal() % pageDivided.getEnd() > 0 ? pageDivided.getTotal() / pageDivided.getEnd() + 1 : pageDivided.getTotal() / pageDivided.getEnd());
		//起始点
		pageDivided.setStart((pageDivided.getCurrentPage() - 1) * pageDivided.getEnd());
		list = signInService.userSignInSelectByPage( pageDivided.getStart(),signIn.getSignAct().getActId(), pageDivided.getEnd());	
		System.out.println(list + "   uu");
		System.out.println(signInService.userSignInSelect(signIn.getSignAct().getActId()).size());
		return "success";  //正确，返回success
	}
	public int getSignActId() {    //get方法
		return signActId;
	}
	public void setSignActId(int signActId) {  //set方法
		this.signActId = signActId;
	}
	public SignInService getSignInService() {//get方法
		return signInService;
	}
	public void setSignInService(SignInService signInService) {//set方法
		this.signInService = signInService;
	}
	public int getCheck() {//get方法
		return check;
	}
	public void setCheck(int check) {//set方法
		this.check = check;
	}

	public int getUserId() {//get方法
		return userId;
	}

	public void setUserId(int userId) {//set方法
		this.userId = userId;
	}
	public String getEnd() {//get方法
		return end;
	}
	public void setEnd(String end) {//set方法
		this.end = end;
	}
	public PageDivided getPageDivided() {//get方法
		return pageDivided;
	}
	public void setPageDivided(PageDivided pageDivided) {//set方法
		this.pageDivided = pageDivided;
	}
	public List<SignIn> getList() {//get方法
		return list;
	}
	public void setList(List<SignIn> list) {//set方法
		this.list = list;
	}
	public SignIn getSignIn() {//get方法
		return signIn;
	}
	public void setSignIn(SignIn signIn) {//set方法
		this.signIn = signIn;
	}
	
}
