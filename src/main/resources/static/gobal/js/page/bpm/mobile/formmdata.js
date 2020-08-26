var writerField = "";
var bpmProcess;
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


$("body").ready(function() {
	$(".js-savebtn").unbind("click").click(function() {
		var formdatajson = getFormDataParam();
		saveFormData(formdatajson);
		top.layer.msg("数据已保存");
	});
	// 转交下一步
});

/**
 * 生成表单HTML
 * 
 * @returns
 */
function doInitForm() {
	$.ajax({
		url : "/ret/bpmget/getProcessInfo",
		type : "post",
		dataType : "json",
		data : {
			runId : runId,
			runProcessId : runProcessId
		},
		success : function(data) {
			if (data.status == "200") {
				bpmProcess = data.list.bpmProcess;
				bpmRunProcess = data.list.bpmRunProcess;
				writerField = data.list.bpmProcess.writerField;
				if(data.list.bpmProcess.changeTitle=="1")
				{
				$("#flowtitle").show();
				$("#flowtitle").unbind("click").click(function(){
					$("#bpmFlowTitle").val($("title").html());
					changeBpmTitle();
				});
				}
				if(data.list.bpmProcess.approvalFlag=="0")
				{
					$("#ideaDiv").remove();
				}else 
				{
					$("#ideaText").summernote({
						height : 150
					});
				}
				$("#docNum").html("NO:"+data.list.bpmList.id)
				$("title").html(data.list.bpmList.flowTitle);
				$("#urgency").val(data.list.bpmList.urgency);
				$("#prcsName").html("步骤名称：" + data.list.bpmProcess.prcsName);
				$(".js-saveandnextbtn").attr("data-value-process-id", data.list.bpmProcess.processId);
				/** ------------主办经办权限处理--------------------* */
				doBtnCreate(bpmProcess.opRule, bpmRunProcess.opFlag,bpmProcess.approvalFlag);
				/** ------------审批意见是否可见--------------------* */
				if (bpmProcess.approval == "1") {
					createApproval(data.list.allBpmRunProcess)
				} else {
					$("#ideadiv").remove();
				}
				/** ------------审批是否可以回退--------------------* */
				if(bpmProcess.prcsType!=1 && bpmProcess.prcsType!=2)
				{
					if (bpmProcess.goBack == "0") {
						$(".js-goback").remove();
					} else if(bpmProcess.goBack == "1"){
						$(".js-goback").show();
						setGobackOpt();
					}else if(bpmProcess.goBack == "2"){
						$(".js-goback").show();
						getCanTaskBackRunProcessList();
					}
				}else
				{
					$(".js-goback").remove();
				}
				/**---------------流程图-----------------------**/
				if (bpmProcess.follow == "0") {
					$(".js-follow").remove();
				} else {
					$(".js-follow").find("label").show();
					setFollowOpt();
				}
				$(".js-flowchat").unbind("click").click(function(){
					flowchat(bpmProcess.flowId);
				})
				$(".js-runflowchat").unbind("click").click(function(){
					runflowchart(runId,bpmProcess.flowId);
				})
				/**------------------加签处理----------------------------**/
				if(bpmProcess.prcsType!=1 && bpmProcess.prcsType!=2)
				{
					if(bpmProcess.addProcess =="0"||bpmProcess.addProcess==""||bpmProcess.addProcess==null)
					{
						$(".js-before").remove();
						$(".js-after").remove();
					}else if(bpmProcess.addProcess=="1")
					{
						$(".js-after").remove();
						$(".js-before").unbind("click").click(function(){
							addProcessBefore();
						});
					}else if(bpmProcess.addProcess=="2")
					{
						$(".js-before").remove();
						$(".js-after").unbind("click").click(function(){
							addProcessAfter()
						});
					}else if(bpmProcess.addProcess=="3")
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
				$("#ideaText").code(data.list.bpmRunProcess.ideaText);
				$("input:radio[name='passStatus'][value='" + data.list.bpmRunProcess['passStatus'] + "']").attr("checked", true);
				
				/** -------------BPM流程公共文件权限设置-----------------* */
				var priv = bpmProcess.publicFile;
				var attachIds = data.list.bpmList.attach;
				setPublicFile(priv, attachIds);
				/**--------------审批步骤文件处理---------------------**/
				$("#runAttach").attr("data_value", data.list.bpmRunProcess.attach);
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
	var mustField = bpmProcess.mustField;
	var numField = bpmProcess.numField;
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
				initChileTable(eName,bpmProcess,this);
			}else if(xType == "xupload") {
				createAttach(eName+"attach","4");
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
				json[eName] =JSON.stringify(getBpmChildTable(eName));
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
			} else if(xType == "xbpm")
			{
				json[eName] =getBpmListSelectData(eName);
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
			}else
			{
				json[eName] = $(this).val();
			}
		}
	})
	return json;
}





function saveFormData(formdatajson) {
	$.ajax({
		url : "/set/bpmset/saveFormData",
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
				//top.layer.msg("数据已保存!");
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
	} else if (bpmProcess.publicFile == "1") {
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
		createAttach("attach", priv)
	}
}

function verification(formdatajson)
{
	var flagNumField=[];
	var flagMustField=[];
	var mustField = bpmProcess.mustField;
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
	var numField = bpmProcess.numField;
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
		console.log(333);
		var v2 = $(".js-saveandnextbtn").attr("data-value-process-id");
		window.location.href='/app/core/bpm/nextprcs?runId='+runId+'&processId='+v2+'&runProcessId='+runProcessId;
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

function doBtnCreate(opRule, opFlag,approvalFlag) {
	if (opRule == "3") {
		$(".js-saveandendbtn").show();
		$(".js-saveandnextbtn").remove();
		$(".js-saveandendbtn").unbind("click").click(function() {
			var formdatajson = getFormDataParam();
			saveFormData(formdatajson);
			doCompleteBpm();
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
				if(bpmProcess.passEndFlag=='1')
				{
					var passStatus = $('input:radio[name=passStatus]:checked').val();
					if(passStatus=='0')
					{
				       bootbox.confirm("由于客观意见为不同意！流程则结束！", function (result) {
				              if (result) {
				            	  $.ajax({
				            			url : "/set/bpmset/doNotPassEndBpm",
				            			type : "post",
				            			dataType : "json",
				            			data : {
				            				runId : runId,
				            				runProcessId : runProcessId
				            			},
				            			success : function(data) {
				            				if(data.status=="200")
				            				{
				            					location.href="/app/core/bpm/doprocess";
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
				doCompleteBpm();
				top.layer.msg("数据已保存!");
			});
		}
	}
}


function doCompleteBpm() {
	$.ajax({
		url : "/set/bpmset/doCompleteBpm",
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


function createApproval(runPrcsDataList) {
	for (var i = 0; i < runPrcsDataList.length; i++) {
		var passStatus = '<div class="ticket-state bg-palegreen" style="right:0px;"><i class="fa fa-check"></i></div>';
		if (runPrcsDataList[i].passStatus == "0") {
			passStatus = '<div class="ticket-state bg-darkorange" style="right:0px;"><i class="fa fa-times"></i></div>';
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
			url : "/set/bpmset/setFollowOpt",
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

function changeBpmTitle()
{
	$("#changBpmTitleModal").modal("show");
	$(".js-changeTitle").unbind("click").click(function(){
		$.ajax({
			url : "/set/bpmset/updateBpmList",
			type : "post",
			dataType : "json",
			data : {
				runId : runId,
				flowTitle : $("#bpmFlowTitle").val()
			},
			success : function(data) {
				if (data.status == "200") {
					top.layer.msg(data.msg);
					$("title").html($("#bpmFlowTitle").val());
					$("#changBpmTitleModal").modal("hide");
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
	open("/app/core/bpm/bpmDesignFlowChart?flowId="+flowId,"_self");
}
function runflowchart(runId, flowId) {
	open("/app/core/bpm/bpmFlowChart?runId=" + runId + "&flowId=" + flowId,"_self");
}
function setGobackOpt() {
	$(".js-goback").unbind("click").click(function(){
		$.ajax({
			url : "/set/bpmset/setGobackOpt",
			type : "post",
			dataType : "json",
			data : {
				runId : runId,
				runProcessId : runProcessId
			},
			success : function(data) {
				if (data.status == "200") {
					top.layer.msg(data.msg);
					//window.location.href="/app/core/bpm/doprocess";
					open("/app/core/bpm/doprocess","_self");
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
			url : "/set/bpmset/addProcessBefore",
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
			url : "/set/bpmset/addProcessAfter",
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
			url : "/ret/bpmget/getCanGoBckProcessList",
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
								url : "/set/bpmset/setCanGobackOpt",
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
										open("/app/core/bpm/doprocess","_self");
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