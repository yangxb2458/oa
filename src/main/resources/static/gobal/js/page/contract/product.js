var data = {};
var newData=[];
$(function() {
	jeDate("#startTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	$('#content').summernote({
		height : 200
	});
	query();
});

function query() {
	$("#myTable").bootstrapTable(
			{
				//url : '/ret/contractget/getContractDetailsList?contractId=234',
				data: [],
				//method : 'post',
				//contentType : 'application/x-www-form-urlencoded',
				//toolbar : '#toobar',// 工具列
				striped : true,// 隔行换色
				cache : false,// 禁用缓存
				pagination : true,// 启动分页
				sidePagination : 'client',// 分页方式
				pageNumber : 1,// 初始化table时显示的页码
				pageSize : 20,// 每页条目
				showFooter : false,// 是否显示列脚
				//showPaginationSwitch : true,// 是否显示 数据条数选择框
				//sortable : true,// 排序
				//search : true,// 启用搜索
				//showColumns : true,// 是否显示 内容列下拉框
				//showRefresh : true,// 显示刷新按钮
				idField : 'detailsId',// key值栏位
				clickToSelect : true,// 点击选中checkbox
				pageList : [ 10, 20, 30, 50 ],// 可选择单页记录数
				queryParams : queryParams,
				columns : [
						{
							checkbox : true
						},
						{
							field : 'num',
							title : '序号',// 标题 可不加
							width : '50px',
							formatter : function(value, row, index) {
								return index + 1;
							}
						},
						{
							field : 'productName',
							title : '产品名称',
							sortable : true,
							width : '200px'
						},
						{
							field : 'model',
							title : '产品型号'
						},
						{
							field : 'unit',
							width : '100px',
							title : '单位'
						},
						{
							field : 'quantity',
							title : '所需数量'
						},
						{
							field : 'price',
							title : '单价'
						},
						{
							field : 'delivery',
							title : '交货期'
						},
						{
							field : 'subtotal',
							width : '100px',
							title : '小计'
						},
						{
							field : 'remark',
							title : '备注'
						},
						{
							field : 'opt',
							width : '120px',
							title : '操作',
							align:'center',
							formatter : function(value, row, index) {
								return "<div style='line-height:30px' id='opt"
										+ index + "'>"
										+ createOptBtn(row.detailsId)
										+ "</div>";
							}
						} ],
				onClickCell : function(field, value, row, $element) {
				},
				responseHandler : function(res) {
					if (res.status == "500") {
						console.log(res.msg);
					} else if (res.status == 100) {
						top.layer.msg(res.msg);
					} else {
						return {
							total : res.list.total, // 总页数,前面的key必须为"total"
							rows : res.list.list
						};
					}
				}
			});
}
function createOptBtn(detailsId) {
	var html = "<a href=\"javascript:void(0);edit('"
			+ detailsId
			+ "')\" class=\"btn btn-primary btn-xs\">编辑</a>&nbsp;&nbsp;<a href=\"javascript:void(0);del('"
			+ detailsId + "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
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

function addRow() {
	if ($(".savebtn").length > 0) {
		$('#modal-warning').modal('show');
	} else {
		var count = $('#myTable').bootstrapTable('getData').length;
		$('#myTable')
				.bootstrapTable(
						'insertRow',
						{
							index : count,
							row : {
								num : count,
								productName : "<input type='hidden' id='productId' name='productId'/><input type='hidden' style='width:100%' id='productName' name='productName'/>",
								model : "<div id='model' style='line-height:34px'></div>",
								unit : "<div id='unit"+count+"' style='line-height:34px'></div>",
								quantity : "<input id='quantity' name='quantity' type='number' class='form-control'/>",
								price : "<input id='price' name='price' type='number' class='form-control'/>",
								delivery : "<input id='delivery' name='delivery' type='text' class='form-control'/>",
								subtotal : "<div id='subtotal"+count+"' style='line-height:34px'></div>",
								remark : "<input type='text' class='form-control' id='remark' name='remark'/>",
								opt : ""
							}
						});
		$("#productName").select2(
						{
							theme : "bootstrap",
							allowClear : true,
							placeholder : "请输入产品名称",
							query : function(query) {
								var url = "/ret/erpget/selectProductByName";
								var param = {
									productName : query.term
								}; // 查询参数，query.term为用户在select2中的输入内容.
								var type = "json";
								var data = {
									results : []
								};
								$.post(
												url,
												param,
												function(datas) {
													var datalist = datas.list;
													for (var i = 0, len = datalist.length; i < len; i++) {
														var land = datalist[i];
														var option = {
															"id" : land.productId,
															"text" : land.productName,
															"model" : land.model,
															"unit":land.unit
														};
														data.results
																.push(option);
													}
													query.callback(data);
												}, type);

							},
							escapeMarkup : function(markup) {
								return markup;
							},
							minimumInputLength : 2,
							formatResult : function(data) {
								return '<div class="select2-user-result">'
										+ data.text + '</div>'
							},
							formatSelection : function(data) {
								$("#model").html(data.model);
								$("#unit"+count).html(getUnitName(data.unit));
								$("#productId").val(data.id);
								return data.text;
							},
							initSelection : function(data, cb) {
								console.log(data);
								cb(data);
							}
						});
	}
	jeDate("#delivery", {
		format: "YYYY-MM-DD"
	});
	$("#quantity").unbind("change").change(function(){
		js(count);
	});
	$("#price").unbind("change").change(function(){
		js(count);
	});
	$("#opt" + count).html(
					"<a href='javascript:void(0);' onclick='save()' class='btn btn-purple btn-xs savebtn'>保存</a>&nbsp;&nbsp;<a href='javascript:void(0);' onclick='delrows("+ count+ ")' class='btn btn-darkorange btn-xs'>删除</a>");
}
function js(count)
{
	if($("#quantity").val()!=''&& $("#price").val()!='')
		{
		var zs = $("#quantity").val()*$("#price").val();
		$("#subtotal"+count).html(zs);
		}
}
function save()
{
	
	var newData=[];
	newData.push(data);
	$('#myTable').bootstrapTable('load', newData);
}
function createCode()
{
	$.ajax({
		url : "/ret/contractget/createCode",
		type : "post",
		dataType : "json",
		success : function(data) {
			console.log(data);
			if(data.status=="200")
			{
				$("#contractCode").val(data.redirect);
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