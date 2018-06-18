package tj.ljjtlb.action.cargo;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import tj.ljjtlb.action.BaseAction;
import tj.ljjtlb.service.ContractProductservice;
import tj.ljjtlb.service.Factoryservice;
import tj.ljjtlb.utils.Page;
import tl.ljjtlb.domain.Contract;
import tl.ljjtlb.domain.ContractProduct;
import tl.ljjtlb.domain.Factory;
import tl.ljjtlb.domain.Module;

public class ContractProductaction extends BaseAction implements ModelDriven<ContractProduct>{
	
	private ContractProduct contractproduct=new ContractProduct();
	public ContractProduct getModel() {
		return contractproduct;
	}
	
    //注入service
    private ContractProductservice contractProductservice;
	public void setContractProductservice(ContractProductservice contractProductservice) {
	this.contractProductservice = contractProductservice;
    }
	private Factoryservice factoryservice;
	public void setFactoryservice(Factoryservice factoryservice) {
		this.factoryservice = factoryservice;
	}

		//注入page  new出分页数据空间
		private Page page=new Page();
		public Page getPage() {
			return page;
		}
		public void setPage(Page page) {
			this.page = page;
		}

	
	public String tocreate() {
//查出厂价名称信息   该购销合同下的货物列表信息
		List<Factory> factorylist = factoryservice.find("from Factory where ctype='货物' and state=1 ", Factory.class, null);
		ActionContext.getContext().put("factorylist", factorylist);
		System.out.println(contractproduct.getContract().getId());
		//查找该合同下的货物列表分页数据  放入值栈栈顶
		page = contractProductservice.findPage("from ContractProduct where contract.id=?", page, ContractProduct.class, new String[]{contractproduct.getContract().getId()});
		page.setUrl("contractProductAction_tocreate");
		ActionContext.getContext().getValueStack().push(page);
		
		return "tocreate";
	}
	
	
	public String insert() {
		//查找数据库  上级部门信息
		contractProductservice.saveOrUpdate(contractproduct);
		//跳转链接
		return tocreate();
	}
	
	
	public String toupdate() {
		List<Factory> factorylist = factoryservice.find("from Factory where ctype='货物' and state=1 ", Factory.class, null);
		ActionContext.getContext().put("factoryList", factorylist);
		
		ContractProduct contract2 = contractProductservice.get(ContractProduct.class, contractproduct.getId());
		ActionContext.getContext().getValueStack().push(contract2);
		return "upui";
	}
	
	public String update() {
		ContractProduct contract2 = contractProductservice.get(ContractProduct.class, contractproduct.getId());
		contract2.setFactory(contractproduct.getFactory());
		contract2.setFactoryName(contractproduct.getFactoryName());
		contract2.setCnumber(contractproduct.getCnumber());
		contract2.setPrice(contractproduct.getPrice());
		contract2.setAmount(contractproduct.getAmount());
		contractProductservice.saveOrUpdate(contract2);
		return tocreate();
	}
	
	public String delete() {
		contractProductservice.deletehuowu(ContractProduct.class, contractproduct);
		return tocreate();
	}
	
	
	
}
