Array.prototype.unique2 = function() {
	this.sort(); //先排序
	var res = [ this[0] ];
	for (var i = 1; i < this.length; i++) {
		if (this[i] !== res[res.length - 1]) {
			res.push(this[i]);
		}
	}
	return res;
}
$(function() {
	jeDate("#birthday", {
		format : "YYYY-MM-DD"
	});
	jeDate("#nextVisit", {
		format : "YYYY-MM-DD"
	});
	$('#content').summernote({
		height : 200
	});
	$(".js-btn").on('click', function() {
		top.goUrl("/app/core/crm/manage/new");
	})
	getSmsConfig("msgType", "crm");
	query();
	$(".js-querybtn").unbind("click").click(function() {
		$('#myTable').bootstrapTable('refresh');
	});
	$.ajax({
		url : "/ret/crmget/getCrmIndustryList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == 200) {
				var list = data.list;
				$("#industry").append("<option value=''>请选择</option>")
				for (var i = 0; i < list.length; i++) {
					$("#industry").append("<option value='" + list[i].industryId + "'>" + list[i].industryName + "</option>")
				}
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
});
function createTagsSelect() {
	$.ajax({
		url : "/ret/crmget/getCrmTagsbyIndustryList",
		type : "post",
		dataType : "json",
		data : {
			industryId : $("#industry").val()
		},
		success : function(data) {
			if (data.status == 200) {
				var list = data.list;
				var html = "";
				for (var i = 0; i < list.length; i++) {
					html += "<span class='list-1 list'><span class='item js-first'><span>" + list[i].tagsName + "</span></span>";
					html += "</span>";
					$(".choose-c").html(html);
				}
				var value = $("#tags").val();
				if (value) {
					var tags = value.split(",");
					$(document).find('.item').each(function() {
						for (var i = 0; i < tags.length; i++) {
							if ($(this).find('span').text() == tags[i]) {
								$(this).find('span').addClass('badge badge-purple badge-square');
							}
						}
					})
				}
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}
$(document).ready(function() {
	$("#industry").unbind("change").change(function() {
		$("#tags").val("");
	});
	$("#tags").unbind("click").click(function() {
		if ($("#industry").val() == "") {
			top.layer.msg("请先选择所业分类!");
			return;
		}
		createTagsSelect();
		$(".badge").each(function() {
			$(this).removeClass("badge badge-purple badge-square");
		});
		$("#tagsModal").modal("show");
	});
	$(".js-clear").unbind("click").click(function() {
		$("#tags").val("");
	});
	$(".js-ok").unbind("click").click(function() {
		if ($(".choose-c").find('span').hasClass('badge')) {
			var arr = [];
			$(".choose-c").find('.badge').each(function() {
				arr.push($(this).text())
			})
			$("#tags").val((arr.unique2()).join(","));
			$("#tagsModal").modal("hide");
		}
	});
});
var country = '<option value="0" a="0">-选择国家-</option>';
for (var a = 0; a <= _areaList.length - 1; a++) {
	var objContry = _areaList[a];
	country += '<option value="' + objContry + '" a="' + (a + 1) + '">' + objContry + '</option>';
}
$("#country").html(country).chosen().change(function() {
	var a = $("#country").find("option[value='" + $("#country").val() + "']").attr("a");
	var _province = areaObj[a];
	var province = '<option value="" b="">-选择省份-</option>';
	for ( var b in _province) {
		var objProvince = _province[b];
		if (objProvince.n) {
			province += '<option value="' + objProvince.n + '" b="' + b + '">' + objProvince.n + '</option>';
		}
	}
	if (!province) {
		province = '<option value="0" b="0">-选择省份-</option>';
	}
	$("#province").html(province).chosen().change(function() {
		var b = $("#province").find("option[value='" + $("#province").val() + "']").attr("b");
		var _city = areaObj[a][b];
		var city = '<option value="">-选择城市-</option>';
		for ( var c in _city) {
			var objCity = _city[c];
			if (objCity.n) {
				city += '<option value="' + objCity.n + '">' + objCity.n + '</option>';
			}
		}
		if (!city) {
			var city = '<option value="0">-选择城市-</option>';
		}
		$("#city").html(city).chosen().change();
		$(".dept_select").trigger("chosen:updated");
	});
	$("#province").change();
	$(".dept_select").trigger("chosen:updated");
});
$("#country").change();

function query() {
	$("#myTable").bootstrapTable({
		url : '/ret/crmget/getCrmCustomerList',
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
		showColumns : true,//是否显示 内容列下拉框
		showRefresh : true,//显示刷新按钮
		idField : 'customerId',//key值栏位
		clickToSelect : true,//点击选中checkbox
		pageList : [ 10, 20, 30, 50 ],//可选择单页记录数
		queryParams : queryParams,
		columns : [ {
			checkbox : true
		}, {
			field : 'num',
			title : '序号',//标题  可不加
			width : '30px',
			formatter : function(value, row, index) {
				return index + 1;
			}
		}, {
			field : 'cnName',
			title : '客户名称',
			sortable : true,
			width : '200px',
			formatter : function(value, row, index) {
				if (value == "" || value == null) {
					return "<a onclick=\"window.open('/app/core/crm/customerdetails?customerId=" + row.customerId + "');\" href='#'>" + row.enName + "</a>";
				} else {
					return "<a onclick=\"window.open('/app/core/crm/customerdetails?customerId=" + row.customerId + "');\" href='#'>" + value + "</a>";
				}
			}
		}, {
			field : 'model',
			width : '50px',
			title : '企业类型',
			visible : false,
			formatter : function(value, row, index) {
				if (value == "1") {
					return "制造";
				} else if (value == "2") {
					return "贸易";
				} else if (value == "0") {
					return "其它";
				}
			}
		}, {
			field : 'industry',
			width : '50px',
			title : '所属行业',
			formatter : function(value, row, index) {
				return getindustryName(row.industry);
			}
		}, {
			field : 'source',
			width : '50px',
			sortable : true,
			title : '客户来源',
			visible : false,
			formatter : function(value, row, index) {
				if (value == "1") {
					return "主动联系";
				} else if (value == "2") {
					return "网络推广";
				} else if (value == "3") {
					return "陌生开拓";
				} else if (value == "4") {
					return "朋友介绍";
				} else if (value == "0") {
					return "其它";
				}
			}
		}, {
			field : 'country',
			width : '100px',
			visible : false,
			title : '国家'
		}, {
			field : 'province',
			width : '50px',
			visible : false,
			title : '地区'
		}, {
			field : 'city',
			width : '50px',
			title : '城市'
		}, {
			field : 'opponent',
			width : '150px',
			visible : false,
			title : '现有对手'
		}, {
			field : 'intention',
			width : '50px',
			title : '合作意向',
			formatter : function(value, row, index) {
				if (value == "1") {
					return "强烈";
				} else if (value = "2") {
					return "一般";
				} else if (value == "0") {
					return "暂无可能";
				}
			}
		}, {
			field : 'level',
			width : '50px',
			sortable : true,
			title : '客户等级',
			formatter : function(value, row, index) {
				if (value == "1") {
					return "公司重点客户";
				} else if (value == "2") {
					return "公司主要客户";
				} else if (value == "3") {
					return "公司一般客户";
				} else if (value == "4") {
					return "潜在重点客户";
				} else if (value == "5") {
					return "潜在主要客户";
				} else if (value =="6") {
					return "潜在一般客户";
				} else if (value == "7") {
					return "潜在重点客户";
				} else if (value == "8") {
					return "黑名单客户";
				} else if (value = "0") {
					return "信息池客户";
				}
			}
		}, {
			field : 'selStage',
			width : '80px',
			title : '当前阶段',
			formatter : function(value, row, index) {
				if (value == "0") {
					return "系统收录";
				} else if (value == "1") {
					return "邮件介绍";
				} else if (value == "2") {
					return "电话沟通";
				} else if (value == "3") {
					return "微信联系";
				} else if (value == "4") {
					return "上门拜访";
				} else if (value == "5") {
					return "合作阶段";
				}
			}
		}, {
			field : 'roles',
			width : '50px',
			visible : false,
			title : '客户角色',
			formatter : function(value, row, index) {
				if (value == "1") {
					return "供应商";
				} else if (value == "2") {
					return "采购商";
				}
			}
		}, {
			field : 'status',
			title : '经营状态',
			visible : false,
			width : '50px',
			formatter : function(value, row, index) {
				if (value == "1") {
					return "优";
				} else if (value == "2") {
					return "良";
				} else if (value = "3") {
					return "差";
				}
			}
		}, {
			field : 'focusProduct',
			width : '100px',
			title : '需求产品',
			formatter : function(value, row, index) {
				if (value) {
					return getMyProductNameStr(value);
				} else {
					return "";
				}

			}
		}, {
			field : 'tags',
			width : '100px',
			title : '企业标签'
		}, {
			field : 'tel',
			width : '100px',
			visible : false,
			title : '企业电话'
		}, {
			field : 'opt',
			title : '操作',
			width : '150px',
			align:'center',
			formatter : function(value, row, index) {
				var customerName = row.enName != "" ? row.cnName : row.enName;
				return createOptBtn(row.customerId, customerName);
			}
		} ],
		onClickCell : function(field, value, row, $element) {
			//alert(row.SystemDesc);
		},
		responseHandler : function(res) {
			console.log(res);
			if (res.status == "500") {
				console.log(res.msg);
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
function createOptBtn(customerId, customerName) {
	var html = "<a onclick=\"addlinkman(this)\" value-data='" + customerId + "' value-cname='" + customerName + "' class=\"btn btn-sky btn-xs\" >联系人</a>&nbsp;&nbsp;"
			+ "<a onclick=\"addrecored(this)\" value-data='" + customerId + "' value-cname='" + customerName + "' class=\"btn btn-success btn-xs\" >联系记录</a>&nbsp;&nbsp;"
			+ "<a onclick=\"edit(this)\" value-data='" + customerId + "' value-cname='" + customerName + "' class=\"btn btn-primary btn-xs\">修改</a>&nbsp;&nbsp;"
			+ "<a onclick=\"del(this)\" value-data='" + customerId + "' value-cname='" + customerName + "' class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
}
function queryParams(params) {
	var temp = {
		search : params.search,
		pageSize : this.pageSize,
		pageNumber : this.pageNumber,
		sort : params.sort,
		sortOrder : params.order,
		industry : $("#industry").val(),
		model : $("#model").val(),
		level : $("#level").val(),
		intention : $("#intention").val(),
		country : $("#country").val(),
		province : $("#province").val(),
		city : $("#city").val(),
		tags : $("#tags").val()
	};
	return temp;
};
function addlinkman(Obj) {
	document.getElementById("form1").reset();
	$("#lCustomerName").html($(Obj).attr("value-cname"));
	var customerId = $(Obj).attr("value-data");
	$("#linkmanmodal").modal("show");
	$(".js-createLinkMan").unbind("click").click(function() {
		$.ajax({
			url : "/set/crmset/createCrmLinkMan",
			type : "post",
			dataType : "json",
			data : {
				customerId : customerId,
				linkName : $("#linkName").val(),
				sex : $('input:radio[name="sex"]:checked').val(),
				deptName : $("#deptName").val(),
				postion : $("#postion").val(),
				mobile : $("#mobile").val(),
				email : $("#email").val(),
				tel : $("#tel").val(),
				fax : $("#fax").val(),
				address : $("#address").val(),
				birthday : $("#birthday").val(),
				hobby : $("#hobby").val(),
				decisionMaker : $('input:radio[name="decisionMaker"]:checked').val()
			},
			success : function(data) {
				if (data.status == 200) {
					top.layer.msg(data.msg);
					$("#linkmanmodal").modal("hide");
				} else {
					console.log(data.msg);
				}
			}
		});
	})

}

function addrecored(Obj) {
	var customerId = $(Obj).attr("value-data");
	$("#rlinkName").select2({
		theme : "bootstrap",
		allowClear : true,
		placeholder : "联系人",
		query : function(query) {
			var url = "/ret/crmget/getCrmLinkManList";
			var param = {
				customerId : customerId,
				search : query.term
			}; // 查询参数，query.term为用户在select2中的输入内容.
			var type = "json";
			var data = {
				results : []
			};
			$.post(url, param, function(datas) {
				var datalist = datas.list;
				for (var i = 0; i < datalist.length; i++) {
					var land = datalist[i];
					var option = {
						"id" : land.linkManId,
						"text" : land.linkName
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
			return data.text;
		},
		initSelection : function(data, cb) {
			console.log(data);
			cb(data);
		}
	});

	document.getElementById("form2").reset();
	$("#rCustomerName").html($(Obj).attr("value-cname"));
	$("#show_crmattach").html("");
	$("#crmattach").attr("data_value", "");
	$("#recoredmodal").modal("show");
	$(".js-createCrmRecord").unbind("click").click(function() {
		$.ajax({
			url : "/set/crmset/createCrmContactRecord",
			type : "post",
			dataType : "json",
			data : {
				customerId : customerId,
				linkManId : $("#rlinkName").val(),
				msgType : getCheckBoxValue("msgType"),
				recordType : $("#recordType").val(),
				content : $("#content").code(),
				linkType : $("#linkType").val(),
				nextVisit : $("#nextVisit").val(),
				attach : $("#crmattach").attr("data_value")
			},
			success : function(data) {
				if (data.status == 200) {
					top.layer.msg(data.msg);
					$("#recoredmodal").modal("hide");
				} else {
					console.log(data.msg);
				}
			}
		});
	});
}

function del(Obj) {
	var customerId = $(Obj).attr("value-data");
	var msg = "您真的确定要删除" + $(Obj).attr("value-cname") + "吗？\n\n请确认！";
	if (confirm(msg) == true) {

		$.ajax({
			url : "/set/crmset/deleteCrmCustomer",
			type : "post",
			dataType : "json",
			data : {
				customerId : customerId
			},
			success : function(data) {
				if (data.status == 200) {
					top.layer.msg(data.msg);
					location.reload();
				} else {
					console.log(data.msg);
				}
			}
		});
	} else {
		return;
	}
}

function getindustryName(industryId) {
	var inustryName = "";
	$.ajax({
		url : "/ret/crmget/getCrmIndustry",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			industryId : industryId
		},
		success : function(data) {
			if (data.status == 200) {
				if (data.list) {
					inustryName = data.list.industryName;
				}
			} else {
				console.log(data.msg);
			}
		}
	});
	return inustryName;
}

function edit(Obj) {
	//window.location.href = "/app/core/crm/manage/new?customerId=" + $(Obj).attr("value-data");
	open("/app/core/crm/manage/new?customerId=" + $(Obj).attr("value-data"),"_self");
}

function getMyProductNameStr(productIds) {
	var names = [];
	$.ajax({
		url : "/ret/crmget/getMyProductNameStr",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			productIds : productIds
		},
		success : function(data) {
			if (data.status == 200) {
				var list = data.list;
				if (list) {
					for (var i = 0; i < list.length; i++) {
						names.push(list[i].productName);
					}
				}
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
	return names.join(",");
}
