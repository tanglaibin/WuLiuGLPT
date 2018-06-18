package tj.ljjtlb.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import tj.ljjtlb.utils.Encrypt;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher{

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		// TODO Auto-generated method stub
		UsernamePasswordToken upToken= (UsernamePasswordToken) token;
		Object pwd= Encrypt.md5(new String(upToken.getPassword()), upToken.getUsername());
		Object dbpwd=info.getCredentials();
		return this.equals(pwd, dbpwd);
	}

}
