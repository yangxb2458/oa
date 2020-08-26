$(function(){
	 $('#remark').summernote({ height:300 });
		jeDate("#beginTime", {
			format: "YYYY-MM-DD",
			clearfun:function(elem, val) {
		         $("#form").data('bootstrapValidator').updateStatus('beginTime', 'NOT_VALIDATED').validateField('beginTime');
		       },
		       donefun:function(obj) {
		         $("#form").data('bootstrapValidator').updateStatus('beginTime', 'NOT_VALIDATED').validateField('beginTime');

		       }
		});
		getSmsConfig("msgType","task");
		getCodeClass("taskType","task");
		$("#updatebut").unbind("click").click(function(){
			updateTask();
		})
		getTaskInfo();
		$("#form").bootstrapValidator('resetForm');
});

function getTaskInfo()
{
	$.ajax({
		url : "/ret/taskget/getTaskById",
		type : "post",
		dataType : "json",
		data : {
			taskId : taskId
		},
		success : function(data) {
			if(data.status=="200")
			{
				for(var id in data.list)
				{
					if(id=="attach")
					{
						$("#taskattach").attr("data_value", data.list[id]);
						createAttach("taskattach",4);
					}else if(id=="supervisorAccountId")
					{
						$("#"+id).val(getUserNameByStr(data.list.supervisorAccountId));
						$("#"+id).attr("data-value",data.list.supervisorAccountId);
					}else if(id=="userPriv")
					{
						$("#"+id).val(getUserNameByStr(data.list.userPriv));
						$("#"+id).attr("data-value",data.list.userPriv);
					}else if(id=="deptPriv")
					{
						$("#"+id).val(getDeptNameByDeptIds(data.list.deptPriv));
						$("#"+id).attr("data-value",data.list.deptPriv);
					}else if(id=="leavePriv")
					{
						$("#"+id).val(getUserLevelStr(data.list.leavePriv));
						$("#"+id).attr("data-value",data.list.leavePriv);
						
					}else if(id=="chargeAccountId")
					{
						$("#"+id).val(getUserNameByStr(data.list.chargeAccountId));
						$("#"+id).attr("data-value",data.list.chargeAccountId);
					}else if(id=="participantAccountId")
					{
						$("#"+id).val(getUserNameByStr(data.list.participantAccountId));
						$("#"+id).attr("data-value",data.list.participantAccountId);
					}else if(id=="remark")
					{
						$('#remark').code(data.list.remark)
					}else if(id=="isTop")
					{
						$("input:radio[name='isTop'][value='" + data.list[id] + "']").attr("checked", "checked");
					}else if(id=="attachPriv"){
						$("input:radio[name='attachPriv'][value='"+data.list[id]+"']").attr("checked","checked");
					}else
					{
						$("#"+id).val(data.list[id])
					}
				}
				
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



function updateTask()
{
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/taskset/updateTask",
		type : "POST",
		dataType : "json",
		data : {
			taskId:taskId,
			taskName : $("#taskName").val(),
			taskType : $("#taskType").val(),
			isTop: $('input:radio[name="isTop"]:checked').val(),
			deptPriv : $("#deptPriv").attr("data-value"),
			userPriv : $("#userPriv").attr("data-value"),
			leavePriv : $("#leavePriv").attr("data-value"),
			chargeAccountId : $("#chargeAccountId").attr("data-value"),
			participantAccountId : $("#participantAccountId").attr("data-value"),
			supervisorAccountId : $("#supervisorAccountId").attr("data-value"),
			attach : $("#taskattach").attr("data_value"),
			attachPriv:$("input:radio[name='attachPriv']:checked").val(),
			beginTime : $("#beginTime").val(),
			endTime : $("#endTime").val(),
			msgType:getCheckBoxValue("msgType"),
			duration : $("#duration").val(),
			remark : $('#remark').code()
		},
		async : false,
		error : function(e) {
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