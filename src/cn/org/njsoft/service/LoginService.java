package cn.org.njsoft.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import cn.org.njsoft.model.User;
/**
 * 2014/12/10
 * 登录的Service
 * @see cn.org.njsoft.service#LoginService
 * @author JAQ
 *
 */
@Scope("singleton")
@Service("loginService")
public interface LoginService {
	public boolean login(User user); //是否可登录
}
