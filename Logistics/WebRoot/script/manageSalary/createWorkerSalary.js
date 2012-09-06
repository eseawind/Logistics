
var _win_create_MWS = null;

function onCreateWorkerSalary()
{
	if(null == _win_create_MWS)
	{
	    createWindow_create_MWS();
	}
	else
	{
		_formPanel_createWorkerSalary_MWS.getForm().reset();
	}
	
	_ds_wokers_MWS.load({
    	callback:function(record,option,success){
					STORE_CALLBACK(_ds_wokers_MWS.reader.message,_ds_wokers_MWS.reader.valid,success);
				 } 
	});
	_win_create_MWS.setPagePosition(GET_WIN_X(_win_create_MWS.width),GET_WIN_Y());
    _win_create_MWS.show();
}

function createWindow_create_MWS()
{
	_win_create_MWS = new Ext.Window({
        title: '新建员工工资记录',
        iconCls: 'commonCreateSheet',
        width: 635,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_createWorkerSalary_MWS,
        listeners: LISTENER_WIN_MOVE
    });
}

var _ds_wokers_MWS = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'ES.queryAllEmployeesName.action',
				method: 'POST'
			}),
	reader : new Self.JsonReader({
				root : 'resultMapList'
			}, 
			[{
				name : 'EmployeeID'
			},{
				name : 'NameID'
			}]
		)
});

var _formPanel_createWorkerSalary_MWS = new Ext.FormPanel({
	
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
			if(_formPanel_createWorkerSalary_MWS.getForm().isValid())
			{
				_formPanel_createWorkerSalary_MWS.getForm().submit({
					url: 'ES.insert.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_formPanel_createWorkerSalary_MWS.getForm().reset();
						_formPanel_createWorkerSalary_MWS.findById('tab_id_MES').focus();
						_grid_MWS.getStore().reload();
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
		handler:　function(){ _win_create_MWS.hide(); }
	}],
	
	items:[
		
		{//Row 1
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.45',
				layout: 'form',
				border: false,
				items: new Ext.form.ComboBox({
				       xtype : 'combo',
				       store : _ds_wokers_MWS,
				       valueField : 'EmployeeID',
				       displayField : 'NameID',
				       mode : 'local',
				       id: 'tab_id_MES',
				       forceSelection : true,
				       hiddenName : 'esdto.EmployeeID',
				       selectOnFocus:true,
				       typeAhead: true,
				       editable : true,
				       triggerAction : 'all',
				       allowBlank : false,
				       emptyText: '选择员工',
				       fieldLabel: '姓名工号',
				       width:　150,
				       listeners: {
				       		beforequery: LISTENER_COMBOBOX_BEFOREQUERY,
				       		blur: function(combo){
				       			if(''==combo.getValue())
				       			{
				       				_formPanel_createWorkerSalary_MWS.findById('id_create_Department_MWS').setValue('');
				       				_formPanel_createWorkerSalary_MWS.findById('id_create_Position_MWS').setValue('');
				       			}
				       		},
				       		select: function(combo, record, index){
				       			Ext.Ajax.request({
									url: 'ES.queryAEmployeeProfile.action?'+'epdto.EID='+record.get('EmployeeID'),
									method: 'POST',
									success : function(response,options){
										var result = Ext.util.JSON.decode(response.responseText);
										if(!result.success){
											AJAX_FAILURE_CALLBACK(result,'员工信息数据加载失败');	
										}else{
											_formPanel_createWorkerSalary_MWS.findById('id_create_Department_MWS').setValue(result.data.Department);
						       				_formPanel_createWorkerSalary_MWS.findById('id_create_Position_MWS').setValue(result.data.Position);
										}
									},
									failure: function(response,options){
										AJAX_SERVER_FAILURE_CALLBACK(response,options,'员工信息加载错误');
									}
								});//Ajax
				       		} 
				       }
				})
			},
			{//Column 2
				columnWidth: '0.55',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					id: 'id_create_Department_MWS',
					fieldLabel: '部门',
					name: 'Department',
					readOnly: true,
					width: 150
				}]
			}]
		},
		{//Row 2
			layout: 'column',
			border: false,
			items: [
			{//Column 1
					columnWidth: '0.45',
					layout: 'form',
					border: false,
					items: [{
						xtype: 'datefield',
						fieldLabel: '工资日期',
						format: 'Y-m-d',
						name: 'esdto.PayDay',
						value: new Date().dateFormat('Y-m-d'),
						allowBlank: false,
						editable: false,
						width: 150
					}]
			},
			{//Column 2
				columnWidth: '0.55',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					id: 'id_create_Position_MWS',
					fieldLabel: '职位',
					name: 'Position',
					readOnly: true,
					width: 150
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
					labelWidth: 520,
					items: [{
						xtype: 'label',
						fieldLabel: '—————————————————————工资记录————————————————————',
						labelSeparator: ' '
					}]
			}]
		}, 
		{//Row 4
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.33',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'numberfield',
					fieldLabel: '岗位工资',
					name: 'esdto.PostSalary',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 2
				columnWidth: '0.33',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'numberfield',
					fieldLabel: '绩效工资',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					name: 'esdto.PerformanceSalary',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 3
				columnWidth: '0.34',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'numberfield',
					fieldLabel: '加班工资',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					name: 'esdto.OvertimeSalary',
					width: NUMBERFIELDWIDTH
				}]
			}]
		},
		{//Row 5
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.33',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'numberfield',
					fieldLabel: '岗位津贴',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					name: 'esdto.PostAllowance',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 2
				columnWidth: '0.33',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'numberfield',
					fieldLabel: '日常奖金',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					name: 'esdto.BasicBonus',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 3
				columnWidth: '0.34',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'numberfield',
					fieldLabel: '年终奖金',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					name: 'esdto.AnnualBonus',
					width: NUMBERFIELDWIDTH
				}]
			}]
		},
		{//Row 6
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.33',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'numberfield',
					fieldLabel: '交通补助',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					name: 'esdto.TransportAllowance',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 2
				columnWidth: '0.33',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'numberfield',
					fieldLabel: '通讯补助',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					name: 'esdto.CommunicationAllowance',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 3
				columnWidth: '0.34',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'numberfield',
					fieldLabel: '餐费补助',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					name: 'esdto.MealAllowance',
					width: NUMBERFIELDWIDTH
				}]
			}]
		},
		{//Row 7
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '0.33',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'numberfield',
					fieldLabel: '补款',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					name: 'esdto.ExtraMoney',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 2
				columnWidth: '0.33',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'numberfield',
					fieldLabel: '扣款',
					maxValue: 0,
					minValue: -MAX_DOUBLE,
					maxValueText: '扣款请输入负值', 
					value: 0,
					allowBlank: false,
					selectOnFocus: true,
					name: 'esdto.DeductionMoney',
					width: NUMBERFIELDWIDTH
				}]
			},
			{//Column 3
				columnWidth: '0.34',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'numberfield',
					fieldLabel: '其他',
					allowNegative: false,
					value: 0,
					maxValue: MAX_DOUBLE,
					allowBlank: false,
					selectOnFocus: true,
					name: 'esdto.OtherPayment',
					width: NUMBERFIELDWIDTH
				}]
			}]
		},{//Row 8
			layout: 'column',
			border: false,
			items: [
			{//Column 1
				columnWidth: '1',
				layout: 'form',
				border: false,
				items: [
				{
					xtype: 'textfield',
					fieldLabel: '备注信息',
					regex: REGEX_COMMON_M,
					regexText: REGEX_COMMON_M_TEXT,
					name: 'esdto.Remarks',
					width: 464
				}]
			}]
		}]
});

