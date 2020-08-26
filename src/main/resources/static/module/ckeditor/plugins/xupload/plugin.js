CKEDITOR.plugins.add('xupload', {
    init: function(editor){
        editor.addCommand('xupload', {
        	exec:function(){
    			$("#xplugin").html(xuploadmodal);
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
        			if(xtype!="xupload")
        			{
        				return;
        			}
        			$("#name").val(element.getAttribute('name'));
        			$("#name").attr("readonly","readonly");
        			$("#title").val(element.getAttribute('title'));
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
        			var dataType=$("#dataType").val();
        			var style=$("#style").val();
        			var defaultValue=$("#defaultValue").val();
        			var title=$("#title").val();
        			var html = "<input type='file' " +
        						"xtype='xupload' " +
	        					"title='"+title+"' " +
	        					"name='"+name+"' " +
	        					"class='form-control' " +
	        					"placeholder='附件上传|"+title+"'/>";
        			var content=editor.getData();
    				$(content).find('*[xtype]').each(function(){
    					fieldList[$(this).attr("name")]=$(this).attr("title");
    				});
    				if(fieldList.hasOwnProperty(name))
					{
					top.layer.msg(title+"字段已存在,请检查!");
					return false;
					}
    				if(title=="")
    					{
    					top.layer.msg("字段标题不能为空!");
    					return false;
    					}
        			editor.insertHtml(html);
                    $("#xmodal").modal("hide");
              	});
        		$('#xmodal').modal("show");
	    	}
	    });
		if ( editor.addMenuItems ){
			editor.addMenuItems({
				xupload:{
					label : "附件上传",
					command : 'xupload',
					group : 'xupload',
					order : 1
				}
			});
		}
		if (editor.contextMenu){
			editor.contextMenu.addListener(function(element,selection){
				if (!element || element.isReadOnly()) return null;
				var isInput = element.hasAscendant( 'input', 1 );
				if ( isInput && element.getAttribute('xtype')=="xupload"){
					return {
						xupload : CKEDITOR.TRISTATE_OFF
					};
				}
				return null;
			});
		}
    }
});

var xuploadmodal=['	 <div class="modal fade bs-example-modal-lg" id="xmodal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">',
				'        <div class="modal-dialog">',
				'            <div class="modal-content">',
				'                <div class="modal-header">',
				'                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>',
				'                    <h4 class="modal-title" id="myLargeModalLabel">附件上传</h4>',
				'                </div>',
				'                <div class="modal-body">',
				'                <form class="form-horizontal" role="form" id="xuploadform">',
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
				'	                </div>',
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