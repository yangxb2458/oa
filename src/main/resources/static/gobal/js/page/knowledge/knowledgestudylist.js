$(function() {
	query();
});
function query()
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/knowledgeget/getKnowledgeStudyList?sortId='+sortId,
	      method: 'post',
	      cardView:true,
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
	      idField: 'knowledgeId',//key值栏位
	      clickToSelect: false,//点击选中checkbox
	      pageList : [10, 20, 30, 50],//可选择单页记录数
	      queryParams:queryParams,
	      columns: [ 
	      {
	       field: 'title',
	       title: '',
	       sortable : true,
	       width:'150px'
	      },
	      {
			field: 'sortName',
			   width:'50px',
			   title: ''
			},
			 {
			       field: 'version',
			       width:'50px',
			       title: ''
			   },
			   {
			       field: 'leaveStar',
			       width:'50px',
			       title: ''
			   },
	      {
		       field: 'createUser',
		       width:'50px',
		       title: ''
		   },
		   {
		       field: 'createTime',
		       width:'100px',
		       title: ''
		   },
		   {
		       field: 'attach',
		       width:'100px',
		       title: '',
		       formatter:function(value,row,index){
		    	   return createTableAttach(value);	   
	            }
		   },
	      {
	       field: 'opt',
	       width:'50px',
	       align:'center',
	       title: '',
    	   formatter:function(value,row,index){
                return createOptBtn(row.knowledgeId);
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
    beginTime:$("#beginTime").val(),
    endTime:$("#endTime").val()
};
return temp;
};
function createOptBtn(knowledgeId)
{
var html="<a href=\"javascript:void(0);study('"+knowledgeId+"')\" class=\"btn btn-sky btn-xs\" >学习</a>";
return html;
}


function study(knowledgeId)
{
	window.open("/app/core/file/detailknowledge?knowledgeId="+knowledgeId);
}