
$(function(){
	
	$('#callapp').bind('click',jsCallApp);
	

});


function jsCallApp(){
	// need android app just called 
	app.sendMessage("Js Called APP");
	alert("JS Called APP");

}

function appCallJS(){
	alert("APP Called JS");
}
