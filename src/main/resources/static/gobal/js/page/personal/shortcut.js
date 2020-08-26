$(function(){
	$.ajax({
		url : "/ret/sysget/getMyMenuInPriv",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var menuList = data.list;
				$("#menu").append("<option>请选择</option>")
				for(var i=0;i<menuList.length;i++)
				{
					$("#menu").append("<option value=\""+menuList[i].sysMenuUrl+"\">"+menuList[i].sysMenuName+"</option>")
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
	$(".js-setshortct-btn").unbind("click").click(function(){
		setShortctMenu();
	});
	getMyShortcutMenu();
})

function setShortctMenu()
{
	var jsonArr=[];
	for(var i=1;i<10;i++)
	{
		var json={};
		json.id=i;
		json.name=$(".js-menu"+i+"-name").html();
		json.url=$(".js-menu"+i+"-url").html();
		json.img=$(".js-menu"+i+"-img").attr("src");
		if($(".js-menu"+i+"-checked").is(':checked')) {
			json.status="1";
		}else
		{
			json.status="0";
		}
		jsonArr.push(json);
	}
	$.ajax({
		url : "/set/sysset/setShortcut",
		type : "post",
		dataType : "json",
		data:{config:JSON.stringify(jsonArr)},
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


function setbindmenu(Obj)
{
	var m = $(Obj).attr("data-value");
	$("#menumodal").modal("show");
	$(".js-bind").unbind("click").click(function(){
		var name = $("#menu").find("option:selected").text();
		var url = $("#menu").val();
		$(".js-"+m+"-name").html(name);
		$(".js-"+m+"-url").html(url);
		$("#menumodal").modal("hide");
	})
}


function getMyShortcutMenu()
{
	$.ajax({
		url : "/ret/sysget/getMyShortcutMenu",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var config = JSON.parse(data.list.config);
				if(config)
				{
					for(var i=0;i<config.length;i++)
					{
						$(".js-menu"+config[i].id+"-name").html(config[i].name);
						$(".js-menu"+config[i].id+"-url").html(config[i].url);
						if(config[i].status=="1")
						{
							$(".js-menu"+config[i].id+"-checked").prop("checked",true);
						}else
						{
							$(".js-menu"+config[i].id+"-checked").prop("checked",false);
						}
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
}