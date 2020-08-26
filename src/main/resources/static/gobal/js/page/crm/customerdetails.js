$(function() {
	getCrmCustomerInfo(customerId);
	getCrmLinkManByCustomerId(customerId);
	getRecordByCustomerId(1, 10);
})
function getCrmCustomerInfo(customerId) {
	$.ajax({
		url : "/ret/crmget/getCrmCustomerById",
		type : "post",
		dataType : "json",
		data : {
			customerId : customerId
		},
		success : function(data) {
			if (data.status == 200) {
				for ( var name in data.list) {
					if (name != "country" && name != "province" && name != "city" && name != "businessAddr") {
						if (name == "level") {
							if (data.list[name] == "1") {
								$("#" + name).html("公司重点客户");
							} else if (data.list[name] == "2") {
								$("#" + name).html("公司主要客户");
							} else if (data.list[name] == "3") {
								$("#" + name).html("公司一般客户");
							} else if (data.list[name] == "4") {
								$("#" + name).html("潜在重点客户");
							} else if (data.list[name] == "5") {
								$("#" + name).html("潜在主要客户");
							} else if (data.list[name] == "6") {
								$("#" + name).html("潜在一般客户");
							} else if (data.list[name] == "7") {
								$("#" + name).html("黑名单客户");
							} else if (data.list[name] == "0") {
								$("#" + name).html("信息池客户");
							}
						} else if (name == "follow") {
							var value = "";
							if (data.list[name]) {
								var varr = data.list[name].split(",");
								for (var i = 0; i < varr.length; i++) {
									if (varr[i] == "1") {
										value += "关注品质,";
									}
									if (varr[i] == "2") {
										value += "关注品牌,";
									}
									if (varr[i] == "3") {
										value += "关注价格,";
									}
									if (varr[i] == "4") {
										value += "关注服务,";
									}
									if (varr[i] == "5") {
										value += "关注交货速度,";
									}
									if (varr[i] == "6") {
										value += "关注设计能力,";
									}
								}
							}
							$("#" + name).html(value);
						} else if (name == "intention") {
							if (data.list[name] == "0") {
								$("#" + name).html("暂无可能");
							} else if (data.list[name] == "1") {
								$("#" + name).html("强烈");
							} else if (data.list[name] == "2") {
								$("#" + name).html("一般");
							}
						} else if (name == "source") {
							if (data.list[name] == "1") {
								$("#" + name).html("主动联系");
							} else if (data.list[name] == "2") {
								$("#" + name).html("网络推广");
							} else if (data.list[name] == "3") {
								$("#" + name).html("陌生开拓");
							} else if (data.list[name] == "4") {
								$("#" + name).html("朋友介绍");
							} else if (data.list[name] == "0") {
								$("#" + name).html("其它");
							}
						} else if (name == "model") {
							if (data.list[name] == "1") {
								$("#" + name).html("制造");
							} else if (data.list[name] == "2") {
								$("#" + name).html("贸易");
							} else if (data.list[name] == "0") {
								$("#" + name).html("其它");
							}
						} else if (name == "roles") {
							if (data.list[name] == "1") {
								$("#" + name).html("供应商");
							} else if (data.list[name] == "2") {
								$("#" + name).html("采购商");
							}
						} else if (name == "nature") {
							if (data.list[name] == "1") {
								$("#" + name).html("私营企业");
							} else if (data.list[name] == "2") {
								$("#" + name).html("合资企业");
							} else if (data.list[name] == "3") {
								$("#" + name).html("国企");
							} else if (data.list[name] == "4") {
								$("#" + name).html("外资企业");
							} else if (data.list[name] == "5") {
								$("#" + name).html("个体工商户");
							} else if (data.list[name] == "0") {
								$("#" + name).html("其它");
							}
						} else if (name == "status") {
							if (data.list[name] == "1") {
								$("#" + name).html("优");
							} else if (data.list[name] == "2") {
								$("#" + name).html("良");
							} else if (data.list[name] == "3") {
								$("#" + name).html("差");
							}
						} else if (name == "selStage") {
							if (data.list[name] == "0") {
								$("#" + name).html("系统收录");
							} else if (data.list[name] == "1") {
								$("#" + name).html("邮件介绍");
							} else if (data.list[name] == "2") {
								$("#" + name).html("电话沟通");
							} else if (data.list[name] == "3") {
								$("#" + name).html("微信联系");
							} else if (data.list[name] == "4") {
								$("#" + name).html("上门拜访");
							} else if (data.list[name] == "5") {
								$("#" + name).html("合作阶段");
							}
						} else if (name == "webSite") {
							$("#" + name).html("<a style='cursor:pointer' href='#' onclick='window.open(\"http://" + data.list[name] + "\")'>" + data.list[name] + "</a>")
						} else if (name == "industry") {
							$("#" + name).html(getIndustryName(data.list[name]));
						} else if (name == "focusProduct") {
							if (data.list[name] != null && data.list[name] != '') {
								$("#" + name).html(getMyProductNameStr(data.list[name]));
							}
						} else {
							$("#" + name).html(data.list[name]);
						}
					}

					$("#businessAddr").html(
							(data.list["country"] == null ? '' : data.list["country"] + ".") + (data.list["province"] == null ? '' : data.list["province"] + ".")
									+ (data.list["city"] == null ? '' : data.list["city"] + ".") + (data.list["businessAddr"] == null ? '' : data.list["businessAddr"]));

				}
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}
function getCrmLinkManByCustomerId(customerId) {
	$.ajax({
		url : "/ret/crmget/getCrmLinkManByCustomerId",
		type : "post",
		dataType : "json",
		data : {
			customerId : customerId
		},
		success : function(data) {
			if (data.status == 200) {
				for (var i = 0; i < data.list.length; i++) {
					$("#linkmanlist").append(
							'<div class="form-group">' + '							<label class="col-sm-1 control-label" style="text-align: center;">' + data.list[i].linkName + '</label>'
									+ '							<label class="col-sm-1 control-label" style="text-align: center;">' + data.list[i].deptName + '</label>'
									+ '							<label class="col-sm-1 control-label" style="text-align: center;">'
									+ (data.list[i].sex == '0' ? '其他' : (data.list[i].sex == "1" ? "男" : "女")) + '</label>'
									+ '							<label class="col-sm-1 control-label" style="text-align: center;">' + (data.list[i].postion == null ? '' : +data.list[i].postion)
									+ '</label>' + '							<label class="col-sm-1 control-label" style="text-align: center;">'
									+ (data.list[i].decisionMaker == "1" ? "不是决策人" : (data.list[i].decisionMaker == "2" ? "是决策人" : "未知")) + '</label>'
									+ '							<label class="col-sm-1 control-label" style="text-align: center;">' + data.list[i].mobile + '</label>'
									+ '							<label class="col-sm-1 control-label" style="text-align: center;">' + data.list[i].email + '</label>'
									+ '							<label class="col-sm-1 control-label" style="text-align: center;">' + data.list[i].tel + '</label>'
									+ '							<label class="col-sm-1 control-label" style="text-align: center;">' + data.list[i].fax + '</label>'
									+ '							<label class="col-sm-3 control-label" style="text-align: center;">' + (data.list[i].hobby == null ? '' : +data.list[i].hobby)
									+ '</label>' + '						</div>')
				}
			} else {
				console.log(data.msg);
			}
		}
	});
}
function pagination(curr, totalCount) {
	var limit = 10;
	$('#callBackPager').extendPagination({
		totalCount : totalCount,
		limit : limit,
		callback : function(curr, limit) {
			createTable(curr, limit);
		}
	});
}

function createTable(curr, pageSize) {
	$.ajax({
		url : "/ret/crmget/getRecordByCustomerId",
		type : "post",
		dataType : "json",
		data : {
			customerId : customerId,
			pageNumber : curr,
			pageSize : pageSize
		},
		success : function(data) {
			if (data.status == 200) {
				var list = data.list.list;
				var html = "";
				for (var i = 0; i < list.length; i++) {
					var userName1 = list[i].linkName;
					var userName2 = list[i].createUserStr;
					if (list[i].recordType == "1") {
						html += "<div style='text-align: left;margin-top:10px;margin-left:20px;margin-right:20px;font-size: 14px;'>" + list[i].content + "</div>"
								+ "<div style='text-align: left;margin-top:10px;margin-left:20px;margin-right:20px;font-size: 14px;' id='show_attach" + i + "'></div>"
								+ "<div style='text-align: left;margin-top:10px;margin-left:20px;margin-right:20px;font-size: 14px;' id='attach" + i + "' data_value='"
								+ list[i].attach + "'></div>"
								+ "<div style='text-align: right;margin-right:20px;border-bottom: 1px solid #ddd;'><span style='margin-right: 10px;'>" + list[i].linkName
								+ "</span>于" + list[i].createTime + "时间联系了<span style='margin-right: 10px;'>" + list[i].createUserStr + "</span></div>";
					} else {
						html += "<div style='text-align: left;margin-top:10px;margin-left:20px;margin-right:20px;font-size: 14px;'>" + list[i].content + "</div>"
								+ "<div style='text-align: left;margin-top:10px;margin-left:20px;margin-right:20px;font-size: 14px;' id='show_attach" + i + "'></div>"
								+ "<div style='text-align: left;margin-top:10px;margin-left:20px;margin-right:20px;font-size: 14px;' id='attach" + i + "' data_value='"
								+ list[i].attach + "'></div>"
								+ "<div style='text-align: right;margin-right:20px;border-bottom: 1px solid #ddd;'><span style='margin-right: 10px;'>" + list[i].createUserStr
								+ "</span>于" + list[i].createTime + "时间联系了<span style='margin-right: 10px;'>" + list[i].linkName + "</span></div>";
					}
				}
				$("#reocrdConent").html(html);
				for (var i = 0; i < list.length; i++) {
					if (list[i].attach) {
						createAttach("attach" + i, 0);
					}
				}
			} else {
				console.log(data.msg);
			}
		}
	});
}

function getRecordByCustomerId(curr, pageSize) {
	$.ajax({
		url : "/ret/crmget/getRecordByCustomerId",
		type : "post",
		dataType : "json",
		data : {
			customerId : customerId,
			pageNumber : curr,
			pageSize : pageSize
		},
		success : function(data) {
			if (data.status == 200) {
				pagination(curr, data.list.total);
				var list = data.list.list;
				var html = "";
				for (var i = 0; i < list.length; i++) {
					var userName1 = list[i].linkName;
					var userName2 = list[i].createUserStr;
					if (list[i].recordType == "1") {
						html += "<div style='text-align: left;margin-top:10px;margin-left:20px;margin-right:20px;font-size: 14px;'>" + list[i].content + "</div>"
								+ "<div style='text-align: left;margin-top:10px;margin-left:20px;margin-right:20px;font-size: 14px;' id='show_attach" + i + "'></div>"
								+ "<div style='text-align: left;margin-top:10px;margin-left:20px;margin-right:20px;font-size: 14px;' id='attach" + i + "' data_value='"
								+ list[i].attach + "'></div>"
								+ "<div style='text-align: right;margin-right:20px;border-bottom: 1px solid #ddd;'><span style='margin-right: 10px;'>" + list[i].linkName
								+ "</span>于" + list[i].createTime + "时间联系了<span style='margin-right: 10px;'>" + list[i].createUserStr + "</span></div>";
					} else {
						html += "<div style='text-align: left;margin-top:10px;margin-left:20px;margin-right:20px;font-size: 14px;'>" + list[i].content + "</div>"
								+ "<div style='text-align: left;margin-top:10px;margin-left:20px;margin-right:20px;font-size: 14px;' id='show_attach" + i + "'></div>"
								+ "<div style='text-align: left;margin-top:10px;margin-left:20px;margin-right:20px;font-size: 14px;' id='attach" + i + "' data_value='"
								+ list[i].attach + "'></div>"
								+ "<div style='text-align: right;margin-right:20px;border-bottom: 1px solid #ddd;'><span style='margin-right: 10px;'>" + list[i].createUserStr
								+ "</span>于" + list[i].createTime + "时间联系了<span style='margin-right: 10px;'>" + list[i].linkName + "</span></div>";
					}
				}
				$("#reocrdConent").html(html);
				for (var i = 0; i < list.length; i++) {
					if (list[i].attach) {
						createAttach("attach" + i, 0);
					}
				}
			} else {
				console.log(data.msg);
			}
		}
	});
}
function getIndustryName(industryId) {
	var industry = "";
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
				var list = data.list;
				if (list) {
					industry = list.industryName;
				}
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
	return industry;
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