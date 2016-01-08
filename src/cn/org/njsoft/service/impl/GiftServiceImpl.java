package cn.org.njsoft.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.org.njsoft.dao.GiftDao;
import cn.org.njsoft.model.Activity;
import cn.org.njsoft.model.Gift;
import cn.org.njsoft.model.SignGift;
import cn.org.njsoft.model.SignIn;
import cn.org.njsoft.service.GiftService;
/**
 * 2015/12/10
 * 礼品管理的Service的实现类
 * @see cn.org.njsoft.service.impl#GiftServiceImpl
 * @author JAQ
 *
 */
@Scope("singleton")
@Service("giftServiceImpl")
public class GiftServiceImpl implements GiftService{
	@Autowired
	private GiftDao giftDao;

	/**
	 * 2015/12/11
	 * 添加礼品
	 * @see cn.org.njsoft.dao.GiftService#addGift(Gift)
	 * @author JAQ
	 * 
	 */
	@Override
	public boolean addGift(Gift gift) {		
		if (giftDao.addGift(gift)){
			return true;
		}else{
			return false;
		}
	}	
	/**
	 * 2015/12/11
	 * 查询活动表，为添加礼品做准备
	 * @see cn.org.njsoft.dao.GiftService#activitySelect(Gift)
	 * @author JAQ
	 */	
	@Override
	public List<Activity> activitySelect(){
		if (giftDao.activitySelect() != null){
			List<Activity> list = giftDao.activitySelect();
			return list;
		}
		return null;
		
	}
	/**
	 * 2015/12/11
	 * 添加礼品
	 * @see cn.org.njsoft.dao.GiftService#giftSelect()
	 * @author JAQ
	 */
	@Override
	public List<Gift> giftSelect() {
		try {
			List<Gift> list = giftDao.giftSelect();
			return list;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 2015/12/11
	 * 礼品分页查询
	 * @see cn.org.njsoft.dao.GiftService#giftSelectByPageAndById(String , int , int )
	 * @author MY
	 */
	@Override
	public List<Gift> giftSelectByPageAndById(String giftId, int start, int end) {
		String hql;
		if ("".equals(giftId) || giftId == null) {
			hql ="from Gift";
		}else {
			hql="from Gift g where g.giftAct.actId="+ giftId;
		}
		return giftDao.giftSelectByPage(hql,start,end);
	}
	/**
	 * 2015/12/21
	 * 通过活动id查询活动所有礼品
	 * @see cn.org.njsoft.dao.GiftService# getAllGiftByActid( actId)
	 * @author YXF
	 */
	@Override
	public List<Gift> getAllGiftByActid(int actId) {
		// TODO Auto-generated method stub
		return giftDao.getAllGiftByActid(actId);
	}
	
	/**	 
	 * 2015/12/21
	 * 根据活动ID查询所有礼品
	 * @see cn.org.njsoft.dao.GiftService#giftSelectByActID(int)
	 * @author JAQ	 
	 */
	@Override
	public int giftSelectByActID(int actId) {
		if (giftDao.giftSelectByActID(actId) != 0){
			return giftDao.giftSelectByActID(actId);
		}else{
			return 0;
		}
	}
	/**	 
	 * 2015/12/21
	 * 更新礼品表礼品剩余数量,并返回剩余量
	 * @see cn.org.njsoft.dao.GiftService#updateGiftRest(int)
	 * @author DB	 
	 */
	@Override
	public int updateGiftRest(int giftId) {
		// TODO Auto-generated method stub
		return giftDao.updateGiftRest(giftId);
	}
	/**	 
	 * 2015/12/21
	 * 根据活动ID查询并返回礼品表所有礼品种类
	 * @see cn.org.njsoft.dao.GiftService#getGiftTypes(int)
	 * @author DB	 
	 */
	@Override
	public List<Gift> getGiftTypes(int signActId) {
		// TODO Auto-generated method stub
		return giftDao.getGiftTypes(signActId);
	}
	/**	 
	 * 2015/12/21
	 * 根据活动ID和礼品ID查询所有礼品分配
	 * @see cn.org.njsoft.dao.GiftService#getSignGiftByActidAndGiftId(giftId,actid)
	 * @author YXF	 
	 */
	@Override
	public List<SignGift> getSignGiftByActidAndGiftId(int giftId, int actid) {
		return giftDao. getSignGiftByActidAndGiftId(giftId,actid);
	}
	
	/**	 
	 * 2015/12/21
	 * 根据分页查询，根据活动ID和礼品ID查询所有礼品分配
	 * @see cn.org.njsoft.dao.GiftService#getSignGiftByActidAndGiftId(giftId,actid, start,end)
	 * @author YXF	 
	 */
	@Override
	public List<SignGift> getSignGiftByActidAndGiftId(int giftId, int actid,
			int start, int end) {
		// TODO Auto-generated method stub
		return giftDao.getSignGiftByActidAndGiftId(giftId,actid,start,end);
	}
	
	/**	 
	 * 2015/12/22
	 * 分页查询，根据活动ID查询所有礼品分配查询已签到但未收到礼品的会员信息
	 * @see cn.org.njsoft.dao.GiftService#getNumByPageAndByActId(actid, start,end)
	 * @author YXF	 
	 */
	@Override
	public List<SignIn> getNumByPageAndByActId(int actid, int start, int end) {
		// TODO Auto-generated method stub
		return giftDao.getNumByPageAndByActId(actid,start,end);
	}
	
	/**	 
	 * 2015/12/22
	 * 根据活动ID查询所有礼品分配查询已签到但未收到礼品的会员信息
	 * @see cn.org.njsoft.dao.GiftService#getNumByActId(actid)
	 * @author YXF	 
	 */
	@Override
	public List<SignIn> getNumByActId(int actid) {
		// TODO Auto-generated method stub
		return giftDao.getNumByActId(actid);
	}
}
