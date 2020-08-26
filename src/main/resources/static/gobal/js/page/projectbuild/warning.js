$(function() {
	$("#selectproject").select2({
		theme : "bootstrap",
		allowClear : true,
		placeholder : "请输入工程项目名称",
		query : function(query) {
			var url = "/ret/projectbuildget/selectProjectBuild2ByTitle";
			var param = {
				projectTitle : query.term
			}; // 查询参数，query.term为用户在select2中的输入内容.
			var type = "json";
			var data = {
				results : []
			};
			$.post(url, param, function(datas) {
				var datalist = datas.list;
				for (var i = 0, len = datalist.length; i < len; i++) {
					var land = datalist[i];
					var option = {
						"id" : land.projectId,
						"text" : land.projectTitle
					};
					data.results.push(option);
				}
				query.callback(data);
			}, type);
		},
		escapeMarkup : function(markup) {
			return markup;
		},
		minimumInputLength : 0,
		formatResult : function(data) {
			return '<div class="select2-user-result">' + data.text + '</div>'
		},
		formatSelection : function(data) {
			var projectId = data.id;
			$("#myTable").bootstrapTable('destroy');
			query(projectId);
			return data.text;
		},
		initSelection : function(data, cb) {
			cb(data);
		}
	});
});

function query(projectId) {
	$("#myTable").bootstrapTable({
		url : '/ret/projectbuildget/getprojectbuildStagelist?projectId=' + projectId,
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
		idField : 'stageId',// key值栏位
		clickToSelect : false,// 点击选中checkbox
		pageList : [ 10, 20, 30, 50 ],// 可选择单页记录数
		queryParams : queryParams,
		columns : [ {
			field : 'num',
			title : '序号',// 标题 可不加
			width : '50px',
			formatter : function(value, row, index) {
				return index + 1;
			}
		}, {
			field : 'projectTitle',
			title : '项目名称',
			sortable : true,
			width : '200px'
		}, {
			field : 'stageName',
			width : '50px',
			title : '节点名称'
		}, {
			field : 'beginTime',
			width : '100px',
			title : '开始时间'
		}, {
			field : 'endTime',
			width : '100px',
			title : '结束时间'

		}, {
			field : 'superintendent',
			width : '100px',
			title : '负责人',
			formatter : function(value, row, index) {
				return getUserNameByStr(value);
			}
		}, {
			field : 'status',
			title : '状态',
			width : '50px',
			formatter : function(value, row, index) {
				if(value=="0")
				{
				return "未开始";
				}else if(value=="1")
				{
					return "进行中";
				}else if(value=="2")
				{
					return "延期";
				}else if(value=="3")
				{
					return "完成"
				}
			}
		}, {
			field : 'opt',
			title : '操作',
			align:'center',
			width : '200px',
			formatter : function(value, row, index) {
				return createOptBtn(row.stageId,row.projectId,row.status);
			}
		} ],
		onClickCell : function(field, value, row, $element) {
		},
		onClickRow:function (row,$element) {
			$("#myTable1").bootstrapTable('destroy');	
			query1(row.stageId,row.status);
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
function createOptBtn(stageId,projectId,status) {
	var html = "<a href=\"javascript:void(0);readstage('" + stageId + "')\" class=\"btn btn-sky btn-xs\" >节点详情</a>&nbsp;&nbsp;";
	if(status!='3')
	{
		html+="<a href=\"javascript:void(0);setMaterial('" + stageId + "','"+projectId+"')\" class=\"btn btn-success btn-xs\" >设置材料</a>";
	}
	return html;
}
function readstage(stageId)
{
	window.open("/app/core/projectbuild/project/stagedetails?stageId="+stageId);
}

function setMaterial(stageId,projectId)
{
	document.getElementById("form1").reset();
	$("#flagb").hide();
	$("#flaga").show();
$.fn.modal.Constructor.prototype.enforceFocus = function () { };
$("#materialstagemodal").modal("show");
$("#materialId").select2({
	theme : "bootstrap",
	allowClear : true,
	placeholder : "请输入材料名称或编号",
	query : function(query) {
		var url = "/ret/projectbuildmaterialget/getselect2materiallist";
		var param = {
			search : query.term
		}; // 查询参数，query.term为用户在select2中的输入内容.
		var type = "json";
		var data = {
			results : []
		};
		$.post(url, param, function(datas) {
			var datalist = datas.list;
			for (var i = 0, len = datalist.length; i < len; i++) {
				var land = datalist[i];
				var option = {
					"id" : land.materialId,
					"text" : land.name,
					"materialCode":land.materialCode,
					"sortName":land.sortName,
					"brand":land.brand,
					"model":land.model,
					"price":land.price
				};
				data.results.push(option);
			}
			query.callback(data);
		}, type);
	},
	escapeMarkup : function(markup) {
		return markup;
	},
	minimumInputLength : 2,
	formatResult : function(data) {
		return '<div class="select2-user-result">'+data.materialCode+'|' + data.text +'|'+data.brand+'|'+data.model+ '|'+data.price+'(RMB元)</div>'
	},
	formatSelection : function(data) {
		return data.text;
	},
	initSelection : function(data, cb) {
		cb(data);
	}
});
$(".js-save").unbind("click").click(function(){
	addMaterialStage(stageId,projectId);
});
}

function addMaterialStage(stageId,projectId)
{
	$.ajax({
		url : "/set/projectbuildmaterialset/insertMaterialStage",
		type : "post",
		dataType : "json",
		data : {
			stageId:stageId,
			projectId:projectId,
			sortNo:$("#sortNo").val(),
			materialId:$("#materialId").val(),
			useQuantity:$("#useQuantity").val(),
			remark:$("#remark").val()
		},
		success : function(data) {
			if (data.status == 200) {
				$("#myTable1").bootstrapTable('destroy');
				 query1(stageId);
				 $("#materialstagemodal").modal("hide");
			} else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else{
				console.log(data.msg);
			}
		}
	});
}

function query1(stageId,status) {
	$("#myTable1").bootstrapTable({
		url : '/ret/projectbuildmaterialget/getmaterialStagelist?stageId=' + stageId,
		method : 'post',
		contentType : 'application/x-www-form-urlencoded',
		toolbar : '#toobar1',// 工具列
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
		idField : 'materialStageId',// key值栏位
		clickToSelect : true,// 点击选中checkbox
		pageList : [ 10, 20, 30, 50 ],// 可选择单页记录数
		queryParams : queryParams1,
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
			field : 'stageName',
			title : '节点名称',
			sortable : true,
			width : '100px'
		}, {
			field : 'materialCode',
			title : '材料编号',
			sortable : true,
			width : '100px'
		}, {
			field : 'name',
			width : '10px',
			title : '材料名称'
		}, {
			field : 'brand',
			width : '100px',
			title : '品牌'
		}, {
			field : 'model',
			width : '100px',
			title : '规格'

		}, {
			field : 'unit',
			width : '50px',
			title : '单位',
			formatter : function(value, row, index) {
				return getprojectbuildunitbyid(value);
			}
		}, {
			field : 'price',
			title : '价格',
			width : '100px',
				formatter : function(value, row, index) {
					return value+"/元(RMB)";
				}
		}, {
			field : 'useQuantity',
			title : '预警值',
			width : '50px'
		}, {
			field : 'status',
			title : '已用值',
			width : '50px'
		}, {
			field : 'opt',
			title : '操作',
			align:'center',
			width : '200px',
			formatter : function(value, row, index) {
				return createOptBtn1(row.stageId,row.materialStageId,status);
			}
		} ],
		onClickCell : function(field, value, row, $element) {
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
function createOptBtn1(stageId,materialStageId,status) {
	var html = "<a href=\"javascript:void(0);read('" + materialStageId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;";
	if(status!=3)
	{
		html+="<a href=\"javascript:void(0);edit('"+stageId+"','" + materialStageId + "','"+status+"')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;" 
		+ "<a href=\"javascript:void(0);del('" + materialStageId + "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	}
	return html;
}

function edit(stageId,materialStageId,status)
{
$("#materialstagemodal").modal("show");
document.getElementById("form1").reset();
$.ajax({
	url : "/ret/projectbuildmaterialget/getMaterialStageById",
	type : "post",
	dataType : "json",
	data : {
		materialStageId : materialStageId
	},
	success : function(data) {
		if (data.status == 200) {
			for(var name in data.list)
				{
				if(name=="materialId")
					{
						$("#flaga").hide();
						$("#flagb").show();
						$("#materialdiv").html(getMaterial(data.list[name]));
					}else
					{
						$("#"+name).val(data.list[name]);
					}
				}
			
			
		} else {
			console.log(data.msg);
		}
	}
});

$(".js-save").unbind("click").click(function(){
	updateMaterialStage(stageId,materialStageId,status);
});

}
function updateMaterialStage(stageId,materialStageId,status)
{
	$.ajax({
		url : "/set/projectbuildmaterialset/updateMaterialStage",
		type : "post",
		dataType : "json",
		data : {
			materialStageId:materialStageId,
			sortNo:$("#sortNo").val(),
			useQuantity:$("#useQuantity").val(),
			remark:$("#remark").val()
		},
		success : function(data) {
			if (data.status == 200) {
				$("#myTable1").bootstrapTable('destroy');
				 query1(stageId,status);
				 $("#materialstagemodal").modal("hide");
			} else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else{
				console.log(data.msg);
			}
		}
	});
}


function read(materialStageId) {
	window.open("/app/core/projectbuild/material/materialstagedetails?materialStageId=" + materialStageId);
}
function del(materialStageId) {
	if (confirm("确定删除当前材料吗？")) {
		$.ajax({
			url : "/set/projectbuildmaterialset/delMaterialStage",
			type : "post",
			dataType : "json",
			data : {
				materialStageId : materialStageId
			},
			success : function(data) {
				if (data.status == 200) {
					top.layer.msg(data.msg);
					$("#myTable1").bootstrapTable('destroy');
					query1(materialStageId);
				} else {
					console.log(data.msg);
				}
			}
		});
	} else {
		return;
	}
}

function getprojectbuildunitbyid(untiId) {
	var returnStr="";
	$.ajax({
		url : "/ret/projectbuildunitget/getunitbyid",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			unitId:untiId
			
		},
		success : function(data) {
			if (data.status == 200) {
				if(data.list.znName!=null && data.list.znName!="")
					{
						returnStr = data.list.cnName+"|"+data.list.znName;
					}else
					{
						returnStr = data.list.cnName;
					}
			} else {
				console.log(data.msg);
			}
		}
	});
	return returnStr;
}


function getMaterial(materialId) {
	var returnStr="";
		$.ajax({
			url : "/ret/projectbuildmaterialget/getMaterialById",
			type : "post",
			dataType : "json",
			async : false,
			data : {
				materialId : materialId
			},
			success : function(data) {
				if (data.status == 200) {
					returnStr=data.list.name;
				} else {
					console.log(data.msg);
				}
			}
		});
		return returnStr;
}
