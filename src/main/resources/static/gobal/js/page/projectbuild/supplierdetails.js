$(function(){
	$.ajax({
		url : "/ret/projectbuildsupplierget/getProjectBuildSupplierById",
		type : "post",
		dataType : "json",
		data : {
			supplierId:supplierId
		},
		success : function(data) {
			if (data.status == 200) {
				for(name in data.list)
					{
						if(name=="level")
						{
							if(data.list.level=="1")
				    		{
				    			$("#level").html("优秀供应商");
				    		}else if(data.list.level=="2")
				    		{
				    			$("#level").html("一般供应商");
				    		}else if(data.list.level=="3")
				    		{
				    			$("#level").html("劣质供应商");
				    		}
						}else if(name=="industry")
						{
							if(data.list.industry=="1")
				    		{
				    			$("#industry").html("代理");
				    		}else if(data.list.industry=="2")
				    		{
				    			$("#industry").html("厂家");
				    		}else if(data.list.industry=="0")
				    		{
				    			$("#industry").html("其它");
				    		}
						}else if(name=="status")
						{
							if(data.list.status=="1")
				    		{
				    			$("#status").html("优");
				    		}else if(data.list.industry=="2")
				    		{
				    			$("#status").html("良");
				    		}else if(data.list.industry=="3")
				    		{
				    			$("#status").html("差");
				    		}
						}else if(name=="attach")
						{
							$("#projectbuildattach").attr("data_value",data.list.attach);
							createAttach("projectbuildattach","1");
						}else
						{
							$("#"+name).html(data.list[name]);
						}
					}
			} else if(data.status==100)
				{
					top.layer.msg(data.msg);
				}else
				{
				console.log(data.msg);
			}
		}
	});
})