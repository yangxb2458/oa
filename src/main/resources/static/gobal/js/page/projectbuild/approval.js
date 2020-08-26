var zTree;
var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/projectbuildget/getProjectBuildSortTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "sortId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	callback : {
		onClick : zTreeOnClick
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "sortId",
			pIdKey : "sortLeave",
			rootPId : "0"
		},
		key : {
			name : "sortName"
		}
	}
};

var setting1 = {
		async : {
			enable : true,// 设置 zTree 是否开启异步加载模式
			url : "/ret/projectbuildget/getProjectBuildSortTree",// Ajax 获取数据的 URL 地址
			autoParam : [ "sortId" ],// 异步加载时需要自动提交父节点属性的参数
		},
		view : {
			dblClickExpand : false,
			selectedMulti : false
		//禁止多选
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "sortId",
				pIdKey : "sortLeave",
				rootPId : "0"
			},
			key : {
				name : "sortName"
			}
		},
		callback : {
			onClick : function(e, treeId, treeNode) {
				$("#form").data("bootstrapValidator").updateStatus("sortId", "NOT_VALIDATED", null );
				var zTree = $.fn.zTree.getZTreeObj("menuTree"), nodes = zTree.getSelectedNodes(), v = "";
				vid = "";
				nodes.sort(function compare(a, b) {
					return a.id - b.id;
				});
				for (var i = 0, l = nodes.length; i < l; i++) {
					v += nodes[i].sortName + ",";
					vid += nodes[i].sortId + ",";
				}
				if (v.length > 0)
					v = v.substring(0, v.length - 1);
				var nameem = $("#sortId");
				nameem.val(v).trigger('change');
				$("#form").data('bootstrapValidator').validateField('sortId');
				if (vid.length > 0)
					vid = vid.substring(0, vid.length - 1);
					nameem.attr("data-value",vid);
					
			}
		}
	};

$(function(){
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	$("#createbut").unbind("click").click(function(){
		addprojet();
	});
	$.ajax({
		url : "/ret/projectbuildget/getProjectBuildSortTree",
		type : "post",
		dataType : "json",
		success : function(data) {
			zTree = $.fn.zTree.init($("#tree"), setting, data);// 初始化树节点时，添加同步获取的数据
			$.fn.zTree.init($("#menuTree"), setting1, data);
		}
	});
	
	$("#sortId").unbind("click").click(function(e) {
		e.stopPropagation();
		$("#menuContent").css({
			"width" : $(this).outerWidth() + "px"
		}).slideDown(200);
	});
	$("body").unbind("click").click(function() {
		$("#menuContent").hide();
	});

	$("#menuContent").unbind("click").click(function(e) {
		e.stopPropagation();
	});
	
	query("");
	$("#form").bootstrapValidator('validate');

});
$(document).ready(function(){
	$('#remark').summernote({ height:300 });
	$("#cbut").unbind("click").click(function() {
		document.getElementById("form").reset();
		$("#listtable").hide();
		$("#creatediv").show();
		$("#updatabut").hide();
		$("#createbut").show();
		$("#remark").code("");
		$("#show_projectbuildattach").html("");
		$("#projectbuildattach").attr("data_value","");
	});
 });

function addprojet()
{
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/projectbuildset/insertProjectBuildDetails",
		type : "post",
		dataType : "json",
		data:{
			projectTitle:$("#projectTitle").val(),
			sortNo:$("#sortNo").val(),
			projectType:$("#projectType").val(),
			sortId:$("#sortId").attr("data-value"),
			firstParty:$("#firstParty").val(),
			secondParty:$("#secondParty").val(),
			amount:$("#amount").val(),
			endTime:$("#endTime").val(),
			beginTime:$("#beginTime").val(),
			manager:$("#manager").attr("data-value"),
			partALinkUserName:$("#partALinkUserName").val(),
			partALinkTel:$("#partALinkTel").val(),
			partALinkFax:$("#partALinkFax").val(),
			partALinkEmail:$("#partALinkEmail").val(),
			partALinkMobile:$("#partALinkMobile").val(),
			remark:$("#remark").code(),
			attach:$("#projectbuildattach").attr("data_value")
		},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				location.reload();
				top.layer.msg(data.msg);
				}
		}
	})
	}
}

function zTreeOnClick(event, treeId, treeNode) {
	$("#creatediv").hide();
	$("#listtable").show();
	var sortId = treeNode.sortId;
	$("#myTable").bootstrapTable('destroy');
	query(sortId);
}

function query(sortId)
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/projectbuildget/getprojectbuilddetailslist?sortId='+sortId,
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
	      idField: 'projectId',//key值栏位
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
	       field: 'projectTitle',
	       title: '工程项目名称',
	       sortable : true,
	       width:'200px'
	      },
	      {
			field: 'sortName',
			   width:'50px',
			   title: '工程分类'
			},
			{
		       field: 'projectType',
		       title: '工程类型',
		       width:'50px',
		       formatter:function(value,row,index){
		    	   if(value=="1")
		    		 {
		    		   return "总包项目";
		    		 }else if(value=="2")
		    		{
		    			 return "分包项目";
		    		}
	            }
	      },
	      {
		       field: 'secondParty',
		       width:'100px',
		       title: '对方单位'
		   },
		   {
		       field: 'amount',
		       width:'50px',
		       sortable : true,
		       title: '工程造价'
		       
		   },
		   {
		       field: 'manager',
		       width:'100px',
		       title: '项目经理',
		       formatter:function(value,row,index){
	                return getUserNameByStr(value);
	            }
		   },
		   {
		       field: 'endTime',
		       width:'100px',
		       sortable : true,
		       title: '交付日期'
		   },
	      {
	       field: 'opt',
	       title: '操作',
	       align:'center',
	       width:'120px',
    	   formatter:function(value,row,index){
                return createOptBtn(row.projectId);
            }
	      }],
	      onClickCell: function (field, value, row, $element) {
	      //alert(row.SystemDesc);
	    },
	    responseHandler:function(res){
	    	if(res.status=="500")
    		{
    		console.log(res.msg);
    		}else if(res.status=="100")
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
function createOptBtn(projectId)
{
	var html="<a href=\"javascript:void(0);readproject('"+projectId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);editproject('"+projectId+"')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);delproject('"+projectId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
}

function readproject(projectId)
{
	window.open("/app/core/projectbuild/project/detalis?projectId="+projectId);
}


function delproject(projectId)
{
	if (confirm("确定删除当前工程项目吗？")) {
		$.ajax({
			url : "/set/projectbuildset/delProjectBuildDetails",
			type : "post",
			dataType : "json",
			data : {
				projectId:projectId
			},
			success : function(data) {
				if (data.status == 200) {
					top.layer.msg(data.msg);
					location.reload();
				} else {
					console.log(data.msg);
				}
			}
		});
	} else {
		return;
	}
}

function editproject(projectId)
{
	document.getElementById("form").reset();
	$("#listtable").hide();
	$("#creatediv").show();
	$("#createbut").hide();
	$("#updatabut").show();
	$("#updatabut").unbind("click").click(function(){
		updateProject(projectId);
	});
	$.ajax({
		url : "/ret/projectbuildget/getProjectBuildDetailsById",
		type : "post",
		dataType : "json",
		data:{
			projectId:projectId
		},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else{
				for(name in data.list)
					{
						if(name=="manager")
						{
							$("#manager").attr("data-value",data.list.manager);
							$("#manager").val(getUserNameByStr(data.list.manager));
						}else if(name=="sortId")
						{
							$("#sortId").attr("data-value",data.list.sortId);
							$.ajax({
								url : "/ret/projectbuildget/getProjectBuildSortById",
								type : "post",
								dataType : "json",
								data : {
									sortId :data.list.sortId
								},
								success : function(data) {
									if (data.status == "200") {
										if (data.list) {
											$("#sortId").val(data.list.sortName);
										} else {
											$("#sortLeaveName").val("");
										}
									}
								}
							});
						}else if(name=="attach")
						{
							$("#projectbuildattach").attr("data_value",data.list.attach);
							createAttach("projectbuildattach","1");
						}else if(name=="remark")
						{
							$("#remark").code(data.list.remark);
						}else
						{
							$("#"+name).val(data.list[name]);
						}
					}
			}
		}
	});
	
}


function updateProject(projectId)
{
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/projectbuildset/updateProjectBuildDetails",
		type : "post",
		dataType : "json",
		data:{
			projectId:projectId,
			projectTitle:$("#projectTitle").val(),
			sortNo:$("#sortNo").val(),
			projectType:$("#projectType").val(),
			sortId:$("#sortId").attr("data-value"),
			firstParty:$("#firstParty").val(),
			secondParty:$("#secondParty").val(),
			amount:$("#amount").val(),
			endTime:$("#endTime").val(),
			beginTime:$("#beginTime").val(),
			manager:$("#manager").attr("data-value"),
			partALinkUserName:$("#partALinkUserName").val(),
			partALinkTel:$("#partALinkTel").val(),
			partALinkFax:$("#partALinkFax").val(),
			partALinkEmail:$("#partALinkEmail").val(),
			partALinkMobile:$("#partALinkMobile").val(),
			remark:$("#remark").code(),
			attach:$("#projectbuildattach").attr("data_value")
		},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				location.reload();
				top.layer.msg(data.msg);
				}
		}
	})
	}
}