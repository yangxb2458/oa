function fileUpLoad(module,fileId){
	$.ajaxFileUpload({
         url:'/sys/file/upload?module='+module, //上传文件的服务端
         secureuri:false,  //是否启用安全提交
         async:false,
         dataType: 'json',   //数据类型  
         fileElementId:fileId, //表示文件域ID
         success: function(data,status){
        	 	if(data.status=="200")
        	 		{
        	 		top.layer.msg(data.msg);
        	 		var datalist = data.list;
        	 		var attachIds=$("#"+fileId).attr("data_value");
        	 		var attachArr=[];
        	 		if(attachIds)
        	 			{
        	 			attachArr = attachIds.split(",");
        	 			}
        	 		for(var i=0;i<datalist.length;i++)
        	 		{ 
        	 			attachArr.push(datalist[i].attachId);
        	 		}
        	 		createAttachDiv(fileId,datalist,4);
        	 		$("#"+fileId).attr("data_value",attachArr.join(","));
        	 		}else if(data.status=="100")
    				{
    					top.layer.msg(data.msg);
    				}else
        	 			{
    					top.layer.msg(data.msg);
        	 			}
         },
    //提交失败处理函数
         error: function (data,status,e){
        	 top.layer.msg("文件上传出错!请检查文件格式!");
        	console.log(data.msg);
         }
    });
}

function fileDocumentUpLoad(module,fileId){
	$.ajaxFileUpload({
         url:'/sys/file/upload?module='+module, //上传文件的服务端
         secureuri:false,  //是否启用安全提交
         async:false,
         dataType: 'json',   //数据类型  
         fileElementId:fileId, //表示文件域ID
         success: function(data,status){
        	 	if(data.status=="200")
        	 		{
        	 		top.layer.msg(data.msg);
        	 		var datalist = data.list;
        	 		var attachIds=$("#"+fileId).attr("data_value");
        	 		var attachArr=[];
        	 		if(attachIds)
        	 			{
        	 			attachArr = attachIds.split(",");
        	 			}
        	 		for(var i=0;i<datalist.length;i++)
        	 		{ 
        	 			attachArr.push(datalist[i].attachId);
        	 		}
        	 		createAttachDocumentDiv(fileId,datalist,4);
        	 		$("#"+fileId).attr("data_value",attachArr.join(","));
        	 		}else if(data.status=="100")
    				{
    					top.layer.msg(data.msg);
    				}else
        	 			{
    					top.layer.msg(data.msg);
        	 			}
         },
    //提交失败处理函数
         error: function (data,status,e){
        	 top.layer.msg("文件上传出错!请检查文件格式!");
        	console.log(data.msg);
         }
    });
}

function uploadheadimg(fileId){
	$.ajaxFileUpload({
         url:'/sys/file/uploadHeadImg', //上传文件的服务端
         secureuri:false,  //是否启用安全提交
         async:false,
         dataType: 'json',   //数据类型  
         fileElementId:fileId, //表示文件域ID
         success: function(data,status){
        	 top.layer.msg(data.msg);
        	 if(data.status==200)
        		 {
        		 if(data.list==1)
        			 {
        			 $("#accountHeadImg img").attr("src","/sys/file/getHeadImg?r="+Math.random());
        			 $(".avatar-area img").attr("src","/sys/file/getHeadImg?r="+Math.random());
        			 }
        		 }
         },
    //提交失败处理函数
         error: function (data,status,e){
        	console.log(data.msg);
         }
    });
}

function uploadimg(fileId,module){
	$.ajaxFileUpload({
         url:'/sys/file/uploadimg?module='+module, //上传文件的服务端
         secureuri:false,  //是否启用安全提交
         async:false,
         dataType: 'json',   //数据类型  
         fileElementId:fileId, //表示文件域ID
         success: function(data,status){
        	 if(data.status=="200")
        		 {
        		 	$("#"+fileId).attr("data-value",data.redirect);
        			$("#"+fileId+"_img").attr("src","/sys/file/getStaticImg?r="+Math.random()+"&module="+module+"&fileName="+data.redirect);
        		 }else if(data.status=="100")
        		{
        			 top.layer.msg(data.msg);
        		}else
        		{
        			 console.log(data.msg);
        		}
         },
         error: function (data,status,e){
        	console.log(data.msg);
         }
    });
}


function uploadimglogo(fileId){
	$.ajaxFileUpload({
         url:'/sys/file/uploadimglogo', //上传文件的服务端
         secureuri:false,  //是否启用安全提交
         async:false,
         dataType: 'json',   //数据类型  
         fileElementId:fileId, //表示文件域ID
         success: function(data,status){
        	 if(data.status=="200")
        		 {
        		 	$("#"+fileId).attr("data-value",data.redirect);
        			$("#"+fileId+"_img").attr("src","/sys/file/getBackgroundImg?fileName="+data.redirect);
        		 }else if(data.status=="100")
        		{
        			 top.layer.msg(data.msg);
        		}else
        		{
        			 console.log(data.msg);
        		}
         },
         error: function (data,status,e){
        	console.log(data.msg);
         }
    });
}


//priv:1只读权限
//priv:2可以打印
//priv:3可以下载,打印
//priv:4 编辑权限
function createAttach(eId,priv)
{
	var attachIds = $("#"+eId).attr("data_value");
	$("#show_"+eId).empty();
	if(attachIds!=""&&attachIds!=undefined)
		{
		$.ajax({
			url : "/sys/file/getAttachList",
			type : "post",
			dataType : "json",
			data:{
				attachIds:attachIds
			},
			success : function(data) {
				if(data.status==200){
					var datalist = data.list;
					if(datalist!=null)
						{
							createAttachDiv(eId,datalist,priv);
						}
				}else if(data.status==100)
				{
					console.log(data.msg);
				}else
					{
					console.log(data.msg);
					}
			}
		});
	}
}

function createAttachDocument(eId,priv)
{
	var attachIds = $("#"+eId).attr("data_value");
	$("#show_"+eId).empty();
	if(attachIds!=""&&attachIds!=undefined)
		{
		$.ajax({
			url : "/sys/file/getAttachList",
			type : "post",
			dataType : "json",
			data:{
				attachIds:attachIds
			},
			success : function(data) {
				if(data.status==200){
					var datalist = data.list;
					if(datalist!=null)
						{
							createAttachDocumentDiv(eId,datalist,priv);
						}
				}else if(data.status==100)
				{
					console.log(data.msg);
				}else
					{
					console.log(data.msg);
					}
			}
		});
	}
}


function createAttachDiv(eId,attachlist,priv)
{
	var htmlattach="";
	var htmlimg="";
	//在线查看
	if(priv=="1")
		{
			for(var i=0;i<attachlist.length;i++)
			{
			if(attachlist[i].extName!='.jpg'&&attachlist[i].extName!='.jpeg'&&attachlist[i].extName!='.png'&&attachlist[i].extName!='.bmp'&&attachlist[i].extName!='.tit'&&attachlist[i].extName!='.gif')
				{
				htmlattach+="<span class='btn-group' style=\"margin-right: 10px;margin-bottom: 10px;\" id='attachdiv_"+attachlist[i].attachId+"'>" +
				"<a class=\"btn btn-success\" href=\"javascript:void(0);\">"+attachlist[i].oldName+"</a>" +
				"<a class=\"btn btn-success  dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:void(0);\" aria-expanded=\"false\"><i class=\"fa fa-angle-down\"></i></a>" +
				"<ul class=\"dropdown-menu dropdown-success\">" +
				"<li>" +
				"<a href=\"javascript:openFileOnLine('"+attachlist[i].extName+"','"+attachlist[i].attachId+"',1);\">查看 </a>" +
				"</li>" +
				"</ul>" +
				"</span>";
				}else
				{
					htmlimg+="<span class='attachDiv' onclick=\"showImg('"+attachlist[i].attachId+"')\";>" +
							"<img class='attachImg' src='/sys/file/getImage?attachId="+attachlist[i].attachId+"'/>" +
							"<div style='text-align:center;'>"+attachlist[i].oldName+"</div>" +
							"</span>";
				}
			}
		}else if(priv=="2")//可以打印
		{
			for(var i=0;i<attachlist.length;i++)
			{
			if(attachlist[i].extName!='.jpg'&&attachlist[i].extName!='.jpeg'&&attachlist[i].extName!='.png'&&attachlist[i].extName!='.bmp'&&attachlist[i].extName!='.tit'&&attachlist[i].extName!='.gif')
				{
				htmlattach+="<span class='btn-group' style=\"margin-right: 10px;margin-bottom: 10px;\" id='attachdiv_"+attachlist[i].attachId+"'>" +
				"<a class=\"btn btn-success\" href=\"javascript:void(0);\">"+attachlist[i].oldName+"</a>" +
				"<a class=\"btn btn-success  dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:void(0);\" aria-expanded=\"false\"><i class=\"fa fa-angle-down\"></i></a>" +
				"<ul class=\"dropdown-menu dropdown-success\">" +
				"<li>" +
				"<a href=\"javascript:openFileOnLine('"+attachlist[i].extName+"','"+attachlist[i].attachId+"',3);\">打印 </a>" +
				" </li>" +
				"<li>" +
				"<a href=\"javascript:openFileOnLine('"+attachlist[i].extName+"','"+attachlist[i].attachId+"',1);\">查看 </a>" +
				"</li>" +
				"</ul>" +
				"</span>";
				}else
				{
					htmlimg+="<span class='attachDiv' onclick=\"showImg('"+attachlist[i].attachId+"')\";>" +
								"<img class='attachImg' src='/sys/file/getImage?attachId="+attachlist[i].attachId+"'/>" +
								"<div style='text-align:center;'>"+attachlist[i].oldName+"</div>" +
							"</span>";
				}
			}
		}else if(priv=="3")//可以下载,打印
		{
			for(var i=0;i<attachlist.length;i++)
			{
			if(attachlist[i].extName!='.jpg'&&attachlist[i].extName!='.jpeg'&&attachlist[i].extName!='.png'&&attachlist[i].extName!='.bmp'&&attachlist[i].extName!='.tit'&&attachlist[i].extName!='.gif')
				{
				htmlattach+="<span class='btn-group' style=\"margin-right: 10px;margin-bottom: 10px;\" id='attachdiv_"+attachlist[i].attachId+"'>" +
				"<a class=\"btn btn-success\" href=\"javascript:void(0);\">"+attachlist[i].oldName+"</a>" +
				"<a class=\"btn btn-success  dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:void(0);\" aria-expanded=\"false\"><i class=\"fa fa-angle-down\"></i></a>" +
				"<ul class=\"dropdown-menu dropdown-success\">" +
				"<li>" +
				"<a href=\"javascript:openFileOnLine('"+attachlist[i].extName+"','"+attachlist[i].attachId+"',3);\">打印 </a>" +
				" </li>" +
				"<li>" +
				"<a href=\"/sys/file/getFileDown?attachId="+attachlist[i].attachId+"\">下载</a>" +
				" </li>" +
				"<li>" +
				"<a href=\"javascript:openFileOnLine('"+attachlist[i].extName+"','"+attachlist[i].attachId+"',1);\">查看 </a>" +
				"</li>" +
				"</ul>" +
				"</span>";
				}else
				{
					htmlimg+="<span class='attachDiv' onclick=\"showImg('"+attachlist[i].attachId+"')\";>" +
								"<img class='attachImg' src='/sys/file/getImage?attachId="+attachlist[i].attachId+"'/>" +
								"<div style='text-align:center;'>"+attachlist[i].oldName+"</div>" +
							"</span>";
				}
			}
		}else if(priv=="4")
		{
			for(var i=0;i<attachlist.length;i++)
			{
			if(attachlist[i].extName!='.jpg'&&attachlist[i].extName!='.jpeg'&&attachlist[i].extName!='.png'&&attachlist[i].extName!='.bmp'&&attachlist[i].extName!='.tit'&&attachlist[i].extName!='.gif')
				{
				htmlattach+="<span class='btn-group' style=\"margin-right: 10px;margin-bottom: 10px;\" id='attachdiv_"+attachlist[i].attachId+"'>" +
				"<a class=\"btn btn-success\" href=\"javascript:void(0);\">"+attachlist[i].oldName+"</a>" +
				"<a class=\"btn btn-success  dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:void(0);\" aria-expanded=\"false\"><i class=\"fa fa-angle-down\"></i></a>" +
				"<ul class=\"dropdown-menu dropdown-success\">" +
				"<li>" +
				"<a href=\"javascript:openFileOnLine('"+attachlist[i].extName+"','"+attachlist[i].attachId+"',2);\">打印 </a>" +
				"</li>" +
				"<li>" +
				"<a href=\"javascript:openFileOnLine('"+attachlist[i].extName+"','"+attachlist[i].attachId+"',4);\">编辑 </a>" +
				"</li>" +
				"<li>" +
				"<a href=\"javascript:void(0);delattach('"+eId+"','"+attachlist[i].attachId+"')\">删除</a>" +
				"</li>" +
				"<li>" +
				"<a href=\"/sys/file/getFileDown?attachId="+attachlist[i].attachId+"\">下载</a>" +
				" </li>" +
				"<li>" +
				"<a href=\"javascript:openFileOnLine('"+attachlist[i].extName+"','"+attachlist[i].attachId+"',1);\">查看 </a>" +
				"</li>" +
				"</ul>" +
				"</span>";
				}else
				{
					htmlimg+="<span class='attachDiv' onclick=\"showImg('"+attachlist[i].attachId+"')\";>" +
								"<img class='attachImg' src='/sys/file/getImage?attachId="+attachlist[i].attachId+"'/>" +
								"<div style='text-align:center;'>"+attachlist[i].oldName+"</div>" +
							"</span>";
				}
			}
		}else
		{
			for(var i=0;i<attachlist.length;i++)
			{
			if(attachlist[i].extName!='.jpg'&&attachlist[i].extName!='.jpeg'&&attachlist[i].extName!='.png'&&attachlist[i].extName!='.bmp'&&attachlist[i].extName!='.tit'&&attachlist[i].extName!='.gif')
				{
				htmlattach+="<span class='btn-group' style=\"margin-right: 10px;margin-bottom: 10px;\" id='attachdiv_"+attachlist[i].attachId+"'>" +
				"<a class=\"btn btn-success\" href=\"javascript:void(0);\">"+attachlist[i].oldName+"</a>" +
				"<a class=\"btn btn-success  dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:void(0);\" aria-expanded=\"false\"><i class=\"fa fa-angle-down\"></i></a>" +
				"<ul class=\"dropdown-menu dropdown-success\">" +
				"<li>" +
				"<a onclick=\"readfile('"+attachlist[i].extName+"','"+attachlist[i].attachId+"')\">查看</a>" +
				"</li>" +
				"<li>" +
				"<a href=\"javascript:void(0);delattach('"+eId+"','"+attachlist[i].attachId+"')\">删除</a>" +
				"</li>" +
				"	<li>" +
				"<a href=\"/sys/file/getFileDown?attachId="+attachlist[i].attachId+"\">下载</a>" +
				" </li>" +
				"</ul>" +
				"</span>";
				}else
					{
					htmlimg+="<span class='attachDiv' id='attachdiv_"+attachlist[i].attachId+"' onclick=\"delattach('"+eId+"','"+attachlist[i].attachId+"')\";><p class=\"diyControl\"><span class=\"diyCancel\"><i></i></span></p><img class='attachImg' src='/sys/file/getImage?attachId="+attachlist[i].attachId+"'/></span>";
					}
			}
	}
	$("#show_"+eId).append("<div>"+htmlimg+"</div><div>"+htmlattach+"</div>");
}

function createAttachDocumentDiv(eId,attachlist,priv)
{
	var htmlattach="";
	var htmlimg="";
	//在线查看
	if(priv=="1")
		{
			for(var i=0;i<attachlist.length;i++)
			{
			if(attachlist[i].extName!='.jpg'&&attachlist[i].extName!='.jpeg'&&attachlist[i].extName!='.png'&&attachlist[i].extName!='.bmp'&&attachlist[i].extName!='.tit'&&attachlist[i].extName!='.gif')
				{
				htmlattach+="<span class='btn-group' style=\"margin-right: 10px;margin-bottom: 10px;\" id='attachdiv_"+attachlist[i].attachId+"'>" +
				"<a class=\"btn btn-success\" href=\"javascript:void(0);\">"+attachlist[i].oldName+"</a>" +
				"<a class=\"btn btn-success  dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:void(0);\" aria-expanded=\"false\"><i class=\"fa fa-angle-down\"></i></a>" +
				"<ul class=\"dropdown-menu dropdown-success\">" +
				"<li>" +
				"<a href=\"javascript:openFileOnLine('"+attachlist[i].extName+"','"+attachlist[i].attachId+"',1);\">查看 </a>" +
				"</li>" +
				"</ul>" +
				"</span>";
				}else
				{
					htmlimg+="<span class='attachDiv' onclick=\"showImg('"+attachlist[i].attachId+"')\";>" +
							"<img class='attachImg' src='/sys/file/getImage?attachId="+attachlist[i].attachId+"'/>" +
							"<div style='text-align:center;'>"+attachlist[i].oldName+"</div>" +
							"</span>";
				}
			}
		}else if(priv=="2")//可以打印
		{
			for(var i=0;i<attachlist.length;i++)
			{
			if(attachlist[i].extName!='.jpg'&&attachlist[i].extName!='.jpeg'&&attachlist[i].extName!='.png'&&attachlist[i].extName!='.bmp'&&attachlist[i].extName!='.tit'&&attachlist[i].extName!='.gif')
				{
				htmlattach+="<span class='btn-group' style=\"margin-right: 10px;margin-bottom: 10px;\" id='attachdiv_"+attachlist[i].attachId+"'>" +
				"<a class=\"btn btn-success\" href=\"javascript:void(0);\">"+attachlist[i].oldName+"</a>" +
				"<a class=\"btn btn-success  dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:void(0);\" aria-expanded=\"false\"><i class=\"fa fa-angle-down\"></i></a>" +
				"<ul class=\"dropdown-menu dropdown-success\">" +
				"<li>" +
				"<a href=\"javascript:openFileOnLine('"+attachlist[i].extName+"','"+attachlist[i].attachId+"',3);\">打印 </a>" +
				" </li>" +
				"<li>" +
				"<a href=\"javascript:openFileOnLine('"+attachlist[i].extName+"','"+attachlist[i].attachId+"',1);\">查看 </a>" +
				"</li>" +
				"</ul>" +
				"</span>";
				}else
				{
					htmlimg+="<span class='attachDiv' onclick=\"showImg('"+attachlist[i].attachId+"')\";>" +
								"<img class='attachImg' src='/sys/file/getImage?attachId="+attachlist[i].attachId+"'/>" +
								"<div style='text-align:center;'>"+attachlist[i].oldName+"</div>" +
							"</span>";
				}
			}
		}else if(priv=="3")//可以下载,打印
		{
			for(var i=0;i<attachlist.length;i++)
			{
			if(attachlist[i].extName!='.jpg'&&attachlist[i].extName!='.jpeg'&&attachlist[i].extName!='.png'&&attachlist[i].extName!='.bmp'&&attachlist[i].extName!='.tit'&&attachlist[i].extName!='.gif')
				{
				htmlattach+="<span class='btn-group' style=\"margin-right: 10px;margin-bottom: 10px;\" id='attachdiv_"+attachlist[i].attachId+"'>" +
				"<a class=\"btn btn-success\" href=\"javascript:void(0);\">"+attachlist[i].oldName+"</a>" +
				"<a class=\"btn btn-success  dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:void(0);\" aria-expanded=\"false\"><i class=\"fa fa-angle-down\"></i></a>" +
				"<ul class=\"dropdown-menu dropdown-success\">" +
				"<li>" +
				"<a href=\"javascript:openFileOnLine('"+attachlist[i].extName+"','"+attachlist[i].attachId+"',3);\">打印 </a>" +
				" </li>" +
				"<li>" +
				"<a href=\"/sys/file/getFileDown?attachId="+attachlist[i].attachId+"\">下载</a>" +
				" </li>" +
				"<li>" +
				"<a href=\"javascript:openFileOnLine('"+attachlist[i].extName+"','"+attachlist[i].attachId+"',1);\">查看 </a>" +
				"</li>" +
				"</ul>" +
				"</span>";
				}else
				{
					htmlimg+="<span class='attachDiv' onclick=\"showImg('"+attachlist[i].attachId+"')\";>" +
								"<img class='attachImg' src='/sys/file/getImage?attachId="+attachlist[i].attachId+"'/>" +
								"<div style='text-align:center;'>"+attachlist[i].oldName+"</div>" +
							"</span>";
				}
			}
		}else if(priv=="4")
		{
			for(var i=0;i<attachlist.length;i++)
			{
			if(attachlist[i].extName!='.jpg'&&attachlist[i].extName!='.jpeg'&&attachlist[i].extName!='.png'&&attachlist[i].extName!='.bmp'&&attachlist[i].extName!='.tit'&&attachlist[i].extName!='.gif')
				{
				htmlattach+="<span class='btn-group' style=\"margin-right: 10px;margin-bottom: 10px;\" id='attachdiv_"+attachlist[i].attachId+"'>" +
				"<a class=\"btn btn-success\" href=\"javascript:void(0);\">"+attachlist[i].oldName+"</a>" +
				"<a class=\"btn btn-success  dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:void(0);\" aria-expanded=\"false\"><i class=\"fa fa-angle-down\"></i></a>" +
				"<ul class=\"dropdown-menu dropdown-success\">" +
				"<li>" +
				"<a href=\"javascript:openFileOnLine('"+attachlist[i].extName+"','"+attachlist[i].attachId+"',2);\">打印 </a>" +
				"</li>" +
				"<li>" +
				"<a href=\"javascript:openFileOnLineTaoHong('"+attachlist[i].extName+"','"+attachlist[i].attachId+"',4);\">编辑 </a>" +
				"</li>" +
				"<li>" +
				"<a href=\"javascript:void(0);delattach('"+eId+"','"+attachlist[i].attachId+"')\">删除</a>" +
				"</li>" +
				"<li>" +
				"<a href=\"/sys/file/getFileDown?attachId="+attachlist[i].attachId+"\">下载</a>" +
				" </li>" +
				"<li>" +
				"<a href=\"javascript:openFileOnLine('"+attachlist[i].extName+"','"+attachlist[i].attachId+"',1);\">查看 </a>" +
				"</li>" +
				"</ul>" +
				"</span>";
				}else
				{
					htmlimg+="<span class='attachDiv' onclick=\"showImg('"+attachlist[i].attachId+"')\";>" +
								"<img class='attachImg' src='/sys/file/getImage?attachId="+attachlist[i].attachId+"'/>" +
								"<div style='text-align:center;'>"+attachlist[i].oldName+"</div>" +
							"</span>";
				}
			}
		}else if(priv=="5")
		{
			for(var i=0;i<attachlist.length;i++)
			{
			if(attachlist[i].extName!='.jpg'&&attachlist[i].extName!='.jpeg'&&attachlist[i].extName!='.png'&&attachlist[i].extName!='.bmp'&&attachlist[i].extName!='.tit'&&attachlist[i].extName!='.gif')
				{
				htmlattach+="<span class='btn-group' style=\"margin-right: 10px;margin-bottom: 10px;\" id='attachdiv_"+attachlist[i].attachId+"'>" +
				"<a class=\"btn btn-success\" href=\"javascript:void(0);\">"+attachlist[i].oldName+"</a>" +
				"<a class=\"btn btn-success  dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:void(0);\" aria-expanded=\"false\"><i class=\"fa fa-angle-down\"></i></a>" +
				"<ul class=\"dropdown-menu dropdown-success\">" +
				"<li>" +
				"<a href=\"javascript:openFileOnLine('"+attachlist[i].extName+"','"+attachlist[i].attachId+"',2);\">打印 </a>" +
				"</li>" +
				"<li>" +
				"<a href=\"javascript:openFileOnLineTaoHong('"+attachlist[i].extName+"','"+attachlist[i].attachId+"',5);\">套红 </a>" +
				"</li>" +
				"<li>" +
				"<a href=\"javascript:void(0);delattach('"+eId+"','"+attachlist[i].attachId+"')\">删除</a>" +
				"</li>" +
				"<li>" +
				"<a href=\"/sys/file/getFileDown?attachId="+attachlist[i].attachId+"\">下载</a>" +
				" </li>" +
				"<li>" +
				"<a href=\"javascript:openFileOnLine('"+attachlist[i].extName+"','"+attachlist[i].attachId+"',1);\">查看 </a>" +
				"</li>" +
				"</ul>" +
				"</span>";
				}else
				{
					htmlimg+="<span class='attachDiv' onclick=\"showImg('"+attachlist[i].attachId+"')\";>" +
								"<img class='attachImg' src='/sys/file/getImage?attachId="+attachlist[i].attachId+"'/>" +
								"<div style='text-align:center;'>"+attachlist[i].oldName+"</div>" +
							"</span>";
				}
			}
		}else
		{
			for(var i=0;i<attachlist.length;i++)
			{
			if(attachlist[i].extName!='.jpg'&&attachlist[i].extName!='.jpeg'&&attachlist[i].extName!='.png'&&attachlist[i].extName!='.bmp'&&attachlist[i].extName!='.tit'&&attachlist[i].extName!='.gif')
				{
				htmlattach+="<span class='btn-group' style=\"margin-right: 10px;margin-bottom: 10px;\" id='attachdiv_"+attachlist[i].attachId+"'>" +
				"<a class=\"btn btn-success\" href=\"javascript:void(0);\">"+attachlist[i].oldName+"</a>" +
				"<a class=\"btn btn-success  dropdown-toggle\" data-toggle=\"dropdown\" href=\"javascript:void(0);\" aria-expanded=\"false\"><i class=\"fa fa-angle-down\"></i></a>" +
				"<ul class=\"dropdown-menu dropdown-success\">" +
				"<li>" +
				"<a onclick=\"readfile('"+attachlist[i].extName+"','"+attachlist[i].attachId+"')\">查看</a>" +
				"</li>" +
				"<li>" +
				"<a href=\"javascript:void(0);delattach('"+eId+"','"+attachlist[i].attachId+"')\">删除</a>" +
				"</li>" +
				"	<li>" +
				"<a href=\"/sys/file/getFileDown?attachId="+attachlist[i].attachId+"\">下载</a>" +
				" </li>" +
				"</ul>" +
				"</span>";
				}else
					{
					htmlimg+="<span class='attachDiv' id='attachdiv_"+attachlist[i].attachId+"' onclick=\"delattach('"+eId+"','"+attachlist[i].attachId+"')\";><p class=\"diyControl\"><span class=\"diyCancel\"><i></i></span></p><img class='attachImg' src='/sys/file/getImage?attachId="+attachlist[i].attachId+"'/></span>";
					}
			}
	}
	$("#show_"+eId).append("<div>"+htmlimg+"</div><div>"+htmlattach+"</div>");
}


function showImg(attachId)
{
	window.open("/sys/file/getImage?attachId="+attachId, "_blank");
}

function readfile(extName,attachId)
{
if(extName==".txt"||extName==".html")
	{
		window.open("/sys/file/readFile?attachId="+attachId, "_blank");
	}else
	{
		
	}
}

/**
 * 
 * @param eId
 * @param attachId
 * @returns
 */
function delattach(eId,attachId)
{
	var v=$("#"+eId).attr("data_value");
	v = (v+",").replace(attachId+",","");
	$("#"+eId).attr("data_value",v);
	$("#attachdiv_"+attachId).remove();
}

function getFileType(extName)
{
	extName = extName.toUpperCase();
	if(extName=='.JPG'||extName=='.PNG'||extName=='.JPEG'||extName=='.BMP'||extName=='.TIT'||extName=='.GIF')
		{
		return "img";
		}else
			{
				return "doc";
			}
	}

function createTableAttach(attachIds)
{
	var html="";
	if(attachIds!=""&&attachIds!=undefined)
		{
		$.ajax({
			url : "/sys/file/getAttachList",
			type : "post",
			dataType : "json",
			async : false,
			data:{
				attachIds:attachIds
			},
			success : function(data) {
				if(data.status==200){
					var datalist = data.list;
					for(var i=0;i<datalist.length;i++)
						{
							html+="<a onclick=\"openFileOnLine('"+datalist[i].extName+"','"+datalist[i].attachId+"','1')\" href=\"javascript:void(0);\" class=\"btn btn-palegreen btn-xs\">"+datalist[i].oldName+"</a>";
						}
				}else if(data.status==100)
				{
					console.log(data.msg);
				}else
					{
					console.log(data.msg);
					}
			}
		});
	}
	return html;
}


function openFileOnLineTaoHong(extName,attachId,priv)
{
	var extName = extName.toUpperCase();
	if(extName==".TXT"||extName==".HTML")
	{
		window.open("/sys/file/readFile?attachId="+attachId, "_blank");
	}else if(extName==".DOC"||extName==".DOT"||extName==".DOCX"||extName==".DOTX")
	{
		POBrowser.openWindowModeless("/office/taohongword?runId="+runId+"&attachId="+attachId+"&openModeType="+priv,"width=1200px;height=800px;");
	}else if(extName==".XLS"||extName==".XLSX"||extName==".CSV")
	{
		POBrowser.openWindowModeless("/office/openexcel?attachId="+attachId+"&openModeType="+priv,"width=1200px;height=800px;");
	}else if(extName==".PPT"||extName==".PPTX")
	{
		POBrowser.openWindowModeless("/office/openppt?attachId="+attachId+"&openModeType="+priv,"width=1200px;height=800px;");
	}else if(extName==".PDF")
	{
		POBrowser.openWindowModeless("/office/openpdf?attachId="+attachId+"&openModeType="+priv,"width=1200px;height=800px;");
	}else if(extName=='.JPG'||extName=='.PNG'||extName=='.JPEG'||extName=='.BMP'||extName=='.TIT'||extName=='.GIF')
	{
		window.open("/sys/file/getImage?attachId="+attachId, "_blank");
	}else
	{
		top.layer.msg("文件格式不支持在线打开！");
	}
}

/**
 * 在线打开文件
 * @param extName
 * @param attachId
 * @param priv
 * @returns
 */
function openFileOnLine(extName,attachId,priv)
{
	var extName = extName.toUpperCase();
	if(extName==".TXT"||extName==".HTML")
	{
		window.open("/sys/file/readFile?attachId="+attachId, "_blank");
	}else if(extName==".DOC"||extName==".DOT"||extName==".DOCX"||extName==".DOTX")
	{
		POBrowser.openWindowModeless("/office/openword?attachId="+attachId+"&openModeType="+priv,"width=1200px;height=800px;");
	}else if(extName==".XLS"||extName==".XLSX"||extName==".CSV")
	{
		POBrowser.openWindowModeless("/office/openexcel?attachId="+attachId+"&openModeType="+priv,"width=1200px;height=800px;");
	}else if(extName==".PPT"||extName==".PPTX")
	{
		POBrowser.openWindowModeless("/office/openppt?attachId="+attachId+"&openModeType="+priv,"width=1200px;height=800px;");
	}else if(extName==".PDF")
	{
		POBrowser.openWindowModeless("/office/openpdf?attachId="+attachId+"&openModeType="+priv,"width=1200px;height=800px;");
	}else if(extName=='.JPG'||extName=='.PNG'||extName=='.JPEG'||extName=='.BMP'||extName=='.TIT'||extName=='.GIF')
	{
		window.open("/sys/file/getImage?attachId="+attachId, "_blank");
	}else
	{
		top.layer.msg("文件格式不支持在线打开！");
	}
}


function openNetDiskFileOnLine(extName,netDiskId,path)
{
	var priv=1;
	var attachId="";
	var extName = extName.toUpperCase();
	if(extName==".TXT"||extName==".HTML")
	{
		window.open("/sys/file/readFile?attachId="+attachId+"&netDiskId="+netDiskId+"&path="+encodeURIComponent(path), "_blank");
	}else if(extName==".DOC"||extName==".DOT"||extName==".DOCX"||extName==".DOTX")
	{
		POBrowser.openWindowModeless("/office/openword?attachId="+attachId+"&openModeType="+priv+"&netDiskId="+netDiskId+"&path="+path,"width=1200px;height=800px;");
	}else if(extName==".XLS"||extName==".XLSX"||extName==".CSV")
	{
		POBrowser.openWindowModeless("/office/openexcel?attachId="+attachId+"&openModeType="+priv+"&netDiskId="+netDiskId+"&path="+path,"width=1200px;height=800px;");
	}else if(extName==".PPT"||extName==".PPTX")
	{
		POBrowser.openWindowModeless("/office/openppt?attachId="+attachId+"&openModeType="+priv+"&netDiskId="+netDiskId+"&path="+path,"width=1200px;height=800px;");
	}else if(extName==".PDF")
	{
		POBrowser.openWindowModeless("/office/openpdf?attachId="+attachId+"&openModeType="+priv+"&netDiskId="+netDiskId+"&path="+path,"width=1200px;height=800px;");
	}else if(extName=='.JPG'||extName=='.PNG'||extName=='.JPEG'||extName=='.BMP'||extName=='.TIT'||extName=='.GIF')
	{
		window.open("/sys/file/getImage?attachId="+attachId+"&netDiskId="+netDiskId+"&path="+encodeURIComponent(path), "_blank");
	}else
	{
		top.layer.msg("文件格式不支持在线打开！");
	}
}