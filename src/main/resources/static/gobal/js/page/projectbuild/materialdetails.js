$(function(){
		$.ajax({
		url : "/ret/projectbuildmaterialget/getMaterialById",
		type : "post",
		dataType : "json",
		data : {
			materialId:materialId
		},
		success : function(data) {
			if (data.status == 200) {
				for(name in data.list)
					{
						if(name=="unit")
						{
							$("#"+name).html(getprojectbuildunitbyid(data.list[name]));
						}else if(name=="sortId")
						{
							$("#"+name).html(getMaterialSortNameById(data.list[name]));
						}else
						{
							$("#"+name).html(data.list[name]);
						}
					}
			} else if(data.status==100)
				{
					top.layer.msg(data.msg);
				}else
				{
				console.log(data.msg);
			}
		}
	});
})

function getprojectbuildunitbyid(unitId) {
	var returnStr="";
	$.ajax({
		url : "/ret/projectbuildunitget/getunitbyid",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			unitId:unitId
		},
		success : function(data) {
			if (data.status == 200) {
				if(data.list.znName!=null && data.list.znName!="")
					{
						returnStr = data.list.cnName+"|"+data.list.znName;
					}else
					{
						returnStr = data.list.cnName;
					}
			} else {
				console.log(data.msg);
			}
		}
	});
	return returnStr;
}

function getMaterialSortNameById(sortId) {
	var returnStr="";
	$.ajax({
		url : "/ret/projectbuildmaterialget/getMaterialSortById",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			sortId:sortId
			
		},
		success : function(data) {
			if (data.status == 200) {
				returnStr = data.list.sortName;
			} else {
				console.log(data.msg);
			}
		}
	});
	return returnStr;
}
