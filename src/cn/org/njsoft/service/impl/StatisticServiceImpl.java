package cn.org.njsoft.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import cn.org.njsoft.dao.SignInDao;
import cn.org.njsoft.model.SignIn;
import cn.org.njsoft.service.StatisticService;
/**
 * 统计用户
 * 15/12/19
 * @author MaYue
 */
@Scope("singleton")
@Service("statisticServiceImpl")
public class StatisticServiceImpl implements StatisticService {
	
	@Autowired
	private SignInDao signInDao;
	/**
	 * 2015/12/21
	 * 签到分页查询
	 * @param actid ifgetGift
	 * @see org.njsoft.service#StatisticService
	 * @author MaYue
	 * @return List<SignIn>
	 */
	@Override
	public List<SignIn> signInSelectByPageByActidAndByGift(String actId, String ifgetGift, int start, int end) {
		String hql;
		//ifgetGift为空的情况下，默认查询所有typeId为2的和某活动相关的的所有签到
		if ("".equals(ifgetGift) || ifgetGift==null) {
			hql ="from SignIn a where a.signUser.userType.typeId=2 and a.signAct.actId=" + actId;
		//ifgetGift不为空的情况，查询所有未领奖用户的
		} else {
			hql ="from SignIn a where a.signUser.userType.typeId=2 and a.signAct.actId=" + actId + " and giftState=" +ifgetGift;
		}
		return signInDao.selectSigninByPage(hql, start, end);
	}
	
	/**
	 * 2015/12/22
	 * 非会员签到分页查询
	 * @param actid ifgetGift
	 * @see org.njsoft.service#StatisticService
	 * @author MaYue
	 * @return List<SignIn>
	 */
	@Override
	public List<SignIn> signInUnMemSelectByPageByActidAndByGift(String actId, int start, int end) {
		//ifgetGift为空的情况下，默认查询所有typeId为2的和某活动相关的的所有签到
		String hql ="from SignIn a where a.signUser.userType.typeId=3 and a.signAct.actId=" + actId;
		return signInDao.selectSigninByPage(hql, start, end);
	}

	/**
	 * 2015/12/21
	 * 会员签到总数查询
	 * @see cn.org.njsoft.service#UserService
	 * @author MaYue
	 * @return List<SignIn>
	 */
	@Override
	public List<SignIn> signInSelect(String actId ,String ifgetGift ,int usertypeid) {
		try {
			if("".equals(ifgetGift)||ifgetGift==null){
				List<SignIn> list = signInDao.selectNumSignin(Integer.parseInt(actId), 0);
				return list;
			} else {
				List<SignIn> list = signInDao.selectNumSignin(Integer.parseInt(actId), Integer.parseInt(ifgetGift));
				return list;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 2015/12/22
	 * 非会员签到总数查询
	 * @see cn.org.njsoft.service#UserService
	 * @author MaYue
	 * @return List<SignIn>
	 */
	@Override
	public List<SignIn> signInUnMemSelect(String actId) {
		try {
			List<SignIn> list = signInDao.selectUnnumSignin(Integer.parseInt(actId));
			return list;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}
}
