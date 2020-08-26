$(function() {
	query();
	$(".js-query-but").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	});
});

function approval(meetingId) {
	$("#applaymodal").modal("show");
	$(".js-notpass").unbind("click").click(function(){
		updateStatus(meetingId,"2")
	})
	$(".js-pass").unbind("click").click(function(){
		updateStatus(meetingId,"1")
	})
}

function updateStatus(meetingId,status)
{
	$.ajax({
	url : "/set/meetingset/updateMeeting",
	type : "post",
	dataType : "json",
	data : {
		meetingId:meetingId,
		status:status
	},
	success : function(data) {
		if (data.status == 200) {
			top.layer.msg(data.msg);
			$("#applaymodal").modal("hide");
			$("#myTable").bootstrapTable("refresh");
		} else {
			console.log(data.msg);
		}
	}
});
}

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/meetingget/getApplyMeetingList',
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
		idField : 'meetingId',//key值栏位
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
			field : 'subject',
			title : '会议主题名称',
			sortable : true,
			width : '100px'
		}, {
			field : 'name',
			width : '100px',
			title : '会议室'
		}, {
			field : 'chairUserName',
			title : '会议主持',
			width : '50px'
		}, {
			field : 'createUserName',
			width : '100px',
			title : '申请人'
		}, {
			field : 'beginTime',
			width : '100px',
			title : '开始时间'
		}, {
			field : 'endTime',
			width : '100px',
			title : '结束时间'
		},{
			field : 'remark',
			width : '200px',
			title : '备注'
		}, {
			field : 'opt',
			title : '操作',
			align:'center',
			width : '100px',
			formatter : function(value, row, index) {
				return createOptBtn(row.meetingId,row.status);
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
		sortOrder : params.order,
		beginTime:$("#beginTime").val(),
		endTime:$("#endTime").val()
	};
	return temp;
};
function createOptBtn(meetingId,status) {
	var html="";
	if(status=="0")
	{
		html += "<a href=\"javascript:void(0);approval('" + meetingId + "')\" class=\"btn btn-success btn-xs\" >审批</a>&nbsp;&nbsp;";
	}
	html+="<a href=\"javascript:void(0);details('" + meetingId+"')\" class=\"btn btn-darkorange btn-xs\" >详情</a>";
	return html;
}

function details(meetingId)
{
	window.open("/app/core/meeting/meetingdetails?meetingId="+meetingId);
}

