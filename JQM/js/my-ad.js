//main functon
$(function(){
		setInterval(ad,2000);
		

});



// variable
var index = 0;  // 目前書籍的索引 
var adImg,adTitle,adLink; // 目前取得的圖檔名、標題名稱 

// 圖片
// var aryBookSrc = new Array("panda2.png","cat.png","fox.png");
var adAryImg = ["panda2.png","cat.png","fox.png"];
// 書名 var aryBookName = new Array
var adAryTitle = new Array("Panda","Cat","Fox");
// 書名 var aryBookName = new Array
var adAryLink = new Array("https://tw.yahoo.com","https://translate.google.com.tw","http://www.runoob.com");

// callback function
// advertisement 
function ad(){ 

		index--;
		if (index<0){index=2;} // 可循環 
		adImg="image/"+ adAryImg[index];
		adTitle=adAryTitle[index];
		adLink=adAryLink[index];
		//Alter attributes
		// 動態顯示圖片 
		$("#ad_img").attr("src",adImg);
		// 動態顯示名稱
		$("#ad_title").text(adTitle);
		// 動態link
		$("#ad_link").attr("href",adLink);

}