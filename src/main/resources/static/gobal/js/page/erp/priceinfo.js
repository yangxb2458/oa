$(function(){
	$.ajax({
			url : "/ret/erpget/getProuctAndBomInfoByProductId",
			type : "post",
			dataType : "json",
			data:{
				productId:productId
			},
			success : function(data) {
				if(data.status==200){
					for(id in data.list)
						{
						if(id=="status")
							{
							if(data.list[id]=="0")
								{
								$("#"+id).html("停用");
								}else if(data.list[id]=="1")
									{
									$("#"+id).html("启用");
									}
							}else if(id=="productImg")
								{
								$("#attach").attr("data_value",data.list[id]);
								}else
									{
									$("#"+id).html(data.list[id]);
									}
						}
					query(data.list["bomId"]);
				}else
					{
					console.log(data.msg);
					}
			}
		});
	var data = getCostMxByProductId(productId);
	var rjson = getProductPrice(data.mx);
	$("#pircespan").html(rjson.unitPrice);
});

function getBomMateriel(bomId)
{
	var wltotal=0;
	var returnjson ={};
	var flag=true;
	var emx=[];
	var data = getCostMxByBomId(bomId);
	if(data.mx)
		{
			var mx = data.mx;
			for(var i=0;i<mx.length;i++)
				{
				var ej={};
				if(mx[i].bommx)
					{
					var bommxlist = mx[i].bommx;
					for(var j=0;j<bommxlist.length;j++)
						{
							if(bommxlist[j].price&&bommxlist[j].useCount)
								{
								wltotal+=(bommxlist[j].price)*(bommxlist[j].useCount);
								}else
									{
									ej.materielId=bommxlist[j].erpMateriel.materielId;
									ej.materielCode=bommxlist[j].erpMateriel.materielCode;
									ej.materielName=bommxlist[j].erpMateriel.materielName;
									ej.useCount = mbommxlist[j].useCount;
									ej.price=mbommxlist[j].price
									emx.push(ej);
									flag = false;
									}
						}
					}else
						{
						if(mx[i].price&&mx[i].useCount)
						{
							wltotal+=(mx[i].price)*(mx[i].useCount);
						}else
						{
							ej.materielId=mx[i].erpMateriel.materielId;
							ej.materielCode=mx[i].erpMateriel.materielCode;
							ej.materielName=mx[i].erpMateriel.materielName;
							ej.useCount = mx[i].useCount;
							ej.price=mx[i].price
							emx.push(ej);
							flag = false;
						}
						}
				}
		}else
			{
			flag = false;
			}
	returnjson.unitPrice=wltotal;
	returnjson.flag=flag;
	returnjson.emx=emx;
	return returnjson;
}

function getProductPrice(data)
{
	console.log(data);
	var wltotal=0;
	var emx=[];
	var returnjson ={};
	var flag=true;
	for(var j=0;j<data.length;j++)
		{
		var ej={};
			if(data[j].bommx)
				{
				for(var k=0;k<data[j].bommx.length;k++)
				{
				if(data[j].bommx[k].price&&data[j].useCount)
					{
					wltotal+=(data[j].bommx[k].price)*(data[j].bommx[k].useCount);
					}else
						{
						ej.materielId=data[j].bommx[k].erpMateriel.materielId;
						ej.materielCode=data[j].bommx[k].erpMateriel.materielCode;
						ej.materielName=data[j].bommx[k].erpMateriel.materielName;
						ej.useCount = data[j].bommx[k].useCount;
						ej.price=data[j].bommx[k].price
						emx.push(ej);
						flag = false;
						}
				}
				}else
					{
					wltotal+=(data[j].price)*(data[j].bomDetail.useCount);
					}
			console.log(wltotal);
		}
	returnjson.unitPrice=wltotal;
	returnjson.flag=flag;
	returnjson.emx=emx;
	return returnjson;
}

function query(bomId)
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/erpget/getProductMaterielListByBomId?bomId='+bomId,
	      method: 'post',
	      contentType:'application/x-www-form-urlencoded',
	      //toolbar: '#toobar',//工具列
	      striped: true,//隔行换色
	      cache: false,//禁用缓存
	      pagination: true,//启动分页
	      sidePagination: 'server',//分页方式
	      pageNumber: 1,//初始化table时显示的页码
	      pageSize: 20,//每页条目
	      showFooter: false,//是否显示列脚
	      showPaginationSwitch: false,//是否显示 数据条数选择框
	      sortable: true,//排序
	      search: false,//启用搜索
	      showColumns: false,//是否显示 内容列下拉框
	      showRefresh: false,//显示刷新按钮
	      idField: 'flagId',//key值栏位
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
	       field: 'flagId',
	       title: '物料编码',
	       sortable : true,
	       width:'200px',
	       formatter:function(value, row, index)
	       {
	    	   if(row.type!="b")
	    		   {
	    		   	return value;
	    		   }else
	    			   {
	    			   return "";
	    			   }
	       }
	      },
	      {
	       field: 'name',
	       width:'250px',
	       title: '物料&部套名称',
	       formatter:function(value, row, index)
	       {
	    	   if(row.type!="b")
	    		   {
	    		   	return value;
	    		   }else
	    			   {
	    			   return "<a style='cursor: pointer;' onclick=\"readbommx('"+row.flagId+"');\">"+value+"</a>";
	    			   }
	       }
	     },
	     {
	       field: 'type',
	       title: '类型',
	       width:'100px',
	       formatter:function(value, row, index)
		     {
	    	   if(value=='1')
	    		   {
	    		   return "物料";
	    		   }else if(value=='2')
	    			   {
	    			   return "外协"
	    			   }else if(value=="3")
	    				   {
	    				   return "工艺"
	    				   }else if(value=="b")
	    					   {
	    					   return "部套";
	    					   }
		     }
	      },
		  {
		     field: 'useCount',
		     width:'100px',
		     title: '用量'
		   },
		   {
			     field: 'price',
			     width:'100px',
			     title: '单价',
			     formatter:function(value, row, index)
			     {
				     if(row.type=="b")
		    		 {
		    		   var rs=getBomMateriel(row.flagId);
		    		   if(rs.flag)
		    			   {
		    			   return rs.unitPrice;
		    			   }
		    		}else
		    			{
		    			return value;
		    			}
			     }
		   },
			{
				field: 'xj',
				width:'100px',
				title: '小计',
				formatter:function(value, row, index)
			       {
//			    	   if(row.type=="b")
//			    		 {
//			    		   //var rs=getBomMateriel("DDDCB857-F39E-4E9C-87E5-E9A713FAF6CF");
//			    		   var rs=getBomMateriel(row.flagId);
//			    		   if(rs.flag)
//			    			   {
//			    			   return rs.unitPrice+"/RMB";
//			    			   }else
//			    				   {
//			    				 return "<a style='color:red;cursor: pointer;'>"+(rs.unitPrice*row.useCount)+"/RMB</a>"
//			    				   }
//			    		}else
//			    			{
			    				return (row.price*row.useCount)+"/RMB";
			    	//		}
			       }
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
			       field: 'param',
			       title: '规格'
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
    		   if(row.type!="b")
    			   {
    			   return "";
    			   }else
    				   {
    				   return "<div style='line-height:30px' id='opt"+index+"'>"+createOptBtn(row.bomDetailId)+"</div>";
    				   }
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
	return "<a href=\"javascript:void(0);edit('"+bomDetailId+"')\" class=\"btn btn-primary btn-xs\">编辑</a>&nbsp;&nbsp;<a href=\"javascript:void(0);del('"+bomDetailId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
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


function readbommx(bomId)
{
	window.open("/app/core/erp/cost/bompirce?bomId="+bomId);
	}