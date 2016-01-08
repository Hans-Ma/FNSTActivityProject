package cn.org.njsoft.service;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.org.njsoft.model.Activity;
import cn.org.njsoft.model.Gift;
import cn.org.njsoft.model.SignGift;
import cn.org.njsoft.model.SignIn;
/**
 * 2015/12/10
 * 礼品管理的Service
 * @see cn.org.njsoft.service#GiftService
 * @author JAQ
 *
 */
@Scope("singleton")
@Service("giftService")
public interface GiftService {
	public boolean addGift(Gift gift); //添加礼品
	public List<Activity> activitySelect();//查询活动表，为添加礼品做准备
	public List<Gift> giftSelect();//查询所有礼品
	public List<Gift> giftSelectByPageAndById(String giftId, int start, int end);
	public List<Gift> getAllGiftByActid(int actId);// 通过活动id查询活动所有礼品
	public int giftSelectByActID(int actId);//根据活动ID查询所有礼品
	public int updateGiftRest(int giftId);// 更新礼品表礼品剩余数量,并返回剩余量
	public List<Gift> getGiftTypes(int signActId);//根据活动ID查询并返回礼品表所有礼品种类
	public List<SignGift> getSignGiftByActidAndGiftId(int giftId, int actid);
	//根据活动ID和礼品ID查询所有礼品分配
	public List<SignGift> getSignGiftByActidAndGiftId(int giftId, int actid,
			int start, int end);//分页查询，根据活动ID和礼品ID查询所有礼品分配
	public List<SignIn> getNumByPageAndByActId(int actid, int start, int end);
	//分页查询，根据活动ID查询所有礼品分配查询已签到但未收到礼品的会员信息
	public List<SignIn> getNumByActId(int actid);
	//根据活动ID查询所有礼品分配查询已签到但未收到礼品的会员信息
}
