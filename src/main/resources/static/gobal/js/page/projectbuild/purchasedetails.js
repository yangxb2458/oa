$(function(){
	$.ajax({
		url : "/ret/projectbuildmaterialget/getMaterialPurchaseById",
		type : "post",
		dataType : "json",
		data:{
			purchaseId:purchaseId
		},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else{
				for(var name in data.list)
				{
					if(name=="attach")
					{
						$("#projectbuildattach").attr("data_value",data.list.attach);
						createAttach("projectbuildattach","1");	
					}else if(name=="projectId")
					{
						
					}else if(name=="")
					{
						
					}else
					{
						$("#"+name).html(data.list[name]);
					}
				}
			}
		}
		})
})
