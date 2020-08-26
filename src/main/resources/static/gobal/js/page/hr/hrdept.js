var zTree;
var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/hrget/getHrDepartmentTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "deptId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	callback : {
		onClick : zTreeOnClick
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "deptId",
			pIdKey : "orgLevelId",
			rootPId : "0"
		},
		key : {
			name : "deptName"
		}
	}
};

var setting1 = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/hrget/getHrDepartmentTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "deptId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	view : {
		dblClickExpand : false,
		selectedMulti : false
	// 禁止多选
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "deptId",
			pIdKey : "orgLevelId",
			rootPId : "0"
		},
		key : {
			name : "deptName"
		}
	},
	callback : {
		onClick : function(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("menuTree"), nodes = zTree
					.getSelectedNodes(), v = "";
			vid = "";
			nodes.sort(function compare(a, b) {
				return a.id - b.deptId;
			});
			for (var i = 0, l = nodes.length; i < l; i++) {
				v += nodes[i].deptName + ",";
				vid += nodes[i].deptId + ",";
			}
			if (v.length > 0)
				v = v.substring(0, v.length - 1);
			var nameem = $("#parentDeptName");
			nameem.val(v);
			if (vid.length > 0)
				vid = vid.substring(0, vid.length - 1);
			var idem = $("#orgLevelId");
			idem.val(vid);
		}
	}
};

$(function() {
	var topNode = [ {
		deptName : orgName,
		orgLevelId : '',
		isParent : "true",
		deptId : "0",
		icon : "/gobal/img/org/org.png"
	} ];
	var topNode1 = [ {
		deptName : orgName,
		orgLevelId : '',
		isParent : "true",
		deptId : "0",
		icon : "/gobal/img/org/org.png"
	} ];
	zTree = $.fn.zTree.init($("#tree"), setting, topNode);// 初始化树节点时，添加同步获取的数据
	var zTreeObj = $.fn.zTree.init($("#menuTree"), setting1, topNode1);
	var nodes = zTree.getNodes();
	var nodes1 = zTreeObj.getNodes();
	for (var i = 0; i < nodes.length; i++) {
		zTree.expandNode(nodes[i], true, false, false);// 默认展开第一级节点
	}
	for (var i = 0; i < nodes1.length; i++) {
		zTreeObj.expandNode(nodes1[i], true, false, false);// 默认展开第一级节点
	}
	$("#createbut").unbind("click").click(function() {
		adddept();
	});
	$("#cbut").unbind("click").click(function() {
		document.getElementById("form").reset();
		$("#createbut").show();
		$("#updatabut").hide();
		$("#delbut").hide();
	});
	$("#delbut").unbind("click").click(function() {
		delUnitDept();
	});
	$("#updatabut").unbind("click").click(function() {
		updateUnitDept();
	});

	$("#parentDeptName").unbind("click").click(function(e) {
		e.stopPropagation();
		$("#menuContent").css({
			"width" : $(this).outerWidth() + "px"
		}).slideDown(200);
	});
	$("body").unbind("click").click(function() {
		$("#menuContent").hide();
	});

	$("#menuContent").unbind("click").click(function(e) {
		e.stopPropagation();
	});
	$("#cimport").unbind("click").click(function() {
		$("#exdiv").modal("show");
	});
});
function zTreeOnClick(event, treeId, treeNode) {
	$.ajax({
		url : "/ret/hrget/getHrDepartmentById",
		type : "post",
		dataType : "json",
		data : {
			deptId : treeNode.deptId
		},
		success : function(data) {
			if (data.status == 200) {
				var v = data.list;
				for (name in v) {
					if (name == "orgLevelId") {
						$("#orgLevelId").val( v["orgLevelId"]);
						$.ajax({
							url : "/ret/hrget/getHrDepartmentById",
							type : "post",
							dataType : "json",
							data : {
								deptId : v["orgLevelId"]
							},
							success : function(data) {
								if (data.status == "200") {
									if (data.list) {
										$("#parentDeptName").val(data.list.deptName);
									} else {
										$("#parentDeptName").val("");
									}
								}else
								{
									console.log(data.msg);
								}
							}
						});
					} else if (name == "deptLead") {
						$("#deptLead").attr("data-value", v[name]);
						$("#deptLead").val(v["deptLeadUserName"]);
					} else {
						$("#" + name).val(v[name]);
					}
				}

			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}

		}
	});
	$("#createbut").hide();
	$("#updatabut").show();
	$("#delbut").show();
}
function delUnitDept() {
	if (confirm("确定删除当前部门吗？")) {
		$.ajax({
			url : "/set/hrset/deleteHrDepartment",
			type : "post",
			dataType : "json",
			data : {
				deptId : $("#deptId").val()
			},
			success : function(data) {
				if (data.status == 200) {
					top.layer.msg(data.msg);
					location.reload();
				} else if (data.status == 100) {
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

function adddept() {
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if (flag) {
		$.ajax({
			url : "/set/hrset/insertHrDepartment",
			type : "post",
			dataType : "json",
			data : {
				sortNo : $("#sortNo").val(),
				deptName : $("#deptName").val(),
				deptTel : $("#deptTel").val(),
				deptFax : $("#deptFax").val(),
				deptEmail : $("#deptEmail").val(),
				deptLead : $("#deptLead").attr("data-value"),
				deptFunction : $("#deptFunction").val(),
				orgLevelId : $("#orgLevelId").val()
			},
			success : function(data) {
				if (data.status == 200) {
					top.layer.msg(data.msg);
					location.reload();
				} else if (data.status == 100) {
					top.layer.msg(data.msg);
				} else {
					console.log(data.msg);
				}
			}
		});
	}
}

function updateUnitDept() {
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if (flag) {
		$.ajax({
			url : "/set/hrset/updateHrDepartment",
			type : "post",
			dataType : "json",
			data : {
				deptId : $("#deptId").val(),
				sortNo : $("#sortNo").val(),
				deptName : $("#deptName").val(),
				deptTel : $("#deptTel").val(),
				deptFax : $("#deptFax").val(),
				orgLevelId : $("#orgLevelId").val(),
				deptLead : $("#deptLead").attr("data-value"),
				deptEmail : $("#deptEmail").val(),
				deptFunction : $("#deptFunction").val()
			},
			success : function(data) {
				if (data.status == 200) {
					top.layer.msg(data.msg);
					location.reload();
				} else if (data.status == 100) {
					top.layer.msg(data.msg);
				} else {
					console.log(data.msg);
				}
			}
		});
	}
}