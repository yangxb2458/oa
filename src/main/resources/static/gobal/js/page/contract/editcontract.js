var setting1 = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/contractget/getContractSortTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "sortId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	view : {
		dblClickExpand : false,
		selectedMulti : false
	//禁止多选
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "sortId",
			pIdKey : "sortLeave",
			rootPId : "0"
		},
		key : {
			name : "sortName"
		}
	},
	callback : {
		onClick : function(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("menuTree"), nodes = zTree.getSelectedNodes(), v = "";
			vid = "";
			nodes.sort(function compare(a, b) {
				return a.id - b.id;
			});
			for (var i = 0, l = nodes.length; i < l; i++) {
				v += nodes[i].sortName + ",";
				vid += nodes[i].sortId + ",";
			}
			if (v.length > 0)
				v = v.substring(0, v.length - 1);
			if (vid.length > 0)
				vid = vid.substring(0, vid.length - 1);
			$("#sortId").attr("data-value", vid);
			$("#sortId").val(v);
			$("#menuContent").hide();
		}
	}
};
$(function() {
	jeDate("#signTime", {
		format : "YYYY-MM-DD"
	});
	jeDate("#startTime", {
		format : "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format : "YYYY-MM-DD"
	});
	$('#content').summernote({
		height : 200
	});
	/** 编辑*/
	$("#updatabut").click(function() {
		updateContract();
	})

	$.ajax({
		url : "/ret/contractget/getContractSortTree",
		type : "post",
		dataType : "json",
		success : function(data) {
			$.fn.zTree.init($("#menuTree"), setting1, data);
		}
	});
	$("#sortId").unbind("click").click(function(e) {
		e.stopPropagation();
		$("#menuContent").css({
			"width" : $(this).outerWidth() + "px"
		}).slideDown(200);
	});
	$("body").unbind("click").click(function() {
		$("#menuContent").hide();
	});
	$("#menuContent").unbind("click").click(function(e) {
		e.stopPropagation();
	});
	getContractById();
})

function getContractById() {
	$.ajax({
		type : "post",
		url : "/ret/contractget/getContractById",
		data : {
			contractId : contractId
		},
		async : false,
		success : function(data) {
			if (data.status == 200) {
				contract = data.list;
				$("#contractCode").html(contract.contractCode);
				$("#title").val(contract.title);
				$("#contractType").find("option[value=" + contract.contractType + "]").attr("selected", true);
				$("#sortId").attr("data-value", contract.sortId);
				$("#sortId").val(getSortName(contract.sortId));
				$("#startTime").val(contract.startTime);
				$("#endTime").val(contract.endTime);
				$("#payType").find("option[value=" + contract.payType + "]").attr("selected", true);
				$("#cashType").find("option[value=" + contract.cashType + "]").attr("selected", true);
				$("#signTime").val(contract.signTime);
				$("#signAddress").val(contract.signAddress);
				$("#content").code(contract.content);
				$("#contractattach").attr("data_value", contract.attach);
				createAttach("contractattach", 4);
				$("#registAddr").val(contract.registAddr);
				$("#legalPerson").val(contract.legalPerson);
				$("#mobile").val(contract.mobile);
				$("#bankAccount").val(contract.bankAccount);
				$("#taxNo").val(contract.taxNo);
				$("#myOrgName").val(contract.myOrgName);
				$("#myOrgAdd").val(contract.myOrgAdd);
				$("#myLegalPerson").val(contract.myLegalPerson);
				$("#mySignUser").attr("data-value",contract.mySignUser);
				$("#mySignUser").val(getUserNameByStr(contract.mySignUser));
				$("#mySignMobile").val(contract.mySignMobile);
				$("#myBank").val(contract.myBank);
				$("#myBankAccount").val(contract.myBankAccount);
				$("#myTaxNo").val(contract.myTaxNo);
				$("#total").val(contract.total);
				$("#realTotal").val(contract.realTotal);
				$("#customerName").val(contract.customerName);
				$("#customerSignUser").val(contract.customerSignUser);
				$("#bank").val(contract.bank);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		},
		error : function(e) {
		}
	})
	return contract;
}
function updateContract() {
	$.ajax({
		url : "/set/contractset/updateContract",
		type : "post",
		dataType : "json",
		data : {
			contractId : contractId,
			contractType : $("#contractType").val(),
			title : $("#title").val(),
			sortId : $("#sortId").attr("data-value"),
			startTime : $("#startTime").val(),
			endTime : $("#endTime").val(),
			payType : $("#payType").val(),
			cashType : $("#cashType").val(),
			signTime : $("#signTime").val(),
			signAddress : $("#signAddress").val(),
			content : $("#content").code(),
			attach : $("#contractattach").attr("data_value"),
			customerName : $("#customerName").val(),
			myOrgName : $("#myOrgName").val(),
			registAddr : $("#registAddr").val(),
			myOrgAdd : $("#myOrgAdd").val(),
			legalPerson : $("#legalPerson").val(),
			myLegalPerson : $("#myLegalPerson").val(),
			customerSignUser : $("#customerSignUser").val(),
			mySignUser:$("#mySignUser").attr("data-value"),
			mobile : $("#mobile").val(),
			mySignMobile : $("#mySignMobile").val(),
			bank : $("#bank").val(),
			myBank : $("#myBank").val(),
			bankAccount : $("#bankAccount").val(),
			myBankAccount : $("#myBankAccount").val(),
			taxNo : $("#taxNo").val(),
			myTaxNo : $("#myTaxNo").val(),
			total : $("#total").val(),
			realTotal : $("#realTotal").val()
		},
		success : function(data) {
			if (data.status == "200") {
				top.layer.msg(data.msg);
				window.location.reload();
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
}

function getSortName(sortId) {
	var returnStr="";
	$.ajax({
		url : "/ret/contractget/getContractSortById",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			sortId : sortId
		},
		success : function(data) {
			if (data.status == "200") {
				returnStr=data.list.sortName;
			}
		}
	});
return returnStr;
}