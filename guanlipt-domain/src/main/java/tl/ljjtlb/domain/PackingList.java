package tl.ljjtlb.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @Description:	PackingListService接口
 * @Author:			唐来彬	唐来彬
 * @Company:		http://www.baidu.com
 * @CreateDate:		2018-3-20 16:02:05
 */

public class PackingList implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id;	  	
	private String seller;			
	private String buyer;			
	private String invoiceNo;			//选择
	private String invoiceDate;			
	private String marks;			
	private String descriptions;			
	private String exportIds;			//报运ID集合
	private String exportNos;			//报运NO集合x,y|z,h
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
	
	
	public String getSeller() {
		return this.seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}	
	
	public String getBuyer() {
		return this.buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}	
	
	public String getInvoiceNo() {
		return this.invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}	
	
	public String getInvoiceDate() {
		return this.invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}	
	
	public String getMarks() {
		return this.marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}	
	
	public String getDescriptions() {
		return this.descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}	
	
	public String getExportIds() {
		return this.exportIds;
	}
	public void setExportIds(String exportIds) {
		this.exportIds = exportIds;
	}	
	
	public String getExportNos() {
		return this.exportNos;
	}
	public void setExportNos(String exportNos) {
		this.exportNos = exportNos;
	}	
	
	public Integer getState() {
		return this.state;
	}
	public void setState(Integer state) {
		this.state = state;
	}	
	
	
	
}
