CKEDITOR.plugins.add('xfeedback', {
    requires: ['dialog'],
    init: function(editor){
        var b = editor.addCommand('xfeedback', {
        	exec:function(){
	    		var url = contextPath+"/common/ckeditor/plugins/xfeedback/plugin.jsp";
	    		top.$.jBox.open("post:"+url,"会签控件",360,380,{buttons:{"确定":"ok","关闭":true},submit:function(v){
	    			if(v=="ok"){
	    				if(validate()){
	    					editor.insertHtml(toDomStr());
	    					return true;
	    				}
	    				return false;
	    			}
	    		}});
	    	}
	    });
        editor.ui.addButton('xfeedback', {
            label: "会签控件",
            command: 'xfeedback',
            icon: this.path + 'images/code.jpg'
        });
		
		if ( editor.addMenuItems ){
			editor.addMenuItems(
			{
				xfeedback:{
					label : "会签控件",
					command : 'xfeedback',
					group : 'xfeedback',
					order : 5
				}
			});
		}
		
		if ( editor.contextMenu )
		{
			editor.contextMenu.addListener( function( element, selection )
				{
					if ( !element || element.isReadOnly() )
						return null;

					var isInput = element.hasAscendant( 'textarea', 1 );
					
					if ( isInput && element.getAttribute('xtype')=="xfeedback")
					{
						return {
							xfeedback : CKEDITOR.TRISTATE_OFF
						};
					}

					return null;
				} );
		}
    }
});