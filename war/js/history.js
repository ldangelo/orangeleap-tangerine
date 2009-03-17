// JavaScript logic for loading the history list
//Ext.namespace('OrangeLeap','OrangeLeap.history');

Ext.onReady(function() {

    $.getJSON("mruList.json", function(json) {
        loadMRU(json.rows);
    });
});


var loadMRU = function(arr) {

    var historyList = $('#bookmarkHistory');
    historyList.empty();

    var pId = /personId=(\d+)/g.exec(document.location.search);

    // see if we're looking at a new person
    if (pId) {
        var addIt = true;
        for (var i = 0; i < arr.length; i++) {
            // explicit == test. pId will have a String, while arr.id is a number
            // so let it do the autocast
            if (arr[i].id == pId[1]) {
                addIt = false;
                break;
            }
        }

        // if we have a new ID, call back to the server and update again.
        // we return here so that the updated list is what gets rendered when
        // the ajax call hits this method again
        if (addIt) {
            $.getJSON("mruUpdate.json", {accountNumber: pId[1]}, function(json) {
                loadMRU(json.rows);
            });
            return;
        }
    }

    // the template to render with the person name
    var template = new Ext.XTemplate(
            '<li><a href="person.htm?personId={id}">',
            '<tpl if="firstName.length == 0 && lastName.length == 0">',
                '{orgName}',
            '</tpl>',
            '<tpl if="firstName.length &gt; 0 || lastName.length &gt; 0">',
                '{firstName} {lastName}',
            '</tpl>',
            '</a></li>');

    var maxWidth = 0;
    var tm = Ext.util.TextMetrics.createInstance('bookmarkHistory');
    for (var j = 0; j < arr.length; j++) {
        historyList.append(template.apply(arr[j]));
        var newWidth = 0;
        if(arr[j].firstName.length == 0 && arr[j].lastName.length == 0) {
            newWidth = tm.getWidth(arr[j].orgName);
        } else {
            newWidth = tm.getWidth(arr[j].firstName + " " + arr[j].lastName); 
        }

        if (newWidth > maxWidth) maxWidth = newWidth;
    }

    $('#bookmarkHistory').width(maxWidth + 20);


};