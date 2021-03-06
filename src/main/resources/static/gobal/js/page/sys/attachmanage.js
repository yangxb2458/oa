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
})
function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/fileget/getAttachManageList',
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
		      idField: 'attachId',//key值栏位
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
		       field: 'oldName',
		       title: '文件名称',
		       width:'100px'
		      },
		      {
			       field: 'newName',
			       title: '当前文件名称',
			       width:'100px'
			      },
		      
		      {
				field: 'modules',
				   width:'50px',
				   title: '所属模块'
				},
				{
			       field: 'delFlag',
			       title: '当前状态',
			       width:'30px',
			       formatter:function(value,row,index){
		    	   if(value=="0")
		    		   {
		    		   		return "正常";
		    		   }else
		    			{
		    			   return "删除";   
		    			}
	            }
		      },
		      {
			       field: 'createUserName',
			       width:'50px',
			       title: '上传人'
			   },
			   {
			       field: 'upTime',
			       width:'100px',
			       title: '创建时间'
			   },
			   {
			       field: 'path',
			       width:'150px',
			       visible: false,
			       title: '物理路径'
			   },
			   {
			       field: 'fileSize',
			       width:'50px',
			       title: '文件大小',
			       formatter:function(value,row,index){
			    	   return value+"&nbsp;&nbsp;(KB)"
			       }
			   },
		      {
		       field: 'opt',
		       width:'80px',
		       align:'center',
		       title: '操作',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.attachId,row.extName);
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
        modules:$("#modules").val(),
        beginTime:$("#beginTime").val(),
        endTime:$("#endTime").val(),
        extName:$("#extName").val(),
        createAccount:$("#createAccount").attr("data-value")
    };
    return temp;
};
function createOptBtn(attachId,extName)
{
	var html="<a href=\"javascript:void(0);readdetails('"+attachId+"','"+extName+"')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;";
	html+="<a href=\"javascript:void(0);delfile('"+attachId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
}

function readdetails(attachId,extName)
{
	openFileOnLine(extName,attachId,1);
}

function delfile(attachId)
{
	if(confirm("确定删除当前文件记录吗？"))
    {
	$.ajax({
		url : "/sys/file/delAttch",
		type : "post",
		dataType : "json",
		data : {
			attachId : attachId
		},
		success : function(data) {
			if (data.status == "200") {
				top.layer.msg(data.msg);
				$("#myTable").bootstrapTable("refresh");
			} else if (data.status == "100") {
				top.layer.msg(data.msg);
			} else {
				console.log(data.msg);
			}
		}
	});
    }
}