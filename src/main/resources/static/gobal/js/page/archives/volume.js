$(function(){
	getCodeClass("voucherType","volume_voucher_type");
	jeDate("#endDate", {
		format: "YYYY-MM-DD",
		isinitVal: true
	});
	jeDate("#beginDate", {
		format: "YYYY-MM-DD",
		isinitVal: true
	});
	getRepositoryList();
	$(".js-add-save").unbind("click").click(function(){
		addVolume();
	})
})
function addVolume()
{
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/archivesset/insertArchivesVolume",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			volumeCode:$("#volumeCode").val(),
			volumeTitle:$("#volumeTitle").val(),
			repositoryId:$("#repositoryId").val(),
			beginDate:$("#beginDate").val(),
			endDate:$("#endDate").val(),
			createOrg:$("#createOrg").val(),
			deptId:$("#deptId").attr("data-value"),
			storagePeriod:$("#storagePeriod").val(),
			secretLevel:$("#secretLevel").val(),
			microNo:$("#microNo").val(),
			voucherType:$("#voucherType").val(),
			voucherBeginNo:$("#voucherBeginNo").val(),
			voucherEndNo:$("#voucherEndNo").val(),
			pageTotal:$("#pageTotal").val(),
			manageUser:$("#manageUser").attr("data-value"),
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
			}else
				{
				console.log(data.msg);
				}
		}
	});
	}
}
function getRepositoryList()
{
	$.ajax({
		url : "/ret/archivesget/getArchivesRepositoryList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var recordList = data.list;
				for(var i=0;i<recordList.length;i++)
				{
					$("#repositoryId").append("<option value=\""+recordList[i].repositoryId+"\">"+recordList[i].title+"</option>")
				}
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				console.log(data.msg);
				}
		}
})
}