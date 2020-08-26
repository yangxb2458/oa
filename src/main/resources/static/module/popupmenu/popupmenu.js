(function() {
	var f;
	var g;
	var d;
	var c = "embedMenu" + new Date().getTime();
	function e() {
		if (!f || !g) {
			var h = "<table class='embed-menu'>";
			h += "<tr class='embed-menu-head'><td class='embed-menu-top-left'></td><td id='"
					+ c
					+ "top' class='embed-menu-top'></td><td class='embed-menu-top-right'></td></tr>";
			h += "<tr><td class='embed-menu-left'></td><td class='embed-menu-body' id='"
					+ c + "'></td><td class='embed-menu-right'></td></tr>";
			h += "<tr class='embed-menu-foot'><td class='embed-menu-bottom-left'></td><td class='embed-menu-bottom'></td><td class='embed-menu-bottom-right'></td></tr>";
			h += "</table>";
			f = $(h);
			f.css({
				opacity : 0
			});
			f.appendTo($("body"));
			g = $("#" + c);
			d = $("#" + c + "top");
			$(document).mousedown(function() {
				b();
			});
		}
	}
	$.fn.popupEmbedMenu = function(h) {
		var j = $(this);
		j[0].menuItem = h;
		e();
		var i = function(p) {
			var n = p.offset().left;
			var t = p.offset().top;
			var l = p.width();
			var u = p.height();
			var k = $(window).height();
			var m = $(window).width();
			g.html("");
			var q = p[0].menuItem;
			for (var o = 0; o < q.length; o++) {
				var s = $("<div title='" + q[o].title + "' class='embed-item'>"
						+ q[o].text + "</div>");
				s[0].func = q[o].func;
				s.appendTo(g).click(function() {
					if (this.func) {
						this.func(p);
					}
					b();
				});
			}
			var r = n + l / 2 - f.width() + 20;
			if (r < 0) {
				d.removeClass("embed-menu-top").addClass(
						"embed-menu-top-reverse");
				r = n + l / 2 - 20;
			} else {
				d.removeClass("embed-menu-top-reverse").addClass(
						"embed-menu-top");
			}
			f.css({
				left : r,
				top : t + u
			});
			a();
		};
		i(j);       
         var ev = getEvent();
         if (window.event) {
                  ev.returnValue=false;
                     ev.cancelBubble=true;        
             }else{
                  ev.preventDefault();
                  ev.stopPropagation();    
             }
	};
	function b() {
		f.animate({
			opacity : 0
		}, 200, "swing", function() {
			g.html("");
		});
	}
	function a() {
		f.animate({
			opacity : 1
		}, 200, "swing", function() {
		});
	}
})(jQuery);

function getEvent(){
     if(window.event)    {return window.event;}
     func=getEvent.caller;
     while(func!=null){
         var arg0=func.arguments[0];
         if(arg0){
             if((arg0.constructor==Event || arg0.constructor ==MouseEvent
                || arg0.constructor==KeyboardEvent)
                ||(typeof(arg0)=="object" && arg0.preventDefault
                && arg0.stopPropagation)){
                 return arg0;
             }
         }
         func=func.caller;
     }
     return null;
 }