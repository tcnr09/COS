<?php

  session_start();
  if(isset($_SESSION["username"])){
    echo '<script>alert("'.$_SESSION["username"].' Member Logined ");</script>';


  }else{
    echo '<script>alert("Need Login");location.href="member-login.php";</script>';
  }

?>


<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Member Read</title>

    <!-- Bootstrap -->
    <!-- <link href="css/bootstrap.min.css" rel="stylesheet"> -->
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <style>
    	.mt10{
    		margin-top: 10px;

    	}
    	.mr5{
    		margin-right: 5px;
    	}
    </style>


  </head>

  <body>
  	<div class="container">
  		<div class="row">
  			<h1 class="text-center">Member List</h1>
  			<div class="col-sm-8 col-sm-offset-2 mt10">
  				<table class="table">
			    	<thead class="text-center">
			    		<tr>
			    			<th>ID</th>
			    			<th>Username</th>
			    			<th>Password</th>
			    			<th>Bday</th>
			    			<th>Sex</th>
			    			<th>Create_time</th>
			    			<th>Update/Delete</th>
			    		</tr>
			    	</thead>
			    	<tbody id="member_list">
			    		<!-- <tr >
			    			<td>1</td>
			    			<td>Vicky</td>
			    			<td>123456</td>
			    			<td>2019/02/18</td>
			    			<td>Male</td>
			    			<td>2019/02/18</td>
			    			<td>
			    				<a href="#" class="btn btn-primary">Update</a>
			    				<a href="#" class="btn btn-primary">Delete</a>
			    			</td>
			    		</tr> -->
			    	</tbody>
			    </table>
  			</div>
  		</div>
  	</div>
    

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <!-- <script src="js/bootstrap.min.js"></script> -->
    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

     <script>
    	$(function(){
    		$.ajax({
    			type:"GET",
    			url:"http://192.168.60.109/COS_MemberAPI/php/readone-api.php",
    			dataType:"json",
    			success: showMember,
    			error:function(){
    				alert("Failed Member List");
    			}
    		});

        
    	});//END main

    	function showMember(data){
    		// console.log();

    		for (i = 0;i<data.length ; i++) {
    			strHtml="";
    			strHtml='<tr><td>'+data[i].ID+
    					'</td><td>'+data[i].Username+
    					'</td><td>'+data[i].Password+
    					'</td><td>'+data[i].Bday+
    					'</td><td>'+data[i].Sex+
    					'</td><td>'+data[i].Create_time+
    					'</td><td><a href="member-update.php?ID='+data[i].ID+'" class="btn btn-primary mr5">Update</a><a data-id ='+data[i].ID+' href="javascript:delFunc('+data[i].ID+')" class="btn btn-danger mr5">Delete</a></td>'+
    					'</td></tr>';

    			// console.log(strHtml);

    			$("#member_list").append(strHtml);    			
    		}
    	}//END showMember()

      function delFunc(id){
        if (confirm("R U Sure Del ?")) {
          // alert(id);
          location.href = './php/delete-api.php?ID='+id  ;

          
        } else {
          alert("You pressed Cancel!");
        }

      }//END DelFunc

      

    </script>

  </body>
</html>