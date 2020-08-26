$(function(){
	getDocumentFlowList();
})

function getDocumentFlowList()
{
	$.ajax({
		url : "/ret/documentget/getDocumentFlowList",
		type : "post",
		dataType : "json",
		success : function(data) {
			var html1="";
			var html2="";
			for(var i=0;i<data.list.length;i++)
			{
				var optHtml ="";
				var statusStr="";
				if(data.list[i].status=="1")
				{
					statusStr="已停用";
					optHtml='<a href="javascript:void(0);setStatusFlow(\''+data.list[i].flowId+'\',\'0\')" class="btn btn-primary btn-xs">启用</a>';
				}else
				{
					statusStr="使用中";
					optHtml='<a href="javascript:void(0);setStatusFlow(\''+data.list[i].flowId+'\',\'1\')" class="btn btn-maroon btn-xs">停用</a>';
				}	
				if(data.list[i].documentType=="1")
				{
					html1+=['<div class="col-lg-3 col-sm-6 col-xs-12 flowdiv">',
						'									<div class="flowhead">收文流程<span style="float:right">'+statusStr+'</span></div>',
						'									<div>',
						'                                        <span class="glyphicon glyphicon-random" style="font-size:30px;"></span>',
						'										<a href="/app/core/document/documentflow?view=edit&flowId='+data.list[i].flowId+'" class="flowtitle">'+data.list[i].flowName+'</a>',
						'									</div>',
						'									<div style="text-justify:distribute-all-lines;text-align:justify;text-align-last:justify;">',
						'									<a href="javascript:void(0);clearData(\''+data.list[i].flowId+'\')" class="btn btn-sky btn-xs">初始化</a>',
						'									<a href="javascript:void(0);clone(\''+data.list[i].flowId+'\')" class="btn btn-success btn-xs">克隆</a>',
						'									'+optHtml,
						'									<a href="/ret/documentget/getDocumentFlowXmlFile?flowId='+data.list[i].flowId+'" class="btn btn-palegreen btn-xs">导出</a>',
						'									</div>',
						'									<div class="row flowfoot"><a href="javascript:void(0);gotemplate(\''+data.list[i].formId+'\');" class="btn btn-link" style="color: #fff !important">'+data.list[i].formTitle+'</a><a href="javascript:void(0);goDocumentFlowDesigner(\''+data.list[i].flowId+'\');" style="float: right" class="btn btn-success">流程设计</a></div>',
						'								</div>'].join("");
				}else if(data.list[i].documentType=="2")
				{
					html2+=['<div class="col-lg-3 col-sm-6 col-xs-12 flowdiv">',
						'									<div class="flowhead">发文流程<span style="float:right">'+statusStr+'</span></div>',
						'									<div>',
						'                                        <span class="glyphicon glyphicon-random" style="font-size:30px;"></span>',
						'										<a href="/app/core/document/documentflow?view=edit&flowId='+data.list[i].flowId+'" class="flowtitle">'+data.list[i].flowName+'</a>',
						'									</div>',
						'									<div style="text-justify:distribute-all-lines;text-align:justify;text-align-last:justify;">',
						'									<a href="javascript:void(0);clearData(\''+data.list[i].flowId+'\')" class="btn btn-sky btn-xs">初始化</a>',
						'									<a href="javascript:void(0);clone(\''+data.list[i].flowId+'\')" class="btn btn-success btn-xs">克隆</a>',
						'									'+optHtml,
						'									<a href="/ret/documentget/getDocumentFlowXmlFile?flowId='+data.list[i].flowId+'" class="btn btn-palegreen btn-xs">导出</a>',
						'									</div>',
						'									<div class="row flowfoot"><a href="javascript:void(0);gotemplate(\''+data.list[i].formId+'\');" class="btn btn-link" style="color: #fff !important">'+data.list[i].formTitle+'</a><a href="javascript:void(0);goDocumentFlowDesigner(\''+data.list[i].flowId+'\');" style="float: right" class="btn btn-success">流程设计</a></div>',
						'								</div>'].join("");
				}
			}
			$("#document1").html(html1);
			$("#document2").html(html2);
		}
	});
}
function gotemplate(formId)
{
window.open("/app/core/document/documentformtemplate?formId="+formId);
}
function goDocumentFlowDesigner(flowId)
{
	window.open("/app/core/document/documentflowdesigner?flowId=" + flowId);
}

function clone(flowId)
{
	if(window.confirm('您确要需要克隆该流程吗?')){
		$.ajax({
			url : "/set/documentset/cloneDocumentFlow",
			type : "post",
			dataType : "json",
			data : {
				flowId : flowId
			},
			success : function(data) {
				if(data.status=="200")
				{
					location.reload();
				}else if(data.status=="100")
				{
					top.layer.msg(data.msg);
				}else
				{
					console.log(data.msg);
				}
			}
		});
		}else
		{
			return;
		}
}

function clearData(flowId)
{
	if(window.confirm('历史数据会丢失!您确认初始化该流程吗?')){
	$.ajax({
		url : "/set/documentset/clearDocumentFlowData",
		type : "post",
		dataType : "json",
		data : {
			flowId : flowId
		},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);	
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	});
	}else
	{
		return;
	}
}


function setStatusFlow(flowId,status)
{
	if(window.confirm('您确定要停用当前流程吗?')){
	$.ajax({
		url : "/set/documentset/updateDocumentFlow",
		type : "post",
		dataType : "json",
		data : {
			flowId:flowId,
			status:status
		},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				location.reload();
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
}