var tableHead;
$(function(){
	getBpmBiTemplateById();
	query();
	$(".js-query-but").unbind("click").click(function(){
		$("#myTable").bootstrapTable('refresh');
	})
});

function query()
{
	var columns=[];
	var jsonStart={};
	jsonStart.field = 'num';
	jsonStart.title ='序号';
	jsonStart.width ='50px';
	jsonStart.formatter =function (value, row, index) {
		return index+1;
	}
	columns.push(jsonStart);
	var jsonArr = JSON.parse(tableHead);
	for(var i=0;i<jsonArr.length;i++ )
	{
		var tempJson=jsonArr[i];
		var json={};
		json.field = tempJson.field;
		json.title =tempJson.fieldTitle;
		json.width ='100px';
		json.align='center';
		columns.push(json);
      }
	var jsonEnd={};
	jsonEnd.field = 'opt';
	jsonEnd.title ='操作';
	jsonEnd.width ='100px';
	jsonEnd.align='center';
	jsonEnd.formatter =function (value, row, index) {
		return createOptBtn(row.runId);
	}
	columns.push(jsonEnd);
	$("#myTable").bootstrapTable({
	      url: '/ret/bpmget/getBiFormDataListByBpmBiTemplate',
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
	      showPaginationSwitch: false,//是否显示 数据条数选择框
	      sortable: true,//排序
	      search: false,//启用搜索
	      showColumns: false,//是否显示 内容列下拉框
	      showRefresh: false,//显示刷新按钮
	      idField: 'runId',//key值栏位
	      clickToSelect: true,//点击选中checkbox
	      pageList : [10, 20, 30, 50],//可选择单页记录数
	      queryParams:queryParams,
	      columns: columns,
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
	var conditionJson = [];
	$(".js-queryselectfield").each(function(){
		var json={};
		if($(this).val()!="")
		{
			json.field=$(this).attr("id");
			json.value=$(this).val();
			conditionJson.push(json);
		}
	})
var temp = {
  search: params.search,
  pageSize:this.pageSize,
  pageNumber:this.pageNumber,
  sort: params.sort,
  sortOrder:params.order,
  templateId:templateId,
  conditionJson:JSON.stringify(conditionJson)
};
return temp;
};

function createOptBtn(runId)
{
	var html="<a href=\"javascript:void(0);readBpm('"+runId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	return html;
}

function readBpm(runId)
{
	open("/app/core/bpm/bpmread?runId=" + runId,"_self");
}

function getBpmBiTemplateById()
{
	$.ajax({
		url : "/ret/bpmget/getBpmBiTemplateById",
		type : "post",
		dataType : "json",
		async : false,
		data:{templateId:templateId},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				var recordInfo = data.list;
				for(var id in recordInfo)
				{
					if(id=="queryFields")
					{
						var queryFields = $.parseJSON(recordInfo.queryFields);
						for(var i=0;i<queryFields.length;i++)
							{
								var html="<div class=\"form-group\">"+
									"<label class=\"col-sm-1 control-label no-padding-right\">"+queryFields[i].fieldTitle+"：</label>"+
									"<div class=\"col-sm-5\">"+
										"<input type=\"text\" class=\"form-control js-queryselectfield\" id=\""+queryFields[i].field+"\" name=\""+queryFields[i].field+"\" placeholder=\""+queryFields[i].fieldTitle+"\" >"+
									"</div>"+
								"</div>";
								$("#queryfield").append(html);
							}
					}else if(id=="exportFlag")
					{
						if(recordInfo[id]=="1")
						{
							$(".js-exportFlag").show();
							$(".js-exportFlag").unbind("click").click(function(){
								getBiDataFile();
							})
						}
					}else if(id=="tableHead")
					{
						tableHead = recordInfo[id];
					}
						
				}
				}
		}
	})
}

function getBiDataFile()
{
	var conditionJson = [];
	$(".js-queryselectfield").each(function(){
		var json={};
		if($(this).val()!="")
		{
			json.field=$(this).attr("id");
			json.value=$(this).val();
			conditionJson.push(json);
		}
	})
	$(".js-conditionJson").val(JSON.stringify(conditionJson));
	$("#form2").attr("action","/ret/bpmget/getBiFormDataToFile");
	$("#form2").submit();
}