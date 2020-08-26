var setting = {
	async : {
		enable : false,// 设置 zTree 是否开启异步加载模式
		autoParam : [ "bpmSortId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	callback : {
		onClick : zTreeOnClick
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "bpmSortId",
			rootPId : "0"
		},
		key : {
			name : "bpmSortName"
		}
	}
};

$(function() {
	$.ajax({
		url : "/ret/bpmget/getBpmFlowListByPriv",
		type : "post",
		dataType : "json",
		data : {
			bpmSortId : "0"
		},
		success : function(data) {
			$.fn.zTree.init($("#tree"), setting, data);// 初始化树节点时，添加同步获取的数据
		}
	});
	query();
	$(".js-allflow").unbind("click").click(function(){
		$("#mycommonbpmflowlist").hide();
		$("#mybpmflowlist").show();
		getMyAllBpmFlow();
	});
	$(".js-frequently").unbind("click").click(function(){
		getCommonBpmFlowListByAccount();
	});
});
function flowchat(flowId)
{
	//window.location.href = "/app/core/bpm/bpmDesignFlowChart?flowId="+flowId;
	open("/app/core/bpm/bpmDesignFlowChart?flowId="+flowId,"_self");
}
function quickbpm(flowId)
{
	$.ajax({
		url : "/set/bpmset/quickBpm",
		type : "post",
		dataType : "json",
		data : {
			flowId : flowId
		},
		success : function(data) {
			if (data.status == 500) {
				console.log(data.msg);
			} else if (data.status == 500) {
				top.layer.msg(data.msg);
			} else {
				//window.location.href = data.redirect;
				open(data.redirect,"_self");
			}
		}
	});
}


function sendbpm(flowId) {
	$("#sendbpm").modal("show");
	getDocNumByBpmFlow(flowId);
	$("#sendBpmBtn").unbind("click").click(function() {
		if($("#flowTitle").val()==""||$("#flowTitle").val()==null)
		{
			top.layer.msg("BPM标题不能为空！");
			return;
		}
		$.ajax({
			url : "/set/bpmset/startBpm",
			type : "post",
			dataType : "json",
			data : {
				flowId : flowId,
				flowTitle : $("#flowTitle").val(),
				follow : getCheckBoxValue("follow"),
				urgency : $("#urgency").val()
			},
			success : function(data) {
				if (data.status == 500) {
					console.log(data.msg);
				} else if (data.status == 500) {
					top.layer.msg(data.msg);
				} else {
					$("#sendbpm").modal("hide");
					//window.location.href = data.redirect;
					open(data.redirect,"_self");
				}
			}
		});
	});
}

function getCheckBoxValue(name) {
	var returnStr = "";
	$('input[name="' + name + '"]:checked').each(function() {
		returnStr += $(this).val() + ",";
	});
	return returnStr;
}


function getDocNumByBpmFlow(flowId)
{
	$.ajax({
		url : "/ret/bpmget/getDocNumByBpmFlow",
		type : "post",
		dataType : "json",
		data : {
			flowId : flowId
		},
		success : function(data) {
			if (data.status == 500) {
				console.log(data.msg);
			} else if (data.status == 500) {
				top.layer.msg(data.msg);
			} else {
				$("#flowTitle").val(data.redirect)
			}
		}
	});
}

function zTreeOnClick(event, treeId, treeNode) {
	$("#mycommonbpmflowlist").hide();
	$("#mybpmflowlist").show();
	$("#flowSort").val(treeNode.bpmSortId);
	$('#myTable').bootstrapTable('refresh');
	$("#myTable").find('thead').remove();
}
function getMyAllBpmFlow()
{
	$("#flowSort").val("");
	$('#myTable').bootstrapTable('refresh');
	$("#myTable").find('thead').remove();
}

function query()
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/bpmget/getMyBpmFlowList',
	      method: 'post',
	      contentType:'application/x-www-form-urlencoded',
	      striped: true,//隔行换色
	      cache: false,//禁用缓存
	      pagination: true,//启动分页
	      paginationDetailHAlign:'right',
	      sidePagination: 'server',//分页方式
	      pageNumber: 1,//初始化table时显示的页码
	      pageSize: 20,//每页条目
	      showFooter: false,//是否显示列脚
	      showPaginationSwitch: false,//是否显示 数据条数选择框
	      sortable: true,//排序
	      search: false,//启用搜索
	      showColumns: false,//是否显示 内容列下拉框
	      showRefresh: false,//显示刷新按钮
	      idField: 'flowId',//key值栏位
	      clickToSelect: false,//点击选中checkbox
	      pageList : [20, 30, 50],//可选择单页记录数
	      queryParams:queryParams,
	      columns: [
	     {
			formatter: function (value, row, index) {
					return createItem(row);
				}
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
	 $("#myTable").find('thead').remove();
}
function queryParams(params) {
var temp = {
    search: params.search,
    pageSize:this.pageSize,
    pageNumber:this.pageNumber,
    sort: params.sort,
    sortOrder:params.order,
    flowSort:$("#flowSort").val()
};
return temp;
};

function createItem(row)
{
	var html=['<div class="col-lg-12 col-sm-12 col-xs-12" style="height: 40px;">',
		'					<div class="col-lg-4 col-sm-4 col-xs-12" style="line-height: 40px">',
		'						<b style="font-size: 16px">'+row.flowName+'</b>',
		'					</div>',
		'					<div class="col-lg-2 col-sm-2 col-xs-12" style="line-height: 40px">创建时间：'+row.createTime.substring(0,10),
		'					</div>',
		'					<div class="col-lg-3 col-sm-3 col-xs-12" style="line-height: 40px" align="center">',
		'							<a href="javascript:void(0);" onclick="flowchat(\''+row.flowId+'\');" class="btn btn-darkorange">设计流程图</a>',
		'							<a href="javascript:void(0);" onclick="template(\''+row.formId+'\');" class="btn btn-magenta">表单模版</a>',
		'							<a href="javascript:void(0);" onclick="showFunction(\''+row.flowId+'\');" class="btn btn-azure">BPM说明</a>',
		'					</div>',
		'					<div class="col-lg-3 col-sm-3 col-xs-12" style="line-height: 40px;" align="right">',
		'						<a href="javascript:void(0);"  onclick="quickbpm(\''+row.flowId+'\');" class="btn btn-maroon">快速创建</a>',
		'						<a href="javascript:void(0);" onclick="sendbpm(\''+row.flowId+'\');" class="btn btn-darkorange">向导新建</a>',
		'					</div>',
		'				</div>'].join("");
	return html;
}

function getCommonBpmFlowListByAccount()
{
	$.ajax({
		url : "/ret/bpmget/getCommonBpmFlowListByAccount",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == 500) {
				console.log(data.msg);
			} else if (data.status == 500) {
				top.layer.msg(data.msg);
			} else {
				var list = data.list;
				if(list.length>0)
				{
					$("#mybpmflowlist").hide();
					$("#mycommonbpmflowlist").show();
					var html="";
					for(var i=0;i<list.length;i++)
					{
						html+="<tr><td>"+createItem(list[i])+"</td></tr>";
					}
					$(".js-commonbpmflow").html(html);
				}
			}
		}
	});
}


function showFunction(flowId)
{
	$("#flowFunction").html("");
	$("#bpmfunction").modal("show");
	$.ajax({
		url : "/ret/bpmget/getBpmFlow",
		type : "post",
		dataType : "json",
		data : {
			flowId : flowId
		},
		success : function(data) {
			if (data.status == 500) {
				console.log(data.msg);
			} else if (data.status == 500) {
				top.layer.msg(data.msg);
			} else {
				$("#flowFunction").html(data.list.remark);
			}
		}
	});
}

function template(formId)
{
window.open("/app/core/bpm/bpmFormTemplate?formId="+formId);
}