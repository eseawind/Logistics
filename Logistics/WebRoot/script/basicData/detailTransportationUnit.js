
var _win_detail_TU = null;
var _mask_detail_TU = null;
function onDetailTransportationUnit(unitID)
{
	if(null == _win_detail_TU)
	{
	    createWindow_detail_TU();
	}else
	{
		_formPanel_detailTransUnit_TU.getForm().reset();
	}
	_mask_detail_TU.show();
	_formPanel_detailTransUnit_TU.getForm().load({
    	url: 'FreightContractor.queryOne.action',
    	method: 'POST',
    	params: {'freightContractorDTO.freightContractorID': unitID},
    	success: function(form,action){
    		_mask_detail_TU.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'用户数据读取失败');
    		_mask_detail_TU.hide();
    	}
    });
	_win_detail_TU.setPagePosition(GET_WIN_X(_win_detail_TU.width),GET_WIN_Y_M(250));
    _win_detail_TU.show();
}

function createWindow_detail_TU()
{
	_win_detail_TU = new Ext.Window({
        title: '查看承运单位明细',
        iconCls: 'commonDetail',
        width: 675,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_detailTransUnit_TU,
        listeners: LISTENER_WIN_MOVE
    });
    _win_detail_TU.show();
    _mask_detail_TU = new Ext.LoadMask(_win_detail_TU.body, {msg:"正在载入,请稍后..."});
}


var _formPanel_detailTransUnit_TU = new Ext.FormPanel({
	
	layout: 'form',
	style: 'margin:0px',
	frame: true,
	labelAlign: 'right',
	bodyStyle: PADDING,
	labelWidth: 70,
	border: false,
	buttonAlign: 'center',
	
	buttons:[
	{
		text: '关闭',
		iconCls: 'commonCancel',
		handler: function(){ _win_detail_TU.hide(); }
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
				items: [{
					xtype: 'textfield',
					name: 'freightContractorDTO.freightContractorID',
					readOnly: true,
					fieldLabel: '单位编号',
					width: 200
				}]
			}]
		},
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
					name: 'freightContractorDTO.name',
					readOnly: true,
					fieldLabel: '单位名称',
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
						name: 'freightContractorDTO.contact',
						readOnly: true,
						width: 200
					}]
			}]
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
						fieldLabel: '联系电话',
						name: 'freightContractorDTO.phone',
						readOnly: true,
						width: 200
				}]
			},
			{//Column 2
				columnWidth: '0.5',
				layout: 'form',
				border: false,
				items: [{
						xtype: 'textfield',
						fieldLabel: '邮件',
						name: 'freightContractorDTO.email',
						readOnly: true,
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
						name: 'freightContractorDTO.address',
						readOnly: true,
						width: 512
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
						fieldLabel: '备注',
						name: 'freightContractorDTO.remarks',
						readOnly: true,
						width: 512
					}]
			}]
		}
	]
});
