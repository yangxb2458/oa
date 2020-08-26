$(function(){
	 $('#simplewizard').wizard().on('finished', function (e) {
		 updateBiTemplate();
     });
	getAllBpmFlowListByManage();
	$(".js-queryfield").unbind("click").click(function(){
		document.getElementById("form1").reset();
		getParentFlowField();
		$("#queryModal").modal("show");
		$(".js-addqueryfield").unbind("click").click(function(){
			var field=$("#formField").val();
			var fieldTitle = $("#formField").find("option:selected").text();
			var queryTitle = $("#queryTitle").val();
			var index=Date.parse(new Date());
			var html="<tr data-value='"+index+"' class=\"js-querytr js-queryfield-tr"+index+"\"><td data-value=\""+field+"\">"+fieldTitle+"</td>" +
					"<td>"+queryTitle+"</td><td><i class=\"fa fa-arrow-up\" onclick=\"goqueryup(this)\"></i><i class=\"fa fa-arrow-down\"  onclick=\"goquerydown(this)\"></i><i class=\"fa fa-times\"  onclick=\"goquerydel(this)\"></i></td></tr>"
			$("#queryFields").append(html);
			$("#queryModal").modal("hide");	
		})
	})
	
	$(".js-tablefield").unbind("click").click(function()
	{
		document.getElementById("form2").reset();
		getParentFlowField1();
		$("#tableModal").modal("show");
		$(".js-addtablefield").unbind("click").click(function(){
			var field=$("#formField1").val();
			var fieldTitle = $("#formField1").find("option:selected").text();
			var tableTitle = $("#tableTitle").val();
			var index=Date.parse(new Date());
			var html="<tr data-value='"+index+"' class=\"js-tabletr js-tablefield-tr"+index+"\"><td data-value=\""+field+"\">"+fieldTitle+"</td>" +
					"<td>"+tableTitle+"</td><td><i class=\"fa fa-arrow-up\" onclick=\"gotableup(this)\"></i><i class=\"fa fa-arrow-down\" onclick=\"gotabledown(this)\"></i><i class=\"fa fa-times\"  onclick=\"gotabledel(this)\"></i></td></tr>"
			$("#tableFields").append(html);
			$("#tableModal").modal("hide");	
		})
	});
	$(".btn-next").click(function(){
		if($("#flowId").val()=="")
			{
			top.layer.msg("请先选择对应的流程！");
			$(".btn-prev").trigger("click");
			return false;
			}
	})
	getBpmBiTemplateById();
})

function gotableup(Obj)
{
	var objParentTR = $(Obj).parent().parent();
	var prevTR = objParentTR.prev();
	if (prevTR.length > 0) {
	prevTR.insertAfter(objParentTR);
	}
}

function gotabledown(Obj)
{
	var objParentTR = $(Obj).parent().parent();
	var nextTR = objParentTR.next();
	if (nextTR.length > 0) {
	nextTR.insertBefore(objParentTR);
	}
}

function gotabledel(Obj)
{
	var index = $(Obj).parent("td").parent("tr").attr("data-value");
	$(".js-tablefield-tr"+index).remove();
	console.log(index)

}



function goqueryup(Obj)
{
	var objParentTR = $(Obj).parent().parent();
	var prevTR = objParentTR.prev();
	if (prevTR.length > 0) {
	prevTR.insertAfter(objParentTR);
	}
}

function goquerydown(Obj)
{
	var objParentTR = $(Obj).parent().parent();
	var nextTR = objParentTR.next();
	if (nextTR.length > 0) {
	nextTR.insertBefore(objParentTR);
	}
}

function goquerydel(Obj)
{
	var index = $(Obj).parent("td").parent("tr").attr("data-value");
	$(".js-queryfield-tr"+index).remove();
	console.log(index)

}

function getAllBpmFlowListByManage()
{
	$.ajax({
		url : "/ret/bpmget/getAllBpmFlowListByManage",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
				{
					var html = "<option value=''>全部</option>"
					for(var i=0;i<data.list.length;i++)
						{
						html+="<option value='"+data.list[i].flowId+"'>"+data.list[i].flowName+"</option>"
						}
				$("#flowId").html(html);
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

function getParentFlowField()
{
	$.ajax({
		url : "/ret/bpmget/getBpmFormFieldByFlowId",
		dataType : "json",
		type : "post",
		data : {
			flowId : $("#flowId").val()
		},
		async : false,
		success : function(data) {
			if(data.status=="200")
			{
				var html="";
				for(var i=0;i<data.list.length;i++)
				{
					html+="<option value='"+ data.list[i].name + "'>"+data.list[i].title+"</option>"
				}
				$("#formField").html(html);
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
function getParentFlowField1()
{
	$.ajax({
		url : "/ret/bpmget/getBpmFormFieldByFlowId",
		dataType : "json",
		type : "post",
		data : {
			flowId : $("#flowId").val()
		},
		async : false,
		success : function(data) {
			if(data.status=="200")
			{
				var html="";
				for(var i=0;i<data.list.length;i++)
				{
					html+="<option value='"+ data.list[i].name + "'>"+data.list[i].title+"</option>"
				}
				$("#formField1").html(html);
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

function updateBiTemplate()
{
	$.ajax({
		url : "/set/bpmset/updateBpmBiTemplate",
		type : "post",
		dataType : "json",
		data:{
			templateId:templateId,
			sortNo:$("#sortNo").val(),
			title:$("#title").val(),
			flowId:$("#flowId").val(),
			remark:$("#remark").val(),
			userPriv:$("#userPriv").attr("data-value"),
			deptPriv:$("#deptPriv").attr("data-value"),
			levelPriv:$("#levelPriv").attr("data-value"),
			queryFields:JSON.stringify(getQueryFields()),
			tableHead:JSON.stringify(getTableFields()),
			totalFlag:$("input:radio[name='totalFlag']:checked").val(),
			exportFlag:$("input:radio[name='exportFlag']:checked").val(),
			remark:$("#remark").val()
			},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				window.location.reload()
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

function getQueryFields()
{
	var queryField=[];
	$(".js-querytr").each(function(){
		var tdArr = $(this).children();
		var json={};
		json.field=$(tdArr[0]).attr("data-value");
		json.text=$(tdArr[0]).text();
		json.fieldTitle=$(tdArr[1]).text();
		queryField.push(json);
	})
	return queryField;
}

function getTableFields()
{
	var tableField=[];
	$(".js-tabletr").each(function(){
		var tdArr = $(this).children();
		var json={};
		json.field=$(tdArr[0]).attr("data-value");
		json.text=$(tdArr[0]).text();
		json.fieldTitle=$(tdArr[1]).text();
		tableField.push(json);
	})
	return tableField;
}
function getBpmBiTemplateById()
{
	$.ajax({
		url : "/ret/bpmget/getBpmBiTemplateById",
		type : "post",
		dataType : "json",
		data:{templateId:templateId},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				var recordInfo = data.list;
				for(var id in recordInfo)
				{
					if(id=="userPriv")
					{
						$("#userPriv").attr("data-value", recordInfo[id]);
						$("#userPriv").val(getUserNameByStr(recordInfo[id]));
					}else if(id=="deptPriv")
					{
						$("#deptPriv").attr("data-value", recordInfo[id]);
						$("#deptPriv").val(getDeptNameByDeptIds(recordInfo[id]));
					}else if(id=="levelPriv")
					{
						$("#levelPriv").attr("data-value",recordInfo[id]);
						$("#levelPriv").val(getUserLevelStr(recordInfo[id]));
					}else if(id=="queryFields")
					{
						var queryFields = $.parseJSON(recordInfo[id]);
						for(var i=0;i<queryFields.length;i++)
							{
							var field=queryFields[i].field;
							var fieldTitle = queryFields[i].text;
							var queryTitle = queryFields[i].fieldTitle;
							var index=Date.parse(new Date());
							var html="<tr data-value='"+i+"' class=\"js-querytr js-queryfield-tr"+i+"\"><td data-value=\""+field+"\">"+fieldTitle+"</td>" +
									"<td>"+queryTitle+"</td><td><i class=\"fa fa-arrow-up\" onclick=\"goqueryup(this)\"></i><i class=\"fa fa-arrow-down\"  onclick=\"goquerydown(this)\"></i><i class=\"fa fa-times\"  onclick=\"goquerydel(this)\"></i></td></tr>"
							$("#queryFields").append(html);
							}
						
						
					}else if(id=="tableHead")
					{
						var tableHead = $.parseJSON(recordInfo[id]);
						for(var i=0;i<tableHead.length;i++)
						{
						var field=tableHead[i].field;
						var fieldTitle = tableHead[i].text;
						var tableTitle =  tableHead[i].fieldTitle;
						var index=Date.parse(new Date());
						var html="<tr data-value='"+i+"' class=\"js-tabletr js-tablefield-tr"+i+"\"><td data-value=\""+field+"\">"+fieldTitle+"</td>" +
								"<td>"+tableTitle+"</td><td><i class=\"fa fa-arrow-up\" onclick=\"gotableup(this)\"></i><i class=\"fa fa-arrow-down\" onclick=\"gotabledown(this)\"></i><i class=\"fa fa-times\"  onclick=\"gotabledel(this)\"></i></td></tr>"
						$("#tableFields").append(html);
						}
					}else if(id=="totalFlag")
					{
						$("input:radio[name='totalFlag'][value='"+recordInfo[id]+"']").attr("checked","checked");
					}else if(id=="exportFlag")
					{
						$("input:radio[name='exportFlag'][value='"+recordInfo[id]+"']").attr("checked","checked");
					}else
					{
						$("#"+id).val(recordInfo[id]);
					}
				}
				}
		}
	})
}