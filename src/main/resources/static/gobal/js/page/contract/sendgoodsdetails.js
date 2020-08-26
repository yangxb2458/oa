$(function(){
	$.ajax({
		type : "post",
		url : "/ret/contractget/getContractSendgoodsById",
		data : {
			recordId : recordId
		},
		success : function(data) {
			if (data.status == 200) {
				for(var id in data.list)
				{
					if(id=="attach")
					{
						$("#contractattach").attr("data_value",data.list[id]);
						createAttach("contractattach",1);
					}else if(id=="contractId")
					{
						$.ajax({
							type : "post",
							url : "/ret/contractget/getContractById",
							data : {contractId : data.list[id]},
							success : function(res) {
								if (res.status == 200) {
									$("#contractId").html(res.list.title);
								} else if (res.status == "100") {
									top.layer.msg(res.msg);
								} else {
									console.log(res.msg);
								}
							}
							});
					}else
					{
						$("#"+id).html(data.list[id]);
					}
				}
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