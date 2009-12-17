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
    cancel: 'Cancel',
    close: 'Close',
    gift: 'Gift',
    name: 'Name',
    adjustedGift: 'Adjusted Gift',
    showOpenBatches: 'Show Open Batches',
    showExecutedBatches: 'Show Executed Batches',
    showBatchesWithErrors: 'Show Batches with Errors',
    askExecuteBatch: 'Execute Batch?',
    askDeleteBatch: 'Delete Batch?',
    areYouSureDeleteBatch: 'Are you sure you want to delete batch ID <strong>{0}</strong>?',
    areYouSureExecuteBatch: 'Are you sure you want to execute batch ID <strong>{0}</strong>?',
    cannotDeleteExecutedBatch: 'You cannot delete an executed batch.',
    cannotExecuteExecutedBatch: 'You cannot execute an already executed batch.',
    cannotExecuteBatchCorrectErrors: 'This batch cannot be executed until the following errors are corrected: ',
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
    batchList: 'Batch List <span id="savedMarker" class="marker">Saved</span> <span id="executedMarker" class="marker">Executed</span> <span id="deletedMarker" class="marker">Deleted</span>',
    addNewBatch: 'Add a new Batch',
    manageBatch: 'Manage Batch',
    chooseSegmentations: 'Choose Segmentations',
    count: 'Count',
    lastExecDt: 'Last Execution Date',
    lastExecBy: 'Last Executed By',
    error: 'Error',
    info: 'Info',
    mustDoStep1: 'You must choose a Batch Type (Step 1) first.',
    mustDoStep2: 'You must pick Segmentations (Step 2) first.',
    mustDoStep3: 'There are no updatable rows based on the Segmentation you chose.  Choose a different segmentation (Step 2).',
    mustDoStep4: 'You must create Field Update Criteria (Step 4) first.',
    loading: 'Loading...',
    loadingSegmentations: 'Loading Segmentations...',
    loadingRows: 'Loading Rows...',
    deletingBatch: 'Deleting Batch...',
    executingBatch: 'Executing Batch...',
    followingBeModified: 'For your reference, the following rows will be modified. Click \'Next\' to continue or \'Prev\' to change segmentations',
    followingChangesApplied: 'The following changes will be applied when you execute the batch.',
    noSegmentationsFound: 'No Segmentations were found for the Type selected.  Please choose a different Type (Step 1).',
    noRowsFound: 'No rows were found for the Segmentations selected.  Please choose a different Segmentation (Step 2).',
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
    valueRequired: 'A value is required',
    errorStep1: 'Could not load Step 1 data.  Please try again or contact your administrator if this issue continues.',
    errorStep2: 'Could not load Step 2 data.  Please try again or contact your administrator if this issue continues.',
    errorStep3: 'Could not load Step 3 data.  Please try again or contact your administrator if this issue continues.',
    errorStep4: 'Could not load Step 4 data.  Please try again or contact your administrator if this issue continues.',
    errorStep5: 'Could not load Step 5 data.  Please try again or contact your administrator if this issue continues.',
    errorSave: 'The batch could not be saved due to an error.  Please try again or contact your administrator if this issue continues.',
    errorAjax: 'The request could not be processed due to an error.  Please try again or contact your administrator if this issue continues.',
    errorBatchDelete: 'The batch could not be deleted due to an error.  Please try again or contact your administrator if this issue continues.',
    errorBatchExecute: 'The batch could not be executed due to an error.  Please try again or contact your administrator if this issue continues.',
    batchExecutedWithErrorBatchCreated: 'The batch execution completed but an Error Batch with ID <strong>{0}</strong> ' +
                                        'was created for rows that were not able to be executed due to errors. ' +
                                        'Select "Show Batches With Errors" to edit criteria and re-execute.'
};

Ext.onReady(function() {
    Ext.QuickTips.init();

    var msgs = OrangeLeap.msgBundle;

    Ext.Ajax.on('requestexception', function(conn, response, options) {
        Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
            buttons: Ext.MessageBox.OK,
            msg: msgs.errorAjax });
    });

    var batchReader = new Ext.data.JsonReader();

    var store = new OrangeLeap.ListStore({
        url: 'batchList.json',
        reader: batchReader,
        root: 'rows',
        totalProperty: 'totalRows',
        autoSave: true,
        remoteSort: true,
        sortInfo: {field: 'id', direction: 'ASC'},
        fields: [
            {name: 'id', mapping: 'id', type: 'int'}
        ],
        listeners: {
            'metachange': function(store, meta) {
                var cols = [];
                var fields = meta.fields;

                for (var x = 0; x < fields.length; x++) {
                    var name = fields[x].name;
                    // Do not have a column for 'executed' displayed
                    if (name != 'executed') {
                        var hdr = fields[x].header;
                        cols[cols.length] = {
                            header: hdr, dataIndex: name, sortable: true,
                            renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                                return '<span ext:qtitle="' + hdr + '"ext:qwidth="250" ext:qtip="' + value + '">' + value + '</span>';
                            }
                        };
                    }
                }
                var showBatchStatus = combo.getValue();
                if (showBatchStatus !== 'executed' && (OrangeLeap.allowExecute || OrangeLeap.allowDelete)) {
                    /* Add the 'actions' column for not executed batches */
                    cols[cols.length] = { header: ' ', width: 50, menuDisabled: true, fixed: false, css: 'cursor:default;', id: 'actions',
                        renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                            if ( ! record.get('executed')) {
                                var html = '';
                                if (OrangeLeap.allowExecute) {
                                    html += '<a href="javascript:void(0)" class="executeLink" id="execute-link-' + record.id +
                                               '" ext:qwidth="150" ext:qtip="' + msgs.executeBatch + '">' + msgs.executeBatch + '</a>&nbsp;';
                                }
                                if (OrangeLeap.allowDelete) {
                                    html += '<a href="javascript:void(0)" class="deleteLink" id="delete-link-' + record.id +
                                            '" ext:qwidth="150" ext:qtip="' + msgs.removeBatch + '">' + msgs.removeBatch + '</a>';
                                }
                                return html;
                            }
                            return '';
                        }
                    }
                }
                grid.reconfigure(store, new Ext.grid.ColumnModel(cols));
                bar.bindStore(store, true);
            },
            'exception': function(misc) {
                Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                    buttons: Ext.MessageBox.OK,
                    msg: msgs.errorAjax });
            }
        }
    });

    // custom toolbar for batch GRID to  - this effectively overrides what the 'refresh' button action is on the GRID toolbar
    OrangeLeap.BatchGridToolbar = Ext.extend(Ext.PagingToolbar, {
        doLoad: function(start) {
            var o = { }, pn = this.getParams();
            o[pn.start] = start;
            o[pn.limit] = this.pageSize;

            var state = this.store.getSortState();
            o['sort'] = state.field;
            o['dir'] = state.direction;
            o['showBatchStatus'] = combo.getValue();

            if (this.fireEvent('beforechange', this, o) !== false) {
                this.store.load( {params: o} );
            }
        }
    });
    var bar = new OrangeLeap.BatchGridToolbar({
        pageSize: 100,
        stateEvents: ['change'],
        stateId: 'pageBar',
        stateful: true,
        getState: function() {
            var config = {};
            config.start = this.cursor;
            return config;
        },
        applyState: function(state, config) {
            if (state.start) {
                this.cursor = state.start;
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
                'showBatchStatus',
                'desc'
            ],
            data: [['open', msgs.showOpenBatches], ['executed', msgs.showExecutedBatches], ['errors', msgs.showBatchesWithErrors]]
        }),
        displayField: 'desc',
        valueField: 'showBatchStatus',
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
        var showBatchStatusVal = comboBox.getValue();

        if (OrangeLeap.allowCreate) {
            if (showBatchStatusVal !== 'open') {
                grid.addButton.disable();
            }
            else {
                grid.addButton.enable();
            }
        }
        var state = store.getSortState();
        if (state) {
            state.field = showBatchStatusVal == 'executed' ? 'executedDate' : 'createDate';
            state.direction = 'DESC';
        }
        store.load( { params: { showBatchStatus: showBatchStatusVal, start: 0, limit: 100, sort: state.field, dir: state.direction } });
    });

    var grid = new Ext.grid.GridPanel({
        id: 'batchList',
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
                var colCt = cm.getColumnCount();
                for (var i = 0; i < state.mc.length; i++) {
                    var colIndex = cm.findColumnIndex(state.mc[i].di);
                    if (colIndex != -1 && i < colCt) {
                        if (colIndex != i) {
                            cm.moveColumn(colIndex, i);
                        }
                        cm.setHidden(i, state.mc[i].h);
                        cm.setColumnWidth(i, state.mc[i].w);
                    }
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
            { header: msgs.id, dataIndex: 'id', width: 40, sortable: true }
        ],
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
//        viewConfig: { forceFit: true },
        height: 600,
        width: 780,
        frame: true,
        header: true,
        title: msgs.batchList,
        loadMask: true,
        listeners: {
            rowdblclick: function(grid, row, evt) {
                if (OrangeLeap.allowCreate) {
                    var rec = grid.getSelectionModel().getSelected();
                    showBatchWin(rec.get('id'));
                }
            },
            click: function(event) {
                if (OrangeLeap.allowDelete) {
                    var deleteTarget = event.getTarget('a.deleteLink');
                    if (deleteTarget) {
                        event.stopPropagation();
                        deleteBatch(deleteTarget);
                    }
                }
                if (OrangeLeap.allowExecute) {
                    var executeTarget = event.getTarget('a.executeLink');
                    if (executeTarget) {
                        event.stopPropagation();
                        executeBatch(executeTarget);
                    }
                }
            }
        },
        tbar: [
            combo
        ],
        bbar: bar,
        renderTo: 'managerGrid'
    });

    if (OrangeLeap.allowCreate) {
        var toolbar = grid.getTopToolbar();
        toolbar.addSpacer();
        toolbar.addSeparator();
        toolbar.addSpacer();
        toolbar.addButton({ text: msgs.addNew, tooltip: msgs.addNewBatch,
            iconCls:'add', id: 'addButton', ref: '../addButton',
            handler: function() {
                showBatchWin(null);
            }
        });
    }

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

    // When the page loads, show the Open Batches first
    store.load( { params: { showBatchStatus: 'open', start: pageStart, limit: pageLimit, sort: sortProp, dir: sortDir },
        callback: function(rec, options, success) {
            combo.setValue('open');
            var thisView = grid.getView();
            if (thisView.prevScrollState) {
                thisView.restoreScroll(thisView.prevScrollState);
            }
        }
    });

    function executeBatch(target) {
        if (OrangeLeap.allowExecute) {
            var batchId = target.id;
            if (batchId) {
                batchId = batchId.replace('execute-link-', '');
                var rec = store.getById(batchId);
                // Disallow executes of already executed batches
                if (rec.get('executed')) {
                    Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                        buttons: Ext.MessageBox.OK,
                        msg: msgs.cannotExecuteExecutedBatch });
                }
                else {
                    Ext.Msg.show({
                        title: msgs.askExecuteBatch,
                        msg: String.format(msgs.areYouSureExecuteBatch, batchId),
                        buttons: Ext.Msg.OKCANCEL,
                        icon: Ext.MessageBox.WARNING,
                        fn: function(btn, text) {
                            if (btn == "ok") {
                                var recToExecute = store.getById(batchId);
                                if (recToExecute) {
                                    Ext.get('batchList').mask(msgs.executingBatch);
                                    
                                    $('#batchExecutor').load(function() {
                                        Ext.get('batchList').unmask();
                                        var returnObj = Ext.decode( $( $(this).get(0).contentWindow.document.body ).text());
                                        if ( ! returnObj) {
                                            Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                                                buttons: Ext.MessageBox.OK,
                                                msg: msgs.errorBatchExecute });
                                        }
                                        else {
                                            if (returnObj.hasBatchErrors) {
                                                var thisMsg = msgs.cannotExecuteBatchCorrectErrors;
                                                if (returnObj.errorMsgs) {
                                                    var len = returnObj.errorMsgs.length;
                                                    thisMsg += '<ul class="listable">';
                                                    for (var x = 0; x < len; x++) {
                                                        var thisErrorMsg = returnObj.errorMsgs[x];
                                                        thisErrorMsg = thisErrorMsg.replace(/&lt;/g, '<').replace(/&gt;/g, '>').replace(/&quot;/g, '"');
                                                        thisMsg += '<li>' + thisErrorMsg + '</li>';
                                                    }
                                                    thisMsg += '</ul>';
                                                }
                                                Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                                                    buttons: Ext.MessageBox.OK,
                                                    msg: thisMsg });
                                            }
                                            else {
                                                store.remove(recToExecute);

                                                // no batch errors; check if any errors occurred in the batch entries themselves and an error batch was created
                                                if (returnObj.errorBatchId && Ext.isNumber(returnObj.errorBatchId)) {
                                                    var aMsg = String.format(msgs.batchExecutedWithErrorBatchCreated, returnObj.errorBatchId);
                                                    Ext.MessageBox.show({ title: msgs.info, icon: Ext.MessageBox.INFO,
                                                        buttons: Ext.MessageBox.OK, fn: showExecutedMarker, 
                                                        msg: aMsg });
                                                }
                                                else {
                                                    showExecutedMarker();
                                                }
                                            }
                                        }
                                    });
                                    $('#batchExecutor').attr('src', 'executeBatch.json?batchId=' + batchId);
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    function showExecutedMarker() {
        hideOtherMarkers('executedMarker');
        $("#executedMarker").show();
        setTimeout(function() {
            $("#executedMarker").hide();
        }, 10000);
    }

    function hideOtherMarkers(markerName) {
        if (markerName != 'executedMarker') {
            $('#executedMarker').hide();
        }
        if (markerName != 'savedMarker') {
            $('#savedMarker').hide();
        }
        if (markerName != 'deletedMarker') {
            $('#deletedMarker').hide();
        }
    }

    function deleteBatch(target) {
        if (OrangeLeap.allowDelete) {
            var batchId = target.id;
            if (batchId) {
                batchId = batchId.replace('delete-link-', '');
                var rec = store.getById(batchId);
                // Disallow deletes of executed batches
                if (rec.get('executed')) {
                    Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                        buttons: Ext.MessageBox.OK,
                        msg: msgs.cannotDeleteExecutedBatch });
                }
                else {
                    Ext.Msg.show({
                        title: msgs.askDeleteBatch,
                        msg: String.format(msgs.areYouSureDeleteBatch, batchId),
                        buttons: Ext.Msg.OKCANCEL,
                        icon: Ext.MessageBox.WARNING,
                        fn: function(btn, text) {
                            if (btn == "ok") {
                                Ext.get('batchList').mask(msgs.deletingBatch);
                                Ext.Ajax.request({
                                    url: 'deleteBatch.json',
                                    method: 'POST',
                                    params: { 'batchId': batchId },
                                    success: function(response, options) {
                                        store.remove(rec);
                                        Ext.get('batchList').unmask();
                                        hideOtherMarkers('deletedMarker');
                                        $("#deletedMarker").show();
                                        setTimeout(function() {
                                            $("#deletedMarker").hide();
                                        }, 10000);
                                    },
                                    failure: function(response, options) {
                                        Ext.get('batchList').unmask();
                                        Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                                            buttons: Ext.MessageBox.OK,
                                            msg: msgs.errorBatchDelete });
                                    }
                                });
                            }
                        }
                    });
                }
            }
        }
    }

    /* The following are the for the create/edit/view batch modal window */
    function showBatchWin(thisBatchId) {
        if (OrangeLeap.allowCreate) {
            batchID = thisBatchId;
            if (batchWin.rendered) {
                // If batchWin has already been rendered, make sure step1 is the first one shown
                var panel = batchWin.groupTabPanel;
                panel.setActiveGroup(0);
                var firstItem = panel.items.items[0];
                firstItem.setActiveTab(firstItem.items.items[0]);
            }
            batchWin.show(batchWin);
        }
    }

    // custom toolbar for batch window to invoke initFocus - this effectively overrides what the 'refresh' button action is on the toolbar
    OrangeLeap.BatchWinToolbar = Ext.extend(Ext.PagingToolbar, {
        doLoad: function(start) {
            var o = { }, pn = this.getParams();
            o[pn.start] = start;
            o[pn.limit] = this.pageSize;
            if (this.fireEvent('beforechange', this, o) !== false) {
                initFocus(batchWin.groupTabPanel, batchWin.groupTabPanel.activeGroup, start);
            }
        }
    });

    var flowExecutionKey = null;
    var batchID = null;

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

    function maskStep1Form() {
        var step1GrpDivId = $('#step1Grp').parent('div').attr('id');
        if (step1GrpDivId) {
            Ext.get(step1GrpDivId).mask(msgs.loading);
        }
    }

    function unmaskStep1Form() {
        var step1GrpDivId = $('#step1Grp').parent('div').attr('id');
        if (step1GrpDivId) {
            Ext.get(step1GrpDivId).unmask();
        }
    }

    var step2PageSize = 20;

    function findPickedSegmentations() {
         // this will find the picked and not picked segmentations on this page only
        var pickedIds = [];
        var notPickedIds = [];
        if (step2Store.data && step2Store.data.items) {
            var items = step2Store.data.items;
            var len = items.length;
            for (var x = 0; x < len; x++) {
                var thisItem = items[x];
                if (thisItem.get('picked')) {
                    pickedIds[pickedIds.length] = thisItem.id;
                }
                else {
                    notPickedIds[notPickedIds.length] = thisItem.id;
                }
            }
        }
        return { 'pickedIds': pickedIds, 'notPickedIds': notPickedIds };
    }

    function initFocus(groupTabPanel, thisGrp, startNum) {
        if ( ! startNum) {
            startNum = 0;
        }
        if (thisGrp.mainItem.id == 'step1Grp') {
            batchWin.setTitle(msgs.manageBatch + ": " + msgs.step1Tip);
            maskStep1Form();
            step1Form.getForm().load({
                url: 'doBatch.htm',
                params: {
                    'batchId': batchID,
                    '_eventId_step1': 'step1',
                    'execution': getFlowExecutionKey()
                },
                success: function(form, action) {
                    flowExecutionKey = action.result.flowExecutionKey;
                    unmaskStep1Form();
                    setTimeout(function() {
                        var elem = Ext.getCmp('batchDesc');
                        if (elem && elem.el) {
                            elem.el.focus();
                        }
                    }, 900);
                },
                failure: function(form, action) {
                    unmaskStep1Form();
                    Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                        buttons: Ext.MessageBox.OK,
                        msg: msgs.errorStep1 });
                }
            });
        }
        else if (thisGrp.mainItem.id == 'step2Grp') {
            // Submit step 1's data to step 2 - submit saved IDs to back end
            var theseSegs = findPickedSegmentations();
            step2Store.load({ params: { 'batchType': getBatchTypeValue(), 'pickedIds': theseSegs.pickedIds.toString(),
                'notPickedIds': theseSegs.notPickedIds.toString(),
                'batchDesc': Ext.getCmp('batchDesc').getValue(),
                'start': startNum, 'limit': step2PageSize, '_eventId_step2': 'step2',
                'execution': getFlowExecutionKey(), 'sort': 'lastDt', 'dir': 'DESC' }});
            batchWin.setTitle(msgs.manageBatch + ": " + msgs.step2Tip);
            $('#step1Num').addClass('complete');
        }
        else if (thisGrp.mainItem.id == 'step3Grp') {
            // Submit step 2's data to step 3
            var theseSegs = findPickedSegmentations();
            step3Store.load({ params: { 'pickedIds': theseSegs.pickedIds.toString(), 'notPickedIds': theseSegs.notPickedIds.toString(),
                '_eventId_step3': 'step3', 'execution': getFlowExecutionKey(), start: startNum, limit: 50 }});
            batchWin.setTitle(msgs.manageBatch + ": " + msgs.step3Tip);
            $('#step2Num').addClass('complete');
        }
        else if (thisGrp.mainItem.id == 'step4Grp') {
            // No data for step 3 (just reference data) to submit to step 4
            step4UpdatableFieldsStore.load({ params: { '_eventId_step4': 'step4',
                'execution': getFlowExecutionKey() }});
            batchWin.setTitle(msgs.manageBatch + ": " + msgs.step4Tip);
            $('#step3Num').addClass('complete');
        }
        else if (thisGrp.mainItem.id == 'step5Grp') {
            batchWin.setTitle(msgs.manageBatch + ": " + msgs.step5Tip);
            // Submit step 4's data to step 5
            var step4DataItems = step4Form.store.data.items;
            var params = {};
            for (var x = 0; x < step4DataItems.length; x++) {
                var value = step4DataItems[x].data.value;
                if (Ext.isDate(value)) {
                    value = value.dateFormat('Y-m-d H:i:s');
                }
                params['param-' + step4DataItems[x].data.name] = value;
            }
            params['start'] = startNum;
            params['limit'] = 20;
            params['sort'] = 'id';
            params['dir'] = 'ASC';
            params['_eventId_step5'] = 'step5';
            params['execution'] = getFlowExecutionKey();
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
                disabled: true,
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
                text: msgs.cancel,
                cls: 'button',
                ref: '../closeButton',
                handler: function(button, event) {
                    cancelBatch();
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
                listeners: {
                    'focus': elementFocus,
                    'blur': elementBlur,
                    'change': function(field, newVal, oldVal) {
                        if (Ext.isEmpty(newVal)) {
                            step1Form.nextButton.disable();
                        }
                        else {
                            // If the new batch type is different from the old batch type, they have to repeat steps 2-5 again
                            if (newVal != oldVal) {
                                resetSteps2Thru5();
                            }
                            step1Form.nextButton.enable();
                        }
                    },
                    scope: this
                }
            }
        ]
    });

    function getBatchTypeValue() {
        return Ext.getCmp('batchType').getValue();
    }

    function getFlowExecutionKey() {
        return flowExecutionKey;
    }

    var pickedSegmentationsCount = 0;
    var step2Store = new OrangeLeap.ListStore({
        url: 'doBatch.htm',
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
                        step2Form.getView().onRowSelect(x);
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
    step2Store.proxy.on('load', function(proxy, txn, options) {
        flowExecutionKey = txn.reader.jsonData.flowExecutionKey; // update the flowExecutionKey generated by spring web flow
        pickedSegmentationsCount = txn.reader.jsonData.pickedSegmentationsCount;
    });

    function hasPickedRows() {
        return pickedSegmentationsCount > 0;
    }

    var step2Bar = new OrangeLeap.BatchWinToolbar({
        pageSize: step2PageSize,
        stateEvents: ['change'],
        stateId: 'step2Bar',
        stateful: true,
        getState: function() {
            var config = {};
            config.start = this.cursor;
            return config;
        },
        applyState: function(state, config) {
            if (state.start) {
                this.cursor = state.start;
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
                pickedSegmentationsCount++;
                step2Form.getView().onRowSelect(index);
                step2Form.nextButton.enable();
            }
            else {
                pickedSegmentationsCount--;
                step2Form.getView().onRowDeselect(index);
                if ( ! hasPickedRows()) {
                    step2Form.nextButton.disable();
                }
            }
        }
    });

    var step2RowSelModel = new Ext.grid.RowSelectionModel({
        singleSelect: false,
        listeners: {
            'beforerowselect': function() {
                return false; // disallow individual row selects; users must use the checkbox
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
//            forceFit: true,
            emptyText: msgs.noSegmentationsFound
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
                text: msgs.cancel,
                cls: 'button',
                ref: '../closeButton',
                handler: function(button, event) {
                    cancelBatch();
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
            }
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
                var colCt = cm.getColumnCount();
                for (var i = 0; i < state.mc.length; i++) {
                    var colIndex = cm.findColumnIndex(state.mc[i].di);
                    if (colIndex != -1 && i < colCt) {
                        if (colIndex != i) {
                            cm.moveColumn(colIndex, i);
                        }
                        cm.setHidden(i, state.mc[i].h);
                        cm.setColumnWidth(i, state.mc[i].w);
                    }
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
        url: 'doBatch.htm',
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
                step3Bar.bindStore(store, true);
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
    step3Store.proxy.on('load', function(proxy, txn, options) {
        flowExecutionKey = txn.reader.jsonData.flowExecutionKey; // update the flowExecutionKey generated by spring web flow
    });

    var step3Bar = new OrangeLeap.BatchWinToolbar({
        pageSize: 50,
        stateEvents: ['change'],
        stateId: 'step3Bar',
        stateful: true,
        getState: function() {
            var config = {};
            config.start = this.cursor;
            return config;
        },
        applyState: function(state, config) {
            if (state.start) {
                this.cursor = state.start;
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
            emptyText: msgs.noRowsFound
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
                text: msgs.cancel,
                cls: 'button',
                ref: '../closeButton',
                handler: function(button, event) {
                    cancelBatch();
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
        url: 'doBatch.htm',
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
                            newSource[recName] = Ext.isDate(recVal) ? recVal : Date.parseDate(recVal, 'Y-m-d H:i:s');
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
        flowExecutionKey = txn.reader.jsonData.flowExecutionKey; // update the flowExecutionKey generated by spring web flow
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
                text: msgs.cancel,
                cls: 'button',
                ref: '../closeButton',
                handler: function(button, event) {
                    cancelBatch();
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
        url: 'doBatch.htm',
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
        step5Bar.bindStore(store, true);
    });
    step5Store.proxy.on('load', function(proxy, txn, options) {
        flowExecutionKey = txn.reader.jsonData.flowExecutionKey; // update the flowExecutionKey generated by spring web flow
    });

    var step5Bar = new OrangeLeap.BatchWinToolbar({
        pageSize: 50,
        stateEvents: ['change'],
        stateId: 'step5Bar',
        stateful: true,
        getState: function() {
            var config = {};
            config.start = this.cursor;
            return config;
        },
        applyState: function(state, config) {
            if (state.start) {
                this.cursor = state.start;
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
                    saveBatch();
                }
            },
            {
                text: msgs.cancel,
                cls: 'button',
                ref: '../closeButton',
                handler: function(button, event) {
                    cancelBatch();
                }
            }
        ],
        buttonAlign: 'center',
        tbar: step5Toolbar
    });

    function saveBatch() {
        Ext.Ajax.request({
            url: 'doBatch.htm',
            method: 'POST',
            params: { '_eventId_save': 'save', 'execution': getFlowExecutionKey() },
            success: function(response, options) {
                var responseText = response.responseText;
                // reload the main batch window to see the new batch
                store.reload({
                    // to see the new batch, we have to reload the 'Open Batches' with CreateDate in desc order
                    params: { showBatchStatus: 'open', start: 0, limit: 100, sort: 'createDate', dir: 'DESC' },
                    callback: function() {
                        // TODO: highlight the background
                        
//                        var obj = Ext.decode(responseText);
//                        if (obj && obj.batchId) {
//                            var recIndex = store.indexOfId(obj.batchId);
//                            if (recIndex > -1) {
//                                var row = grid.getView().getRow(recIndex);
//                                if (row) {
//                                    Ext.fly(row).highlight("FFFF9C", {
//                                        attr: "background-color",
//                                        easing: 'easeOut',
//                                        duration: 3
//                                    });
//                                }
//                            }
//                        }
                    }
                });
                cancelBatch(); // tell the server side to end the flow
                hideOtherMarkers('savedMarker');
                $("#savedMarker").show();
                setTimeout(function() {
                    $("#savedMarker").hide();
                }, 10000);
            },
            failure: function(response, options) {
                Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                    buttons: Ext.MessageBox.OK,
                    msg: msgs.errorSave });
            }
        });
    }

    function cancelBatch() {
        Ext.Ajax.request({
            url: 'doBatch.htm',
            method: 'POST',
            params: { '_eventId_cancel': 'cancel', 'execution': getFlowExecutionKey() } 
        });
        batchWin.hide(batchWin);
    }

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
                     }],
                     listeners: {
                         'afterrender': maskStep1Form
                     }
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
    };
    batchWin.on('beforeshow', function() {
        $(window).bind('keydown', function(e) {
            hideOnEscape(e);
        });
    });
    batchWin.on('beforehide', function() {
        resetBatchWin();
        $(window).unbind('keydown', hideOnEscape);
    });

    function resetBatchWin() {
        // When the batchWin is hidden, clear out all set store objects, reset CSS classes, etc, for re-use for another batch
        flowExecutionKey = null; // on hide, clear out the flow execution key in order to start a new flow next time
        batchWin.groupTabPanel.items.items[0].activeTab = null;
        batchWin.groupTabPanel.strip.select('li.x-grouptabs-strip-active', true).removeClass('x-grouptabs-strip-active');
        batchWin.groupTabPanel.activeGroup = null;
        $('#step1Num').removeClass('complete');
        Ext.getCmp('batchDesc').setRawValue(''); // reset the batchDesc to empty string; use 'setRawValue()' to bypass form validation
        Ext.getCmp('batchType').setRawValue('gift'); // reset the batchDesc to 'gift'; use 'setRawValue()' to bypass form validation
        resetSteps2Thru5();
    }

    function resetSteps2Thru5() {
        pickedSegmentationsCount = 0;
        $('#step2Num').removeClass('complete');
        $('#step3Num').removeClass('complete');
        $('#step4Num').removeClass('complete');
        $('#step5Num').removeClass('complete');
        step2Store.removeAll();
        step3Store.removeAll();
        step4UpdatableFieldsStore.removeAll();
        step4Form.store.removeAll();
        step5Store.removeAll();
    }
});


