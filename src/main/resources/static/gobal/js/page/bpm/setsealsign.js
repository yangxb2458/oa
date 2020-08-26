$(function() {
	$(".js-add-but").unbind("click").click(function() {
		$("#form").bootstrapValidator('resetForm'); //重置
		document.getElementById("form").reset();
		$("#sealsignsetmodal").modal("show");
		$(".js-add").unbind("click").click(function() {
			addBpmSealSign();
		})
	});
	query();
	$("#form").bootstrapValidator('resetForm'); //重置
})
function addBpmSealSign() {
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if (flag) {
	if($("#password").val()!=$("#password1").val())
	{
		top.layer.msg("两次输入的密码不一致!");
		return;
	}
	$.ajax({
		url : "/set/bpmset/insertBpmSealSign",
		type : "post",
		dataType : "json",
		data : {
			sortNo : $("#sortNo").val(),
			title : $("#title").val(),
			accountId : $("#accountId").attr("data-value"),
			attachId:$("#systemattach").attr("data_value"),
			password : $("#password").val()
		},
		success : function(data) {
			if (data.status == 200) {
				$("#myTable").bootstrapTable('refresh');
				$("#sealsignsetmodal").modal("hide");
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
	}
}
function updateBpmSealSign(sealSignId) {
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if (flag) {
		if($("#password").val()!=$("#password1").val())
		{
			top.layer.msg("两次输入的密码不一致!");
			return;
		}
	$.ajax({
		url : "/set/bpmset/updateBpmSealSign",
		type : "post",
		dataType : "json",
		data : {
			sealSignId : sealSignId,
			sortNo : $("#sortNo").val(),
			title : $("#title").val(),
			accountId : $("#accountId").attr("data-value"),
			attachId:$("#systemattach").attr("data_value"),
			password : $("#password").val()
		},
		success : function(data) {
			if (data.status == 200) {
				$("#myTable").bootstrapTable('refresh');
				$("#sealsignsetmodal").modal("hide");
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
	}
}
function deleteSealSign(sealSignId) {
	if (confirm("确定删除当前签名吗？")) {
		$.ajax({
			url : "/set/bpmset/deleteBpmSealSign",
			type : "post",
			dataType : "json",
			data : {
				sealSignId : sealSignId
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

function editBpmSealSign(sealSignId) {
	$("#form").bootstrapValidator('resetForm'); //重置
	$("#sealsignsetmodal").modal("show");
	document.getElementById("form").reset();
	$.ajax({
		url : "/ret/bpmget/getBpmSealSignById",
		type : "post",
		dataType : "json",
		data : {
			sealSignId : sealSignId
		},
		success : function(data) {
			console.log(data.list);
			if (data.status == 200) {
				for ( var name in data.list) {
					if(name=="accountId")
					{
						$("#" + name).attr("data-value",data.list[name]);
						$("#" + name).val(getUserNameByStr(data.list[name]));
					}else
					{
						$("#" + name).val(data.list[name]);
					}
				}
				$(".js-add").unbind("click").click(function() {
					updateBpmSealSign(sealSignId);
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
		url : '/ret/bpmget/getAllBpmSealSignList',
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
		idField : 'sealSignId',// key值栏位
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
			field : 'title',
			width : '10px',
			sortable : true,
			title : '签名名称'
		}, {
			field : 'usedUserName',
			title : '授权使用人',
			width : '100px'
		}, {
			field : 'createUserName',
			title : '创建人',
			width : '100px'
		}, {
			field : 'opt',
			title : '操作',
			width : '100px',
			align:'center',
			formatter : function(value, row, index) {
				return createOptBtn(row.sealSignId);
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
function createOptBtn(sealSignId) {
	var html = "<a href=\"javascript:void(0);editBpmSealSign('" + sealSignId + "')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;" + "<a href=\"javascript:void(0);deleteSealSign('" + sealSignId + "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
return html;
}
