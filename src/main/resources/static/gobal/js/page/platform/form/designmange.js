$(function(){
	$(".js-create").unbind("click").click(function(){
		$("#pageCreateModal").modal("show");
		$("#tableName").attr("readonly","readonly").val(Date.parse(new Date())/1000);
		$(".js-save").unbind("click").click(function(){
			insertPlatformPage();
		})
	})
	getAllPlatformPageTypeList();
	query();
});


function insertPlatformPage()
{
	$.ajax({
		url : "/set/platformset/insertPlatformPage",
		type : "post",
		dataType : "json",
		data : {
			sortNo : $("#sortNo").val(),
			pageName : $("#pageName").val(),
			pageType : $("#pageType").val(),
			cacheFlag : $("#cacheFlag").val(),
			remark : $("#renark").val()
		},
		success : function(data) {
			if (data.status == 200) {
				top.layer.msg(data.msg);
				$("#pageCreateModal").modal("hide");
			} else {
				console.log(data.msg);
			}
		}
	});
}

function getAllPlatformPageTypeList()
{
	$.ajax({
		url : "/ret/platformget/getAllPlatformPageTypeList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == "200") {
				var recordList = data.list;
				var queryhtml="<option value=''>全部</option>";
				var html="";
				for(var i=0;i<recordList.length;i++)
					{
						queryhtml+="<option value='"+recordList[i].typeId+"'>"+recordList[i].title+"</option>";
						html+="<option value='"+recordList[i].typeId+"'>"+recordList[i].title+"</option>";
					}
			$("#pageType").html(html);
			$("#pageTypeQuery").html(queryhtml);
			} else if(data.status=="100")
			{
				top.layer.mag(data.msg);
			}else{
				console.log(data.msg);
			}
		}
	});
}


function query()
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/platformget/getManagePlatformPageList',
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
	      idField: 'pageId',//key值栏位
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
	       field: 'pageName',
	       title: '页面名称',
	       sortable : true,
	       width:'100px'
	      },
			{
		       field: 'tableName',
		       title: '数据表名',
		       width:'50px'
	      },
	  	{
		       field: 'pageTypeTitle',
		       title: '页面类型',
		       width:'50px'
	      },
	      {
		       field: 'cacheFlag',
		       title: '存储方式',
		       width:'50px',
		       formatter:function(value,row,index){
		    	   if(value=="0")
		    		  {
		    		   return "数据库";
		    		  }else
		    			  {
		    			  return "缓存文件";
		    			  }
	            }
	      },
	      {
		       field: 'createUserName',
		       width:'50px',
		       title: '创建人'
		   },
		   {
		       field: 'createTime',
		       width:'50px',
		       title: '创建时间'
		   },
		   {
		       field: 'remark',
		       width:'150px',
		       title: '备注'
		   },
	      {
	       field: 'opt',
	       width:'200px',
	       title: '操作',
	       align:'center',
    	   formatter:function(value,row,index){
                return createOptBtn(row.pageId);
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
    pageType:$("#pageTypeQuery").val()
};
return temp;
};
function createOptBtn(pageId)
{
var html="<a href=\"javascript:void(0);godesignform('"+pageId+"')\" class=\"btn btn-azure btn-xs\" >设计</a>&nbsp;&nbsp;" +
		"<a href=\"javascript:void(0);gobpmmobiledesignform('"+pageId+"')\" class=\"btn btn-azure btn-xs\" >移动端</a>&nbsp;&nbsp;" +
		"<a href=\"/ret/bpmget/getFormFile?formId="+pageId+"\" class=\"btn btn-sky btn-xs\" >导出</a>&nbsp;&nbsp;"+
		"<a href=\"javascript:void(0);getFields('"+pageId+"')\" class=\"btn btn-primary btn-xs\">字段</a>&nbsp;&nbsp;"+
		"<a href=\"javascript:void(0);getTableData('"+pageId+"')\" class=\"btn btn-palegreen btn-xs\">表数据</a>&nbsp;&nbsp;"+
		"<a href=\"javascript:void(0);showFormVersion('"+pageId+"')\" class=\"btn btn-darkorange btn-xs\">版本</a>&nbsp;&nbsp;"+
		"<a href=\"javascript:void(0);delForm('"+pageId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
return html;
}
function godesignform(pageId)
{
	window.open('/app/core/platform/formdesign?view=design&pageId='+pageId);
}