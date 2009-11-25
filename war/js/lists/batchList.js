Ext.WindowMgr.zseed = 9015; // use zseed so that the msgBox will appear above the batchWin instead of underneath

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
    id: 'ID',
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
    error: 'Error',
    mustDoStep1: 'You must choose a Batch Type (Step 1) first.',
    mustDoStep2: 'You must pick Segmentations (Step 2) first.',
    mustDoStep3: 'There are no updatable rows based on the Segmentation you chose).  Choose a different segmentation (Step 2).',
    mustDoStep4: 'You must create Field Update Criteria (Step 4) first.',
    loading: 'Loading...',
    loadingSegmentations: 'Loading Segmentations...',
    loadingRows: 'Loading Rows...',
    followingBeModified: 'For your reference, the following rows will be modified. Click \'Next\' to continue or \'Prev\' to change segmentations',
    followingChangesApplied: 'The following changes will be applied when you click \'Save\'.',
    noSegmentationsFound: 'No Segmentations were found for Type \'{0}\'.  Please choose a different Type (Step 1).',
    noRowsFound: 'No {0} rows were found for the Segmentations selected.  Please choose a different Segmentation (Step 2).',
    noFieldUpdates: 'You did not create any Field Update Criteria.  Please create Criteria first (Step 4).',
    step1Title: '<span class="step"><span class="stepNum" id="step1Num">1</span><span class="stepTxt">Choose Batch Type</span>',
    step2Title: '<span class="step"><span class="stepNum" id="step2Num">2</span><span class="stepTxt">Choose Segmentations</span>',
    step3Title: '<span class="step"><span class="stepNum" id="step3Num">3</span><span class="stepTxt">View Rows That Will Be Updated</span>',
    step4Title: '<span class="step"><span class="stepNum" id="step4Num">4</span><span class="stepTxt">Create Field Update Criteria</span>',
    step5Title: '<span class="step"><span class="stepNum" id="step4Num">5</span><span class="stepTxt">Confirm Changes</span>',
    step1Tip: 'Step 1',
    step2Tip: 'Step 2',
    step3Tip: 'Step 3',
    step4Tip: 'Step 4',
    step5Tip: 'Step 5',
    errorStep1: 'Could not load Step 1 data.  Please Please try again or contact your administrator if this issue continues.',
    errorStep2: 'Could not load Step 2 data.  Please Please try again or contact your administrator if this issue continues.',
    errorStep3: 'Could not load Step 3 data.  Please Please try again or contact your administrator if this issue continues.',
    errorStep4: 'Could not load Step 4 data.  Please Please try again or contact your administrator if this issue continues.',
    errorStep5: 'Could not load Step 5 data.  Please Please try again or contact your administrator if this issue continues.'
};

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

    OrangeLeap.BatchToolbar = Ext.extend(Ext.PagingToolbar, { // custom toolbar for batch to invoke initFocus
        doLoad: function(start) {
            var o = { }, pn = this.getParams();
            o[pn.start] = start;
            o[pn.limit] = this.pageSize;
            if (this.fireEvent('beforechange', this, o) !== false) {
                initFocus(batchWin.groupTabPanel, batchWin.groupTabPanel.activeGroup, start);
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

    function checkFieldCriteriaValid() {
        var thisStore = step4Form.store;
        var isCriteriaValid = true;
        var recLength = thisStore.data.items.length;
        for (var x = 0; x < recLength; x++) {
            var rec = thisStore.data.items[x];
            if (Ext.isEmpty(rec.get('name')) || Ext.isEmpty(rec.get('value'))) {
                isCriteriaValid = false;
                break;
            }
        }
        return isCriteriaValid;
    }

    function checkConditions(groupTabPanel, groupToShow, activeGroup) {
        function checkBatchType() {
            var isValid = false;
            var batchType = getBatchTypeValue();
            if ( ! batchType || Ext.isEmpty(batchType)) {
                Ext.MessageBox.show.defer(1, Ext.MessageBox,
                    [{ title: msgs.error, icon: Ext.MessageBox.ERROR,
                    buttons: Ext.MessageBox.OK,
                    msg: msgs.mustDoStep1}]); // use defer so that the msgBox will appear above the batchWin
            }
            else {
                isValid = true;
            }
            return isValid;
        }

        function checkSegmentationsPicked() {
            var isValid = false;
            if ( ! hasPickedRows()) {
                Ext.MessageBox.show.defer(1, Ext.MessageBox,
                    [{ title: msgs.error, icon: Ext.MessageBox.ERROR,
                    buttons: Ext.MessageBox.OK,
                    msg: msgs.mustDoStep2}]); // use defer so that the msgBox will appear above the batchWin
            }
            else {
                isValid = true;
            }
            return isValid;
        }

        function checkRowsAvailableToUpdate() {
            var isValid = false;
            if (step3Store.getCount() == 0) {
                Ext.MessageBox.show.defer(1, Ext.MessageBox,
                    [{ title: msgs.error, icon: Ext.MessageBox.ERROR,
                    buttons: Ext.MessageBox.OK,
                    msg: msgs.mustDoStep3}]); // use defer so that the msgBox will appear above the batchWin
            }
            else {
                isValid = true;
            }
            return isValid;
        }

        function checkUpdatableFieldCriteria() {
            var isValid = false;
            var thisStore = step4Form.store;

            if (thisStore.getCount() == 0 || ! checkFieldCriteriaValid()) {
                Ext.MessageBox.show.defer(1, Ext.MessageBox,
                    [{ title: msgs.error, icon: Ext.MessageBox.ERROR,
                    buttons: Ext.MessageBox.OK,
                    msg: msgs.mustDoStep4}]); // use defer so that the msgBox will appear above the batchWin
            }
            else {
                isValid = true;
            }
            return isValid;
        }

        if (groupToShow.mainItem.id == 'step2Grp' ||
                groupToShow.mainItem.id == 'step3Grp' ||
                groupToShow.mainItem.id == 'step4Grp' ||
                groupToShow.mainItem.id == 'step5Grp') {
            if ( ! checkBatchType()) {
                return false;
            }
        }
        if (groupToShow.mainItem.id == 'step3Grp' ||
                groupToShow.mainItem.id == 'step4Grp' ||
                groupToShow.mainItem.id == 'step5Grp') {
            if ( ! checkSegmentationsPicked()) {
                return false;
            }
        }
        if (groupToShow.mainItem.id == 'step4Grp' ||
                groupToShow.mainItem.id == 'step5Grp') {
            if ( ! checkRowsAvailableToUpdate()) {
                return false;
            }
        }
        if (groupToShow.mainItem.id == 'step5Grp') {
            if ( ! checkUpdatableFieldCriteria()) {
                return false;
            }
        }
        return true;
    }

    function initFocus(groupTabPanel, thisGrp, startNum) {
        if ( ! startNum) {
            startNum = 0;
        }
        if (thisGrp.mainItem.id == 'step1Grp') {
            setTimeout(function() {
                var elem = Ext.getCmp('batchDesc');
                if (elem && elem.el) {
                    elem.el.focus();
                }
            }, 900);
            batchWin.setTitle(msgs.manageBatch + ": " + msgs.step1Tip);
            // TODO: load step1 store with data
        }
        else if (thisGrp.mainItem.id == 'step2Grp') {
            var batchType = getBatchTypeValue();
            step2Store.load({ params: { batchType: batchType, start: startNum, limit: 50, sort: 'lastDt', dir: 'DESC' }});
            batchWin.setTitle(msgs.manageBatch + ": " + msgs.step2Tip);
            $('#step1Num').addClass('complete');
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
            var batchType = getBatchTypeValue();
            step3Store.load({ params: { 'ids': selIds.toString(), 'batchType': batchType, start: startNum, limit: 50 }});
            batchWin.setTitle(msgs.manageBatch + ": " + msgs.step3Tip);
            $('#step2Num').addClass('complete');
        }
        else if (thisGrp.mainItem.id == 'step4Grp') {
            var batchType = getBatchTypeValue();
            step4UpdatableFieldsStore.load({ params: { 'batchType': batchType }});
            batchWin.setTitle(msgs.manageBatch + ": " + msgs.step4Tip);
            $('#step3Num').addClass('complete');
        }
        else if (thisGrp.mainItem.id == 'step5Grp') {
            batchWin.setTitle(msgs.manageBatch + ": " + msgs.step5Tip);
            var step4DataItems = step4Form.store.data.items;
            var params = {};
            for (var x = 0; x < step4DataItems.length; x++) {
                var value = step4DataItems[x].data.value;
                if (Ext.isDate(value)) {
                    value = value.dateFormat('Y-m-d H:i:s');
                }
                params['param-' + step4DataItems[x].data.name] = value;
            }
            params['batchType'] = getBatchTypeValue();
            params['ids'] = step3Store.collect('id').toString();
            params['start'] = startNum;
            params['limit'] = 20;
            params['sort'] = 'id';
            params['dir'] = 'ASC';
            step5Store.load({ 'params': params });
            $('#step4Num').addClass('complete');
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
                    if (panel.setActiveGroup(1)) {
                        var firstItem = panel.items.items[1];
                        firstItem.setActiveTab(firstItem.items.items[0]);
                        $('#step1Num').addClass('complete');
                    }
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

    function getBatchTypeDesc() {
        return Ext.getCmp('batchType').getRawValue();
    }

    function getBatchTypeValue() {
        return Ext.getCmp('batchType').getValue();
    }
    
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
            {name: 'lastUser', mapping: 'lastUser', type: 'string'},
            {name: 'picked', mapping: 'picked', type: 'boolean'}
        ],
        listeners: {
            'beforeload': function(store, options) {
                Ext.get($('#step1Grp').parent('div').next().attr('id')).mask(msgs.loadingSegmentations);
            },
            'load': function(store, records, options) {
                var len = records.length;
                for (var x = 0; x < len; x++) {
                    if (records[x].get('picked') === true) {
                        step2Form.getSelectionModel().selectRow(x, true);
                    }
                }
                if (hasPickedRows()) {
                    step2Form.nextButton.enable();
                }
                Ext.get($('#step1Grp').parent('div').next().attr('id')).unmask();
            },
            'exception': function(misc) {
                Ext.get($('#step1Grp').parent('div').next().attr('id')).unmask();
                Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                    buttons: Ext.MessageBox.OK,
                    msg: msgs.errorStep2 });
            }
        }
    });

    function hasPickedRows() {
        return step2Store.find('picked', true) > -1;
    }

    var step2Bar = new OrangeLeap.BatchToolbar({
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
                step2Form.nextButton.enable();
            }
            else {
                step2Form.getSelectionModel().deselectRow(index);
                if ( ! hasPickedRows()) {
                    step2Form.nextButton.disable();
                }
            }
        }
    });

    var step2RowSelModel = new Ext.grid.RowSelectionModel({singleSelect: false});

    step2RowSelModel.on({
        'rowselect': function(selModel, rowIndex, record) {
            record.set('picked', true);
            step2Form.nextButton.enable();
        },
        'rowdeselect': function(selModel, rowIndex, record) {
            record.set('picked', false);
            if ( ! hasPickedRows()) {
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
        forceLayout: true,
        header: false,
        frame: false,
        border: false,
        selModel: step2RowSelModel,
        viewConfig: {
            forceFit: true,
            emptyText: String.format(msgs.noSegmentationsFound, getBatchTypeDesc())
        },
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
                    if (panel.setActiveGroup(2)) { // check that all required fields entered and activeGroup is actually set to 2
                        var firstItem = panel.items.items[2];
                        firstItem.setActiveTab(firstItem.items.items[0]);
                        $('#step2Num').addClass('complete');
                    }
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
        ],
        listeners: {
            'metachange': function(store, meta) {
                var cols = [];
                cols[0] =  { header: msgs.id,
                    sortable: true,
                    dataIndex: 'id',
                    editable: false,
                    editor: new Ext.form.DisplayField() };

                var fields = meta.fields;
                for (var x = 0; x < fields.length; x++) {
                    var name = fields[x].name;
                    if (name && name != 'constituentId' && name != 'id') {
                        cols[cols.length] = {
                            header: fields[x].header, dataIndex: name, sortable: true,
                            renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                                return '<span ext:qwidth="250" ext:qtip="' + value + '">' + value + '</span>';
                            }
                        };
                    }
                }
                step3Form.reconfigure(store, new Ext.grid.ColumnModel(cols));
            },
            'beforeload': function(store, options) {
                Ext.get($('#step2Grp').parent('div').next().attr('id')).mask(msgs.loadingRows);
            },
            'load': function(store, records, options) {
                if (records.length > 0) {
                    step3Form.nextButton.enable();  // Only enable next if there are rows available to select
                }
                else {
                    step3Form.nextButton.disable();
                }
                Ext.get($('#step2Grp').parent('div').next().attr('id')).unmask();
            },
            'exception': function(misc) {
                Ext.get($('#step2Grp').parent('div').next().attr('id')).unmask();
                Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                    buttons: Ext.MessageBox.OK,
                    msg: msgs.errorStep3 });
            }
        }
    });

    var step3Bar = new OrangeLeap.BatchToolbar({
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
        forceLayout: true,
        header: false,
        frame: false,
        border: false,
        selModel: step3RowSelect,
        viewConfig: {
            forceFit: true,
            emptyText: String.format(msgs.noRowsFound, getBatchTypeDesc())
        },
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
                disabled: true,
                disabledClass: 'disabledButton',
                handler: function(button, event) {
                    var panel = batchWin.groupTabPanel;
                    if (panel.setActiveGroup(3)) { // check that all required fields entered and activeGroup is actually set to 3
                        var firstItem = panel.items.items[3];
                        firstItem.setActiveTab(firstItem.items.items[0]);
                        $('#step3Num').addClass('complete');
                    }
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
    step3Form.on('rowdblclick', function(grid, rowIndex, event) {
        var batchType = getBatchTypeValue();
        var record = step3Store.getAt(rowIndex);
        if (batchType && record) {
            // open window to view record
            var thisUrl = batchType + '.htm?' + batchType + 'Id=' + record.get('id') +
                                  (record.get('constituentId') ? '&constituentId=' + record.get('constituentId') : '');
            window.open(thisUrl, batchType + 'Win');
        }
    });

    var step4UpdatableFieldsStore = new Ext.data.JsonStore({
        url: 'findBatchUpdateFields.json',
        autoLoad: false,
        autoSave: false,
        totalProperty: 'totalRows',
        root: 'rows',
        fields: [
            {name: 'name', type: 'string'},
            {name: 'desc', type: 'string'},
            {name: 'type', type: 'string'},
            {name: 'value', type: 'string'},
            {name: 'selected', type: 'boolean'}
        ],
        listeners: {
            'load': function(store, records, options) {
                var len = records.length;
                var newPropertyNames = {};
                var newCustomEditors = {};
                var initValues = {};
                var newSource;

                for (var x = 0; x < len; x++) {
                    var recName = records[x].get('name');
                    newPropertyNames[recName] = records[x].get('desc');

                    var recType = records[x].get('type');
                    var recVal = records[x].get('value');

                    // Source is for existing data, not new data
                    if (! Ext.isEmpty(recVal)) {
                        if ( ! newSource) {
                            newSource = {};
                        }
                        records[x].set('selected', true);
                        if (recType == 'date' || recType == 'date_time') {
                            newSource[recName] = Ext.isDate(recVal) ? recVal : new Date(Date.parse(recVal));
                        }
                        else if (recType == 'checkbox') {
                            newSource[recName] = Ext.isBoolean(recVal) ? recVal : (recVal.toString().toLowerCase() == 'true' ||
                                                                                   recVal.toString().toLowerCase() == 't' ||
                                                                                   recVal.toString().toLowerCase() == 'y' ||
                                                                                   recVal == '1');
                        }
                        else if (recType == 'number' || recType == 'percentage') {
                            newSource[recName] = Ext.isNumber(recVal) ? recVal : (recVal.toString().indexOf(".") > -1 ? parseFloat(recVal) : parseInt(recVal, 10));
                        }
                        else {
                            newSource[recName] = recVal;
                        }
                    }

                    // Custom editors determine what type of control will be displayed to the user
                    if (recType == 'date' || recType == 'date_time') {
                        newCustomEditors[recName] = step4Form.colModel.editors['date'];
                        initValues[recName] = new Date();
                    }
                    else if (recType == 'checkbox') {
                        newCustomEditors[recName] = step4Form.colModel.editors['boolean'];
                        initValues[recName] = true;
                    }
                    else if (recType == 'number' || recType == 'percentage') {
                        newCustomEditors[recName] = step4Form.colModel.editors['number'];
                        initValues[recName] = parseInt(0, 10);
                    }
                    else if (recType == 'picklist') {    // TODO: multi_picklist, code, code_other, query_lookup, query_lookup_other
                        var myPicklist = step4Picklists[recName + '-Data'];
                        if (myPicklist) {
                            var myStore = new Ext.data.JsonStore({
                                autoSave: false,
                                fields: ['itemName', 'displayVal'],
                                data: myPicklist
                            });
                            var initVal = myPicklist.length > 0 ? myPicklist[0]['itemName'] : '';
                            initValues[recName] = initVal;
                            newCustomEditors[recName] = new Ext.grid.GridEditor(new Ext.form.ComboBox({
                                name: recName,
                                allowBlank: false,
                                displayField: 'displayVal',
                                valueField: 'itemName',
                                typeAhead: true,
                                mode: 'local',
                                triggerAction: 'all',
                                selectOnFocus: true,
                                forceSelection: true,
                                store: myStore,
                                value: initVal
                            }));
                        }
                    }
                    else {
                        // every other type is a string editor type
                        initValues[recName] = '';
                    }
                }
                if ( ! newSource) {
                    // if there are no pre-existing values, we have to initialize to the first one in the records list with a default value
                    newSource = {};
                    var thisRecName = records[0].get('name');
                    records[0].set('selected', true);
                    newSource[thisRecName] = initValues[thisRecName];
                }
                step4Form.propertyNames = newPropertyNames;
                step4Form.customEditors = newCustomEditors;
                step4Form.setSource(newSource);
                Ext.get($('#step3Grp').parent('div').next().attr('id')).unmask();
            },
            'beforeload': function(store, options) {
                Ext.get($('#step3Grp').parent('div').next().attr('id')).mask(msgs.loading);
            },
            'exception': function(misc) {
                Ext.get($('#step3Grp').parent('div').next().attr('id')).unmask();
                Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                    buttons: Ext.MessageBox.OK,
                    msg: msgs.errorStep4 });
            }
        }
    });
    var step4Picklists = {};
    step4UpdatableFieldsStore.proxy.on('load', function(proxy, txn, options) {
        if (txn.reader.jsonData && txn.reader.jsonData.rows) {
            var rows = txn.reader.jsonData.rows;
            var len = rows.length;

            // Setup custom editors, if any
            for (var x = 0; x < len; x++) {
                if (rows[x].type == 'picklist') {
                    var name = rows[x].name;
                    var picklistNameKey = name + '-Data'; // get the JSON itemName/displayVal data for the picklist
                    if (txn.reader.jsonData[picklistNameKey]) {
                        step4Picklists[picklistNameKey] = txn.reader.jsonData[picklistNameKey];
                    }
                }
            }
        }
    });

    var step4Form = new OrangeLeap.DynamicPropertyGrid({
        width: 726,
        height: 468,
        loadMask: true,
        header: false,
        frame: false,
        border: false,
        forceLayout: true,
        propertyNames: { },
        source: { },
        viewConfig : { forceFit: true },
        updatableFieldsStore: step4UpdatableFieldsStore,
        customEditors: { },
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
                text: msgs.next,
                cls: 'saveButton',
                ref: '../nextButton',
                formBind: true,
                disabledClass: 'disabledButton',
                handler: function(button, event) {
                    var panel = batchWin.groupTabPanel;
                    if (panel.setActiveGroup(4)) { // check that all required fields entered and activeGroup is actually set to 4
                        var firstItem = panel.items.items[4];
                        firstItem.setActiveTab(firstItem.items.items[0]);
                        $('#step4Num').addClass('complete');
                    }
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

    var checkStep4EnableButton = function(store) {
        if (store.getCount() == 0 || ! checkFieldCriteriaValid()) {
            step4Form.nextButton.disable();
        }
        else {
            step4Form.nextButton.enable();
        }
    };
    step4Form.store.addListener({
        'add': checkStep4EnableButton,
        'update': checkStep4EnableButton,
        'remove': checkStep4EnableButton
    });

    var step5Reader = new Ext.data.JsonReader();

    var step5Store = new OrangeLeap.ListStore({
        url: 'reviewUpdates.json',
        reader: step5Reader,
        root: 'rows',
        totalProperty: 'totalRows',
        remoteSort: true,
        sortInfo: {field: 'id', direction: 'ASC'},
        fields: [
            {name: 'id', mapping: 'id', type: 'int'}
        ],
        listeners: {
            'beforeload': function(store, options) {
                Ext.get($('#step4Grp').parent('div').next().attr('id')).mask(msgs.loadingRows);
            },
            'load': function(store, records, options) {
                Ext.get($('#step4Grp').parent('div').next().attr('id')).unmask();
            },
            'exception': function(misc) {
                Ext.get($('#step4Grp').parent('div').next().attr('id')).unmask();
                Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                    buttons: Ext.MessageBox.OK,
                    msg: msgs.errorStep5 });
            }
        }
    });

    step5Store.on('metachange', function(store, meta) {
        var cols = [];
        var fields = meta.fields;
        for (var x = 0; x < fields.length; x++) {
            var name = fields[x].name;
            if (name && name != 'constituentId' && name != 'id') {
                cols[cols.length] = {
                    header: fields[x].header, dataIndex: name, sortable: (name != 'type' && name != 'displayedId'),
                    renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                        return '<span ext:qwidth="250" ext:qtip="' + value + '">' + value + '</span>';
                    }
                };
            }
        }
        step5Form.reconfigure(store, new Ext.grid.ColumnModel(cols));
    });

    var step5Bar = new OrangeLeap.BatchToolbar({
        pageSize: 50,
        stateEvents: ['change'],
        stateId: 'step5Bar',
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
        store: step5Store,
        displayInfo: true,
        displayMsg: msgs.displayMsg,
        emptyMsg: msgs.emptyMsg
    });

    var step5RowSelect = new Ext.grid.RowSelectionModel();
    step5RowSelect.on('beforerowselect', function(selModel, rowIndex, keepExisting, record) {
        return false;
    });

    var step5Toolbar = new Ext.Toolbar({
        items: [
            msgs.followingChangesApplied
        ]
    });
    step5Toolbar.on('afterlayout', function(tb){
        tb.el.child('.x-toolbar-right').remove();
        var t = tb.el.child('.x-toolbar-left');
        t.removeClass('x-toolbar-left');
        t = tb.el.child('.x-toolbar-ct');
        t.setStyle('width', 'auto');
        t.wrap({tag: 'center'});
    }, null, {single: true});

    var step5Form = new Ext.grid.GridPanel({
        stateId: 'step5List',
        stateEvents: ['columnmove', 'columnresize', 'sortchange', 'bodyscroll'],
        stateful: true,
        store: step5Store,
        bbar: step5Bar,
        stripeRows: false,
        width: 726,
        height: 468,
        loadMask: true,
        header: false,
        frame: false,
        border: false,
        selModel: step5RowSelect,
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
                    panel.setActiveGroup(3);
                    var thisItem = panel.items.items[3];
                    thisItem.setActiveTab(thisItem.items.items[0]);
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
        buttonAlign: 'center',
        tbar: step5Toolbar
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
        items:[{
             xtype: 'grouptabpanel',
             tabWidth: 135,
             activeGroup: 0,
             ref: '../groupTabPanel',
             layoutOnTabChange: true,
             listeners: {
                 'groupchange': initFocus,
                 'beforegroupchange': checkConditions
             },
             items: [
                 {
                     layoutOnTabChange: true,
                     items: [{
                         id: 'step1Grp',
                         title: msgs.step1Title,
                         tabTip: msgs.step1Tip,
                         style: 'padding: 20px 40px;',
                         items: [ step1Form ]
                     }]
                 },
                 {
                     layoutOnTabChange: true,
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
                         items: [ step4Form ]
                     }]
                 },
                 {
                     items: [{
                         id: 'step5Grp',
                         title: msgs.step5Title,
                         tabTip: msgs.step5Tip,
                         items: [ step5Form ]
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


