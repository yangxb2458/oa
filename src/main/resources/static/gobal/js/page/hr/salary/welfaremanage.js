$(function() {
	jeDate("#beginTimeQuery", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTimeQuery", {
		format: "YYYY-MM-DD"
	});
	query();
	$(".js-simple-query").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	})
	jeDate("#year", {
		format: "YYYY"
	});
	$('#remark').summernote({ height:300 });

})

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/hrget/getHrWelfareRecordList',
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
			title : '福利标题',
			width : '100px'
		},{
			field : 'userName',
			title : '人员姓名',
			sortable : true,
			width : '80px'
		}, {
			field : 'year',
			width : '80px',
			title : '年度'
		},{
			field : 'month',
			width : '50px',
			title : '月份'
		},{
			field : 'amount',
			width : '50px',
			title : '金额'
		}, {
			field : 'type',
			width : '50px',
			title : '福利类型',
			formatter : function(value, row, index) {
				if(value=="1")
				{
					return "现金";
				}else if(value=="2")
				{
					return "物质";
				}else if(value=="0")
				{
					return "其它";
				}
			}
		},{
			field : 'remark',
			title : '备注',
			width : '250px'
		},  {
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
		type : $("#typeQuery").val(),
		beginTime : $("#beingTimeQuery").val(),
		endTime : $("#endTimeQuery").val()
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
		url : "/set/hrset/deleteHrWelfareRecord",
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
	$("#welfarelistdiv").hide();
	$("#welfarediv").show();
	$(".js-back-btn").unbind("click").click(function(){
		goback();
	})
	$.ajax({
		url : "/ret/hrget/getHrWelfareRecordById",
		type : "post",
		dataType : "json",
		data:{recordId:recordId},
		success : function(data) {
			if(data.status=="200")
			{
				var recordinfo = data.list;
				for(var id in recordinfo)
				{
					if(id=="userId")
					{
						$("#"+id).val(getHrUserNameByStr(recordinfo[id]));
						$("#"+id).attr("data-value",recordinfo[id]);
					}else if(id=="remark")
					{
						$("#remark").code(recordinfo[id]);
					}else
					{
						$("#"+id).val(recordinfo[id]);
					}
				}
				$(".js-update-save").unbind("click").click(function(){
					updateHrWelfareRecord(recordId);
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
	window.open("/app/core/hr/welfaredetails?recordId="+recordId);
}
function updateHrWelfareRecord(recordId)
{
	$.ajax({
		url : "/set/hrset/updateHrWelfareRecord",
		type : "post",
		dataType : "json",
		data:{
			recordId:recordId,
			sortNo:$("#sortNo").val(),
			userId:$("#userId").attr("data-value"),
			title:$("#title").val(),
			year:$("#year").val(),
			month:$("#month").val(),
			type:$("#type").val(),
			amount:$("#amount").val(),
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
	$("#welfarediv").hide();
	$("#welfarelistdiv").show();
}