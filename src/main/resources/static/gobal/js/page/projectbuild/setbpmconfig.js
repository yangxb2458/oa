$(function(){
	$.ajax({
		url : "/ret/bpmget/getAllBpmFlowListByManage",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
				{
					var html="";
					for(var i=0;i<data.list.length;i++)
					{
						html+="<option value='"+data.list[i].flowId+"'>"+data.list[i].flowName+"</option>";
					}
					$("#flowId").html(html);
				}else if(data.status=="100")
				{
					top.layer.msg(data.msg);
				}else
				{
					console.log(data.msg);
				}
		}
	});
	$(".js-cg").unbind("click").click(function(){
		$("#setmodal").modal("show");
		var event = $(this).attr("data-value");
		$(".js-add").unbind("click").click(function(){
			addBpm(event);
		});
	});
	$(".js-out").unbind("click").click(function(){
			$("#setmodal").modal("show");
			var event = $(this).attr("data-value");
			$(".js-add").unbind("click").click(function(){
				addBpm(event);
		});
			
	});
	
	$.ajax({
		url : "/ret/projectbuilget/config/getAllProjectBuildBpmConfig",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
				{
					for(var i=0;i<data.list.length;i++)
					{
						var html = "<div class='col-lg-8 col-sm-8 col-xs-8'  data-value='"+data.list[i].flowId+"'>"+data.list[i].flowName+"</div><div class='col-lg-4 col-sm-4 col-xs-4' style='text-align: right;'><a onclick='del(this);' href='javascript:void(0);' data-value='"+data.list[i].configId+"' class='btn btn-primary btn-sm'>删除</a></div>";
						if(data.list[i].event=="purchase")
						{
							$("#purchaselist").html(html);
						}else if(data.list[i].event=="materialOut")
						{
							$("#materialOutlist").html(html);
						}
					}
				}else if(data.status=="100")
				{
					top.layer.msg(data.msg);
				}else
				{
					console.log(data.msg);
				}
		}
	});
})

function del(Obj)
{
	var configId = $(Obj).attr("data-value");
	$.ajax({
		url : "/set/projectbuildset/config/deleteProjectBuildBpmConfig",
		type : "post",
		dataType : "json",
		data:{
			configId:configId
		},
		success : function(data) {
			if(data.status=="200")
				{
					top.layer.msg(data.msg);
					window.location.reload();
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

function addBpm(event)
{
	$.ajax({
		url : "/set/projectbuildset/config/insertProjectBuildBpmConfig",
		type : "post",
		dataType : "json",
		data:{
			event:event,
			flowId:$("#flowId").val()
		},
		success : function(data) {
			if(data.status=="200")
				{
					top.layer.msg(data.msg);
					$("#setmodal").modal("hide");
					window.location.reload();
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