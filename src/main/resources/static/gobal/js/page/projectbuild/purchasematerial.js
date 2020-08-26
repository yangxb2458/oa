$(function() {
	$.fn.modal.Constructor.prototype.enforceFocus = function() {};
	$("#projectId").select2({
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
		minimumInputLength : 2,
		formatResult : function(data) {
			return '<div class="select2-user-result">' + data.text + '</div>'
		},
		formatSelection : function(data) {
			var projectId = data.id;
			getStageList(projectId);
			return data.text;
		},
		initSelection : function(data, cb) {
			cb(data);
		}
	});

	$("#supplierId").select2({
		theme : "bootstrap",
		allowClear : true,
		placeholder : "请输供应商名称",
		query : function(query) {
			var url = "/ret/projectbuildsupplierget/getSelect2SupplierList";
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
						"id" : land.supplierId,
						"text" : land.companyName
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
			return '<div class="select2-user-result">' + data.text + '</div>'
		},
		formatSelection : function(data) {
			var projectId = data.id;
			return data.text;
		},
		initSelection : function(data, cb) {
			cb(data);
		}
	});
	query();
	$("#createbut").unbind("click").click(function() {
		sendapply();
	})
});

function sendapply() {
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	var json = $('#myTable').bootstrapTable('getData');
	var paramjson = [];
	if (json == null || json.length == 0) {
		top.layer.msg("采购材料列表不能为空");
		return;
	} else {
		for (var i = 0; i < json.length; i++) {
			paramjson.push({
				sortNo : json[i].num,
				materialId : json[i].materialId,
				quantity : json[i].quantity,
				price : json[i].price,
				remark : json[i].remark
			});
		}
	}
	$.ajax({
		url : "/set/projectbuildmaterialset/insertMaterialPurchase",
		type : "post",
		dataType : "json",
		data : {
			title : $("#title").val(),
			projectId : $("#projectId").val(),
			stageId : $("#stageId").val(),
			supplierId : $("#supplierId").val(),
			materialMx : JSON.stringify(paramjson),
			amount : $("#amount").val(),
			remark : $("#remark").val(),
			attach:$("#projectbuildattach").attr("data_value")
		},
		success : function(data) {
			if (data.status == 200) {
				//window.location.href = data.redirect;
				open(data.redirect,"_self");
			} else {
				console.log(data.msg);
			}
		}
	});
	}
}

function getStageList(projectId) {
	$.ajax({
		url : "/ret/projectbuildget/getprojectbuildStageOpenlist",
		type : "post",
		dataType : "json",
		data : {
			projectId : projectId
		},
		success : function(data) {
			if (data.status == "200") {
				var html = "";
				for (var i = 0; i < data.list.length; i++) {
					html += "<option value='" + data.list[i].stageId + "'>" + data.list[i].stageName + "</option>";
				}
				$("#stageId").html(html);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function addRow() {
	var stageId = $("#stageId").val();
	if (stageId == null && stageId != "") {
		top.layer.msg("请先确定工程节点！");
		return;
	}
	document.getElementById("form1").reset();
	$("#materialCode").attr("data-value", "");
	$("#materialCode").html("");
	$("#model").html("");
	$("#brand").html("");
	$("#unit").html("");
	$("#ckjg").html("");
	$("#materialSelectModal").modal("show");
	$("#materialId").select2({
		theme : "bootstrap",
		allowClear : true,
		placeholder : "请输入材料名称",
		query : function(query) {
			var url = "/ret/projectbuildmaterialget/getMaterialListInStage";
			var param = {
				search : query.term,
				stageId : stageId
			}; // 查询参数，query.term为用户在select2中的输入内容.
			var type = "json";
			var data = {
				results : []
			};
			$.post(url, param, function(datas) {
				var datalist = datas.list;
				for (var i = 0, len = datalist.length; i < len; i++) {
					var land = datalist[i];
					console.log(land);
					var option = {
						"id" : land.materialId,
						"text" : land.name,
						"materialCode" : land.materialCode,
						"brand" : land.brand,
						"price" : land.price,
						"unit" : land.unit,
						"model" : land.model
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
			return '<div class="select2-user-result">' + data.text + '</div>'
		},
		formatSelection : function(data) {
			$("#materialCode").attr("data-value-id", data.id);
			$("#materialCode").attr("data-value", data.text);
			$("#materialCode").html(data.materialCode);
			$("#model").html(data.model);
			$("#barnd").html(data.barnd);
			$("#ckjg").html(data.price + "元/RMB");
			$("#unit").html(getprojectbuildunitbyid(data.unit));
			return data.text;
		},
		initSelection : function(data, cb) {
			cb(data);
		}
	});
	$(".js-save").unbind("click").click(function() {
		var count = $('#myTable').bootstrapTable('getData').length;
		$('#myTable').bootstrapTable('insertRow', {
			index : count,
			row : {
				num : count,
				materialId : $("#materialCode").attr("data-value-id"),
				name : $("#materialCode").attr("data-value"),
				materialCode : $("#materialCode").text(),
				model : $("#model").text(),
				brand : $("#brand").text(),
				quantity : $("#quantity").val(),
				price : $("#price").val(),
				unit : $("#unit").text(),
				remark : $("#mxremark").val(),
				opt : "<a href=\"javascript:void(0);delrow(" + count + ");\" class=\"btn btn-darkorange btn-xs\" >删除</a>"
			}
		});
		$("#materialSelectModal").modal("hide");
	});
}

function delrow(index) {
	$("#myTable").bootstrapTable('remove', {
		field : "num",
		values : [ index ]
	});
}

function changeMaterial(materialId) {

}

function query() {
	$("#myTable").bootstrapTable({
		method : 'post',
		contentType : 'application/x-www-form-urlencoded',
		striped : true,// 隔行换色
		cache : false,// 禁用缓存
		pagination : false,// 启动分页
		sidePagination : 'client',// 分页方式
		pageNumber : 1,// 初始化table时显示的页码
		pageSize : 50,// 每页条目
		showFooter : false,// 是否显示列脚
		showPaginationSwitch : false,// 是否显示 数据条数选择框
		sortable : false,// 排序
		search : false,// 启用搜索
		showColumns : false,// 是否显示 内容列下拉框
		showRefresh : false,// 显示刷新按钮
		idField : 'materialMxId',// key值栏位
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
			field : 'materialId',
			title : '材料ID',
			visible : false,
			width : '150px'
		}, {
			field : 'name',
			title : '材料名称',
			width : '150px'
		}, {
			field : 'materialCode',
			width : '100px',
			title : '材料编号'
		}, {
			field : 'model',
			width : '150px',
			title : '产品型号'
		}, {
			field : 'quantity',
			width : '100px',
			title : '采购数量'
		}, {
			field : 'price',
			width : '100px',
			title : '采购单价'
		}, {
			field : 'unit',
			width : '50px',
			title : '单位'
		}, {
			field : 'remark',
			width : '150px',
			title : '备注'
		}, {
			field : 'opt',
			title : '操作',
			width : '50px'
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

function getprojectbuildunitbyid(untiId) {
	var returnStr = "";
	$.ajax({
		url : "/ret/projectbuildunitget/getunitbyid",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			unitId : untiId

		},
		success : function(data) {
			if (data.status == 200) {
				if (data.list.znName != null && data.list.znName != "") {
					returnStr = data.list.cnName + "|" + data.list.znName;
				} else {
					returnStr = data.list.cnName;
				}
			} else {
				console.log(data.msg);
			}
		}
	});
	return returnStr;
}
