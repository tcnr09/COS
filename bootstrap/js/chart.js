$(function(){
    $.ajax({
        type:"GET",
        url:"http://localhost/ChartDemo/php/get_chart.php",      
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
        labels: ["一月", "二月", "三月", "四月", "五月", "六月", "七月"],
        datasets: [{
            label: "My First dataset",
            backgroundColor: 'rgb(173,255,47)',
            borderColor: 'rgb(255,0,255)',
            data: [5, 10, 15, 20, 25, 30, 35],
        }]
    },

    // Configuration options go here
    options: {}
});


