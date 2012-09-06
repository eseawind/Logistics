
var _win_edit_MBC = null;
var _mask_edit_MBC = null;
function onEditBarCode(id)
{
	if(null == _win_edit_MBC)
	{
	    createWindow_edit_MBC();
	}else
	{
		_formPanel_editBarCode_MBC.getForm().reset();
	}
	_mask_edit_MBC.show();
	_formPanel_editBarCode_MBC.getForm().load({
    	url: 'Barcode.queryOne.action',
    	method: 'POST',
    	params: {'barcode.barcodeID': id},
    	success: function(form,action){
			_mask_edit_MBC.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'条码数据读取失败');
    		_mask_edit_MBC.hide();
    	}
    });
	_win_edit_MBC.setPagePosition(GET_WIN_X(_win_edit_MBC.width),GET_WIN_Y_M(600));
    _win_edit_MBC.show();
}

function createWindow_edit_MBC()
{
	_win_edit_MBC = new Ext.Window({
        title: '条码修改',
        iconCls: 'commonEditSheet',
        width: 375,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_editBarCode_MBC,
        listeners: LISTENER_WIN_MOVE
    });
    _win_edit_MBC.show();
    _mask_edit_MBC = new Ext.LoadMask(_win_edit_MBC.body, {msg:"正在载入,请稍后..."});
}

var _formPanel_editBarCode_MBC = new Ext.FormPanel({
	
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
			if(_formPanel_editBarCode_MBC.getForm().isValid())
			{
				_formPanel_editBarCode_MBC.getForm().submit({
					url: 'Barcode.update.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_formPanel_editBarCode_MBC.getForm().reset();
						_win_edit_MBC.hide();
						_grid_MBC.getStore().reload();
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
		handler: function(){ _win_edit_MBC.hide(); }
	}],
	
	items:[
		{//Row 1
			layout: 'column',
			border: false,
			items: [
			{//Column 0
				columnWidth: '1',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					hidden: true,
					name:'barcode.barcodeID'
				}]
			},
			{//Column 1
				columnWidth: '1',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					fieldLabel: '条码值',
					name: 'barcode.barcode',
					allowBlank: false,
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT,
					width: 200
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
				items: [{
					xtype: 'textfield',
					fieldLabel: '物料编号',
					name: 'barcode.itemID',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT,
					width: 200
				}]
			}]
		},
		{//Row 1
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '1',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					fieldLabel: '物料名称',
					name: 'barcode.itemName',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT,
					width: 200
				}]
			}]
		},
		{//Row 1
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '1',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					fieldLabel: '物料代码',
					name: 'barcode.itemNumber',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT,
					width: 200
				}]
			}]
		},
		{//Row 1
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '1',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					fieldLabel: '批次',
					name: 'barcode.batch',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT,
					width: 200
				}]
			}]
		},
		{//Row 1
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '1',
				layout: 'form',
				border: false,
				items: [{
						xtype: 'numberfield',
						fieldLabel: '数量',
						name: 'barcode.amount',
						allowNegative: false,
						value: 0,
						maxValue: MAX_DOUBLE,
						allowBlank: false,
						selectOnFocus: true,
						width: 200
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
				items: new Ext.form.ComboBox({
				       xtype : 'combo',
				       store : new Ext.data.SimpleStore({      
				              fields : ["returnValue", "displayText"],
				              data : [['出库', '出库'], ['入库', '入库']]
				       }),
				       
				       valueField : 'returnValue',
				       displayField : 'displayText',
				       mode : 'local',
				       forceSelection : true,
				       hiddenName : 'barcode.operationType',
				       editable : false,
				       triggerAction : 'all',
				       fieldLabel: '操作类型',
				       allowBlank : false,
				       value: '入库',
				       width:　200
				})
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
					items: [{
						xtype: 'textfield',
						fieldLabel: '单号',
						name: 'barcode.manifestID',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT,
						width: 200
					}]
				}]
		},
		{//Row 5
			layout: 'column',
			border: false,
			items: [
				{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items: [{
						xtype: 'textfield',
						fieldLabel: '客户编号',
						name: 'barcode.customerID',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT,
						width: 200
					}]
				}]
		},
		{//Row 6
			layout: 'column',
			border: false,
			items: [
				{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items: [{
						xtype: 'textfield',
						fieldLabel: '客户名称',
						name: 'barcode.customer',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT,
						width: 200
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
					items: [{
						xtype: 'textfield',
						fieldLabel: '库存地编号',
						name: 'barcode.warehouseID',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT,
						width: 200
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
					items: [{
						xtype: 'textfield',
						fieldLabel: '库存地名称',
						name: 'barcode.warehouse',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT,
						width: 200
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
					items: [{
						xtype: 'textfield',
						fieldLabel: '备注',
						name: 'barcode.remarks',
					regex: REGEX_COMMON_S,
					regexText: REGEX_COMMON_S_TEXT,
						width: 200
					}]
			}]
		}
	]
});
