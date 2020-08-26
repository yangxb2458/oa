$(function() {
	QuerySelect("incentiveItem");
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
	jeDate("#salaryMonth", {
		format: "YYYY-MM",
		minDate:getSysDate(),
	});
	jeDate("#incentiveTime", {
		format: "YYYY-MM-DD"
	});
	getSmsConfig("msgType","hr");
	$('#remark').summernote({ height:300 });
	$(".js-auto-select").each(function(){
		var module = $(this).attr("module");
		createAutoSelect(module);
	})

})

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/hrget/getHrIncentiveList',
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
		sortOrder: "asc",
		showColumns : true,//是否显示 内容列下拉框
		showRefresh : true,//显示刷新按钮
		idField : 'incentiveId',//key值栏位
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
			field : 'userId',
			title : '奖惩人员',
			sortable : true,
			width : '80px',
			formatter : function(value, row, index) {
				if (row.userName == "" || row.userName == null) {
					return getHrUserNameByStr(row.userId);
				} else {
					return row.userName;
				}
			}
		}, {
			field : 'incentiveItem',
			width : '100px',
			title : '奖惩事件',
			formatter : function(value, row, index) {
				return getHrClassCodeName('incentiveItem', value);
			}
		},{
			field : 'incentiveType',
			width : '100px',
			title : '奖惩类型',
			formatter : function(value, row, index) {
				if(value=="0")
				{
					return "奖励";
				}else if(value=="1")
				{
					return "惩罚";
				}
			}
		}, {
			field : 'incentiveTime',
			width : '50px',
			title : '处理日期'
		}, {
			field : 'incentiveAmount',
			width : '100px',
			title : '金额'
		}, {
			field : 'salaryMonth',
			title : '工资月份',
			width : '100px'
		}, {
			field : 'opt',
			title : '操作',
			align : 'center',
			width : '120px',
			formatter : function(value, row, index) {
				return createOptBtn(row.incentiveId);
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
		userId : $("#userIdQuery").attr("data-value"),
		incentiveItem : $("#incentiveItemQuery").val(),
		incentiveType : $("#incentiveTypeQuery").val(),
		endTime : $("#endTimeQuery").val(),
		beginTime : $("#beginTimeQuery").val()
	};
	return temp;
};
function createOptBtn(incentiveId) {
	var html = "<a href=\"javascript:void(0);edit('" + incentiveId + "')\" class=\"btn btn-primary btn-xs\">编辑</a>&nbsp;&nbsp;" 
	+ "<a href=\"javascript:void(0);deleteIncentive('" + incentiveId + "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>&nbsp;&nbsp;";
	html += "<a href=\"javascript:void(0);details('" + incentiveId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	return html;
}

function deleteIncentive(incentiveId)
{
	if(confirm("确定删除当前记录吗？"))
    {
	$.ajax({
		url : "/set/hrset/deleteHrIncentive",
		type : "post",
		dataType : "json",
		data:{incentiveId:incentiveId},
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
function edit(incentiveId)
{
	$("#incentivelistdiv").hide();
	$("#incentivediv").show();
	$(".js-back-btn").unbind("click").click(function(){
		goback();
	})
	$.ajax({
		url : "/ret/hrget/getHrIncentiveById",
		type : "post",
		dataType : "json",
		data:{incentiveId:incentiveId},
		success : function(data) {
			if(data.status=="200")
			{
				var incentiveInfo = data.list;
				for(var id in incentiveInfo)
				{
					if(id=="attach")
					{
						$("#show_hrattach").html("");
						$("#hrattach").attr("data_value", incentiveInfo.attach);
						createAttach("hrattach", 4);
					}else if(id=="userId")
					{
						$("#"+id).val(getHrUserNameByStr(incentiveInfo[id]));
						$("#"+id).attr("data-value",incentiveInfo[id]);
					}else if(id=="remark")
					{
						$("#remark").code(incentiveInfo[id]);
					}else if(id=="reminder")
					{
						$("input:radio[name='reminder'][value='"+incentiveInfo[id]+"']").attr("checked","checked");
					}else
					{
						$("#"+id).val(incentiveInfo[id]);
					}
				}
				$(".js-update-save").unbind("click").click(function(){
					updateHrIncentive(incentiveId);
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
function details(incentiveId)
{
	window.open("/app/core/hr/incentivedetails?incentiveId="+incentiveId);
}
function updateHrIncentive(incentiveId)
{
	$.ajax({
		url : "/set/hrset/updateHrIncentive",
		type : "post",
		dataType : "json",
		data:{
			incentiveId:incentiveId,
			sortNo:$("#sortNo").val(),
			incentiveType:$("#incentiveType").val(),
			userId:$("#userId").attr("data-value"),
			incentiveItem:$("#incentiveItem").val(),
			incentiveTime:$("#incentiveTime").val(),
			incentiveAmount:$("#incentiveAmount").val(),
			salaryMonth:$("#salaryMonth").val(),
			msgType:getCheckBoxValue("msgType"),
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
	$("#incentivediv").hide();
	$("#incentivelistdiv").show();
}