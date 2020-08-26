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
		url:"/ret/documentget/getDocumentProcess",
		dataType:"json",
		type:"post",
		data:{flowId:flowId},
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
		var processId = data.processId;
		var x = data.x;
		var y = data.y;
		if(data.prcsType==1){
			//开始节点
			startNode = designer.drawStart(x,y,data);
		}else if(data.prcsType==2){//结束节点
			endNode = designer.drawEnd(x,y,data);
		}else if(data.prcsType==3){//普通节点
			var node = designer.drawSimpleNode(x,y,data);
		}else if(data.prcsType==4){//并发节点
			var node = designer.drawParallelNode(x,y,data);
		}else if(data.prcsType==5){//聚合节点
			var node = designer.drawAggregationNode(x,y,data);
		}else if(data.prcsType==6){//子流程节点
			var node = designer.drawChildNode(x,y,data);
		}
}
	//绘制步骤
	for(var i=0;i<prcsList.length;i++){
		var data = prcsList[i];
		var processId = data.processId;
		if(data.nextPrcs)
			{
			var nexts = data.nextPrcs.split(",");
			var node = $("#"+processId);
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
