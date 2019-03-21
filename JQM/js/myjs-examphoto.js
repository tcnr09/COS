
 // Start Function 20190120 auther castle 
$(function(){

	// Get Json From Exam DB
	$.ajax({
		type:"GET",
		url:"http://localhost/MyWeb/php/getphotoset.php",
		dataType:"json",
		success:show,
		error:function(){

		}
	});

});

//Show data from GET EXAM DB 
function show(data){
	for (var i = 0, cnt = data.length ; i < cnt; i++) {
		
		$("#pic_"+i).html("<img src='images/hairdryer/0"+data[i].ID+".jpg' width='85%' class='photo_popup'><br> ");

		$("#pic_"+i).append("ID : "+data[i].ID+"<br>");
		$("#pic_"+i).append("Title : "+data[i].Title+"<br>");
		$("#pic_"+i).append("Price : "+data[i].price+"<br>");
		$("#pic_"+i).append("Description : "+data[i].description+"<br>");

	}
}

