$(function(){
	$.ajax({
		url : "/ret/bpmget/getbpmmobileformset",
		type : "post",
		dataType : "json",
		data : {
			formId : formId
		},
		success : function(data) {
			if(data.status=="200")
			{
				$("#formTitle").html(data.list.formTitle);
				$("#jstextarea").val(data.list.mobileScript);
				$("#styletextarea").val(data.list.mobileStyle);
				$("#leftdata").html(data.list.mobileHtmlCode);
				var rightData = data.list.rightdata;
				if(rightData!=null)
				{
					$("#rightdata").html(rightData);
				}
			}else if(data.status=="100")
			{
				console.log(data.msg);
			}else
			{
				console.log(data.msg);
			}
			
		}
	});
	$(".saveform").unbind("click").click(function(){
		save();
	})
	$(".js-javascript").unbind("click").click(function(){
		$("#formjavascript").modal("show");
		$(".js-javascriptsave").unbind("click").click(function(){
			$.ajax({
		        url:"/set/bpmset/updateBpmForm",
		        dataType:"json",
		        type : "post",
		        data:{
		        	formId:formId,
		        	mobileScript:$("#jstextarea").val()
		        	},
		        async:false,
		        success:function(data){
		        	if(data.status==200)
		        		{
		        		top.layer.msg(data.msg);
		        		}else if(data.status==100)
						{
							top.layer.msg(data.msg);
						}else
		        			{
		        			console.log(data.msg)
		        			}
		        }
		    });
			$("#formjavascript").modal("hide");
		});
	});
	$(".js-style").unbind("click").click(function(){
		$("#formstyle").modal("show");
		$(".js-stylesave").unbind("click").click(function(){
			   $.ajax({
			        url:"/set/bpmset/updateBpmForm",
			        dataType:"json",
			        type : "post",
			        data:{
			        	formId:formId,
			        	mobileStyle:$("#styletextarea").val()
			        	},
			        async:false,
			        success:function(data){
			        	if(data.status==200)
			        		{
			        		top.layer.msg(data.msg);
			        		}else if(data.status==100)
							{
								top.layer.msg(data.msg);
							}else
			        			{
			        			console.log(data.msg)
			        			}
			        }
			    });
			   $("#formstyle").modal("hide");
		});
	});
})

function save()
{
	var html=$("#leftdata").html();
	$.ajax({
		url : "/set/bpmset/updateMobileBpmForm",
		type : "post",
		dataType : "json",
		data : {
			formId : formId,
			mobileHtmlCode:html
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
	});
}