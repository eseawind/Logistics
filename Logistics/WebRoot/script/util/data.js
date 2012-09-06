
var C_ORIGINPERSON = '华亿物流';
var C_ORIGINPHONE = '029-89164440';
var C_ORIGINADDRESS = '西安市太华北路延伸线与北三环立交东北角惠达物流B区华亿物流';

var TEXTFIELDWIDTH = 120;
var ANCHER = '90%';
var DATAFIELDWIDTH = 100;
var SHORTWIDTH = 83;

var NUMBERFIELDWIDTH = 80;
var PADDING = 'padding: 15px 20px 5px 5px';

var COMBO_PAGESIZE = 10;

var V_DECIMAL = 3;

var STORE_PROVINCE = new Ext.data.SimpleStore
({       
	fields : ['province','display'],
	data : [['北京','北京'],
			['广东','广东'], 
			['上海','上海'], 
			['天津','天津'], 
			['重庆','重庆'], 
			['辽宁','辽宁'], 
			['江苏','江苏'], 
			['湖北','湖北'], 
			['四川','四川'], 
			['陕西','陕西'], 
			['河北','河北'],
			['山西','山西'],
			['河南','河南'],
			['吉林','吉林'], 
			['黑龙江','黑龙江'], 
			['内蒙古','内蒙古'], 
			['山东','山东'], 
			['安徽','安徽'],
			['浙江','浙江'],
			['福建','福建'],
			['云南','云南'], 
			['江西','江西'],
			['湖南','湖南'],
			['广西','广西'], 
			['贵州','贵州'], 
			['西藏','西藏'],
			['海南','海南'],
			['新疆','新疆'],
			['甘肃','甘肃'], 
			['宁夏','宁夏'],
			['青海','青海'],
			['其他','其他']]
})

var STORE_YN = new Ext.data.SimpleStore
({   
	fields : ['value'],
	data: [['是'],['否']]
})

var STORE_ITEMUNIT = new Ext.data.SimpleStore
({   
	fields : ['unit'],
	data: [['件'],['箱'],['台'],['袋']]
})

var STORE_NEWSSTATE = new Ext.data.SimpleStore
({      
	fields:['value','display'],
	data : [['','全部信息'],['新闻信息','新闻信息'],['行业动态','行业动态'],['公告通知','公告通知'],
	['知识管理','知识管理'],['文化天地','文化天地'],['业务动态','业务动态']]
})

var STORE_TRANS_CHARGE_MODE = new Ext.data.SimpleStore
({      
	fields : ["value"],
	data : [['体积(M3)'],['重量(KG)'],['数量']]
})

var STORE_ENTRUCK_CHARGE_MODE = new Ext.data.SimpleStore
({      
	fields:["value"],
	data : [['直接计费'],['车辆类型'],['以数量计'],['以重量计'],['以体积计']]
})

var M_TRACK_1 = [['已收到站','[2]已收到站'],['已收到总部','[3]已收到总部']];
var M_TRACK_2 = [['已收到总部','[3]已收到总部']];
var M_TRACK_3 = [['已返回客户','[4]已返回客户']];
var M_TRACK_4 = [['','']];

var STORE_MONEY_TRACKSTATE = new Ext.data.SimpleStore
({   
	fields : ['value','display'],
	data : [['未收','[1]未收'],['已收到站','[2]已收到站'],['已收到总部','[3]已收到总部'],
			['已返回客户','[4]已返回客户']]
})

var DATA_TRACK_1 = [['已发货','[2]已发货'],['异常','[7]异常']];
var DATA_TRACK_2 = [['已到港','[3]已到港'],['已中转','[5]已中转'],
				    ['异常','[7]异常']];
var DATA_TRACK_3 = [['已签收','[4]已签收'],['已中转','[5]已中转'],
		   			['异常','[7]异常']];
var DATA_TRACK_4 = [['已收回单','[6]已收回单'],['异常','[7]异常']];
var DATA_TRACK_5 = [['已到港','[3]已到港'],['已中转','[5]已中转'],
				    ['异常','[7]异常']];  			
var DATA_TRACK_6 = [['已发货','[2]已发货'],['已到港','[3]已到港'],
				    ['已签收','[4]已签收'],['已中转','[5]已中转'],
				    ['已收回单','[6]已收回单']];
var DATA_TRACK_7 = [['','']];

var STORE_TRACKSTATE = new Ext.data.SimpleStore
({   
	fields : ['value','display'],
	data: [['已创建','[1]已创建'],
		   ['已发货','[2]已发货'],['已到港','[3]已到港'],
		   ['已签收','[4]已签收'],['已中转','[5]已中转'],['已收回单','[6]已收回单'],
		   ['异常','[7]异常']]
})

var STORE_TRANSTATE = new Ext.data.SimpleStore
({   
	fields : ['value','display'],
	data: [['','----未选择----'],['已创建','[1]已创建'],
		   ['已发货','[2]已发货'],['已到港','[3]已到港'],
		   ['已签收','[4]已签收'],['已中转','[5]已中转'],
		   ['已收回单','[6]已收回单'],['异常','[7]异常']]
})

var STORE_DATATYPE = new Ext.data.SimpleStore
({   
	fields : ['value','display'],
	data: [['','----未选择----'],['建单日期','建单日期'],
		   ['发货日期','发货日期'],['预计到货日期','预计到货日期'],
		   ['到货签收','到货签收']]
})

var STORE_ENTRUCK_TRANS_DATATYPE = new Ext.data.SimpleStore
({   
	fields : ['value','display'],
	data: [['','----未选择----'],['建单日期','建单日期'],['预计到货日期','预计到货日期']]
})

var STORE_STOCKTAKETYPE = new Ext.data.SimpleStore
({   
	fields : ['value','display'],
	data: [['','----未选择----'],['全盘','全盘'],
		   ['动盘','动盘']]
})

var STORE_STOCKTAKETYPE_C = new Ext.data.SimpleStore
({   
	fields : ['value','display'],
	data: [['全盘','全盘'],['动盘','动盘']]
})

var STORE_VERIFY = new Ext.data.SimpleStore
({      
      fields : ["value","display"],
      data : [['','---未选择---'],['已审核','已审核'], ['未审核','未审核']]
})

var STORE_AUTHORIZE = new Ext.data.SimpleStore
({      
      fields : ["value","display"],
      data : [['','---未选择---'],['已批准','已批准'], ['未批准','未批准']]
})

//通用校验
var REGEX_COMMON_S = new RegExp("^.{0,40}$");
var REGEX_COMMON_S_TEXT = '输入内容不超过40个字符';
var REGEX_COMMON_M = new RegExp("^.{0,80}$");
var REGEX_COMMON_M_TEXT = '输入内容不超过80个字符';
var REGEX_COMMON_H = new RegExp("^.{0,120}$");
var REGEX_COMMON_H_TEXT = '输入内容不超过120个字符';

var REGEX_COMMON_T = new RegExp("^[\\d]{0,11}$");
var REGEX_COMMON_T_TEXT = '输入单号为不超过11位数字';

var MAX_DOUBLE = 10000000;
var MAX_INT = 1000;

//正则式表
var REGEX_UID = new RegExp("^[a-zA-Z\\d\\u4e00-\\u9fa5][\\w\\u4e00-\\u9fa5]{0,15}$");
var REGEX_UID_TEXT = '用户名由字母、下划线"_"、数字、汉字组成，不以"_"开头，长度小于16个字符';
var REGEX_UPASSWORD = new RegExp("^[\\x21-\\x7e][\\x21-\\x7e]{0,31}$");
var REGEX_UPASSWORD_TEXT = '请输入不超过32位英文字母、数字、标点';
var REGEX_CITYID = new RegExp("^[a-zA-Z\\d]{0,8}$");
var REGEX_CITYID_TEXT = '城市编号为不超过8位数字或字母，建议为区号';
var REGEX_STORAGEID = new RegExp("^[a-zA-Z\\d]{0,6}$");
var REGEX_STORAGEID_TEXT = '仓库编号为不超过6位数字或字母';

var REGEX_EMAIL = new RegExp("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
var REGEX_EMAIL_TEXT = '邮件地址格式错误'; 
var REGEX_PHONE = new RegExp("((^\\d\\d{9}\\d$)|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)");
var REGEX_PHONE_TEXT = '请输入11位手机号码,3-4位区号，7-8位直播号码，1－4位分机号';
var REGEX_IDCARD = new RegExp("(^\\d\\d{16}[\\d|x]$)|(^\\d\\d{13}\\d$)");
var REGEX_IDCARD_TEXT = '身份证号码格式错误';

//键盘校验监听
function KEYDOWN_DATE_VALIDATOR(the,e)
{
	var k = e.getKey();
	if(k==8 || k==46)
		the.setValue('');
}

function KEYDOWN_LENGTH_VLIDATOR(the,e)
{
	var str = the.getValue();
	if(str.length > 40)
		str = str.substr(0,40);
	the.setValue(str);
}

function KEYDOWN_NO_VLIDATOR(the,e,length)
{
	var str = the.getValue();
	str = str.replace(new RegExp("[^\\d]","ig"),'');
	if(str.length > length)
		str = str.substr(0,length);
	the.setValue(str);
}

//计算弹出窗口位置
function GET_WIN_X(width)
{
	var left = (Ext.getBody().getWidth()-width)/2;
	return (left<50?50:left);
}

function GET_WIN_Y()
{
	var top = (Ext.getBody().getHeight()-380)/2;
	return (top<50?50:top);
}

function GET_WIN_Y_M(height)
{
	var top = (Ext.getBody().getHeight()-height)/2;
	return (top<50?50:top);
}

function CHECK_WIN_XY(width,x,y)
{
	var xy = {x:0,y:0};
	xy.x = x; xy.y = y;
	if(x < 0)
		xy.x = 5;
	if(x > (Ext.getBody().getWidth())-width)
		xy.x = Ext.getBody().getWidth()- width -5;
	if(y < 0)
		xy.y = 5;
	if(y > Ext.getBody().getHeight()-80)
		xy.y = Ext.getBody().getHeight() - 80;
	return xy;
}

var LISTENER_WIN_MOVE = {
	move : function(win,x,y){
		var pos = CHECK_WIN_XY(win.width,x,y);
    	if(pos.x != x || pos.y != y)
    		win.setPagePosition(pos.x,pos.y);
	}
}

var LISTENER_WIN_MOVE_FUNC = function(win,x,y)
{
	var pos = CHECK_WIN_XY(win.width,x,y);
	if(pos.x != x || pos.y != y)
		win.setPagePosition(pos.x,pos.y);
}

var Self = { JsonReader : null };
//自定义Reader
Self.JsonReader = Ext.extend(Ext.data.JsonReader, {  
    read : function(response){  
        var json = response.responseText;  
        var o = Ext.decode(json);  
        this.message = o.message;
        this.valid = o.valid;
        this.data = o.data;
        if(!o) {  
            throw {message: 'JsonReader.read: Json object not found'};  
        }  
        return this.readRecords(o);  
    }  
});  

//Grid Func
var GRID_AUTHORIZE = function(grid,url,param,index)
{
	var records = grid.getSelectionModel().getSelections();
	if(records.length <= 0)
		Ext.Msg.show({
			title : '操作提示',
			msg : '至少选择一条记录！',
			buttons : Ext.Msg.OK,
			icon : Ext.Msg.WARNING
		});
	else
	{
		var params = {};
		for(var i=0; i<records.length; i++)
		{
			params[param+'['+i.toString()+']'] = records[i].get(index);
		}
		Ext.Msg.confirm('操作确认','确认要批准选中的['+records.length+']条记录?<br>提示 ：操作只会批准未批准的单据，已批准的单据将自动忽略！',function(btn)
		{
			if(btn == 'yes')
			{
				var mask = new Ext.LoadMask(grid.getEl(), {msg:"正在处理,请稍等..."});
				mask.show();
				Ext.Ajax.request({
					url: url,
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							Ext.Msg.show({
								title : '批准错误',
								msg : result.message,
								buttons : Ext.Msg.OK,
								icon : Ext.Msg.WARNING
							});		
						}else{
							grid.getStore().reload();
						}
						mask.hide();
					},
					failure: function(response,options){
						AJAX_SERVER_FAILURE_CALLBACK(response,options,'操作请求失败');
					},
					params: params
				});//Ajax
			}
		});//Msg confirm
	}//else
}//handler

var GRID_VERIFY = function(grid,url,param,index)
{
	var records = grid.getSelectionModel().getSelections();
	if(records.length <= 0)
		Ext.Msg.show({
			title : '操作提示',
			msg : '至少选择一条记录！',
			buttons : Ext.Msg.OK,
			icon : Ext.Msg.WARNING
		});
	else
	{
		var params = {};
		for(var i=0; i<records.length; i++)
		{
			params[param+'['+i.toString()+']'] = records[i].get(index);
		}
		Ext.Msg.confirm('操作确认','确认审核选中的['+records.length+']条记录?<br>提示：操作只会审核未审核的单据，已审核的单据将自动忽略！',function(btn)
		{
			if(btn == 'yes')
			{
				var mask = new Ext.LoadMask(grid.getEl(), {msg:"正在审核,请稍等..."});
				mask.show();
				Ext.Ajax.request({
					url: url,
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							Ext.Msg.show({
								title : '审核错误',
								msg : result.message,
								buttons : Ext.Msg.OK,
								icon : Ext.Msg.WARNING
							});		
						}else{
							grid.getStore().reload();
						}
						mask.hide();
					},
					failure: function(response,options){
						AJAX_SERVER_FAILURE_CALLBACK(response,options,'操作请求失败');
					},
					params: params
				});//Ajax
			}
		});//Msg confirm
	}//else
}//handler

var GRID_FINANCE_SAVE = function(grid,func,index)
{
	var records = grid.getSelectionModel().getSelections();
	if(records.length <= 0)
	{
		Ext.Msg.show({
			title : '操作提示',
			msg : '至少选择一条记录！',
			buttons : Ext.Msg.OK,
			icon : Ext.Msg.WARNING
		});
		return;
	}
	func(records,index);
}//handler

var GRID_ARCHIVE = function(grid,url,param,index)
{
	var records = grid.getSelectionModel().getSelections();
	if(records.length <= 0)
		Ext.Msg.show({
			title : '操作提示',
			msg : '至少选择一条记录！',
			buttons : Ext.Msg.OK,
			icon : Ext.Msg.WARNING
		});
	else
	{
		var params = {};
		for(var i=0; i<records.length; i++)
		{
			params[param+'['+i.toString()+']'] = records[i].get(index);
		}
		Ext.Msg.confirm('操作确认','确认要归档选中的['+records.length+']条记录?',function(btn)
		{
			if(btn == 'yes')
			{
				var mask = new Ext.LoadMask(grid.getEl(), {msg:"正在归档,请稍等..."});
				mask.show();
				Ext.Ajax.request({
					url: url,
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							Ext.Msg.show({
								title : '归档错误',
								msg : result.message,
								buttons : Ext.Msg.OK,
								icon : Ext.Msg.WARNING
							});		
						}else{
							grid.getStore().reload();
						}
						mask.hide();
					},
					failure: function(response,options){
						AJAX_SERVER_FAILURE_CALLBACK(response,options,'操作请求失败');
					},
					params: params
				});//Ajax
			}
		});//Msg confirm
	}//else
}//handler

var GRID_UNARCHIVE = function(grid,url,param,index)
{
	var records = grid.getSelectionModel().getSelections();
	if(records.length <= 0)
		Ext.Msg.show({
			title : '操作提示',
			msg : '至少选择一条记录！',
			buttons : Ext.Msg.OK,
			icon : Ext.Msg.WARNING
		});
	else
	{
		var params = {};
		for(var i=0; i<records.length; i++)
		{
			params[param+'['+i.toString()+']'] = records[i].get(index);
		}
		Ext.Msg.confirm('操作确认','确认要取消归档选中的['+records.length+']条记录?',function(btn)
		{
			if(btn == 'yes')
			{
				var mask = new Ext.LoadMask(grid.getEl(), {msg:"正在取消归档,请稍等..."});
				mask.show();
				Ext.Ajax.request({
					url: url,
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							Ext.Msg.show({
								title : '取消归档错误',
								msg : result.message,
								buttons : Ext.Msg.OK,
								icon : Ext.Msg.WARNING
							});		
						}else{
							grid.getStore().reload();
						}
						mask.hide();
					},
					failure: function(response,options){
						AJAX_SERVER_FAILURE_CALLBACK(response,options,'操作请求失败');
					},
					params: params
				});//Ajax
			}
		});//Msg confirm
	}//else
}//handler

var GRID_DELETE = function(grid,url,param,index)
{
	var records = grid.getSelectionModel().getSelections();
	if(records.length <= 0)
		Ext.Msg.show({
			title : '操作提示',
			msg : '至少选择一条记录！',
			buttons : Ext.Msg.OK,
			icon : Ext.Msg.WARNING
		});
	else
	{
		var params = {};
		for(var i=0; i<records.length; i++)
		{
			params[param+'['+i.toString()+']'] = records[i].get(index);
		}
		Ext.Msg.confirm('操作确认','确认要删除选中的['+records.length+']条记录?',function(btn)
		{
			if(btn == 'yes')
			{
				var mask = new Ext.LoadMask(grid.getEl(), {msg:"正在删除,请稍等..."});
				mask.show();
				Ext.Ajax.request({
					url: url,
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							Ext.Msg.show({
								title : '删除错误',
								msg : result.message,
								buttons : Ext.Msg.OK,
								icon : Ext.Msg.WARNING
							});		
						}else{
							grid.getStore().reload();
						}
						mask.hide();
					},
					failure: function(response,options){
						AJAX_SERVER_FAILURE_CALLBACK(response,options,'操作请求失败');
						mask.hide();
					},
					params: params
				});//Ajax
			}
		});//Msg confirm
	}//else
}//handler

var GRID_PRINT = function(grid,url,index)
{
	var records = grid.getSelectionModel().getSelections();
	if(records.length <= 0)
		Ext.Msg.show({
			title : '操作提示',
			msg : '至少选择一条记录！',
			buttons : Ext.Msg.OK,
			icon : Ext.Msg.WARNING
		});
	else
	{
		Ext.Msg.confirm('操作确认','确认要打印选中的['+records.length+']张单据?',function(btn)
		{
			if(btn == 'yes')
			{
				var src = url+"?pid=";
				var params = "";
				for(var i=0; i<records.length; i++)
				{
					if(i!=0) params += "_";
					params += records[i].get(index);
				}
				src += params;
				window.open(src);
			}
		});//Msg confirm
	}//else
}//handler

var GRID_M_DELETE = function(grid,url,param,mindex)
{
	var records = grid.getSelectionModel().getSelections();
	if(records.length <= 0)
		Ext.Msg.show({
			title : '操作提示',
			msg : '至少选择一条记录！',
			buttons : Ext.Msg.OK,
			icon : Ext.Msg.WARNING
		});
	else
	{
		var params = {};
		for(var i=0; i<records.length; i++)
		{
			for(var j=0; j<mindex.length; j++)
				params[param+'['+i.toString()+'].'+mindex[j]] = records[i].get(mindex[j]);
		}
		Ext.Msg.confirm('操作确认','确认要删除选中的['+records.length+']条记录?',function(btn)
		{
			if(btn == 'yes')
			{
				var mask = new Ext.LoadMask(grid.getEl(), {msg:"正在删除,请稍等..."});
				mask.show();
				Ext.Ajax.request({
					url: url,
					method: 'POST',
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(!result.success){
							Ext.Msg.show({
								title : '删除错误',
								msg : result.message,
								buttons : Ext.Msg.OK,
								icon : Ext.Msg.WARNING
							});		
						}else{
							grid.getStore().reload();
						}
						mask.hide();
					},
					failure: function(response,options){
						AJAX_SERVER_FAILURE_CALLBACK(response,options,'操作请求失败');
						mask.hide();
					},
					params: params
				});//Ajax
			}
		});//Msg confirm
	}//else
}//handler

function GRID_EDITDETAIL(grid,func,index)
{
	var records = grid.getSelectionModel().getSelections();
	if(records.length != 1)
		Ext.Msg.show({
			title : '操作提示',
			msg : '请选择一条记录！',
			buttons : Ext.Msg.OK,
			icon : Ext.Msg.WARNING
		});	
	else
	{
		func(records[0].get(index));
	}
}

function GRID_M_EDITDETAIL(grid,func,mindex)
{
	var records = grid.getSelectionModel().getSelections();
	if(records.length != 1)
		Ext.Msg.show({
			title : '操作提示',
			msg : '请选择一条记录！',
			buttons : Ext.Msg.OK,
			icon : Ext.Msg.WARNING
		});	
	else
	{
		var values = {};
		for(var i=0; i<mindex.length; i++)
		{
			values[mindex[i]]=records[0].get(mindex[i]);
		}
		func(values);
	}
}

var GRID_LIST_DELETE = function(grid)
{
	var records = grid.getSelectionModel().getSelections();
	if(records.length <= 0)
		Ext.Msg.show({
			title : '操作提示',
			msg : '至少选择一条记录！',
			buttons : Ext.Msg.OK,
			icon : Ext.Msg.WARNING
		});
	else
	{
		var store = grid.getStore();
		for(var i=0; i<records.length; i++)
		{
			store.remove(records[i]);
		}
		grid.view.refresh();
	}
}

//COMBOBOX
function LISTENER_COMBOBOX_BEFOREQUERY(qe) 
{
    var combo = qe.combo;
    var q = qe.query;
    var forceAll = qe.forceAll;
    if (forceAll === true || (q.length >= this.minChars)) {
        if (this.lastQuery !== q) {
            this.lastQuery = q;
            if (this.mode == 'local') {
                this.selectedIndex = -1;
                if (forceAll) {
                    this.store.clearFilter();
                } else {
                    // 检索的正则
                	q.replace(new RegExp("[^\\w\\u4e00-\\u9fa5]","g"),'');
                    var regExp = new RegExp(".*" + q + ".*", "i");
                    // 执行检索
                    this.store.filterBy(function(record, id) {
                        // 得到每个record的项目名称值
                        var text = record.get(combo.displayField);
                        return regExp.test(text);
                    });
                }
                this.onLoad();
            } else {
                this.store.baseParams[this.queryParam] = q;
                this.store.load({
                    params: this.getParams(q)
                });
                this.expand();
            }
        } else {
            this.selectedIndex = -1;
            this.onLoad();
        }
    }
    return false;
}

var MAX_LIST_COUNT = 200;
var MAX_TRANS_GOODS_COUNT = 50;
var MAX_TRANS_SHEET_COUNT = 50;
var MAX_STORAGE_GOODS_COUNT = 50;

var ISINLIST = function(grid,field,value)
{
	var count = grid.getStore().getCount();
	var store = grid.getStore();
	for(var i=0; i<count; i++)
	{
		var record = store.getAt(i);
		var mark = 0;
		for(var j=0; j<field.length; j++)
		{
			if(value[j]==record.get(field[j]))
				mark++;
		}
		if(mark==field.length)
		{
			grid.getSelectionModel().clearSelections();
			grid.getSelectionModel().selectRow(i);
			return true;
		}
	}
	return false;
}

var CHECKLISTSAME = function(grid,field,defaultValue)
{
	var count = grid.getStore().getCount();
	var store = grid.getStore();
	if(count > 0)
	{
		if(defaultValue == store.getAt(0).get(field))
			return true;
	}
	for(var i=0; i<count-1; i++)
	{
		var record = store.getAt(i);
		for(var j=i+1; j<count; j++)
		{
			var tmp = store.getAt(j);
			if(defaultValue == tmp.get(field))
				return true;
			if(tmp.get(field) == record.get(field))
				return true;
		}
	}
	return false;
}

var SETFORMPARAMTOSTORE = function(form,store)
{
	if(!form.getForm().isValid())
	{
		Ext.Msg.show({
			title : '操作提示',
			msg : '请输入正确的查询条件！',
			buttons : Ext.Msg.OK,
			icon : Ext.Msg.WARNING
		});
		return false;
	}
	var params = form.getForm().getValues(true);
	var paramTmp = params.split('&');
	var field = ''; var value = '';
	for(var i=0; i<paramTmp.length; i++)
	{
		field = paramTmp[i].split('=')[0];
		value = paramTmp[i].split('=')[1];
		value = decodeURI(value);
		store.baseParams[field] = value==null?'':value;
	}
	return true;
}

var ONRIGHTCLICKGRID = function(grid,index,e,menu)
{
	e.preventDefault();
	if(grid.getStore().getCount()==0)
		return;
	if(grid.getSelectionModel().isSelected(index) !== true
	    || grid.getSelectionModel().getSelections().length >= 1) 
	{
		grid.getSelectionModel().clearSelections();
		grid.getSelectionModel().selectRow(index);
	}
	menu.showAt(e.getXY());
}

var STORE_CITY = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'City.queryAllNameID.action',
				method: 'POST'
			}),
	reader : new Self.JsonReader({
				root : 'resultMapList'
			}, 
			[{
				name : 'cityID'
			},{
				name : 'jointName'
			},{
				name : 'name'
			},{
				name : 'nameID'
			}]
		)
});

function STORE_CITY_LOAD()
{
	STORE_CITY.load({
    	callback:function(record,option,success){
					STORE_CALLBACK(STORE_CITY.reader.message,STORE_CITY.reader.valid,success);
				 } 
	});
}

var STORE_CARTYPE = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'CarType.queryNameID.action',
				method: 'POST'
			}),
	reader : new Self.JsonReader({
				root : 'resultMapList'
			}, 
			[{
				name : 'carTypeID'
			},{
				name : 'carTypeName'
			}]
		)
});

function STORE_CARTYPE_LOAD()
{
	STORE_CARTYPE.load({
    	callback:function(record,option,success){
					STORE_CALLBACK(STORE_CARTYPE.reader.message,STORE_CARTYPE.reader.valid,success);
				 } 
	});
}

var STORE_TRANS_UNIT = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'FreightContractor.queryNameID.action',
				method: 'POST'
			}),
	reader : new Self.JsonReader({
				root : 'resultMapList'
			}, 
			[{
				name : 'freightContractorID'
			},{
				name : 'jointName'
			}]
		)
});

function STORE_TRANS_UNIT_LOAD()
{
	STORE_TRANS_UNIT.load({
    	callback:function(record,option,success){
					STORE_CALLBACK(STORE_TRANS_UNIT.reader.message,STORE_TRANS_UNIT.reader.valid,success);
				 } 
	});
}

var STORE_CUSTOMER = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'Customer.queryNameID.action',
				method: 'POST'
			}),
	reader : new Self.JsonReader({
				root : 'resultMapList'
			}, 
			[{
				name : 'customerID'
			},{
				name : 'jointName'
			}]
		)
});

function STORE_CUSTOMER_LOAD(type)
{
	STORE_CUSTOMER.load({
    	callback:function(record,option,success){
					STORE_CALLBACK(STORE_CUSTOMER.reader.message,STORE_CUSTOMER.reader.valid,success);
				 },
		params: { 'customerDTO.type': type }
	});
}

var STORE_CUSTOMER_ALL = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'Customer.queryNameID.action',
				method: 'POST'
			}),
	reader : new Self.JsonReader({
				root : 'resultMapList'
			}, 
			[{
				name : 'customerID'
			},{
				name : 'jointName'
			}]
		)
});

function STORE_CUSTOMER_ALL_LOAD()
{
	STORE_CUSTOMER_ALL.load({
    	callback:function(record,option,success){
					STORE_CALLBACK(STORE_CUSTOMER_ALL.reader.message,STORE_CUSTOMER_ALL.reader.valid,success);
				 },
		params: { 'customerDTO.type': '' }
	});
}

var STORE_TRANS_WAY = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'FreightRoute.queryNameID.action',
				method: 'POST'
			}),
	reader : new Self.JsonReader({
				root : 'resultMapList'
			}, 
			[{
				name : 'freightRouteID'
			},{
				name : 'jointName'
			}]
		)
});

function STORE_TRANS_WAY_LOAD()
{
	STORE_TRANS_WAY.load({
    	callback:function(record,option,success){
					STORE_CALLBACK(STORE_TRANS_WAY.reader.message,STORE_TRANS_WAY.reader.valid,success);
				 } 
	});
}

var STORE_COSTCENTER = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'CostCenter.queryNameID.action',
				method: 'POST'
			}),
	reader : new Self.JsonReader({
				root : 'resultMapList'
			}, 
			[{
				name : 'costCenterID'
			}]
		)
});

function STORE_COSTCENTER_LOAD()
{
	STORE_COSTCENTER.load({
    	callback:function(record,option,success){
					STORE_CALLBACK(STORE_COSTCENTER.reader.message,STORE_COSTCENTER.reader.valid,success);
				 } 
	});
}

var STORE_SELLCENTER = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'SellCenter.queryNameID.action',
				method: 'POST'
			}),
	reader : new Self.JsonReader({
				root : 'resultMapList'
			}, 
			[{
				name : 'sellCenterID'
			}]
		)
});

function STORE_SELLCENTER_LOAD()
{
	STORE_SELLCENTER.load({
    	callback:function(record,option,success){
					STORE_CALLBACK(STORE_SELLCENTER.reader.message,STORE_SELLCENTER.reader.valid,success);
				 } 
	});
}

var STORE_CACHE_GOODS = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'FreightManifest.queryCargoID.action',
				method: 'POST'
			}),
	reader : new Self.JsonReader({
				root : 'resultMapList'
			}, 
			[{
				name : 'cargoID'
			}]
		)
});

function STORE_CACHE_GOODS_LOAD()
{
	STORE_CACHE_GOODS.load({
    	callback:function(record,option,success){
					STORE_CALLBACK(STORE_CACHE_GOODS.reader.message,STORE_CACHE_GOODS.reader.valid,success);
				 } 
	});
}

var STORE_TAKE_UNIT = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'ShipmentManifest.queryConsigneeCompanies.action',
				method: 'POST'
			}),
	reader : new Self.JsonReader({
				root : 'resultMapList'
			}, 
			[{
				name : 'consigneeCompany'
			}]
		)
});

function STORE_TAKE_UNIT_LOAD()
{
	STORE_TAKE_UNIT.load({
    	callback:function(record,option,success){
					STORE_CALLBACK(STORE_TAKE_UNIT.reader.message,STORE_TAKE_UNIT.reader.valid,success);
				 } 
	});
}

var STORE_STORAGE = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'Warehouse.queryNames.action',
				method: 'POST'
			}),
	reader : new Self.JsonReader({
				root : 'resultMapList'
			}, 
			[{
				name : 'warehouseID'
			},
			{
				name : 'name'
			}]
		)
});

function STORE_STORAGE_LOAD()
{
	STORE_STORAGE.load({
    	callback:function(record,option,success){
					STORE_CALLBACK(STORE_STORAGE.reader.message,STORE_STORAGE.reader.valid,success);
				 } 
	});
}

var STORE_GOODS = new Ext.data.Store({	
	proxy : new Ext.data.HttpProxy({
				url : 'Item.queryNameID.action',
				method: 'POST'
			}),
	reader : new Self.JsonReader({
				root : 'resultMapList'
			}, 
			[{
				name : 'itemID'
			},{
				name : 'jointName'
			}]
		)
});

function STORE_GOODS_LOAD()
{
	STORE_GOODS.load({
    	callback:function(record,option,success){
					STORE_CALLBACK(STORE_GOODS.reader.message,STORE_GOODS.reader.valid,success);
				 } 
	});
}

//file opt
var DOWN_FRAME = {};

function FILE_DOWNLOAD(url,idName,id)
{
//	if(typeof(DOWN_FRAME.iframe)== "undefined")
//	{
//		var iframe = document.createElement("iframe");
//		DOWN_FRAME.iframe = iframe;
//		document.body.appendChild(DOWN_FRAME.iframe); 
//	}
//	DOWN_FRAME.iframe.src = url+'?'+idName+'='+id;
//	DOWN_FRAME.iframe.style.display = "none";
	window.open(url+'?'+idName+'='+id);
}

function TEMPLATE_DOWNLOAD(url)
{
	window.open(url);
	//location.href = url;
}

function FILE_IMPORT(url)
{
	
}

//format
function CHANGEDECIMAL(num)
{
	return Math.round(num*100)/100;
}

function CHANGEDECIMAL_M(num,d)
{
	var base = 10;
	for(var i=0;i<d-1;i++) base*=10;
	return Math.round(num*base)/base;
}

function TREE_CHECKCHANGE(node,checked)
{
	var parentNode = node.parentNode;
	if (parentNode != null) {
		CHECK_PARENT(parentNode, checked);
	}
	node.expand();
	node.attributes.checked = checked;
//	node.eachChild(function(child) {
//	    child.ui.toggleCheck(checked);
//	    child.attributes.checked = checked;
//	    child.fireEvent('checkchange', child, checked);
//	});
}

function TREE_DFS(node,checked,bOpen)
{
	if(bOpen) node.expand();
	node.attributes.checked = checked;
	node.eachChild(function(child) {
	    child.ui.toggleCheck(checked);
	    child.attributes.checked = checked;
	    TREE_DFS(child,checked);
	});
}

function CHECK_PARENT(node,checked)
{
	var checkbox = node.getUI().checkbox;
	if (typeof checkbox == 'undefined')
		return false;
	if (!(checked ^ checkbox.checked))
		return false;
	var childNodes = node.childNodes;
	var mark = false;
	if (childNodes || childNodes.length > 0) {
		for (var i = 0; i < childNodes.length; i++)
		{
			if (childNodes[i].getUI().checkbox.checked)
			{mark = true;break;}
		}
	}
	if (!checked && mark)
		return false;
	checkbox.checked = checked;
	node.attributes.checked = checked;
	node.getOwnerTree().fireEvent('check', node, checked);
	if (node.parentNode !== null) {
		CHECK_PARENT(node.parentNode,checked);
	}
}

function TREE_SETCHECKEDNODES(nodes,pl)
{
	var bEmpty = false;
	for(var i=0; i<nodes.length; i++)
	{
		if(nodes[i].hasChildNodes())
		{
			bEmpty = TREE_SETCHECKEDNODES(nodes[i].childNodes,pl);
		}
		var id=nodes[i].id;
		var tmp = id.substring(id.indexOf('_')+1);
		var key = tmp.substring(0,tmp.indexOf('.'));
		var type = tmp.substring(tmp.indexOf('.')+1);
		if(type=='empty')
		{	
			if(bEmpty){
				nodes[i].ui.toggleCheck(true);
	    		nodes[i].attributes.checked = true;
    		}
			continue;
		}
		if (pl[key].permission & P_F[type])
		{
			nodes[i].ui.toggleCheck(true);
    		nodes[i].attributes.checked = true;
    		bEmpty = true;
		}
	}
	return bEmpty;
}

function RESET_TREE(treepanel,bOpen)
{
	var nodes = treepanel.getRootNode().childNodes;
	for(var i=0;i<nodes.length;i++)
	{
		TREE_DFS(nodes[i],false,bOpen);
	}
	treepanel.collapseAll();
	treepanel.getRootNode().expand();
}

var P_K = [
	'UserAction',
	'BarcodeAction',
	'CarAction',
	'CarTypeAction',
	'CityAction',
	'CostCenterAction',
	'CustomerAction',
	'EmployeeProfileAction',
	'EmployeeSalaryAction',
	'FinancialLogAction',
	'FreightContractorAction',
	'FreightCostAction',
	'FreightIncomeAction',
	'FreightManifestAction',
	'FreightRouteAction',
	'FreightStateAction',
	'InventoryManifestAction',
	'ItemAction',
	'MessageAction',
	'PaymentCollectionAction',
	'RoleAction',
	'SellCenterAction',
	'ShipmentCostAction',
	'ShipmentManifestAction',
	'SpecialStockIncomeAction',
	'StockFinanceAction',
	'StockinFinanceAction',
	'StockinManifestAction',
	'StockItemAction',
	'StockoutFinanceAction',
	'StockoutManifestAction',
	'StockTransferFinanceAction',
	'StockTransferManifestAction',
	'WarehouseAction',
	'SystemInfoAction'
];

var P_F = {
	'query':0x00000001,
	'insert':0x00000002,
	'update':0x00000004,
	'delete':0x00000008,
	'download':0x00000010,
	'importFile':0x00000030,
	'archive':0x00000040,
	'unarchive':0x00000080,
	'updateState':0x00000100,
	'approve':0x00000200,
	'audit':0x00000400,
	'account':0x00000800,
	'export':0x00001000,
	'print':0x000002000
};

function GET_P_L()
{
	var pl = {};
	for(var i=0; i<P_K.length; i++)
	{
		pl[P_K[i]]={actionName:'Logistics.Servlet.'+P_K[i],permission:0};
	}
	return pl;
};

var P_LIST = [
	'sellCenter',
	'manageOutStorageSheet',
	'storageFinance',
	'queryStorage',
	'transportationUnit',
	'manageStocktake',
	'manageWorkerSalary',
	'enterStorageFinance',
	'carType',
	'outStorageFinance',
	'systemInformation',
	'trackCOD',
	'manageTransportationSheet',
	'financeLog',
	'transferStorageFinance',
	'manageTransferStorageSheet',
	'goodsInformation',
	'cityInformation',
	'manageEnterStorageSheet',
	'manageBarCode',
	'transportationPay',
	'costCenter',
	'transportationWay',
	'specialStorageIncome',
	'manageWorkerInfo',
	'storageInformation',
	'transportationTrack',
	'manageNews',
	'customerInformation',
	'manageRole',
	'manageUser',
	'carInformation',
	'manageEntruckingSheet',
	'entruckingPay',
	'transportationIncome'
];

var G_P_LIST = {};

function MAKE_P_LIST(data)
{
	for(var i=0; i<P_K.length; i++)
	{
		G_P_LIST[P_LIST[i]]=data[P_LIST[i]];
	}
};

function IsPermition(id,bNotice)
{
	if(!G_P_LIST[id])
	{
		if(bNotice){
			Ext.Msg.show({
				title : '操作提示',
				msg : '对不起，您没有该功能操作权限！',
				buttons : Ext.Msg.OK,
				icon : Ext.Msg.WARNING
			});
		}
		return false;
	}
	return true;
}

var GRID_EXPORT = function(grid,type,index)
{
	var records = grid.getSelectionModel().getSelections();
	if(records.length <= 0)
		Ext.Msg.show({
			title : '操作提示',
			msg : '至少选择一条记录！',
			buttons : Ext.Msg.OK,
			icon : Ext.Msg.WARNING
		});
	else
	{
		Ext.Msg.confirm('操作确认','确认要导出选中的['+records.length+']条记录?',function(btn)
		{
			if(btn == 'yes')
			{
				var url="";
				switch(type)
				{
					case 'ESF':url='StockinFinance.export.action';break;//enterStoreage
					case 'EP':url='ShipmentCost.export.action';break;//entruckingPay
					case 'OSF':url='StockoutFinance.export.action';break;//outStorage
					case 'SSF':url='SpecialStockIncome.export.action';break;//specialStorageIncome
					case 'SF':url='StockFinance.export.action';break;//storage
					case 'MCOD':url='PaymentCollection.export.action';break;//COD
					case 'TSSF':url='StockTransferFinance.export.action';break;//transferStorage
					case 'TI':url='FreightIncome.export.action';break;//transIncome
					case 'TP':url='FreightCost.export.action';break;//transPay
					case 'MBC':url='Barcode.export.action';break;//barcode
					default: Ext.Msg.show({
						title : '操作提示',
						msg : '该功能尚未开放！',
						buttons : Ext.Msg.OK,
						icon : Ext.Msg.WARNING
					});return;
				}
				var src = url+"?eid=";
				var params = "";
				for(var i=0; i<records.length; i++)
				{
					if(i!=0) params += "_";
					params += records[i].get(index);
				}
				src += params;
				window.open(src);
			}
		});//Msg confirm
	}//else
}//handler