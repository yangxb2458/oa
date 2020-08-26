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

var setting1 = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/unitget/getUnitDeptTree",// Ajax 获取数据的 URL 地址
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
			pIdKey : "orgLeaveId",
			rootPId : "0"
		},
		key : {
			name : "deptName"
		}
	},
	callback : {
		onClick : function(e, treeId, treeNode) {
			$("#form").data("bootstrapValidator").updateStatus("parentDeptName", "NOT_VALIDATED", null );//更新指定的字段
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
			var idem = $("#orgLeaveId");
			idem.val(vid);
			$("#form").bootstrapValidator("validateField","parentDeptName");
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
	var topNode1 = [ {
		deptName : orgName,
		orgLeaveId : '',
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
		$("#form").bootstrapValidator('resetForm'); //重置
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
	$("#form").bootstrapValidator('resetForm');
});
function zTreeOnClick(event, treeId, treeNode) {
	$("#form").bootstrapValidator('resetForm'); //重置
	$.ajax({
		url : "/ret/unitget/getUnitDeptById",
		type : "post",
		dataType : "json",
		data : {
			deptId : treeNode.deptId
		},
		success : function(data) {
			if (data.status == 200) {
				var v = data.list;
				for (name in v) {
					if (name == "orgLeaveId") {
						$("#orgLeaveId").val( v["orgLeaveId"]);
						$.ajax({
							url : "/ret/unitget/getUnitDeptById",
							type : "post",
							dataType : "json",
							data : {
								deptId : v["orgLeaveId"]
							},
							success : function(data) {
								if (data.status == "200") {
									if (data.list) {
										$("#parentDeptName").val(data.list.deptName);
									} else {
										$("#parentDeptName").val(orgName);
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
			url : "/set/unitset/delUnitDept",
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
			url : "/set/unitset/addUnitDept",
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
				orgLeaveId : $("#orgLeaveId").val()
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
	if($("#deptId").val()==$("#orgLeaveId").val())
		{
			top.layer.msg("父级部门不能为本身,请重新选择！");
			return;
		}
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if (flag) {
		$.ajax({
			url : "/set/unitset/updateUnitDept",
			type : "post",
			dataType : "json",
			data : {
				deptId : $("#deptId").val(),
				sortNo : $("#sortNo").val(),
				deptName : $("#deptName").val(),
				deptTel : $("#deptTel").val(),
				deptFax : $("#deptFax").val(),
				orgLeaveId : $("#orgLeaveId").val(),
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

function importDeptForExcel(){
	$.ajaxFileUpload({
         url:'/set/unitset/importUnitDept', //上传文件的服务端
         secureuri:false,  //是否启用安全提交
         async:false,
         dataType: 'json',   //数据类型  
         fileElementId:'file', //表示文件域ID
         success: function(data,status){
        	 	if(data.status==200)
        	 		{
        	 		top.layer.msg(data.msg);
        	 		$("#exdiv").modal("hide");
        	 		}else if(data.status==100)
    				{
    					top.layer.msg(data.msg);
    				}else
        	 			{
        	 			console.log(data.msg);
        	 			}
         },
    //提交失败处理函数
         error: function (data,status,e){
        	 top.layer.msg("文件上传出错!请检查文件格式!");
        	console.log(data.msg);
         }
    });
}