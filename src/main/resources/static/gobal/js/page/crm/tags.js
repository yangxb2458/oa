$(function() {
	$(".js-btn").unbind("click").click(function() {
		document.getElementById("form1").reset()
		$("#addTagsModal").modal("show");
		$(".js-save").unbind("click").click(function() {
			add();
		});
	});
	query();
	$.ajax({
		url : "/ret/crmget/getCrmIndustryList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == 200) {
				var list = data.list;
				$("#industry").append("<option value=''>请选择</option>")
				for (var i = 0; i < list.length; i++) {
					$("#industry").append(
							"<option value='" + list[i].industryId + "'>"
									+ list[i].industryName + "</option>")
				}
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
});
function add() {
	$.ajax({
		url : "/set/crmset/addTags",
		type : "post",
		dataType : "json",
		data : {
			sortNo : $("#sortNo").val(),
			industryId : $("#industry").val(),
			tagsName : $("#tagsName").val(),
			remark : $("#remark").val()
		},
		success : function(data) {
			if (data.status == 200) {
				top.layer.msg(data.msg);
				$('#myTable').bootstrapTable('refresh');
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
	$("#addTagsModal").modal("hide");
}

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/crmget/getAllTags',
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
		idField : 'tagsId',//key值栏位
		clickToSelect : true,//点击选中checkbox
		pageList : [ 10, 20, 30, 50 ],//可选择单页记录数
		queryParams : queryParams,
		columns : [ {
			checkbox : true
		}, {
			field : 'num',
			title : '序号',//标题  可不加
			width : '30px',
			formatter : function(value, row, index) {
				return index + 1;
			}
		}, {
			field : 'tagsId',
			title : '系统编码',
			sortable : true,
			width : '400px'
		}, {
			field : 'sortNo',
			title : '排序号',
			sortable : true,
			width : '50px'
		}, {
			field : 'industryName',
			title : '所属行业',
			sortable : false,
			width : '150px'
		}, {
			field : 'tagsName',
			title : '企业标签',
			sortable : true,
			width : '200px'
		}, {
			field : 'remark',
			title : '备注'
		}, {
			field : 'opt',
			title : '操作',
			width : '180px',
			align:'center',
			formatter : function(value, row, index) {
				return createOptBtn(row.tagsId, row.tagsName);
			}
		} ],
		onClickCell : function(field, value, row, $element) {
			//alert(row.SystemDesc);
		},
		responseHandler : function(res) {
			if (res.status == "500") {
				console.log(res.msg);
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
function createOptBtn(tagsId, tagsName) {
	var html = "<a onclick=\"edit(this)\" value-data='" + tagsId
			+ "' value-cname='" + tagsName
			+ "' class=\"btn btn-primary btn-xs\">修改</a>&nbsp;&nbsp;"
			+ "<a onclick=\"del(this)\" value-data='" + tagsId
			+ "' value-cname='" + tagsName
			+ "' class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
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
function del(Obj) {
	var msg = "您真的确定要删除" + $(Obj).attr("value-cname") + "吗？\n\n请确认！";
	if (confirm(msg) == true) {
		$.ajax({
			url : "/set/crmset/delTags",
			type : "post",
			dataType : "json",
			data : {
				tagsId : $(Obj).attr("value-data")
			},
			success : function(data) {
				if (data.status == "200") {
					top.layer.msg(data.msg);
					$('#myTable').bootstrapTable('refresh');
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
function edit(Obj) {
	document.getElementById("form1").reset()
	$.ajax({
		url : "/ret/crmget/getCrmTags",
		type : "post",
		dataType : "json",
		data : {
			tagsId : $(Obj).attr("value-data")
		},
		success : function(data) {
			if (data.status == "200") {
				var tagsId = data.list.tagsId;
				$("#sortNo").val(data.list.sortNo);
				$("#tagsName").val(data.list.tagsName);
				$("#remark").val(data.list.remark);
				$(".js-save").unbind("click").click(function() {
					update(tagsId);
				});
				$("#addTagsModal").modal("show");
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function update(tagsId) {
	$.ajax({
		url : "/set/crmset/updateTags",
		type : "post",
		dataType : "json",
		data : {
			tagsId : tagsId,
			sortNo : $("#sortNo").val(),
			industryId : $("#industry").val(),
			tagsName : $("#tagsName").val(),
			remark : $("#remark").val()
		},
		success : function(data) {
			if (data.status == 200) {
				top.layer.msg(data.msg);
				$('#myTable').bootstrapTable('refresh');
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
	$("#addTagsModal").modal("hide");
}