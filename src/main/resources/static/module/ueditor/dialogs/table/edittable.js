/**
 * Created with JetBrains PhpStorm.
 * User: xuheng
 * Date: 12-12-19
 * Time: 下午4:55
 * To change this template use File | Settings | File Templates.
 */
(function () {
    var title = $G("J_title"),
        titleCol = $G("J_titleCol"),
        caption = $G("J_caption"),
        sorttable = $G("J_sorttable"),
        autoSizeContent = $G("J_autoSizeContent"),
        autoSizePage = $G("J_autoSizePage"),
        tone = $G("J_tone"),
        tdtone = $G("J_tdcolor"),
        borderWidth = $G("J_border"),
        selectWidth = $G("J_selectWidth"),
        selectHeight = $G("J_selectHeight"),
        tableWidth = $G("J_tableWidth"),
        tableHeight = $G("J_tableHeight"),
        widthUnit = $G("J_widthUnit"),
        heightUnit = $G("J_heightUnit"),
        colWidth = $G("J_colWidth"),
        selectCol = $G("J_selectCol"),
        colgroup,
        maxcol,
        me,
        start = editor.selection.getStart(),
        cell = start && domUtils.findParentByTagName(start, ["td", "th"], true),
        ut = UE.UETable.getUETable(cell),
        table = domUtils.findParentByTagName(cell, "table", true),
        preview = $G("J_preview"),
        colorHex = {"aliceblue":"#f0f8ff","antiquewhite":"#faebd7","aqua":"#00ffff","aquamarine":"#7fffd4","azure":"#f0ffff","beige":"#f5f5dc","bisque":"#ffe4c4","black":"#000000","blanchedalmond":"#ffebcd","blue":"#0000ff","blueviolet":"#8a2be2","brown":"#a52a2a","burlywood":"#deb887","cadetblue":"#5f9ea0","chartreuse":"#7fff00","chocolate":"#d2691e","coral":"#ff7f50","cornflowerblue":"#6495ed","cornsilk":"#fff8dc","crimson":"#dc143c","cyan":"#00ffff","darkblue":"#00008b","darkcyan":"#008b8b","darkgoldenrod":"#b8860b","darkgray":"#a9a9a9","darkgreen":"#006400","darkgrey":"#a9a9a9","darkkhaki":"#bdb76b","darkmagenta":"#8b008b","darkolivegreen":"#556b2f","darkorange":"#ff8c00","darkorchid":"#9932cc","darkred":"#8b0000","darksalmon":"#e9967a","darkseagreen":"#8fbc8f","darkslateblue":"#483d8b","darkslategray":"#2f4f4f","darkslategrey":"#2f4f4f","darkturquoise":"#00ced1","darkviolet":"#9400d3","deeppink":"#ff1493","deepskyblue":"#00bfff","dimgray":"#696969","dimgrey":"#696969","dodgerblue":"#1e90ff","firebrick":"#b22222","floralwhite":"#fffaf0","forestgreen":"#228b22","fuchsia":"#ff00ff","gainsboro":"#dcdcdc","ghostwhite":"#f8f8ff","gold":"#ffd700","goldenrod":"#daa520","gray":"#808080","green":"#008000","greenyellow":"#adff2f","grey":"#808080","honeydew":"#f0fff0","hotpink":"#ff69b4","indianred":"#cd5c5c","indigo":"#4b0082","ivory":"#fffff0","khaki":"#f0e68c","lavender":"#e6e6fa","lavenderblush":"#fff0f5","lawngreen":"#7cfc00","lemonchiffon":"#fffacd","lightblue":"#add8e6","lightcoral":"#f08080","lightcyan":"#e0ffff","lightgoldenrodyellow":"#fafad2","lightgray":"#d3d3d3","lightgreen":"#90ee90","lightgrey":"#d3d3d3","lightpink":"#ffb6c1","lightsalmon":"#ffa07a","lightseagreen":"#20b2aa","lightskyblue":"#87cefa","lightslategray":"#778899","lightslategrey":"#778899","lightsteelblue":"#b0c4de","lightyellow":"#ffffe0","lime":"#00ff00","limegreen":"#32cd32","linen":"#faf0e6","magenta":"#ff00ff","maroon":"#800000","mediumaquamarine":"#66cdaa","mediumblue":"#0000cd","mediumorchid":"#ba55d3","mediumpurple":"#9370db","mediumseagreen":"#3cb371","mediumslateblue":"#7b68ee","mediumspringgreen":"#00fa9a","mediumturquoise":"#48d1cc","mediumvioletred":"#c71585","midnightblue":"#191970","mintcream":"#f5fffa","mistyrose":"#ffe4e1","moccasin":"#ffe4b5","navajowhite":"#ffdead","navy":"#000080","oldlace":"#fdf5e6","olive":"#808000","olivedrab":"#6b8e23","orange":"#ffa500","orangered":"#ff4500","orchid":"#da70d6","palegoldenrod":"#eee8aa","palegreen":"#98fb98","paleturquoise":"#afeeee","palevioletred":"#db7093","papayawhip":"#ffefd5","peachpuff":"#ffdab9","peru":"#cd853f","pink":"#ffc0cb","plum":"#dda0dd","powderblue":"#b0e0e6","purple":"#800080","red":"#ff0000","rosybrown":"#bc8f8f","royalblue":"#4169e1","saddlebrown":"#8b4513","salmon":"#fa8072","sandybrown":"#f4a460","seagreen":"#2e8b57","seashell":"#fff5ee","sienna":"#a0522d","silver":"#c0c0c0","skyblue":"#87ceeb","slateblue":"#6a5acd","slategray":"#708090","slategrey":"#708090","snow":"#fffafa","springgreen":"#00ff7f","steelblue":"#4682b4","tan":"#d2b48c","teal":"#008080","thistle":"#d8bfd8","tomato":"#ff6347","turquoise":"#40e0d0","violet":"#ee82ee","wheat":"#f5deb3","white":"#ffffff","whitesmoke":"#f5f5f5","yellow":"#ffff00","yellowgreen":"#9acd32"};
    var editTable = function () {
        me = this;
        me.init();
        me.createCol();
    };
    editTable.prototype = {
        init:function () {
            var colorPiker = new UE.ui.ColorPicker({
                    editor:editor
                }),
                colorPop = new UE.ui.Popup({
                    editor:editor,
                    content:colorPiker
                });
            var colorPiker2 = new UE.ui.ColorPicker({
                    editor:editor
                }),
                colorPop2 = new UE.ui.Popup({
                    editor:editor,
                    content:colorPiker2
                });

            title.checked = editor.queryCommandState("inserttitle") == -1;
            titleCol.checked = editor.queryCommandState("inserttitlecol") == -1;
            caption.checked = editor.queryCommandState("insertcaption") == -1;
            sorttable.checked = editor.queryCommandState("enablesort") == 1;

            var enablesortState = editor.queryCommandState("enablesort"),
                disablesortState = editor.queryCommandState("disablesort");

            sorttable.checked = !!(enablesortState < 0 && disablesortState >=0);
            sorttable.disabled = !!(enablesortState < 0 && disablesortState < 0);
            sorttable.title = enablesortState < 0 && disablesortState < 0 ? lang.errorMsg:'';

            me.createTable(title.checked, titleCol.checked, caption.checked);
            me.setWidthSize();
            me.setColor(me.getColor('td'),'td');
            me.setColor(me.getColor('table'),'table');
            me.setBorder(me.getBorder());
            me.setWidth(me.getWidth());
            me.setHeight(me.getHeight());

            domUtils.on(title, "click", me.titleHanler);
            domUtils.on(titleCol, "click", me.titleColHanler);
            domUtils.on(caption, "click", me.captionHanler);
            domUtils.on(sorttable, "click", me.sorttableHanler);
            domUtils.on(autoSizeContent, "click", me.autoSizeContentHanler);
            domUtils.on(autoSizePage, "click", me.autoSizePageHanler);
            domUtils.on(selectWidth, "click", me.selectWidthHanler);
            domUtils.on(selectCol, "click", me.selectColHanler);

            domUtils.on(tone, "click", function () {
                colorPop.showAnchor(tone);
            });
            domUtils.on(document, 'mousedown', function () {
                colorPop.hide();
            });
            colorPiker.addListener("pickcolor", function () {
                me.setColor(arguments[1]);
                colorPop.hide();
            });
            colorPiker.addListener("picknocolor", function () {
                me.setColor("");
                colorPop.hide();
            });
            domUtils.on(tdtone, "click", function () {
                colorPop2.showAnchor(tdtone);
            });
            domUtils.on(document, 'mousedown', function () {
                colorPop2.hide();
            });
            colorPiker2.addListener("pickcolor", function () {
                me.setColor(arguments[1],'td');
                colorPop2.hide();
            });
            colorPiker2.addListener("picknocolor", function () {
                me.setColor("",'td');
                colorPop2.hide();
            });

        },
        createCol:function(){
            var maxrow = ut.rowsNum,
                sizeMap = [];
            maxcol = ut.colsNum;
            var map = ut.getTableMap(table);
            colgroup = table.getElementsByTagName('colgroup');
            var createColgroup = colgroup.length == 0;
            if(!table.style.width && !table.getAttribute('width')){
                //table.style.width = table.offsetWidth+'px';
                table.setAttribute('width', '100%');
            }
            colgroup = createColgroup ? document.createElement('colgroup') : colgroup[0];
            var showColsSize = table.showColsSize = table.insertRow(table.rows.length);
            while (showColsSize.cells.length < maxcol) {
                var sizeCell = showColsSize.insertCell();
                colgroup.getElementsByTagName('col')[showColsSize.cells.length - 1] || colgroup.appendChild(document.createElement('col'))
            }
            for (var i = 0; i < maxcol; i++) {
                var col = colgroup.getElementsByTagName('col')[i],
                    reseted = false,
                    num = i+1,
                    li = document.createElement('li'),
                    input = document.createElement('input');
                li.innerHTML = '<span>第'+num+'列：</span><label><input type="radio" name="autoCol'+i+'" value="1" checked>自适应</label><label><input type="radio" name="autoCol'+i+'" value="2">指定宽度：</label>';
                for (var j = 0; j < maxrow; j++) {
                    var celler = map[j][i];
                    if (!reseted) {
                        try {
                            sizeMap[i] = UE.UETable.getWidth(showColsSize.cells[i]);
                            col.style.width = i == (maxcol - 1) ? 'auto' : sizeMap[i] + 'px';
                            input.value = sizeMap[i];
                            input.className = "col-width";
                            input.disabled = true;
                            reseted = true;
                        } catch (e) {}
                    }
                    UE.dom.domUtils.removeStyle(celler,'width');
                    celler.removeAttribute('width');
                }
                li.appendChild(input);
                colWidth.appendChild(li);
            }
            createColgroup && table.insertBefore(colgroup,table.children[0]);
            table.showColsSize.parentNode.removeChild(table.showColsSize);

            for (var i = 0; i < maxcol; i++) {
                domUtils.on(document.getElementsByName("autoCol"+i)[0],'click',function(){
                    var inputvalue = this.parentNode.nextSibling;
                    inputvalue.disabled = true;
                });
                domUtils.on(document.getElementsByName("autoCol"+i)[1],'click',function(){
                    var inputvalue = this.parentNode.nextSibling;
                    inputvalue.disabled = false;
                });
            };
        },
        createTable:function (hasTitle, hasTitleCol, hasCaption) {
            var arr = [],
                sortSpan = '<span>^</span>';
            arr.push("<table id='J_example' border='1'>");
            if (hasCaption) {
                arr.push("<caption>" + lang.captionName + "</caption>")
            }
            if (hasTitle) {
                arr.push("<tr>");
                if(hasTitleCol) { arr.push("<th>" + lang.titleName + "</th>"); }
                for (var j = 0; j < 5; j++) {
                    arr.push("<th>" + lang.titleName + "</th>");
                }
                arr.push("</tr>");
            }
            for (var i = 0; i < 6; i++) {
                arr.push("<tr>");
                if(hasTitleCol) { arr.push("<th>" + lang.titleName + "</th>") }
                for (var k = 0; k < 5; k++) {
                    arr.push("<td>" + lang.cellsName + "</td>")
                }
                arr.push("</tr>");
            }
            arr.push("</table>");
            preview.innerHTML = arr.join("");
            this.updateSortSpan();
        },
        titleHanler:function () {
            var example = $G("J_example"),
                frg=document.createDocumentFragment(),
                color = domUtils.getComputedStyle(domUtils.getElementsByTagName(example, "td")[0], "border-color"),
                colCount = example.rows[0].children.length;

            if (title.checked) {
                example.insertRow(0);
                for (var i = 0, node; i < colCount; i++) {
                    node = document.createElement("th");
                    node.innerHTML = lang.titleName;
                    frg.appendChild(node);
                }
                example.rows[0].appendChild(frg);

            } else {
                domUtils.remove(example.rows[0]);
            }
            me.setColor(color);
            me.updateSortSpan();
        },
        titleColHanler:function () {
            var example = $G("J_example"),
                color = domUtils.getComputedStyle(domUtils.getElementsByTagName(example, "td")[0], "border-color"),
                colArr = example.rows,
                colCount = colArr.length;

            if (titleCol.checked) {
                for (var i = 0, node; i < colCount; i++) {
                    node = document.createElement("th");
                    node.innerHTML = lang.titleName;
                    colArr[i].insertBefore(node, colArr[i].children[0]);
                }
            } else {
                for (var i = 0; i < colCount; i++) {
                    domUtils.remove(colArr[i].children[0]);
                }
            }
            me.setColor(color);
            me.updateSortSpan();
        },
        captionHanler:function () {
            var example = $G("J_example");
            if (caption.checked) {
                var row = document.createElement('caption');
                row.innerHTML = lang.captionName;
                example.insertBefore(row, example.firstChild);
            } else {
                domUtils.remove(domUtils.getElementsByTagName(example, 'caption')[0]);
            }
        },
        sorttableHanler:function(){
            me.updateSortSpan();
        },
        autoSizeContentHanler:function () {
            var example = $G("J_example");
            example.removeAttribute("width");
            tableWidth.disabled = true;
            widthUnit.disabled = true;
        },
        autoSizePageHanler:function () {
            var example = $G("J_example");
            var tds = example.getElementsByTagName(example, "td");
            utils.each(tds, function (td) {
                td.removeAttribute("width");
            });
            example.setAttribute('width', '100%');
            tableWidth.disabled = true;
            widthUnit.disabled = true;
        },
        selectWidthHanler:function(){
            tableWidth.disabled = false;
            widthUnit.disabled = false;
        },
        selectColHanler:function(){
            if(selectCol.checked){
                colWidth.style.display = "block";
            }
            else{
                colWidth.style.display = "none";
            }
        },
        getWidth: function(){
            var width = table && domUtils.getComputedStyle(table, "width");
            if (!width)  width = "";
            return width;
        },
        setWidth: function(width){
            if(width){
                tableWidth.value = parseFloat(width)-1;
            }
        },
        setTableWidth: function(){
            var re = /[0-9]+$/gi,
                width = tableWidth.value,
                unit = widthUnit.options[widthUnit.selectedIndex].value;

            if(re.test(width)){
                width = parseInt(width)+1;
                unit == "px"?table.style.width = width+"px":table.style.width = width+"%";
                return width;
            }
            else{
                return 0;
            }
        },
        getHeight: function(){
            var height = table && domUtils.getStyle(table, "height");
            if (!height)  height = "";
            return height;
        },
        setHeight: function(height){
            if(height){
                tableHeight.value = parseFloat(height);
            }
        },
        setTableHeight: function(){
            var re = /[0-9]+$/gi,
                unit = heightUnit.value,
                unit = heightUnit.options[heightUnit.selectedIndex].value;
            height = tableHeight.value;
            if(re.test(height)){
                height = parseInt(height);
                unit == "px"?table.style.height = height+"px":table.style.height = height+"%";
                return height;
            }
            else{
                return 0;
            }
        },

        updateSortSpan: function(){
            var example = $G("J_example"),
                row = example.rows[0];

            var spans = domUtils.getElementsByTagName(example,"span");
            utils.each(spans,function(span){
                span.parentNode.removeChild(span);
            });
            if (sorttable.checked) {
                utils.each(row.cells, function(cell, i){
                    var span = document.createElement("span");
                    span.innerHTML = "^";
                    cell.appendChild(span);
                });
            }
        },
        convertColor:function(color){
            if(/^#/.test(color))
                return color;
            else{
                try{
                    return me.convertColor(colorHex[color]);
                }catch(e){
                    return '#000000';
                }

            }
        },
        getColor:function (element) {
            if(element == "td"){
                color = cell && domUtils.getComputedStyle(cell, "border-color");
            }
            else{
                color = cell && domUtils.getComputedStyle(table, "border-color");
            }
            if(color.toLowerCase() == 'currentcolor'){
                color = '#000000';
            }
            if (!color) color = "#DDDDDD";
            color = me.convertColor(color);
            return color;
        },
        setColor:function (color,element) {
            var example = $G("J_example"),
                arr = domUtils.getElementsByTagName(example, "td").concat(
                    domUtils.getElementsByTagName(example, "th"),
                    domUtils.getElementsByTagName(example, "caption")
                );


            if(element === 'td'){
                tdtone.value = color;
                utils.each(arr, function (node) {
                    domUtils.setAttributes(node,{
                        style:"*border:1px solid "+color+";border-color:"+color
                    });
                });
            }else{
                tone.value = color;
                domUtils.setStyle(example,"border-color",color);
            }

        },
        getBorder:function () {
            border = table && domUtils.getComputedStyle(table, "border-width");
            if(border=='medium') {
                border = '1';
            }
            border = parseInt(border);
            /[0-9]+/.test(border)?border:border = "1";
            return border;
        },
        setBorder:function (border) {
            var example = $G("J_example"),
                arr = domUtils.getElementsByTagName(example, "td").concat(
                    domUtils.getElementsByTagName(example, "th"),
                    domUtils.getElementsByTagName(example, "caption")
                );
            borderWidth.value = parseInt(border);
            domUtils.setStyle(example,"border",border+'px solid '+color);

        },
        setBorderWidth: function(){
            var re = /[0-9]+$/gi,
                border = borderWidth.value;
            if(border == "") border = 1;
            if(re.test(border)){
                table.setAttribute("border",border);
                table.removeAttribute('bordercolor');
                domUtils.setStyle(table,'border',border+'px solid '+tone.value);
                return border;
            }
            else{
                return 0;
            }
        },
        setWidthSize:function () {
            var me = this;
            //autoSizePage.checked = true;
            //me.autoSizePageHanler();
            selectWidth.checked = true;
        }
    };

    new editTable;

    dialog.onok = function () {
        if(selectWidth.checked){
            if(me.setTableWidth()==0){
                alert('请输入宽度值');
                tableWidth.value = '';
                return false;
            }
            if(!selectCol.checked){
                table.removeChild(colgroup)
            }
        }
        if(me.setBorderWidth()<0){
            alert('请输入有效数值');
            borderWidth.value = '';
            return false;
        }
        if(tableHeight.value){
            if(me.setTableHeight()==0){
                alert('请输入高度值');
                tableHeight.value = '';
                return false;
            }
        }
        if(selectCol.checked){
            var total = 0;
            var flag = 1;
            var col;
            var input;
            for (var i = 0; i < maxcol; i++) {
                col = document.getElementsByName("autoCol"+i)[1];
                input = col.parentNode.nextSibling;
                if(input.disabled == false){
                    total += parseInt(colWidth.getElementsByTagName('li')[i].getElementsByTagName('input')[2].value);
                    colgroup.getElementsByTagName('col')[i].style.width = input.value+'px';
                }
                else{
                    flag = 0;
                    colgroup.getElementsByTagName('col')[i].style.width = 'auto';
                }

            }
            if(selectWidth.checked){
                if(flag && total > me.setTableWidth()){
                    alert("列宽与表格宽度不一致，请重新输入");
                    return false;
                }
            }
            else if(flag){
                table.style.width = total+'px';
            }
        }
        editor.__hasEnterExecCommand = true;

        var checks = {
            title:"inserttitle deletetitle",
            titleCol:"inserttitlecol deletetitlecol",
            caption:"insertcaption deletecaption",
            sorttable:"enablesort disablesort"
        };
        editor.fireEvent('saveScene');
        for(var i in checks){
            var cmds = checks[i].split(" "),
                input = $G("J_" + i);
            if(input["checked"]){
                editor.queryCommandState(cmds[0])!=-1 &&editor.execCommand(cmds[0]);
            }else{
                editor.queryCommandState(cmds[1])!=-1 &&editor.execCommand(cmds[1]);
            }
        }
        editor.execCommand("edittable", tdtone.value,editor);
        autoSizeContent.checked ?editor.execCommand('adaptbytext') : "";
        //autoSizePage.checked ? editor.execCommand("adaptbywindow") : "";
        if(autoSizePage.checked){
            UE.dom.domUtils.removeStyle(table,'width');
            editor.execCommand("adaptbywindow");
        }
        editor.fireEvent('saveScene');

        editor.__hasEnterExecCommand = false;
    };
})();