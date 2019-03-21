
var myPrevTimer;
var myNextTimer;
var index=1;
var indexPrev=0;


//Number Panel 
$( document ).on( "pagecreate", "#number", function() {
    $( document ).on( "swipeleft swiperight", "#number", function( e ) {
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


//main functon
$(function(){
	$("#no1").css("box-shadow","2px 2px 2px 2px #fa0");
	
	$("#showPrev").bind("click",function(){
		clearInterval(myPrevTimer);
		clearInterval(myNextTimer);
		myPrevTimer=setInterval(prevFunc,200);

	});

	$("#showNext").bind("click",function(){
		clearInterval(myPrevTimer);
		clearInterval(myNextTimer);
		myNextTimer=setInterval(nextFunc,200);

	});
		
});

function prevFunc(){ 

		index--;
		indexPrev = index + 1 ;

		if (index<1){
			index = 8;
			indexPrev = 1;
		} 

		imgsrc = "images/number/0"+index+".png";
		imgID = "#no"+index;
		imgTxt ="Licky number : "+index;

		//Clear shadow
		
		$("#no"+indexPrev).css("box-shadow","2px 2px 2px 2px #8f8");
		$("#mainimg").attr("src",imgsrc);
		$(imgID).css("box-shadow","2px 2px 2px 2px #fa0");
		$("#numberTxt").html(imgTxt);

}

function nextFunc(){ 

		index++;
		indexPrev = index - 1 ;

		if (index>8){
			index = 1;
			indexPrev = 8;
		} 

		imgsrc = "images/number/0"+index+".png";
		imgID = "#no"+index;
		imgTxt ="Licky number : "+index;

		//Clear shadow
		
		$("#no"+indexPrev).css("box-shadow","2px 2px 2px 2px #8f8");
		$("#mainimg").attr("src",imgsrc);
		$(imgID).css("box-shadow","2px 2px 2px 2px #fa0");
		$("#numberTxt").html(imgTxt);

}