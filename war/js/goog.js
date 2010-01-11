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

    Ext.Ajax.request({url: 'dashboardItemsData.json', success: returnDashboard});
}


var returnDashboard = function(resp) {


    var itemsData = Ext.decode(resp.responseText).itemsData;

    var dashboard = $('#dashboard');
    
    var table = document.createElement("table");
    table.id = 'dashboard-content';
    table.style.visibility = 'hidden';
    dashboard.get(0).appendChild(table);
    
    var tr;

    for (var j = 0; j < itemsData.length; j++) {

    	if (j % 2 == 0) {
    		tr = document.createElement("tr");
    		table.appendChild(tr);
    	}
    	var td = document.createElement("td");
    	td.align='left';
    	td.valign='top';
    	td.style["vertical-align"]='top';
		tr.appendChild(td);

    	var itemData = itemsData[j];

    	var h5 = document.createElement("h5");
    	h5.innerHTML = itemData.title + '&nbsp;&nbsp;';
    	td.appendChild(h5);
   
    	var elem = document.createElement("div");
    	td.appendChild(elem);

    	if (itemData.graphType === 'Rss' ) rss(itemData, elem);
    	if (itemData.graphType === 'IFrame' ) iframe(itemData, elem);
    	if (itemData.graphType === 'Text' ) text(itemData, elem);
    	if (itemData.graphType === 'Guru' ) guru(itemData, elem);

    }
    
	table.style.visibility = 'visible';
    
};


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
        $(div).attr("scrolling","no");
        $(div).attr("frameborder","0");
        elem.appendChild(div);
    } catch (e) {
    }
}

function text(itemData, elem) {
    try {
    	var url = ""+itemData.url;
        var div = document.createElement("div");
        $(div).html(Ext.util.Format.htmlDecode(url));
        $(div).attr("width","350");
        $(div).attr("height","220");
        $(div).attr("scrolling","no");
        $(div).attr("frameborder","0");
        elem.appendChild(div);
    } catch (e) {
    }
}

function guru(itemData, elem) {

	try {

        var url = '/'+contextPrefix+'jasperserver/fileview/fileview/Reports/' + OrangeLeap.thisSiteName +'/Content_files/'+itemData.url+'.html_files/img_0_0_0';

        url = url.replace(/amp;/g, "");
        
        $.ajax({
            type: "GET",
            url: url,
            data: "",
            success: function(html){

        		// Only add img tag if image exists.
        		var img = document.createElement("img");
        		var aimg = $(img);
        		aimg.attr("src", url);
        		elem.appendChild(aimg.get(0));

            	return false;
            }

        });

    } catch (e) {
    }
    
}
