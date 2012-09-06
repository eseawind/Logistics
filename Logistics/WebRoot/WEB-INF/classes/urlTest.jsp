<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'urlTest.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
   <form action="Barcode.importBarcodes.action" method="post" enctype="multipart/form-data">
            标题：<input type="text" name="mdto.header">
            正文：<input type="text" name="mdto.body">
            类型：<input type="text" name="mdto.type">
           文件：
            <input type="file" name="upload" >
            <input type="submit" name="submit" value=" 新增 ">
    </form>
     -------------------------------------------
  ------------------------------------------------
  
  <form action="FinancialLog.queryOnCondition.action" method="post" enctype="multipart/form-data" >
  开始日期：<input type="text" name="startDate">
  结束日期：<input type="text" name="endDate">
  limit:<input type="text" name="limit">
  user<input type="text" name="user">
  oprtype：<input type="text" name="">
  <input type="submit" name="submit" value="查询">
  </form>
  -------------------------------------------
  ------------------------------------------------
  <form action="FinancialLog.queryOne.action" method="post" enctype="multipart/form-data" >
  编号：<input type="text" name="barcode.barcodeID">
  <input type="submit" name="submit" value="查询一个">
  
  </form>
   -------------------------------------------
  ------------------------------------------------
   <form action="FinancialLog.update.action" method="post" enctype="multipart/form-data" >
  编号：<input type="text" name="barcode.barcodeID">
  barcode.manifestID<input type="text" name="barcode.manifestID">
  附件：<input type="file" name="upload">
  <input type="submit" name="submit" value="修改">
  </form>
   -------------------------------------------
  ------------------------------------------------
 
  <form action="FinancialLog.delete.action" method="post" enctype="multipart/form-data" >
  编号：<input type="text" name="ids">
  <input type="submit" name="submit" value="删除">
   
  </form>
  -------------------------------------------
  ------------------------------------------------
   
  <form action="FinancialLog.download.action" method="post" enctype="multipart/form-data" >
  编号：<input type="text" name=mdto.messageID>
  <input type="submit" name="submit" value="下载">
  
  </form>
   -------------------------------------------
  ------------------------------------------------
  
  
  <form action="FinancialLog.importFinancialLogs.action" method="post" enctype="multipart/form-data">
            File:
            <input type="file" name="upload" >
            <input type="submit" name="submit" value="导入">
    </form>
     -------------------------------------------
  ------------------------------------------------
  
    
  </body>
</html>
