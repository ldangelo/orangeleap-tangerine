Ext.onReady(function() {
    Ext.QuickTips.init(); 

    /* Manage Dashboard below */
    var dashboardProxy = new Ext.data.HttpProxy({
        api: {
            read: {url: 'manageDashboard.json', method: 'GET'},
            load: {url: 'manageDashboard.json', method: 'GET'},
            create: {url: 'manageDashboard.json', method: 'POST'},
            update: {url: 'manageDashboard.json', method: 'POST'},
            destroy: {url: 'manageDashboard.json', method: 'POST'}
        }
    });
    var dashboardSaveError = function() {
        var thisGrid = Ext.get('dashboardWin');
        thisGrid.unmask();
        Ext.MessageBox.show({ title: 'Error', icon: Ext.MessageBox.ERROR,
            buttons: Ext.MessageBox.OK,
            msg: 'The custom field changes could not be saved.  Please try again or contact your administrator if this issue continues.'});
    }

    dashboardProxy.on('write', function(proxy, action, data, response, records, options) {
        if (response.success === 'true') {
        	dashboardStore.suspendEvents();
        	dashboardStore.removeAll();

            var dataLength = data.length;
            var updatedRecords = [];
            for (var y = 0; y < dataLength; y++) {
                updatedRecords[updatedRecords.length] = new dashboardStore.recordType(data[y], data[y].id);
            }
            dashboardStore.add(updatedRecords);
            dashboardGrid.getView().refresh();
            dashboardStore.resumeEvents();
            var thisGrid = Ext.get('dashboardWin');
            thisGrid.unmask();
            $("#dashboardFieldsSavedMarker").show();
            setTimeout(function() {
                $("#dashboardFieldsSavedMarker").hide();
            }, 10000);
        }
        else {
        	dashboardSaveError();
        }
    });
    dashboardProxy.on('exception', function(proxy, type, action, options, response, args) {
    	dashboardSaveError();
    });

    var dashboardWriter = new Ext.data.JsonWriter({ listful: true, writeAllFields: true });

    var dashboardStore = new OrangeLeap.BulkSaveStore({
        batch: true,
        proxy: dashboardProxy,
        writer: dashboardWriter,
        autoSave: false,
        remoteSort: false,
        totalProperty: 'totalRows',
        root: 'rows',
        fields:[
            {name: 'id', type: 'string'},
            {name: 'type', type: 'string'},
            {name: 'title', type: 'string'},
            {name: 'url', type: 'string'},
            {name: 'order', type: 'string'}
        ]
    });
    dashboardStore.on('beforewrite', function(proxy, action, rs, options, args) {
        var thisGrid = Ext.get('dashboardWin');
        thisGrid.mask("Saving...");
    });

    function trapKeyDown(fld, event) {
        if (event.getKey() == event.ENTER) {
            setTimeout(function() {
            	dashboardWin.saveButton.handler();
            }, 100);
        }
        else if (event.getKey() == event.A && event.altKey) {
            Ext.getCmp('addFldButton').handler();
        }
    }

    function escapeScriptTag(val) {
        if (val) {
            if (val.indexOf('<script') > -1) {
                var beginIndex = val.indexOf('<script');
                val =  val.substring(0, beginIndex) + '&lt;script' + val.substring(beginIndex + 7);
            }
            if (val.indexOf('</script') > -1) {
                var endIndex = val.indexOf('</script');
                val =  val.substring(0, endIndex) + '&gt;/script' + val.substring(endIndex + 8);
            }
            val = val.replace(/</g, '&lt;').replace(/>/g, '&gt;');
        }
        return val;
    }

    function checkIfValid() {
        var isValid = true;
        var valCtMap = {};
        // Renumber order
        if (dashboardStore.data && dashboardStore.data.items) {
            var len = dashboardStore.data.items.length;
            for (var x = 0; x < len; x++) {
                dashboardStore.data.items[x].set('order', ""+x);
            }
        }
        return isValid;
    }
/*
    var dashboardNameFld = new Ext.form.TextField({
        allowBlank: false,
        maxLength: 255,
        enableKeyEvents: true,
        validator: function(val) {
            var results = true;
            if (Ext.isEmpty(val)) {
                results = "This field is required";
            }
            return results;
        }
    });
    dashboardNameFld.on('keydown', function(fld, event) {
        trapKeyDown(fld, event);
    });

    var dashboardDescFld = new Ext.form.TextField({
        allowBlank: true,
        maxLength: 255,
        enableKeyEvents: true
    });
    dashboardDescFld.on('keydown', function(fld, event) {
        trapKeyDown(fld, event);
    });

    var dashboardValueFld = new Ext.form.TextField({
        allowBlank: false,
        maxLength: 255,
        enableKeyEvents: true,
        validator: function(val) {
            var results = true;
            if (Ext.isEmpty(val)) {
                results = "This field is required";
            }
            return results;
        }
    });
    dashboardValueFld.on('keydown', function(fld, event) {
        trapKeyDown(fld, event);
    });
    */

    var dashboardGrid = new Ext.grid.EditorGridPanel({
        store: dashboardStore,
        header: false,
        frame: false,
        border: false,
        id: 'dashboardGrid',
        viewConfig: { forceFit: true },
        clicksToEdit: 1,
        columns: [
            {   header: 'Id',
                width: 170,
                sortable: true,
                dataIndex: 'id',
                align: 'left',
                editable: false
            },
            {   header: 'Type',
                width: 255,
                sortable: true,
                dataIndex: 'type',
                align: 'left',
                editable: true
            },
            {   header: 'Title',
                width: 255,
                sortable: true,
                dataIndex: 'title',
                align: 'left',
                editable: true
            },
            {   header: 'Url',
                width: 255,
                sortable: true,
                dataIndex: 'url',
                align: 'left',
                editable: true
            },
            {   header: ' ', width: 25, menuDisabled: true, fixed: true,
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    return '<a href="javascript:void(0)" class="deleteLink" id="delete-link-' + record.id + '" title="Remove Item">Remove</a>';
                }
            }
        ],
        tbar: [
            { text: 'Add New', tooltip:'Add a new Item', iconCls:'add', id: 'addFldButton', ref: '../addFldButton',
              handler: function() {
                    var cgStore = dashboardGrid.getStore();
                    var Fld = cgStore.recordType;
                    var field = new Fld({
                        id: '0',
                        type: '',
                        title: '',
                        url: false
                    });
                    dashboardGrid.stopEditing();
                    var nextIndex = cgStore.getCount();
                    cgStore.add(field);
                    dashboardGrid.startEditing(nextIndex, 0);
                }
            }
        ]
    });
    dashboardGrid.on('keydown', function(e) {
        if (e.getKey() == e.A && e.altKey) {
            Ext.getCmp('addFldButton').handler();
        }
    });
    dashboardGrid.on('click', function(event) {
        $("#dashboardFieldsSavedMarker").hide();
        var target = event.getTarget('a.deleteLink');
        if (target) {
            event.stopPropagation();
            var id = target.id;
            if (id) {
                id = id.replace('delete-link-', '');
                var rec = dashboardStore.getById(id);
                dashboardStore.remove(rec);
            }
        }
    });

    var dashboardWin = new Ext.Window({
        title: 'Manage Dashboard <span id="dashboardFieldsSavedMarker" class="savedIcon">Saved</span>',
        layout: 'fit',
        width: 700,
        height: 500,
        cls: 'win',
        id: 'dashboardWin',
        buttons: [
            {   text: 'Save',
                cls: 'saveButton',
                ref: '../saveButton',
                handler: function(button, event) {
                    if (checkIfValid()) {
                        $("#dashboardFieldsSavedMarker").hide();
                        dashboardStore.saveAll();
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
            	    dashboardWin.hide(this);
                }
            }
        ],
        buttonAlign: 'center',
        modal: true,
        closable: false,
        closeAction: 'hide'
    });

    dashboardWin.add(dashboardGrid);
    var hideOnEscape = function(e, win) {
        if (e.keyCode == 27) {
            win.hide();
        }
    }
    dashboardWin.on('beforeshow', function(win) {
        $(window).bind('keydown', function(e) {
            hideOnEscape(e, win);
        });
    });
    dashboardWin.on('beforehide', function(win) {
        $(window).unbind('keydown', hideOnEscape);
    });

    var manageDashboard = function() {
    	dashboardStore.load({ sortInfo: { field: 'order', direction: 'ASC'},
            callback: function(records, options, success) {
                if (success) {
                	dashboardWin.show();
                }
                else {
                    Ext.MessageBox.show({ title: 'Error', icon: Ext.MessageBox.ERROR,
                        buttons: Ext.MessageBox.OK,
                        msg: 'Could not load the Dashboard window.  Please try again or contact your administrator if this issue continues.'});
                }
            }
        });
    }
});