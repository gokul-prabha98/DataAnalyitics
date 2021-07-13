var client = 1;

$( document ).ready(function(e) {

	readLogin();

});

function readLogin()
{
	var userID = $.cookie("uid");
	var password = $.cookie("pass");

	if(validateData(userID) && validateData(password))
	{
		login(userID , password, true);
	}
}


function login(userid , pwd, remember)
{
	$.ajax({
		url : 'client-login?uid='+userid +'&pass='+pwd +'&remember='+remember,
		success: function(result)
		{
			var response = $.parseJSON(result);
			if(response.isErrorExist == true)
			{
				alert("Invalid login credential");
			}
			else
			{
				window.location.replace("home.jsp");
			}
		}
	});
}

$("#btn-submit").click(function() {
	var userid = $("#username").val();
	var pass = $("#password").val();
	var remember = $("#remember-me").is(":checked");
	if(validateData(userid) && validateData(pass))
		login(userid , pass , remember);
	else
		alert("invalid login credential..");

});
function validateData(data)
{
	if(data != null && data != "null" && data != "" )
		return true;
	return false;
}
