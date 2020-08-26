$(function() {
	$(".js-templatebtn").unbind("click").click(function() {
		$("#show_templateattach").empty();
		$("#templateattach").attr("data_value","");
		document.getElementById("form3").reset();
		$("#templatemodal").modal("show");
		$(".js-savetemplate").unbind("click").click(function() {
			addTemplate();
		});
	});
	getAllDocumentFlowList();
	query3();
});
function query3() {
	$("#myTable3").bootstrapTable({
		url : '/ret/documentget/getDocumentTemplateList',
		method : 'post',
		contentType : 'application/x-www-form-urlencoded',
		toolbar : '#toobar3',//工具列
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
		idField : 'templateId',//key值栏位
		clickToSelect : true,//点击选中checkbox
		pageList : [ 10, 20, 30, 50 ],//可选择单页记录数
		queryParams : queryParams3,
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
			field : 'templateName',
			title : '模版名称',
			align : 'center',
			sortable : true,
			width : '200px'
		}, {
			field : 'flowId',
			width : '50px',
			title : 'BPM流程名称',
			align : 'center',
			formatter : function(value, row, index) {
				return getDocumentFlow(value);
			}
		}, {
			field : 'createUserName',
			width : '100px',
			align : 'center',
			title : '创建人'
		}, {
			field : 'createTime',
			width : '100px',
			align : 'center',
			title : '创建时间'
		}, {
			field : 'attach',
			width : '50px',
			align : 'center',
			title : '红头模版',
			formatter : function(value, row, index) {
				return createTableAttach(value);
			}
		}, {
			field : 'opt',
			title : '操作',
			width : '100px',
			align:'center',
			formatter : function(value, row, index) {
				return createOptBtn3(row.templateId);
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
function queryParams3(params) {
	var temp = {
		search : params.search,
		pageSize : this.pageSize,
		pageNumber : this.pageNumber,
		sort : params.sort,
		sortOrder : params.order
	};
	return temp;
};
function createOptBtn3(templateId) {
	var html = "<a href=\"javascript:void(0);edit('" + templateId + "')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;" + "<a href=\"javascript:void(0);del('" + templateId
			+ "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
}
function del(templateId)
{
	if(window.confirm('你确定要删除模版吗？')){
		$.ajax({
			url : "/set/documentset/deleteDocumentTemplate",
			type : "post",
			dataType : "json",
			data : {
				templateId : templateId
			},
			success : function(data) {
				if (data.status == "200") {
					top.layer.msg(data.msg);
					$('#myTable3').bootstrapTable('refresh');
				} else if (data.status == "100") {
					top.layer.msg(data.msg);
				} else {
					console.log(data.msg);
				}
			}
		})
     }else{
        return;
    }
}

function addTemplate() {
	$.ajax({
		url : "/set/documentset/insertDocumentTemplate",
		type : "post",
		dataType : "json",
		data : {
			sortNo : $("#sortNo").val(),
			flowId : $("#flowId").val(),
			templateName : $("#templateName").val(),
			attach : $("#templateattach").attr("data_value")
		},
		success : function(data) {
			if (data.status == "200") {
				top.layer.msg(data.msg);
				$('#myTable3').bootstrapTable('refresh');
				$("#templatemodal").modal("hide");
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	})
}
function updateTemplate(templateId) {
	$.ajax({
		url : "/set/documentset/updateDocumentTemplate",
		type : "post",
		dataType : "json",
		data : {
			templateId:templateId,
			sortNo : $("#sortNo").val(),
			flowId : $("#flowId").val(),
			templateName : $("#templateName").val(),
			attach : $("#templateattach").attr("data_value")
		},
		success : function(data) {
			if (data.status == "200") {
				top.layer.msg(data.msg);
				$('#myTable3').bootstrapTable('refresh');
				$("#templatemodal").modal("hide");
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	})
}

function edit(templateId)
{
	document.getElementById("form3").reset();
	$("#show_templateattach").empty();
	$("#templateattach").attr("data_value","");
	$.ajax({
		url : "/ret/documentget/getDocumentTemplateById",
		type : "post",
		dataType : "json",
		data : {
			templateId : templateId
		},
		success : function(data) {
			if (data.status == "200") {
				$("#flowId").val(data.list.flowId);
				$("#sortNo").val(data.list.sortNo);
				$("#templateName").val(data.list.templateName);
				$("#templateattach").attr("data_value",data.list.attach);
				createAttach("templateattach","1");
				$("#templatemodal").modal("show");
				$(".js-savetemplate").unbind("click").click(function() {
					updateTemplate(data.list.templateId);
				});
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	})
}

function getAllDocumentFlowList()
{
	$.ajax({
		url : "/ret/documentget/getAllDocumentFlowList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
				{
					var html = "<option value=''>全部</option>"
					for(var i=0;i<data.list.length;i++)
						{
						html+="<option value='"+data.list[i].flowId+"'>"+data.list[i].flowName+"</option>"
						}
				$("#flowId").html(html);
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

function getDocumentFlow(flowId)
{
	var returnStr;
	$.ajax({
		url : "/ret/documentget/getDocumentFlow",
		type : "post",
		dataType : "json",
		data:{flowId:flowId},
		async : false,
		success : function(data) {
			if(data.status=="200")
				{
				returnStr = data.list.flowName;
				}else if(data.status=="100")
				{
					top.layer.msg(data.msg);
				}else
				{
					console.log(data.msg);
				}
		}
	});	
	return returnStr;
}
