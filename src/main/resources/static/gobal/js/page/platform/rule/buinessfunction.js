$(function(){
	setqueryfields();
	$(".js-add-showfield").unbind("click").click(function(){
		document.getElementById("form1").reset();
		$("#showFieldsModal").modal("show");
		$(".js-addtablefield").unbind("click").click(function(){
			var field=$("#fieldsSelect").val();
			var fieldTitle = $("#fieldsSelect").find("option:selected").text();
			var tableTitle = $("#title").val();
			var width = $("#width").val();
			var align = $("#align").val();
			var alignTitle = $("#align").find("option:selected").text();
			var formatter = $("#formatter").val();
			var index=Date.parse(new Date());
			if(tableTitle=="")
			{
				tableTitle = fieldTitle;
			}
			if(tableTitle=="")
			{
				top.layer.msg("显示名称不能为空！");
			}
			if(width=="")
			{
				top.layer.msg("宽度不能为空！")
			}
			var html="<tr data-value='"+index+"' class=\"js-tabletr js-tablefield-tr"+index+"\">" +
					"<td>"+tableTitle+"</td>"+
					"<td data-value=\""+field+"\">"+fieldTitle+"</td>" +
					"<td data-value=\""+width+"\">"+width+"px</td>" +
					"<td data-value=\""+align+"\">"+alignTitle+"</td>" +
					"<td data-value=\""+formatter+"\">"+formatter+"</td>" +
					"<td><i class=\"fa fa-arrow-up\" onclick=\"gotableup(this)\"></i><i class=\"fa fa-arrow-down\" onclick=\"gotabledown(this)\"></i><i class=\"fa fa-times\"  onclick=\"gotabledel(this)\"></i></td></tr>"
			$("#tableFields").append(html);
			$("#showFieldsModal").modal("hide");	
		})
	})
	$("#createbut").unbind("click").click(function(){
		addPlatformBuinessRule();
	});
	getPlatformBuinessRule();
});
function setqueryfields()
{
	var recordList = getpagefields(pageId);
	for(var i=0;i<recordList.length;i++)
	{
		$("#queryfields").append("<div class='checkbox' style='display:inline-block;'><label><input class='colored-danger' name='queryFields' type='checkbox' value='"
				+ recordList[i].name + "'><span class='text'>" + recordList[i].title + "</span></label></div>");
		
		$("#mustFields").append("<div class='checkbox' style='display:inline-block;'><label><input class='colored-danger' name='mustFields' type='checkbox' value='"
				+ recordList[i].name + "'><span class='text'>" + recordList[i].title + "</span></label></div>");
		$("#numFields").append("<div class='checkbox' style='display:inline-block;'><label><input class='colored-danger' name='numFields' type='checkbox' value='"
				+ recordList[i].name + "'><span class='text'>" + recordList[i].title + "</span></label></div>");
		$("#fieldsSelect").append("<option value="+ recordList[i].name + ">"+ recordList[i].title+"</option>");
	}
}


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
function getTableFields()
{
	var tableField=[];
	$(".js-tabletr").each(function(){
		var tdArr = $(this).children();
		var json={};
		json.text=$(tdArr[0]).text();
		json.field=$(tdArr[1]).attr("data-value");
		json.fieldTitle=$(tdArr[1]).text();
		json.width=$(tdArr[2]).attr("data-value");
		json.widthText=$(tdArr[2]).text();
		json.align=$(tdArr[3]).attr("data-value");
		json.alignText=$(tdArr[3]).text();
		json.formatter =$(tdArr[4]).text();
		tableField.push(json);
	})
	return tableField;
}

function addPlatformBuinessRule()
{
	$.ajax({
		url : "/set/platformset/addPlatformBuinessRule",
		type : "post",
		dataType : "json",
		data:{
			menuId:menuId,
			pageId:pageId,
			mustFields:getCheckBoxValue("mustFields"),
			numFields:getCheckBoxValue("numFields"),
			manageQueryFields:getCheckBoxValue("queryFields"),
			manageShowFields:JSON.stringify(getTableFields()),
			exportFlag:$("input:radio[name='exportFlag']:checked").val(),
			totalFlag:$("input:radio[name='totalFlag']:checked").val(),
			selectall:$("input:radio[name='selectall']:checked").val(),
			manageOption:getCheckBoxValue("option")
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

function getPlatformBuinessRule()
{
	$.ajax({
		url : "/ret/platformget/getPlatformBuinessRuleById",
		type : "post",
		dataType : "json",
		data:{
			menuId:menuId,
			pageId:pageId
			},
		success : function(data) {
			if(data.status=="200")
			{
				var recordInfo = data.list;
				for(var id in recordInfo)
					{
						if(id=="manageShowFields")
							{
							var showFields = $.parseJSON(recordInfo[id]);
							for(var i=0;i<showFields.length;i++)
								{
								var field=showFields[i].field;
								var text = showFields[i].text;
								var fieldTitle = showFields[i].fieldTitle;
								var width = showFields[i].width;
								var widthText = showFields[i].widthText;
								var align = showFields[i].align;
								var alignText = showFields[i].alignText;
								var formatter = showFields[i].formatter;
								var html="<tr data-value='"+i+"' class=\"js-tabletr js-tablefield-tr"+i+"\"><td>"+text+"</td>" +
										"<td data-value='"+field+"'>"+fieldTitle+"</td><td data-value='"+width+"'>"+widthText+"</td>" +
										"<td data-value='"+align+"'>"+alignText+"</td><td>"+formatter+"</td>" +
										"<td><i class=\"fa fa-arrow-up\" onclick=\"gotableup(this)\"></i><i class=\"fa fa-arrow-down\" onclick=\"gotabledown(this)\"></i><i class=\"fa fa-times\"  onclick=\"gotabledel(this)\"></i></td></tr>"
								$("#tableFields").append(html);
								}
							}else if(id=="mustFields")
							{
									var valArr = recordInfo[id].split(",");
									for (var j = 0; j < valArr.length; j++) {
										$("input:checkbox[name='mustFields'][value='" + valArr[j] + "']").attr("checked", "checked");
									}
							}else if(id=="numFields")
							{
								var valArr = recordInfo[id].split(",");
								for (var j = 0; j < valArr.length; j++) {
									$("input:checkbox[name='numFields'][value='" + valArr[j] + "']").attr("checked", "checked");
								}
							}else if(id=="manageQueryFields")
							{
								var valArr = recordInfo[id].split(",");
								for (var j = 0; j < valArr.length; j++) {
									$("input:checkbox[name='queryFields'][value='" + valArr[j] + "']").attr("checked", "checked");
								}
							}else if(id=="manageOption")
							{
								var valArr = recordInfo[id].split(",");
								for (var j = 0; j < valArr.length; j++) {
									$("input:checkbox[name='option'][value='" + valArr[j] + "']").attr("checked", "checked");
								}
							}else if(id=="exportFlag")
							{
								$("input:radio[name='exportFlag'][value='"+recordInfo[id]+"']").attr("checked","checked");
							}else if(id=="totalFlag")
							{
								$("input:radio[name='totalFlag'][value='"+recordInfo[id]+"']").attr("checked","checked");
							}else if(id=="selectall")
							{
								$("input:radio[name='selectall'][value='"+recordInfo[id]+"']").attr("checked","checked");
							}
							
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
}