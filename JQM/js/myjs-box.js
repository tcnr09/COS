$(function(){
		$("#name").bind("input propertychange" , check);
		$("#passwd").bind("input propertychange" , checkCSS);
		$("button").bind("click" , GetVal);

	});

	function check(){
		if($("#name").val().length < 6){
			$("#name").css("background-color","red");

		}else{
			$("#name").css("background-color","white");

		}			
	}

	function checkCSS(){
		if( $(".mycss").val().length < 5 ){
			$("#passwd").css("background-color","red");
		}else{
			$("#passwd").css("background-color","white");

		}	
	}

	function GetVal(){
		var array = [] ;
		var drink = [] ;
		// "input[name='chkgrp']:checked"不要有空格
		$.each( $("input[name='chkgrp']:checked"),
			function(){
				array.push($(this).val());
			} );
		alert("Array : " + array.join(" : "));
		alert($("input[name='radiog']:checked").val());

}
