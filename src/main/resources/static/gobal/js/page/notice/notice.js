$(function(){
	 $('#content').summernote({ height:300 });
	getCodeClass("noticeType","notice");
	$("#noticeType").unbind("change").change(function(){
		$.ajax({
			url : "/ret/noticeget/getRedHeadListByType",
			type : "post",
			dataType : "json",
			data:{noticeType:$(this).val()},
			success : function(data) {
				if(data.status=="200")
					{
						if(data.list.length>0)
							{
							$("#redheaddiv").show();
							var html="<option value=\"\">请选择</option>";
							for(var i=0;i<data.list.length;i++)
								{
								html+="<option value=\""+data.list[i].templateId+"\">"+data.list[i].templateName+"</option>";
								}
							$("#templateType").html(html);
							}else
							{
								$("#redheaddiv").hide();
							}
					}
			}
		});
	});
	getSmsConfig("msgType","notice");
	$("#createbut").unbind("click").click(function(){
		sendNotice();
	});
	jeDate("#sendTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate(),
		isinitVal: true
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate(),
	});
	if(isDocument)
	{
		if(runId!=null&&runId!="")
		{
			$.ajax({
				url : '/ret/documentget/getDocumentPrintData',
				type : "post",
				dataType : "json",
				data : {
					flowId : flowId,
					runId : runId
				},
				success : function(data) {
					if (data.status == "500") {
						console.log(data.msg);
					} else if (data.status == "100") {
						top.layer.msg(data.msg);
					} else {
						$("#content").code(data.list.documentFormHtml);
					}
				}
		});
		}
	}else
	{
		if(runId!=null&&runId!="")
		{
			$.ajax({
				url : '/ret/bpmget/getBpmPrintData',
				type : "post",
				dataType : "json",
				data : {
					flowId : flowId,
					runId : runId
				},
				success : function(data) {
					if (data.status == "500") {
						console.log(data.msg);
					} else if (data.status == "100") {
						top.layer.msg(data.msg);
					} else {
						$("#content").code(data.list.bpmFormHtml);
					}
				}
		});
		}
	}
});


function sendNotice()
{
	$.ajax({
		url : "/set/noticeset/sendNotice",
		type : "post",
		dataType : "json",
		data:{
			noticeTitle:$("#noticeTitle").val(),
			noticeType:$("#noticeType").val(),
			userPriv:$("#userPriv").attr("data-value"),
			deptPriv:$("#deptPriv").attr("data-value"),
			leavePriv:$("#leavePriv").attr("data-value"),
			content:$("#content").code(),
			sendTime:$("#sendTime").val(),
			endTime:$("#endTime").val(),
			attach:$("#noticeattach").attr("data_value"),
			isTop:$("input:radio[name='isTop']:checked").val(),
			attachPriv:$("input:radio[name='attachPriv']:checked").val(),
			msgType:getCheckBoxValue("msgType")
		},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				window.location.reload();
				top.layer.msg(data.msg);
				}
		}
	})
}