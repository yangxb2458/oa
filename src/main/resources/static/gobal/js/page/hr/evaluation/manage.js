$(function() {
	jeDate("#beginTimeQuery", {
		format : "YYYY-MM-DD"
	});
	jeDate("#endTimeQuery", {
		format : "YYYY-MM-DD"
	});
	query();
	$(".js-simple-query").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	})
	$('#remark').summernote({ height:300 });
	$(".js-auto-select").each(function(){
		var module = $(this).attr("module");
		createAutoSelect(module);
	})
	QuerySelect("getType");
	jeDate("#applyTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#receiveTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#nextApplyTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#employBeginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#employEndTime", {
		format: "YYYY-MM-DD"
	});

})

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/hrget/getHrTitleEvaluationList',
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
		sortOrder: "asc",
		showColumns : true,//是否显示 内容列下拉框
		showRefresh : true,//显示刷新按钮
		idField : 'recordId',//key值栏位
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
		},{
			field : 'userId',
			title : '职称申请人',
			sortable : true,
			width : '80px',
			formatter : function(value, row, index) {
				if (row.userName == "" || row.userName == null) {
					return getHrUserNameByStr(row.userId);
				} else {
					return row.userName;
				}
			}
		}, {
			field : 'postName',
			width : '100px',
			title : '申请职称名称'
		},{
			field : 'receiveTime',
			width : '100px',
			title : '获取职称日期'
		}, {
			field : 'nextPostName',
			width : '100px',
			title : '下次申请职称名称'
		}, {
			field : 'nextApplyTime',
			width : '100px',
			title : '下次申请职称日期'
		}, {
			field : 'employComp',
			title : '聘用公司',
			width : '100px'
		},{
			field : 'employBeginTime',
			title : '聘用开时间',
			width : '100px'
		},{
			field : 'employEndTime',
			title : '聘用结束时间',
			width : '100px'
		}, {
			field : 'opt',
			title : '操作',
			align : 'center',
			width : '120px',
			formatter : function(value, row, index) {
				return createOptBtn(row.recordId);
			}
		} ],
		onClickCell : function(field, value, row, $element) {
			//alert(row.SystemDesc);
		},
		responseHandler : function(res) {
			if (res.status == "500") {
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
		sortOrder : params.order,
		userId : $("#userIdQuery").attr("data-value"),
		endTime : $("#endTimeQuery").val(),
		beginTime : $("#beginTimeQuery").val(),
		getType : $("#getTypeQuery").val()
	};
	return temp;
};
function createOptBtn(recordId) {
	var html = "<a href=\"javascript:void(0);edit('" + recordId + "')\" class=\"btn btn-primary btn-xs\">编辑</a>&nbsp;&nbsp;" 
	+ "<a href=\"javascript:void(0);deleteReocrd('" + recordId + "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>&nbsp;&nbsp;";
	html += "<a href=\"javascript:void(0);details('" + recordId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	return html;
}

function deleteReocrd(recordId)
{
	if(confirm("确定删除当前记录吗？"))
    {
	$.ajax({
		url : "/set/hrset/deleteHrTitleEvaluation",
		type : "post",
		dataType : "json",
		data:{recordId:recordId},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				$("#myTable").bootstrapTable("refresh");
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
}
function edit(recordId)
{
	$("#evaluationlistdiv").hide();
	$("#evaluationdiv").show();
	$(".js-back-btn").unbind("click").click(function(){
		goback();
	})
	$.ajax({
		url : "/ret/hrget/getHrTitleEvaluationById",
		type : "post",
		dataType : "json",
		data:{recordId:recordId},
		success : function(data) {
			if(data.status=="200")
			{
				var recordInfo = data.list;
				for(var id in recordInfo)
				{
					if(id=="attach")
					{
						$("#show_hrattach").html("");
						$("#hrattach").attr("data_value", recordInfo.attach);
						createAttach("hrattach", 4);
					}else if(id=="userId")
					{
						$("#"+id).val(getHrUserNameByStr(recordInfo[id]));
						$("#"+id).attr("data-value",recordInfo[id]);
					}else if(id=="deptId")
					{
						$("#"+id).val(getHrDeptNameByStr(recordInfo[id]));
						$("#"+id).attr("data-value",recordInfo[id]);
					}else if(id=="post")
					{
						$("#"+id).val(getHrUserLevelByStr(recordInfo[id]));
						$("#"+id).attr("data-value",recordInfo[id]);
					}else if(id=="remark")
					{
						$("#remark").code(recordInfo[id]);
					}else
					{
						$("#"+id).val(recordInfo[id]);
					}
				}
				$(".js-update-save").unbind("click").click(function(){
					updateHrTitleEvaluation(recordId);
				})
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
		})
}
function details(recordId)
{
	window.open("/app/core/hr/evaluationdetails?recordId="+recordId);
}
function updateHrTitleEvaluation(recordId)
{
	$.ajax({
		url : "/set/hrset/updateHrTitleEvaluation",
		type : "post",
		dataType : "json",
		data:{
			recordId:recordId,
			sortNo:$("#sortNo").val(),
			userId:$("#userId").attr("data-value"),
			deptId:$("#deptId").attr("data-value"),
			post:$("#post").attr("data-value"),
			postName:$("#postName").val(),
			getType:$("#getType").val(),
			applyTime:$("#applyTime").val(),
			receiveTime:$("#receiveTime").val(),
			nextApplyTime:$("#nextApplyTime").val(),
			nextPostName:$("#nextPostName").val(),
			employComp:$("#employComp").val(),
			employPost:$("#employPost").val(),
			employBeginTime:$("#employBeginTime").val(),
			employEndTime:$("#employEndTime").val(),
			attach:$("#hrattach").attr("data_value"),
			remark:$("#remark").code()
		},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				location.reload();
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
	})
}
function goback()
{
	$("#evaluationdiv").hide();
	$("#evaluationlistdiv").show();
}