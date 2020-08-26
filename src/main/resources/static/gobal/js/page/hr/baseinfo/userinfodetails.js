$(function(){
	$.ajax({
		url : "/ret/hrget/getHrUserInfoById",
		type : "post",
		dataType : "json",
		data:{
			userId:userId
		},
		success : function(data) {
			if(data.status=="200")
			{
				var userInfo = data.list;
				for(var id in userInfo)
				{
					if(id=="attach")
					{
						$("#hrattach").attr("data_value", data.list.attach);
						createAttach("hrattach", 1);
					}else if(id=="accountId")
					{
						$("#"+id).html("【"+userInfo[id]+"】"+getUserNameByStr(userInfo[id]));
					}else if(id=="deptId")
					{
						$("#"+id).html(getHrDeptNameByStr(userInfo[id]));
					}else if(id=="leaveId")
					{
						$("#"+id).html(getHrUserLevelByStr(userInfo[id]));
					}else if(id=="photos")
					{
						$("#file_img").attr("src","/sys/file/getStaticImg?module=hrphotos&fileName="+userInfo[id]);
					}else if(id=="resume")
					{
						$("#resume").html(userInfo[id]);
					}else if(id=="sex")
					{
						if(userInfo[id]=="0")
						{
							$("#sex").html("男");
						}else if(userInfo[id]=="1")
						{
							$("#sex").html("女");
						}else
						{
							$("#sex").html("其他");
						}
					}else if(id=="nativePlace")
					{
						$("#"+id).html(getHrClassCodeName("nativePlace",userInfo[id]));
					}else if(id=="politicalStatus")
					{
						$("#"+id).html(getHrClassCodeName("politicalStatus",userInfo[id]));
					}else if(id=="maritalStatus")
					{
						if(userInfo[id]=="0")
						{
							$("#"+id).html("未婚");
						}else if(userInfo[id]=="1")
						{
							$("#"+id).html("已婚");
						}else if(userInfo[id]=="2")
						{
							$("#"+id).html("离异");
						}else if(userInfo[id]=="3")
						{
							$("#"+id).html("丧偶");
						}
					}else if(id=="staffType")
					{
						$("#"+id).html(getHrClassCodeName("staffType",userInfo[id]));
					}else if(id=="workType")
					{
						$("#"+id).html(getHrClassCodeName("workType",userInfo[id]));
					}else if(id=="occupation")
					{
						$("#"+id).html(getHrClassCodeName("occupation",userInfo[id]));
					}else if(id=="workStatus")
					{
						$("#"+id).html(getHrClassCodeName("workStatus",userInfo[id]));
					}else if(id=="workJob")
					{
						$("#"+id).html(getHrClassCodeName("workJob",userInfo[id]));
					}else if(id=="persentPosition")
					{
						$("#"+id).html(getHrClassCodeName("persentPosition",userInfo[id]));
					}else if(id=="workLeave")
					{
						$("#"+id).html(getHrClassCodeName("workLeave",userInfo[id]));
					}else if(id=="highsetShool")
					{
						$("#"+id).html(getHrClassCodeName("highsetShool",userInfo[id]));
					}else if(id=="highsetDegree")
					{
						$("#"+id).html(getHrClassCodeName("highsetDegree",userInfo[id]));
					}else if(id=="wagesLevel")
					{
						$("#"+id).html(getWagesTitle(userInfo[id]));
					}else if(id=="attendType")
					{
						$("#"+id).html(getAttendTypeName(userInfo[id]));
					}else
					{
						$("#"+id).html(userInfo[id]);
					}
				}
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
	});
});