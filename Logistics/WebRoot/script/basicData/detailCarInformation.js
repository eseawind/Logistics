
var _win_detail_CI = null;
var _mask_detail_CI = null;
function onDetailCarInformation(carID)
{
	if(null == _win_detail_CI)
	{
	    createWindow_detail_CI();
	}else
	{
		_formPanel_detailCarInfo_CI.getForm().reset();
	}
	_mask_detail_CI.show();
	_formPanel_detailCarInfo_CI.getForm().load({
    	url: 'Car.queryOne.action',
    	method: 'POST',
    	params: { 'carDTO.carID': carID },
    	success: function(form,action){
			_mask_detail_CI.hide();
    	},
    	failure: function(form,action){
    		FORM_FAILURE_CALLBACK(form,action,'用户数据读取失败');
    		_mask_detail_CI.hide();
    	}
    });
	_win_detail_CI.setPagePosition(GET_WIN_X(_win_detail_CI.width),GET_WIN_Y());
    _win_detail_CI.show();
}

function createWindow_detail_CI()
{
	_win_detail_CI = new Ext.Window({
        title: '查看车辆信息',
        iconCls: 'commonDetail',
        width: 375,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_detailCarInfo_CI,
        listeners: LISTENER_WIN_MOVE
    });
    _win_detail_CI.show();
    _mask_detail_CI = new Ext.LoadMask(_win_detail_CI.body, {msg:"正在载入,请稍后..."});
}


var _formPanel_detailCarInfo_CI = new Ext.FormPanel({
	
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
		handler: function(){ _win_detail_CI.hide(); }
	}],
	
	items:[
		{//Row 承运单位
				layout: 'column',
				border: false,
				items: [
				{//Column 1
					columnWidth: '1',
					layout: 'form',
					border: false,
					items: [{
						xtype: 'textfield',
						fieldLabel: '承运单位',
						name: 'freightContractor',
						readOnly: true,
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
					fieldLabel: '车牌号码',
					name: 'carDTO.carID',
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
				columnWidth: '1',
				layout: 'form',
				border: false,
				items: [{
						xtype: 'textfield',
						fieldLabel: '驾驶员姓名',
						name: 'carDTO.driverName',
						readOnly: true,
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
						readOnly: true,
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
						fieldLabel: '车辆类型',
						name: 'carType',
						readOnly: true,
						width: 200
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
						readOnly: true,
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
						readOnly: true,
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
						readOnly: true,
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
						readOnly: true,
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
						readOnly: true,
						width: 200
					}]
			}]
		}
	]
});

