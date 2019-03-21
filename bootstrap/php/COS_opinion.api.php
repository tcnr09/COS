<?php
// session_start();

// $_SESSION["username"]=$_POST["username"];


$Clean = $_POST["radio1"];
$Attitude = $_POST["radio2"];
$Food = $_POST["radio3"];
$Advise = $_POST["advise"];

require_once("dbtool.inc.php");
$link = connection_sql();

$sql = "INSERT INTO COS_opinion(ID, clean, attitude, food, advise) Values('', '$Clean', '$Attitude', '$Food', '$Advise')";

if(execute_sql($link, "COS_WebSite", $sql)){
	echo "1";
}else{
	echo "reg fail";
}

?>