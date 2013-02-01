var lastId=0;
var count= 0;
var msInYear = 31556925993;
var currentTime = 1328755483000;
var divideby = 100000;
var lastMs;
var hashtable = {};
var refreshIntervalId;

$(document).ready( function() {
	//var hashtable = {};
	$.getJSON('data.json', function(data) {
		var items = [];

		$.each(data, function(key, val) {

			hashtable[val.Percent] = [val.Date, val.Details];

		});

	});

	refreshIntervalId = setInterval( function () {
		lastId = refreshIntervalId;
	
		var yearToSub = Math.pow(Math.E,20.3444*Math.pow(count/divideby,3)+3) - Math.pow(Math.E, 3);
		var T = currentTime - msInYear*yearToSub;
		var date = new Date();
		date.setTime(T);
		$("#year").text("Current date: " + date.toString());

		$("#progressbar").progressbar({
			value: (count/divideby*100)
		});
		$(".bar").css("width",count/divideby*100+"%");
		//alert(count);

		$("#percentage").text(Math.round(count/divideby*10000)/100+"%");
		$("#date").text(hashtable[Math.round(count/divideby*100)/100][0]);

		//$("#date").text(date.toString());

		$("#event").text(hashtable[Math.round(count/divideby*100)/100][1]);

		//alert("alert: " + );
		count+=1000;
		if (count>divideby)
			clearInterval(refreshIntervalId);
	}, 500);
	$('.btn').click( function() {
		test($('#input01').val());
	})
});
//testing out new ways to change change the progress of the bar
function test(interval) {
	if (lastId){
		//alert("deleting last one:");
		clearInterval(lastId);
	}
	refreshIntervalId = setInterval( function () {
		//alert('interval: ' + interval);
		var yearToSub = Math.pow(Math.E,20.3444*Math.pow(count/divideby,3)+3) - Math.pow(Math.E, 3);
		var T = currentTime - msInYear*yearToSub;
		var date = new Date();
		date.setTime(T);
//		$("#year").text("minus: " + yearToSub*msInYear + " date: " + date.toString() + " count: " + count/divideby);
		$("#year").text("Current date: " + date.toString());

		$("#progressbar").progressbar({
			value: (count/divideby*100)
		});
		$(".bar").css("width",count/divideby*100+"%");
		//alert(count);

		$("#percentage").text(Math.round(count/divideby*10000)/100+"%");
		$("#date").text(hashtable[Math.round(count/divideby*100)/100][0]);

		//$("#date").text(date.toString());

		$("#event").text(hashtable[Math.round(count/divideby*100)/100][1]);

		//alert("alert: " + );
		count+=1/interval*10;
		if (count>divideby)
			clearInterval(refreshIntervalId);
	}, interval);
	lastId = refreshIntervalId;
}
