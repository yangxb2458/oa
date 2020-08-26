$(function() {
	$(".js-add-but").unbind("click").click(function() {
		document.getElementById("form").reset();
		$("#unitsetmodal").modal("show");
		$(".js-add").unbind("click").click(function() {
			addUnit();
		})
	});
	query();
	$(".js-query-but").unbind("click").click(function(){
		$("#myTable").bootstrapTable('refresh');
	});
})
function addUnit() {
	$.ajax({
		url : "/set/officesuppliesset/insertOfficeSuppliesUnit",
		type : "post",
		dataType : "json",
		data : {
			sortNo : $("#sortNo").val(),
			type : $("#type").val(),
			cnName : $("#cnName").val(),
			enName : $("#enName").val()
		},
		success : function(data) {
			if (data.status == 200) {
				$("#myTable").bootstrapTable('refresh');
				$("#unitsetmodal").modal("hide");
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}
function updateUnit(unitId) {
	$.ajax({
		url : "/set/officesuppliesset/updateOfficeSuppliesUnit",
		type : "post",
		dataType : "json",
		data : {
			unitId : unitId,
			sortNo : $("#sortNo").val(),
			type : $("#type").val(),
			cnName : $("#cnName").val(),
			enName : $("#enName").val()
		},
		success : function(data) {
			if (data.status == 200) {
				$("#myTable").bootstrapTable('refresh');
				$("#unitsetmodal").modal("hide");
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});

}
function deleteUnit(unitId) {
	if (confirm("确定删除当前单位吗？")) {
		$.ajax({
			url : "/set/officesuppliesset/deleteOfficeSuppliesUnit",
			type : "post",
			dataType : "json",
			data : {
				unitId : unitId
			},
			success : function(data) {
				if (data.status == 200) {
					top.layer.msg(data.msg);
					$("#myTable").bootstrapTable('refresh');
				} else if (data.status == "100") {
					top.layer.msg(data.msg);
				} else {
					console.log(data.msg);
				}
			}
		});
	} else {
		return;
	}
}

function editUnit(unitId) {
	$("#unitsetmodal").modal("show");
	document.getElementById("form").reset();
	$.ajax({
		url : "/ret/officesuppliesget/getofficeSuppliesUnitById",
		type : "post",
		dataType : "json",
		data : {
			unitId : unitId,
		},
		success : function(data) {
			if (data.status == 200) {
				for ( var name in data.list) {
					$("#" + name).val(data.list[name]);
				}
				$(".js-add").unbind("click").click(function() {
					updateUnit(unitId);
				})
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/officesuppliesget/getOfficeSuppliesUnitList',
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
		idField : 'unitId',// key值栏位
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
			field : 'cnName',
			title : '计量单位中文名称',
			sortable : true,
			width : '150px'
		}, {
			field : 'enName',
			title : '计量单位英文名称',
			sortable : true,
			width : '150px'
		}, {
			field : 'opt',
			title : '操作',
			align:'center',
			width : '100px',
			align: 'center',
			formatter : function(value, row, index) {
				return createOptBtn(row.unitId);
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
function createOptBtn(unitId) {
	var html = "<a href=\"javascript:void(0);editUnit('" + unitId + "')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;" + "<a href=\"javascript:void(0);deleteUnit('" + unitId + "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
return html;}
