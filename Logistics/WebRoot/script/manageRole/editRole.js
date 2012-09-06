
var _win_edit_MR = null;
var _mask_edit_MR = null;
function onEditRole(roleId)
{
	if(null == _win_edit_MR)
	{
	    createWindow_edit_MR();
	    RESET_TREE(_treepanelPrivilege_edit,true);
	}else
	{
		RESET_TREE(_treepanelPrivilege_edit,false);
		_formPanel_editRole_MR.getForm().reset();
	}
	_mask_edit_MR.show();
	_formPanel_editRole_MR.getForm().load({
    	url: 'Role.queryOne.action',
    	method: 'POST',
    	params: {'role.roleID': roleId},
    	success: function(form,action){
    		if(action.result.resultMapList != null){
	    		SetTreeCheck_edit(action.result.resultMapList);
    		}
			_mask_edit_MR.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'角色数据读取失败');
    		_mask_edit_MR.hide();
    	}
    });
	_win_edit_MR.setPagePosition(GET_WIN_X(_win_edit_MR.width),GET_WIN_Y());
    _win_edit_MR.show();
}

function createWindow_edit_MR()
{
	_win_edit_MR = new Ext.Window({
        title: '修改角色',
        iconCls: 'editRole',
        width: 540,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_editRole_MR,
        listeners: LISTENER_WIN_MOVE
    });
     _win_edit_MR.show();
    _mask_edit_MR = new Ext.LoadMask(_win_edit_MR.body, {msg:"正在载入,请稍后..."});
}

var _treepanelPrivilege_edit = new Ext.tree.TreePanel({
							
		rootVisible : true,
		border : true,
		animate : true,
		style: 'margin:5px 5px 5px 5px',
		fieldLabel: '权限选择',
		autoScroll : true,
		height: 250,
		root : new Ext.tree.AsyncTreeNode
		({			
			expanded : true,
			text : '&nbsp;&nbsp;',
			iconCls:'navigationMenu',
			leaf: false,
			children : [
			{
				id : 'edit_MessageAction.query',
				text : '&nbsp;&nbsp;信息管理',
				leaf : false,
				checked: false,
				iconCls: 'manageNews',
				children:[
				{
					id : 'edit_MessageAction.insert',
					text : '&nbsp;&nbsp;新建',
					leaf : true,
					checked: false,
					iconCls: 'commonAdd'
				},{
					id : 'edit_MessageAction.update',
					text : '&nbsp;&nbsp;修改',
					leaf : true,
					checked: false,
					iconCls: 'commonEdit'
				},{
					id : 'edit_MessageAction.delete',
					text : '&nbsp;&nbsp;删除',
					leaf : true,
					checked: false,
					iconCls: 'commonDelete'
				},{
					id : 'edit_MessageAction.download',
					text : '&nbsp;&nbsp;附件下载',
					leaf : true,
					checked: false,
					iconCls: 'attachment'
				}
				]
			},{
				id : 'edit_manageTransportation.empty',
				text : '&nbsp;&nbsp;运输管理',
				leaf : false,
				checked: false,
				iconCls: 'manageTransportation',
				children:[
				{
					id : 'edit_FreightManifestAction.query',
					text : '&nbsp;&nbsp;运输单管理',
					leaf : false,
					checked: false,
					iconCls: 'manageTransportationSheet',
					children:[
					{
						id : 'edit_FreightManifestAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'edit_FreightManifestAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'edit_FreightManifestAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					},{
						id : 'edit_FreightManifestAction.print',
						text : '&nbsp;&nbsp;打印',
						leaf : true,
						checked: false,
						iconCls: 'commonPrint'
					}
					]
				},{
					id : 'edit_ShipmentManifestAction.query',
					text : '&nbsp;&nbsp;装车单管理',
					leaf : false,
					checked: false,
					iconCls: 'manageEntruckingSheet',
					children:[
					{
						id : 'edit_ShipmentManifestAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'edit_ShipmentManifestAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'edit_ShipmentManifestAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					},{
						id : 'edit_ShipmentManifestAction.print',
						text : '&nbsp;&nbsp;打印',
						leaf : true,
						checked: false,
						iconCls: 'commonPrint'
					}
					]
				},{
					id : 'edit_FreightStateAction.query',
					text : '&nbsp;&nbsp;运输单跟踪',
					leaf : false,
					checked: false,
					iconCls: 'transportationTrack',
					children:[
					{
						id : 'edit_FreightStateAction.update',
						text : '&nbsp;&nbsp;状态流转',
						leaf : true,
						checked: false,
						iconCls: 'flow'
					}
					]
				}
				]
			},{
				id : 'edit_manageStorage.empty',
				text : '&nbsp;&nbsp;仓储管理',
				leaf : false,
				checked: false,
				iconCls: 'manageStorage',
				children:[
				{
					id : 'edit_StockinManifestAction.query',
					text : '&nbsp;&nbsp;入库单管理',
					leaf : false,
					checked: false,
					iconCls: 'manageEnterStorageSheet',
					children:[
					{
						id : 'edit_StockinManifestAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'edit_StockinManifestAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'edit_StockinManifestAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					},{
						id : 'edit_StockinManifestAction.print',
						text : '&nbsp;&nbsp;打印',
						leaf : true,
						checked: false,
						iconCls: 'commonPrint'
					},{
						id : 'edit_StockinManifestAction.audit',
						text : '&nbsp;&nbsp;审核',
						leaf : true,
						checked: false,
						iconCls: 'commonCheck'
					},{
						id : 'edit_StockinManifestAction.approve',
						text : '&nbsp;&nbsp;批准',
						leaf : true,
						checked: false,
						iconCls: 'commonAllow'
					}
					]
				},{
					id : 'edit_StockoutManifestAction.query',
					text : '&nbsp;&nbsp;出库单管理',
					leaf : false,
					checked: false,
					iconCls: 'manageOutStorageSheet',
					children:[
					{
						id : 'edit_StockoutManifestAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'edit_StockoutManifestAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'edit_StockoutManifestAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					},{
						id : 'edit_StockoutManifestAction.audit',
						text : '&nbsp;&nbsp;审核',
						leaf : true,
						checked: false,
						iconCls: 'commonCheck'
					},{
						id : 'edit_StockoutManifestAction.approve',
						text : '&nbsp;&nbsp;批准',
						leaf : true,
						checked: false,
						iconCls: 'commonAllow'
					}
					]
				},{
					id : 'edit_StockTransferManifestAction.query',
					text : '&nbsp;&nbsp;移库单管理',
					leaf : false,
					checked: false,
					iconCls: 'manageTransferStorageSheet',
					children:[
					{
						id : 'edit_StockTransferManifestAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'edit_StockTransferManifestAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'edit_StockTransferManifestAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					},{
						id : 'edit_StockTransferManifestAction.audit',
						text : '&nbsp;&nbsp;审核',
						leaf : true,
						checked: false,
						iconCls: 'commonCheck'
					},{
						id : 'edit_StockTransferManifestAction.approve',
						text : '&nbsp;&nbsp;批准',
						leaf : true,
						checked: false,
						iconCls: 'commonAllow'
					}
					]
				},{
					id : 'edit_StockItemAction.query',
					text : '&nbsp;&nbsp;库存查询',
					leaf : true,
					checked: false,
					iconCls: 'queryStorage'
				},{
					id : 'edit_InventoryManifestAction.query',
					text : '&nbsp;&nbsp;盘点管理',
					leaf : false,
					checked: false,
					iconCls: 'manageStocktake',
					children:[
					{
						id : 'edit_InventoryManifestAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'edit_InventoryManifestAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'edit_InventoryManifestAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'edit_BarcodeAction.query',
					text : '&nbsp;&nbsp;条码管理',
					leaf : false,
					checked: false,
					iconCls: 'manageBarCode',
					children:[
					{
						id : 'edit_BarcodeAction.importFile',
						text : '&nbsp;&nbsp;条码导入',
						leaf : true,
						checked: false,
						iconCls: 'commonImport'
					},{
						id : 'edit_BarcodeAction.export',
						text : '&nbsp;&nbsp;条码导出',
						leaf : true,
						checked: false,
						iconCls: 'commonExcel'
					},{
						id : 'edit_BarcodeAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'edit_BarcodeAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				}
				]
			},{
				id : 'edit_manageFinance.empty',
				text : '&nbsp;&nbsp;业务收支',
				leaf : false,
				checked: false,
				iconCls: 'manageFinance',
				children:[
				{
					id : 'edit_FreightIncomeAction.query',
					text : '&nbsp;&nbsp;运输单收入',
					leaf : false,
					checked: false,
					iconCls: 'transportationIncome',
					children:[
					{
						id : 'edit_FreightIncomeAction.update',
						text : '&nbsp;&nbsp;财务维护',
						leaf : true,
						checked: false,
						iconCls: 'commonSaveAll'
					},{
						id : 'edit_FreightIncomeAction.archive',
						text : '&nbsp;&nbsp;归档',
						leaf : true,
						checked: false,
						iconCls: 'filing'
					},{
						id : 'edit_FreightIncomeAction.unarchive',
						text : '&nbsp;&nbsp;取消归档',
						leaf : true,
						checked: false,
						iconCls: 'unfiling'
					},{
						id : 'edit_FreightIncomeAction.export',
						text : '&nbsp;&nbsp;导出数据',
						leaf : true,
						checked: false,
						iconCls: 'commonExport'
					}
					]
				},{
					id : 'edit_FreightCostAction.query',
					text : '&nbsp;&nbsp;运输单支出',
					leaf : false,
					checked: false,
					iconCls: 'transportationPay',
					children:[
					{
						id : 'edit_FreightCostAction.update',
						text : '&nbsp;&nbsp;财务维护',
						leaf : true,
						checked: false,
						iconCls: 'commonSaveAll'
					},{
						id : 'edit_FreightCostAction.archive',
						text : '&nbsp;&nbsp;归档',
						leaf : true,
						checked: false,
						iconCls: 'filing'
					},{
						id : 'edit_FreightCostAction.unarchive',
						text : '&nbsp;&nbsp;取消归档',
						leaf : true,
						checked: false,
						iconCls: 'unfiling'
					},{
						id : 'edit_FreightCostAction.export',
						text : '&nbsp;&nbsp;导出数据',
						leaf : true,
						checked: false,
						iconCls: 'commonExport'
					}
					]
				},{
					id : 'edit_ShipmentCostAction.query',
					text : '&nbsp;&nbsp;装车单支出',
					leaf : false,
					checked: false,
					iconCls: 'entruckingPay',
					children:[
					{
						id : 'edit_ShipmentCostAction.update',
						text : '&nbsp;&nbsp;财务维护',
						leaf : true,
						checked: false,
						iconCls: 'commonSaveAll'
					},{
						id : 'edit_ShipmentCostAction.archive',
						text : '&nbsp;&nbsp;归档',
						leaf : true,
						checked: false,
						iconCls: 'filing'
					},{
						id : 'edit_ShipmentCostAction.unarchive',
						text : '&nbsp;&nbsp;取消归档',
						leaf : true,
						checked: false,
						iconCls: 'unfiling'
					},{
						id : 'edit_ShipmentCostAction.export',
						text : '&nbsp;&nbsp;导出数据',
						leaf : true,
						checked: false,
						iconCls: 'commonExport'
					}
					]
				},{
					id : 'edit_StockinFinanceAction.query',
					text : '&nbsp;&nbsp;入库收支',
					leaf : false,
					checked: false,
					iconCls: 'enterStorageFinance',
					children:[
					{
						id : 'edit_StockinFinanceAction.update',
						text : '&nbsp;&nbsp;财务维护',
						leaf : true,
						checked: false,
						iconCls: 'commonSaveAll'
					},{
						id : 'edit_StockinFinanceAction.archive',
						text : '&nbsp;&nbsp;归档',
						leaf : true,
						checked: false,
						iconCls: 'filing'
					},{
						id : 'edit_StockinFinanceAction.unarchive',
						text : '&nbsp;&nbsp;取消归档',
						leaf : true,
						checked: false,
						iconCls: 'unfiling'
					},{
						id : 'edit_StockinFinanceAction.export',
						text : '&nbsp;&nbsp;导出数据',
						leaf : true,
						checked: false,
						iconCls: 'commonExport'
					}
					]
				},{
					id : 'edit_StockoutFinanceAction.query',
					text : '&nbsp;&nbsp;出库收支',
					leaf : false,
					checked: false,
					iconCls: 'outStorageFinance',
					children:[
					{
						id : 'edit_StockoutFinanceAction.update',
						text : '&nbsp;&nbsp;财务维护',
						leaf : true,
						checked: false,
						iconCls: 'commonSaveAll'
					},{
						id : 'edit_StockoutFinanceAction.archive',
						text : '&nbsp;&nbsp;归档',
						leaf : true,
						checked: false,
						iconCls: 'filing'
					},{
						id : 'edit_StockoutFinanceAction.unarchive',
						text : '&nbsp;&nbsp;取消归档',
						leaf : true,
						checked: false,
						iconCls: 'unfiling'
					},{
						id : 'edit_StockoutFinanceAction.export',
						text : '&nbsp;&nbsp;导出数据',
						leaf : true,
						checked: false,
						iconCls: 'commonExport'
					}
					]
				},{
					id : 'edit_StockTransferFinanceAction.query',
					text : '&nbsp;&nbsp;移库收支',
					leaf : false,
					checked: false,
					iconCls: 'transferStorageFinance',
					children:[
					{
						id : 'edit_StockTransferFinanceAction.update',
						text : '&nbsp;&nbsp;财务维护',
						leaf : true,
						checked: false,
						iconCls: 'commonSaveAll'
					},{
						id : 'edit_StockTransferFinanceAction.archive',
						text : '&nbsp;&nbsp;归档',
						leaf : true,
						checked: false,
						iconCls: 'filing'
					},{
						id : 'edit_StockTransferFinanceAction.unarchive',
						text : '&nbsp;&nbsp;取消归档',
						leaf : true,
						checked: false,
						iconCls: 'unfiling'
					},{
						id : 'edit_StockTransferFinanceAction.export',
						text : '&nbsp;&nbsp;导出数据',
						leaf : true,
						checked: false,
						iconCls: 'commonExport'
					}
					]
				},{
					id : 'edit_StockFinanceAction.query',
					text : '&nbsp;&nbsp;仓储收支',
					leaf : false,
					checked: false,
					iconCls: 'storageFinance',
					children:[
					{
						id : 'edit_StockItemAction.account',
						text : '&nbsp;&nbsp;新建结算',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},
					{
						id : 'edit_StockFinanceAction.update',
						text : '&nbsp;&nbsp;财务维护',
						leaf : true,
						checked: false,
						iconCls: 'commonSaveAll'
					},{
						id : 'edit_StockFinanceAction.archive',
						text : '&nbsp;&nbsp;归档',
						leaf : true,
						checked: false,
						iconCls: 'filing'
					},{
						id : 'edit_StockFinanceAction.unarchive',
						text : '&nbsp;&nbsp;取消归档',
						leaf : true,
						checked: false,
						iconCls: 'unfiling'
					},{
						id : 'edit_StockFinanceAction.export',
						text : '&nbsp;&nbsp;导出数据',
						leaf : true,
						checked: false,
						iconCls: 'commonExport'
					}
					]
				},{
					id : 'edit_SpecialStockIncomeAction.query',
					text : '&nbsp;&nbsp;特殊仓储收支',
					leaf : false,
					checked: false,
					iconCls: 'specialStorageIncome',
					children:[
					{
						id : 'edit_SpecialStockIncomeAction.insert',
						text : '&nbsp;&nbsp;新建特殊收支',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},
					{
						id : 'edit_SpecialStockIncomeAction.update',
						text : '&nbsp;&nbsp;财务维护',
						leaf : true,
						checked: false,
						iconCls: 'commonSaveAll'
					},{
						id : 'edit_SpecialStockIncomeAction.archive',
						text : '&nbsp;&nbsp;归档',
						leaf : true,
						checked: false,
						iconCls: 'filing'
					},{
						id : 'edit_SpecialStockIncomeAction.unarchive',
						text : '&nbsp;&nbsp;取消归档',
						leaf : true,
						checked: false,
						iconCls: 'unfiling'
					},{
						id : 'edit_SpecialStockIncomeAction.export',
						text : '&nbsp;&nbsp;导出数据',
						leaf : true,
						checked: false,
						iconCls: 'commonExport'
					}
					]
				},{
					id : 'edit_PaymentCollectionAction.query',
					text : '&nbsp;&nbsp;代收货款',
					leaf : false,
					checked: false,
					iconCls: 'trackCOD',
					children:[
					{
						id : 'edit_PaymentCollectionAction.updateState',
						text : '&nbsp;&nbsp;状态流转',
						leaf : true,
						checked: false,
						iconCls: 'flow'
					},
					{
						id : 'edit_PaymentCollectionAction.update',
						text : '&nbsp;&nbsp;财务维护',
						leaf : true,
						checked: false,
						iconCls: 'commonSaveAll'
					},{
						id : 'edit_PaymentCollectionAction.archive',
						text : '&nbsp;&nbsp;归档',
						leaf : true,
						checked: false,
						iconCls: 'filing'
					},{
						id : 'edit_PaymentCollectionAction.unarchive',
						text : '&nbsp;&nbsp;取消归档',
						leaf : true,
						checked: false,
						iconCls: 'unfiling'
					},{
						id : 'edit_PaymentCollectionAction.export',
						text : '&nbsp;&nbsp;导出数据',
						leaf : true,
						checked: false,
						iconCls: 'commonExport'
					}
					]
				},{
					id : 'edit_FinancialLogAction.query',
					text : '&nbsp;&nbsp;财务日志',
					leaf : false,
					checked: false,
					iconCls: 'financeLog',
					children:[
					{
						id : 'edit_FinancialLogAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				}
				]
			},{
				id : 'edit_basicData.empty',
				text : '&nbsp;&nbsp;基础数据',
				leaf : false,
				checked: false,
				iconCls: 'basicData',
				children:[
				{
					id : 'edit_FreightRouteAction.query',
					text : '&nbsp;&nbsp;运输路线信息',
					leaf : false,
					checked: false,
					iconCls: 'transportationWay',
					children:[
					{
						id : 'edit_FreightRouteAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'edit_FreightRouteAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'edit_FreightRouteAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'edit_FreightContractorAction.query',
					text : '&nbsp;&nbsp;承运单位信息',
					leaf : false,
					checked: false,
					iconCls: 'transportationUnit',
					children:[
					{
						id : 'edit_FreightContractorAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'edit_FreightContractorAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'edit_FreightContractorAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'edit_CarAction.query',
					text : '&nbsp;&nbsp;车辆信息',
					leaf : false,
					checked: false,
					iconCls: 'carInformation',
					children:[
					{
						id : 'edit_CarAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'edit_CarAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'edit_CarAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'edit_CarTypeAction.query',
					text : '&nbsp;&nbsp;车辆类型',
					leaf : false,
					checked: false,
					iconCls: 'carType',
					children:[
					{
						id : 'edit_CarTypeAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'edit_CarTypeAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'edit_CarTypeAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'edit_ItemAction.query',
					text : '&nbsp;&nbsp;物料信息',
					leaf : false,
					checked: false,
					iconCls: 'goodsInformation',
					children:[
					{
						id : 'edit_ItemAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'edit_ItemAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'edit_ItemAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					},{
						id : 'create_ItemAction.importFile',
						text : '&nbsp;&nbsp;数据导入',
						leaf : true,
						checked: false,
						iconCls: 'commonImport'
					}
					]
				},{
					id : 'edit_CustomerAction.query',
					text : '&nbsp;&nbsp;客户信息',
					leaf : false,
					checked: false,
					iconCls: 'customerInformation',
					children:[
					{
						id : 'edit_CustomerAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'edit_CustomerAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'edit_CustomerAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'edit_CostCenterAction.query',
					text : '&nbsp;&nbsp;成本中心',
					leaf : false,
					checked: false,
					iconCls: 'costCenter',
					children:[
					{
						id : 'edit_CostCenterAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'edit_CostCenterAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'edit_SellCenterAction.query',
					text : '&nbsp;&nbsp;销售中心',
					leaf : false,
					checked: false,
					iconCls: 'sellCenter',
					children:[
					{
						id : 'edit_SellCenterAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'edit_SellCenterAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'edit_WarehouseAction.query',
					text : '&nbsp;&nbsp;仓库信息',
					leaf : false,
					checked: false,
					iconCls: 'manageStorage',
					children:[
					{
						id : 'edit_WarehouseAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'edit_WarehouseAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'edit_WarehouseAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'edit_CityAction.query',
					text : '&nbsp;&nbsp;城市信息',
					leaf : false,
					checked: false,
					iconCls: 'cityInformation',
					children:[
					{
						id : 'edit_CityAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'edit_CityAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'edit_CityAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				}
				]
			},{
				id : 'edit_manageWorker.empty',
				text : '&nbsp;&nbsp;人事管理',
				leaf : false,
				checked: false,
				iconCls: 'manageWorker',
				children:[
				{
					id : 'edit_EmployeeProfileAction.query',
					text : '&nbsp;&nbsp;员工信息',
					leaf : false,
					checked: false,
					iconCls: 'personalInformation',
					children:[
					{
						id : 'edit_EmployeeProfileAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'edit_EmployeeProfileAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'edit_EmployeeProfileAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'edit_EmployeeSalaryAction.query',
					text : '&nbsp;&nbsp;工资管理',
					leaf : false,
					checked: false,
					iconCls: 'manageSalary',
					children:[
					{
						id : 'edit_EmployeeSalaryAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'edit_EmployeeSalaryAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'edit_EmployeeSalaryAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				}
				]
			},{
				id : 'edit_manageSystem.empty',
				text : '&nbsp;&nbsp;系统管理',
				leaf : false,
				checked: false,
				iconCls: 'manageSystem',
				children:[
				{
					id : 'edit_UserAction.query',
					text : '&nbsp;&nbsp;用户管理',
					leaf : false,
					checked: false,
					iconCls: 'manageUser',
					children:[
					{
						id : 'edit_UserAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'edit_UserAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'edit_UserAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'edit_RoleAction.query',
					text : '&nbsp;&nbsp;角色管理',
					leaf : false,
					checked: false,
					iconCls: 'manageRole',
					children:[
					{
						id : 'edit_RoleAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'edit_RoleAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'edit_RoleAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'edit_SystemInfoAction.query',
					text : '&nbsp;&nbsp;系统信息',
					leaf : true,
					checked: false,
					iconCls: 'systemInformation'
				}
				]
			}]//node
		}),
		listeners: {
			checkchange:TREE_CHECKCHANGE
		}
		
});//tree

function GetParamsFromTree_edit()
{
	var params = {};
	var nodes = _treepanelPrivilege_edit.getChecked();
	var pl = GET_P_L();
	for(var i=0;i<nodes.length;i++)
	{
		var id=nodes[i].id;
		var tmp = id.substring(id.indexOf('_')+1);
		var key = tmp.substring(0,tmp.indexOf('.'));
		var type = tmp.substring(tmp.indexOf('.')+1);
		if(type=='empty') continue;
		pl[key].permission |= P_F[type];
	}
	for(var i=0;i<P_K.length;i++)
	{
		params['permissions['+i.toString()+'].actionName']=pl[P_K[i]]['actionName'];
		params['permissions['+i.toString()+'].permission']=pl[P_K[i]]['permission'];
	}
	params['role.roleName']=_formPanel_editRole_MR.findById('edit_roleName_MR').getValue();
	params['role.roleID']=_formPanel_editRole_MR.findById('edit_roleID_MR').getValue();
	params['role.remarks']=_formPanel_editRole_MR.findById('edit_remarks_MR').getValue();
	return params;
}

function SetTreeCheck_edit(plist)
{
	var pl = GET_P_L();
	for(var i=0; i<plist.length; i++)
	{
		var tmp = plist[i].actionName.substring(plist[i].actionName.indexOf('.')+1);
		var key = tmp.substring(tmp.indexOf('.')+1);
		pl[key].permission = plist[i].permission;
	}
	TREE_SETCHECKEDNODES(_treepanelPrivilege_edit.getRootNode().childNodes,pl);
}

var _formPanel_editRole_MR = new Ext.FormPanel({
	
	layout: 'form',
	style: 'margin:0px',
	frame: true,
	labelAlign: 'right',
	bodyStyle: PADDING,
	autoHeight: true,
	autoScoll: true,
	labelWidth: 70,
	border: false,
	buttonAlign: 'center',
	
	buttons:[
	{
		text: '保存',
		iconCls: 'commonSave',
		handler: function(){
			
			if(_formPanel_editRole_MR.getForm().isValid())
			{
				var mask = new Ext.LoadMask(_win_edit_MR.body, {msg:"正在保存,请稍后..."});
				mask.show();
				var params = GetParamsFromTree_edit();
				Ext.Ajax.request({
					url: 'Role.update.action',
					method: 'POST',
					params:params,
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							AJAX_FAILURE_CALLBACK(result,'新建角色失败');
						}else{
							_grid_MR.getStore().reload();
						}
						mask.hide();
					},
					failure: function(response,options){
						AJAX_SERVER_FAILURE_CALLBACK(response,options,'新建角色失败');
						mask.hide();
					}
				});//Ajax
			}
		}
	},{
		text: '取消',
		iconCls: 'commonCancel',
		handler: function(){ _win_edit_MR.hide(); }
	}],
	
	items:[
		{//Row 1
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '角色名称',
						allowBlank:false,
						id:'edit_roleName_MR',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						name: 'role.roleName',
						width: TEXTFIELDWIDTH
					},{
						xtype: 'textfield',
						id:'edit_roleID_MR',
						hidden:true,
						name: 'role.roleID'
					}]
				}]
			},
			{//Row 2
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '权限描述',
						id:'edit_remarks_MR',
						name: 'role.remarks',
						allowBlank: true,
						regex: REGEX_COMMON_M,
						regexText: REGEX_COMMON_M_TEXT,
						width:　370
					}]
				}]
			},
			{//Row 3
				layout: 'column',
				border: false,
				items: [
					{//Column 1
						columnWidth: '1',
						layout: 'form',
						border: false,
						items: _treepanelPrivilege_edit
					}]//column
			}]//row
});
