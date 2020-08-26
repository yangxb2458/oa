$(function(){
	query();
	jeDate("#beginTime", {
		format: "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format: "YYYY-MM-DD"
	});
	$(".js-btn").unbind("click").click(function(){
		$("#accountId").val("");
		$("#accountId").attr("data-value","");
		$("#content").val("");
		$("#sendWebSmsModal").modal("show");
		$(".js-send").unbind("click").click(function(){
			sendSms()
		})
	});
	$(".js-query-but").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	})
})

function query()
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/oaget/getMySms',
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
	      idField: 'smsId',//key值栏位
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
	       field: 'userName',
	       title: '发送方',
	       width:'50px'
	      },
	      {
		       field: 'smsTo',
		       title: '接收方',
		       width:'50px',
		       formatter: function (value, row, index) {
		    	   return getUserNameByStr(value);
		       }
		      },
	      {
			field: 'smsSendTime',
			width:'50px',
			sortable : true,
			title: '发送时间'
		},
		{
			field: 'module',
			width:'50px',
			title: '消息类型',
		   	formatter: function (value, row, index) {
		   			return getSmsType(value);
		   			}
			},
			{
		       field: 'smsContent',
		       title: '消息内容',
		       width:'300px'
	      },
	      {
	       field: 'opt',
	       title: '操作',
	       align:'center',
	       width:'100px',
    	   formatter:function(value,row,index){
                return createOptBtn(row);
            }
	      }],
	      onClickCell: function (field, value, row, $element) {
	      //alert(row.SystemDesc);
	    },
	    responseHandler:function(res){
	    	if(res.status=="500")
	    		{
	    		console.log(res.msg);
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
    endTime:$("#endTime").val()
};
return temp;
};
function createOptBtn(row)
{
var html="<a href=\"javascript:void(0);details('"+row.smsUrl+"','"+row.smsId+"');\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;<a href=\"javascript:void(0);del('"+row.smsId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
return html;
}

function details(url,smsId)
{
	$.ajax({
		url : "/set/oaset/updateSms",
		type : "post",
		dataType : "json",
		data:{smsId:smsId,smsStatus:'1'},
		success : function(data) {
			if (data.status == 200) {
				window.open(url)
			} else if (data.status == 100) {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	})
}
function del(smsId)
{
	$.ajax({
		url : "/set/oaset/delSms",
		type : "post",
		dataType : "json",
		data:{
			smsId:smsId
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
				window.location.reload();
				top.layer.msg(data.msg);
				}
		}
	})
}


function sendSms()
{
	if($("#content").val()=="")
	{
		top.layer.msg("消息内容不能为空！");
	}else
	{
		if($("#accountId").attr("data-value")==""||$("#accountId").attr("data-value")==null||$("#accountId").attr("data-value")==undefined)
		{
			top.layer.msg("消息接收人员不能为空！");
		}else
		{
			$.ajax({
				url : "/set/oaset/sendSms",
				type : "post",
				dataType : "json",
				data:{
					toUser:$("#accountId").attr("data-value"),
					content:$("#content").val()
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
						window.location.reload();
						top.layer.msg(data.msg);
						}
				}
			})
		}
	}
}