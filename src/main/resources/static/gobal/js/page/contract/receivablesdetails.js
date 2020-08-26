$(function(){
	$.ajax({
		url : "/ret/contractget/getContractReceivablesById",
		type : "post",
		dataType : "json",
		data:{receivablesId:receivablesId},
		success : function(data) {
			if(data.status=="200")
			{
				var recordinfo = data.list;
				for(var id in recordinfo)
				{
					if(id=="attach")
					{
						$("#contractattach").attr("data_value", recordinfo.attach);
						createAttach("hrattach", 1);
					}else if(id=="userPriv")
					{
						$("#"+id).html(getUserNameByStr(recordinfo[id]));
					}else if(id=="contractId")
					{
						$.ajax({
							url : "/ret/contractget/getContractById",
							type : "post",
							dataType : "json",
							data:{contractId:recordinfo[id]},
							success : function(res) {
								if(res.status=="200")
								{
									$("#contractId").html(res.list.title);
								}else if(res.status=="100")
								{
									top.layer.msg(res.msg);
								}else if(res.status=="500")
								{
									console.log(res.msg);
								}
							}
						});
					}else
					{
						$("#"+id).html(recordinfo[id]);
					}
				}
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
		})
		query();
})


function query()
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/contractget/getContractReceivablesRecordList?receivablesId='+receivablesId,
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
	      sortable: false,//排序
	      search: false,//启用搜索
	      showColumns: false,//是否显示 内容列下拉框
	      showRefresh: false,//显示刷新按钮
	      idField: 'recordId',//key值栏位
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
	       field: 'payee',
	       title: '收款人',
	       width:'100px',
	       formatter: function (value, row, index) {
				return getUserNameByStr(value);
			}
	      },
	      {
		       field: 'payeeTime',
		       title: '收款时间',
		       width:'100px'
		      },
	      
			{
		       field: 'amount',
		       title: '收款金额',
		       width:'50px'
	      },
	      {
		       field: 'remark',
		       width:'200px',
		       title: '备注'
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
