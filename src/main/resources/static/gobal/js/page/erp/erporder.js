$(function() {
	jeDate("#delivery", {
	    format: "YYYY-MM-DD"
	});
	if($("#orderId").val()!="")
		{
		$("#savebtn").hide();
		$("#upatebtn").show();
		}else
			{
			$("#savebtn").show();
			$("#upatebtn").hide();
			}
});
function save()
{
	$.ajax({
		url : "/set/erpset/insertErpOrder",
		type : "post",
		dataType : "json",
		data:{
			orderCode:$("#orderCode").val(),
			orderTitle:$("#orderTitle").val(),
			customer:$("#customer").val(),
			linkName:$("#linkName").val(),
			tel:$("#tel").val(),
			payType:$("#payType").val(),
			delivery:$("#delivery").val(),
			address:$("#address").val(),
			attach:$("#attach").attr("data_value"),
			remark:$("#remark").val(),
			tax:$("#tax").val(),
			packCharges:$("#packCharges").val(),
			freight:$("#freight").val(),
			otherCharges:$("#otherCharges").val()
			},
		success : function(data) {
			if(data.status="200")
				{
				top.layer.msg(data.msg);
				location.href=data.redirect
				}else
					{
					console.log(data.msg);
					}
		}
	});
}

function update()
{
	$.ajax({
		url : "/set/erpset/updateErpOrder",
		type : "post",
		dataType : "json",
		data:{
			orderId:$("#orderId").val(),
			orderCode:$("#orderCode").val(),
			orderTitle:$("#orderTitle").val(),
			customer:$("#customer").val(),
			linkName:$("#linkName").val(),
			tel:$("#tel").val(),
			payType:$("#payType").val(),
			delivery:$("#delivery").val(),
			address:$("#address").val(),
			attach:$("#attach").attr("data_value"),
			remark:$("#remark").val(),
			tax:$("#tax").val(),
			packCharges:$("#packCharges").val(),
			freight:$("#freight").val(),
			otherCharges:$("#otherCharges").val()
			},
		success : function(data) {
			if(data.status="200")
				{
				top.layer.msg(data.msg);
				}else
					{
					console.log(data.msg);
					}
		}
	});
}