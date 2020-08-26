$(function(){
	$(".js-add-parent-but").unbind("click").click(function(){
		$("#childclode").hide();
		$("#parentclode").show();
		$(".js-update").hide();
		$(".js-add").show();
	});
	$(".js-add").unbind("click").click(function(){
		addParentCode();
	})
	getAllParentCodeList();
});

function addParentCode()
{
	$.ajax({
		url : "/set/hrset/insertHrClassCode",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			module:$("#module").val(),
			codeName:$("#codeName").val()
		},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				location.reload();
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

function getAllParentCodeList()
{
	$.ajax({
		url : "/ret/hrget/getAllParentCodeList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
			{
				var html="";
				var selectHtml="";
				for(var i=0;i<data.list.length;i++)
				{
					selectHtml+="<option value='"+data.list[i].module+"'>"+data.list[i].codeName+"</option>";
					html+="<tr>"+
								"<td>"+(i+1)+"</td>"+
								"<td>"+data.list[i].codeName+"</td>"+
								"<td><a onclick=\"edit('"+data.list[i].codeId+"')\" class=\"btn btn-link\">编辑</a>&nbsp;&nbsp;<a class=\"btn btn-link\" onclick=\"editNext('"+data.list[i].codeId+"','"+data.list[i].module+"')\">下一级</a>";
					if(data.list[i].codeFlag=="1")
					{
						html+="&nbsp;&nbsp;<a class=\"btn btn-link\" onclick=\"deleteClassCode('"+data.list[i].codeId+"')\">删除</a>";
					}
					html+="</td>"+
						"</tr>";
				}
				$("#tbodylist").html(html);
				$("#parentId").html(selectHtml)
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

function deleteClassCode(codeId)
{
	 if(confirm("确定删除当前分类码吗？同时包含子集"))
	    {
	$.ajax({
		url : "/set/hrset/deleteHrClassCode",
		type : "post",
		dataType : "json",
		data:{codeId:codeId},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				location.reload();
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
}


function edit(codeId)
{
	$("#childclode").hide();
	$("#parentclode").show();
	$(".js-add").hide();
	$(".js-update").show();
	$.ajax({
		url : "/ret/hrget/getHrClassCodeById",
		type : "post",
		dataType : "json",
		data:{codeId:codeId},
		success : function(data) {
			if(data.status=="200")
			{
				$("#sortNo").val(data.list.sortNo);
				$("#module").val(data.list.module);
				$("#codeName").val(data.list.codeName);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else 
			{
				console.log(data.msg);
			}
		}
		});
	
	$(".js-update").unbind("click").click(function(){
		updateCode(codeId);
	})
}

function updateCode(codeId)
{
	$.ajax({
		url : "/set/hrset/updateHrClassCode",
		type : "post",
		dataType : "json",
		data:{
			codeId:codeId,
			sortNo:$("#sortNo").val(),
			module:$("#module").val(),
			codeName:$("#codeName").val()
			},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				location.reload();
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

function editNext(codeId,module)
{
	$("#parentclode").hide();
	$("#childclode").show();
	$(".js-add-child-but").attr("data-value",module);
	$.ajax({
		url : "/ret/hrget/getCodeListByModule",
		type : "post",
		dataType : "json",
		data:{module:module},
		success : function(data) {
			if(data.status=="200")
			{
				var html="";
				for(var i=0;i<data.list.length;i++)
				{
					html+="<tr>"+
					"<td>"+(i+1)+"</td>"+
					"<td>"+data.list[i].sortNo+"</td>"+
					"<td>"+data.list[i].codeName+"</td>"+
					"<td>"+data.list[i].codeValue+"</td>"+
					"<td><a onclick=\"editchild('"+data.list[i].codeId+"')\" class=\"btn btn-link\">编辑</a>";
					if(data.list[i].codeFlag=="1")
					{
						html+="&nbsp;&nbsp;<a class=\"btn btn-link\" onclick=\"deleteClassCode('"+data.list[i].codeId+"')\">删除</a>"
					}
					html+="</td>"+
					"</tr>";
				}
				$("#childTbody").html(html);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else 
			{
				console.log(data.msg);
			}
		}
	})
	$(".js-add-child-but").unbind("click").click(function(){
		$("#childClassCodeModal").modal("show");
		$("#parentId").val(module);
		$(".js-save").unbind("click").click(function(){
			$.ajax({
				url : "/set/hrset/insertHrClassCode",
				type : "post",
				dataType : "json",
				data:{
					sortNo:$("#childSortNo").val(),
					codeName:$("#childCodeName").val(),
					codeValue:$("#childCodeValue").val(),
					parentId:$("#parentId").val(),
					module:module
					},
				success : function(data) {
					if(data.status=="200")
						{
						top.layer.msg(data.msg);
						$("#childClassCodeModal").modal("hide");
						location.reload();
						}else if(data.status=="100")
						{
							top.layer.msg(data.msg);
						}else
						{
							console.log(data.msg);
						}
				}
			});
		});
	})
}

function editchild(codeId)
{
	$.ajax({
		url : "/ret/hrget/getHrClassCodeById",
		type : "post",
		dataType : "json",
		data:{codeId:codeId},
		success : function(data) {
			if(data.status=="200")
			{
				$("#childSortNo").val(data.list.sortNo);
				$("#parentId").val(data.list.parentId);
				$("#childCodeName").val(data.list.codeName);
				$("#childCodeValue").val(data.list.codeValue);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else 
			{
				console.log(data.msg);
			}
		}
		});
	$(".js-save").unbind("click").click(function(){
		$.ajax({
			url : "/set/hrset/updateHrClassCode",
			type : "post",
			dataType : "json",
			data:{
				codeId:codeId,
				sortNo:$("#childSortNo").val(),
				codeName:$("#childCodeName").val(),
				codeValue:$("#childCodeValue").val(),
				parentId:$("#parentId").val()
				},
			success : function(data) {
				if(data.status=="200")
					{
					top.layer.msg(data.msg);
					$("#childClassCodeModal").modal("hide");
					location.reload();
					}else if(data.status=="100")
					{
						top.layer.msg(data.msg);
					}else
					{
						console.log(data.msg);
					}
			}
		});
	});
	$("#childClassCodeModal").modal("show");
}
