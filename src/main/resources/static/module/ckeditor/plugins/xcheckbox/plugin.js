CKEDITOR.plugins.add('xcheckbox', {
    requires: ['dialog'],
    init: function(editor){
        editor.addCommand('xcheckbox', {
        	exec:function(){
    			$("#xplugin").html(xcheckboxmodal);
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
        			if(xtype!="xcheckbox")
        			{
        				return;
        			}
        			var model = element.getAttribute('model');
        			var jsonarr=JSON.parse(model);
        			for(var i=0;i<jsonarr.length;i++)
        				{
        				var c="";
        				if(jsonarr[i].checked)
        					{
        					c="checked='checked'";
        					}
        				var html=['<div class="optdivs"><div class="col-sm-3" align="center"><input type="text" class="form-control input-sm value" value="'+jsonarr[i].vals+'"/></div><div class="col-sm-3" align="center"><input type="text" class="form-control input-sm name" value="'+jsonarr[i].option+'"/></div><div class="col-sm-2" align="center"><input type="checkbox" name="dxcheckbox" '+c+' style="opacity:1;position: unset"></div><div class="col-sm-2" align="center"><a style="line-height:34px;cursor: pointer;" onclick="deloption(this);">删除</a></div></div>'].join("");
        				$("#optiondiv").append(html);
        				}
        			$("#name").val(element.getAttribute('name'));
        			$("#dataType").val(element.getAttribute('dataType'));
        			$("#style").val(element.getAttribute('style'));
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
        			var options=[];
        			$(".optdivs").each(function(){
        				var json={};
        				json.vals=$(this).find(".value").val();
        				json.option=$(this).find(".name").val();
        				json.checked=$(this).find('input:checkbox').is(':checked');
        				$(this).find(".input-sm").val();
        				options.push(json);
        			});
        			var modelStr=JSON.stringify(options);
        			var name=$("#name").val();
        			var dataType=$("#dataType").val();
        			var style=$("#style").val();
        			var title=$("#title").val();
        			var optStr="";
        			for(var i=0;i<options.length;i++)
        				{
        				if(options[i].checked)
        					{
        						optStr+="<input xtype='xcheckbox' name='"+name+"' title='"+title+"' dataType='"+dataType+"' style='"+style+"' model='"+modelStr+"' type='checkbox' checked='checked' value='"+options[i].vals+"'>"+options[i].option;
        					}else
        						{
        						optStr+="<input xtype='xcheckbox' name='"+name+"' title='"+title+"' dataType='"+dataType+"' style='"+style+"' model='"+modelStr+"' type='checkbox' value='"+options[i].vals+"'>"+options[i].option;
        						}
        				}
        			if(title=="")
					{
					top.layer.msg("字段标题不能为空!");
					return false;
					}
        			var em=top.editor.document.getById('em_id_'+name);
        			if(em)
        				{
        				em.setHtml(optStr);
        				}else
        					{
        					if($("#name").attr("readonly")=="readonly")
            				{
        						var html = "<em id='em_id_"+name+"'>"+optStr+"</em>";
	            				editor.insertHtml(html);
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
        					var html = "<em id='em_id_"+name+"'>"+optStr+"</em>";
        					editor.insertHtml(html);
            				}
        					}
                    $("#xmodal").modal("hide");
              	});
        		$('#xmodal').modal("show");
	    	}
	    });
        CKEDITOR.dialog.add('xcheckbox', this.path + 'dialogs/xcheckbox.js');
		if ( editor.addMenuItems ){
			editor.addMenuItems({
				xcheckbox:{
					label : "多选属性",
					command : 'xcheckbox',
					group : 'xcheckbox',
					order : 5
				}
			});
		}
		if (editor.contextMenu){
			editor.contextMenu.addListener(function(element,selection){
				if (!element || element.isReadOnly()) return null;
				var isInput = element.hasAscendant( 'input', 1 );
				if ( isInput && element.getAttribute('xtype')=="xcheckbox"){
					return {
						xcheckbox : CKEDITOR.TRISTATE_OFF
					};
				}
				return null;
			});
		}
    }
});
function getOption()
{
	$(".optdivs").each(function(){
		$(this).find(".input-sm").each(function(){
			console.log($(this).val());
		});
		$(this).find("input[name='dxcheckbox']").each(function(){
			console.log($(this).attr("checked"));
		});
	})
	
	}
function addcheckboxoption()
{
	var html=['<div class="optdivs"><div class="col-sm-3" align="center"><input type="text" class="form-control input-sm value"/></div><div class="col-sm-3" align="center"><input type="text" class="form-control input-sm name"/></div><div class="col-sm-2" align="center"><input type="checkbox" name="dxcheckbox" style="opacity:1;position: unset"></div><div class="col-sm-2" align="center"><a style="line-height:34px;cursor: pointer;" onclick="deloption(this);">删除</a></div></div>'].join("");
	$("#optiondiv").append(html);
	}

function deloption(Obj)
{
	$(Obj).parent().parent().remove();
	}
function clearchecked()
{
	$("input[name=dxcheckbox]").each(function(){
		$(this).removeAttr("checked");
	});
	}
var xcheckboxmodal=['	 <div class="modal fade bs-example-modal-lg" id="xmodal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">',
				'        <div class="modal-dialog">',
				'            <div class="modal-content">',
				'                <div class="modal-header">',
				'                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>',
				'                    <h4 class="modal-title" id="myLargeModalLabel">多选按钮</h4>',
				'                </div>',
				'                <div class="modal-body">',
				'                <form class="form-horizontal" role="form" id="xcheckboxform">',
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
				'	                <div class="form-group" style="padding-left: 100px;">',
				'							<div class="col-sm-3" align="center">数值</div><div class="col-sm-3" align="center">选择项</div><div class="col-sm-2" align="center">默认选中</div><div class="col-sm-2" align="center">操作</div>',
				'						<div id="optiondiv">',
				'						</div>',
				'						 <label class="col-sm-12 control-label no-padding-right" style="padding-right:20px;">',
				'							<a href="javascript:void(0);addcheckboxoption();" class="btn btn-palegreen btn-xs">添加数据项</a>',
				'							<a href="javascript:void(0);clearchecked();" class="btn btn-darkorange btn-xs" style="margin-right: 20px;">清空选中</a>',
				'						</label>',
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