package tj.ljjtlb.action;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import tj.ljjtlb.service.Moduleservice;
import tj.ljjtlb.service.Roleservice;
import tj.ljjtlb.utils.Page;
import tl.ljjtlb.domain.Module;
import tl.ljjtlb.domain.Role;

public class Roleaction extends BaseAction implements ModelDriven<Role>{
	
	private Role role=new Role();
	public Role getModel() {
		return role;
	}
    //注入service
	private Roleservice roleservice;
	public void setRoleservice(Roleservice roleservice) {
		this.roleservice = roleservice;
	}
	
	private Moduleservice moduleservice;
	public void setModuleservice(Moduleservice moduleservice) {
		this.moduleservice = moduleservice;
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
		page = roleservice.findPage("from Role", page, Role.class, null);
		page.setUrl("roleAction_list");
		ActionContext.getContext().getValueStack().push(page);
		return "list";
	}
	
	public String toview(){
//根据封装的id  查找dept对象  放入值栈取出展示
		Role dept2 = roleservice.get(Role.class, role.getId());
		ActionContext.getContext().getValueStack().push(dept2);
		return "toview";
	}
	
	public String tocreate() {
		//查找数据库   查找用户所属部门信息
		return "tocreate";
	}
	
	
	public String insert() {
		//查找数据库  上级部门信息
		roleservice.saveOrUpdate(role);
		//跳转链接
		return "alllist";
	}
	
	public String toupdate() {
		Role role2 = roleservice.get(Role.class, role.getId());
		ActionContext.getContext().getValueStack().push(role2);
		
		return "upui";
	}
	
	public String update() {
		Role role2 = roleservice.get(Role.class, role.getId());
		role2.setName(role.getName());
		role2.setRemark(role.getRemark());
		roleservice.saveOrUpdate(role2);
		return "alllist";
	}
	
	public String delete() {
		String ids[] =role.getId().split(", ");
		roleservice.delete(Role.class, ids);
		return "alllist";
	}
	
	//加载角色信息  存入值栈
	public String tomodule() {
		Role role2 = roleservice.get(Role.class, role.getId());
		ActionContext.getContext().getValueStack().push(role2);
		
		return "model";
	}
	
	//加载权限信息   response写回页面ajax   { "id":"221", "pId":"22", "name":"随意勾选 2-2-1", "checked":"true"},
	public String roleModuleJsonStr() throws IOException {
	//获取角色的模块   获取所有模块  判断 如果包含角色的模块  选中勾选
		Role role2 = roleservice.get(Role.class, role.getId());
		Set<Module> models = role2.getModels();
		
		List<Module> list = moduleservice.find("from Module", Module.class, null);
		int size=list.size();
		
		StringBuilder sb=new StringBuilder();
		sb.append("[");
		for (Module m : list) {
			size--;
			sb.append("{\"id\":\"").append(m.getId());
			sb.append("\",\"pId\":\"").append(m.getParentId());
			sb.append("\",\"name\":\"").append(m.getName());
			sb.append("\",\"checked\":\"");
			if (models.contains(m)) {
				sb.append("true");
			}else{
				sb.append("false");
			}
			sb.append("\"}");
			
			if (size>0) {
				sb.append(",");
			}
		}
		sb.append("]");
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		
		response.getWriter().write(sb.toString());
		
		return NONE;
	}
	
	
	public String module() {
	//保存角色的权限设置   根据id查找role  根据页面发送的string  添加模块
		Role role2 = roleservice.get(Role.class, role.getId());
		
		Set<Module> models = new HashSet<>();
		
		String[] ids = moduleIds.split(",");
		if (ids!=null && ids.length>0) {
			for (String id : ids) {
				models.add(moduleservice.get(Module.class, id));
			}
		}
		role2.setModels(models);
		roleservice.saveOrUpdate(role2);
		return "alllist";
	}
	
	
	private String moduleIds;
	public void setModuleIds(String moduleIds) {
		this.moduleIds = moduleIds;
	}
	
	
	
}
