$(function(){
	getDocumentFormSelectList();
	getDocumentFlowInfo();
})
	$("#delbut").unbind("click").click(function() {
		if (confirm("确定删除当前流程吗？")) {
			$.ajax({
				url : "/set/documentset/deleteDocumentFlow",
				type : "post",
				dataType : "json",
				data : {
					flowId : flowId
				},
				success : function(data) {
					if (data.status == 500) {
						console.log(data.msg);
					} else {
						top.layer.msg(data.msg);
					}
				}
			});
		} else {
			return;
		}
	});

$("#designerbut").unbind("click").click(function() {
	window.open("/app/core/document/documentflowdesigner?flowId=" + flowId);
});
function getDocumentFlowInfo()
{
	$.ajax({
		url : "/ret/documentget/getDocumentFlowById",
		type : "post",
		dataType : "json",
		data:{flowId:flowId},
		success : function(data) {
			if(data.status=="200")
			{
				var flowInfo = data.list;
				for(var id in flowInfo)
				{
					if(id=="autoStyle")
					{
						$("input:radio[name='autoStyle'][value='"+ flowInfo[id] + "']").attr("checked", true);
					}else if(id=="flowCache")
					{
						$("input:radio[name='flowCache'][value='"+ flowInfo[id] + "']").attr("checked", true);
					}else if(id=="attachPriv")
					{
						$("input:radio[name='attachPriv'][value='"+ flowInfo[id] + "']").attr("checked", true);
					}else if(id=="printFlag")
					{
						$("input:radio[name='printFlag'][value='"+ flowInfo[id] + "']").attr("checked", true);
					}else if(id=="queryPriv")
					{
						$("#queryPriv").attr("data-value", flowInfo[id]);
						$("#queryPriv").val(getUserNameByStr(flowInfo[id]));
					}else if(id=="managePriv")
					{
						$("#managePriv").attr("data-value", flowInfo[id]);
						$("#managePriv").val(getUserNameByStr(flowInfo[id]));
					}else
					{
						$("#"+id).val(flowInfo[id]);
					}
				}
				getFormField(flowInfo.formId,flowInfo.printField)
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
			}
		})
}

function getFormField(formId,printField)
{
	$.ajax({
		url : "/ret/documentget/getDocumentFormFieldByFormId",
		type : "post",
		dataType : "json",
		data : {
			formId : formId
		},
		success : function(data) {
			if (data.status == "200") {
				var html="";
				for(var i=0;i<data.list.length;i++)
					{
					if(data.list[i].name!='ID'&&data.list[i].name!='RUN_ID'&&data.list[i].name!='ORG_ID')
						{
						var name = toCamel(data.list[i].name);
						html+="<label><input type=\"checkbox\" value=\""+name+"\" class=\"colored-blue\" name=\"printField\"";
						if(((","+printField+",").indexOf((","+name+",")))>=0)
							{
							html+=" checked=\"checked\"";
							}
						html+="><span class=\"text\">"+data.list[i].title+"</span></label>";
						}
					}
			$("#printFieldDiv").html(html);
			}else if(data.status=="100")
			{
				tap.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
		});
}
function getDocumentFormSelectList()
{
	$.ajax({
		url : "/ret/documentget/getDocumentFormSelectList",
		type : "post",
		dataType : "json",
		async : false,
		success : function(data) {
			if(data.status=="200")
			{
				var html="";
				for(var i=0;i<data.list.length;i++)
				{
					html+="<option value=\""+data.list[i].formId+"\">"+data.list[i].formTitle+"</option>";
				}
				$("#formId").html(html);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
			}
		})
}

$("#updatabut").unbind("click").click(function() {
		$("#form").bootstrapValidator('validate');
		var flag = $('#form').data('bootstrapValidator').isValid();
		if (flag) {
		$.ajax({
			url : "/set/documentset/updateDocumentFlow",
			type : "post",
			dataType : "json",
			data : {
				flowId:flowId,
				sortNo : $("#sortNo").val(),
				flowName : $("#flowName").val(),
				documentType : $("#documentType").val(),
				formId : $("#formId").val(),
				remark : $("#remark").val(),
				docNumRule : $("#docNumRule").val(),
				beginDocNum : $("#beginDocNum").val(),
				freeToOther : $("#freeToOther").val(),
				autoStyle : $('input:radio[name=autoStyle]:checked').val(),
				flowCache : $('input:radio[name=flowCache]:checked').val(),
				attachPriv : $('input:radio[name=attachPriv]:checked').val(),
				printFlag : $('input:radio[name=printFlag]:checked').val(),
				queryPriv : $("#queryPriv").attr("data-value"),
				printField:getCheckBoxValue("printField"),
				managePriv : $("#managePriv").attr("data-value")
			},
			success : function(data) {
				if (data.status == 500) {
					console.log(data.msg);
				} else {
					top.layer.msg(data.msg);
					window.location.href = "/app/core/document/documentflow";
				}
			}
		});
		}
	});
