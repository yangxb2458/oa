$(function(){
	$(".js-mobile-save").unbind("click").click(function(){
		setMobilePriv();
	})
	$.ajax({
		url : "/ret/sysget/getAllAppList",
		type : "post",
		dataType : "json",
		success : function(data) {
			if(data.status=="500")
				{
					console.log(data.msg);
				}else if(data.status=="100")
				{
					top.layer.msg(data.msg);
				}else
				{
					for(var i=0;i<data.list.length;i++)
					{
						var tmp=['<tr>',
							'	<td>'+(i+1)+'</td>',
							'	<td><img src="'+data.list[i].img+'"/></td>',
							'	<td>'+data.list[i].title+'</td>',
							'	<td>'+data.list[i].module+'</td>',
							'	<td>'+data.list[i].remark+'</td>',
							'       <td>',
							'       <label style="margin: 0px;">',
							'			<input name="privcheckbox" value="'+data.list[i].appId+'" class="checkbox-slider slider-icon colored-palegreen" type="checkbox">',
							'           <span class="text"></span>',
							'		</label>',
							'     </td>',
							' </tr>'].join("");
						$("#applist").append(tmp);
					}
					if(mobilePrivStr!=null&&mobilePrivStr!="")
					{
						var privArr = mobilePrivStr.split(",");
						for(var i=0;i<privArr.length;i++)
						{
							$("input:checkbox[name='privcheckbox'][value='" + privArr[i] + "']").attr("checked", "checked");
						}
					}
					
				}
		}
	});
})

function setMobilePriv()
{
	var privStr=getCheckBoxValue("privcheckbox");
	console.log(privStr);
	 $.ajax({
         url:"/set/unitset/updateUserPriv",
         dataType:"json",
         type:"post",
         data:{userPrivId:userPrivId,mobilePriv:privStr},
         async:false,
         error:function(e){
             alert(e.message);
         },
         success:function(data){
      		if(data.status==200){
					top.layer.msg(data.msg);
					location.reload();
				}else if(data.status==100)
				{
					top.layer.msg(data.msg);
				}else
					{
					console.log(data.msg);
					}
             }
     });
}