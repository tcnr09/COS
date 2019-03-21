
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

    config.data.labels=[];
    config.data.datasets[0].data = [];
    // config.data.datasets[0].label = "Member population distribusion Statistics";
    //For Loop
    for (var i = 0;  i<data.length ; i++) {
        config.data.labels.push(data[i].Addr);//X axis
        config.data.datasets[0].data.push(data[i].counts);//Y axis

        
    }//END FOR LOOP   
    // config.update();
    var ctx = document.getElementById('chart-area').getContext('2d');
	window.myPie = new Chart(ctx, config);

}


var randomScalingFactor = function() {
	return Math.round(Math.random() * 100);
};

var config = {
	type: 'pie',
	data: {
		datasets: [{
			data: [
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor(),
				randomScalingFactor()
			],
			backgroundColor: [
				// window.chartColors.red,
				// window.chartColors.orange,
				// window.chartColors.yellow,
				// window.chartColors.green,
				// window.chartColors.blue,
				// window.chartColors.indigo,
				// window.chartColors.purple,
				// window.chartColors.pink
				'rgba(255, 99, 132, 0.5)',
                'rgba(54, 162, 235, 0.5)',
                'rgba(255,255,0, 0.5)',
                'rgba(0, 255, 0, 0.5)',
                'rgba(153, 102, 255, 0.5)',
                'rgba(255,165,0, 0.5)',
                'rgba(102,205,170, 0.5)',
                'rgba(220,20,60, 0.5)'
			],
			label: 'Dataset 1'
		}],
		labels: [
			'Red',
			'Orange',
			'Yellow',
			'Green',
			'Blue',
			'Indigo',
			'Purple',
			'Pink' 
		]
	},
	options: {
		responsive: true
	}
};

// window.onload = function() {
// 	var ctx = document.getElementById('chart-area').getContext('2d');
// 	window.myPie = new Chart(ctx, config);
// };

document.getElementById('randomizeData').addEventListener('click', function() {
	config.data.datasets.forEach(function(dataset) {
		dataset.data = dataset.data.map(function() {
			return randomScalingFactor();
		});
	});

	window.myPie.update();
});

var colorNames = Object.keys(window.chartColors);
document.getElementById('addDataset').addEventListener('click', function() {
	var newDataset = {
		backgroundColor: [],
		data: [],
		label: 'New dataset ' + config.data.datasets.length,
	};

	for (var index = 0; index < config.data.labels.length; ++index) {
		newDataset.data.push(randomScalingFactor());

		var colorName = colorNames[index % colorNames.length];
		var newColor = window.chartColors[colorName];
		newDataset.backgroundColor.push(newColor);
	}

	config.data.datasets.push(newDataset);
	window.myPie.update();
});

document.getElementById('removeDataset').addEventListener('click', function() {
	config.data.datasets.splice(0, 1);
	window.myPie.update();
});



