$(function(){
	getDocumentFormSelectList();
})

$("#createbut").unbind("click").click(function() {
		$("#form").bootstrapValidator('validate');
		var flag = $('#form').data('bootstrapValidator').isValid();
		if (flag) {
		$.ajax({
			url : "/set/documentset/insertDocumentFlow",
			type : "post",
			dataType : "json",
			data : {
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