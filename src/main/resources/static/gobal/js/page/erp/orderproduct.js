$(function() {
	query(orderId);
	$("#payType").html(getPayType(payType));
	createAttach("attach");
});

function query(orderId)
	{
		 $("#myTable").bootstrapTable({
		      url: '/ret/erpget/getErpOrderDetail?orderId='+orderId,
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
		      idField: 'detailId',//key值栏位
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
		       field: 'productName',
		       title: '产品名称',
		       sortable : true,
		       width:'250px'
		      },
		      {
		       field: 'model',
		       width:'200px',
		       title: '产品型号'
		     },
		     {
			       field: 'bomId',
			       title: 'BOM清单'
			   },
			  {
			     field: 'count',
			     width:'100px',
			     title: '采购数量'
			   },
			   {
				 field: 'unit',
				 width:'100px',
				 title: '单位',
				 formatter:function(value, row, index)
				   {
				    return "<div id='unit"+index+"' style='line-height:34px'>"+getUnitName(value)+"</div>";
				   }
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
	                return "<div style='line-height:30px' id='opt"+index+"'>"+createOptBtn(row.detailId)+"</div>";
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


function edit(detailId)
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
			detailId:detailId
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
				}else
					{
					console.log(data.msg);
					}
		}
	});
	$('#editdiv').modal('show');
	$(".js-cancel").bind('click',function(){
		$('#editdiv').modal('hide');	
	});
	$(".js-save").bind('click',function(){
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
					}else if(data.status=="100")
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
    	productName:"<input type='text' style='width: 100%;' id='productName' name='productName'/>",
    	model:"<div id='model' style='line-height:34px'></div>",
    	bomId:"<div id='bomId' style='line-height:34px'></div>",
    	unit:"<div id='unit' style='line-height:34px'></div>",
    	count:"<input type='number' style='width: 100%;' id='count' name='count' class='form-control'/>",
    	remark:"<input type='text' class='form-control' id='remark' name='remark'/>",
    	opt:""
    		}
    });
    $("#productName").select2(
    {
    	 theme: "bootstrap",
	        allowClear: true,
	        placeholder:"请输入产品名称",
	        query: function (query){
	        	var url = "/ret/erpget/selectProductByName";
	        	var param = {productName:query.term}; // 查询参数，query.term为用户在select2中的输入内容.
	        	var type="json";
	        	var data = { results: [] };
	        	$.post(url, param, function(datas){
		        			var datalist = datas.list;
		        			for(var i= 0, len=datalist.length;i<len;i++){
		        				var land = datalist[i];
		        				var option = {"id":land.productId,"text": land.productName,"bomId":land.bomId,"unit":land.unit,"model":land.model};
		        				data.results.push(option);
		        			}
		        			query.callback(data);
	        	}, type);
	        	
	        },
	        escapeMarkup: function (markup) {return markup; },
	        minimumInputLength: 2,
	        formatResult:function(data){return '<div class="select2-user-result">' + data.text + '</div>'},
	        formatSelection: function(data){
	        	$("#productName").attr("data-value",data.id);
	        	$("#model").html(data.model);
	        	$("#bomId").html(data.bomId);
	        	$("#unit"+count).html(getUnitName(data.unit));
	        	return data.text;
	        },
	        initSelection:function(data, cb){console.log(data); cb(data);}
	  });
			}
	$("#opt"+count).html("<a href='javascript:void(0);' onclick='save()' class='btn btn-purple btn-xs savebtn'>保存</a>&nbsp;&nbsp;<a href='javascript:void(0);' onclick='delrows("+count+")' class='btn btn-darkorange btn-xs'>删除</a>");
}



function del(bomDetailId)
{
	var msg = "您真的确定要删除吗？\n\n请确认！"; 
	if (confirm(msg)==true){
		$.ajax({
			url : "/set/erpset/deleteErpOrderDetail",
			type : "post",
			dataType : "json",
			data:{
				orderId:orderId,
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


function save()
{
	$.ajax({
		url : "/set/erpset/insertErpOrderDetail",
		type : "post",
		dataType : "json",
		data:{
			orderId:orderId,
			productId:$("#productName").attr("data-value"),
			count:$("#count").val(),
			remark:$("#remark").val()
			},
		success : function(data) {
			if(data.status="200")
				{
				top.layer.msg(data.msg);
				$("#myTable").bootstrapTable("refresh");
				}else
					{
					console.log(data.msg);
					}
		}
	});
}
