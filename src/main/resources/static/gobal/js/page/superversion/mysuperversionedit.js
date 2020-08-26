$(function(){
	$('#content').summernote({ height:300 });
	getsuperversiontype();
	getsupperversioninfo();
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
	$("#updatebtn").unbind("click").click(function(){
		updatesuperversion();
	});
	getSmsConfig("msgType","supperversion");
	$("#configId").unbind("change").change(function(){
		var leadIds = $(this).find("option:selected").attr("data-value");
		getsuperversionLead(leadIds);
	});
	$("#form").bootstrapValidator('resetForm');
});

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
function getsuperversionLead(accountStrs)
{
	$.ajax({
		url : "/ret/unitget/getAllUserInfoByAccountList",
		type : "post",
		dataType : "json",
		async : false,
		data:{accountStrs:accountStrs},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
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

function updatesuperversion()
{
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/superversionset/updateSuperversion",
		type : "post",
		dataType : "json",
		data:{
			superversionId:superversionId,
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
				//window.location.href = "/app/core/superversion/createsuperverion";
				open("/app/core/superversion/createsuperverion","_self");
				}
		}
	})
	}
}

function getsupperversioninfo()
{
	$.ajax({
		url : "/ret/superversionget/getSuperversionById",
		type : "post",
		dataType : "json",
		data:{superversionId:superversionId},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				for(var name in data.list)
					{
						if(name=="attach")
						{
							$("#superversionattach").attr("data_value", data.list[name]);
							createAttach("superversionattach");
						}else if(name=="handedUser")
						{
							$("#handedUser").attr("data-value",data.list.handedUser);
							$("#handedUser").val(getUserNameByStr(data.list.handedUser));
						}else if(name=="joinUser")
						{
							$("#joinUser").attr("data-value",data.list.joinUser);
							$("#joinUser").val(getUserNameByStr(data.list.joinUser));
						}else if(name=="content")
						{
							$("#content").code(data.list.content);
						}else if(name=="type")
						{
							$("#configId").val(data.list.type);
							getLeasOpt(data.list.type);
							$("#leadId").val(data.list.leadId);
						}else
						{
							if(name!="leadId")
							{
								$("#"+name).val(data.list[name]);
							}
						}
					}
				}
		}
	})
}

function getLeasOpt(configId)
{
	$.ajax({
		url : "/ret/superversionget/getSuperversionConfigById",
		type : "post",
		dataType : "json",
		async : false,
		data:{configId:configId},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
					getsuperversionLead(data.list.leadId)
				}
		}
	})
}