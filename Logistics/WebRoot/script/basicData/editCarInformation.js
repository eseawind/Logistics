
var _win_edit_CI = null;
var _mask_edit_CI = null;
function onEditCarInformation(carID)
{
	if(null == _win_edit_CI)
	{
	    createWindow_edit_CI();
	    STORE_TRANS_UNIT_LOAD();
		STORE_CARTYPE_LOAD();
	}else
	{
		_formPanel_editCarInfo_CI.getForm().reset();
	}
	_mask_edit_CI.show();
	_formPanel_editCarInfo_CI.getForm().load({
    	url: 'Car.queryOne.action',
    	method: 'POST',
    	params: { 'carDTO.carID': carID },
    	success: function(form,action){
			_mask_edit_CI.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'用户数据读取失败');
    		_mask_edit_CI.hide();
    	}
    });
	_win_edit_CI.setPagePosition(GET_WIN_X(_win_edit_CI.width),GET_WIN_Y());
    _win_edit_CI.show();
}

function createWindow_edit_CI()
{
	_win_edit_CI = new Ext.Window({
        title: '修改车辆信息',
        iconCls: 'commonEditSheet',
        width: 375,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_editCarInfo_CI,
        listeners: LISTENER_WIN_MOVE
    });
    _win_edit_CI.show();
    _mask_edit_CI = new Ext.LoadMask(_win_edit_CI.body, {msg:"正在载入,请稍后..."});
}


var _formPanel_editCarInfo_CI = new Ext.FormPanel({
	
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
			if(_formPanel_editCarInfo_CI.getForm().isValid())
			{
				_formPanel_editCarInfo_CI.getForm().submit({
					url: 'Car.update.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_formPanel_editCarInfo_CI.getForm().reset();
						_win_edit_CI.hide();
						_grid_CI.getStore().reload();
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
		handler: function(){ _win_edit_CI.hide(); }
	}],
	
	items:[
	{//Row 承运单位
				layout: 'column',
				border: false,
				items: [{//Column 1
							columnWidth: '1',
							layout: 'form',
							border: false,
							items:new Ext.form.ComboBox({
						       xtype : 'combo',
						       store : STORE_TRANS_UNIT,
						       valueField : 'freightContractorID',
						       displayField : 'jointName',
						       mode : 'local',
						       forceSelection : true,
						       hiddenName : 'carDTO.freightContractorID',
						       selectOnFocus:true,
						       typeAhead: true,
						       editable : true,
						       triggerAction : 'all',
						       fieldLabel: '承运单位',
						       emptyText: '请选择所属承运单位',
						       allowBlank: false,
						       name : 'carUnit',
						       width:　200,
						       listeners: {
						       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY
						       }
						})
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
					fieldLabel: '车牌号码',
					name: 'carDTO.carID',
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
						fieldLabel: '驾驶员姓名',
						name: 'carDTO.driverName',
						allowBlank: false,
						regex: REGEX_COMMON_S,
						regexText: REGEX_COMMON_S_TEXT,
						width: 200
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
					items: [{
						xtype: 'textfield',
						fieldLabel: '车主姓名',
						name: 'carDTO.ownerName',
						allowBlank: false,
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
					items: [{//Column 1
							columnWidth: '1',
							layout: 'form',
							border: false,
							items:new Ext.form.ComboBox({
						       xtype : 'combo',
						       store : STORE_CARTYPE,
						       valueField : 'carTypeID',
						       displayField : 'carTypeName',
						       mode : 'local',
						       forceSelection : true,
						       hiddenName : 'carDTO.carTypeID',
						       triggerAction : 'all',
						       fieldLabel: '车辆类型',
						       allowBlank: false,
						       name : 'Unit',
						       width:　200
						})
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
					items: [{
						xtype: 'textfield',
						fieldLabel: '联系电话',
						name: 'carDTO.phone',
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
						fieldLabel: '发动机号',
						name: 'carDTO.engineNo',
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
						fieldLabel: '车架号',
						name: 'carDTO.vehicleIdentificationNo',
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
						fieldLabel: '行驶证号',
						name: 'carDTO.roadWorthyCertificateNo',
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
						xtype: 'textarea',
						fieldLabel: '备注',
						name: 'carDTO.remarks',
						regex: REGEX_COMMON_M,
						regexText: REGEX_COMMON_M_TEXT,
						width: 200
					}]
			}]
		}
	]
});

