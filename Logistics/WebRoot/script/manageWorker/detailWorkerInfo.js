
var _win_detail_CWI = null;
var _mask_detail_CWI = null;
function onDetailWorkerInfo(employeeID)
{
	if(null == _win_detail_CWI)
	{
	    createWindow_detail_CWI();
	}
	_win_detail_CWI.setPagePosition(GET_WIN_X(_win_detail_CWI.width),GET_WIN_Y());
	_mask_detail_CWI.show();
    _formPanel_detailWokerInfo_CWI.getForm().load({
    	url: 'queryAEmployee.action',
    	method: 'POST',
    	params: {EmployeeID: employeeID},
    	success: function(form,action){
			_mask_detail_CWI.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'用户数据读取失败');
    		_mask_detail_CWI.hide();
    	}
    });s
    _win_detail_CWI.show();
}

function createWindow_detail_CWI()
{
	_win_detail_CWI = new Ext.Window({
        title: '查看员工信息',
        iconCls: 'commonDetail',
        width: 380,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_detailWokerInfo_CWI,
        listeners: LISTENER_WIN_MOVE
    });
    _win_detail_CWI.show();
    _mask_detail_CWI = new Ext.LoadMask(_win_detail_CWI.body, {msg:"正在载入,请稍后..."});
}

var _formPanel_detailWokerInfo_CWI = new Ext.FormPanel({
	
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
		handler: function(){ _win_detail_CWI.hide(); }
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
						fieldLabel: '工号',
						name: 'EmployeeID',
						readOnly: true,
						width:　200
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
						fieldLabel: '姓名',
						name: 'Name',
						readOnly: true,
						width:　200
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
						xtype: 'textfield',
						fieldLabel: '部门',
						name: 'Department',
						readOnly: true,
						width:　200
					}]
				}]
			},
			{//Row 4
				layut: 'column',
				border: false,
				items: [{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '职位',
						name: 'Position',
						readOnly: true,
						width:　200
					}]
				}]
			},
			{//Row 5
				layut: 'column',
				border: false,
				items: [{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '手机号码',
						name: 'CellPhone',
						readOnly: true,
						width:　200
					}]
				}]
			},
			{//Row 6
				layut: 'column',
				border: false,
				items: [{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '电子邮件',
						name: 'Email',
						readOnly: true,
						width:　200
					}]
				}]
			},
			{//Row 7
				layut: 'column',
				border: false,
				items: [{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items:[
					{
						xtype: 'textfield',
						fieldLabel: '身份证号码',
						name: 'IDCard',
						readOnly: true,
						width:　200
					}]
				}]
			},
			{//Row 8
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
						fieldLabel: '备注信息',
						name: 'Remarks',
						readOnly: true,
						width:　200
					}]
				}]
			}]
});

