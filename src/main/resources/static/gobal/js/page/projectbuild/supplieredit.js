$(function() {
	jeDate("#registerDate", {
		format : "YYYY-MM-DD"
	});
	jeDate("#timeOfVaildity", {
		format : "YYYY-MM-DD"
	});
	getSupperlier();
	var country = '<option value="0" a="0">-选择国家-</option>';
	for (var a=0;a<=_areaList.length-1;a++) {
		var objContry = _areaList[a];
			country += '<option value="'+objContry+'" a="'+(a+1)+'">'+objContry+'</option>';
	}
	$("#country").html(country).chosen().change(function(){
		var a = $("#country").find("option[value='"+$("#country").val()+"']").attr("a");
		var _province = areaObj[a];
		var province = '';
		for (var b in _province) {
			var objProvince = _province[b];
			if (objProvince.n) {
				province += '<option value="'+objProvince.n+'" b="'+b+'">'+objProvince.n+'</option>';
			}
		}
		if (!province) {
			province = '<option value="0" b="0">-选择省份-</option>';
		}
		$("#province").html(province).chosen().change(function(){
			var b = $("#province").find("option[value='"+$("#province").val()+"']").attr("b");
			var _city = areaObj[a][b];
			var city = '';
			for (var c in _city) {
				var objCity = _city[c];
				if (objCity.n) {
					city += '<option value="'+objCity.n+'">'+objCity.n+'</option>';
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
	$("#country").find("option[value='中国']").attr("selected", true).trigger("chosen:updated");
	$("#country").change();
	$("#updatebtn").unbind("click").click(function(){
		updateSupplier();
	})
})

function getSupperlier()
{
	$.ajax({
		url : "/ret/projectbuildsupplierget/getProjectBuildSupplierById",
		type : "post",
		dataType : "json",
		data : {
			supplierId:supplierId
		},
		success : function(data) {
			if (data.status == 200) {
				for(name in data.list)
					{
						if(name=="remark")
						{
							$("#remark").code(data.list.remark);
						}else if(name=="country")
						{
							$("#"+name).find("option[value=" + data.list[name] + "]").attr("selected", true).trigger("chosen:updated");
							$("#country").change();
							$("#province").find("option[value=" + data.list.province + "]").attr("selected", true).trigger("chosen:updated");
							$("#province").change();
							$("#city").find("option[value=" + data.list.city + "]").attr("selected", true).trigger("chosen:updated");
						}else if(name=="attach")
						{
							$("#projectbuildattach").attr("data_value",data.list.attach);
							createAttach("projectbuildattach","1");
						}else
						{
							if(name!="province"&&name!="city")
								{
								$("#"+name).val(data.list[name]);
								}
						}
					}
			} else if(data.status==100)
				{
					top.layer.msg(data.msg);
				}else
				{
				console.log(data.msg);
			}
		}
	});
}



function updateSupplier()
{
	$("#form").bootstrapValidator('validate');
	var flag = $('#form').data('bootstrapValidator').isValid();
	if(flag)
	{
	$.ajax({
		url : "/set/projectbuilsupplierset/updateSupplier",
		type : "post",
		dataType : "json",
		data : {
			supplierId:supplierId,
			companyName : $("#companyName").val(),
			sortNo : $("#sortNo").val(),
			level : $("#level").val(),
			industry : $("#industry").val(),
			tags : $("#tags").val(),
			creditCode : $("#creditCode").val(),
			registeredCapital : $("#registeredCapital").val(),
			registerDate : $("#registerDate").val(),
			timeOfVaildity : $("#timeOfVaildity").val(),
			legalPerson : $("#legalPerson").val(),
			status : $("#status").val(),
			remark : $("#remark").val(),
			registerAddr : $("#registerAddr").val(),
			country : $("#country").val(),
			province : $("#province").val(),
			city : $("#city").val(),
			address : $("#address").val(),
			tel : $("#tel").val(),
			fax : $("#fax").val(),
			email : $("#email").val(),
			linkName : $("#linkName").val(),
			bankOfDeposit : $("#bankOfDeposit").val(),
			bankAccount : $("#bankAccount").val(),
			dutyParagraph : $("#dutyParagraph").val(),
			attach :$("#projectbuildattach").attr("data_value")
		},
		success : function(data) {
			if (data.status == 200) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
	}
}