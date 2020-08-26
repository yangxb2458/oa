$(function(){
	query();
	$(".js-btn").unbind("click").click(function(){
		if(bpmtable=="auto")
		{
			$("#tableName").attr("readonly","readonly").val(Date.parse(new Date())/1000);
		}
		$("#formmodal").modal("show");
		$(".js-save").unbind("click").click(function(){
			insertDocumentForm();
		})
	});
})
function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/documentget/getDocumentFormList',
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
		      idField: 'formId',//key值栏位
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
		       field: 'formTitle',
		       title: '表单名称',
		       sortable : true,
		       width:'200px',
		       formatter:function(value,row,index){
		       return "<a href=\"javascript:void(0);gotemplate('"+row.formId+"')\">"+value+"</a>";
		       }
		      },
		      {
			       field: 'tableName',
			       title: '数据库表名称',
			       sortable : true,
			       width:'100px'
			      },
		      
		      {
				field: 'documentType',
				   width:'50px',
				   title: '表单类型',
				   formatter:function(value,row,index){
					   if(value=="1")
						{
						   return "收文表单";
						}else if(value=="2")
						{
							return "发文表单";
						}
		            }
				},
				{
			       field: 'version',
			       title: '版本号',
			       width:'100px'
		      },
		      {
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
	                return createOptBtn(row.formId);
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
function createOptBtn(formId)
{
	var html="<a href=\"javascript:void(0);editFormInfo('"+formId+"')\" class=\"btn btn-sky btn-xs\" >编辑</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);formMobiledesigner('"+formId+"')\" class=\"btn btn-azure btn-xs\" >移动端</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);formdesigner('"+formId+"')\" class=\"btn btn-success btn-xs\" >PC设计</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);getFields('"+formId+"')\" class=\"btn btn-primary btn-xs\">字段</a>&nbsp;&nbsp;" +
					"<a href=\"javascript:void(0);deleteDocumentForm('"+formId+"')\" class=\"btn btn-darkorange btn-xs\">删除</a>";
	return html;
}

function getFields(formId)
{
	window.open('/app/core/document/documentform?view=formfields&formId='+formId);
}

function deleteDocumentForm(formId)
{
	if(confirm("确定删除当前表单吗？"))
    {
		$.ajax({
			url : "/set/documentset/deleteDocumentForm",
			type : "post",
			dataType : "json",
			data:{formId:formId},
			success : function(data){
				if(data.status=="200")
				{
					top.layer.msg(data.msg);
				}else if(data.status=="100")
				{
					top.layer.msg(data.msg);
				}else if(data.status=="500")
				{
					console.log(data.msg);
				}
			}
			});
    }else
    	{
    	return;
    	}
}

function editFormInfo(formId)
{
	$("#tableName").attr("readonly","readonly");
	document.getElementById("form1").reset();
	$.ajax({
		url : "/ret/documentget/getDocumentFormById",
		type : "post",
		dataType : "json",
		data:{formId:formId},
		success : function(data) {
			if(data.status=="500")
			{
			console.log(data.msg);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				$("#formmodal").modal("show");
				for(var id in data.list)
				{
					$("#"+id).val(data.list[id]);
				}
				$(".js-save").unbind("click").click(function(){
					updateDocumentForm(formId);
				})
				}
		}
	})
}

function updateDocumentForm(formId)
{
	$.ajax({
		url : "/set/documentset/updateDocumentForm",
		type : "post",
		dataType : "json",
		data:{
			formId:formId,
			sortNo:$("#sortNo").val(),
			formTitle:$("#formTitle").val(),
			documentType:$("#documentType").val(),
			version:$("#version").val()
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

function gotemplate(formId)
{
window.open("/app/core/document/documentformtemplate?formId="+formId);
}

function formdesigner(formId)
{
	window.open("/app/core/document/documentformdesigner?formId=" + formId);
}

function formMobiledesigner(formId)
{
	window.open("/app/core/document/documentformmdesigner?formId=" + formId);
}



function insertDocumentForm()
{
	$.ajax({
		url : "/set/documentset/insertDocumentForm",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			formTitle:$("#formTitle").val(),
			tableName:$("#tableName").val(),
			documentType:$("#documentType").val(),
			version:$("#version").val()
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
