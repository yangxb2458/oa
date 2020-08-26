CKEDITOR.plugins.add('xlist', {
    init: function(editor){
        editor.addCommand('xlist', {
        	exec:function(){
    			$("#xplugin").html(xlistmodal);
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
        			if(xtype!="xlist")
        			{
        				return;
        			}
        			$("#name").val(element.getAttribute('name'));
        			$("#name").attr("readonly","readonly");
        			$("#style").val(element.getAttribute('style'));
        			$("#title").val(element.getAttribute('title'));
        			var model = element.getAttribute('model');
        			var jsonarr=JSON.parse(model);
        			for(var i=0;i<jsonarr.length;i++)
        				{
        				var html=['<div class="optionclass"><div class="col-sm-2">',
        					'       	 <input type="text" class="form-control" id="childTitle" name="childTitle" placeholder="字段标题" value="'+jsonarr[i].childTitle+'">',
        					'    	</div>',
        					'       <div class="col-sm-2">',
        					'       	 <input type="text" class="form-control" readonly="readonly" id="childName" name="childName" placeholder="字段名称" value="'+jsonarr[i].childName+'">',
        					'    	</div>',
        					'       <div class="col-sm-1 checkbox no-padding">',
        					'               <label class="no-padding">',
        					'                 <input type="checkbox" name="isTotal" '+setIsTotal(jsonarr[i].isTotal)+'>',
        					'                 <span class="text"></span>',
        					'               </label>',
        					'    	</div>',
        					'        <div class="col-sm-2 no-padding">',
        					'         <input type="text" class="form-control" id="formula" name="formula" placeholder="计算公式" value="'+jsonarr[i].formula+'">',
        					'         </div>',
        					'        <label class="col-sm-2">',
        					'			<select id="childModel" name="childModel" style="border-radius: 0px;">'+setmodel(jsonarr[i].childModel)+'',
        					'         </select>',
        					'			</label>',
        					'       <div class="col-sm-1 no-padding">',
        					'       	 <input type="text" class="form-control" id="width" name="width" placeholder="样式宽度" value="'+jsonarr[i].width+'">',
        					'    	</div>',
        					'        <label class="col-sm-1 no-padding"><input type="text" class="form-control" id="defaultValue" name="defaultValue" placeholder="初始值" value="'+jsonarr[i].defaultValue+'"></label>',
        					'        <label class="col-sm-1 no-padding"><a onclick="delrows(this);" class="btn btn-maroon">删除</a></label></div>'].join("");
        				
        				$("#childTableListDiv").append(html);
        				}
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
        		$(".js-savelistbtn").unbind('click').click(function (){
        			var modelStr=JSON.stringify(getChildOpt());
        			var name=$("#name").val();
        			var style=$("#style").val();
        			var title=$("#title").val();
        			var html = "<img xtype='xlist' " +
	        					"title='"+title+"' " +
	        					"name='"+name+"' " +
	        					"model='"+modelStr+"' " +
	        					"style='"+style+"' " +
	        					"src='/module/ckeditor/plugins/xlist/xlist.png'/>";
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
				xlist:{
					label : "列表属性",
					command : 'xlist',
					group : 'xlist',
					order : 1
				}
			});
		}
		if (editor.contextMenu){
			editor.contextMenu.addListener(function(element,selection){
				if (!element || element.isReadOnly()) return null;
				var isImg = element.hasAscendant( 'img', 1 );
				if ( isImg && element.getAttribute('xtype')=="xlist"){
					return {
						xlist : CKEDITOR.TRISTATE_OFF
					};
				}
				return null;
			});
		}
    }
});

var xlistmodal=['<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" id="xmodal" aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">',
	'        <div class="modal-dialog modal-lg">',
	'            <div class="modal-content">',
	'                <div class="modal-header">',
	'                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>',
	'                    <h4 class="modal-title" id="myLargeModalLabel">列表组件</h4>',
	'                </div>',
	'                <div class="modal-body">',
	'                <form class="form-horizontal" role="form" id="xinputform">',
	'				                	<div class="form-group">',
	'        <label class="col-sm-2 control-label no-padding-right">控件标题</label>',
	'        <div class="col-sm-10">',
	'        <input type="text" class="form-control" id="title" name="title" placeholder="控件标题">',
	'        </div>',
	'    </div>',
	'    <div class="form-group">',
	'        <label class="col-sm-2 control-label no-padding-right">字段名称</label>',
	'        <div class="col-sm-10">',
	'        <input type="text" class="form-control" id="name" name="name" placeholder="字段名称">',
	'    </div>',
	'    </div>',
	'    <div class="form-group">',
	'        <label class="col-sm-2 control-label no-padding-right">控件样式</label>',
	'        <div class="col-sm-10">',
	'        <textarea rows="5" class="form-control" id="style" name="style"></textarea>',
	'    </div>',
	'    </div>',
	'     <div class="form-group" style="text-align: center;">',
	'        <label class="col-sm-2 no-padding">字段标题</label>',
	'        <label class="col-sm-2 no-padding">字段名称</label>',
	'        <label class="col-sm-1 no-padding">合计</label>',
	'        <label class="col-sm-2 no-padding">计算公式</label>',
	'        <label class="col-sm-2 no-padding">字段模式</label>',
	'        <label class="col-sm-1 no-padding">样式宽度</label>',
	'        <label class="col-sm-1 no-padding">初始值</label>',
	'        <label class="col-sm-1 no-padding">操作</label>',
	'    </div>',
	'    <div class="form-group" style="text-align: center;" id="childTableListDiv">',
	'    </div>',
	'		                  <div class="form-group">',
	'		                  <div class="col-sm-12">',
	'		                  <a href="javascript:void(0);addrows();" class="btn btn-darkorange">添加列表项</a>',
	'		                  </div>',
	'						  </div>',
	'                </form>',
	'                </div>',
	'                <div class="modal-footer">',
	'                    <button type="button" class="btn btn-warning" data-dismiss="modal">取消</button>',
	'                    <button type="button" class="btn btn-primary js-savelistbtn">确定</button>',
	'                </div>',
	'            </div><!-- /.modal-content -->',
	'        </div><!-- /.modal-dialog -->',
	'    </div>'].join("");
function addrows()
{
	$("#childTableListDiv").append(childTable);
	}
function delrows(Obj)
{
	$(Obj).parent("label").parent("div").remove();
	}
var childTable=['<div class="optionclass"><div class="col-sm-2">',
	'       	 <input type="text" class="form-control" id="childTitle" name="childTitle" placeholder="字段标题">',
	'    	</div>',
	'       <div class="col-sm-2">',
	'       	 <input type="text" class="form-control" id="childName" name="childName" placeholder="字段名称">',
	'    	</div>',
	'       <div class="col-sm-1 checkbox no-padding">',
	'               <label class="no-padding">',
	'                 <input type="checkbox" name="isTotal">',
	'                 <span class="text"></span>',
	'               </label>',
	'    	</div>',
	'        <div class="col-sm-2 no-padding">',
	'         <input type="text" class="form-control" id="formula" name="formula" placeholder="计算公式">',
	'         </div>',
	'        <label class="col-sm-2">',
	'			<select id="childModel" name="childModel" style="border-radius: 0px;">',
	'         		<option value="1">文本输入框</option>',
	'         		<option value="2">下拉列表</option>',
	'         		<option value="3">单选框</option>',
	'         		<option value="4">多选框</option>',
	'         		<option value="5">时间选择</option>',
	'         </select>',
	'			</label>',
	'        <label class="col-sm-1 no-padding"><input type="text" class="form-control" id="defaultValue" name="defaultValue" placeholder="初始值"></label>',
	'        <label class="col-sm-1 no-padding"><input type="text" class="form-control" id="width" name="width" placeholder="样式宽度"></label>',
	'        <label class="col-sm-1 no-padding"><a onclick="delrows(this);" class="btn btn-maroon">删除</a></label></div>'].join("");

function getChildOpt()
{
	var jsonarr=[];
	$("#childTableListDiv").children(".optionclass").each(function(){
		var json={};
		json.childTitle=$(this).find("input[name='childTitle']").val();
		json.childName=$(this).find("input[name='childName']").val();
		json.formula=$(this).find("input[name='formula']").val();
		json.childModel=$(this).find("select[name='childModel']").val();
		json.isTotal=$(this).find("input[name='isTotal']").is(":checked");
		json.defaultValue=$(this).find("input[name='defaultValue']").val();
		json.width=$(this).find("input[name='width']").val();
		jsonarr.push(json);
	});
	return jsonarr;
	}
function setIsTotal(flag)
{
	if(flag)
		{
		return "checked";
		}
	}

function setmodel(modelval)
{
var html="";
if(modelval=="1")
	{
		html+="<option value='1' selected>文本输入框</option>";
		html+="<option value='2'>下拉列表</option>";
		html+="<option value='3'>单选框</option>";
		html+="<option value='4'>多选框</option>";
		html+="<option value='5'>时间选择</option>";
	}if(modelval=="2")
	{
		html+="<option value='1'>文本输入框</option>";
		html+="<option value='2' selected>下拉列表</option>";
		html+="<option value='3'>单选框</option>";
		html+="<option value='4'>多选框</option>";
		html+="<option value='5'>时间选择</option>";
	}if(modelval=="3")
	{
		html+="<option value='1'>文本输入框</option>";
		html+="<option value='2'>下拉列表</option>";
		html+="<option value='3' selected>单选框</option>";
		html+="<option value='4'>多选框</option>";
		html+="<option value='5'>时间选择</option>";
	}if(modelval=="4")
	{
		html+="<option value='1'>文本输入框</option>";
		html+="<option value='2'>下拉列表</option>";
		html+="<option value='3'>单选框</option>";
		html+="<option value='4' selected>多选框</option>";
		html+="<option value='5'>时间选择</option>";
	}if(modelval=="5")
	{
		html+="<option value='1'>文本输入框</option>";
		html+="<option value='2'>下拉列表</option>";
		html+="<option value='3'>单选框</option>";
		html+="<option value='4'>多选框</option>";
		html+="<option value='5' selected>时间选择</option>";
	}
	return html;
}