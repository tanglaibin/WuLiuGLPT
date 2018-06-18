package tj.ljjtlb.action.cargo;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import tj.ljjtlb.action.BaseAction;
import tj.ljjtlb.service.Exportservice;
import tj.ljjtlb.service.PackingListService;
import tj.ljjtlb.utils.Page;
import tj.ljjtlb.utils.SysConstant;
import tl.ljjtlb.domain.Export;
import tl.ljjtlb.domain.PackingList;
import tl.ljjtlb.domain.User;

/**
 * @Description:	PackingListService接口
 * @Author:			唐来彬	唐来彬
 * @Company:		http://www.baidu.com
 * @CreateDate:		2018-3-20 16:02:05
 */

public class PackingListAction extends BaseAction implements ModelDriven<PackingList> {
	//注入service
	private PackingListService packingListService;
	public void setPackingListService(PackingListService packingListService) {
		this.packingListService = packingListService;
	}
	
	
	private Exportservice exportservice;
	public void setExportservice(Exportservice exportservice) {
	this.exportservice = exportservice;
}
	
	
	//model驱动
	private PackingList model = new PackingList();
	public PackingList getModel() {
		return this.model;
	}
	
	//作为属性驱动，接收并封装页面参数
	private Page page = new Page();			//封装页面的参数，主要当前页参数
	public void setPage(Page page) {
		this.page = page;
	}


	//部门列表
	public String list(){
		String hql = "from PackingList";			//查询所有内容
		//给页面提供分页数据
		page.setUrl("packingListAction_list");		//配置分页按钮的转向链接
		page = packingListService.findPage(hql, page, PackingList.class, null);
		ActionContext.getContext().getValueStack().push(page);
		return "plist";			//page list
	}
	
	//转向新增页面
	public String tocreate(){
		//进入新增装箱单页面   获取报运单分页信息  放入值栈  转发页面  页面展示出来   
		
		String hql="from Export where state = 1";
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
		
		return "pcreate";
	}
	
	//新增保存
	public String insert(){
		//System.out.println(model.getExportIds());
		User user = (User) session.get(SysConstant.CURRENT_USER_INFO);
		model.setCreateBy(user.getId());
		model.setCreateDept(user.getDept().getId());
		packingListService.saveOrUpdate(model);
		return "alist";
	}

	
	
	
	//上报或取消上报  修改state为1 或为0
	public String submit(){
		//获取ids  遍历 设置state为1 or 0
		String ids[] =model.getId().split(", ");
		exportservice.submit(ids,1);		
		return "alist";
	}
	
	public String cancle(){
		//获取ids  遍历 设置state为1 or 0
		String[] ids =model.getId().split(", ");
		exportservice.submit(ids,0);		
		return "alist";
	}
	
	//转向修改页面
	public String toupdate(){
		//准备修改的数据
		PackingList obj = packingListService.get(PackingList.class, model.getId());
		ActionContext.getContext().getValueStack().push(obj);
		return "pupdate";
	}
	
	//修改保存
	public String update(){
		PackingList packingList = packingListService.get(PackingList.class, model.getId());
		
		//设置修改的属性，根据业务去掉自动生成多余的属性
		packingList.setSeller(model.getSeller());
		packingList.setBuyer(model.getBuyer());
		packingList.setInvoiceNo(model.getInvoiceNo());
		packingList.setInvoiceDate(model.getInvoiceDate());
		packingList.setMarks(model.getMarks());
		packingList.setDescriptions(model.getDescriptions());
		
		packingListService.saveOrUpdate(packingList);
		
		return "alist";
	}
	
	//删除一条
	public String deleteById(){
		packingListService.deleteById(PackingList.class, model.getId());
		
		return "alist";
	}
	
	
	public String delete(){
		//删除装箱单 及多条装箱单    删除装箱单同时 更新下面的报运单状态为null 
		packingListService.delete(PackingList.class, model.getId().split(", "));
		return "alist";
	}
	
	//查看
	public String toview(){
		PackingList obj = packingListService.get(PackingList.class, model.getId());
		ActionContext.getContext().getValueStack().push(obj);
		return "pview";			//转向查看页面
	}
}
