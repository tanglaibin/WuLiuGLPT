<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
    <script type="text/javascript" src="${ctx}/components/jquery-ui/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/tabledo.js"></script>	
	<script type="text/javascript" src="${ctx}/js/datepicker/WdatePicker.js"></script>

</head>

<body>
<form name="icform" method="post">
	<input type="hidden" name="id" value="${id}"/>

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('exportAction_update','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   修改出口报运
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">报运号：</td>
	            <td class="tableContent">${customerContract}</td>
	            <td class="columnTitle">制单日期：</td>
	            <td class="tableContent">
					<input type="text" style="width:90px;" name="inputDate"
	            	 value="<fmt:formatDate value="${inputDate}" pattern="yyyy-MM-dd"/>"
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
				</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">信用证号</td>
	            <td class="tableContent"><input type="text" name="lcno" value="${lcno}"/></td>
	            <td class="columnTitle">收货人及地址：</td>
	            <td class="tableContent"><input type="text" name="consignee" value="${consignee}"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">装运港：</td>
	            <td class="tableContent"><input type="text" name="shipmentPort" value="${shipmentPort}"/></td>
	            <td class="columnTitle">目的港：</td>
	            <td class="tableContent"><input type="text" name="destinationPort" value="${destinationPort}"/></td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">运输方式：</td>
	            <td class="tableContent"><input type="text" name="transportMode" value="${transportMode}"/></td>
	            <td class="columnTitle">价格条件：</td>
	            <td class="tableContent"><input type="text" name="priceCondition" value="${priceCondition}"/></td>
	        </tr>
	        <tr>
	            <td class="columnTitle">唛头：</td>
	            <td class="tableContent"><textarea name="marks" style="height:120px;">${marks}</textarea></td>
	            <td class="columnTitle">备注：</td>
	            <td class="tableContent"><textarea name="remark" style="height:120px;">${remark}</textarea></td>
	        </tr>
		</table>
	</div>




 <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   出口报运货物列表
  </div> 


<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr style="height: 40px;font-weight: bold;">
		<td class="tableHeader">id</td>
		<td class="tableHeader">毛重</td>
		<td class="tableHeader">净重</td>
		<td class="tableHeader">长</td>
		<td class="tableHeader">宽</td>
		<td class="tableHeader">高</td>
		<td class="tableHeader">数量</td>
		<td class="tableHeader">出口单价</td>
		<td class="tableHeader">含税</td>
	</tr>
	</thead>
	<tbody class="tableBody" >
 	<c:forEach items="${exportProducts}" var="o" varStatus="status">
	<tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
		<td><input type="text" name="mr_id" value="${o.id}"/></td>
		<td><input type="text" name="mr_grossWeight" value="${o.grossWeight}"></td>
		<td><input type="text" name="mr_netWeight" value="${o.netWeight}"></td>
		<td><input type="text" name="mr_sizeLength" value="${o.sizeLength}"></td>
		<td><input type="text" name="mr_sizeWidth" value="${o.sizeWidth}"></td>
		<td><input type="text" name="mr_sizeHeight" value="${o.sizeHeight}"></td>
		<td><input type="text" name="mr_cnumber" value="${o.cnumber}"></td>
		<td><input type="text" name="mr_exPrice" value="${o.exPrice}"></td>
		<td><input type="text" name="mr_tax" value="${o.tax}"></td>
	</tr>
	</c:forEach> 
	
	</tbody>
</table>
</div> 

 
</form>
</body>
</html>

