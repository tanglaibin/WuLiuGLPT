package tj.ljjtlb.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import tj.ljjtlb.service.Deptservice;
import tj.ljjtlb.service.Roleservice;
import tj.ljjtlb.service.Userservice;
import tj.ljjtlb.utils.Page;
import tl.ljjtlb.domain.Dept;
import tl.ljjtlb.domain.Role;
import tl.ljjtlb.domain.User;

public class Useraction extends BaseAction implements ModelDriven<User>{
	
	private User user=new User();
	public User getModel() {
		return user;
	}
	
	
    //注入service
	private Userservice userservice;
	public void setUserservice(Userservice userservice) {
		this.userservice = userservice;
	}
	
	private Deptservice deptservice;
	public void setDeptservice(Deptservice deptservice) {
		this.deptservice = deptservice;
	}
	
	private Roleservice roleservice;
	public void setRoleservice(Roleservice roleservice) {
		this.roleservice = roleservice;
	}
	
	private String[] roleIds;
	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	
	//注入page  new出分页数据空间
	private Page page=new Page();
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}

	public String list(){
		page = userservice.findPage("from User where state=1", page, User.class, null);
		page.setUrl("userAction_list");
		ActionContext.getContext().getValueStack().push(page);
		return "list";
	}
	
	
	public String toview(){
//根据封装的id  查找dept对象  放入值栈取出展示
		User dept2 = userservice.get(User.class, user.getId());
		ActionContext.getContext().getValueStack().push(dept2);
		return "toview";
	}
	
	
	public String tocreate() {
		//查找数据库   查找用户所属部门信息
		List<Dept> list = deptservice.find("from Dept where state=1", Dept.class, null);
		ActionContext.getContext().put("deptList", list);
		List<User> list2 = userservice.find("from User where state=1", User.class, null);
		ActionContext.getContext().put("userList", list2);
		return "tocreate";
	}
	
	
	public String insert() {
		//查找数据库  上级部门信息
		userservice.saveOrUpdate(user);
		//跳转链接
		return "alllist";
	}
	
	
	public String toupdate() {
		User user2 = userservice.get(User.class, user.getId());
		ActionContext.getContext().getValueStack().push(user2);
		
		List<Dept> list = deptservice.find("from Dept where state=1", Dept.class, null);
		ActionContext.getContext().put("deptList", list);
		
		return "upui";
	}
	
	public String update() {
		User user2 = userservice.get(User.class, user.getId());
		
		user2.setDept(user.getDept());
		user2.setUserName(user.getUserName());
		user2.setState(user.getState());
		userservice.saveOrUpdate(user2);
		return "alllist";
	}
	
	
	public String delete() {
		String ids[] =user.getId().split(", ");
		System.out.println(ids);
		userservice.delete(User.class, ids);
		return "alllist";
	}
	
	
	public String torole() {
//根据用户id  取得用户  放入栈顶   取得角色列表信息  放入值栈   取得用户角色信息列表  新建stringbuff  创建角色字符串  存入值栈  
        User user2 = userservice.get(User.class, user.getId());
        ActionContext.getContext().getValueStack().push(user2);
        
        List<Role> roles = roleservice.find("from Role", Role.class, null);
        ActionContext.getContext().put("rolelist",roles);
        
        Set<Role> roles2 = user2.getRoles();
        StringBuilder sb=new StringBuilder();
        
        for (Role role : roles2) {
			sb.append(role.getName()).append(",");
		}
        ActionContext.getContext().put("userrole",sb.toString());
        
        return "torole";
	}
	
	
	public String role() {
		//根据用户id  取得用户  新建ids集合  接收前台页面ids数据   遍历保存到角色set集合   绑定到user  存储到数据库
		        User user2 = userservice.get(User.class, user.getId());
		        //System.out.println(user2);
		        Set<Role> roles=new HashSet<>();
		       for (String id : roleIds) {
				Role role = roleservice.get(Role.class, id);
				//System.out.println(role);
				roles.add(role);
			}
		        user2.setRoles(roles);
		        userservice.saveOrUpdate(user2);
		        return "alllist";
			}
	
	
	
	
	
	
}
