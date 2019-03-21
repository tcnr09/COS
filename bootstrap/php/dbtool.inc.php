<?php 
	// 表示允许任何域名跨域访问
	header('Access-Control-Allow-Origin:*');
	
	function connection_sql(){
		$link = mysqli_connect("localhost","owner","123456")
		or die("Can't connection Server ".mysqli_connect_error());

		mysqli_query($link,"SET NAMES UTF8");

		return $link;

	}

	function execute_sql($link,$dbname,$sql){
		mysqli_select_db($link,$dbname)
		or die("Can't link DB".mysqli_error($link));

		$result = mysqli_query($link,$sql);

		return $result;
	}




?>

