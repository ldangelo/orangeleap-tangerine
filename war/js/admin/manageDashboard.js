Ext.onReady(function() {
   
	Ext.QuickTips.init();
    
    var availableRolesStore = [];
    
    $.ajax({
        type: "GET",
        url: "manageDashboard.json",
        data: "availableRoles=true",
        success: function(html){
    		html = eval("(" + html + ")");
    		html = html.availableRoles;
    		onReadyContinue(html.split(","));
        	return false;
        }

    });
    
}); 

function onReadyContinue(availableRolesStore) {    
	
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
    
   
    
    var writer = new Ext.data.JsonWriter({ listful: true, writeAllFields: true });

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
                {name: 'itemid', type: 'int'},
                {name: 'type', type: 'string'},
                {name: 'title', type: 'string'},
                {name: 'url', type: 'string'},
                {name: 'order', type: 'string'},
                {name: 'roles', type: 'string'}
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
    store.on('load', function(store, records, index) {

        // unescape urls
        if (store.data && store.data.items) {
            var len = store.data.items.length;
            for (var x = 0; x < len; x++) {
                var eurl = store.data.items[x].get('url');
                eurl = Ext.util.Format.htmlDecode(eurl);
                store.data.items[x].set('url', eurl);
            }
        }
        
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



    var stringFld = new Ext.form.TextField({
        allowBlank: true,
        maxLength: 255,
        enableKeyEvents: true
    });
    stringFld.on('keydown', function(fld, event) {
        trapKeyDown(grid, fld, event, 'addFldButton');
    });
    
    var numberFld = new Ext.form.NumberField({
        allowBlank: false,
        allowNegative: false,
        allowDecimals: false,
        minValue: 0,
        width: 23,
        enableKeyEvents: true
    });
    numberFld.on('keydown', function(fld, event) {
        trapKeyDown(grid, fld, event, 'addFldButton');
    });
    numberFld.on('change', function(fld, newVal, oldVal) {
        var rec = null;
        var index = -1;
        if (Ext.isIE && previouslySelectedRecord) {
            rec = previouslySelectedRecord; // Apparent bug in Ext/IE where the Store's record value is set to the newVal by the time the change method is called
            index = store.indexOf(previouslySelectedRecord);
        }
        else {
            index = store.find('order', oldVal);
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
            rec.set('order', newVal + 1);
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
    

    

    var grid = new Ext.grid.EditorGridPanel({
        store: store,
        width: 780,
        height: 600,
        title: 'Manage Dashboard Items <span id="savedMarker">Saved</span>',
        loadMask: true,
        frame: true,
        renderTo: 'managerGrid',
        viewConfig: { forceFit: true },
        selModel: new Ext.grid.RowSelectionModel({}),
        enableDragDrop: true,
        clicksToEdit: 1,
        columns: [
             new OrangeLeap.RowGrip({ tooltip: 'Click and Hold to Drag Row' } ),
             {
                 header: "<span class='required'>*</span> Order",
                 dataIndex: 'order',
                 width: 23,
                 sortable: false,
                 editor: numberFld,
                 renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                     return rowIndex + 1;
                 }
             },            
             {  header: 'Type',
                width: 40,
                sortable: false,
                dataIndex: 'type',
                align: 'left',
                editable: true,
                editor: new Ext.form.ComboBox({
                	typeAhead:true,
                	triggerAction:'all',
                	store:['Guru','Rss','Text','IFrame'],
                	valueField: 'val',
                	displayField: 'desc',
                	lazyRenderer: true
                })
            },
            {   header: 'Title',
                width: 170,
                sortable: false,
                dataIndex: 'title',
                align: 'left',
                editable: true,
                editor: stringFld
            },
            {   header: 'Role',
                width: 100,
                sortable: false,
                dataIndex: 'roles',
                align: 'left',
                editable: true,
                editor: new Ext.form.ComboBox({
                	typeAhead:true,
                	triggerAction:'all',
                	store: availableRolesStore,  
                	valueField: 'val',
                	displayField: 'desc',
                	lazyRenderer: true
                })
            },
            {   header: 'Details',
                width: 170,
                sortable: false,
                dataIndex: 'url',
                align: 'left',
                editable: true,
                editor: stringFld
            },
            {   header: ' ', width: 25, menuDisabled: true, fixed: true,
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    return '<a href="javascript:void(0)" class="deleteLink" id="delete-link-' + record.id + '" title="Remove Dashboard Item">Remove</a>';
                }
            }
        ],
        tbar: [
               { text: 'Add New', tooltip:'Add a new Dashboard Item', iconCls:'add', id: 'addFldButton', ref: '../addFldButton',
                 handler: function() {
                       var aStore = grid.getStore();
                       var Rec = aStore.recordType;
                       var row = new Rec({
                    	   itemid: '0',
                           type: 'Guru',
                           title: '',
                           url: '',
                           order: '0'
                       });
                       grid.stopEditing();
                       var nextIndex = aStore.getCount();
                       aStore.add(row);
                       grid.startEditing(nextIndex, 0);
                   }
               }
           ],
        buttons: [
            {text: 'Save', cls: 'saveButton', ref: '../saveButton', disabled: true,
                disabledClass: 'disabledButton',
                handler: function() {
                    $("#savedMarker").css('visibility', 'hidden');
                    store.saveAll();
                }
            },
            {text: 'Undo', cls: 'button', ref: '../undoButton', disabled: true,
                disabledClass: 'disabledButton',
                handler: function() {
            		store.rejectChanges();
            		window.location.reload();
                }
            }
        ],
        buttonAlign: 'center'
    });
    var dropTgt = new Ext.ux.dd.GridDropTarget(grid.getEl(), {
        grid: grid,
        ddGroup: grid.ddGroup || 'GridDD'
    });    

    grid.on('click', function(event) {
        $("#savedMarker").css('visibility', 'hidden');
        var target = event.getTarget('a.deleteLink');
        if (target) {
            event.stopPropagation();
            var id = target.id;
            if (id) {
                id = id.replace('delete-link-', '');
                var rec = store.getById(id);
                store.remove(rec);
            }
            grid.saveButton.enable();
            grid.undoButton.enable();
        }
    });    
    
    var previouslySelectedRec = null;
    grid.on('cellclick', function(grid, rowIndex, columnIndex, event) {
        if (columnIndex == 1) {
            var record = store.data.items[rowIndex];
            var myIndex = rowIndex + 1;
            previouslySelectedRecord = record;
            if (record.get('order') != myIndex) {
                record.set('order', myIndex);
            }
        }
    });
    grid.on('sortchange', function() {
        if (store.data && store.data.items) {
            grid.saveButton.enable();
            grid.undoButton.enable();
        }
    });
    
}