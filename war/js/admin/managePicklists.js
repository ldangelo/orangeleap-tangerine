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
                fixed: true,
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
                    maxLength: 255
                })
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
                grid.addButton.disable();
                grid.customizeButton.disable();
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
        title: 'Manage Picklist Items',
        loadMask: true,
        frame: true,
        id: 'managementGrid',
        viewConfig: { forceFit: false },
        buttons: [
            {text: 'Save', cls: 'saveButton', handler: function() {
                    if (checkForModifiedRecords()) {
                        store.save();
                    }
                }
            },
            {text: 'Undo', cls: 'button', handler: function() {
                    if (checkForModifiedRecords()) {
                        store.rejectChanges();
                        undoChanges();
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

