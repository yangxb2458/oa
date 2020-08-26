(function () {  
    var priority = {  
        '*': 2,  
        '/': 2,  
        '+': 1,  
        '-': 1  
    }, strExpression2arrExpression = function (expression) {//字符串转换为数组  
        var arr = [];  
        for (var i = 0, s, t, l = expression.length; i < l; i++) {  
            s = expression.charAt(i);  
            if (isNaN(s)&&s!='.') {  
                arr.push(s);  
            } else {  
                t = s;  
                while (i < l) {  
                    s = expression.charAt(i + 1);  
                    if (!isNaN(s)||s=='.') {  
                        t += s;  
                        i++;  
                    } else {  
                        break;  
                    }  
                }  
                arr.push(parseFloat(t));  
            }  
        }  
        return arr;  
    }, infixExpression2prefixExpression = (function () { //将中缀表达式转换为前缀表达式  
        var s1 = [], s2 = [], operator = function (o) {  
            var last = s1[s1.length - 1];  
            if (s1.length == 0 || last == ')') {  
                s1.push(o);  
            } else if (priority[o] >= priority[last]) {  
                s1.push(o);  
            } else {  
                s2.push(s1.pop());  
                operator(o);  
            }  
        };  
        return function (arrExpression) {  
            s1.length = 0;  
            s2.length = 0;  
            for (var i = arrExpression.length - 1, o; i >= 0; i--) {  
                o = arrExpression[i]  
                if (!isNaN(o)) {  
                    s2.push(o);  
                } else {  
                    if (o == '+' || o == '-' || o == '*' || o == '/') {//运算符  
                        operator(o)  
                    } else {//括号  
                        if (o == ')') {//右括号  
                            s1.push(o)  
                        } else {//左括号  
                            var s = s1.pop();  
                            while (s != ')') {  
                                s2.push(s);  
                                s = s1.pop();  
                            }  
                        }  
                    }  
                }  
            }  
            if (s1.length > 0) {  
                while (s1[0] != undefined) {  
                    s2.push(s1.pop())  
                }  
            }  
            s1.length = 0;  
            return s2.slice();  
        }  
    })(), computePrefixExpression = (function () {  
        var s1 = [], result;  
        return function (prefixExpression) {  
            s1.length = 0;  
            //计算  
            while (prefixExpression.length > 0) {  
                var o = prefixExpression.shift();  
                if (!isNaN(o)) {  
                    s1.push(o);  
                } else {  
                    switch (o) {  
                        case '+':  
                        {  
                            result = s1.pop() + s1.pop();  
                            break;  
                        }  
                        case '-':  
                        {  
                            result = s1.pop() - s1.pop();  
                            break;  
                        }  
                        case '*':  
                        {  
                            result = s1.pop() * s1.pop();  
                            break;  
                        }  
                        case '/':  
                        {  
                            result = s1.pop() / s1.pop();  
                            break;  
                        }  
                    }  
                    s1.push(result);  
                }  
                //console.log(s2,s1)  
            }  
            //console.log(s1)  
            return s1[0];  
        }  
    })();  
    window.compute = function (expression) {  
        return computePrefixExpression(infixExpression2prefixExpression(strExpression2arrExpression(expression)));  
    }  
})();

/**
 * 人民币转大写金额
 * @param num
 * @returns
 */
function RMB(num) { // 转成人民币大写金额形式
	var str1 = '零壹贰叁肆伍陆柒捌玖'; // 0-9所对应的汉字
	var str2 = '万仟佰拾亿仟佰拾万仟佰拾元角分'; // 数字位所对应的汉字
	var str3; // 从原num值中取出的值
	var str4; // 数字的字符串形式
	var str5 = ''; // 人民币大写金额形式
	var i; // 循环变量
	var j; // num的值乘以100的字符串长度
	var ch1; // 数字的汉语读法
	var ch2; // 数字位的汉字读法
	var nzero = 0; // 用来计算连续的零值是几个
	num = Math.abs(num).toFixed(2); // 将num取绝对值并四舍五入取2位小数
	str4 = (num * 100).toFixed(0).toString(); // 将num乘100并转换成字符串形式
	j = str4.length; // 找出最高位
	if (j > 15) {
		return '溢出';
	}
	str2 = str2.substr(15 - j); // 取出对应位数的str2的值。如：200.55,j为5所以str2=佰拾元角分
	// 循环取出每一位需要转换的值
	for (i = 0; i < j; i++) {
		str3 = str4.substr(i, 1); // 取出需转换的某一位的值
		if (i != (j - 3) && i != (j - 7) && i != (j - 11) && i != (j - 15)) { // 当所取位数不为元、万、亿、万亿上的数字时
			if (str3 == '0') {
				ch1 = '';
				ch2 = '';
				nzero = nzero + 1;
			} else {
				if (str3 != '0' && nzero != 0) {
					ch1 = '零' + str1.substr(str3 * 1, 1);
					ch2 = str2.substr(i, 1);
					nzero = 0;
				} else {
					ch1 = str1.substr(str3 * 1, 1);
					ch2 = str2.substr(i, 1);
					nzero = 0;
				}
			}
		} else { // 该位是万亿，亿，万，元位等关键位
			if (str3 != '0' && nzero != 0) {
				ch1 = "零" + str1.substr(str3 * 1, 1);
				ch2 = str2.substr(i, 1);
				nzero = 0;
			} else {
				if (str3 != '0' && nzero == 0) {
					ch1 = str1.substr(str3 * 1, 1);
					ch2 = str2.substr(i, 1);
					nzero = 0;
				} else {
					if (str3 == '0' && nzero >= 3) {
						ch1 = '';
						ch2 = '';
						nzero = nzero + 1;
					} else {
						if (j >= 11) {
							ch1 = '';
							nzero = nzero + 1;
						} else {
							ch1 = '';
							ch2 = str2.substr(i, 1);
							nzero = nzero + 1;
						}
					}
				}
			}
		}
		if (i == (j - 11) || i == (j - 3)) { // 如果该位是亿位或元位，则必须写上
			ch2 = str2.substr(i, 1);
		}
		str5 = str5 + ch1 + ch2;
		if (i == j - 1 && str3 == '0') { // 最后一位（分）为0时，加上“整”
			str5 = str5 + '整';
		}
	}
	if (num == 0) {
		str5 = '零元整';
	}
	return str5;
}
/**
 */
function optStrProcess(model) {
	//var data_regx = /(?<=\().*(?=\))/;
	var data_regx = /\(([^)]*)\)/;
	var optstr = model.match(data_regx)[1];
	optstr = optstr.replace(/\+/g, ",").replace(/\-/g, ",").replace(/\*/g, ",")
	.replace(/\\/g, ",").replace(/\(/g, "").replace(/\)/g, ",").replace(/\[/g, "").replace(/\]/g, "").replace(/\{/g, "").replace(/\}/g, "");
	var optArr = optstr.split(",");
	return optArr;
}
function calculateOptStr(optStr, elArr) {
	for (var i = 0; i < elArr.length; i++) {
		if(isNaN(elArr[i])){
			var reg = new RegExp( "\{"+elArr[i]+"\}" , "g" );
		    optStr = optStr.replace(reg, $("input[title='" + elArr[i] + "']").val());
		}
	}
	return optStr;
}
function getAllOptStrForArr(optArr)
{
	var returnArr=[];
	for (var i = 0; i < optArr.length; i++) {
		returnArr.push($("input[title='" + optArr[i] + "']").val());
	}
	return returnArr;
}
function AVG(arr)
{
	for(var i=0;i<arr.length;i++)
		{
			if(arr[i]=="")
			{
					arr[i]=0;
			}
		}
	var sum=eval(arr.join("+"));
	return sum/arr.length;
}

function DAY(sDate1,sDate2){
	var time1 = Date.parse(new Date(sDate1));
	var time2 = Date.parse(new Date(sDate2));
	var nDays = parseInt((time2 - time1)/1000/3600/24);
	return nDays;
};

function HOUR(sDate1,sDate2){
	var time1 = Date.parse(new Date(sDate1));
	var time2 = Date.parse(new Date(sDate2));
	var nDays = parseInt((time2 - time1)/1000/3600);
	return nDays;
};

function DATE(sDate1,sDate2){
	var time1 = Date.parse(new Date(sDate1));
	var time2 = Date.parse(new Date(sDate2));
	var nDays = parseInt((time2 - time1)/1000);
	return getDuration(nDays);
};

function getDuration(second) {
	console.log(second);
    var days = Math.floor(second / 86400);
    var hours = Math.floor((second % 86400) / 3600);
    var minutes = Math.floor(((second % 86400) % 3600) / 60);
    var seconds = Math.floor(((second % 86400) % 3600) % 60);
    var duration = days + "天" + hours + "小时" + minutes + "分" + seconds + "秒";
    return duration;
}

function checkFormat(str) 
{ 
	if(/.*[\u4e00-\u9fa5]+.*$/.test(str)) 
	{
		return false;      
	}else if(/.*[a-zA-Z]+.*$/.test(str))
	{
		return false;
	}else{
		return true;
	}
} 
function calculateFunction(eName,model)
{
	if (model.indexOf("RMB") > -1) {
		//人民币大小写转换处理
		var optArr = optStrProcess(model);
		//var data_regx = /(?<=\().*(?=\))/;
		var data_regx = /\(([^)]*)\)/;
		var optstr = model.match(data_regx)[1];
		for (var i = 0; i < optArr.length; i++) {
			$("input[title='" + optArr[i] + "']").unbind("change").change(function() {
					var optevl = calculateOptStr(optstr, optArr);
					 if(checkFormat(optevl)) { 
						 $("input[name='" + eName + "']").val(RMB(compute(optevl)));
					 }else
					{
						 $("input[name='" + eName + "']").val(RMB(0));
					}
			});
		}
	}else if(model.indexOf("MIN") > -1)
	{
		//找出最小值如:MIN(标题1,标题2);
		var optArr = optStrProcess(model);
		//var data_regx = /(?<=\().*(?=\))/;
		var data_regx = /\(([^)]*)\)/;
		var optstr = model.match(data_regx)[1];
		for (var i = 0; i < optArr.length; i++) {
			$("input[title='" + optArr[i] + "']").unbind("change").change(function() {
					$("input[name='" + eName + "']").val(Math.min.apply(Math, getAllOptStrForArr(optArr)));
			});
		}
	}else if(model.indexOf("MAX") > -1)
	{
		//找出最大值如:MAX(标题1,标题2);
		var optArr = optStrProcess(model);
		//var data_regx = /(?<=\().*(?=\))/;
		var data_regx = /\(([^)]*)\)/;
		var optstr = model.match(data_regx)[1];
		for (var i = 0; i < optArr.length; i++) {
			$("input[title='" + optArr[i] + "']").unbind("change").change(function() {
					$("input[name='" + eName + "']").val(Math.max.apply(Math, getAllOptStrForArr(optArr)));
			});
		}
	}else if(model.indexOf("ABS") > -1)
	{
		//找出绝对值如:ABS(标题1-标题2);
		var optArr = ptstr.split(",");
		//var data_regx = /(?<=\().*(?=\))/;
		var data_regx = /\(([^)]*)\)/;
		var optstr = model.match(data_regx)[1];
		for (var i = 0; i < optArr.length; i++) {
			$("input[title='" + optArr[i] + "']").unbind("change").change(function() {
				try {
					$("input[name='" + eName + "']").val(Math.abs(compute(calculateOptStr(optstr, optArr))));
				} catch (e) {

				}
			});
		}
	}else if(model.indexOf("AVG") > -1)
	{
		var optArr = optStrProcess(model);
		//var data_regx = /(?<=\().*(?=\))/;
		var data_regx = /\(([^)]*)\)/;
		var optstr = model.match(data_regx)[1];
		for (var i = 0; i < optArr.length; i++) {
			$("input[title='" + optArr[i] + "']").unbind("change").change(function() {
					$("input[name='" + eName + "']").val(AVG(getAllOptStrForArr(optArr)));
			});
		}
	}else if(model.indexOf("DAY") > -1)
	{
		var optArr = optStrProcess(model);
		//var data_regx = /(?<=\().*(?=\))/;
		var data_regx = /\(([^)]*)\)/;
		var optstr = model.match(data_regx)[1];
		for (var i = 0; i < optArr.length; i++) {
			$("input[title='" + optArr[i] + "']").unbind("change").change(function() {
				var dataArr = getAllOptStrForArr(optArr);
					$("input[name='" + eName + "']").val(DAY(dataArr[0],dataArr[1]));
			});
		}
	}else if(model.indexOf("HOUR") > -1)
	{
		var optArr = optStrProcess(model);
		//var data_regx = /(?<=\().*(?=\))/;
		var data_regx = /\(([^)]*)\)/;
		var optstr = model.match(data_regx)[1];
		for (var i = 0; i < optArr.length; i++) {
			$("input[title='" + optArr[i] + "']").unbind("change").change(function() {
				var dataArr = getAllOptStrForArr(optArr);
					$("input[name='" + eName + "']").val(HOUR(dataArr[0],dataArr[1]));
			});
		}
	}else if(model.indexOf("DATE") > -1)
	{
		var optArr = optStrProcess(model);
		//var data_regx = /(?<=\().*(?=\))/;
		var data_regx = /\(([^)]*)\)/;
		var optstr = model.match(data_regx)[1];
		for (var i = 0; i < optArr.length; i++) {
			$("input[title='" + optArr[i] + "']").unbind("change").change(function() {
				var dataArr = getAllOptStrForArr(optArr);
					$("input[name='" + eName + "']").val(DATE(dataArr[0],dataArr[1]));
			});
		}
	}else if(model.indexOf("LIST") > -1)
	{
		
	}else {
		var optstr = model;
		model = model.replace(/\+/g, ",").replace(/\-/g, ",").replace(/\*/g, ",")
		.replace(/\\/g, ",").replace(/\(/g, "").replace(/\)/g, ",").replace(/\[/g, "").replace(/\]/g, "").replace(/\{/g, "").replace(/\}/g, "");
		var optArr = model.split(",");
		console.log(optArr);
		for (var i = 0; i < optArr.length; i++) {
			$("input[title='" + optArr[i] + "']").unbind("change").change(function() {
				var optevl = calculateOptStr(optstr, optArr);
				 if(checkFormat(optevl)) { 
					$("input[name='" + eName + "']").val(compute(optevl));
				 }else
					{
					 $("input[name='" + eName + "']").val("0");
					}
			});
		}
	}
}