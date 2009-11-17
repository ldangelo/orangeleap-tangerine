Ext.ns('OrangeLeap', 'OrangeLeap.msgBundle');
OrangeLeap.ListStore = Ext.extend(Ext.data.JsonStore, {
    sort: function(fieldName, dir) {
        this.lastOptions.params['start'] = 0;
        this.lastOptions.params['limit'] = 100;
        return OrangeLeap.ListStore.superclass.sort.call(this, fieldName, dir);
    }
});

OrangeLeap.msgBundle = {
    displayMsg: 'Displaying {0} - {1} of {2}',
    emptyMsg: 'No rows to display',
    addNew: 'Add New',
    next: 'Next',
    previous: 'Previous',
    save: 'Save',
    close: 'Close',
    gift: 'Gift',
    name: 'Name',
    adjustedGift: 'Adjusted Gift',
    showCurrentBatches: 'Show Current Batches',
    showExecutedBatches: 'Show Executed Batches',
    batchId: 'Batch ID',
    type: 'Type',
    size: 'Size',
    description: 'Description',
    executeDate: 'Execute Date',
    creationDate: 'Creation Date',
    userId: 'User ID',
    executeBatch: 'Execute Batch',
    removeBatch: 'Remove Batch',
    batchList: 'Batch List',
    addNewBatch: 'Add a new Batch',
    manageBatch: 'Manage Batch',
    chooseSegmentations: 'Choose Segmentations',
    count: 'Count',
    lastExecDt: 'Last Execution Date',
    lastExecBy: 'Last Executed By',
    checkSegmentations: '',
    followingBeModified: 'For your reference, the following rows will be modified. Click \'Next\' to continue or \'Prev\' to change segmentations',
    step1Title: '<span class="step"><span class="stepNum" id="step1Num">1</span><span class="stepTxt">Choose Batch Type</span>',
    step2Title: '<span class="step"><span class="stepNum" id="step2Num">2</span><span class="stepTxt">Choose Segmentations</span>',
    step3Title: '<span class="step"><span class="stepNum" id="step3Num">3</span><span class="stepTxt">Confirm Choices</span>',
    step4Title: '<span class="step"><span class="stepNum" id="step4Num">4</span><span class="stepTxt">Update Field Values</span>',
    step1Tip: 'Step 1',
    step2Tip: 'Step 2',
    step3Tip: 'Step 3',
    step4Tip: 'Step 4'
}

Ext.onReady(function() {
    Ext.QuickTips.init();

    var msgs = OrangeLeap.msgBundle;

    var store = new OrangeLeap.ListStore({
        url: 'batchList.json',
        totalProperty: 'totalRows',
        root: 'rows',
        remoteSort: true,
        sortInfo: {field: 'createDate', direction: 'DESC'},
        fields: [
            {name: 'id', mapping: 'id', type: 'int'},
            {name: 'entity', mapping: 'entity', type: 'string'},
            {name: 'reviewSetSize', mapping: 'reviewSetSize', type: 'int'},
            {name: 'postBatchDesc', mapping: 'postBatchDesc', type: 'string'},
            {name: 'batchUpdated', mapping: 'batchUpdated', type: 'boolean'},
            {name: 'batchUpdatedDate', mapping: 'batchUpdatedDate', type: 'date', dateFormat: 'Y-m-d H:i:s'},
            {name: 'createDate', mapping: 'createDate', type: 'date', dateFormat: 'Y-m-d H:i:s'},
            {name: 'loginId', mapping: 'loginId', type: 'string'}
        ]
    });
    store.on('load', function(store, recs, options) {
        var batchIdCol = 0;
        var executeDtCol = 4;
        var createDtCol = 5;
        var loginIdCol = 6;
        var actionsCol = 7;
        if (combo.getValue() == 'true') {
            // for executed batches, hide batchId, actions & createDt column
            grid.colModel.setHidden(batchIdCol, true);
            grid.colModel.setHidden(createDtCol, true);
            grid.colModel.setHidden(actionsCol, true);
            grid.colModel.setHidden(executeDtCol, false);
            grid.colModel.setHidden(loginIdCol, false);
        }
        else {
           // for not executed batches, hide batchId, executeDt & loginId column
            grid.colModel.setHidden(batchIdCol, true);
            grid.colModel.setHidden(executeDtCol, true);
            grid.colModel.setHidden(loginIdCol, true);
            grid.colModel.setHidden(actionsCol, false);
            grid.colModel.setHidden(createDtCol, false);
        }
    });

    var bar = new Ext.PagingToolbar({
        pageSize: 100,
        stateEvents: ['change'],
        stateId: 'pageBar',
        stateful: true,
        getState: function() {
            var config = {};
            config.start = this.cursor;
            config.limit = this.pageSize;
            return config;
        },
        applyState: function(state, config) {
            if (state.start) {
                this.cursor = state.start;
            }
            if (state.limit) {
                this.pageSize = state.limit;
            }
        },
        store: store,
        displayInfo: true,
        displayMsg: msgs.displayMsg,
        emptyMsg: msgs.emptyMsg
    });

    var combo = new Ext.form.ComboBox({
       store: new Ext.data.ArrayStore({
            fields: [
                'showRanBatches',
                'desc'
            ],
            data: [['false', msgs.showCurrentBatches], ['true', msgs.showExecutedBatches]] 
        }),
        displayField: 'desc',
        valueField: 'showRanBatches',
        typeAhead: false,
        mode: 'local',
        forceSelection: true,
        triggerAction: 'all',
        emptyText: ' ',
        selectOnFocus: true,
        minListWidth: 250,
        stateId: 'currentBatchesCombo',
        stateful: true,
        stateEvents: ['select']
    });
    combo.on('select', function(comboBox, record, index) {
        var showRanBatchesVal = comboBox.getValue();
        var state = store.getSortState();
        if (state) {
            state.field = showRanBatchesVal == 'true' ? 'batchUpdatedDate' : 'createDate';
            state.direction = 'DESC';
        }
        store.load( { params: { showRanBatches: showRanBatchesVal, start: 0, limit: 100, sort: state.field, dir: state.direction } });
    });

    var grid = new Ext.grid.GridPanel({
        stateId: 'batchList',
        stateEvents: ['columnmove', 'columnresize', 'sortchange', 'bodyscroll'],
        stateful: true,
        getState: function() {
            var config = {};
            var cm = this.getColumnModel();
            var sortState = this.store.getSortState();
            if (sortState) {
                config.sf = sortState['field'];
                config.sd = sortState['direction'];
            }
            config.ss = this.getView().getScrollState();
            config.mc = [];
            for (var i = 0; i < cm.config.length; i++) {
                config.mc[i] = {};
                config.mc[i].di = cm.config[i].dataIndex;
                config.mc[i].h = cm.config[i].hidden;
                config.mc[i].w = cm.config[i].width;
            }
            return config;
        },
        applyState: function(state, config) {
            if (state.mc != null) {
                var cm = this.getColumnModel();
                for (var i = 0; i < state.mc.length; i++) {
                    var colIndex = cm.findColumnIndex(state.mc[i].di);
                    if (colIndex != -1)
                    if (colIndex != i) {
                        cm.moveColumn(colIndex, i);
                    }
                    cm.setHidden(i, state.mc[i].h);
                    cm.setColumnWidth(i, state.mc[i].w);
                }
            }
            if (state.sf && state.sd) {
                this.sortParams = { direction: state.sd, dataIndex: state.sf };
            }
            if (state.ss) {
                this.getView().prevScrollState = state.ss;
            }
        },
        store: store,
        addClass: 'pointer',
        columns: [
            { header: msgs.batchId, dataIndex: 'id', width: 50, sortable: true, hidden: true },
            { header: msgs.type, dataIndex: 'entity', width: 150, sortable: true },
            { header: msgs.size, dataIndex: 'reviewSetSize', width: 100, sortable: true },
            { header: msgs.description, dataIndex: 'postBatchDesc', sortable: true,
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    return '<span ext:qtitle="' + msgs.description + '" ext:qwidth="250" ext:qtip="' + value + '">' + value + '</span>';
                }
            },
            { header: msgs.executeDate, dataIndex: 'batchUpdatedDate', sortable: true, id: 'executeDt' },
            { header: msgs.creationDate, dataIndex: 'createDate', sortable: true, id: 'createDt' },
            { header: msgs.userId, dataIndex: 'loginId', sortable: true },
            { header: ' ', width: 50, menuDisabled: true, fixed: false, css: 'cursor:default;',
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    if ( ! record.get('batchUpdated')) {
                        var html = '<a href="javascript:void(0)" class="executeLink" id="execute-link-' + record.id + '" title="' + msgs.executeBatch + '">' + msgs.executeBatch + '</a>&nbsp;';
                        html += '<a href="javascript:void(0)" class="deleteLink" id="delete-link-' + record.id + '" title="' + msgs.removeBatch + '">' + msgs.removeBatch + '</a>';
                        return html;
                    }
                }
            }
        ],
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        viewConfig: { forceFit: true },
        height: 600,
        width: 780,
        frame: true,
        header: true,
        title: msgs.batchList,
        loadMask: true,
        listeners: {
            rowdblclick: function(grid, row, evt) {
                var rec = grid.getSelectionModel().getSelected();
                batchWin.show();
                // TODO: modal
//                Ext.get(document.body).mask('Loading Record');
//                window.location.href = "constituent.htm?constituentId=" + rec.data.id;
            }
        },
        tbar: [
            combo, ' ', '-', ' ', 
            {
                text: msgs.addNew, tooltip: msgs.addNewBatch, iconCls:'add', id: 'addButton', ref: '../addButton',
                handler: function() {
                    batchWin.show();
                }
            }
        ],
        bbar: bar,
        renderTo: 'managerGrid'
    });

    var sortDir = 'DESC';
    var sortProp = 'createDate';
    var pageStart = 0;
    var pageLimit = 100;
    if (grid.sortParams) {
        if (grid.sortParams.direction) {
            sortDir = grid.sortParams.direction;
        }
        if (grid.sortParams.dataIndex) {
            sortProp = grid.sortParams.dataIndex;
        }
    }
    if (bar.cursor) {
        pageStart = bar.cursor;
    }
    if (bar.pageSize) {
        pageLimit = bar.pageSize;
    }
    
    store.sortToggle[sortProp] = sortDir;
    store.sortInfo = { field: sortProp, direction: sortDir };
    store.load( { params: { showRanBatches: 'false', start: pageStart, limit: pageLimit, sort: sortProp, dir: sortDir },
        callback: function(rec, options, success) {
            combo.setValue('false');
            var thisView = grid.getView();
            if (thisView.prevScrollState) {
                thisView.restoreScroll(thisView.prevScrollState);
            }
        }
    });

    /* Following is for the edit/add batch modal */
    function elementFocus(fld) {
        $('#' + fld.getId()).parents('div.x-form-element').prev('label').addClass('inFocus');
    }

    function elementBlur(fld) {
        $('#' + fld.getId()).parents('div.x-form-element').prev('label').removeClass('inFocus');
    }

    function initFocus(groups, thisGrp) {
        if (thisGrp.mainItem.id == 'step1Grp') {
            setTimeout(function() {
                var elem = Ext.getCmp('batchDesc');
                if (elem && elem.el) {
                    elem.el.focus();
                }
            }, 900);
        }
        else if (thisGrp.mainItem.id == 'step2Grp') {
            var batchType = Ext.getCmp('batchType').getValue();
            step2Store.load({ params: { batchType: batchType, start: 0, limit: 100, sort: 'lastDt', dir: 'DESC' }});
        }
        else if (thisGrp.mainItem.id == 'step3Grp') {
            var selIds = [];
            if (step2Store.data && step2Store.data.items) {
                var items = step2Store.data.items;
                var len = items.length;
                for (var x = 0; x < len; x++) {
                    var thisItem = items[x];
                    if (thisItem.get('picked')) {
                        selIds[selIds.length] = thisItem.id;
                    }
                }
            }
            var batchType = Ext.getCmp('batchType').getValue();
            step3Store.load({ params: { 'ids': selIds.toString(), 'batchType': batchType }});
        }
        else if (thisGrp.mainItem.id == 'step4Grp') {
        }
    }

    var step1Form = new Ext.form.FormPanel({
        baseCls: 'x-plain',
        labelAlign: 'right',
        margins: '10 0',
        formId: 'step1Form',
        ctCls: 'wizard',
        monitorValid: true,
        layoutConfig: {
            labelSeparator: '' 
        },
        buttons: [
            {
                text: msgs.next,
                cls: 'saveButton',
                ref: '../nextButton',
                formBind: true,
                disabledClass: 'disabledButton',
                handler: function(button, event) {
                    var panel = batchWin.groupTabPanel;
                    panel.setActiveGroup(1);
                    var firstItem = panel.items.items[1];
                    firstItem.setActiveTab(firstItem.items.items[0]);
                    $('#step1Num').addClass('complete');
                }
            },
            {
                text: msgs.close,
                cls: 'button',
                ref: '../closeButton',
                handler: function(button, event) {
                    batchWin.hide(this);
                }
            }
        ],
        buttonAlign: 'center',
        items: [
            // Textarea for description is first
            {
                fieldLabel: msgs.description, name: 'batchDesc', id: 'batchDesc', xtype: 'textarea',
                maxLength: 255, height: 60, width: 500, grow: true, growMin: 60, growMax: 400,
                listeners: {
                    'focus': elementFocus,
                    'blur': elementBlur,
                    scope: this
                }
            },
            // Combobox for type is second
            {
                fieldLabel: '<span class="required">*</span>' + msgs.type, name: 'batchType', id: 'batchType', xtype: 'combo',
                store: new Ext.data.ArrayStore({
                    fields: [
                        'value',
                        'desc'
                    ],
                    data: [['gift', msgs.gift] ]//, ['adjustedGift', msgs.adjustedGift]] TODO: put back adjustedGift when adjustedGift segmentations ready
                }),
                value: 'gift',
                displayField: 'desc',
                valueField: 'value',
                typeAhead: false,
                mode: 'local',
                forceSelection: true,
                triggerAction: 'all',
                emptyText: ' ',
                selectOnFocus: true,
                minListWidth: 500,
                width: 500,
                listeners: {
                    'focus': elementFocus,
                    'blur': elementBlur,
                    scope: this
                }
            }
        ]
    });
    
    var step2Store = new OrangeLeap.ListStore({
        url: 'findSegmentations.json',
        totalProperty: 'totalRows',
        root: 'rows',
        remoteSort: true,
        sortInfo: {field: 'name', direction: 'ASC'},
        fields: [
            {name: 'id', mapping: 'id', type: 'int'},
            {name: 'name', mapping: 'name', type: 'string'},
            {name: 'desc', mapping: 'desc', type: 'string'},
            {name: 'count', mapping: 'count', type: 'int'},
            {name: 'lastDt', mapping: 'lastDt', type: 'date', dateFormat: 'Y-m-d H:i:s'},
            {name: 'lastUser', mapping: 'lastUser', type: 'string'}
        ]
    });

    var step2Bar = new Ext.PagingToolbar({
        pageSize: 50,
        stateEvents: ['change'],
        stateId: 'step2Bar',
        stateful: true,
        getState: function() {
            var config = {};
            config.start = this.cursor;
            config.limit = this.pageSize;
            return config;
        },
        applyState: function(state, config) {
            if (state.start) {
                this.cursor = state.start;
            }
            if (state.limit) {
                this.pageSize = state.limit;
            }
        },
        store: step2Store,
        displayInfo: true,
        displayMsg: msgs.displayMsg,
        emptyMsg: msgs.emptyMsg
    });

    var checkColumn = new Ext.grid.CheckColumn({
        header: 'Choose?',
        dataIndex: 'picked',
        width: 55
    });
    checkColumn.on('click', function(el, event, htmlEl, record) {
        if (htmlEl) {
            var index = step2Form.getView().findRowIndex(htmlEl);
            if (record.get('picked')) {
                step2Form.getSelectionModel().selectRow(index, true);
                pickedRows++;
                if (pickedRows > 0) {
                    step2Form.nextButton.enable();
                }
            }
            else {
                step2Form.getSelectionModel().deselectRow(index);
                pickedRows--;
                if (pickedRows == 0) {
                    step2Form.nextButton.disable();
                }
            }
        }
    });

    var pickedRows = 0;

    var step2RowSelModel = new Ext.grid.RowSelectionModel({singleSelect: false});

    step2RowSelModel.on({
        'rowselect': function(selModel, rowIndex, record) {
            record.set('picked', true);
            pickedRows++;
            if (pickedRows > 0) {
                step2Form.nextButton.enable();
            }
        },
        'rowdeselect': function(selModel, rowIndex, record) {
            record.set('picked', false);
            pickedRows--;
            if (pickedRows == 0) {
                step2Form.nextButton.disable();
            }
        }
    });

    var step2Form = new Ext.grid.EditorGridPanel({
        stateId: 'step2List',
        stateEvents: ['columnmove', 'columnresize', 'sortchange', 'bodyscroll'],
        stateful: true,
        store: step2Store,
        bbar: step2Bar,
        width: 726,
        height: 468,
        loadMask: true,
        header: false,
        frame: false,
        border: false,
        selModel: step2RowSelModel,
        viewConfig: { forceFit: true },
        plugins: [ checkColumn ],
        buttons: [
            {
                text: msgs.previous,
                cls: 'button',
                ref: '../prevButton',
                formBind: true,
                disabledClass: 'disabledButton',
                handler: function(button, event) {
                    var panel = batchWin.groupTabPanel;
                    panel.setActiveGroup(1);
                    var firstItem = panel.items.items[0];
                    firstItem.setActiveTab(firstItem.items.items[0]);
                }
            },
            {
                text: msgs.next,
                cls: 'saveButton',
                ref: '../nextButton',
                formBind: true,
                disabled: true,
                disabledClass: 'disabledButton',
                handler: function(button, event) {
                    var panel = batchWin.groupTabPanel;
                    panel.setActiveGroup(2);
                    var firstItem = panel.items.items[2];
                    firstItem.setActiveTab(firstItem.items.items[0]);
                    $('#step2Num').addClass('complete');
                }
            },
            {
                text: msgs.close,
                cls: 'button',
                ref: '../closeButton',
                handler: function(button, event) {
                    batchWin.hide(this);
                }
            }
        ],
        buttonAlign: 'center',
        columns: [
            checkColumn,
            {
                header: msgs.name,
                sortable: true,
                dataIndex: 'name',
                editable: false,
                editor: new Ext.form.DisplayField(),
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    return '<span ext:qtitle="' + msgs.name + '" ext:qwidth="250" ext:qtip="' + value + '">' + value + '</span>';
                }
            },
            {
                header: msgs.description,
                sortable: true,
                dataIndex: 'desc',
                editable: false,
                editor: new Ext.form.DisplayField(),
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    return '<span ext:qtitle="' + msgs.description + '" ext:qwidth="250" ext:qtip="' + value + '">' + value + '</span>';
                }                
            },
            {
                header: msgs.count,
                sortable: true,
                dataIndex: 'count',
                editable: false,
                editor: new Ext.form.DisplayField(),
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    return '<span ext:qtitle="' + msgs.count + '" ext:qwidth="250" ext:qtip="' + value + '">' + value + '</span>';
                }
            },
            {
                header: msgs.lastExecDt,
                sortable: true,
                dataIndex: 'lastDt',
                editable: false,
                editor: new Ext.form.DisplayField(),
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    return '<span ext:qtitle="' + msgs.lastExecDt + '" ext:qwidth="250" ext:qtip="' + value + '">' + value + '</span>';
                }
            },
            {
                header: msgs.lastExecBy,
                sortable: true,
                dataIndex: 'lastUser',
                editable: false,
                editor: new Ext.form.DisplayField(),
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    return '<span ext:qtitle="' + msgs.lastExecBy + '" ext:qwidth="250" ext:qtip="' + value + '">' + value + '</span>';
                }
            },
        ],
        getState: function() {
            var config = {};
            var cm = this.getColumnModel();
            var sortState = this.store.getSortState();
            if (sortState) {
                config.sf = sortState['field'];
                config.sd = sortState['direction'];
            }
            config.ss = this.getView().getScrollState();
            config.mc = [];
            for (var i = 0; i < cm.config.length; i++) {
                config.mc[i] = {};
                config.mc[i].di = cm.config[i].dataIndex;
                config.mc[i].h = cm.config[i].hidden;
                config.mc[i].w = cm.config[i].width;
            }
            return config;
        },
        applyState: function(state, config) {
            if (state.mc != null) {
                var cm = this.getColumnModel();
                for (var i = 0; i < state.mc.length; i++) {
                    var colIndex = cm.findColumnIndex(state.mc[i].di);
                    if (colIndex != -1)
                    if (colIndex != i) {
                        cm.moveColumn(colIndex, i);
                    }
                    cm.setHidden(i, state.mc[i].h);
                    cm.setColumnWidth(i, state.mc[i].w);
                }
            }
            if (state.sf && state.sd) {
                this.sortParams = { direction: state.sd, dataIndex: state.sf };
            }
            if (state.ss) {
                this.getView().prevScrollState = state.ss;
            }
        }
    });

    var step3Reader = new Ext.data.JsonReader();

    var step3Store = new OrangeLeap.ListStore({
        url: 'confirmChoices.json',
        reader: step3Reader,
        root: 'rows',
        totalProperty: 'totalRows',
        remoteSort: true,
        sortInfo: {field: 'id', direction: 'ASC'},
        fields: [
            {name: 'id', mapping: 'id', type: 'int'}
        ]
    });

    step3Store.on('metachange', function(store, meta) {
        var cols = [];
        var fields = meta.fields;
        for (var x = 0; x < fields.length; x++) {
            var name = fields[x].name;
            if (name && name != 'constituentId' && name != 'id') {
                cols[cols.length] = {
                    header: fields[x].header, dataIndex: name, sortable: true,
                    renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                        return '<span ext:qwidth="250" ext:qtip="' + value + '">' + value + '</span>'; 
                    }
                }
            }
        }
        step3Form.reconfigure(store, new Ext.grid.ColumnModel(cols));
    });

    var step3Bar = new Ext.PagingToolbar({
        pageSize: 50,
        stateEvents: ['change'],
        stateId: 'step3Bar',
        stateful: true,
        getState: function() {
            var config = {};
            config.start = this.cursor;
            config.limit = this.pageSize;
            return config;
        },
        applyState: function(state, config) {
            if (state.start) {
                this.cursor = state.start;
            }
            if (state.limit) {
                this.pageSize = state.limit;
            }
        },
        store: step3Store,
        displayInfo: true,
        displayMsg: msgs.displayMsg,
        emptyMsg: msgs.emptyMsg
    });

    var step3RowSelect = new Ext.grid.RowSelectionModel();
    step3RowSelect.on('beforerowselect', function(selModel, rowIndex, keepExisting, record) {
        return false;
    });

    var step3Toolbar = new Ext.Toolbar({
        items: [
            msgs.followingBeModified
        ]
    });
    step3Toolbar.on('afterlayout', function(tb){
        tb.el.child('.x-toolbar-right').remove();
        var t = tb.el.child('.x-toolbar-left');
        t.removeClass('x-toolbar-left');
        t = tb.el.child('.x-toolbar-ct');
        t.setStyle('width', 'auto');
        t.wrap({tag: 'center'});
    }, null, {single: true});

    var step3Form = new Ext.grid.GridPanel({
        stateId: 'step3List',
        stateEvents: ['columnmove', 'columnresize', 'sortchange', 'bodyscroll'],
        stateful: true,
        store: step3Store,
        bbar: step3Bar,
        width: 726,
        height: 468,
        loadMask: true,
        header: false,
        frame: false,
        border: false,
        selModel: step3RowSelect,
        viewConfig: { forceFit: true },
        columns: [
            {
                header: msgs.id,
                sortable: false,
                dataIndex: 'id',
                editable: false,
                editor: new Ext.form.DisplayField()
            }
        ],
        buttons: [
            {
                text: msgs.previous,
                cls: 'button',
                ref: '../prevButton',
                formBind: true,
                disabledClass: 'disabledButton',
                handler: function(button, event) {
                    var panel = batchWin.groupTabPanel;
                    panel.setActiveGroup(1);
                    var firstItem = panel.items.items[1];
                    firstItem.setActiveTab(firstItem.items.items[0]);
                }
            },
            {
                text: msgs.next,
                cls: 'saveButton',
                ref: '../nextButton',
                formBind: true,
                disabledClass: 'disabledButton',
                handler: function(button, event) {
                    var panel = batchWin.groupTabPanel;
                    panel.setActiveGroup(3);
                    var firstItem = panel.items.items[3];
                    firstItem.setActiveTab(firstItem.items.items[0]);
                    $('#step3Num').addClass('complete');
                }
            },
            {
                text: msgs.close,
                cls: 'button',
                ref: '../closeButton',
                handler: function(button, event) {
                    batchWin.hide(this);
                }
            }
        ],
        buttonAlign: 'center',
        tbar: step3Toolbar
    });

    /*
    var step4Reader = new Ext.data.JsonReader();

    var step4Store = new OrangeLeap.BulkSaveStore({
        url: 'findBatchUpdateFields.json',
        reader: step4Reader,
        root: 'rows',
        totalProperty: 'totalRows',
        remoteSort: true,
        sortInfo: {field: 'id', direction: 'ASC'},
        fields: [
            {name: 'id', mapping: 'id', type: 'int'}
        ],
        listeners: {
            load: {
                fn: function(store, records, options){
                    store.each(function(record) {
                        source[record.get('key')] = record.get('value');
                    });
                    step4Grid.setSource(source);
                }
            }
        }
    });

    var step4Grid = new Ext.grid.PropertyGrid({
        width: 726,
        height: 468,
        loadMask: true,
        header: false,
        frame: false,
        border: false,
        viewConfig : { forceFit: true },
        propertyNames: {
//            tested: 'QA',
//            borderWidth: 'Border Width'
        },
        source: {
//            '(name)': 'Properties Grid',
//            grouping: false,
//            autoFitColumns: true,
//            productionQuality: false,
//            created: new Date(Date.parse('10/15/2006')),
//            tested: false,
//            version: 0.01,
//            borderWidth: 1
        },
        buttons: [
            {
                text: msgs.previous,
                cls: 'button',
                ref: '../prevButton',
                formBind: true,
                disabledClass: 'disabledButton',
                handler: function(button, event) {
                    var panel = batchWin.groupTabPanel;
                    panel.setActiveGroup(2);
                    var firstItem = panel.items.items[2];
                    firstItem.setActiveTab(firstItem.items.items[0]);
                }
            },
            {
                text: msgs.save,
                cls: 'saveButton',
                ref: '../saveButton',
                formBind: true,
                disabledClass: 'disabledButton',
                handler: function(button, event) {
                }
            },
            {
                text: msgs.close,
                cls: 'button',
                ref: '../closeButton',
                handler: function(button, event) {
                    batchWin.hide(this);
                }
            }
        ],
        buttonAlign: 'center'
    });
    */

    var step4Grid = new OrangeLeap.DynamicPropertyGrid({
        width: 726,
        height: 468,
        loadMask: true,
        header: false,
        frame: false,
        border: false,
        propertyNames: {
            postedDate: 'Posted Date (Creates Journal Entry)',
            source: 'Source',
            status: 'Status'
        },
        source: {
            'postedDate': new Date()
//            grouping: false,
//            autoFitColumns: true,
//            productionQuality: false,
//            created: new Date(Date.parse('10/15/2006')),
//            tested: false,
//            version: 0.01,
//            borderWidth: 1
        },
        viewConfig : {
            forceFit: true
        },
        customEditors: {
            'source': new Ext.grid.GridEditor(new Ext.form.ComboBox({
                name: 'source',
                allowBlank: false,
                store: new Ext.data.ArrayStore({
                     fields: [
                         'itemName',
                         'displayVal'
                     ],
                     data: [
                         ['Call', 'Call'],
                         ['Mail', 'Mail'],
                         ['Online', 'Online']
                     ]
                }),
                displayField: 'displayVal',
                valueField: 'itemName',
                value: 'Call',
                typeAhead: true,
                mode: 'local',
                triggerAction: 'all',
                selectOnFocus: true,
                forceSelection: true
            })),
            'status': new Ext.grid.GridEditor(new Ext.form.ComboBox({
                name: 'status',
                allowBlank: false,
                store: new Ext.data.ArrayStore({
                     fields: [
                         'itemName',
                         'displayVal'
                     ],
                     data: [
                         ['Pending', 'Pending'],
                         ['Paid', 'Paid'],
                         ['Not Paid', 'Not Paid']
                     ]
                }),
                displayField: 'displayVal',
                valueField: 'itemName',
                value: 'Pending',
                typeAhead: true,
                mode: 'local',
                triggerAction: 'all',
                selectOnFocus: true,
                forceSelection: true
            }))
        },
        tbar: [
            {
                text: 'Add Criteria',
                tooltip:'Add Criteria to Update Field Value',
                iconCls:'add',
                id: 'addButton',
                ref: '../addButton',
                handler: function() {
                    var fourStore = step4Grid.getStore();
                    var newRec = new fourStore.recordType({
                        name: '',
                        value: ''
                    });
                    step4Grid.stopEditing();
                    var nextIndex = fourStore.getCount();
                    fourStore.add(newRec);
                    step4Grid.startEditing(nextIndex, 0);
                }
            }
        ],
        buttons: [
            {
                text: msgs.previous,
                cls: 'button',
                ref: '../prevButton',
                formBind: true,
                disabledClass: 'disabledButton',
                handler: function(button, event) {
                    var panel = batchWin.groupTabPanel;
                    panel.setActiveGroup(2);
                    var firstItem = panel.items.items[2];
                    firstItem.setActiveTab(firstItem.items.items[0]);
                }
            },
            {
                text: msgs.save,
                cls: 'saveButton',
                ref: '../saveButton',
                formBind: true,
                disabled: true,
                disabledClass: 'disabledButton',
                handler: function(button, event) {
                }
            },
            {
                text: msgs.close,
                cls: 'button',
                ref: '../closeButton',
                handler: function(button, event) {
                    batchWin.hide(this);
                }
            }
        ],
        buttonAlign: 'center'
    });

    var batchWin = new Ext.Window({
        title: msgs.manageBatch,
        layout: 'fit',
        width: 875,
        height: 500,
        cls: 'win',
        id: 'batchWin',
        modal: true,
        closable: false,
        closeAction: 'hide',
//        buttons: [
//            {   text: msgs.save,
//                cls: 'disabledButton',
//                ref: '../saveButton',
//                disabled: true,
//                handler: function(button, event) {
////                    if (checkIfValid()) {
////                        $("#optionsFieldsSavedMarker").hide();
////                        optionsStore.saveAll();
////                    }
////                    else {
////                        Ext.MessageBox.show({ title: 'Correct Errors', icon: Ext.MessageBox.WARNING,
////                            buttons: Ext.MessageBox.OK,
////                            msg: 'You must fix the highlighted errors first before saving.'});
////                    }
//                }
//            },
//            {   text: msgs.close,
//                cls: 'button',
//                ref: '../closeButton',
//                handler: function(button, event) {
//                    batchWin.hide(this);
//                }
//            }
//        ],
//        buttonAlign: 'center',

        items:[{
             xtype: 'grouptabpanel',
             tabWidth: 135,
             activeGroup: 0,
             ref: '../groupTabPanel',
             listeners: {
                 'groupchange': initFocus
             },
             items: [
                 {
                     items: [{
                         id: 'step1Grp',
                         title: msgs.step1Title,
                         tabTip: msgs.step1Tip,
                         style: 'padding: 20px 40px;',
                         items: [ step1Form ]
                     }]
                 },
                 {
                     items: [{
                         id: 'step2Grp',
                         title: msgs.step2Title,
                         tabTip: msgs.step2Tip,
                         items: [ step2Form ]
                     }]
                 },
                 {
                     items: [{
                         id: 'step3Grp',
                         title: msgs.step3Title,
                         tabTip: msgs.step3Tip,
                         items: [ step3Form ]
                     }]
                 },
                 {
                     items: [{
                         id: 'step4Grp',
                         title: msgs.step4Title,
                         tabTip: msgs.step4Tip,
                         items: [ step4Grid ]
                     }]
                 }
             ]
         }]
    });

    var hideOnEscape = function(e) {
        if (e.keyCode == 27) {
            batchWin.hide();
        }
    }
    batchWin.on('beforeshow', function() {
        $(window).bind('keydown', function(e) {
            hideOnEscape(e);
        });
    });
    batchWin.on('beforehide', function() {
        $(window).unbind('keydown', hideOnEscape);
    });

    batchWin.show(); // TODO: remove
});


