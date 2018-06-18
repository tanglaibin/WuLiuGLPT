package tj.ljjtlb.action.cargo;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import tj.ljjtlb.action.BaseAction;
import tj.ljjtlb.service.Contractservice;
import tj.ljjtlb.utils.Page;
import tj.ljjtlb.utils.SysConstant;
import tl.ljjtlb.domain.Contract;
import tl.ljjtlb.domain.User;

public class Contractaction extends BaseAction implements ModelDriven<Contract>{
	
	private Contract contract=new Contract();
	public Contract getModel() {
		return contract;
	}
    //注入service

private Contractservice contractservice;
	public void setContractservice(Contractservice contractservice) {
	this.contractservice = contractservice;
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
		//细粒度控制
		String hql="from Contract where 1=1";
		//获取用户 根据用户等级  确定展示内容
		User user=super.getcuruser();
		int degree=user.getUserinfo().getDegree();
		if (degree==4) {
			hql+="and createby='"+user.getId()+"'";
		}else if(degree==3){
			hql+="and createDept='"+user.getDept().getId()+"'";
		}
		
		page = contractservice.findPage(hql, page, Contract.class, null);
		page.setUrl("contractAction_list");
		ActionContext.getContext().getValueStack().push(page);
		return "list";
	}
	
	
	public String toview(){
//根据封装的id  查找dept对象  放入值栈取出展示
		Contract dept2 = contractservice.get(Contract.class, contract.getId());
		ActionContext.getContext().getValueStack().push(dept2);
		return "toview";
	}
	
	
	public String tocreate() {
		//查找数据库   查找用户所属部门信息
		
		return "tocreate";
	}
	
	
	public String insert() {
		//查找数据库  上级部门信息
		User user = (User) session.get(SysConstant.CURRENT_USER_INFO);
		contract.setCreateby(user.getId());
		contract.setCreateDept(user.getDept().getId());
		contractservice.saveOrUpdate(contract);
		//跳转链接
		return "alllist";
	}
	
	
	public String toupdate() {
		Contract contract2 = contractservice.get(Contract.class, contract.getId());
		ActionContext.getContext().getValueStack().push(contract2);
		return "upui";
	}
	
	public String update() {
		Contract contract2 = contractservice.get(Contract.class, contract.getId());
		contract2.setCustomName(contract.getCustomName());
		contract2.setPrintStyle(contract.getPrintStyle());
		contract2.setContractNo(contract.getContractNo());
		contract2.setOfferor(contract.getOfferor());
		
		contractservice.saveOrUpdate(contract2);
		return "alllist";
	}
	
	
	public String delete() {
		String ids[] =contract.getId().split(", ");
		contractservice.delete(Contract.class, ids);
		return "alllist";
	}
	
	public String submit() {
		String ids[] =contract.getId().split(", ");
		contractservice.submit(ids,1);
		return "alllist";
	}
	
	public String cancel() {
		String ids[] =contract.getId().split(", ");
		contractservice.submit(ids,0);
		return "alllist";
	}
	
	/**
	 * 打印
	 */
	public String print() throws Exception {
		//1.根据购销合同的id,得到购销合同对象
		Contract contract2 = contractservice.get(Contract.class, contract.getId());
		
		//2.指定path
		String path = ServletActionContext.getServletContext().getRealPath("/");//应用程序的根路径
		
		//3.指定response
		HttpServletResponse response = ServletActionContext.getResponse();
		
		ContractPrint cp = new ContractPrint();
		cp.print(contract2, path, response);
		
		return NONE;
	}
	
}
