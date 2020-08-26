var zTree;
var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/unitget/getUnitDeptTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "deptId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	callback : {
		onClick : zTreeOnClick
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "deptId",
			pIdKey : "orgLeaveId",
			rootPId : "0"
		},
		key : {
			name : "deptName"
		}
	}
};
$(function() {
	var topNode = [ {
		deptName : orgName,
		orgLeaveId : '',
		isParent : "true",
		deptId : "0",
		icon : "/gobal/img/org/org.png"
	} ];
	zTree = $.fn.zTree.init($("#tree"), setting, topNode);// 初始化树节点时，添加同步获取的数据
	var nodes = zTree.getNodes();
	for (var i = 0; i < nodes.length; i++) {
		zTree.expandNode(nodes[i], true, false, false);//默认展开第一级节点
	}
	query("");
});
function zTreeOnClick(event, treeId, treeNode) {
	$("#myTable").bootstrapTable('destroy');
	if (treeNode.deptId == 0) {
		query("");
	} else {
		query(treeNode.deptId);
	}
}

function query(deptId) {
	$("#myTable").bootstrapTable({
		url : '/ret/unitget/getWeiXinUserInfoByDeptId?deptId=' + deptId,
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
		idField : 'accountId',//key值栏位
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
			field : 'accountId',
			title : 'OA账号',
			sortable : true,
			width : '100px'
		}, {
			field : 'wAccountId',
			title : '微信账号',
			sortable : true,
			width : '100px'
		}, {
			field : 'userName',
			width : '100px',
			title : '姓名'
		}, {
			field : 'sex',
			width : '50px',
			title : '性别'
		}, {
			field : 'mobileNo',
			width : '100px',
			title : '手机号码'
		}, {
			field : 'postion',
			width : '100px',
			title : '职务'
		}, {
			field : 'wStatus',
			title : '绑定状态',
			align : 'center',
			width : '100px',
			formatter : function(value, row, index) {
				if (value == "1") {
					return "<a href=\"javascript:void(0);\" class=\"btn btn-palegreen btn-xs\">已绑定</a>";
				} else {
					return "<a href=\"javascript:void(0);\" class=\"btn btn-darkorang btn-xs\">未绑定</a>";
				}
			}
		}, {
			field : 'opt',
			title : '操作',
			align : 'center',
			width : '100px',
			formatter : function(value, row, index) {
				return createOptBtn(row.accountId, row.wStatus);
			}
		} ],
		onClickCell : function(field, value, row, $element) {
		},
		responseHandler : function(res) {
			if (res.status == "500") {
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
function createOptBtn(accountId, wStatus) {
	var html = "";
	if (wStatus == "" || wStatus == null||wStatus==undefined) {
		html += "<a href=\"javascript:void(0);syncWeiXinAccount('" + accountId + "')\" class=\"btn btn-primary btn-xs\">同步用户</a>";
	} else {
		html += "<a href=\"javascript:void(0);deleteWeiXinAccount('" + accountId + "')\" class=\"btn btn-warning btn-xs\">删除用户</a>";
	}

	return html;
}

function deleteWeiXinAccount(accountId) {
	$.ajax({
		url : "/set/unitset/deleteWeiXinAccount",
		type : "post",
		dataType : "json",
		data : {
			accountId : accountId
		},
		success : function(data) {
			if (data.status == "200") {
				$("#myTable").bootstrapTable('refresh');
				top.layer.msg(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function syncWeiXinAccount(accountId) {
	$.ajax({
		url : "/set/unitset/createWeiXinAccount",
		type : "post",
		dataType : "json",
		data : {
			accountId : accountId
		},
		success : function(data) {
			if (data.status == "200") {
				$("#myTable").bootstrapTable('refresh');
				top.layer.msg(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}