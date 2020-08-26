$(function() {
	QuerySelect("highsetShool");
	QuerySelect("occupation");
	jeDate("#beginTimeQuery", {
		format : "YYYY-MM-DD"
	});
	jeDate("#endTimeQuery", {
		format : "YYYY-MM-DD"
	});
	query();
	$(".js-simple-query").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	})
	$(".js-auto-select").each(function(){
		var module = $(this).attr("module");
		createAutoSelect(module);
	})
})

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/hrget/getOldApprovedHrRecruitNeedsList',
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
			field : 'planTitle',
			title : '计划标题',
			width : '150px'
		}, {
			field : 'title',
			title : '标题',
			width : '100px'
		},{
			field : 'deptId',
			width : '100px',
			title : '需求部门',
			formatter : function(value, row, index) {
				return getHrDeptNameByStr(value);
			}
		},{
			field : 'userCount',
			width : '100px',
			title : '所需人数'
		}, {
			field : 'lastTime',
			width : '50px',
			title : '要求到岗日期'
		}, {
			field : 'workJob',
			width : '50px',
			title : '工种',
				formatter : function(value, row, index) {
					return getHrClassCodeName('workJob',value);
				}
		}, {
			field : 'occupation',
			title : '招聘类型',
			width : '100px',
			formatter : function(value, row, index) {
				return getHrClassCodeName('occupation',value);
			}
		}, {
			field : 'approvedTime',
			title : '审批时间',
			width : '100px'
		}, {
			field : 'opt',
			title : '操作',
			align : 'center',
			width : '120px',
			formatter : function(value, row, index) {
				return createOptBtn(row.recordId,row.status);
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
		status:$("#statusQuery").val(),
		occupation : $("#occupationQuery").val(),
		endTime : $("#endTimeQuery").val(),
		beginTime : $("#beginTimeQuery").val(),
		highsetShool:$("#highsetShoolQuery").val()
		
	};
	return temp;
};
function createOptBtn(recordId,status) {
	var html ="";
	html += "<a href=\"javascript:void(0);details('" + recordId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	return html;
}
function details(recordId)
{
	window.open("/app/core/hr/requirementsdetails?recordId="+recordId);
}
