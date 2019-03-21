$(function(){
    pieChart();


});


function pieChart(){
    clearCanvas();
    window.myPie = new Chart(context, config);
}
/**
 *擦除canvas画布
 */
function clearCanvas(){
    $('#canvas').remove();
    $('#container').append('<canvas id="canvas"></canvas>');
    container=document.getElementById("canvas");
    context=container.getContext("2d");
}

var config = {
    type: 'pie',
    data: {
        datasets: [{
            data: [
                40,
                30,
                20,
                10
            ],
            backgroundColor: [
                window.chartColors.red,
                window.chartColors.orange,
                window.chartColors.green,
                window.chartColors.blue,
            ],
            label: 'Dataset 1'
        }],
        labels: [
            "優秀",
            "良好",
            "中等",
            "偏差"
        ]
    },
    options: {
        responsive: true,
        legend: {
            position: 'top',
        },
        title: {
            display: true,
            text: '圓餅圖'
        },
        animation: {
            animateScale: true,
            animateRotate: true
        }
    }
};