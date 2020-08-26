$(function(){
		$.ajax({
		url : "/ret/projectbuildget/getProjectBuildStageById",
		type : "post",
		dataType : "json",
		data : {
			stageId:stageId
		},
		success : function(data) {
			if (data.status == 200) {
				for(name in data.list)
					{
						if(name=="superintendent")
						{
							$("#"+name).html(getUserNameByStr(data.list[name]));
						}else
						{
							$("#"+name).html(data.list[name]);
						}
					}
			} else if(data.status==100)
				{
					top.layer.msg(data.msg);
				}else
				{
				console.log(data.msg);
			}
		}
	});
})