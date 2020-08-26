$(function(){
	getBpmFlowInfo();
	jeDate("#createTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	
	$(".js-query").unbind("click").click(function(){
		query();
	})
	$(".js-html-bi").unbind("click").click(function(){
		queryHtmlBi();
	})
	$(".js-excel-bi").unbind("click").click(function(){
		queryExcellBi();
	})
	
	
})
function getBpmFlowInfo()
{
	$.ajax({
		url : '/ret/bpmget/getBpmFlow',
		type : "post",
		dataType : "json",
		data:{flowId:flowId},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				$("#flowName").html(data.list.flowName);
				getBpmFormFieldByFormId(data.list.formId);
			}
		}
	})
}


function getBpmFormFieldByFormId(formId)
{
	$.ajax({
		url : '/ret/bpmget/getBpmFormFieldByFormId',
		type : "post",
		dataType : "json",
		data:{formId:formId},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				var list = data.list;
				var h="";
				for(var j=0;j<list.length;j++)
				{
					if(list[j].xType!='xhtml'&&list[j].xType!='xupload'&&list[j].xType!='xbpm'&&list[j].xType!='xlist'&&list[j].xType!='xiframe'&&list[j].xType!='xsql')
					{
						h+="<div class=\"col-sm-12 col-lg-12 col-xs-12 js-opt\" data-value=\""+list[j].name+"\">\n"+
						   "<div class=\"col-sm-3 col-lg-3 col-xs-12\">"+list[j].title+":\n</div><div class=\"col-sm-2 col-lg-2 col-xs-12\">"+
						   "<select class=\"form-control\" id=\"where_"+list[j].name+"\" style=\"margin-left:20px;border-radius: 0px;\">\n"+
						   		"<option value=\"0\">请选择</option>\n"+
	               				"<option value=\"1\">等于</option>\n"+
	                            "<option value=\"2\">大于</option>\n"+
				                "<option value=\"3\">小于</option>\n"+
				                "<option value=\"4\">大于等于</option>\n"+
				                "<option value=\"5\">小于等于</option>\n"+
				                "<option value=\"6\">不等于</option>\n"+
				                "<option value=\"7\">开始为</option>\n"+
				                "<option value=\"8\">包含</option>\n"+
				                "<option value=\"9\">结束为</option>\n"+
				                "<option value=\"10\">为空</option>\n"+
							"</select>\n"+
							"</div>\n"+
							"<div class=\"col-sm-3 col-lg-3 col-xs-12\">\n"+
								"<input type=\"text\" class=\"form-control\" id=\""+list[j].name+"\" name=\""+list[j].name+"\" placeholder=\""+list[j].title+"\">\n"+
							"</div>\n"+
						"</div>\n";
					}
				}
				$("#whereDiv").html(h);
				var html="<li class=\"item\" data-id=\"id\">流水号</li><li class=\"item\" data-id=\"runId\">唯一标识</li><li class=\"item\" data-id=\"status\">流程状态</li><li class=\"item\" data-id=\"createTime\">开始时间</li><li class=\"item\" data-id=\"endTime\">结束时间</li>";
				for(var i=0;i<list.length;i++)
				{
					html+="<li class=\"item\" data-id=\""+list[i].name+"\">"+list[i].title+"</li>";
				}
				$(".left-box").html(html);	
				$('#selectTitle').initList({
					openDrag: true,
					openDblClick: true
				});
			}
		}
	})
}

function query()
{
	$("#tablelist").show();
	$("#myTable").bootstrapTable("refresh");
	queryList();
}

function queryHtmlBi()
{
	var rf= getResFields();
	if(rf.length==0)
	{
		top.layer.msg("请先选择统计报表的字段项!");
		return;
	}
	var resFields = JSON.stringify(rf);
	var whereLists = JSON.stringify(getWhereList());
	$("#form2").attr("action","/ret/bpmget/queryHtmlBi");
	$(".js-queryType").val("html");
	$(".js-flowTitle").val($("#flowTitle").val());
	$(".js-createTime").val($("#createTime").val());
	$(".js-endTime").val($("#endTime").val());
	$(".js-queryRange").val($("#queryRange").val());
	$(".js-status").val($("#status").val());
	$(".js-publicAttach").val($("#publicAttach").val());
	$(".js-whereListMapJson").val(whereLists);
	$(".js-resFieldsJson").val(resFields);
	$("#form2").submit();
}

function queryExcellBi()
{
	var rf= getResFields();
	if(rf.length==0)
	{
		top.layer.msg("请先选择导出报表的字段项!");
		return;
	}
	var resFields = JSON.stringify(rf);
	var whereLists = JSON.stringify(getWhereList());
	$("#form2").attr("action","/ret/bpmget/queryExcel");
	$(".js-queryType").val("html");
	$(".js-flowTitle").val($("#flowTitle").val());
	$(".js-createTime").val($("#createTime").val());
	$(".js-endTime").val($("#endTime").val());
	$(".js-queryRange").val($("#queryRange").val());
	$(".js-status").val($("#status").val());
	$(".js-publicAttach").val($("#publicAttach").val());
	$(".js-whereListMapJson").val(whereLists);
	$(".js-resFieldsJson").val(resFields);
	$("#form2").submit();
}


function getResFields()
{
	var arr=[];
	$(".right-box").find("li").each(function(){
		var json ={};
		json.fieldName=$(this).attr("data-id");
		json.title=$(this).html();
		arr.push(json);
	});
return arr;
}

function getWhereList()
{
	var optArr=[];
	$(".js-opt").each(function(){
		var field=$(this).attr("data-value");
		var opt = $("#where_"+field).val();
		var value = $("#"+field).val();
		if(opt!="0")
			{
			 var json={};
			 json.field = field;
			 json.opt = opt;
			 json.value = value;
			 optArr.push(json);
			}
	})
	return optArr;
}

function queryList()
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/bpmget/querybpmlist',
	      method: 'post',
	      contentType:'application/x-www-form-urlencoded',
	      toolbar: '#toobar',//工具列
	      striped: true,//隔行换色
	      cache: false,//禁用缓存
	      pagination: true,//启动分页
	      sidePagination: 'server',//分页方式
	      pageNumber: 1,//初始化table时显示的页码
	      pageSize: 10,//每页条目
	      showFooter: false,//是否显示列脚
	      showPaginationSwitch: true,//是否显示 数据条数选择框
	      sortable: true,//排序
	      search: true,//启用搜索
	      showColumns: true,//是否显示 内容列下拉框
	      showRefresh: true,//显示刷新按钮
	      idField: 'runId',//key值栏位
	      clickToSelect: true,//点击选中checkbox
	      pageList : [10, 20, 30, 50],//可选择单页记录数
	      queryParams:queryParams,
	      columns: [ {
	      checkbox: true
	      },
	     {
	    	field: 'num',
			title: '序号',//标题  可不加
			width:'50px',
			formatter: function (value, row, index) {
					return index+1;
				}
	      },
	      {
	       field: 'flowTitle',
	       title: 'BPM流程标题',
	       sortable : true,
	       width:'200px'
	      },
	      {
			field: 'urgency',
			   width:'50px',
			   title: '紧急程序',
			   formatter:function(value,row,index){
					if(value=="0")
    		   		{
    		   		return "一般";
    		   		}else if(value=="1")
    		   			{
    		   			return "紧急";
    		   			}else if(value=="2")
    		   				{
    		   				return "加急";
    		   				}	
	            }
			},
			{
		       field: 'delFlag',
		       title: '是否删除',
		       width:'50px',
		       formatter:function(value,row,index){
	    	   if(value=="1")
	    		   {
	    		   		return "已删除";
	    		   }else
	    			{
	    			   return "未删除";   
	    			}
	    				  
            }
	      },
	      {
	    	  field: 'opUserStr',
	    	  width:'100px',
	    	  align : 'center',
	    	  title: '参与人员',
	    	  formatter:function(value, row, index)
			     {
	    		  return getUserNameByStr(value);
			     }
	      },
	      {
		       field: 'createUser',
		       width:'100px',
		       title: '创建人'
		   },
		   {
		       field: 'createTime',
		       width:'100px',
		       title: '创建时间'
		   },
	      {
	       field: 'opt',
	       title: '操作',
	       width:'200px',
	       align:'center',
    	   formatter:function(value,row,index){
                return createOptBtn(row.runId);
            }
	      }],
	      onClickCell: function (field, value, row, $element) {
	      //alert(row.SystemDesc);
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
function queryParams(params) {
	var rf= getResFields();
	var resFields = JSON.stringify(rf);
	var whereLists = JSON.stringify(getWhereList());
var temp = {
    search: params.search,
    pageSize:this.pageSize,
    pageNumber:this.pageNumber,
    sort: params.sort,
    sortOrder:params.order,
    flowId:flowId,
    queryType:"thml",
    flowTitle:$("#flowTitle").val(),
    createTime:$("#createTime").val(),
    endTime:$("#endTime").val(),
    queryRange:$("#queryRange").val(),
    status:$("#status").val(),
    publicAttach:$("#publicAttach").val(),
    whereListMapJson:whereLists,
    resFieldsJson:resFields
};
return temp;
};
function createOptBtn(runId)
{
var html="<a href=\"javascript:void(0);readbpm('"+runId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>";
return html;
}
