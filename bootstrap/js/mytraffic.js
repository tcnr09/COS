
$(function(){
	setInterval(myTraffic,1000);
});


var flag =1;

function myTraffic(){

	switch(flag){
		case 1:
			$("#led01").css( "background-color","red");
			$("#led02").css( "background-color","black");
			$("#led03").css( "background-color","black");
			flag++;
			break;

		case 2:
			$("#led01").css( "background-color","black");
			$("#led02").css( "background-color","yellow");
			$("#led03").css( "background-color","black");
			flag++;
			break;

		case 3:
			$("#led01").css( "background-color","black");
			$("#led02").css( "background-color","black");
			$("#led03").css( "background-color","green");
			flag = 1 ;
			break;

	}

}