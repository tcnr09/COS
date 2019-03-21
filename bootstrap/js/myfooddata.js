$(function(){
	$.ajax({
		type:"GET",
		url:"http://localhost/MyWeb/php/get_foodopendata.php",
		dataType:"json",
		success:showFoodMap,
		error:function(){
			alert("Error Ajax Get : MyFoodData");
			// console.log("Error Ajax Get !");
		}
	});


});

//Action Show Food Map function
function showFoodMap(fooddata){
	//debug
	// alert(fooddata.length);
	// console.log(fooddata.length);

	//Gobal variable
	//全局的infowindow 解决google map中点击一个infowindow关闭其他infowindow
	var infowindow = new google.maps.InfoWindow();

	//set map center
	var map_div = document.getElementById('myfood');

	//Split coordinate string
	parray = fooddata[0].Coordinate.split(",");

	//get latitude & longtude
	var lat = parray[0];//0～90°
	var lng = parray[1];//0～360°

	//google.maps.LatLng(經度與緯度)
	var latlng = new google.maps.LatLng(lat,lng);

	/*
	google.maps.Map()共包含了三個參數 
	zoom: 設定地圖的縮放比例 
	Center: 設定地圖中心點的經緯度，必須是LatLng 物件型態 
	mapTypeId: 設定地圖的型態
  				google.maps.MapTypeId.SATELLITE: 衛星地圖
  				google.maps.MapTypeId.HYBRID:衛星與街景混合地圖  
  				google.maps.MapTypeId.TERRAIN: 顯示具有高度、山峰與河流的地圖 
	*/
	var gmap = new google.maps.Map(map_div,{
		zoom:8,
		center:latlng,
		mapTypeId:google.maps.MapTypeId.ROADMAP
	});

	/*
	google.maps.Marker() 共四個參數 
	1.position: 地標的經緯度必需為LatLng物件型態 
	2.iocn: 圖示的位址 
	3.gmap: 即為 var gmap= new google.maps.Map()建立的地圖 
	4.tilte: 移動到地標時所提示的訊息
	*/
	var marker = new google.maps.Marker({
		position:latlng,
		icon:"images/food/f1.png",
		map:gmap,
		title:"My Food Home"
	});

	//Add Listener to Map Icon
	var html_str="<img src=\""+fooddata[0].PicURL+"\" class=\"myfoodimg\" id=\"myimg\"><br><hr>"+
					"<h1>"+fooddata[0].Name+" : 0 </h1>";
	google.maps.event.addListener(marker,"click",function(event){

		infowindow.setContent(html_str);
		infowindow.open(gmap,marker);

	});

	//利用迴圈重複執行google.maps.Marker，同時必須改變Marker position的值
	// var marker2 = Array();
	var marker2 = [];
	var harray = [];

	for (var i = 1; i <fooddata.length ; i++) {
		//Split coordinate string
		parray = fooddata[i].Coordinate.split(",");

		var lat=parray[0];
		var lng=parray[1];

		latlng = new google.maps.LatLng(lat,lng);
		marker2[i] = new google.maps.Marker({
			position:latlng,
			icon:"images/food/f2.png",
			map:gmap,
			title:"My Food"+i
		});


		//HTML message 
		harray[i] = "<img src=\""+fooddata[i].PicURL+"\" class=\"myfoodimg\" id=\"myimg\"><br>"+
						"<h1>"+fooddata[0].Name+" : "+ i +" </h1>"+						
						"Tel : "+ fooddata[i].Tel + "<br>"+
						"Address : "+fooddata[i].Address + "<br>"+
						"latitude : "+parray[0] + "<br>"+
						"longitude : "+parray[1] + "<br>" ;

		//Click listener				
		google.maps.event.addListener(marker2[i],"click",function(event){
			
			//取得地圖點擊座標
			var lat2=event.latLng.lat();
			var lng2=event.latLng.lng();
			for (var j = 0; j < fooddata.length; j++) {
				//分割字元店家位置
				var parray2 = fooddata[j].Coordinate.split(",");
				var lati=parray2[0];
				var lngi=parray2[1];
				
				// 計算地球上兩點的距離
				var disp = getDistance(lat2,lng2,lati,lngi);

				//靈敏度1M
				if(disp < 0.001){
					// var infowindow = new google.maps.InfoWindow({
					// 	content:harray[j]
					// });
					//解决google map中点击一个infowindow关闭其他infowindow
					infowindow.setContent(harray[j]);
					infowindow.open(gmap,marker2[j]);

				}//end IF

			}//END FOR
			
		});//END AddListener

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

//FIN



