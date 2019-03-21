

$(function(){
	$.ajax({
		type:"GET",
		url:"http://localhost/MyWeb/php/demo.php",
		dataType:"json",
		success:show,
		error:function() {
			// body...
			alert("Error Data");
		}
	});

});


function show(data){
	for (var i =0 ,cnt = data.length ; i < cnt; i++) {
		$("#msgid").append(data[i].ID+"<br>");
		$("#msgna").append(data[i].UserName+"<br>");
		$("#msgpw").append(data[i].Password+"<br>");
		$("#msgag").append(data[i].Age+"<br>");
	}
}
