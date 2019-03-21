		
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

			<div data-role="fieldcontain">
				<label for="passwd">PassWord</label>
				<input type="password" data-clear-btn="true"  placeholder="Hint" name="passwd" id="passwd" />				
			</div>
			
			<div data-role="fieldcontain">
				<label for="date">Date</label>
				<input type="date" data-clear-btn="true"  placeholder="Hint" name="date" id="date" />				
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

		