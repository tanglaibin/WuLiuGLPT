package tj.ljjtlb.action.cargo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import jckby.webservice.EpService;
import jckby.webservice.Exception_Exception;
import tj.ljjtlb.action.BaseAction;
import tj.ljjtlb.service.Contractservice;
import tj.ljjtlb.service.Exportproductservice;
import tj.ljjtlb.service.Exportservice;
import tj.ljjtlb.utils.Page;
import tj.ljjtlb.utils.SysConstant;
import tl.ljjtlb.domain.Contract;
import tl.ljjtlb.domain.Export;
import tl.ljjtlb.domain.ExportProduct;
import tl.ljjtlb.domain.User;

public class Exportaction extends BaseAction implements ModelDriven<Export>{
	
	private Export export=new Export();
	public Export getModel() {
		return export;
	}
    //注入service
    private Exportservice exportservice;
	public void setExportservice(Exportservice exportservice) {
	this.exportservice = exportservice;
}

	private Contractservice contractservice;
	public void setContractservice(Contractservice contractservice) {
	this.contractservice = contractservice;
}
	
	private Exportproductservice exportproductservice;
	public void setExportproductservice(Exportproductservice exportproductservice) {
		this.exportproductservice = exportproductservice;
	}
	
	private String mr_id[];
	private Integer mr_cnumber[];
	private Integer mr_orderNo[];
	private Double mr_grossWeight[];
	private Double mr_netWeight[];
	private Double mr_sizeLength[];
	private Double mr_sizeWidth[];
	private Double mr_sizeHeight[];
	private Double mr_exPrice[];
	private Double mr_tax[];
	public void setMr_id(String[] mr_id) {
		this.mr_id = mr_id;
	}
	public void setMr_cnumber(Integer[] mr_cnumber) {
		this.mr_cnumber = mr_cnumber;
	}
	public void setMr_orderNo(Integer[] mr_orderNo) {
		this.mr_orderNo = mr_orderNo;
	}
	public void setMr_grossWeight(Double[] mr_grossWeight) {
		this.mr_grossWeight = mr_grossWeight;
	}
	public void setMr_netWeight(Double[] mr_netWeight) {
		this.mr_netWeight = mr_netWeight;
	}
	public void setMr_sizeLength(Double[] mr_sizeLength) {
		this.mr_sizeLength = mr_sizeLength;
	}
	public void setMr_sizeWidth(Double[] mr_sizeWidth) {
		this.mr_sizeWidth = mr_sizeWidth;
	}
	public void setMr_sizeHeight(Double[] mr_sizeHeight) {
		this.mr_sizeHeight = mr_sizeHeight;
	}
	public void setMr_exPrice(Double[] mr_exPrice) {
		this.mr_exPrice = mr_exPrice;
	}
	public void setMr_tax(Double[] mr_tax) {
		this.mr_tax = mr_tax;
	}
	//注入page  new出分页数据空间
	private Page page=new Page();
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}

	
	public String contractList(){
//查找购销合同数
		
		//细粒度控制
				String hql="from Contract where state=1";
				//获取用户 根据用户等级  确定展示内容
				User user=super.getcuruser();
				int degree=user.getUserinfo().getDegree();
				if (degree==4) {
					hql+="and createby='"+user.getId()+"'";
				}else if(degree==3){
					hql+="and createDept='"+user.getDept().getId()+"'";
				}
				
				page = contractservice.findPage(hql, page, Contract.class, null);
				page.setUrl("exportAction_contractList");
				ActionContext.getContext().getValueStack().push(page);
		
		return "contractList";
	}
	
	public String tocreate() {
		//System.out.println(export.getId()+"export 封装了contractid");
		return "tocreate";
	}
	
	
	public String insert() {
		//查找数据库  上级部门信息
		User user = (User) session.get(SysConstant.CURRENT_USER_INFO);
		export.setCreateby(user.getId());
		export.setCreateDept(user.getDept().getId());
		exportservice.saveOrUpdate(export);
		//跳转链接
		return contractList();
	}
	
	public String list(){
		//细粒度控制
		String hql="from Export where 1=1";
		//获取用户 根据用户等级  确定展示内容
		User user=super.getcuruser();
		int degree=user.getUserinfo().getDegree();
		if (degree==4) {
			hql+="and createby='"+user.getId()+"'";
		}else if(degree==3){
			hql+="and createDept='"+user.getDept().getId()+"'";
		}
		
		page = exportservice.findPage(hql, page, Export.class, null);
		page.setUrl("exportAction_list");
		ActionContext.getContext().getValueStack().push(page);
		return "list";
	}
	
	public String toview(){
		//根据封装的id  查找dept对象  放入值栈取出展示
				Export dept2 = exportservice.get(Export.class, export.getId());
				ActionContext.getContext().getValueStack().push(dept2);
				return "toview";
			}
	
	
	
	
	public String toupdate() {
		Export export2 = exportservice.get(Export.class, export.getId());
		ActionContext.getContext().getValueStack().push(export2);
		return "upui";
	}
	
	public String update() {
		//保存更改后的运单
		Export ext = exportservice.get(Export.class, export.getId());
		//2.设置修改的属性
				ext.setInputDate(export.getInputDate());
		        ext.setLcno(export.getLcno());
		        ext.setConsignee(export.getConsignee());
		        ext.setShipmentPort(export.getShipmentPort());
		        ext.setDestinationPort(export.getDestinationPort());
		        ext.setTransportMode(export.getTransportMode());
		        ext.setPriceCondition(export.getPriceCondition());
		        ext.setMarks(export.getMarks());//唛头是指具有一定格式的备注信息
		        ext.setRemark(export.getRemark());
		//封装运单下的货物修改信息
		        
		        Set<ExportProduct> epSet =new HashSet<ExportProduct>();//商品列表
		        
		        for(int i=0;i<mr_id.length;i++){
		        	//遍历数组，得到每个商品对象
		        	ExportProduct ep = exportproductservice.get(ExportProduct.class, mr_id[i]);
		        		ep.setCnumber(mr_cnumber[i]);
		        		ep.setGrossWeight(mr_grossWeight[i]);
		        		ep.setNetWeight(mr_netWeight[i]);
		        		ep.setSizeLength(mr_sizeLength[i]);
		        		ep.setSizeWidth(mr_sizeWidth[i]);
		        		ep.setSizeHeight(mr_sizeHeight[i]);
		        		ep.setExPrice(mr_exPrice[i]);
		        		ep.setTax(mr_tax[i]);
		        	epSet.add(ep);
		        }
		        
		        //设置报运单与商品列表的关系 
		        ext.setExportProducts(epSet);
		exportservice.saveOrUpdate(ext);
		return "alllist";
	}
	
	
	public String delete() {
		String ids[] =export.getId().split(", ");
		exportservice.delete(Export.class, ids);
		return list();
	}
	
	public String submit() {
		String ids[] =export.getId().split(", ");
		exportservice.submit(ids,1);
		return list();
	}
	
	public String cancel() {
		String ids[] =export.getId().split(", ");
		exportservice.submit(ids,0);
		return list();
	}
	
	
	public String export() throws Exception_Exception {
//货物export  转换为json  调取远程service  发取得反馈  保存数据库 
		Export export2 = exportservice.get(Export.class, export.getId());
		String jsonString = JSON.toJSONString(export2);
		//System.out.println(jsonString);
		
		JaxWsProxyFactoryBean client = new JaxWsProxyFactoryBean();
		
		//2.设置服务端的访问地址
		client.setAddress("http://localhost:8080/jckby/ws/export?wsdl");
		
		//3.设置服务端的接口
		client.setServiceClass(EpService.class);
		
		//4.创建客户端对象
		EpService iws = (EpService) client.create();
		
		//5.调用远程的服务端提供的方法
		String result = iws.exportE(jsonString);
		//System.out.println(result);
		//将结果保存到数据库 更新
		/*export2.set?  还是beanu？   用json再转回对象*/
		Export export3=JSON.parseObject(result, Export.class);
		exportservice.updatee(export3);
		
		return list();
	}
	
	
	
}
