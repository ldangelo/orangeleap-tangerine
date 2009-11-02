Ext.onReady(function() {
    Ext.QuickTips.init(); 
    var proxy = new Ext.data.HttpProxy({
        api: {
            read: {url: 'manageDashboard.json', method: 'GET'},
            load: {url: 'manageDashboard.json', method: 'GET'},
            create: {url: 'manageDashboard.json', method: 'POST'},
            update: {url: 'manageDashboard.json', method: 'POST'},
            destroy: {url: 'manageDashboard.json', method: 'POST'}
        }
    });
    var saveError = function() {
        var thisGrid = Ext.get('managerGrid');
        thisGrid.unmask();
        Ext.MessageBox.show({ title: 'Error', icon: Ext.MessageBox.ERROR,
            buttons: Ext.MessageBox.OK,
            msg: 'The dashboard changes could not be saved.  Please try again or contact your administrator if this issue continues.'});
    }

    proxy.on('write', function(proxy, action, data, response, records, options) {
        if (response.success === 'true') {
            store.commitChanges();
            grid.saveButton.disable();
            grid.undoButton.disable();
            var thisGrid = Ext.get('managerGrid');
            thisGrid.unmask();
            $("#savedMarker").css('visibility', 'visible');
            setTimeout(function() {
                $("#savedMarker").css('visibility', 'hidden');
            }, 10000);
        }
        else {
            saveError();
        }
    });
    proxy.on('exception', function(proxy, type, action, options, response, args) {
        saveError();
    });

    var writer = new Ext.data.JsonWriter({ listful: true });

    var store = new OrangeLeap.BulkSaveStore({
        batch: true,
        proxy: proxy,
        writer: writer,
        autoLoad: true,
        autoSave: false,
        remoteSort: false,
        sortInfo: { field: 'order', direction: 'ASC'},
        totalProperty: 'totalRows',
        root: 'rows',
        fields:[
                {name: 'id', type: 'int'},
                {name: 'type', type: 'string'},
                {name: 'title', type: 'string'},
                {name: 'url', type: 'string'},
                {name: 'order', type: 'string'}
        ]
    });
    store.on('beforewrite', function(proxy, action, rs, options, args) {
        var thisGrid = Ext.get('managerGrid');
        thisGrid.mask("Saving...");
        
        // Renumber order
        if (store.data && store.data.items) {
            var len = store.data.items.length;
            for (var x = 0; x < len; x++) {
                store.data.items[x].set('order', ""+x);
            }
        }
        
    });
    store.on('add', function(store, records, index) {
        grid.saveButton.enable();
        grid.undoButton.enable();
    });
    store.on('update', function(store, record, operation) {
        grid.saveButton.enable();
        grid.undoButton.enable();
    });

    var entityValueFld = new Ext.form.TextField({
        allowBlank: true,
        maxLength: 255,
        enableKeyEvents: true
    });
    entityValueFld.on('keydown', function(fld, event) {
        if (event.getKey() == event.ENTER) {
            setTimeout(function() {
                grid.saveButton.handler();
            }, 100);
        }
    });

    var grid = new Ext.grid.EditorGridPanel({
        store: store,
        width: 780,
        height: 600,
        title: 'Manage Dashboard <span id="savedMarker">Saved</span>',
        loadMask: true,
        frame: true,
        renderTo: 'managerGrid',
        viewConfig: { forceFit: true },
        clicksToEdit: 1,
        columns: [
            {   header: 'Type',
                width: 100,
                sortable: true,
                dataIndex: 'type',
                align: 'left',
                editable: false,
                editor: new Ext.form.DisplayField()
            },
            {   header: 'Title',
                width: 170,
                sortable: true,
                dataIndex: 'title',
                align: 'left',
                editable: true,
                editor: new Ext.form.DisplayField()
            },
            {   header: 'Url',
                width: 170,
                sortable: true,
                dataIndex: 'url',
                align: 'left',
                editable: true,
                editor: entityValueFld
            }
        ],
        buttons: [
            {text: 'Save', cls: 'saveButton', ref: '../saveButton', disabled: true,
                disabledClass: 'disabledButton',
                handler: function() {
                    $("#savedMarker").css('visibility', 'hidden');
                    store.save();
                }
            },
            {text: 'Undo', cls: 'button', ref: '../undoButton', disabled: true,
                disabledClass: 'disabledButton',
                handler: function() {
                    var thisGrid = Ext.get('managerGrid');
                    thisGrid.mask("Undoing...");
                    store.rejectChanges();
                    $("#savedMarker").css('visibility', 'hidden');
                    grid.saveButton.disable();
                    grid.undoButton.disable();
                    thisGrid.unmask();
                }
            }
        ],
        buttonAlign: 'center'
    });

    grid.on('click', function(event) {
        $("#savedMarker").css('visibility', 'hidden');
    });    
});