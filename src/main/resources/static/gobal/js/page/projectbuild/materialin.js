$(function() {
	$("#purchaseId").select2({
		theme : "bootstrap",
		allowClear : true,
		placeholder : "请输入采购申请标题",
		query : function(query) {
			var url = "/ret/projectbuildmaterialget/getMaterialPurchaseList";
			var param = {
				search : query.term
			}; // 查询参数，query.term为用户在select2中的输入内容.
			var type = "json";
			var data = {
				results : []
			};
			$.post(url, param, function(datas) {
				var datalist = datas.list;
				for (var i = 0, len = datalist.length; i < len; i++) {
					var land = datalist[i];
					var option = {
						"id" : land.purchaseId,
						"text" : land.title
					};
					data.results.push(option);
				}
				query.callback(data);
			}, type);
		},
		escapeMarkup : function(markup) {
			return markup;
		},
		minimumInputLength : 0,
		formatResult : function(data) {
			return '<div class="select2-user-result">' + data.text + '</div>'
		},
		formatSelection : function(data) {
			var purchaseId = data.id;
			$("#myTable").bootstrapTable('destroy');
			query(purchaseId);
			return data.text;
		},
		initSelection : function(data, cb) {
			cb(data);
		}
	});
	query("");
});


function query(purchaseId)
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/projectbuildmaterialget/getPurchaseMaterialMxList?purchaseId='+purchaseId,
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
	      idField: 'materialId',//key值栏位
	      clickToSelect: false,//点击选中checkbox
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
	       field: 'name',
	       title: '材料名称',
	       sortable : true,
	       width:'200px'
	      },
	      {
		       field: 'materialCode',
		       title: '材料编号',
		       width:'50px'
	      },
	      {
			field: 'sortName',
			   width:'50px',
			   title: '材料分类'
			},
			{
		       field: 'brand',
		       title: '品牌',
		       width:'50px'
	      },
	      {
		       field: 'model',
		       width:'100px',
		       title: '规格型号'
		   },
		   {
		       field: 'quantity',
		       width:'100px',
		       title: '采购数量'
		   },
		   {
		       field: 'inQuantity',
		       width:'100px',
		       title: '已入库'
		   },
		   {
		       field: 'unit',
		       width:'100px',
		       title: '计量单位',
		       formatter:function(value,row,index){
	                return getprojectbuildunitbyid(value);
	            }
		       
		   },
		   {
		       field: 'price',
		       width:'100px',
		       title: '采购价格',
		       formatter:function(value,row,index){
	                return value+"/元(RMB)";
	            }
		   },
	      {
	       field: 'opt',
	       title: '操作',
	       align:'center',
	       width:'100px',
    	   formatter:function(value,row,index){
                return createOptBtn(row.purchaseId,row.materialId,row.quantity,row.projectId);
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
function createOptBtn(purchaseId,materialId,quantity,projectId)
{
	var html="<a href=\"javascript:void(0);materialIn('"+purchaseId+"','"+materialId+"',"+quantity+",'"+projectId+"')\" class=\"btn btn-sky btn-xs\" >入库</a>&nbsp;&nbsp;<a href=\"javascript:void(0);materialInlist('"+projectId+"','"+materialId+"')\" class=\"btn btn-sky btn-xs\" >台账</a>";
	return html;
}

function materialInlist(projectId,materialId)
{
	window.open("/app/core/projectbuild/material/materialinlist?projectId="+projectId+"&materialId="+materialId);
}

function materialIn(purchaseId,materialId,quantity,projectId)
{
	document.getElementById("form").reset();
	$("#materialInmodal").modal("show");
	$(".js-save").unbind("click").click(function(){
		var indeVal=checkMaterialQuantity(purchaseId,materialId);
		var inquantity=$("#quantiry").val();
		if((parseFloat(inquantity)+indeVal)>quantity)
			{
				top.layer.msg("入库数量大于采购入数，请检查！");
				return;
			}else
			{
				inMaterial(purchaseId,materialId,projectId);
			}
		
	})
}

function inMaterial(purchaseId,materialId,projectId)
{
	$.ajax({
		url : "/set/projectbuildmaterialset/insertMaterialIn",
		type : "post",
		dataType : "json",
		data : {
			purchaseId:purchaseId,
			materialId:materialId,
			projectId:projectId,
			quantity:$("#quantiry").val(),
			inUser:$("#inUser").attr("data-value"),
			remark:$("#remark").val()
		},
		success : function(data) {
			if (data.status == 200) {
				top.layer.msg(data.msg);
				$("#materialInmodal").modal("hide");
				$("#myTable").bootstrapTable('destroy');
				query(purchaseId);
			}else if(data.status==100)
				{
				top.layer.msg(data.msg);
				}else {
				console.log(data.msg);
			}
		}
	});
}


function checkMaterialQuantity(purchaseId,materialId)
{
	var returnVal=0;
	$.ajax({
		url : "/ret/projectbuildmaterialget/sumMaterialById",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			purchaseId:purchaseId,
			materialId:materialId
			
		},
		success : function(data) {
			if (data.status == 200) {
				returnVal=data.list;
			} else {
				console.log(data.msg);
			}
		}
	});
	return returnVal;
}



function getprojectbuildunitbyid(untiId) {
	var returnStr="";
	$.ajax({
		url : "/ret/projectbuildunitget/getunitbyid",
		type : "post",
		dataType : "json",
		async : false,
		data : {
			unitId:untiId
			
		},
		success : function(data) {
			if (data.status == 200) {
				if(data.list.znName!=null && data.list.znName!="")
					{
						returnStr = data.list.cnName+"|"+data.list.znName;
					}else
					{
						returnStr = data.list.cnName;
					}
			} else {
				console.log(data.msg);
			}
		}
	});
	return returnStr;
}