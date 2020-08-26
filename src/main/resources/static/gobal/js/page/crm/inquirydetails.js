$(function(){
	$.ajax({
		url : "/ret/crmget/getCrmInquiry",
		type : "post",
		dataType : "json",
		data : {
			inquiryId:inquiryId
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				for(var id in data.list)
				{
					if(id=="attach")
					{
						
					}else if(id=="cashType")
					{
						if(data.list[id]=="1")
						{
							$("#"+id).html("RMB 人民币");
						}else if(data.list[id]=="2")
						{
							$("#"+id).html("US 美元");
						}
						
					}else if(id=="payType")
					{
						if(data.list[id]=="1")
						{
							$("#"+id).html("进度付款");
						}else if(data.list[id]=="2")
						{
							$("#"+id).html("滚动付款");
						}else if(data.list[id]=="3")
						{
							$("#"+id).html("款到发货");
						}else if(data.list[id]=="4")
						{
							$("#"+id).html("货到付款");
						}
					}else
					{
						$("#"+id).html(data.list[id]);
					}
				}
			}
		}
	})
})
