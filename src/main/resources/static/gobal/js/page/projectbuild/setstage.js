var zTree;
var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/projectbuildget/getProjectBuildSortAllParentTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "sortId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	callback : {
			onExpand: function(event, treeId, treeNode) {
				var sortId = treeNode.sortId;
				if (treeNode.isParent) {
					$.ajax({
						url : "/ret/projectbuildget/getProjectBuildListForTree",
						type : "post",
						data : {
							sortId : sortId
						},
						dataType : "json",
						success : function(data) {
							zTree.reAsyncChildNodes(treeNode, "refresh");
							zTree.addNodes(treeNode, data);
						}
					});
				}
			},
		onClick : function(event, treeId, treeNode) {
			var sortId = treeNode.sortId;
			if (!treeNode.isParent) {
				getProjectBuildDetails(sortId);
					$("#myTable").bootstrapTable('destroy');
					query(sortId);
			}
		}
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

$(function() {
	jeDate("#stageBeginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#stageEndTime", {
		format: "YYYY-MM-DD"
	});
	$.ajax({
		url : "/ret/projectbuildget/getProjectBuildSortAllParentTree",
		type : "post",
		dataType : "json",
		success : function(data) {
			var topNode = [ {
				sortName : "全部",
				isParent : true,
				sortId : "0"
			} ];
			var newTreeNodes = topNode.concat(data);
			zTree = $.fn.zTree.init($("#tree"), setting, newTreeNodes);
			var nodes = zTree.getNodes();
			if (nodes.length > 0) {
				for (var i = 0; i < nodes.length; i++) {
					zTree.expandNode(nodes[i], true, false, false);
				}
			}
		}
	});
	query("");
	$("#cbut").unbind("click").click(function(){
		if($("#projectId").val()=="")
			{
			top.layer.msg("请先选择需要设置的工程！");
			return;
			}else
			{
				document.getElementById("form").reset();
				$("#setStageModal").modal("show");
				$(".js-save-stage").unbind("click").click(function(){
					var projectId = $("#projectId").val();
					addsetage(projectId);
				});
				
			}
	});
	$('#remark').summernote({ height:200 });
});

function query(projectId) {
	$("#myTable").bootstrapTable(
			{
				url : '/ret/projectbuildget/getprojectbuildStagelist?projectId='+ projectId,
				method : 'post',
				contentType : 'application/x-www-form-urlencoded',
				toolbar : '#toobar',//工具列
				striped : true,//隔行换色
				cache : false,//禁用缓存
				pagination : true,//启动分页
				sidePagination : 'server',//分页方式
				pageNumber : 1,//初始化table时显示的页码
				pageSize : 10,//每页条目
				showFooter : false,//是否显示列脚
				showPaginationSwitch : true,//是否显示 数据条数选择框
				sortable : true,//排序
				search : true,//启用搜索
				showColumns : true,//是否显示 内容列下拉框
				showRefresh : true,//显示刷新按钮
				idField : 'stageId',//key值栏位
				clickToSelect : true,//点击选中checkbox
				pageList : [ 10, 20, 30, 50 ],//可选择单页记录数
				queryParams : queryParams,
				columns : [ {
					checkbox : true
				}, {
					field : 'num',
					title : '序号',//标题  可不加
					width : '50px',
					formatter : function(value, row, index) {
						return index + 1;
					}
				}, {
					field : 'projectTitle',
					title : '项目名称',
					sortable : true,
					width : '200px'
				}, {
					field : 'stageName',
					width : '50px',
					title : '节点名称'
				}, {
					field : 'beginTime',
					width : '100px',
					title : '开始时间'
				}, {
					field : 'endTime',
					width : '100px',
					title : '结束时间'

				}, {
					field : 'superintendent',
					width : '100px',
					title : '负责人',
					formatter:function(value, row, index)
					{
						return getUserNameByStr(value);
					}
				},  {
					field : 'status',
					title : '状态',
					width : '50px',
					formatter:function(value, row, index)
					{
						if(value=="0")
							{
							return "未开始";
							}else if(value=="1")
							{
								return "进行中";
							}else if(value=="2")
							{
								return "延期";
							}else if(value=="3")
							{
								return "完成"
							}
					
					}
				},{
					field : 'opt',
					title : '操作',
					align:'center',
					width : '200px',
					formatter : function(value, row, index) {
						return createOptBtn(row.projectId,row.stageId,row.status);
					}
				} ],
				onClickCell : function(field, value, row, $element) {
				},
				responseHandler : function(res) {
					if (res.status == "500") {
						console.log(res.msg);
					} else if (res.status == "100") {
						top.layer.msg(res.msg);
					} else {
						return {
							total : res.list.total, //总页数,前面的key必须为"total"
							rows : res.list.list
						//行数据，前面的key要与之前设置的dataField的值一致.
						};
					}
				}
			});
}

function queryParams(params) {
	var temp = {
		search : params.search,
		pageSize : this.pageSize,
		pageNumber : this.pageNumber,
		sort : params.sort,
		sortOrder : params.order
	};
	return temp;
};
function createOptBtn(projectId,stageId,status) {
	var html = "<a href=\"javascript:void(0);readstage('" + stageId
			+ "')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;";
	if(status!=3)
	{
		html+= "<a href=\"javascript:void(0);editstage('" + stageId
		+ "')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;"
		+ "<a href=\"javascript:void(0);delstage('" + stageId
		+ "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	}
	if(status=="0")
	{
		html+="&nbsp;&nbsp;<a href=\"javascript:void(0);setStageStatus('"+projectId+"','" +stageId
		+ "','1')\" class=\"btn btn-sky btn-xs\" >开始</a>";
	}else if(status=="1")
	{
		html+="&nbsp;&nbsp;<a href=\"javascript:void(0);setStageStatus('"+projectId+"','" + stageId
		+ "','3')\" class=\"btn btn-success btn-xs\" >完成</a>&nbsp;&nbsp;"
			+ "<a href=\"javascript:void(0);setStageStatus('"+projectId+"','" + stageId
			+ "','2')\" class=\"btn btn-darkorange btn-xs\" >延期</a>";
	}else if(status=="2")
	{
		html+="&nbsp;&nbsp;<a href=\"javascript:void(0);setStageStatus('"+projectId+"','" + stageId
		+ "','3')\" class=\"btn btn-success btn-xs\" >完成</a>";
	}
	return html;
}

function setStageStatus(projectId,stageId,status)
{
	$.ajax({
		url : "/set/projectbuildset/updateProjectBuildStage",
		type : "post",
		dataType : "json",
		data:{
			stageId:stageId,
			status:status
		},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else{
				$("#myTable").bootstrapTable('destroy');
				query(projectId);
			}
		}
	});
}

function addsetage(projectId)
{
	$.ajax({
		url : "/set/projectbuildset/insertProjectBuildStage",
		type : "post",
		dataType : "json",
		data:{
			projectId:projectId,
			stageName:$("#stageName").val(),
			sortNo:$("#stageSortNo").val(),
			beginTime:$("#stageBeginTime").val(),
			endTime:$("#stageEndTime").val(),
			remark:$("#remark").code(),
			superintendent:$("#superintendent").attr("data-value")
		},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else{
				$("#setStageModal").modal("hide");
				$("#myTable").bootstrapTable('destroy');
				query(projectId);
			}
		}
	});
}


function getProjectBuildDetails(projectId)
{
	$.ajax({
		url : "/ret/projectbuildget/getProjectBuildDetailsById",
		type : "post",
		dataType : "json",
		data:{projectId:projectId},
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
						if(name=="projectType")
						{
							if(data.list[name]=="1")
							{
								$("#projectType").html("总包项目");
							}else if(data.list[name]=="2")
							{
								$("#projectType").html("分包项目");
							}
						}else if(name=="manager")
						{
							$("#manager").html(getUserNameByStr(data.list.manager));
						}else if(name=="sortId")
						{
							$("#sortId").html(getProjectBuildSortById(data.list.sortId));
						}else if(name=="projectId")
						{
							$("#projectId").val(data.list.projectId);
						}else if(name=="status")
						{
							if(data.list.status=="1")
							{
								$("#cbut").remove();
							}
						}else
						{
							$("#"+name).html(data.list[name]);
						}
					}
			}
		}
	});
}
function readstage(stageId)
{
	window.open("/app/core/projectbuild/project/stagedetails?stageId="+stageId);
}

function delstage(stageId) {
	if (confirm("确定删除当前结点吗？")) {
		$.ajax({
			url : "/set/projectbuildset/delProjectBuildStage",
			type : "post",
			dataType : "json",
			data : {
				stageId:stageId
			},
			success : function(data) {
				if (data.status == 200) {
					top.layer.msg(data.msg);
					var projectId = $("#projectId").val();
					$("#myTable").bootstrapTable('destroy');
					query(projectId);
				} else {
					console.log(data.msg);
				}
			}
		});
	} else {
		return;
	}
}


function getProjectBuildSortById(sortId)
{
	var returnStr="";
	$.ajax({
		url : "/ret/projectbuildget/getProjectBuildSortById",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			sortId:sortId
		},
		success : function(data) {
			if (data.status == 200) {
				returnStr=data.list.sortName;
			} else {
				console.log(data.msg);
			}
		}
	});
	return returnStr;
}