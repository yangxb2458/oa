$(function() {
	query();
});

function query()
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/fileget/getNetDiskList',
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
		      idField: 'netDiskId',//key值栏位
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
		       field: 'netDiskName',
		       width:'100px',
		       title: '网络硬盘名称'
		     },
		     {
			       field: 'rootPath',
			       title: '硬盘目录',
			       width:'200px',
			 },
		     {
		       field: 'spaceLimit',
		       title: '容量大小',
		       width:'100px',
		       formatter:function(value,row,index){
		    	   return value+"GB";
		       }
		      },
		      {
			       field: 'orderBy',
			       title: '序排对象',
			       width:'100px',
			       formatter:function(value,row,index){
		                if(value=="0")
		                {
		                	return "文件名称";
		                }else if(value=="1")
		                {
		                	return "修改时间";
		                }else if(value=="2")
		                {
		                	return "文件大小";
		                }else if(value=="3")
		                {
		                	return "项目类型";
		                }
		            }
			   },
			   {
			       field: 'ascOrDesc',
			       title: '排序方式',
			       width:'100px',
			       formatter:function(value,row,index){
			    	   if(value=="0")
			    		   {
			    		   return "升序";  
			    		   }else if(value=="DESC")
			    			   {
			    			 return "1";  
			    			   }
			       }
			   },
			   {
			       field: 'diskCreateTime',
			       width:'100px',
			       title: '创建时间'
			   },
			   {
			       field: 'diskCreateUser',
			       width:'50px',
			       title: '创建人',
			       formatter:function(value,row,index){
			    	   return getUserNameByStr(value);
			       }
			   },
		      {
		       field: 'opt',
		       title: '操作',
		       align:'center',
		       width:'150px',
	    	   formatter:function(value,row,index){
	                return createOptBtn(row.netDiskId);
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
function createOptBtn(netDiskId)
{
	var html="<a href=\"javascript:void(0);privset('"+netDiskId+"')\" class=\"btn btn-sky btn-xs\" >权限</a>&nbsp;&nbsp;<a href=\"javascript:void(0);edit('"+netDiskId+"');\" class=\"btn btn-purple btn-xs\" >修改</a>&nbsp;&nbsp;<a href=\"javascript:void(0);del('"+netDiskId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
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

function privset(netDiskId)
{
	
	$.ajax({
		url : "/ret/fileget/getNetDisk",
		type : "post",
		dataType : "json",
		data:{netDiskId:netDiskId},
		success : function(data) {
			if(data.status=="500")
				{
				console.log(data.msg);
				}else if(data.status=="100")
					{
					top.layer.msg(data.msg);
					}else
						{
							var datalist = data.list;
							for(var name in datalist)
								{
									if(name=="accessUser"||name=="downUser"||name=="manageUser"||name=="createUser")
										{
											$("#"+name).attr("data-value",datalist[name]);
											$("#"+name).val(getUserNameByStr(datalist[name]));
										}else if(name=="accessDept"||name=="downDept"||name=="manageDept"||name=="createDept")
										{
											$("#"+name).attr("data-value",datalist[name]);
											$("#"+name).val(getDeptNameByDeptIds(datalist[name]));
										}else if(name=="accessLeave"||name=="downLeave"||name=="manageLeave"||name=="createLeave")
										{
											$("#"+name).attr("data-value",datalist[name]);
											$("#"+name).val(getUserLevelStr(datalist[name]));
										}
								}
							$("#netDiskPrivModal").modal("show");
							$(".js-setprivbtn").unbind("click").click(function(){
								var optType=$('input:radio[name="optType"]:checked').val();
								if(optType=="0")
									{
										updatepriv(netDiskId);
									}else if(optType=="1")
									{
										addpriv(netDiskId)
									}else if(optType=="2")
									{
										removepriv(netDiskId);
									}
								$("#netDiskPrivModal").modal("hide");
							});
							
						}
		}
	});
	
}

function updatepriv(netDiskId)
{
	$.ajax({
		url : "/set/fileset/updateNetDisk",
		type : "post",
		dataType : "json",
		data:{
			netDiskId:netDiskId,
			accessUser:$("#accessUser").attr("data-value"),
			accessDept:$("#accessDept").attr("data-value"),
			accessLeave:$("#accessLeave").attr("data-value"),
			downUser:$("#downUser").attr("data-value"),
			downDept:$("#downDept").attr("data-value"),
			downLeave:$("#downLeave").attr("data-value"),
			manageUser:$("#manageUser").attr("data-value"),
			manageDept:$("#manageDept").attr("data-value"),
			manageLeave:$("#manageLeave").attr("data-value"),
			createUser:$("#createUser").attr("data-value"),
			createDept:$("#createDept").attr("data-value"),
			createLeave:$("#createLeave").attr("data-value")
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
						}
		}
	});
	}
function addpriv(netDiskId)
{
	$.ajax({
		url : "/set/fileset/addNetDiskPriv",
		type : "post",
		dataType : "json",
		data:{
			netDiskId:netDiskId,
			user:$("#user").attr("data-value"),
			dept:$("#dept").attr("data-value"),
			leave:$("#leave").attr("data-value"),
			range:getCheckBoxValue("range")
			
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
						}
		}
	});
}
function removepriv(netDiskId)
{
	$.ajax({
		url : "/set/fileset/removeNetDiskPriv",
		type : "post",
		dataType : "json",
		data:{
			netDiskId:netDiskId,
			user:$("#user").attr("data-value"),
			dept:$("#dept").attr("data-value"),
			leave:$("#leave").attr("data-value"),
			range:getCheckBoxValue("range")
			
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
						}
		}
	});
}
function edit(netDiskId)
{
	document.getElementById("form1").reset();
	$.ajax({
		url : "/ret/fileget/getNetDisk",
		type : "post",
		dataType : "json",
		data:{
			netDiskId:netDiskId
		},
		success : function(data) {
			console.log(data);
			if(data.status=="500")
				{
				console.log(data.msg);
				}else if(data.status=="100")
					{
					top.layer.msg(data.msg);
					}else
						{
						for(var name in data.list)
							{
							$("#"+name).val(data.list[name]);
							}
						}
		}
	});
	$("#setnetdisk").modal("show");
	$(".js-save").unbind("click").click(function(){
		$.ajax({
			url : "/set/fileset/updateNetDisk",
			type : "post",
			dataType : "json",
			data:{
				netDiskId:netDiskId,
				sortNo:$("#sortNo").val(),
				netDiskName:$("#netDiskName").val(),
				rootPath:$("#rootPath").val(),
				spaceLimit:$("#spaceLimit").val(),
				orderBy:$("#orderBy").val(),
				ascOrDesc:$("#ascOrDesc").val()
			},
			success : function(data) {
				if(data.status=="500")
					{
					console.log(data.msg);
					}else
						{
						top.layer.msg(data.msg);
						}
			}
		});
		$('#myTable').bootstrapTable('refresh');
		$("#setnetdisk").modal("hide");
	});
}
function del(netDiskId)
{
	var msg = "您真的确定要删除吗？\n\n请确认！"; 
	if (confirm(msg)==true){
		$.ajax({
			url : "/set/fileset/deleteNetDisk",
			type : "post",
			dataType : "json",
			data:{
				netDiskId:netDiskId
			},
			success : function(data) {
				if(data.status=="500")
				{
				console.log(data.msg);
				}else if(data.status=="100")
					{
					console.log(data.msg);
					}else
					{
					$('#myTable').bootstrapTable('refresh');
					top.layer.msg(data.msg);
					}
			}
		});
	}else{ 
	return; 
	} 
}

function doadd()
{
document.getElementById("form1").reset();
$("#setnetdisk").modal("show");
$(".js-save").unbind("click").click(function(){
	$.ajax({
		url : "/set/fileset/createNetDisk",
		type : "post",
		dataType : "json",
		data:{
			sortNo:$("#sortNo").val(),
			netDiskName:$("#netDiskName").val(),
			rootPath:$("#rootPath").val(),
			spaceLimit:$("#spaceLimit").val(),
			orderBy:$("#orderBy").val(),
			ascOrDesc:$("#ascOrDesc").val()
		},
		success : function(data) {
			if(data.status=="500")
				{
				console.log(data.msg);
				}else if(data.status=="100")
				{
					console.log(data.msg);
				}else
					{
					$('#myTable').bootstrapTable('refresh');
					top.layer.msg(data.msg);
					}
		}
	});
	$("#setnetdisk").modal("hide");
});
}