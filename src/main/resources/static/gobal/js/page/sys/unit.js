$(function(){
	$("#savebtn").unbind("click").click(function(){
		update();
	});
});
function update()
{
	$.ajax({
		url : "/set/unitset/updateUnit",
		type : "post",
		dataType : "json",
		data:{
			orgLogo:$("#orgLogo").attr("data-value"),
			orgName:$("#orgName").val(),
			orgTel:$("#orgTel").val(),
			orgAdd:$("#orgAdd").val(),
			orgPost:$("#orgPost").val(),
			orgFax:$("#orgFax").val(),
			orgEmail:$("#orgEmail").val(),
			orgLegalPerson:$("#orgLegalPerson").val(),
			orgBank:$("#orgBank").val(),
			orgBankAccount:$("#orgBankAccount").val(),
			orgTaxNo:$("#orgTaxNo").val()
		},
		success : function(data) {
			if(data.status==200){
				top.layer.msg(data.msg);
				location.reload();
			}else if(data.status==100)
			{
				top.layer.msg(data.msg);
			}else
				{
				console.log(data.msg);
				}
		}
	});
}