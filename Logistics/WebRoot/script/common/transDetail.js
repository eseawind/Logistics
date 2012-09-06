
var _win_detail_TT_COMMON = null;

function onDetailTransportationTrack(transId)
{
	if(null == _win_detail_TT_COMMON)
	{
	    createWindow_detail_TT_COMMON();
	}else
	{
		_ds_TT_COMMON.removeAll();
	}
	_ds_TT_COMMON.load({
		callback: function(record,option,success){
			STORE_CALLBACK(_ds_TT_COMMON.reader.message,_ds_TT_COMMON.reader.valid,success);
		},
		params: {'fmdto.freightManifestID': transId}
	});
	_win_detail_TT_COMMON.setTitle('物流明细查看&nbsp;'+'[&nbsp;运输单号&nbsp;：&nbsp;'+transId+'&nbsp;]');
	_win_detail_TT_COMMON.setPagePosition(GET_WIN_X(_win_detail_TT_COMMON.width),GET_WIN_Y());
    _win_detail_TT_COMMON.show();
}

function createWindow_detail_TT_COMMON()
{
	_win_detail_TT_COMMON = new Ext.Window({
        title: '物流明细查看',
        iconCls: 'trackDetail',
        width: 650,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _grid_detailTrack_TT_COMMON,
        listeners: LISTENER_WIN_MOVE
    });
}

var _limit_TT_COMMON = 30;

var _ds_TT_COMMON = new Ext.data.Store({	
		proxy : new Ext.data.HttpProxy({
					url : 'FreightState.queryOne.action'
				}),
		reader : new Self.JsonReader({
					root : 'resultMapList'
				}, 
				[{
					name : 'state'
				}, {
					name : 'date'
				}, {
					name : 'stateRemarks'
				}]
			),
			baseParams: {
				start: 0,
				limit : _limit_TT_COMMON
			}
	});
	
	var _cm_detailTrack_TT_COMMON = new Ext.grid.ColumnModel({
		
			columns:[
				new Ext.grid.RowNumberer(),
			{
				dataIndex : 'state',
				header : '状态名称',
				width: 30
			},{
				dataIndex : 'date',
				header : '操作日期',
				width: 60
			},{
				dataIndex : 'stateRemarks',
				header : '备注说明'
			}],
			
			defaults: {
				sortable: true,
				menuDisabled: true
			}
	});
	
	var _grid_detailTrack_TT_COMMON = new Ext.grid.GridPanel({
		cm : _cm_detailTrack_TT_COMMON,
		ds : _ds_TT_COMMON,
		style: 'margin:0px 0px 0px 0px',
		stripeRows : true,
		loadMask : true,
		border : true,
		frame: false,
		height : 300,
		autoScroll: true,
		draggable : false,
		hideLabel: true,
		viewConfig : {
			forceFit : true
		},
		bbar: new Ext.PagingToolbar({
				pageSize : _limit_TT_COMMON,
				store : _ds_TT_COMMON,
				displayInfo : true,
				width : 500,
				displayMsg : '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
				emptyMsg : "没有记录"
			})
	});
			
