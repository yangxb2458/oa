$(function(){
	jeDate("#buyTime", {
		format: "YYYY-MM-DD",
		maxDate:getSysDate()
	});
	jeDate("#yearlyTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate()
	});
	jeDate("#insureTime", {
		format: "YYYY-MM-DD",
		minDate:getSysDate()
	});
	$('#remark').summernote({ height:300 });
	getCodeClass("nature","vehicle");
	$(".js-add-save").unbind("click").click(function(){
		addVehicleInfo();
	})
})

function addVehicleInfo()
{
	$.ajax({
		url : "/set/vehicleset/insertVehicleInfo",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			vehicleNumber:$("#vehicleNumber").val(),
			brand:$("#brand").val(),
			model:$("#model").val(),
			displacement:$("#displacement").val(),
			engineNo:$("#engineNo").val(),
			color:$("#color").val(),
			seats:$("#seats").val(),
			frameNo:$("#frameNo").val(),
			certification:$("#certification").val(),
			nature:$("#nature").val(),
			type:$("#type").val(),
			manageDept:$("#manageDept").attr("data-value"),
			managePhone:$("#managePhone").val(),
			onwer:$("#onwer").attr("data-value"),
			onwerPhone:$("#onwerPhone").attr("data-value"),
			caruser:$("#caruser").attr("data-value"),
			caruserPhone:$("#caruserPhone").val(),
			price:$("#price").val(),
			tax:$("#tax").val(),
			buyTime:$("#buyTime").val(),
			mileage:$("#mileage").val(),
			yearlyTime:$("#yearlyTime").val(),
			insureTime:$("#insureTime").val(),
			userPriv:$("#userPriv").attr("data-value"),
			deptPriv:$("#deptPriv").attr("data-value"),
			levelPriv:$("#levelPriv").attr("data-value"),
			remark:$("#remark").code(),
			photo:$("#file").attr("data-value"),
			attach:$("#vechicleattach").attr("data_value")
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
	});
}

function delPhotos()
{
	$("#file_img").attr("src","/gobal/img/other/car.jpg");
	$("#file").attr("data-value","");
}