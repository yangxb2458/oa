function getHrDeptNameByStr(deptIds) {
	if (deptIds == "@all") {
		return "全体部门";
	} else {
		var deptNames = [];
		if (deptIds) {
			$.ajax({
				url : "/ret/hrget/getHrDeptNameByStr",
				type : "post",
				dataType : "json",
				async : false,
				data : {
					deptIds : deptIds
				},
				success : function(data) {
					if (data.status == 200) {
						for (var i = 0; i < data.list.length; i++) {
							deptNames.push(data.list[i].deptName);
						}
					} else if (data.status == 100) {
						console.log(data.msg);
					} else {
						console.log(data.msg);
					}
				}
			});
		}
		return deptNames.join(",");
	}
}

function getHrUserNameByStr(userIds) {
	if (userIds == "@all") {
		return "全体人员";
	} else {
		var userNames = [];
		if (userIds) {
			$.ajax({
				url : "/ret/hrget/getUserNamesByUserIds",
				type : "post",
				dataType : "json",
				async : false,
				data : {
					userIds : userIds
				},
				success : function(data) {
					if (data.status == 200) {
						for (var i = 0; i < data.list.length; i++) {
							userNames.push(data.list[i].userName);
						}
					} else if (data.status == 100) {
						console.log(data.msg);
					} else {
						console.log(data.msg);
					}
				}
			});
		}
		return userNames.join(",");
	}
}



function delPhotos()
{
	$("#file_img").attr("src","/assets/img/avatars/adam-jansen.jpg");
	$("#file").attr("data-value","");
}
function getHrUserLevelByStr(levelIds) {
	if (levelIds == "@all") {
		return "所有级别";
	} else {
		var userLevelNames = [];
		if (levelIds) {
			$.ajax({
				url : "/ret/hrget/getHrUserLevelByStr",
				type : "post",
				dataType : "json",
				async : false,
				data : {
					levelIds : levelIds
				},
				success : function(data) {
					if (data.status == "200") {
						for (var i = 0; i < data.list.length; i++) {
							userLevelNames.push(data.list[i].name);
						}
					} else if (data.status == "100") {
						console.log(data.msg);
					} else {
						console.log(data.msg);
					}
				}
			});
		}
		return userLevelNames.join(",");
	}
}

function createAutoSelect(module)
{
	$.ajax({
		url : "/ret/hrget/getCodeListByModule",
		type : "post",
		dataType : "json",
		async : false,
		data:{module:module},
		success : function(data) {
			if(data.status=="200")
			{
				var html="<option value=''>请选择</option>";
				for(var i=0;i<data.list.length;i++)
				{
					html+="<option value=\""+data.list[i].codeValue+"\">"+data.list[i].codeName+"</option>";
				}
				$("#"+module).html(html);
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

function QuerySelect(module)
{
	$.ajax({
		url : "/ret/hrget/getCodeListByModule",
		type : "post",
		dataType : "json",
		async : false,
		data:{module:module},
		success : function(data) {
			if(data.status=="200")
			{
				var html="<option value=''>请选择</option>";
				for(var i=0;i<data.list.length;i++)
				{
					html+="<option value=\""+data.list[i].codeValue+"\">"+data.list[i].codeName+"</option>";
				}
				$("#"+module+"Query").html(html);
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

function getAttendType(module)
{
	$.ajax({
		url : "/ret/oaget/getAllAttendConfigList",
		type : "post",
		dataType : "json",
		async : false,
		success : function(data) {
			if(data.status=="200")
			{
				var html="<option value=''>请选择</option>";
				for(var i=0;i<data.list.length;i++)
				{
					html+="<option value='"+data.list[i].configId+"'>"+data.list[i].title+"</option>";
				}
				$("#"+module).html(html);
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

function getAttendTypeName(module,configId)
{
	var returnStr="";
	$.ajax({
		url : "/ret/oaget/getAttendConfigById",
		type : "post",
		dataType : "json",
		async : false,
		data:{configId:configId},
		success : function(data) {
			if(data.status=="200")
			{
				returnStr=data.list.title;
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
			}
		})
		return returnStr;
}


function getWagesTitle(wagesId)
{
	var returnStr="";
	$.ajax({
		url : "/ret/hrget/getHrWagesLevelById",
		type : "post",
		dataType : "json",
		async : false,
		data:{
			wagesId:wagesId
		},
		success : function(data) {
			if(data.status=="500")
				{
				console.log(data.msg);
				}else if(data.status=="100")
					{
					top.layer.msg(data.msg);
					}else
						{
						returnStr = data.list.title
						}
		}
	});
	
	return returnStr;
}


function getHrClassCodeName(module,value)
{
	var returnStr=[];
	$.ajax({
		url : "/ret/hrget/getHrClassCodeName",
		type : "post",
		dataType : "json",
		async : false,
		data:{module:module,codeValue:value},
		success : function(data) {
			if(data.status=="200")
			{
				for(var i=0;i<data.list.length;i++)
				{
					returnStr.push(data.list[i].codeName);
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
		return returnStr.join(",");
}