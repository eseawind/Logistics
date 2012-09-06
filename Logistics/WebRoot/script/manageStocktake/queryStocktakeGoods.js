function addStocktakeAllGoods(id,store,btn)
{
	btn.disable();
	Ext.Ajax.request({
		url: 'StockItem.queryAllInventoryItems.action',
		method: 'POST',
		success : function(response,options){
			var result = Ext.util.JSON.decode(response.responseText);
			if(!result.success){
				AJAX_FAILURE_CALLBACK(result,'物料报价获取失败！');	
			}else{
				store.loadData(result,false);
			}
			btn.enable();
		},
		failure: function(response,options){
			AJAX_SERVER_FAILURE_CALLBACK(response,options,'物料报价获取错误！');
			btn.enable();
		},
		params:{ 'sidto.warehouseID':id }
	});//Ajax
}

function addStocktakeChangedGoods(id,store,start,end,btn)
{
	btn.disable();
	Ext.Ajax.request({
		url: 'StockItem.queryChangedInventoryItems.action',
		method: 'POST',
		success : function(response,options){
			var result = Ext.util.JSON.decode(response.responseText);
			if(!result.success){
				AJAX_FAILURE_CALLBACK(result,'物料报价获取失败！');	
			}else{
				store.loadData(result,false);
			}
			btn.enable();
		},
		failure: function(response,options){
			AJAX_SERVER_FAILURE_CALLBACK(response,options,'物料报价获取错误！');
			btn.enable();
		},
		params:{ 'sidto.warehouseID':id,
			startDate:start,
			endDate:end
		}
	});//Ajax
}