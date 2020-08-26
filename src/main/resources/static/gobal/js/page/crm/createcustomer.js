Array.prototype.unique2 = function(){
 this.sort(); //先排序
 var res = [this[0]];
 for(var i = 1; i < this.length; i++){
  if(this[i] !== res[res.length - 1]){
   res.push(this[i]);
  }
 }
 return res;
}
$(function(){
	jeDate("#registerDate", {
		format: "YYYY-MM-DD"
	});
	jeDate("#timeOfVaildity", {
		format: "YYYY-MM-DD"
	});
	$.ajax({
		url : "/ret/crmget/getCrmIndustryList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status==200){
				var list = data.list;
				$("#industry").append("<option value=''>请选择</option>")
				for(var i=0;i<list.length;i++)
					{
					$("#industry").append("<option value='"+list[i].industryId+"'>"+list[i].industryName+"</option>")
					}
			}else if(data.status=="100")
				{
				top.layer.msg(data.msg);
				}else
					{
					console.log(data.msg);
					}
		}
	});
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

if($("#customerId").val()=="")
{
	$("#customerProduct").tagsinput();
	$("#opponent").tagsinput();
	$("#updatabtn").remove();
	$("#backbtn").remove();
	$("#createbtn").unbind("click").click(function(){
		createCustomer();
	});
	loadInfo("");
}else
	{
	$("#updatabtn").unbind("click").click(function(){
		updateCustomer();
	});
	$("#backbtn").unbind("click").click(function(){
		history.go(-1);
	});
	$("#createbtn").remove();
	$.ajax({
		url : "/ret/crmget/getCrmCustomerById",
		type : "post",
		dataType : "json",
		data:{customerId:$("#customerId").val()},
		success : function(data) {
			if(data.status==200){
				for(var name in data.list)
					{
						if(name=="remark"||name=="scopeBusiness")
							{
							$("#"+name).code(data.list[name]);
							}else if(name=="country")
							{
								$("#"+name).find("option[value=" + data.list[name] + "]").attr("selected", true).trigger("chosen:updated");
								$("#country").change();
								$("#province").find("option[value=" + data.list.province + "]").attr("selected", true).trigger("chosen:updated");
								$("#province").change();
								$("#city").find("option[value=" + data.list.city + "]").attr("selected", true).trigger("chosen:updated");
							}else if(name=="follow")
								{
									if(data.list[name])
									{
										var valArr = data.list[name].split(",");
										for(var j=0;j<valArr.length;j++)
										{
											$("input:checkbox[name='follow'][value='"+valArr[j]+"']").attr("checked","checked");
										}
									}
								}else if(name=="intention")
								{
									$("input:radio[name='intention'][value='"+data.list[name]+"']").attr("checked",true);
								}else if(name=="customerProduct")
								{
									$("#customerProduct").val(data.list[name]);
									$("#customerProduct").tagsinput();
								}else if(name=="focusProduct")
								{
									loadInfo(data.list[name]);
								}else if(name=="opponent")
								{
									$("#opponent").val(data.list[name]);
									$("#opponent").tagsinput();
								}else
								{
								if(name!="province"&&name!="city")
									{
									$("#"+name).val(data.list[name]);
									}
							}
					}
				createTagsSelect();
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

$('.selectpicker').selectpicker({
    selectedText: 'cat',
    noneSelectedText: '请选择',
    noneResultsText: '没有找到相关的产品 {0}'
});
});

function loadInfo(focusProducts) {
     $.ajax({
         type: "post",
         url: "/ret/crmget/getCrmMyProductList",
         success: function (data) {
             if (data.status =="200") {
            	 var focusProduct = document.getElementById("focusProduct");
                 var result = data.list;
                 for (var i=0;i<result.length;i++) {
                	 focusProduct.options.add(new Option(result[i].productName,result[i].productId));
                 }
                 if(focusProducts)
                	 {
                	 	var arr = focusProducts.split(",")
                	 	$('#focusProduct').selectpicker('val', arr); 
                	 }
                 $('.selectpicker').selectpicker('refresh');
             } else if(data.status=="100")
             {
            	 top.layer.msg(data.msg);
             }else
             {
                 controls.log(data)
             }
         }
     });
}
function createTagsSelect()
{
	$(".choose-c").html("所属行业暂无标签");
　　$.ajax({
	url : "/ret/crmget/getCrmTagsbyIndustryList",
	type : "post",
	data:{industryId:$("#industry").val()},
	dataType : "json",
	success : function(data) {
		if(data.status==200){
			var list = data.list;
			var html="";
			for(var i=0;i<list.length;i++)
				{
				html+="<span class='list-1 list'><span class='item js-first'><span>"+list[i].tagsName+"</span></span>";
				html+="</span>";
				$(".choose-c").html(html);
				}
			var value= $("#tags").val();
			if(value)
			{
				var tags = value.split(",");
				$("body").find('.item').each(function(){
					for(var i=0;i<tags.length;i++)
					{
						if($(this).find('span').text()==tags[i])
						{
							
							$(this).find('span').addClass('badge badge-purple badge-square');
						}
					}
				})
			}
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
function getFocusProduct()
{
	var value=$('#focusProduct').selectpicker('val');
	if(value)
		{
		return value.join(",");
		}else
			{
			return "";
			}
	}
function createCustomer()
{
	$.ajax({
		url : "/set/crmset/createCrmCustomer",
		type : "post",
		dataType : "json",
		data:{
			cnName:$("#cnName").val(),
			enName:$("#enName").val(),
			level:$("#level").val(),
			source:$("#source").val(),
			model:$("#model").val(),
			roles:$("#roles").val(),
			nature:$("#nature").val(),
			selStage:$("#selStage").val(),
			industry:$("#industry").val(),
			focusProduct:getFocusProduct(),
			creditCode:$("#creditCode").val(),
			registerCapital:$("#registerCapital").val(),
			registerDate:$("#registerDate").val(),
			timeOfVaildity:$("#timeOfVaildity").val(),
			legalPerson:$("#legalPerson").val(),
			status:$("#status").val(),
			registerAddr:$("#registerAddr").val(),
			country:$("#country").val(),
			province:$("#province").val(),
			tags:$("#tags").val(),
			follow:getCheckBoxValue("follow"),
			customerProduct:$("#customerProduct").val(),
			intention:getCheckBoxValue("intention"),
			opponent:$("#opponent").val(),
			city:$("#city").val(),
			businessAddr:$("#businessAddr").val(),
			tel:$("#tel").val(),
			fax:$("#fax").val(),
			eMail:$("#eMail").val(),
			webSite:$("#webSite").val(),
			scopeBusiness:$("#scopeBusiness").code(),
			remark:$('#remark').code()
		},
		success : function(data) {
			if(data.status=="200"){
				top.layer.msg(data.msg);
				location.reload();
			}else if(data.status=="100"){
				top.layer.msg(data.msg);
			}else
				{
				console.log(data.msg);
				}
		}
	});
}

function updateCustomer()
{
	$.ajax({
		url : "/set/crmset/updateCrmCustomer",
		type : "post",
		dataType : "json",
		data:{
			customerId:$("#customerId").val(),
			cnName:$("#cnName").val(),
			enName:$("#enName").val(),
			level:$("#level").val(),
			source:$("#source").val(),
			model:$("#model").val(),
			roles:$("#roles").val(),
			nature:$("#nature").val(),
			selStage:$("#selStage").val(),
			industry:$("#industry").val(),
			focusProduct:getFocusProduct(),
			creditCode:$("#creditCode").val(),
			registerCapital:$("#registerCapital").val(),
			registerDate:$("#registerDate").val(),
			timeOfVaildity:$("#timeOfVaildity").val(),
			legalPerson:$("#legalPerson").val(),
			status:$("#status").val(),
			registerAddr:$("#registerAddr").val(),
			follow:getCheckBoxValue("follow"),
			customerProduct:$("#customerProduct").val(),
			intention:getCheckBoxValue("intention"),
			opponent:$("#opponent").val(),
			country:$("#country").val(),
			tags:$("#tags").val(),
			province:$("#province").val(),
			city:$("#city").val(),
			businessAddr:$("#businessAddr").val(),
			tel:$("#tel").val(),
			fax:$("#fax").val(),
			eMail:$("#eMail").val(),
			webSite:$("#webSite").val(),
			scopeBusiness:$("#scopeBusiness").code(),
			remark:$('#remark').code()
		},
		success : function(data) {
			if(data.status=="200"){
				top.layer.msg(data.msg);
			}else if(data.status=="100"){
				top.layer.msg(data.msg);
			}else
				{
				console.log(data.msg);
				}
		}
	});
}