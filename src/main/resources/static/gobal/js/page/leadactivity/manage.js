$(function(){
	jeDate("#beginTimeQuery", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTimeQuery", {
		format: "YYYY-MM-DD"
	});
	$('#remark').summernote({ height:200 });
	query();
	$(".js-simple-query").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	})
})

function query()
{
	$("#myTable").bootstrapTable({
		url : '/ret/oaget/getLeadActivityLsit',
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
			field : 'leaderUserName',
			title : '领导姓名',
			width : '80px'
		}, {
			field : 'title',
			width : '100px',
			title : '行程标题'
		}, {
			field : 'beginTime',
			width : '50px',
			title : '开始时间'
		}, {
			field : 'endTime',
			width : '50px',
			title : '结束时间'
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
		leader : $("#leaderQuery").attr("data-value"),
		endTime : $("#endTimeQuery").val(),
		beginTime : $("#beginTimeQuery").val()
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
	if(confirm("确定删除当前行程吗？"))
    {
	$.ajax({
		url : "/set/oaset/deleteLeadActivity",
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
	$("#recordlistdiv").hide();
	$("#recorddiv").show();
	$(".js-back-btn").unbind("click").click(function(){
		goback();
	})
	$.ajax({
		url : "/ret/oaget/getLeadActivityById",
		type : "post",
		dataType : "json",
		data:{recordId:recordId},
		success : function(data) {
			if(data.status=="200")
			{
				var recordInfo = data.list;
				for(var id in recordInfo)
				{
					if(id=="leader")
					{
						$("#"+id).val(getUserNameByStr(recordInfo[id]));
						$("#"+id).attr("data-value",recordInfo[id]);
					}else if(id=="remark")
					{
						$("#remark").code(recordInfo[id]);
					}else if(id=="userPriv")
					{
						$("#"+id).val(getUserNameByStr(recordInfo[id]));
						$("#"+id).attr("data-value",recordInfo[id]);
					}else if(id=="deptPriv")
					{
						$("#deptPriv").attr("data-value", recordInfo[id]);
						$("#deptPriv").val(getDeptNameByDeptIds(recordInfo[id]));
					}else if(id=="levelPriv")
					{
						$("#levelPriv").attr("data-value",recordInfo[id]);
						$("#levelPriv").val(getUserLevelStr(recordInfo[id]));
					}else
					{
						$("#"+id).val(recordInfo[id]);
					}
				}
				$(".js-update-save").unbind("click").click(function(){
					updateLeadActivity(recordId);
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
	window.open("/app/core/leadactivity/details?recordId="+recordId);
}
function updateLeadActivity(recordId)
{
	$.ajax({
		url : "/set/oaset/updateLeadActivity",
		type : "post",
		dataType : "json",
		data:{
			recordId:recordId,
			title:$("#title").val(),
			leader:$("#leader").attr("data-value"),
			userPriv:$("#userPriv").attr("data-value"),
			deptPriv:$("#deptPriv").attr("data-value"),
			levelPriv:$("#levelPriv").attr("data-value"),
			remark:$("#remark").code(),
			beginTime:$("#beginTime").val(),
			endTime:$("#endTime").val()
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
	$("#recorddiv").hide();
	$("#recordlistdiv").show();
}