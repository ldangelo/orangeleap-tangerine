
var googleWidgetsLoaded = false;
var googlePageLoaded = false;
var googleDashboardLoading = false;

function setupGoogleWidgets() {
	googleWidgetsLoaded = true;
	checkDashboard();
}

Ext.onReady(function() {
	googlePageLoaded = true;
	checkDashboard();
});

// Need both page and google loads to have finished
function checkDashboard() {
	if (googleWidgetsLoaded && googlePageLoaded && !googleDashboardLoading) {
		googleDashboardLoading = true;
		callDashboard();
	}
}
function callDashboard() {
	
	//alert("callDashboard");
	
    Ext.Ajax.request({url: 'dashboardItemsData.json', success: returnDashboard});
}


var returnDashboard = function(resp) {

	//alert("returnDashboard");  // This doesn't always get called, even though the server returns without error - why?

    var itemsData = Ext.decode(resp.responseText).itemsData;
    
    var dashboard = $('#dashboard');
    dashboard.empty();
 
    var template = new Ext.XTemplate(
          '<div class="column googleWidget">',
      	  ' <h5>{title}&nbsp;&nbsp;</h5>',
      	  ' <div id="chart_div_{divnum}" />',
          '</div>'
            );
    
    for (var j = 0; j < itemsData.length; j++) {
    	
    	var itemData;
    	itemData = itemsData[j];
    	itemData.divnum = "" + j;
    	dashboard.append(template.apply(itemData));
    	var elem = document.getElementById('chart_div_'+itemData.divnum);
    	
    	if (itemData.graphType === 'Pie' ) pieChart(itemData, elem);
    	if (itemData.graphType === 'Bar' ) barChart(itemData, elem);
    	if (itemData.graphType === 'Rss' ) rss(itemData, elem);
    	if (itemData.graphType === 'Area' ) areaChart(itemData, elem);
    	if (itemData.graphType === 'IFrame' ) iframe(itemData, elem);
    	if (itemData.graphType === 'Guru' ) guru(itemData, elem);

    	if (j % 2 == 1) {
    		dashboard.append('<div class="clearColumns" />');
    	}
    	
    }

	$(".googleWidget").css("visibility","visible");

};

function loadGoogleData(itemData) {
	
	var i,j;
	var rowData, datapoint;
    var data = new google.visualization.DataTable();
    
    for (i = 0; i < itemData.columnTitles.length; i++) {
    	data.addColumn(itemData.columnTypes[i], itemData.columnTitles[i]);
    }

    data.addRows(itemData.rowLabels.length+1);
    
    for (i = 0; i < itemData.rowLabels.length; i++) {
        data.setValue(i, 0, itemData.rowLabels[i]); 
    }
    
    for (i = 0; i < itemData.rowData.length; i++) {
    	rowData = itemData.rowData[i];
        for (j = 0; j < rowData.length; j++) {
        	datapoint = rowData[j];
        	data.setValue(j, i+1, datapoint);
        }
    }
    
	return data;
	
}


function pieChart(itemData, elem) {
	try {
		var data = loadGoogleData(itemData);
		var chart = new google.visualization.PieChart(elem);
    	chart.draw(data, {width: 330, height: 220, is3D: true});
    } catch (e) {
    }
}

function areaChart(itemData, elem) {
    try {
       var data = loadGoogleData(itemData);
       var chart = new google.visualization.AreaChart(elem);
       chart.draw(data, {width: 375, height: 240, legend: 'bottom'});
    } catch (e) {
    }
}

function barChart(itemData, elem) {
    try {
       var data = loadGoogleData(itemData);
       var customColors=new Array("#6cc316","#0b89a9");
       var chart = new google.visualization.ColumnChart(elem);
       chart.draw(data, {width: 360, height: 240, is3D: true,colors: customColors,borderColor:"#444",legend:'bottom' });
    } catch (e) {
    }
}

function rss(itemData, elem) {
    try {
      var feed = new google.feeds.Feed(itemData.url);
      feed.setNumEntries(6);
      feed.load(function(result) {
        if (!result.error) {
          var container = elem;
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
    } catch (e) {
    }
}

function iframe(itemData, elem) {
    try {
    	var url = ""+itemData.url;
    	url = url.replace(/amp;/g, "");
        var div = document.createElement("iframe");
        $(div).attr("src",url);
        $(div).attr("width","350");
        $(div).attr("height","220");
//        $(div).attr("scrolling","vertical");
        $(div).attr("scrolling","no");
        $(div).attr("frameborder","0");
        elem.appendChild(div);
    } catch (e) {
    }
}

function guru(itemData, elem) {
    try {
        var url = ""+itemData.url;
        url = url.replace(/amp;/g, "");
        var div = document.createElement("div");
        var adiv = $(div);

        $.ajax({
                type: "GET",
                url: url,
                data: "",
                success: function(html){
                        adiv.html(html);

                        var aele = adiv.find("a[name='JR_PAGE_ANCHOR_0_1']");
                        var aimg = aele.parent().find("tbody tr:eq(2) td:eq(1) img");
//alert("src = "+aimg.attr("src"));     
                    elem.appendChild(aimg.get(0));
                    return false;
                }

        });

    } catch (e) {
    }
}
