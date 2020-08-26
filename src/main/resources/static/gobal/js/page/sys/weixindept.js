var $table = $('#table');
$(function() {
	$.ajax({
		url : "/ret/unitget/getDeptTableTreeList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.status == "200") {
				createTableTree(data.list);
			}else if(data.status=="100")
			{
				top.layer.msg(data.msg);
			}else
			{
				console.log(data.msg);
			}
		}
	});
	
});
function createTableTree(res)
{
	$table.bootstrapTable({
        data:res,
        idField: 'id',
        toolbar: '#toobar',//工具列
        striped: true,//隔行换色
	    cache: false,//禁用缓存
        dataType:'json',
        sortable: true,//排序
	    search: false,//启用搜索
	    showColumns: true,//是否显示 内容列下拉框
	    showRefresh: true,//显示刷新按钮
        columns: [
            { 
            	field: 'check',  
            	checkbox: true, 
            	formatter: function (value, row, index) {
                if (row.check == true) {
                        return {  checked: true };
                    }
                }
            },
            { 
            	field: 'name',  
            	title: '名称' 
            },
            { 
            	field: 'wDeptId',  
            	title: '状态',
            	align:"center",
            	formatter: function (value, row, index) {
                    if (value=="0"||value==""||value==null) {
                            return "未同步";
                    }else
                    {
                    		return "已同步";
                    }
                 }
            	
            },
            { 
            	field: 'operate', 
            	title: '操作', align: 'center', 
            	formatter: 'operateFormatter' 
            },
        ],
        treeShowField: 'name',
        parentIdField: 'pid',
        onResetView: function(data) {
            $table.treegrid({
                initialState: 'collapsed',// 所有节点都折叠
                treeColumn: 1,
                onChange: function() {
                    $table.bootstrapTable('resetWidth');
                }
            });
            $table.treegrid('getRootNodes').treegrid('expand');
        },
        onCheck:function(row){
            var datas = $table.bootstrapTable('getData');
            selectChilds(datas,row,"id","pid",true);
            selectParentChecked(datas,row,"id","pid")
            $table.bootstrapTable('load', datas);
        },

        onUncheck:function(row){
            var datas = $table.bootstrapTable('getData');
            selectChilds(datas,row,"id","pid",false);
            $table.bootstrapTable('load', datas);
        }
    });
}


    // 格式化按钮
    function operateFormatter(value, row, index) {
    	if(row.wDeptId=="0"||row.wDeptId==""||row.wDeptId==null)
    	{
    		return '<a href="javascript:void(0);" onclick=weixinsync("'+row.id+'") class="btn btn-purple">同步部门</a>';
    	}else
    	{
    		return '<a href="javascript:void(0);" onclick=weixinupdate("'+row.id+'") class="btn btn-success">更新部门</a>';
    	}

    }

    function weixinupdate(deptId)
    {
    	$.ajax({
    		url : "/set/unitset/weixinupdate",
    		type : "post",
    		dataType : "json",
    		data:{deptId:deptId},
    		success : function(data) {
    			if(data.status=="200")
    			{
    				top.layer.msg(data.msg);
    				location.reload();
    			}else if(data.status=="100")
    			{
    				top.layer.msg(data.msg);
    			}else if(data.status=="500")
    			{
    				console.log(data.msg);
    			}
    		}
    	})
    }
    
    function weixinsync(deptId)
    {
    	$.ajax({
    		url : "/set/unitset/weixinsync",
    		type : "post",
    		dataType : "json",
    		data:{deptId:deptId},
    		success : function(data) {
    			if(data.status=="200")
    			{
    				top.layer.msg(data.msg);
    				//$table.bootstrapTable('refresh');
    				location.reload();
    			}else if(data.status=="100")
    			{
    				top.layer.msg(data.msg);
    			}else if(data.status=="500")
    			{
    				console.log(data.msg);
    			}
    		}
    	})
    }
    
    
    /*
           *    选中父项时，同时选中子项
    * @param datas 所有的数据
    * @param row 当前数据
    * @param id id 字段名
    * @param pid 父id字段名
    */
   function selectChilds(datas,row,id,pid,checked) {
       for(var i in datas){
           if(datas[i][pid] == row[id]){
               datas[i].check=checked;
               selectChilds(datas,datas[i],id,pid,checked);
           };
       }
   }

   function selectParentChecked(datas,row,id,pid){
       for(var i in datas){
           if(datas[i][id] == row[pid]){
               datas[i].check=true;
               selectParentChecked(datas,datas[i],id,pid);
           };
       }
   } 