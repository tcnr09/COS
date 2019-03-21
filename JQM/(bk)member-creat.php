		
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

	<title>Member Update</title>

	<script>
	$(function(){
		$("#username").bind("input propertychange",function(){
			if($("#username").val().length < 5){
				$("#err_user").html("帳號不得少於5個字數");
				$("#err_user").css("background-color","red");
				$("#err_user").css("color","white");

			}else{
				$("#err_user").html("");
			}
		});

		$("#passwd").bind("input propertychange",function(){
			if($("#passwd").val().length < 8){
				$("#err_pw").html("密碼不得少於8個字數");
				$("#err_pw").css("background-color","red");
				$("#err_pw").css("color","white");

			}else{
				$("#err_pw").html("");
			}
		});// end function()


		$("#reg_ok").bind("click", function(){
				$.ajax({
					type: "POST",
					url: "http://192.168.60.109/MemberAPI/php/creat-api.php",
					data: {username: $("#username").val(), 
							password: $("#passwd").val(), 
							bday: $("#date").val(), 
							sex: $("#sex").val() },
					success: reg,
					error:function(){
						alert("註冊api回傳失敗");
					}
				}); // end ajax
			});

	});//END Main FUNC
	function reg(data){
		
		if(data = "註冊成功"){
			location.href = "member-login.php";

		}else{
			alert(data);
		}

	}//END Reg Func

</script>

</head>


<body>

	<!-- tile comment -->
	<div data-role = "page" id ="id" align="center" data-theme="c" data-fullscreen="true" >
		
		<!-- Header -->
		<div data-role="header" >
			
			<h1>Header : </h1>
			
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
				<input type="text" data-clear-btn="true"  placeholder="Hint" name="username" id="username" />				
			</div>
			<div id="err_user"></div>

			<div data-role="fieldcontain">
				<label for="passwd">PassWord</label>
				<input type="password" data-clear-btn="true"  placeholder="Hint" name="passwd" id="passwd" />				
			</div>
			<div id="err_pw"></div>
			
			<div data-role="fieldcontain">
				<label for="date">Date</label>
				<input type="date" data-clear-btn="true"  placeholder="Hint" name="date" id="date" />				
			</div>


			<div data-role="fieldcontain">
				<!-- Select option -->
				<label for="sex">Sex</label>
				<select name="sex" id="sex" data-role="slider">
					<option value="男生" >Male</option>
					<option value="女生" >FeMale</option>				
							
				</select>					
			</div>

			<div data-role="fieldcontain">
				<label for="mail">E-Mail</label>
				<input type="email" data-clear-btn="true"  placeholder="Hint" name="mail" id="mail" />				
			</div>
			<div data-role="fieldcontain">
				<label for="tel">Tel</label>
				<input type="tel" data-clear-btn="true"  placeholder="Hint" name="tel" id="tel" />				
			</div>
			
			

			<div class="ui-grid-a">
				<div class="ui-block-a"><a href="#" data-role="button" id="cancle">取消</a></div>
				<div class="ui-block-b"><a href="#" data-role="button" id="reg_ok">註冊</a></div>
				
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

		