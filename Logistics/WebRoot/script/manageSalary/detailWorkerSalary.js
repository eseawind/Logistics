
var _win_detail_MWS = null;
var _mask_detail_MWS = null;
function onDetailWorkerSalary(ESID)
{
	if(null == _win_detail_MWS)
	{
	    createWindow_detail_MWS();
	}
	else
	{
		_formPanel_detailWorkerSalary_MWS.getForm().reset();
	}
	_mask_detail_MWS.show();
	_formPanel_detailWorkerSalary_MWS.getForm().load({
    	url: 'ES.queryASalary.action',
    	method: 'POST',
    	params: { 'esdto.ESID': ESID },
    	success: function(form,action){
			_mask_detail_MWS.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'用户数据读取失败');
    		_mask_detail_MWS.hide();
    	}
    });
	_win_detail_MWS.setPagePosition(GET_WIN_X(_win_detail_MWS.width),GET_WIN_Y());
    _win_detail_MWS.show();
}

function createWindow_detail_MWS()
{
	_win_detail_MWS = new Ext.Window({
        title: '查看员工工资记录明细',
        iconCls: 'commonDetail',
        width: 635,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_detailWorkerSalary_MWS,
        listeners: LISTENER_WIN_MOVE
    });
    _win_detail_MWS.show();
    _mask_detail_MWS = new Ext.LoadMask(_win_detail_MWS.body, {msg:"正在载入,请稍后..."});
}

var _formPanel_detailWorkerSalary_MWS = new Ext.FormPanel({
	
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
	
	buttons:[{
		text: '关闭',
		iconCls: 'commonCancel',
		handler:　function(){ _win_detail_MWS.hide(); }
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
				items: [{
					xtype: 'textfield',
					fieldLabel: '姓名',
					name: 'epdto.Name',
					readOnly: true,
					width: 150
				}]
			},
			{//Column 2
				columnWidth: '0.55',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
					fieldLabel: '部门',
					name: 'epdto.Department',
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
						xtype: 'textfield',
						fieldLabel: '工资日期',
						name: 'esdto.PayDay',
						readOnly: true,
						width: 150
					}]
			},
			{//Column 2
				columnWidth: '0.55',
				layout: 'form',
				border: false,
				items: [{
					xtype: 'textfield',
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
					readOnly: true,
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
					readOnly: true,
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
					readOnly: true,
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
					readOnly: true,
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
					readOnly: true,
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
					readOnly: true,
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
					readOnly: true,
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
					readOnly: true,
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
					readOnly: true,
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
					readOnly: true,
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
					readOnly: true,
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
					readOnly: true,
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
					readOnly: true,
					name: 'esdto.Remarks',
					width: 464
				}]
			}]
		}]
});

