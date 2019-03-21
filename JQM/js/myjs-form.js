
	// Variable flag to determine state
	var flag_name = false;
	var flag_account = false;
	var flag_passwd = false;
	var flag_idno = false;
	var flag_date = false;


	$(function(){
		// $('#login').bind('click',showmsg); 
		$('#myname').bind('input propertychange',check_name);
		$('#myaccount').bind('input propertychange',check_account);
		$('#mypasswd').bind('input propertychange',check_passwd); 
		$('#myidno').bind('input propertychange',check_idno); 
		$('#list').bind('click',listfunc); 
		$('#cancel').bind('click',cancelfunc); 
		// $('#datebtn').bind('click',birthfunc); 

	});
	

	function showmsg(){ 

		alert( $("#account").val() +"\n"+ $("#passwd").val() );
	}


	function check_name(){ 

		if($("#myname").val().length < 2 ){
			$("#msg_name").text("Account is less than two words !!!");

			// 提示：17 种标准色 : 
			// aqua, black, blue, fuchsia, 
			// gray, green, lime, maroon, 
			// navy, olive, orange, purple, 
			// red, silver, teal, white, yellow。


			// $("#msg_account").css("background-color","red");
			// $("#msg_account").css("color","green");
			// $("#msg_account").css("font-size","28px");

			$("#msg_name").css({
				"background-color":"red" ,
				"color":"green" ,
				"font-size":"28px"
			});

			flag_name = false;
		
		}else if($("#myname").val().length >= 2){
			$("#msg_name").text("");
			flag_name = true;

		}
	
	}

	function check_account(){ 

		if($("#myaccount").val().length < 5 ){
			$("#msg_myaccount").text("Account is less than five words !!!");

			// 提示：17 种标准色 : 
			// aqua, black, blue, fuchsia, 
			// gray, green, lime, maroon, 
			// navy, olive, orange, purple, 
			// red, silver, teal, white, yellow。


			// $("#msg_account").css("background-color","red");
			// $("#msg_account").css("color","green");
			// $("#msg_account").css("font-size","28px");

			$("#msg_myaccount").css({
				"background-color":"orange" ,
				"color":"red" ,
				"font-size":"28px"
			});
			flag_account = false;
		
		}else if($("#myaccount").val().length >= 5){
			$("#msg_myaccount").text("");
			flag_account = true;

		}
	
	}

	function check_passwd(){ 

		if($("#mypasswd").val().length < 8 ){
			$("#msg_pwd").text("Password is less than eight words !!!");
			$("#msg_pwd").css("background-color","purple");
			$("#msg_pwd").css("color","olive");
			$("#msg_pwd").css("font-size","28px");
			flag_passwd = false;
		
		}else if($("#mypasswd").val().length >= 8){
			$("#msg_pwd").text("");
			flag_passwd = true;

		}
		
	}

	function check_idno(){ 

		if($("#myidno").val().length != 10 ){
			$("#msg_idno").text("Your Id No. is not equal ten words !!!");
			$("#msg_idno").css("background-color","blue");
			$("#msg_idno").css("color","white");
			$("#msg_idno").css("font-size","28px");
			flag_idno = false;
		
		}else {
			$("#msg_idno").text("");
			flag_idno = true;

		}
		
	}

	function check_age(){ 

		if($("#age").val().length > 2 ){
			$("#msg_age").text("Age is more than two words !!!");

			// 提示：17 种标准色 : 
			// aqua, black, blue, fuchsia, 
			// gray, green, lime, maroon, 
			// navy, olive, orange, purple, 
			// red, silver, teal, white, yellow。

			$("#msg_age").css({
				"background-color":"red" ,
				"color":"green" ,
				"font-size":"28px"
			});
		
		}else if($("#age").val().length < 3 ){
			$("#msg_age").text("");

		}
	
	}

	function bmifunc(){
		var bmi = $("#weight").val()  / Math.pow($("#height").val(),2) * 10000 ;
		// bmi.toFixed(2);
		
	 	// 狀態						BMI (kg/m2)		BMI 	素數
		//  最低	最高	最低	最高
		//  非常嚴重的體重不足						15		0.60
		//  嚴重體重不足				15				16		0.60	0.64
		//  體重過輕					16				18.5	0.64	0.74
		//  體重正常 (健康體重)		18.5			25		0.74	1.0
		//  體重過重					25				30		1.0		1.2
		//  肥胖I級（中等肥胖）		30				35		1.2		1.4
		//  肥胖II級（嚴重肥胖）		35				40		1.4		1.6
		//  肥胖III級（非常嚴重肥胖）					40		1.6	 

		//alert(  "BMI : \n"+ bmi.toFixed(2) );
		//$("#message").text("Your BMI : " + bmi.toFixed(2));
		
		// $("#message").text("Your BMI : " + bmi.toFixed(2));
		if( bmi < 15){
			$("#message").text("Your BMI :\t"+ bmi.toFixed(2)+" \t 非常嚴重的體重不足" );
			$("#message").css("color","red");
		}else if( bmi>18 && bmi < 25){
			$("#message").text("Your BMI :\t"+ bmi.toFixed(2)+" \t 體重正常 (健康體重)" );
			$("#message").css("color","green");
		}else if(bmi > 30){
			$("#message").text("Your BMI :\t"+ bmi.toFixed(2)+" \t 非常嚴重的肥胖" );
			$("#message").css("color","blue");
		}else{
			$("#message").text("Your BMI : " + bmi.toFixed(2));
		}

		$("#message").css("background-color","orange");	
		$("#message").css("font-size","28px");

	}

	function cancelfunc(){
		var name = $("#myname").val();
		var account = $("#myaccount").val();
		var passwd = $("#mypasswd").val();
		var date = $("#date").val();

		var sex = $("#sex").val();
		var height = $("#height").val();
		var weight = $("#weight").val();
		var idno = $("#myidno").val();

		var select = $("#select").val();
		var blood_type = $("input[name='blood']:checked").val();
		var edu = $("input[name='edug']:checked").val();
		var interest = [] ;
		// "input[name='chkgrp']:checked"不要有空格
		$.each( $("input[name='interestg']:checked"),
			function(){
				interest.push($(this).val());	
			} 
		);

		$("#info").css({
			"height": "512px",
			"background-color":"blue" ,
			"color":"white" ,
			"font-size":"28px"
		});


		// Debug mode
		var stmp = 
			"Name    	: "+ name +"\r\n" +
			"Account 	: "+ account +"\r\n" +
			"passwd  	: "+ passwd +"\r\n" +
			"Birthday  	: "+ date +"\r\n" +

			"Sex  	: "+ sex +"\r\n" +
			"height  	: "+ height +"cm\r\n" +
			"weight  	: "+ weight +"Kg\r\n" +
			"idno  	: "+ idno +"\r\n" +

			"Select  	: "+ select +"\r\n" +
			"blood_type : "+ blood_type +"\r\n" +
			"edu  	: "+ edu +"\r\n" +
			"Leisure	: "+ interest.join(" , ");
		$("#info").append(stmp);
	}

	function birthfunc(){
		alert("BirthDay : " + $("#day").val() );
	}

	function set_color(){
		var red = $("#red_slider").val();
		var green = $("#green_slider").val();
		var blue = $("#blue_slider").val();
		var color = "RGB(" + red +","+ green + "," + blue +")";

		$("#colordemo").css("background-color",color);
	}

	function set_sex(){
		
		alert($("#slider").val());
	}

	function set_onoff(){
		var color = $("#setOF").val();
		$("div").css("background-color",color);
	}

	
	


	function listfunc(){

		var name = $("#myname").val();
		var account = $("#myaccount").val();
		var passwd = $("#mypasswd").val();
		var date = $("#date").val();

		var sex = $("#sex").val();
		var height = $("#height").val();
		var weight = $("#weight").val();
		var idno = $("#myidno").val();

		var select = $("#select").val();
		var blood_type = $("input[name='blood']:checked").val();
		var edu = $("input[name='edug']:checked").val();
		var interest = [] ;
		// "input[name='chkgrp']:checked"不要有空格
		$.each( $("input[name='interestg']:checked"),
			function(){
				interest.push($(this).val());	
			} 
		);

		
		$("#message").css({
			"background-color":"#99E811" ,
			"color":"#C38A32" ,
			"font-size":"28px"
		});

		
		
		
		var date = $("#date").val().length;

		if(date != 10 ){
			flag_date = false;
		}else{
			flag_date = true;
		}


		if(flag_name && flag_account && flag_passwd && flag_idno && flag_date){
			// $("#message").text( //for textarea use stmp 
			$("#message").html(
				"<p>Name    	: "+ name +"</p>" +
				"<p>Account 	: "+ account +"</p>" +
				"<p>passwd  	: "+ passwd +"</p>" +
				"<p>Birthday  	: "+ date +"</p>" +

				"<p>Sex  		: "+ sex +"</p>" +
				"<p>height  	: "+ height +"cm</p>" +
				"<p>weight  	: "+ weight +"Kg</p>" +
				"<p>idno 		: "+ idno +"</p>" +

				"<p>Select  	: "+ select +"</p>" +
				"<p>blood_type : "+ blood_type +"</p>" +
				"<p>edu  		: "+ edu +"</p>" +
				"<p>Leisure	: "+ interest.join(" , ")

			);

			

		}else{
			alert("Please check list item !!!")
		}






		
	}




