var _win_import_COMMN = null;
var _win_type_import_COMMN = null;
function onImportDate(type)
{
	if(null == _win_import_COMMN)
	{
	    createWindow_import_COMMON();
	}else
	{
		_formPanel_import_COMMN.getForm().reset();
	}
	_win_type_import_COMMN = type;
	_win_import_COMMN.setPagePosition(GET_WIN_X(_win_import_COMMN.width),GET_WIN_Y_M(260));
	_win_import_COMMN.show();
}

function createWindow_import_COMMON()
{
	_win_import_COMMN = new Ext.Window({
        title: '数据导入',
        iconCls: 'commonImport',
        width: 600,
        autoHeight: true,
        closeAction: 'hide',
        maximizable: false,
        resizable: false,
        items: _formPanel_import_COMMN,
        listeners: LISTENER_WIN_MOVE
    });
}

var _formPanel_import_COMMN = new Ext.FormPanel({
	
	layout: 'form',
	fileUpload:true,
	style: 'margin:0px',
	frame: true,
	labelAlign: 'right',
	bodyStyle: 'padding: 10px 10px 7px 10px',
	autoHeight: true,
	autoScoll: true,
	labelWidth: 100,
	border: false,
	buttonAlign: 'center',
	
	buttons:[
	{
		text: '导入',
		iconCls: 'commonSave',
		handler: function()
		{
			if(_formPanel_import_COMMN.getForm().isValid())
			{
				var url = '';
				switch(_win_type_import_COMMN)
				{
					case 'MBC':url='Barcode.importBarcodes.action';break;
					case 'GI':url='Item.importFile.action';break;
					default: Ext.Msg.show({
						title : '操作提示',
						msg : '该功能尚未开放！',
						buttons : Ext.Msg.OK,
						icon : Ext.Msg.WARNING
					});return;
				}
				_formPanel_import_COMMN.getForm().submit({
					url:url,
					waitTitle:"导入数据",
					waitMsg: '正在导入...',
					success:function(form,action){
						_formPanel_import_COMMN.getForm().reset();
						_win_import_COMMN.hide();
						switch(_win_type_import_COMMN)
						{
							case 'MBC':_grid_MBC.getStore().reload();break;
							case 'GI':
							default: break;
						}
					},
					failure: function(form,action){
						FORM_FAILURE_CALLBACK(form,action,"数据导入失败");
					}
				});
			}
		}
	},{
		text: '关闭',
		iconCls: 'commonCancel',
		handler: function(){ _win_import_COMMN.hide(); }
	}],
	
	items:[{
			layout: 'column',
			border: false,
			items: [{//Column 1
				columnWidth: '1',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'button',
					fieldLabel: '导入模板',
					text: '&nbsp;&nbsp;模板下载',
					style: 'margin: 0px 0px 0px 0px',
					iconCls :'commonExcel',
					width: 100,
					handler: function(){
						var url = '';
						switch(_win_type_import_COMMN)
						{
							case 'MBC':url='Barcode.downloadTemplate.action';break;
							case 'GI':url='Item.downloadTemplate.action';break;
							default: Ext.Msg.show({
								title : '操作提示',
								msg : '该功能尚未开放！',
								buttons : Ext.Msg.OK,
								icon : Ext.Msg.WARNING
							});return;
						}
						TEMPLATE_DOWNLOAD(url);
					}
				}]
			}]
		},{
			layout: 'column',
			border: false,
			items: [{//Column 1
				columnWidth: '1',
				layout: 'form',
				border: false,
				items:[
				{
					xtype: 'fileuploadfield',
					fieldLabel: '模板上传',
					name:'upload',
					emptyText: '请选择对应的数据模板导入',
					width: 400,
					buttonText: '',
		            buttonCfg: {
		                iconCls: 'attachment'
		            }
				}]
			}]
		}]
});