$(function() {
	jeDate("#beginTimeQuery", {
		format : "YYYY-MM-DD"
	});
	jeDate("#endTimeQuery", {
		format : "YYYY-MM-DD"
	});
	jeDate("#sendTime", {
		format : "YYYY-MM-DD",
		minDate:getSysDate()
	});
	getCodeClass("dataType","dataType");
	getCodeClass("dataTypeQuery","dataType");
	query();
	$(".js-simple-query").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	})
	$('#remark').summernote({ height:300 });
	getSmsConfig("msgType","datainfo");
})

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/datauploadget/getDataUploadInfoList',
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
		fromAccountId : $("#fromAccountIdQuery").attr("data-value"),
		deptId : $("#deptIdQuery").attr("data-value"),
		dataType : $("#dataTypeQuery").val(),
		endTime : $("#endTimeQuery").val(),
		beginTime : $("#beginTimeQuery").val(),
		approvedType:$("#approvedTypeQuery").val()
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
	$.ajax({
		url : "/set/datauploadset/deleteDataUploadInfo",
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
function edit(recordId)
{
	$("#infolistdiv").hide();
	$("#infodiv").show();
	$(".js-back-btn").unbind("click").click(function(){
		goback();
	})
	$.ajax({
		url : "/ret/datauploadget/getDataUploadInfoById",
		type : "post",
		dataType : "json",
		data:{recordId:recordId},
		success : function(data) {
			if(data.status=="200")
			{
				var info = data.list;
				for(var id in info)
				{
					if(id=="attach")
					{
						$("#show_hrattach").html("");
						$("#hrattach").attr("data_value", info.attach);
						createAttach("hrattach", 4);
					}else if(id=="fromAccountId"||id=="toUser"||id=="approvedUser")
					{
						$("#"+id).val(getUserNameByStr(info[id]));
						$("#"+id).attr("data-value",info[id]);
					}else if(id=="deptId")
					{
						$("#"+id).val(getDeptNameByDeptIds(info[id]));
						$("#"+id).attr("data-value",info[id]);
					}else if(id=="remark")
					{
						$("#remark").code(info[id]);
					}else
					{
						$("#"+id).val(info[id]);
					}
				}
				$(".js-update-save").unbind("click").click(function(){
					updateDataUploadInfo(recordId);
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
	window.open("/app/core/dataupload/uploadinfodetails?recordId="+recordId);
}
function updateDataUploadInfo(recordId)
{
	$.ajax({
		url : "/set/datauploadset/updateDataUploadInfo",
		type : "post",
		dataType : "json",
		data:{
			recordId:recordId,
			title:$("#title").val(),
			dataType:$("#dataType").val(),
			deptId:$("#deptId").attr("data-value"),
			fromAccountId:$("#fromAccountId").attr("data-value"),
			approvedType:$("#approvedType").val(),
			approvedUser:$("#approvedUser").attr("data-value"),
			sendTime:$("#sendTime").val(),
			toUser:$("#toUser").attr("data-value"),
			attach:$("#hrattach").attr("data_value"),
			msgType:getCheckBoxValue("msgType"),
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
	$("#infodiv").hide();
	$("#infolistdiv").show();
}