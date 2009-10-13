Ext.ns('OrangeLeap');

OrangeLeap.BulkSaveStore = function(config){
 	OrangeLeap.BulkSaveStore.superclass.constructor.call(this, config);
};

Ext.extend(OrangeLeap.BulkSaveStore, Ext.data.JsonStore, {
    save: function() {
        var rs = [].concat(this.getModifiedRecords());
        for (var i = rs.length-1; i >= 0; i--) {
            if (!rs[i].isValid()) { // splice-off any !isValid real records
                rs.splice(i,1);
            }
        }
        this.doTransaction('update', rs);
        return true;
    }
});

Ext.onReady(function() {
    var checkColumn = new Ext.grid.CheckColumn({
       header: 'Inactive?',
       dataIndex: 'inactive',
       width: 55
    });

    function checkUniqueDisplayVal(val) {
        var isUnique = true;
        var valCount = 0;
        if (store.data && store.data.items) {
            var len = store.data.items.length;
            for (var x = 0; x < len; x++) {
                if (valCount < 2) {
                    var thisItem = store.data.items[x];
                    if (thisItem.get('displayVal') == val) {
                        valCount++;
                    }
                }
                else {
                    break;
                }
            }
        }
        if (valCount > 1) {
            isUnique = false;
        }
        return isUnique;
    }

    var colModel = new Ext.grid.ColumnModel({
        defaults: {
            sortable: true
        },
        columns: [
            new OrangeLeap.RowGrip({ css: 'grip', tooltip: 'Click and Hold to Drag Row' } ),
            {
                header: 'Order',
                dataIndex: 'itemOrder',
                width: 23,
                sortable: false,
                editor: new Ext.form.NumberField({
                    allowBlank: false,
                    allowNegative: false,
                    allowDecimals: false,
                    minValue: 0,
                    width: 23
                })
            },
            {
                header: 'Short Display Name',
                dataIndex: 'displayVal',
                editable: true,
                editor: new Ext.form.TextField({
                    allowBlank: false,
                    maxLength: 255,
                    validator: function(val) {
                        var results = true;
                        if ( ! checkUniqueDisplayVal(val)) {
                            results = "The Short Display Name " + val + " is not unique for this picklist.";
                        }
                        return results;
                    }
                }),
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    if (record) {
                        if (Ext.isEmpty(value)) {
                            // No value so highlight this cell as in an error state
                            metaData.css += ' x-form-invalid';
                            metaData.attr = 'ext:qtip="A value is required"; ext:qclass="x-form-invalid-tip"';
                        }
                        else if ( ! checkUniqueDisplayVal(value)) {
                            metaData.css += ' x-form-invalid';
                            metaData.attr = 'ext:qtip="The Short Display Name ' + value + ' is not unique for this picklist."; ext:qclass="x-form-invalid-tip"';
                        }
                        else {
                            metaData.css = '';
                            metaData.attr = 'ext:qtip=""';
                        }
                    }
                    return value;
                }
            },
            {
                header: 'Long Display Name',
                dataIndex: 'desc',
                editable: true,
                editor: new Ext.form.TextField({
                    allowBlank: true,
                    maxLength: 255
                })
            },
            {
                header: 'Description',
                dataIndex: 'detail',
                editable: true,
                editor: new Ext.form.TextField({
                    allowBlank: true,
                    maxLength: 255
                })
            },
            checkColumn,
            {header: 'Customize', width: 95, menuDisabled: true, fixed: true, renderer: function() { return '<a href=""><img src="images/icons/gears.png" alt="Customize" title="Customize"/></a>'; } }
        ]
    });
    var filters = new Ext.ux.grid.GridFilters({
        encode: false,
        local: true,
        filters: [{
            type: 'string',
            dataIndex: 'displayVal'
        }, {
            type: 'string',
            dataIndex: 'desc'
        }, {
            type: 'string',
            dataIndex: 'detail'
        }, {
            type: 'boolean',
            dataIndex: 'inactive'
        }]
    });

    var proxy = new Ext.data.HttpProxy({
        api: {
            read    : 'getPicklistItems.json',
            create  : 'managePicklistItems.json',
            update  : 'managePicklistItems.json',
            destroy : 'managePicklistItems.json'
        }
    });

    var writer = new Ext.data.JsonWriter({ listful: true });

    var store = new OrangeLeap.BulkSaveStore({
        batch: true,
        proxy: proxy,
        writer: writer,
        autoSave: false,
        remoteSort: false,
        totalProperty: 'totalRows',
        root: 'rows',
        fields: [
            {name: 'id', mapping: 'id', type: 'int', allowBlank: false},
            {name: 'itemOrder', mapping: 'itemOrder', type: 'int', allowBlank: false, sortDir: 'ASC'},
            {name: 'itemName', mapping: 'itemName', type: 'string', allowBlank: false},
            {name: 'displayVal', mapping: 'displayVal', type: 'string', allowBlank: false},
            {name: 'desc', mapping: 'desc', type: 'string'},
            {name: 'detail', mapping: 'detail', type: 'string'},
            {name: 'inactive', mapping: 'inactive', type: 'boolean'}
        ]
    });
    store.setDefaultSort('itemOrder', 'ASC');
    store.on('beforewrite', function(proxy, action, rs, options, args) {
        if (options) {
            options.params['picklistNameId'] = combo.getValue();
        }
        var thisGrid = Ext.get('managementGrid');
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

    proxy.on('write', function(proxy, action, data, response, records, options) {
        if (response.success === 'true' && store.data && store.data.items) {
            var updatedRecords = [];

            var recLength = store.data.items.length;
            var dataLength = data.length;
            for (var x = 0; x < recLength; x++) {
                var thisClientRec = store.data.items[x];
                for (var y = 0; y < dataLength; y++) {
                    var thisServerRec = data[y];
                    if (thisClientRec.phantom) {
                        if (thisClientRec.get('displayVal') == thisServerRec.displayVal) {
                            thisClientRec.id = thisServerRec['id'];
                            thisClientRec.phantom = false;
                            thisClientRec.set('id', thisServerRec['id']);
                            thisClientRec.set('itemName', thisServerRec['itemName']);
                            thisClientRec.set('itemOrder', thisServerRec['itemOrder']);
                            updatedRecords[updatedRecords.length] = thisClientRec.copy();
                            break;
                        }
                    }
                    else if (thisClientRec.id == thisServerRec.id) {
                        thisClientRec.set('itemOrder', thisServerRec['itemOrder']);
                        updatedRecords[updatedRecords.length] = thisClientRec.copy();
                        break;
                    }
                }
            }
            store.removeAll();
            store.add(updatedRecords);
            store.sort('itemOrder', 'ASC');
            grid.getView().refresh();
            grid.saveButton.disable();
            grid.undoButton.disable();
            var thisGrid = Ext.get('managementGrid');
            thisGrid.unmask();
            $("#savedMarker").css('visibility', 'visible');
            setTimeout(function() {
                $("#savedMarker").css('visibility', 'hidden');
            }, 20000);
        }
    });
    proxy.on('exception', function(proxy, type, action, options, response, args) {
        var thisGrid = Ext.get('managementGrid');
        thisGrid.unmask();
        Ext.MessageBox.show({ title: 'Error', icon: Ext.MessageBox.ERROR,
            buttons: Ext.MessageBox.OK,
            msg: 'The picklist changes could not be saved.  Please try again or contact your administrator if this issue continues.'});
    });

    var picklistItemsLoaded = function(record, options, success) {
        if (success) {
            grid.addButton.enable();
            grid.customizeButton.enable();
        }
    };

    var picklistStore = new Ext.data.ArrayStore({
        fields: ['nameId', 'desc'],
        data : OrangeLeap.Picklists
    });

    var combo = new Ext.form.ComboBox({
        store: picklistStore,
        displayField: 'desc',
        valueField: 'nameId',
        typeAhead: true,
        mode: 'local',
        forceSelection: true,
        triggerAction: 'all',
        emptyText: ' ',
        selectOnFocus: true,
        minListWidth: 250,
        stateId: 'picklistCombo',
        stateful: true,
        stateEvents: ['select']
    });
    combo.on('beforeselect', function(comboBox, record, index) {
        var prevSelValue = comboBox.getValue();
        var doSelect = function(record) {
            if (record && record.data && record.data['nameId']) {
                $("#savedMarker").css('visibility', 'hidden');
                grid.addButton.disable();
                grid.customizeButton.disable();
                grid.saveButton.disable();
                grid.undoButton.disable();
                var state = store.getSortState();
                if (state) {
                    state.field = 'itemOrder';
                    state.direction = 'ASC';
                }
                store.load({ params: { 'picklistNameId' : record.data['nameId'] }, sortInfo: {field: 'itemOrder', direction: 'ASC'}, callback: picklistItemsLoaded });
            }
        }
        if (checkForModifiedRecords()) {
            confirmUndoChanges(function() {
                doSelect(record);
            }, function() {
                comboBox.setValue(prevSelValue);
            });
        }
        else {
            doSelect(record);
        }
    });

    var checkForModifiedRecords = function() {
        var hasModified = false;
        if (store.getModifiedRecords() && store.getModifiedRecords().length > 0) {
            hasModified = true;
        }
        return hasModified;
    }

    var checkDisplayValuesValid = function() {
        var isValid = true;
        var valCtMap = {};
        if (store.data && store.data.items) {
            var len = store.data.items.length;
            for (var x = 0; x < len; x++) {
                var thisDisplayValue = store.data.items[x].get('displayVal');
                if (Ext.isEmpty(thisDisplayValue)) {
                    isValid = false;
                    break;
                }
                else {
                    if ( ! valCtMap[thisDisplayValue]) {
                        valCtMap[thisDisplayValue] = 1;
                    }
                    else {
                        var count = valCtMap[thisDisplayValue];
                        valCtMap[thisDisplayValue] = ++count;
                        if (count > 1) {
                            isValid = false;
                            break;
                        }
                    }
                }
            }
        }
        return isValid;
    }

    var confirmUndoChanges = function(okCallback, cancelCallback) {
        Ext.Msg.show({
            title: 'Lose Changes?',
            msg: 'You have made changes to this picklist.  Would you like to continue without saving?',
            buttons: Ext.Msg.OKCANCEL,
            icon: Ext.MessageBox.WARNING,
            fn: function(btn, text) {
                if (btn == "ok") {
                    store.rejectChanges();
                    if (okCallback && Ext.isFunction(okCallback)) {
                        okCallback();
                    }
                }
                else if (cancelCallback && Ext.isFunction(cancelCallback)) {
                    cancelCallback();
                }
            }
        });
    }

    var grid = new Ext.grid.EditorGridPanel({
        store: store,
        colModel: colModel,
        renderTo: 'managerGrid',
        width: 780,
        height: 600,
        title: 'Manage Picklist Items <span id="savedMarker">Saved</span>',
        loadMask: true,
        frame: true,
        id: 'managementGrid',
        viewConfig: { forceFit: true },
        buttons: [
            {text: 'Save', cls: 'saveButton', ref: '../saveButton', disabled: true, handler: function() {
                    if (checkForModifiedRecords()) {
                        if (checkDisplayValuesValid()) {
                            store.save();
                        }
                        else {
                            Ext.MessageBox.show({ title: 'Correct Errors', icon: Ext.MessageBox.WARNING,
                                buttons: Ext.MessageBox.OK,
                                msg: 'You must fix the errors in the grid first before you can save.'});
                        }
                    }
                }
            },
            {text: 'Undo', cls: 'button', ref: '../undoButton', disabled: true, handler: function() {
                    if (checkForModifiedRecords()) {
                        store.rejectChanges();
                        undoChanges();
                        $("#savedMarker").css('visibility', 'hidden');
                        grid.saveButton.disable();
                        grid.undoButton.disable();
                    }
                }
            }
        ],
        buttonAlign:'center',
        enableDragDrop: true,
        xtype: "grid",
        // specify the check column plugin on the grid so the plugin is initialized
        plugins: [ checkColumn, filters ],
        selModel: new Ext.grid.RowSelectionModel({}),
        clicksToEdit: 1,
        tbar: [
            'Picklist: ', ' ', combo, ' ', ' ', '-',
            { text: 'Customize', tooltip:'Customize Picklist', iconCls: 'customize', ref: '../customizeButton',
              disabled: true, handler: function() {
                }
            }, '-',
            { text: 'Add Item', tooltip:'Add a new Picklist Item', iconCls:'add', id: 'addButton', ref: '../addButton',
              disabled: true, handler: function() {
                    var gStore = grid.getStore();
                    var PickItem = gStore.recordType;
                    var item = new PickItem({
                        id: 0,
                        itemOrder: store.data.length + 1,
                        itemName: 'NewItemName',
                        displayVal: '',
                        desc: '',
                        detail: '',
                        inactive: false
                    });
                    grid.stopEditing();
                    var nextIndex = gStore.getCount();
                    gStore.add(item);
                    grid.startEditing(nextIndex, 1);
                }
            }
        ]
    });
    var dropTgt = new Ext.ux.dd.GridDropTarget(grid.getEl(), {
        grid: grid,
        ddGroup: grid.ddGroup || 'GridDD'
    });
    grid.on('sortchange', function() {
        if (store.data) {
            for (var x = 0; x < store.data.length; x++) {
                var rec = store.getAt(x);
                rec.set('itemOrder', x + 1);
            }
            grid.getView().refresh();
        }
    });

    var undoChanges = function() {
        var len = store.data.length;
        var y = 0;
        var toRemove = [];
        for (var x = 0; x < len; x++) {
            var rec = store.getAt(x);
            if ( ! rec.isValid()) {
                toRemove[y++] = rec;
            }
        }
        for (var z = 0; z < toRemove.length; z++) {
            store.remove(toRemove[z]);
        }
        store.sort('itemOrder', 'ASC');        
    }

    $('#managerGrid').keydown(function(e) {
        if (e.keyCode == 65 && e.altKey) {
            Ext.getCmp('addButton').handler();
        }
    });
});

