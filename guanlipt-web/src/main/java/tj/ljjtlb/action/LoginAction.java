package tj.ljjtlb.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;

import antlr.Utils;
import tj.ljjtlb.service.LoginLogService;
import tj.ljjtlb.utils.SysConstant;
import tj.ljjtlb.utils.UtilFuns;
import tl.ljjtlb.domain.LoginLog;
import tl.ljjtlb.domain.User;

public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;

    private LoginLogService loginLogService;
	public void setLoginLogService(LoginLogService loginLogService) {
	this.loginLogService = loginLogService;
}

	//SSH传统登录方式
	public String login() throws Exception {
		
//		if(true){
//			String msg = "登录错误，请重新填写用户名密码!";
//			this.addActionError(msg);
//			throw new Exception(msg);
//		}
//		User user = new User(username, password);
//		User login = userService.login(user);
//		if (login != null) {
//			ActionContext.getContext().getValueStack().push(user);
//			session.put(SysConstant.CURRENT_USER_INFO, login);	//记录session
//			return SUCCESS;
//		}
//		return "login";
		
		if (UtilFuns.isEmpty(username)) {
			return  "login";
		}
		
		try {
			//1.得到Subject
			Subject subject = SecurityUtils.getSubject();
			//2.调用登录方法
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.login(token);//当这一代码执行时，就会自动跳入到AuthRealm中认证方法
			//3.登录成功时，就从Shiro中取出用户的登录信息
			User user = (User) subject.getPrincipal();
			
			//登陆成功后 添加日志
			LoginLog log=new LoginLog();
			log.setLoginName(user.getUserName());
			HttpServletRequest request2 = ServletActionContext.getRequest();
			log.setIpAddress(request2.getRemoteAddr());
			
			Date date = new Date();
			/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			log.setLoginTime(sdf.format(date));*/
			
			log.setLoginTime(date.toLocaleString());
			
			loginLogService.saveOrUpdate(log);
			
			
			//4.将用户放入session域中
			session.put(SysConstant.CURRENT_USER_INFO, user);
			
		} catch (Exception e) {
			e.printStackTrace();
			request.put("errorInfo", "对不起，用户名或密码错误！");
			return "login";
		}
		return "suess";
	}
	
	
	//退出
	public String logout(){
		session.remove(SysConstant.CURRENT_USER_INFO);		//删除session
		return "logout";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

