var zTree;
var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/hrget/getHrDepartmentTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "deptId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	callback : {
		onClick : zTreeOnClick
	},
	data : {
		simpleData : {
			enable : true,
			idKey : "deptId",
			pIdKey : "orgLeaveId",
			rootPId : "0"
		},
		key : {
			name : "deptName"
		}
	}
};

$(function() {
	var topNode = [{deptName: orgName,orgLeaveId:'', isParent: "true", deptId: "0",icon:"/gobal/img/org/org.png"}];
	zTree = $.fn.zTree.init($("#tree"), setting, topNode);// 初始化树节点时，添加同步获取的数据
	var nodes = zTree.getNodes();
	 for(var i=0;i<nodes.length;i++){
		 zTree.expandNode(nodes[i], true, false, false);//默认展开第一级节点
         }
	query("")
});
function zTreeOnClick(event, treeId, treeNode)
{
	$("#myTable").bootstrapTable('destroy');
	query(treeNode.deptId);
}

function query(deptId)
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/hrget/getHrUserInfoListByDeptId?deptId='+deptId,
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
	      idField: 'userId',//key值栏位
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
	       field: 'userName',
	       title: '人员姓名',
	       sortable : true,
	       width:'80px'
	      },
	      {
		       field: 'workNo',
		       title: '工号',
		       sortable : true,
		       width:'80px'
		      },
	      {
	       field: 'deptName',
	       width:'100px',
	       title: '部门'
	     },
	     {
		   field: 'sex',
		   width:'50px',
		   title: '性别'
		  },
		  {
			field: 'employedTime',
			width:'100px',
			title: '入司时间'
		 },
	     {
	       field: 'phone',
	       title: '联系电话',
	       width:'100px'
	      },
		   {
		       field: 'workType',
		       title: '工种',
		       width:'100px',
				formatter : function(value, row, index) {
					return getHrClassCodeName('workType', value);
				}
		   },
		   {
		       field: 'workStatus',
		       title: '在职状态',
		       width:'50px',
				formatter : function(value, row, index) {
					return getHrClassCodeName('workStatus', value);
				}
		   },
	      {
	       field: 'opt',
	       title: '操作',
	       align:'center',
	       width:'180px',
    	   formatter:function(value,row,index){
                return createOptBtn(row.userId);
            }
	      }],
	      onClickCell: function (field, value, row, $element) {
	      //alert(row.SystemDesc);
	    },
	    responseHandler:function(res){
	    	if(res.status=="500")
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


function createOptBtn(userId)
{
	var html="<a href=\"javascript:void(0);details('"+userId+"')\" class=\"btn btn-primary btn-xs\">详情</a>";
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
function details(userId)
{
	window.open("/app/core/hr/userinfodetails?userId="+userId);
}

