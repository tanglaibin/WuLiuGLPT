package tj.ljjtlb.shiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import tj.ljjtlb.service.Userservice;
import tl.ljjtlb.domain.Module;
import tl.ljjtlb.domain.Role;
import tl.ljjtlb.domain.User;

public class AuthRealm extends AuthorizingRealm{
private Userservice userservice;
public void setUserservice(Userservice userservice) {
	this.userservice = userservice;
}
@Override
protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
	System.out.println("授权");
	User user = (User) pc.fromRealm(this.getName()).iterator().next();//根据realm的名字去找对应的realm
	
	Set<Role > roles = user.getRoles();//对象导航
	List<String> permissions = new ArrayList<String>();
	for(Role role :roles){
		//遍历每个角色 
		Set<Module> modules = role.getModels();//得到每个角色下的模块列表
		for(Module m :modules){
			permissions.add(m.getName());
		}
	}
	
	SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
	info.addStringPermissions(permissions);//添加用户的模块（权限）
	return info;
}
@Override
protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
	System.out.println("认证");
	
	//1.向下转型
	UsernamePasswordToken upToken  = (UsernamePasswordToken) token;
	
	//2.调用业务方法，实现根据用户名查询
	String hql = "from User where userName=?";
	List<User> list = userservice.find(hql, User.class, new String[]{upToken.getUsername()});
	if(list!=null && list.size()>0){
		User user = list.get(0);
		AuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
		return info;   //此处如果返回，就会立即进入到密码比较器
	}
	
	return null;//就会出现异常
}

}
