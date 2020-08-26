var designer;// 获取设计器
var startNode;
var endNode;
var prcsList;
var chlidTable = {};
var formDataOption="";
$(function() {
	designer = new Designer("notepad");
	repaint();
	$.ajax({
		url : "/ret/documentget/getDocumentFormFieldByFlowId",
		dataType : "json",
		type : "post",
		data : {flowId : flowId},
		async : false,
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				for (var i = 0; i < data.list.length; i++) {
					formDataOption+="<option value='"+ data.list[i].name + "'>"+data.list[i].title+"</option>";
					$("#writediv").append(
							"<div class='checkbox' style='display:inline-block;'><label><input class='colored-danger' name='writerField' type='checkbox' value='"
									+ data.list[i].name + "'><span class='text'>" + data.list[i].title + "</span></label></div>");
					$("#hideFielddiv").append(
							"<div class='checkbox' style='display:inline-block;'><label><input class='colored-danger' name='hideField' type='checkbox' value='" + data.list[i].name
									+ "'><span class='text'>" + data.list[i].title + "</span></label></div>");
					$("#mustFielddiv").append(
							"<div class='checkbox' style='display:inline-block;'><label><input class='colored-danger' name='mustField' type='checkbox' value='" + data.list[i].name
									+ "'><span class='text'>" + data.list[i].title + "</span></label></div>");
					$("#numFielddiv").append(
							"<div class='checkbox' style='display:inline-block;'><label><input class='colored-danger' name='numField' type='checkbox' value='" + data.list[i].name
									+ "'><span class='text'>" + data.list[i].title + "</span></label></div>");
					if (data.list[i].xType == "xlist") {
						$("#childTablePrivList").append(
								"<tr name='" + data.list[i].name+ "'><td align=\"center\">" + data.list[i].title + "</td>" +
								"<td align=\"center\">" + data.list[i].name+ "</td>" +
								"<td align=\"center\"><label><input type=\"checkbox\" id='child_add_" + data.list[i].name+ "'><span class=\"text\"></span></label></td>" +
								"<td align=\"center\"><label><input type=\"checkbox\" id='child_del_" + data.list[i].name+ "'><span class=\"text\"></span></label></td>" +
								"<td align=\"center\"><a data-value='" + data.list[i].name+ "' href=\"javascript:void(0);\" class=\"btn btn-sky btn-sm js-childtableset-btn\">设置</a></td></tr>");
						chlidTable[data.list[i].name] = data.list[i].model;
					}
				}
			}
		}
	});
	var h = $(window).height() - 40;
	$("#notepad").css("height", h + "px");
	$(".js-save").unbind("click").click(function() {
		updateBpmProcess();
	})

	$.ajax({
		url : "/ret/documentget/getDocumentFormFieldByFlowId",
		dataType : "json",
		type : "POST",
		async : false,
		data : {
			flowId : flowId
		},
		async : false,
		error : function(e) {
			alert(e.message);
		},
		success : function(data) {
			if (data.status == "200") {
				var items = data.list;
				var html = "<optgroup label=\"表单字段\">";
				for (var i = 0; i < items.length; i++) {
					html += "<option value='" + items[i].name + "'>" + items[i].title + "</option>";
				}
				html += "</optgroup>";
				html += "<optgroup label=\"流程实例信息\">";
				html += "<option value=\"[发起人姓名]\">[发起人姓名]</option>";
				html += "<option value=\"[发起人账号]\">[发起人账号]</option>";
				html += "<option value=\"[发起人部门]\">[发起人部门]</option>";
				html += "<option value=\"[发起人行政级别]\">[发起人行政级别]</option>";
				html += "<option value=\"[主办人会签意见]\">[主办人会签意见]</option>";
				//html += "<option value=\"[经办人会签意见]\">[经办人会签意见]</option>";
				//html += "<option value=\"[当前步骤序号]\">[当前步骤序号]</option>";
				html += "<option value=\"[当前步骤自增ID号]\">[当前步骤自增ID号]</option>";
				html += "<option value=\"[当前步骤名称]\">[当前步骤名称]</option>";
				html += "<option value=\"[当前主办人姓名]\">[当前主办人姓名]</option>";
				html += "<option value=\"[当前主办人账号]\">[当前主办人账号]</option>";
				html += "<option value=\"[当前主办人行政级别]\">[当前主办人行政级别]</option>";
				//html += "<option value=\"[当前主办人辅助行政级别]\">[当前主办人辅助行政级别]</option>";
				html += "<option value=\"[当前主办人部门]\">[当前主办人部门]</option>";
				html += "</optgroup>";
				html += "<optgroup label=\"流程变量\">";
				html += "</optgroup>";
				$("#fields").html(html);
				$("#childConditionFields").html(html);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
	$('#processInfo').on('hide.bs.modal', function() {
		window.location.reload();
	})
	$('#childTablePrivModal').on('hide.bs.modal', function() {
		$(".childTableModalBody").html("");
	})
	$(".js-print").unbind("click").click(function(){
		$("#flowdiv").jqprint();
	});
	getBpmPluginsListForProcess();
	
	$.ajax({
		url : "/ret/documentget/getAllDocumentFlowListByManage",
		dataType : "json",
		type : "post",
		success : function(data) {
			if(data.status=="200")
			{
				var html ="<option value=''>请选择</option>";
				for(var i=0;i<data.list.length;i++)
				{
					html+="<option value='"+data.list[i].flowId+"'>"+data.list[i].flowName+"</option>";
				}
				$("#childFlowId").html(html);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	})
	$("#childParentFields").html(formDataOption);
	$("#autoSendType").unbind("change").change(function(){
		if($("#autoSendType").val()=='2')
		{
			$("#autoSendTypeDiv").show();
		}else
		{
			$("#autoSendTypeDiv").hide();
		}
			
	})
});
function resize() {
	var h = $(window).height() - 40;
	$("#notepad").css("height", h + "px");
}

function repaint() {
	$("#notepad").html("");
	document.oncontextmenu = function() {
		return;
	}
	// 获取流程步骤
	$.ajax({
		url : "/ret/documentget/getDocumentProcess",
		dataType : "json",
		type : "post",
		async : false,
		data : {
			flowId : flowId
		},
		error : function(e) {
			console.log(e);
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				prcsList = data.list;
			}
			// 绘制步骤
			for (var i = 0; i < prcsList.length; i++) {
				var data = prcsList[i];
				var processId = data.processId;
				var x = data.x;
				var y = data.y;
				if (data.prcsType == 1) {
					// 开始节点
					startNode = designer.drawStart(x, y, data);
					// 设置开始节点菜单
					startNode.mousedown(startEvent);
				} else if (data.prcsType == 2) {// 结束节点
					endNode = designer.drawEnd(x, y, data);
				} else if (data.prcsType == 3) {// 普通节点
					var node = designer.drawSimpleNode(x, y, data);
					node.mousedown(simpleNodeEvent);
				} else if (data.prcsType == 6) {// 子流程节点
					var node = designer.drawChildNode(x, y, data);
					node.mousedown(childEvent);
				}else if(data.prcsType == 4)
				{
					var node = designer.drawAggregationNode(x, y, data);
					node.mousedown(gatherNodeEvent);
				}
				if (startNode) {
					startNode[0].onmouseup = function(e) {
						var e = window.event || e;
						if (e.button != 2 && e.button != 3) {
							saveLayout(this);
						}
					};
				}
				if (endNode) {
					endNode[0].onmouseup = function(e) {
						var e = window.event || e;
						if (e.button != 2 && e.button != 3) {
							saveLayout(this);
						}
					};
				}
				if (node) {
					node[0].onmouseup = function(e) {
						var e = window.event || e;
						if (e.button != 2 && e.button != 3) {
							saveLayout(this);
						}
					};
				}
			}
			// 绘制步骤
			for (var i = 0; i < prcsList.length; i++) {
				var data = prcsList[i];
				var processId = data.processId;
				if (data.nextPrcs!=null&&data.nextPrcs!=undefined) {
					var nexts = data.nextPrcs.split(",");
					var node = $("#" + processId);
					for (var j = 0; j < nexts.length; j++) {
						if (nexts[j]) {
							node.addNextNode($("#" + nexts[j]));
						}
					}
				}
			}
			designer.doLineTo();
		}
	});
	document.oncontextmenu = function (event) {
		 event.preventDefault();
		};
}
function otherChildNode(node) {
	var processId = node.context.id;
	$("#toOtherPrcsTitlePrcsName").html("("+node.context.textContent+")");
	var nextnodes = node.context.nextnodes;
	var html="";
	var prcsArr=[];
	for(var i=0;i<nextnodes.length;i++)
	{
		var nextPrcsId = $(nextnodes[i]).attr("id");
		prcsArr.push(nextPrcsId);
		var prcsName = prcsName=$("#"+nextPrcsId).text();
		html+="<li class=\"item\" data-id=\""+nextPrcsId+"\">"+prcsName+"</li>";
	}
	$(".right-box").html(html);
	$.ajax({
		url : '/ret/documentget/getAlternativePrcsList',
		dataType : "json",
		type : "POST",
		data : {
			flowId : flowId,
			processId:processId,
			processIds : prcsArr.join(",")
		},
		success : function(data) {
			if(data.status=="200")
			{
				var list = data.list;
				var html="";
				for(var i=0;i<list.length;i++)
				{
					html+="<li class=\"item\" data-id=\""+list[i].processId+"\">"+list[i].prcsName+"</li>";
				}
				$(".left-box").html(html);	
				$("#toOtherPrcsSet").modal("show");
				$('#selectTitle').initList({
					openDrag: true,
					openDblClick: true
				});
				$(".js-toOtherPrcsSetsave").unbind("click").click(function(){
					var nextprcsArr=[];
					$(".right-box").find("li").each(function(){
						nextprcsArr.push($(this).attr("data-id"));
					});
					$.ajax({
						url : "/set/documentset/updateDocumentProcess",
						dataType : "json",
						type : "POST",
						data : {
							flowId : flowId,
							processId : processId,
							nextPrcs : nextprcsArr.join(",")
						},
						success : function(data) {
							if (data.status == "500") {
								console.log(data.msg);
							} else if (data.status == "100") {
								top.layer.msg(data.msg);
							} else {
								top.layer.msg(data.msg);
								repaint();
							}
						}
					});
				});
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	});
}

/**
 * 添加子流程节点
 */
function addChildNode(node) {
	bootbox.prompt("请输入新建子流程步骤名称", function (result) {
        if (result ===null) {
        } else {
        	 if (result =="") {
                 top.layer.msg("子流程步骤名称不能为空!");
             }else
            {
        	var x = parseInt(node.position().left);
        	var y = parseInt(node.position().top + node.height() + 80);
        	var id = node.attr("id");
        	$.ajax({
        		url : "/set/documentset/insertDocumentProcess",
        		type : "POST",
        		dataType : "json",
        		data : {
        			flowId : flowId,
        			prcsType : "6",
        			parentId : id,
        			prcsName :result,
        			pointX : x,
        			pointY : y
        		},
        		success : function(data) {
        			repaint();
        		}
        	});
        }
        }
	});
}

function startEvent(event) {
	if (event.which != 3) {
		return;
	}
	$(this).popupEmbedMenu([ {
		text : '节点设置',
		title : '',
		func : setNodeinfo
	},
	{
		text : '指向其它',
		title : '',
		func : otherChildNode
	},
	{
		text : '添加普通节点',
		title : '',
		func : addSimpleNode
	},{
		text : '添加聚合节点',
		title : '',
		func : addGatherNode
	}, {
		text : '添加子流程节点',
		title : '',
		func : addChildNode
	} ]);
}
/*******************************************************************************
 * 节点基本信息设置
 */

function setChildNodeinfo(node) {
	var processId = node.attr("id");
	document.getElementById("form1").reset();
	$("#childMyLargeModalLabel").html("子流程基本信息设置&nbsp（" + node.context.textContent + "）&nbsp标识号："+processId);
	var html0="";
	for (var i = 0; i < prcsList.length; i++) {
		if (prcsList[i].prcsType != "1") {
			if (prcsList[i].processId != processId && prcsList[i].prcsType=='3') {
				html0+="<div class='radio'><label><input class='colored-danger' name='childNextPrcs' type='radio' value='" + prcsList[i].processId + "'><span class='text'>"
						+ prcsList[i].prcsName + "</span></label></div>";
			}
		}
	}
	$("#childNextprcsdiv").html(html0);
	$.ajax({
		url : "/ret/documentget/getDocumentChildProcessPrcs",
		dataType : "json",
		type : "post",
		data : {
			processId : processId
		},
		async : false,
		success : function(data) {
			if(data.status=="200")
			{
				if(data.list!=null)
					{
						for(var id in data.list)
						{
							if(id=="childNextPrcs")
							{
								$("input:radio[name='childNextPrcs'][value='" + data.list[id] + "']").attr("checked", "checked");
							}else if(id=="childEndOpt")
							{
								if(data.list.childEndOpt!="0")
								{
									$("#childNextdiv").hide();
								}
								$("input:radio[name='childEndOpt'][value='" + data.list[id] + "']").attr("checked", true);
							}else if(id=="childAutoSendUser")
							{
								$("#childAutoSendUser").attr("data-value", data.list[id]);
								$("#childAutoSendUser").val(getUserNameByStr(data.list[id]));
							}else if(id=="childFlowId")
							{
								$("#"+id).val(data.list[id]);
								getParentFlowField();
							}else if(id=="childDataMapping")
							{
								$("#childFieldList").empty();
								var dataMapping = JSON.parse(data.list.childDataMapping);
								for(var i=0;i<dataMapping.length;i++)
									{
									createDataMapping(dataMapping[i].parentField,dataMapping[i].childField);
									}
							}else if(id=="childAutoUserModel")
							{
								$("#childAutoUserModel").val(data.list["childAutoUserModel"]);
								createChilddefaultdiv();
								if(data.list["childAutoUserModel"]=="1")
									{
									$("#childDefaultdivUserV").attr("data-value", data.list.childSPrcsAuto);
									$("#childDefaultdivUserV").val(getUserNameByStr(data.list.childSPrcsAuto));
									}else if(data.list["childAutoUserModel"]=="4"||data.list["childAutoUserModel"]=="5" )
									{
										$("#childDefaultdivDeptV").attr("data-value", data.list.childSPrcsAuto);
										$("#childDefaultdivDeptV").val(getDeptNameByDeptIds(data.list.childSPrcsAuto));
									}else if(data.list["childAutoUserModel"]=="7")
									{
										$("#childDefaultdivFormDataV").val(data.list.childSPrcsAuto);
									}
							}else if (id == "childPrcsCondition") {
								createChildConditionDiv(node,data.list.childNextPrcs,data.list.childPrcsCondition);
							}else
							{
								$("#"+id).val(data.list[id])
							}
						}
					}else
						{
							$("#childNextdiv").hide();
						}
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
			
		}
	})
	$("#childNodeModal").modal("show");
	$(".js-child-save").unbind("click").click(function(){
		setChildFlow(processId);
	})
	$(".js-add-datamapp").unbind("click").click(function(){
		setDataMapping();
	})
	
	
}

function setNodeinfo(node) {
	document.getElementById("form").reset();
	var processId = node.attr("id");
	$("#mySmallModalLabel").html("步骤基本信息设置&nbsp（" + node.context.textContent + "） &nbsp 标识号："+processId);
	$("#nextprcsdiv").empty();
	var prcsListHtml="";
	for (var i = 0; i < prcsList.length; i++) {
		if(prcsList[i].prcsType!="1"&&prcsList[i].prcsType!="2")
		{
			$("#waitPrcsId").append("<option value='" + prcsList[i].processId + "'>"+prcsList[i].prcsName+"</option>");
		}
		if (prcsList[i].prcsType != "1") {
			if (prcsList[i].processId != processId) {
				$("#nextprcsdiv").append(
						"<div class='checkbox'><label><input class='colored-danger' name='nextPrcs' type='checkbox' value='" + prcsList[i].processId + "'><span class='text'>"
								+ prcsList[i].prcsName + "</span></label></div>");
			}
		}
	}
	$.ajax({
				url : "/ret/documentget/getDocumentProcessPrcs",
				dataType : "json",
				type : "post",
				data : {
					processId : processId
				},
				async : false,
				success : function(data) {
					console.log(data);
					if (data.status = "200") {
						for ( var name in data.list) {
							if (name == "changeTitle") {
								$("input:radio[name='changeTitle'][value='" + data.list[name] + "']").attr("checked", true);
							} else if (name == "follow") {
								$("input:radio[name='follow'][value='" + data.list[name] + "']").attr("checked", true);
							} else if (name == "approval") {
								$("input:radio[name='approval'][value='" + data.list[name] + "']").attr("checked", true);
							} else if (name == "approvalFlag") {
								$("input:radio[name='approvalFlag'][value='" + data.list[name] + "']").attr("checked", true);
							} else if (name == "gatherFlag") {
								$("input:radio[name='gatherFlag'][value='" + data.list[name] + "']").attr("checked", true);
							}else if (name == "passEndFlag") {
								console.log(data.list[name]);
								$("input:radio[name='passEndFlag'][value='" + data.list[name] + "']").attr("checked", true);
							} else if (name == "userLeaveFlag") {
								$("input:radio[name='userLeaveFlag'][value='" + data.list[name] + "']").attr("checked", true);
							} else if (name == "parentWait") {
								$("input:radio[name='parentWait'][value='" + data.list[name] + "']").attr("checked", true);
								if(data.list[name]=="1")
									{
									$("#waitPrcsDiv").show();
									$("#waitPrcsId").val(data.list.waitPrcsId);	
									}else
									{
										$("#waitPrcsDiv").hide();
									}
							}  else if (name == "goBack") {
								$("input:radio[name='goBack'][value='" + data.list[name] + "']").attr("checked", true);
							} else if (name == "opRule") {
								$("input:radio[name='opRule'][value='" + data.list[name] + "']").attr("checked", true);
								if(data.list[name]!='0')
								{
									$("#autouserdiv").hide();
									$("#defaultdivOtherUserV").attr("data-value", data.list.sPrcsAutoOther);
									$("#defaultdivOtherUserV").val(getUserNameByStr(data.list.sPrcsAutoOther));
									createOtherUserdefaultdiv();
								}else
								{
									$("#autouserdiv").show();
									$("#autoUserModel").val(data.list["autoUserModel"]);
									createdefaultdiv();
									if(data.list["autoUserModel"]=="1")
										{
										$("#defaultdivUserV").attr("data-value", data.list.sPrcsAuto);
										$("#defaultdivUserV").val(getUserNameByStr(data.list.sPrcsAuto));
										$("#defaultdivOtherUserV").attr("data-value", data.list.sPrcsAutoOther);
										$("#defaultdivOtherUserV").val(getUserNameByStr(data.list.sPrcsAutoOther));
										}else if(data.list["autoUserModel"]=="4"||data.list["autoUserModel"]=="5" )
										{
											$("#defaultdivDeptV").attr("data-value", data.list.sPrcsAuto);
											$("#defaultdivDeptV").val(getDeptNameByDeptIds(data.list.sPrcsAuto));
										}else if(data.list["autoUserModel"]=="7")
										{
											$("#defaultdivFormDataV").val(data.list.sPrcsAuto);
										}
								}
							} else if (name == "autoSendUser") {
								$("#autoSendUser").attr("data-value", data.list[name]);
								$("#autoSendUser").val(getUserNameByStr(data.list[name]));
							} else if (name == "writerField") {
								if (data.list[name]) {
									var valArr = data.list[name].split(",");
									for (var j = 0; j < valArr.length; j++) {
										$("input:checkbox[name='writerField'][value='" + valArr[j] + "']").attr("checked", "checked");
									}
								}
							} else if (name == "hideField") {
								if (data.list[name]) {
									var valArr = data.list[name].split(",");
									for (var j = 0; j < valArr.length; j++) {
										$("input:checkbox[name='hideField'][value='" + valArr[j] + "']").attr("checked", "checked");
									}
								}
							} else if (name == "numField") {
								if (data.list[name]) {
									var valArr = data.list[name].split(",");
									for (var j = 0; j < valArr.length; j++) {
										$("input:checkbox[name='numField'][value='" + valArr[j] + "']").attr("checked", "checked");
									}
								}
							} else if (name == "mustField") {
								if (data.list[name]) {
									var valArr = data.list[name].split(",");
									for (var j = 0; j < valArr.length; j++) {
										$("input:checkbox[name='mustField'][value='" + valArr[j] + "']").attr("checked", "checked");
									}
								}
							} else if (name == "publicFile") {
								$("input:radio[name='publicFile'][value='" + data.list[name] + "']").attr("checked", true);
							} else if (name == "nextPrcs") {
								if (data.list[name]) {
									var processIdArr = data.list[name].split(",");
									for (var j = 0; j < processIdArr.length; j++) {
										if (processIdArr[j] != "") {
											$("input:checkbox[name='nextPrcs'][value='" + processIdArr[j] + "']").attr("checked", "checked");
										}
									}
								}
							} else if (name == "addProcess") {
								$("input:radio[name='addProcess'][value='" + data.list[name] + "']").attr("checked", true);
							} else if (name == "concurrentFlag") {
								$("input:radio[name='concurrentFlag'][value='" + data.list[name] + "']").attr("checked", true);
							} else if (name == "prcsCondition") {
								createConditionDiv(node, data.list["nextPrcs"], data.list[name]);
							} else if (name == "childTablePriv") {
								$(".js-childtableset-btn")
										.unbind("click")
										.click(
												function() {
													var childTableName = $(this).attr("data-value");
													var modelJsonArr = JSON.parse(chlidTable[childTableName]);
													var html = "<div class='form-group'><div class='col-md-3'>子表标题</div><div class='col-md-3'>表字段</div><div class='col-md-2'>类型</div><div class='col-md-4' align='center'>权限</div></div>";
													for (var i = 0; i < modelJsonArr.length; i++) {
														html += "<div class='form-group' style='padding-top: 10px;' name='" + childTableName + "'><div class='col-md-3'>"
																+ modelJsonArr[i].childTitle + "</div><div class='col-md-3' name='childName' value-data='"
																+ modelJsonArr[i].childName + "'>" + modelJsonArr[i].childName + "</div><div class='col-md-2'>";
														if (modelJsonArr[i].childModel == "1") {
															html += "文本输入框";
														} else if (modelJsonArr[i].childModel == "2") {
															html += "下拉列表";
														} else if (modelJsonArr[i].childModel == "3") {
															html += "单选框";
														} else if (modelJsonArr[i].childModel == "4") {
															html += "多选框";
														} else if (modelJsonArr[i].childModel == "5") {
															html += "时间选择";
														}
														html += "</div><div class='col-md-4' align='center'><div class='radio' style='display:inline-block;'><label><input name='optpriv_"
																+ modelJsonArr[i].childName
																+ "' type='radio' value='0'><span class='text'>只读</span></label></div>"
																+ "<div class='radio' style='display:inline-block;'><label><input name='optpriv_"
																+ modelJsonArr[i].childName
																+ "' type='radio' value='1'><span class='text'>可写</span></label></div>"
																+ "<div class='radio' style='display:inline-block;'><label><input name='optpriv_"
																+ modelJsonArr[i].childName
																+ "' type='radio' value='2'><span class='text'>隐藏</span></label></div></div></div>";
													}
													$(".childTableModalBody").html(html);
													var paramjson = {};
													if (data.list["childTablePriv"]) {
														paramjson = JSON.parse(data.list["childTablePriv"]);
													}
													try {
														var setings = paramjson[childTableName]
														for (var i = 0; i < setings.length; i++) {
															$("input:radio[name='optpriv_" + setings[i].childName + "'][value='" + setings[i].optpriv + "']").attr("checked", true);
														}
													} catch (e) {
													}
													$("#childTablePrivModal").modal("show");
													$(".js-childtableprivsave").unbind("click").click(function() {
														var jsonArr = [];
														$("div[name='" + childTableName + "'").each(function() {
															var json = {};
															json["optpriv"] = $(this).find('input:checked').val();
															json["childName"] = $(this).find("div[name='childName']").attr("value-data");
															jsonArr.push(json);
														});
														paramjson[childTableName] = jsonArr;
														$.ajax({
															url : "/set/documentset/updateBpmProcess",
															dataType : "json",
															type : "POST",
															data : {
																flowId : flowId,
																processId : $("#processId").val(),
																childTablePriv : JSON.stringify(paramjson)
															},
															success : function(data) {
																if (data.status == "500") {
																	console.log(data.msg);
																} else if (data.status == "100") {
																	top.layer.msg(data.msg);
																} else {
																	window.location.reload();
																}
															}
														});
													});
												});
							} else if (name == "userPriv") {
								$("#userPriv").attr("data-value", data.list[name]);
								$("#userPriv").val(getUserNameByStr(data.list[name]));
							} else if (name == "deptPriv") {
								$("#deptPriv").attr("data-value", data.list[name]);
								$("#deptPriv").val(getDeptNameByDeptIds(data.list[name]));
							} else if (name == "leavePriv") {
								$("#leavePriv").attr("data-value", data.list[name]);
								$("#leavePriv").val(getUserLevelStr(data.list[name]));
							}else if (name == "childTableRowPriv") {
								var childTableRowPriv= JSON.parse(data.list[name]);
								if(childTableRowPriv!=null)
								{
									$("#childTablePrivList").find("tr").each(function(){
										var vname=$(this).attr("name");
										var json = childTableRowPriv[vname];
										if(json.addpriv=="1")
										{
											$("#child_add_"+vname).attr("checked",true);
										}
										if(json.delpriv=="1")
										{
											$("#child_del_"+vname).attr("checked",true);
										}
									});
								}
							}else if(name=="remindNextUser")
							{
								var remindNextUser = $.parseJSON(data.list[name]);
								for(vs in remindNextUser)
								{
									$("input:radio[name='remindNextUser_"+vs+"'][value='" + remindNextUser[vs] + "']").attr("checked", true);
								}
							}else if(name=="remindCreateUser")
							{
								var remindCreateUser = $.parseJSON(data.list[name]);
								for(vs in remindCreateUser)
								{
									$("input:radio[name='remindCreateUser_"+vs+"'][value='" + remindCreateUser[vs] + "']").attr("checked", true);
								}
							}else if(name=="remindParticipant")
							{
								var remindParticipant = $.parseJSON(data.list[name]);
								for(vs in remindParticipant)
								{
									$("input:radio[name='remindParticipant_"+vs+"'][value='" + remindParticipant[vs] + "']").attr("checked", true);
								}
							}else if(name=="autoSendType")
							{
								$("#" + name).val(data.list[name]);
								if(data.list[name]=='2')
								{
									$("#autoSendTypeDiv").show();
								}else
								{
									$("#autoSendTypeDiv").hide();
								}
							}else{
								if(name!='sPrcsAuto' && name!="autoUserModel"&& name!="waitPrcsId")
								{
									$("#" + name).val(data.list[name]);
								}
							}
						}
					} else {
						console.log(data.msg);
					}
				}
			});
	$('#processInfo').modal('show');
}
/*
 * 添加普通步骤
 */

function addSimpleNode(node) {
	bootbox.prompt("请输入新建步骤名称", function (result) {
        if (result ===null) {
        } else {
        	 if (result =="") {
                 top.layer.msg("步骤名称不能为空!");
             }else
            {
        	var x = parseInt(node.position().left);
        	var y = parseInt(node.position().top + node.height() + 80);
        	var id = node.attr("id");
        	$.ajax({
        		url : "/set/documentset/insertDocumentProcess",
        		type : "POST",
        		dataType : "json",
        		data : {
        			flowId : flowId,
        			prcsType : "3",
        			parentId : id,
        			prcsName :result,
        			pointX : x,
        			pointY : y
        		},
        		success : function(data) {
        			repaint();
        		}
        	});
        }
        }
	});
}
/**
 * 添加聚合节点
 * @param node
 * @returns
 */
function addGatherNode(node) {
	bootbox.prompt("请输入新建步骤名称", function (result) {
        if (result ===null) {
        } else {
        	 if (result =="") {
                 top.layer.msg("步骤名称不能为空!");
             }else
            {
        	var x = parseInt(node.position().left);
        	var y = parseInt(node.position().top + node.height() + 80);
        	var id = node.attr("id");
        	$.ajax({
        		url : "/set/documentset/insertDocumentProcess",
        		type : "POST",
        		dataType : "json",
        		data : {
        			flowId : flowId,
        			prcsType : "4",
        			parentId : id,
        			prcsName :result,
        			pointX : x,
        			pointY : y
        		},
        		success : function(data) {
        			repaint();
        		}
        	});
        }
        }
	});
}

function gatherNodeEvent(event) {
	if (event.which != 3) {
		return;
	} else {
		var menuItems = new Array();
		menuItems.push({
			text : '节点设置',
			title : '',
			func : setNodeinfo
		});
		menuItems.push({
			text : '添加普通节点',
			title : '',
			func : addSimpleNode
		});
		var patternNode = searchFirstPatternedNode($(this));
		histroy = new Array();
			menuItems.push({
				text : '添加子流程节点',
				title : '',
				func : addChildNode
			});
			menuItems.push({
				text : '指向其它',
				title : '',
				func : otherChildNode
			});
			menuItems.push({
				text : '指向结束',
				title : '',
				func : toEnd
			});
		menuItems.push({
			text : '删除节点',
			title : '',
			func : remove
		});
		$(this).popupEmbedMenu(menuItems);
	}
}

function simpleNodeEvent(event) {
	if (event.which != 3) {
		return;
	} else {
		var menuItems = new Array();
		menuItems.push({
			text : '节点设置',
			title : '',
			func : setNodeinfo
		});
		menuItems.push({
			text : '添加普通节点',
			title : '',
			func : addSimpleNode
		});
		menuItems.push({
			text : '添加聚合节点',
			title : '',
			func : addGatherNode
		});
		var patternNode = searchFirstPatternedNode($(this));
		histroy = new Array();
			menuItems.push({
				text : '添加子流程节点',
				title : '',
				func : addChildNode
			});
			menuItems.push({
				text : '指向其它',
				title : '',
				func : otherChildNode
			});
			menuItems.push({
				text : '指向结束',
				title : '',
				func : toEnd
			});
		menuItems.push({
			text : '删除节点',
			title : '',
			func : remove
		});
		$(this).popupEmbedMenu(menuItems);
	}
}

/**
 * 搜索第一个匹配里程碑节点（主要搜索聚合节点和并发节点）
 */
var histroy = new Array();
var loop = 0;
function searchFirstPatternedNode(node) {
	if (loop > 100) {
		loop = 0;
		return undefined;
	}
	for (var i = 0; i < histroy.length; i++) {
		if (histroy[i].attr("id") == node.attr("id")) {
			histroy = new Array();
			return undefined;
		}
	}
	var prevNodes = node.getPrevNodes();
	if (!prevNodes || prevNodes == null) {
		histroy = new Array();
		return undefined;
	} else if (prevNodes.length == 0) {
		histroy = new Array();
		return undefined;
	}
	for (var i = 0; i < prevNodes.length; i++) {
		var tmp = prevNodes[i];
		histroy.push(tmp);
		if (tmp.attr('aggregation') == '' || tmp.attr('parallel') == '') {
			histroy = new Array();
			return tmp;
		} else {
			histroy = new Array();
			loop++;
			return searchFirstPatternedNode(tmp);
		}
	}
}

function childEvent(event) {
	if (event.which != 3) {
		return;
	}
	var menuItems = new Array();
	menuItems.push({
		text : '节点设置',
		title : '',
		func : setChildNodeinfo
	});
//	menuItems.push({
//		text : '指向其它',
//		title : '',
//		func : otherChildNode
//	});
	menuItems.push({
		text : '指向结束',
		title : '',
		func : toEnd
	});
	menuItems.push({
		text : '删除节点',
		title : '',
		func : remove
	});
	$(this).popupEmbedMenu(menuItems);
}

/**
 * 删除节点
 */
function remove(node) {
	if (window.confirm("确定删除该节点吗？")) {
		var processId = node.attr("id");
		$.ajax({
			url : "/set/documentset/deleteDocumentProcess",
			dataType : "json",
			type : "POST",
			data : {
				flowId : flowId,
				processId : processId
			},
			success : function(data) {
				if (data.status == "500") {
					console.log(data.msg);
				} else if (data.status == "100") {
					top.layer.msg(data.msg);
				} else {
					top.layer.msg(data.msg);
					window.location.reload();
				}
			}
		});
	} else {
		return;
	}
}

/**
 * 指向结束节点
 */
function toEnd(node) {
	var processId = node.attr("id");
	$.ajax({
		url : "/set/documentset/toEndProcess",
		dataType : "text",
		data : {
			flowId : flowId,
			processId : processId
		},
		type : "POST",
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				node.addNextNode(endNode);
				designer.doLineTo();
				repaint();
			}

		}
	});
}

// 保存布局
function saveLayout() {
	var nodes = $("#notepad [node='']");
	var layoutArr = [];
	nodes.each(function(i, obj) {
		var json = {};
		json.processId = $(this).attr("id");
		json.pointX = $(this).position().left;
		json.pointY = $(this).position().top;
		json.flowId = flowId;
		layoutArr.push(json);
	});
	$.ajax({
		url : "/set/documentset/saveDocumentProcessLayout",
		dataType : "json",
		type : "post",
		contentType : 'application/json',
		data : JSON.stringify(layoutArr),
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				prcsList = data.list;
			}
		}
	});
}
function updateBpmProcess() {
	var childTableRowPriv={};
	$("#childTablePrivList").find("tr").each(function(){
		var json={};
		var name=$(this).attr("name");
		if($("#child_add_"+name).is(':checked'))
		{
			json.addpriv="1";
		}else
		{
			json.addpriv="0";
		}
		if($("#child_del_"+name).is(':checked'))
		{
			json.delpriv="1";
		}else
		{
			json.delpriv="0";
		}
		childTableRowPriv[name]=json;
	});
	var defaulev = getDefaultValue();
	var nextPrcsArr = [];
	var nowProcessId = $("#processId").val();
	for (var i = 0; i < prcsList.length; i++) {
		if (prcsList[i].processId == nowProcessId) {
			if (prcsList[i].nextPrcs) {
				nextPrcsArr = prcsList[i].nextPrcs.split(",");
			}
			break;
		}
	}
	$.ajax({
		url : "/set/documentset/updateDocumentProcess",
		dataType : "json",
		type : "POST",
		async : false,
		data : {
			flowId : flowId,
			processId : $("#processId").val(),
			sortNo : $("#sortNo").val(),
			prcsName : $("#prcsName").val(),
			prcsType : $("#prcsType").val(),
			userLeaveFlag : $('input:radio[name="userLeaveFlag"]:checked').val(),
			parentWait:$('input:radio[name="parentWait"]:checked').val(),
			passEndFlag : $('input:radio[name="passEndFlag"]:checked').val(),
			waitPrcsId:$("#waitPrcsId").val(),
			changeTitle : $('input:radio[name="changeTitle"]:checked').val(),
			follow : $('input:radio[name="follow"]:checked').val(),
			approval : $('input:radio[name="approval"]:checked').val(),
			approvalFlag : $('input:radio[name="approvalFlag"]:checked').val(),
			gatherFlag : $('input:radio[name="gatherFlag"]:checked').val(),
			remark : $("#remark").val(),
			childTableRowPriv:JSON.stringify(childTableRowPriv),
			addProcess : $('input:radio[name="addProcess"]:checked').val(),
			concurrentFlag : $('input:radio[name="concurrentFlag"]:checked').val(),
			goBack : $('input:radio[name="goBack"]:checked').val(),
			passTime : $("#passTime").val(),
			publicFile : $('input:radio[name="publicFile"]:checked').val(),
			mustField : getCheckBoxValue("mustField"),
			hideField : getCheckBoxValue("hideField"),
			numField : getCheckBoxValue("numField"),
			writerField : getCheckBoxValue("writerField"),
			userPriv : $("#userPriv").attr("data-value"),
			deptPriv : $("#deptPriv").attr("data-value"),
			leavePriv : $("#leavePriv").attr("data-value"),
			opRule : $('input:radio[name="opRule"]:checked').val(),
			autoUserModel : defaulev.autoUserModel,
			sPrcsAuto : defaulev.defaultValue,
			sPrcsAutoOther : $("#defaultdivOtherUserV").attr("data-value"),
			prcsCondition : compositRequestData(nextPrcsArr),
			nextPrcs : getCheckBoxValue("nextPrcs"),
			autoSendType : $("#autoSendType").val(),
			autoSendUser : $("#autoSendUser").attr("data-value"),
			beforeClass : $("#beforeClass").val(),
			beforeParam : $("#beforeParam").val(),
			afterClass : $("#afterClass").val(),
			afterParam : $("#afterParam").val(),
			remindNextUser:getRemindNextUser(),
			remindCreateUser:getRemindCreateUser(),
			remindParticipant:getRemindParticipant()
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				top.layer.msg(data.msg);
				// window.location.reload();
			}
		}
	});
}

function createConditionDiv(node, processIds, prcsConditions) {
	$("#conditionList").empty();
	if (processIds) {
		var processIdArr = processIds.split(",");
		var html = "";
		for (var i = 0; i < processIdArr.length; i++) {
			for (var j = 0; j < prcsList.length; j++) {
				if (prcsList[j].processId == processIdArr[i]) {
					html += "<div class=\"col-sm-12\" style=\"margin-top: 5px;\">路径：[步骤]" + node.context.textContent
							+ "&nbsp;<i class=\"glyphicon glyphicon-arrow-right\"></i>&nbsp;[步骤]" + prcsList[j].prcsName
							+ "<a class=\"btn btn-darkorange btn-sm\" style=\"margin-left: 20px;\" onclick=\"addCondition('" + prcsList[j].processId + "');\">添加</a></div>"
							+ "<div class=\"col-sm-12\" style=\"margin-top: 5px;\" id=\"conditiondiv_" + prcsList[j].processId + "\"></div>"
							+ "<div class=\"col-sm-12\" style=\"margin-top: 5px;\"><textarea class=\"form-control\" id=\"conditionexp_" + prcsList[j].processId
							+ "\"></textarea></div>" + "</div>";
					break;
				}
			}
		}
		$("#conditionList").html(html);
	}
	if (prcsConditions) {
		var conditions = $.parseJSON(prcsConditions)
		for (var i = 0; i < conditions.length; i++) {
			var prcsTo = conditions[i].prcsTo;
			var condition = conditions[i].condition;
			var exp = conditions[i].exp;
			if (condition) {
				for (var j = 0; j < condition.length; j++) {
					var model = {};
					var order = getOrder($("#conditiondiv_" + prcsTo));
					model.name = condition[j].name;
					model.title = condition[j].title;
					model.value = condition[j].value;
					model.oper = condition[j].oper;
					$("#conditiondiv_" + prcsTo).append(
							"<div model='" + JSON.stringify(model) + "'><span>{" + (order + 1) + "}</span>'" + model.title + "'&nbsp" + model.oper + "&nbsp;'" + model.value
									+ "'<div class=\"widget-buttons\" onclick='removeConditionItem(this,\"" + processId
									+ "\");'><a href=\"#\" data-toggle=\"dispose\"><i class=\"fa fa-times darkorange\"></i></a></div></div>");
				}
			}
			$("#conditionexp_" + prcsTo).val(exp);
		}
	}
}

function addCondition(processId) {
	var field = $("#fields option:selected");
	var oper = $("#oper option:selected");
	var val = $("#value");
	var model = {};
	model.name = field.val();
	model.title = field.html();
	model.value = val.val();
	model.oper = oper.html();
	var order = getOrder($("#conditiondiv_" + processId));
	$("#conditiondiv_" + processId).append(
			"<div model='" + JSON.stringify(model) + "'><span>{" + (order + 1) + "}</span>'" + model.title + "'&nbsp" + model.oper + "&nbsp;'" + model.value
					+ "'<div class=\"widget-buttons\" onclick='removeConditionItem(this,\"" + processId
					+ "\");'><a href=\"#\" data-toggle=\"dispose\"><i class=\"fa fa-times darkorange\"></i></a></div></div>");
}
function addChildCondition(processId) {
	var field = $("#childConditionFields option:selected");
	var oper = $("#childOper option:selected");
	var val = $("#childValue");
	var model = {};
	model.name = field.val();
	model.title = field.html();
	model.value = val.val();
	model.oper = oper.html();
	var order = getOrder($("#childConditiondiv_" + processId));
	$("#childConditiondiv_" + processId).append(
			"<div model='" + JSON.stringify(model) + "'><span>{" + (order + 1) + "}</span>'" + model.title + "'&nbsp" + model.oper + "&nbsp;'" + model.value
					+ "'<div class=\"widget-buttons\" onclick='removeChildConditionItem(this,\"" + processId
					+ "\");'><a href=\"#\" data-toggle=\"dispose\"><i class=\"fa fa-times darkorange\"></i></a></div></div>");
}
function getOrder(container) {
	return $(container).children().length;
}

function removeConditionItem(cur, processId) {
	$(cur).parent().remove();
	recountOrder(processId);
}
function removeChildConditionItem(cur, processId) {
	$(cur).parent().remove();
	recountChildOrder(processId);
}

function recountChildOrder(processId) {
	$("#childConditiondiv_" + processId).children().each(function(i, obj) {
		$(obj).find("span:first").html("{" + (i + 1) + "}");
	});
}

function recountOrder(processId) {
	$("#conditiondiv_" + processId).children().each(function(i, obj) {
		$(obj).find("span:first").html("{" + (i + 1) + "}");
	});
}

function compositRequestData(nextPrcsArr) {
	var arr = new Array();
	for (var i = 0; i < nextPrcsArr.length; i++) {
		var item = {};
		item["prcsTo"] = nextPrcsArr[i];
		var conditionArr = new Array();
		var conditionItems = $("#conditiondiv_" + nextPrcsArr[i]).children();
		for (var j = 0; j < conditionItems.length; j++) {
			var obj = eval("(" + conditionItems[j].getAttribute("model") + ")");
			conditionArr.push(obj);
		}
		item["condition"] = conditionArr;
		item["exp"] = $("#conditionexp_" + nextPrcsArr[i]).val();
		arr.push(item);
	}
	return JSON.stringify(arr);
}
function compositChildRequestData(nextPrcsArr) {
	var arr = new Array();
	for (var i = 0; i < nextPrcsArr.length; i++) {
		var item = {};
		item["prcsTo"] = nextPrcsArr[i];
		var conditionArr = new Array();
		var conditionItems = $("#childConditiondiv_" + nextPrcsArr[i]).children();
		for (var j = 0; j < conditionItems.length; j++) {
			var obj = eval("(" + conditionItems[j].getAttribute("model") + ")");
			conditionArr.push(obj);
		}
		item["condition"] = conditionArr;
		item["exp"] = $("#childConditionexp_" + nextPrcsArr[i]).val();
		arr.push(item);
	}
	return JSON.stringify(arr);
}
function createdefaultdiv() {
	$(".js-defaultValue").each(function(){
		$(this).hide();
	});
	var model = $("#autoUserModel").val();
	if (model == "1") {
		$("#defaultdivUser").show();
		$("#defaultdivOtherUser").show();
	} else if (model == "4" || model == "5") {
		$("#defaultdivDept").show();
	} else if(model=="7"){
		$(".js-formdatefield").html(formDataOption);
		$("#defaultdivFormData").show();
	}else
	{
		//$("#defaultdiv").html("");
	}
}
function createChilddefaultdiv() {
	$(".js-childDefaultValue").each(function(){
		$(this).hide();
	});
	var model = $("#childAutoUserModel").val();
	if (model == "1") {
		$("#childDefaultdivUser").show();
	} else if (model == "4" || model == "5") {
		$("#childDefaultdivDept").show();
	} else if(model=="7"){
		$(".js-childFormdatefield").html(formDataOption);
		$("#childDefaultdivFormData").show();
	}else
	{
		//$("#defaultdiv").html("");
	}
}
function createOtherUserdefaultdiv()
{
	$(".js-defaultValue").each(function(){
		$(this).hide();
	});
	$("#defaultdivOtherUser").show();
}

function getDefaultValue()
{
	var json={};
	var opRunle = $('input:radio[name="opRule"]:checked').val();
	if(opRunle=='0')
	{
		autoUserModel = $("#autoUserModel").val();
		json.autoUserModel = autoUserModel;
		if(autoUserModel=='1')
		{
			json.defaultValue = $("#defaultdivUserV").attr("data-value");
		}else if(autoUserModel=='4'||autoUserModel=='5')
		{
			json.defaultValue = $("#defaultdivDeptV").attr("data-value");
		}else if(autoUserModel=='7')
		{
			json.defaultValue = $("#defaultdivFormDataV").val();
		}
	}
	return json;
}

function getChildDefaultValue()
{
	var json={};
		autoUserModel = $("#childAutoUserModel").val();
		json.autoUserModel = autoUserModel;
		if(autoUserModel=='1')
		{
			json.defaultValue = $("#childDefaultdivUserV").attr("data-value");
		}else if(autoUserModel=='4'||autoUserModel=='5')
		{
			json.defaultValue = $("#childDefaultdivDeptV").attr("data-value");
		}else if(autoUserModel=='7')
		{
			json.defaultValue = $("#childDefaultdivFormDataV").val();
		}
	return json;
}
function getBpmPluginsListForProcess()
{
	$.ajax({
		url : "/ret/documentget/getDocumentPluginsListForProcess",
		dataType : "json",
		type : "POST",
		async : false,
		success : function(data) {
			if(data.status=="200")
			{
				var html="<option value=\"\">请选择</option>";
				for(var i=0;i<data.list.length;i++)
				{
					html+="<option value=\""+data.list[i].pluginsId+"\">"+data.list[i].title+"</option>"
				}
				$("#beforeClass").html(html);
				$("#afterClass").html(html);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	});
}

function showAndHideChildNextPrcs()
{
	var v = $('input:radio[name="childEndOpt"]:checked').val();
	if(v=="0")
	{
		$("#childNextdiv").show();
	}else
	{
		$("#childNextdiv").hide();
	}
}

function setChildFlow(processId)
{
	var endOpt = $('input:radio[name="childEndOpt"]:checked').val();
	var nextPrcs =$('input:radio[name="childNextPrcs"]:checked').val();
	if(endOpt=="0")
		{
			if(nextPrcs==""||nextPrcs==null)
			{
				top.layer.msg("请选择子流程结时返回的步骤节点！");
				return false;
			}
		}else
		{
			nextPrcs="";
		}
	var nextPrcsArr=[]
	for (var i = 0; i < prcsList.length; i++) {
		if (prcsList[i].processId == processId) {
			if (prcsList[i].nextPrcs) {
				nextPrcsArr = prcsList[i].nextPrcs.split(",");
			}
			break;
		}
	}
	var defaulev = getChildDefaultValue();
	
	$.ajax({
		url : "/set/documentset/setDocumentChildProcess",
		dataType : "json",
		type : "POST",
		data:{
			processId:processId,
			sortNo:$("#childSortNo").val(),
			prcsName:$("#childPrcsName").val(),
			childFlowId:$("#childFlowId").val(),
			endOpt:endOpt,
			nextPrcs:nextPrcs,
			dataMapping:getDataMapping(),
			prcsCondition : compositChildRequestData(nextPrcsArr),
			opRule : 0,
			autoUserModel : defaulev.autoUserModel,
			sPrcsAuto : defaulev.defaultValue,
			autoSendType:$("#childAutoSendType").val(),
			autoSendUser:$("#childAutoSendUser").attr("data-value")
			},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				$("#childNodeModal").modal("hide");
				repaint();
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	});
}



function getParentFlowField()
{
	$.ajax({
		url : "/ret/documentget/getDocumentFormFieldByFlowId",
		dataType : "json",
		type : "post",
		data : {
			flowId : $("#childFlowId").val()
		},
		async : false,
		success : function(data) {
			if(data.status=="200")
			{
				var html="";
				for(var i=0;i<data.list.length;i++)
				{
					html+="<option value='"+ data.list[i].name + "'>"+data.list[i].title+"</option>"
				}
				$("#childFields").html(html);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	});
}

function createChildConditionDiv(node, processIds, prcsConditions) {
	$("#childConditionList").empty();
	if (processIds) {
		var processIdArr = processIds.split(",");
		var html = "";
		for (var i = 0; i < processIdArr.length; i++) {
			for (var j = 0; j < prcsList.length; j++) {
				if (prcsList[j].processId == processIdArr[i]) {
					html += "<div class=\"col-sm-12\" style=\"margin-top: 5px;\">路径：[步骤]" + node.context.textContent
							+ "&nbsp;<i class=\"glyphicon glyphicon-arrow-right\"></i>&nbsp;[步骤]" + prcsList[j].prcsName
							+ "<a class=\"btn btn-darkorange btn-sm\" style=\"margin-left: 20px;\" onclick=\"addChildCondition('" + prcsList[j].processId + "');\">添加</a></div>"
							+ "<div class=\"col-sm-12\" style=\"margin-top: 5px;\" id=\"childConditiondiv_" + prcsList[j].processId + "\"></div>"
							+ "<div class=\"col-sm-12\" style=\"margin-top: 5px;\"><textarea class=\"form-control\" id=\"childConditionexp_" + prcsList[j].processId
							+ "\"></textarea></div>" + "</div>";
					break;
				}
			}
		}
		$("#childConditionList").html(html);
	}
	if (prcsConditions) {
		var conditions = $.parseJSON(prcsConditions)
		for (var i = 0; i < conditions.length; i++) {
			var prcsTo = conditions[i].prcsTo;
			var condition = conditions[i].condition;
			var exp = conditions[i].exp;
			if (condition) {
				for (var j = 0; j < condition.length; j++) {
					var model = {};
					var order = getOrder($("#childconditiondiv_" + prcsTo));
					model.name = condition[j].name;
					model.title = condition[j].title;
					model.value = condition[j].value;
					model.oper = condition[j].oper;
					$("#childConditiondiv_" + prcsTo).append(
							"<div model='" + JSON.stringify(model) + "'><span>{" + (order + 1) + "}</span>'" + model.title + "'&nbsp" + model.oper + "&nbsp;'" + model.value
									+ "'<div class=\"widget-buttons\" onclick='removeConditionItem(this,\"" + processId
									+ "\");'><a href=\"#\" data-toggle=\"dispose\"><i class=\"fa fa-times darkorange\"></i></a></div></div>");
				}
			}
			$("#childConditionexp_" + prcsTo).val(exp);
		}
	}
}

function setDataMapping()
{
	var fromField = $("#childParentFields").val();
	var fromFieldTitle = $("#childParentFields").find("option:selected").text();
	var toField = $("#childFields").val();
	var toFieldTitle = $("#childFields").find("option:selected").text();
	var parentField={};
	parentField.field=fromField;
	parentField.title=fromFieldTitle;
	var childField={};
	childField.field=toField;
	childField.title=toFieldTitle;
	createDataMapping(parentField,childField);
}


function createDataMapping(parentField,childField)
{
	var html="<span class=\"tag label label-info js-datamapping\" parentfield=\""+parentField.field+"\" parenttitle=\""+parentField.title+"\" childfield=\""+childField.field+"\" childtitle=\""+childField.title+"\">"+parentField.title+"-->"+childField.title+"<span data-role=\"remove\" onclick=\"removeDataMapping(this);\"></span></span>"
	$("#childFieldList").append(html);
}

function removeDataMapping(Obj)
{
	$(Obj).parent("span").remove();
}
function getDataMapping()
{
	var dataMapp=[];
	$(".js-datamapping").each(function(){
		var json={};
		var parentField={};
		var childField={};
		parentField.field=$(this).attr("parentfield");
		parentField.title=$(this).attr("parenttitle");
		childField.field=$(this).attr("childfield");
		childField.title=$(this).attr("childtitle");
		json.parentField=parentField;
		json.childField=childField;
		dataMapp.push(json);
	})
	return  JSON.stringify(dataMapp);
}


function getRemindNextUser()
{
	var json={};
	json.webSms=$("input[name='remindNextUser_webSms']:checked").val();
	json.mobileSms=$("input[name='remindNextUser_mobileSms']:checked").val();
	json.appSms=$("input[name='remindNextUser_appSms']:checked").val();
	json.webMail=$("input[name='remindNextUser_webMail']:checked").val();
	json.ddSms=$("input[name='remindNextUser_ddSms']:checked").val();
	return  JSON.stringify(json);
}

function getRemindCreateUser()
{
	var json={};
	json.webSms=$("input[name='remindCreateUser_webSms']:checked").val();
	json.mobileSms=$("input[name='remindCreateUser_mobileSms']:checked").val();
	json.appSms=$("input[name='remindCreateUser_appSms']:checked").val();
	json.webMail=$("input[name='remindCreateUser_webMail']:checked").val();
	json.ddSms=$("input[name='remindCreateUser_ddSms']:checked").val();
	return  JSON.stringify(json);
}
function getRemindParticipant()
{
	var json={};
	json.webSms=$("input[name='remindParticipant_webSms']:checked").val();
	json.mobileSms=$("input[name='remindParticipant_mobileSms']:checked").val();
	json.appSms=$("input[name='remindParticipant_appSms']:checked").val();
	json.webMail=$("input[name='remindParticipant_webMail']:checked").val();
	json.ddSms=$("input[name='remindParticipant_ddSms']:checked").val();
	return  JSON.stringify(json);
}