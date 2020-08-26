$(function() {
	jeDate("#yearQuery", {
		format: "YYYY"
	});
	query();
	$(".js-simple-query").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	})
	jeDate("#year", {
		format: "YYYY"
	});

})

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/hrget/getHrSalaryRecordList',
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
			title : '人员姓名',
			sortable : true,
			width : '80px',
			formatter : function(value, row, index) {
					return getHrUserNameByStr(row.userId);
			}
		}, {
			field : 'year',
			width : '80px',
			title : '年度'
		},{
			field : 'month',
			width : '50px',
			title : '月份'
		},{
			field : 'sumAmount',
			width : '100px',
			title : '应发合计'
		}, {
			field : 'realCost',
			width : '100px',
			title : '实扣费用'
		},{
			field : 'realSalary',
			title : '实发工资',
			width : '100px'
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
		year : $("#yearQuery").val(),
		month : $("#monthQuery").val()
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
		url : "/set/hrset/deleteHrSalaryRecord",
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
	$("#salarylistdiv").hide();
	$("#salarydiv").show();
	$(".js-back-btn").unbind("click").click(function(){
		goback();
	})
	$.ajax({
		url : "/ret/hrget/getHrSalaryRecordById",
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
					}else
					{
						$("#"+id).val(recordinfo[id]);
					}
				}
				$(".js-update-save").unbind("click").click(function(){
					updateHrSalaryRecord(recordId);
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
	window.open("/app/core/hr/salarydetails?recordId="+recordId);
}
function updateHrSalaryRecord(recordId)
{
	$.ajax({
		url : "/set/hrset/updateHrSalaryRecord",
		type : "post",
		dataType : "json",
		data:{
			recordId:recordId,
			sortNo:$("#sortNo").val(),
			userId:$("#userId").attr("data-value"),
			year:$("#year").val(),
			month:$("#month").val(),
			postSalary:$("#postSalary").val(),
			levelSalary:$("#levelSalary").val(),
			foodSalary:$("#foodSalary").val(),
			otherPassSalary:$("#otherPassSalary").val(),
			transportSalary:$("#transportSalary").val(),
			postAllowance:$("#postAllowance").val(),
			sumAmount:$("#sumAmount").val(),
			pensoin:$("#pensoin").val(),
			unemployment:$("#unemployment").val(),
			medical:$("#medical").val(),
			accumulationFund:$("#accumulationFund").val(),
			tax:$("#tax").val(),
			costOther:$("#costOther").val(),
			realCost:$("#realCost").val(),
			realSalary:$("#realSalary").val(),
			msgType:getCheckBoxValue("msgType")
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
	$("#salarydiv").hide();
	$("#salarylistdiv").show();
}