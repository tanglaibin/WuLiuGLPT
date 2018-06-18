package tj.ljjtlb.utils;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 电子邮件工具类
 * @author Administrator
 *
 */
public class MailUtil {
	
	public static void sendMail(String addr,String subject,String text) throws MessagingException{
		Properties props=new Properties();
		props.put("mail.smtp.host","smtp.163.com");
		props.put("mail.smtp.auth","true");
		Session session=Session.getInstance(props);
		//构造信息体 
		MimeMessage message =new MimeMessage(session);
		 //发件地址 
		Address address = new InternetAddress("tlbljj@163.com"); 
		message.setFrom(address);
		//收件地址 
		Address toAddress = new InternetAddress(addr); 
		message.setRecipient(MimeMessage.RecipientType.TO, toAddress);
		//主题 
		message.setSubject(subject);
		//正文 
		message.setText(text);
		message.saveChanges();
		Transport transport = session.getTransport("smtp"); 
		transport.connect("smtp.163.com", "tlbljj", "1234qwet"); //发送 
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		
	}
	
}
