var zTree;
var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/unitget/getUnitDeptTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "deptId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	callback : {
		onClick : zTreeOnClick
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "deptId",
			pIdKey : "orgLeaveId",
			rootPId : "0"
		},
		key : {
			name : "deptName"
		}
	}
};

var setting1 = {
		async : {
			enable : true,// 设置 zTree 是否开启异步加载模式
			url : "/ret/unitget/getUnitDeptTree",// Ajax 获取数据的 URL 地址
			autoParam : [ "deptId" ],// 异步加载时需要自动提交父节点属性的参数
		},
		view : {
			dblClickExpand : false,
			selectedMulti: false//禁止多选
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "deptId",
				pIdKey : "orgLeaveId",
				rootPId : "0"
			},
			key : {
				name : "deptName"
			}
		},
		callback : {
			onClick : function(e, treeId, treeNode) {
				$("#form").bootstrapValidator('validate');
				var zTree = $.fn.zTree.getZTreeObj("menuTree"), nodes = zTree.getSelectedNodes(), v = "";
				vid = "";
				nodes.sort(function compare(a, b) {
					return a.id - b.deptId;
				});
				for (var i = 0, l = nodes.length; i < l; i++) {
					v += nodes[i].deptName + ",";
					vid += nodes[i].deptId + ",";
				}
				if (v.length > 0)
					v = v.substring(0, v.length - 1);
				var nameem = $("#deptName");
				nameem.val(v);
				if (vid.length > 0)
					vid = vid.substring(0, vid.length - 1);
				var idem = $("#deptId");
				idem.val(vid);
				$("#form").data("bootstrapValidator").updateStatus("deptName", "NOT_VALIDATED", null );//更新指定的字段
			}
		}
	};
	


$(function() {
	var topNode = [{deptName: orgName,orgLeaveId:'', isParent: "true", deptId: "0",icon:"/gobal/img/org/org.png"}];
	var topNode1 = [{deptName: orgName,orgLeaveId:'', isParent: "true", deptId: "0",icon:"/gobal/img/org/org.png"}];
	zTree = $.fn.zTree.init($("#tree"), setting, topNode);// 初始化树节点时，添加同步获取的数据
	var zTreeObj=$.fn.zTree.init($("#menuTree"), setting1,topNode1);
	var nodes = zTree.getNodes();
	var nodes1 = zTreeObj.getNodes();
	 for(var i=0;i<nodes.length;i++){
		 zTree.expandNode(nodes[i], true, false, false);//默认展开第一级节点
         }
	 for(var i=0;i<nodes1.length;i++){
		 zTreeObj.expandNode(nodes1[i], true, false, false);//默认展开第一级节点
         }
	$("#createbut").unbind("click").click(function(){
		addUserInfo();
	});
	$("#cbut").unbind("click").click(function(){
		document.getElementById("form").reset();
		$("#datalist").hide();
		$("#updatabut").hide();
		$("#delbut").hide();
		$("#creatediv").show();
		$("#createbut").show();
		$("#accountId").removeAttr("readonly");
	});
	$("#delbut").unbind("click").click(function(){
		deleteUserInfo("");
	});
	$("#cquery").unbind("click").click(function(){
		$("#creatediv").hide();
		$("#datalist1").hide();
		$("#datalist").show();
		query("");
	});
	$("#notLoginQuery").unbind("click").click(function(){
		$("#creatediv").hide();
		$("#datalist").hide();
		$("#datalist1").show();
		querynotlogin();
	});
	notLoginQuery
	$("#deptName").unbind("click").click(function(e){
		e.stopPropagation();
		$("#menuContent").css({
			"width":$(this).outerWidth()+"px"
		}).slideDown(200);
	});
	$("body").unbind("click").click(function(){
		$("#menuContent").hide();
	});

	$("#menuContent").unbind("click").click(function(e){
		e.stopPropagation();
	});
	$("#cimport").unbind("click").click(function(){
		$("#exdiv").modal("show");
	});
	$("#butdel").unbind("click").click(function(){
		var selected = $('#myTable').bootstrapTable('getSelections');
		var ids = new Array();
		for(var i=0;i<selected.length;i++){
		ids.push(selected[i].accountId);
		}
		console.log(ids.length);
		if(ids.length==0)
		{
			top.layer.msg("请先选择需要删除的人员账号！")
		}else
		{
			if(confirm("确定删除当前用户信息吗？"))
		    {
		$.ajax({
			url : "/set/unitset/deleteUserInfos",
			type : "post",
			dataType : "json",
			data:{
				accountIds:ids.join(',')
			},
			success : function(data) {
				if(data.status==200){
					top.layer.msg(data.msg);
					location.reload();
				}else if(data.status==100)
				{
					top.layer.msg(data.msg);
				}else
					{
					console.log(data.msg);
					}
			}
			});
		    }
		}
	})
	getAttendConfigList();
	$("#form").bootstrapValidator('resetForm');
});
function zTreeOnClick(event, treeId, treeNode)
{
	$("#creatediv").hide();
	$("#datalist1").hide();
	$("#datalist").show();
	$("#myTable").bootstrapTable('destroy');
	if(treeNode.deptId=="0")
		{
			query("");
		}else
		{
			query(treeNode.deptId);
		}
}

function query(deptId)
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/unitget/getUserInfoByDeptId?deptId='+deptId,
	      method: 'post',
	      contentType:'application/x-www-form-urlencoded',
	      toolbar: '#toobar',//工具列
	      striped: true,//隔行换色
	      cache: false,//禁用缓存
	      pagination: true,//启动分页
	      sidePagination: 'server',//分页方式
	      pageNumber: 1,//初始化table时显示的页码
	      pageSize: 10,//每页条目
	      showFooter: false,//是否显示列脚
	      showPaginationSwitch: true,//是否显示 数据条数选择框
	      sortable: true,//排序
	      search: true,//启用搜索
	      showColumns: true,//是否显示 内容列下拉框
	      showRefresh: true,//显示刷新按钮
	      idField: 'accountId',//key值栏位
	      clickToSelect: true,//点击选中checkbox
	      pageList : [10, 20, 30, 50],//可选择单页记录数
	      queryParams:queryParams,
	      columns: [ {
	      checkbox: true
	      },
		    {
		    	field: 'num',
				title: '序号',//标题  可不加
				width:'50px',
				formatter: function (value, row, index) {
				return index+1;
			}
		    },
	     {
	       field: 'accountId',
	       title: '账号',
	       sortable : true,
	       width:'150px'
	      },
	      {
	       field: 'userName',
	       width:'100px',
	       title: '姓名'
	     },
	     {
		   field: 'sex',
		   width:'50px',
		   title: '性别'
		  },
		  {
			field: 'postion',
			width:'200px',
			title: '职务'
		 },
	     {
	       field: 'userPriv',
	       title: '权限',
	       width:'200px',
	       formatter:function(value,row,index){
	    	   var str=getUserPrivNamesByIds(row.userPriv);
               return str.substring(0,str.length-1);
           }
	      },
		   {
		       field: 'manage',
		       title: '管理范围'
		   },
		   {
		       field: 'status',
		       title: '人员状态',
		       formatter:function(value,row,index){
		    	   if(value=="0")
		    		   {
		    		   return "正常登陆";
		    		   }else
		    			   {
		    			   return "禁止登陆";
		    			   }
		       }
		   },
	      {
	       field: 'opt',
	       title: '操作',
	       align:'center',
	       width:'180px',
    	   formatter:function(value,row,index){
                return createOptBtn(row.accountId,deptId,row.status);
            }
	      }],
	      onClickCell: function (field, value, row, $element) {
	      //alert(row.SystemDesc);
	    },
	    responseHandler:function(res){
	    	if(res.status=="500")
	    		{
	    		top.layer.msg(res.msg);
	    		}else
	    			{
	    			return {
	    				total : res.list.total, //总页数,前面的key必须为"total"
	    				rows : res.list.list //行数据，前面的key要与之前设置的dataField的值一致.
	    			};
	    			}
	    }
	   });
}


function createOptBtn(accountId,deptId,notLogin)
{
	var html="<a href=\"javascript:void(0);edit('"+accountId+"')\" class=\"btn btn-primary btn-xs\">编辑</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);deleteUserInfo('"+accountId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>&nbsp;&nbsp;";
	if(notLogin=="0")
	{
		html+="<a href=\"javascript:void(0);stopAccount('"+accountId+"','"+deptId+"')\" class=\"btn btn-darkorange btn-xs\" >禁用</a>";
	}else if(notLogin=="1")
	{
		html+="&nbsp;&nbsp;<a href=\"javascript:void(0);openAccount('"+accountId+"','"+deptId+"')\" class=\"btn btn-primary btn-xs\">启用</a>";
	}
	return html;
	}
function queryParams(params) {
    var temp = {
        search: params.search,
        pageSize:this.pageSize,
        pageNumber:this.pageNumber,
        sort: params.sort,
        sortOrder:params.order
    };
    return temp;
};

function stopAccount(accountId,deptId)
{
	$.ajax({
		url : "/set/unitset/stopAccount",
		type : "post",
		dataType : "json",
		data:{
			accountId:accountId
		},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				$("#myTable").bootstrapTable('destroy');
				query(deptId);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	});
}
function openAccount(accountId,deptId)
{
	$.ajax({
		url : "/set/unitset/openAccount",
		type : "post",
		dataType : "json",
		data:{
			accountId:accountId
		},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				$("#myTable").bootstrapTable('destroy');
				query(deptId);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	});
}

function edit(accountId)
{
	$("#form").bootstrapValidator('resetForm'); //重置
	$("#creatediv").show();
	$("#datalist").hide();
	$("#createbut").hide();
	$("#updatabut").show();
	$("#delbut").show();
	$("#accountId").attr("readOnly","readOnly");
	$.ajax({
		url : "/ret/unitget/getAccountAndUserInfo",
		type : "post",
		dataType : "json",
		data:{
			accountId:accountId
		},
		success : function(data) {
			if(data.status==200){
				var info = data.list;
				for(var id in info)
					{
					if(id=="userPrivName")
						{
							$("#userPriv").val(info[id]);
						}else if(id=="userPriv")
							{
							$("#userPriv").attr("data-value",info[id]);
							$("#userPriv").val(getUserPrivNamesByIds(info[id]));
							}else if(id=="leadLeave")
								{
								$("#leadLeave").attr("data-value",info[id]);
								}else if(id=="leadLevelName")
									{
									$("#leadLeave").val(info[id]);
									}else if(id=="manageDept")
										{
											$("#manageDept").attr("data-value",info[id]);
										}else if(id=="manageDeptName")
											{
											$("#manageDept").val(info[id]);	
											}else if(id=="leadId")
												{
													$("#leadId").attr("data-value",info[id]);
												}else if(id=="leadName")
													{
													$("#leadId").val(info[id]);	
													}else if(id=="sex")
														{
														$("input[name='sex'][value='"+info[id]+"']").attr("checked",true); 
														}else{
																$("#"+id).val(info[id]);
																}
													
									
					}
				$("#updatabut").unbind("click").click(function(){
					updateAccountAndUserInfo(accountId);
				});
			}else if(data.status==100)
			{
				top.layer.msg(data.msg);
			}else
				{
				console.log(data.msg);
				}
		}
	});
	}

function deleteUserInfo(accountId)
{
	 if(confirm("确定删除当前用户信息吗？"))
	    {
	if(accountId=="")
		{
		accountId=$("#accountId").val();
		}
	$.ajax({
		url : "/set/unitset/deleteUserInfo",
		type : "post",
		dataType : "json",
		data:{
			accountId:accountId
		},
		success : function(data) {
			if(data.status==200){
				top.layer.msg(data.msg);
				location.reload();
			}else if(data.status==100)
			{
				top.layer.msg(data.msg);
			}else
				{
				console.log(data.msg);
				}
		}
	});
	    }
}

function updateAccountAndUserInfo(accountId)
{
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/unitset/updateAccountAndUserInfo",
		type : "post",
		dataType : "json",
		data:{
			accountId:accountId,
			dAccountId:$("#dAccountId").val(),
			wAccountId:$("#wAccountId").val(),
			sortNo:$("#sortNo").val(),
			passwordType:$("#passwordType").val(),
			userPriv:$("#userPriv").attr("data-value"),
			userName:$("#userName").val(),
			sex:$("input[name='sex']:checked").val(),
			deptId:$("#deptId").val(),
			leadId:$("#leadId").attr("data-value"),
			workId:$("#workId").val(),
			manageDept:$("#manageDept").attr("data-value"),
			attendConfigId:$("#attendConfigId").val(),
			leadLeave:$("#leadLeave").attr("data-value"),
			mobileNo:$("#mobileNo").val()
		},
		success : function(data) {
			if(data.status==200){
				top.layer.msg(data.msg);
				location.reload();
			}else if(data.status==100)
			{
				top.layer.msg(data.msg);
			}else
				{
				console.log(data.msg);
				}
		}
	});
	}
}


function addUserInfo()
{
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/unitset/insertAccountAndUserInfo",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			accountId:$("#accountId").val(),
			passWord:$("#passWord").val(),
			passwordType:$("#passwordType").val(),
			dAccountId:$("#dAccountId").val(),
			wAccountId:$("#wAccountId").val(),
			userPriv:$("#userPriv").attr("data-value"),
			userName:$("#userName").val(),
			sex:$("input[name='sex']:checked").val(),
			deptId:$("#deptId").val(),
			leadId:$("#leadId").attr("data-value"),
			workId:$("#workId").val(),
			manageDept:$("#manageDept").attr("data-value"),
			attendConfigId:$("#attendConfigId").val(),
			leadLeave:$("#leadLeave").attr("data-value"),
			mobileNo:$("#mobileNo").val()
		},
		success : function(data) {
			if(data.status==200){
				top.layer.msg(data.msg);
				location.reload();
			}else if(data.status==100)
			{
				top.layer.msg(data.msg);
			}else
				{
				console.log(data.msg);
				}
		}
	});
	}
}

function getAttendConfigList()
{
	$.ajax({
		url : "/ret/oaget/getAllAttendConfigList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status==200){
				var html="";
				for(var i=0;i<data.list.length;i++)
					{
					html+="<option value='"+data.list[i].configId+"'>"+data.list[i].title+"</option>";
					}
				$("#attendConfigId").html(html);
			}else if(data.status==100)
			{
				top.layer.msg(data.msg);
			}else
				{
				console.log(data.msg);
				}
		}
	});
}


function importUserInfoForExcel(){
	$.ajaxFileUpload({
         url:'/set/unitset/importUserInfo', //上传文件的服务端
         secureuri:false,  //是否启用安全提交
         async:false,
         dataType: 'json',   //数据类型  
         fileElementId:'file', //表示文件域ID
         success: function(data,status){
        	 	if(data.status==200)
        	 		{
        	 		top.layer.msg(data.msg);
        	 		$("#exdiv").modal("hide");
        	 		}else if(data.status==100)
    				{
    					top.layer.msg(data.msg);
    				}else
        	 			{
        	 			console.log(data.msg);
        	 			}
         },
    //提交失败处理函数
         error: function (data,status,e){
        	 top.layer.msg("文件上传出错!请检查文件格式!");
        	console.log(data.msg);
         }
    });
}

function querynotlogin()
{
	 $("#myTable1").bootstrapTable({
	      url: '/ret/unitget/getLeaveUserInfo',
	      method: 'post',
	      contentType:'application/x-www-form-urlencoded',
	      toolbar: '#toobar1',//工具列
	      striped: true,//隔行换色
	      cache: false,//禁用缓存
	      pagination: true,//启动分页
	      sidePagination: 'server',//分页方式
	      pageNumber: 1,//初始化table时显示的页码
	      pageSize: 10,//每页条目
	      showFooter: false,//是否显示列脚
	      showPaginationSwitch: true,//是否显示 数据条数选择框
	      sortable: true,//排序
	      search: true,//启用搜索
	      showColumns: true,//是否显示 内容列下拉框
	      showRefresh: true,//显示刷新按钮
	      idField: 'accountId',//key值栏位
	      clickToSelect: true,//点击选中checkbox
	      pageList : [10, 20, 30, 50],//可选择单页记录数
	      queryParams:queryParams,
	      columns: [ {
	      checkbox: true
	      },
		    {
		    	field: 'num',
				title: '序号',//标题  可不加
				width:'50px',
				formatter: function (value, row, index) {
				return index+1;
			}
		    },
	     {
	       field: 'accountId',
	       title: '账号',
	       sortable : true,
	       width:'150px'
	      },
	      {
	       field: 'userName',
	       width:'100px',
	       title: '姓名'
	     },
	     {
		   field: 'sex',
		   width:'50px',
		   title: '性别'
		  },
		  {
			field: 'postion',
			width:'200px',
			title: '职务'
		 },
	     {
	       field: 'userPriv',
	       title: '权限',
	       width:'200px',
	       formatter:function(value,row,index){
	    	   var str=getUserPrivNamesByIds(row.userPriv);
               return str.substring(0,str.length-1);
           }
	      },
		   {
		       field: 'manage',
		       title: '管理范围'
		   },
		   {
		       field: 'status',
		       title: '人员状态',
		       formatter:function(value,row,index){
		    	   if(value=="0")
		    		   {
		    		   return "正常登陆";
		    		   }else
		    			   {
		    			   return "禁止登陆";
		    			   }
		       }
		   },
	      {
	       field: 'opt',
	       title: '操作',
	       align:'center',
	       width:'180px',
    	   formatter:function(value,row,index){
                return createOptBtn(row.accountId,deptId,row.status);
            }
	      }],
	      onClickCell: function (field, value, row, $element) {
	      //alert(row.SystemDesc);
	    },
	    responseHandler:function(res){
	    	if(res.status=="500")
	    		{
	    		top.layer.msg(res.msg);
	    		}else
	    			{
	    			return {
	    				total : res.list.total, //总页数,前面的key必须为"total"
	    				rows : res.list.list //行数据，前面的key要与之前设置的dataField的值一致.
	    			};
	    			}
	    }
	   });
}
