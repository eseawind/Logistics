
var _win_create_CI = null;

function onCreateCarInformation()
{
	if(null == _win_create_CI)
	{
	    createWindow_create_CI();
	    STORE_TRANS_UNIT_LOAD();
		STORE_CARTYPE_LOAD();
	}else
	{
		_formPanel_createCarInfo_CI.getForm().reset();
	}
	_win_create_CI.setPagePosition(GET_WIN_X(_win_create_CI.width),GET_WIN_Y());
    _win_create_CI.show();
}

function createWindow_create_CI()
{
	_win_create_CI = new Ext.Window({
        title: '新建车辆信息',
        iconCls: 'commonCreateSheet',
        width: 375,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_createCarInfo_CI,
        listeners: LISTENER_WIN_MOVE
    });
}

var _formPanel_createCarInfo_CI = new Ext.FormPanel({
	
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
			if(_formPanel_createCarInfo_CI.getForm().isValid())
			{
				_formPanel_createCarInfo_CI.getForm().submit({
					url: 'Car.insert.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_grid_CI.getStore().reload();
					},
					failure: function(form,action){
						FORM_FAILURE_CALLBACK(form,action,"数据保存失败");
					}
				});
			}
		}
	},{
		text: '重置',
		iconCls: 'commonReset',
		handler: function(){
			_formPanel_createCarInfo_CI.getForm().reset();
			_formPanel_createCarInfo_CI.findById('tab_id_CI').focus();
		}
	},{
		text: '取消',
		iconCls: 'commonCancel',
		handler: function(){ _win_create_CI.hide(); }
	}],
	
	items:[
	{//Row 
				layout: 'column',
				border: false,
				items: [{//Column 1
							columnWidth: '1',
							layout: 'form',
							border: false,
							items:new Ext.form.ComboBox({
						       xtype : 'combo',
						       id:'tab_id_CI',
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

