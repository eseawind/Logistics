
var _win_edit_MWS = null;
var _mask_edit_MWS = null;
function onEditWorkerSalary(ESID)
{
	if(null == _win_edit_MWS)
	{
	    createWindow_edit_MWS();
	}
	else
	{
		_formPanel_editWorkerSalary_MWS.getForm().reset();
	}
	_mask_edit_MWS.show();
	_ds_wokers_MWS.load({
    	callback:function(record,option,success){
					STORE_CALLBACK(_ds_wokers_MWS.reader.message,_ds_wokers_MWS.reader.valid,success);
				 } 
	});
	_formPanel_editWorkerSalary_MWS.getForm().load({
    	url: 'ES.queryASalary.action',
    	method: 'POST',
    	params: { 'esdto.ESID': ESID },
    	success: function(form,action){
			_mask_edit_MWS.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'用户数据读取失败');
    		_mask_edit_MWS.hide();
    	}
    });
	_win_edit_MWS.setPagePosition(GET_WIN_X(_win_edit_MWS.width),GET_WIN_Y());
    _win_edit_MWS.show();
}

function createWindow_edit_MWS()
{
	_win_edit_MWS = new Ext.Window({
        title: '修改员工工资记录',
        iconCls: 'commonEditSheet',
        width: 635,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_editWorkerSalary_MWS,
        listeners: LISTENER_WIN_MOVE
    });
    _win_edit_MWS.show();
    _mask_edit_MWS = new Ext.LoadMask(_win_edit_MWS.body, {msg:"正在载入,请稍后..."});
}

var _formPanel_editWorkerSalary_MWS = new Ext.FormPanel({
	
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
			if(_formPanel_editWorkerSalary_MWS.getForm().isValid())
			{
				_formPanel_editWorkerSalary_MWS.getForm().submit({
					url: 'ES.update.action',
					waitTitle:"保存数据",
					waitMsg: '正在保存...',
					success:function(form,action){
						_formPanel_editWorkerSalary_MWS.getForm().reset();
						_win_edit_MWS.hide();
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
		handler:　function(){ _win_edit_MWS.hide(); }
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
				       				_formPanel_editWorkerSalary_MWS.findById('id_edit_Department_MWS').setValue('');
				       				_formPanel_editWorkerSalary_MWS.findById('id_edit_Position_MWS').setValue('');
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
											_formPanel_editWorkerSalary_MWS.findById('id_edit_Department_MWS').setValue(result.data.Department);
						       				_formPanel_editWorkerSalary_MWS.findById('id_edit_Position_MWS').setValue(result.data.Position);
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
					id: 'id_edit_Department_MWS',
					fieldLabel: '部门',
					name: 'epdto.Department',
					readOnly: true,
					width: 150
				},{
					xtype: 'textfield',
					hidden: true,
					name: 'esdto.ESID'
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
					id: 'id_edit_Position_MWS',
					fieldLabel: '职位',
					name: 'epdto.Position',
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
					maxValue: MAX_DOUBLE,
					allowNegative: false,
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
					maxValue: MAX_DOUBLE,
					allowNegative: false,
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
					maxValue: MAX_DOUBLE,
					allowNegative: false,
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
					maxValue: MAX_DOUBLE,
					allowNegative: false,
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
					maxValue: MAX_DOUBLE,
					allowNegative: false,
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
					maxValue: MAX_DOUBLE,
					allowNegative: false,
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
					maxValue: MAX_DOUBLE,
					allowNegative: false,
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
					maxValue: MAX_DOUBLE,
					allowNegative: false,
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
					maxValue: MAX_DOUBLE,
					allowNegative: false,
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
					maxValue: MAX_DOUBLE,
					allowNegative: false,
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
					maxValue: MAX_DOUBLE,
					allowNegative: false,
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

