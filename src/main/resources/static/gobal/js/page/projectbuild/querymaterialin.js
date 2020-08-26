$(function() {
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	query();
	$(".js-querybtn").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	})
});
function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/projectbuildmaterialget/getQueryMaterialInList',
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
		search : false,//启用搜索
		showColumns : true,//是否显示 内容列下拉框
		showRefresh : true,//显示刷新按钮
		idField : 'materialId',//key值栏位
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
		}, {
			field : 'title',
			title : '采购单标题',
			sortable : true,
			width : '200px'
		}, {
			field : 'materialName',
			title : '材料名称',
			sortable : true,
			width : '200px'
		}, {
			field : 'materialCode',
			width : '50px',
			title : '材料编号'
		}, {
			field : 'brand',
			width : '50px',
			title : '品牌'
		}, {
			field : 'model',
			width : '50px',
			title : '规格型号'
		}, {
			field : 'quantity',
			title : '入库数量',
			width : '50px'
		}, {
			field : 'unit',
			title : '单位',
			width : '50px',
			formatter : function(value, row, index) {
			return getprojectbuildunitbyid(value);
			}
		}, {
			field : 'inUserName',
			width : '100px',
			title : '入库人'
		}, {
			field : 'createTime',
			width : '100px',
			sortable : true,
			title : '入库时间'
		}, {
			field : 'opt',
			title : '操作',
			align:'center',
			width : '50px',
			formatter : function(value, row, index) {
				return createOptBtn(row.purchaseId);
			}
		} ],
		onClickCell : function(field, value, row, $element) {
			//alert(row.SystemDesc);
		},
		responseHandler : function(res) {
			if (res.status == "500") {
				console.log(res.msg);
			} else if (res.status == "100") {
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
		   inUser:$("#inUser").attr("data-value"),
	        beginTime:$("#beginTime").val(),
	        endTime:$("#endTime").val(),
	        purchaseTitle:$("#purchaseTitle").val(),
	        materialName:$("#materialName").val()
	};
	return temp;
};
function createOptBtn(purchaseId) {
	var html = "<a href=\"javascript:void(0);readNews('" + purchaseId + "')\" class=\"btn btn-sky btn-xs\" >关联采购申请</a>&nbsp;&nbsp;"
	return html;
}

function readpurchase(purchaseId)
{
	window.open("/app/core/projectbuild/material/purchasedetails?purchaseId="+purchaseId);
}

function getprojectbuildunitbyid(unitId) {
	var returnStr="";
	$.ajax({
		url : "/ret/projectbuildunitget/getunitbyid",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			unitId:unitId
		},
		success : function(data) {
			if (data.status == 200) {
				if(data.list.znName!=null && data.list.znName!="")
					{
						returnStr = data.list.cnName+"|"+data.list.znName;
					}else
					{
						returnStr = data.list.cnName;
					}
			} else {
				console.log(data.msg);
			}
		}
	});
	return returnStr;
}