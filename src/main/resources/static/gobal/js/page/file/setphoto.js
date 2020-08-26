$(function() {
	query();
});

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/fileget/getPhotoList',
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
		search : false,//启用搜索
		showColumns : true,//是否显示 内容列下拉框
		showRefresh : true,//显示刷新按钮
		idField : 'photoId',//key值栏位
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
			field : 'photoTitle',
			width : '100px',
			title : '相册名称'
		}, {
			field : 'rootPath',
			title : '硬盘目录',
			width : '200px',
		}, {
			field : 'createTime',
			width : '100px',
			title : '创建时间'
		}, {
			field : 'createUserName',
			width : '50px',
			title : '创建人'
		}, {
			field : 'opt',
			title : '操作',
			align:'center',
			width : '150px',
			formatter : function(value, row, index) {
				return createOptBtn(row.photoId);
			}
		} ],
		onClickCell : function(field, value, row, $element) {
			//alert(row.SystemDesc);
		},
		responseHandler : function(res) {
			if (res.status == "500") {
				console.log(res.msg);
				top.layer.msg(res.msg);
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
function createOptBtn(photoId) {
	var html = "<a href=\"javascript:void(0);edit('" + photoId + "');\" class=\"btn btn-purple btn-xs\" >编辑</a>&nbsp;&nbsp;<a href=\"javascript:void(0);del('" + photoId + "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>&nbsp;&nbsp;<a href=\"javascript:void(0);readdetails('" + photoId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>";
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
function readdetails(photoId)
{
	$("#detailsmodal").modal("show");
	$.ajax({
		url : "/ret/fileget/getPhotoById",
		type : "post",
		dataType : "json",
		data : {
			photoId : photoId
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				for ( var name in data.list) {
					for ( var name in data.list) {
						if (name == "accessUser") {
							$("#d_accessUser").html(getUserNameByStr(data.list[name]));
						} else if (name == "accessDept") {
							$("#d_accessDept").html(getDeptNameByDeptIds(data.list[name]));
						} else if (name == "accessLeave") {
							$("#d_accessLeave").html(getUserLevelStr(data.list[name]));
						} else if (name == "manageUser") {
							$("#d_manageUser").html(getUserNameByStr(data.list[name]));
						} else {
							$("#d_" + name).html(data.list[name]);
						}
					}
				}
			}
		}
	});
}

function edit(photoId) {
	document.getElementById("form1").reset();
	$.ajax({
		url : "/ret/fileget/getPhotoById",
		type : "post",
		dataType : "json",
		data : {
			photoId : photoId
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				for ( var name in data.list) {
					for ( var name in data.list) {
						if (name == "accessUser") {
							$("#accessUser").attr("data-value", data.list[name]);
							$("#accessUser").val(getUserNameByStr(data.list[name]));
						} else if (name == "accessDept") {
							$("#accessDept").attr("data-value", data.list[name]);
							$("#accessDept").val(getDeptNameByDeptIds(data.list[name]));
						} else if (name == "accessLeave") {
							$("#accessLeave").attr("data-value", data.list[name]);
							$("#accessLeave").val(getUserLevelStr(data.list[name]));
						} else if (name == "manageUser") {
							$("#manageUser").attr("data-value", data.list[name]);
							$("#manageUser").val(getUserNameByStr(data.list[name]));
						} else {
							$("#" + name).val(data.list[name]);
						}
					}
				}
			}
		}
	});
	$("#createPhotomodal").modal("show");
	$(".js-save").unbind("click").click(function() {
		$.ajax({
			url : "/set/fileset/updatePhoto",
			type : "post",
			dataType : "json",
			data : {
				photoId : photoId,
				sortNo : $("#sortNo").val(),
				photoTitle : $("#photoTitle").val(),
				accessUser : $("#accessUser").attr("data-value"),
				accessDept : $("#accessDept").attr("data-value"),
				accessLeave : $("#accessLeave").attr("data-value"),
				manageUser : $("#manageUser").attr("data-value")
			},
			success : function(data) {
				if (data.status == "500") {
					console.log(data.msg);
				} else {
					top.layer.msg(data.msg);
				}
			}
		});
		$('#myTable').bootstrapTable('refresh');
		$("#createPhotomodal").modal("hide");
	});
}
function del(photoId) {
	var msg = "您真的确定要删除吗？请确认！";
	if (confirm(msg) == true) {
		$.ajax({
			url : "/set/fileset/delPhoto",
			type : "post",
			dataType : "json",
			data : {
				photoId : photoId
			},
			success : function(data) {
				if (data.status == "500") {
					console.log(data.msg);
				} else if (data.status == "100") {
					console.log(data.msg);
				} else {
					$('#myTable').bootstrapTable('refresh');
					top.layer.msg(data.msg);
				}
			}
		});
	} else {
		return;
	}
}

function doadd() {
	document.getElementById("form1").reset();
	$("#createPhotomodal").modal("show");
	$(".js-save").unbind("click").click(function() {
		$.ajax({
			url : "/set/fileset/insertPhoto",
			type : "post",
			dataType : "json",
			data : {
				sortNo : $("#sortNo").val(),
				photoTitle : $("#photoTitle").val(),
				rootPath : $("#rootPath").val(),
				accessUser : $("#accessUser").attr("data-value"),
				accessDept : $("#accessDept").attr("data-value"),
				accessLeave : $("#accessLeave").attr("data-value"),
				manageUser : $("#manageUser").attr("data-value")
			},
			success : function(data) {
				if (data.status == "500") {
					console.log(data.msg);
				} else if (data.status == "100") {
					console.log(data.msg);
				} else {
					$('#myTable').bootstrapTable('refresh');
					top.layer.msg(data.msg);
				}
			}
		});
		$("#createPhotomodal").modal("hide");
	});
}