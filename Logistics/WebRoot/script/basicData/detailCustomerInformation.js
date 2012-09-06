
var _win_detail_CCI = null;
var _mask_detail_CCI = null;
function onDetailCustomerInformation(customerID)
{
	if(null == _win_detail_CCI)
	{
	    createWindow_detail_CCI();
	}else
	{
		_formPanel_detailCustomer_CCI.getForm().reset();
	}
	_mask_detail_CCI.show();
	_formPanel_detailCustomer_CCI.getForm().load({
    	url: 'Customer.queryOne.action',
    	method: 'POST',
    	params: {'customerDTO.customerID': customerID},
    	success: function(form,action){
			_mask_detail_CCI.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'用户数据读取失败');
    		_mask_detail_CCI.hide();
    	}
    });
	_win_detail_CCI.setPagePosition(GET_WIN_X(_win_detail_CCI.width),GET_WIN_Y());
    _win_detail_CCI.show();
}

function createWindow_detail_CCI()
{
	_win_detail_CCI = new Ext.Window({
        title: '查看客户信息',
        iconCls: 'commonDetail',
        width: 675,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_detailCustomer_CCI,
        listeners: LISTENER_WIN_MOVE
    });
    _win_detail_CCI.show();
    _mask_detail_CCI = new Ext.LoadMask(_win_detail_CCI.body, {msg:"正在载入,请稍后..."});
}


var _formPanel_detailCustomer_CCI = new Ext.FormPanel({
	
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
		handler: function(){ _win_detail_CCI.hide(); }
	}],
	
	items:[
		{//Row 1
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					readOnly: true,
					name: 'customerDTO.type',
					fieldLabel: '客户类型',
					width: 200
				}]
			},
			{//Column 2
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					readOnly: true,
					name: 'customerDTO.customerID',
					fieldLabel: '客户编号',
					width: 200
				}]
			}
			]
		},
		{//Row 2
			layout: 'column',
			border: false,
			items: [
			{//Column 1
					columnWidth: '0.5',
					layout: 'form',
					border: false,
					items: [{
						xtype: 'textfield',
						fieldLabel: '客户名称',
						readOnly: true,
						name: 'customerDTO.name',
						width: 200
					}]
			},
			{//Column 2
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					fieldLabel: '联系人',
					readOnly: true,
					name: 'customerDTO.contact',
					width: 200
				}]
			}]
		},
		{//Row 3
			layout: 'column',
			border: false,
			items: [
				{//Column 1
					columnWidth: '0.5',
					layout: 'form',
					border: false,
					items: [{
						xtype: 'textfield',
						fieldLabel: '联系电话',
						readOnly: true,
						name: 'customerDTO.phone',
						width: 200
					}]
				},
				{//Column 1
					columnWidth: '0.5',
					layout: 'form',
					border: false,
					items: [{
						xtype: 'textfield',
						fieldLabel: '邮件',
						readOnly: true,
						name: 'customerDTO.email',
						width: 200
					}]
			}]
		},
		{//Row 4
			layout: 'column',
			border: false,
			items: [
				{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items: [{
						xtype: 'textfield',
						fieldLabel: '地址',
						readOnly: true,
						name: 'customerDTO.address',
						width: 512
					}]
			}]
		},
		{//Row 7
			layout: 'column',
			border: false,
			items: [
				{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					labelWidth: 570,
					items: [{
						xtype: 'label',
						fieldLabel: '———————————————————入库费用报价———————————————————',
						labelSeparator: ' '
					}]
			}]
		},
		{//Row 7+
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'numberfield',
					fieldLabel: '数量单价',
					readOnly: true,
					name: 'customerDTO.stockInCostPerCount',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 2
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'numberfield',
					fieldLabel: '体积单价',
					readOnly: true,
					name: 'customerDTO.stockInCostPerVolume',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 3
				columnWidth: '0.4',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'numberfield',
					fieldLabel: '重量单价',
					readOnly: true,
					name: 'customerDTO.stockInCostPerWeight',
					width: NUMBERFIELDWIDTH
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
					labelWidth: 570,
					items: [{
						xtype: 'label',
						fieldLabel: '———————————————————出库费用报价———————————————————',
						labelSeparator: ' '
					}]
			}]
		},
		{//Row 8+
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'numberfield',
					fieldLabel: '数量单价',
					readOnly: true,
					name: 'customerDTO.stockOutCostPerCount',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 2
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'numberfield',
					fieldLabel: '体积单价',
					readOnly: true,
					name: 'customerDTO.stockOutCostPerVolume',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 3
				columnWidth: '0.4',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'numberfield',
					fieldLabel: '重量单价',
					readOnly: true,
					name: 'customerDTO.stockOutCostPerWeight',
					width: NUMBERFIELDWIDTH
				}]
			}]
		},
		{//Row 9
			layout: 'column',
			border: false,
			items: [
				{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					labelWidth: 570,
					items: [{
						xtype: 'label',
						fieldLabel: '———————————————————库存费用报价———————————————————',
						labelSeparator: ' '
					}]
			}]
		},
		{//Row 9+
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'numberfield',
					fieldLabel: '数量单价',
					readOnly: true,
					name: 'customerDTO.stockCostPerCount',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 2
				columnWidth: '0.3',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'numberfield',
					fieldLabel: '体积单价',
					readOnly: true,
					name: 'customerDTO.stockCostPerVolume',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 3
				columnWidth: '0.4',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'numberfield',
					fieldLabel: '重量单价',
					readOnly: true,
					name: 'customerDTO.stockCostPerWeight',
					width: NUMBERFIELDWIDTH
				}]
			}]
		},
		{//Row last
			layout: 'column',
			border: false,
			items: [
				{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items: [{
						xtype: 'textfield',
						fieldLabel: '备注',
						readOnly: true,
						name: 'customerDTO.remarks',
						width: 512
					}]
			}]
		}
	]
});

