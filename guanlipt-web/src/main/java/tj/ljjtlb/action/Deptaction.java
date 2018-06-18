package tj.ljjtlb.action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import tj.ljjtlb.service.Deptservice;
import tj.ljjtlb.utils.Page;
import tl.ljjtlb.domain.Dept;

public class Deptaction extends BaseAction implements ModelDriven<Dept>{
	
	private Dept dept=new Dept();
	public Dept getModel() {
		return dept;
	}
//注入service
	private Deptservice deptservice;
	public void setDeptservice(Deptservice deptservice) {
		this.deptservice = deptservice;
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
		page = deptservice.findPage("from Dept", page, Dept.class, null);
		page.setUrl("deptAction_list");
		ActionContext.getContext().getValueStack().push(page);
		return "list";
	}
	
	
	public String toview(){
//根据封装的id  查找dept对象  放入值栈取出展示
		Dept dept2 = deptservice.get(Dept.class, dept.getId());
		ActionContext.getContext().getValueStack().push(dept2);
		return "toview";
	}
	
	
	public String tocreate() {
		//查找数据库  上级部门信息
		List<Dept> list = deptservice.find("from Dept where state=1", Dept.class, null);
		//放入值栈
		ActionContext.getContext().put("deptList", list);
		//跳转链接
		return "tocreate";
	}
	
	
	public String insert() {
		//查找数据库  上级部门信息
		deptservice.saveOrUpdate(dept);
		//跳转链接
		return "alllist";
	}
	
	
	public String toupdate() {
		Dept dept2 = deptservice.get(Dept.class, dept.getId());
		ActionContext.getContext().getValueStack().push(dept2);
		
		List<Dept> list = deptservice.find("from Dept where state=1", Dept.class, null);
		list.remove(dept2);
		ActionContext.getContext().put("deptList", list);
		
		return "upui";
	}
	
	public String update() {
		Dept dept2 = deptservice.get(Dept.class, dept.getId());
		dept2.setDept_name(dept.getDept_name());
		dept2.setDeptparent(dept.getDeptparent());
		deptservice.saveOrUpdate(dept2);
		return "alllist";
	}
	
	
	public String delete() {
		String ids[] =dept.getId().split(", ");
		System.out.println(ids);
		deptservice.delete(Dept.class, ids);
		return "alllist";
	}
	
}
