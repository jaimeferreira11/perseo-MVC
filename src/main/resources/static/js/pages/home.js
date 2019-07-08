var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/", 2));
//Flot Bar Chart
/*$(function() {
    var barOptions = {
        series: {
            bars: {
                show: true,
                barWidth: 0.6,
                fill: true,
                fillColor: {
                    colors: [{
                        opacity: 0.8
                    }, {
                        opacity: 0.8
                    }]
                }
            }
        },
        xaxis: {
            tickDecimals: 0
        },
        colors: ["#1ab394", "#79d2c0"],
        grid: {
            color: "#999999",
            hoverable: true,
            clickable: true,
            tickColor: "#D4D4D4",
            borderWidth:0
        },
        legend: {
            show: true
        },
        tooltip: true,
        tooltipOpts: {
            content: "x: %x, y: %y"
        }
    };
    var barData = {
        label: "bar",
        data: [
            [1, 34],
            [2, 25],
            [3, 19],
            [4, 34],
            [5, 32],
            [6, 44]
        ]
    };
    $.plot($("#flot-bar-chart"), [barData], barOptions);

});*/

$(function() {

    var data = [{
        label: "Articulo 1",
        data: 21,
        color: "#d3d3d3",
    }, {
        label: "Articulo 2",
        data: 3,
        color: "#bababa",
    }, {
        label: "Articulo 3",
        data: 15,
        color: "#79d2c0",
    }, {
        label: "Articulo 4",
        data: 52,
        color: "#1ab394",
    }];

    var plotObj = $.plot($("#flot-pie-chart"), data, {
        series: {
            pie: {
                show: true
            }
        },
        grid: {
            hoverable: true
        },
        tooltip: true,
        tooltipOpts: {
            content: "%p.0%, %s", // show percentages, rounding to 2 decimal places
            shifts: {
                x: 20,
                y: 0
            },
            defaultTheme: false
        }
    });

});

$(document).ready(function(){

	
	localStorage.usuario = sjcl.encrypt(encrypt,
									pojoLogin($("#idusuario").val(),
											$("#username").val(), 
											$("#idempresa").val(),
											$("#idsucursal").val(),
											$("#iddeposito").val()));

	//calendario
	$('#calendar').fullCalendar({
    	locale: "es" ,
    	buttonText: {
    		year: "año", 
    		today: "hoy",
    		month: "mes",
    		week: "Semana",
    		day: "dia"
    	}
       
    });
	
	
	chart()

});


function chart(){
	var labels = ['Enero' , 'Febrero', 'Marzo' , 'Abril', 'Mayo', 'Junio']
	var data1 = [20,15,30,40,50,40];
	var data2 = [5,10,20,35,25,30];
	var data3 = [12,22,22,42,32,12];
	 var barData = {
		        labels: labels,
		        stepped : true,
		        datasets: [
		            {
		                label: "Ventas",
		                backgroundColor: '#1ab394',
		              //  borderColor: "rgb(255, 0, 0)",
		                data: data1,
		              

		            },
		            {
		                label: "Compras",
		                backgroundColor: '#79d2c0',
		              //  borderColor: "rgb(255, 255, 0)",
		                data: data2,
		                
		            },
		            {
		                label: "Mov. Articulos",
		                backgroundColor: '#bababa',
		               // borderColor: "rgb(0, 128, 0)",
		                data: data3,
		               
		                
		            }
		        ]
		    };

		    var barOptions = {
		        responsive: true,
		        
             
                scales: {
                    xAxes: [{
                            ticks: {
                                beginAtZero: true,
                                fontFamily: "'Open Sans Bold', sans-serif",
                                fontSize: 12
                            },
                            scaleLabel: {
                                display: false
                            },
                            gridLines: {
                            },
                           // stacked: true
                        }],
                    yAxes: [{
                            barThickness: 20,
                            gridLines: {
                                display: false,
                                color: "#fff",
                                zeroLineColor: "#fff",
                                zeroLineWidth: 0
                            },
                            ticks: {
                                fontFamily: "'Open Sans Bold', sans-serif",
                                fontSize: 14
                            },
                           // stacked: true
                        }]
                },
		        	
		        legend: {
		            labels: {
		                // This more specific font property overrides the global property
		                fontSize : 16
		            }
		        }
		    };
		
		    	
		    var ctx2 = document.getElementById("barChart").getContext("2d");
		    new Chart(ctx2, {type: 'bar', data: barData, options:barOptions});
		  
}


