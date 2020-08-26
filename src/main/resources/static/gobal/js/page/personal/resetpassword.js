$(function(){
	$(".js-resetpassword").unbind("click").click(function(){
		resetPassWord();
	});
});
function resetPassWord()
{
	if($("#newPassWord").val()!=$("#confirmPassWord").val())
		{
		top.layer.msg("两次密码输入不一致!请检查!");
		return;
		}else
		{
			var newPassWord = $("#newPassWord").val();
			if(newPassWord=="")
			{
				top.layer.msg("新密码不能为空!请检查!");
				return;
			}else
			{
				if(newPassWord.length<passWordLength)
					{
					 top.layer.msg("密码长度必须大于等于"+passWordLength+"位");
				     return;
					}
				if(passWordStrength=="1")
				{
					var pwdRegex = new RegExp('(?=.*[0-9])(?=.*[a-zA-Z]).{8,30}');
					
					 if (!pwdRegex.test(newPassWord)) {
					     top.layer.msg("您的密码复杂度太低（密码中必须包含字母、数字）,请重新设置!");
					     return;
					 }
				}
			}
			$.ajax({
				url : "/set/unitset/resetPassWord",
				type : "post",
				dataType : "json",
				data:{
					firstPassWord:$("#firstPassWord").val(),
					newPassWord:$("#newPassWord").val()
				},
				success : function(data) {
					if(data.status=="500")
					{
						console.log(data.msg);
					}else if(data.status=="100")
					{
						top.layer.msg(data.msg);
					}else
					{
						top.layer.msg(data.msg);
					}
				}
			})
		}
}