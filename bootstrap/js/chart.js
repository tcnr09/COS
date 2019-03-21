$(function(){
    $.ajax({
        type:"GET",
        url:"http://192.168.60.109/COS_WebSite/php/get_chart.php",      
        dataType:"json",
        success:show,
        error:function(){
            alert("error Chart Get");
        }
    });


});

function show(data){
    // alert(data.length); 
    console.log(data);

    chart.data.labels=[];
    chart.data.datasets[0].data = [];
    chart.data.datasets[0].label = "Member population distribusion Statistics";
    //For Loop
    for (var i = 0;  i<data.length ; i++) {
        chart.data.labels.push(data[i].Addr);//X axis
        chart.data.datasets[0].data.push(data[i].counts);//Y axis

        
    }//END FOR LOOP   
    chart.update();

}


var ctx = document.getElementById('myChart').getContext('2d');
var chart = new Chart(ctx, {
    // The type of chart we want to create
    type: 'line',
    // type: 'bar',

    // The data for our dataset
    data: {
        // labels: ["January", "February", "March", "April", "May", "June", "July"],
        labels: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        datasets: [{
            label: "消費紀錄",
            backgroundColor: 'rgb(173,255,47)',
            borderColor: 'rgb(255,0,255)',
            data: [55, 60, 35, 30, 35, 30, 55,80,45,30,35,40],
        }]
    },

    // Configuration options go here
    options: {}
});


