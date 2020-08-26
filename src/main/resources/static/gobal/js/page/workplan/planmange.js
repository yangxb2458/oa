$(function(){
	$('#remark').summernote({ height:300 });
	getCodeClass("planType","workplan");
	getCodeClass("planTypeQuery","workplan");
	jeDate("#beginTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate(),
		isinitVal: true
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate(),
	});
	jeDate("#beginTimeQuery", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTimeQuery", {
		format: "YYYY-MM-DD"
	});
	getSmsConfig("msgType","workplan");
	$(".js-add-save").unbind("click").click(function(){
		addWorkPlan();
	})
	$(".js-simple-query").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	})
	query();
})

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/workplanget/getManageWorkPlanList',
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
			field : 'planType',
			title : '计划类型',
			width : '80px',
			formatter : function(value, row, index) {
				return "【"+getCodeClassName(value,"workplan")+"】";
			}
		},{
			field : 'title',
			title : '计划标题',
			width : '150px'
		}, {
			field : 'beginTime',
			width : '100px',
			title : '开始日期'
		},{
			field : 'endTime',
			width : '100px',
			title : '结束日期'
		},{
			field : 'status',
			width : '100px',
			title : '当前状态',
			formatter : function(value, row, index) {
				if(value=="0")
				{
					return "进行中";
				}else if(value=="1")
				{
					return "结束";
				}else if(value=="2")
				{
					return "挂起";
				}
			}
		}, {
			field : 'holdUserName',
			width : '100px',
			title : '负责人'
		}, {
			field : 'supUserName',
			width : '100px',
			title : '督查人'
		}, {
			field : 'opt',
			title : '操作',
			align : 'center',
			width : '120px',
			formatter : function(value, row, index) {
				return createOptBtn(row.planId);
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
		planType : $("#planTypeQuery").val(),
		endTime : $("#endTimeQuery").val(),
		beginTime : $("#beginTimeQuery").val()
	};
	return temp;
};
function createOptBtn(planId) {
	var html = "<a href=\"javascript:void(0);edit('" + planId + "')\" class=\"btn btn-primary btn-xs\">编辑</a>&nbsp;&nbsp;" 
	+ "<a href=\"javascript:void(0);deleteWorkPlan('" + planId + "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>&nbsp;&nbsp;";
	html += "<a href=\"javascript:void(0);details('" + planId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	return html;
}

function details(planId)
{
	window.open("/app/core/workplan/workplandetails?planId="+planId);
}
function goback()
{
	$("#plandiv").hide();
	$("#planlistdiv").show();
}
function edit(planId)
{
	$("#planlistdiv").hide();
	$("#plandiv").show();
	$(".js-back-btn").unbind("click").click(function(){
		goback();
	})
	$.ajax({
		url : "/ret/workplanget/getWorkPlanById",
		type : "post",
		dataType : "json",
		data:{planId:planId},
		success : function(data) {
			if(data.status=="200")
			{
				var workplan = data.list;
				for(var id in workplan)
				{
					if(id=="attach")
					{
						$("#show_workplanattach").html("");
						$("#workplanattach").attr("data_value", workplan.attach);
						createAttach("workplanattach", 4);
					}else if(id=="userPriv"||id=="joinUser"||id=="holdUser"||id=="supUser")
					{
						$("#"+id).val(getUserNameByStr(workplan[id]));
						$("#"+id).attr("data-value",workplan[id]);
					}else if(id=="deptPriv")
					{
						$("#"+id).val(getDeptNameByDeptIds(workplan[id]));
						$("#"+id).attr("data-value",workplan[id]);
					}else if(id=="levelPriv")
					{
						$("#"+id).val(getUserLevelStr(workplan[id]));
						$("#"+id).attr("data-value",workplan[id]);
					}else if(id=="remark")
					{
						$("#remark").code(workplan[id]);
					}else
					{
						$("#"+id).val(workplan[id]);
					}
				}
				$(".js-update-save").unbind("click").click(function(){
					updateWorkPlan(planId);
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
function updateWorkPlan(planId)
{
	$.ajax({
		url : "/set/workplanset/updateWorkPlan",
		type : "post",
		dataType : "json",
		data:{
			planId:planId,
			sortNo:$("#sortNo").val(),
			planType:$("#planType").val(),
			title:$("#title").val(),
			userPriv:$("#userPriv").attr("data-value"),
			deptPriv:$("#deptPriv").attr("data-value"),
			levelPriv:$("#levelPriv").attr("data-value"),
			joinUser:$("#joinUser").attr("data-value"),
			holdUser:$("#holdUser").attr("data-value"),
			supUser:$("#supUser").attr("data-value"),
			remark:$("#remark").code(),
			beginTime:$("#beginTime").val(),
			endTime:$("#endTime").val(),
			attach:$("#workplanattach").attr("data_value")
		},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				window.location.reload();
				top.layer.msg(data.msg);
				}
		}
	})
}


function deleteWorkPlan(planId)
{
	 if(confirm("确定删除当前计划信息吗？"))
	    {
	$.ajax({
		url : "/set/workplanset/deleteWorkPlan",
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