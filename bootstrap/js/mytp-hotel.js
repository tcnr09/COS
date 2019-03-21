//Main function 20190208 author by PJ
$(function(){
	$.ajax({
		type:"GET",
		url:"http://localhost/MyWeb/php/get_hotel.php",
		dataType:"json",
		success:show_list,
		error:function(){
			alert("Erroe Ajax Get Function");
		}
	});

	// Hotel Page 切换页面时自动刷新页面 
	// $("#hotelpage").bind("pageshow",AutoRefresh(3000));
	// $("#hotelpage").bind("pageshow",Refresh());




});

function show_list(data){
	//debug
	console.log(data.length);


	var regionTitle = new Array();
	var counter = new Array();
	var regionData = new Array();

		//资料结构分析处理
	for (var i = 0 ,cnt = data.length ; i < cnt ; i++) {

		// substring() 	提取字符串中两个指定的索引号之间的字符
		// indexOf() 	返回字符串中检索指定字符第一次出现的位置

		var getRegion = data[i]["display_addr"].substring(0,data[i]["display_addr"].indexOf("區",0)+1);

		// console.log(getRegion);
		if(counter[getRegion] == undefined){
			counter[getRegion] = regionData.length;
			regionData.push(new Array());
			regionTitle[counter[getRegion]] = getRegion;
		}
		regionData[counter[getRegion]].push(data[i]);

	}//End FOR LOOP

	// Creat ListView Item
	$("#list_region").empty();
	for (var j = 0 ; j < regionTitle.length; j++) {
		var hotel_name ="";
		var hotel_addr ="";
		var hotel_tel ="";
		var hotel_lat ="";
		var hotel_lng ="";
		
		
		//取得相關資料用於連結傳遞
		for (var k =0; k < regionData[j].length; k++) {
			hotel_name += regionData[j][k]["name"]+"|";
			hotel_addr += regionData[j][k]["display_addr"]+"|";
			hotel_tel += regionData[j][k]["tel"]+"|";
			//For Gmap langitube longitube
			hotel_lat += regionData[j][k]["Y"]+"|";
			hotel_lng += regionData[j][k]["X"]+"|";

		}
	

		//判断regionTitle如为非[区]字串则并入为[其他旅业]
		var html_str="旅館資料";
		if(regionTitle[j] == ""){
			regionTitle[j]="其他";
			html_str="旅业";

		}
		else{
			html_str="旅館資料";
		
		}
		//在超連結加入訊息傳遞
		//動態LISTVIEW在超連結加入訊息傳遞
		strHtml = "<li data-icon=\"location\">"+
					"<a href=\"#hotel_list\""+" regionTitle = \""+ regionTitle[j] +"\" hotel_name = \""+ hotel_name+"\" hotel_addr = \""+ hotel_addr +"\" hotel_tel = \""+ hotel_tel +"\" hotel_lat = \""+ hotel_lat +"\" hotel_lng = \""+ hotel_lng +"\">"+
						regionTitle[j]+" "+html_str+
						"<span class=\"ui-li-count\">"+ regionData[j].length+"</span>"+				
					"</a>"+
					"<a href=\"#hotel_gmap\""+" hotel_name = \""+ hotel_name+"\" hotel_addr = \""+ hotel_addr +"\" hotel_tel = \""+ hotel_tel +"\" hotel_lat = \""+ hotel_lat +"\" hotel_lng = \""+ hotel_lng +"\" id=\"map_link_"+j+"\" >"+						
					"</a>"+						
				"</li>";

		$("#list_region").append(strHtml);
		
	}//End of J loop

	//增加傳值監聽 four parameter
	$("a",$("#list_region")).bind("click",function(e){
		getItem($(this).attr("regionTitle"),$(this).attr("hotel_name"),$(this).attr("hotel_addr"),$(this).attr("hotel_tel"));
		
	});
	// 增加傳值監聽 Five Parameter
	// $("a",$("#output")).bind("click",function(e){
	// 	showGMap($(this).attr("hotel_name"),$(this).attr("hotel_addr"),$(this).attr("hotel_tel"),$(this).attr("hotel_lat"),$(this).attr("hotel_lng"));
		
	// });

	// 增加傳值監聽 For ID Five Parameter
	for (var k = 0 ; k < regionTitle.length; k++) {
		$("#map_link_"+k).bind("click",function(e){
			//For Gmaps
			showGMap($(this).attr("hotel_name"),$(this).attr("hotel_addr"),$(this).attr("hotel_tel"),$(this).attr("hotel_lat"),$(this).attr("hotel_lng"));
		});
	}

	//更新ListView
	$("#list_region").listview("refresh");

}//END OF Show_List Function


function getItem(regionTitle,hotel_name,hotel_addr,hotel_tel){
	// 顯示分區 Show Page - header 的標題 
	$("#list_head").html(regionTitle+"\t旅館");

	// Debug
	// console.log(regionTitle);
	// console.log("hotel_name : "+ hotel_name);
	// console.log("hotel_addr : "+ hotel_addr);
	// console.log("hotel_tel : "+ hotel_tel);

	//拆分資料
	var arrayName =  hotel_name.split("|");
	var arrayAddr =  hotel_addr.split("|");
	var arrayTel =  hotel_tel.split("|");
 	
 	//Debug
 	// console.log("arrayName : "+ arrayName.length);
 	$("#level2").empty();
	for (var i = 0; i < arrayName.length-1 ; i++) {
		//Debug
		// console.log("arrayName : "+ arrayName[i]);

		var strHtml = "<li data-icon=\"star\">"+
						
						"<a href=\"#\""+" hotel_addr = \""+ arrayAddr[i] +"\" >"+
							"<img src=\"images/myicon/flag.png\">"+
							"<h3 class=\"mytitle\">" + arrayName[i]+ "</h3>"+
							"<div><p> Addr : " + arrayAddr[i]+ "</p>"+
							"TEL : " + arrayTel[i] +"</div>"+
						"</a>"+
					"</li>";
		$("#level2").append(strHtml) ; 
 	
	}//END OF FOR LOOP
	
	//連結GOOGLE MAP 以地址搜尋旅館位置 
	$("a",$("#level2")).bind("click", function() {
		
		searchFor($(this).attr("hotel_addr"));
	
	});
	
	//更新ListView This have BUG ERROR
	$("#level2").listview("refresh");
	

}//END OF getItem Function

//當使用者按下旅館名稱時傳地址參數給SearchFor() 呼叫連結並傳遞地址參數!
function searchFor(addr){
	// window.open("http://maps.google.com/maps?hl=zh-TW&q=" + addr );

	//地址轉經緯度20190211 Author PJ
	$.ajax({
		type:"GET",
		url:"https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyAPgs9AL4JHkdrxqNSil0y7l30wpgamS20&address=" + addr,
		dataType:"json",
		success:get_latlng,
		error:function(){
			alert("Erroe Ajax Get searchFor Function");
		}
	});


}//END OF searchFor Function

//取得地址經緯度并存到database
function get_latlng(latlng) {
	// body...
	alert("debug : "+latlng.results[0].formatted_address);
	// alert("debug : "+latlng.results[0].geometry["location"].lat);
	// alert("debug : "+latlng.results[0].geometry["location"].lng);

	//Confirm message
	if(confirm("Are U Sure Insert SQL Data ?")){
		$.ajax({
			type:"POST",
			url:"http://localhost/MyWeb/php/post_hotelgeo.php",
			data:{
				hotel_addr:latlng.results[0].formatted_address,
				hotel_lat:latlng.results[0].geometry["location"].lat,
				hotel_lng:latlng.results[0].geometry["location"].lng
			},
			success: showMsg,
			error:function(){
				alert("Failed Insert Data !");
			}

		});//end ajax

	}//end if

}

//show result from SQL data
function showMsg(result){
	alert(result);
}

// JavaScript自动刷新页面的方法
function Refresh(){
	
}

// JavaScript自动刷新页面的方法
function AutoRefresh(t){
	setTimeout("location.reload(true);", t);

}

// 根据HOTEL地址显示在GMAPS
function showGMap(hotel_name,hotel_addr,hotel_tel,hotel_lat,hotel_lng){
	// Debug	
	// console.log("hotel_name : "+ hotel_name);
	// console.log("hotel_addr : "+ hotel_addr);
	// console.log("hotel_tel : "+ hotel_tel);
	// console.log("hotel_lat : "+ hotel_lat);
	// console.log("hotel_lng : "+ hotel_lng);

	//Gobal variable
	//全局的infowindow 解决google map中点击一个infowindow关闭其他infowindow
	var infowindow = new google.maps.InfoWindow();

	//set map center
	var map_div = document.getElementById("myhotel");
	// Debug	
	// console.log("hotel_div : "+ map_div);

	//Split coordinate string
	//拆分資料
	var arrayName =  hotel_name.split("|");
	var arrayAddr =  hotel_addr.split("|");
	var arrayTel =  hotel_tel.split("|");
	var arrayLat =  hotel_lat.split("|");
	var arrayLng =  hotel_lng.split("|");

	//get latitude & longtude
	var lat = arrayLat[0];//0～90°
	var lng = arrayLng[0];//0～360°
	// Debug
	// console.log("hotel_lat : "+ lat);
	// console.log("hotel_lng : "+ lng);
	console.log("arrayName : "+ arrayName.length);

	//google.maps.LatLng(經度與緯度)
	//latitude longitude 经纬度座标
	var latlng = new google.maps.LatLng(lat,lng);
	// Debug
	// console.log("hotel_latlng : "+ latlng);

	
	//Display Google Map
	var gmap = new google.maps.Map(map_div,{
		//缩放比例
		zoom:14,
		//中心位置
		center:latlng,
		/**********************************************************************
		google.maps.Map()共包含了三個參數 
		zoom: 設定地圖的縮放比例 
		Center: 設定地圖中心點的經緯度，必須是LatLng 物件型態 
		mapTypeId: 設定地圖的型態
					google.maps.MapTypeId.ROADMAP:２Ｄ街景地圖
					google.maps.MapTypeId.SATELLITE: 衛星地圖
					google.maps.MapTypeId.HYBRID:衛星與街景混合地圖  
					google.maps.MapTypeId.TERRAIN:顯示具有高度、山峰與河流的地圖 
		**********************************************************************/
		mapTypeId:google.maps.MapTypeId.ROADMAP

	});

	/*******************************************************
	google.maps.Marker() 共四個參數 
	1.position: 地標的經緯度必需為LatLng物件型態 
	2.iocn: 圖示的位址 
	3.gmap: 即為 var gmap= new google.maps.Map()建立的地圖 
	4.tilte: 移動到地標時所提示的訊息
	*******************************************************/
	var marker = new google.maps.Marker({
		position:latlng,
		icon:"images/myicon/myhotel.png",
		map:gmap,
		title:"My Hotel Center"
	});

	//Add Listener to Map Icon
	//地图旗标点击事件
	var html_str="<img src=\"images/myicon/hotel2.png\" class=\"myhotel_img\" id=\"myimg0\"><br><hr>"+
				"<h2>"+arrayName[0]+" : Center Point</h2>"+
				"Address : "+ arrayAddr[0] + "<br>"+
				"<a href=\"tel:+886919677397\">"+
					"Tel : "+ arrayTel[0] + "<br>"+
				"</a>"+						
				"ArrayLat : "+ arrayLat[0] + "<br>"+
				"ArrayLng : "+ arrayLng[0] + "<br>" ;

	google.maps.event.addListener(marker,"click",function(event){

		infowindow.setContent(html_str);
		infowindow.open(gmap,marker);

	});

	/***************************************************************************************************/
	//利用迴圈重複執行google.maps.Marker，同時必須改變position的值
	//PHP use marker3 = array();
	//JS use marker3 = Array();

	var loop_marker = [];
	var html_loopmsg = [];

	//Loop Function
	for (var i = 1 ; i < arrayName.length-1 ; i++) {

		//get latitude & longtude
		var loop_lat = arrayLat[i];
		var loop_lng = arrayLng[i];

		//Set map coordination
		var loop_latlng = new google.maps.LatLng(loop_lat,loop_lng);

		//Set Marker flag;
		loop_marker[i] = new google.maps.Marker({
			position:loop_latlng,
			icon:"images/myicon/hotel.png",
			map:gmap,
			title:"MyHotel Maps : "+i
		});

		//拆分TEL資料
		var arrayPN =  arrayTel[i].split("-");
		

		//HTML message
		html_loopmsg[i] = "<img src=\"images/myicon/hotel2.png\" class=\"myhotel_img\" id=\"myimg"+i+"\"><br><hr>"+						
						"<h2>Hotel : "+ arrayName[i] + "</h2>"+
						"Address : "+ arrayAddr[i] + "<br>"+

						"<a href=\"tel:+886919677397\">"+
							"Tel : "+ arrayTel[i] + "<br>"+
						"</a>"+						
						"ArrayLat : "+ arrayLat[i] + "<br>"+
						"ArrayLng : "+ arrayLng[i] + "<br>" ;
		//Click listener
		google.maps.event.addListener(loop_marker[i],"click",function(event){
			
			//取得地圖點擊座標
			var lat_clk=event.latLng.lat();
			var lng_clk=event.latLng.lng();

			for(var j = 1 ; j < arrayName.length-1 ; j++){
				//Data From Travel JS
				var lati = arrayLat[j];
				var lngi = arrayLng[j];

				// 計算地球上兩點的距離
				var disp = getDistance(lat_clk,lng_clk,lati,lngi);

				//靈敏度1M
				if(disp < 0.001){
					// infowindow = new google.maps.InfoWindow({
					// 	content:html_lang3[j]
					// });
					//解决google map中点击一个infowindow关闭其他infowindow
					infowindow.setContent(html_loopmsg[j]);
					infowindow.open(gmap,loop_marker[j]);
				}//end IF

			}//END FOR

		});//END AddListener

	}//End OF FORLOOP

}//END OF showGMap

// 計算地球上兩點的距離
function getDistance(Lat1,Long1,Lat2,Long2){

	ConverDegreeToRadians=function(degrees){
		return(Math.PI/180)*degrees;
	}
	var Lat1r = ConverDegreeToRadians(Lat1);
	var Lat2r = ConverDegreeToRadians(Lat2);
	var Long1r = ConverDegreeToRadians(Long1);
	var Long2r = ConverDegreeToRadians(Long2);

	var R =6371; // 地球半徑(km)
	//求地球兩點的距離 (KM) acos( sin(a1)*sin(a2)+cos(a1)*cos(a2)*cos(b2-b1) )*R
	// var d = Math.acos(Math.sin(Lat1r) * Math.sin(Lat2r) + Math.cos(Lat1r) * Math.cos(Lat2r) * Math.cos(Long2r-Long1r)) * R;
	var d = Math.acos(Math.sin(Lat1r) * Math.sin(Lat2r) + Math.cos(Lat1r) * Math.cos(Lat2r) * Math.cos(Long2r-Long1r)) * R; 

	return d;
}
