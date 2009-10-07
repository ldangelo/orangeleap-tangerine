Ext.onReady(function() {
    var checkColumn = new Ext.grid.CheckColumn({
       header: 'Inactive?',
       dataIndex: 'inactive',
       width: 55
    });

    var originalItemsOrder = null;

    var colModel = new Ext.grid.ColumnModel({
        defaults: {
            sortable: true
        },
        columns: [
            new Ext.grid.RowNumberer(),
            {
                header: 'Short Display Name',
                dataIndex: 'displayVal',
                width: 120,
                editable: true,
                editor: new Ext.form.TextField({
                    allowBlank: false,
                    maxLength: 255
                })
            },
            {
                header: 'Long Display Name',
                dataIndex: 'desc',
                width: 255,
                editable: true,
                editor: new Ext.form.TextField({
                    allowBlank: true,
                    maxLength: 255
                })
            },
            {
                header: 'Description',
                dataIndex: 'detail',
                width: 190,
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
        // encode and local configuration options defined previously for easier reuse
        encode: false, // json encode the filter query
        local: true,   // defaults to false (remote filtering)
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

    var store = new Ext.data.JsonStore({
        url: 'getPicklistItems.json',
        totalProperty: 'totalRows',
        root: 'rows',
        remoteSort: false,
        fields: [
            {name: 'id', mapping: 'id', type: 'int'},
            {name: 'itemName', mapping: 'itemName', type: 'string'},
            {name: 'displayVal', mapping: 'displayVal', type: 'string'},
            {name: 'desc', mapping: 'desc', type: 'string'},
            {name: 'detail', mapping: 'detail', type: 'string'},
            {name: 'inactive', mapping: 'inactive', type: 'boolean'}
        ]
    });
    store.on('load', function(store, records, options) {
        if (store.data && store.data.keys) {
            originalItemsOrder = store.data.keys.toString();
        }
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
    combo.on('select', function(comboBox, record, index) {
        var doSelect = function(record) {
            if (record && record.data && record.data['nameId']) {
                grid.addButton.disable();
                grid.customizeButton.disable();
                store.load({ params: { 'picklistNameId' : record.data['nameId'] }, callback: picklistItemsLoaded });
            }
        }
        if (checkForModifiedRecords()) {
            undoChanges(function() {
                doSelect(record);
            });
        }
        else {
            doSelect(record);
        }
    });

    var checkForModifiedRecords = function() {
        var data = store.data;
        var hasModified = false;
        if (data) {
            var itemNames = data.keys;
            if ((store.getModifiedRecords() && store.getModifiedRecords().length > 0) ||
                (itemNames && originalItemsOrder && (itemNames.toString() != originalItemsOrder))) {
                hasModified = true;
            }
        }
        return hasModified;
    }

    var undoChanges = function(callback) {
        Ext.Msg.show({
            title: 'Lose Changes?',
            msg: 'You have made changes to this picklist.  Would you like to continue without saving?',
            buttons: Ext.Msg.OKCANCEL,
            icon: Ext.MessageBox.WARNING,
            fn: function(btn, text) {
                if (btn == "ok") {
                    store.rejectChanges();
                    if (callback && Ext.isFunction(callback)) {
                        callback();
                    }
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
        buttons: [
            {text: 'Save', handler: function() {
                    if (checkForModifiedRecords()) {

                    }
                }
            },
            {text: 'Undo', handler: function() {
                    if (checkForModifiedRecords()) {
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

    $('#managerGrid').keydown(function(e) {
        if (e.keyCode == 65 && e.altKey) {
            Ext.getCmp('addButton').handler();
        }
    });
});

