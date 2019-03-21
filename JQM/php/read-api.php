<?php
	require_once("dbtool.inc.php");

	$link = connection_sql();

	$sql ="SELECT * FROM cos ORDER BY ID DESC";

	$result = execute_sql($link,"my_db",$sql);
	$row = mysqli_fetch_assoc($result);

	$memberArray = Array();

	if(mysqli_num_rows($result) >0){

		do{
			$memberArray[] = $row;

		}while ($row = mysqli_fetch_assoc($result));

		echo json_encode($memberArray);

	}else{
		echo "No Data";

	}

?>