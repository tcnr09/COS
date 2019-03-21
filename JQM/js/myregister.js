

var flag_user=false;
var flag_pswd=false;
var flag_repswd = false;
var flag_email=false;

$(function(){
	// input on propertychange event function 
	$("#username").bind("input propertychange",userFunc);
	$("#passwd").bind("input propertychange",passwdFunc);
	$("#repasswd").bind("input propertychange",repswdFunc);
	$("#email").bind("input propertychange",emailFunc);


	// Get Login Button click event
	$("#login").bind("click",loginFunc);
	$("#logout").bind("click",logoutFunc);


});


//check user name
function userFunc(){
	if( $("#username").val().length < 3 ){
		$("#message").text("Warning:UserName less more 3 words !!!");
		$("#message").css("background","orange");
		flag_user=false;

	}else{
		$("#message").text("");
		$("#message").css("background","white");
		flag_user=true;
	}
}

//check password
function passwdFunc(){
	if( $("#passwd").val().length < 8 ){
		$("#message").text("Warning:Password less more 8 words !!!");
		$("#message").css("background","orange");
		flag_pswd=false;

	}else{
		$("#message").text("");
		$("#message").css("background","white");
		flag_pswd=true;
	}
}

//check re-password
function repswdFunc(){
	if( $("#repasswd").val() != $("#passwd").val() ){
		$("#message").text("Warning:Re-Password is not the same password !!!");
		$("#message").css("background","orange");
		flag_repswd=false;

	}else{
		$("#message").text("");
		$("#message").css("background","white");
		flag_repswd=true;
	}
}

//check email
function emailFunc(){
	if( $("#email").val().length < 10 ){
		$("#message").text("Warning:E-Mail less more 10 words !!!");
		$("#message").css("background","orange");
		flag_email=false;

	}else{
		$("#message").text("");
		$("#message").css("background","white");
		flag_email=true;
	}
}

//login function
function loginFunc(){
	if(flag_user&&flag_pswd&&flag_repswd&&flag_email){
		//Confirm message
		if(confirm("Are U Sure Insert SQL Data ?")){
			$.ajax({
				type:"POST",
				url:"http://172.24.177.220/MyWeb/php/myregister.php",
				data:{
					user:$("#username").val(),
					passwd:$("#passwd").val(),
					email:$("#email").val()
				},
				success: showMsg,
				error:function(){
					alert("Failed login !");
				}

			});//end ajax

		}//end if

	}else{
		if(!flag_user){
			alert("Check UserName input data !");
		}
		if(!flag_pswd){
			alert("Check Password input data !");
		}
		if(!flag_repswd){
			alert("Check Re-Password input data !");
		}
		if(!flag_email){
			alert("Check E-mail input data !");
		}
	}
}

//show dat from SQL data
function showMsg(data){
	alert(data);
}

//logout function
function logoutFunc(){
	
}