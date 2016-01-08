package cn.org.njsoft.service;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.org.njsoft.model.SignIn;

@Scope("singleton")
@Service("statisticService")
public interface StatisticService {
	public List<SignIn> signInSelectByPageByActidAndByGift(String actid,String ifgetGift, int start, int end);//用户信息分页查询
	public List<SignIn> signInSelect(String actId ,String ifgetGift ,int usertypeid);//查询所有会员的签到信息
	public List<SignIn> signInUnMemSelect(String actId);//查询所有非会员的签到信息
	public List<SignIn> signInUnMemSelectByPageByActidAndByGift(String actId, int start, int end);//分页查询非会员信息
}
