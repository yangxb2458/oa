var editor;
CKEDITOR.dialog.add('xmacrotag', function(editor){
	var nameCode = null;
	window.editor = editor;
    var escape = function(value){
        return value;
    };
    return {
        title: '宏标记',
        width: 330,
        height: 360,
        contents: [{
            id: 'cb',
			padding:0,
            elements: [{
				type:'html',
				html:"<div style='width:300px;height:300px;'>" +
						"<table style='width:100%'>" +
						"<tr>" +
						"	<td>#MACRO_表单名称</td>" +
						"	<td><a href='javascript:void(0)' onclick='insertMacroTag(1)'>[添加]</a></td>" +
						"</tr>" +
						"<tr>" +
						"	<td>#MACRO_流程名称</td>" +
						"	<td><a href='javascript:void(0)' onclick='insertMacroTag(2)'>[添加]</a></td>" +
						"</tr>" +
						"<tr>" +
						"	<td>#MACRO_流程标题</td>" +
						"	<td><a href='javascript:void(0)' onclick='insertMacroTag(3)'>[添加]</a></td>" +
						"</tr>" +
						"<tr>" +
						"	<td>#MACRO_流程开始时间</td>" +
						"	<td><a href='javascript:void(0)' onclick='insertMacroTag(4)'>[添加]</a></td>" +
						"</tr>" +
						"<tr>" +
						"	<td>#MACRO_流程结束时间</td>" +
						"	<td><a href='javascript:void(0)' onclick='insertMacroTag(5)'>[添加]</a></td>" +
						"</tr>" +
						"<tr>" +
						"	<td>#MACRO_流程序号</td>" +
						"	<td><a href='javascript:void(0)' onclick='insertMacroTag(6)'>[添加]</a></td>" +
						"</tr>" +
						"<tr>" +
                        "	<td>#MACRO_流水标识码</td>" +
                        "	<td><a href='javascript:void(0)' onclick='insertMacroTag(7)'>[添加]</a></td>" +
                        "</tr>" +
						"<tr>" +
						"	<td>#MACRO_流程发起人姓名</td>" +
						"	<td><a href='javascript:void(0)' onclick='insertMacroTag(8)'>[添加]</a></td>" +
						"</tr>" +
						"<tr>" +
						"	<td>#MACRO_流程发起人ID</td>" +
						"	<td><a href='javascript:void(0)' onclick='insertMacroTag(9)'>[添加]</a></td>" +
						"</tr>" +
						"<tr>" +
						"	<td>#MACRO_设计步骤(会签意见)<input type=\"text\" style=\"width:30px\" id=\"prcsId\"></td>" +
						"	<td><a href='javascript:void(0)' onclick='insertMacroTag(10)'>[添加]</a></td>" +
						"</tr>" +
//						"<tr>" +
//						"	<td>#MACRO_运行步骤(会签意见)<input type=\"text\" style=\"width:30px\" id=\"runPrcsId\"></td>" +
//						"	<td><a href='javascript:void(0)' onclick='insertMacroTag(11)'>[添加]</a></td>" +
//						"</tr>" +
						"</table>" +
						"</div>"
			}]
        }],
        onOk: function(){
    		editor.insertHtml();
        },
        onShow: function(){
			var selection = editor.getSelection();
			var ranges = selection.getRanges();
			var element = selection.getSelectedElement();
        }
    };
});

function insertMacroTag(type){
	switch(type){
	case 1:editor.insertHtml("#[MACRO_FORM_TITLE]");break;
	case 2:editor.insertHtml("#[MACRO_FLOW_NAME]");break;
	case 3:editor.insertHtml("#[MACRO_FLOW_TITLE]");break;
	case 4:editor.insertHtml("#[MACRO_BEGIN_TIME]");break;
	case 5:editor.insertHtml("#[MACRO_END_TIME]");break;
	case 6:editor.insertHtml("#[MACRO_RUN_ID]");break;
	case 7:editor.insertHtml("#[MACRO_RUN_GUID]");break;
	case 8:editor.insertHtml("#[MACRO_BEGIN_USERNAME]");break;
	case 9:editor.insertHtml("#[MACRO_BEGIN_ACCOUNT_ID]");break;
	case 10:
		{
		var prcsId = $("#prcsId").val();
		editor.insertHtml("#[MACRO_PRCS_IDEA{"+prcsId+"}]");
		}break;
	case 11:
		{
		var runPrcsId = $("#runPrcsId").val();
		editor.insertHtml("#[MACRO_RUN_PRCS_IDEA{"+runPrcsId+"}]");
		}break;
	default:editor.insertHtml("");
	}
}