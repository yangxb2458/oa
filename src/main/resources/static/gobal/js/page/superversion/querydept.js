var $table = $('#table');
$(function() {
	$.ajax({
		url : "/ret/superversionget/getQuerySuperversionForDept",
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
        sortable: false,//排序
	    search: false,//启用搜索
	    showColumns: false,//是否显示 内容列下拉框
	    showRefresh: false,//显示刷新按钮
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
            	field: 'createCount',  
            	title: '发布数量',
            	align:"center"
            },
            { 
            	field: 'leadCount', 
            	title: '督办数量', 
            	align: 'center'
            },
            { 
            	field: 'handedCount', 
            	title: '牵头数量', 
            	align: 'center'
            },
            { 
            	field: 'doinCount', 
            	title: '办理中数量', 
            	align: 'center'
            },
            { 
            	field: 'delayCount', 
            	title: '延期数量', 
            	align: 'center'
            },
            { 
            	field: 'endCount', 
            	title: '完成数量', 
            	align: 'center'
            }
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