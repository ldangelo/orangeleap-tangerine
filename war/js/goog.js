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
                var html = '<table id="dashboard-content">';

				function Rss(itemData, elemId) {
					try {
						var feed = new google.feeds.Feed(itemData.url);
						feed.setNumEntries(6);
						feed.load(function(result) {
							if (!result.error) {
								var $container = $('#' + elemId);
								for (var i = 0; i < result.feed.entries.length; i++) {
									var entry = result.feed.entries[i];
									var rHtml = '<p><a href="' + entry.link + '">' + entry.title + '&raquo;</a></p>';
									$container.get(0).innerHTML = rHtml;
								}
							}
						});
					}
					catch (e) {

					}
				}

				function Iframe(itemData, elemId) {
					try {
						var url = itemData.url;
						url = url.replace(/amp;/g, "");
						var iHtml = '<iframe src="' + url + '" width="350" height="220" scrolling="no" frameborder="0"></iframe>';
						$('#' + elemId).get(0).innerHTML = iHtml;
					}
					catch (e) { }
				}

				function Text(itemData, elemId) {
					try {
						var url = itemData.url;
						var tHtml = '<div style="overflow: visible; width: 350px; height: 220px; border: 0;">' + Ext.util.Format.htmlDecode(url) + '</div>';
						$('#' + elemId).get(0).innerHTML = tHtml;
					}
					catch (e) {

					}
				}

				function Guru(itemData, elemId) {
					try {
						var url = '/' + contextPrefix + 'jasperserver/fileview/fileview/Reports/' + OrangeLeap.thisSiteName + '/Content_files/' + itemData.url + '.html_files/img_0_0_0';
						url = url.replace(/amp;/g, "");

						jQuery.ajaxq('guruQueue', {
							url: url,
							type: 'POST',
							cache: false,
							success: function(data) {
								if (data) {
									$('#' + elemId).get(0).innerHTML = '<img src="' + url + '"></img>';
								}
							}
						});
					}
					catch (e) {
					
					}
				}

				for (var j = 0; j < itemsData.length; j++) {
					if (j % 2 == 0) {
						html += '<tr>';
					}
					html += '<td>';

					var itemData = itemsData[j];

					html += '<h5>' + itemData.title + '</h5>';
					html += '<div id="';

					if (itemData.graphType === 'Rss') {
						var thisId = 'rssDiv' + j;
						html += thisId;
						new Rss(itemData, thisId);
					}
					if (itemData.graphType === 'IFrame') {
						var thisId = 'iframeDiv' + j;
						html += thisId;
						new Iframe(itemData, thisId);
					}
					if (itemData.graphType === 'Text') {
						var thisId = 'textDiv' + j;
						html += thisId;
						new Text(itemData, thisId);
					}
					if (itemData.graphType === 'Guru') {
						var thisId = 'guruDiv' + j;
						html += thisId;
						new Guru(itemData, thisId);
					}
					html += '"></div>';
					html += '</td>';
					if (j % 2 == 1) {
						html += '</tr>';
					}
				}
				var $dashboard = $('#dashboard');
				$dashboard.get(0).innerHTML = html;
			}
		 });
	}
}
