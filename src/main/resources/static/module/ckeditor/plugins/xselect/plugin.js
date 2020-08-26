CKEDITOR.plugins.add('xselect', {
    init: function(editor){
        editor.addCommand('xselect', {
        	exec:function(){
    			$("#xplugin").html(xselectmodal);
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
        			if(xtype!="xselect")
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
        				var html=['<div class="optdivs"><div class="col-sm-3" align="center"><input type="text" class="form-control input-sm value" value="'+jsonarr[i].vals+'"/></div><div class="col-sm-3" align="center"><input type="text" class="form-control input-sm name" value="'+jsonarr[i].option+'"/></div><div class="col-sm-2" align="center"><input type="radio" name="dfradio" '+c+' style="opacity:1;position: unset"></div><div class="col-sm-2" align="center"><a style="line-height:34px;cursor: pointer;" onclick="deloption(this);">删除</a></div></div>'].join("");
        				$("#optiondiv").append(html);
        				}
        			$("#name").val(element.getAttribute('name'));
        			$("#name").attr("readonly","readonly");
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
        				json.checked=$(this).find('input:radio').is(':checked');
        				$(this).find(".input-sm").val();
        				options.push(json);
        			});
        			var optStr="";
        			for(var i=0;i<options.length;i++)
        				{
        				if(options[i].checked)
        					{
        					optStr+="<option selected='selected' value='"+options[i].vals+"'>"+options[i].option+"</option>";
        					}else
        						{
        						optStr+="<option value='"+options[i].vals+"'>"+options[i].option+"</option>";
        						}
        				}
        			var modelStr=JSON.stringify(options);
        			var name=$("#name").val();
        			var dataType=$("#dataType").val();
        			var style=$("#style").val();
        			var title=$("#title").val();
        			var html = "<select xtype='xselect'" +
					        			"title='"+title+"' " +
										"name='"+name+"' " +
										"dataType='"+dataType+"' " +
										"style='"+style+"' " +
			        					"class='form-control' " +
			        					"model='"+modelStr+"' " +
			        					"placeholder='"+$("#title").val()+"'>"+optStr+"</select>";
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
				xselect:{
					label : "下拉列表属性",
					command : 'xselect',
					group : 'xselect',
					order : 4
				}
			});
		}
		if (editor.contextMenu){
			editor.contextMenu.addListener(function(element,selection){
				if (!element || element.isReadOnly()) return null;
				var isInput = element.hasAscendant( 'select', 1 );
				if ( isInput && element.getAttribute('xtype')=="xselect"){
					return {
						xselect : CKEDITOR.TRISTATE_OFF
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
		$(this).find("input[name='dfradio']").each(function(){
			console.log($(this).attr("checked"));
		});
	})
	
	}
function addoption()
{
	var html=['<div class="optdivs"><div class="col-sm-3" align="center"><input type="text" class="form-control input-sm value"/></div><div class="col-sm-3" align="center"><input type="text" class="form-control input-sm name"/></div><div class="col-sm-2" align="center"><input type="radio" name="dfradio" style="opacity:1;position: unset"></div><div class="col-sm-2" align="center"><a style="line-height:34px;cursor: pointer;" onclick="deloption(this);">删除</a></div></div>'].join("");
	$("#optiondiv").append(html);
	}

function deloption(Obj)
{
	$(Obj).parent().parent().remove();
	}
function clearchecked()
{
	$("input[name=dfradio]").each(function(){
		$(this).removeAttr("checked");
	});
	}
var xselectmodal=['	 <div class="modal fade bs-example-modal-lg" id="xmodal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">',
				'        <div class="modal-dialog">',
				'            <div class="modal-content">',
				'                <div class="modal-header">',
				'                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>',
				'                    <h4 class="modal-title" id="myLargeModalLabel">下拉列表</h4>',
				'                </div>',
				'                <div class="modal-body">',
				'                <form class="form-horizontal" role="form" id="xselectform">',
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
				'							<a href="javascript:void(0);addoption();" class="btn btn-palegreen btn-xs">添加数据项</a>',
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