/**
 * 用户选择
 * 
 * @param obj
 * @param opt
 * 为'true'为多选 'false' 为单选
 * @returns
 */
function selectUser(obj, opt) {
	var u = $(obj).attr("opt-id");
	var accountIds = $("#" + u).attr("data-value");
	initUserSelect(accountIds, opt);
	$(".selectuser").empty();
	$(".selecteduser").empty();
	var orgSetig = {
		async : {
			enable : true,// 设置 zTree 是否开启异步加载模式
			url : "/ret/unitget/getUnitDeptTree",// Ajax 获取数据的 URL 地址
			autoParam : ["deptId"],// 异步加载时需要自动提交父节点属性的参数
		},
		callback : {
			onClick : toselectuserclick
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
	var topNode = [{
		deptName : orgName.substring(0, 8),
		orgLeaveId : '',
		isParent : "true",
		deptId : "0",
		icon : "/gobal/img/org/org.png"
	}];
	var zTree = $.fn.zTree.init($("#selectUsertree"), orgSetig, topNode);// 初始化树节点时，添加同步获取的数据
	var nodes = zTree.getNodes();
	for (var i = 0; i < nodes.length; i++) {
		zTree.expandNode(nodes[i], true, false, false);// 默认展开第一级节点
	}
	function toselectuserclick(event, treeId, treeNode) {
		$(".selectuser").empty();
		$.ajax({
			url : "/ret/unitget/getSelectUserByDeptId",
			type : "post",
			dataType : "json",
			data : {
				deptId : treeNode.deptId
			},
			success : function(data) {
				if (data.status == 200) {
					var userlist = data.list;
					for (var i = 0; i < userlist.length; i++) {
						if (isExist(".selecteduser", userlist[i].accountId)) {
							var headimg = "/gobal/img/org/U01.png";
							if (userlist[i].sex == "女") {
								headimg = "/gobal/img/org/U11.png"
							}
							$(".selectuser").append(
									"<div class=\"selectdiv\" onclick=\"doSelectUser(this,'"
											+ opt + "');\"  data-name=\""
											+ userlist[i].userName
											+ "\" data-value=\""
											+ userlist[i].accountId
											+ "\"><img src=\"" + headimg
											+ "\"><span>"
											+ userlist[i].userName
											+ "</span></div>");
						}
					}
				} else if (data.status == 100) {
					top.layer.msg(data.msg);
				} else {
					console.log(data.msg);
				}
			}
		});
	}
	$('#userselect').modal('show');
	$('.js-selectuserall').unbind("click").click(function() {
		if (opt == 'true') {
			selectUserAll();
		} else {
			top.layer.msg("单选模式不能全选");
		}
	});
	$('.js-unselectuserall').unbind("click").click(function() {
		unSelectUserAll();
	});
	$(".selectokbtn").unbind("click").click(function() {
		var nameStr = [];
		var accountIdStr = [];
		$(".selecteduser div").each(function() {
			nameStr.push($(this).attr("data-name"));
			accountIdStr.push($(this).attr("data-value"));
		});
		$("#" + u).val(nameStr.join(","));
		$("#" + u).attr("data-value", accountIdStr.join(","));
		$('#userselect').modal('hide');
	});
	$(".js-searchuser").unbind("click").click(function() {
		searchuser(obj, opt);
	});
	$(".js-selectalluser").unbind("click").click(function() {
		$("#" + u).val("全体人员");
		$("#" + u).attr("data-value", "@all");
		$('#userselect').modal('hide');
	});
	if (opt != 'true') {
		$(".js-selectalluser").remove();
	}
	$(".js-dept").unbind("click").click(function(){
		$(".js-part2").hide();
		$(".js-part3").hide();
		$(".js-part1").show();
	})
	$(".js-level").unbind("click").click(function(){
		$(".js-part1").hide();
		$(".js-part3").hide();
		$(".js-part2").show();
		getuserlevellist(opt);
	})
	$(".js-group").unbind("click").click(function(){
		$(".js-part1").hide();
		$(".js-part2").hide();
		$(".js-part3").show();
		getusergrouplist(opt);
	})
}

function getusergrouplist(opt)
{
	$.ajax({
		url : "/ret/unitget/getUserGroupListByAccountId",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var html="";
				var groupList = data.list;
				for(var i=0;i<groupList.length;i++)
				{
					html+="<div class=\"selectdiv\" onclick=\"getgroupuserlist(this,'"+opt+"');\"  data-value=\""+ groupList[i].groupId+ "\"><span>"+ groupList[i].groupTitle+ "</span></div>";
				}
			$("#selectgrouplist").html(html);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function getgroupuserlist(Obj,opt)
{
	$(".selectuser").empty();
	$.ajax({
		url : "/ret/unitget/getSelectUserByGroupId",
		type : "post",
		dataType : "json",
		data : {
			groupId : $(Obj).attr("data-value")
		},
		success : function(data) {
			if (data.status == 200) {
				var userlist = data.list;
				for (var i = 0; i < userlist.length; i++) {
					if (isExist(".selecteduser", userlist[i].accountId)) {
						var headimg = "/gobal/img/org/U01.png";
						if (userlist[i].sex == "女") {
							headimg = "/gobal/img/org/U11.png"
						}
						$(".selectuser").append(
								"<div class=\"selectdiv\" onclick=\"doSelectUser(this,'"
										+ opt + "');\"  data-name=\""
										+ userlist[i].userName
										+ "\" data-value=\""
										+ userlist[i].accountId
										+ "\"><img src=\"" + headimg
										+ "\"><span>"
										+ userlist[i].userName
										+ "</span></div>");
					}
				}
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function getuserlevellist(opt)
{
	$.ajax({
		url : "/ret/unitget/getUserLevelList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var html="";
				var levelList = data.list;
				for(var i=0;i<levelList.length;i++)
				{
					html+="<div class=\"selectdiv\" onclick=\"getuserlist(this,'"+opt+"');\"  data-value=\""+ levelList[i].levelId+ "\"><span>"+ levelList[i].levelName+ "</span></div>";
				}
			$("#selectlevellist").html(html);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function getuserlist(Obj,opt)
{
	$(".selectuser").empty();
	$.ajax({
		url : "/ret/unitget/getSelectUserByLevelId",
		type : "post",
		dataType : "json",
		data : {
			levelId : $(Obj).attr("data-value")
		},
		success : function(data) {
			if (data.status == 200) {
				var userlist = data.list;
				for (var i = 0; i < userlist.length; i++) {
					if (isExist(".selecteduser", userlist[i].accountId)) {
						var headimg = "/gobal/img/org/U01.png";
						if (userlist[i].sex == "女") {
							headimg = "/gobal/img/org/U11.png"
						}
						$(".selectuser").append(
								"<div class=\"selectdiv\" onclick=\"doSelectUser(this,'"
										+ opt + "');\"  data-name=\""
										+ userlist[i].userName
										+ "\" data-value=\""
										+ userlist[i].accountId
										+ "\"><img src=\"" + headimg
										+ "\"><span>"
										+ userlist[i].userName
										+ "</span></div>");
					}
				}
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function searchuser(obj, opt) {
	if($("#searchuser").val()=="")
	{
		top.layer.msg("请输入需要查询的员人的姓名或拼音");
		return;
	}
	$(".selectuser").empty();
	$.ajax({
		url : "/ret/unitget/getUserInfoBySearchuser",
		type : "post",
		dataType : "json",
		data : {
			searchuser : $("#searchuser").val()
		},
		success : function(data) {
			if (data.status == 200) {
				var userlist = data.list;
				if (userlist) {
					for (var i = 0; i < userlist.length; i++) {
						if (isExist(".selectuser", userlist[i].accountId)) {
							var headimg = "/gobal/img/org/U01.png";
							if (userlist[i].sex == "女") {
								headimg = "/gobal/img/org/U11.png"
							}
							$(".selectuser").append(
									"<div class=\"selectdiv\" onclick=\"doSelectUser(this,'"
											+ opt + "');\"  data-name=\""
											+ userlist[i].userName
											+ "\" data-value=\""
											+ userlist[i].accountId
											+ "\"><img src=\"" + headimg
											+ "\"><span>"
											+ userlist[i].userName
											+ "</span></div>");
						}
					}
				}
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}
function doSelectUser(Obj, opt) {
	if (opt == 'false') {
		if ($(".selecteduser div").length == 0) {
			if (isExist(".selecteduser", $(Obj).attr("data-value"))) {
				$(".selecteduser").append(
						"<div class=\"selecteddiv\" onclick=\"doUnSelectUser(this,'"
								+ opt + "');\"  data-name=\""
								+ $(Obj).attr("data-name") + "\" data-value=\""
								+ $(Obj).attr("data-value") + "\"><img src=\""
								+ $(Obj).find("img").attr("src") + "\"><span>"
								+ $(Obj).attr("data-name") + "</span></div>");
			}
			$(Obj).remove();
		} else {
			top.layer.msg("单选模式不能多选");
		}
	} else {
		if (isExist(".selecteduser", $(Obj).attr("data-value"))) {
			$(".selecteduser").append(
					"<div class=\"selecteddiv\" onclick=\"doUnSelectUser(this,'"
							+ opt + "');\"  data-name=\""
							+ $(Obj).attr("data-name") + "\" data-value=\""
							+ $(Obj).attr("data-value") + "\"><img src=\""
							+ $(Obj).find("img").attr("src") + "\"><span>"
							+ $(Obj).attr("data-name") + "</span></div>");
		}
		$(Obj).remove();
	}

}
function doUnSelectUser(Obj, opt) {
	if (isExist(".selectuser", $(Obj).attr("data-value"))) {
		$(".selectuser").append(
				"<div class=\"selectdiv\" onclick=\"doSelectUser(this,'" + opt
						+ "');\" data-name=\"" + $(Obj).attr("data-name")
						+ "\" data-value=\"" + $(Obj).attr("data-value")
						+ "\"><img src=\"" + $(Obj).find("img").attr("src")
						+ "\"><span>" + $(Obj).attr("data-name")
						+ "</span></div>");
	}
	$(Obj).remove();
}

function initUserSelect(accountIds, opt) {
	$("#orgselectdiv").html(mselect);
	if (accountIds) {
		$.ajax({
					url : "/ret/unitget/getUserNamesByAccountIds",
					type : "post",
					dataType : "json",
					data : {
						accountIds : accountIds
					},
					success : function(data) {
						if (data.status == 200) {
							var userlist = data.list;
							if (userlist) {
								for (var i = 0; i < userlist.length; i++) {
									if (isExist(".selecteduser",
											userlist[i].accountId)) {

										var headimg = "/gobal/img/org/U01.png";
										if (userlist[i].sex == "女") {
											headimg = "/gobal/img/org/U11.png"
										}
										$(".selecteduser").append(
												"<div class=\"selecteddiv\" onclick=\"doUnSelectUser(this,'"
														+ opt
														+ "');\"  data-name=\""
														+ userlist[i].userName
														+ "\" data-value=\""
														+ userlist[i].accountId
														+ "\"><img src=\""
														+ headimg + "\"><span>"
														+ userlist[i].userName
														+ "</span></div>");
									}
								}
							}
						} else if (data.status == 100) {
							console.log(data.msg);
						} else {
							console.log(data.msg);
						}
					}
				});
	}
}
function selectUserAll() {
	$(".selectuser div").each(function() {
		doSelectUser(this)
	})
}
function unSelectUserAll() {
	$(".selecteduser div").each(function() {
		doUnSelectUser(this)
	})
}

function isExist(c, a) {
	var strs = [];
	$(c + " div").each(function() {
		strs.push($(this).attr("data-value"));
	})
	if ($.inArray(a, strs) >= 0) {
		return false;
	} else {
		return true;
	}
}

/**
 * 用户部门选择
 * 
 * @param obj
 * @param opt
 *            为'true'为多选 'false' 为单选
 * @returns
 */
function selectDept(obj, opt) {
	var u = $(obj).attr("opt-id");
	var deptIds = $("#" + u).attr("data-value");
	initDeptSelect(deptIds, opt);
	$(".selectdept").empty();
	$(".selecteddept").empty();
	var orgSetig = {
		async : {
			enable : true,// 设置 zTree 是否开启异步加载模式
			url : "/ret/unitget/getUnitDeptTree",// Ajax 获取数据的 URL 地址
			autoParam : [ "deptId" ],// 异步加载时需要自动提交父节点属性的参数
		},
		callback : {
			onClick : toselectdeptclick
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
	var topNode = [ {
		deptName : orgName.substring(0, 8),
		orgLeaveId : '',
		isParent : "true",
		deptId : "0",
		icon : "/gobal/img/org/org.png"
	} ];
	var zTree = $.fn.zTree.init($("#selectdepttree"), orgSetig, topNode);// 初始化树节点时，添加同步获取的数据
	var nodes = zTree.getNodes();
	for (var i = 0; i < nodes.length; i++) {
		zTree.expandNode(nodes[i], true, false, false);// 默认展开第一级节点
	}
	$('#deptselect').modal('show');
	$('.js-selectdeptall').unbind("click").click(function() {
		if (opt == 'true') {
			selectDeptAll();
		} else {
			top.layer.msg("单选模式不能全选");
		}
	});
	$('.js-unselectdeptall').unbind("click").click(function() {
		unSelectDeptAll();
	});
	$(".selectokbtn").unbind("click").click(function() {
		var deptStr = [];
		var deptIdStr = [];
		$(".selecteddept div").each(function() {
			deptStr.push($(this).attr("data-name"));
			deptIdStr.push($(this).attr("data-value"));
		});
		$("#" + u).val(deptStr.join(","));
		$("#" + u).attr("data-value", deptIdStr.join(","));
		$('#deptselect').modal('hide');
	});
	$(".js-searchdept").unbind("click").click(function() {
		searchdept();
	});
	$(".js-selectalldept").unbind("click").click(function() {
		$("#" + u).val("全体部门");
		$("#" + u).attr("data-value", "@all");
		$('#deptselect').modal('hide');
	});

	function toselectdeptclick(event, treeId, treeNode) {
		$(".selectdept").empty();
		if (isExist(".selecteddept", treeNode.deptId)) {
			$(".selectdept").append(
					"<div class=\"selectdiv\" onclick=\"doSelectDept(this,'"
							+ opt + "');\"  data-name=\"" + treeNode.deptName
							+ "\" data-value=\"" + treeNode.deptId
							+ "\"><img src=\"" + treeNode.icon + "\"><span>"
							+ treeNode.deptName + "</span></div>");
		}
		$.ajax({
			url : "/ret/unitget/getDeptList",
			type : "post",
			dataType : "json",
			data : {
				orgLeaveId : treeNode.deptId
			},
			success : function(data) {
				if (data.status == 200) {
					var deptlist = data.list;
					for (var i = 0; i < deptlist.length; i++) {
						if (isExist(".selecteddept", deptlist[i].deptId)) {
							$(".selectdept").append(
									"<div class=\"selectdiv\" onclick=\"doSelectDept(this,'"
											+ opt + "');\"  data-name=\""
											+ deptlist[i].deptName
											+ "\" data-value=\""
											+ deptlist[i].deptId
											+ "\"><img src=\"" + treeNode.icon
											+ "\"><span>"
											+ deptlist[i].deptName
											+ "</span></div>");

						}
					}
				} else if (data.status == 100) {
					top.layer.msg(data.msg);
				} else {
					console.log(data.msg);
				}
			}
		});
	}

}
function selectDeptAll() {
	$(".selectdept div").each(function() {
		doSelectDept(this)
	})
}
function unSelectDeptAll() {
	$(".selecteddept div").each(function() {
		doUnSelectDept(this)
	})
}

function doUnSelectDept(Obj, opt) {
	if (isExist(".selectdept", $(Obj).attr("data-value"))) {
		$(".selectdept").append(
				"<div class=\"selectdiv\" onclick=\"doSelectDept(this,'" + opt
						+ "');\" data-name=\"" + $(Obj).attr("data-name")
						+ "\" data-value=\"" + $(Obj).attr("data-value")
						+ "\"><img src=\"" + $(Obj).find("img").attr("src")
						+ "\"><span>" + $(Obj).attr("data-name")
						+ "</span></div>");
	}
	$(Obj).remove();
}

function doSelectDept(Obj, opt) {
	if (opt == 'false') {
		if ($(".selecteddept div").length == 0) {
			if (isExist(".selecteddept", $(Obj).attr("data-value"))) {
				$(".selecteddept").append(
						"<div class=\"selecteddiv\" onclick=\"doUnSelectDept(this,'"
								+ opt + "');\"  data-name=\""
								+ $(Obj).attr("data-name") + "\" data-value=\""
								+ $(Obj).attr("data-value") + "\"><img src=\""
								+ $(Obj).find("img").attr("src") + "\"><span>"
								+ $(Obj).attr("data-name") + "</span></div>");
			}
			$(Obj).remove();
		} else {
			top.layer.msg("单选模式不能多选");
		}
	} else {
		if (isExist(".selecteddept", $(Obj).attr("data-value"))) {
			$(".selecteddept").append(
					"<div class=\"selecteddiv\" onclick=\"doUnSelectDept(this,'"
							+ opt + "');\"  data-name=\""
							+ $(Obj).attr("data-name") + "\" data-value=\""
							+ $(Obj).attr("data-value") + "\"><img src=\""
							+ $(Obj).find("img").attr("src") + "\"><span>"
							+ $(Obj).attr("data-name") + "</span></div>");
		}
		$(Obj).remove();
	}

}

function initDeptSelect(deptIds, opt) {
	$("#orgselectdiv").html(dselect);
	if (deptIds) {
		$
				.ajax({
					url : "/ret/unitget/getUnitDeptByDeptIds",
					type : "post",
					dataType : "json",
					data : {
						deptIds : deptIds
					},
					success : function(data) {
						if (data.status == 200) {
							var deptlist = data.list;
							if (deptlist) {
								for (var i = 0; i < deptlist.length; i++) {
									if (isExist(".selecteddept",
											deptlist[i].deptId)) {
										$(".selecteddept")
												.append(
														"<div class=\"selecteddiv\" onclick=\"doUnSelectDept(this,'"
																+ opt
																+ "');\"  data-name=\""
																+ deptlist[i].deptName
																+ "\" data-value=\""
																+ deptlist[i].deptId
																+ "\"><img src=\"/gobal/img/org/dept.png\"><span>"
																+ deptlist[i].deptName
																+ "</span></div>");
									}
								}
							}
						} else if (data.status == 100) {
							// top.layer.msg(data.msg);
						} else {
							console.log(data.msg);
						}
					}
				});
	}
	if (opt != "true") {
		$(".js-selectalldept").remove();
	}
}

function searchdept(obj, opt) {
	if($("#searchdept").val()=="")
	{
		top.layer.msg("搜索项不能为空！");
		return;
	}
	$(".selectdept").empty();
	$
			.ajax({
				url : "/ret/unitget/getUnitDeptBySearchdept",
				type : "post",
				dataType : "json",
				data : {
					searchdept : $("#searchdept").val()
				},
				success : function(data) {
					if (data.status == 200) {
						var deptlist = data.list;
						if (deptlist) {
							for (var i = 0; i < deptlist.length; i++) {
								if (isExist(".selecteddept", deptlist[i].deptId)) {
									$(".selectdept")
											.append(
													"<div class=\"selecteddiv\" onclick=\"doSelectDept(this,'"
															+ opt
															+ "');\"  data-name=\""
															+ deptlist[i].deptName
															+ "\" data-value=\""
															+ deptlist[i].deptId
															+ "\"><img src=\"/gobal/img/org/dept.png\"><span>"
															+ deptlist[i].deptName
															+ "</span></div>");
								}
							}
						}
					} else if (data.status == 100) {
						top.layer.msg(data.msg);
					} else {
						console.log(data.msg);
					}
				}
			});
}

function selectUerPriv(obj, opt) {
	var u = $(obj).attr("opt-id");
	var privIds = $("#" + u).attr("data-value");
	initPrivSelect(privIds, opt);
	$(".selectpriv").empty();
	$(".selectprived").empty();
	$("#privselect").modal('show');

	$('.js-selectprivall').unbind("click").click(function() {
		if (opt == 'true') {
			selectPrivAll();
		} else {
			top.layer.msg("单选模式不能全选");
		}
	});
	$('.js-unselectprivall').unbind("click").click(function() {
		unSelectPrivAll();
	});
	$(".selectokbtn").unbind("click").click(function() {
		var privStr = [];
		var privIdStr = [];
		$(".selectedpriv div").each(function() {
			privStr.push($(this).attr("data-name"));
			privIdStr.push($(this).attr("data-value"))
		});
		$("#" + u).val(privStr.join(",")).change();
		$("#" + u).attr("data-value", privIdStr.join(","));
		$('#privselect').modal('hide');
	});

//	$(".js-selectallPriv").unbind("click").click(function() {
//		$("#" + u).val("所有权限");
//		$("#" + u).attr("data-value", "@all");
//		$('#privselect').modal('hide');
//	});
//	if (opt != 'true') {
//		$(".js-selectallPriv").remove();
//	}
}
function selectPrivAll() {
	$(".selectpriv div").each(function() {
		doSelectPriv(this)
	})
}
function unSelectPrivAll() {
	$(".selectedpriv div").each(function() {
		doUnSelectPriv(this)
	})
}

function initPrivSelect(privIds, opt) {
	$("#orgselectdiv").html(pselect);
	$.ajax({
		url : "/ret/unitget/getSelectPrivList",
		type : "post",
		dataType : "json",
		data : {
			privIds : privIds
		},
		success : function(data) {
			if (data.status == 200) {
				var plist = data.list;
				for (var i = 0; i < plist.length; i++) {
					$(".selectpriv").append(
							"<div class=\"selecteddiv\" onclick=\"doSelectPriv(this,'"
									+ opt + "');\"  data-name=\""
									+ plist[i].userPrivName
									+ "\" data-value=\"" + plist[i].userPrivId
									+ "\"><span>" + plist[i].userPrivName
									+ "</span></div>");
				}
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});

	$.ajax({
		url : "/ret/unitget/getUserPrivNamesByIds",
		type : "post",
		dataType : "json",
		data : {
			userPrivIds : privIds
		},
		success : function(data) {
			if (data.status == 200) {
				var plist = data.list;
				for (var i = 0; i < plist.length; i++) {
					$(".selectedpriv").append(
							"<div class=\"selectdiv\" onclick=\"doUnSelectPriv(this,'"
									+ opt + "');\"  data-name=\""
									+ plist[i].userPrivName
									+ "\" data-value=\"" + plist[i].userPrivId
									+ "\"><span>" + plist[i].userPrivName
									+ "</span></div>");
				}
			} else if (data.status == 100) {
				// top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function doSelectPriv(Obj, opt) {
	if (opt == 'false') {
		if ($(".selectedpriv div").length == 0) {
			if (isExist(".selectedpriv", $(Obj).attr("data-value"))) {
				$(".selectedpriv").append(
						"<div class=\"selecteddiv\" onclick=\"doUnSelectPriv(this,'"
								+ opt + "');\"  data-name=\""
								+ $(Obj).attr("data-name") + "\" data-value=\""
								+ $(Obj).attr("data-value") + "\"><span>"
								+ $(Obj).attr("data-name") + "</span></div>");
			}
			$(Obj).remove();
		} else {
			top.layer.msg("单选模式不能多选");
		}
	} else {
		if (isExist(".selectedpriv", $(Obj).attr("data-value"))) {
			$(".selectedpriv").append(
					"<div class=\"selecteddiv\" onclick=\"doUnSelectPriv(this,'"
							+ opt + "');\"  data-name=\""
							+ $(Obj).attr("data-name") + "\" data-value=\""
							+ $(Obj).attr("data-value") + "\"><span>"
							+ $(Obj).attr("data-name") + "</span></div>");
		}
		$(Obj).remove();
	}
}
function doUnSelectPriv(Obj, opt) {
	if (isExist(".selectdpriv", $(Obj).attr("data-value"))) {
		$(".selectpriv").append(
				"<div class=\"selectdiv\" onclick=\"doSelectpriv(this,'" + opt
						+ "');\" data-name=\"" + $(Obj).attr("data-name")
						+ "\" data-value=\"" + $(Obj).attr("data-value")
						+ "\"><span>" + $(Obj).attr("data-name")
						+ "</span></div>");
	}
	$(Obj).remove();
}

/**
 * 用户行政级别选择
 * 
 * @param obj
 * @param opt
 * @returns
 */
function selectUserLevel(obj, opt) {
	var u = $(obj).attr("opt-id");
	var levelIds = $("#" + u).attr("data-value");
	$("#orgselectdiv").html(lselect);
	$("#selectlevel").modal("show");
	initLevelSelect(levelIds, opt);
	$('.js-selectlevelall').unbind("click").click(function() {
		if (opt == 'true') {
			selectlevelAll();
		} else {
			top.layer.msg("单选模式不能全选");
		}
	});
	$('.js-unselectdeptall').unbind("click").click(function() {
		unselectlevelAll();
	});
	$(".selectokbtn").unbind("click").click(function() {
		var nameStr = [];
		var levelIdsStr = [];
		$(".selectedlevel div").each(function() {
			nameStr.push($(this).attr("data-name"));
			levelIdsStr.push($(this).attr("data-value"));
		});
		$("#" + u).val(nameStr.join(","));
		$("#" + u).attr("data-value", levelIdsStr.join(","));
		$('#selectlevel').modal('hide');
	});

	$(".js-selectalllevel").unbind("click").click(function() {
		$("#" + u).val("所有级别");
		$("#" + u).attr("data-value", "@all");
		$('#selectlevel').modal('hide');
	});
}
function selectlevelAll() {
	$(".selectlevel div").each(function() {
		doSelectLevel(this)
	})
}
function unselectlevelAll() {
	$(".selectedlevel div").each(function() {
		doUnSelectLevel(this)
	})
}
function initLevelSelect(levelIds, opt) {
	$.ajax({
		url : "/ret/unitget/getUserLevelList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == 200) {
				var levelList = data.list;
				if (levelList) {
					for (var i = 0; i < levelList.length; i++) {
						if (("," + levelIds + ",").indexOf((","
								+ levelList[i].levelId + ",")) < 0) {
							$(".selectlevel").append(
									"<div class=\"selectdiv\" onclick=\"doSelectLevel(this,'"
											+ opt + "');\"  data-name=\""
											+ levelList[i].levelName
											+ "\" data-value=\""
											+ levelList[i].levelId
											+ "\"><span>"
											+ levelList[i].levelName
											+ "</span></div>");
						} else {
							$(".selectedlevel").append(
									"<div class=\"selecteddiv\" onclick=\"doUnSelectLevel(this,'"
											+ opt + "');\"  data-name=\""
											+ levelList[i].levelName
											+ "\" data-value=\""
											+ levelList[i].levelId
											+ "\"><span>"
											+ levelList[i].levelName
											+ "</span></div>");
						}
					}
				}
			} else if (data.status == 100) {
				console.log(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}
function doSelectLevel(Obj, opt) {
	if (opt == 'false') {
		if ($(".selectedlevel div").length == 0) {
			if (isExist(".selectedlevel", $(Obj).attr("data-value"))) {
				$(".selectedlevel").append(
						"<div class=\"selecteddiv\" onclick=\"doUnSelectLevel(this,'"
								+ opt + "');\"  data-name=\""
								+ $(Obj).attr("data-name") + "\" data-value=\""
								+ $(Obj).attr("data-value") + "\"><span>"
								+ $(Obj).attr("data-name") + "</span></div>");
			}
			$(Obj).remove();
		} else {
			top.layer.msg("单选模式不能多选");
		}
	} else {
		if (isExist(".selectedlevel", $(Obj).attr("data-value"))) {
			$(".selectedlevel").append(
					"<div class=\"selecteddiv\" onclick=\"doUnSelectLevel(this,'"
							+ opt + "');\"  data-name=\""
							+ $(Obj).attr("data-name") + "\" data-value=\""
							+ $(Obj).attr("data-value") + "\"><span>"
							+ $(Obj).attr("data-name") + "</span></div>");
		}
		$(Obj).remove();
	}

}
function doUnSelectLevel(Obj, opt) {
	if (isExist(".selectlevel", $(Obj).attr("data-value"))) {
		$(".selectlevel").append(
				"<div class=\"selectdiv\" onclick=\"doSelectLevel(this,'" + opt
						+ "');\" data-name=\"" + $(Obj).attr("data-name")
						+ "\" data-value=\"" + $(Obj).attr("data-value")
						+ "\"><span>" + $(Obj).attr("data-name")
						+ "</span></div>");
	}
	$(Obj).remove();
}

var lselect = [
		' <div id="selectlevel" class="modal fade"  tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" style="display: none;">',
		'        <div class="modal-dialog">',
		'            <div class="modal-content" style="width: 450px;">',
		'                <div class="modal-header">',
		'                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>',
		'                    <h4 class="modal-title" id="myLargeModalLabel">行政级别</h4>',
		'                </div>',
		'                <div class="modal-body">',
		'                    <div class="row" style="margin: 0px">',
		'    			                 <div class="col-xs-6 col-md-6" style="padding-left:4px;padding-right:4px;">',
		'				                 <div class="flat radius-bordered">',
		'		                                <div class="widget-header bg-blueberry" style="text-align: center;padding-left:0px;">',
		'		                                    <span class="widget-caption" style="float:none;">备选级别</span>',
		'		                                    <a class="selectlevelall js-selectlevelall">全选</a>',
		'		                                </div>',
		'										<div class="widget-body-1 selectlevel">',
		'											',
		'		                                </div>',
		'		                           </div>',
		'				                 </div>',
		'				                 <div class="col-xs-6 col-md-6" style="padding-right: 2px;padding-left:10px;">',
		'				                 <div class="flat radius-bordered">',
		'		                                <div class="widget-header bg-palegreen" style="text-align: center;padding-left:0px;">',
		'		                                    <span class="widget-caption" style="float:none;">已选级别</span>',
		'		                                    <a class="selectlevelall js-unselectdeptall">反选</a>',
		'		                                </div>',
		'										<div class="widget-body-1 selectedlevel">',
		'										',
		'		                                </div>',
		'		                            </div>',
		'				                 </div>',
		'                    </div>',
		'                </div>',
		'                <div class="modal-footer">',
		'                	<a href="javascript:void(0);" class="btn btn-maroon js-selectalllevel">所有级别</a>',
		'                     <button type="button" class="btn btn-warning" data-dismiss="modal">取消</button>',
		'                     <button type="button" class="btn btn-primary selectokbtn">确定</button>',
		'                </div>', '            </div><!-- /.modal-content -->',
		'        </div><!-- /.modal-dialog -->', '    </div>' ].join("");

var dselect = [
		'<div id="deptselect" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">',
		'	        <div class="modal-dialog">',
		'	            <div class="modal-content">',
		'	                <div class="modal-header">',
		'	                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>',
		'	                    <h4 class="modal-title" id="myLargeModalLabel">部门选择</h4>',
		'	                </div>',
		'	                <div class="modal-body" style="padding: 0px;">',
		'	                 <div class="row" style="margin: 10px">',
		'	                 	<div class="col-xs-10 col-md-10"><input type="text" class="form-control" id="searchdept" placeholder="部门名称" ></div>',
		'	                 	<div class="col-xs-2 col-md-2"><a href="javascript:void(0);" class="btn btn-darkorange js-searchdept" style="width:100%">查询</a></div>',
		'	                 </div>',
		'		                <div class="row" style="margin: 0px">',
		'			                 <div class="col-xs-4 col-md-4" style="padding-left:2px;padding-right:10px;">',
		'			                  <div class="flat radius-bordered">',
		'	                                <div class="widget-header bg-lightred" style="text-align: center;padding-left:0px;">',
		'	                                    <span class="widget-caption" style="float:none;">部门列表</span>',
		'	                                </div>',
		'									<div class="widget-body-1">',
		'	                                    <ul id="selectdepttree" class="ztree"></ul>',
		'	                                </div>',
		'	                            </div>',
		'			                 </div>',
		'			                 <div class="col-xs-4 col-md-4" style="padding-left:4px;padding-right:4px;">',
		'			                 <div class="flat radius-bordered">',
		'	                                <div class="widget-header bg-blueberry" style="text-align: center;padding-left:0px;">',
		'	                                    <span class="widget-caption" style="float:none;">备选部门</span>',
		'	                                    <a class="selectuserall js-selectdeptall">全选</a>',
		'	                                </div>',
		'									<div class="widget-body-1 selectdept">',
		'										',
		'	                                </div>',
		'	                           </div>',
		'			                 </div>',
		'			                 <div class="col-xs-4 col-md-4" style="padding-right: 2px;padding-left:10px;">',
		'			                 <div class="flat radius-bordered">',
		'	                                <div class="widget-header bg-palegreen" style="text-align: center;padding-left:0px;">',
		'	                                    <span class="widget-caption" style="float:none;">已选部门</span>',
		'	                                    <a class="selectuserall js-unselectdeptall">反选</a>',
		'	                                </div>',
		'									<div class="widget-body-1 selecteddept">',
		'	                                </div>',
		'	                            </div>',
		'			                 </div>',
		'		                </div>',
		'	                </div>',
		'	                <div class="modal-footer">',
		'					<a href="javascript:void(0);" class="btn btn-maroon js-selectalldept">全体部门</a>',
		'	                   <button type="button" class="btn btn-warning" data-dismiss="modal">取消</button>',
		'	                  <button type="button" class="btn btn-primary selectokbtn" >确定</button>',
		'	               </div>',
		'	            </div><!-- /.modal-content -->',
		'	        </div><!-- /.modal-dialog -->', '	    </div>' ].join("");

var mselect = [
		'<div id="userselect" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">',
		'        <div class="modal-dialog">',
		'            <div class="modal-content">',
		'                <div class="modal-header">',
		'                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>',
		'                    <h4 class="modal-title" id="myLargeModalLabel">人员选择</h4>',
		'                </div>',
		'                <div class="modal-body" style="padding: 0px;">',
		'                 <div class="row" style="margin: 10px">',
		'                 	<div class="col-xs-10 col-md-10"><input type="text" class="form-control" id="searchuser" placeholder="姓名,账号" ></div>',
		'                 	<div class="col-xs-2 col-md-2"><a href="javascript:void(0);" class="btn btn-darkorange js-searchuser" style="width:100%">查询</a></div>',
		'                 </div>',
		'	                <div class="row" style="margin: 0px">',
		'		                 <div class="col-xs-4 col-md-4" style="padding-left:2px;padding-right:10px;">',
		'		                  <div class="flat radius-bordered">',
		'                                <div class="widget-header bg-lightred" style="text-align: center;padding-left:0px;">',
		'                                    <span class="widget-caption" style="float:none;"><a class="setype js-dept">部门</a><a class="setype js-level">角色</a><a class="setype js-group">分组</a></span>',
		'                                </div>',
		'								<div class="widget-body-1 js-part1">',
		'                                    <ul id="selectUsertree" class="ztree"></ul>',
		'                                </div>',
		'								<div class="widget-body-1 js-part2" style="display:none;" id="selectlevellist">',
		'                                </div>',
		'								<div class="widget-body-1 js-part3" style="display:none;" id="selectgrouplist">',
		'                                </div>',
		'                            </div>',
		'		                 </div>',
		'		                 <div class="col-xs-4 col-md-4" style="padding-left:4px;padding-right:4px;">',
		'		                 <div class="flat radius-bordered">',
		'                                <div class="widget-header bg-blueberry" style="text-align: center;padding-left:0px;">',
		'                                    <span class="widget-caption" style="float:none;">备选人员</span>',
		'                                    <a class="selectuserall js-selectuserall">全选</a>',
		'                                </div>',
		'								<div class="widget-body-1 selectuser">',
		'									',
		'                                </div>',
		'                            </div>',
		'		                 </div>',
		'		                 <div class="col-xs-4 col-md-4" style="padding-right: 2px;padding-left:10px;">',
		'		                 <div class="flat radius-bordered">',
		'                                <div class="widget-header bg-palegreen" style="text-align: center;padding-left:0px;">',
		'                                    <span class="widget-caption" style="float:none;">已选人员</span>',
		'                                    <a class="selectuserall js-unselectuserall">反选</a>',
		'                                </div>',
		'								<div class="widget-body-1 selecteduser">',
		'                                </div>',
		'                            </div>',
		'		                 ',
		'		                 </div>',
		'	                </div>',
		'                </div>',
		'                <div class="modal-footer">',
		'					<a href="javascript:void(0);" class="btn btn-maroon js-selectalluser">全体人员</a>',
		'                   <button type="button" class="btn btn-warning" data-dismiss="modal">取消</button>',
		'                  <button type="button" class="btn btn-primary selectokbtn" >确定</button>',
		'               </div>', '            </div><!-- /.modal-content -->',
		'        </div><!-- /.modal-dialog -->', '    </div>' ].join("");

var pselect = [
		'<div id="privselect" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">',
		'        <div class="modal-dialog">',
		'            <div class="modal-content" style="width: 450px;">',
		'                <div class="modal-header">',
		'                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>',
		'                    <h4 class="modal-title" id="myLargeModalLabel">权限选择</h4>',
		'                </div>',
		'                <div class="modal-body" style="padding: 0px;">',
		'                <div class="row" style="margin: 0px">',
		'                <div class="col-xs-6 col-md-6" style="padding-left:4px;padding-right:4px;">',
		'				     <div class="flat radius-bordered">',
		'	                      <div class="widget-header bg-blueberry" style="text-align: center;padding-left:0px;">',
		'		                       <span class="widget-caption" style="float:none;">备选权限</span>',
		'		                            <a class="selectuserall js-selectprivall">全选</a>',
		'	                       </div>',
		'							<div class="widget-body-1 selectpriv">									',
		'		                     </div>',
		'		                  </div>',
		'		             </div>',
		'		             <div class="col-xs-6 col-md-6" style="padding-left:4px;padding-right:4px;">',
		'				     <div class="flat radius-bordered">',
		'	                      <div class="widget-header bg-blueberry" style="text-align: center;padding-left:0px;">',
		'		                       <span class="widget-caption" style="float:none;">已选权限</span>',
		'		                            <a class="selectuserall js-unselectprivall">反选</a>',
		'	                       </div>',
		'							<div class="widget-body-1 selectedpriv">									',
		'		                     </div>',
		'		                  </div>',
		'		             </div>',
		'                </div>',
		'                </div>',
		'                <div class="modal-footer">',
//		'						<a href="javascript:void(0);" class="btn btn-maroon js-selectallPriv">所有权限</a>',
		'		                   <button type="button" class="btn btn-warning" data-dismiss="modal">取消</button>',
		'		                  <button type="button" class="btn btn-primary selectokbtn" >确定</button>',
		'		               </div>',
		'            </div><!-- /.modal-content -->',
		'        </div><!-- /.modal-dialog -->', '    </div>' ].join("");
