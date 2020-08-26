$(function(){
	$(".js-btn").unbind("click").click(function(){
		document.getElementById("form1").reset();
		$("#contractInfoModal").modal("show");
		$(".js-tosave").unbind("click").click(function(){
			addInfo();
		});
	});
	query();
	 $("#customerId").select2(
			    {
			    	 theme: "bootstrap",
				        allowClear: true,
				        placeholder:"请输入客户名称",
				        query: function (query){
				        	var url = "/ret/crmget/getSelect2CustomerList";
				        	var param = {search:query.term}; // 查询参数，query.term为用户在select2中的输入内容.
				        	var type="json";
				        	var data = { results: [] };
				        	$.post(url, param, function(datas){
					        	var datalist = datas.list;
					        	   for(var i= 0, len=datalist.length;i<len;i++){
					                	var land = datalist[i];
					                	var name = land.cnName!=''?land.cnName:land.enName;
					                        var option = {"id":land.customerId, "text": name};
					                        data.results.push(option);
					                   }
					        	   query.callback(data);
				        	}, type);
				        },
				        escapeMarkup: function (markup) {return markup; },
				        minimumInputLength: 2,
				        formatResult:function(data){return '<div class="select2-user-result">' + data.text + '</div>'},
				        formatSelection: function(data){
				        	return data.text;
				        },
				        initSelection:function(data, cb){console.log(data); cb(data);}
				  });
});
function query()
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/crmget/getCrmContractInfoList',
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
	      idField: 'contractInfoId',//key值栏位
	      clickToSelect: true,//点击选中checkbox
	      pageList : [10, 20, 30, 50],//可选择单页记录数
	      queryParams:queryParams,
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
	       field: 'cnName',
	       title: '客户名称',
	       sortable : false,
	       width:'200px',
	       formatter: function (value, row, index) {
	   			if(value==""||value==null)
	   				{
	   				return "<a href='/app/core/crm/customerdetails?customerId="+row.customerId+"'>"+row.enName+"</a>";
	   				}else
	   					{
	   					return "<a href='/app/core/crm/customerdetails?customerId="+row.customerId+"'>"+value+"</a>";
	   					}
	   			}
	      },
	      {
			field: 'bank',
			   width:'150px',
			   title: '开户银行'
			},
			{
		       field: 'bankAccount',
		       title: '银行账户',
		       width:'150px'
	      },
	      {
		       field: 'taxNo',
		       width:'100px',
		       title: '税务登记号'
		   },
	      {
	       field: 'opt',
	       title: '操作',
	       width:'100px',
	       align:'center',
    	   formatter:function(value,row,index){
                return createOptBtn(row.contractInfoId);
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
function createOptBtn(contractInfoId)
{
var html="<a href=\"javascript:void(0);editInfo('"+contractInfoId+"')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;" +
		"<a href=\"javascript:void(0);del('"+contractInfoId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
return html;
}
function editInfo(contractInfoId)
{
	document.getElementById("form2").reset();
	$.ajax({
		url : "/ret/crmget/getContractInfoById",
		type : "post",
		dataType : "json",
		data:{
			contractInfoId:contractInfoId
		},
		success : function(data) {
			if(data.status=="200")
			{
				console.log(data);
				for(var name in data.list)
					{
						$("#"+name+"1").val(data.list[name]);
					}
				$("#customerName").html((data.list.cnName!=''?data.list.cnName:data.list.enName))
				$(".js-tosave1").unbind("click").click(function(){
					updateInfo(contractInfoId);
				});
				$("#updatecontractInfoModal").modal("show");
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
				{
				console.log(data.msg);
				}
		}
	});		
}

function del(contractInfoId)
{
	 if(confirm("确定删除当前信息吗？"))
	    {
	$.ajax({
		url : "/set/crmset/delCrmContractInfo",
		type : "post",
		dataType : "json",
		data:{
			contractInfoId:contractInfoId
		},
		success : function(data) {
			if(data.status==200)
			{
				top.layer.msg(data.msg);
			}else if(data.status==100)
			{
				top.layer.msg(data.msg);
			}else
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
function updateInfo(contractInfoId)
{
	$.ajax({
		url : "/set/crmset/updateCrmContractInfo",
		type : "post",
		dataType : "json",
		data:{
			contractInfoId:contractInfoId,
			bank:$("#bank1").val(),
			bankAccount:$("#bankAccount1").val(),
			taxNo:$("#taxNo1").val()
		},
		success : function(data) {
			if(data.status==200)
			{
				top.layer.msg(data.msg);
				$("#updatecontractInfoModal").modal("hide");
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
}
function addInfo()
{
	$.ajax({
		url : "/set/crmset/addCrmContractInfo",
		type : "post",
		dataType : "json",
		data:{
			customerId:$("#customerId").val(),
			bank:$("#bank").val(),
			bankAccount:$("#bankAccount").val(),
			taxNo:$("#taxNo").val()
		},
		success : function(data) {
			if(data.status==200)
			{
				top.layer.msg(data.msg);
				$("#contractInfoModal").modal("hide");
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
}