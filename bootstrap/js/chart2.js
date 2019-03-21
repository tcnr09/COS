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

    myChart.data.labels=[];
    myChart.data.datasets[0].data = [];
    myChart.data.datasets[0].label = "Member population distribusion Statistics";
    //For Loop
    for (var i = 0;  i<data.length ; i++) {
        myChart.data.labels.push(data[i].Addr);//X axis
        myChart.data.datasets[0].data.push(data[i].counts);//Y axis

        
    }//END FOR LOOP   
    myChart.update();

}

var ctx = document.getElementById("myChartBar").getContext('2d');
var myChart = new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
        datasets: [{
            label: '# of Votes',
            data: [12, 19, 3, 5, 2, 3,6,6],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255,255,0, 0.2)',
                'rgba(0, 255, 0, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255,165,0, 0.2)',
                'rgba(102,205,170, 0.2)',
                'rgba(220,20,60, 0.2)'
            ],
            borderColor: [
                'rgba(255,99,132,1)',
                'rgba(54, 162, 235, 1)',
                'rgba(204,204,0, 1)',
                'rgba(0,128,0, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255,140,0, 1)',
                'rgba(0,128,128, 0.2)',
                'rgba(139,0,0, 0.2)'
            ],
            borderWidth: 1
        }]
    },
    options: {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero:true
                }
            }]
        }
    }
});