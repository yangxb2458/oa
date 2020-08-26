$(function(){
	query();
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	$(".js-query-but").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	});
	 getSuperversionType();
})
function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/superversionget/getDelayApplyList',
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
		      idField: 'delayId',//key值栏位
		      clickToSelect: false,//点击选中checkbox
		      pageList : [10, 20, 30, 50],//可选择单页记录数
		      queryParams:queryParams,
		      columns: [{
		    	field: 'num',
				title: '序号',//标题  可不加
				width:'20px',
				formatter: function (value, row, index) {
						return index+1;
					}
		      },
		      {
		       field: 'title',
		       title: '督查事件名称',
		       sortable : true,
		       width:'100px',
		       formatter:function(value,row,index){
   		   		return "<a href='#' onclick='readdetails(\""+row.superversionId+"\")'>"+value+"</a>";
		       	}
		      },
		      {
				field: 'typeName',
				   width:'50px',
				   title: '事件类型'
				},
				{
			       field: 'beginTime',
			       title: '开始时间',
			       width:'50px'
		      },
		      {
			       field: 'endTime',
			       title: '完成时间',
			       width:'50px'
		      },
		      {
			       field: 'delayTime',
			       title: '延期时间',
			       width:'50px'
		      },
		      {
			       field: 'createUserName',
			       width:'50px',
			       title: '申请人'
			   },
			   {
			       field: 'createTime',
			       width:'100px',
			       title: '申请时间'
			   },
			   {
			       field: 'passStatus',
			       width:'50px',
			       title: '审批状态',
			       formatter:function(value,row,index){
			    	   if(value=="0")
			    		{
			    		   return "<a href=\"javascript:void(0);\" class=\"btn btn-palegreen btn-xs\">审批中</a>";
			    		}else if(value=="1")
			    		{
			    			return "<a href=\"javascript:void(0);\" class=\"btn btn-success btn-xs\">通过</a>";
			    		}else if(value=="2")
			    		{
			    			return "<a href=\"javascript:void(0);\" class=\"btn btn-danger btn-xs\">未通过</a>";
			    		}
		            }
			   },
		      {
		       field: 'opt',
		       width:'50px',
		       align:'center',
		       title: '操作',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.delayId,row.passStatus);
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
        createUser:$("#createUser").attr("data-value"),
        beginTime:$("#beginTime").val(),
        endTime:$("#endTime").val(),
        status:$("#status").val()
    };
    return temp;
};
function createOptBtn(delayId,passStatus)
{
	var html="<a href=\"javascript:void(0);details('"+delayId+"')\" class=\"btn btn-magenta btn-xs\">审批详情</a>";
	if(passStatus=="0")
	{
		html="<a href=\"javascript:void(0);apply('"+delayId+"')\" class=\"btn btn-sky btn-xs\">审批</a>";
	}
	return html;
}
function readdetails(superversionId)
{
	window.open("/app/core/superversion/superversiondetails?superversionId="+superversionId);
}
function getSuperversionType()
{
	$.ajax({
		url : "/ret/superversionget/getMySuperversionConfigList",
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
				var html="<option value=''>全部</option>"
					for(var i=0;i<data.list.length;i++)
					{
						html+="<option value='"+data.list[i].configId+"'>"+data.list[i].typeName+"</option>"
					}
				$("#type").html(html);
				}
		}
	})
}

function details(delayId)
{
	window.open("/app/core/superversion/applydetails?delayId="+delayId);
}
function apply(delayId)
{
	$("#applymodal").modal("show");
	$.ajax({
		url : "/ret/superversionget/getSuperversionDelayById",
		type : "post",
		dataType : "json",
		data:{delayId:delayId},
		success : function(data) {
			if(data.status=="200")
			{
				$("#content").html(data.list.content);
				$("#createTime").html(data.list.createTime);
				$("#delayTime").html(data.list.delayTime);
				$("#createUserName").html(getUserNameByStr(data.list.createUser));
				$("#attach").attr("data_value",data.list.attach);
				createAttach("attach","1");
				$(".js-notpass").unbind("click").click(function(){
					doApply(delayId,"2");
				})
				$(".js-pass").unbind("click").click(function(){
					doApply(delayId,"1");
				})
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
function doApply(delayId,passStatus)
{
	$.ajax({
		url : "/set/superversionset/updateSuperversionDelay",
		type : "post",
		dataType : "json",
		data:{delayId:delayId,passStatus:passStatus,ideaText:$("#ideaText").val()},
		success : function(data) {
		if(data.status=="200")
		{
			top.layer.msg(data.msg);
			$("#applymodal").modal("hide");
			$("#myTable").bootstrapTable("refresh");
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