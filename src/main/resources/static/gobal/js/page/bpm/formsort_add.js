var zTree;
var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/bpmget/getBpmSortTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "bpmSortId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	callback : {
		onClick : zTreeOnClick
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "bpmSortId",
			rootPId : "0"
		},
		key : {
			name : "bpmSortName"
		}
	}
};

var setting1 = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/bpmget/getBpmSortTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "bpmSortId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	view : {
		dblClickExpand : false,
		selectedMulti : false
	// 禁止多选
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "bpmSortId",
			rootPId : "0"
		},
		key : {
			name : "bpmSortName"
		}
	},
	callback : {
		onClick : function(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("menuTree"), nodes = zTree
					.getSelectedNodes(), v = "";
			vid = "";
			nodes.sort(function compare(a, b) {
				return a.id - b.id;
			});
			for (var i = 0, l = nodes.length; i < l; i++) {
				v += nodes[i].bpmSortName + ",";
				vid += nodes[i].bpmSortId + ",";
			}
			if (v.length > 0)
				v = v.substring(0, v.length - 1);
			var nameem = $("#levelName");
			nameem.val(v);
			if (vid.length > 0)
				vid = vid.substring(0, vid.length - 1);
			var idem = $("#levelId");
			idem.val(vid);
		}
	}
};

$(function() {
	$.ajax({
		url : "/ret/bpmget/getBpmSortTree",
		type : "post",
		dataType : "json",
		data : {
			levelId : "0"
		},
		success : function(data) {
			zTree = $.fn.zTree.init($("#tree"), setting, data);// 初始化树节点时，添加同步获取的数据
			var topNode = [ {
				bpmSortName : "TOP分类",
				isParent : "false",
				bpmSortId : "0"
			} ];
			var newTreeNodes = topNode.concat(data);
			$.fn.zTree.init($("#menuTree"), setting1, newTreeNodes);
		}
	});
	$("#createbut").unbind("click").click(function() {
		addsort();
	});
	$("#delbut").unbind("click").click(function() {
		delBpmSort();
	});
	$("#updatabut").unbind("click").click(function() {
		updateBpmSort();
	});
	$("#cbut").unbind("click").click(function() {
		document.getElementById("form").reset();
		$("#createbut").show();
		$("#updatabut").hide();
		$("#delbut").hide();
	});
	$("#levelName").unbind("click").click(function(e) {
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
});
function zTreeOnClick(event, treeId, treeNode) {
	$("#form").bootstrapValidator('resetForm'); //重置
	$.ajax({
		url : "/ret/bpmget/getBpmSortById",
		type : "post",
		dataType : "json",
		data : {
			bpmSortId : treeNode.bpmSortId
		},
		success : function(data) {
			if (data.status == 200) {
				var v = data.list;
				for (name in v) {
					if (name == "levelId") {
						$.ajax({
							url : "/ret/bpmget/getBpmSortById",
							type : "post",
							dataType : "json",
							data : {
								bpmSortId : v["levelId"]
							},
							success : function(data) {
								if (data.status == "200") {
									if (data.list) {
										$("#levelName").val(data.list.bpmSortName);
									} else{
										$("#levelName").val("");
									}
								}
							}
						});
					}else if(name=="manageAccountId")
					{
						$("#manageAccountId").attr("data-value",v[name]);
						$("#manageAccountId").val(getUserNameByStr(v[name]));
					}else
					{
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
function delBpmSort() {
	if (confirm("确定删除当前分类吗？")) {
		$.ajax({
			url : "/set/bpmset/delBpmSort",
			type : "post",
			dataType : "json",
			data : {
				bpmSortId : $("#bpmSortId").val()
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

function addsort() {
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if (flag) {
		$.ajax({
			url : "/set/bpmset/insertBpmSort",
			type : "post",
			dataType : "json",
			data : {
				sortNo : $("#sortNo").val(),
				bpmSortName : $("#bpmSortName").val(),
				manageAccountId:$("#manageAccountId").attr("data-value"),
				levelId : $("#levelId").val()
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

function updateBpmSort() {
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if (flag) {
		$.ajax({
			url : "/set/bpmset/updateBpmSort",
			type : "post",
			dataType : "json",
			data : {
				bpmSortId : $("#bpmSortId").val(),
				sortNo : $("#sortNo").val(),
				bpmSortName : $("#bpmSortName").val(),
				manageAccountId:$("#manageAccountId").attr("data-value"),
				levelId : $("#levelId").val()
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