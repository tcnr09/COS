//Home Panel 
$( document ).on( "pagecreate", "#member", function() {
    $( document ).on( "swipeleft swiperight", "#member", function( e ) {
        // We check if there is no open panel on the page because otherwise
        // a swipe to close the left panel would also open the right panel (and v.v.).
        // We do this by checking the data that the framework stores on the page element (panel: open).
        if ( $( ".ui-page-active" ).jqmData( "panel" ) !== "open" ) {
            if ( e.type == "swipeleft" ) {
                $( "#myPanRight " ).panel( "open" );
            } else if ( e.type == "swiperight" ) {
                $( "#myPanLeft" ).panel( "open" );
            }
        }
    });
});

// varitable flag
var flag_user=false;
var flag_pawd=false;
var flag_repw=false;

// Start Function
$(function(){
	// LoginPanel function
	$("#mylogin").bind("click",loginFunc);

	//Input Function
	$("#account").bind("input propertychange",accountFunc);
	$("#accpasswd").bind("input propertychange",passwdFunc);
	$("#repasswd").bind("input propertychange",repasswdFunc);

	//Register Function
	$("#register").bind("click",registerFunc);
});

// Login Function
function loginFunc(){
	if($("#name").val()=="admin" && $("#passwd").val()=="123456"){
		alert("Login Successful !!!");
	}else{
		alert("Login Failed !!!");
	}
}
//Account
function accountFunc(){
	if($("#account").val().length < 3 || $("#account").val().length > 8 ){
		
		$("#member_regmsg").html("Warning:<br>Account length must between 3 to 8 words");
		$("#member_regmsg").css("background","orange");
		$("#account").css("background","red");
		flag_user=false;

	}else{
		$("#member_regmsg").html("Registered :<br>Display Register Data Details list <br>");
		$("#member_regmsg").css("background","grey");
		$("#account").css("background","black");
		flag_user=true;

	}
}

//Password
function passwdFunc(){
	if($("#accpasswd").val().length < 6 || $("#accpasswd").val().length > 12 ){
		
		$("#member_regmsg").html("Warning:<br>Password length must between 6 to 12 words");
		$("#member_regmsg").css("background","orange");
		$("#accpasswd").css("background","red");
		flag_pawd=false;

	}else{
		$("#member_regmsg").html("Registered :<br>Display Register Data Details list <br>");
		$("#member_regmsg").css("background","grey");
		$("#accpasswd").css("background","black");
		flag_pawd=true;

	}
}

//repassword
function repasswdFunc(){
	if($("#repasswd").val() != $("#accpasswd").val() ){
		
		$("#member_regmsg").html("Warning:<br>Re-Password Not To The Same Of Password");
		$("#member_regmsg").css("background","orange");
		$("#repasswd").css("background","red");
		flag_repw=false;

	}else{
		$("#member_regmsg").html("Registered :<br>Display Register Data Details list <br>");
		$("#member_regmsg").css("background","grey");
		$("#repasswd").css("background","black");
		flag_repw=true;

	}
}

//register
function registerFunc(){

	var name =$("#account").val();
	var pw =$("#accpasswd").val();
	
	var sex =$("#sex").val();
	var edu =$("#input[name='chkgrp']:checked").val();

	var interest = [];
	$.each($("input[name='intgrp']:checked"),
		function(){
			interest.push($(this).val());
		} 
	);

	$("#member_regmsg").css({
		"background-color":"#99E811" ,
		"color":"#purple" ,
		"font-size":"28px"
	});



	if( flag_user && flag_pawd && flag_repw ){
		
		//Regist message
		$("#member_regmsg").html(
				"<p>Name    	: "+ name +"</p>" +
				"<p>passwd  	: "+ pw +"</p>" +
				"<p>Sex  		: "+ sex +"</p>" +
				"<p>edu  		: "+ edu +"</p>" +
				"<p>Interest	: "+ interest.join(" , ")

			);

	}else{
		
		if(!flag_user){
			alert("Check UserName input data !");
		}
		if(!flag_pawd){
			alert("Check Password input data !");
		}
		if(!flag_repw){
			alert("Check Re-Password input data !");
		}

	}
}


















