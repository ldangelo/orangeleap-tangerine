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
    step1Title: '<span class="step"><span class="stepNum">1</span><span class="stepTxt">Choose Batch Type</span>',
    step2Title: '<span class="step"><span class="stepNum">2</span><span class="stepTxt">Choose Segmentations</span>',
    step3Title: '<span class="step"><span class="stepNum">3</span><span class="stepTxt">Choose Rows from Segmentations</span>',
    step4Title: '<span class="step"><span class="stepNum">4</span><span class="stepTxt">Update Field Values</span>',
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

    var descText = new Ext.form.TextArea({
        maxLength: 255
    });

    var typeCombo = new Ext.form.ComboBox({
       store: new Ext.data.ArrayStore({
            fields: [
                'value',
                'desc'
            ],
            data: [['gift', msgs.gift], ['adjustedGift', msgs.adjustedGift]]
        }),
        displayField: 'desc',
        valueField: 'value',
        typeAhead: false,
        mode: 'local',
        forceSelection: true,
        triggerAction: 'all',
        emptyText: ' ',
        selectOnFocus: true,
        minListWidth: 100,
        stateId: 'typeCombo'
    });

    /* Following is for the edit/add batch modal */
    var step1Form = new Ext.form.FormPanel({
        baseCls: 'x-plain',
        labelAlign: 'right',
        margins: '10 0',
        layoutConfig: {
            labelSeparator: '' 
        },
        buttons: [
            {
                text: msgs.next,
                cls: 'button',
                ref: '../nextButton',
                handler: function(button, event) {
//                    batchWin.hide(this);
                }
            }
        ],
        buttonAlign: 'center',
        items: [
            {
                fieldLabel: msgs.description, name: 'batchDesc', id: 'batchDesc', xtype: 'textarea',
                maxLength: 255, height: 60, width: 500, grow: true, growMin: 60, growMax: 400
            },
            {
                fieldLabel: msgs.type, name: 'batchType', id: 'batchType', xtype: 'combo',
                store: new Ext.data.ArrayStore({
                    fields: [
                        'value',
                        'desc'
                    ],
                    data: [['gift', msgs.gift], ['adjustedGift', msgs.adjustedGift]]
                }),
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
                stateId: 'typeCombo'
            }
        ]
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
        buttons: [
            {   text: msgs.save,
                cls: 'saveButton',
                ref: '../saveButton',
                disabled: true,
                handler: function(button, event) {
//                    if (checkIfValid()) {
//                        $("#optionsFieldsSavedMarker").hide();
//                        optionsStore.saveAll();
//                    }
//                    else {
//                        Ext.MessageBox.show({ title: 'Correct Errors', icon: Ext.MessageBox.WARNING,
//                            buttons: Ext.MessageBox.OK,
//                            msg: 'You must fix the highlighted errors first before saving.'});
//                    }
                }
            },
            {   text: msgs.close,
                cls: 'button',
                ref: '../closeButton',
                handler: function(button, event) {
                    batchWin.hide(this);
                }
            }
        ],
        buttonAlign: 'center',

        items:[{
             xtype: 'grouptabpanel',
             tabWidth: 135,
             activeGroup: 0,
             items: [
                 {
                     items: [{
                         title: msgs.step1Title,
                         tabTip: msgs.step1Tip,
                         style: 'padding: 20px 40px;',
                         items: [ step1Form ]
                     }]
                 },
                 {
                     items: [{
                         title: msgs.step2Title,
                         tabTip: msgs.step2Tip,
                         html: '<div>23</div>'
                     }]
                 },
                 {
                     items: [{
                         title: msgs.step3Title,
                         tabTip: msgs.step3Tip,
                         html: '<div>30</div>'
                     }]
                 },
                 {
                     items: [{
                         title: msgs.step4Title,
                         tabTip: msgs.step4Tip,
                         html: '<div>40</div>'
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
