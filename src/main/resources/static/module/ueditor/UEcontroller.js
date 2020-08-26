/**
 * Created by admin on 2018/5/22.
 */
    //ue富文本编译器在邮件 、公告、新闻等使用了ue编译器得模块得后台传送图片配置
function UEimgfuc(){
    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl = function(action) {
        if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadvideo') {
            return '/ueditor/upload?module=ueditor';
        } else {
            return this._bkGetActionUrl.call(this, action);
        }
    }
}

