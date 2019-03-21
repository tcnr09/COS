<?php
	$ID = $_POST["ID"];
	$Username = $_POST["username"];
	$Password = $_POST["passwd"];
	$Bday = $_POST["bday"];
	$Sex = $_POST["sex"];
	$Mail = $_POST["mail"];
	$Tel = $_POST["tel"];

	require_once("dbtool.inc.php");

	$link = connection_sql();

	$sql ="UPDATE cos SET Username='$Username',Password='$Password',Bday='$Bday',Sex='$Sex',Sex='$Mail',Sex='$Tel' WHERE ID=$ID";

	if(execute_sql($link,"my_db",$sql) ){
		echo 1;//Record Updated successfully

	}else{
		echo "Error updating record : ".mysqli_error($link);

	}	

?>