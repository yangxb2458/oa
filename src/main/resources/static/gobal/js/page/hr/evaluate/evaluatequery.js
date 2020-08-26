$(function(){
	$(".js-simple-query").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	})
	jeDate("#beginTime", {
		format : "YYYY-MM-DD"
	});
	jeDate("#endTime", {
		format : "YYYY-MM-DD"
	});
	query();
});

function query()
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/hrget/getHrEvaluateQueryList',
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
	      sortable: false,//排序
	      search: true,//启用搜索
	      showColumns: true,//是否显示 内容列下拉框
	      showRefresh: true,//显示刷新按钮
	      idField: 'recordId',//key值栏位
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
	       title: '人员姓名',
	       sortable : true,
	       width:'80px'
	      },
	      {
		       field: 'attitudeLevel',
		       title: '工作态度',
		       width:'150px',
		       formatter:function(value,row,index){
	                return value+"分"
	            }
		      },
	      {
	       field: 'learnLevel',
	       width:'150px',
	       title: '学习能力',
	       formatter:function(value,row,index){
               return value+"分"
           }
	     },
		  {
			field: 'skillLevel',
			width:'150px',
			title: '工作持能',
			formatter:function(value,row,index){
                return value+"分"
            }
		 },
	      {
	       field: 'opt',
	       title: '操作',
	       align:'center',
	       width:'120px',
    	   formatter:function(value,row,index){
                return createOptBtn(row.recordId);
            }
	      }],
	      onClickCell: function (field, value, row, $element) {
	      //alert(row.SystemDesc);
	    },
	    responseHandler:function(res){
	    	if(res.status=="500")
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


function createOptBtn(recordId)
{
	var html="<a href=\"javascript:void(0);details('"+recordId+"')\" class=\"btn btn-primary btn-xs\">详情</a>";
	return html;
	}
function queryParams(params) {
    var temp = {
        search: params.search,
        pageSize:this.pageSize,
        pageNumber:this.pageNumber,
        sort: params.sort,
        sortOrder:params.order,
        status:$("#status").val(),
        beginTime:$("#beginTime").val(),
        endTime:$("#endTime").val(),
        userId:$("#userId").attr("data-value")
    };
    return temp;
};
function details(recordId)
{
		$.ajax({
			url : "/ret/hrget/getHrEvaluateById",
			type : "post",
			dataType : "json",
			data:{recordId:recordId},
			success : function(data) {
				if(data.status=="200")
				{
					for(var id in data.list)
					{
						if(id=="skillLevel")
						{
							$('#skillLevelD').raty({ readOnly: true,score: data.list[id]});
						}else if(id=="learnLevel")
						{
							$('#learnLevelD').raty({ readOnly: true,score: data.list[id]});
						}else if(id=="attitudeLevel")
						{
							$('#attitudeLevelD').raty({ readOnly: true,score: data.list[id]});	
						}else if(id=="status")
						{
							if(data.list[id]=="1")
							{
								$("#statusD").html("优秀");
							}else if(data.list[id]=="2")
							{
								$("#statusD").html("一般");
							}else if(data.list[id]=="3")
							{
								$("#statusD").html("较差");
							}
						}else if(id=="remark")
						{
							$("#"+id+"D").html(data.list[id]);
						}
						$("#evaluatemodalD").modal("show");
					}
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