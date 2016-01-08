package cn.org.njsoft.action;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.org.njsoft.model.Activity;
import cn.org.njsoft.model.SignIn;
import cn.org.njsoft.service.SignInService;

import com.opensymphony.xwork2.ActionSupport;

/** 
 * 2015/12/24
 * web.cr6868.com HTTP接口 发送短信
 * http://web.cr6868.com/asmx/smsservice.aspx?name=登录名&pwd=接口密码&mobile=手机号码&content=内容&sign=签名&stime=发送时间&type=pt&extno=自定义扩展码
 * @see cn.org.njsoft.action#ActivityAction
 * @author JAQ		
*/
@SuppressWarnings("serial")
@Scope("request")
@Controller("messageValidate")
public class MessageValidateAction extends ActionSupport {
	@Autowired
	@Qualifier("signInServiceImpl")
	private SignInService signInService;
	Activity activity = new Activity();     //活动类的实例化
	/**
	 * @param args
	 * @throws IOException
	 */
	public String mainSelect() throws Exception {
		List<SignIn> list = signInService.userSignInSelect(activity.getActId());
		String phoneString;
		//发送内容
		String content = "亲爱的会员，你的验证码@如非本人操作，请忽略此短信。"; 
		String sign="士富";
		
		// 创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer("http://web.cr6868.com/asmx/smsservice.aspx?");

		// 向StringBuffer追加用户名
		sb.append("name=15195940131");

		// 向StringBuffer追加密码（登陆网页版，在管理中心--基本资料--接口密码，是28位的）
		sb.append("&pwd=06B9B37A8CB0241723D5BDC3068B");

		// 向StringBuffer追加手机号码
		for(int i=0; i<list.size(); i++){
			phoneString = list.get(i).getSignUser().getUserPhone();
			sb.append("&mobile="+URLEncoder.encode(phoneString,"UTF-8"));
			
		}

		// 向StringBuffer追加消息内容转URL标准码
		sb.append("&content="+URLEncoder.encode(content,"UTF-8"));
		
		//追加发送时间，可为空，为空为及时发送
		sb.append("&stime=");
		
		//加签名
		sb.append("&sign="+URLEncoder.encode(sign,"UTF-8"));
		
		//type为固定值pt  extno为扩展码，必须为数字 可为空
		sb.append("&type=pt&extno=");
		// 创建url对象
		//String temp = new String(sb.toString().getBytes("GBK"),"UTF-8");
		System.out.println("sb:"+sb.toString());
		URL url = new URL(sb.toString());

		// 打开url连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// 设置url请求方式 ‘get’ 或者 ‘post’
		connection.setRequestMethod("POST");

		// 发送
		InputStream is =url.openStream();

		//转换返回值
		String returnStr = MessageValidateAction.convertStreamToString(is);
		
		// 返回结果为‘0，20140009090990,1，提交成功’ 发送成功   具体见说明文档
		System.out.println(returnStr);
		// 返回发送结果
		return "success";

		

	}
	/**
	 * 转换返回值类型为UTF-8格式.
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {    
        StringBuilder sb1 = new StringBuilder();    
        byte[] bytes = new byte[4096];  
        int size = 0;  
        
        try {    
        	while ((size = is.read(bytes)) > 0) {  
                String str = new String(bytes, 0, size, "UTF-8");  
                sb1.append(str);  
            }  
        } catch (IOException e) {    
            e.printStackTrace();    
        } finally {    
            try {    
                is.close();    
            } catch (IOException e) {    
               e.printStackTrace();    
            }    
        }    
        return sb1.toString();    
    }
	public Activity getActivity() {//get方法
		return activity;
	}
	public void setActivity(Activity activity) {//set方法
		this.activity = activity;
	}

}
