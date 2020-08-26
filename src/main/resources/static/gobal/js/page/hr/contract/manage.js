$(function() {
	QuerySelect("enterpries");
	QuerySelect("contractType");
	QuerySelect("contractType");
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
	jeDate("#signTime", {
		format: "YYYY-MM-DD",
		maxDate:getSysDate(),
	});
	jeDate("#startTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate(),
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
		url : '/ret/hrget/getHrContractList',
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
		idField : 'contractId',//key值栏位
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
			field : 'enterpries',
			width : '100px',
			title : '签约公司',
			formatter : function(value, row, index) {
				return getHrClassCodeName('enterpries', value);
			}
		}, {
			field : 'userId',
			title : '雇佣者',
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
			field : 'contractType',
			width : '100px',
			title : '合同类型',
			formatter : function(value, row, index) {
				return getHrClassCodeName('contractType', value);
			}
		}, {
			field : 'signTime',
			width : '50px',
			title : '签订日期'
		}, {
			field : 'startTime',
			width : '100px',
			title : '生效日期'
		}, {
			field : 'endTime',
			title : '终止日期',
			width : '100px'
		}, {
			field : 'poolPosition',
			title : '应聘岗位',
			width : '100px',
			formatter : function(value, row, index) {
				return getHrClassCodeName('poolPosition', value);
			}
		}, {
			field : 'signType',
			title : '签约方式',
			width : '50px',
			formatter : function(value, row, index) {
				if(value=="1")
				{
					return "新签";
				}else if(value=="2")
				{
					return "补签";
				}else if(value=="3")
				{
					return "改签";
				}else if(value=="5")
				{
					return "续签";
				}else
				{
					return "未知";
				}
			}
		}
		, {
			field : 'opt',
			title : '操作',
			align : 'center',
			width : '120px',
			formatter : function(value, row, index) {
				return createOptBtn(row.contractId);
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
		enterpries : $("#enterpriesQuery").val(),
		contractType : $("#contractTypeQuery").val(),
		endTime : $("#endTimeQuery").val(),
		beginTime : $("#beginTimeQuery").val()
	};
	return temp;
};
function createOptBtn(contractId) {
	var html = "<a href=\"javascript:void(0);edit('" + contractId + "')\" class=\"btn btn-primary btn-xs\">编辑</a>&nbsp;&nbsp;" 
	+ "<a href=\"javascript:void(0);deleteContract('" + contractId + "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>&nbsp;&nbsp;";
	html += "<a href=\"javascript:void(0);details('" + contractId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	return html;
}

function deleteContract(contractId)
{
	if(confirm("确定删除当前合同记录吗？"))
    {
	$.ajax({
		url : "/set/hrset/deleteHrContract",
		type : "post",
		dataType : "json",
		data:{contractId:contractId},
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
function edit(contractId)
{
	$("#baseinfolistdiv").hide();
	$("#baseinfodiv").show();
	$(".js-back-btn").unbind("click").click(function(){
		goback();
	})
	$.ajax({
		url : "/ret/hrget/getHrContractById",
		type : "post",
		dataType : "json",
		data:{contractId:contractId},
		success : function(data) {
			if(data.status=="200")
			{
				var contractInfo = data.list;
				for(var id in contractInfo)
				{
					if(id=="attach")
					{
						$("#show_hrattach").html("");
						$("#hrattach").attr("data_value", contractInfo.attach);
						createAttach("hrattach", 4);
					}else if(id=="userId")
					{
						$("#"+id).val(getHrUserNameByStr(contractInfo[id]));
						$("#"+id).attr("data-value",contractInfo[id]);
					}else if(id=="remark")
					{
						$("#remark").code(contractInfo[id]);
					}else if(id=="reminder")
					{
						$("input:radio[name='reminder'][value='"+contractInfo[id]+"']").attr("checked","checked");
					}else if(id=="sendToUser")
					{
						$("#"+id).attr("data-value",contractInfo[id]);
						$("#"+id).val(getUserNameByStr(contractInfo[id]));
					}else
					{
						$("#"+id).val(contractInfo[id]);
					}
				}
				$(".js-update-save").unbind("click").click(function(){
					updateHrContract(contractId);
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
function details(contractId)
{
	window.open("/app/core/hr/contractdetails?contractId="+contractId);
}
function updateHrContract(contractId)
{
	$.ajax({
		url : "/set/hrset/updateHrContract",
		type : "post",
		dataType : "json",
		data:{
			contractId:contractId,
			sortNo:$("#sortNo").val(),
			userName:$("#userName").val(),
			signType:$("#signType").val(),
			poolPosition:$("#poolPosition").val(),
			contractCode:$("#contractCode").val(),
			contractType:$("#contractType").val(),
			enterpries:$("#enterpries").val(),
			userId:$("#userId").attr("data-value"),
			startTime:$("#startTime").val(),
			endTime:$("#endTime").val(),
			specialization:$("#specialization").val(),
			signTime:$("#signTime").val(),
			reminder:$("input:radio[name='reminder']:checked").val(),
			sendToUser:$("#sendToUser").attr("data-value"),
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
	$("#baseinfodiv").hide();
	$("#baseinfolistdiv").show();
}