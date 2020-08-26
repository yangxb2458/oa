$(function(){
	$.ajax({
		url : "/ret/hrget/getHrLicenceById",
		type : "post",
		dataType : "json",
		data:{licenceId:licenceId},
		success : function(data) {
			if(data.status=="200")
			{
				var licenceInfo = data.list;
				for(var id in licenceInfo)
				{
					if(id=="attach")
					{
						$("#hrattach").attr("data_value", licenceInfo.attach);
						createAttach("hrattach", 1);
					}else if(id=="userId")
					{
						$("#"+id).html(getHrUserNameByStr(licenceInfo[id]));
					}else if(id=="remark")
					{
						$("#remark").html(licenceInfo[id]);
					}else if(id=="licenceType")
					{
						$("#"+id).html(getHrClassCodeName("licenceType",licenceInfo[id]));
					}else
					{
						$("#"+id).html(licenceInfo[id]);
					}
				}
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
		})
})