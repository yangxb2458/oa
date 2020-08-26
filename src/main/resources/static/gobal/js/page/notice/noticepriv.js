$(function() {
	initConfig();
	getNoticType();
	$(".js-templatebtn").unbind("click").click(function() {
		$("#show_templateattach").empty();
		$("#templateattach").attr("data_value","");
		document.getElementById("form3").reset();
		$("#templatemodal").modal("show");
		$(".js-savetemplate").unbind("click").click(function() {
			addTemplate();
		});
	});
	query1();
	query2();
	query3();
});

function initConfig()
{
	$.ajax({
		url : "/set/noticeset/initNocticeConfig",
		type : "post",
		dataType : "json",
		async : false,
		success : function(data) {
			if (data.status == "200") {
				top.layer.msg(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	})
}

function query1() {
	$("#myTable1").bootstrapTable({
		url : '/ret/noticeget/getApproverUserList',
		method : 'post',
		contentType : 'application/x-www-form-urlencoded',
		//toolbar : '#toobar1',//工具列
		striped : true,//隔行换色
		cache : false,//禁用缓存
		pagination : true,//启动分页
		sidePagination : 'server',//分页方式
		pageNumber : 1,//初始化table时显示的页码
		pageSize : 10,//每页条目
		showFooter : false,//是否显示列脚
		showPaginationSwitch : false,//是否显示 数据条数选择框
		sortable : true,//排序
		search : false,//启用搜索
		showColumns : false,//是否显示 内容列下拉框
		showRefresh : false,//显示刷新按钮
		idField : 'configId',//key值栏位
		clickToSelect : false,//点击选中checkbox
		pageList : [ 10, 20, 30, 50 ],//可选择单页记录数
		queryParams : queryParams1,
		columns : [{
			field : 'num',
			title : '序号',//标题  可不加
			width : '50px',
			formatter : function(value, row, index) {
				return index + 1;
			}
		},{
			field : 'noticeType',
			width : '50px',
			title : '公告分类',
			align : 'center',
			formatter : function(value, row, index) {
				return getCodeClassName(value, "notice");
			}
		}, {
			field : 'approver',
			width : '500px',
			align : 'center',
			title : '审批人',
			formatter : function(value, row, index) {
				return getUserNameByStr(value);
			}
		}, {
			field : 'createTime',
			width : '100px',
			align : 'center',
			title : '创建时间'
		}, {
			field : 'opt',
			title : '操作',
			align:'center',
			width : '100px',
			formatter : function(value, row, index) {
				return createOptBtn1(row.configId);
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
function queryParams1(params) {
	var temp = {
		search : params.search,
		pageSize : this.pageSize,
		pageNumber : this.pageNumber,
		sort : params.sort,
		sortOrder : params.order
	};
	return temp;
};
function createOptBtn1(configId) {
	var html = "<a href=\"javascript:void(0);setapprover('" + configId + "')\" class=\"btn btn-success btn-xs\" >设置人员</a>&nbsp;&nbsp;" + "<a href=\"javascript:void(0);";
	return html;
}

function setapprover(configId)
{
	$("#appoveraccount").attr("data-value","").val("");
	$("#appovermodal").modal("show");
	$(".js-saveappover").unbind("click").click(function(){
		$.ajax({
			url : "/set/noticeset/updateNoticeConfig",
			type : "post",
			dataType : "json",
			data : {
				configId : configId,
				appoverType:"0",
				approver :$("#appoveraccount").attr("data-value")
			},
			success : function(data) {
				if (data.status == "200") {
					top.layer.msg(data.msg);
					$('#myTable1').bootstrapTable('refresh');
					$("#appovermodal").modal("hide");
				} else if (data.status == "100") {
					top.layer.msg(data.msg);
				} else {
					console.log(data.msg);
				}
			}
		})
	});
}

function setnotapprover(configId)
{
	$("#appoveraccount").attr("data-value","").val("");
	$("#appovermodal").modal("show");
	$(".js-saveappover").unbind("click").click(function(){
		$.ajax({
			url : "/set/noticeset/updateNoticeConfig",
			type : "post",
			dataType : "json",
			data : {
				configId : configId,
				appoverType:"1",
				approver :$("#appoveraccount").attr("data-value")
			},
			success : function(data) {
				if (data.status == "200") {
					top.layer.msg(data.msg);
					$('#myTable2').bootstrapTable('refresh');
					$("#appovermodal").modal("hide");
				} else if (data.status == "100") {
					top.layer.msg(data.msg);
				} else {
					console.log(data.msg);
				}
			}
		})
	});
}


function query2() {
	$("#myTable2").bootstrapTable({
		url : '/ret/noticeget/getNotApproverUserList',
		method : 'post',
		contentType : 'application/x-www-form-urlencoded',
		//toolbar : '#toobar1',//工具列
		striped : true,//隔行换色
		cache : false,//禁用缓存
		pagination : true,//启动分页
		sidePagination : 'server',//分页方式
		pageNumber : 1,//初始化table时显示的页码
		pageSize : 10,//每页条目
		showFooter : false,//是否显示列脚
		showPaginationSwitch : false,//是否显示 数据条数选择框
		sortable : true,//排序
		search : false,//启用搜索
		showColumns : false,//是否显示 内容列下拉框
		showRefresh : false,//显示刷新按钮
		idField : 'configId',//key值栏位
		clickToSelect : false,//点击选中checkbox
		pageList : [ 10, 20, 30, 50 ],//可选择单页记录数
		queryParams : queryParams2,
		columns : [{
			field : 'num',
			title : '序号',//标题  可不加
			width : '50px',
			formatter : function(value, row, index) {
				return index + 1;
			}
		},{
			field : 'noticeType',
			width : '50px',
			title : '公告分类',
			align : 'center',
			formatter : function(value, row, index) {
				return getCodeClassName(value, "notice");
			}
		}, {
			field : 'approver',
			width : '500px',
			align : 'center',
			title : '免审批人员',
			formatter : function(value, row, index) {
				return getUserNameByStr(value);
			}
		}, {
			field : 'createTime',
			width : '100px',
			align : 'center',
			title : '创建时间'
		}, {
			field : 'opt',
			title : '操作',
			align:'center',
			width : '100px',
			formatter : function(value, row, index) {
				return createOptBtn2(row.configId);
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
function queryParams2(params) {
	var temp = {
		search : params.search,
		pageSize : this.pageSize,
		pageNumber : this.pageNumber,
		sort : params.sort,
		sortOrder : params.order
	};
	return temp;
};
function createOptBtn2(configId) {
	var html = "<a href=\"javascript:void(0);setnotapprover('" + configId + "')\" class=\"btn btn-success btn-xs\" >设置人员</a>&nbsp;&nbsp;" + "<a href=\"javascript:void(0);";
	return html;
}

function query3() {
	$("#myTable3").bootstrapTable({
		url : '/ret/noticeget/getNoticeTemplateList',
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
			field : 'noticeType',
			width : '50px',
			title : '公告分类',
			align : 'center',
			formatter : function(value, row, index) {
				return getCodeClassName(value, "notice");
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
			align:'center',
			width : '100px',
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
	if(window.confirm('你确定要删除吗？')){
		$.ajax({
			url : "/set/noticeset/delNoticeTemplate",
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
		url : "/set/noticeset/addNoticeTemplate",
		type : "post",
		dataType : "json",
		data : {
			sortNo : $("#sortNo").val(),
			noticeType : $("#noticeType").val(),
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
		url : "/set/noticeset/updateNoticeTemplate",
		type : "post",
		dataType : "json",
		data : {
			templateId:templateId,
			sortNo : $("#sortNo").val(),
			noticeType : $("#noticeType").val(),
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
		url : "/ret/noticeget/getNoticeTemplate",
		type : "post",
		dataType : "json",
		data : {
			templateId : templateId
		},
		success : function(data) {
			if (data.status == "200") {
				$("#noticeType").val(data.list.noticeType);
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

function getNoticType() {
	$.ajax({
		url : "/ret/sysget/getCodeClassByModule",
		type : "post",
		dataType : "json",
		data : {
			module : "notice"
		},
		success : function(data) {
			for (var i = 0; i < data.list.length; i++) {
				$("#noticeType").append("<option value=\"" + data.list[i].codeValue + "\">" + data.list[i].codeName + "</option>");
			}
		}
	})
}