/**
 * 用户选择
 * 
 * @param obj
 * @param opt
 * 为'true'为多选 'false' 为单选
 * @returns
 */
function selectHrUser(obj, opt) {
	var u = $(obj).attr("opt-id");
	var userIds = $("#" + u).attr("data-value");
	initUserHrSelect(userIds, opt);
	$(".selectuser").empty();
	$(".selecteduser").empty();
	var orgSetig = {
		async : {
			enable : true,// 设置 zTree 是否开启异步加载模式
			url : "/ret/hrget/getHrDepartmentTree",// Ajax 获取数据的 URL 地址
			autoParam : ["deptId"],// 异步加载时需要自动提交父节点属性的参数
		},
		callback : {
			onClick : toselectHruserclick
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
	function toselectHruserclick(event, treeId, treeNode) {
		$(".selectuser").empty();
		$.ajax({
			url : "/ret/hrget/getHrUserInfoByDeptId",
			type : "post",
			dataType : "json",
			data : {
				deptId : treeNode.deptId
			},
			success : function(data) {
				if (data.status == 200) {
					var userlist = data.list;
					for (var i = 0; i < userlist.length; i++) {
						if (isExist(".selecteduser", userlist[i].userId)) {
							var headimg = "/gobal/img/org/U01.png";
							if (userlist[i].sex == "女") {
								headimg = "/gobal/img/org/U11.png"
							}
							$(".selectuser").append(
									"<div class=\"selectdiv\" onclick=\"doSelectHrUser(this,'"
											+ opt + "');\"  data-name=\""
											+ userlist[i].userName
											+ "\" data-value=\""
											+ userlist[i].userId
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
			selectHrUserAll();
		} else {
			top.layer.msg("单选模式不能全选");
		}
	});
	$('.js-unselectuserall').unbind("click").click(function() {
		unSelectHrUserAll();
	});
	$(".selectokbtn").unbind("click").click(function() {
		var nameStr = [];
		var userIdStr = [];
		$(".selecteduser div").each(function() {
			nameStr.push($(this).attr("data-name"));
			userIdStr.push($(this).attr("data-value"));
		});
		$("#" + u).val(nameStr.join(","));
		$("#" + u).attr("data-value", userIdStr.join(","));
		$('#userselect').modal('hide');
	});
	$(".js-searchuser").unbind("click").click(function() {
		searchHruser(obj, opt);
	});
	$(".js-selectalluser").unbind("click").click(function() {
		$("#" + u).val("全体人员");
		$("#" + u).attr("data-value", "@all");
		$('#userselect').modal('hide');
	});
}
function searchHruser(obj, opt) {
	$(".selectuser").empty();
	$.ajax({
		url : "/ret/hrget/getHrUserInfoBySearchuser",
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
						if (isExist(".selectuser", userlist[i].userId)) {
							var headimg = "/gobal/img/org/U01.png";
							if (userlist[i].sex == "女") {
								headimg = "/gobal/img/org/U11.png"
							}
							$(".selectuser").append(
									"<div class=\"selectdiv\" onclick=\"doSelectHrUser(this,'"
											+ opt + "');\"  data-name=\""
											+ userlist[i].userName
											+ "\" data-value=\""
											+ userlist[i].userId
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
function doSelectHrUser(Obj, opt) {
	if (opt == 'false') {
		if ($(".selecteduser div").length == 0) {
			if (isExist(".selecteduser", $(Obj).attr("data-value"))) {
				$(".selecteduser").append(
						"<div class=\"selecteddiv\" onclick=\"doUnSelectHrUser(this,'"
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
					"<div class=\"selecteddiv\" onclick=\"doUnSelectHrUser(this,'"
							+ opt + "');\"  data-name=\""
							+ $(Obj).attr("data-name") + "\" data-value=\""
							+ $(Obj).attr("data-value") + "\"><img src=\""
							+ $(Obj).find("img").attr("src") + "\"><span>"
							+ $(Obj).attr("data-name") + "</span></div>");
		}
		$(Obj).remove();
	}

}
function doUnSelectHrUser(Obj, opt) {
	if (isExist(".selectuser", $(Obj).attr("data-value"))) {
		$(".selectuser").append(
				"<div class=\"selectdiv\" onclick=\"doSelectHrUser(this,'" + opt
						+ "');\" data-name=\"" + $(Obj).attr("data-name")
						+ "\" data-value=\"" + $(Obj).attr("data-value")
						+ "\"><img src=\"" + $(Obj).find("img").attr("src")
						+ "\"><span>" + $(Obj).attr("data-name")
						+ "</span></div>");
	}
	$(Obj).remove();
}

function initUserHrSelect(userIds, opt) {
	$("#orgselectdiv").html(mhrselect);
	if (userIds) {
		$.ajax({
					url : "/ret/hrget/getUserNamesByUserIds",
					type : "post",
					dataType : "json",
					data : {
						userIds : userIds
					},
					success : function(data) {
						if (data.status == 200) {
							var userlist = data.list;
							if (userlist) {
								for (var i = 0; i < userlist.length; i++) {
									if (isExist(".selecteduser",
											userlist[i].userId)) {
										var headimg = "/gobal/img/org/U01.png";
										if (userlist[i].sex == "女") {
											headimg = "/gobal/img/org/U11.png"
										}
										$(".selecteduser").append(
												"<div class=\"selecteddiv\" onclick=\"doUnSelectHrUser(this,'"
														+ opt
														+ "');\"  data-name=\""
														+ userlist[i].userName
														+ "\" data-value=\""
														+ userlist[i].userId
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
	if (opt != 'true') {
		$(".js-selectalluser").remove();
	}
}
function selectHrUserAll() {
	$(".selectuser div").each(function() {
		doSelectHrUser(this)
	})
}
function unSelectHrUserAll() {
	$(".selecteduser div").each(function() {
		doUnSelectHrUser(this)
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
function selectHrDept(obj, opt) {
	var u = $(obj).attr("opt-id");
	var deptIds = $("#" + u).attr("data-value");
	initHrDeptSelect(deptIds, opt);
	$(".selectdept").empty();
	$(".selecteddept").empty();
	var orgSetig = {
		async : {
			enable : true,// 设置 zTree 是否开启异步加载模式
			url : "/ret/hrget/getHrDepartmentTree",// Ajax 获取数据的 URL 地址
			autoParam : [ "deptId" ],// 异步加载时需要自动提交父节点属性的参数
		},
		callback : {
			onClick : toselectHrdeptclick
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
			selectHrDeptAll();
		} else {
			top.layer.msg("单选模式不能全选");
		}
	});
	$('.js-unselectdeptall').unbind("click").click(function() {
		unSelectHrDeptAll();
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
		searchHrdept();
	});
	$(".js-selectalldept").unbind("click").click(function() {
		$("#" + u).val("全体部门");
		$("#" + u).attr("data-value", "@all");
		$('#deptselect').modal('hide');
	});

	function toselectHrdeptclick(event, treeId, treeNode) {
		$(".selectdept").empty();
		if (isExist(".selecteddept", treeNode.deptId)) {
			$(".selectdept").append(
					"<div class=\"selectdiv\" onclick=\"doSelectHrDept(this,'"
							+ opt + "');\"  data-name=\"" + treeNode.deptName
							+ "\" data-value=\"" + treeNode.deptId
							+ "\"><img src=\"" + treeNode.icon + "\"><span>"
							+ treeNode.deptName + "</span></div>");
		}
		$.ajax({
			url : "/ret/hrget/getHrDeptList",
			type : "post",
			dataType : "json",
			data : {
				orgLevelId : treeNode.deptId
			},
			success : function(data) {
				if (data.status == 200) {
					var deptlist = data.list;
					for (var i = 0; i < deptlist.length; i++) {
						if (isExist(".selecteddept", deptlist[i].deptId)) {
							$(".selectdept").append(
									"<div class=\"selectdiv\" onclick=\"doSelectHrDept(this,'"
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
function selectHrDeptAll() {
	$(".selectdept div").each(function() {
		doSelectHrDept(this)
	})
}
function unSelectHrDeptAll() {
	$(".selecteddept div").each(function() {
		doUnSelectHrDept(this)
	})
}

function doUnSelectHrDept(Obj, opt) {
	if (isExist(".selectdept", $(Obj).attr("data-value"))) {
		$(".selectdept").append(
				"<div class=\"selectdiv\" onclick=\"doSelectHrDept(this,'" + opt
						+ "');\" data-name=\"" + $(Obj).attr("data-name")
						+ "\" data-value=\"" + $(Obj).attr("data-value")
						+ "\"><img src=\"" + $(Obj).find("img").attr("src")
						+ "\"><span>" + $(Obj).attr("data-name")
						+ "</span></div>");
	}
	$(Obj).remove();
}

function doSelectHrDept(Obj, opt) {
	if (opt == 'false') {
		if ($(".selecteddept div").length == 0) {
			if (isExist(".selecteddept", $(Obj).attr("data-value"))) {
				$(".selecteddept").append(
						"<div class=\"selecteddiv\" onclick=\"doUnSelectHrDept(this,'"
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
					"<div class=\"selecteddiv\" onclick=\"doUnSelectHrDept(this,'"
							+ opt + "');\"  data-name=\""
							+ $(Obj).attr("data-name") + "\" data-value=\""
							+ $(Obj).attr("data-value") + "\"><img src=\""
							+ $(Obj).find("img").attr("src") + "\"><span>"
							+ $(Obj).attr("data-name") + "</span></div>");
		}
		$(Obj).remove();
	}

}

function initHrDeptSelect(deptIds, opt) {
	$("#orgselectdiv").html(dhrselect);
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
														"<div class=\"selecteddiv\" onclick=\"doUnSelectHrDept(this,'"
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

function searchHrdept(obj, opt) {
	$(".selectuser").empty();
	$.ajax({
				url : "/ret/hrget/getHrDeptBySearchdept",
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
													"<div class=\"selecteddiv\" onclick=\"doSelectHrDept(this,'"
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

function selectHrUerPriv(obj, opt) {
	var u = $(obj).attr("opt-id");
	var privIds = $("#" + u).attr("data-value");
	initHrPrivSelect(privIds, opt);
	$(".selectpriv").empty();
	$(".selectprived").empty();
	$("#privselect").modal('show');

	$('.js-selectprivall').unbind("click").click(function() {
		if (opt == 'true') {
			selectHrPrivAll();
		} else {
			top.layer.msg("单选模式不能全选");
		}
	});
	$('.js-unselectprivall').unbind("click").click(function() {
		unSelectHrPrivAll();
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
function selectHrPrivAll() {
	$(".selectpriv div").each(function() {
		doSelectHrPriv(this)
	})
}
function unSelectHrPrivAll() {
	$(".selectedpriv div").each(function() {
		doUnSelectHrPriv(this)
	})
}

function initHrPrivSelect(privIds, opt) {
	$("#orgselectdiv").html(phrselect);
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
							"<div class=\"selecteddiv\" onclick=\"doSelectHrPriv(this,'"
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
							"<div class=\"selectdiv\" onclick=\"doUnSelectHrPriv(this,'"
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

function doSelectHrPriv(Obj, opt) {
	if (opt == 'false') {
		if ($(".selectedpriv div").length == 0) {
			if (isExist(".selectedpriv", $(Obj).attr("data-value"))) {
				$(".selectedpriv").append(
						"<div class=\"selecteddiv\" onclick=\"doUnSelectHrPriv(this,'"
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
					"<div class=\"selecteddiv\" onclick=\"doUnSelectHrPriv(this,'"
							+ opt + "');\"  data-name=\""
							+ $(Obj).attr("data-name") + "\" data-value=\""
							+ $(Obj).attr("data-value") + "\"><span>"
							+ $(Obj).attr("data-name") + "</span></div>");
		}
		$(Obj).remove();
	}
}
function doUnSelectHrPriv(Obj, opt) {
	if (isExist(".selectdpriv", $(Obj).attr("data-value"))) {
		$(".selectpriv").append(
				"<div class=\"selectdiv\" onclick=\"doSelectHrpriv(this,'" + opt
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
function selectHrUserLeave(obj, opt) {
	var u = $(obj).attr("opt-id");
	var levelIds = $("#" + u).attr("data-value");
	$("#orgselectdiv").html(lhrselect);
	$("#selectleave").modal("show");
	initLeaveHrSelect(levelIds, opt);
	$('.js-selectleaveall').unbind("click").click(function() {
		if (opt == 'true') {
			selectHrLeaveAll();
		} else {
			top.layer.msg("单选模式不能全选");
		}
	});
	$('.js-unselectdeptall').unbind("click").click(function() {
		unSelectHrLeaveAll();
	});
	$(".selectokbtn").unbind("click").click(function() {
		var nameStr = [];
		var levelIdsStr = [];
		$(".selectedleave div").each(function() {
			nameStr.push($(this).attr("data-name"));
			levelIdsStr.push($(this).attr("data-value"));
		});
		$("#" + u).val(nameStr.join(","));
		$("#" + u).attr("data-value", levelIdsStr.join(","));
		$('#selectleave').modal('hide');
	});

	$(".js-selectallleave").unbind("click").click(function() {
		$("#" + u).val("所有级别");
		$("#" + u).attr("data-value", "@all");
		$('#selectleave').modal('hide');
	});
}
function selectHrLeaveAll() {
	$(".selectleave div").each(function() {
		doSelectHrLeave(this)
	})
}
function unSelectHrLeaveAll() {
	$(".selectedleave div").each(function() {
		doUnSelectHrLeave(this)
	})
}
function initLeaveHrSelect(levelIds, opt) {
	$.ajax({
		url : "/ret/hrget/getHrUserLevelList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == 200) {
				var levelList = data.list;
				if (levelList) {
					for (var i = 0; i < levelList.length; i++) {
						if (("," + levelIds + ",").indexOf((","
								+ levelList[i].levelId + ",")) < 0) {
							$(".selectleave").append(
									"<div class=\"selectdiv\" onclick=\"doSelectHrLeave(this,'"
											+ opt + "');\"  data-name=\""
											+ levelList[i].levelName
											+ "\" data-value=\""
											+ levelList[i].levelId
											+ "\"><span>"
											+ levelList[i].levelName
											+ "</span></div>");
						} else {
							$(".selectedleave").append(
									"<div class=\"selecteddiv\" onclick=\"doUnSelectHrLeave(this,'"
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
function doSelectHrLeave(Obj, opt) {
	if (opt == 'false') {
		if ($(".selectedleave div").length == 0) {
			if (isExist(".selectedleave", $(Obj).attr("data-value"))) {
				$(".selectedleave").append(
						"<div class=\"selecteddiv\" onclick=\"doUnSelectHrLeave(this,'"
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
		if (isExist(".selectedleave", $(Obj).attr("data-value"))) {
			$(".selectedleave").append(
					"<div class=\"selecteddiv\" onclick=\"doUnSelectHrLeave(this,'"
							+ opt + "');\"  data-name=\""
							+ $(Obj).attr("data-name") + "\" data-value=\""
							+ $(Obj).attr("data-value") + "\"><span>"
							+ $(Obj).attr("data-name") + "</span></div>");
		}
		$(Obj).remove();
	}

}
function doUnSelectHrLeave(Obj, opt) {
	if (isExist(".selectleave", $(Obj).attr("data-value"))) {
		$(".selectleave").append(
				"<div class=\"selectdiv\" onclick=\"doSelectHrLeave(this,'" + opt
						+ "');\" data-name=\"" + $(Obj).attr("data-name")
						+ "\" data-value=\"" + $(Obj).attr("data-value")
						+ "\"><span>" + $(Obj).attr("data-name")
						+ "</span></div>");
	}
	$(Obj).remove();
}

var lhrselect = [
		' <div id="selectleave" class="modal fade"  tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" style="display: none;">',
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
		'		                                    <a class="selectleaveall js-selectleaveall">全选</a>',
		'		                                </div>',
		'										<div class="widget-body-1 selectleave">',
		'											',
		'		                                </div>',
		'		                           </div>',
		'				                 </div>',
		'				                 <div class="col-xs-6 col-md-6" style="padding-right: 2px;padding-left:10px;">',
		'				                 <div class="flat radius-bordered">',
		'		                                <div class="widget-header bg-palegreen" style="text-align: center;padding-left:0px;">',
		'		                                    <span class="widget-caption" style="float:none;">已选级别</span>',
		'		                                    <a class="selectleaveall js-unselectdeptall">反选</a>',
		'		                                </div>',
		'										<div class="widget-body-1 selectedleave">',
		'										',
		'		                                </div>',
		'		                            </div>',
		'				                 </div>',
		'                    </div>',
		'                </div>',
		'                <div class="modal-footer">',
		'                	<a href="javascript:void(0);" class="btn btn-maroon js-selectallleave">所有级别</a>',
		'                     <button type="button" class="btn btn-warning" data-dismiss="modal">取消</button>',
		'                     <button type="button" class="btn btn-primary selectokbtn">确定</button>',
		'                </div>', '            </div><!-- /.modal-content -->',
		'        </div><!-- /.modal-dialog -->', '    </div>' ].join("");

var dhrselect = [
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

var mhrselect = [
		'<div id="userselect" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">',
		'        <div class="modal-dialog">',
		'            <div class="modal-content">',
		'                <div class="modal-header">',
		'                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>',
		'                    <h4 class="modal-title" id="myLargeModalLabel">人员选择</h4>',
		'                </div>',
		'                <div class="modal-body" style="padding: 0px;">',
		'                 <div class="row" style="margin: 10px">',
		'                 	<div class="col-xs-10 col-md-10"><input type="text" class="form-control" id="searchuser" placeholder="姓名,英文名" ></div>',
		'                 	<div class="col-xs-2 col-md-2"><a href="javascript:void(0);" class="btn btn-darkorange js-searchuser" style="width:100%">查询</a></div>',
		'                 </div>',
		'	                <div class="row" style="margin: 0px">',
		'		                 <div class="col-xs-4 col-md-4" style="padding-left:2px;padding-right:10px;">',
		'		                  <div class="flat radius-bordered">',
		'                                <div class="widget-header bg-lightred" style="text-align: center;padding-left:0px;">',
		'                                    <span class="widget-caption" style="float:none;">部门列表</span>',
		'                                </div>',
		'								<div class="widget-body-1">',
		'                                    <ul id="selectUsertree" class="ztree"></ul>',
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

var phrselect = [
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
