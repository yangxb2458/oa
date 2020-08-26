CKEDITOR.plugins.add('xbpm', {
    init: function(editor){
        editor.addCommand('xbpm', {
        	exec:function(){
    			$("#xplugin").html(xbpmmodal);
        		if(bpmfield=="auto")
        			{
        				$(".autodiv").each(function(){
        					$(this).remove();
        				});
        			}
        		var selection = top.editor.getSelection();
        		var element = selection.getSelectedElement();
        		if(element)
        			{
        			var xtype = element.getAttribute('xtype');
        			if(xtype!="xbpm")
        			{
        				return;
        			}
        			$("#name").val(element.getAttribute('name'));
        			$("#name").attr("readonly","readonly");
        			$("#title").val(element.getAttribute('title'));
        			var model=element.getAttribute('model');
        			var json=JSON.parse(model);
        			$("#model").val(json.type);
        			}else
        				{
        				if(bpmfield=="auto")
	            			{
	            				var content=editor.getData();
	            				$("#name").attr("readonly","readonly");
	            				var maxName =0;
	            				$(content).find('*[xtype]').each(function(){
	            					var thisName = $(this).attr("name");
	            					var thisCount = thisName.substring(7,thisName.length);
	            					if(parseInt(thisCount)>maxName)
	            						{
	            						maxName = parseInt(thisCount);
	            						}
	            				})
	            				$("#name").val(getFieldNameAuto(maxName));
	            			}
        				}
        		$(".js-saveinputbtn").unbind('click').click(function (){
        			var name=$("#name").val();
        			var style=$("#style").val();
        			var model=$("#model").val();
        			var title=$("#title").val();
        			var json={};
        			json.type=model;
        			var modelStr=JSON.stringify(json);
        			var html = "<img type='text' " +
        						"xtype='xbpm' " +
	        					"title='"+title+"' " +
	        					"name='"+name+"' " +
	        					"src='/module/ckeditor/plugins/xbpm/bpmlist.png'"+
	        					"model='"+modelStr+"'/>";
        			if(title=="")
					{
						top.layer.msg("字段标题不能为空!");
						return false;
					}
        			if($("#name").attr("readonly")=="readonly")
    				{
    				editor.insertHtml(html);
                    $("#xmodal").modal("hide");
    				}else
    				{
        			var content=editor.getData();
    				$(content).find('*[xtype]').each(function(){
    					fieldList[$(this).attr("name")]=$(this).attr("title");
    				});
        			if(fieldList.hasOwnProperty(name))
					{
					top.layer.msg(title+"字段已存在,请检查!");
					return false;
					}
        			editor.insertHtml(html);
                    $("#xmodal").modal("hide");
    				}
              	});
        		$('#xmodal').modal("show");
	    	}
	    });
		if ( editor.addMenuItems ){
			editor.addMenuItems({
				xbpm:{
					label : "流程选择",
					command : 'xbpm',
					group : 'xbpm',
					order : 1
				}
			});
		}
		if (editor.contextMenu){
			editor.contextMenu.addListener(function(element,selection){
				if (!element || element.isReadOnly()) return null;
				var isInput = element.hasAscendant( 'img', 1 );
				if ( isInput && element.getAttribute('xtype')=="xbpm"){
					return {
						xbpm : CKEDITOR.TRISTATE_OFF
					};
				}
				return null;
			});
		}
    }
});

var xbpmmodal=['	 <div class="modal fade bs-example-modal-lg" id="xmodal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">',
				'        <div class="modal-dialog">',
				'            <div class="modal-content">',
				'                <div class="modal-header">',
				'                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>',
				'                    <h4 class="modal-title" id="myLargeModalLabel">流程选择</h4>',
				'                </div>',
				'                <div class="modal-body">',
				'                <form class="form-horizontal" role="form" id="xbpmform">',
				'                	<div class="form-group">',
				'	                    <label class="col-sm-2 control-label no-padding-right">控件标题</label>',
				'	                    <div class="col-sm-10">',
				'	                    <input type="text" class="form-control" id="title" name="title" placeholder="控件标题">',
				'	                    </div>',
				'	                </div>',
				'	                <div class="form-group">',
				'	                    <label class="col-sm-2 control-label no-padding-right">字段名称</label>',
				'	                    <div class="col-sm-10">',
				'	                    <input type="text" class="form-control" id="name" name="name" placeholder="字段名称">',
				'	               		 </div>',
				'	                </div>',
				'	                <div class="form-group">',
				'	                    <label class="col-sm-2 control-label no-padding-right">选择类型</label>',
				'	                    <div class="col-sm-10">',
				'	                    <select class="form-control" id="model" name="model" placeholder="字段类型"  style="border-radius:0px">',
				'							<option value="1">选择我发起的流程</option>',
				'							<option value="2">选择我经办的流程</option>',
				'							<option value="3">选择我管理的流程</option>',
				'							<option value="4">同类型的流程</option>',
				'						</select>',
				'	                	</div>',
				'	                </div>',
				'                </form>',
				'                </div>',
				'                <div class="modal-footer">',
				'                  <button type="button" class="btn btn-warning" data-dismiss="modal">取消</button>',
				'                 <button type="button" class="btn btn-primary js-saveinputbtn">确定</button>',
				'                </div>',
				'            </div>',
				'        </div>',
				'    </div>'].join("");