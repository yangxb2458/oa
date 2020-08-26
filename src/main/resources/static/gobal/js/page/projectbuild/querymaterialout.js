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
		url : '/ret/projectbuildmaterialget/getQueryMaterialOutList',
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
			field : 'projectTitle',
			title : '项目名称',
			sortable : true,
			width : '150px'
		}, {
			field : 'materialName',
			width : '50px',
			title : '材料名称'
		}, {
			field : 'materialCode',
			title : '材料编码',
			width : '50px'
		}, {
			field : 'brand',
			width : '50px',
			title : '品牌'
		}, {
			field : 'model',
			width : '100px',
			title : '型号'
		},{
			field : 'quantity',
			width : '50px',
			title : '出库数量'
		},{
			field : 'unit',
			width : '50px',
			title : '单位',
			formatter : function(value, row, index) {
				return getprojectbuildunitbyid(value);
			}
		},{
			field : 'createTime',
			sortable : true,
			width : '100px',
			title : '出库时间'
		},{
			field : 'outUserName',
			width : '50px',
			title : '出库人'
		}, {
			field : 'opt',
			title : '操作',
			align:'center',
			width : '50px',
			formatter : function(value, row, index) {
				return createOptBtn(row.materialId);
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
		  outUser:$("#outUser").attr("data-value"),
	        beginTime:$("#beginTime").val(),
	        endTime:$("#endTime").val(),
	        projectTitle:$("#projectTitle").val(),
	        materialName:$("#materialName").val()
	};
	return temp;
};
function createOptBtn(materialId) {
	var html = "<a href=\"javascript:void(0);readNews('" + materialId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;";
	return html;
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