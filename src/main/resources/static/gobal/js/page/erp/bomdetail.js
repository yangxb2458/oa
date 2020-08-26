$(function() {
	query(bomId);
	query1(bomId);
});

function query(bomId)
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/erpget/getErpBomDetailById?bomId='+bomId,
		      method: 'post',
		      contentType:'application/x-www-form-urlencoded',
		      toolbar: '#toobar',//工具列
		      striped: true,//隔行换色
		      cache: false,//禁用缓存
		      pagination: true,//启动分页
		      sidePagination: 'server',//分页方式
		      pageNumber: 1,//初始化table时显示的页码
		      pageSize: 20,//每页条目
		      showFooter: false,//是否显示列脚
		      showPaginationSwitch: true,//是否显示 数据条数选择框
		      sortable: true,//排序
		      search: true,//启用搜索
		      showColumns: true,//是否显示 内容列下拉框
		      showRefresh: true,//显示刷新按钮
		      idField: 'bomDetailId',//key值栏位
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
		       field: 'materielCode',
		       title: '物料编码',
		       sortable : true,
		       width:'200px'
		      },
		      {
		       field: 'materielName',
		       width:'250px',
		       title: '物料名称'
		     },
		     {
			       field: 'param',
			       title: '规格'
			   },
		     {
		       field: 'type',
		       title: '类型',
		       width:'100px',
		       formatter:function(value, row, index)
			     {
		    	   if(value=='1')
		    		   {
		    		   return "<div id='type"+index+"' style='line-height:34px'>物料</div>";
		    		   }else if(value=='2')
		    			   {
		    			   return "<div id='type"+index+"' style='line-height:34px'>外协</div>"
		    			   }else if(value=="3")
		    				   {
		    				   return "<div id='type"+index+"' style='line-height:34px'>工艺</div>"
		    				   }else
		    					   {
		    					   return "<div id='type"+index+"' style='line-height:34px'>其它</div>";
		    					   }
			     }
		      },
			  {
			     field: 'useCount',
			     width:'100px',
			     title: '用量'
			   },
			   {
				     field: 'unit',
				     width:'50px',
				     title: '单位',
				     formatter:function(value, row, index)
				     {
				    	 return "<div id='unit"+index+"' style='line-height:34px'>"+getUnitName(value)+"</div>";
				     }
				   },
				  
		      {
			       field: 'createTime',
			       title: '创建时间',
				   width:'150px'
			   },
			   {
			       field: 'remark',
			       title: '备注'
			   },
		      {
		       field: 'opt',
		       title: '操作',
		       align:'center',
		       width:'180px',
	    	   formatter:function(value,row,index){
	                return "<div style='line-height:30px' id='opt"+index+"'>"+createOptBtn(row.bomDetailId)+"</div>";
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
function createOptBtn(bomDetailId)
{
	var html="<a href=\"javascript:void(0);edit('"+bomDetailId+"')\" class=\"btn btn-primary btn-xs\">编辑</a>&nbsp;&nbsp;<a href=\"javascript:void(0);del('"+bomDetailId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
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


function edit(bomDetailId)
{
	document.getElementById("form").reset();
	$("#eerpBomDetailId").val("");
	$("#materielCodediv").html("");
	$("#materielNamediv").html("");
	$("#euseCount").val("");
	$("#unitdiv").html("");
	$("#param").html("");
	$("#eremark").val("")
	$.ajax({
		url : "/ret/erpget/getErpBomDetailByDetailId",
		type : "post",
		dataType : "json",
		data:{
			bomId:bomId,
			bomDetailId:bomDetailId
		},
		success : function(data) {
			if(data.status==200)
				{
					$("#eerpBomDetailId").val(data.list.bomDetailId);
					$("#materielCodediv").html(data.list.materielCode);
					$("#materielNamediv").html(data.list.materielName);
					$("#euseCount").val(data.list.useCount);
					$("#param").val(data.list.param),
					$("#unitdiv").html(getUnitName(data.list.unit));
					$("#eremark").val(data.list.remark)
				}else if(data.status==100)
				{
					top.layer.msg(data.msg);
				}else
					{
					console.log(data.msg);
					}
		}
	});
	$('#editdiv').modal('show');
	$(".js-cancel").unbind("click").click('click',function(){
		$('#editdiv').modal('hide');	
	});
	$(".js-save").unbind("click").click('click',function(){
		$.ajax({
			url : "/set/erpset/updateErpBomDetail",
			type : "post",
			dataType : "json",
			data:{
				bomId:bomId,
				bomDetailId:$("#eerpBomDetailId").val(),
				useCount:$("#euseCount").val(),
				param:$("#eparam").val(),
				remark:$("#eremark").val()
				},
			success : function(data) {
				if(data.status="200")
					{
					top.layer.msg(data.msg);
					$('#myTable').bootstrapTable('refresh');
					}else if(data.status==100)
					{
						top.layer.msg(data.msg);
					}else
						{
						console.log(data.msg);
						}
			}
		});
		$('#editdiv').modal('hide');	
	});
	
	
	}
/**
 * 新增一行数据
 */
function addRow(){
	if($(".savebtn").length>0)
		{
			$('#modal-warning').modal('show');
		}else
			{
    var count = $('#myTable').bootstrapTable('getData').length;
    $('#myTable').bootstrapTable('insertRow',{index:count,row:{
    	num:count,
    	materielCode:"<input type='hidden' style='width: 100%;' id='materielCode' name='materielCode'/>",
    	materielName:"<div id='materielName' style='line-height:34px'></div>",
    	useCount:"<input type='text' class='form-control' id='useCount' name='useCount'/>",
    	param:"<input type='text' class='form-control' id='param' name='param'/>",
    	remark:"<input type='text' class='form-control' id='remark' name='remark'/>",
    	type:"<div id='type' style='line-height:34px'></div>",
    	unit:"<div id='unit' style='line-height:34px'></div>",
    	opt:"",
    		}
    });
    $("#materielCode").select2(
    {
    	 theme: "bootstrap",
	        allowClear: true,
	        placeholder:"请输入物料编码",
	        query: function (query){
	        	var url = "/ret/erpget/selectMateriel2ById";
	        	var param = {materielCode:query.term}; // 查询参数，query.term为用户在select2中的输入内容.
	        	var type="json";
	        	var data = { results: [] };
	        	$.post(url, param, function(datas){
		        	var datalist = datas.list;
		        	   for(var i= 0, len=datalist.length;i<len;i++){
		                	var land = datalist[i];
		                        var option = {"id":land.materielCode, "text": land.materielName,"type":land.type,"unit":land.unit};
		                        data.results.push(option);
		                   }
		        	   query.callback(data);
	        	}, type);
	        },
	        escapeMarkup: function (markup) {return markup; },
	        minimumInputLength: 6,
	        formatResult:function(data){return '<div class="select2-user-result">' + data.text + '</div>'},
	        formatSelection: function(data){
	        	$("#materielName").html(data.text);
	        	$("#unit"+count).html(getUnitName(data.unit));
	        	var typeStr="未知";
	        	if(data.type=="1")
	        		{
	        		typeStr="物料";
	        		}else if(data.type=="2")
	        			{
	        			typeStr="外协";
	        			}else if(data.type=="3")
	        				{
	        				typeStr="工艺";	
	        				}else{
	        					tryeStr="其它"
	        				}
	        	$("#type"+count).html(typeStr);
	        	return data.id;
	        },
	        initSelection:function(data, cb){console.log(data); cb(data);}
	  });
			}
	$("#type"+count).html("");
	$("#unit"+count).html("");
	$("#opt"+count).html("<a href='javascript:void(0);' onclick='save()' class='btn btn-purple btn-xs savebtn'>保存</a>&nbsp;&nbsp;<a href='javascript:void(0);' onclick='delrows("+count+")' class='btn btn-darkorange btn-xs'>删除</a>");
}



function del(bomDetailId)
{
	var msg = "您真的确定要删除吗？\n\n请确认！"; 
	if (confirm(msg)==true){
		$.ajax({
			url : "/set/erpset/deleteErpBomDetail",
			type : "post",
			dataType : "json",
			data:{
				bomDetailId:bomDetailId
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


function delrows(index)
{
	$('#myTable').bootstrapTable('remove', {
		field: 'num',
        values:[index]
    });
}

function delrows1(index)
{
	$('#myTable1').bootstrapTable('remove', {
		field: 'num',
        values:[index]
    });
}
function save()
{
	$.ajax({
		url : "/set/erpset/insertErpBomDetail",
		type : "post",
		dataType : "json",
		data:{
			bomId:bomId,
			materielCode:$("#materielCode").val(),
			useCount:$("#useCount").val(),
			param:$("#param").val(),
			remark:$("#remark").val()
			},
		success : function(data) {
			if(data.status="200")
				{
				top.layer.msg(data.msg);
				$("#myTable").bootstrapTable("refresh");
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


function addRow1(){
	if($(".savebtn1").length>0)
		{
			$('#modal-warning').modal('show');
		}else
			{
    var count = $('#myTable1').bootstrapTable('getData').length;
    $('#myTable1').bootstrapTable('insertRow',{index:count,row:{
    	num:count,
    	bomName:"<input type='hidden' id='childBomId' name='childBomId'/><input type='hidden' style='width:100%' id='bomName' name='bomName'/>",
    	version:"<div id='version' style='line-height:34px'></div>",
    	mapNumber:"<div id='mapNumber' style='line-height:34px'></div>",
    	productNumber:"<div id='productNumber' style='line-height:34px'></div>",
    	status:"<div id='status' style='line-height:34px'></div>",
    	useCount:"<input type='text' class='form-control' id='useCount' name='useCount'/>",
    	remark:"<input type='text' class='form-control' id='remark' name='remark'/>",
    	opt1:""
    		}
    });
    $("#bomName").select2(
    {
    	 theme: "bootstrap",
	        allowClear: true,
	        placeholder:"请输入BOM名称",
	        query: function (query){
	        	var url = "/ret/erpget/selectBomList2ById";
	        	var param = {bomName:query.term}; // 查询参数，query.term为用户在select2中的输入内容.
	        	var type="json";
	        	var data = { results: [] };
	        	$.post(url, param, function(datas){
		        	var datalist = datas.list;
		        	   for(var i= 0, len=datalist.length;i<len;i++){
		                	var land = datalist[i];
		                        var option = {"id":land.bomId, "text": land.bomName,"mapNumber":land.mapNumber,"productNumber":land.productNumber,"status":land.status,"version":land.version};
		                        data.results.push(option);
		                   }
		        	   query.callback(data);
	        	}, type);
	        	
	        },
	        escapeMarkup: function (markup) {return markup; },
	        minimumInputLength:2,
	        formatResult:function(data){return '<div class="select2-user-result">' + data.text + '</div>'},
	        formatSelection: function(data){
	        	$("#version").html(data.version);
	        	$("#mapNumber").html(data.mapNumber);
	        	$("#productNumber").html(data.productNumber);
	        	$("#childBomId").val(data.id);
	        	 if(data.status==0)
             	{
	        		 $("#status").val("停用");
             	}else if(data.status==1)
             		{
             		 $("#status").val("启用");
             		}
	        	
	        	return data.text;
	        },
	        initSelection:function(data, cb){console.log(data); cb(data);}
	  });
			}
	$("#opt1"+count).html("<a href='javascript:void(0);' onclick='save1()' class='btn btn-purple btn-xs savebtn1'>保存</a>&nbsp;&nbsp;<a href='javascript:void(0);' onclick='delrows1("+count+")' class='btn btn-darkorange btn-xs'>删除</a>");
}

function save1()
{
	$.ajax({
		url : "/set/erpset/insertErpBomDetail",
		type : "post",
		dataType : "json",
		data:{
			bomId:bomId,
			childBomId:$("#childBomId").val(),
			useCount:$("#useCount").val(),
			remark:$("#remark").val()
			},
		success : function(data) {
			if(data.status="200")
				{
				top.layer.msg(data.msg);
				$("#myTable1").bootstrapTable("refresh");
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

function query1(bomId)
{
	 $("#myTable1").bootstrapTable({
	      url: '/ret/erpget/getErpBomByBomIdList?bomId='+bomId,
	      method: 'post',
	      contentType:'application/x-www-form-urlencoded',
	      toolbar: '#toobar1',//工具列
	      striped: true,//隔行换色
	      cache: false,//禁用缓存
	      pagination: true,//启动分页
	      sidePagination: 'server',//分页方式
	      pageNumber: 1,//初始化table时显示的页码
	      pageSize: 20,//每页条目
	      showFooter: false,//是否显示列脚
	      showPaginationSwitch: true,//是否显示 数据条数选择框
	      sortable: true,//排序
	      search: true,//启用搜索
	      showColumns: true,//是否显示 内容列下拉框
	      showRefresh: true,//显示刷新按钮
	      idField: 'materielId',//key值栏位
	      clickToSelect: true,//点击选中checkbox
	      pageList : [10, 20, 30, 50],//可选择单页记录数
	      queryParams:queryParams1,
	      columns: [{
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
	       field: 'bomName',
	       title: 'BOM名称',
	       sortable : true,
	       width:'200px'
	      },
	      {
	       field: 'version',
	       title: '版本'
	     },
	     {
	       field: 'mapNumber',
	       title: '设计图号'
	      },
		  {
		     field: 'productNumber',
		     title: '制品号'
		   },
	      {
	       field: 'status',
	       title: '状态',
	    	   formatter:function(value,row,index){
	                if(value==0)
	                	{
	                	return "停用";
	                	}else if(value==1)
	                		{
	                		return "启用";
	                		}
	            }
	      },
	      {
		       field: 'useCount',
		       title: '用量',
			   width:'200px'
		   },
		   {
		       field: 'remark',
		       title: '备注'
		   },
	      {
	       field: 'opt1',
	       width:'180px',
	       align:'center',
	       title: '操作',
	    	   formatter:function(value,row,index){
	                return "<div style='line-height:30px' id='opt1"+index+"'>"+createOptBtn(row.bomDetailId)+"</div>";
	            }
	      }],
	      onClickCell: function (field, value, row, $element) {
	    },
	    responseHandler:function(res){
	    	if(res.status=="500")
	    		{
	    		console.log(res.msg);
	    		}else if(res.status==100)
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

function queryParams1(params) {
    var temp = {
        search: params.search,
        pageSize:this.pageSize,
        pageNumber:this.pageNumber,
        sort: params.sort,
        sortOrder:params.order
    };
    return temp;
};