var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/bpmget/getBpmFlowSortTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "bpmSortId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	view : {
		dblClickExpand : false,
		selectedMulti : false
	//禁止多选
	},
	callback : {
		onClick : zTreeOnClick
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "bpmSortId",
			rootPId : "0"
		},
		key : {
			name : "bpmSortName"
		}
	}
};

$(function() {
	query();
	var topNode = [ {
		bpmSortName : "全部",
		orgLeaveId : '',
		isParent : "true",
		bpmSortId : "0",
		icon : "/gobal/img/org/org.png"
	} ];
	var zTreeObj = $.fn.zTree.init($("#tree"), setting, topNode);
	var nodes = zTreeObj.getNodes();
	for (var i = 0; i < nodes.length; i++) {
		zTreeObj.expandNode(nodes[i], true, false, false);//默认展开第一级节点
	}
	$("#bpmSrot").unbind("click").click(function(e) {
		e.stopPropagation();
		$("#menuContent").css({
			"width" : $(this).outerWidth() + "px",
			"left":(document.getElementById("bpmSrot").offsetLeft-40)+ "px"
		}).slideDown(200);
	});

	$("body").unbind("click").click(function() {
		$("#menuContent").hide();
	});

	$("#menuContent").unbind("click").click(function(e) {
		e.stopPropagation();
	});
	$(".js-simple-query").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	});
});
function zTreeOnClick(event, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("tree"), nodes = zTree.getSelectedNodes(), v = "";
	if(!treeNode.isParent)
	{
		vid = "";
		nodes.sort(function compare(a, b) {
			return a.id - b.bpmSortId;
		});
		for (var i = 0, l = nodes.length; i < l; i++) {
			v += nodes[i].bpmSortName + ",";
			vid += nodes[i].bpmSortId + ",";
		}
		if (v.length > 0)
			v = v.substring(0, v.length - 1);
		if (vid.length > 0)
			vid = vid.substring(0, vid.length - 1);
		var idem = $("#bpmSrot");
		idem.attr("data-value", vid);
		idem.val(v);
	}
}
function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/bpmget/searchBpmList',
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
		idField : 'runId',//key值栏位
		clickToSelect : true,//点击选中checkbox
		pageList : [ 10, 20, 30, 50 ],//可选择单页记录数
		queryParams : queryParams,
		columns : [ {
			checkbox : true
		}, {
			field : 'id',
			width : '50px',
			sortable : true,
			title : '流水号'
		}, {
			field : 'flowTitle',
			title : '流程标题',
			sortable : true,
			width : '200px'
		}, {
			field : 'opUserStr',
			width : '100px',
			align : 'center',
			title : '参与人员',
			formatter : function(value, row, index) {
				return getUserNameByStr(value);
			}
		}, {
			field : 'urgency',
			width : '50px',
			title : '紧急程度',
			align : 'center',
			formatter : function(value, row, index) {
				if (value == "0") {
					return "一般";
				} else if (value == "1") {
					return "紧急";
				} else if (value == "2") {
					return "加急";
				}
			}
		}, {
			field : 'createUser',
			width : '50px',
			align : 'center',
			title : '流程创建人'
		}, {
			field : 'createTime',
			width : '50px',
			align : 'center',
			sortable : true,
			title : '创建时间'
		}, {
			field : 'status',
			width : '50px',
			align : 'center',
			title : '当前状态',
			formatter : function(value, row, index) {
				if (value == "0") {
					return "<a href=\"javascript:void(0);\" class=\"btn btn-palegreen btn-xs\">进行中</a>";
				} else if (value == "1") {
					return " <a href=\"javascript:void(0);\" class=\"btn btn-darkorange btn-xs\">已结束</a>";
				} else {
					return "<a href=\"javascript:void(0);\" class=\"btn btn-darkorange btn-xs\">异常</a>";
				}

			}
		}, {
			field : 'opt',
			title : '操作',
			align : 'center',
			width : '100px',
			formatter : function(value, row, index) {
				return createOptBtn(row.runId, row.flowId);
			}
		} ],
		onClickCell : function(field, value, row, $element) {
			//alert(row.SystemDesc);
		},
		responseHandler : function(res) {
			if (res.status == "500") {
				console.log(res.msg);
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
function createOptBtn(runId, flowId) {
	var html = "<a href=\"javascript:void(0);read('" + runId + "','" + flowId + "')\" class=\"btn btn-primary btn-xs\">查看</a>&nbsp;&nbsp;"
			+ "<a href=\"javascript:void(0);flowchart('" + runId + "','" + flowId + "')\" class=\"btn btn-success btn-xs\">流程图</a>&nbsp;&nbsp;"
			+ "<a href=\"javascript:void(0);print('" + runId + "','" + flowId + "')\" class=\"btn btn-darkorange btn-xs\" >打印</a>";
	return html;
}
function read(runId, flowId) {
	//window.location.href = "/app/core/bpm/bpmread?runId=" + runId + "&flowId=" + flowId;
	open("/app/core/bpm/bpmread?runId=" + runId + "&flowId=" + flowId,"_self");
}

function print(runId, flowId)
{
	window.open("/app/core/bpm/bpmread?runId=" + runId + "&flowId=" + flowId);
}


function flowchart(runId, flowId) {
	//window.location.href = "/app/core/bpm/bpmFlowChart?runId=" + runId + "&flowId=" + flowId;
	open("/app/core/bpm/bpmFlowChart?runId=" + runId + "&flowId=" + flowId,"_self");
}

function queryParams(params) {
	var temp = {
		search : params.search,
		pageSize : this.pageSize,
		pageNumber : this.pageNumber,
		sort : params.sort,
		sortOrder : params.order,
		flowId:$("#bpmSrot").attr("data-value"),
		id:$("#bpmRunNo").val(),
		managePriv:$("#managePriv").val(),
		status:$("#status").val(),
		createUser:$("#createUsre").attr("data-value")
	};
	return temp;
};