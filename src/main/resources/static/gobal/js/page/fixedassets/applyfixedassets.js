var zTree;
var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/fixedassetsget/getFixedAssetSortTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "sortId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	callback : {
		onClick : zTreeOnClick
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "sortId",
			pIdKey : "sortLeave",
			rootPId : "0"
		},
		key : {
			name : "sortName"
		}
	}
};

$(function() {
	$.ajax({
		url : "/ret/fixedassetsget/getFixedAssetSortTree",
		type : "post",
		dataType : "json",
		success : function(data) {
			zTree = $.fn.zTree.init($("#tree"), setting, data);// 初始化树节点时，添加同步获取的数据
		}
	});
	query("");
});
function zTreeOnClick(event, treeId, treeNode) {
	$("#myTable").bootstrapTable('destroy');
	query(treeNode.sortId);
}

function query(sortId)
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/fixedassetsget/getApplyFixedAssetsList?sortId='+sortId,
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
	      idField: 'assetsId',//key值栏位
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
		       field: 'assetsName',
		       title: '固定资产名称',
		       sortable : true,
		       width:'100px'
		      },
		      {
			       field: 'assetsCode',
			       width:'100px',
			       title: '固定资产编号'
			   },
		      {
				field: 'ownDeptName',
				   width:'100px',
				   title: '所属部门'
				},
				{
				       field: 'sortName',
				       width:'50px',
				       title: '固定资产分类'
				   },
				{
			       field: 'brand',
			       title: '品牌',
			       width:'50px'
		      },
		      {
			       field: 'model',
			       width:'100px',
			       title: '型号'
			   },
			   {
			       field: 'purchasePrice',
			       width:'50px',
			       title: '采购价格'
			   },
	      {
	       field: 'opt',
	       title: '操作',
	       align:'center',
	       width:'120px',
    	   formatter:function(value,row,index){
                return createOptBtn(row.assetsId);
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
function createOptBtn(assetsId)
{
	var html="<a href=\"javascript:void(0);readDetails('"+assetsId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;" +
			"<a href=\"javascript:void(0);applyAssets('"+assetsId+"')\" class=\"btn btn-darkorange btn-xs\" >申请</a>";
	return html;
}
function readDetails(assetsId)
{
	window.open("/app/core/fixedassets/detailsfixedassets?assetsId="+assetsId);
}

function applyAssets(assetsId)
{
	$("#applyfixedmodal").modal("show");
	getFixedAssetsById(assetsId);
	$(".js-save").unbind("click").click(function(){
		applyFixedassets(assetsId);
	})
	
}

function applyFixedassets(assetsId)
{
	$.ajax({
		url : "/set/fixedassetsset/insertFixedAssetsApply",
		type : "post",
		dataType : "json",
		data:{
			assetsId:assetsId,
			usedUser:$("#usedUser").attr("data-value"),
			usedDept:$("#usedDept").attr("data-value"),
			remark:$("#remark").val()
			},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				$("#applyfixedmodal").modal("hide");
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


function getFixedAssetsById(assetsId)
{
	$.ajax({
		url : "/ret/fixedassetsget/getFixedAssetsById",
		type : "post",
		dataType : "json",
		data:{assetsId:assetsId},
		success : function(data) {
			var info = data.list;
			for(var name in info)
				{
					if(name=="assetsName")
					{
						$("#"+name).html(info[name]);
					}else if(name=="assetsCode")
					{
						$("#"+name).html(info[name]);
					}else if(name=="brand")
					{
						$("#"+name).html(info[name]);
					}else if(name=="model")
					{
						$("#"+name).html(info[name]);
					}
				}
		}
	});
}