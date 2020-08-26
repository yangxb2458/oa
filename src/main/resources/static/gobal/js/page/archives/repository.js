$(function(){
	$(".js-add-save").unbind("click").click(function(){
		addRepository();
	})
})
function addRepository()
{
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/archivesset/insertArchivesRepository",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			title:$("#title").val(),
			manageUser:$("#manageUser").attr("data-value"),
			userPriv:$("#userPriv").attr("data-value"),
			levelPriv:$("#levelPriv").attr("data-value"),
			deptPriv:$("#deptPriv").attr("data-value"),
			remark:$("#remark").val()
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