CKEDITOR.plugins.add('xmacrotag', {
    requires: ['dialog'],
    init: function(editor){
        editor.addCommand('xmacrotag', new CKEDITOR.dialogCommand('xmacrotag'));
        editor.ui.addButton('xmacrotag', {
            label: "常量标记",
            command: 'xmacrotag',
            icon: this.path + 'images/code.jpg'
        });
        CKEDITOR.dialog.add('xmacrotag', this.path + 'dialogs/xmacrotag.js');
		
		if (editor.addMenuItems){
			editor.addMenuItems({
				xmacrotag:{
					label : "常量标记",
					command : 'xmacrotag',
					group : 'xmacrotag',
					icon: this.path + 'images/code.jpg',
					order : 5
				}
			});
		}
		if (editor.contextMenu){
			editor.contextMenu.addListener(function(element,selection){
					if (!element|| element.isReadOnly())
						return null;
					var tagName = element.hasAscendant( 'img', 1 );
					if ( tagName && element.getAttribute('xtype')=="xmacrotag"){
						return {
							xmacrotag : CKEDITOR.TRISTATE_OFF
						};
					}

					return null;
			});
		}
    }
});