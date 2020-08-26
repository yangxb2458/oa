$(function(){
	jeDate("#sendDate", {
		format : "YYYY-MM-DD"
	});
	$('#remark').summernote({
		height : 200
	});
	$("#contractId").select2({
		theme : "bootstrap",
		allowClear : true,
		placeholder : "请输合同名称或编号",
		query : function(query) {
			var url = "/ret/contractget/getSelect2ContractList";
			var param = {
				search : query.term
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
						"id" : land.contractId,
						"text" : land.title
					};
					data.results.push(option);
				}
				query.callback(data);
			}, type);
		},
		escapeMarkup : function(markup) {
			return markup;
		},
		minimumInputLength : 2,
		formatResult : function(data) {
			return '<div class="select2-user-result">' + data.text + '</div>'
		},
		formatSelection : function(data) {
			return data.text;
		},
		initSelection : function(data, cb) {
			cb(data);
		}
	});
	$("#createbut").unbind("click").click(function(){
		addSendgoodsRecord();
	})
})

function addSendgoodsRecord()
{
	$.ajax({
		type : "post",
		dataType : "json",
		url : "/set/contractset/insertContractSendgoods",
		data : {
			contractId : $("#contractId").val(),
			sendDate:$("#sendDate").val(),
			trackingComp:$("#trackingComp").val(),
			trackingNumber:$("#trackingNumber").val(),
			consignee:$("#consignee").val(),
			address:$("#address").val(),
			iphone:$("#iphone").val(),
			remark:$("#remark").code(),
			userPriv:$("#userPriv").attr("data-value"),
			attach:$("#contractattach").attr("data_value")
		},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				window.location.reload();
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
	});
}