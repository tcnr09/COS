
$(function(){
	$("#send").bind('click',showimg);
	$("#calbmi").bind('click',showbmi);

});

function showimg(){
	$.ajax({
		// 不能有空格
		type:"GET",
		url:"http://172.24.177.220/MyWeb/php/getimg.php",
		success:showdata,
		error:function(){
			alert("GET Error!!!");
		} 

	});

};

function showdata(data){
	alert("GET Command Success!!!");
	$("#showimg").html(data);
	$("#showmsg").html($("#msg").val());
	$("#showmsg").attr("class","showfont");


}

function showbmi(){
	$.ajax({
		type:"POST",
		url:"http://172.24.177.220/MyWeb/php/postbmi.php",
		data:{
			"name" : $("#name").val(),
			"cm" : $("#height").val(),
			"kg" : $("#weight").val(),
			"msg": $("#msg").val() },
		success:bmimsg,
		error:function(){
			alert("BMI Calculator Error !!!");
		}


	});
}

function bmimsg(data){

	$("#showbmi").html(data);
	$("#showbmi").attr("class","showbmi")
}


