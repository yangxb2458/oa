$(function(){
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
				$("#customerName").html(contract.customerName);
				$("#title").html(contract.title);
				if(contract.contractType=="1")
				{
					$("#contractType").html("销售合同");
				}else if(contract.contractType=="2")
				{
					$("#contractType").html("采购合同");
				}else if(contract.contractType=="3")
				{
					$("#contractType").html("服务合同");
				}
				$("#sortId").html(getSortName(contract.sortId));
				$("#startTime").html(contract.startTime);
				$("#endTime").html(contract.endTime);
				if(contract.payType=="1")
				{
					$("#payType").html("进度付款");
				}else if(contract.payType=="2")
				{
					$("#payType").html("滚动付款");
				}else if(contract.payType=="3")
				{
					$("#payType").html("款到发货");
				}else if(contract.payType=="4")
				{
					$("#payType").html("货到付款");
				}
				if(contract.cashType=="1")
				{
					$("#cashType").html("人民币");
				}else if(contract.cashType=="2")
				{
					$("#cashType").html("美元");
				}else if(contract.cashType=="3")
				{
					$("#cashType").html("其它");
				}
				$("#signTime").html(contract.signTime);
				$("#signAddress").html(contract.signAddress);
				$("#content").html(contract.content);
				$("#contractattach").attr("data_value",contract.attach);
				createAttach("contractattach",1);
				$("#registAddr").html(contract.registAddr);
				$("#legalPerson").html(contract.legalPerson);
				$("#mobile").html(contract.mobile);
				$("#bankAccount").html(contract.bankAccount);
				$("#taxNo").html(contract.taxNo);
				$("#myOrgName").html(contract.myOrgName);
				$("#myOrgAdd").html(contract.myOrgAdd);
				$("#myLegalPerson").html(contract.myLegalPerson);
				$("#mySignUser").html(getUserNameByStr(contract.mySignUser));
				$("#mySignMobile").html(contract.mySignMobile);
				$("#myBank").html(contract.myBank);
				$("#myBankAccount").html(contract.myBankAccount);
				$("#myTaxNo").html(contract.myTaxNo);
				$("#total").html(contract.total+"  元");
				$("#realTotal").html(contract.realTotal+"  元");
				$("#customerSignUser").html(contract.customerSignUser);
				$("#bank").html(contract.bank);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		},
		error : function(e) {
		}
	})
});
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