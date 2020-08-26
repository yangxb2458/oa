var designer;//获取设计器
var startNode;
var endNode;
var prcsList;
var chlidTable={};
$(function(){
	designer = new Designer("notepad");
	repaint();
	var h=$(window).height(); 
	 $("#notepad").css("height",h+"px");
});
function resize(){ 
    var h=$(window).height(); 
    $("#notepad").css("height",h+"px"); 
  } 

function repaint(){
	$("#notepad").html("");
	//获取流程步骤
	$.ajax({
		url:"/ret/documentget/getRunPrcsStep",
		dataType:"json",
		type:"post",
		data:{runId:runId},
		error : function(e){console.log(e);},
		success:function(data){
			if(data.status=="500")
				{
				console.log(data.msg);
				}else if(data.status=="100")
					{
					top.layer.msg(data.msg);
					}else
						{
						prcsList=data.list;
						}
	//绘制步骤
	for(var i=0;i<prcsList.length;i++){
		var data = prcsList[i];
		var runProcessId = data.runProcessId;
		var x = data.x;
		var y = data.y;
		if(data.prcsType==1){
			//开始节点
			startNode = designer.drawStart(x,y,data);
		}else if(data.prcsType==2){//结束节点
			endNode = designer.drawEnd(x,y,data);
		}else if(data.prcsType==3){//普通节点
			var node = designer.drawSimpleNode(x,y,data);
		}
//		$("#"+runProcessId).mouseover(function(){
//		    console.log($(this).attr("id"))
//		  });
//		$("#"+runProcessId).mouseout(function(){
//		 
//		  });
		$("#"+runProcessId).click(function(){
		    console.log($(this).attr("id"))
		  });
}
	//绘制步骤
	for(var i=0;i<prcsList.length;i++){
		var data = prcsList[i];
		var runProcessId = data.runProcessId;
		if(data.nextPrcs!=""&&data.nextPrcs!=null)
			{
			var nexts = data.nextPrcs.split(",");
			var node = $("#"+runProcessId);
			for(var j=0;j<nexts.length;j++){
				if(nexts[j])
				{
					node.addNextNode($("#"+nexts[j]));
				}
			}
			}
	}
	designer.doLineTo();
	
	
	
		}
	});
}
