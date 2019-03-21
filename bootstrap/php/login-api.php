<?php
	session_start();

	$Username=$_POST["username"];
	$Password=$_POST["password"];

	require_once("dbtool.inc.php");

	$link = connection_sql();

	// $sql = "SELECT * FROM members WHERE Username='Tom' AND Password='123456'";
	$sql = "SELECT * FROM cosAdmin WHERE Username='$Username' AND Password='$Password'";


	$result = execute_sql($link,"my_db",$sql);
	if(mysqli_num_rows($result) ==1){		
		$_SESSION["username"]=$_POST["username"];//無法放在ECHO後面
		echo "1";//admin Login Successful

	}else{

		$sql = "SELECT * FROM cos WHERE Username='$Username' AND Password='$Password'";

		$result = execute_sql($link,"my_db",$sql);

		if(mysqli_num_rows($result) ==1){		
			$_SESSION["username"]=$_POST["username"];//無法放在ECHO後面
			echo "2";//user Login Successful

		}else{
			echo "0";//Login Failed
		}
		
	}


?>