$(function(){
	$.ajax({
		url : "/ret/bpmget/getBpmBusinessById",
		type : "post",
		dataType : "json",
		data:{businessId:businessId},
		success : function(data) {
			if(data.status=="200")
			{
				var recordInfo = data.list;
				for(var id in recordInfo)
				{
					if(id=="dbSourceId")
					{
						$.ajax({
							url : "/ret/sysget/getDbSource",
							type : "post",
							dataType : "json",
							async : false,
							data:{
								dbSourceId:recordInfo.dbSourceId
							},
							success : function(data) {
								if(data.status=="500")
									{
									console.log(data.msg);
									}else if(data.status=="100")
										{
										top.layer.msg(data.msg);
										}else
											{
											$("#dbSourceId").html(data.list.dbSourceName);
											}
							}
						});
					}else if(id=="flowId")
					{
						$.ajax({
							url : "/ret/bpmget/getBpmFlow",
							type : "post",
							dataType : "json",
							async : false,
							data:{
								flowId:recordInfo.flowId
							},
							success : function(data) {
								if(data.status=="500")
									{
									console.log(data.msg);
									}else if(data.status=="100")
										{
										top.layer.msg(data.msg);
										}else
											{
											$("#flowId").html(data.list.flowName);
											}
							}
						});
					}else if(id=="processId")
					{
						$.ajax({
							url : "/ret/bpmget/getBpmProcessPrcs",
							type : "post",
							dataType : "json",
							async : false,
							data:{
								processId:recordInfo.processId
							},
							success : function(data) {
								if(data.status=="500")
									{
									console.log(data.msg);
									}else if(data.status=="100")
										{
										top.layer.msg(data.msg);
										}else
											{
											$("#processId").html(data.list.prcsName);
											}
							}
						});
					}else if(id=="status")
					{
						if(recordInfo.status=="0")
						{
							$("#"+id).html("停用中");
						}else if(recordInfo.status=="1")
						{
							$("#"+id).html("启用中");
						}else
						{
							$("#"+id).html("未知状态");
						}
					}else 
					{
						$("#"+id).html(recordInfo[id]);
					}
				}
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
		})
})