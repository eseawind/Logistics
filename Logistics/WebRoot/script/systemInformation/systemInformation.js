
function update_systemInformation(contentPanel,node)
{
    
}

function init_systemInformation(id)
{
	var page = Ext.getCmp(id);
	page.add(_grid_SYS);
	_ds_SYS.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_SYS.reader.message,_ds_SYS.reader.valid,success);
		}
	});
}
	
var _ds_SYS = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'SystemInfo.queryOnCondition.action'
			}),
	reader : new Self.JsonReader({
				totalProperty : 'qualifiedAmount',
				root : 'resultMapList'
			}, 
			[{
				name : 'name'
			}, {
				name : 'value'
			}]
		)
});

var _cm_SYS = new Ext.grid.ColumnModel({
	
		columns:[
			new Ext.grid.RowNumberer(),
		{
			header : '名称',
			dataIndex : 'name'
		},{
			header : '统计信息',
			dataIndex : 'value'
		}],
		
		defaults: {
			sortable: true,
			menuDisabled: true
		}
});

var _grid_SYS = new Ext.grid.GridPanel({
	
			region: 'center',
			cm : _cm_SYS,
			ds : _ds_SYS,
			style: 'margin:0',
			stripeRows : true,
			loadMask : true,
			border : false,
			autoScroll: true,
			draggable : false,
			viewConfig : {
				forceFit : true
			},
			
			tbar : new Ext.Toolbar({
				border: true,
				items : [
				{
					xtype: 'tbfill'
				},'-',
				{
					text : "刷新",
					iconCls: 'commonReset',
					handler: function(){
						_ds_SYS.load({
							callback: function(record,option,success){
								STORE_CALLBACK(_ds_SYS.reader.message,_ds_SYS.reader.valid,success);
							}
						});
					}
				},'-',
				{
					xtype: 'tbspacer',
					width: 5
				}]
			}),

			bbar : new Ext.PagingToolbar({
						store : _ds_SYS,
						displayInfo : true,
						displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
						emptyMsg : "没有记录"
					})
		});