CKEDITOR.plugins.add('xfetch', {
    init: function(editor){
        editor.addCommand('xfetch', {
        	exec:function(){
    			$("#xplugin").html(xfetchmodal);
        		if(bpmfield=="auto")
        			{
        				$(".autodiv").each(function(){
        					$(this).remove();
        				});
        			}
        		$("#type").on("change",function(){
        			switch($(this).val()){
        				case "1":
        					$("#format").removeAttr("disabled");
        					$("#format").html(renderDatetime());
        					break;
        				case "2":
        					$("#format").removeAttr("disabled");
        					$("#format").html(renderTime());
        					break;
        				default:
        					$("#format").html("");
        					$("#format").attr("disabled", "disabled");
        				
        			}
        		})
        		var selection = top.editor.getSelection();
        		var element = selection.getSelectedElement();
        		if(element)
        			{
        			var xtype = element.getAttribute('xtype');
        			if(xtype!="xfetch")
        			{
        				return;
        			}
        			$("#name").val(element.getAttribute('name'));
        			$("#name").attr("readonly","readonly");
        			$("#dataType").val(element.getAttribute('dataType'));
        			$("#style").val(element.getAttribute('style'));
        			$("#defaultValue").val(element.getAttribute('defaultValue'));
        			$("#title").val(element.getAttribute('title'));
        			var model=element.getAttribute('model');
        			var json=JSON.parse(model);
        			$("#type").val(json.type);
        			if(json.type=="1")
        				{
        				$("#format").removeAttr("disabled");
    					$("#format").html(renderDatetime());
        				}else if(json.type=="2")
        					{
        					$("#format").removeAttr("disabled");
        					$("#format").html(renderTime());
        					}else
        					{
        						$("#format").html("");
            					$("#format").attr("disabled", "disabled");
        					}
        			$("#format").val(json.format);
        			}else
        				{
        				$("#format").html(renderDatetime());
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
        			var json={};
        			json.type=$("#type").val();
        			json.format=$("#format").val();
        			var modelStr=JSON.stringify(json);
        			var html = "<input type='text' " +
        						"xtype='xfetch' " +
	        					"title='"+title+"' " +
	        					"name='"+name+"' " +
	        					"dataType='"+dataType+"' " +
	        					"defaultValue='"+defaultValue+"' " +
	        					"value='"+defaultValue+"' " +
	        					"style='"+style+"' " +
	        					"model='"+modelStr+"' " +
	        					"class='form-control' " +
	        					"placeholder='选择控件|"+title+"'/>";
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
				xfetch:{
					label : "选择控件",
					command : 'xfetch',
					group : 'xfetch',
					order : 1
				}
			});
		}
		if (editor.contextMenu){
			editor.contextMenu.addListener(function(element,selection){
				if (!element || element.isReadOnly()) return null;
				var isInput = element.hasAscendant( 'input', 1 );
				if ( isInput && element.getAttribute('xtype')=="xfetch"){
					return {
						xfetch : CKEDITOR.TRISTATE_OFF
					};
				}
				return null;
			});
		}
    }
});

function renderDatetime() {
	var dom = "";
	dom += "<option value='YYYY-MM-DD'>YYYY-MM-DD</option>";
	dom += "<option value='YYYY-MM-DD hh:mm'>YYYY-MM-DD hh:mm</option>";
	dom += "<option value='YYYY-MM'>YYYY-MM</option>";
	dom += "<option value='yy-MM-DD'>yy-MM-DD</option>";
	dom += "<option value='YYYYMMdd'>YYYYMMdd</option>";
	dom += "<option value='MM-DD YYYY'>MM-DD YYYY</option>";
	dom += "<option value='YYYY年MM月'>YYYY年MM月</option>";
	dom += "<option value='YYYY年MM月DD日'>YYYY年MM月DD日</option>";
	dom += "<option value='YYYY年MM月DD日 hh:mm'>YYYY年MM月DD日 hh:mm</option>";
	dom += "<option value='YYYY年MM月DD日 hh时mm分'>YYYY年MM月DD日 hh时mm分</option>";
	dom += "<option value='MM月DD日'>MM月DD日</option>";
	dom += "<option value='YYYY.MM'>YYYY.MM</option>";
	dom += "<option value='YYYY.MM.DD'>YYYY.MM.DD</option>";
	dom += "<option value='MM.DD'>MM.DD</option>";
	return dom;
};
function renderTime () {
	var dom = "";
	dom += "<option value='hh时'>hh时</option>";
	dom += "<option value='hh时mm分'>hh时mm分</option>";
	dom += "<option value='hh时mm分ss秒'>hh时mm分ss秒</option>";
	dom += "<option value='hh:mm'>hh:mm</option>";
	dom += "<option value='hh:mm:ss'>hh:mm:ss</option>";
	return dom;
};

var xfetchmodal=['	 <div class="modal fade bs-example-modal-lg" id="xmodal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">',
				'        <div class="modal-dialog">',
				'            <div class="modal-content">',
				'                <div class="modal-header">',
				'                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>',
				'                    <h4 class="modal-title" id="myLargeModalLabel">选择控件</h4>',
				'                </div>',
				'                <div class="modal-body">',
				'                <form class="form-horizontal" role="form" id="xfetchform">',
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
				'	                <div class="form-group autodiv">',
				'	                    <label class="col-sm-2 control-label no-padding-right">字段类型</label>',
				'	                    <div class="col-sm-10">',
				'	                    <select class="form-control" id="dataType" name="dataType">',
				'							<option>请选择</option>',
				'							<option value="int">数值型</option>',
				'							<option value="varchar">字符型</option>',
				'							<option value="text">文本型</option>',
				'						</select>',
				'	                	</div>',
				'	                </div>',
				'	                <div class="form-group">',
				'	                    <label class="col-sm-2 control-label no-padding-right">选择类型</label>',
				'	                    <div class="col-sm-10">',
				'	                    <select class="form-control" id="type" name="type" style="border-radius: 0px">',
				'							<option value="1">日期选择器</option>',
				'							<option value="2">时间选择器</option>',
				'							<option value="3">部门选择器[单部门]</option>',
				'							<option value="4">部门选择器[多部门]</option>',
				'							<option value="5">行政级别选择器[单行政级别]</option>',
				'							<option value="6">行政级别选择器[多行政级别]</option>',
				'							<option value="7">人员选择器[单人员]</option>',
				'							<option value="8">人员选择器[多人员]</option>',
				'						</select>',
				'	                	</div>',
				'	                </div>',
				'	                <div class="form-group">',
				'	                    <label class="col-sm-2 control-label no-padding-right">回显格式</label>',
				'	                    <div class="col-sm-10">',
				'	                    <select class="form-control" id="format" name="format" style="border-radius: 0px">',
				'						</select>',
				'	                	</div>',
				'	                </div>',
				'	                <div class="form-group">',
				'	                    <label class="col-sm-2 control-label no-padding-right">初始值</label>',
				'	                    <div class="col-sm-10">',
				'	                    <input type="text" class="form-control" id="defaultValue" name="defaultValue" placeholder="初始值">',
				'	               		</div>',
				'	                </div>',
				'	                <div class="form-group">',
				'	                    <label class="col-sm-2 control-label no-padding-right">控件样式</label>',
				'	                    <div class="col-sm-10">',
				'	                    <textarea rows="5" class="form-control" id="style" name="style"></textarea>',
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