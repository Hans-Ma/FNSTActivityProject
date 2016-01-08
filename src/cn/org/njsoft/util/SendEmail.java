package cn.org.njsoft.util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class SendEmail {

	public static boolean sendMail(String toMail, String mailContent)
			throws Exception {
		boolean flag = true;
		Properties props = new Properties(); // 可以加载一个配置文件
		// 使用smtp：简单邮件传输协议
		props.put("mail.smtp.host", "smtp.qq.com"); // 存储发送邮件服务器的信息
		props.put("mail.smtp.auth", "true");// 同时通过验证

		Session session = Session.getInstance(props);// 根据属性新建一个邮件会话
		MimeMessage message = new MimeMessage(session);// 由邮件会话新建一个消息对象
		message.setFrom("1433865813@qq.com");// 设置发件人的地址
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(
				toMail));// 设置收件人,并设置其接收类型为TO
		message.setSubject("FNST活动验证信息");// 设置标题
		// 设置信件内容
		message.setContent("用户您好!您的验证码是:" + mailContent + "",
				"text/html;charset=gbk"); // 发送HTML邮件，内容样式比较丰富
		message.setSentDate(new Date());// 设置发信时间
		message.saveChanges();// 存储邮件信息

		// 发送邮件
		Transport transport = session.getTransport();
		// 链接用户名和密码
		transport.connect("1433865813@qq.com", "cfcctunidlydjjaa");
		try {
			transport.sendMessage(message, message.getAllRecipients());// 发送邮件,其中第二个参数是所有已设好的收件人地址
		} catch (SendFailedException se) {
			se.printStackTrace();
			flag = false;
		} catch (MessagingException me) {
			me.printStackTrace();
			flag = false;
		}
		transport.close();
		return flag;
	}

}