var zTree;
var setting = {
	async : {
		enable : true,// 设置 zTree 是否开启异步加载模式
		url : "/ret/hrget/getHrUserInfoDepartmentTree",// Ajax 获取数据的 URL 地址
		autoParam : [ "deptId" ],// 异步加载时需要自动提交父节点属性的参数
	},
	callback : {
		onExpand: function(event, treeId, treeNode) {
		var deptId = treeNode.deptId;
		if (treeNode.isParent) {
			$.ajax({
				url : "/ret/hrget/getHrUserInfoByDeptId",
				type : "post",
				data : {
					deptId : deptId
				},
				dataType : "json",
				success : function(data) {
					var appNode=[];
					if(data.list.length>0)
					{
						for(var i=0;i<data.list.length;i++)
						{
							var newnode={};
							newnode.deptId = data.list[i].userId;
							newnode.deptName = data.list[i].userName;
							newnode.isParent = false;
							if(data.list[i].sex=='男')
							{
								newnode.icon = '/gobal/img/org/U01.png';
							}else if(data.list[i].sex=='女')
							{
								newnode.icon = '/gobal/img/org/U11.png';
							}else
							{
								newnode.icon = '/gobal/img/org/U01.png';
							}
							appNode.push(newnode);
							
						}
						zTree.reAsyncChildNodes(treeNode, "refresh");
						zTree.addNodes(treeNode, appNode);
					}
				}
			});
		}
	},
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
	$(".js-evaluate").unbind("click").click(function(){
		if($("#userId").val()=="")
		{
			top.layer.msg("请选择您评价的人员！");
			return;
		}
		$(".js-save").unbind("click").click(function(){
			addHrEvaluate();
		});
		$("#evaluatemodal").modal("show");
		$('#skillLevel').raty({
			  half  : true,
			  hints : [['bad 1/2', 'bad'], ['poor 1/2', 'poor'], ['regular 1/2', 'regular'], ['good 1/2', 'good'], ['gorgeous 1/2', 'gorgeous']]
			});
		$('#attitudeLevel').raty({
			  half  : true,
			  hints : [['bad 1/2', 'bad'], ['poor 1/2', 'poor'], ['regular 1/2', 'regular'], ['good 1/2', 'good'], ['gorgeous 1/2', 'gorgeous']]
			});
		$('#learnLevel').raty({
			  half  : true,
			  hints : [['bad 1/2', 'bad'], ['poor 1/2', 'poor'], ['regular 1/2', 'regular'], ['good 1/2', 'good'], ['gorgeous 1/2', 'gorgeous']]
			});
	});
	
});
function zTreeOnClick(event, treeId, treeNode)
{
	if(treeNode.isParent==false)
	{
		$("#userId").val(treeNode.deptId);
		$("#userEvaluate").html("<span style='color:blue'>"+treeNode.deptName+"</span>历次评价！")
		$("#myTable").bootstrapTable('destroy');
		query(treeNode.deptId);
		
	}
}

function query(userId)
{
	 $("#myTable").bootstrapTable({
	      url: '/ret/hrget/getHrEvaluateByUserIdList?userId='+userId,
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
	      showPaginationSwitch: false,//是否显示 数据条数选择框
	      sortable: false,//排序
	      search: false,//启用搜索
	      showColumns: false,//是否显示 内容列下拉框
	      showRefresh: false,//显示刷新按钮
	      idField: 'recordId',//key值栏位
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
		       field: 'attitudeLevel',
		       title: '工作态度',
		       width:'150px',
		       formatter:function(value,row,index){
	                return value+"分"
	            }
		      },
	      {
	       field: 'learnLevel',
	       width:'150px',
	       title: '学习能力',
	       formatter:function(value,row,index){
               return value+"分"
           }
	     },
		  {
			field: 'skillLevel',
			width:'150px',
			title: '工作持能',
			formatter:function(value,row,index){
                return value+"分"
            }
		 },
	      {
	       field: 'opt',
	       title: '操作',
	       align:'center',
	       width:'120px',
    	   formatter:function(value,row,index){
                return createOptBtn(row.recordId);
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


function createOptBtn(recordId)
{
	var html="<a href=\"javascript:void(0);editevaluate('"+recordId+"')\" class=\"btn btn-success btn-xs\">更新</a>";
		html+="&nbsp;&nbsp;<a href=\"javascript:void(0);deleteReocrd('"+recordId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>"
		+"&nbsp;&nbsp;<a href=\"javascript:void(0);details('"+recordId+"')\" class=\"btn btn-primary btn-xs\">详情</a>";
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

function deleteReocrd(recordId)
{
	 if(confirm("确定删除当前评价信息吗？"))
	    {
	$.ajax({
		url : "/set/hrset/deleteHrEvaluate",
		type : "post",
		dataType : "json",
		data:{recordId:recordId},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				$("#myTable").bootstrapTable("refresh");
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
}

function editevaluate(recordId)
{
	$.ajax({
		url : "/ret/hrget/getHrEvaluateById",
		type : "post",
		dataType : "json",
		data:{recordId:recordId},
		success : function(data) {
			if(data.status=="200")
			{
				for(var id in data.list)
				{
					if(id=="skillLevel")
					{
						$('#skillLevel').raty({half:true,score: data.list[id]});
					}else if(id=="learnLevel")
					{
						$('#learnLevel').raty({half:true,score: data.list[id]});
					}else if(id=="attitudeLevel")
					{
						$('#attitudeLevel').raty({half:true,score: data.list[id]});	
					}else
					{
						$("#"+id).val(data.list[id]);
					}
					$("#evaluatemodal").modal("show");
					$(".js-save").unbind("click").click(function(){
						updateHrEvaluate(recordId);
					})
				}
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else if(data.status=="500")
			{
				console.log(data.msg);
			}
		}
	});
}
function details(recordId)
{
		$.ajax({
			url : "/ret/hrget/getHrEvaluateById",
			type : "post",
			dataType : "json",
			data:{recordId:recordId},
			success : function(data) {
				if(data.status=="200")
				{
					for(var id in data.list)
					{
						if(id=="skillLevel")
						{
							$('#skillLevelD').raty({ readOnly: true,score: data.list[id]});
						}else if(id=="learnLevel")
						{
							$('#learnLevelD').raty({ readOnly: true,score: data.list[id]});
						}else if(id=="attitudeLevel")
						{
							$('#attitudeLevelD').raty({ readOnly: true,score: data.list[id]});	
						}else if(id=="status")
						{
							if(data.list[id]=="1")
							{
								$("#statusD").html("优秀");
							}else if(data.list[id]=="2")
							{
								$("#statusD").html("一般");
							}else if(data.list[id]=="3")
							{
								$("#statusD").html("较差");
							}
						}else if(id=="remark")
						{
							$("#"+id+"D").html(data.list[id]);
						}
						$("#evaluatemodalD").modal("show");
					}
				}else if(data.status=="100")
				{
					top.layer.msg(data.msg);
				}else if(data.status=="500")
				{
					console.log(data.msg);
				}
			}
		});
}


function addHrEvaluate()
{
	var skillLevel = $("#skillLevel").find("input[name='score']").val();
	var learnLevel = $("#learnLevel").find("input[name='score']").val();
	var attitudeLevel = $("#attitudeLevel").find("input[name='score']").val();
	$.ajax({
		url : "/set/hrset/insertHrEvaluate",
		type : "post",
		dataType : "json",
		data:{
			userId:$("#userId").val(),
			status:$("#status").val(),
			skillLevel:skillLevel,
			learnLevel:learnLevel,
			attitudeLevel:attitudeLevel,
			remark:$("#remark").val()
		},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				location.reload();
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	})
}

function updateHrEvaluate(recordId)
{
	var skillLevel = $("#skillLevel").find("input[name='score']").val();
	var learnLevel = $("#learnLevel").find("input[name='score']").val();
	var attitudeLevel = $("#attitudeLevel").find("input[name='score']").val();
	$.ajax({
		url : "/set/hrset/updateHrEvaluate",
		type : "post",
		dataType : "json",
		data:{
			recordId:recordId,
			status:$("#status").val(),
			skillLevel:skillLevel,
			learnLevel:learnLevel,
			attitudeLevel:attitudeLevel,
			remark:$("#remark").val()
		},
		success : function(data) {
			if(data.status=="200")
			{
				top.layer.msg(data.msg);
				location.reload();
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	})
}
