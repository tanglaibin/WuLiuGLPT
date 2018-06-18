package tl.ljjtlb.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @Description:	ShippingOrderService接口
 * @Author:			唐来彬	唐来彬
 * @Company:		http://www.baidu.com
 * @CreateDate:		2018-3-22 11:45:55
 */

public class ShippingOrder implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id;	  
	private String orderType;			//SEA海运AIR空运
	private String shipper;			
	private String consignee;			
	private String notifyParty;			
	private String lcNo;			
	private String portOfLoading;			
	private String portOfTrans;			
	private String portOfDischarge;			
	private String loadingDate;			
	private String limitDate;			
	private String isBatch;			//1是0否
	private String isTrans;			//1是0否
	private String copyNum;			
	private String remark;			
	private String specialCondition;			
	private String freight;			
	private String checkBy;			
	private Integer state;			//0草稿 1已上报
	private String createBy;			
	private String createDept;			
	private String createTime;			

	
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateDept() {
		return createDept;
	}
	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	public String getOrderType() {
		return this.orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}	
	
	public String getShipper() {
		return this.shipper;
	}
	public void setShipper(String shipper) {
		this.shipper = shipper;
	}	
	
	public String getConsignee() {
		return this.consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}	
	
	public String getNotifyParty() {
		return this.notifyParty;
	}
	public void setNotifyParty(String notifyParty) {
		this.notifyParty = notifyParty;
	}	
	
	public String getLcNo() {
		return this.lcNo;
	}
	public void setLcNo(String lcNo) {
		this.lcNo = lcNo;
	}	
	
	public String getPortOfLoading() {
		return this.portOfLoading;
	}
	public void setPortOfLoading(String portOfLoading) {
		this.portOfLoading = portOfLoading;
	}	
	
	public String getPortOfTrans() {
		return this.portOfTrans;
	}
	public void setPortOfTrans(String portOfTrans) {
		this.portOfTrans = portOfTrans;
	}	
	
	public String getPortOfDischarge() {
		return this.portOfDischarge;
	}
	public void setPortOfDischarge(String portOfDischarge) {
		this.portOfDischarge = portOfDischarge;
	}	
	
	public String getLoadingDate() {
		return this.loadingDate;
	}
	public void setLoadingDate(String loadingDate) {
		this.loadingDate = loadingDate;
	}	
	
	public String getLimitDate() {
		return this.limitDate;
	}
	public void setLimitDate(String limitDate) {
		this.limitDate = limitDate;
	}	
	
	public String getIsBatch() {
		return this.isBatch;
	}
	public void setIsBatch(String isBatch) {
		this.isBatch = isBatch;
	}	
	
	public String getIsTrans() {
		return this.isTrans;
	}
	public void setIsTrans(String isTrans) {
		this.isTrans = isTrans;
	}	
	
	public String getCopyNum() {
		return this.copyNum;
	}
	public void setCopyNum(String copyNum) {
		this.copyNum = copyNum;
	}	
	
	public String getRemark() {
		return this.remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}	
	
	public String getSpecialCondition() {
		return this.specialCondition;
	}
	public void setSpecialCondition(String specialCondition) {
		this.specialCondition = specialCondition;
	}	
	
	public String getFreight() {
		return this.freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}	
	
	public String getCheckBy() {
		return this.checkBy;
	}
	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}	
	
	public Integer getState() {
		return this.state;
	}
	public void setState(Integer state) {
		this.state = state;
	}	
	
	
	
}
