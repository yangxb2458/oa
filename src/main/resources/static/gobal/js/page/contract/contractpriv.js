$(function() {
	$.ajax({
		url : "/ret/contractget/getContractPriv",
		type : "post",
		dataType : "json",
		data : {},
		success : function(data) {
			if (data.status == 200) {
				if (data.list) {
					for ( var name in data.list) {
						if (name == "financialStaff") {
							$("#financialStaff").attr("data-value", data.list[name]);
							$("#financialStaff").val(getUserNameByStr(data.list[name]));
						} else if (name == "shipper") {
							$("#shipper").attr("data-value", data.list[name]);
							$("#shipper").val(getUserNameByStr(data.list[name]));
						} else if (name == "lendingExaminer") {
							$("#lendingExaminer").attr("data-value", data.list[name]);
							$("#lendingExaminer").val(getUserNameByStr(data.list[name]));
						} else if (name == "borrowPerson") {
							$("#borrowPerson").attr("data-value", data.list[name]);
							$("#borrowPerson").val(getUserNameByStr(data.list[name]));
						}
					}
				}
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
	$(".js-setbtn").unbind("click").click(function() {
		$.ajax({
			url : "/set/contractset/setContractPriv",
			type : "post",
			dataType : "json",
			data : {
				financialStaff : $("#financialStaff").attr("data-value"),
				sender : $("#shipper").attr("data-value"),
				lendingExaminer : $("#lendingExaminer").attr("data-value"),
				borrowPerson : $("#borrowPerson").attr("data-value")
			},
			success : function(data) {
				if (data.status == 200) {
					top.layer.msg(data.msg);
				} else if (data.status == 100) {
					top.layer.msg(data.msg);
				} else {
					console.log(data.msg);
				}
			}
		});
	});
});