// JavaScript logic for loading the history list
//Ext.namespace('OrangeLeap','OrangeLeap.history');

Ext.onReady(function() {

    Ext.Ajax.request({url: 'mruList.json',success: loadMRU});

});


var loadMRU = function(resp) {

    var arr = Ext.decode(resp.responseText).rows;

    var historyList = $('#bookmarkHistory');
    historyList.empty();

    var pId = /constituentId=(\d+)/g.exec(document.location.search);

    // see if we're looking at a new constituent
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

            Ext.Ajax.request({url: 'mruUpdate.json',
                params: {accountNumber: pId[1]},
                success: loadMRU});
           
            return;
        }
    }

    // the template to render with the constituent name
    var template = new Ext.XTemplate(
            '<li><a href="constituent.htm?constituentId={id}">',
            '<tpl if="firstName.length == 0 && lastName.length == 0">',
                '{orgName}',
            '</tpl>',
            '<tpl if="firstName.length &gt; 0 || lastName.length &gt; 0">',
                '{firstName} {lastName}',
            '</tpl>',
            '</a></li>');

    var maxWidth = 100;
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

    historyList.width(maxWidth + 20);


};