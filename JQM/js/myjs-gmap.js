// 定義顯示 mappage 頁面時執行行 GetMap() 載入地圖和地標
$(function(){
	// Map page 切换页面时載入地圖和地標 
	$("#map").bind("pageshow",GetMap);

	// Travel page 切换页面时載入地圖和地標 
	$("#travel").bind("pageshow",showTravelMap);




});

/****************User Defind Map****************************/
//Default point
var curGeoPoint={lat:24.170566, lng:120.609932};

//Get map data from listview 
function GetGeo(lat,lng){
	//取得LISTVIEW上设定定位点
	curGeoPoint.lat=lat;
	curGeoPoint.lng=lng;
	// mappage 的 pageshow 會呼叫 GetMap() 顯示地圖 
	$.mobile.changePage("#map","slide",false,true);

	//GetMap();  // 也可以在此直接呼叫 GetMap() 顯示地圖

	//避免重複觸發
	e.preventDefault();
}//FIN GetGeo

//Display google maps
function GetMap(){
	//get html div ID for display map
	var map_div =document.getElementById("gmap");

	//latitude longitude 经纬度座标
	// google.maps.LatLng(經度與緯度)
	var latlng = new google.maps.LatLng(curGeoPoint.lat,curGeoPoint.lng);

	//Display Google Map
	var gmap = new google.maps.Map(map_div,{
		//缩放比例
		zoom:16,
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

	/*************************************************************************
	google.maps.Marker() 共四個參數 
	1.position: 地標的經緯度必需為LatLng物件型態 
	2.iocn: 圖示的位址 
	3.gmap: 即為 var gmap= new google.maps.Map()建立的地圖 
	4.tilte: 移動到地標時所提示的訊息
	*************************************************************************/
	//Creat Marker Map Flag
	var marker = new google.maps.Marker({
		position:latlng,
		icon:"images/myicon/1.png",
		map:gmap,
		title:"My Home"
	});


	//地图旗标点击事件
	var html_lang = "<img src=\"images/myicon/flag.png\" class=\"myimg\">"+"<br><hr>"+
					"<h1>G-Map</h1><br>";
	
	google.maps.event.addListener(marker,"click",function(event){
			var inforwindow = new google.maps.InfoWindow({
				content:html_lang
		});
		inforwindow.open(gmap,marker);
	});

}//FIN GetMap


/*************Travel Function******From JS******************/

function showTravelMap(){

	//全局的infowindow 解决google map中点击一个infowindow关闭其他infowindow
	var infowindow = new google.maps.InfoWindow();	

	//debug
	// alert("Debug : Travel Data Length "+ mytraveldata.length);

	//Get ID
	var trav_div = document.getElementById('mytravel');

	//set Map Center
	var trav_lat = 23.8238283;
	var trav_lng = 121.0184692;

	//google.maps.LatLng(經度與緯度)
	var trav_latlng = new google.maps.LatLng(trav_lat,trav_lng);

	/*
	google.maps.Map()共包含了三個參數 
	zoom: 設定地圖的縮放比例 
	Center: 設定地圖中心點的經緯度，必須是LatLng 物件型態 
	mapTypeId: 設定地圖的型態
  				google.maps.MapTypeId.SATELLITE: 衛星地圖
  				google.maps.MapTypeId.HYBRID:衛星與街景混合地圖  
  				google.maps.MapTypeId.TERRAIN: 顯示具有高度、山峰與河流的地圖 
	*/
	//Display Gmap
	var trav_gmap = new google.maps.Map(trav_div,{
		zoom:8,
		center:trav_latlng,
		mapTypeId:google.maps.MapTypeId.ROADMAP
	});


	/*
	google.maps.Marker() 共四個參數 
	1.position: 地標的經緯度必需為LatLng物件型態 
	2.iocn: 圖示的位址 
	3.gmap: 即為 var gmap= new google.maps.Map()建立的地圖 
	4.tilte: 移動到地標時所提示的訊息
	*/
	//建立地图旗标
	var trav_marker = new google.maps.Marker({
		position:trav_latlng,
		icon:"images/myicon/travel.png",
		map:trav_gmap,
		title:"My Travel Map"
	});


	//地图旗标触发事件
	var html_msg = "<img src=\"images/myicon/flag.png\" class=\"mytravelimg\" id=\"myimg\">"+
					"<h1>My Travel Maps:</h1>"+
					"<h3>My Home</h3>";

	google.maps.event.addListener(trav_marker,"click",function(event){
		// var infowindow = new google.maps.InfoWindow({
		// 	content:html_msg
		// });
		infowindow.setContent(html_msg);
		infowindow.open(trav_gmap,trav_marker);
	});


	/***************************************************************************************************/
	//利用迴圈重複執行google.maps.Marker，同時必須改變position的值
	//PHP use marker3 = array();
	//JS use marker3 = Array();

	var loop_marker = [];
	var html_loopmsg = [];

	//Loop Function
	for (var i = 0 ; i < mytraveldata.length ; i++) {

		//分割字元
		// parray = data[i].Coordinate.split(",");
		// var lat=parray[0];
		// var lng=parray[1];

		var loop_lat = mytraveldata[i].latitude;
		var loop_lng = mytraveldata[i].longitude;
		
		//Set map coordination
		var loop_latlng = new google.maps.LatLng(loop_lat,loop_lng);

		//Set Marker flag;
		loop_marker[i] = new google.maps.Marker({
			position:loop_latlng,
			icon:"images/myicon/birds.png",
			map:trav_gmap,
			title:"Mytravel Maps : "+i
		});

		//HTML message
		html_loopmsg[i] = "<img src=\"images/myicon/"+mytraveldata[i].image +"\" class=\"mytravelimg\" id=\"myimg"+i+"\"><br><hr>"+
						"<h2>Travel"+i+"</h2>"+
						"Title : "+ mytraveldata[i].title + "<br>"+
						"Details : "+mytraveldata[i].details + "<br>"+
						"latitude : "+mytraveldata[i].latitude + "<br>"+
						"longitude : "+mytraveldata[i].longitude + "<br>" ;
		//Click listener
		google.maps.event.addListener(loop_marker[i],"click",function(event){
			//取得地圖點擊座標
			var lat_clk=event.latLng.lat();
			var lng_clk=event.latLng.lng();


			for(var j = 0 ; j < mytraveldata.length ; j++){
				//Data From Travel JS
				var lati = mytraveldata[j].latitude;
				var lngi = mytraveldata[j].longitude;

				// 計算地球上兩點的距離
				var disp = getDistance(lat_clk,lng_clk,lati,lngi);

				//靈敏度1M
				if(disp < 0.001){
					// infowindow = new google.maps.InfoWindow({
					// 	content:html_lang3[j]
					// });
					//解决google map中点击一个infowindow关闭其他infowindow
					infowindow.setContent(html_loopmsg[j]);
					infowindow.open(trav_gmap,loop_marker[j]);
				}//end IF

			}//END FOR

		});//END AddListener

	}//********END FOR*********************************

	
}


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

//FIN


