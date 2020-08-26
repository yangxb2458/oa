$(function() {
	query();
});

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/sysget/getAllSysProfileList',
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
		showPaginationSwitch : false,//是否显示 数据条数选择框
		sortable : false,//排序
		search : false,//启用搜索
		showColumns : false,//是否显示 内容列下拉框
		showRefresh : false,//显示刷新按钮
		idField : 'profileId',//key值栏位
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
			field : 'title',
			width : '100px',
			title : '门户名称'
		}, {
			field : 'url',
			title : 'URL标识',
			width : '50px',
		}, {
			field : 'userPriv',
			width : '150px',
			title : '用户权限',
			formatter : function(value, row, index) {
				return getUserNameByStr(value);
			}
		}, {
			field : 'deptPriv',
			title : '部门权限',
			width : '150px',
			formatter : function(value, row, index) {
				return getDeptNameByDeptIds(value);
			}
		}, {
			field : 'leavePriv',
			title : '职务权限',
			width : '150px',
			formatter : function(value, row, index) {
				return getUserLevelStr(value);
			}
		}, {
			field : 'status',
			width : '50px',
			title : '状态',
			formatter : function(value, row, index) {
				if(value=="0")
				{
					return "<a href=\"javascript:void(0);\" class=\"btn btn-palegreen btn-xs\">不可用</a>";
				}else 
				{
					return "<a href=\"javascript:void(0);\" class=\"btn btn-success btn-xs\">使用中</a>";
				}
			}
		}, {
			field : 'remark',
			width : '200px',
			title : '备注'
		}, {
			field : 'opt',
			title : '操作',
			align : 'center',
			width : '100px',
			formatter : function(value, row, index) {
				return createOptBtn(row.profileId, row.status);
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
function createOptBtn(profileId, status) {
	var html = "<a href=\"javascript:void(0);edit('" + profileId + "')\" class=\"btn btn-sky btn-xs\" >权限</a>&nbsp;&nbsp;";
	if (status == "0") {
		html += "<a href=\"javascript:void(0);setstatus('" + profileId + "','1')\" class=\"btn btn-success btn-xs\" >启用</a>";
	} else {
		html += "<a href=\"javascript:void(0);setstatus('" + profileId + "','0')\" class=\"btn btn-darkorange btn-xs\" >停用</a>";
	}
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
function setstatus(profileId,status)
{
	$.ajax({
		url : "/set/sysset/updateSysProfile",
		type : "post",
		dataType : "json",
		data : {
			profileId : profileId,
			status:status
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				top.layer.msg(data.msg);
				$("#myTable").bootstrapTable('refresh');
			}
		}
	});
}



function edit(profileId) {
	document.getElementById("form1").reset();
	$("#userPriv").attr("data-value", "");
	$("#deptPriv").attr("data-value", "");
	$("#leavePriv").attr("data-value", "");
	$.ajax({
		url : "/ret/sysget/getSysProfileById",
		type : "post",
		dataType : "json",
		data : {
			profileId : profileId
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				for ( var name in data.list) {
					if (name == "userPriv") {
						$("#userPriv").val(getUserNameByStr(data.list[name]));
						$("#userPriv").attr("data-value", data.list[name]);
					} else if (name == "deptPriv") {
						$("#deptPriv").val(getDeptNameByDeptIds(data.list[name]));
						$("#deptPriv").attr("data-value", data.list[name]);
					} else if (name == "leavePriv") {
						$("#leavePriv").val(getUserLevelStr(data.list[name]));
						$("#leavePriv").attr("data-value", data.list[name]);
					}
				}
			}
		}
	});
	$("#setprofilemodal").modal("show");
	$(".js-save").unbind("click").click(function() {
		$.ajax({
			url : "/set/sysset/updateSysProfile",
			type : "post",
			dataType : "json",
			data : {
				profileId : profileId,
				userPriv : $("#userPriv").attr("data-value"),
				deptPriv : $("#deptPriv").attr("data-value"),
				leavePriv : $("#leavePriv").attr("data-value")
			},
			success : function(data) {
				if (data.status == "500") {
					console.log(data.msg);
				} else if (data.status == "100") {
					top.layer.msg(data.msg);
				} else {
					top.layer.msg(data.msg);
					$("#myTable").bootstrapTable('refresh');
				}
			}
		});
		$("#setprofilemodal").modal("hide");
	});
}
