$(function(){
	getMyPhoto();
})

function getMyPhoto()
{
	$.ajax({
		url : "/ret/fileget/getMyPhotoList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				var html="";
				for(var i=0;i<data.list.length;i++)
					{
						html+="<a href=\"javascript:void(0);\" data-value=\""+data.list[i].photoId+"\" class=\"btn btn-blue btn-lg js-myphoto\" style=\"margin-left:20px;margin-top: 0px;\">"+data.list[i].photoTitle+"</a>";
					}
				$("#dropdownbuttons").html(html);
				$(".js-myphoto").each(function(){
					$(this).unbind("clcik").click(function(){
						var photoId = $(this).attr("data-value");
						 getPhotoImgById(photoId);
					})
				});
			}
		}
	});
}


function getPhotoImgById(photoId)
{
	$.ajax({
		url : "/ret/fileget/getMyPhotoFileList",
		type : "post",
		dataType : "json",
		data:{photoId:photoId},
		success : function(data) {
			console.log(data);
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				var html="";
				for(var i=0;i<data.list.length;i++)
					{
					html+="<div class='col-sm-6 col-md-2'>\n"+
	                "<div class='thumbnail'>\n"+
	                   " <a class='lightbox' href='"+data.list[i].url+"'>\n"+
	                        "<img src='"+data.list[i].url+"' alt='"+data.list[i].fileName+"'>\n"+
	                    "</a>\n"+
	                    "<div class='caption'>\n"+
	                        "<h3>"+data.list[i].fileName+"</h3>\n"+
	                        "<p>图片尺寸："+data.list[i].fileSize+" 创建时间："+data.list[i].createTime+" </p>\n"+
	                    "</div>\n"+
	                "</div>\n"+
	           "</div>";
					}
				console.log(html);
				$("#photos").html(html);
				baguetteBox.run('.tz-gallery');
			}
		}
	});
}