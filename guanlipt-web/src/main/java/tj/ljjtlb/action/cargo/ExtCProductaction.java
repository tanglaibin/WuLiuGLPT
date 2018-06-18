package tj.ljjtlb.action.cargo;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import tj.ljjtlb.action.BaseAction;
import tj.ljjtlb.service.ContractProductservice;
import tj.ljjtlb.service.ExtCProductservice;
import tj.ljjtlb.service.Factoryservice;
import tj.ljjtlb.utils.Page;
import tl.ljjtlb.domain.Contract;
import tl.ljjtlb.domain.ContractProduct;
import tl.ljjtlb.domain.ExtCproduct;
import tl.ljjtlb.domain.Factory;
import tl.ljjtlb.domain.Module;

public class ExtCProductaction extends BaseAction implements ModelDriven<ExtCproduct>{
	
	private ExtCproduct extCproduct=new ExtCproduct();
	public ExtCproduct getModel() {
		return extCproduct;
	}
	
        //注入service
    private ExtCProductservice extCProductservice;
	public void setExtCProductservice(ExtCProductservice extCProductservice) {
	this.extCProductservice = extCProductservice;
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
		
		List<Factory> factorylist = factoryservice.find("from Factory where ctype='附件' and state=1 ", Factory.class, null);
		ActionContext.getContext().put("factorylist", factorylist);
		//查找该合同下的货物列表分页数据  放入值栈栈顶
		page = extCProductservice.findPage("from ExtCproduct where contractProduct.id=?", page, ExtCproduct.class, new String[]{extCproduct.getContractProduct().getId()});
		page.setUrl("extCProductAction_tocreate");
		ActionContext.getContext().getValueStack().push(page);
		
		return "tocreate";
	}
	
	
	public String insert() {
		//查找数据库  上级部门信息
		extCProductservice.saveOrUpdate(extCproduct);
		//跳转链接
		return tocreate();
	}
	
	
	public String toupdate() {
		List<Factory> factorylist = factoryservice.find("from Factory where ctype='附件' and state=1 ", Factory.class, null);
		ActionContext.getContext().put("factorylist", factorylist);
		//查找该合同下的货物列表分页数据  放入值栈栈顶
		
		ExtCproduct contract2 = extCProductservice.get(ExtCproduct.class, extCproduct.getId());
		ActionContext.getContext().getValueStack().push(contract2);
		
		return "upui";
	}
	
	public String update() {
		ExtCproduct contract2 = extCProductservice.get(ExtCproduct.class, extCproduct.getId());
		contract2.setFactory(extCproduct.getFactory());
		contract2.setFactoryName(extCproduct.getFactoryName());
		contract2.setCnumber(extCproduct.getCnumber());
		contract2.setPrice(extCproduct.getPrice());
		contract2.setAmount(extCproduct.getAmount());;
		extCProductservice.saveOrUpdate(contract2);
		return tocreate();
	}
	
	public String delete() {
        //删除附件   更新货物金额    合同金额
		extCProductservice.deletefujian(ExtCproduct.class, extCproduct);
		return tocreate();
	}
	
	
	
}
