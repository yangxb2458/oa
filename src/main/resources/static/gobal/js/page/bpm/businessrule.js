$(function(){
	$(".js-create-but").unbind("click").click(function(){
		$("#setbusiness").modal("show");
		$(".js-save").unbind("click").click(function(){
			 addBpmBusiness();
		})
	})
	$("#flowId").unbind("change").change(function(){
		$("#processId").val("");
		getPrcsList();
	})
	getAllBpmFlowListByManage();
	getDbSource();
	query();
})
function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/bpmget/getBpmBusinessList',
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
		      sortOrder: "desc",
		      search: true,//启用搜索
		      showColumns: true,//是否显示 内容列下拉框
		      showRefresh: true,//显示刷新按钮
		      idField: 'businessId',//key值栏位
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
		       field: 'title',
		       title: '业务标题',
		       sortable : true,
		       width:'200px'
		      },
		      {
			       field: 'flowName',
			       title: '相关BPM流程',
			       width:'100px'
			  },{
			       field: 'prcsName',
			       title: '执行步骤',
			       width:'100px'
			  },
			  {
			       field: 'dbSourceName',
			       title: '数据源',
			       sortable : true,
			       width:'100px',
			       formatter:function(value,row,index){
			    	   if(value==""||value==null)
			    		{
			    		   return "当前系统";
			    		}else
			    		{
			    			return value;
			    		}
		            }
			  },
		      {
			       field: 'status',
			       width:'100px',
			       title: '当前状态',
			       formatter:function(value,row,index){
			    	   if(value=="0")
			    		{
			    		   return "已停用";
			    		}else if(value=="1")
			    		{
			    			return "已启用";
			    		}else
			    		{
			    			return "未知";
			    		}
			       }
			   },{
			       field: 'createTime',
			       width:'100px',
			       title: '创建时间'
			   },
		      {
			       field: 'createUserName',
			       width:'100px',
			       title: '创建人'
			   },
		      {
		       field: 'opt',
		       title: '操作',
		       align:'center',
		       width:'150px',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.businessId);
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
    var temp = {
        search: params.search,
        pageSize:this.pageSize,
        pageNumber:this.pageNumber,
        sort: params.sort,
        sortOrder:params.order
    };
    return temp;
};
function createOptBtn(businessId)
{
	var html="<a href=\"javascript:void(0);read('"+businessId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);edit('"+businessId+"')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);del('"+businessId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
}

function read(businessId)
{
	window.open("/app/core/bpm/businessdetails?businessId="+businessId);
}

function edit(businessId)
{
	$("#setbusiness").modal("show");
	document.getElementById("form1").reset();
	$.ajax({
		url : "/ret/bpmget/getBpmBusinessById",
		type : "post",
		dataType : "json",
		data:{businessId:businessId},
		success : function(data) {
			if(data.status=="200")
			{
				var recordInfo = data.list;
				for(var id in recordInfo)
				{
					if(id=="flowId")
					{
						$("#"+id).val(recordInfo[id]);
						getPrcsList();
						$("#processId").val(recordInfo.processId);
					}else if(id=="status")
					{
						$("input:radio[name='status'][value='"+recordInfo[id]+"']").attr("checked","checked");
					}else
					{
						if(id!="processId")
						{
							$("#"+id).val(recordInfo[id]);
						}
					}
				}
				$(".js-save").unbind("click").click(function(){
					updateBpmBusiness(businessId);
				})
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
		})
}


function updateBpmBusiness(businessId)
{
	if($("#flowId").val()=="")
	{
		top.layer.msg("请选择指定的流程！")
		return;
	}
	if($("#processId").val()=="")
	{
		top.layer.msg("请选择指定的执行步骤！")
		return;
	}if($("#sqlStr").val()=="")
	{
		top.layer.msg("请编写执行SQL语句！")
		return;
	}
	$.ajax({
		url : "/set/bpmset/updateBpmBusiness",
		type : "post",
		dataType : "json",
		data:{
			businessId:businessId,
			sortNo:$("#sortNo").val(),
			title:$("#title").val(),
			flowId:$("#flowId").val(),
			dbSourceId:$("#dbSourceId").val(),
			sqlStr:$("#sqlStr").val(),
			processId:$("#processId").val(),
			status:$("input:radio[name='status']:checked").val(),
			remark:$("#remark").val()
			},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				$("#setbusiness").modal("hide");
				$("#myTable").bootstrapTable("refresh");
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

function del(businessId)
{
	if(confirm("确定删除当前业务引擎吗？"))
    {
$.ajax({
	url : "/set/bpmset/deleteBpmBusiness",
	type : "post",
	dataType : "json",
	data:{businessId:businessId},
	success : function(data) {
		if(data.status=="200")
		{
			top.layer.msg(data.msg);
			$("#myTable").bootstrapTable("refresh");
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
}

function getPrcsList()
{
	var flowId = $("#flowId").val();
	if(flowId!="")
	{
	$.ajax({
		url : "/ret/bpmget/getBpmProcess",
		dataType : "json",
		type : "post",
		async : false,
		data : {
			flowId : flowId
		},
		error : function(e) {
			console.log(e);
		},
		success : function(data) {
			if (data.status == "500") {
				console.log(data.msg);
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				var html="";
				for(var i=0;i<data.list.length;i++)
				{
					if(data.list[i].prcsType!="2")
					{
						html+="<option value='"+data.list[i].processId+"'>"+data.list[i].prcsName+"</option>"
					}
				}
				$("#processId").html(html);
			}
		}
	});
	}
}

function getAllBpmFlowListByManage()
{
	$.ajax({
		url : "/ret/bpmget/getAllBpmFlowListByManage",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
				{
					var html = "<option value=''>全部</option>"
					for(var i=0;i<data.list.length;i++)
						{
						
							html+="<option value='"+data.list[i].flowId+"'>"+data.list[i].flowName+"</option>"
						}
				$("#flowId").html(html);
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

function addBpmBusiness()
{
	if($("#flowId").val()=="")
	{
		top.layer.msg("请选择指定的流程！")
		return;
	}
	if($("#processId").val()=="")
	{
		top.layer.msg("请选择指定的执行步骤！")
		return;
	}if($("#sqlStr").val()=="")
	{
		top.layer.msg("请编写执行SQL语句！")
		return;
	}
	$.ajax({
		url : "/set/bpmset/insertBpmBusiness",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			title:$("#title").val(),
			flowId:$("#flowId").val(),
			dbSourceId:$("#dbSourceId").val(),
			sqlStr:$("#sqlStr").val(),
			processId:$("#processId").val(),
			status:$("input:radio[name='status']:checked").val(),
			remark:$("#remark").val()
			},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				$("#setbusiness").modal("hide");
				$("#myTable").bootstrapTable("refresh");
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

function getDbSource()
{
	$.ajax({
		url:"/ret/sysget/getDbSourceList",
		type:"POST",
		dataType:"json",
		success:function(data){
			if(data.status=="200")
				{
				var html="<option value=''>系统内部数据</option>";
				for(var i=0;i<data.list.length;i++)
					{
					html+="<option value='"+data.list[i].dbSourceId+"'>"+data.list[i].dbSourceName+"</option>";
					}
				$("#dbSourceId").html(html);
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