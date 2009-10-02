Ext.override(Ext.data.Store, {
	insert : function(index, records){
		records = [].concat(records);
		var snapshotIx;
		if(this.snapshot){
			snapshotIx = index ? this.snapshot.indexOf(this.getAt(index - 1)) + 1 : 0;
		}
		for(var i = 0, len = records.length; i < len; i++){
			this.data.insert(index, records[i]);
			if(this.snapshot){
				this.snapshot.insert(snapshotIx, records[i]);
			}
			records[i].join(this);
		}
		this.fireEvent("add", this, records, index);
	},
	getById : function(id){
		return (this.snapshot || this.data).key(id);
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
        if (record && record.data && record.data['nameId']) {
            grid.addButton.disable();
            grid.customizeButton.disable();
            store.load({ params: { 'picklistNameId' : record.data['nameId'] }, callback: picklistItemsLoaded });
        }
    });

    var grid = new Ext.grid.EditorGridPanel({
        store: store,
        colModel: colModel,
        renderTo: 'managerGrid',
        width: 780,
        height: 600,
        title: 'Manage Picklist Items',
        loadMask: true,
        enableDragDrop: true,
        xtype: "grid",
        // specify the check column plugin on the grid so the plugin is initialized
        plugins: [ checkColumn, filters//, new Ext.ux.dd.GridDragDropRowOrder({
//                scrollable: true // enable scrolling support (default is false)
//            })
        ],
        selModel: new Ext.grid.RowSelectionModel({}),
        clicksToEdit: 1,
        tbar: [
            'Picklist: ', ' ', combo, ' ', ' ', '-',
            { text: 'Customize', tooltip:'Customize Picklist', iconCls: 'customize', ref: '../customizeButton',
              disabled: true, handler : function() {
                }
            }, '-',
            { text: 'Add Item', tooltip:'Add a new Picklist Item', iconCls:'add', ref: '../addButton',
              disabled: true, handler : function() {
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

    grid.on('afteredit', function(edit) {
    });
});

