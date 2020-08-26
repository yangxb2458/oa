$(function() {
	query();
	jeDate("#beginTime", {
		format : "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format : "YYYY-MM-DD"
	});
	$(".js-simple-query").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	})
})
function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/crmget/getCrmInquiryList',
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
		idField : 'inquiryId',//key值栏位
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
			field : 'inquiryCode',
			title : '询价单号',
			width : '150px'
		}, {
			field : 'title',
			title : '询价标题',
			width : '150px'
		}, {
			field : 'customerId',
			width : '150px',
			title : '客户名称'
		}, {
			field : 'customerType',
			width : '50px',
			title : '客户类型',
			formatter : function(value, row, index) {
				if (value == "1") {
					return "终端用户";
				} else if (value = "2") {
					return "合作伙伴";
				}
			}
		}, {
			field : 'endTime',
			title : '报价截止',
			sortable : true,
			width : '50px'
		}, {
			field : 'attach',
			width : '50px',
			visible:false,
			title : '附件',
			formatter : function(value, row, index) {
				if (value != '') {
					return '<i class="fa fa-paperclip"></i>';
				} else {
					return '';
				}
			}
		}, {
			field : 'createUserName',
			width : '100px',
			visible:false,
			title : '创建人'
		}, {
			field : 'createTime',
			width : '100px',
			title : '创建时间'
		},{
			field : 'status',
			width : '100px',
			title : '状态',
			formatter : function(value, row, index) {
				if(value=="0")
					{
						return "进行中"
					}else if(value=="1")
					{
						return "终止"
					}else if(value=="2")
					{
						return "完成";
					}
			}
		}, {
			field : 'opt',
			title : '操作',
			width : '200px',
			align:'center',
			formatter : function(value, row, index) {
				return createOptBtn(row.inquiryId,row.status);
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
		beginTime : $("#beginTime").val(),
		endTime : $("#endTime").val(),
		customerType : $("#customerType").val(),
		status:$("#status").val()
	};
	return temp;
};
function createOptBtn(inquiryId,status) {
	var html="";	
	if(status=="0")
	{
		html+="<a href=\"javascript:void(0);editinquiry('"+ inquiryId+ "')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;"+
		"<a href=\"javascript:void(0);stopUpdate('" + inquiryId+ "')\" class=\"btn btn-primary btn-xs\">终止</a>&nbsp;&nbsp;" +
		"<a href=\"javascript:void(0);createOffer('" + inquiryId + "')\" class=\"btn btn-info btn-xs\" >报价</a>&nbsp;&nbsp;";
	}else if(status=="1")
	{
		html+="<a href=\"javascript:void(0);del('" + inquiryId+ "')\" class=\"btn btn-danger btn-xs\">删除</a>&nbsp;&nbsp;";
		html+="<a href=\"javascript:void(0);recovery('" + inquiryId+ "')\" class=\"btn btn-success btn-xs\">恢复</a>&nbsp;&nbsp;";
	}
	html += "<a href=\"javascript:void(0);details('" + inquiryId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>"
	return html;
}

function details(inquiryId) {
	window.open("/app/core/crm/inquirydetails?inquiryId="+inquiryId);
}

function recovery(inquiryId)
{
		$.ajax({
			url : "/set/crmset/updateCrmInquiry",
			type : "post",
			dataType : "json",
			data : {
				inquiryId:inquiryId,
				status:"0"
			},
			success : function(data) {
				if (data.status == "500") {
					console.log(data.msg);
				} else if (data.status == "100") {
					top.layer.msg(data.msg);
				} else {
					top.layer.msg(data.msg);
					$("#myTable").bootstrapTable("refresh");
				}
			}
		})
}

function stopUpdate(inquiryId)
{
		$.ajax({
			url : "/set/crmset/updateCrmInquiry",
			type : "post",
			dataType : "json",
			data : {
				inquiryId:inquiryId,
				status:"1"
			},
			success : function(data) {
				if (data.status == "500") {
					console.log(data.msg);
				} else if (data.status == "100") {
					top.layer.msg(data.msg);
				} else {
					top.layer.msg(data.msg);
					$("#myTable").bootstrapTable("refresh");
				}
			}
		})
}

function createOffer(inquiryId)
{

}

function del(inquiryId)
{
	 if(confirm("确定删除当前询价单记录吗？"))
	    {
	$.ajax({
		url : "/set/crmset/deleteCrmInquiry",
		type : "post",
		dataType : "json",
		data:{inquiryId:inquiryId},
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

function editinquiry(inquiryId)
{
	location.href = "/app/core/crm/inquiry?inquiryId="+inquiryId; 
}