$(function() {
	query();
	$(".js-query-but").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	});
});

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/meetingget/getOldApplyMeetingList',
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
			field : 'status',
			width : '50px',
			title : '审批状态',
			formatter : function(value, row, index) {
				if(value=="1")
				{
					return "通过";
				}else if(value=="2")
				{
					return "未通过";
				}else if(value=="3")
				{
					return "取消";
				}
			}
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
				return createOptBtn(row.meetingId);
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
		endTime:$("#endTime").val(),
		status:$("#status").val()
	};
	return temp;
};
function createOptBtn(meetingId) {
	var html="";
	html+="<a href=\"javascript:void(0);details('" + meetingId+"')\" class=\"btn btn-darkorange btn-xs\" >详情</a>";
	return html;
}

function details(meetingId)
{
	window.open("/app/core/meeting/meetingdetails?meetingId="+meetingId);
}
