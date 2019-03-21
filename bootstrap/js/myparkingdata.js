$(function(){

	$.ajax({
		type:"GET",
		url:"http://localhost/MyWeb/php/get_parkingopendata.php",
		dataType:"json",
		success:outsideParking,
		error:function(){
			alert("Error Ajax Get : MyParking");
		}
	});

});


function outsideParking(parkdata){
	
	//全局的infowindow 解决google map中点击一个infowindow关闭其他infowindow
	var infowindow = new google.maps.InfoWindow();

	//Set Map Center
	var map_div = document.getElementById('mypark');

	// 台灣桃園市大園區第一航廈(入境)二號停車場/@25.0804206,121.2358555
	var lat=25.0804206;
	var lng=121.2358555;

	//google.maps.LatLng(經度與緯度)
	var latlng = new google.maps.LatLng(lat,lng);

	/*
	google.maps.Map()建立地图共包含了三個參數 
	zoom: 設定地圖的縮放比例 
	Center: 設定地圖中心點的經緯度，必須是LatLng 物件型態 
	mapTypeId: 設定地圖的型態
				google.maps.MapTypeId.ROADMAP:２Ｄ街景地圖
  				google.maps.MapTypeId.SATELLITE: 衛星地圖
  				google.maps.MapTypeId.HYBRID:衛星與街景混合地圖  
  				google.maps.MapTypeId.TERRAIN: 顯示具有高度、山峰與河流的地圖 
	*/
	var gmap = new google.maps.Map(map_div,{
		zoom:11,
		center:latlng,
		mapTypeId:google.maps.MapTypeId.ROADMAP
	});

	/*
	google.maps.Marker() 建立地图旗标共四個參數 
	1.position: 地標的經緯度必需為LatLng物件型態 
	2.iocn: 圖示的位址 
	3.gmap: 即為 var gmap= new google.maps.Map()建立的地圖 
	4.tilte: 移動到地標時所提示的訊息
	*/
	var marker = new google.maps.Marker({
		position:latlng,
		icon:"images/myicon/3.png",
		map:gmap,
		title:"My Parking outside"
	});

	
	var html_msg = "<div class=\"mycar_gmap\">"+
					"<img src=\"images/myicon/cup128.png\" class=\"mycarimg\" id=\"myimg"+parkdata.parkingLots[0].areaId+"\"><br><hr>"+
					"<h3>臺灣桃園國際機場-台灣桃園市大園區第一航廈(入境)二號停車場 : "+latlng+
					"</h3></div>";
	
	// 设定旗标间听事件
	google.maps.event.addListener(marker,"click",function(event){
		infowindow.setContent(html_msg);
		infowindow.open(gmap,marker);
	});


	/***************************************************************************************************/
	//利用迴圈重複執行google.maps.Marker，同時必須改變position的值
	//PHP use marker = array();
	//JS use marker = Array();
	var marker_flag = [];
	var html_lang = [];
	for (var i = 0; i < parkdata.parkingLots.length ; i++ ) {
		//分割字元
		// parray = data[i].Coordinate.split(",");
		// var lat=parray[0];
		// var lng=parray[1];

		lat = parkdata.parkingLots[i].wgsY;//緯度
		lng = parkdata.parkingLots[i].wgsX;//經度

		latlng = new google.maps.LatLng(lat,lng);
		marker_flag[i] = new google.maps.Marker({
			position:latlng,
			icon:"images/myicon/parking.png",
			map:gmap,
			title:"My ParkingLots Car : "+i
		});

		//HTML message 
		html_lang[i] = "<img src=\"images/myicon/car.png\" class=\"mycarimg\" id=\"myimg"+i+"\"><br><hr>"+
						"<h2>"+parkdata.parkingLots[i].areaName+" : "+ parkdata.parkingLots[i].parkName +" : "+ parkdata.parkingLots[i].parkId +"</h2>"+			
						"TotalSpace : "+ parkdata.parkingLots[i].totalSpace + "<br>"+
						"PayGuide : "+parkdata.parkingLots[i].payGuide + "<br>"+
						"Introduction : "+parkdata.parkingLots[i].introduction + "<br>"+
						"Address : "+parkdata.parkingLots[i].address + "<br>"+
						"latitude : "+parkdata.parkingLots[i].wgsY+ "<br>"+
						"longitude : "+parkdata.parkingLots[i].wgsX  + "<br>" ;
		//Click listener
		google.maps.event.addListener(marker_flag[i],"click",function(event){

			//取得地圖點擊座標
			lat = event.latLng.lat();
			lng = event.latLng.lng();
			for( var j = 0 ; j < parkdata.parkingLots.length ; j++){
				//分割字元店家位置
				// var parray2 = data[j].Coordinate.split(",");
				// var lati=parray2[0];
				// var lngi=parray2[1];
				var lati = parkdata.parkingLots[j].wgsY;
				var lngi = parkdata.parkingLots[j].wgsX;

				//Calculator 地球上兩點的距離
				var disp = getDistance(lat, lng, lati, lngi);

				//靈敏度1M
				if(disp <0.001){
					//解决google map中点击一个infowindow关闭其他infowindow
					infowindow.setContent(html_lang[j]);
					infowindow.open(gmap,marker_flag[j]);


				}//END IF

			}//END FOR

		});//END Addlistener
		
	}//********END FOR**********************************************************************************/

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

//FIN JS