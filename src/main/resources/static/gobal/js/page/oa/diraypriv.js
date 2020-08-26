$(function(){
	 $('#template').summernote({ height:300 });
	 getDiaryPriv();
	 $(".js-setbtn").unbind("click").click(function(){
		 setDiaryPriv(); 
	 });
})

function getDiaryPriv()
{
	$.ajax({
		url : "/ret/oaget/getDiaryPriv",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status="200")
				{
					$("#lockDay").val(data.list.lockDay);
					$("input:radio[name='shareStatus'][value='"+data.list.shareStatus+"']").attr("checked",true);
					$("input:radio[name='commStatus'][value='"+data.list.commStatus+"']").attr("checked",true);
					$('#template').code(data.list.template);
				}else if(data.status=="100")
					{
					top.layer.msg(data.msg);
					}else
						{
						console.log(data);
						}
		}
	});
}

function setDiaryPriv()
{
	$.ajax({
		url : "/set/oaset/setDiaryPriv",
		type : "post",
		dataType : "json",
		data:{
			lockDay:$("#lockDay").val(),
			shareStatus: getCheckBoxValue("shareStatus"),
			commStatus:getCheckBoxValue("commStatus"),
			template:$("#template").code()
		},
		success : function(data) {
			console.log(data);
			if(data.status="200")
				{
				top.layer.msg(data.msg);
				}else if(data.status=="100")
					{
					top.layer.msg(data.msg);
					}else
						{
						console.log(data);
						}
					
			
		}
	});
}