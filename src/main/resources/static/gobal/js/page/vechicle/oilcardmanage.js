$(function() {
	jeDate("#beginTimeQuery", {
		format : "YYYY-MM-DD"
	});
	jeDate("#endTimeQuery", {
		format : "YYYY-MM-DD"
	});
	query();
	$(".js-simple-query").unbind("click").click(function() {
		$("#myTable").bootstrapTable("refresh");
	})
	jeDate("#cardTime", {
		format : "YYYY-MM-DD",
		maxDate:getSysDate(),
	});
})

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/vehicleget/getVehicleOilCardList',
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
		sortOrder : "asc",
		showColumns : true,// 是否显示 内容列下拉框
		showRefresh : true,// 显示刷新按钮
		idField : 'cardId',// key值栏位
		clickToSelect : true,// 点击选中checkbox
		pageList : [ 10, 20, 30, 50 ],// 可选择单页记录数
		queryParams : queryParams,
		columns : [ {
			checkbox : true
		}, {
			field : 'num',
			title : '序号',// 标题 可不加
			width : '50px',
			formatter : function(value, row, index) {
				return index + 1;
			}
		}, {
			field : 'oilType',
			title : '油卡类型',
			width : '80px',
			formatter : function(value, row, index) {
				if (value=="1") {
					return "汽油";
				} else if(value=="2"){
					return "柴油";
				}
			}
		}, {
			field : 'oilComp',
			width : '100px',
			title : '发卡公司'
		},{
			field : 'cardCode',
			width : '100px',
			title : '油卡编号'
		}, {
			field : 'cardNo',
			width : '100px',
			title : '油卡卡号'
		}, {
			field : 'passWord',
			width : '50px',
			title : '油卡密码'
		},{
			field : 'cardUser',
			width : '100px',
			title : '办卡人',
			formatter : function(value, row, index) {
				return getUserNameByStr(value);
			}
		}, {
			field : 'cardTime',
			width : '100px',
			title : '办卡日期'
		}, {
			field : 'balance',
			title : '余额',
			width : '100px'
		},{
			field : 'status',
			width : '50px',
			title : '当前状态',
			formatter : function(value, row, index) {
				if (value=="0") {
					return "空闲";
				} else if(value=="1"){
					return "使用中";
				} else if(value=="2"){
					return "挂失";
				} else if(value=="3"){
					return "失效";
				}
			}
		},{
			field : 'opt',
			title : '操作',
			align : 'center',
			width : '120px',
			formatter : function(value, row, index) {
				return createOptBtn(row.cardId);
			}
		} ],
		onClickCell : function(field, value, row, $element) {
			// alert(row.SystemDesc);
		},
		responseHandler : function(res) {
			if (res.status == "500") {
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
		sortOrder : params.order,
		oilType : $("#oilTypeQuery").val(),
		endTime : $("#endTimeQuery").val(),
		beginTime : $("#beginTimeQuery").val()
	};
	return temp;
};
function createOptBtn(cardId) {
	var html = "<a href=\"javascript:void(0);edit('" + cardId + "')\" class=\"btn btn-primary btn-xs\">编辑</a>&nbsp;&nbsp;" + 
	"<a href=\"javascript:void(0);deleteCard('" + cardId + "')\" class=\"btn btn-darkorange btn-xs\" >删除</a>&nbsp;&nbsp;";
	html += "<a href=\"javascript:void(0);details('" + cardId + "')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	return html;
}

function deleteCard(cardId) {
	$.ajax({
		url : "/set/vehicleset/deleteVehicleOilCard",
		type : "post",
		dataType : "json",
		data : {
			cardId : cardId
		},
		success : function(data) {
			if (data.status == "200") {
				top.layer.msg(data.msg);
				$("#myTable").bootstrapTable("refresh");
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}
function edit(cardId) {
	$("#oilcardlistdiv").hide();
	$("#oilcarddiv").show();
	$(".js-back-btn").unbind("click").click(function() {
		goback();
	})
	$.ajax({
		url : "/ret/vehicleget/getVehicleOilCardById",
		type : "post",
		dataType : "json",
		data : {
			cardId : cardId
		},
		success : function(data) {
			if (data.status == "200") {
				var oilcardInfo = data.list;
				for ( var id in oilcardInfo) {
					if (id == "cardUser") {
						$("#" + id).val(getUserNameByStr(oilcardInfo[id]));
						$("#" + id).attr("data-value", oilcardInfo[id]);
					}else {
						$("#" + id).val(oilcardInfo[id]);
					}
				}
				$(".js-update-save").unbind("click").click(function() {
					updateVehicleOilCard(cardId);
				})
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else if (data.status == "500") {
				console.log(data.msg);
			}
		}
	})
}
function details(cardId) {
	window.open("/app/core/vehicle/oilcarddetails?cardId=" + cardId);
}
function updateVehicleOilCard(cardId) {
	$.ajax({
		url : "/set/vehicleset/updateVehicleOilCard",
		type : "post",
		dataType : "json",
		data : {
			cardId : cardId,
			sortNo:$("#sortNo").val(),
			oilType:$("#oilType").val(),
			oilComp:$("#oilComp").val(),
			cardCode:$("#cardCode").val(),
			cardNo:$("#cardNo").val(),
			passWord:$("#passWord").val(),
			status:$("#status").val(),
			balance:$("#balance").val(),
			cardTime:$("#cardTime").val(),
			cardUser:$("#cardUser").attr("data-value"),
			remark:$("#remark").val()
		},
		success : function(data) {
			if (data.status == "200") {
				top.layer.msg(data.msg);
				location.reload();
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else if (data.status == "500") {
				console.log(data.msg);
			}
		}
	})
}
function goback() {
	$("#oilcarddiv").hide();
	$("#oilcardlistdiv").show();
}