var ztreeNodes=[];
var setting = {
        check: {
            autoCheckTrigger: true,
            enable: true,
            chkStyle: "checkbox",
            chkboxType: { "Y": "ps", "N": "ps" }
        },
        data: {
            simpleData: {
                enable: true}
                },
        view: {
            showLine: false
                }
           };

$(function() {
	$.ajax({
		url : "/ret/platformget/getPlatfromPrivTree",
		type : "post",
		dataType : "json",
		data:{privId:privId},
		success : function(data) {
			if(data.status=="200")
			{
				for(var i=0;i<data.list.length;i++)
				{
				if(data.list[i].pId=='0')
					{
					tmp.push(data.list[i]);
					getZtreeNodes(data.list,data.list[i].id);
					ztreeNodes.push(tmp);
					tmp=[];
					}
				}
				$("#ztrees").css("width",180*ztreeNodes.length+"px")
				for(var i=0;i<ztreeNodes.length;i++)
					{
						$("#ztrees").append("<div style=\"display: inline-block;vertical-align: top;\"><ul id=\"tree_"+i+"\" class=\"ztree\"></ul></div>");
						$.fn.zTree.init($("#tree_"+i), setting,ztreeNodes[i]).expandAll(true);
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
});
var tmp=[];
function getZtreeNodes(data, id) {
	for(var i=0;i<data.length;i++)
		{
		if (data[i].pId == id) {
			getZtreeNodes(data, data[i].id);
			tmp.push(data[i]);
		}
		}
}

function doSave(){
    var privStr="";
    for(var i=0;ztreeNodes.length>i;i++)
   	 {
   	 var treeObj = $.fn.zTree.getZTreeObj("tree_"+i);
   	 var sNodes = treeObj.getCheckedNodes(true);
   	 if (sNodes.length > 0) {
   		 for(var j=0;j<sNodes.length;j++){
   			 privStr += sNodes[j].id+",";
   		 }
   	 }
    }
    if(privStr.length>0)
    {
   	 privStr=privStr.substr(0,privStr.length-1);
    }
       $.ajax({
               url:"/set/platformset/updatePlatformPriv",
               dataType:"json",
               type:"post",
               data:{privId:privId,privStr:privStr},
               async:false,
               error:function(e){
                   alert(e.message);
               },
               success:function(data){
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