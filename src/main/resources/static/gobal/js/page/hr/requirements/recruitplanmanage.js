$(function() {
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
	jeDate("#beginTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate()
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate()
	});
	jeDate("#planEndTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate()
	});
	$('#remark').summernote({ height:300 });
})

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/hrget/getHrRecruitPlanList',
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
		idField : 'planId',//key值栏位
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
			field : 'title',
			title : '计划标题',
			sortable : true,
			width : '150px'
		},{
			field : 'planEndTime',
			width : '100px',
			title : '填报截止日期'
		}, {
			field : 'beginTime',
			width : '100px',
			title : '招聘开始日期'
		}, {
			field : 'endTime',
			width : '100px',
			title : '招聘结束日期'
		}, {
			field : 'approvedUserName',
			width : '100px',
			title : '审批人'
		}, {
			field : 'createTime',
			title : '创建时间',
			width : '100px'
		}, {
			field : 'opt',
			title : '操作',
			align : 'center',
			width : '120px',
			formatter : function(value, row, index) {
				return createOptBtn(row.planId,row.status);
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
		endTime : $("#endTimeQuery").val(),
		beginTime : $("#beginTimeQuery").val()
	};
	return temp;
};
function createOptBtn(planId,status) {
	var html ="";
	if(status=='true')
	{
		html += "<a href=\"javascript:void(0);edit('" + planId + "')\" class=\"btn btn-primary btn-xs\">编辑</a>&nbsp;&nbsp;" ;
		html += "<a href=\"javascript:void(0);deleteReocrd('" + planId + "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>&nbsp;&nbsp;";
	}
	html += "<a href=\"javascript:void(0);details('" + planId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	return html;
}

function deleteReocrd(planId)
{
	if(confirm("确定删除当前记录吗？"))
    {
	$.ajax({
		url : "/set/hrset/deleteHrRecruitPlan",
		type : "post",
		dataType : "json",
		data:{planId:planId},
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
function edit(planId)
{
	$("#planlistdiv").hide();
	$("#plandiv").show();
	$(".js-back-btn").unbind("click").click(function(){
		goback();
	})
	$.ajax({
		url : "/ret/hrget/getHrRecruitPlanById",
		type : "post",
		dataType : "json",
		data:{planId:planId},
		success : function(data) {
			if(data.status=="200")
			{
				var planInfo = data.list;
				for(var id in planInfo)
				{
					if(id=="attach")
					{
						$("#show_hrattach").html("");
						$("#hrattach").attr("data_value", planInfo.attach);
						createAttach("hrattach", 4);
					}else if(id=="approvedUser")
					{
						$("#"+id).val(getUserNameByStr(planInfo[id]));
						$("#"+id).attr("data-value",planInfo[id]);
					}else if(id=="remark")
					{
						$("#remark").code(planInfo[id]);
					}else
					{
						$("#"+id).val(planInfo[id]);
					}
				}
				$(".js-update-save").unbind("click").click(function(){
					updateHrRecruitPlan(planId);
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
function details(planId)
{
	window.open("/app/core/hr/hrrecruitplandetails?planId="+planId);
}
function updateHrRecruitPlan(planId)
{
	$.ajax({
		url : "/set/hrset/updateHrRecruitPlan",
		type : "post",
		dataType : "json",
		data:{
			planId:planId,
			sortNo:$("#sortNo").val(),
			planEndTime:$("#planEndTime").val(),
			title:$("#title").val(),
			beginTime:$("#beginTime").val(),
			endTime:$("#endTime").val(),
			approvedUser:$("#approvedUser").attr("data-value"),
			attach:$("#hrattach").attr("data_value"),
			remark:$("#remark").code()
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
	$("#plandiv").hide();
	$("#planlistdiv").show();
}