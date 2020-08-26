$(function(){
	$("#projectId").select2({
		theme : "bootstrap",
		allowClear : true,
		placeholder : "请输入工程项目名称",
		query : function(query) {
			var url = "/ret/projectbuildget/selectProjectBuild2ByTitle";
			var param = {
				projectTitle : query.term
			}; // 查询参数，query.term为用户在select2中的输入内容.
			var type = "json";
			var data = {
				results : []
			};
			$.post(url, param, function(datas) {
				var datalist = datas.list;
				for (var i = 0, len = datalist.length; i < len; i++) {
					var land = datalist[i];
					var option = {
						"id" : land.projectId,
						"text" : land.projectTitle
					};
					data.results.push(option);
				}
				query.callback(data);
			}, type);
		},
		escapeMarkup : function(markup) {
			return markup;
		},
		minimumInputLength : 0,
		formatResult : function(data) {
			return '<div class="select2-user-result">' + data.text + '</div>'
		},
		formatSelection : function(data) {
			var projectId = data.id;
			$("#myTable").bootstrapTable('destroy');
			query(projectId);
			return data.text;
		},
		initSelection : function(data, cb) {
			cb(data);
		}
	});
	query("");
});



function query(projectId) {
	$("#myTable").bootstrapTable({
		url : '/ret/projectbuildmaterialget/getMaterialByProjectId?projectId=' + projectId,
		method : 'post',
		contentType : 'application/x-www-form-urlencoded',
		toolbar : '#toobar',// 工具列
		striped : true,// 隔行换色
		cache : false,// 禁用缓存
		pagination : true,// 启动分页
		sidePagination : 'server',// 分页方式
		pageNumber : 1,// 初始化table时显示的页码
		pageSize : 10,// 每页条目
		showFooter : false,// 是否显示列脚
		showPaginationSwitch : true,// 是否显示 数据条数选择框
		sortable : true,// 排序
		search : true,// 启用搜索
		showColumns : true,// 是否显示 内容列下拉框
		showRefresh : true,// 显示刷新按钮
		idField : 'materialId',// key值栏位
		clickToSelect : false,// 点击选中checkbox
		pageList : [ 10, 20, 30, 50 ],// 可选择单页记录数
		queryParams : queryParams,
		columns : [ {
			field : 'num',
			title : '序号',// 标题 可不加
			width : '50px',
			formatter : function(value, row, index) {
				return index + 1;
			}
		}, {
			field : 'name',
			title : '材料名称',
			sortable : true,
			width : '200px'
		}, {
			field : 'materialCode',
			width : '100px',
			title : '材料编号'
		}, {
			field : 'brand',
			width : '100px',
			title : '品牌'
		}, {
			field : 'model',
			width : '100px',
			title : '型号'

		}, {
			field : 'quantity',
			width : '50px',
			title : '采购总数'
		}, {
			field : 'usedQuantity',
			title : '已领数量',
			width : '50px'
		},
		{
			field : 'canused',
			title : '最多可用数量',
			width : '50px',
			formatter:function(value,row,index)
			{
				return row.quantity-row.usedQuantity;
			}
		}, {
			field : 'unit',
			width : '50px',
			title : '单位',
			formatter:function(value,row,index)
			{
				return	getprojectbuildunitbyid(value);
			}

		}, {
			field : 'opt',
			title : '操作',
			align:'center',
			width : '80px',
			formatter : function(value, row, index) {
				return createOptBtn(row.materialId,row.quantity,row.usedQuantity);
			}
		} ],
		onClickCell : function(field, value, row, $element) {
		},
		responseHandler : function(res) {
			if (res.status == "500") {
				console.log(res.msg);
			} else if (res.status == "100") {
				top.layer.msg(res.msg);
			} else {
				return {
					total : res.list.total, // 总页数,前面的key必须为"total"
					rows : res.list.list
				// 行数据，前面的key要与之前设置的dataField的值一致.
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
		sortOrder : params.order
	};
	return temp;
};
function createOptBtn(materialId,quantity,usedQuantity) {
	var html = "<a href=\"javascript:void(0);materialout('" + materialId + "',"+quantity+","+usedQuantity+")\" class=\"btn btn-sky btn-xs\" >领用</a>&nbsp;&nbsp;<a href=\"javascript:void(0);materialoutdetails('" + materialId + "')\" class=\"btn btn-sky btn-xs\" >台账</a>";
	return html;
}

function materialoutdetails(materialId)
{
	var projectId = $("#projectId").val();
	window.open("/app/core/projectbuild/material/materialoutlist?projectId="+projectId+"&materialId="+materialId);
}

function materialout(materialId,quantity,usedQuantity)
{
	var canused = quantity-usedQuantity;
	$("#canused").html(canused);
	$("#materialoutmodal").modal("show");
	$(".js-save").unbind("click").click(function(){
		var quantity = $("#quantity").val();
		console.log(quantity>canused)
		if(quantity>canused)
		{
			top.layer.msg("出库数量不能大于库存数量");
			return;
		}
		var outUser = $("#outUser").attr("data-value");
		if(outUser=="")
		{
			top.layer.msg("出库人不能为空！");
			return;
		}
		$.ajax({
			url : "/set/projectbuildmaterialset/insertMaterialOut",
			type : "post",
			dataType : "json",
			data : {
				materialId:materialId,
				projectId:$("#projectId").val(),
				remark:$("#remark").val(),
				quantity:$("#quantity").val(),
				outUser:outUser
			},
			success : function(data) {
				if (data.status == 200) {
					top.layer.msg(data.msg);
					$("#materialoutmodal").modal("hide");
				} else if(data.status==100)
				{
					top.layer.msg(data.msg);
				}else{
					console.log(data.msg);
				}
			}
		});
	})
}

function getprojectbuildunitbyid(untiId) {
	var returnStr="";
	$.ajax({
		url : "/ret/projectbuildunitget/getunitbyid",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			unitId:untiId
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