$(function(){
	$.ajax({
		url : "/ret/documentget/getFormTableFields",
		type : "post",
		dataType : "json",
		data : {
			formId : formId
		},
		success : function(data) {
			if(data.status=="200")
			{
				$("#title").html(data.list.formTitle+"&nbsp;&nbsp;&nbsp;&nbsp;("+data.list.tableName+")")
				var fieldsInfo = data.list.fieldsInfo;
				for(var i=0;i<fieldsInfo.length;i++)
				{
					var html="<tr><td>"+(i+1)+"</td><td>"+fieldsInfo[i].remark+"</td><td>"+fieldsInfo[i].columnName+"</td><td>"+fieldsInfo[i].columnType+"</td><td>"+fieldsInfo[i].dataType+"</td></tr>";
					$("#tbody").append(html)
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
})