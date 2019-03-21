<?php

	$ID = $_GET["ID"];

	require_once("dbtool.inc.php");

	$link = connection_sql();

	$sql = "DELETE FROM cos WHERE id = '$ID'";

	if (execute_sql($link,"my_db",$sql)) {
	    echo '<script>location.href="../member-admin.php";</script>'; //Delete Successful
	} else {
	    echo 0; //Delete Failed
	}


?>

