
$(function(){

	$("#idprev").bind('click',prevFunc);
	$("#idnext").bind('click',nextFunc);

});

var index = 0;

var myImgSrc,myText;

var aryImgSrc = new Array("cal.png", "calt.png", "ok.png");

var aryImgName = new Array("This Calculator","This is Robot","This is Ok PNG");

function prevFunc(){
	index--;
	if( index < 0 ){index = 2 ;}
	myImgSrc = "images/myicon/"+aryImgSrc[index];
	myText = aryImgName[index];
	$("#pimg").attr("src",myImgSrc);
	$("#pname").text(myText);
}

function nextFunc(){
	index++;
	if( index > 2 ){index = 0 ;}
	myImgSrc = "images/myicon/"+aryImgSrc[index];
	myText = aryImgName[index];
	$("#pimg").attr("src",myImgSrc);
	$("#pname").text(myText);
}
