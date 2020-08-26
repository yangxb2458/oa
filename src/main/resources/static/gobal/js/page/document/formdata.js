var writerField = "";
var documentProcess;
$(function() {
	doInitForm();
	$(".js-next-back").unbind("click").click(function() {
		$(".modal-backdrop").remove();
	});
	$(".js-next-runprcs").unbind("click").click(function() {
		var checkedPrcsArr = [];
		$(".js-prcscb").each(function() {
			if ($(this).is(':checked')) {
				var prcsType = $(this).attr("prcs_type");
				var json = {};
				json.prcsType=prcsType;
				json.processId = $(this).attr("data-value");
				checkedPrcsArr.push(json);
			}
		})
		var checkedPrcsNextInfo = [];
		if (checkedPrcsArr.length > 0) {
			for (var i = 0; i < checkedPrcsArr.length; i++) {
				var json = {};
				json.processId = checkedPrcsArr[i].processId;
				json.prcsType =  checkedPrcsArr[i].prcsType;
				if(checkedPrcsArr[i].prcsType!="2")
				{
					var opUser = $(".js-opUser_" + checkedPrcsArr[i].processId).find("div").attr("data-value");
					var opRule = $(".js-opUser_" + checkedPrcsArr[i].processId).attr("opRule");
					var otherOpArr = [];
					if (opRule == "0") {
						if (opUser == undefined) {
							top.layer.msg("请选择主办人!");
							return;
						}
						json.opUser = opUser;
						$(".js-otherOpUser_" + checkedPrcsArr[i].processId).find("div").each(function() {
							otherOpArr.push($(this).attr("data-value"));
						});
						json.otherOpUser = otherOpArr.join(",");
					} 
					else {
						if (opUser != "" && opUser != null) {
							otherOpArr.push(opUser);
						}
						$(".js-otherOpUser_" + checkedPrcsArr[i].processId).find("div").each(function() {
							otherOpArr.push($(this).attr("data-value"));
						});
						if(otherOpArr.length==0||otherOpArr==undefined)
							{
								top.layer.msg("至少需要一个主办或者经办人员!");
								return;
							}else
							{
								json.otherOpUser = otherOpArr.join(",");
							}
					}
					}
				checkedPrcsNextInfo.push(json);
			}
			goNext(checkedPrcsNextInfo);
		}else
			{
			top.layer.msg("请确认您需转交的步骤!");
			}
	});
});

function goNext(checkedPrcsNextInfo) {
	$.ajax({
		url : "/set/documentset/goNextProcess",
		type : "post",
		dataType : "json",
		data : {
			runId : runId,
			runProcessId : runProcessId,
			remindNextUser : getCheckBoxValue("remindNextUser"),
			remindCreateUser : getCheckBoxValue("remindCreateUser"),
			remindParticipant : getCheckBoxValue("remindParticipant"),
			msgContent : $("#msg-content").val(),
			autoSendUser:$("#sendToUser").attr("data-value"),
			nextPrcsInfo : JSON.stringify(checkedPrcsNextInfo)
		},
		success : function(data) {
			if (data.status == "200") {
				$("#goNextdiv").modal("hide");
				top.layer.msg(data.msg);
				open("/app/core/document/dispatchapproved","_self");
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

$("body").ready(function() {
	$(".js-savebtn").unbind("click").click(function() {
		var formdatajson = getFormDataParam();
		saveFormData(formdatajson);
		top.layer.msg("数据已保存");
	});
	// 转交下一步
});

function createOpUserSelectDiv(Obj) {
	var processId = $(Obj).attr("data-value");
	var prcsType = $(Obj).attr("prcs_type");
	if((prcsType=="2")&&($(Obj).is(':checked')))
	{
		$(".js-prcscb").each(function() {
			var temId = $(this).attr("data-value");
			if (temId != processId) {
			$(this).attr("checked", false);
			}
			$('#prcs_select_' + temId).hide();
		})
	}else
	{
	if (documentProcess.concurrentFlag == "0") {
		$(".js-prcscb").each(function() {
			var temId = $(this).attr("data-value");
				if (temId != processId) {
					$(this).attr("checked", false);
					$('#prcs_select_' + temId).hide();
				} else {
					$('#prcs_select_' + temId).show();
				}
		})
	} else if (documentProcess.concurrentFlag == "1") {
		var flag = true;
		$(".js-prcscb").each(function() {
		var temPrcsType = $(this).attr("prcs_type");
		if((temPrcsType=="2")&&($(this).is(':checked')))
		{
			flag = false;
			top.layer.msg("并发过程中不能包含结束事件!")
		}
		});
		if(flag)
		{
			if ($(Obj).is(':checked')) {
				$('#prcs_select_' + processId).show();
			} else {
				$('#prcs_select_' + processId).hide();
			}
		}else
		{
			$(Obj).attr("checked", false);
		}
	}
	}
}

/**
 * 生成表单HTML
 * 
 * @returns
 */
function doInitForm() {
	$.ajax({
		url : "/ret/documentget/getProcessInfo",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			runId : runId,
			runProcessId : runProcessId
		},
		success : function(data) {
			if (data.status == "200") {
				documentProcess = data.list.documentProcess;
				documentRunProcess = data.list.documentRunProcess;
				writerField = data.list.documentProcess.writerField;
				if(data.list.documentProcess.changeTitle=="1")
				{
				$("#flowTitle").unbind("click").click(function(){
					$("#documentFlowTitle").val($(this).html());
					changeDocumentTitle();
				});
				}
				if(data.list.documentProcess.approvalFlag=="0")
				{
					$("#ideaDiv").remove();
				}else 
				{
					$("#ideaText").summernote({
						height : 150
					});
				}
				$("#flowTitle").html(data.list.documentList.flowTitle);
				$("#urgency").val(data.list.documentList.urgency);
				$("#prcsName").html("当前步骤：" + data.list.documentProcess.prcsName);
				$(".js-saveandnextbtn").attr("data-value-process-id", data.list.documentProcess.processId);
				// $(".js-saveandnextbtn").attr("data-value-next-prcs", data.list.documentProcess.nextPrcs);
				//$("#formData").html(data.list.documentFormHtml);
				/** ------------主办经办权限处理--------------------* */
				doBtnCreate(documentProcess.opRule, documentRunProcess.opFlag,documentProcess.approvalFlag);
				/** ------------审批意见是否可见--------------------* */
				if (documentProcess.approval == "1") {
					createApproval(data.list.allDocumentRunProcess)
				} else {
					$("#ideadiv").remove();
				}
				/** ------------审批是否可以回退--------------------* */
				if(documentProcess.prcsType!=1 && documentProcess.prcsType!=2)
				{
					if (documentProcess.goBack == "0") {
						$(".js-goback").remove();
					} else if(documentProcess.goBack == "1"){
						$(".js-goback").show();
						setGobackOpt();
					}else if(documentProcess.goBack == "2"){
						$(".js-goback").show();
						getCanTaskBackRunProcessList();
					}
				}else
				{
					$(".js-goback").remove();
				}
				/**---------------流程图-----------------------**/
				if (documentProcess.follow == "0") {
					$(".js-follow").remove();
				} else {
					$(".js-follow").find("label").show();
					setFollowOpt();
				}
				$(".js-flowchat").unbind("click").click(function(){
					flowchat(documentProcess.flowId);
				})
				$(".js-runflowchat").unbind("click").click(function(){
					runflowchart(runId,documentProcess.flowId);
				})
				/**------------------加签处理----------------------------**/
				if(documentProcess.prcsType!=1 && documentProcess.prcsType!=2)
				{
					if(documentProcess.addProcess =="0"||documentProcess.addProcess==""||documentProcess.addProcess==null)
					{
						$(".js-before").remove();
						$(".js-after").remove();
					}else if(documentProcess.addProcess=="1")
					{
						$(".js-after").remove();
						$(".js-before").unbind("click").click(function(){
							addProcessBefore();
						});
					}else if(documentProcess.addProcess=="2")
					{
						$(".js-before").remove();
						$(".js-after").unbind("click").click(function(){
							addProcessAfter()
						});
					}else if(documentProcess.addProcess=="3")
					{
						$(".js-before").unbind("click").click(function(){
							addProcessBefore()
						});
						$(".js-after").unbind("click").click(function(){
							addProcessAfter()
						});
					}
				}else
				{
					$(".js-before").remove();
					$(".js-after").remove();
				}
				/**------------------处理特殊的元素----------------**/
				getElements();
				/**------------------会签----------------**/
				$("#ideaText").code(data.list.documentRunProcess.ideaText);
				$("input:radio[name='passStatus'][value='" + data.list.documentRunProcess['passStatus'] + "']").attr("checked", true);
				
				/** -------------BPM流程公共文件权限设置-----------------* */
				var priv = documentProcess.publicFile;
				var attachIds = data.list.documentList.attach;
				setPublicFile(priv, attachIds);
				/**--------------审批步骤文件处理---------------------**/
				$("#runAttach").attr("data_value", data.list.documentRunProcess.attach);
				createAttach("runAttach", 4);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg)
			}
		}
	});
}

function getElements() {
	var mustField = documentProcess.mustField;
	var numField = documentProcess.numField;
	$("[xtype]").each(function() {
		var eName = $(this).attr("name");
		var xType = $(this).attr("xtype");
		if ((","+writerField+",").indexOf("," + eName + ",") >= 0) {
			if (xType == "xfetch") {
				var model = JSON.parse($(this).attr("model"));
				var type = model.type;
				if (type == "1" || type == "2") {
					jeDate("[name='" + eName + "']", {
						format : model.format
					});
				} else if (type == "3") {
					$(this).attr("opt-id", eName).attr("id", eName);
					$(this).unbind("click").click(function() {
						selectDept(this, 'false');
					});
				} else if (type == "4") {
					$(this).attr("opt-id", eName).attr("id", eName);
					$(this).unbind("click").click(function() {
						selectDept(this, 'true');
					});
				} else if (type == "5") {
				} else if (type == "6") {
				} else if (type == "7") {
					$(this).attr("opt-id", eName).attr("id", eName);
					$(this).unbind("click").click(function() {
						selectUser(this, 'true');
					});
				} else if (type == "8") {
					$(this).attr("opt-id", eName).attr("id", eName);
					$(this).unbind("click").click(function() {
						selectUser(this, 'true');
					});
				}
			} else if (xType == "xtextuedit") {
				var defaultvalue = $(this).attr("defaultvalue");
				$(this).summernote({
					height : 150
				}).code(defaultvalue);
			} else if (xType == "xselect") {

			} else if (xType == "xcalculate") {
				var model = $(this).attr("model");
				calculateFunction(eName,model);
			} else if (xType == "xlist") {
				initChileTable(eName,documentProcess,this);
			}else if(xType == "xupload") {
				createAttachDocument(eName+"attach","4");
			}else if(xType == "xhtml")
			{
			}
		}else
		{
			if(xType == "xupload") {
				createAttach(eName+"attach","1");
			}
		}

	})
}

function getFormDataParam() {
	var json = {};
	$.each($("[xtype]"),function() {
		var eName = $(this).attr("name");
		if (("," + writerField + ",").indexOf("," + eName + ",") >= 0) {
			var xType = $(this).attr("xtype");
			if (xType == "xlist") {
				json[eName] =JSON.stringify(getDocumentChildTable(eName));
			} else if (xType == "xradio") {
				if ($(this).is(':checked')) {
					json[eName] = $(this).val();
				}
			} else if (xType == "xcheckbox") {
				var name = $(this).attr("name");
				var value = [];
				$.each($('input[name=' + name + ']:checkbox:checked'), function() {
					value.push($(this).val());
				})
				json[eName] = value.join(",");
			} else if(xType == "xdocument")
			{
				json[eName] =getDocumentListSelectData(eName);
			}else if(xType =="xupload")
			{
				json[eName] =$("#"+eName+"attach").attr("data_value");
			}else if(xType=="xsql")
			{
				json[eName] = $(this).prop("outerHTML");
			}else if(xType=="xsqlselect"){
				var tmpjson={};
				tmpjson.vkey=$(this).val();
				tmpjson.value=$(this).find("option:selected").text();
				json[eName]=JSON.stringify(tmpjson);
			}else if(xType=="xhtml"){
				json[eName] = $(this).formhtml();
			}else if(xType=="xtextuedit"){
				json[eName] = $(this).code();
			}else if(xType=="xseal")
			{
				json[eName] = $(this).attr("src");
			}else{
				json[eName] = $(this).val();
			}
		}
	})
	return json;
}





function saveFormData(formdatajson) {
	$.ajax({
		url : "/set/documentset/saveFormData",
		type : "post",
		dataType : "json",
		data : {
			runId : runId,
			runProcessId : runProcessId,
			urgency : $("#urgency").val(),
			attach : $("#attach").attr("data_value"),
			passStatus : $('input:radio[name=passStatus]:checked').val(),
			runAttach : $("#runAttach").attr("data_value"),
			ideaText : $("#ideaText").code(),
			formData : JSON.stringify(formdatajson)
		},
		success : function(data) {
			if (data.status == "200") {
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function setPublicFile(priv, attachIds) {
	if (priv == "0") {
		$("#publicFileDiv").remove();
		$(".js-netfile").remove();
	} else if (documentProcess.publicFile == "1") {
		$("#attach").attr("data_value", attachIds);
		$("#attach").attr("disable", "disable").attr("onchange", "").attr("type", "hidden");
		$("#attach").parent().hide();
		$(".js-netfile").remove();
		createAttach("attach", priv)
	} else if (priv == "2") {
		$("#attach").attr("data_value", attachIds);
		$("#attach").attr("disable", "disable").attr("onchange", "").attr("type", "hidden");
		$("#attach").parent().hide();
		$(".js-netfile").remove();
		createAttach("attach", priv)
	} else if (priv == "3") {
		$("#attach").attr("data_value", attachIds);
		$("#attach").attr("disable", "disable").attr("onchange", "").attr("type", "hidden");
		$("#attach").parent().hide();
		$(".js-netfile").remove();
		createAttach("attach", priv)
	} else if (priv == "4") {
		$("#attach").attr("data_value", attachIds);
		createAttachDocument("attach", priv)
	}else if (priv == "5") {
		$("#attach").attr("data_value", attachIds);
		createAttachDocument("attach", priv)
	}
}

function doBtnCreate(opRule, opFlag,approvalFlag) {
	if (opRule == "3") {
		$(".js-saveandendbtn").show();
		$(".js-saveandnextbtn").remove();
		$(".js-saveandendbtn").unbind("click").click(function() {
			var formdatajson = getFormDataParam();
			saveFormData(formdatajson);
			doCompleteDocument();
		});
	} else {
		if (opFlag == "0") {
			$(".js-saveandendbtn").remove();
			$(".js-saveandnextbtn").show();
			$(".js-saveandnextbtn").unbind("click").click(function() {
				var formdatajson = getFormDataParam();
				if(approvalFlag=="2")
				{
					if($("#ideaText").code()==null||$("#ideaText").code()=="")
					{
						top.layer.msg("会签意见不能为空！");
						return;
					}
				}
				saveFormData(formdatajson);
				if(documentProcess.passEndFlag=='1')
				{
					var passStatus = $('input:radio[name=passStatus]:checked').val();
					if(passStatus=='0')
					{
				       bootbox.confirm("由于客观意见为不同意！流程则结束！", function (result) {
				              if (result) {
				            	  $.ajax({
				            			url : "/set/documentset/doNotPassEndDocument",
				            			type : "post",
				            			dataType : "json",
				            			data : {
				            				runId : runId,
				            				runProcessId : runProcessId
				            			},
				            			success : function(data) {
				            				if(data.status=="200")
				            				{
				            					history.back(-1);
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
				         });
				       return;
					}
				}
				verification(formdatajson);
			});
		} else if (opFlag == "1") {
			$(".js-saveandendbtn").show();
			$(".js-saveandnextbtn").remove();
			$(".js-saveandendbtn").unbind("click").click(function() {
				var formdatajson = getFormDataParam();
				if(approvalFlag=="2")
				{
					if($("#ideaText").code()==null||$("#ideaText").code()=="")
					{
						top.layer.msg("会签意见不能为空！");
						return;
					}
				}
				saveFormData(formdatajson);
				doCompleteDocument();
				top.layer.msg("数据已保存!");
			});
		}
	}
}

function getNextPrcs() {
	var v2 = $(".js-saveandnextbtn").attr("data-value-process-id");
	$.ajax({
		url : "/ret/documentget/getNextPrcs",
		type : "post",
		dataType : "json",
		data : {
			runId : runId,
			oldProcessId : v2,
			runProcessId : runProcessId
		},
		success : function(data) {
			if (data.status == "200") {
				var prcsList = data.list;
				if(prcsList.length==0)
				{
					top.layer.msg("没有符合条件的下一步骤,请与管理员联系!");
				}else
				{
					top.layer.msg("数据已保存!");
					createOpUserAndPrcs(prcsList);
				}
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
			if (documentProcess.concurrentFlag == "0"||documentProcess.concurrentFlag == "1") {
				$(".js-prcscb").unbind("click").click(function() {
					createOpUserSelectDiv(this);
				});
			} else {
				$(".js-prcscb").each(function() {
					$(this).attr("checked", true);
					$(this).unbind("click").click(function(){
						return false;
					})
				});
			}
		}
	});
}

function doCompleteDocument() {
	$.ajax({
		url : "/set/documentset/doCompleteDocument",
		type : "post",
		dataType : "json",
		data : {
			runId : runId,
			runProcessId : runProcessId
		},
		success : function(data) {
			if (data.status == "200") {
				//window.location.href=data.redirect;
				open(data.redirect,"_self");
				top.layer.msg("数据已保存!");
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function createOpUserAndPrcs(prcsList) {
	var prcsHtml = "";
	var html = "";
	for (var i = 0; i < prcsList.length; i++) {
		var opUserHtml="";
		var otherUserHtml="";
		if(prcsList[i].prcsType!="2")
		{
			if( prcsList[i].opRule=='0')
			{
				for(var k=0;k<prcsList[i].opUser.length;k++)
				{
				opUserHtml=['<div class="alert alert-danger fade in" style="width: 80px;height: 32px;line-height: 13px;font-size: 12px;padding-right: 4px;margin-bottom:0px;" data-value="'+prcsList[i].opUser[k].accountId+'">',
					'    <button class="close" data-dismiss="alert" style="line-height: 3px;color: white;font-size: 16px;opacity:1;">',
					'        ×',
					'    </button>',
					prcsList[i].opUser[k].userName,
					'	</div>'].join("");
				}
				if(prcsList[i].otherUser)
				{
					for(var k=0;k<prcsList[i].otherUser.length;k++)
					{
						var tmpStr=['<div class="alert alert-info fade in" style="display: inline-block;width: 80px;height: 32px;line-height: 13px;font-size: 12px;padding-right: 4px;margin-right:10px;margin-bottom:5px;" data-value="'+prcsList[i].otherUser[k].accountId+'">',
							'    <button class="close" data-dismiss="alert" style="line-height: 3px;color: white;font-size: 16px;opacity:1;">',
							'        ×',
							'    </button>',
							prcsList[i].otherUser[k].userName,
							'	</div>'].join("");
						otherUserHtml+=tmpStr;
					}
				}
			}else
			{
				if(prcsList[i].otherUser)
				{
					for(var k=0;k<prcsList[i].otherUser.length;k++)
					{
					var tmpStr=['<div class="alert alert-info fade in" style="display: inline-block;width: 80px;height: 32px;line-height: 13px;font-size: 12px;padding-right: 4px;margin-right:10px;margin-bottom:5px;" data-value="'+prcsList[i].otherUser[k].accountId+'">',
						'    <button class="close" data-dismiss="alert" style="line-height: 3px;color: white;font-size: 16px;opacity:1;">',
						'        ×',
						'    </button>',
						prcsList[i].otherUser[k].userName,
						'	</div>'].join("");
					otherUserHtml+=tmpStr;
					}
				}
			}
		}
		prcsHtml += "<div class=\"checkbox\" style=\"height: 40px;\" data-value=\"" + prcsList[i].processId + "\">" + "<label style=\"line-height: 40px;\">"
				+ "<input prcs_type=\""+prcsList[i].prcsType+"\" type=\"checkbox\" class=\"inverted js-prcscb\" name=\"prcsCheckbox\" id=\"prcs_" + i + "\" data-value=\"" + prcsList[i].processId + "\">"
				+ "<span class=\"text\">" + prcsList[i].prcsName + "</span>" + "</label>" + "</div>";
		var ishide="";
		if(documentProcess.concurrentFlag!="2")
		{
			ishide = "display:none;";
		}
		if (prcsList[i].prcsType != "2") {
			html += '<div id="prcs_select_' + prcsList[i].processId + '" style="'+ishide+'background-color: #f5f5f5;margin-bottom:10px;" class="row">'
					+ '<div class="col-lg-12 col-sm-12 col-xs-12"><h5 class="row-title before-darkorange"><i class="fa fa-flash darkorange"></i>' + prcsList[i].prcsName + '</h5>'
					+ '<a href="javascript:void(0);" style="float:right;margin-top: 10px;" class="btn btn-purple js-documentPrcsSelectOpuser" auto-accountid='+prcsList[i].sPrcsAutoAccountId
					+' auto-username=' + prcsList[i].sPrcsAutoUserName+' data-value=' + prcsList[i].processId
					+ ' opRule='+prcsList[i].opRule+'>选择人员</a>' + '</div>';
			if(prcsList[i].opRule==0)
			{
				html+= '<div class="col-lg-12 col-sm-12 col-xs-12">' 
				+ 		'<span>主办人员：</span>' 
				+ 		'<span opRule='+prcsList[i].opRule+' style="display: inline-block;" class="js-opUser_'+ prcsList[i].processId + '">'+opUserHtml+'</span>' 
				+ '</div>';
			}
			
			html+= '<div class="col-lg-12 col-sm-12 col-xs-12" style="margin-top: 10px; margin-bottom: 10px;">'
					+ '<span>经办人员：</span><span  style="display: inline-block;" class="js-otherOpUser_' + prcsList[i].processId + '">'+ otherUserHtml+'</span>' + '</div>' + '</div>';
			$(".js-prcsselectdiv").html(html);
		}
	}
	$(".js-documentPrcsSelectOpuser").each(function() {
		$(this).unbind("click").click(function() {
			documentOpUserSelect(this);
		});
	});
	$("#prcsInfo").html(prcsHtml);
	$("#goNextdiv").modal("show");
	if(prcsList.length==1)
	{
		$('#prcs_select_' + prcsList[0].processId).show();
		$("#prcs_0").attr("checked", "checked");
	}
	if(documentProcess.autoSendType=="1")
	{
		$("#sendToUserDiv").show();
	}
	createPrcsMsgRemind("msgType",documentProcess.remindNextUser,documentProcess.remindCreateUser,documentProcess.remindParticipant);
	//getSmsConfig("msgType", "documentNext");
}

function createApproval(runPrcsDataList) {
	for (var i = 0; i < runPrcsDataList.length; i++) {
		var passStatus = '<div class="ticket-state bg-palegreen"><i class="fa fa-check"></i></div>';
		if (runPrcsDataList[i].passStatus == "0") {
			passStatus = '<div class="ticket-state bg-darkorange"><i class="fa fa-times"></i></div>';
		}
		var el = [
				'<li class="ticket-item">',
				'    <div class="row">',
				'        <div class="ticket-user col-lg-3 col-sm-12">',
				'            <img src="/sys/file/getOtherHeadImg?headImg=' + runPrcsDataList[i].headImg + '" class="user-avatar">',
				'            <span class="user-name">' + runPrcsDataList[i].userName + '</span>',
				'            <span class="user-at">at</span>',
				'            <span class="user-company">' + runPrcsDataList[i].prcsName + '</span>',
				'        </div>',
				'        <div class="ticket-time  col-lg-3 col-sm-6 col-xs-12">',
				'            <div class="divider hidden-md hidden-sm hidden-xs"></div>',
				'            <i class="fa fa-clock-o"></i>',
				'            <span class="time">' + runPrcsDataList[i].endTime + '</span>',
				'        </div>',
				'        <div class="ticket-type  col-lg-6 col-sm-6 col-xs-12" style="height:100%">',
				'            <span class="divider hidden-xs"></span>',
				'            <span class="type">' + runPrcsDataList[i].ideaText + '<span id="show_' + runPrcsDataList[i].runProcessId + '"></span><span id="'
						+ runPrcsDataList[i].runProcessId + '"></span></span>', '        </div>' + passStatus, '    </div>', '</li>' ].join("");
		$("#ideaList").append(el);
		$("#" + runPrcsDataList[i].runProcessId).attr("data_value", runPrcsDataList[i].attach);
		createAttach(runPrcsDataList[i].runProcessId, 3);
	}

}
function setFollowOpt() {
	$("input:checkbox[name='follow-checkbox']").unbind("change").change(function() {
		var isFollow = $(this).is(':checked');
		$.ajax({
			url : "/set/documentset/setFollowOpt",
			type : "post",
			dataType : "json",
			data : {
				runId : runId,
				isFollow : isFollow
			},
			success : function(data) {
				if (data.status == "200") {
					top.layer.msg(data.msg);
				} else if (data.status == "100") {
					top.layer.msg(data.msg);
				} else {
					console.log(data.msg);
				}
			}
		})
	})
}

function changeDocumentTitle()
{
	$("#changDocumentTitleModal").modal("show");
	$(".js-changeTitle").unbind("click").click(function(){
		$.ajax({
			url : "/set/documentset/updateDocumentList",
			type : "post",
			dataType : "json",
			data : {
				runId : runId,
				flowTitle : $("#documentFlowTitle").val()
			},
			success : function(data) {
				if (data.status == "200") {
					top.layer.msg(data.msg);
					$("#flowTitle").html($("#documentFlowTitle").val());
					$("#changDocumentTitleModal").modal("hide");
				} else if (data.status == "100") {
					top.layer.msg(data.msg);
				} else {
					console.log(data.msg);
				}
			}
		})
	})
}
function flowchat(flowId)
{
	//window.location.href = "/app/core/document/documentDesignFlowChart?flowId="+flowId;
	open("/app/core/document/documentDesignFlowChart?flowId="+flowId,"_self");
}
function runflowchart(runId, flowId) {
	//window.location.href = "/app/core/document/documentFlowChart?runId=" + runId + "&flowId=" + flowId;
	open("/app/core/document/documentFlowChart?runId=" + runId + "&flowId=" + flowId,"_self");
}
function setGobackOpt() {
	$(".js-goback").unbind("click").click(function(){
		$.ajax({
			url : "/set/documentset/setGobackOpt",
			type : "post",
			dataType : "json",
			data : {
				runId : runId,
				runProcessId : runProcessId
			},
			success : function(data) {
				if (data.status == "200") {
					top.layer.msg(data.msg);
					open("/app/core/document/dispatchapproved","_self");
				} else if (data.status == "100") {
					top.layer.msg(data.msg);
				} else {
					console.log(data.msg);
				}
			}
		});
	})
}

function addProcessBefore()
{
	$("#addAccountId").attr("data-value","");
	$("#addAccountId").val("");
	$("#addProcessModal").modal("show");
	$(".js-addProcess").unbind("click").click(function(){
		$.ajax({
			url : "/set/documentset/addProcessBefore",
			type : "post",
			dataType : "json",
			data : {
				runId : runId,
				runProcessId : runProcessId,
				addAccountId:$("#addAccountId").attr("data-value")
			},
			success : function(data) {
				if (data.status == "200") {
					top.layer.msg(data.msg);
					$("#addProcessModal").modal("hide");
				} else if (data.status == "100") {
					top.layer.msg(data.msg);
				} else {
					console.log(data.msg);
				}
			}
		});
	})
}
function addProcessAfter()
{
	$("#addAccountId").attr("data-value","");
	$("#addAccountId").val("");
	$("#addProcessModal").modal("show");
	$(".js-addProcess").unbind("click").click(function(){
		$.ajax({
			url : "/set/documentset/addProcessAfter",
			type : "post",
			dataType : "json",
			data : {
				runId : runId,
				runProcessId : runProcessId,
				addAccountId:$("#addAccountId").attr("data-value")
			},
			success : function(data) {
				if (data.status == "200") {
					top.layer.msg(data.msg);
					$("#addProcessModal").modal("hide");
				} else if (data.status == "100") {
					top.layer.msg(data.msg);
				} else {
					console.log(data.msg);
				}
			}
		});
	})
}

function getCanTaskBackRunProcessList()
{
	$(".js-goback").unbind("click").click(function(){
		$.ajax({
			url : "/ret/documentget/getCanGoBckProcessList",
			type : "post",
			dataType : "json",
			data : {
				runId : runId
			},
			success : function(data) {
				if (data.status == "200") {
					$("#goBackProcessModal").modal("show");
					var html="";
					for(var i=0;i<data.list.length;i++)
						{
						html+="<tr>";
						html+="<td><div class='radio' style='margin-top:0px;margin-bottom:0px;'><label style='padding-left:0px'><input value='"+data.list[i].runProcessId+"' name='gobackradio' type='radio' class='colored-blue'><span class='text'></span></label></div></td>";
						html+="<td>"+data.list[i].prcsName+"</td>";
						html+="<td>"+data.list[i].opUserName+"</td>";
						html+="<td>"+data.list[i].endTime+"</td>";
						html+="</tr>";
						}
					$("#cangotbody").html(html);
					$(".js-goBackProcess").unbind("click").click(function(){
						var goBackRunProcessId = $('input:radio[name="gobackradio"]:checked').val();
						if(goBackRunProcessId==""||goBackRunProcessId==null)
							{
							top.layer.msg("请选择需要回退的步骤！");
							return;
							}
							$.ajax({
								url : "/set/documentset/setCanGobackOpt",
								type : "post",
								dataType : "json",
								data : {
									runId : runId,
									runProcessId:runProcessId,
									tagerRunProcessId:goBackRunProcessId
								},
								success : function(data) {
									if(data.status=="200")
									{
										$("#goBackProcessModal").modal("hide");
										top.layer.msg(data.msg);
										open("/app/core/document/dispatchapproved","_self");
									}else if(data.status=="100")
									{
										top.layer.msg(data.msg);
									}else
									{
										console.log(data.msg);
									}
									}
								});
					});
				} else if (data.status == "100") {
					top.layer.msg(data.msg);
				} else {
					console.log(data.msg);
				}
			}
			});
	});
}

function verification(formdatajson)
{
	var flagNumField=[];
	var flagMustField=[];
	var mustField = documentProcess.mustField;
	if(mustField!="")
	{
		var mustFieldArr = mustField.split(",");
		for(var i=0;i<mustFieldArr.length;i++)
		{
			if(formdatajson[mustFieldArr[i]]==""||formdatajson[mustFieldArr[i]]==null)
			{
				var titleName = $("[name='"+mustFieldArr[i]+"']").attr("title");
				flagMustField.push(titleName);
			}
		}
	}
	var numField = documentProcess.numField;
	if(numField!="")
	{
		var numFieldArr = numField.split(",");
		for(var i=0;i<numFieldArr.length;i++)
		{
			if(!mustNumber(formdatajson[numFieldArr[i]]))
			{
				var titleName = $("[name='"+numFieldArr[i]+"']").attr("title");
				flagNumField.push(titleName);
			}
		}
		
	}
	if(flagMustField.length==0&&flagNumField.length==0)
	{
		getNextPrcs();
	}else
	{
		for(var i=0;flagMustField.length;i++)
		{
			top.layer.msg(flagMustField[i]+"为必填字段！");
			return;
		}
		for(var i=0;flagNumField.length;i++)
		{
			top.layer.msg(flagNumField[i]+"必须为数字！");
			return;
		}
	}
}