$(function() {
	QuerySelect("channel");
	QuerySelect("courseType");
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
})

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/hrget/getHrTrainRecordApprovedOldList',
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
		}, {
			field : 'title',
			width : '150px',
			title : '培训标题'
		},{
			field : 'trainCode',
			width : '80px',
			title : '培训计划编号'
		},{
			field : 'channel',
			width : '100px',
			title : '培训渠道',
			formatter : function(value, row, index) {
				return getHrClassCodeName("channel",value);
			}
		},{
			field : 'courseType',
			width : '50px',
			title : '培训类型',
			formatter : function(value, row, index) {
				return getHrClassCodeName("courseType",value);
			}
		}, {
			field : 'institutionName',
			title : '培训机构名称',
			width : '100px'
		}, {
			field : 'courseName',
			title : '课程名称',
			width : '100px'
		}, {
			field : 'courseTime',
			title : '课时',
			width : '50px'
		}, {
			field : 'beginTime',
			width : '50px',
			title : '开课日期'
		}, {
			field : 'endTime',
			width : '50px',
			title : '结果日期'
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
		status : $("#statusQuery").val(),
		channel : $("#channelQuery").val(),
		courseType : $("#courseTypeQuery").val(),
		endTime : $("#endTimeQuery").val(),
		beginTime : $("#beginTimeQuery").val()
	};
	return temp;
};
function createOptBtn(recordId,status) {
	var html ="<a href=\"javascript:void(0);details('" + recordId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	return html;
}



function details(recordId)
{
	window.open("/app/core/hr/traindetails?recordId="+recordId);
}
