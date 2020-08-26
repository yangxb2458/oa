$(function(){
	$.ajax({
		url : "/ret/vehicleget/getVehicleOperatorByOrgId",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				$("#optUser").attr("data-value",data.list.optUser);
				$("#optUser").val(getUserNameByStr(data.list.optUser));
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
	});
	$(".js-set").unbind("click").click(function(){
		setOperator();
	})
});

function setOperator()
{
	$.ajax({
		url : "/set/vehicleset/setVehicleOperator",
		type : "post",
		dataType : "json",
		data:{optUser:$("#optUser").attr("data-value")},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
	});

}