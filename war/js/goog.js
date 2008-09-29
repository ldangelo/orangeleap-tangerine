function setupGoogleWidgets() {
	pieChart();
	areaChart();
	rss();
	barChart();
	$(".googleWidget").css("visibility","visible");
}
function pieChart() {
      var data = new google.visualization.DataTable();
      data.addColumn('string', 'Task');
      data.addColumn('number', 'Hours per Day');
      data.addRows(7);
      data.setValue(0, 0, 'Direct Mail');
      data.setValue(0, 1, 809);
      data.setValue(1, 0, 'Seminar');
      data.setValue(1, 1, 533);
      data.setValue(2, 0, 'Banquet');
      data.setValue(2, 1, 221);
      data.setValue(3, 0, 'Television');
      data.setValue(3, 1, 102);
      data.setValue(4, 0, 'Web');
      data.setValue(4, 1, 68);

      var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
      chart.draw(data, {width: 330, height: 220, is3D: true});
      //, title: 'Gift Analysis by Motivation'
}
function areaChart() {
      var data = new google.visualization.DataTable();
      data.addColumn('string', 'Year');
      data.addColumn('number', 'New Donors');
      data.addColumn('number', 'Pledge Donors');
      data.addRows(4);
      data.setValue(0, 0, 'May');
      data.setValue(0, 1, 65);
      data.setValue(0, 2, 15);
      data.setValue(1, 0, 'Jun');
      data.setValue(1, 1, 74);
      data.setValue(1, 2, 20);
      data.setValue(2, 0, 'Jul');
      data.setValue(2, 1, 94);
      data.setValue(2, 2, 24);
      data.setValue(3, 0, 'Aug');
      data.setValue(3, 1, 94);
      data.setValue(3, 2, 35);

      var chart = new google.visualization.AreaChart(document.getElementById('chart_div2'));
      chart.draw(data, {width: 375, height: 240, legend: 'bottom'});
      //, title: 'Donor Trends'
}
function barChart() {
      var data = new google.visualization.DataTable();
      data.addColumn('string', 'Day');
      data.addColumn('number', 'Total Gifts ($)');
      data.addColumn('number', 'Largest Gift ($)');
      data.addRows(7);
      data.setValue(0, 0, 'Sep 21');
      data.setValue(0, 1, 21000);
      data.setValue(0, 2, 2500);
      data.setValue(1, 0, 'Sep 22');
      data.setValue(1, 1, 18300);
      data.setValue(1, 2, 4000);
      data.setValue(2, 0, 'Sep 23');
      data.setValue(2, 1, 19500);
      data.setValue(2, 2, 1000);
      data.setValue(3, 0, 'Sep 24');
      data.setValue(3, 1, 23600);
      data.setValue(3, 2, 6000);
      data.setValue(4, 0, 'Sep 25');
      data.setValue(4, 1, 24000);
      data.setValue(4, 2, 3000);
      data.setValue(5, 0, 'Sep 26');
      data.setValue(5, 1, 22100);
      data.setValue(5, 2, 2500);
      data.setValue(6, 0, 'Sep 27');
      data.setValue(6, 1, 28000);
      data.setValue(6, 2, 10000);
      var customColors=new Array("#6cc316","#0b89a9");

      var chart = new google.visualization.ColumnChart(document.getElementById('barchart'));
      chart.draw(data, {width: 360, height: 240, is3D: true,colors: customColors,borderColor:"#444",legend:'bottom' });
}
function rss() {
    var feed = new google.feeds.Feed("http://blogs.mpowersystems.com/feed/");
    feed.setNumEntries(6);
    feed.load(function(result) {
      if (!result.error) {
        var container = document.getElementById("feed");
        for (var i = 0; i < result.feed.entries.length; i++) {
          var entry = result.feed.entries[i];
          var para = document.createElement("p");
          var div = document.createElement("a");
          div.innerHTML=entry.title+" &raquo;";
          $(div).attr("href",entry.link);
          para.appendChild(div);
          container.appendChild(para);
        }
      }
    });
}