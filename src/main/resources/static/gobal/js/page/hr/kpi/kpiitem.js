$(function(){
	$(".js-child-item").unbind("click").click(function(){
		addChildItem();
	})
	$(".js-add-save").unbind("click").click(function(){
		insertKpiItem();
	})
	getCodeClass("kpiType","kpiType");
});


function addChildItem()
{
	var index=$("#child-item-table").find("tr").length;
	$("#child-item-table").append("<tr><td><input type='number' name='sortNo' value='"+index+"' class='form-control'></td><td><input type='text' name='childTitle' class='form-control'></td>" +
			"<td><input type='text' name='childScore' class='form-control'></td><td><a onclick=\"deleteChildItem(this)\" class='btn btn-darkorange btn-xs'>删除</a></td></tr>");
}

function deleteChildItem(Obj)
{
	$(Obj).parent("td").parent("tr").remove();
}

function insertKpiItem()
{
	$.ajax({
		url : "/set/hrset/insertHrKpiItem",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			title:$("#title").val(),
			optType:$("#optType").val(),
			kpiType:$("#kpiType").val(),
			remark:$("#remark").val(),
			childItem:getParam()
		},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				location.reload();
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
	})
}

function getParam()
{
	var paramArr =[];
	$("#child-item-table").find("tr").next().each(function(){
		var json={};
		var sortNo = $(this).children("td").eq(0).find("input").val();
		var childTitle = $(this).children("td").eq(1).find("input").val();
		var childScore = $(this).children("td").eq(2).find("input").val();
		json.sortNo = sortNo;
		json.childTitle = childTitle;
		json.childScore = childScore;
		paramArr.push(json);
	});
	return JSON.stringify(paramArr);
}