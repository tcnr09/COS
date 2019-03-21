$(function(){
    $.ajax({
        type:"GET",
        url:"http://localhost/Bootstrap/php/get_chart2.php",      
        dataType:"json",
        success:show,
        error:function(){
            alert("error Chart Get");
        }
    });//END AJAX


});

function show(data){
    // alert(data.length); 
    console.log(data);

    //動態LISTVIEW在超連結加入訊息傳遞
	strHtml = "<th class=\"mytxt\">ID</th>"+
			"<th class=\"mytxt\">Name</th>"+
	   		"<th class=\"mytxt\">Addr</th>"+
	   		"<th class=\"mytxt\">Money</th>"+
	   		"<th class=\"mytxt\">date</th>";

	$("#head").html(strHtml);
	$("#my_tb").html("");
	var myArray=new Array("success","info","warning","active","danger");

	for (var k =0; k < data.length; k++) {
		var j = k%5;
		strHtml = "<tr class=\""+myArray[j] +"\">"+
						"<td>"+data[k]["ID"]+"</td>"+
    					"<td>"+data[k]["Name"]+"</td>"+
    					"<td>"+data[k]["Addr"]+"</td>"+
    					"<td>"+data[k]["Money"]+"</td>"+
    					"<td>"+data[k]["date"]+"</td>"+
    				"</tr>";

	   	$("#my_tb").append(strHtml);

	}//END IFOR LOOP
    

}	