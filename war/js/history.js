// JavaScript logic for loading the history list
//Ext.namespace('MPower','MPower.history');

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
            if (arr[i].id === pId) {
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
    var template = new Ext.Template('<li><a href="person.htm?personId={id}">{firstName} {lastName}</a></li>');

    var maxWidth = 0;
    var tm = Ext.util.TextMetrics.createInstance('bookmarkHistory');
    for (var j = 0; j < arr.length; j++) {
        historyList.append(template.apply(arr[j]));
        var w = tm.getWidth(arr[j].firstName + " " + arr[j].lastName);
        if (w > maxWidth) maxWidth = w;
    }

    $('#bookmarkHistory').width(maxWidth + 20);


};