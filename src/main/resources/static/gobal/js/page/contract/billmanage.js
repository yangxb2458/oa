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
	$('#remark').summernote({
		height : 200
	});
	$(".js-back-btn").unbind("click").click(function(){
		$("#recorddiv").hide();
		$("#recordlistdiv").show();
	})
	jeDate("#billTime", {
		format : "YYYY-MM-DD"
	});
});
function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/contractget/getContractBillList',
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
		idField : 'billId',//key值栏位
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
			field : 'billCode',
			title : '票据编号',
			sortable : true,
			width : '100px'
		}, {
			field : 'title',
			title : '合同名称',
			sortable : true,
			width : '100px'
		}, {
			field : 'customerName',
			title : '对方名称',
			width : '100px'
		}, {
			field : 'billType',
			width : '50px',
			title : '发票类型',
			formatter : function(value, row, index) {
				if (value=="1") {
					return "普票";
				} else if (value=="2") {
						return "增票";
				} else if (value=="3") {
						return "服务票";
				}else if (value=="4") {
						return "电子票";
				}
			}
		}, {
			field : 'total',
			title : '开票金额',
			width : '50px'
		}, {
			field : 'billTime',
			width : '50px',
			title : '开票日期'
		}, {
			field : 'isOpen',
			width : '100px',
			title : '票据事件',
				formatter : function(value, row, index) {
					 if(value=="1")
					{
						 return "开票";
					}else if(value=="2")
					{
						return "收票";
					}
				}
		}, {
			field : 'opt',
			width : '100px',
			align : 'center',
			title : '操作',
			formatter : function(value, row, index) {
				return createOptBtn(row.billId);
			}
		} ],
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
		isOpen : $("#isOpen").val(),
		billType:$("#billType").val(),
		beginTime : $("#beginTime").val(),
		endTime : $("#endTime").val(),
		status:$("#status").val()

	};
	return temp;
};
function createOptBtn(billId) {
	var html = "<a href=\"javascript:void(0);billDetails('" + billId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;";
	html += "<a href=\"javascript:void(0);editBill('" + billId + "')\" class=\"btn btn-primary btn-xs\">编辑</a>&nbsp;&nbsp;";
	html += "<a href=\"javascript:void(0);del('" + billId + "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
}

function editBill(billId)
{
	$("#show_contractattach").html("");
	$("#contractattach").attr("data-value");
	$("#recordlistdiv").hide();
	$("#recorddiv").show();
	$.ajax({
		url : "/ret/contractget/getContractBillById",
		type : "post",
		dataType : "json",
		data:{billId:billId},
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
					}else  if(id=="remark")
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
									$("#contractId").html(res.list.title);
								}else if(res.status=="100")
								{
									top.layer.msg(res.msg);
								}else if(res.status=="500")
								{
									console.log(res.msg);
								}
							}
						});
					}else if(id=="isOpen")
					{
						$("input:radio[name='isOpen'][value='"+recordinfo[id]+"']").attr("checked","checked");
					}else
					{
						$("#"+id).val(recordinfo[id]);
					}
				}
				$(".js-update-save").unbind("click").click(function(){
					updateBill(billId);
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
function updateBill(billId)
{
	$.ajax({
		type : "post",
		dataType : "json",
		url : "/set/contractset/updateContractBill",
		data : {
			billId:billId,
			contractId:$("#contractId").val(),
			billCode:$("#billCode").val(),
			customerName:$("#customerName").val(),
			remark:$("#remark").code(),
			billType:$("#billType").val(),
			isOpen:$('input:radio[name="isOpen"]:checked').val(),
			billTime:$("#billTime").val(),
			total:$("#total").val(),
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

function billDetails(billId)
{
	window.open("/app/core/contract/billdetails?billId="+billId);
}

function del(billId)
{
	 if(confirm("确定删除当前票据记录吗？"))
	    {
	$.ajax({
		url : "/set/contractset/deleteContractBill",
		type : "post",
		dataType : "json",
		data:{billId:billId},
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