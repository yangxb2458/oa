$(function(){
	query();
})

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/hrget/getMyHrPersonnelTransferList',
		method : 'post',
		contentType : 'application/x-www-form-urlencoded',
		toolbar : '#toobar',// 工具列
		striped : true,// 隔行换色
		cache : false,// 禁用缓存
		pagination : true,// 启动分页
		sidePagination : 'server',// 分页方式
		pageNumber : 1,// 初始化table时显示的页码
		pageSize : 10,// 每页条目
		showFooter : false,// 是否显示列脚
		showPaginationSwitch : false,// 是否显示 数据条数选择框
		sortable : true,// 排序
		search : false,// 启用搜索
		sortOrder : "asc",
		showColumns : false,// 是否显示 内容列下拉框
		showRefresh : false,// 显示刷新按钮
		idField : 'transferId',// key值栏位
		clickToSelect : true,// 点击选中checkbox
		pageList : [ 10, 20, 30, 50 ],// 可选择单页记录数
		queryParams : queryParams,
		columns : [ {
			checkbox : true
		}, {
			field : 'num',
			title : '序号',// 标题 可不加
			width : '50px',
			formatter : function(value, row, index) {
				return index + 1;
			}
		}, {
			field : 'title',
			width : '100px',
			title : '文件标题'
		},{
			field : 'userId',
			title : '调动人员',
			width : '80px',
			formatter : function(value, row, index) {
				if (row.userName == "" || row.userName == null) {
					return getHrUserNameByStr(row.userId);
				} else {
					return row.userName;
				}
			}
		}, {
			field : 'transferType',
			width : '100px',
			title : '调动类型',
			formatter : function(value, row, index) {
				return getHrClassCodeName('transferType', value);
			}
		},{
			field : 'deptName',
			width : '100px',
			title : '调动前部门'
		},{
			field : 'transferDept',
			width : '100px',
			title : '调动后部门'
		},{
			field : 'levelName',
			width : '100px',
			title : '调动前职务'
		},{
			field : 'transferLevel',
			width : '100px',
			title : '调动后职务'
		}, {
			field : 'transferTime',
			sortable : true,
			width : '50px',
			title : '调动日期'
		}, {
			field : 'startTime',
			sortable : true,
			width : '50px',
			title : '生效日期'
		}, {
			field : 'opt',
			title : '操作',
			align : 'center',
			width : '120px',
			formatter : function(value, row, index) {
				return createOptBtn(row.transferId);
			}
		} ],
		onClickCell : function(field, value, row, $element) {
			// alert(row.SystemDesc);
		},
		responseHandler : function(res) {
			if (res.status == "500") {
				top.layer.msg(res.msg);
			} else {
				return {
					total : res.list.total, // 总页数,前面的key必须为"total"
					rows : res.list.list
				// 行数据，前面的key要与之前设置的dataField的值一致.
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
		sortOrder : params.order
	};
	return temp;
};
function createOptBtn(transferId) {
	var html = "<a href=\"javascript:void(0);details('" + transferId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	return html;
}
function details(transferId) {
	window.open("/app/core/hr/transferdetails?transferId=" + transferId);
}