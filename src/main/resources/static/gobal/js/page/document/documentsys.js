//驼峰式转下横线	
function toLowerLine(str) {
	var temp = str.replace(/[A-Z]/g, function (match) {	
		return "_" + match.toLowerCase();
  	});
  	if(temp.slice(0,1) === '_'){ //如果首字母是大写，执行replace时会多一个_，这里需要去掉
  		temp = temp.slice(1);
  	}
	return temp;
};
//下横线转驼峰式
function toCamel(str) {
	str=str.toLowerCase();
  	return str.replace(/([^_])(?:_+([^_]))/g, function ($0, $1, $2) {
	    return $1 + $2.toUpperCase();
  	});
}

function getFieldNameAuto(fieldCount)
{
	return "dataNum"+(fieldCount+1);
	}
//判断当前时间是否超时
function getPassTimeStatus(timestamp1)
{
	
	// 获取当前时间戳(以s为单位)
	var timestamp = Date.parse(new Date());
	if(timestamp1>timestamp)
		{
		return true;
		}else
			{
		return false;	
			}
	}

function getNowStep(runId)
{
	var returnJsonArr;
	$.ajax({
		url : "/ret/documentget/getNowStep",
		type : "post",
		dataType : "json",
		async : false,
		data:{runId:runId},
		success : function(data) {
			if(data.status=="200")
				{
				returnJsonArr = data.list;
				}
		}
	});
	return returnJsonArr;
}


function getChangeDocumentUserStep(runId)
{
	var returnJsonArr;
	$.ajax({
		url : "/ret/documentget/getChangeDocumentUserStep",
		type : "post",
		dataType : "json",
		async : false,
		data:{runId:runId},
		success : function(data) {
			if(data.status=="200")
				{
				returnJsonArr = data.list;
				}
		}
	});
	return returnJsonArr;
}

function selectdocument(Obj)
{
	var type = $(Obj).attr("data-value");
	var parentName = $(Obj).attr("data-parent");
	$("#documentselectdiv").html(xdocumentSelect);
	$("#searchdocument").val("");
	$("#searchType").val(type);
	$(".js-querydocument").unbind("click").click(function(){
		$("#mydocumentTable").bootstrapTable("refresh");
	})
	$(".xdocumentSelectokbtn").unbind("click").click(function(){
		var arr = returnSelectDocumentList();
		$(".js-documentlist-"+parentName).each(function(){
			$(this).remove();
		});
		for(var i=0;i<arr.length;i++)
			{
			$("div[name='"+parentName+"']").append("<div class='alert alert-info fade in js-documentlist-"+parentName+"' style='margin-bottom: 2px;margin-top:2px;'>" +
					"<button class='close' data-dismiss='alert'>×</button>" +
					"<a onclick='readdocument(this)' data-value='"+arr[i].runId+"' style='cursor: pointer;color: #FFFFFF;'>"+arr[i].flowTitle+"</a></div>"
			);
			}
		$("#xdocumentselect").modal("hide");
	});
	query();
	$("#xdocumentselect").modal("show");
}

function getDocumentListSelectData(parentName)
{
	var returnArr=[];
	$(".js-documentlist-"+parentName).each(function(){
		returnArr.push($(this).find("a").attr("data-value"));
	})
	return returnArr.join(",");
}




function returnSelectDocumentList()
{
	var selected = $('#mydocumentTable').bootstrapTable('getSelections');
	var ids = new Array();
	for(var i=0;i<selected.length;i++){
	var json={};
	json.runId = selected[i].runId;
	json.flowTitle = selected[i].flowTitle;
	ids.push(json);
	}
	return ids;
}


var xdocumentSelect = [
	'<div id="xdocumentselect" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">',
	'        <div class="modal-dialog modal-lg">',
	'            <div class="modal-content">',
	'                <div class="modal-header">',
	'                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>',
	'                    <h4 class="modal-title" id="myLargeModalLabel">备选BPM列表</h4>',
	'                </div>',
	'                <div class="modal-body" style="padding: 0px;">',
	'                 <div class="row" style="margin: 10px">',
	'                 	<div class="col-xs-10 col-md-10"><input type="text" class="form-control" id="searchdocument" placeholder="BPM标题,流水号" ><input type="hidden" class="form-control" id="searchType" ></div>',
	'                 	<div class="col-xs-2 col-md-2"><a href="javascript:void(0);" class="btn btn-darkorange js-querydocument" style="width:100%">查询</a></div>',
	'                 </div>',
	'                 <div class="row" style="margin: 10px">',
	'                 <table id="mydocumentTable" style="width:100%">',
	'                </table>',
	'                </div>',	
	'                </div>',
	'                <div class="modal-footer">',
	'                   <button type="button" class="btn btn-warning" data-dismiss="modal">取消</button>',
	'                  <button type="button" class="btn btn-primary xdocumentSelectokbtn" >确定</button>',
	'               </div>',
	'        </div>', 
	'    </div>' ].join("");


function readdocument(Obj)
{
	var runId = $(Obj).attr("data-value");
	window.open("/app/core/document/documentread?runId=" + runId);
}

function query()
{
	 $("#mydocumentTable").bootstrapTable({
	      url: '/ret/documentget/getXDocumentSelectList',
	      method: 'post',
	      clickEdit: true,
	      contentType:'application/x-www-form-urlencoded',
	      striped: true,//隔行换色
	      cache: false,//禁用缓存
	      pagination: true,//启动分页
	      sidePagination: 'server',//分页方式
	      pageNumber: 1,//初始化table时显示的页码
	      pageSize: 10,//每页条目
	      showFooter: false,//是否显示列脚
	      showPaginationSwitch: false,//是否显示 数据条数选择框
	      sortable: true,//排序
	      search: false,//启用搜索
	      showColumns: false,//是否显示 内容列下拉框
	      showRefresh: false,//显示刷新按钮
	      idField: 'runId',//key值栏位
	      clickToSelect: true,//点击选中checkbox
	      pageList : [10, 20, 30, 50],//可选择单页记录数
	      queryParams:queryParams1,
	      columns: [ {
	      checkbox: true
	      },
	      {
		       field: 'id',
		       title: '流水号',
		       sortable : true,
		       width:'80px'
		      },
	      {
	       field: 'flowTitle',
	       title: 'BPM标题',
	       sortable : true,
	       width:'400px'
	      },
	      {
		       field: 'createUser',
		       width:'100px',
		       title: '创建人'
		   },
		   {
		       field: 'createTime',
		       sortable : true,
		       width:'150px',
		       title: '创建时间'
		   }],
	      onClickCell: function (field, value, row, $element) {
	    },
	    responseHandler:function(res){
	    	if(res.status=="500")
    		{
    		console.log(res.msg);
    		}else if(res.status=="100")
    			{
    			top.layer.msg(res.msg);
    			}else
    			{
    			return {
    				total : res.list.total, //总页数,前面的key必须为"total"
    				rows : res.list.list //行数据，前面的key要与之前设置的dataField的值一致.
    			};
    			}
	    }
	   });
}
function queryParams1(params) {
var temp = {
	searchdocument: $("#searchdocument").val(),
    pageSize:this.pageSize,
    pageNumber:this.pageNumber,
    sort: params.sort,
    sortOrder:params.order,
    searchType:$("#searchType").val(),
    runId:runId
    
};
return temp;
};

function initChileTable(eName,documentProcess,Obj)
{
	var isTotalFlag=false;
	var childTableRowPriv = JSON.parse(documentProcess.childTableRowPriv);
	if(childTableRowPriv!=null)
	{
		if(childTableRowPriv[eName].addpriv=="1")
		{
			$("#toobar_"+eName).append("<a href='javascript:void(0);' class='btn btn-magenta js-addrowbtn-"+eName+"'>添加行</a>");
		}
	}
	var model = JSON.parse($(Obj).attr("model"));
	if (documentProcess.childTablePriv) {
		var privArrs = JSON.parse(documentProcess.childTablePriv)[eName];
		var jsonPriv={};
		for(var i=0;i<privArrs.length;i++)
		{
			jsonPriv[privArrs[i].childName]=privArrs[i];
		}
		var tableFieldArr = [];
		var uniqueJson = {};
		uniqueJson.field = "UNIQUE_ID";
		uniqueJson.visible=false;
		tableFieldArr.push(uniqueJson);
		for (var i = 0; i < model.length; i++) {
			var fieldJson = {};
			fieldJson.field = model[i].childName;
			fieldJson.title = model[i].childTitle;
			fieldJson.isTotal = model[i].isTotal;
			if(jsonPriv[model[i].childName].optpriv=="2")
			{
				fieldJson.visible=false;
			}else if(jsonPriv[model[i].childName].optpriv=="0")
			{
				fieldJson.cellStyle={css:{"color":"#ed4e2a"}};
			}
			fieldJson.childModel = model[i].childModel;
			fieldJson.formula =  model[i].formula;
			if(model[i].formula!="")
			{
				fieldJson.formatter=rowCalculate;
			}
			fieldJson.defaultValue= model[i].defaultValue;
			fieldJson.width =  model[i].width+"px";
			if(model[i].isTotal==true)
			{
				isTotalFlag = true;
				fieldJson.footerFormatter= totals;
			}
			tableFieldArr.push(fieldJson);
		}
		if(childTableRowPriv!=null)
		{
			if(childTableRowPriv[eName].delpriv=="1")
			{
				var endJson = {};
				endJson.field = "opt";
				endJson.title = "操作";
				endJson.width ="50px";
				endJson.formatter=function(value,row,index){
					return createDocumentTableOptBtn(row.UNIQUE_ID,eName);
				};
				tableFieldArr.push(endJson);
			}
		}
		$(".js-addrowbtn-"+eName).unbind("click").click(function(){
			addDocumentTableRow(eName,tableFieldArr);
		});
		createChildTable(eName, tableFieldArr,jsonPriv,isTotalFlag);
	}else
	{
		$("#toobar_"+eName).remove();
	}
}
function rowCalculate(value,row,index)
{
	var formula = this.formula;
	return childTableRowCalculate(formula,value,row,index);
}


function childTableRowCalculate(formula,value,row,index)
{
	
					//var data_regx = /(?<=\().*(?=\))/;
					var data_regx = /\(([^)]*)\)/;
					var optStr = formula.match(data_regx)[1];
					optStr = optStr.replace(/\+/g, ",").replace(/\-/g, ",").replace(/\*/g, ",")
					.replace(/\\/g, ",").replace(/\(/g, "").replace(/\)/g, ",").replace(/\[/g, "").replace(/\]/g, "").replace(/\{/g, "").replace(/\}/g, "");
					var optArr = optStr.split(",");
					for(var j=0;j<optArr.length;j++)
					{
						if(isNaN(optArr[j])){
							var reg = new RegExp( "\("+optArr[j]+"\)" , "g" );
							formula = formula.replace(reg, row[optArr[j]]);
						}
					}
					var optstr = formula.match(data_regx)[1];
					if (formula.indexOf("RMB") > -1)
					{
						 if(checkFormat(optstr)) { 
							 return RMB(compute(optstr));
						 }else
							{
							 return  RMB(0);
							}
					}else
					{
						if(checkFormat(optstr)) { 
							return  compute(optstr);
						}else
						{
							return  0;
						}
					}
					
}

function totals (value) {
	var field = this.field;
    var count = 0;
    for (var i in value) {
    	if(value[i][field]!=null){
            count += parseInt(value[i][field]);
        }
    }
    return count;
   // return count.toFixed(2);//保留两位小数
}

function createChildTable(eName, tableFields,jsonPriv,isTotalFlag) {
	var calculateFormula = getFormula(tableFields);
	var jsonFields = {};
	for(var i=0;i<tableFields.length;i++)
		{
			jsonFields[tableFields[i].field] = tableFields[i];
		}
	
	
	$("table[name='" + eName + "']").bootstrapTable({
		url : '/ret/documentget/getDocumentChileTableData?eName='+eName,
		method : 'post',
		contentType : 'application/x-www-form-urlencoded',
		toolbar : '#toobar_'+eName, // 工具列
		striped : true, // 隔行换色
		cache : false, // 禁用缓存
		pagination : true, // 启动分页
		sidePagination : 'server', // 分页方式
		showPaginationSwitch : true, // 是否显示 数据条数选择框
		sortable : true, // 排序
		search : false, // 启用搜索
		showColumns : true, // 是否显示 内容列下拉框
	    showFooter: isTotalFlag,   //显示合计栏
		showRefresh : true, // 显示刷新按钮
		idField : 'UNIQUE_ID', // key值栏位
		clickToSelect : true, // 点击选中checkbox
		queryParams : queryParamsChildTable,
		columns : tableFields,
		onClickCell : function(field, value, row, $element) {
			if(field!='opt')
			{
			if(jsonPriv[field].optpriv=="1")
				{
				let index = $element.parent().data('index');
				var title = jsonFields[field].title;
				var defaultValue = jsonFields[field].defaultValue;
				var childModel = jsonFields[field].childModel;
				editRowModal(eName,childModel,field,title,row,defaultValue);
				$(".js-save-row").unbind("click").click(function(){
					var tempvalue=""
						if(childModel=="1")
						{
							tempvalue=$("input[name='row_"+field+"']").val();
						}else if(childModel=="2")
						{
							tempvalue=$("input[name='row_"+field+"']").val()
						}else if(childModel=="3")
						{
							tempvalue=$('input:radio[name="row_'+field+'"]:checked').val()
						}else if(childModel=="4")
						{
							tempvalue=getCheckBoxValue('row_'+field);
						}else if(childModel=="5")
						{
							tempvalue=$("input[name='row_"+field+"']").val();
						}
					saveData(eName,index, field, tempvalue);
				});
			}else
			{
				alert("您没有编辑权限!");
			}
			}
		},
		responseHandler : function(res) {
			if (res.status == "500") {
				console.log(res.msg);
				top.layer.msg(res.msg);
			} else {
				return {
					total : res.list.total, // 总页数,前面的key必须为"total"
					rows : res.list.list// 行数据，前面的key要与之前设置的dataField的值一致.
				};
			}
		},
		onPostBody:function () {
            //合并页脚
           // merge_footer();
        }
	});
	//childTableRowCalculate(calculateFormula)
}
function saveData(eName,index, field, value) {
	$("table[name='" + eName + "']").bootstrapTable('updateCell', {
        index: index,       //行索引
        field: field,       //列名
        value: value        //cell值
    })
    $("#addrowmodal_"+eName).modal("hide");
}

function createDocumentTableOptBtn(uniqueId,eName) {
	var html = "<a onclick=\"del_row(this)\" value-data='" + uniqueId + "' value-cname='" + eName + "' class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
}

function queryParamsChildTable(params) {
	var temp = {
		runId : runId
	};
	return temp;
};


function getFormula(tableFields)
{
	var json={};
	for(var i=0;i<tableFields.length;i++)
		{
			if(tableFields[i].formula!=""&&tableFields[i].formula!=undefined)
				{
				var optStr = tableFields[i].formula;
				//var data_regx = /(?<=\().*(?=\))/;
				var data_regx = /\(([^)]*)\)/;
				var optStr = optStr.match(data_regx)[1];
				optStr = optStr.replace(/\+/g, ",").replace(/\-/g, ",").replace(/\*/g, ",")
				.replace(/\\/g, ",").replace(/\(/g, "").replace(/\)/g, ",").replace(/\[/g, "").replace(/\]/g, "").replace(/\{/g, "").replace(/\}/g, "");
				var optArr = optStr.split(",");
				for(var j=0;j<optArr.length;j++)
				{
					if(isNaN(optArr[j])){
						if(json[optArr[j]]==undefined)
							{
							var a=[];
							var tempjson = {};
							tempjson.resField=tableFields[i].field;
							tempjson.formula = tableFields[i].formula;
							a.push(tempjson);
							json[optArr[j]]=a;
							}else
							{
								var tempjson = {};
								tempjson.resField=tableFields[i].field;
								tempjson.formula = tableFields[i].formula;
								json[optArr[j]].push(tempjson);		
							}
					}
				}
			}
		}
	return json;
}
//添加行
function addDocumentTableRow(eName,tableFields){
	var uniqueId = (new Date()).getTime()+"_"+ Math.random();
	var count = $("table[name='" + eName + "']").bootstrapTable('getData').length;
	var jsonFiles={};
	for(var i=0;i<tableFields.length;i++)
	{
		if(tableFields[i].field=="UNIQUE_ID")
		{
			jsonFiles[tableFields[i].field]=uniqueId;
		}else if(tableFields[i].field=="opt")
		{
			jsonFiles[tableFields[i].field]=createDocumentTableOptBtn(uniqueId,eName);
		}else
		{
			jsonFiles[tableFields[i].field]="";
		}
	}
	$("table[name='" + eName + "']").bootstrapTable('hideColumn', 'UNIQUE_ID');
	$("table[name='" + eName + "']").bootstrapTable('insertRow',{index:count,row:jsonFiles});
}

function del_row(Obj)
{
	var eName = $(Obj).attr("value-cname");
	var uniqueId =$(Obj).attr("value-data");
	$("table[name='" + eName + "']").bootstrapTable('remove',{
        field: "UNIQUE_ID",
        values: [uniqueId]
	});
}

function getDocumentChildTable(eName)
{
	return $("table[name='" + eName + "']").bootstrapTable("getData");
}

function editRowModal(eName,childModel,field,title,row,defaultValue)
{
	var htmlTemp='<div class="form-group">'+
				'             <label for="sminput">'+title+'</label>';
				if(childModel=="1")
				{
					htmlTemp+='<input type="text" class="form-control" name="row_'+field+'" placeholder="'+title+'" value="'+row[field]+'">';
				}else if(childModel=="2")
				{
						var arr = defaultValue.split(",");
						var html="<option></option>";
						for(var k=0;k<arr.length;k++)
							{
								if(arr[k]==row[field])
								{
									html+="<option value='"+arr[k]+"' selected>"+arr[k]+"</option>";
								}else
								{
									html+="<option value='"+arr[k]+"'>"+arr[k]+"</option>";
								}
							}
						htmlTemp+='<select type="text" class="form-control" name="row_'+field+'" placeholder="'+title+'">'+html+'</select>';
				}else if(childModel=="3")
				{
						var arr = defaultValue.split(",");
						var html="<div id='document_tepm_radio_div'>";
						for(var k=0;k<arr.length;k++)
							{
								if(arr[k]==row[field])
								{
									html+="<input type='radio' name='row_"+field+"' value='"+arr[k]+"' checked/>"+arr[k];
								}else
								{
									html+="<input type='radio' name='row_"+field+"' value='"+arr[k]+"'/>"+arr[k];
								}
							}
						html+="</div>";
						htmlTemp+=html;
				}else if(childModel=="4")
				{
						var arr = defaultValue.split(",");
						var html="<div id='document_tepm_checkbox_div'>";
						var values = row[field].split(",");
						for(var k=0;k<arr.length;k++)
							{
							if($.inArray(arr[k], values)>-1)
							{
								html+="<input type='checkbox' name='row_"+field+"' value='"+arr[k]+"' checked/>"+arr[k];
							}else
							{
								html+="<input type='checkbox' name='row_"+field+"' value='"+arr[k]+"'/>"+arr[k];
							}
							}
						html+="</div>";
						htmlTemp+=html;
				}else if(childModel=="5")
				{
					htmlTemp+='<input type="date" class="form-control" name="row_'+field+'" placeholder="'+title+'" value="'+row[field]+'">';
				}
				htmlTemp+='</div>';
	var addrowModal=['<div class="modal fade bs-example-modal-sm" id="addrowmodal_'+eName+'" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">',
		'        <div class="modal-dialog modal-sm">',
		'            <div class="modal-content">',
		'                <div class="modal-header">',
		'                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>',
		'                    <h4 class="modal-title" id="mySmallModalLabel">编辑单元格</h4>',
		'                </div>',
		'                <div class="modal-body"><form id="form1">'+htmlTemp,
		'                </div></form>',
		'                <div class="modal-footer">',
		'                      <button type="button" class="btn btn-warning" data-dismiss="modal">取消</button>',
		'                      <button type="button" class="btn btn-primary js-save-row">保存</button>',
		'                </div>',
		'            </div><!-- /.modal-content -->',
		'        </div><!-- /.modal-dialog -->',
		'    </div>'].join("");
	$("#rowmodal").html(addrowModal);
	$("#addrowmodal_"+eName).modal("show");
}

function createPrcsMsgRemind(msgId,remindNextUser,remindCreateUser,remindParticipant)
{
	var flag1=false;
	var flag2=false;
	var flag3=false;
	var msgTypeHtml="";
	if(remindNextUser!=null&&remindNextUser!="")
	{
		var eId = "remindNextUser";
		var checkedHtml=" onclick='return false;' checked='checked'";
		var remindNextUser =  $.parseJSON(remindNextUser);
		var html = "<div class='form-group'><label class='col-sm-2 control-label no-padding-right'>提醒下一步人员:</label><div class='col-sm-10'>";
		for(key in remindNextUser)
		{
			
			if(key=="webSms")
			{
				if(remindNextUser[key]=="1")
				{
					flag1=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'><span class='text'>站内消息</span></div>";
				}else if(remindNextUser[key]=="2")
				{
					flag1=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'"+checkedHtml+" ><span class='text'>站内消息</span></div>";
				}
			}
			if(key=="mobileSms")
			{
				if(remindNextUser[key]=="1")
				{
					flag1=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'><span class='text'>手机短信</span></div>";
				}else if(remindNextUser[key]=="2")
				{
					flag1=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'"+checkedHtml+" ><span class='text'>手机短信</span></div>";
				}
			}
			if(key=="appSms")
			{
				if(remindNextUser[key]=="1")
				{
					flag1=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'><span class='text'>APP推送</span></div>";
				}else if(remindNextUser[key]=="2")
				{
					flag1=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'"+checkedHtml+" ><span class='text'>APP推送</span></div>";
				}
			}
			if(key=="webMail")
			{
				if(remindNextUser[key]=="1")
				{
					flag1=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'><span class='text'>外部邮件</span></div>";
				}else if(remindNextUser[key]=="2")
				{
					flag1=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'"+checkedHtml+" ><span class='text'>外部邮件</span></div>";
				}
			}
			if(key=="ddSms")
			{
				if(remindNextUser[key]=="1")
				{
					flag1=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'><span class='text'>钉钉提醒</span></div>";
				}else if(remindNextUser[key]=="2")
				{
					flag1=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'"+checkedHtml+" ><span class='text'>钉钉提醒</span></div>";
				}
			}
			if(key=="wxSms")
			{
				if(remindNextUser[key]=="1")
				{
					flag1=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'><span class='text'>微信提醒</span></div>";
				}else if(remindNextUser[key]=="2")
				{
					flag1=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'"+checkedHtml+" ><span class='text'>微信提醒</span></div>";
				}
			}
		}
		html += "</div></div>";
		if(flag1)
		{
			msgTypeHtml+=html;
		}
	}
	if(remindCreateUser!=null&&remindCreateUser!="")
	{
		var eId = "remindCreateUser";
		var checkedHtml=" onclick='return false;' checked='checked'";
		var remindCreateUser =  $.parseJSON(remindCreateUser);
		var html = "<div class='form-group'><label class='col-sm-2 control-label no-padding-right'>提醒流程发起人:</label><div class='col-sm-10'>";
		for(key in remindCreateUser)
		{
			
			if(key=="webSms")
			{
				if(remindCreateUser[key]=="1")
				{
					flag2=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'><span class='text'>站内消息</span></div>";
				}else if(remindCreateUser[key]=="2")
				{
					flag2=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'"+checkedHtml+" ><span class='text'>站内消息</span></div>";
				}
			}
			if(key=="mobileSms")
			{
				if(remindCreateUser[key]=="1")
				{
					flag2=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'><span class='text'>手机短信</span></div>";
				}else if(remindCreateUser[key]=="2")
				{
					flag2=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'"+checkedHtml+" ><span class='text'>手机短信</span></div>";
				}
			}
			if(key=="appSms")
			{
				if(remindCreateUser[key]=="1")
				{
					flag2=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'><span class='text'>APP推送</span></div>";
				}else if(remindCreateUser[key]=="2")
				{
					flag2=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'"+checkedHtml+" ><span class='text'>APP推送</span></div>";
				}
			}
			if(key=="webMail")
			{
				if(remindCreateUser[key]=="1")
				{
					flag2=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'><span class='text'>外部邮件</span></div>";
				}else if(remindCreateUser[key]=="2")
				{
					flag2=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'"+checkedHtml+" ><span class='text'>外部邮件</span></div>";
				}
			}
			if(key=="ddSms")
			{
				if(remindCreateUser[key]=="1")
				{
					flag2=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'><span class='text'>钉钉提醒</span></div>";
				}else if(remindCreateUser[key]=="2")
				{
					flag2=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'"+checkedHtml+" ><span class='text'>钉钉提醒</span></div>";
				}
			}
			if(key=="wxSms")
			{
				if(remindCreateUser[key]=="1")
				{
					flag2=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'><span class='text'>微信提醒</span></div>";
				}else if(remindCreateUser[key]=="2")
				{
					flag2=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'"+checkedHtml+" ><span class='text'>微信提醒</span></div>";
				}
			}
		}
		html += "</div></div>";
		if(flag2)
		{
			msgTypeHtml+=html;
		}
	}
	if(remindParticipant!=null&&remindParticipant!="")
	{
		var eId = "remindParticipant";
		var checkedHtml=" onclick='return false;' checked='checked'";
		var remindParticipant =  $.parseJSON(remindParticipant);
		var html = "<div class='form-group'><label class='col-sm-2 control-label no-padding-right'>提醒流程参与人:</label><div class='col-sm-10'>";
		for(key in remindParticipant)
		{
			
			if(key=="webSms")
			{
				if(remindParticipant[key]=="1")
				{
					flag3=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'><span class='text'>站内消息</span></div>";
				}else if(remindParticipant[key]=="2")
				{
					flag3=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'"+checkedHtml+" ><span class='text'>站内消息</span></div>";
				}
			}
			if(key=="mobileSms")
			{
				if(remindParticipant[key]=="1")
				{
					flag3=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'><span class='text'>手机短信</span></div>";
				}else if(remindParticipant[key]=="2")
				{
					flag3=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'"+checkedHtml+" ><span class='text'>手机短信</span></div>";
				}
			}
			if(key=="appSms")
			{
				if(remindParticipant[key]=="1")
				{
					flag3=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'><span class='text'>APP推送</span></div>";
				}else if(remindParticipant[key]=="2")
				{
					flag3=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'"+checkedHtml+" ><span class='text'>APP推送</span></div>";
				}
			}
			if(key=="webMail")
			{
				if(remindParticipant[key]=="1")
				{
					flag3=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'><span class='text'>外部邮件</span></div>";
				}else if(remindParticipant[key]=="2")
				{
					flag3=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'"+checkedHtml+" ><span class='text'>外部邮件</span></div>";
				}
			}
			if(key=="ddSms")
			{
				if(remindParticipant[key]=="1")
				{
					flag3=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'><span class='text'>钉钉提醒</span></div>";
				}else if(remindParticipant[key]=="2")
				{
					flag3=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'"+checkedHtml+" ><span class='text'>钉钉提醒</span></div>";
				}
			}
			if(key=="wxSms")
			{
				if(remindParticipant[key]=="1")
				{
					flag3=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'><span class='text'>微信提醒</span></div>";
				}else if(remindParticipant[key]=="2")
				{
					flag3=true;
					html += "<div class='checkbox' style='display: inline-block;margin-top:0px;'><label><input type='checkbox' name='" + eId + "' value='" + key + "'"+checkedHtml+" ><span class='text'>微信提醒</span></div>";
				}
			}
		}
		html += "</div></div>";
		if(flag3)
		{
			msgTypeHtml+=html;
		}
	}
	$("#"+msgId).html(msgTypeHtml);
	if(flag1||flag2||flag3)
	{
		$("#msgdiv").show();
	}
}

var $sigdiv;
function showSealPlate(Obj)
{
	$("#sealModal").modal("show");
	if($sigdiv==undefined)
		{
		$sigdiv = $("#signature").jSignature({'UndoButton':true,'height':'100%','width':'100%'});
		}
	$(".js-seal").unbind("click").click(function(){
		var data = $sigdiv.jSignature('getData', 'image');
		$(Obj).attr("src","data:"+data[0]+","+data[1]);
		$("#sealModal").modal("hide");
		$sigdiv.jSignature('reset');
	})
	$(".js-sealreset").unbind("click").click(function(){
		$sigdiv.jSignature('reset');
	})
	
}