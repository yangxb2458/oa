$(function() {
	query();
	jeDate("#beginTime", {
		format : "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format : "YYYY-MM-DD"
	});
	$(".js-query-but").unbind("click").click(function() {
		$("#myTable").bootstrapTable("refresh");
	});
})
function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/oaget/getAllCalendarList',
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
		idField : 'calendarId',//key值栏位
		clickToSelect : false,//点击选中checkbox
		pageList : [ 10, 20, 30, 50 ],//可选择单页记录数
		queryParams : queryParams,
		columns : [ {
			field : 'num',
			title : '序号',//标题  可不加
			width : '50px',
			formatter : function(value, row, index) {
				return index + 1;
			}
		}, {
			field : 'calendarStart',
			sortable : true,
			width : '80px',
			title : '开始时间'
		}, {
			field : 'calendarEnd',
			title : '结束时间',
			width : '80px'
		}, {
			field : 'type',
			title : '日程类型',
			width : '80px',
			formatter : function(value, row, index) {
				if(value=="1")
				{
					return "部门例会";
				}else if(value=="2")
				{
					return "工作汇报";
				}else if(value=="3")
				{
					return "公司出差";
				}else if(value=="4")
				{
					return "接待客户";
				}else if(value=="5")
				{
					return "个人事务";
				}else if(value=="0")
				{
					return "其它事务";
				}
			}
		}, {
			field : 'title',
			title : '日程',
			width : '400px'
		}, {
			field : 'share',
			width : '50px',
			title : '领导是否可见',
			formatter : function(value, row, index) {
				if (value == "1") {
					return "可见"
				} else {
					return "不可见";
				}
			}
		}],
		onClickCell : function(field, value, row, $element) {
			//alert(row.SystemDesc);
		},
		responseHandler : function(res) {
			if (res.status == "500") {
				console.log(res.msg);
			} else if (res.status == "100") {
				top.layer.msg(res.msg);
			} else {
				console.log(res.list.list);
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
		type : $("#type").val(),
		beginTime : $("#beginTime").val(),
		endTime : $("#endTime").val()
	};
	return temp;
};

