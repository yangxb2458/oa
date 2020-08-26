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
	jeDate("#payableTime", {
		format : "YYYY-MM-DD"
	});
	$('#remark').summernote({
		height : 200
	});
	$(".js-back-btn").unbind("click").click(function(){
		$("#recorddiv").hide();
		$("#recordlistdiv").show();
	})
});
function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/contractget/getContractPayableList',
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
		idField : 'payableId',//key值栏位
		clickToSelect : true,//点击选中checkbox
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
			field : 'title',
			title : '合同名称',
			sortable : true,
			width : '150px'
		}, {
			field : 'customerName',
			title : '客户名称',
			width : '150px'
		}, {
			field : '',
			width : '50px',
			title : '付款状态',
			formatter : function(value, row, index) {
				if (row.payabled == 0||row.payabled==null) {
					return "未付";
				} else {
					if (row.unPayabled== 0) {
						return "已付清";
					} else if (row.unPayabled> 0) {
						return "未付清";
					}
				}
			}
		}, {
			field : 'unPayabled',
			title : '应付金额',
			width : '50px'
		}, {
			field : 'payableTime',
			width : '50px',
			title : '应付日期'
		}, {
			field : 'payabled',
			width : '100px',
			title : '已付金额'
		}, {
			field : 'userPrivUserName',
			width : '50px',
			title : '收付负责人'
		}, {
			field : 'opt',
			width : '120px',
			align : 'center',
			title : '操作',
			formatter : function(value, row, index) {
				var status="0";
				if(row.unPayabled==0)
				{
					status="1";
				}
				return createOptBtn(row.payableId,status);
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
		status : $("#status").val(),
		beginTime : $("#beginTime").val(),
		endTime : $("#endTime").val(),
		userPriv : $("#userPriv").attr("data-value")
	};
	return temp;
};
function createOptBtn(payableId, status) {
	var html="";
	if(status=="1")
	{
		html += "<a href=\"javascript:void(0);details('" + payableId + "')\" class=\"btn btn-primary btn-xs\">详情</a>&nbsp;&nbsp;";
	}else
	{
		html += "<a href=\"javascript:void(0);edit('" + payableId + "')\" class=\"btn btn-sky btn-xs\" >编辑</a>&nbsp;&nbsp;";
		html += "<a href=\"javascript:void(0);payable('" + payableId + "','0')\" class=\"btn btn-primary btn-xs\">付款</a>&nbsp;&nbsp;";
		html += "<a href=\"javascript:void(0);details('" + payableId + "')\" class=\"btn btn-primary btn-xs\">详情</a>&nbsp;&nbsp;";
		html += "<a href=\"javascript:void(0);del('" + payableId + "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	}
	return html;
}

function details(payableId)
{
window.open("/app/core/contract/payablesdetails?payableId="+payableId);
}


function payable(payableId)
{
	document.getElementById("form").reset()
	$("#payableee").attr("data-value","")
	$("#payablesmodal").modal("show");
	$(".js-save").unbind("click").click(function(){
		$.ajax({
		type : "post",
		dataType : "json",
		url : "/set/contractset/insertContractPayableRecord",
		data : {
			payableId:payableId,
			amount : $("#amount").val(),
			payableTime:$("#payableTime").val(),
			remark:$("#otherremark").val(),
			payableee:$("#payableee").attr("data-value")
		},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				$("#payablesmodal").modal("hide");
				$("#myTable").bootstrapTable("refresh");
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
	});
	})
}


function edit(payableId)
{
	document.getElementById("form").reset();
	$("#userPriv").attr("data-value","")
	$("#show_contractattach").html("");
	$("#contractattach").attr("data-value");
	$("#recordlistdiv").hide();
	$("#recorddiv").show();
	$.ajax({
		url : "/ret/contractget/getContractPayableById",
		type : "post",
		dataType : "json",
		data:{payableId:payableId},
		success : function(data) {
			if(data.status=="200")
			{
				var recordinfo = data.list;
				for(var id in recordinfo)
				{
					if(id=="attach")
					{
						$("#show_contractattach").html("");
						$("#contractattach").attr("data_value", recordinfo.attach);
						createAttach("hrattach", 4);
					}else if(id=="userPriv")
					{
						$("#"+id).val(getUserNameByStr(recordinfo[id]));
						$("#"+id).attr("data-value",recordinfo[id]);
					}else if(id=="remark")
					{
						$("#remark").code(recordinfo[id]);
					}else if(id=="contractId")
					{
						$.ajax({
							url : "/ret/contractget/getContractById",
							type : "post",
							dataType : "json",
							data:{contractId:recordinfo[id]},
							success : function(res) {
								if(res.status=="200")
								{
									$("#contractTitle").html(res.list.title);
								}else if(res.status=="100")
								{
									top.layer.msg(res.msg);
								}else if(res.status=="500")
								{
									console.log(res.msg);
								}
							}
						});
					}else
					{
						$("#"+id).val(recordinfo[id]);
					}
				}
				$(".js-update-save").unbind("click").click(function(){
					updatePayable(payableId);
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

function updatePayable(payableId)
{
	$.ajax({
		type : "post",
		dataType : "json",
		url : "/set/contractset/updateContractPayable",
		data : {
			payableId:payableId,
			contractId : $("#contractId").val(),
			payabledTime:$("#payabledTime").val(),
			unPayabled:$("#unPayabled").val(),
			remark:$("#remark").code(),
			userPriv:$("#userPriv").attr("data-value"),
			attach:$("#contractattach").attr("data_value")
		},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				window.location.reload();
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
	});
}