import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class javamailtest {

	@Test
	public void run(){
		ApplicationContext ac=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		SimpleMailMessage mes = (SimpleMailMessage) ac.getBean("mailMessage");
		JavaMailSender sendm = (JavaMailSender) ac.getBean("mailSender");
		mes.setSubject("入职邮件");
		mes.setText("你好");
		mes.setTo("136839532@qq.com");
		
		sendm.send(mes);
	}
}
