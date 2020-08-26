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
	$("#createbut").unbind("click").click(function(){
		addTask();
	})
	$("#form").bootstrapValidator('resetForm');
});

function addTask()
{
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/taskset/addTask",
		type : "POST",
		dataType : "json",
		data : {
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