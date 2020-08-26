$(function(){
	$('#content').summernote({ height:300 });
	getSmsConfig("msgType","supperversion");
	$("#configId").unbind("change").change(function(){
		var leadIds = $(this).find("option:selected").attr("data-value");
		getsuperversionLead(leadIds);
	});
	jeDate("#beginTime", {
		format: "YYYY-MM-DD",
		clearfun:function(elem, val) {
	         $("#form").data('bootstrapValidator').updateStatus('beginTime', 'NOT_VALIDATED').validateField('beginTime');
	       },
	       donefun:function(obj) {
	         $("#form").data('bootstrapValidator').updateStatus('beginTime', 'NOT_VALIDATED').validateField('beginTime');

	       }
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD",
		clearfun:function(elem, val) {
	         $("#form").data('bootstrapValidator').updateStatus('endTime', 'NOT_VALIDATED').validateField('endTime');
	       },
	       donefun:function(obj) {
	         $("#form").data('bootstrapValidator').updateStatus('endTime', 'NOT_VALIDATED').validateField('endTime');

	       }
	});
	getsuperversiontype();
	$("#createbut").unbind("click").click(function(){
		addSupperversion();
	});
	$("#form").bootstrapValidator('resetForm');
});

function addSupperversion()
{
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/superversionset/insertSuperversion",
		type : "post",
		dataType : "json",
		data:{
			title:$("#title").val(),
			type:$("#configId").val(),
			leadId:$("#leadId").val(),
			beginTime:$("#beginTime").val(),
			endTime:$("#endTime").val(),
			handedUser:$("#handedUser").attr("data-value"),
			joinUser:$("#joinUser").attr("data-value"),
			content:$("#content").code(),
			attach:$("#superversionattach").attr("data_value"),
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
				top.layer.msg(data.msg);
				window.location.reload();
				}
		}
	})
	}
}


function getsuperversionLead(accountStrs)
{
	$.ajax({
		url : "/ret/unitget/getAllUserInfoByAccountList",
		type : "post",
		dataType : "json",
		data:{accountStrs:accountStrs},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
				$("#leadId").html("");
			}else
				{
				var html="";
					for(var i=0;i<data.list.length;i++)
						{
							html+="<option value='"+data.list[i].accountId+"'>"+data.list[i].userName+"</option>"
						}
					$("#leadId").html(html);
				}
		}
	})
}

function getsuperversiontype()
{
	$.ajax({
		url : "/ret/superversionget/getAllSuperversionConfigList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				var html="<option value=''>请选择</option>";
					for(var i=0;i<data.list.length;i++)
						{
							html+="<option data-value='"+data.list[i].leadId+"' value='"+data.list[i].configId+"'>"+data.list[i].typeName+"</option>"
						}
					$("#configId").html(html);
				}
		}
	})
}