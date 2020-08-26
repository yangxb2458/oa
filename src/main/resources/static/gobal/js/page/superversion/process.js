$(function(){
	query();
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#finishTime", {
		format: "YYYY-MM-DD",
		maxDate:getSysTime()
	});
	getsuperversiontype()
	$(".js-query-but").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	});
	getsuperversiontype();
})

function getsuperversiontype()
{
	$.ajax({
		url : "/ret/superversionget/getAllSuperversionConfigList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				var html="<option value=''>请选择</option>";
					for(var i=0;i<data.list.length;i++)
						{
							html+="<option data-value='"+data.list[i].leadId+"' value='"+data.list[i].configId+"'>"+data.list[i].typeName+"</option>"
						}
					$("#type").html(html);
				}
		}
	})
}
function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/superversionget/getSupperversionPorcessList',
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
		      sortOrder:"desc",
		      search: true,//启用搜索
		      showColumns: true,//是否显示 内容列下拉框
		      showRefresh: true,//显示刷新按钮
		      idField: 'superversionId',//key值栏位
		      clickToSelect: true,//点击选中checkbox
		      pageList : [10, 20, 30, 50],//可选择单页记录数
		      queryParams:queryParams,
		      columns: [ 
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
		       title: '督查督办标题',
		       width:'150px'
		      },
		      {
				field: 'typeName',
				   width:'50px',
				   title: '事件类型'
				},
				{
				       field: 'leadUserName',
				       title: '督查领导',
				       width:'50px' 
			      },
				{
			       field: 'handedUserName',
			       title: '牵头人',
			       width:'50px' 
		      },
		      {
			       field: 'beginTime',
			       width:'50px',
			       sortable : true,
			       title: '开始时间'
			   },
			   {
			       field: 'endTime',
			       width:'50px',
			       sortable : true,
			       title: '结束时间'
			   },
			   {
			       field: 'joinUser',
			       width:'100px',
			       title: '参与人',
			       formatter:function(value,row,index){
		                return getUserNameByStr(value);
		            }
			   },
			   {
			       field: 'createTime',
			       width:'100px',
			       title: '创建时间'
			   },
			   {
			       field: 'prcsCount',
			       width:'50px',
			       title: '已处理次数',
			    	   formatter:function(value,row,index){
			                return "<a style='color:#65b951;cursor:pointer;'>已处理<span style='color:red'>"+value+"</span>次</a>"
			            }
			   },
			   {
			       field: 'status',
			       width:'30px',
			       title: '状态',
			       formatter:function(value,row,index){
		                if(value=="0")
		                	{
		                		return "<a href='javascript:void(0);' class='btn btn-info shiny btn-xs'>进行中</a>";
		                	}else if(value=="1")
		                	{
		                		return "<a href='javascript:void(0);' class='btn btn-warning shiny btn-xs'>延期中</a>";
		                	}else if(value=="2")
		                	{
		                		return "<a href='javascript:void(0);' class='btn btn-success shiny btn-xs'>已完成</a>";
		                	}
		            }
			    	
			   },
		      {
		       field: 'opt',
		       width:'100px',
		       align:'center',
		       title: '操作',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.superversionId,row.status);
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
        sortOrder:params.order,
        type:$("#type").val(),
        beginTime:$("#beginTime").val(),
        endTime:$("#endTime").val(),
        status:$("#status").val(),
        handedUser:$("#handedUser").attr("data-value")
    };
    return temp;
};
function createOptBtn(superversionId,status)
{
	var html="<a href=\"javascript:void(0);readdetails('"+superversionId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>";
	if(status=="0")
	{
		html+="&nbsp;&nbsp;<a href=\"javascript:void(0);dowork('"+superversionId+"')\" class=\"btn btn-magenta btn-xs\" >处理</a>"
	}
	return html;
}
function dowork(superversionId)
{
	document.getElementById("form").reset();
	$("#superversionattach").attr("data_value");
	$("#dowordmodal").modal("show");
	$(".js-save").unbind("click").click(function(){
		addProcess(superversionId);
	})
}

function addProcess(superversionId)
{
	$.ajax({
		url : "/set/superversionset/insertSuperversionProcess",
		type : "post",
		dataType : "json",
		data:{
			superversionId:superversionId,
			content:$("#content").val(),
			attach:$("#superversionattach").attr("data_value"),
			status:$("#status").val(),
			prcsValue:$("#prcsValue").val(),
			finishTime:$("#finishTime").val()
			},
		success : function(data) {
			if(data.status=="500")
			{
				console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				top.layer.msg(data.msg);
				$("#dowordmodal").modal("hide");
				}
		}
	})
}


function getsuperversiontype()
{
	$.ajax({
		url : "/ret/superversionget/getAllSuperversionConfigList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				var html="<option value=''>请选择</option>";
					for(var i=0;i<data.list.length;i++)
						{
							html+="<option data-value='"+data.list[i].leadId+"' value='"+data.list[i].configId+"'>"+data.list[i].typeName+"</option>"
						}
					$("#type").html(html);
				}
		}
	})
}

function readdetails(supervresionId)
{
	window.open("/app/core/superversion/superversiondetails?superversionId="+supervresionId);
}