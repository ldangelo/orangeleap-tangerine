Ext.onReady(function() {
    Ext.QuickTips.init(); 
    var proxy = new Ext.data.HttpProxy({
        api: {
            read: {url: 'manageSiteSettings.json', method: 'GET'},
            load: {url: 'manageSiteSettings.json', method: 'GET'},
            create: {url: 'manageSiteSettings.json', method: 'POST'},
            update: {url: 'manageSiteSettings.json', method: 'POST'},
            destroy: {url: 'manageSiteSettings.json', method: 'POST'}
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

    var reader = new Ext.data.JsonReader({
        root: 'rows',
        totalProperty: 'totalRows',
        fields: [
            {name: 'id', type: 'string'},
            {name: 'label', type: 'string'},
            {name: 'value', type: 'string'},
            {name: 'group', type: 'string'},
            {name: 'class', type: 'string'},
            {name: 'maxLen', type: 'int'}
        ]
    });
    var writer = new Ext.data.JsonWriter({ listful: true });

    var store = new OrangeLeap.GroupingBulkSaveStore({
        batch: true,
        proxy: proxy,
        writer: writer,
        reader: reader,
        autoLoad: true,
        autoSave: false
    });
    store.on('load', function(store, records, options) {
        store.sortInfo = { field: 'label', direction: 'ASC'};
        store.sort('label');
        store.groupBy('group', true);
    });
    store.on('exception', function(misc) {
        Ext.MessageBox.show({ title: 'Error', icon: Ext.MessageBox.ERROR,
            buttons: Ext.MessageBox.OK,
            msg: 'An exception occurred with the grid.  Please try again or contact your administrator if this issue continues.'});
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

    var valueFld = new Ext.form.TextField({
        allowBlank: true,
        maxLength: 255,
        enableKeyEvents: true
    });
    valueFld.on('keydown', function(fld, event) {
        if (event.getKey() == event.ENTER) {
            setTimeout(function() {
                grid.saveButton.handler();
            }, 100);
        }
        else {
            fld.filterKeys(event);
        }
    });
    valueFld.on('focus', function(fld) {
        if (fld.gridEditor) {
            var rec = fld.gridEditor.record;
            if (rec) {
                var extClazz = rec.get('class');
                var maxLen = rec.get('maxLen');
                if (maxLen && Ext.isNumber(maxLen)) {
                    fld.maxLength = parseInt(maxLen, 10);
                    if (fld.maxLength){
                        fld.el.dom.setAttribute('maxLength', fld.maxLength);
                    }
                }
                else {
                    fld.maxLength = 255;
                    fld.el.dom.setAttribute('maxLength', 255);
                }
                if (extClazz && extClazz !== 'string') {
                    if (extClazz === 'int') {
                        fld.maskRe = /[0-9]/;
                    }
                    else if (extClazz === 'boolean') {
                        fld.maskRe = /[0-1]/;
                    }
                }
                else {
                    fld.maskRe = null;
                }
            }
        }
    });
    valueFld.on('blur', function(fld) {
        fld.maxLength = 255;
        fld.el.dom.setAttribute('maxLength', 255);
    });    

    var grid = new Ext.grid.EditorGridPanel({
        store: store,
        width: 780,
        height: 600,
        title: 'Manage Site Settings <span id="savedMarker">Saved</span>',
        loadMask: true,
        frame: true,
        renderTo: 'managerGrid',
        view: new Ext.grid.GroupingView({
            forceFit: true,
            groupTextTpl: '{text}',
            enableGrouping: true,
            enableNoGroups: false,
            enableGroupingMenu: false
        }),
        clicksToEdit: 1,
        columns: [
            {   header: 'Name',
                width: 255,
                sortable: true,
                dataIndex: 'label',
                align: 'left',
                editable: false,
                editor: new Ext.form.DisplayField()
            },
            {   header: 'Type',
                width: 200,
                sortable: true,
                dataIndex: 'group',
                align: 'left',
                editable: false,
                hidden: true,
                editor: new Ext.form.DisplayField()
            },
            {   header: 'Value',
                width: 255,
                sortable: true,
                dataIndex: 'value',
                align: 'left',
                editable: true,
                editor: valueFld,
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    var str = value;
                    if (record.id == 'smtpPassword' || record.id == 'jasperPassword') {
                        if (value) {
                            str = '*****';
                        }
                    }
                    return escapeScriptTag(str);
                }
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
        buttonAlign: 'center',
        tbar: [
            { text: 'Site Options', tooltip:'Manage Site Options', iconCls: 'customize', ref: '../customizeButton',
              disabled: false, handler: function(button, event) {
                        manageSiteOptions();
                    }
            }
        ]
    });

    grid.on('click', function(event) {
        $("#savedMarker").css('visibility', 'hidden');
    });

    /* Manage Site Options below */
    var optionsProxy = new Ext.data.HttpProxy({
        api: {
            read: {url: 'manageSiteOptions.json', method: 'GET'},
            load: {url: 'manageSiteOptions.json', method: 'GET'},
            create: {url: 'manageSiteOptions.json', method: 'POST'},
            update: {url: 'manageSiteOptions.json', method: 'POST'},
            destroy: {url: 'manageSiteOptions.json', method: 'POST'}
        }
    });
    var optionsSaveError = function() {
        var thisGrid = Ext.get('optionsWin');
        thisGrid.unmask();
        Ext.MessageBox.show({ title: 'Error', icon: Ext.MessageBox.ERROR,
            buttons: Ext.MessageBox.OK,
            msg: 'The custom field changes could not be saved.  Please try again or contact your administrator if this issue continues.'});
    }

    optionsProxy.on('write', function(proxy, action, data, response, records, options) {
        if (response.success === 'true') {
            optionsStore.suspendEvents();
            optionsStore.removeAll();

            var dataLength = data.length;
            var updatedRecords = [];
            for (var y = 0; y < dataLength; y++) {
                updatedRecords[updatedRecords.length] = new optionsStore.recordType(data[y], data[y].id);
            }
            optionsStore.add(updatedRecords);
            optionsGrid.getView().refresh();
            optionsStore.resumeEvents();
            var thisGrid = Ext.get('optionsWin');
            thisGrid.unmask();
            $("#optionsFieldsSavedMarker").show();
            setTimeout(function() {
                $("#optionsFieldsSavedMarker").hide();
            }, 10000);
        }
        else {
            optionsSaveError();
        }
    });
    optionsProxy.on('exception', function(proxy, type, action, options, response, args) {
        optionsSaveError();
    });

    var optionsWriter = new Ext.data.JsonWriter({ listful: true, writeAllFields: true });

    var optionsStore = new OrangeLeap.BulkSaveStore({
        batch: true,
        proxy: optionsProxy,
        writer: optionsWriter,
        autoSave: false,
        remoteSort: false,
        totalProperty: 'totalRows',
        root: 'rows',
        fields:[
            {name: 'id', type: 'string'},
            {name: 'name', type: 'string'},
            {name: 'nameRO', type: 'boolean'},
            {name: 'desc', type: 'string'},
            {name: 'value', type: 'string'},
            {name: 'valRO', type: 'boolean'}
        ]
    });
    optionsStore.on('beforewrite', function(proxy, action, rs, options, args) {
        var thisGrid = Ext.get('optionsWin');
        thisGrid.mask("Saving...");
    });

    function checkUnique(val) {
        var isUnique = true;
        var valCount = 0;
        if (val && optionsStore.data && optionsStore.data.items) {
            var len = optionsStore.data.items.length;
            for (var x = 0; x < len; x++) {
                if (valCount < 2) {
                    var thisItem = optionsStore.data.items[x];
                    if (thisItem.get('name') == val){
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

    function trapKeyDown(fld, event) {
        if (event.getKey() == event.ENTER) {
            setTimeout(function() {
                optionsWin.saveButton.handler();
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
        }
        return val;
    }

    function checkIfValid() {
        var isValid = true;
        var valCtMap = {};
        if (optionsStore.data && optionsStore.data.items) {
            var len = optionsStore.data.items.length;
            for (var x = 0; x < len; x++) {
                var thisVal = optionsStore.data.items[x].get('name');
                if (Ext.isEmpty(thisVal)) {
                    isValid = false;
                    break;
                }
                else {
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

    var optionNameFld = new Ext.form.TextField({
        allowBlank: false,
        maxLength: 255,
        enableKeyEvents: true,
        validator: function(val) {
            var results = true;
            if (Ext.isEmpty(val)) {
                results = "This field is required";
            }
            if ( ! checkUnique(val)) {
                results = "The custom field name " + val + " is not unique.";
            }
            return results;
        }
    });
    optionNameFld.on('keydown', function(fld, event) {
        trapKeyDown(fld, event);
    });

    var optionDescFld = new Ext.form.TextField({
        allowBlank: true,
        maxLength: 255,
        enableKeyEvents: true
    });
    optionDescFld.on('keydown', function(fld, event) {
        trapKeyDown(fld, event);
    });

    var optionValueFld = new Ext.form.TextField({
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
    optionValueFld.on('keydown', function(fld, event) {
        trapKeyDown(fld, event);
    });

    var optionsGrid = new Ext.grid.EditorGridPanel({
        store: optionsStore,
        header: false,
        frame: false,
        border: false,
        id: 'optionsGrid',
        viewConfig: { forceFit: true },
        clicksToEdit: 1,
        columns: [
            {   header: 'Name',
                width: 170,
                sortable: true,
                dataIndex: 'name',
                align: 'left',
                editable: true,
                editor: optionNameFld,
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    if (record) {
                        if (Ext.isEmpty(value)) {
                            metaData.css += ' x-form-invalid';
                            metaData.attr = 'ext:qtip="A name is required"; ext:qclass="x-form-invalid-tip"';
                        }
                        else if ( ! checkUnique(value)) {
                            metaData.css += ' x-form-invalid';
                            metaData.attr = 'ext:qtip="The name ' + value + ' is not unique."; ext:qclass="x-form-invalid-tip"';
                        }
                        else {
                            metaData.css = '';
                            metaData.attr = 'ext:qtip=""';
                        }
                    }
                    return escapeScriptTag(value);
                }
            },
            {   header: 'Description',
                width: 170,
                sortable: true,
                dataIndex: 'desc',
                align: 'left',
                editable: true,
                editor: optionDescFld,
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    return escapeScriptTag(value);
                }
            },
            {   header: 'Value',
                width: 170,
                sortable: true,
                dataIndex: 'value',
                align: 'left',
                editable: true,
                editor: optionValueFld,
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    return escapeScriptTag(value);
                }
            },
            {   header: ' ', width: 25, menuDisabled: true, fixed: true,
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    return '<a href="javascript:void(0)" class="deleteLink" id="delete-link-' + record.id + '" title="Remove Site Option">Remove</a>';
                }
            }
        ],
        tbar: [
            { text: 'Add New', tooltip:'Add a new Site Option', iconCls:'add', id: 'addFldButton', ref: '../addFldButton',
              handler: function() {
                    var cgStore = optionsGrid.getStore();
                    var CustFld = cgStore.recordType;
                    var field = new CustFld({
                        name: '',
                        desc: '',
                        value: '',
                        nameRO: false,
                        valRO: false
                    });
                    optionsGrid.stopEditing();
                    var nextIndex = cgStore.getCount();
                    cgStore.add(field);
                    optionsGrid.startEditing(nextIndex, 0);
                }
            }
        ]
    });
    optionsGrid.on('keydown', function(e) {
        if (e.getKey() == e.A && e.altKey) {
            Ext.getCmp('addFldButton').handler();
        }
    });
    optionsGrid.on('click', function(event) {
        $("#optionsFieldsSavedMarker").hide();
        var target = event.getTarget('a.deleteLink');
        if (target) {
            event.stopPropagation();
            var id = target.id;
            if (id) {
                id = id.replace('delete-link-', '');
                var rec = optionsStore.getById(id);
                optionsStore.remove(rec);
            }
        }
    });
    optionsGrid.on('beforeedit', function(obj) {
        if ((obj.field == 'name' && obj.record.get('nameRO')) ||
                (obj.field == 'value' && obj.record.get('valRO'))) {
            obj.cancel = true;
        }
    });

    var optionsWin = new Ext.Window({
        title: 'Manage Site Options <span id="optionsFieldsSavedMarker" class="savedIcon">Saved</span>',
        layout: 'fit',
        width: 600,
        height: 500,
        cls: 'win',
        id: 'optionsWin',
        buttons: [
            {   text: 'Save',
                cls: 'saveButton',
                ref: '../saveButton',
                handler: function(button, event) {
                    if (checkIfValid()) {
                        $("#optionsFieldsSavedMarker").hide();
                        optionsStore.saveAll();
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
                    optionsWin.hide(this);
                }
            }
        ],
        buttonAlign: 'center',
        modal: true,
        closable: false,
        closeAction: 'hide'
    });

    optionsWin.add(optionsGrid);
    var hideOnEscape = function(e, win) {
        if (e.keyCode == 27) {
            win.hide();
        }
    }
    optionsWin.on('beforeshow', function(win) {
        $(window).bind('keydown', function(e) {
            hideOnEscape(e, win);
        });
    });
    optionsWin.on('beforehide', function(win) {
        $(window).unbind('keydown', hideOnEscape);
    });

    var manageSiteOptions = function() {
        optionsStore.load({ sortInfo: { field: 'name', direction: 'ASC'},
            callback: function(records, options, success) {
                if (success) {
                    optionsWin.show();
                }
                else {
                    Ext.MessageBox.show({ title: 'Error', icon: Ext.MessageBox.ERROR,
                        buttons: Ext.MessageBox.OK,
                        msg: 'Could not load the Site Options window.  Please try again or contact your administrator if this issue continues.'});
                }
            }
        });
    }
});