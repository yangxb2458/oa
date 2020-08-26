$(function() {
	getAllUserLevelList();
	$(".js-add").unbind("click").click(function(){
		 add();
	})
	$(".js-update").unbind("click").click(function(){
		update();
	})
	$(".js-del").unbind("click").click(function(){
		del();
	})
	var oc =  $('#chart-container').orgchart({
      'data' : '/ret/unitget/getUserLevelChart?levelId=0',
      'nodeContent': 'levelNoId',
      'nodeId': 'id'
    });
    oc.$chartContainer.on('click', '.node', function() {
        var nodes = $(this);
       setUpdate(nodes.context.id)
      });
 });
function add()
{
	if($("#levelName").val()==""||$("#levelNoId").val()=="")
	{
		top.layer.msg("行政级别名称不能为空!");
		return;
	}else
	{
		$.ajax({
			url : "/set/unitset/addUserLevel",
			type : "post",
			dataType : "json",
			data:{
				levelName:$("#levelName").val(),
				superior:$("#superior").val(),
				levelNoId:$("#levelNoId").val()
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
}

function update()
{
	$.ajax({
		url : "/set/unitset/updateUserLevel",
		type : "post",
		dataType : "json",
		data:{
			levelId:$("#levelId").val(),
			levelName:$("#levelName").val(),
			superior:$("#superior").val(),
			levelNoId:$("#levelNoId").val()
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

function setUpdate(levelId)
{
	$.ajax({
		url : "/ret/unitget/getUserLevel",
		type : "post",
		dataType : "json",
		data:{
			levelId:levelId
		},
		success : function(data) {
			if(data.status==200){
				for(var name in data.list)
					{
					$("#"+name).val(data.list[name]);
					}
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
function del()
{
	var msg = "您真的确定要删除吗？\n\n请确认！"; 
	if (confirm(msg)==true){
		$.ajax({
			url : "/set/unitset/delUserLevel",
			type : "post",
			dataType : "json",
			data:{
				levelId:$("#levelId").val()
			},
			success : function(data) {
				if(data.status==200){
					location.reload();
					top.layer.msg(data.msg);
				}else if(data.status==100)
				{
					top.layer.msg(data.msg);
				}else
					{
					console.log(data.msg);
					}
			}
		});	
	}else{ 
	return; 
	} 
}




function getAllUserLevelList()
{
	$.ajax({
		url : "/ret/unitget/getUserLevelList",
		type : "post",
		dataType : "json",
		data:{
		},
		success : function(data) {
			if(data.status==200){
				$("#superior").append("<option value='0'></option>")
				for(var i=0;i<data.list.length;i++)
					{
					$("#superior").append("<option value='"+data.list[i].levelId+"'>"+data.list[i].levelName+"</option>")
					}
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