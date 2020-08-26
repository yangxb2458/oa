/**
 * [getparams 获取表单页面的值]
 * @return {[type]} [页面所有值]
 */
function getparams()
{
	var json = {};
	$.each($("[xtype]"),function() {
		var eName = $(this).attr("name");
			var xType = $(this).attr("xtype");
			if (xType == "xlist") {
				json[eName] =JSON.stringify(getBpmChildTable(eName));
			} else if (xType == "xradio") {
				if ($(this).is(':checked')) {
					json[eName] = $(this).val();
				}
			} else if (xType == "xcheckbox") {
				var name = $(this).attr("name");
				var value = [];
				$.each($('input[name=' + name + ']:checkbox:checked'), function() {
					value.push($(this).val());
				})
				json[eName] = value.join(",");
			} else if(xType == "xbpm")
			{
				json[eName] =getBpmListSelectData(eName);
			}else if(xType =="xupload")
			{
				json[eName] =$("#"+eName+"attach").attr("data_value");
			}else if(xType=="xsql")
			{
				json[eName] = $(this).prop("outerHTML");
			}else if(xType=="xsqlselect"){
				var tmpjson={};
				tmpjson.vkey=$(this).val();
				tmpjson.value=$(this).find("option:selected").text();
				json[eName]=JSON.stringify(tmpjson);
			}else if(xType=="xhtml"){
				json[eName] = $(this).formhtml();
			}else if(xType=="xtextuedit"){
				json[eName] = $(this).code();
			}else if(xType=="xseal")
			{
				json[eName] = $(this).attr("src");
			}
			else{
				json[eName] = $(this).val();
			}
	})
	return json;
}
function getvalue(xtype,name)
{
	if(xtype=="xradio")
	{
		return $("input:radio[name='"+name+"']:checked").val();
	}else if(xtype=="xlist")
	{
		return $("table[name='" + name + "']").bootstrapTable("getData");
	}else if(xtype=="xsql")
	{
		return $(this).prop("outerHTML");
	}
	else if(xtype=="xsqlselect")
	{
		var tmpjson={};
			tmpjson.vkey=$(this).val();
			tmpjson.value=$(this).find("option:selected").text();
		return tmpjson;
	}else if(xtype=="xcheckbox")
	{
		var value = [];
		$.each($('input[name=' + name + ']:checkbox:checked'), function() {
			value.push($(this).val());
		})
		return value;
	}else if(xtype=="xinput")
	{
		return $('input[name=' + name + ']').val();
	}
}
/**
 * [getpagefields 获取页面所有字段信息]
 * @param  {[type]} pageId [页面PAGE_ID]
 * @return {[type]}        [页面字段列表]
 */
function getpagefields(pageId)
{
	var returnval;
	$.ajax({
		url : "/ret/platformget/getPageTableFieldBeansList",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			pageId:pageId
		},
		success : function(data) {
			if (data.status == "200") {
				returnval = data.list;
			} else if(data.status=="100"){
				top.layer.msg(data.msg);
			}else {
				console.log(data.msg);
			}
		}
	});
	return returnval;
}