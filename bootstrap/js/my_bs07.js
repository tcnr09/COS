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
		if(data[k]["Money"] > 500){
			j=4;

		}
		strHtml = "<tr class=\""+myArray[j] +"\">"+
						"<td data-th=\"ID : \"><span class=\"table-col\">"+data[k]["ID"]+"</span></td>"+
    					"<td data-th=\"Name : \"><span class=\"table-col\">"+data[k]["Name"]+"</span></td>"+
    					"<td data-th=\"Addr : \"><span class=\"table-col\">"+data[k]["Addr"]+"</span></td>"+
    					"<td data-th=\"Money : \"><span class=\"table-col\">"+data[k]["Money"]+"</span></td>"+
    					"<td data-th=\"date : \"><span class=\"table-col\">"+data[k]["date"]+"</span></td>"+
    				"</tr>";

	   	$("#my_tb").append(strHtml);

	}//END IFOR LOOP

    

}

