$(function() {
	query();
	$(".js-btn").unbind("click").click(function() {
		document.getElementById("form1").reset();
		$("#createModal").modal("show");
		$(".js-createSysDeskConfig").unbind("click").click(function(){
			$.ajax({
				url : "/set/sysset/createDeskConfig",
				type : "post",
				dataType : "json",
				data : {
					sortNo : $("#sortNo").val(),
					moduleName:$("#moduleName").val(),
					module:$("#module").val()
				},
				success : function(data) {
					if (data.status == "500") {
						console.log(data.msg);
					} else if (data.status == "100") {
						top.layer.msg(data.msg);
					} else {
						top.layer.msg(data.msg);
						$("#createModal").modal("hide");
						$('#myTable').bootstrapTable('refresh');
					}
				}
			})
		});
	});
})
function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/sysget/getAllSysDeskConfig',
		method : 'post',
		contentType : 'application/x-www-form-urlencoded',
		toolbar : '#toobar',// 工具列
		striped : true,// 隔行换色
		cache : false,// 禁用缓存
		pagination : true,// 启动分页
		sidePagination : 'server',// 分页方式
		pageNumber : 1,// 初始化table时显示的页码
		pageSize : 10,// 每页条目
		showFooter : false,// 是否显示列脚
		showPaginationSwitch : true,// 是否显示 数据条数选择框
		sortable : true,// 排序
		search : true,// 启用搜索
		showColumns : true,// 是否显示 内容列下拉框
		showRefresh : true,// 显示刷新按钮
		idField : 'diskConfigId',// key值栏位
		clickToSelect : true,// 点击选中checkbox
		pageList : [ 10, 20, 30, 50 ],// 可选择单页记录数
		queryParams : queryParams,
		columns : [ {
			checkbox : true
		}, {
			field : 'num',
			title : '序号',// 标题 可不加
			width : '50px',
			formatter : function(value, row, index) {
				return index + 1;
			}
		}, {
			field : 'moduleName',
			title : '模块名称',
			sortable : true,
			width : '200px'
		}, {
			field : 'module',
			width : '50px',
			title : '唯一识别'
		}, {
			field : '',
			title : '可选范围设置',
			width : '100px',
			align:'center',
			formatter : function(value, row, index) {
				return createUsePrivSetBtn(row.deskConfigId);
			}
		}, {
			field : '',
			title : '必选范围设置',
			width : '100px',
			align:'center',
			formatter : function(value, row, index) {
				return createMustUsePrivSetBtn(row.deskConfigId);
			}
		},

		{
			field : '',
			title : '禁用范围设置',
			width : '100px',
			align:'center',
			formatter : function(value, row, index) {
				return createUnUsePrivSetBtn(row.deskConfigId);
			}
		}, {
			field : 'opt',
			title : '操作',
			width : '100px',
			align:'center',
			formatter : function(value, row, index) {
				return createOptBtn(row.deskConfigId);
			}
		} ],
		onClickCell : function(field, value, row, $element) {
			// alert(row.SystemDesc);
		},
		responseHandler : function(res) {
			if (res.status == "500") {
				console.log(res.msg);
			} else if (res.status == "100") {
				top.layer.msg(res.msg);
			} else {
				return {
					total : res.list.total, // 总页数,前面的key必须为"total"
					rows : res.list.list
				// 行数据，前面的key要与之前设置的dataField的值一致.
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
		sortOrder : params.order
	};
	return temp;
};
function createUsePrivSetBtn(deskConfigId) {
	var html = "<a href=\"javascript:void(0);setUsePriv('" + deskConfigId + "')\" class=\"btn btn-sky btn-xs\" >可选设置</a>";
	return html;
}

function createMustUsePrivSetBtn(deskConfigId) {
	var html = "<a href=\"javascript:void(0);setUseMustPriv('" + deskConfigId + "')\" class=\"btn btn-success btn-xs\" >必选设置</a>";
	return html;
}
function createUnUsePrivSetBtn(deskConfigId) {
	var html = "<a href=\"javascript:void(0);setUnUsePriv('" + deskConfigId + "')\" class=\"btn btn-azure btn-xs\">禁用设置</a>";
	return html;
}

function createOptBtn(deskConfigId) {
	var html = "<a href=\"javascript:void(0);edit('"+ deskConfigId + "')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;" + 
	"<a href=\"javascript:void(0);del('" + deskConfigId+ "')\" class=\"btn btn-primary btn-xs\">删除</a>";
	return html;
}

function setUsePriv(deskConfigId)
{
	$("#setprivmodal").modal("show");
	document.getElementById("form2").reset()
	$("#useUserPriv").attr("data-value","");
	$("#useDeptPriv").attr("data-value","");
	$("#useLeavePriv").attr("data-value","");
	$(".js-use-priv").unbind("click").click(function(){
		$.ajax({
			url : "/set/sysset/updateDeskConfig",
			type : "post",
			dataType : "json",
			data:{
				deskConfigId:deskConfigId,
				useUserPriv:$("#useUserPriv").attr("data-value"),
				useDeptPriv:$("#useDeptPriv").attr("data-value"),
				useLeavePriv:$("#useLeavePriv").attr("data-value")
				},
			success : function(data) {
				if(data.status=="200")
				{
					top.layer.msg(data.msg);
					$("#setprivmodal").modal("hide");
				}else if(data.status=="100")
				{
					top.layer.msg(data.msg);
				}else
				{
					console.log(data.msg);
				}
			}
		});
	});
}

function setUseMustPriv(deskConfigId)
{
	$("#setmustprivmodal").modal("show");
	document.getElementById("form4").reset()
	$("#mustUseUserPriv").attr("data-value","");
	$("#mustUseDeptPriv").attr("data-value","");
	$("#mustUseLeavePriv").attr("data-value","");
	$(".js-must-priv").unbind("click").click(function(){
		$.ajax({
			url : "/set/sysset/updateDeskConfig",
			type : "post",
			dataType : "json",
			data:{
				deskConfigId:deskConfigId,
				mustUseUserPriv:$("#mustUseUserPriv").attr("data-value"),
				mustUseDeptPriv:$("#mustUseDeptPriv").attr("data-value"),
				mustUseLeavePriv:$("#mustUseLeavePriv").attr("data-value")
				},
			success : function(data) {
				if(data.status=="200")
				{
					top.layer.msg(data.msg);
					$("#setmustprivmodal").modal("hide");
				}else if(data.status=="100")
				{
					top.layer.msg(data.msg);
				}else
				{
					console.log(data.msg);
				}
			}
		});
	});
	
}

function setUnUsePriv(deskConfigId)
{
	$("#setunprivmodal").modal("show");
	document.getElementById("form3").reset()
	$("#unUseUserPriv").attr("data-value","");
	$("#unUseDeptPriv").attr("data-value","");
	$("#unUseLeavePriv").attr("data-value","");
	$(".js-un-priv").unbind("click").click(function(){
		$.ajax({
			url : "/set/sysset/updateDeskConfig",
			type : "post",
			dataType : "json",
			data:{
				deskConfigId:deskConfigId,
				unUseUserPriv:$("#unUseUserPriv").attr("data-value"),
				unUseDeptPriv:$("#unUseDeptPriv").attr("data-value"),
				unUseLeavePriv:$("#unUseLeavePriv").attr("data-value")
				},
			success : function(data) {
				if(data.status=="200")
				{
					top.layer.msg(data.msg);
					$("#setunprivmodal").modal("hide");
				}else if(data.status=="100")
				{
					top.layer.msg(data.msg);
				}else
				{
					console.log(data.msg);
				}
			}
		});
	});
}



function edit(deskConfigId) {
	document.getElementById("form1").reset();
	$.ajax({
		url : "/ret/sysget/getSysDeskConfig",
		type : "post",
		dataType : "json",
		data : {
			deskConfigId : deskConfigId
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				$("#sortNo").val(data.list.sortNo);
				$("#moduleName").val(data.list.moduleName);
				$("#module").val(data.list.module);
				$("#createModal").modal("show");
				$(".js-createSysDeskConfig").unbind("click").click(function(){
					$.ajax({
						url : "/set/sysset/updateDeskConfig",
						type : "post",
						dataType : "json",
						data : {
							deskConfigId:deskConfigId,
							sortNo : $("#sortNo").val(),
							moduleName:$("#moduleName").val(),
							module:$("#module").val()
						},
						success : function(data) {
							if (data.status == "500") {
								console.log(data.msg);
							} else if (data.status == "100") {
								top.layer.msg(data.msg);
							} else {
								top.layer.msg(data.msg);
								$('#myTable').bootstrapTable('refresh');
							}
						}
					})
				});
			}
		}
	})
}


function detail(deskConfigId) {
	//
}

function del(deskConfigId) {
	$.ajax({
		url : "/set/sysset/deskConfig",
		type : "post",
		dataType : "json",
		data : {
			deskConfigId : deskConfigId
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				top.layer.msg(data.msg);
				$('#myTable').bootstrapTable('refresh');
			}
		}
	})

}
