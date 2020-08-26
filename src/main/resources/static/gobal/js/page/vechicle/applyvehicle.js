$(function(){
	jeDate("#beginTime", {
		format: "YYYY-MM-DD hh:mm",
		minDate:getSysDate()
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD hh:mm",
		minDate:getSysDate()
	});
	$("#applyBtn").unbind("click").click(function(){
		apply();
	});
	getOptUser();
})
function apply()
{
	$.ajax({
		url : "/set/vehicleset/insertVehicleApply",
		type : "post",
		dataType : "json",
		data:{
			type:$("#type").val(),
			usedUser:$("#usedUser").attr("data-value"),
			cause:$("#cause").val(),
			sourceAddress:$("#sourceAddress").val(),
			destination:$("#destination").val(),
			mileage:$("#mileage").val(),
			card:$("#card").val(),
			beginTime:$("#beginTime").val(),
			endTime:$("#endTime").val(),
			passenger:$("#passenger").val(),
			driver:$("#driver").val(),
			optUser:$("#optUser").val(),
			attach:$("#hrattach").attr("data_value"),
			remark:$("#remark").val()
		},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				location.reload();
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
	})
}

function getOptUser()
{
	$.ajax({
		url : "/ret/vehicleget/getVehicleOperatorByOrgId",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				getOptUserSelect(data.list.optUser);
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


function getOptUserSelect(accountStrs)
{
	$.ajax({
		url : "/ret/unitget/getAllUserInfoByAccountList",
		type : "post",
		dataType : "json",
		data:{accountStrs:accountStrs},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
				$("#optUser").html("");
			}else
				{
				var html="";
					for(var i=0;i<data.list.length;i++)
						{
							html+="<option value='"+data.list[i].accountId+"'>"+data.list[i].userName+"</option>"
						}
					$("#optUser").html(html);
				}
		}
	})
}