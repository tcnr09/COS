var book_id=0;

var myurl="http://localhost/MyWeb/php/getfoodinfo.php";


function getFoodInfo(mode){

	$.get(	
			//URL
			myurl,
			//Data
			{book_id:book_id , mode:mode},
			//Success function
			function(data){
				
				book_id=data.book_id;
				
				//image
				$img ="http://localhost/MyWeb/images/food/"+data.image_name;
				$("#bookimg").attr("src",$img);
				
				//message
				$("#bookmsg").html(data.description+"<br>");
				$("#bookmsg").append("Debug :" + $img);

			},
			//type
			"json"
		);

}