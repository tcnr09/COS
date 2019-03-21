//Home Panel 
$( document ).on( "pagecreate", "#home", function() {
    $( document ).on( "swipeleft swiperight", "#home", function( e ) {
        // We check if there is no open panel on the page because otherwise
        // a swipe to close the left panel would also open the right panel (and v.v.).
        // We do this by checking the data that the framework stores on the page element (panel: open).
        if ( $( ".ui-page-active" ).jqmData( "panel" ) !== "open" ) {
            if ( e.type == "swipeleft" ) {
                $( "#myPanRight " ).panel( "open" );
            } else if ( e.type == "swiperight" ) {
                $( "#myPanLeft" ).panel( "open" );
            }
        }
    });
});


// Start Function
$(function(){
	setInterval(myImgShowTime,5000);
	setInterval(myEAAShowTime,4000);
	setInterval(myLPDShowTime,3000);
	$("#mylogin").bind("click",loginFunc);

});


var index_img =1;
var imgsrc ="";
function myImgShowTime(){
		
	if(index_img > 12){
		index_img=1;
		imgsrc ="images/hairdryer/0"+index_img+".jpg";
	}else if(index_img > 9 ){
		imgsrc ="images/hairdryer/"+index_img+".jpg";
		
	}else{
		imgsrc ="images/hairdryer/0"+index_img+".jpg";
	}
	index_img++;
	
	$("#myimg").attr("src",imgsrc);
}

var flag_msg =1;
function myEAAShowTime(){

	switch(flag_msg){
		case 1:
			$("#eaa").text( "散文特展" );			
			flag_msg++;
			break;
		case 2:
			$("#eaa").text( "名人傳記特展");		
			flag_msg++;
			break;
		case 3:
			$("#eaa").text( "名畫特展");			
			flag_msg=1;
			break;
	}
}

var flag_sale =1;
function myLPDShowTime(){

	switch(flag_sale){
		case 1:
			$("#lpda").text( "大特價 A01" );			
			flag_sale++;
			break;
		case 2:
			$("#lpda").text( "大特價 A02");		
			flag_sale++;
			break;
		case 3:
			$("#lpda").text( "大特價 A03");		
			flag_sale++;
			break;
		case 4:
			$("#lpda").text( "大特價 A04");			
			flag_sale=1;
			break;
	}
}

// Login Function
function loginFunc(){
	if($("#name").val()=="admin" && $("#passwd").val()=="123456"){
		alert("Login Successful !!!");
	}else{
		alert("Login Failed !!!");
	}
}





