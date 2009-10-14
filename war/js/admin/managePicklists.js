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
    }//,

//    afterEdit: function(record) {
//        // overridden to not consistently fire update after a bulk record edit
//        if (this.modified.indexOf(record) == -1){
//            this.modified.push(record);
//        }
//    }
});

Ext.onReady(function() {
    var checkColumn = new Ext.grid.CheckColumn({
       header: 'Inactive?',
       dataIndex: 'g',
       width: 55
    });

    function checkUniqueDisplayVal(val) {
        var isUnique = true;
        var valCount = 0;
        if (val && store.data && store.data.items) {
            var len = store.data.items.length;
            for (var x = 0; x < len; x++) {
                if (valCount < 2) {
                    var thisItem = store.data.items[x];
                    if (thisItem.get('c').toLowerCase() == val.toLowerCase()) {
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

    var escapeScriptTag = function(val) {
        if (val) {
            if (val.indexOf('<script') > -1) {
                var beginIndex = val.indexOf('<script');
                val =  val.substring(0, beginIndex) + '&lt;script' + val.substring(beginIndex + 7);
            }
            if (val.indexOf('</script') > -1) {
                var endIndex = val.indexOf('</script');
                val =  val.substring(0, endIndex) + '&gt;/script' + val.substring(endIndex + 8);
            }
        }
        return val;
    }

    var numberFld = new Ext.form.NumberField({
        allowBlank: false,
        allowNegative: false,
        allowDecimals: false,
        minValue: 0,
        width: 23,
        enableKeyEvents: true
    });
    numberFld.on('keydown', function(fld, event) {
        saveOnEnter(fld, event);
    });

    var displayValFld = new Ext.form.TextField({
        allowBlank: false,
        maxLength: 255,
        validator: function(val) {
            var results = true;
            if ( ! checkUniqueDisplayVal(val)) {
                results = "The Short Display Name " + val + " is not unique for this picklist.";
            }
            return results;
        },
        enableKeyEvents: true
    });
    displayValFld.on('keydown', function(fld, event) {
        saveOnEnter(fld, event);
    });

    var longDisplayFld = new Ext.form.TextField({
        allowBlank: true,
        maxLength: 255,
        enableKeyEvents: true
    });
    longDisplayFld.on('keydown', function(fld, event) {
        saveOnEnter(fld, event);
    });

    var descFld = new Ext.form.TextField({
        allowBlank: true,
        maxLength: 255,
        enableKeyEvents: true
    });
    descFld.on('keydown', function(fld, event) {
        saveOnEnter(fld, event);
    });

    var saveOnEnter = function(fld, event) {
        if (event.getKey() == 13) {
            setTimeout(function() {
                grid.saveButton.handler();
            }, 200);
        }
    }

    var colModel = new Ext.grid.ColumnModel({
        defaults: {
            sortable: true
        },
        columns: [
            new OrangeLeap.RowGrip({ tooltip: 'Click and Hold to Drag Row' } ),
            {
                header: 'Order',
                dataIndex: 'f',
                width: 23,
                sortable: false,
                editor: numberFld
            },
            {
                header: 'Short Display Name',
                dataIndex: 'c',
                editable: true,
                editor: displayValFld,
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
                    return escapeScriptTag(value);
                }
            },
            {
                header: 'Long Display Name',
                dataIndex: 'd',
                editable: true,
                editor: longDisplayFld,
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    return escapeScriptTag(value);
                }
            },
            {
                header: 'Description',
                dataIndex: 'e',
                editable: true,
                editor: descFld,
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    return escapeScriptTag(value);
                }
            },
            checkColumn,
            {header: 'Customize', width: 95, menuDisabled: true, fixed: true,
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    var url = 'picklistItemCustomize.htm?picklistNameId=' + escape(combo.getValue()) + '&picklistItemId=' +
                              record.data['id'] + '&defaultDisplayValue=' + escape(record.data['c']);
                    return '<a href="javascript:void(0)" onclick="Manager.customize(\'' + url + '\')" class="customizeLink">Customize</a>';
                } 
            }
        ]
    });
    var filters = new Ext.ux.grid.GridFilters({
        encode: false,
        local: true,
        filters: [{
            type: 'string',
            dataIndex: 'c'
        }, {
            type: 'string',
            dataIndex: 'd'
        }, {
            type: 'string',
            dataIndex: 'e'
        }, {
            type: 'boolean',
            dataIndex: 'g'
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
            {name: 'f', mapping: 'f', type: 'int', allowBlank: false, sortDir: 'ASC'},
            {name: 'b', mapping: 'b', type: 'string', allowBlank: false},
            {name: 'c', mapping: 'c', type: 'string', allowBlank: false},
            {name: 'd', mapping: 'd', type: 'string'},
            {name: 'e', mapping: 'e', type: 'string'},
            {name: 'g', mapping: 'g', type: 'boolean'}
        ]
    });
    store.setDefaultSort('f', 'ASC');
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
            store.suspendEvents();
            var recLength = store.data.items.length;
            var dataLength = data.length;
            for (var x = 0; x < recLength; x++) {
                var thisClientRec = store.data.items[x];
                for (var y = 0; y < dataLength; y++) {
                    var thisServerRec = data[y];
                    if (thisClientRec.phantom) {
                        if (thisClientRec.get('c') == thisServerRec.c) {
                            thisClientRec.id = thisServerRec['id'];
                            thisClientRec.phantom = false;
                            thisClientRec.set('id', thisServerRec['id']);
                            thisClientRec.set('b', thisServerRec['b']);
                            thisClientRec.set('f', thisServerRec['f']);
                            updatedRecords[updatedRecords.length] = thisClientRec.copy();
                            break;
                        }
                    }
                    else if (thisClientRec.id == thisServerRec.id) {
                        thisClientRec.set('f', thisServerRec['f']);
                        updatedRecords[updatedRecords.length] = thisClientRec.copy();
                        break;
                    }
                }
            }
            store.removeAll();
            store.add(updatedRecords);
            store.sort('f', 'ASC');
            grid.getView().refresh();
            grid.saveButton.disable();
            grid.undoButton.disable();
            store.resumeEvents();
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
                    state.field = 'f';
                    state.direction = 'ASC';
                }
                store.load({ params: { 'picklistNameId' : record.data['nameId'] }, sortInfo: {field: 'f', direction: 'ASC'}, callback: picklistItemsLoaded });
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
                var thisDisplayValue = store.data.items[x].get('c');
                if (Ext.isEmpty(thisDisplayValue)) {
                    isValid = false;
                    break;
                }
                else {
                    thisDisplayValue = thisDisplayValue.toLowerCase();
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
                                msg: 'You must fix the errors in the grid first before saving.'});
                        }
                    }
                }
            },
            {text: 'Undo', cls: 'button', ref: '../undoButton', disabled: true, handler: function() {
                    if (checkForModifiedRecords()) {
                        var thisGrid = Ext.get('managementGrid');
                        thisGrid.mask("Undoing...");
                        undoChanges();
                        $("#savedMarker").css('visibility', 'hidden');
                        grid.saveButton.disable();
                        grid.undoButton.disable();
                        thisGrid.unmask();
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
              disabled: true, handler: function(button, event) {
                    Manager.customize('picklistCustomize.htm?picklistNameId=' + escape(combo.getValue()));
                }
            }, '-',
            { text: 'Add Item', tooltip:'Add a new Picklist Item', iconCls:'add', id: 'addButton', ref: '../addButton',
              disabled: true, handler: function() {
                    var gStore = grid.getStore();
                    var PickItem = gStore.recordType;
                    var item = new PickItem({
                        id: 0,
                        b: 'NewItemName',
                        c: '',
                        d: '',
                        e: '',
                        f: store.data.length + 1,
                        g: false
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
        if (store.data && store.data.items) {
            var thisGrid = Ext.get('managementGrid');
            thisGrid.mask("Sorting...");
            store.suspendEvents();
            var items = store.data.items;
            var len = items.length;
            for (var x = 0; x < len; x++) {
                items[x].set('f', x + 1);
            }
            grid.getView().refresh();
            grid.saveButton.enable();
            grid.undoButton.enable();
            store.resumeEvents();
            thisGrid.unmask();
        }
    });

    var undoChanges = function() {
        store.suspendEvents();
        store.rejectChanges();
        var len = store.data.items.length;
        var y = 0;
        var toRemove = [];
        for (var x = 0; x < len; x++) {
            var rec = store.data.items[x];
            if ( ! rec.isValid()) {
                toRemove[y++] = rec;
            }
        }
        for (var z = 0; z < toRemove.length; z++) {
            store.remove(toRemove[z]);
        }
        store.sort('f', 'ASC');
        grid.getView().refresh();
        store.resumeEvents();
    }

    $('#managerGrid').keydown(function(e) {
        if (e.keyCode == 65 && e.altKey) {
            Ext.getCmp('addButton').handler();
        }
    });
});

Ext.ns('Manager');
Manager.customize = function(url) {
    if (url) {
        
    }
}

