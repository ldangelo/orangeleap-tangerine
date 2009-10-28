OrangeLeap.ListStore = Ext.extend(Ext.data.JsonStore, {
    sort: function(fieldName, dir) {
        this.lastOptions.params['start'] = 0;
        this.lastOptions.params['limit'] = 100;
        return OrangeLeap.ListStore.superclass.sort.call(this, fieldName, dir);
    }
});
Ext.onReady(function() {
    Ext.QuickTips.init();

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
        var executeDtCol = 4;
        var createDtCol = 5;
        var loginIdCol = 6;
        if (combo.getValue() == 'true') {
            // for executed batches, hide createDt column
            grid.colModel.setHidden(createDtCol, true);
            grid.colModel.setHidden(executeDtCol, false);
            grid.colModel.setHidden(loginIdCol, false);
        }
        else {
           // for not executed batches, hide executeDt & loginId column
            grid.colModel.setHidden(executeDtCol, true);
            grid.colModel.setHidden(loginIdCol, true);
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
        displayMsg: 'Displaying {0} - {1} of {2}',
        emptyMsg: 'No rows to display'
    });

    var combo = new Ext.form.ComboBox({
       store: new Ext.data.ArrayStore({
            fields: [
                'showRanBatches',
                'desc'
            ],
            data: [['false', 'Show Current Batches'], ['true', 'Show Executed Batches']] 
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
            { header: 'Batch Id', dataIndex: 'id', sortable: true },
            { header: 'Type', dataIndex: 'entity', sortable: true },
            { header: 'Size', dataIndex: 'reviewSetSize', sortable: true },
            { header: 'Description', dataIndex: 'postBatchDesc', sortable: true,
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    return '<span ext:qtitle="Description" ext:qwidth="250" ext:qtip="' + value + '">' + value + '</span>';
                }
            },
            { header: 'Execute Date', dataIndex: 'batchUpdatedDate', sortable: true, id: 'executeDt' },
            { header: 'Creation Date', dataIndex: 'createDate', sortable: true, id: 'createDt' },
            { header: 'User ID', dataIndex: 'loginId', sortable: true },
            { header: ' ', width: 25, menuDisabled: true, fixed: true,
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    return '<a href="javascript:void(0)" class="deleteLink" id="delete-link-' + record.id + '" title="Remove Batch">Remove</a>';
                }
            }
        ],
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        viewConfig: { forceFit: true },
        height: 600,
        width: 780,
        frame: true,
        header: true,
        title: 'Batch List',
        loadMask: true,
        listeners: {
            rowdblclick: function(grid, row, evt) {
                var rec = grid.getSelectionModel().getSelected();
                // TODO: modal
//                Ext.get(document.body).mask('Loading Record');
//                window.location.href = "constituent.htm?constituentId=" + rec.data.id;
            }
        },
        tbar: [
            combo, ' ', '-', ' ', 
            {
                text: 'Add New', tooltip:'Add a new Batch', iconCls:'add', id: 'addButton', ref: '../addButton',
                handler: function() {
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
});
