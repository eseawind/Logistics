
Ext.onReady(function(){

/************************************Initialize************************************/	
	
        Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
        Ext.BLANK_IMAGE_URL = 'extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.form.TextField.prototype.msgTarget = 'side';

/********************************CreatePageElements*************************/
        
        //Create Navigation Menu TreePanel
        
        var treePanelManageNews = new Ext.tree.TreePanel({
        	
				border : false,
				rootVisible : false,
				border : false, //边框
				animate : true, //动画效果
				style: 'margin:5px 5px 5px 5px',		
				root : new Ext.tree.AsyncTreeNode({
							
				expanded : true,
				children : [{
							id : 'firstPage',
							text : '&nbsp;&nbsp;首页',
							leaf : true,
							iconCls: 'firstPage'
						},
						{
							id : 'manageNews',
							text : '&nbsp;&nbsp;信息管理',
							leaf : true,
							iconCls: 'manageNews'
						}]
					})
			});
		// 增加鼠标单击事件
		treePanelManageNews.on('click', treeClick);	
		
		 var treePanelManageTransportation = new Ext.tree.TreePanel({
        	
				border : false,
				rootVisible : false,
				border : false, // 边框
				animate : true, // 动画效果
				style: 'margin:5px 5px 5px 5px',				
				root : new Ext.tree.AsyncTreeNode({
							
				expanded : true,
				children : [{
							id : 'manageTransportationSheet',
							text : '&nbsp;&nbsp;运输单管理',
							leaf : true,
							iconCls: 'manageTransportationSheet'
						},
						{
							id : 'manageEntruckingSheet',
							text : '&nbsp;&nbsp;装车单管理',
							leaf : true,
							iconCls: 'manageEntruckingSheet'
						},
						{
							id : 'transportationTrack',
							text : '&nbsp;&nbsp;运输单跟踪',
							leaf : true,
							iconCls: 'transportationTrack'
						}]
				})
			});
		// 增加鼠标单击事件
	    treePanelManageTransportation.on('click', treeClick);	   
		    
	    var treePanelManageStorage = new Ext.tree.TreePanel({
        	
				border : false,
				rootVisible : false,
				border : false, // 边框
				animate : true, // 动画效果
				style: 'margin:5px 5px 5px 5px',				
				root : new Ext.tree.AsyncTreeNode({
							
				expanded : true,
				children : [
						{
							id : 'manageEnterStorageSheet',
							text : '&nbsp;&nbsp;入库单管理',
							leaf : true,
							iconCls: 'manageEnterStorageSheet'
						},
						{
							id : 'manageOutStorageSheet',
							text : '&nbsp;&nbsp;出库单管理',
							leaf : true,
							iconCls: 'manageOutStorageSheet'
						},
						{
							id : 'manageTransferStorageSheet',
							text : '&nbsp;&nbsp;移库单管理',
							leaf : true,
							iconCls: 'manageTransferStorageSheet'
						},
						{
							id : 'queryStorage',
							text : '&nbsp;&nbsp;库存查询',
							leaf : true,
							iconCls: 'queryStorage'
						},
						{
							id : 'manageStocktake',
							text : '&nbsp;&nbsp;盘点管理',
							leaf : true,
							iconCls: 'manageStocktake'
						},
						{
							id : 'manageBarCode',
							text : '&nbsp;&nbsp;条码管理',
							leaf : true,
							iconCls: 'manageBarCode'
						}]
				})
			});
		// 增加鼠标单击事件
	    treePanelManageStorage.on('click', treeClick);	   
	    
	     var treePanelFinance = new Ext.tree.TreePanel({
        	
				border : false,
				rootVisible : false,
				border : false, // 边框
				animate : true, // 动画效果
				style: 'margin:5px 5px 5px 5px',				
				root : new Ext.tree.AsyncTreeNode({
							
				expanded : true,
				children : [
						{
							id : 'transportationIncome',
							text : '&nbsp;&nbsp;运输单收入',
							leaf : true,
							iconCls: 'transportationIncome'
						},
						{
							id : 'transportationPay',
							text : '&nbsp;&nbsp;运输单支出',
							leaf : true,
							iconCls: 'transportationPay'
						},
						{
							id : 'entruckingPay',
							text : '&nbsp;&nbsp;装车单支出',
							leaf : true,
							iconCls: 'entruckingPay'
						},
						{
							id : 'enterStorageFinance',
							text : '&nbsp;&nbsp;入库收支',
							leaf : true,
							iconCls: 'enterStorageFinance'
						},
						{
							id : 'outStorageFinance',
							text : '&nbsp;&nbsp;出库收支',
							leaf : true,
							iconCls: 'outStorageFinance'
						},
						{
							id : 'transferStorageFinance',
							text : '&nbsp;&nbsp;移库收支',
							leaf : true,
							iconCls: 'transferStorageFinance'
						},
						{
							id : 'storageFinance',
							text : '&nbsp;&nbsp;仓储收支',
							leaf : true,
							iconCls: 'storageFinance'
						},
						{
							id : 'specialStorageIncome',
							text : '&nbsp;&nbsp;特殊仓储收支',
							leaf : true,
							iconCls: 'specialStorageIncome'
						},
						{
							id : 'trackCOD',
							text : '&nbsp;&nbsp;代收货款',
							leaf : true,
							iconCls: 'trackCOD'
						},
						{
							id : 'financeLog',
							text : '&nbsp;&nbsp;财务日志',
							leaf : true,
							iconCls: 'financeLog'
						}]
				})
			});
		// 增加鼠标单击事件
	    treePanelFinance.on('click', treeClick);
	    
	    var treePanelBasicData = new Ext.tree.TreePanel({
        	
				border : false,
				rootVisible : false,
				border : false, // 边框
				animate : true, // 动画效果
				style: 'margin:5px 5px 5px 5px',				
				root : new Ext.tree.AsyncTreeNode({
							
				expanded : true,
				children : [
						{
							id : 'transportationWay',
							text : '&nbsp;&nbsp;运输路线信息',
							leaf : true,
							iconCls: 'transportationWay'
						},
						{
							id : 'transportationUnit',
							text : '&nbsp;&nbsp;承运单位信息',
							leaf : true,
							iconCls: 'transportationUnit'
						},
						{
							id : 'carInformation',
							text : '&nbsp;&nbsp;车辆信息',
							leaf : true,
							iconCls: 'carInformation'
						},
						{
							id : 'carType',
							text : '&nbsp;&nbsp;车辆类型',
							leaf : true,
							iconCls: 'carType'
						},
						{
							id : 'goodsInformation',
							text : '&nbsp;&nbsp;物料信息',
							leaf : true,
							iconCls: 'goodsInformation'
						},
						{
							id : 'customerInformation',
							text : '&nbsp;&nbsp;客户信息',
							leaf : true,
							iconCls: 'customerInformation'
						},
						{
							id : 'costCenter',
							text : '&nbsp;&nbsp;成本中心',
							leaf : true,
							iconCls: 'costCenter'
						},
						{
							id : 'sellCenter',
							text : '&nbsp;&nbsp;销售中心',
							leaf : true,
							iconCls: 'sellCenter'
						},
						{
							id : 'storageInformation',
							text : '&nbsp;&nbsp;仓库信息',
							leaf : true,
							iconCls: 'manageStorage'
						},
						{
							id : 'cityInformation',
							text : '&nbsp;&nbsp;城市信息',
							leaf : true,
							iconCls: 'cityInformation'
						}]
				})
			});
		// 增加鼠标单击事件
	    treePanelBasicData.on('click', treeClick);	   
	    
	     var treePanelManageWorker = new Ext.tree.TreePanel({
        	
				border : false,
				rootVisible : false,
				border : false, // 边框
				animate : true, // 动画效果
				style: 'margin:5px 5px 5px 5px',			
				root : new Ext.tree.AsyncTreeNode({
							
				expanded : true,
				children : [{
							id : 'manageWorkerInfo',
							text : '&nbsp;&nbsp;员工信息',
							leaf : true,
							iconCls: 'personalInformation'
						},
						{
							id : 'manageWorkerSalary',
							text : '&nbsp;&nbsp;工资管理',
							leaf : true,
							iconCls: 'manageSalary'
						}]
					})
			});
		 // 增加鼠标单击事件
		 treePanelManageWorker.on('click', treeClick);
	    
	    var treePanelManageSystem = new Ext.tree.TreePanel({
        	
				border : false,
				rootVisible : false,
				border : false, // 边框
				animate : true, // 动画效果
				style: 'margin:5px 5px 5px 5px',			
				root : new Ext.tree.AsyncTreeNode({
							
				expanded : true,
				children : [{
							id : 'editPersonalPassword',
							text : '&nbsp;&nbsp;修改密码',
							leaf : true,
							iconCls: 'editUserPassword'
						},
						{
							id : 'manageUser',
							text : '&nbsp;&nbsp;用户管理',
							leaf : true,
							iconCls: 'manageUser'
						}, {
							id : 'manageRole',
							text : '&nbsp;&nbsp;角色管理',
							leaf : true,
							iconCls: 'manageRole'
						}, {
							id : 'systemInformation',
							text : '&nbsp;&nbsp;系统信息',
							leaf: true,
							iconCls: 'systemInformation'
						}]
					})
			});
		 // 增加鼠标单击事件
		 treePanelManageSystem.on('click', treeClick);	
	    
        //Create Navigation Menu
        var navigationMenu = new Ext.Panel({
 
	            region: 'west',
	            id: 'navigationMenu', 
	            title: '&nbsp;&nbsp;导航菜单',
	            split: true,
	            width: 175,
	            minSize: 150,
	            maxSize: 200,
	            collapsible: true,
	            margins: '0 0 5 5',
	    
	            iconCls:'navigationMenu',
	            layout: {
	                type: 'accordion',
	                animate: true
	            },
	            items: [{
	                id: 'news',
	                title: '&nbsp;&nbsp;新闻公告',
	                border: false,
	                iconCls: 'news',
	                items: treePanelManageNews
	            }, {
	                title: '&nbsp;&nbsp;运输管理',
	                id: 'manageTransportation',
	                border: false,
	                iconCls: 'manageTransportation',
	                items: treePanelManageTransportation
	            }, {
	                title: '&nbsp;&nbsp;仓储管理',
	                id: 'manageStorage',
	                border: false,
	                iconCls: 'manageStorage',
	                items: treePanelManageStorage
	            }, {
	                title: '&nbsp;&nbsp;业务收支',
	                id: 'manageFinance',
	                border: false,
	                iconCls: 'manageFinance',
	                items: treePanelFinance
	            }, {
	                title: '&nbsp;&nbsp;基础数据',
	                id: 'basicData',
	                border: false,
	                iconCls: 'basicData',
	                items: treePanelBasicData
	            }, {
	                title: '&nbsp;&nbsp;人事管理',
	                id: 'manageWorker',
	                border: false,
	                iconCls: 'manageWorker',
	                items: treePanelManageWorker
	            }, {
	                title: '&nbsp;&nbsp;系统管理',
	                id: 'manageSystem',
	                border: false,
	                iconCls: 'manageSystem',
	                items: treePanelManageSystem
	            }]
	  
        });
		
        //Create TabPanel
        var contentPanel = new Ext.TabPanel({
                region: 'center',
                deferredRender: false,
                id: 'contentPanel',
                margins: '0 5 5 0',
                activeTab: 0,
                animScroll : true,
            	enableTabScroll : true,
            	autoDestroy: false,
                items: [ {
                    contentEl: 'firstPageNews',
                    id: 'tab_firstPage',
                    title: '首页',
                    iconCls: 'firstPage',
                    border: false
                }],
                defaults: {
                	autoScroll: true
                },
                listeners: {
                	tabchange: function(tab,panel){
                		var id = panel.getId();
                		var start = id.indexOf('_');
                		updateSwitch(id.substring(start+1));
                	}
                }
            });
            
		//Create Viewport		
        var viewport = new Ext.Viewport({
            layout: 'border',
            renderTo: Ext.getBody(),
            items: [{
            			border: false,
            			frame: false,
            			layout: 'fit',
            			region: 'north',
	            		border: false,
	            		height: 28,//Important
	            		margins: '0 5 0 5',
            			
	            	    items: new Ext.Toolbar({
	            	    
	            	    border: false,
	            		items:[{
							xtype: 'tbspacer'
						},
						{
							iconCls: 'btnLogin'
						},
						{
							xtype: 'tbspacer'
						},
						{
							xtype: 'label',
							text : "当前登录用户："
						},
						{
							xtype: 'tbspacer',
							width :5
						},
						{
							id: 'currentUser',
							autoShow: true,
							xtype: 'label',
							text : ""
						},
						{
							xtype: 'tbfill'
						},
						{
							xtype: 'tbspacer',
							width :5
						},
						{
							id: 'timeToolbar',
							xtype: 'label',
							text : ""
						},
						{
							xtype: 'tbspacer'
						},'-',
						{
							text : "退出系统",
							iconCls: 'btnLogout',
							handler: function()
							{
								Ext.Msg.confirm('操作确认','确认退出系统?',function(btn)
								{
									if(btn == 'yes')
									{
										var mask = new Ext.LoadMask(viewport.getEl(), {msg:"正在退出,请稍等..."});
										mask.show();
										Ext.Ajax.request({
											url: 'logout.action',
											method: 'POST',
											success : function(response,options){
												window.location.href = 'index.jsp';
												mask.hide();
											},
											failure: function(response,options){
												AJAX_SERVER_FAILURE_CALLBACK(response,options,'操作请求失败');
												mask.hide();
											}
										});//Ajax
									}
								});//Msg confirm
							}
						},
						{
							xtype: 'tbspacer'
						}]
	            		
	            	})
            	}
            	,  
            	navigationMenu//left
              	,
              	contentPanel//center
            ]
        });
        
        //Initialize Main Page
        initPage();
        
    });

/********************************FunctionDefinition**********************************/ 
    var strTabShow = '#tab_firstPage';
    //Initialize Main Page
	function initPage()
	{
		initRemoteData();
		setTime();
		setWelcome();
		setInterval("setTime()",1000);/* 这个是跳动函数，后面是多久跳以毫秒，1000就是一秒跳动 */
	}
	
	//Show the Welcome Information
	function setWelcome()
	{
		
	}
	
    //Dynamic Time
    function setTime(){
		 var dt=new Date();
		 var arr_week=new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
		 var strWeek=arr_week[dt.getDay()];
		 var strHour=dt.getHours();
		 var strMinutes=dt.getMinutes();
		 var strSeconds=dt.getSeconds();
		 if (strMinutes<10) strMinutes="0"+strMinutes;
		 if (strSeconds<10) strSeconds="0"+strSeconds;
		 var strYear=dt.getFullYear()+"年";
		 var strMonth=(dt.getMonth()+1)+"月";
		 var strDay=dt.getDate()+"日";
		 var strTime=strHour+":"+strMinutes+":"+strSeconds;
		 var strDst = strYear+strMonth+strDay+"  "+strTime+"   "+strWeek;
		 Ext.getCmp('timeToolbar').setText(strDst);
	}
	var NewsNode = new Ext.tree.TreeNode(
		{
			id : 'manageNews',
			text : '&nbsp;&nbsp;信息管理',
			leaf : true,
			iconCls: 'manageNews'
		}
	);
	
	var TransTrackNode = new Ext.tree.TreeNode(
		{
			id : 'transportationTrack',
			text : '&nbsp;&nbsp;运输单跟踪',
			leaf : true,
			iconCls: 'transportationTrack'
		}
	);
	
    // 设置树的点击事件
    function treeClick(node, event) {
        if (node.isLeaf()) {
        	if(event!=null)
            	event.stopEvent();
            
            //菜单直接弹出窗口
            if(node.id == 'editPersonalPassword')
            {
            	onEditPersonalPassword();
            	return;
            }
            
            var contentPanel = Ext.getCmp('contentPanel');
            var tabPanel = contentPanel.getComponent('tab_'+node.id);
            if (!tabPanel) 
            {	
            	//被隐藏的页面判断
            	var hidePanel = Ext.getCmp('tab_'+node.id);
            	if(hidePanel)
            	{
            		tabPanel = contentPanel.add(hidePanel);
            		updateSwitch(node.id);
            		contentPanel.setActiveTab(tabPanel);
            		strTabShow += "#tab_" + node.id;
            	}
            	else
            	{//功能页面没有加载
            		if(!IsPermition(node.id,true))
            			return;
	                tabPanel = contentPanel.add({
					    id : 'tab_'+node.id,
					    title: node.text.substr(12),
					    iconCls: node.attributes.iconCls,
					    closable : true,
					    closeAction: 'hide',
						border: false,
						layout: 'border',
		                listeners: {
		                	beforeclose: function(panel){
		                		var start = strTabShow.indexOf('#tab_'+node.id);
		                		var end = start + ('#tab_'+node.id).length;
		                		strTabShow = strTabShow.substring(0,start) + strTabShow.substring(end);
		                		if(contentPanel.getActiveTab()===panel)
		                		{
		                			var p = strTabShow.lastIndexOf('#');
		                			var strCurTab = strTabShow.substring(p+1);
		                			contentPanel.setActiveTab(strCurTab);
		                		}
		                		contentPanel.remove(panel,false);
		                		return false;
		                	} 
		                } 
				    });
				    strTabShow += "#tab_" + node.id;
			    	//页面初始化函数
				    initSwitch(node);
				    contentPanel.setActiveTab(tabPanel);
				    tabPanel.syncSize();
            	}
            }
            else
            {//打开的功能页面切换
			   contentPanel.setActiveTab(tabPanel);
			}
        }
    }
    
function initSwitch(node)
{
	switch(node.id)
    {
    case 'firstPage':break;
    case 'manageNews':init_manageNews('tab_'+node.id);break;
    case 'manageTransportationSheet':init_manageTransportationSheet('tab_'+node.id);break;
    case 'manageEntruckingSheet':init_manageEntruckingSheet('tab_'+node.id);break;
    case 'transportationTrack':init_transportationTrack('tab_'+node.id);break;
    case 'manageBarCode':init_manageBarCode('tab_'+node.id);break;
    case 'manageEnterStorageSheet':init_manageEnterStorageSheet('tab_'+node.id);break;
    case 'manageOutStorageSheet':init_manageOutStorageSheet('tab_'+node.id);break;
    case 'manageTransferStorageSheet':init_manageTransferStorageSheet('tab_'+node.id);break;
    case 'queryStorage':init_queryStorage('tab_'+node.id);break;
    case 'manageStocktake':init_manageStocktake('tab_'+node.id);break;
    
    case 'transportationIncome':init_transportationIncome('tab_'+node.id);break;
    case 'transportationPay': init_transportationPay('tab_'+node.id);break;
    case 'entruckingPay': init_entruckingPay('tab_'+node.id);break;
    case 'enterStorageFinance': init_enterStorageFinance('tab_'+node.id);break;
    case 'outStorageFinance': init_outStorageFinance('tab_'+node.id);break;
    case 'transferStorageFinance': init_transferStorageFinance('tab_'+node.id);break;
	case 'storageFinance': init_storageFinance('tab_'+node.id);break;
	case 'trackCOD': init_trackCOD('tab_'+node.id);break;
	case 'financeLog': init_financeLog('tab_'+node.id);break;
    
    case 'transportationWay': init_transportationWay('tab_'+node.id);break;
    case 'transportationUnit': init_transportationUnit('tab_'+node.id);break;
    case 'carInformation': init_carInformation('tab_'+node.id);break;
    case 'carType': init_carType('tab_'+node.id);break;
    case 'costCenter': init_costCenter('tab_'+node.id);break;
    case 'sellCenter': init_sellCenter('tab_'+node.id);break;
    case 'goodsInformation': init_goodsInformation('tab_'+node.id);break;
    case 'customerInformation': init_customerInformation('tab_'+node.id);break;
    case 'storageInformation': init_storageInformation('tab_'+node.id);break;
    case 'specialStorageIncome': init_specialStorageFinance('tab_'+node.id);break;
    case 'cityInformation': init_cityInformation('tab_'+node.id);break;
    
    case 'manageWorkerInfo': init_manageWorkerInfo('tab_'+node.id);break;
    case 'manageWorkerSalary': init_manageWorkerSalary('tab_'+node.id);break;
     
    case 'manageUser': init_manageUser('tab_'+node.id);break;
    case 'manageRole': init_manageRole('tab_'+node.id);break;
    case 'systemInformation': init_systemInformation('tab_'+node.id);break;
    default:Ext.MessageBox.show({
	   		title: '网络异常',
	   		msg: '功能页面加载错误!',
	   		buttons: Ext.MessageBox.OK,
	   		icon: Ext.MessageBox.ERROR		   		
   		});
    }
}

function updateSwitch(id)
{
	switch(id)
    {
    case 'firstPage':break;
    case 'manageNews':update_manageNews('tab_'+id);break;
    case 'manageTransportationSheet':update_manageTransportationSheet('tab_'+id);break;
    case 'manageEntruckingSheet':update_manageEntruckingSheet('tab_'+id);break;
    case 'transportationTrack':update_transportationTrack('tab_'+id);break;
    case 'manageBarCode':update_manageBarCode('tab_'+id);break;
    case 'manageEnterStorageSheet':update_manageEnterStorageSheet('tab_'+id);break;
    case 'manageOutStorageSheet':update_manageOutStorageSheet('tab_'+id);break;
    case 'manageTransferStorageSheet':update_manageTransferStorageSheet('tab_'+id);break;
    case 'queryStorage':update_queryStorage('tab_'+id);break;
    case 'manageStocktake':update_manageStocktake('tab_'+id);break;
    
    case 'transportationIncome':update_transportationIncome('tab_'+id);break;
    case 'transportationPay': update_transportationPay('tab_'+id);break;
    case 'entruckingPay': update_entruckingPay('tab_'+id);break;
    case 'enterStorageFinance': update_enterStorageFinance('tab_'+id);break;
    case 'outStorageFinance': update_outStorageFinance('tab_'+id);break;
    case 'transferStorageFinance': update_transferStorageFinance('tab_'+id);break;
    case 'storageFinance': update_storageFinance('tab_'+id);break;
    case 'specialStorageIncome': update_specialStorageFinance('tab_'+id);break;
    case 'trackCOD': update_trackCOD('tab_'+id);break;
    case 'financeLog': update_financeLog('tab_'+id);break;
    
    case 'transportationWay': update_transportationWay('tab_'+id);break;
    case 'transportationUnit': update_transportationUnit('tab_'+id);break;
    case 'carInformation': update_carInformation('tab_'+id);break;
    case 'carType': update_carType('tab_'+id);break;
    case 'costCenter': update_costCenter('tab_'+id);break;
    case 'sellCenter': update_sellCenter('tab_'+id);break;
    case 'goodsInformation': update_goodsInformation('tab_'+id);break;
    case 'customerInformation': update_customerInformation('tab_'+id);break;
    case 'storageInformation': update_storageInformation('tab_'+id);break;
    case 'cityInformation': update_cityInformation('tab_'+id);break;
    
    case 'manageWorkerInfo': update_manageWorkerInfo('tab_'+id);break;
    case 'manageWorkerSalary': update_manageWorkerSalary('tab_'+id);break;
    
    case 'manageUser': update_manageUser('tab_'+id);break;
    case 'manageRole': update_manageRole('tab_'+id);break;
    case 'systemInformation': update_systemInformation('tab_'+id);break;
    default:Ext.MessageBox.show({
	   		title: '网络异常',
   			msg: '功能页面加载错误!',
	   		buttons: Ext.MessageBox.OK,
	   		icon: Ext.MessageBox.ERROR		   		
   		});
   }
}
////////////////////////////////////////////////////////////////////////////////////
function initRemoteData()
{
	var mask = new Ext.LoadMask(Ext.getBody(), {msg:"正在载入,请稍后..."});
	mask.show();
	Ext.Ajax.request({
		url: 'queryCurrentUser.action',
		method: 'POST',
		success : function(response,options){
			var result = Ext.util.JSON.decode(response.responseText);
			if(!result.success){
				AJAX_FAILURE_CALLBACK(result,'系统初始化失败');
			}else{
				Ext.getCmp('currentUser').setText('['+result.currentUserID+'] ['+result.roleName+']');
				MAKE_P_LIST(result.data);
				if(IsPermition('transportationTrack',false))
				{
					setTimeout("onTrackNotice()",5000);
					setInterval("onTrackNotice()",5000*12*10);
				}
			}
		},
		failure: function(response,options){
			AJAX_SERVER_FAILURE_CALLBACK(response,options,'系统初始化载入失败');
		}
	});//Ajax
	Ext.Ajax.request({
		url: 'Message.queryNews.action',
		method: 'POST',
		params: { limit:5 },
		success : function(response,options){
			var result = Ext.util.JSON.decode(response.responseText);
			if(!result.success){
				AJAX_FAILURE_CALLBACK(result,'新闻加载错误');
			}else{
				formatNewsPage(result,false);
			}
			mask.hide();
		},
		failure: function(response,options){
			AJAX_SERVER_FAILURE_CALLBACK(response,options,'新闻初始化载入失败');
			formatNewsPage(rs,true);
			mask.hide();
		}
	});//Ajax
}

function formatNewsPage(rs,fail)
{
	var img1 = "xwxx.gif"; var img2 = "hydt.gif"; var img3 = "ggtz.gif";
	var img4 = "zsgl.gif"; var img5 = "whtd.gif"; var img6 = "ywdt.gif";
	var t1 = '1', t2 = '2', t3 = '3';
	var t4 = '4', t5 = '5', t6 = '6';
	var startInnerHtml = "<table cellSpacing=0 cellPadding=0 width=350 border=0 style='padding-left:15px;padding-top:5px;'><tbody>"+
        						   "<tr><td width='250' height='25'><p><img src='images/news/";
    var midA = "' height='35' width='120'/></p></td><td width='100' height='25' align='center'><a href='javascript:setNewsType(";
    var midB = ");treeClick(NewsNode,null);'><font size='2'>更多</font></a></td></tr>";
	var endInnerHtml =  "</tbody></table>";
	var h1 = formatPageBody(rs.newsList,rs.data.ncount,fail);
	var h2 = formatPageBody(rs.logisticsNews,rs.data.lcount,fail);
	var h3 = formatPageBody(rs.announcementList,rs.data.acount,fail);
	var h4 = formatPageBody(rs.knowledgeList,rs.data.kcount,fail);
	var h5 = formatPageBody(rs.cultureList,rs.data.ccount,fail);
	var h6 = formatPageBody(rs.actionNews,rs.data.atcount,fail);
	document.getElementById('div_xwxx').innerHTML = startInnerHtml+img1+midA+t1+midB+h1+endInnerHtml;
	document.getElementById('div_hydt').innerHTML = startInnerHtml+img2+midA+t2+midB+h2+endInnerHtml;
	document.getElementById('div_ggtz').innerHTML = startInnerHtml+img3+midA+t3+midB+h3+endInnerHtml;
	document.getElementById('div_zsgl').innerHTML = startInnerHtml+img4+midA+t4+midB+h4+endInnerHtml;
	document.getElementById('div_whtd').innerHTML = startInnerHtml+img5+midA+t5+midB+h5+endInnerHtml;
	document.getElementById('div_ywdt').innerHTML = startInnerHtml+img6+midA+t6+midB+h6+endInnerHtml;
}

function formatPageBody(list,num,fail)
{
	var html = "";
	if(num == 0 || fail==true) return "<tr><td width='250' height='25'><li><font size='2'>当前无新信息</font></li></td></tr>";
	for(var i=0; i<num; i++)
	{
		if(i>0)
			html += "<tr><td colSpan=3 height=1 width='350'><hr width=95% size=1 color=#999></td></tr>";
		html += "<tr><td width='250' height='25'><li><a href='javascript:onDetailNews("+ list[i].messageID +")'><font size='2'>"+ list[i].header +"</font></a></li></td>" +
					"<td width='100' height='25' align='center'><font size='1'>["+ list[i].datePosted +"]</font></td></tr>";
	}
	return html;
}
	
	