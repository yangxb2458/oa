$(function(){
	$("#savebtn").unbind("click").click(function(){
		update();
	});
});
function update()
{
	$.ajax({
		url : "/set/unitset/updateDdConfig",
		type : "post",
		dataType : "json",
		data:{
			dingdingCorpId:$("#dingdingCorpId").val(),
			dingdingAgentId:$("#dingdingAgentId").val(),
			dingdingAppKey:$("#dingdingAppKey").val(),
			dingdingAppSecret:$("#dingdingAppSecret").val()
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