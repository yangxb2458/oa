$(function(){
	getAllBpmFlowListByManage();
	query();
	$(".js-simple-query").unbind("click").click(function(){
		$("#myTable").bootstrapTable("refresh");
	});
})
function getAllBpmFlowListByManage()
{
	$.ajax({
		url : "/ret/bpmget/getAllBpmFlowListByManage",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="200")
				{
					var html = "<option value=''>全部</option>"
					for(var i=0;i<data.list.length;i++)
						{
						html+="<option value='"+data.list[i].flowId+"'>"+data.list[i].flowName+"</option>"
						}
				$("#flowId").html(html);
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
function query()
{
	$("#myTable").bootstrapTable({
    url: '/ret/bpmget/getBpmBiTemplateList',
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
    sortOrder: "desc",
    search: true,//启用搜索
    showColumns: true,//是否显示 内容列下拉框
    showRefresh: true,//显示刷新按钮
    idField: 'templateId',//key值栏位
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
     field: 'title',
     title: '报表名称',
     sortable : true,
     width:'150px'
    },
    {
	       field: 'flowName',
	       title: '对应流程',
	       width:'100px'
	      },
    {
	       field: 'createUserName',
	       width:'100px',
	       title: '创建人'
	   },
	   {
	       field: 'createTime',
	       width:'100px',
	       title: '创建时间'
	   },
    {
     field: 'opt',
     title: '操作',
     align:'center',
     width:'120px',
	   formatter:function(value,row,index){
          return createOptBtn(row.templateId);
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
sortOrder:params.order,
flowId:$("#flowId").val()
};
return temp;
};
function createOptBtn(templateId)
{
var html="<a href=\"javascript:void(0);edit('"+templateId+"')\" class=\"btn btn-success btn-xs\" >编辑</a>&nbsp;&nbsp;" +
//"<a href=\"javascript:void(0);details('"+templateId+"')\" class=\"btn btn-sky btn-xs\" >详情</a>&nbsp;&nbsp;" +
"<a href=\"javascript:void(0);del('"+templateId+"')\" class=\"btn btn-darkorange btn-xs\" >删除</a>";
return html;
}

function details(templateId)
{
	window.open("/app/core/bpm/bpmbidetails?templateId=" + templateId);  
}

function edit(templateId)
{
	location.href="/app/core/bpm/bpmbiset?view=edit&templateId=" + templateId;  
}

function del(templateId)
{
	if(window.confirm("确定删除选中的BPM数据报表吗？")){
		$.ajax({
			url : "/set/bpmset/deleteBpmBiTemplate",
			type : "post",
			dataType : "json",
			data:{templateId:templateId},
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
					$('#myTable').bootstrapTable('refresh');
					}
			}
		})
		}else
			{
			return;
			}
		
}
