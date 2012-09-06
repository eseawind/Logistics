
var _win_create_MR = null;

function onCreateRole()
{
	if(null == _win_create_MR)
	{
	    createWindow_create_MR();
	}else{
		RESET_TREE(_treepanelPrivilege_create,false);
		_formPanel_createRole_MR.getForm().reset();
	}
	_win_create_MR.setPagePosition(GET_WIN_X(_win_create_MR.width),GET_WIN_Y());
    _win_create_MR.show();
}

function createWindow_create_MR()
{
	_win_create_MR = new Ext.Window({
        title: '新建角色',
        iconCls: 'addRole',
        width: 540,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_createRole_MR,
        listeners: LISTENER_WIN_MOVE
    });
}

var _treepanelPrivilege_create = new Ext.tree.TreePanel({
							
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
				id : 'create_MessageAction.query',
				text : '&nbsp;&nbsp;信息管理',
				leaf : false,
				checked: false,
				iconCls: 'manageNews',
				children:[
				{
					id : 'create_MessageAction.insert',
					text : '&nbsp;&nbsp;新建',
					leaf : true,
					checked: false,
					iconCls: 'commonAdd'
				},{
					id : 'create_MessageAction.update',
					text : '&nbsp;&nbsp;修改',
					leaf : true,
					checked: false,
					iconCls: 'commonEdit'
				},{
					id : 'create_MessageAction.delete',
					text : '&nbsp;&nbsp;删除',
					leaf : true,
					checked: false,
					iconCls: 'commonDelete'
				},{
					id : 'create_MessageAction.download',
					text : '&nbsp;&nbsp;附件下载',
					leaf : true,
					checked: false,
					iconCls: 'attachment'
				}
				]
			},{
				id : 'create_manageTransportation.empty',
				text : '&nbsp;&nbsp;运输管理',
				leaf : false,
				checked: false,
				iconCls: 'manageTransportation',
				children:[
				{
					id : 'create_FreightManifestAction.query',
					text : '&nbsp;&nbsp;运输单管理',
					leaf : false,
					checked: false,
					iconCls: 'manageTransportationSheet',
					children:[
					{
						id : 'create_FreightManifestAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'create_FreightManifestAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'create_FreightManifestAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					},{
						id : 'create_FreightManifestAction.print',
						text : '&nbsp;&nbsp;打印',
						leaf : true,
						checked: false,
						iconCls: 'commonPrint'
					}
					]
				},{
					id : 'create_ShipmentManifestAction.query',
					text : '&nbsp;&nbsp;装车单管理',
					leaf : false,
					checked: false,
					iconCls: 'manageEntruckingSheet',
					children:[
					{
						id : 'create_ShipmentManifestAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'create_ShipmentManifestAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'create_ShipmentManifestAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					},{
						id : 'create_ShipmentManifestAction.print',
						text : '&nbsp;&nbsp;打印',
						leaf : true,
						checked: false,
						iconCls: 'commonPrint'
					}
					]
				},{
					id : 'create_FreightStateAction.query',
					text : '&nbsp;&nbsp;运输单跟踪',
					leaf : false,
					checked: false,
					iconCls: 'transportationTrack',
					children:[
					{
						id : 'create_FreightStateAction.update',
						text : '&nbsp;&nbsp;状态流转',
						leaf : true,
						checked: false,
						iconCls: 'flow'
					}
					]
				}
				]
			},{
				id : 'create_manageStorage.empty',
				text : '&nbsp;&nbsp;仓储管理',
				leaf : false,
				checked: false,
				iconCls: 'manageStorage',
				children:[
				{
					id : 'create_StockinManifestAction.query',
					text : '&nbsp;&nbsp;入库单管理',
					leaf : false,
					checked: false,
					iconCls: 'manageEnterStorageSheet',
					children:[
					{
						id : 'create_StockinManifestAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'create_StockinManifestAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'create_StockinManifestAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					},{
						id : 'create_StockinManifestAction.print',
						text : '&nbsp;&nbsp;打印',
						leaf : true,
						checked: false,
						iconCls: 'commonPrint'
					},{
						id : 'create_StockinManifestAction.audit',
						text : '&nbsp;&nbsp;审核',
						leaf : true,
						checked: false,
						iconCls: 'commonCheck'
					},{
						id : 'create_StockinManifestAction.approve',
						text : '&nbsp;&nbsp;批准',
						leaf : true,
						checked: false,
						iconCls: 'commonAllow'
					}
					]
				},{
					id : 'create_StockoutManifestAction.query',
					text : '&nbsp;&nbsp;出库单管理',
					leaf : false,
					checked: false,
					iconCls: 'manageOutStorageSheet',
					children:[
					{
						id : 'create_StockoutManifestAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'create_StockoutManifestAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'create_StockoutManifestAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					},{
						id : 'create_StockoutManifestAction.audit',
						text : '&nbsp;&nbsp;审核',
						leaf : true,
						checked: false,
						iconCls: 'commonCheck'
					},{
						id : 'create_StockoutManifestAction.approve',
						text : '&nbsp;&nbsp;批准',
						leaf : true,
						checked: false,
						iconCls: 'commonAllow'
					}
					]
				},{
					id : 'create_StockTransferManifestAction.query',
					text : '&nbsp;&nbsp;移库单管理',
					leaf : false,
					checked: false,
					iconCls: 'manageTransferStorageSheet',
					children:[
					{
						id : 'create_StockTransferManifestAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'create_StockTransferManifestAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'create_StockTransferManifestAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					},{
						id : 'create_StockTransferManifestAction.audit',
						text : '&nbsp;&nbsp;审核',
						leaf : true,
						checked: false,
						iconCls: 'commonCheck'
					},{
						id : 'create_StockTransferManifestAction.approve',
						text : '&nbsp;&nbsp;批准',
						leaf : true,
						checked: false,
						iconCls: 'commonAllow'
					}
					]
				},{
					id : 'create_StockItemAction.query',
					text : '&nbsp;&nbsp;库存查询',
					leaf : true,
					checked: false,
					iconCls: 'queryStorage'
				},{
					id : 'create_InventoryManifestAction.query',
					text : '&nbsp;&nbsp;盘点管理',
					leaf : false,
					checked: false,
					iconCls: 'manageStocktake',
					children:[
					{
						id : 'create_InventoryManifestAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'create_InventoryManifestAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'create_InventoryManifestAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'create_BarcodeAction.query',
					text : '&nbsp;&nbsp;条码管理',
					leaf : false,
					checked: false,
					iconCls: 'manageBarCode',
					children:[
					{
						id : 'create_BarcodeAction.importFile',
						text : '&nbsp;&nbsp;条码导入',
						leaf : true,
						checked: false,
						iconCls: 'commonImport'
					},{
						id : 'create_BarcodeAction.export',
						text : '&nbsp;&nbsp;条码导出',
						leaf : true,
						checked: false,
						iconCls: 'commonExcel'
					},{
						id : 'create_BarcodeAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'create_BarcodeAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				}
				]
			},{
				id : 'create_manageFinance.empty',
				text : '&nbsp;&nbsp;业务收支',
				leaf : false,
				checked: false,
				iconCls: 'manageFinance',
				children:[
				{
					id : 'create_FreightIncomeAction.query',
					text : '&nbsp;&nbsp;运输单收入',
					leaf : false,
					checked: false,
					iconCls: 'transportationIncome',
					children:[
					{
						id : 'create_FreightIncomeAction.update',
						text : '&nbsp;&nbsp;财务维护',
						leaf : true,
						checked: false,
						iconCls: 'commonSaveAll'
					},{
						id : 'create_FreightIncomeAction.archive',
						text : '&nbsp;&nbsp;归档',
						leaf : true,
						checked: false,
						iconCls: 'filing'
					},{
						id : 'create_FreightIncomeAction.unarchive',
						text : '&nbsp;&nbsp;取消归档',
						leaf : true,
						checked: false,
						iconCls: 'unfiling'
					},{
						id : 'create_FreightIncomeAction.export',
						text : '&nbsp;&nbsp;导出数据',
						leaf : true,
						checked: false,
						iconCls: 'commonExport'
					}
					]
				},{
					id : 'create_FreightCostAction.query',
					text : '&nbsp;&nbsp;运输单支出',
					leaf : false,
					checked: false,
					iconCls: 'transportationPay',
					children:[
					{
						id : 'create_FreightCostAction.update',
						text : '&nbsp;&nbsp;财务维护',
						leaf : true,
						checked: false,
						iconCls: 'commonSaveAll'
					},{
						id : 'create_FreightCostAction.archive',
						text : '&nbsp;&nbsp;归档',
						leaf : true,
						checked: false,
						iconCls: 'filing'
					},{
						id : 'create_FreightCostAction.unarchive',
						text : '&nbsp;&nbsp;取消归档',
						leaf : true,
						checked: false,
						iconCls: 'unfiling'
					},{
						id : 'create_FreightCostAction.export',
						text : '&nbsp;&nbsp;导出数据',
						leaf : true,
						checked: false,
						iconCls: 'commonExport'
					}
					]
				},{
					id : 'create_ShipmentCostAction.query',
					text : '&nbsp;&nbsp;装车单支出',
					leaf : false,
					checked: false,
					iconCls: 'entruckingPay',
					children:[
					{
						id : 'create_ShipmentCostAction.update',
						text : '&nbsp;&nbsp;财务维护',
						leaf : true,
						checked: false,
						iconCls: 'commonSaveAll'
					},{
						id : 'create_ShipmentCostAction.archive',
						text : '&nbsp;&nbsp;归档',
						leaf : true,
						checked: false,
						iconCls: 'filing'
					},{
						id : 'create_ShipmentCostAction.unarchive',
						text : '&nbsp;&nbsp;取消归档',
						leaf : true,
						checked: false,
						iconCls: 'unfiling'
					},{
						id : 'create_ShipmentCostAction.export',
						text : '&nbsp;&nbsp;导出数据',
						leaf : true,
						checked: false,
						iconCls: 'commonExport'
					}
					]
				},{
					id : 'create_StockinFinanceAction.query',
					text : '&nbsp;&nbsp;入库收支',
					leaf : false,
					checked: false,
					iconCls: 'enterStorageFinance',
					children:[
					{
						id : 'create_StockinFinanceAction.update',
						text : '&nbsp;&nbsp;财务维护',
						leaf : true,
						checked: false,
						iconCls: 'commonSaveAll'
					},{
						id : 'create_StockinFinanceAction.archive',
						text : '&nbsp;&nbsp;归档',
						leaf : true,
						checked: false,
						iconCls: 'filing'
					},{
						id : 'create_StockinFinanceAction.unarchive',
						text : '&nbsp;&nbsp;取消归档',
						leaf : true,
						checked: false,
						iconCls: 'unfiling'
					},{
						id : 'create_StockinFinanceAction.export',
						text : '&nbsp;&nbsp;导出数据',
						leaf : true,
						checked: false,
						iconCls: 'commonExport'
					}
					]
				},{
					id : 'create_StockoutFinanceAction.query',
					text : '&nbsp;&nbsp;出库收支',
					leaf : false,
					checked: false,
					iconCls: 'outStorageFinance',
					children:[
					{
						id : 'create_StockoutFinanceAction.update',
						text : '&nbsp;&nbsp;财务维护',
						leaf : true,
						checked: false,
						iconCls: 'commonSaveAll'
					},{
						id : 'create_StockoutFinanceAction.archive',
						text : '&nbsp;&nbsp;归档',
						leaf : true,
						checked: false,
						iconCls: 'filing'
					},{
						id : 'create_StockoutFinanceAction.unarchive',
						text : '&nbsp;&nbsp;取消归档',
						leaf : true,
						checked: false,
						iconCls: 'unfiling'
					},{
						id : 'create_StockoutFinanceAction.export',
						text : '&nbsp;&nbsp;导出数据',
						leaf : true,
						checked: false,
						iconCls: 'commonExport'
					}
					]
				},{
					id : 'create_StockTransferFinanceAction.query',
					text : '&nbsp;&nbsp;移库收支',
					leaf : false,
					checked: false,
					iconCls: 'transferStorageFinance',
					children:[
					{
						id : 'create_StockTransferFinanceAction.update',
						text : '&nbsp;&nbsp;财务维护',
						leaf : true,
						checked: false,
						iconCls: 'commonSaveAll'
					},{
						id : 'create_StockTransferFinanceAction.archive',
						text : '&nbsp;&nbsp;归档',
						leaf : true,
						checked: false,
						iconCls: 'filing'
					},{
						id : 'create_StockTransferFinanceAction.unarchive',
						text : '&nbsp;&nbsp;取消归档',
						leaf : true,
						checked: false,
						iconCls: 'unfiling'
					},{
						id : 'create_StockTransferFinanceAction.export',
						text : '&nbsp;&nbsp;导出数据',
						leaf : true,
						checked: false,
						iconCls: 'commonExport'
					}
					]
				},{
					id : 'create_StockFinanceAction.query',
					text : '&nbsp;&nbsp;仓储收支',
					leaf : false,
					checked: false,
					iconCls: 'storageFinance',
					children:[
					{
						id : 'create_StockItemAction.account',
						text : '&nbsp;&nbsp;新建结算',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},
					{
						id : 'create_StockFinanceAction.update',
						text : '&nbsp;&nbsp;财务维护',
						leaf : true,
						checked: false,
						iconCls: 'commonSaveAll'
					},{
						id : 'create_StockFinanceAction.archive',
						text : '&nbsp;&nbsp;归档',
						leaf : true,
						checked: false,
						iconCls: 'filing'
					},{
						id : 'create_StockFinanceAction.unarchive',
						text : '&nbsp;&nbsp;取消归档',
						leaf : true,
						checked: false,
						iconCls: 'unfiling'
					},{
						id : 'create_StockFinanceAction.export',
						text : '&nbsp;&nbsp;导出数据',
						leaf : true,
						checked: false,
						iconCls: 'commonExport'
					}
					]
				},{
					id : 'create_SpecialStockIncomeAction.query',
					text : '&nbsp;&nbsp;特殊仓储收支',
					leaf : false,
					checked: false,
					iconCls: 'specialStorageIncome',
					children:[
					{
						id : 'create_SpecialStockIncomeAction.insert',
						text : '&nbsp;&nbsp;新建特殊收支',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},
					{
						id : 'create_SpecialStockIncomeAction.update',
						text : '&nbsp;&nbsp;财务维护',
						leaf : true,
						checked: false,
						iconCls: 'commonSaveAll'
					},{
						id : 'create_SpecialStockIncomeAction.archive',
						text : '&nbsp;&nbsp;归档',
						leaf : true,
						checked: false,
						iconCls: 'filing'
					},{
						id : 'create_SpecialStockIncomeAction.unarchive',
						text : '&nbsp;&nbsp;取消归档',
						leaf : true,
						checked: false,
						iconCls: 'unfiling'
					},{
						id : 'create_SpecialStockIncomeAction.export',
						text : '&nbsp;&nbsp;导出数据',
						leaf : true,
						checked: false,
						iconCls: 'commonExport'
					}
					]
				},{
					id : 'create_PaymentCollectionAction.query',
					text : '&nbsp;&nbsp;代收货款',
					leaf : false,
					checked: false,
					iconCls: 'trackCOD',
					children:[
					{
						id : 'create_PaymentCollectionAction.updateState',
						text : '&nbsp;&nbsp;状态流转',
						leaf : true,
						checked: false,
						iconCls: 'flow'
					},
					{
						id : 'create_PaymentCollectionAction.update',
						text : '&nbsp;&nbsp;财务维护',
						leaf : true,
						checked: false,
						iconCls: 'commonSaveAll'
					},{
						id : 'create_PaymentCollectionAction.archive',
						text : '&nbsp;&nbsp;归档',
						leaf : true,
						checked: false,
						iconCls: 'filing'
					},{
						id : 'create_PaymentCollectionAction.unarchive',
						text : '&nbsp;&nbsp;取消归档',
						leaf : true,
						checked: false,
						iconCls: 'unfiling'
					},{
						id : 'create_PaymentCollectionAction.export',
						text : '&nbsp;&nbsp;导出数据',
						leaf : true,
						checked: false,
						iconCls: 'commonExport'
					}
					]
				},{
					id : 'create_FinancialLogAction.query',
					text : '&nbsp;&nbsp;财务日志',
					leaf : false,
					checked: false,
					iconCls: 'financeLog',
					children:[
					{
						id : 'create_FinancialLogAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				}
				]
			},{
				id : 'create_basicData.empty',
				text : '&nbsp;&nbsp;基础数据',
				leaf : false,
				checked: false,
				iconCls: 'basicData',
				children:[
				{
					id : 'create_FreightRouteAction.query',
					text : '&nbsp;&nbsp;运输路线信息',
					leaf : false,
					checked: false,
					iconCls: 'transportationWay',
					children:[
					{
						id : 'create_FreightRouteAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'create_FreightRouteAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'create_FreightRouteAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'create_FreightContractorAction.query',
					text : '&nbsp;&nbsp;承运单位信息',
					leaf : false,
					checked: false,
					iconCls: 'transportationUnit',
					children:[
					{
						id : 'create_FreightContractorAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'create_FreightContractorAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'create_FreightContractorAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'create_CarAction.query',
					text : '&nbsp;&nbsp;车辆信息',
					leaf : false,
					checked: false,
					iconCls: 'carInformation',
					children:[
					{
						id : 'create_CarAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'create_CarAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'create_CarAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'create_CarTypeAction.query',
					text : '&nbsp;&nbsp;车辆类型',
					leaf : false,
					checked: false,
					iconCls: 'carType',
					children:[
					{
						id : 'create_CarTypeAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'create_CarTypeAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'create_CarTypeAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'create_ItemAction.query',
					text : '&nbsp;&nbsp;物料信息',
					leaf : false,
					checked: false,
					iconCls: 'goodsInformation',
					children:[
					{
						id : 'create_ItemAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'create_ItemAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'create_ItemAction.delete',
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
					id : 'create_CustomerAction.query',
					text : '&nbsp;&nbsp;客户信息',
					leaf : false,
					checked: false,
					iconCls: 'customerInformation',
					children:[
					{
						id : 'create_CustomerAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'create_CustomerAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'create_CustomerAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'create_CostCenterAction.query',
					text : '&nbsp;&nbsp;成本中心',
					leaf : false,
					checked: false,
					iconCls: 'costCenter',
					children:[
					{
						id : 'create_CostCenterAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'create_CostCenterAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'create_SellCenterAction.query',
					text : '&nbsp;&nbsp;销售中心',
					leaf : false,
					checked: false,
					iconCls: 'sellCenter',
					children:[
					{
						id : 'create_SellCenterAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'create_SellCenterAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'create_WarehouseAction.query',
					text : '&nbsp;&nbsp;仓库信息',
					leaf : false,
					checked: false,
					iconCls: 'manageStorage',
					children:[
					{
						id : 'create_WarehouseAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'create_WarehouseAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'create_WarehouseAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'create_CityAction.query',
					text : '&nbsp;&nbsp;城市信息',
					leaf : false,
					checked: false,
					iconCls: 'cityInformation',
					children:[
					{
						id : 'create_CityAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'create_CityAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'create_CityAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				}
				]
			},{
				id : 'create_manageWorker.empty',
				text : '&nbsp;&nbsp;人事管理',
				leaf : false,
				checked: false,
				iconCls: 'manageWorker',
				children:[
				{
					id : 'create_EmployeeProfileAction.query',
					text : '&nbsp;&nbsp;员工信息',
					leaf : false,
					checked: false,
					iconCls: 'personalInformation',
					children:[
					{
						id : 'create_EmployeeProfileAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'create_EmployeeProfileAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'create_EmployeeProfileAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'create_EmployeeSalaryAction.query',
					text : '&nbsp;&nbsp;工资管理',
					leaf : false,
					checked: false,
					iconCls: 'manageSalary',
					children:[
					{
						id : 'create_EmployeeSalaryAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'create_EmployeeSalaryAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'create_EmployeeSalaryAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				}
				]
			},{
				id : 'create_manageSystem.empty',
				text : '&nbsp;&nbsp;系统管理',
				leaf : false,
				checked: false,
				iconCls: 'manageSystem',
				children:[
				{
					id : 'create_UserAction.query',
					text : '&nbsp;&nbsp;用户管理',
					leaf : false,
					checked: false,
					iconCls: 'manageUser',
					children:[
					{
						id : 'create_UserAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'create_UserAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'create_UserAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'create_RoleAction.query',
					text : '&nbsp;&nbsp;角色管理',
					leaf : false,
					checked: false,
					iconCls: 'manageRole',
					children:[
					{
						id : 'create_RoleAction.insert',
						text : '&nbsp;&nbsp;新建',
						leaf : true,
						checked: false,
						iconCls: 'commonAdd'
					},{
						id : 'create_RoleAction.update',
						text : '&nbsp;&nbsp;修改',
						leaf : true,
						checked: false,
						iconCls: 'commonEdit'
					},{
						id : 'create_RoleAction.delete',
						text : '&nbsp;&nbsp;删除',
						leaf : true,
						checked: false,
						iconCls: 'commonDelete'
					}
					]
				},{
					id : 'create_SystemInfoAction.query',
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

function GetParamsFromTree_create()
{
	var params = {};
	var nodes = _treepanelPrivilege_create.getChecked();
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
	params['role.roleName']=_formPanel_createRole_MR.findById('create_roleName_MR').getValue();
	params['role.remarks']=_formPanel_createRole_MR.findById('create_remarks_MR').getValue();
	return params;
}

var _formPanel_createRole_MR = new Ext.FormPanel({
	
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
			
			if(_formPanel_createRole_MR.getForm().isValid())
			{
				var mask = new Ext.LoadMask(_win_create_MR.body, {msg:"正在保存,请稍后..."});
				mask.show();
				var params = GetParamsFromTree_create();
				Ext.Ajax.request({
					url: 'Role.insert.action',
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
		handler: function(){ _win_create_MR.hide(); }
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
						id:'create_roleName_MR',
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						name: 'role.roleName',
						width: TEXTFIELDWIDTH
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
						id:'create_remarks_MR',
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
						items: _treepanelPrivilege_create
					}]//column
			}]//row
});
