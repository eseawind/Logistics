
var _win_detail_FL = null;
var _mask_detail_FL = null;
function onDetailFinanceLog(id)
{
	if(null == _win_detail_FL)
	{
	    createWindow_detail_FL();
	}
	_win_detail_FL.setPagePosition(GET_WIN_X(_win_detail_FL.width),GET_WIN_Y());
	_mask_detail_FL.show();
    _formPanel_detailFinanceLog_FL.getForm().load({
    	url: 'FinancialLog.queryOne.action',
    	method: 'POST',
    	params: {'log.logID': id},
    	success: function(form,action){
			_mask_detail_FL.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'用户数据读取失败');
    		_mask_detail_FL.hide();
    	}
    });
    _win_detail_FL.show();
}

function createWindow_detail_FL()
{
	_win_detail_FL = new Ext.Window({
        title: '查看日志明细',
        iconCls: 'commonDetail',
        width: 700,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_detailFinanceLog_FL,
        listeners: LISTENER_WIN_MOVE
    });
    _win_detail_FL.show();
    _mask_detail_FL = new Ext.LoadMask(_win_detail_FL.body, {msg:"正在载入,请稍后..."});
}

var _formPanel_detailFinanceLog_FL = new Ext.FormPanel({
	
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
		text: '关闭',
		iconCls: 'commonCancel',
		handler: function(){ _win_detail_FL.hide(); }
	}],
	
	items:[
		{//Row 1
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '0.33',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '日志编号',
						name: 'logID',
						readOnly: true,
						width:　TEXTFIELDWIDTH
					}]
				},
				{//Column 2
					columnWidth: '0.33',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '操作账号',
						name: 'user',
						readOnly: true,
						width:　TEXTFIELDWIDTH
					}]
				},
				{//Column 3
					columnWidth: '0.34',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '操作日期',
						name: 'operationTime',
						readOnly: true,
						width:　TEXTFIELDWIDTH
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
						fieldLabel: '业务类型',
						name: 'type',
						readOnly: true,
						width: 548
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
					items:[
					{
						xtype: 'textarea',
						height: 200,
						fieldLabel: '操作内容',
						name: 'content',
						readOnly: true,
						width:　548
					}]
				}]
			}]
});

