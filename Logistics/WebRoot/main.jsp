<%@ page language="java" pageEncoding="UTF-8"%>
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
	
	<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="css/main.css"/>
	<link rel="stylesheet" type="text/css" href="css/images.css"/>
	<link rel="stylesheet" type="text/css" href="css/index.css"/>
	
    <script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="extjs/ext-all.js"></script>
    <script type="text/javascript" src="extjs/src/locale/ext-lang-zh_CN.js"></script>
    <script type="text/javascript" src="script/main.js"></script>
    
    <script type="text/javascript" src="script/util/data.js"></script>
    <script type="text/javascript" src="script/common.js"></script>
    
    <script type="text/javascript" src="script/util/md5.js"></script>

	<script type="text/javascript" src="script/common/createTransWay.js"></script>
	<script type="text/javascript" src="script/common/transDetail.js"></script>
	<script type="text/javascript" src="script/common/goodsDetail.js"></script>
	<script type="text/javascript" src="script/common/trackNotice.js"></script>
	<script type="text/javascript" src="script/common/dataImport.js"></script>

	<script type="text/javascript" src="script/manageNews/createNews.js"></script>
	<script type="text/javascript" src="script/manageNews/editNews.js"></script>
	<script type="text/javascript" src="script/manageNews/detailNews.js"></script>
	<script type="text/javascript" src="script/manageNews/manageNews.js"></script>

	<script type="text/javascript" src="script/manageTransportation/createTransportationSheet.js"></script>
	<script type="text/javascript" src="script/manageTransportation/editTransportationSheet.js"></script>
	<script type="text/javascript" src="script/manageTransportation/detailTransportationSheet.js"></script>
	<script type="text/javascript" src="script/manageTransportation/manageTransportationSheet.js"></script>
	
	<script type="text/javascript" src="script/manageEntrucking/queryTransportationSheet.js"></script>
	<script type="text/javascript" src="script/manageEntrucking/createEntruckingSheet.js"></script>
	<script type="text/javascript" src="script/manageEntrucking/editEntruckingSheet.js"></script>
	<script type="text/javascript" src="script/manageEntrucking/detailEntruckingSheet.js"></script>
	<script type="text/javascript" src="script/manageEntrucking/manageEntruckingSheet.js"></script>
	
	<script type="text/javascript" src="script/manageBarCode/editBarCode.js"></script>
	<script type="text/javascript" src="script/manageBarCode/manageBarCode.js"></script>
	
	<script type="text/javascript" src="script/transportationTrack/transportationTrack.js"></script>
	
	<script type="text/javascript" src="script/manageEnterStorage/createEnterStorageSheet.js"></script>
	<script type="text/javascript" src="script/manageEnterStorage/editEnterStorageSheet.js"></script>
	<script type="text/javascript" src="script/manageEnterStorage/detailEnterStorageSheet.js"></script>
	<script type="text/javascript" src="script/manageEnterStorage/manageEnterStorageSheet.js"></script>
	
	<script type="text/javascript" src="script/manageOutStorage/queryOutGoods.js"></script>
	<script type="text/javascript" src="script/manageOutStorage/createOutStorageSheet.js"></script>
	<script type="text/javascript" src="script/manageOutStorage/editOutStorageSheet.js"></script>
	<script type="text/javascript" src="script/manageOutStorage/detailOutStorageSheet.js"></script>
	<script type="text/javascript" src="script/manageOutStorage/manageOutStorageSheet.js"></script>
	
	<script type="text/javascript" src="script/manageTransferStorage/queryTransferGoods.js"></script>
	<script type="text/javascript" src="script/manageTransferStorage/createTransferStorageSheet.js"></script>
	<script type="text/javascript" src="script/manageTransferStorage/editTransferStorageSheet.js"></script>
	<script type="text/javascript" src="script/manageTransferStorage/detailTransferStorageSheet.js"></script>
	<script type="text/javascript" src="script/manageTransferStorage/manageTransferStorageSheet.js"></script>
	
	<script type="text/javascript" src="script/queryStorage/queryStorage.js"></script>
	
	<script type="text/javascript" src="script/manageStocktake/queryStocktakeGoods.js"></script>
	<script type="text/javascript" src="script/manageStocktake/createStocktake.js"></script>
	<script type="text/javascript" src="script/manageStocktake/editStocktake.js"></script>
	<script type="text/javascript" src="script/manageStocktake/detailStocktake.js"></script>
	<script type="text/javascript" src="script/manageStocktake/manageStocktake.js"></script>
	
	<script type="text/javascript" src="script/manageFinance/editTransIncome.js"></script>
	<script type="text/javascript" src="script/manageFinance/editTransPay.js"></script>
	<script type="text/javascript" src="script/manageFinance/editEntruckingPay.js"></script>
	<script type="text/javascript" src="script/manageFinance/editEnterStorageFee.js"></script>
	<script type="text/javascript" src="script/manageFinance/editOutStorageFee.js"></script>
	<script type="text/javascript" src="script/manageFinance/editTransStorageFee.js"></script>
	<script type="text/javascript" src="script/manageFinance/editSpecialStorageFee.js"></script>
	<script type="text/javascript" src="script/manageFinance/createSpecialStorageSheet.js"></script>
	<script type="text/javascript" src="script/manageFinance/createStorageSheet.js"></script>
	<script type="text/javascript" src="script/manageFinance/editStorageFee.js"></script>
	<script type="text/javascript" src="script/manageFinance/editTrackCOD.js"></script>
	<script type="text/javascript" src="script/manageFinance/detailFinanceLog.js"></script>
	
	<script type="text/javascript" src="script/manageFinance/transportationIncome.js"></script>
	<script type="text/javascript" src="script/manageFinance/transportationPay.js"></script>
	<script type="text/javascript" src="script/manageFinance/entruckingPay.js"></script>
	<script type="text/javascript" src="script/manageFinance/enterStorageFinance.js"></script>
	<script type="text/javascript" src="script/manageFinance/outStorageFinance.js"></script>
	<script type="text/javascript" src="script/manageFinance/transferStorageFinance.js"></script>
	<script type="text/javascript" src="script/manageFinance/storageFinance.js"></script>
	<script type="text/javascript" src="script/manageFinance/specialStorageIncome.js"></script>
	<script type="text/javascript" src="script/manageFinance/trackCOD.js"></script>
	<script type="text/javascript" src="script/manageFinance/financeLog.js"></script>
	
	<script type="text/javascript" src="script/basicData/detailCustomerInformation.js"></script>
	<script type="text/javascript" src="script/basicData/detailCarInformation.js"></script>
	<script type="text/javascript" src="script/basicData/detailTransportationUnit.js"></script>
	
	<script type="text/javascript" src="script/basicData/feeUnitWay.js"></script>
	<script type="text/javascript" src="script/basicData/feeUnitCar.js"></script>
	<script type="text/javascript" src="script/basicData/feeCustomerWay.js"></script>
	<script type="text/javascript" src="script/basicData/createCarType.js"></script>
	<script type="text/javascript" src="script/basicData/editCarType.js"></script>
	<script type="text/javascript" src="script/basicData/carType.js"></script>
	<script type="text/javascript" src="script/basicData/createCostCenter.js"></script>
	<script type="text/javascript" src="script/basicData/costCenter.js"></script>
	<script type="text/javascript" src="script/basicData/createSellCenter.js"></script>
	<script type="text/javascript" src="script/basicData/sellCenter.js"></script>
	<script type="text/javascript" src="script/basicData/createCarInformation.js"></script>
	<script type="text/javascript" src="script/basicData/editCarInformation.js"></script>
	<script type="text/javascript" src="script/basicData/carInformation.js"></script>
	<script type="text/javascript" src="script/basicData/createCustomerInformation.js"></script>
	<script type="text/javascript" src="script/basicData/editCustomerInformation.js"></script>
	<script type="text/javascript" src="script/basicData/customerInformation.js"></script>
	<script type="text/javascript" src="script/basicData/createGoodsInformation.js"></script>
	<script type="text/javascript" src="script/basicData/editGoodsInformation.js"></script>
	<script type="text/javascript" src="script/basicData/goodsInformation.js"></script>
	<script type="text/javascript" src="script/basicData/createStorageInformation.js"></script>
	<script type="text/javascript" src="script/basicData/editStorageInformation.js"></script>
	<script type="text/javascript" src="script/basicData/storageInformation.js"></script>
	<script type="text/javascript" src="script/basicData/createTransportationUnit.js"></script>
	<script type="text/javascript" src="script/basicData/editTransportationUnit.js"></script>
	<script type="text/javascript" src="script/basicData/transportationUnit.js"></script>
	<script type="text/javascript" src="script/basicData/createTransportationWay.js"></script>
	<script type="text/javascript" src="script/basicData/editTransportationWay.js"></script>
	<script type="text/javascript" src="script/basicData/transportationWay.js"></script>
	<script type="text/javascript" src="script/basicData/createCityInformation.js"></script>
	<script type="text/javascript" src="script/basicData/editCityInformation.js"></script>
	<script type="text/javascript" src="script/basicData/cityInformation.js"></script>
	
	<script type="text/javascript" src="script/manageWorker/editWorkerInfo.js"></script>
	<script type="text/javascript" src="script/manageWorker/detailWorkerInfo.js"></script>
	<script type="text/javascript" src="script/manageWorker/createWorkerInfo.js"></script>
	<script type="text/javascript" src="script/manageWorker/manageWorkerInfo.js"></script>
	
	<script type="text/javascript" src="script/manageSalary/createWorkerSalary.js"></script>
	<script type="text/javascript" src="script/manageSalary/editWorkerSalary.js"></script>
	<script type="text/javascript" src="script/manageSalary/detailWorkerSalary.js"></script>
	<script type="text/javascript" src="script/manageSalary/manageWorkerSalary.js"></script>
	
	<script type="text/javascript" src="script/personalInformation/editPersonalPassword.js"></script>
	
	<script type="text/javascript" src="script/manageUser/createUser.js"></script>
	<script type="text/javascript" src="script/manageUser/editUser.js"></script>
	<script type="text/javascript" src="script/manageUser/manageUser.js"></script>
	
	<script type="text/javascript" src="script/manageRole/createRole.js"></script>
	<script type="text/javascript" src="script/manageRole/editRole.js"></script>
	<script type="text/javascript" src="script/manageRole/manageRole.js"></script>
	
	<script type="text/javascript" src="script/systemInformation/systemInformation.js"></script>

  </head>
  
  <body><br>
    <div id="firstPageNews" class="x-hide-display">
        <table>
			<tbody>
			    <tr height='300'>
			        <td width='350' valign='top'>
			        	<div id='div_xwxx'></div>
			        </td>
			        <td width='350' valign='top'>
			        	<div id='div_hydt'></div>
			        </td>
					<td width='350' valign='top'>
						<div id='div_ggtz'></div>
			        </td>
			    </tr>
			    <tr>
			        <td width='350' valign='top'>
			        	<div id='div_zsgl'></div>
			        </td>
			        <td width='350' valign='top'>
			        	<div id='div_whtd'></div>
			        </td>
					<td width='350' valign='top'>
						<div id='div_ywdt'></div>
			        </td>
			    </tr>
			</tbody>
		</table>
    </div>
  </body>
</html>
