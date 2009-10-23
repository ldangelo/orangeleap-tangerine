Ext.onReady(function() {
    Ext.QuickTips.init(); 
    var proxy = new Ext.data.HttpProxy({
        api: {
            read: {url: 'manageSiteDefaults.json', method: 'GET'},
            load: {url: 'manageSiteDefaults.json', method: 'GET'},
            create: {url: 'manageSiteDefaults.json', method: 'POST'},
            update: {url: 'manageSiteDefaults.json', method: 'POST'},
            destroy: {url: 'manageSiteDefaults.json', method: 'POST'}
        }
    });
    var saveError = function() {
        var thisGrid = Ext.get('managerGrid');
        thisGrid.unmask();
        Ext.MessageBox.show({ title: 'Error', icon: Ext.MessageBox.ERROR,
            buttons: Ext.MessageBox.OK,
            msg: 'The site default changes could not be saved.  Please try again or contact your administrator if this issue continues.'});
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
        sortInfo: { field: 'label', direction: 'ASC'},
        totalProperty: 'totalRows',
        root: 'rows',
        fields:[
            {name: 'id', type: 'int'},
            {name: 'entityType', type: 'string'},
            {name: 'label', type: 'string'},
            {name: 'entityName', type: 'string'},
            {name: 'entityValue', type: 'string'}
        ]
    });
    store.on('beforewrite', function(proxy, action, rs, options, args) {
        var thisGrid = Ext.get('managerGrid');
        thisGrid.mask("Saving...");
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
        title: 'Manage Site Defaults <span id="savedMarker">Saved</span>',
        loadMask: true,
        frame: true,
        renderTo: 'managerGrid',
        viewConfig: { forceFit: true },
        clicksToEdit: 1,
        columns: [
            {   header: 'Type',
                width: 100,
                sortable: true,
                dataIndex: 'entityType',
                align: 'left',
                editable: false,
                editor: new Ext.form.DisplayField()
            },
            {   header: 'Name',
                width: 170,
                sortable: true,
                dataIndex: 'label',
                align: 'left',
                editable: false,
                editor: new Ext.form.DisplayField()
            },
            {   header: 'Value',
                width: 170,
                sortable: true,
                dataIndex: 'entityValue',
                align: 'left',
                editable: true,
                editor: entityValueFld
            }
        ],
        buttons: [
            {text: 'Save', cls: 'saveButton', ref: '../saveButton', disabled: true, handler: function() {
                    $("#savedMarker").css('visibility', 'hidden');
                    store.save();
                }
            },
            {text: 'Undo', cls: 'button', ref: '../undoButton', disabled: true, handler: function() {
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