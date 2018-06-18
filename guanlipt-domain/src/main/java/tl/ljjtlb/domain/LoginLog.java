package tl.ljjtlb.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @Description:	LoginLogService接口
 * @Author:			唐来彬	唐来彬
 * @Company:		http://www.baidu.com
 * @CreateDate:		2018-3-22 15:33:04
 */

public class LoginLog implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id;	  	
	private String loginName;			
	private String ipAddress;			
	private String loginTime;			

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getLoginName() {
		return this.loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}	
	
	public String getIpAddress() {
		return this.ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}	
	
	public String getLoginTime() {
		return this.loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}	
}
