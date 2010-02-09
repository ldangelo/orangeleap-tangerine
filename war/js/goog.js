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

		 Ext.Ajax.request({ url: 'dashboardItemsData.json',
			 success: function(resp) {
				var itemsData = Ext.decode(resp.responseText).itemsData;
				var dashboard = $('#dashboard');

				var table = document.createElement("table");
				table.id = 'dashboard-content';
				table.style.visibility = 'hidden';
				dashboard.get(0).appendChild(table);

				var tr;

				function rss(itemData, elemId) {
					try {
						var feed = new google.feeds.Feed(itemData.url);
						feed.setNumEntries(6);
						feed.load(function(result) {
							if (!result.error) {
								var $container = $('#' + elemId);
								for (var i = 0; i < result.feed.entries.length; i++) {
									var entry = result.feed.entries[i];
									var para = document.createElement("p");
									var div = document.createElement("a");
									div.innerHTML = entry.title + " &raquo;";
									$(div).attr("href", entry.link);
									para.appendChild(div);
									$container.get(0).appendChild(para);
								}
							}
						});
					}
					catch (e) { }
				}

				function iframe(itemData, elemId) {
					try {
						var url = itemData.url;
						url = url.replace(/amp;/g, "");
						var div = document.createElement("iframe");
						$(div).attr("src",url);
						$(div).attr("width","350");
						$(div).attr("height","220");
						$(div).attr("scrolling","no");
						$(div).attr("frameborder","0");
						$('#' + elemId).get(0).appendChild(div);
					}
					catch (e) { }
				}

				function text(itemData, elemId) {
					try {
						var url = itemData.url;
						var div = document.createElement("div");
						$(div).html(Ext.util.Format.htmlDecode(url));
						$(div).attr("width","350");
						$(div).attr("height","220");
						$(div).attr("scrolling","no");
						$(div).attr("frameborder","0");
						$('#' + elemId).get(0).appendChild(div);
					}
					catch (e) { }
				}

				function guru(itemData, elemId) {
					try {
						var url = '/' + contextPrefix + 'jasperserver/fileview/fileview/Reports/' + OrangeLeap.thisSiteName + '/Content_files/' + itemData.url + '.html_files/img_0_0_0';
						url = url.replace(/amp;/g, "");

						jQuery.ajaxQueue({
							url: url,
							success: function(data) {
								$('#' + elemId).get(0).innerHTML = '<img src="' + url + '"></img>';
							}
						});
					}
					catch (e) { }
				}

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

					var $elem = $(elem);
					if (itemData.graphType === 'Rss') {
						$elem.attr('id', 'rssDiv');
						rss(itemData, 'rssDiv');
					}
					if (itemData.graphType === 'IFrame') {
						$elem.attr('id', 'iframeDiv');
						iframe(itemData, 'iframeDiv');
					}
					if (itemData.graphType === 'Text') {
						$elem.attr('id', 'textDiv');
						text(itemData, 'textDiv');
					}
					if (itemData.graphType === 'Guru') {
						$elem.attr('id', 'guruDiv');
						guru(itemData, 'guruDiv');
					}
				}
				table.style.visibility = 'visible';
			}
		 });
	}
}
