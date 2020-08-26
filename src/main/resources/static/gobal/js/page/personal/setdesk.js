$(function(){
	getMyDeskConfig();
	$(".js-setdesk-btn").unbind("click").click(function(){
		$.ajax({
			url : "/set/unitset/setDeskConfig",
			type : "post",
			dataType : "json",
			data:{homePage:JSON.stringify(getLRConfig())},
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
	})
});

function getLRConfig()
{
	var lArr=[];
	var rArr=[];
	$(".left-selected").find("li").each(function(){
		var json={};
		json.module=$(this).attr("data-value");
		json.id=$(this).attr("data-id");
		json.moduleName=$(this).text();
		lArr.push(json);
	});
	
	$(".right-selected").find("li").each(function(){
		var json={};
		json.module=$(this).attr("data-value");
		json.id=$(this).attr("data-id");
		json.moduleName=$(this).text();
		rArr.push(json);
	});
	var deskJson={
			left:lArr,
			right:rArr
	};
	return deskJson;
}
function getMyDeskConfig()
{
	getDeskConfig();
	$.ajax({
		url : "/ret/unitget/getMyHomePage",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				if(data.list!=null&&data.list!="null")
				{
				var leftJson=data.list.left;
				var rightJson=data.list.right;
				var htmll="";
				for(var i=0;i<leftJson.length;i++)
				{
					htmll+="<li class=\"item\" data-value=\""+leftJson[i].module+"\" data-id=\""+leftJson[i].deskConfigId+"\">"+leftJson[i].moduleName+"</li>";
				}
				$(".left-selected").html(htmll);
				var htmlr="";
				for(var i=0;i<rightJson.length;i++)
				{
					htmlr+="<li class=\"item\" data-value=\""+rightJson[i].module+"\" data-id=\""+rightJson[i].deskConfigId+"\">"+rightJson[i].moduleName+"</li>";
				}
				}
				$(".right-selected").html(htmlr);
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


function getDeskConfig()
{
	$.ajax({
		url : "/ret/sysget/getDeskConfigList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var html="";
				for(var i=0;i<data.list.length;i++)
					{
					html+="<li class=\"item\" data-value=\""+data.list[i].module+"\" data-id=\""+data.list[i].deskConfigId+"\">"+data.list[i].moduleName+"</li>";
					}
				$(".leftall-box").html(html);
				$(".rightall-box").html(html);
				$('#selectTitleLeft').initList({
					openDrag: true,
					openDblClick: true
				});
				$('#selectTitleRight').initList({
					openDrag: true,
					openDblClick: true
				});
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