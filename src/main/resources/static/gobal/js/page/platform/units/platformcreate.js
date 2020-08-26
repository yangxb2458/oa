$(function(){
	$("#createbut").unbind("clcik").click(function(){
		insertPageValue();
	});
});

function insertPageValue()
{
	if(checkFields(mustFields,numFields))
	{
		$.ajax({
			url : "/set/platformset/insertPageFormData",
			type : "post",
			dataType : "json",
			data : {
				pageId:pageId,
				formData:JSON.stringify(getparams())
			},
			success : function(data) {
				if (data.status == "200") {
					top.layer.msg(data.msg);
					location.reload();
				} else if(data.status=="100"){
					top.layer.msg(data.msg);
				}else {
					console.log(data.msg);
				}
			}
		});
	}
}

function checkFields(mustFields,numFields)
{
	var flag=true;
	if(mustFields!="")
	{
		var arr = mustFields.split(",");
		for(var i=0;i<arr.length;i++)
			{
				$("[name='"+arr[i]+"']").each(function(){
					if($(this).val()=="")
					{
						var title = $(this).attr("title");
						top.layer.msg(title+"不能为空！");
						flag=false;
					}
				})
			}
	}
	if(!flag)
	{
		return flag;
	}
	if(numFields!="")
	{
		var arr = numFields.split(",");
		for(var i=0;i<arr.length;i++)
			{
				$("[name='"+arr[i]+"']").each(function(){
					if(!$.isNumeric($(this).val()))
					{
						var title = $(this).attr("title");
						top.layer.msg(title+"必须为数字！");
						flag=false;
					}
				})
				if(!flag)
				{
					break;
				}
			}
	}
	return flag;
}