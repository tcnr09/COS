<?php

	session_start();
	$_SESSION["username"] = $_POST["username"];
	$_SESSION["password"] = $_POST["password"];

	$Username = $_POST["username"];
	$Password = $_POST["password"];
	$Bday = $_POST["bday"];
	$Sex = $_POST["sex"];

	require_once("dbtool.inc.php");

	$link = connection_sql();

	// $sql = "INSERT INTO members(ID , Username, Password, Bday, Sex) Values('','Tom','123456','2019/02/18','male')";

	$sql = "INSERT INTO members(ID , Username, Password, Bday, Sex) Values('','$Username','$Password','$Bday','$Sex')";

	//PS:無法使用==做判斷
	if(execute_sql($link,"my_db",$sql)){
		// echo "Register Successful";	
		echo "註冊成功";	

	}else{
		echo "Register Failed";

	}

?>