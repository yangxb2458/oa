$(function(){
	getCodeClass("fileType","volume_file_type");
	jeDate("#sendTime", {
		format: "YYYY-MM-DD",
		isinitVal: true
	});
	getVolumeList();
	$(".js-add-save").unbind("click").click(function(){
		addFile();
	})
})
function addFile()
{
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/archivesset/insertArchivesFile",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			fileCode:$("#fileCode").val(),
			title:$("#title").val(),
			sendOrg:$("#sendOrg").val(),
			subject:$("#subject").val(),
			subheading:$("#subheading").val(),
			fileType:$("#fileType").val(),
			secretLevel:$("#secretLevel").val(),
			sendTime:$("#sendTime").val(),
			volumeId:$("#volumeId").val(),
			pageTotal:$("#pageTotal").val(),
			printTotal:$("#printTotal").val(),
			attach:$("#archivesattach").attr("data_value"),
			isaudit:$("input:radio[name='isaudit']:checked").val(),
			attachPriv:$("input:radio[name='attachPriv']:checked").val(),
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
function getVolumeList()
{
	$.ajax({
		url : "/ret/archivesget/getArchivesVolumeListForSelect",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var recordList = data.list;
				for(var i=0;i<recordList.length;i++)
				{
					$("#volumeId").append("<option value=\""+recordList[i].volumeId+"\">"+recordList[i].volumeTitle+"</option>")
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