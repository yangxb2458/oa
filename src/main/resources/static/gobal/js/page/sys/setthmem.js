$(function(){
	$(".js-setbtn").unbind("click").click(function(){
		setSysInterface();
	})
	$("#background").zyUpload({
		width            :   "650px",                 // 宽度
		height           :   "100%",                 // 宽度
		itemWidth        :   "120px",                 // 文件项的宽度
		itemHeight       :   "100px",                 // 文件项的高度
		url              :   "/sys/file/uploadBackgroundImg",  // 上传文件的路径
		multiple         :   true,                    // 是否可以多个文件上传
		dragDrop         :   true,                    // 是否可以拖动上传文件
		del              :   true,                    // 是否可以删除文件
		finishDel        :   false,  				  // 是否在上传文件完成后删除预览
		/* 外部获得的回调接口 */
		onSelect: function(files, allFiles){                    // 选择文件的回调方法
		},
		onDelete: function(file, surplusFiles){                     // 删除一个文件的回调方法
		},
		onSuccess: function(file){                    // 文件上传成功的回调方法
		},
		onFailure: function(file){                    // 文件上传失败的回调方法
		},
		onComplete: function(responseInfo){           // 上传完成的回调方法
		}
	});
	if(backgroundimg!='')
	{
		var arr = backgroundimg.split("*");
		for(var i=0;i<arr.length;i++)
		{
			$("#uploadInf").append("<div style='width:100px;display:inline-block;text-align: center;margin-left:5px;margin-top:5px;'><img data-value='"+arr[i]+"' width='100px' height='100px' src='/sys/file/getBackgroundImg?fileName="+arr[i]+"'/><a style='cursor: pointer;' onclick='delimg(this)'>删除</a></div>");
		}
	}
	
})

function delimg(Obj)
{
	$(Obj).parent("div").remove();
}

function setSysInterface()
{
	$.ajax({
		url : "/set/sysset/setInterface",
		type : "post",
		dataType : "json",
		data:{
			sysTitle:$("#sysTitle").val(),
			idea:$("#idea").val(),
			reocrdNumber:$("#reocrdNumber").val(),
			backgroundImg:getBackGroundImg(),
			tecSupport:$("#tecSupport").val()
			},
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
	})
}


function getBackGroundImg()
{
	var arr = [];
	$("#uploadInf").find("img").each(function(){
		arr.push($(this).attr("data-value"))
	});
	return arr.join("*");
}

