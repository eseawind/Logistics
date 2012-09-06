
var _win_edit_CCI = null;
var _mask_edit_CCI = null;
function onEditCustomerInformation(customerID)
{
	if(null == _win_edit_CCI)
	{
	    createWindow_edit_CCI();
	}else
	{
		_formPanel_editCustomer_CCI.getForm().reset();
	}
	_mask_edit_CCI.show();
	_formPanel_editCustomer_CCI.getForm().load({
    	url: 'Customer.queryOne.action',
    	method: 'POST',
    	params: {'customerDTO.customerID': customerID},
    	success: function(form,action){
			_mask_edit_CCI.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'用户数据读取失败');
    		_mask_edit_CCI.hide();
    	}
    });
	_win_edit_CCI.setPagePosition(GET_WIN_X(_win_edit_CCI.width),GET_WIN_Y());
    _win_edit_CCI.show();
}

function createWindow_edit_CCI()
{
	_win_edit_CCI = new Ext.Window({
        title: '修改客户信息',
        iconCls: 'commonEditSheet',
        width: 675,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_editCustomer_CCI,
        listeners: LISTENER_WIN_MOVE
    });
    _win_edit_CCI.show();
    _mask_edit_CCI = new Ext.LoadMask(_win_edit_CCI.body, {msg:"正在载入,请稍后..."});
}

var _formPanel_editCustomer_CCI = new Ext.FormPanel({
	
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
		handler: function()
		{
			if(_formPanel_editCustomer_CCI.getForm().isValid())
			{
				_formPanel_editCustomer_CCI.getForm().submit({
					url: 'Customer.update.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_formPanel_editCustomer_CCI.getForm().reset();
						_win_edit_CCI.hide();
						_grid_CCI.getStore().reload();
					},
					failure: function(form,action){
						FORM_FAILURE_CALLBACK(form,action,"数据保存失败");
					}
				});
			}
		}
	},{
		text: '取消',
		iconCls: 'commonCancel',
		handler: function(){ _win_edit_CCI.hide(); }
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
				items: new Ext.form.ComboBox({
				       xtype : 'combo',
				       store : new Ext.data.SimpleStore({      
				              fields : ["customerType"],
				              data : [['合同客户'],['零散客户']]
				       }),
				       valueField : 'customerType',
				       displayField : 'customerType',
				       mode : 'local',
				       forceSelection : true,
				       hiddenName : 'customerDTO.type',
				       editable : false,
				       triggerAction : 'all',
				       fieldLabel: '客户类型',
				       allowBlank : false,
				       name : 'city',
				       value: '合同客户',
				       width:　200
				})
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
						allowBlank: false,
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
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
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT,
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
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
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
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
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
						regex: REGEX_COMMON_M,
						regexText: REGEX_COMMON_M_TEXT,
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
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
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
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
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
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
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
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
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
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
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
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
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
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
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
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
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
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
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
						regex: REGEX_COMMON_M,
						regexText: REGEX_COMMON_M_TEXT,
						name: 'customerDTO.remarks',
						width: 512
					}]
			}]
		}
	]
});

