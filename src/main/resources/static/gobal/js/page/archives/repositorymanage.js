$(function(){
	$.ajax({
		url : "/ret/archivesget/getArchivesRepositoryList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var recordList = data.list;
				var html="";
				for(var i=0;i<recordList.length;i++)
				{
					html+="<tr><td>"+(i+1)+"</td><td>"+recordList[i].title+"</td><td>"+getUserNameByStr(recordList[i])+"</td>" +
							"<td>"+recordList[i].createTime+"</td><td>"+recordList[i].createUserName+"</td><td><a href=\"javascript:void(0);edit('" + recordList[i].repositoryId + "')\" class=\"btn btn-primary btn-xs\">编辑</a>&nbsp;&nbsp;" +
									"<a href=\"javascript:void(0);details('" + recordList[i].repositoryId + "')\" class=\"btn btn-sky btn-xs\">详情</a>&nbsp;&nbsp;"+
									"<a href=\"javascript:void(0);deleteRepository('" + recordList[i].repositoryId + "')\" class=\"btn btn-darkorange btn-xs\">删除</a>" +
									"</td></tr>";
				}
				$("#repositorylist").html(html);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				console.log(data.msg);
				}
		}
	});
	$(".js-back-btn").unbind("click").click(function(){
		goback();
	})
})
function details(repositoryId)
{
	window.open("/app/core/archives/repositorydetails?repositoryId="+repositoryId);
}
function deleteRepository(repositoryId)
{
	if(confirm("确定删除当前卷库吗？"))
    {
	$.ajax({
		url : "/set/archivesset/deleteArchivesRepository",
		type : "post",
		dataType : "json",
		data:{repositoryId:repositoryId},
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
function edit(repositoryId)
{
	$("#repositorylistdiv").hide();
	$("#repositorydiv").show();
	$.ajax({
		url : "/ret/archivesget/getArchivesRepositoryById",
		type : "post",
		dataType : "json",
		data:{
			repositoryId:repositoryId,
		},
		success : function(data) {
			if(data.status=="200")
			{
				var recordInfo = data.list;
				for(var id in recordInfo)
				{
					if(id=="userPriv")
					{
						$("#"+id).attr("data-value",(recordInfo[id]));
						$("#"+id).val(getUserNameByStr(recordInfo[id]));
					}else if(id=="deptPriv")
					{
						$("#"+id).attr("data-value",(recordInfo[id]));
						$("#"+id).val(getDeptNameByDeptIds(recordInfo[id]));
					}else if(id=="levelPriv")
					{
						$("#"+id).attr("data-value",(recordInfo[id]));
						$("#"+id).val(getUserLevelStr(recordInfo[id]));
					}else if(id=="manageUser")
					{
						$("#"+id).attr("data-value",(recordInfo[id]));
						$("#"+id).val(getUserNameByStr(recordInfo[id]));
					}else
					{
						$("#"+id).val(recordInfo[id]);
					}
				}
				$(".js-update-save").unbind("click").click(function(){
					updateRepository(repositoryId);
				})
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

function updateRepository(repositoryId)
{
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/archivesset/updateArchivesRepository",
		type : "post",
		dataType : "json",
		data:{
			repositoryId:repositoryId,
			sortNo:$("#sortNo").val(),
			title:$("#title").val(),
			managerUser:$("#managerUser").attr("data-value"),
			userPriv:$("#userPriv").attr("data-value"),
			levelPriv:$("#levelPriv").attr("data-value"),
			deptPriv:$("#deptPriv").attr("data-value"),
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

function goback()
{
	$("#repositorydiv").hide();
	$("#repositorylistdiv").show();
}

