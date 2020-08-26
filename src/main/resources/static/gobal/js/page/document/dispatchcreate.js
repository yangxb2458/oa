$(function(){
	getDispatchDocumentList();
})
function getDispatchDocumentList()
{
	$.ajax({
		url : "/ret/documentget/getMyDocumentDispatchFlowList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var html="";
				for(var i=0;i<data.list.length;i++)
				{
					html+=['<div class="col-xs-12 col-sm-6 col-md-3">',
'                            <div class="plan">',
'                                <div class="header bordered-azure">'+data.list[i].flowName+'</div>',
'                                <ul style="margin:0px;">',
'                                    <li style="padding:5px 0px;"><a style="cursor: pointer;" href=\"javascript:void(0);gotemplate(\''+data.list[i].formId+'\')\">发文单模版明</a></li>',
'                                    <li style="padding:5px 0px;"><a style="cursor: pointer;" href=\"javascript:void(0);flowchat(\''+data.list[i].flowId+'\')\">预设发文流程</a></li>',
'                                    <li style="padding:5px 0px;"><a style="cursor: pointer;" href=\"javascript:void(0);showFunction(\''+data.list[i].flowId+'\')\">流程说明</a></li>',
'                                </ul>',
'                                <a class="signup bg-azure" href=\"javascript:void(0);sendDocument(\''+data.list[i].flowId+'\')\">创建发文</a>',
'                            </div>',
'                        </div>'].join("");
				}
				if(html!="")
				{
					$(".pricing-container").html(html);
				}
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else{
				console.log(data.msg);
			}
		}
	})
}

function flowchat(flowId)
{
	open("/app/core/document/documentDesignFlowChart?flowId="+flowId,"_self");
}
function gotemplate(formId)
{
window.open("/app/core/document/documentformtemplate?formId="+formId);
}

function sendDocument(flowId) {
	$("#senddocument").modal("show");
	getDocNumByDocumentFlow(flowId);
	$("#sendDocumentBtn").unbind("click").click(function() {
		if($("#flowTitle").val()==""||$("#flowTitle").val()==null)
		{
			top.layer.msg("公文标题标题不能为空！");
			return;
		}
		$.ajax({
			url : "/set/documentset/startDocument",
			type : "post",
			dataType : "json",
			data : {
				flowId : flowId,
				flowTitle : $("#flowTitle").val(),
				follow : getCheckBoxValue("follow"),
				urgency : $("#urgency").val()
			},
			success : function(data) {
				if (data.status == 500) {
					console.log(data.msg);
				} else if (data.status == 500) {
					top.layer.msg(data.msg);
				} else {
					$("#senddocument").modal("hide");
					open(data.redirect,"_self");
				}
			}
		});
	});
}

function showFunction(flowId)
{
	$("#flowFunction").html("");
	$("#documentfunction").modal("show");
	$.ajax({
		url : "/ret/documentget/getDocumentFlow",
		type : "post",
		dataType : "json",
		data : {
			flowId : flowId
		},
		success : function(data) {
			if (data.status == 500) {
				console.log(data.msg);
			} else if (data.status == 500) {
				top.layer.msg(data.msg);
			} else {
				$("#flowFunction").html(data.list.remark);
			}
		}
	});
}


function getDocNumByDocumentFlow(flowId)
{
	$.ajax({
		url : "/ret/documentget/getDocNumByDocumentFlow",
		type : "post",
		dataType : "json",
		data : {
			flowId : flowId
		},
		success : function(data) {
			if (data.status == 500) {
				console.log(data.msg);
			} else if (data.status == 500) {
				top.layer.msg(data.msg);
			} else {
				$("#flowTitle").val(data.redirect)
			}
		}
	});
}