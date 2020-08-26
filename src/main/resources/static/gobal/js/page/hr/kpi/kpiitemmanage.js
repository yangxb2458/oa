$(function(){
	getCodeClass("kpiTypeQuery","kpiType");
	getCodeClass("kpiType","kpiType");
	query();
	$(".js-simple-query").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	})
});
function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/hrget/getHrKpiItemList',
		method : 'post',
		contentType : 'application/x-www-form-urlencoded',
		toolbar : '#toobar',//工具列
		striped : true,//隔行换色
		cache : false,//禁用缓存
		pagination : true,//启动分页
		sidePagination : 'server',//分页方式
		pageNumber : 1,//初始化table时显示的页码
		pageSize : 10,//每页条目
		showFooter : false,//是否显示列脚
		showPaginationSwitch : true,//是否显示 数据条数选择框
		sortable : true,//排序
		search : true,//启用搜索
		sortOrder: "asc",
		showColumns : true,//是否显示 内容列下拉框
		showRefresh : true,//显示刷新按钮
		idField : 'itemId',//key值栏位
		clickToSelect : true,//点击选中checkbox
		pageList : [ 10, 20, 30, 50 ],//可选择单页记录数
		queryParams : queryParams,
		columns : [ {
			checkbox : true
		}, {
			field : 'num',
			title : '序号',//标题  可不加
			width : '50px',
			formatter : function(value, row, index) {
				return index + 1;
			}
		},{
			field : 'title',
			title : '考核指标',
			sortable : true,
			width : '150px'
		},{
			field : 'kpiType',
			width : '100px',
			title : '考核类型',
			formatter : function(value, row, index) {
				return getCodeClassName(value,"kpiType");
			}
		}, {
			field : 'optType',
			width : '100px',
			title : '考核方式',
			formatter : function(value, row, index) {
				if(value=="1")
				{
					return "单选";
				}else if(value=="2")
				{
					return "多选";
				}else if(value="3")
				{
					return "简述";
				}
			}
		}, {
			field : 'createTime',
			width : '100px',
			title : '创建时间'
		}, {
			field : 'createUserName',
			width : '100px',
			title : '创建人'
		}, {
			field : 'opt',
			title : '操作',
			align : 'center',
			width : '120px',
			formatter : function(value, row, index) {
				return createOptBtn(row.itemId);
			}
		} ],
		onClickCell : function(field, value, row, $element) {
			//alert(row.SystemDesc);
		},
		responseHandler : function(res) {
			if (res.status == "500") {
				top.layer.msg(res.msg);
			} else {
				return {
					total : res.list.total, //总页数,前面的key必须为"total"
					rows : res.list.list
				//行数据，前面的key要与之前设置的dataField的值一致.
				};
			}
		}
	});
}

function queryParams(params) {
	var temp = {
		search : params.search,
		pageSize : this.pageSize,
		pageNumber : this.pageNumber,
		sort : params.sort,
		sortOrder : params.order,
		createUser : $("#createUserQuery").attr("data-value"),
		kpiType : $("#kpiTypeQuery").val()
	};
	return temp;
};
function createOptBtn(itemId) {
	var html = "<a href=\"javascript:void(0);edit('" + itemId + "')\" class=\"btn btn-primary btn-xs\">编辑</a>&nbsp;&nbsp;" 
	+ "<a href=\"javascript:void(0);deleteItem('" + itemId + "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>&nbsp;&nbsp;";
	html += "<a href=\"javascript:void(0);details('" + itemId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	return html;
}
function goback()
{
	$("#itemdiv").hide();
	$("#itemlistdiv").show();
}
function addChildItem()
{
	var index=$("#child-item-table").find("tr").length;
	$("#child-item-table").append("<tr><td><input type='number' name='sortNo' value='"+index+"' class='form-control'></td><td><input type='text' name='childTitle' class='form-control'></td>" +
			"<td><input type='text' name='childScore' class='form-control'></td><td><a onclick=\"deleteChildItem(this)\" class='btn btn-darkorange btn-xs'>删除</a></td></tr>");
}
function createChildItem(childItemArr)
{
	$("#child-item-table").find("tr").next().remove();
	for(var i=0;i<childItemArr.length;i++)
	{
		$("#child-item-table").append("<tr><td><input type='number' name='sortNo' value='"+(i+1)+"' class='form-control'></td>" +
				"<td><input type='text' name='childTitle' class='form-control' value='"+childItemArr[i].childTitle+"'></td>" +
		"<td><input type='text' name='childScore' class='form-control' value='"+childItemArr[i].childScore+"'></td><td><a onclick=\"deleteChildItem(this)\" class='btn btn-darkorange btn-xs'>删除</a></td></tr>");

	}
}
function edit(itemId)
{
	$(".js-child-item").unbind("click").click(function(){
		addChildItem();
	})
	$("#itemlistdiv").hide();
	$("#itemdiv").show();
	$(".js-back-btn").unbind("click").click(function(){
		goback();
	})
	$.ajax({
		url : "/ret/hrget/getHrKpiItemById",
		type : "post",
		dataType : "json",
		data:{itemId:itemId},
		success : function(data) {
			if(data.status=="200")
			{
				var itemInfo = data.list;
				for(var id in itemInfo)
				{
					if(id=="childItem")
					{
						var childItemArr =JSON.parse(itemInfo[id]);
						createChildItem(childItemArr);
					}else
					{
						$("#"+id).val(itemInfo[id]);
					}
				}
				$(".js-update-save").unbind("click").click(function(){
					updateHrKpiItem(itemId);
				})
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

function details(itemId)
{
	window.open("/app/core/hr/kpi/kpiitemdetails?itemId="+itemId);
}
function deleteItem(itemId)
{
	if(confirm("确定删除当前记录吗？"))
    {
	$.ajax({
		url : "/set/hrset/deleteHrKpiItem",
		type : "post",
		dataType : "json",
		data:{itemId:itemId},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				$("#myTable").bootstrapTable("refresh");
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
function deleteChildItem(Obj)
{
	$(Obj).parent("td").parent("tr").remove();
}
function updateHrKpiItem(itemId)
{
	$.ajax({
		url : "/set/hrset/updateHrKpiItem",
		type : "post",
		dataType : "json",
		data:{
			itemId:itemId,
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