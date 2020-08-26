$(function(){
		$.ajax({
		url : "/ret/projectbuildmaterialget/getMaterialStageById",
		type : "post",
		dataType : "json",
		data : {
			materialStageId:materialStageId
		},
		success : function(data) {
			if (data.status == 200) {
				for(name in data.list)
					{
						 if(name=="materialId")
							{
							 getMaterialById(data.list.materialId);
							}else if(name=="stageId")
							{
								getStageById(data.list.stageId);
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


function getMaterialById(materialId) {
	$.ajax({
		url : "/ret/projectbuildmaterialget/getMaterialById",
		type : "post",
		dataType : "json",
		data : {
			materialId:materialId
			
		},
		success : function(data) {
			console.log(data);
			if (data.status == 200) {
				$("#materialCode").html(data.list.materialCode);
				$("#name").html(data.list.name);
				$("#brand").html(data.list.brand);
				$("#model").html(data.list.model);
				$("#price").html(data.list.price);
				$("#unit").html(getprojectbuildunitbyid(data.list.unit));
			} else {
				console.log(data.msg);
			}
		}
	});
}


function getStageById(stageId) {
	$.ajax({
		url : "/ret/projectbuildget/getProjectBuildStageById",
		type : "post",
		dataType : "json",
		data : {
			stageId:stageId
			
		},
		success : function(data) {
			if (data.status == 200) {
				$("#stageName").html(data.list.stageName);
			} else {
				console.log(data.msg);
			}
		}
	});
}

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