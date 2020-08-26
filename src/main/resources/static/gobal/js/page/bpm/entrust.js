$(function() {
	query();
	jeDate("#beginTime", {
		format : "YYYY-MM-DD",
		minDate:getSysDate(),
		isinitVal: true
	});
	jeDate("#endTime", {
		format : "YYYY-MM-DD"
	});
//	getSmsConfig("msgType", "entrust");
	$(".js-btn").unbind("click").click(function() {
		document.getElementById("form1").reset();
		$("#entrustModal").modal("show");
	});
	getALlBpmFlowList();
	$(".js-createEntrust").unbind("click").click(function() {
		createEntrust();
	});
	$("#flowName").unbind("change").change(function(){
		//var flowId = $(this).val();
		// getbpmFlowInfo(flowId);
	})
	
})

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/bpmget/getMyEntrustList',
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
		idField : 'entrustId',//key值栏位
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
			field : 'flowName',
			title : '流程名称',
			sortable : true,
			width : '200px'
		}, {
			field : 'beginTime',
			title : '开始时间',
			align : 'center',
			width : '100px'
		}, {
			field : 'endTime',
			width : '100px',
			align : 'center',
			title : '结束时间'
		}, {
			field : 'toUserName',
			width : '100px',
			align : 'center',
			title : '受托人'
		}, {
			field : 'createUserName',
			width : '100px',
			align : 'center',
			title : '设置人'
		}, {
			field : '',
			width : '100px',
			title : '状态',
			align : 'center',
			formatter : function(value, row, index) {
				if (row.status == "1") {
					return "<a href='javascript:void(0);' class='btn btn-purple btn-sm'>终止</a>";
				} else {
					var dayNows = getSysTime().split(" ")[0];
					if (dayNows <= row.endTime) {
						return "<a href='javascript:void(0);' class='btn btn-success btn-sm'>正常</a>";
					} else {
						return "<a href='javascript:void(0);' class='btn btn-danger btn-sm'>过时</a>";
					}
				}

			}
		}, {
			field : 'opt',
			title : '操作',
			width : '100px',
			align : 'center',
			formatter : function(value, row, index) {
				return createOptBtn(row.entrustId);
			}
		} ],
		onClickCell : function(field, value, row, $element) {
			//alert(row.SystemDesc);
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
function createOptBtn(entrustId) {
	var html = "<a href=\"javascript:void(0);stopEntrust('" + entrustId + "')\" class=\"btn btn-primary btn-xs\">终止</a>&nbsp;&nbsp;" + "<a href=\"javascript:void(0);del('"
			+ entrustId + "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
}

function edit(newsId) {
	//window.location.href = "/app/core/editnews?newsId=" + newsId;
	open("/app/core/editnews?newsId=" + newsId,"_self");
}

function createEntrust() {
	var flowId = $("#flowName").val();
	var beginTime = $("#beginTime").val();
	var endTime = $("#endTime").val();
	var toId = $("#toId").attr("data-value");
	if (flowId == "") {
		top.layer.msg("请先选择需要委托的流程!");
		return;
	}
	if (beginTime == "") {
		top.layer.msg("请先确认委托的开始时间!");
		return;
	}
	if (endTime == "") {
		top.layer.msg("请先确定委托的结束时间!");
		return;
	}
	if (toId == "") {
		top.layer.msg("请先确定自己的流程委托对象!");
		return;
	}
	$.ajax({
		url : '/set/bpmset/createEntrust',
		type : "post",
		dataType : "json",
		data : {
			flowId : flowId,
			beginTime : beginTime,
			endTime : endTime,
			toId : toId
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				top.layer.msg(data.msg);
				$("#entrustModal").modal("hide");
				$('#myTable').bootstrapTable('refresh');
			}
		}
	})
}

function getALlBpmFlowList() {
	$.ajax({
		url : '/ret/bpmget/getMyAllBpmFlowList',
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				var html = "<option value=''>请选择</option>";
				for (var i = 0; i < data.list.length; i++) {
					html += "<option value='" + data.list[i].flowId + "'>" + data.list[i].flowName + "</option>";
				}
				$("#flowName").html(html);
			}
		}
	})
}

function stopEntrust(entrustId) {
	if(confirm("确定终止委托吗？"))
    {
	$.ajax({
		url : '/set/bpmset/stopEntrust',
		type : "post",
		dataType : "json",
		data : {
			entrustId : entrustId
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				top.layer.msg(data.msg);
				$('#myTable').bootstrapTable('refresh');
			}
		}
	})
    }
}
function del(entrustId) {
	if(confirm("确定删除委托记录吗？"))
    {
	$.ajax({
		url : '/set/bpmset/delEntrust',
		type : "post",
		dataType : "json",
		data : {
			entrustId : entrustId
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				top.layer.msg(data.msg);
				$('#myTable').bootstrapTable('refresh');
			}
		}
	})
    }
}


function getbpmFlowInfo(flowId) {
	$.ajax({
		url : '/ret/bpmget/getBpmFlow',
		type : "post",
		dataType : "json",
		data : {
			flowId : flowId
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				if(data.list.freeToOther=="1")
				{
				//自由委托	
				}else if(data.list.freeToOther=="2")
				{
				//同级委托
				
				}
			}
		}
	})
}
