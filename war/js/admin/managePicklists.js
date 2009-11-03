Ext.onReady(function() {
    var checkColumn = new Ext.grid.CheckColumn({
       header: 'Inactive?',
       dataIndex: 'g',
       width: 55
    });

    function checkUnique(storeObj, fieldName, val, isCaseSensitive) {
        var isUnique = true;
        var valCount = 0;
        if (val && storeObj.data && storeObj.data.items) {
            var len = storeObj.data.items.length;
            for (var x = 0; x < len; x++) {
                if (valCount < 2) {
                    var thisItem = storeObj.data.items[x];
                    if ((isCaseSensitive && thisItem.get(fieldName).toLowerCase() == val.toLowerCase()) ||
                        ( ! isCaseSensitive && thisItem.get(fieldName) == val)){
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
        trapKeyDown(grid, fld, event, 'addButton');
    });
    numberFld.on('change', function(fld, newVal, oldVal) {
        var rec = null;
        var index = -1;
        if (Ext.isIE && previouslySelectedRecord) {
            rec = previouslySelectedRecord; // Apparent bug in Ext/IE where the Store's record value is set to the newVal by the time the change method is called
            index = store.indexOf(previouslySelectedRecord);
        }
        else {
            index = store.find('f', oldVal);
            if (index > -1) {
                rec = store.getAt(index);
            }
        }

        if (rec && index > -1) {
            var endIndex = store.data.items.length - 1;
            if (endIndex < 0) {
                endIndex = 0;
            }
            store.removeAt(index);
            if (newVal > 0) {
                newVal = newVal - 1; // decrement
            }
            if (newVal > endIndex) {
                newVal = endIndex;
            }
            rec.set('f', newVal + 1);
            store.insert(newVal, rec)
            grid.getView().refresh();
            setTimeout(function() {
                var sm = grid.getSelectionModel();
                if (sm) {
                    sm.selectRecords(rec);
                }
            }, 200);
        }
    });

    var displayValFld = new Ext.form.TextField({
        allowBlank: false,
        maxLength: 255,
        validator: function(val) {
            var results = true;
            if (Ext.isEmpty(val)) {
                results = "This field is required";
            }
            if ( ! checkUnique(store, 'c', val, true)) {
                results = "The Short Display Name " + val + " is not unique for this picklist.";
            }
            return results;
        },
        enableKeyEvents: true
    });
    displayValFld.on('keydown', function(fld, event) {
        trapKeyDown(grid, fld, event, 'addButton');
    });

    var longDisplayFld = new Ext.form.TextField({
        allowBlank: true,
        maxLength: 255,
        enableKeyEvents: true
    });
    longDisplayFld.on('keydown', function(fld, event) {
        trapKeyDown(grid, fld, event, 'addButton');
    });

    var descFld = new Ext.form.TextField({
        allowBlank: true,
        maxLength: 255,
        enableKeyEvents: true
    });
    descFld.on('keydown', function(fld, event) {
        trapKeyDown(grid, fld, event, 'addButton');
    });

    var trapKeyDown = function(obj, fld, event, addButtonId) {
        if (event.getKey() == event.ENTER) {
            setTimeout(function() {
                obj.saveButton.handler();
            }, 100);
        }
        else if (event.getKey() == event.A && event.altKey) {
            Ext.getCmp(addButtonId).handler();
        }
    }

    var colModel = new Ext.grid.ColumnModel({
        defaults: {
            sortable: true
        },
        columns: [
            new OrangeLeap.RowGrip({ tooltip: 'Click and Hold to Drag Row' } ),
            {
                header: "<span class='required'>*</span> Order",
                dataIndex: 'f',
                width: 23,
                sortable: false,
                editor: numberFld,
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    return rowIndex + 1;
                }
            },
            {
                header: "<span class='required'>*</span> Short Display Name",
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
                        else if ( ! checkUnique(store, 'c', value, true)) {
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
                    return '<a href="javascript:void(0)" class="customizeLink' +
                           (Ext.isNumber(parseInt(record.data['id'], 10)) && parseInt(record.data['id'], 10) > 0 ? '' : ' disabled') +
                           '" id="custom-link-' + record.data['id'] + '" title="Customize">Customize</a>';
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

    var store = new OrangeLeap.OrderedBulkSaveStore({
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
            grid.getView().refresh();
            grid.saveButton.disable();
            grid.undoButton.disable();
            store.resumeEvents();
            var thisGrid = Ext.get('managementGrid');
            thisGrid.unmask();
            $("#savedMarker").css('visibility', 'visible');
            setTimeout(function() {
                $("#savedMarker").css('visibility', 'hidden');
            }, 15000);
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
        if ( ! grid.saveButton.disabled) {
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

    var checkIfValid = function(storeObj, key, isCaseSensitive) {
        var isValid = true;
        var valCtMap = {};
        if (storeObj.data && storeObj.data.items) {
            var len = storeObj.data.items.length;
            for (var x = 0; x < len; x++) {
                var thisVal = storeObj.data.items[x].get(key);
                if (Ext.isEmpty(thisVal)) {
                    isValid = false;
                    break;
                }
                else {
                    if (isCaseSensitive) {
                        thisVal = thisVal.toLowerCase();
                    }
                    if ( ! valCtMap[thisVal]) {
                        valCtMap[thisVal] = 1;
                    }
                    else {
                        var count = valCtMap[thisVal];
                        valCtMap[thisVal] = ++count;
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
            {text: 'Save', cls: 'saveButton', ref: '../saveButton', disabled: true,
                disabledClass: 'disabledButton',
                handler: function() {
                    if (checkIfValid(store, 'c', true)) {
                        $("#savedMarker").css('visibility', 'hidden');
                        store.save();
                    }
                    else {
                        Ext.MessageBox.show({ title: 'Correct Errors', icon: Ext.MessageBox.WARNING,
                            buttons: Ext.MessageBox.OK,
                            msg: 'You must fix the highlighted errors first before saving.'});
                    }
                }
            },
            {text: 'Undo', cls: 'button', ref: '../undoButton', disabled: true,
                disabledClass: 'disabledButton',
                handler: function() {
                    var thisGrid = Ext.get('managementGrid');
                    thisGrid.mask("Undoing...");
                    undoChanges();
                    $("#savedMarker").css('visibility', 'hidden');
                    grid.saveButton.disable();
                    grid.undoButton.disable();
                    thisGrid.unmask();
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
                    var picklistNameId = combo.getValue();
                    customizeThisPicklist({ 'picklistNameId': picklistNameId }, combo.getRawValue(), picklistNameId, null);
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

    grid.on('click', function(event) {
        $("#savedMarker").css('visibility', 'hidden');
        var target = event.getTarget('a.customizeLink');
        if (target) {
            event.stopPropagation();
            var id = target.id;
            id = id.replace('custom-link-', '');
            id = parseInt(id, 10);
            if (Ext.isNumber(id) && id > 0) {
                var rec = store.getById(id);
                var picklistNameId = combo.getValue();
                customizeThisPicklist({ 'picklistNameId': picklistNameId, 'picklistItemId': id },
                        combo.getRawValue() + ': ' + (rec ? rec.get('c') : ''), picklistNameId, id);
            }
            else {
                Ext.MessageBox.show({ title: 'Save Item First', icon: Ext.MessageBox.INFO,
                    buttons: Ext.MessageBox.OK,
                    msg: 'You must save this item first before you can customize it.'});
            }
        }
    });
    var previouslySelectedRecord = null;
    grid.on('cellclick', function(grid, rowIndex, columnIndex, event) {
        if (columnIndex == 1) {
            var record = store.data.items[rowIndex];
            var myIndex = rowIndex + 1;
            previouslySelectedRecord = record;
            if (record.get('f') != myIndex) {
                record.set('f', myIndex);
            }
        }
    });
    grid.on('sortchange', function() {
        if (store.data && store.data.items) {
            grid.saveButton.enable();
            grid.undoButton.enable();
        }
    });

    var undoChanges = function() {
        store.rejectChanges();
        var len = store.data.items.length;
        var y = 0;
        var toRemove = [];
        for (var x = 0; x < len; x++) {
            var rec = store.data.items[x];
            if ( ! rec.isValid() || rec.phantom) {
                toRemove[y++] = rec;
            }
        }
        for (var z = 0; z < toRemove.length; z++) {
            store.remove(toRemove[z]);
        }
        store.sort('f', 'ASC');
    }

    $('#managerGrid').keydown(function(e) {
        if (e.keyCode == e.A && e.altKey) {
            Ext.getCmp('addButton').handler();
        }
    });

    /* All of the following below is to Customize a Picklist and a Picklist Item */
    var customizeProxy = new Ext.data.HttpProxy({
        api: {
            read: {url: 'customizePicklist.json', method: 'GET'},
            load: {url: 'customizePicklist.json', method: 'GET'},
            create: {url: 'customizePicklist.json', method: 'POST'},
            update: {url: 'customizePicklist.json', method: 'POST'},
            destroy: {url: 'customizePicklist.json', method: 'POST'}
        }
    });
    var customizeSaveError = function() {
        var thisGrid = Ext.get('customizeWin');
        thisGrid.unmask();
        Ext.MessageBox.show({ title: 'Error', icon: Ext.MessageBox.ERROR,
            buttons: Ext.MessageBox.OK,
            msg: 'The custom field changes could not be saved.  Please try again or contact your administrator if this issue continues.'});
    }

    customizeProxy.on('write', function(proxy, action, data, response, records, options) {
        if (response.success === 'true') {
            customizeStore.suspendEvents();
            customizeStore.removeAll();
            
            var dataLength = data.length;
            var updatedRecords = [];
            for (var y = 0; y < dataLength; y++) {
                updatedRecords[updatedRecords.length] = new customizeStore.recordType(data[y], data[y].id);
            }
            customizeStore.add(updatedRecords);
            customizeGrid.getView().refresh();
            customizeStore.resumeEvents();
            var thisGrid = Ext.get('customizeWin');
            thisGrid.unmask();
            $("#customizedFieldsSavedMarker").show();
            setTimeout(function() {
                $("#customizedFieldsSavedMarker").hide();
            }, 10000);
        }
        else {
            customizeSaveError();
        }
    });
    customizeProxy.on('exception', function(proxy, type, action, options, response, args) {
        customizeSaveError();
    });
    
    var customizeWriter = new Ext.data.JsonWriter({ listful: true, writeAllFields: true });

    var customizeStore = new OrangeLeap.BulkSaveStore({
        batch: true,
        proxy: customizeProxy,
        writer: customizeWriter,
        autoSave: false,
        remoteSort: false,
        totalProperty: 'totalRows',
        root: 'rows',
        fields:[
            {name: 'id', type: 'string'},
            {name: 'key', type: 'string'},
            {name: 'value', type: 'string'}
        ]
    });
    customizeStore.on('beforewrite', function(proxy, action, rs, options, args) {
        var thisGrid = Ext.get('customizeWin');
        thisGrid.mask("Saving...");
        if (options) {
            if (customizedPicklistNameId) {
                options.params['picklistNameId'] = customizedPicklistNameId;
            }
            if (customizedPicklistItemId) {
                options.params['picklistItemId'] = customizedPicklistItemId;
            }
        }
    });

    var cusFieldNameFld = new Ext.form.TextField({
        allowBlank: false,
        maxLength: 255,
        enableKeyEvents: true,
        validator: function(val) {
            var results = true;
            if (Ext.isEmpty(val)) {
                results = "This field is required";
            }
            if ( ! checkUnique(customizeStore, 'key', val, false)) {
                results = "The custom field name " + val + " is not unique.";
            }
            return results;
        }
    });
    cusFieldNameFld.on('keydown', function(fld, event) {
        trapKeyDown(customizeWin, fld, event, 'addFldButton');
    });
    
    var cusFieldValueFld = new Ext.form.TextField({
        allowBlank: true,
        maxLength: 255,
        enableKeyEvents: true
    });
    cusFieldValueFld.on('keydown', function(fld, event) {
        trapKeyDown(customizeWin, fld, event, 'addFldButton');
    });

    var customizeGrid = new Ext.grid.EditorGridPanel({
        store: customizeStore,
        header: false,
        frame: false,
        border: false,
        id: 'customizeGrid',
        viewConfig: { forceFit: true },
        clicksToEdit: 1,
        columns: [
            {   header: 'Field Name', 
                width: 170,
                sortable: true,
                dataIndex: 'key',
                align: 'left',
                editable: true,
                editor: cusFieldNameFld,
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    if (record) {
                        if (Ext.isEmpty(value)) {
                            metaData.css += ' x-form-invalid';
                            metaData.attr = 'ext:qtip="A custom field name is required"; ext:qclass="x-form-invalid-tip"';
                        }
                        else if ( ! checkUnique(customizeStore, 'key', value, false)) {
                            metaData.css += ' x-form-invalid';
                            metaData.attr = 'ext:qtip="The custom field name ' + value + ' is not unique."; ext:qclass="x-form-invalid-tip"';
                        }
                        else {
                            metaData.css = '';
                            metaData.attr = 'ext:qtip=""';
                        }
                    }
                    return escapeScriptTag(value);
                }
            },
            {   header: 'Field Default',
                width: 170,
                sortable: true,
                dataIndex: 'value',
                align: 'left',
                editable: true,
                editor: cusFieldValueFld,
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    return escapeScriptTag(value);
                }
            },
            {   header: ' ', width: 25, menuDisabled: true, fixed: true,
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    var fldName = record.get('key');
                    if (fldName && fldName.toLowerCase().indexOf('accountstring') > -1) {
                        return '';
                    }
                    return '<a href="javascript:void(0)" class="deleteLink" id="delete-link-' + record.id + '" title="Remove Custom Field">Remove</a>';
                }
            }
        ],
        tbar: [
            { text: 'Add New', tooltip:'Add a new Custom Field', iconCls:'add', id: 'addFldButton', ref: '../addFldButton',
              handler: function() {
                    var cgStore = customizeGrid.getStore();
                    var CustFld = cgStore.recordType;
                    var field = new CustFld({
                        key: '',
                        value: ''
                    });
                    customizeGrid.stopEditing();
                    var nextIndex = cgStore.getCount();
                    cgStore.add(field);
                    customizeGrid.startEditing(nextIndex, 0);
                }
            }
        ]
    });
    customizeGrid.on('keydown', function(e) {
        if (e.getKey() == e.A && e.altKey) {
            Ext.getCmp('addFldButton').handler();
        }
    });
    customizeGrid.on('beforeedit', function(obj) {
        var fldName = obj.record.get('key');
        if (fldName && fldName.toLowerCase().indexOf('accountstring') > -1) {
            obj.cancel = true;
        }
    });
    customizeGrid.on('click', function(event) {
        $("#customizedFieldsSavedMarker").hide();
        var target = event.getTarget('a.deleteLink');
        if (target) {
            event.stopPropagation();
            var id = target.id;
            if (id) {
                id = id.replace('delete-link-', '');
                var rec = customizeStore.getById(id);
                customizeStore.remove(rec);
            }
        }
    });

    var customizeWin = new Ext.Window({
        title: 'Customize Fields <span id="customizedFieldsSavedMarker">Saved</span>',
        layout: 'fit',
        width: 400,
        height: 300,
        cls: 'win',
        id: 'customizeWin',
        buttons: [
            {   text: 'Save',
                cls: 'saveButton',
                ref: '../saveButton',
                handler: function(button, event) {
                    if (checkIfValid(customizeStore, 'key', false)) {
                        $("#customizedFieldsSavedMarker").hide();
                        customizeStore.saveAll();
                    }
                    else {
                        Ext.MessageBox.show({ title: 'Correct Errors', icon: Ext.MessageBox.WARNING,
                            buttons: Ext.MessageBox.OK,
                            msg: 'You must fix the highlighted errors first before saving.'});
                    }
                }
            },
            {   text: 'Close',
                cls: 'button',
                ref: '../closeButton',
                handler: function(button, event) {
                    customizeWin.hide(this);
                }
            }
        ],
        buttonAlign: 'center',
        modal: true,
        closable: false,
        closeAction: 'hide'
    });

    customizeWin.add(customizeGrid);
    var hideOnEscape = function(e, win) {
        if (e.keyCode == 27) {
            win.hide();
        }
    }
    customizeWin.on('beforeshow', function(win) {
        $(window).bind('keydown', function(e) {
            hideOnEscape(e, win);
        });
    });
    customizeWin.on('beforehide', function(win) {
        $(window).unbind('keydown', hideOnEscape);
    });

    var customizedPicklistNameId = null;
    var customizedPicklistItemId = null;

    var customizeThisPicklist = function(params, title, picklistNameId, picklistItemId) {
        customizedPicklistNameId = picklistNameId;
        customizedPicklistItemId = picklistItemId;
        
        customizeStore.load({ params: params, sortInfo: { field: 'key', direction: 'ASC'},
            callback: function(records, options, success) {
                if (success) {
                    if (title) {
                        customizeWin.setTitle('Customize Fields for ' + title + ' <span id="customizedFieldsSavedMarker" class="savedIcon">Saved</span>');
                    }
                    customizeWin.show();
                }
                else {
                    Ext.MessageBox.show({ title: 'Error', icon: Ext.MessageBox.ERROR,
                        buttons: Ext.MessageBox.OK,
                        msg: 'Could not load the customize window.  Please try again or contact your administrator if this issue continues.'});
                }
            }
        });
    }
});

