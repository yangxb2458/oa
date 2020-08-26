$(function(){
	getBpmBiTemplateById();
})
function getBpmBiTemplateById()
{
	$.ajax({
		url : "/ret/bpmget/getBpmBiTemplateById",
		type : "post",
		dataType : "json",
		data:{templateId:templateId},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				var recordInfo = data.list;
				for(var id in recordInfo)
				{
					if(id=="userPriv")
					{
						
					}else if(id=="deptPriv")
					{
						
					}else if(id=="levelPriv")
					{
						
					}else
					{
						$("#"+id).html(recordInfo[id]);
					}
				}
			}
		}
	})
}