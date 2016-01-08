package cn.org.njsoft.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/**
 * 2015/12/8
 * 过滤器：处理中文乱码
 * @see cn.org.njsoft.util#EncodingFilter
 * @author JAQ
 *
 */
public class EncodingFilter implements Filter{
	/**
	 * 2015/12/10
	 * 初始化
	 * @see cn.org.njsoft.util#EncodingFilter
	 * @author JAQ
	 *
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 2015/12/10
	 * 设置编码
	 * @see cn.org.njsoft.util#EncodingFilter
	 * @author JAQ
	 *
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");  //设置UTF-8编码
		resp.setCharacterEncoding("UTF-8");
		chain.doFilter(req, resp);//设置UTF-8编码
	}
	/**
	 * 2015/12/10
	 * 销毁
	 * @see cn.org.njsoft.util#EncodingFilter
	 * @author JAQ
	 *
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
