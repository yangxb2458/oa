$(function() {
	query();
	jeDate("#beginTime", {
		format : "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format : "YYYY-MM-DD"
	});
	getCodeClass("noticeType", "notice");
	$(".js-query-but").unbind("click").click(function() {
		$("#myTable").bootstrapTable("refresh");
	});
})
function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/sysget/getMySysLogList',
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
		sortOrder:'desc',
		search : true,//启用搜索
		showColumns : true,//是否显示 内容列下拉框
		showRefresh : true,//显示刷新按钮
		idField : 'logId',//key值栏位
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
			field : 'eventType',
			title : '事件类型',
			sortable : true,
			width : '100px',
			formatter:function(value,row,index){
				if(value=="1")
					{
						return "登陆事件";
					}else if(value=="2")
					{
						return "注消事件";
					}else
					{
						return "其它";
					}
	            }
		}, {
			field : 'remark',
			width : '200px',
			title : '详情'
		}, {
			field : 'ip',
			title : 'IP',
			width : '100px'
		}, {
			field : 'createUserName',
			width : '100px',
			title : '操作人'
		}, {
			field : 'createTime',
			width : '100px',
			title : '创建时间'
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
		eventType : $("#eventType").val(),
		beginTime : $("#beginTime").val(),
		endTime : $("#endTime").val()
	};
	return temp;
};
