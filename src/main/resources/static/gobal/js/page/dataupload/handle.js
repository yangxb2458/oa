$(function() {
	jeDate("#beginTimeQuery", {
		format : "YYYY-MM-DD"
	});
	jeDate("#endTimeQuery", {
		format : "YYYY-MM-DD"
	});
	getCodeClass("dataTypeQuery","dataType");
	query();
	$(".js-simple-query").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	})
	getSmsConfig("msgType","datainfo");
})

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/datauploadget/getToProcessInfoList',
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
			field : 'title',
			title : '信息标题',
			width : '150px'
		}, {
			field : 'dataType',
			width : '50px',
			title : '信息类型',
			formatter : function(value, row, index) {
				return getCodeClassName(value,"dataType");
			}
		},{
			field : 'deptId',
			width : '100px',
			title : '上报部门',
			formatter : function(value, row, index) {
				return getDeptNameByDeptIds(value);
			}
		},{
			field : 'fromAccountId',
			width : '100px',
			title : '上报人',
			formatter : function(value, row, index) {
				return getUserNameByStr(value);
			}
		}, {
			field : 'approvedType',
			width : '50px',
			title : '审核方式',
			formatter : function(value, row, index) {
				if(value=="1")
				{
					return "线上审核";
				}else if(value=="2")
				{
					return "线下审核";
				}
			}
		}, {
			field : 'approvedUser',
			width : '50px',
			title : '审批人',
			formatter : function(value, row, index) {
					return getUserNameByStr(value);
				}
		}, {
			field : 'opt',
			title : '操作',
			align : 'center',
			width : '100px',
			formatter : function(value, row, index) {
				return createOptBtn(row.recordId,row.status);
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
		fromAccountId : $("#fromAccountIdQuery").attr("data-value"),
		deptId : $("#deptIdQuery").attr("data-value"),
		dataType : $("#dataTypeQuery").val(),
		endTime : $("#endTimeQuery").val(),
		beginTime : $("#beginTimeQuery").val(),
		approvedType:$("#approvedTypeQuery").val()
	};
	return temp;
};
function createOptBtn(recordId,status) {
	var html="";
	if(status=="0")
	{
		html += "<a href=\"javascript:void(0);doprocess('" + recordId + "')\" class=\"btn btn-primary btn-xs\">处理结果</a>&nbsp;&nbsp;";
	}
	html += "<a href=\"javascript:void(0);details('" + recordId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	return html;
}

function doprocess(recordId)
{
	document.getElementById("form1").reset();
	$("#show_datainfoattach").html("");
	$("#processmodal").modal("show");
	$(".js-save").unbind("click").click(function(){
		addHanle(recordId);
	})
}
function addHanle(recordId)
{
	$.ajax({
		url : "/set/datauploadset/insertDataUploadHandle",
		type : "post",
		dataType : "json",
		data:{
			recordId:recordId,
			remark:$("#remark").val(),
			attach:$("#datainfoattach").attr("data_value")
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
				window.location.reload();
				top.layer.msg(data.msg);
				}
		}
	})
}
function details(recordId)
{
	window.open("/app/core/dataupload/uploadinfodetails?recordId="+recordId);
}
