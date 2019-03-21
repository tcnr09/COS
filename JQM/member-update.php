<?php
	$member_id=$_GET["ID"];

	require_once("./php/dbtool.inc.php");

	$link = connection_sql();

	$sql ="SELECT * FROM cos WHERE ID = $member_id";

	$result = execute_sql($link,"my_db",$sql);

?>
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

	<title>Member</title>

</head>


<body>

	<!-- tile comment -->
	<div data-role = "page" id ="update" align="center" data-theme="c" data-fullscreen="true" >
		
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

			<?php
				if(mysqli_num_rows($result) == 1){
					$row = mysqli_fetch_assoc($result);
					echo $row["Username"];
				}else{
					echo "Update error";
				}
			?>

			<div data-role="fieldcontain">
				<label for="username">Username</label>
				<input type="text" data-clear-btn="true"  placeholder="Hint" name="username" id="username" value="<?php echo $row["Username"]; ?>" />				
			</div>
			<div id="err_user"></div>

			<div data-role="fieldcontain">
				<label for="passwd">PassWord</label>
				<input type="password" data-clear-btn="true"  placeholder="Hint" name="passwd" id="passwd" value="<?php echo $row["Password"]; ?>"/>				
			</div>
			<div id="err_pw"></div>
			
			<div data-role="fieldcontain">
				<label for="date">Date</label>
				<input type="date" data-clear-btn="true"  placeholder="Hint" name="date" id="date" value="<?php echo $row["Bday"]; ?>"/>				
			</div>


			<div data-role="fieldcontain">
				<!-- Select option -->
				<label for="sex">Sex</label>
				<select name="sex" id="sex" data-role="slider" value="<?php echo $row["Sex"]; ?>">
					<option value="男生" >Male</option>
					<option value="女生" >FeMale</option>	
							
				</select>					
			</div>	

			<div data-role="fieldcontain">
				<label for="mail">E-Mail</label>
				<input type="email" data-clear-btn="true"  placeholder="Hint" name="mail" id="mail" value="<?php echo $row["Mail"]; ?>"/>				
			</div>
			<div data-role="fieldcontain">
				<label for="tel">Tel</label>
				<input type="tel" data-clear-btn="true"  placeholder="Hint" name="tel" id="tel" value="<?php echo $row["Tel"]; ?>"/>				
			</div>		

			<div class="ui-grid-a">
				<div class="ui-block-a"><a href="#" data-role="button" id="cancle">Cancle</a></div>
				<div class="ui-block-b"><a href="#" data-role="button" id="update_ok" data-id="<?php echo $row["ID"];  ?>">Update</a></div>
				
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

	<script>
		$(function(){

			$("#update_ok").bind("click",function(){
				// alert($(this).data("id"));
				$.ajax({
					type:"POST",
					url:"http://192.168.60.109/COS_MemberAPI/php/update-api.php",
					data:{ID: $(this).data("id"),
						username: $("#username").val(),
						passwd: $("#passwd").val(),
						bday: $("#date").val(),
						sex: $("#sex").val(),
						mail: $("#mail").val(),
						tel: $("#tel").val() },
					success: show_update,
					error:function(){
						alert( "Update Failed");

					}
				});//END AJAX
			});
		});//END Main

		function show_update(data){
			if(data == 1){
				location.href = "member-admin.php";
			}else{
				alert("update error");
			}
		}//END show_update(dta)

	</script>

	
</body>
</html>

		