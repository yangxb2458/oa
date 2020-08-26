$(function(){
	$.ajax({
		url : "/ret/knowledgeget/getKnowledgeById",
		type : "post",
		dataType : "json",
		data:{knowledgeId:knowledgeId},
		success : function(data) {
			console.log(data);
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				for(var name in data.list)
				{
				if(name=="attach")
					{
					$("#attach").attr("data_value",data.list.attach);
					createAttach("attach","1");
					}else if(name=="sortId")
					{
						$("#"+name).html(getSortName(data.list.sortId));
					}else if(name=="createUser")
					{
						$("#"+name).html(getUserNameByStr(data.list.createUser));
					}else
						{
						$("#"+name).html(data.list[name]);
						}
				}
				}
		}
	});
	$("#onclickCount").html(getLearnCount()+"次");
	setTimeout(function(){
		addKnowledgeLearn();
		},1000*30);
});


function addKnowledgeLearn()
{
	$.ajax({
		url : "/set/knowledgeset/insertKnowledgeLearn",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			knowledgeId:knowledgeId
		},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
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

function getSortName(sortId)
{
	var returnStr="";
	$.ajax({
		url : "/ret/knowledgeget/getKnowledgeSortById",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			sortId : sortId
		},
		success : function(data) {
			if(data.status=="200")
			{
				returnStr=data.list.sortName;
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
		});
	
	return returnStr;
}


function getLearnCount()
{
	var returnStr="";
	$.ajax({
		url : "/ret/knowledgeget/getLearnCount",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			knowledgeId : knowledgeId
		},
		success : function(data) {
			if(data.status=="200")
			{
				returnStr=data.list;
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
		});
	
	return returnStr;
}