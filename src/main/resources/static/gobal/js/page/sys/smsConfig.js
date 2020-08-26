$(function(){
	$(".js-setbtn").unbind("click").click(function(){
		updateSmsConfig();
	});
	getSmsConfig();
});
function getConfigParam()
{
	var params={};
	$(".js-val").each(function(){
		var param=[];
		$(this).find("input[type=checkbox]:checked").each(function(){
			param.push($(this).attr("name"));
		})
		params[$(this).attr("data-value")]=param;
	})
	return params;
}

function updateSmsConfig()
{
	$.ajax({
		url : "/set/sysset/updateSmsConfig",
		type : "post",
		dataType : "json",
		data:{config:JSON.stringify(getConfigParam())},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
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
function getSmsConfig()
{
	$.ajax({
		url : "/ret/sysget/getSmsConfig",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				$(".js-val").each(function(){
					var vals=JSON.parse(data.list.config)[$(this).attr("data-value")];
					for(var i=0;i<vals.length;i++)
						{
						$(this).find("input[name='"+vals[i]+"']").each(function(){
							$(this).attr("checked","checked");
						})
					}
				})
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