function getBiType(el)
{
	$.ajax({
		url : "/ret/biget/getBiType",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200"){
				for(var i=0;i<data.list.length;i++)
					{
					$("#"+el).append("<option value=\""+data.list[i].biTypeId+"\">"+data.list[i].biTypeEnName+" "+data.list[i].biTypeCnName+"</option>")
					}
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