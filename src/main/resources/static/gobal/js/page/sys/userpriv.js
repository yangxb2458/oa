$(function() {
	query();
});

function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/unitget/getAllUserPriv',
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
		      idField: 'userPrivId',//key值栏位
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
		       field: 'userPrivId',
		       title: '权恨组标识',
		       sortable : true,
		       width:'400px'
		      },
		      {
		       field: 'userPrivName',
		       width:'200px',
		       title: '权限组名称'
		     },
		     {
			       field: 'remark',
			       title: '备注'
			   },
		      {
		       field: 'opt',
		       title: '操作',
		       align:'center',
		       width:'300px',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.userPrivId);
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
function createOptBtn(userPrivId)
{
	var html="<a href=\"javascript:void(0);copyPriv('"+userPrivId+"')\" class=\"btn btn-sky btn-xs\" >克隆</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);setuserpriv('"+userPrivId+"')\" class=\"btn btn-primary btn-xs\">PC权限</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);setmobileuserpriv('"+userPrivId+"')\" class=\"btn btn-primary btn-xs\">移动端权限</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);edit('"+userPrivId+"')\" class=\"btn btn-primary btn-xs\">编辑</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);del('"+userPrivId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
	return html;
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

function copyPriv(userPrivId)
{
	$.ajax({
		url : "/set/unitset/copyPriv",
		type : "post",
		dataType : "json",
		data:{
			userPrivId:userPrivId
		},
		success : function(data) {
			if(data.status==200){
				top.layer.msg(data.msg);
				location.reload();
			}else if(data.status==100)
				{
					top.layer.msg(data.msg);
				}else
				{
				console.log(data.msg);
				}
		}
	});	
}
function setuserpriv(userPrivId)
{
	location.href="/app/core/userpriv?view=pcsetpriv&userPrivId="+userPrivId;
}
function setmobileuserpriv(userPrivId)
{
	location.href="/app/core/userpriv?view=mobilesetpriv&userPrivId="+userPrivId;
}
function edit(userPrivId)
{
	document.getElementById("form").reset();
	$("#createmodal").modal("show");
	$.ajax({
		url : "/ret/unitget/getUserPriv",
		type : "post",
		dataType : "json",
		data:{
			userPrivId:userPrivId
		},
		success : function(data) {
			if(data.status==200){
				for(var name in data.list)
					{
						$("#"+name).val(data.list[name]);
					}
				
			}else if(data.status==100)
				{
					top.layer.msg(data.msg);
				}else
				{
				console.log(data.msg);
				}
		}
	});
	
	$(".js-savebtn").unbind("click").click(function(){
		$("#form").bootstrapValidator('validate');
		var flag = $('#form').data('bootstrapValidator').isValid();
		if(flag)
		{
		$.ajax({
			url : "/set/unitset/updateUserPriv",
			type : "post",
			dataType : "json",
			data:{
				userPrivId:$("#userPrivId").val(),
				sortNo:$("#sortNo").val(),
				userPrivName:$("#userPrivName").val(),
				remark:$("#remark").val()
			},
			success : function(data) {
				if(data.status==200){
					top.layer.msg(data.msg);
					location.reload();
				}else if(data.status==100)
				{
					top.layer.msg(data.msg);
				}else
					{
					console.log(data.msg);
					}
			}
		});
		}
	});
}

function createUserPriv(){
	 document.getElementById("form").reset();
	$("#createmodal").modal("show");
	$(".js-savebtn").unbind("click").click(function(){
		$("#form").bootstrapValidator('validate');
		var flag = $('#form').data('bootstrapValidator').isValid();
		if(flag)
		{
		$.ajax({
			url : "/set/unitset/insertUserPriv",
			type : "post",
			dataType : "json",
			data:{
				sortNo:$("#sortNo").val(),
				userPrivName:$("#userPrivName").val(),
				remark:$("#remark").val()
			},
			success : function(data) {
				if(data.status==200){
					top.layer.msg(data.msg);
					location.reload();
				}else if(data.status==100)
				{
					top.layer.msg(data.msg);
				}else
					{
					console.log(data.msg);
					}
			}
		});
		}
	});
}



function del(userPrivId)
{
	var msg = "您真的确定要删除吗？\n\n请确认！"; 
	if (confirm(msg)==true){
		$.ajax({
			url : "/set/unitset/deleteUserPriv",
			type : "post",
			dataType : "json",
			data:{
				userPrivId:userPrivId
			},
			success : function(data) {
				if(data.status==200){
					top.layer.msg(data.msg);
					location.reload();
				}else if(data.status==100)
				{
					top.layer.msg(data.msg);
				}else
					{
					console.log(data.msg);
					}
			}
		});
	}else{ 
	return; 
	} 
}