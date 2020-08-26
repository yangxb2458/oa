CKEDITOR.plugins.add('xmacro', {
    init: function(editor){
        editor.addCommand('xmacro', {
        	exec:function(){
    			$("#xplugin").html(xmacromodal);
        		if(bpmfield=="auto")
        			{
        				$(".autodiv").each(function(){
        					$(this).remove();
        				});
        			}
        		$("#type").on("change",function(){
        			var formatOption="";
        			switch($(this).val()){
        			case "1":
        				formatOption="<option value='yyyy年'>yyyy年</option>"+
        							  "<option value='yyyy'>yyyy</option>"+
        							  "<option value='yy年'>yy年</option>"+
        							  "<option value='yy'>yy</option>";
        				break;
        			case "2":
        				formatOption="<option value='yyyy-MM-dd'>yyyy-MM-dd</option>"+
        							 "<option value='yyyy年MM月dd日'>yyyy年MM月dd日</option>"+
        							 "<option value='yyyyMMdd'>yyyyMMdd</option>"+
        							 "<option value='yyyy/MM/dd'>yyyy/MM/dd</option>"+
        							 "<option value='yyyy.MM.dd'>yyyy.MM.dd</option>"+
        							 "<option value='yyyy年MM月'>yyyy年MM月</option>"+
        							 "<option value='MM月'>MM月</option>"+
        							 "<option value='dd日'>dd日</option>"+
        							 "<option value='MM-dd'>MM-dd</option>"+
        							 "<option value='MM月dd日'>MM月dd日</option>";
        				break;
        			case "3":
        				formatOption ="<option value='yyyy-MM-dd HH:mm'>yyyy-MM-dd HH:mm</option>"+
        								"<option value='yyyy-MM-dd HH:mm:ss'>yyyy-MM-dd HH:mm:ss</option>"+
        								"<option value='yyyy年MM月dd日 HH时mm分'>yyyy年MM月dd日 HH时mm分</option>"+
        								"<option value='yyyy年MM月dd日 HH时mm分ss秒'>yyyy年MM月dd日 HH时mm分ss秒</option>"+
        								"<option value='HH'>HH</option>"+
        								"<option value='HH时'>HH时</option>"+
        								"<option value='HH:mm'>HH:mm</option>"+
        								"<option value='HH时mm分'>HH时mm分</option>"+
        								"<option value='HH:mm:ss'>HH:mm:ss</option>"+
        								"<option value='HH时mm分ss秒'>HH时mm分ss秒</option>";
        				break;
        			}
        			$("#format").html(formatOption);
        		});
        		var selection = top.editor.getSelection();
        		var element = selection.getSelectedElement();
        		if(element)
        			{
        			var xtype = element.getAttribute('xtype');
        			if(xtype!="xmacro")
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
        			var formatOption="";
        			if(json.type=="1")
        				{
        				formatOption="<option value='yyyy年'>yyyy年</option>"+
						  "<option value='yyyy'>yyyy</option>"+
						  "<option value='yy年'>yy年</option>"+
						  "<option value='yy'>yy</option>";
        				}else if(json.type=="2")
        					{
        					formatOption="<option value='yyyy-MM-dd'>yyyy-MM-dd</option>"+
							 "<option value='yyyy年MM月dd日'>yyyy年MM月dd日</option>"+
							 "<option value='yyyyMMdd'>yyyyMMdd</option>"+
							 "<option value='yyyy/MM/dd'>yyyy/MM/dd</option>"+
							 "<option value='yyyy.MM.dd'>yyyy.MM.dd</option>"+
							 "<option value='yyyy年MM月'>yyyy年MM月</option>"+
							 "<option value='MM月'>MM月</option>"+
							 "<option value='dd日'>dd日</option>"+
							 "<option value='MM-dd'>MM-dd</option>"+
							 "<option value='MM月dd日'>MM月dd日</option>";
        					}else if(json.type=="3")
        						{
        						formatOption ="<option value='yyyy-MM-dd HH:mm'>yyyy-MM-dd HH:mm</option>"+
								"<option value='yyyy-MM-dd HH:mm:ss'>yyyy-MM-dd HH:mm:ss</option>"+
								"<option value='yyyy年MM月dd日 HH时mm分'>yyyy年MM月dd日 HH时mm分</option>"+
								"<option value='yyyy年MM月dd日 HH时mm分ss秒'>yyyy年MM月dd日 HH时mm分ss秒</option>"+
								"<option value='HH'>HH</option>"+
								"<option value='HH时'>HH时</option>"+
								"<option value='HH:mm'>HH:mm</option>"+
								"<option value='HH时mm分'>HH时mm分</option>"+
								"<option value='HH:mm:ss'>HH:mm:ss</option>"+
								"<option value='HH时mm分ss秒'>HH时mm分ss秒</option>";
        						}
        			
        			$("#format").html(formatOption);
        			$("#format").val(json.format);
        			}else
        				{
        				var formatOption="<option value='yyyy年'>yyyy年</option>"+
						  "<option value='yyyy'>yyyy</option>"+
						  "<option value='yy年'>yy年</option>"+
						  "<option value='yy'>yy</option>";
        				$("#format").html(formatOption);
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
        						"xtype='xmacro' " +
	        					"title='"+title+"' " +
	        					"name='"+name+"' " +
	        					"dataType='"+dataType+"' " +
	        					"defaultValue='"+defaultValue+"' " +
	        					"style='"+style+"' " +
	        					"model='"+modelStr+"' " +
	        					"class='form-control' " +
	        					"placeholder='自动控件|"+title+"'/>";
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
				xmacro:{
					label : "自动控件属性",
					command : 'xmacro',
					group : 'xmacro',
					order : 1
				}
			});
		}
		if (editor.contextMenu){
			editor.contextMenu.addListener(function(element,selection){
				if (!element || element.isReadOnly()) return null;
				var isInput = element.hasAscendant( 'input', 1 );
				if ( isInput && element.getAttribute('xtype')=="xmacro"){
					return {
						xmacro : CKEDITOR.TRISTATE_OFF
					};
				}
				return null;
			});
		}
    }
});

var xmacromodal=['	 <div class="modal fade bs-example-modal-lg" id="xmodal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">',
				'        <div class="modal-dialog">',
				'            <div class="modal-content">',
				'                <div class="modal-header">',
				'                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>',
				'                    <h4 class="modal-title" id="myLargeModalLabel">自动控件</h4>',
				'                </div>',
				'                <div class="modal-body">',
				'                <form class="form-horizontal" role="form" id="xmacroform">',
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
				'	                    <select class="form-control" id="dataType" name="dataType" placeholder="字段类型">',
				'							<option>请选择</option>',
				'							<option value="int">数值型</option>',
				'							<option value="varchar">字符型</option>',
				'							<option value="text">文本型</option>',
				'						</select>',
				'	                	</div>',
				'	                </div>',
				'	                <div class="form-group">',
				'	                    <label class="col-sm-2 control-label no-padding-right">控件类型</label>',
				'	                    <div class="col-sm-10">',
				'	                    <select id="type" name="type" class="form-control" style="border-radius: 0px">',
				'							<option value="1">当前年份</option>',
				'							<option value="2">当前日期</option>',
				'							<option value="3">当前时间</option>',
				'							<option value="4">流程名称</option>',
				'							<option value="5">流程序号</option>',
				'							<option value="6">流程GUID</option>',
				'							<option value="7">流程标题</option>',
				'							<option value="8">流程发起人帐号</option>',
				'							<option value="9">流程发起人姓名</option>',
				'							<option value="10">流程发起人部门</option>',
				'							<option value="11">流程发起人行政级别</option>',
				'							<option value="12">当前用户帐号</option>',
				'							<option value="13">当前用户姓名</option>',
				'							<option value="14">当前用户部门</option>',
				'							<option value="15">当前用户长部门</option>',
				'							<option value="16">当前用户行政级别</option>',
				'							<option value="17">当前用户IP</option>',
				'							<option value="18">当前用户姓名+日期</option>',
				'							<option value="19">当前用户姓名+时间</option>',
				'							<option value="20">当前用户姓名+日期+时间</option>',
				'							<option value="21">当前用户账号+日期</option>',
				'							<option value="22">当前用户账号+时间</option>',
				'							<option value="23">当前用户账号+日期+时间</option>',
				'							<option value="24">当前用户行政领导姓名</option>',
				'							<option value="25">当前用户行政领导账号</option>',
				'						</select>',
				'	                </div>',
				'	                </div>',
				'	                <div class="form-group">',
				'	                    <label class="col-sm-2 control-label no-padding-right">显示格式</label>',
				'	                    <div class="col-sm-10">',
				'	                    <select id="format" name="format" class="form-control" style="border-radius: 0px">',
				'						</select>',
				'	                </div>',
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