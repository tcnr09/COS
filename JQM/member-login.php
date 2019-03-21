<?php session_start();?>

<!DOCTYPE html>
<html lang="en">
<head>
	
	<meta charset="UTF-8"/>
	<!-- For Mobile monitor -->
	<meta name="viewport" content="width = device-width , initial-scale = 1">

	<!-- My style themes 20181215 by Castle -->
	<!-- http://themeroller.jquerymobile.com -->
	<link rel="stylesheet" href="themes/mystyle.min.css" />
	<link rel="stylesheet" href="themes/jquery.mobile.icons.min.css" />

	<!-- Jquery mobile import file -->
	<link rel="stylesheet" href="css/jquery.mobile-1.4.5.min.css">
	<script src="js/jquery-2.1.0.min.js"></script>
	<script src="js/jquery.mobile-1.4.5.min.js"></script>

	<title>Login</title>

	<script>
		$(function(){
			$("#username").bind("input propertychange", function(){
				if($("#username").val().length < 5){
					$("#err_user").html("帳號不得少於5個字數");
					$("#err_user").css("background-color","red");
					$("#err_user").css("color","white");

				}else{
					$("#err_user").html("");
				}

			});//END username FUNC


			$("#passwd").bind("input propertychange", function(){
				if($("#passwd").val().length < 6){
					$("#err_pw").html("密碼不得少於6個字數");
					$("#err_pw").css("background-color","red");
					$("#err_pw").css("color","white");

				}else{
					$("#err_pw").html("");
				}

			});//END password FUNC

			$("#login_ok").bind("click",function(){
				$.ajax({
					type:"POST",
					url:"http://192.168.60.109/COS_MemberAPI/php/login-api.php",
					data:{username: $("#username").val(), password: $("#passwd").val()},
					success:login,
					error:function(){
						alert("Login Failed");
					}

				});//END AJAX

			});//End Login_ok click

		});//END Main FUNC

		function login(data){
			// console.log(data);
			// alert(data);
			if(data == 1){
				location.href = "member-admin.php";

			}else if(data == 2){
				location.href = "member-read.php";

			}else{
				alert(data);

			}

		}//END Login Func
	</script>

</head>

<body>

	<!-- tile comment -->
	<div data-role = "page" id ="id" align="center" data-theme="c" data-fullscreen="true" >

	
		<!-- Header -->
		<div data-role="header" >
			
			<h1>Login : </h1>
			
			<a href="#" data-rel="back" class="ui-btn-left" data-icon="back" data-iconpos="notext" style="background: #3fa654" data-transition="turn" >
				Back
			</a>
			
			<a href="#" data-rel="external" class="ui-btn-right" data-icon="arrow-r"  style="background: #3fa654">
				next
			</a>

		</div>
		<!-- FIN Header -->
		
		<!-- Content -->
		<div data-role="main" class="ui-content">

			<div data-role="fieldcontain">
				<label for="username">Username</label>
				<!-- <input type="text" data-clear-btn="true"  placeholder="Hint" name="username" id="username" value='<?php echo $_SESSION["username"] ?>' />	 -->
				<input type="text" data-clear-btn="true"  placeholder="Hint" name="username" id="username" />	
			</div>
			<div id="err_user"></div>

			<div data-role="fieldcontain">
				<label for="passwd">PassWord</label>
				<!-- <input type="password" data-clear-btn="true"  placeholder="Hint" name="passwd" id="passwd" value='<?php echo $_SESSION["password"] ?>' /> -->
				<input type="password" data-clear-btn="true"  placeholder="Hint" name="passwd" id="passwd" />
			</div>
			<div id="err_pw"></div>

			<div class="ui-grid-a">
				<div class="ui-block-a"><a href="" data-role="button" id="cancle">Cencle</a></div>
				<div class="ui-block-b"><a href="#" data-role="button" id="login_ok">登陸</a></div>
			</div>
	
		</div>
		<!-- FIN Content -->


		<!-- Footer -->
		<div data-role="footer" data-position="fixed">
			
			<h1>
			Copyright notice <br>					
			</h1>

			<!-- 把 data-role="controlgroup" 属性和 data-type="horizontal|vertical" 规定是否水平或垂直组合按钮 -->
			<div data-role="controlgroup" data-type="horizontal">
				<a href="#" data-role="button">Btn 1</a>
				<a href="#" data-role="button">Btn 2</a>
				<a href="#" data-role="button">Btn 3</a>
			</div>

		</div>
		<!-- FIN Footer -->


	</div>

	
</body>
</html>

