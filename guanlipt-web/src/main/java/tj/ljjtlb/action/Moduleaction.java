package tj.ljjtlb.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import tj.ljjtlb.service.Moduleservice;
import tj.ljjtlb.utils.Page;
import tl.ljjtlb.domain.Module;

public class Moduleaction extends BaseAction implements ModelDriven<Module>{
	
	private Module module=new Module();
	public Module getModel() {
		return module;
	}
    //注入service

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
		page = moduleservice.findPage("from Module", page, Module.class, null);
		page.setUrl("moduleAction_list");
		ActionContext.getContext().getValueStack().push(page);
		return "list";
	}
	
	
	public String toview(){
//根据封装的id  查找dept对象  放入值栈取出展示
		Module dept2 = moduleservice.get(Module.class, module.getId());
		ActionContext.getContext().getValueStack().push(dept2);
		return "toview";
	}
	
	
	public String tocreate() {
		//查找数据库   查找用户所属部门信息
		
		return "tocreate";
	}
	
	
	public String insert() {
		//查找数据库  上级部门信息
		moduleservice.saveOrUpdate(module);
		//跳转链接
		return "alllist";
	}
	
	
	public String toupdate() {
		Module module2 = moduleservice.get(Module.class, module.getId());
		ActionContext.getContext().getValueStack().push(module2);
		return "upui";
	}
	
	public String update() {
		Module module2 = moduleservice.get(Module.class, module.getId());
		module2.setName(module.getName());
		module2.setLayerNum(module.getLayerNum());
		module2.setCpermission(module.getCpermission());
		module2.setCurl(module.getCurl());
		module2.setCtype(module.getCtype());
		module2.setState(module.getState());
		module2.setBelong(module.getBelong());
		module2.setRemark(module.getRemark());
		module2.setOrderNo(module.getOrderNo());
		moduleservice.saveOrUpdate(module2);
		return "alllist";
	}
	
	
	public String delete() {
		String ids[] =module.getId().split(", ");
		moduleservice.delete(Module.class, ids);
		return "alllist";
	}
	
	
}
