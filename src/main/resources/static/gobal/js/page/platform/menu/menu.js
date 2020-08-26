var zTree;
var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/platformget/getPlatformMenuTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "menuId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	callback : {
		onClick : zTreeOnClick
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "menuId",
			pIdKey : "levelId",
			rootPId : "0"
		},
		key : {
			name : "menuName"
		}
	}
};
var setting1 = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/platformget/getPlatformMenuTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "menuId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	view : {
		dblClickExpand : false,
		selectedMulti : false
	//禁止多选
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "menuId",
			pIdKey : "levelId",
			rootPId : "0"
		},
		key : {
			name : "menuName"
		}
	},
	callback : {
		onClick : function(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("menuTree"), nodes = zTree.getSelectedNodes(), v = "";
			vid = "";
			nodes.sort(function compare(a, b) {
				return a.id - b.id;
			});
			for (var i = 0, l = nodes.length; i < l; i++) {
				v += nodes[i].menuName + ",";
				vid += nodes[i].menuId + ",";
			}
			if (v.length > 0)
				v = v.substring(0, v.length - 1);
			var idem = $("#levelId");
			if (vid.length > 0)
				vid = vid.substring(0, vid.length - 1);
			idem.attr("data-value",vid);
			idem.val(v);
		}
	}
};

$(function() {
	$.ajax({
		url : "/ret/platformget/getPlatformMenuTree",
		type : "post",
		dataType : "json",
		success : function(data) {
			zTree = $.fn.zTree.init($("#tree"), setting, data);// 初始化树节点时，添加同步获取的数据
			var topNode = [ {
				menuName : "TOP分类",
				isParent : "fase",
				menuId : ""
			} ];
			var newTreeNodes = topNode.concat(data);
			$.fn.zTree.init($("#menuTree"), setting1, newTreeNodes);
		}
	});
	$("#createbut").unbind("click").click(function() {
		addsort();
	});
	$("#cbut").unbind("click").click(function() {
		document.getElementById("from").reset();
		$("#createbut").show();
		$("#updatabut").hide();
		$("#delbut").hide();
	});
	$("#delbut").unbind("click").click(function() {
		delsort();
	});
	$("#levelId").unbind("click").click(function(e) {
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
	 getPageList();
});
function zTreeOnClick(event, treeId, treeNode) {
	$.ajax({
		url : "/ret/platformget/getPlatformMenuById",
		type : "post",
		dataType : "json",
		data : {
			menuId : treeNode.menuId
		},
		success : function(data) {
			if (data.status == "200") {
				var v = data.list;
				for (name in v) {
					if (name == "levelId") {
						$.ajax({
							url : "/ret/platformget/getPlatformMenuById",
							type : "post",
							dataType : "json",
							data : {
								menuId : v["levelId"]
							},
							success : function(data) {
								if (data.status == "200") {
									if (data.list) {
										$("#levelId").val(data.list.menuName);
									} else {
										$("#levelId").val("");
									}
								}
							}
						});
						$("#levelId").attr("data-value",v.levelId);
					}else if(name=="optType")
					{
						$("input:radio[name='optType'][value='"+v[name]+"']").prop("checked",true);
					}else
					{
						$("#" + name).val(v[name]);
					}
				}
			} else {
				console.log(data.msg);
			}

		}
	});
	$("#createbut").hide();
	$("#updatabut").show();
	$("#delbut").show();
	$("#updatabut").unbind("click").click(function() {
		updateMenu(treeNode.menuId);
	});
}
function delsort() {
	if (confirm("确定删除当前分类吗？")) {
		$.ajax({
			url : "/set/platformset/deletePlatformMenu",
			type : "post",
			dataType : "json",
			data : {
				menuId : $("#menuId").val()
			},
			success : function(data) {
				if (data.status == 200) {
					top.layer.msg(data.msg);
					location.reload();
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
	$.ajax({
		url : "/set/platformset/insertPlatformMenu",
		type : "post",
		dataType : "json",
		data : {
			sortNo : $("#sortNo").val(),
			menuName : $("#menuName").val(),
			appCode : $("#appCode").val(),
			pageId:$("#pageId").val(),
			optType:$("input:radio[name='optType']:checked").val(),
			levelId : $("#levelId").attr("data-value"),
			remark : $("#remark").val()
		},
		success : function(data) {
			if (data.status == 200) {
				top.layer.msg(data.msg);
				location.reload();
			} else {
				console.log(data.msg);
			}
		}
	});
}

function updateMenu(menuId) {
	$.ajax({
		url : "/set/platformset/updatePlatformMenu",
		type : "post",
		dataType : "json",
		data : {
			menuId :menuId,
			sortNo : $("#sortNo").val(),
			menuName : $("#menuName").val(),
			appCode : $("#appCode").val(),
			pageId:$("#pageId").val(),
			optType:$("input:radio[name='optType']:checked").val(),
			levelId : $("#levelId").attr("data-value"),
			remark : $("#remark").val()
		},
		success : function(data) {
			if (data.status == 200) {
				top.layer.msg(data.msg);
				location.reload();
			} else {
				console.log(data.msg);
			}
		}
	});
}

function getPageList()
{
$.ajax({
		url : "/ret/platformget/getPlatformPageList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == "200") {
			var html="<option value=''>请选择</option>";
				for(var i=0; i<data.list.length;i++)
				{
					html+="<option value='"+data.list[i].pageId+"'>"+data.list[i].pageName+"</option>";
				}
			$("#pageId").html(html);
			} else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else{
				console.log(data.msg);
			}
		}
	});
}