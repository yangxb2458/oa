(function($) {
var oldHTML = $.fn.html;
$.fn.formhtml =function() {
if (arguments.length) return oldHTML.apply(this,arguments);
$("input,textarea,button", this).each(function() {
this.setAttribute('value',this.value);
});
$(":radio,:checkbox", this).each(function() {
if (this.checked) this.setAttribute('checked', 'checked');
else this.removeAttribute('checked');
});
$("option", this).each(function() {
if (this.selected) this.setAttribute('selected', 'selected');
else this.removeAttribute('selected');
});
return oldHTML.apply(this);
};
})(jQuery);