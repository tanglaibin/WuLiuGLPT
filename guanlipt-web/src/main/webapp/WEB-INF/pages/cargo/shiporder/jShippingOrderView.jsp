<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
</head>

<body>
<form name="icform" method="post">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   浏览委托单
  </div>
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">运输方式：</td>
	            <td class="tableContent">${orderType}</td>
	            <td class="columnTitle">xx：</td>
	            <td class="tableContent">${shipper}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">x：</td>
	            <td class="tableContent">${consignee}</td>
	            <td class="columnTitle">xx：</td>
	            <td class="tableContent">${notifyParty}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">xx：</td>
	            <td class="tableContent">${lcNo}</td>
	            <td class="columnTitle">xx：</td>
	            <td class="tableContent">${portOfLoading}</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">xx：</td>
	            <td class="tableContent">${portOfTrans}</td>
	            <td class="columnTitle">xxx：</td>
	            <td class="tableContent">${portOfDischarge}</td>
	        </tr>	
	        
		</table>
	</div>
 
 
</form>
</body>
</html>

