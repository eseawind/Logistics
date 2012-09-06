<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>华亿物流管理系统</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="css/index.css" />
	<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"> </script>
	<script type="text/javascript" src="extjs/ext-all.js">  </script>
	<script type="text/javascript" src="script/common.js"></script>
	<script type="text/javascript" src="script/util/md5.js"></script>
	<script type="text/javascript" src="script/index.js"></script>
	
  </head>
  
  <body>
   	<div id='invalidTag'></div>
  </body>
</html>
