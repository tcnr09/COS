<?php
// 1. 啟動 Session 
	session_start();
// 2. 清除所有已登記的 Session 變數
// 	session_unset();
// session_register("username");  
	include("member-CURD-api.php");
$_SESSION["username"]=$_POST['Username'];
 ?>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Document</title>
	<link rel="stylesheet" href="../css/jquery.mobile-1.4.5.min.css">
	<script src="../js/jquery-2.1.0.min.js"></script>
	<script src="../js/jquery.mobile-1.4.5.min.js"></script>
	<script>
		$(function(){
			$("#username").bind("input propertychange", function(){
				if($("#username").val().length < 5){
					$("#error_username").html("帳號不得少於5個字數");
					$("#error_username").css("background-color", "red");
					$("#error_username").css("color", "white");
				}else{
					$("#error_username").html("");
				}
			});

			$("#login_ok").bind("click", function(){
				$.ajax({
					type: "POST",
					url: "https://tcnr1620.000webhostapp.com/test/memberAPI/member-CURD-api.php",
					data: {selefunc_string:"query",username: $("#username").val(), password: $("#password").val()},
					success: login,
					error:function(){
						alert("登入api回傳失敗");
					}
				});
			});	
		});

		function login(data){
			if(data == 1){
				// location.href = "https://www.google.com.tw/webhp?tab=rw";
				location.href = "https://tcnr09.github.io/COS/bootstrap/COS_index.html";
			}else{
				alert(data);
			}
		}

	</script>
</head>
<body>
	<div data-role="page" id="home">
		<div data-role="header" data-position="fixed" id="home" data-theme="b">
			<h1>會員登入</h1>
		</div>
		<div role="main" class="ui-content">
			<div data-role="fieldcontain">
				<label for="username">帳號</label>
				<input type="text" name="username" id="username" value="<?php echo $_SESSION["username"]; ?>">
			</div>
			<div id="error_username"></div>
			<div data-role="fieldcontain">
				<label for="password">密碼</label>
				<input type="password" name="password" id="password">
			</div>
			<div class="ui-grid-a">
				<div class="ui-block-a">
					<a href="#" data-role="button" data-theme="b">取消</a>		
				</div>
				<div class="ui-block-b">
					<a href="#" data-role="button" data-theme="b" id="login_ok">登入</a>		
				</div>
			</div>
		</div>
		<div data-role="footer" data-position="fixed" data-theme="b">
			<h1>footer</h1>
		</div>
	</div>
</body>
</html>		