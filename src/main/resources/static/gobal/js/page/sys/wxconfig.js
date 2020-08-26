$(function(){
	$("#savebtn").unbind("click").click(function(){
		update();
	});
});
function update()
{
	$.ajax({
		url : "/set/unitset/updateWxConfig",
		type : "post",
		dataType : "json",
		data:{
			wxCorpId:$("#wxCorpId").val(),
			wxAgentId:$("#wxAgentId").val(),
			wxAppKey:$("#wxAppKey").val(),
			wxAppSecret:$("#wxAppSecret").val(),
			wxSyncSecret:$("#wxSyncSecret").val()
		},
		success : function(data) {
			if(data.status==200){
				top.layer.msg(data.msg);
				location.reload();
			}else if(data.status==100)
			{
				top.layer.msg(data.msg);
			}else
				{
				console.log(data.msg);
				}
		}
	});
}