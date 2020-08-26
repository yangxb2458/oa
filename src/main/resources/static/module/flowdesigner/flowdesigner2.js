function Designer(container) {
	var designer = $("#" + container);
	var nodes = new Array();
	var fillColor = "#5c96bc";
	jsPlumb.Defaults.Connector = [ "Straight", {
		curviness : 20
	} ];
	jsPlumb.Defaults.DragOptions = {
		cursor : "pointer",
		zIndex : 0
	};
	jsPlumb.Defaults.PaintStyle = {
		strokeStyle : fillColor,
		lineWidth : 1.5
	};
	jsPlumb.Defaults.EndpointStyle = {
		radius : 1,
		fillStyle : fillColor
	};
	jsPlumb.Defaults.ConnectionOverlays = [ [ "Arrow", {
		location : -3,
		id : "arrow",
		length : 14,
		foldback : 0.8
	} ], [ "Label", {
		location : 0.2,
		id : "label",
		cssClass : "aLabel"
	} ] ];
	var anchors = [ [ "Perimeter", {
		anchorCount : 25,
		shape : "Rectangle"
	} ] ];
	var aConnection = {
		anchor : anchors
	};
	var addNodeExtraMethod = function(node) {
		jsPlumb.draggable(node);
		node[0].nextnodes = new Array();
		node[0].prevnodes = new Array();
		node.addNextNode = function(n) {
			var hasNext = false;
			var hasPrev = false;
			var obj = $(this);
			var nextnodes = obj[0].nextnodes;
			var prevnodes = n[0].prevnodes;
			for ( var i = 0; i < nextnodes.length; i++) {
				if (nextnodes[i].attr("id") == n.attr("id")) {
					hasNext = true;
					break;
				}
			}
			for ( var i = 0; i < prevnodes.length; i++) {
				if (prevnodes[i].attr("id") == obj.attr("id")) {
					hasPrev = true;
					break;
				}
			}
			if (!hasNext) {
				nextnodes.push(n);
			}
			if (!hasPrev) {
				prevnodes.push(obj);
			}
		};
		node.getNextNodes = function() {
			var obj = $(this);
			var nextnodes = obj[0].nextnodes;
			return nextnodes;
		};
		node.getPrevNodes = function() {
			var obj = $(this);
			var prevnodes = obj[0].prevnodes;
			return prevnodes;
		};
	};
	this.drawStart = function(x, y, info) {
		var node = $('<div node start noWrap class="node start_node"><img src="/module/flowdesigner/start_node.png" />'
				+ info.prcsName + "["+info.opUserName+"]</div>");
		node.attr("id", info.runProcessId);
		node.data("data", info);
		node.css({
			left : x+"px",
			top : y+"px"
		});
		node.appendTo(designer);
		addNodeExtraMethod(node);
		nodes.push(node);
		return node;
	};
	this.drawEnd = function(x, y, info) {
		var node = $('<div node end noWrap class="node end_node"><img src="/module/flowdesigner/end_node.png" />'
				+ info.prcsName + "</div>");
		node.attr("id", info.runProcessId);
		node.data("data", info);
		node.css({
			left : x+"px",
			top : y+"px"
		});
		node.appendTo(designer);
		addNodeExtraMethod(node);
		nodes.push(node);
		return node;
	};
	this.drawSimpleNode = function(x, y, info) {
	    var prcsName=info.prcsName;
		var node = $('<div node simple noWrap class="node simple_node"><img src="/module/flowdesigner/simple_node.png"/>'+ info.prcsName+ "["+info.opUserName+"]</div>");
		node.attr("id", info.runProcessId);
		node.data("data", info);
		node.css({
			left : x+"px",
			top : y+"px"
		});
		node.appendTo(designer);
		addNodeExtraMethod(node);
		nodes.push(node);
		return node;
	};
	this.drawAggregationNode = function(x, y, info) {
		var node = $('<div node aggregation noWrap class="node aggregation_node"><img src="/module/flowdesigner/aggregation_node.png" />'
				+ info.prcsName + "</div>");
		node.attr("id", info.runProcessId);
		node.data("data", info);
		node.css({
			left : x+"px",
			top : y+"px"
		});
		node.appendTo(designer);
		addNodeExtraMethod(node);
		nodes.push(node);
		return node;
	};
	this.drawParallelNode = function(x, y, info) {
		var node = $('<div  node parallel noWrap class="node parallel_node"><img src="/module/flowdesigner/parallel_node.png" />'
				+ info.prcsName + "</div>");
		node.attr("id", info.runProcessId);
		node.data("data", info);
		node.css({
			left : x+"px",
			top : y+"px"
		});
		node.appendTo(designer);
		addNodeExtraMethod(node);
		nodes.push(node);
		return node;
	};
	this.drawChildNode = function(x, y, info) {
		var node = $('<div  node child noWrap class="node child_node"><img src="/module/flowdesigner/child_node.png" />'
				+ info.prcsName + "</div>");
		node.attr("id", info.runProcessId);
		node.data("data", info);
		node.css({
			left : x+"px",
			top : y+"px"
		});
		node.appendTo(designer);
		addNodeExtraMethod(node);
		nodes.push(node);
		return node;
	};
	this.doLineTo = function() {
		for ( var i = 0; i < nodes.length; i++) {
			var curNode = nodes[i];
			var nextNodes = curNode.getNextNodes();
			var info = curNode.data("data");
			var conditionModel = new Array();
//			if (info.conditionModel) {
//				conditionModel = eval("(" + info.conditionModel + ")")
//			}
			for ( var j = 0; j < nextNodes.length; j++) {
				try {
				var connect = jsPlumb.connect({
					source : curNode,
					target : nextNodes[j]
				}, aConnection);
				} catch (e) {
					//break;
				}
				var label = "联接";
				for ( var h = 0; h < conditionModel.length; h++) {
					try {
						if ((conditionModel[h].prcsTo + "") == ("" + nextNodes[j]
								.data("data").runProcessId)) {
							label = conditionModel[h].label;
							break;
						}
					} catch (e) {
						break;
					}
				}
				if (!label) {
					label = "";
				}
			}
		}
	};
	this.removeNode = function(node) {
		var id = node.attr("id");
		for ( var i = 0; i < nodes.length; i++) {
			var tmp = nodes[i];
			var nextnodes = tmp[0].nextnodes;
			for ( var j = 0; j < nextnodes.length; j++) {
				if (nextnodes[j].attr("id") == id) {
					tmp[0].nextnodes.splice(j, 1);
					break;
				}
			}
			if (tmp.attr("id") == id) {
				nodes.splice(i, 1);
			}
		}
		for ( var i = 0; i < nodes.length; i++) {
			var tmp = nodes[i];
			var prevnodes = tmp[0].prevnodes;
			for ( var j = 0; j < prevnodes.length; j++) {
				if (prevnodes[j].attr("id") == id) {
					tmp[0].prevnodes.splice(j, 1);
					break;
				}
			}
		}
		node.remove();
	};
}
(function() {
	$.fn.addNextNode = function(g) {
		var a = false;
		var b = false;
		var f = $(this);
		var e = g[0].prevnodes;
		try
			{
			var d = f[0].nextnodes;
			for ( var c = 0; c < d.length; c++) {
				if (d[c].attr("id") == g.attr("id")) {
					a = true;
					break;
				}
			}
			if (!a) {
				d.push(g);
			}
			}catch (e) {
				// TODO: handle exception
			}
		for ( var c = 0; c < e.length; c++) {
			if (e[c].attr("id") == f.attr("id")) {
				b = true;
				break;
			}
		}
		
		if (!b) {
			e.push(f);
		}
	};
	$.fn.getNextNodes = function(c) {
		var b = $(this);
		var a = b[0].nextnodes;
		return a;
	};
	$.fn.getPrevNodes = function() {
		var b = $(this);
		var a = b[0].prevnodes;
		return a;
	};
})();