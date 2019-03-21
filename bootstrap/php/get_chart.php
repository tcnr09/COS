<?php
	require_once("dbtool.inc.php");

	$link = connection_sql();

	// $sql = "SELECT * FROM chart";

	$sql = "SELECT count(Addr) as countAddr ,Addr FROM  chart GROUP BY Addr";
	// $sql = "select count(Addr) as counts ,Addr from chart group by Addr";

	$result = execute_sql($link, "my_db", $sql);

	if(mysqli_num_rows($result) > 0){
		$myArray = array();
		while ($row = mysqli_fetch_assoc($result)) {
			# code...
			$myArray[] = $row;
		}
		echo json_encode($myArray);

	}else{
		echo "No Data";
	}

	mysqli_close($link);

?>