package tj.ljjtlb.action.cargo;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import tj.ljjtlb.action.BaseAction;
import tj.ljjtlb.service.PackingListService;
import tj.ljjtlb.service.ShippingOrderService;
import tj.ljjtlb.utils.Page;
import tj.ljjtlb.utils.SysConstant;
import tl.ljjtlb.domain.Export;
import tl.ljjtlb.domain.PackingList;
import tl.ljjtlb.domain.ShippingOrder;
import tl.ljjtlb.domain.User;

/**
 * @Description:	ShippingOrderService接口
 * @Author:			唐来彬	唐来彬
 * @Company:		http://www.baidu.com
 * @CreateDate:		2018-3-22 11:45:56
 */

public class ShippingOrderAction extends BaseAction implements ModelDriven<ShippingOrder> {
	//注入service
	private ShippingOrderService shippingOrderService;
	public void setShippingOrderService(ShippingOrderService shippingOrderService) {
		this.shippingOrderService = shippingOrderService;
	}
	
	private PackingListService packingListService;
	public void setPackingListService(PackingListService packingListService) {
		this.packingListService = packingListService;
	}

	//model驱动
	private ShippingOrder model = new ShippingOrder();
	public ShippingOrder getModel() {
		return this.model;
	}
	
	private String pid;
	public void setPid(String pid) {
		this.pid = pid;
	}

	//作为属性驱动，接收并封装页面参数
	private Page page = new Page();			//封装页面的参数，主要当前页参数
	public void setPage(Page page) {
		this.page = page;
	}


	//部门列表
	public String list(){
		String hql = "from ShippingOrder";			//查询所有内容
		//给页面提供分页数据
		page.setUrl("shippingOrderAction_list");		//配置分页按钮的转向链接
		page = shippingOrderService.findPage(hql, page, ShippingOrder.class, null);
		ActionContext.getContext().getValueStack().push(page);
		return "plist";						//page list
	}
	
	//转向新增页面
	public String tocreate(){
		//准备装箱单数据  放入栈顶  转发到页面 展示
		String hql="from PackingList where state = 1";
		//获取用户 根据用户等级  确定展示内容
		User user=super.getcuruser();
		int degree=user.getUserinfo().getDegree();
		if (degree==4) {
			hql+="and createby='"+user.getId()+"'";
		}else if(degree==3){
			hql+="and createDept='"+user.getDept().getId()+"'";
		}
		page = packingListService.findPage(hql, page, PackingList.class, null);
		page.setUrl("packingListAction_list");
		ActionContext.getContext().getValueStack().push(page);
		return "pcreate";
	}
	
	//新增保存
	public String insert(){
		User user = (User) session.get(SysConstant.CURRENT_USER_INFO);
		model.setCreateBy(user.getId());
		model.setCreateDept(user.getDept().getId());
		shippingOrderService.saveOrUpdate(model,pid);
		return "alist";	
	}

	public String toupdate(){
		ShippingOrder obj = shippingOrderService.get(ShippingOrder.class, model.getId());
		ActionContext.getContext().getValueStack().push(obj);
		return "pupdate";
	}
	
	//修改保存
	public String update(){
		ShippingOrder shippingOrder = shippingOrderService.get(ShippingOrder.class, model.getId());
		shippingOrder.setOrderType(model.getOrderType());
		shippingOrderService.saveOrUpdate(shippingOrder);
		return "alist";
	}
	
	//删除委托单   更新 装箱单 状态为1
	public String delete(){
		shippingOrderService.delete(ShippingOrder.class, model.getId().split(", "));
		return "alist";
	}
	
	//查看
	public String toview(){
		ShippingOrder obj = shippingOrderService.get(ShippingOrder.class, model.getId());
		ActionContext.getContext().getValueStack().push(obj);
		return "pview";			//转向查看页面
	}
	
	public String submit(){
		//获取ids  遍历 设置state为1 or 0
		if (model.getId()==null ||model.getId()=="") {
			return "alist";
		}
		String ids[] =model.getId().split(", ");
		shippingOrderService.submit(ids,1);		
		return "alist";
	}
	
	public String cancle(){
		//获取ids  遍历 设置state为1 or 0
		if (model.getId()==null ||model.getId()=="") {
			return "alist";
		}
		String[] ids =model.getId().split(", ");
		shippingOrderService.submit(ids,0);		
		return "alist";
	}
	
}
