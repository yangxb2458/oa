var bpmProcess=null;
var $myAs = $('#J_ActionSheet');
$(function(){
	getBpmProcess();
	createPrcsMsgRemind("msgType",bpmProcess.remindNextUser,bpmProcess.remindCreateUser,bpmProcess.remindParticipant);
	getNextPrcs();
	$(".selectuser").each(function(){
		$(this).unbind("click").click(function(){
			var processId = $(this).attr("data-value");
			var opflag = $(this).attr("opflag");
			var optId = $(this).attr("opt-id");
			$("#J_ListContent").empty();
			$("#J_ActionSheet").attr("opflag",opflag);
			$("#J_ActionSheet").attr("optId",optId);
			$("#J_ActionSheet").attr("processId",processId);
			getBpmUserList(processId);
			$myAs.actionSheet('open');
			$(".glyphicon-search").unbind("click").click(function(){
				$("#J_ListContent").empty();
				getBpmUserList(processId);
			});
		});
	});

    $('#J_Cancel').on('click', function () {
        $myAs.actionSheet('close');
    });
    
    $(bpmProcess.autoSendType=="1")
    {
    	$("#sendToUserDiv").show();
    }
    
	$(".js-next-runprcs").unbind("click").click(function() {
		var checkedPrcsArr = [];
		$(".js-prcscb").each(function() {
			if ($(this).is(':checked')) {
				var prcsType = $(this).attr("prcs_type");
				var json = {};
				json.prcsType=prcsType;
				json.processId = $(this).attr("process-id");
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
					var opUser = $(".op-user_" + checkedPrcsArr[i].processId).find("div").attr("data-value");
					var opRule = $(".op-user_" + checkedPrcsArr[i].processId).attr("opRule");
					var otherOpArr = [];
					if (opRule == "0") {
						if (opUser == undefined) {
							top.layer.msg("请选择主办人!");
							return;
						}
						json.opUser = opUser;
						$(".jb-user_" + checkedPrcsArr[i].processId).find("div").each(function() {
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
	document.body.addEventListener('touchstart', function () { });
});

function getNextPrcs() {
	$.ajax({
		url : "/ret/bpmget/getNextPrcs",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			runId : runId,
			oldProcessId : processId,
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
createOpUserAndPrcs(prcsList);
				}
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
	for (var i = 0; i < prcsList.length; i++) {
		if(prcsList[i].prcsType=="2")
		{
			prcsHtml+=['<div class="panel panel-default">',
				'			<div class="panel-heading ">',
				'				<h4 class="panel-title">',
				'					<a class="accordion-toggle '+(i>0?"collapsed":"")+'" data-toggle="collapse" data-parent="#accordion" href="#processId_'+prcsList[i].processId+'">'+prcsList[i].prcsName+'</a>',
				'				</h4>',
				'			</div>',
				'			<div id="processId_'+prcsList[i].processId+'" class="panel-collapse collapse '+(i==0?"in":"")+'">',
				'				<div class="panel-body border-red">',
				'					<form class="form-horizontal form-bordered">',
				'					<div class="form-group">',
				'					<label class="col-xs-2 control-label no-padding-right">步骤：</label>',
				'							<div class="col-xs-10" style="display: inline-block;">',
				'					<label> <input class="js-prcscb" name="prcsCheck" prcs_type="'+prcsList[i].prcsType+'" process-id="'+prcsList[i].processId+'" type="checkbox" '+(prcsList.length==1?"checked":"")+'> <span class="text">'+prcsList[i].prcsName+'</span>',
				'				</label>',
				'			</div>',
				'		</div>',
				'	</form>',
				'				</div>',
				'			</div>',
				'		</div>'].join("");
		}else
		{
			var opUserHtml="";
			var otherUserHtml="";
			var opUser="";
			if(prcsList[i].opRule=="0")
			{
				for(var k=0;k<prcsList[i].opUser.length;k++)
				{
					opUser = prcsList[i].opUser[k].accountId;
					opUserHtml='<div class="alert alert-danger fade in" style="width: 80px;font-size: 12px;padding:0px;margin:0px 5px 5px 0px" data-value="'+prcsList[i].opUser[k].accountId+'"><button class="close" data-dismiss="alert" style="color: white;font-size: 14px;opacity:1;">×</button>'+prcsList[i].opUser[k].userName+'</div>';
				}
				if(prcsList[i].otherUser)
				{
					for(var k=0;k<prcsList[i].otherUser.length;k++)
					{
						if(prcsList[i].otherUser[k].accountId!=opUser)
						{
							otherUserHtml+='<div class="alert alert-danger fade in" style="width: 80px;font-size: 12px;padding:0px;margin:0px 5px 5px 0px;display:inline-block;" data-value="'+prcsList[i].otherUser[k].accountId+'"><button class="close" data-dismiss="alert" style="color: white;font-size: 14px;opacity:1;">×</button>'+prcsList[i].otherUser[k].userName+'</div>';
						}
					}
				}
			}else
			{
				if(prcsList[i].otherUser)
				{
					for(var k=0;k<prcsList[i].otherUser.length;k++)
					{
						otherUserHtml+='<div class="alert alert-danger fade in" style="width: 80px;font-size: 12px;padding:0px;margin:0px 5px 5px 0px;display:inline-block;" data-value="'+prcsList[i].otherUser[k].accountId+'"><button class="close" data-dismiss="alert" style="color: white;font-size: 14px;opacity:1;">×</button>'+prcsList[i].otherUser[k].userName+'</div>';
					}
				}
			}
			prcsHtml+=['<div class="panel panel-default">',
				'			<div class="panel-heading ">',
				'				<h4 class="panel-title">',
				'					<a class="accordion-toggle '+(i>0?"collapsed":"")+'" data-toggle="collapse" data-parent="#accordion" href="#processId_'+prcsList[i].processId+'">'+prcsList[i].prcsName+'</a>',
				'				</h4>',
				'			</div>',
				'			<div id="processId_'+prcsList[i].processId+'" class="panel-collapse collapse '+(i==0?"in":"")+'">',
				'				<div class="panel-body border-red">',
				'					<form class="form-horizontal form-bordered">',
				'					<div class="form-group">',
				'					<label class="col-xs-2 control-label no-padding-right">步骤：</label>',
				'							<div class="col-xs-10" style="display: inline-block;">',
				'					<label> <input class="js-prcscb" name="prcsCheck" prcs_type="'+prcsList[i].prcsType+'" process-id="'+prcsList[i].processId+'" type="checkbox" '+(prcsList.length==1?"checked":"")+'> <span class="text">'+prcsList[i].prcsName+'</span>',
				'				</label>',
				'			</div>',
				'		</div>',
				(prcsList[i].opRule==0?
				'			<div class="form-group">'+
				'				<label class="col-xs-2 control-label no-padding-right">主办：</label>'+
				'					<div opRule="'+prcsList[i].opRule+'" class="col-xs-8 op-user_'+prcsList[i].processId+'" style="display: inline-block;" id="zb_'+prcsList[i].processId+'">'+opUserHtml+
				'					</div>'+
				'						<label class="col-xs-2 no-padding-right" style="display: inline-block;"> <a class="selectuser" opFlag="0" data-value="'+prcsList[i].processId+'" opt-id="zb_'+prcsList[i].processId+'">选择</a></label>'+
				'			</div>':''),
				
				
				'			<div class="form-group">',
				'				<label class="col-xs-2 control-label no-padding-right">经办：</label>',
				'				<div class="col-xs-8 jb-user_'+prcsList[i].processId+'" style="display: inline-block;" id="jb_'+prcsList[i].processId+'">'+otherUserHtml+'</div>',
				'					<label class="col-xs-2 no-padding-right" style="display: inline-block;"> <a class="selectuser" opFlag="1" data-value="'+prcsList[i].processId+'" opt-id="jb_'+prcsList[i].processId+'">选择</a></label>',
				'		</div>',
				'	</form>',
				'				</div>',
				'			</div>',
				'		</div>'].join("");
		}
	}
	$("#accordion").html(prcsHtml);
	if(prcsList.length==1)
	{
		$("input[processId="+prcsList[0].processId+"]").attr("checked", "checked")
	}
	if(bpmProcess.concurrentFlag=="0")
	{
		$(".js-prcscb").each(function(){
			$(this).unbind("click").click(function(){
				var processId = $(this).attr("process-id");
				$("input:checkbox[process-id!="+processId+"]").each(function(){
					 $(this).attr("checked",false);
				 });
			});
		});
	}else if(bpmProcess.concurrentFlag=="1")
	{
		$(".js-prcscb").each(function(){
		$(this).unbind("click").click(function(){
			var prcsType = $(this).attr("prcs_type");
			console.log(prcsType);
			if(prcsType=="2")
			{
				$("input:checkbox[prcs_type!=2]").each(function(){
					 $(this).attr("checked",false);
				 });
			}
		})
		});
	}else if(bpmProcess.concurrentFlag=="2")
	{
		$("input:checkbox[prcs_type!=2]").each(function(){
			$(this).attr("checked", true)
			$(this).unbind("click").click(function(){
						return false;
					})
		})
		$("input:checkbox[prcs_type=2]").unbind("click").click(function(){
			return false;
		})
	}
}

function getSearchUserForMobile(Obj) {
	$(".glyphicon-search").unbind("click").click(function(){
		$('#sendToUser').trigger("click");
	});
	$("#J_ListContent").empty();
	$.ajax({
		url : '/ret/unitget/getSearchUserForMobile',
		type : "post",
		dataType : "json",
		data:{
			search:$("#search").val()
			},
		success : function(data) {
			var ret = data.list;
			if (ret) {
				$('#J_ListContent').prepend(template('J_ListHtml', {
					list : ret
				}));
				$myAs.actionSheet('open');
				$(".actionsheet-item").unbind("click").click(function(){
					var accountId = $(this).attr("data-value");
					var userName = $(this).text();
					var id = $(Obj).attr("opt-id");
					var vid = $("#"+id).attr("data-value");
					var vname = $("#"+id).val();
					var tempAccountId=[];
					var tempUserName=[];
					if(vid!=null&&vid!="")
					{
						tempAccountId = vid.split(",");
					}
					if(vname!=null&&vname!="")
					{
						tempUserName = vname.split(",");
					}
					tempAccountId.push(accountId);
					tempUserName.push(userName);
					$("#"+id).attr("data-value",unique(tempAccountId).join(","));
					$("#"+id).val(unique(tempUserName).join(","))
				})
			}
		}
	});

}

function getBpmUserList(processId) {
		$.ajax({
			url : '/ret/bpmget/getUserInfoInPrivForMobile',
			type : "post",
			dataType : "json",
			data:{
				processId:processId,
				search:$("#search").val()
				},
			success : function(data) {
				var ret = data.list;
				if (ret) {
					// 该示例使用了arttemplate模板引擎，当然你也可以用其他方式
					$('#J_ListContent').prepend(template('J_ListHtml', {
						list : ret
					}));
					$(".actionsheet-item").unbind("click").click(function(){
						var accountId = $(this).attr("data-value");
						var userName = $(this).text();
						var opflag = $('#J_ActionSheet').attr("opflag");
						var processId = $('#J_ActionSheet').attr("processId");
						var optId = $(this).attr("opt-id");
						if(opflag=="0")
						{
							if($(".jb-user_"+processId).find("div[data-value="+accountId+"]").length>0)
							{
								$(".jb-user_"+processId).find("div[data-value="+accountId+"]").remove();
							}
							$(".op-user_"+processId).html('<div class="alert alert-danger fade in" style="width: 80px;font-size: 12px;padding:0px;margin:0px 5px 5px 0px" data-value="'+accountId+'"><button class="close" data-dismiss="alert" style="color: white;font-size: 14px;opacity:1;">×</button>'+userName+'</div>');
							$myAs.actionSheet('close');
						}else if(opflag=="1")
						{
							if($(".jb-user_"+processId).find("div[data-value="+accountId+"]").length==0)
							{
								if($(".op-user_"+processId).find("div[data-value="+accountId+"]").length>0)
								{
									top.layer.msg(userName+"已是主办人员，不能添加为经办人员！");
								}else
								{
									$(".jb-user_"+processId).append('<div class="alert alert-danger fade in" style="width: 80px;font-size: 12px;padding:0px;margin:0px 5px 5px 0px;display:inline-block;" data-value="'+accountId+'"><button class="close" data-dismiss="alert" style="color: white;font-size: 14px;opacity:1;">×</button>'+userName+'</div>');
								}
							}else
							{
								top.layer.msg("经办人完已选择，不能重复选择！")
							}
							
						}
					})
				}
			}
		});

}

function getBpmProcess()
{
	$.ajax({
		url : "/ret/bpmget/getBpmProcessPrcs",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			processId : processId
		},
		success : function(data) {
			if(data.status=="200")
			{
				bpmProcess = data.list;
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



function goNext(checkedPrcsNextInfo) {
	$.ajax({
		url : "/set/bpmset/goNextProcess",
		type : "post",
		dataType : "json",
		data : {
			runId : runId,
			runProcessId : runProcessId,
			msgType : getCheckBoxValue("msgType"),
			msgContent : $("#msg-content").val(),
			autoSendUser:$("#sendToUser").attr("data-value"),
			nextPrcsInfo : JSON.stringify(checkedPrcsNextInfo)
		},
		success : function(data) {
			if (data.status == "200") {
				$("#goNextdiv").modal("hide");
				top.layer.msg(data.msg);
				open("/mobile/bpm/mybpmlist","_self");
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}