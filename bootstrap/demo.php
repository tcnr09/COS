<?php 
	require_once("dbtool_inc_php");
	$link = connection();
	$sql = "SELECT * FROM userdata";
	$result = execute_sql($link,"MySQL01",$sql);
	if(mysqli_num_rows($result) > 0){
		

	}else{
		echo "No data";
	}

	mysqli_close($link);
 ?>

<?php
	header('Access-Control-Allow-Origin:*');

	function connection(){
		$link = mysqli_connect("localhost","owner","123456") or 
		die("Can't Connection DB server ".mysqli_connect_error());

		mysqli_query($link,"SET NAMES UTF8");

		return $link;
	}

	function execute_sql($link,$dbname,$sql){
		mysqli_select_db($link,$dbname) or die("Can't link DB ".mysqli_error($link));

		$result = mysqli_query($link,$sql);

		return $result;

	}


?>