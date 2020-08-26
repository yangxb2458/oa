$(function(){
	$(".js-add-save").unbind("click").click(function(){
		addUserInfo();
	})
	$(".js-auto-select").each(function(){
		var module = $(this).attr("module");
		createAutoSelect(module);
	})
	getWagesLevelListForSelect();
	jeDate("#birthDay", {
		format: "YYYY-MM-DD",
		maxDate:getSysDate(),
	});
	jeDate("#joinPartyTime", {
		format: "YYYY-MM-DD",
		maxDate:getSysDate(),
	});
	jeDate("#employedTime", {
		format: "YYYY-MM-DD",
		maxDate:getSysDate(),
	});
	jeDate("#graduationTime", {
		format: "YYYY-MM-DD",
		maxDate:getSysDate(),
	});
	 $('#resume').summernote({ height:300 });
	 getAttendType("attendType");
})

function getWagesLevelListForSelect()
{
	$.ajax({
		url : "/ret/hrget/getWagesLevelListForSelect",
		type : "post",
		dataType : "json",
		async : false,
		success : function(data) {
			if(data.status=="200")
			{
				var html="<option value=''>请选择</option>";
				for(var i=0;i<data.list.length;i++)
				{
					html+="<option value=\""+data.list[i].wagesId+"\">"+data.list[i].title+"</option>";
				}
				$("#wagesLevel").html(html);
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

function addUserInfo()
{
	$.ajax({
		url : "/set/hrset/insertHrUserInfo",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			accountId:$("#accountId").attr("data-value"),
			deptId:$("#deptId").attr("data-value"),
			leaveId:$("#leaveId").attr("data-value"),
			userName:$("#userName").val(),
			userNameEn:$("#userNameEn").val(),
			beforeUserName:$("#beforeUserName").val(),
			sex:$("#sex").val(),
			workNo:$("#workNo").val(),
			staffNo:$("#staffNo").val(),
			staffCardNo:$("#staffCardNo").val(),
			birthDay:$("#birthDay").val(),
			levelType:$("#levelType").val(),
			animal:$("#animal").val(),
			nativePlace:$("#nativePlace").val(),
			address:$("#address").val(),
			bloodType:$("#bloodType").val(),
			nationalty:$("#nationalty").val(),
			maritalStatus:$("#maritalStatus").val(),
			health:$("#health").val(),
			politicalStatus:$("#politicalStatus").val(),
			joinPartyTime:$("#joinPartyTime").val(),
			staffType:$("#staffType").val(),
			staffAddress:$("#staffAddress").val(),
			workType:$("#workType").val(),
			wagesLevel:$("#wagesLevel").val(),
			occupation:$("#occupation").val(),
			employedTime:$("#employedTime").val(),
			workJob:$("#workJob").val(),
			workStatus:$("#workStatus").val(),
			phone:$("#phone").val(),
			attendType:$("#attendType").val(),
			persentPosition:$("#persentPosition").val(),
			workLeave:$("#workLeave").val(),
			mobileNo:$("#mobileNo").val(),
			wxNo:$("#wxNo").val(),
			email:$("#email").val(),
			homeAddress:$("#homeAddress").val(),
			qq:$("#qq").val(),
			otherContact:$("#otherContact").val(),
			bank:$("#bank").val(),
			bankAccount:$("#bankAccount").val(),
			highsetShool:$("#highsetShool").val(),
			highsetDegree:$("#highsetDegree").val(),
			graduationTime:$("#graduationTime").val(),
			graduationShool:$("#graduationShool").val(),
			major:$("#major").val(),
			skills:$("#skills").val(),
			otherLanguage:$("#otherLanguage").val(),
			otherLanguageLevel:$("#otherLanguageLevel").val(),
			cretificate:$("#cretificate").val(),
			surety:$("#surety").val(),
			insure:$("#insure").val(),
			bodyExamim:$("#bodyExamim").val(),
			remark:$("#remark").val(),
			resume:$("#resume").code(),
			photos:$("#file").attr("data-value"),
			attach:$("#hrattach").attr("data_value")
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