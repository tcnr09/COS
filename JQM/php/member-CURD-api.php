<?php

header("Access-Control-Allow-Origin:*");//接收

header("Last-Modified: " . gmdate( "D, d M Y H:i:s" ) . "GMT" );   

header("Cache-Control: no-cache, must-revalidate" );

// 1. 啟動 Session 

session_start();

// // 2. 清除所有已登記的 Session 變數

// session_unset();

// // 3. 銷毀現有的 Session連線紀錄

// session_destroy();

$selefunc=$_REQUEST['selefunc_string'];

switch($selefunc){

//--------選擇瀏覽-----------------------------------------

case "query":



require_once("dbtool.inc.php");





$link = connection_sql();

$Username = $_POST['username'];

$Password = $_POST['password'];

$sql ="SELECT Username FROM COSmember WHERE Username ='$Username' AND Password ='$Password' ";

$sql = stripslashes($sql);

$result = execute_sql($link, "id7769595_friends", $sql) or die('MySQL query error');



if (mysqli_num_rows($result) > 0) {

// output data of each row

 $_SESSION["username"] = $_POST["username"];

 $_SESSION["password"]=$_POST['password'];

 $_SESSION["Permission"]=1;

 echo "1"; 

 } else {

   echo "0 results";

 }

break;



//--------選擇新增-----------------------------------------

case "insert":

$Username=$_POST['Username'];

$Password=$_POST['Password'];

$Bday=$_POST['Bday'];

$Sex=$_POST['Sex'];

$Mail=$_POST['Mail'];

$Tel=$_POST['Tel'];



require_once("dbtool.inc.php");



$link = connection_sql();

$Username = $_POST['Username'];

$sql ="SELECT Username FROM COSmember WHERE Username ='$Username' ";

$sql = stripslashes($sql);

$result = execute_sql($link, "id7769595_friends", $sql) or die('MySQL query error');



if (mysqli_num_rows($result) > 0) {

// output data of each row

 echo "已有相同帳號"; 

 } else {

  $sql = "SELECT Username FROM COSmember WHERE Username = '$Username'";

  $sql = stripslashes($sql);

  $result = execute_sql($link, "id7769595_friends", $sql);

  $row = mysqli_fetch_assoc($result);

  if (mysqli_num_rows($result) > 0){

   echo "已有相同帳號";

  }else{

  	$link = connection_sql();

	$sql = "INSERT INTO COSmember (ID, Username, Password, Bday, Sex, Mail, Tel, Permission) Values(NULL, '$Username', '$Password', '$Bday', '$Sex', '$Mail', '$Tel', '1')";

	if(execute_sql($link, "id7769595_friends", $sql)){  

		$_SESSION["username"] = $_POST['Username'];

		echo "1";

	}else{

		echo "0";

	}

  }

 }

break;



case 'update':

	$Password=$_POST['password'];

	$Username=$_POST['username'];

	require_once("dbtool.inc.php");

	$link = connection_sql();

		$sql = "UPDATE COSmember SET Password = '$Password' WHERE Username = '$Username' ";

		if(execute_sql($link, "id7769595_friends", $sql)){

			echo "1";//修改成功

		}else{

			echo "0";//修改失敗

		}



	break;



case 'read':

	require_once("dbtool.inc.php");

	$link = connection_sql();





	//------管理者讀取所有會員資料------

		$sql = "SELECT * FROM COSmember ORDER BY ID ASC";

		$result = execute_sql($link, "id7769595_friends", $sql);

		$row = mysqli_fetch_assoc($result);

		$memberArray = Array();

		if(mysqli_num_rows($result) > 0){

			do{

				$memberArray[] = $row; 

			}while($row = mysqli_fetch_assoc($result));



			echo json_encode($memberArray);

		}else{

			echo "管理者讀取資料庫api失敗";

		}



	// --------------------------------------

	break;



case 'delete':

	require_once("dbtool.inc.php");

	$link = connection_sql();



	if($_SESSION["Permission"] == 1){

		$sql = "DELETE FROM cos WHERE ID = '$Member_id'";

		if(execute_sql($link, "classDB", $sql)){

			// echo '<script>location.href="20190218-member-read.php";</script>'; //刪除成功自動換頁

			echo 1;//刪除成功

		}else {

			echo 0; //刪除失敗

		}

	}

	

	break;



}//end switch





?>

