<!DOCTYPE html>

<html lang="en">

<head>

	<meta charset="UTF-8">

	<meta name="viewport" content="width=device-width,initial-scale=1">

	<title>Document</title>

	<link rel="stylesheet" type="text/css" href="css/jquery.mobile-1.4.5.min.css">

	<script src="js/jquery-2.1.0.min.js"></script>

	<script src="js/jquery.mobile-1.4.5.min.js"></script>

	<script>

		var usernameFlag,passwordFlag,repwdFlag;

		$(function(){

			// 帳號

			$("#username").bind("input propertychange", function(){

				if($("#username").val().length < 5){

					$("#error_username").html("帳號不得少於5個字數");

					$("#error_username").css("background-color", "red");

					$("#error_username").css("color", "white");

					usernameFlag=false;

				}else{

					$("#error_username").html("");

					usernameFlag=true;

				}

			});

			// 密碼

			$("#password").bind("input propertychange",function(){

				if($("#password").val().length < 8){

					$("#error_password").html("密碼不得少於8個字數");

					$("#error_password").css("background-color", "red");

					$("#error_password").css("color", "white");

					passwordFlag=false;

				}else{

					$("#error_password").html("");

					passwordFlag=true;

				}

			});

			// 確認密碼

			$("#repwd").bind("input propertychange",function(){

				if($("#repwd").val() != $("#password").val()){

					$("#error_repwd").html("密碼不一樣");

					$("#error_repwd").css("background-color", "red");

					$("#error_repwd").css("color", "white");

					repwd=false;

				}else{

					$("#error_repwd").html("");

					repwd=true;

				}

			});



			$("#reg_ok").bind("click", function(){

				$.ajax({

					type: "POST",

					url: "https://tcnr1620.000webhostapp.com/JQM/php/member-CURD-api.php",

					data: {selefunc_string:"insert",Username: $("#username").val(), Password: $("#password").val(), Bday: $("#bday").val(), Sex: $("#sex").val(), Mail: $("#mail").val(), Tel: $("#tel").val() },

					success: reg,

					error:function(){

						alert("註冊api回傳失敗");

					}

				}); // end ajax

			});

		}); // end function()



	

		function reg(data){

			if(data == 1){

				location.href = "https://tcnr1620.000webhostapp.com/JQM/member-login.php";

			}else{

				alert(data);

			}										

		}



	// if(usernameFlag==true && passwordFlag==true && repwdFlag==true){

	// }else{

	// 	if(!usernameFlag)

	// 		alert("帳號不合規定");

	// 	if(!passwordFlag)

	// 		alert("密碼不合規定");

	// 	if(!repwdFlag)

	// 		alert("確認密碼不合規定");

	// 	alert("請注意填寫完整");

	// }



	</script>

</head>

<body>

	<!-- home -->

	<div data-role="page" id="home">

		<div data-role="header" data-position="fixed" id="home" data-theme="b">

			<h1>會員註冊</h1>

		</div>

		<div role="main" class="ui-content">

			<!-- 文字欄位,帳號: -->

			<div data-role="fieldcontain">

				<label for="username">帳號 : </label>

				<input type="text" name="username" id="username">

			</div>

			<div id="error_username"></div>

			<!-- 文字欄位,密碼: -->

			<div data-role="fieldcontain">

				<label for="password">密碼 : </label>

				<input type="password" name="password" id="password">

			</div>

			<div id="error_password"></div>

			<!-- 文字欄位,確認密碼: -->

			<div data-role="fieldcontain">

				<label for="repwd">確認密碼 : </label>

				<input type="password" name="repwd" id="repwd" value="" >

			</div>

			<div id="error_repwd"></div>

			<div data-role="fieldcontain">

				<label for="bday">生日 : </label>

				<input type="date" name="bday" id="bday">

			</div>

			<!-- 文字欄位,信箱: -->

			<div data-role="fieldcontain">

				<label for="mail">信箱 : </label>

				<input type="text" name="mail" id="mail">

			</div>

			<!-- 文字欄位,電話: -->

			<div data-role="fieldcontain">

				<label for="tel">電話 : </label>

				<input type="text" name="tel" id="tel">

			</div>

			<div data-role="fieldcontain">

				<label for="sex">性別 : </label>

				<select name="sex" id="sex" data-role="slider">

					<option value="男">男生</option>

					<option value="女">女生</option>

				</select>	

			</div>

			<div class="ui-grid-a">

				<div class="ui-block-a">

					<a href="#" data-role="button" data-theme="b">取消</a>		

				</div>

				<div class="ui-block-b">

					<a href="#" data-role="button" data-theme="b" id="reg_ok">註冊</a>		

				</div>

			</div>

		</div>

		<div data-role="footer" data-position="fixed" data-theme="b">

			<h1>footer</h1>

		</div>

	</div>

</body>

</html>