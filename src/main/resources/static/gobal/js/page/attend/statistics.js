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
		url : '/ret/oaget/getTotalAttendList',
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
		search : false,//启用搜索
		showColumns : true,//是否显示 内容列下拉框
		showRefresh : true,//显示刷新按钮
		idField : 'attendId',//key值栏位
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
			field : 'createUserName',
			title : '人员姓名',
			sortable : true,
			width : '80px'
		}, {
			field : 'type',
			width : '50px',
			title : '考勤状态',
			formatter : function(value, row, index) {
				if (value == "0") {
					return "正常打卡";
				}else if(value=="1"){
					return "补打卡";
				}else if(value=="2"){
					return "迟到";
				}else if(value=="3"){
					return "早退";
				}else if(value=="4"){
					return "旷工";
				}
			}
		}, {
			field : 'stauts',
			title : '考勤类型',
			width : '50px',
			formatter : function(value, row, index) {
				if (value == "1") {
					return "上班打卡";
				} else if (value == "2") {
					return "下班打卡";
				}

			}
		}, {
			field : 'year',
			width : '50px',
			title : '年'
		}, {
			field : 'month',
			width : '50px',
			title : '月'
		}, {
			field : 'day',
			width : '50px',
			title : '日'
		}, {
			field : 'time',
			width : '50px',
			title : '打卡时间'
		}, {
			field : 'remark',
			width : '200px',
			title : '备注'
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
		type : $("#type").val(),
		beginTime : $("#beginTime").val(),
		endTime : $("#endTime").val(),
		deptId : $("#deptId").attr("data-value"),
		createUser : $("#createUser").attr("data-value")
		
	};
	return temp;
};


