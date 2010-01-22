Ext.WindowMgr.zseed = 9015; // use zseed so that the msgBox will appear above the batchWin instead of underneath

Ext.ns('OrangeLeap', 'OrangeLeap.msgBundle');
OrangeLeap.ListStore = Ext.extend(Ext.data.JsonStore, {
    sort: function(fieldName, dir) {
        if (this.lastOptions) {
            this.lastOptions.params['start'] = 0;
            this.lastOptions.params['limit'] = Ext.isNumber(this.lastOptions.params['limit']) ? this.lastOptions.params['limit'] : 100;
        }
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
    cannotDeleteExecutingBatch: 'You cannot delete a batch whose execution is currently in progress.',
    cannotExecuteExecutedBatch: 'You cannot execute an already executed batch.',
    cannotExecuteExecutingBatch: 'You cannot execute a batch whose execution is currently in progress.',
    cannotExecuteBatchCorrectErrors: 'This batch cannot be executed until the following errors are corrected: ',
    batchExecutionInProgressView: 'This batch is currently executing and can be viewed after execution completes.',
    id: 'ID',
    batchId: 'Batch ID',
    batchType: 'Batch Type',
    size: 'Size',
    value: 'Value',
    description: 'Description',
    executeDate: 'Execute Date',
    creationDate: 'Creation Date',
    userId: 'User ID',
    executeBatch: 'Execute Batch',
    executionInProgress: 'Batch Execution in Progress',
    removeBatch: 'Remove Batch',
    executed: 'Executed',
    batchList: 'Batch List <span id="savedMarker" class="marker">Saved</span> <span id="executedMarker" class="marker"></span> <span id="deletedMarker" class="marker"></span>',
    savedBatchId: 'Saved Batch ID {0}',
    executedBatchId: 'Executed Batch ID {0}',
    deletedBatchId: 'Deleted Batch ID {0}',
    addNewBatch: 'Add a new Batch',
    manageBatch: 'Manage Batch',
    chooseSegmentations: 'Choose Segmentations',
    count: 'Count',
    lastExecDt: 'Last Execution Date',
    lastExecBy: 'Last Executed By',
    error: 'Error',
    info: 'Info',
    criteriaFields: 'Criteria Fields',
    touchPointFields: 'Touch Point Fields',
    batchTypeFields: 'Fields of Selected Batch Type',
    entryType: 'Entry Type',
    mail: 'Mail',
    mailingLabel: 'Mailing Label',
    email: 'Email',
    call: 'Call',
    meeting: 'Meeting',
    note: 'Note',
    task: 'Task',
    other: 'Other',
    template: 'Template',
    recordDate: 'Record Date',
    assignedTo: 'Assigned To',
	comments: 'Comments',
	correspondenceFor: 'Correspondence For',
	primary: 'Primary',
	all: 'All',
	noteType: 'Note Type',
	event: 'Event',
	eventParticipation: 'Event Participation',
	eventDate: 'Event Date',
	eventLocation: 'Event Location',
    mustDoStep1: 'You must choose a Batch Type and Criteria Fields (Step 1) first.',
    mustDoStep2: 'You must pick Segmentations (Step 2) first.',
    noUpdatableRows: 'There are no updatable rows based on the Segmentation you chose.  Choose a different segmentation (Step 2).',
    mustDoStep3: 'You must complete Step 3 first.',
    mustDoStep4TouchPointFields: 'You must create Touch Points (Step 4) first.',
    mustDoStep4FieldUpdateCriteria: 'You must create Field Update Criteria (Step 4) first.',
    loading: 'Loading...',
    loadingSegmentations: 'Loading Segmentations...',
    loadingRows: 'Loading Rows...',
    deletingBatch: 'Deleting Batch...',
    executingBatch: 'Executing Batch...',
    followingBeModified: 'For your reference, the following rows will be modified. Click \'Next\' to continue or \'Prev\' to change segmentations',
    followingHaveTouchPoints: 'The following constituents will have touch points applied to them. Click \'Next\' to continue or \'Prev\' to change segmentations',
    followingChangesApplied: 'The following changes will be applied when you execute the batch.',
    noSegmentationsFound: 'No Segmentations were found for the Type selected.  Please choose a different Type (Step 1).',
    noRowsFound: 'No rows were found for the Segmentations selected.  Please choose a different Segmentation (Step 2).',
    noFieldUpdates: 'You did not create any Field Update Criteria.  Please create Criteria first (Step 4).',
    noTouchPointFields: 'You did not create any Touch Point Fields.  Please create Fields first (Step 4).',
    createFieldUpdateCriteria: 'Create Field Update Criteria',
    createTouchPointFields: 'Create Touch Point Fields',
    step1Title: '<span class="step"><span class="stepNum" id="step1Num">1</span><span class="stepTxt">Choose Batch Type</span>',
    step2Title: '<span class="step"><span class="stepNum" id="step2Num">2</span><span class="stepTxt">Choose Segmentations</span>',
    step3Title: '<span class="step"><span class="stepNum" id="step3Num">3</span><span class="stepTxt">View Rows To Be Updated</span>',
    step4Title: '<span class="step"><span class="stepNum" id="step4Num">4</span><span class="stepTxt">' + this.createFieldUpdateCriteria + '</span>',
    step5Title: '<span class="step"><span class="stepNum" id="step5Num">5</span><span class="stepTxt">Confirm Changes</span>',
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
    errorStepLoad: 'The requested step could not be loaded due to an error.  Please try again or contact your administrator if this issue continues.',
    batchExecutedWithErrorBatchCreated: 'The batch execution completed but an Error Batch with ID <strong>{0}</strong> ' +
                                        'was created for rows that were not able to be executed due to errors. ' +
                                        'Select "Show Batches With Errors" to edit criteria and re-execute.',

    reviewBatch: 'Review Batch',
    reviewStep1Title: '<span class="step"><span class="stepNum complete" id="reviewStep1">1</span><span class="stepTxt">View Batch Type</span>',
    reviewStep2Title: '<span class="step"><span class="stepNum complete" id="reviewStep2">2</span><span class="stepTxt">View Field Update Criteria</span>',
    reviewStep3Title: '<span class="step"><span class="stepNum complete" id="reviewStep3">3</span><span class="stepTxt">View Updated Rows</span>',
    followingRowsModified: '<div style="text-align: center" id="reviewStep3Header"><div>The following rows were modified by this batch.</div>' + 
                           '<div>Values displayed in the grid may not necessarily reflect the current values</div>' +
                           '<div>Double-click a row to display in a new window</div></div>',
    noRowsUpdated: 'No rows were updated as part of this batch.',

    rowsExecutedErrors: 'The errors listed occurred during the previous execution of the batch',
    noRowsForErrorBatch: 'No rows were found for this batch.',
    step1ErrorTitle: '<span class="step"><span class="stepNum complete" id="errorStep1">1</span><span class="stepTxt">Enter Batch Description</span>',
    step2ErrorTitle: '<span class="step"><span class="stepNum complete" id="errorStep2">2</span><span class="stepTxt">View Rows To Be Updated and Errors</span>',
    step3ErrorTitle: '<span class="step"><span class="stepNum complete" id="errorStep3">3</span><span class="stepTxt">Edit Field Update Criteria</span>',
    step4ErrorTitle: '<span class="step"><span class="stepNum complete" id="errorStep4">4</span><span class="stepTxt">Confirm Changes</span>',
    mustDoStep3Error: 'You must create Field Update Criteria (Step 3) first.'
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
                    // Do not have a displayed column for 'executed' or 'currentlyExecuting'
                    if (name != 'executed' && name != 'currentlyExecuting') {
                        var hdr = fields[x].header;
                        cols[cols.length] = {
                            header: hdr, dataIndex: name, sortable: true,
                            renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                                return determineRenderer(this, value, metaData, record);
                            }
                        };
                    }
                }
                var showBatchStatus = combo.getValue();
                if (showBatchStatus !== 'executed' && (OrangeLeap.allowExecute || OrangeLeap.allowDelete)) {
                    /* Add the 'actions' column for not executed batches */
                    cols[cols.length] = { header: ' ', width: 50, menuDisabled: true, fixed: false, hideable: false, css: 'cursor:default;', id: 'actions',
                        renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                            var html = '';
                            if (record.get('currentlyExecuting')) {
                                html += "<center><img src='images/icons/lock.png' style='width: 16px; height: 16px; border: 0' title='" + msgs.executionInProgress + "' alt='" + msgs.executionInProgress + "'/></center>";
                            }
                            else if ( ! record.get('executed')) {
                                // Since IE7 does not support inline-block, wrap a table around the <a> links
                                if ((OrangeLeap.allowExecute || OrangeLeap.allowDelete) && Ext.isIE7) {
                                    html += '<table><tr>';
                                }
                                if (OrangeLeap.allowExecute) {
                                    if (Ext.isIE7) {
                                        html += '<td>';
                                    }
                                    html += '<a href="javascript:void(0)" class="executeLink" id="execute-link-' + record.id +
                                               '" ext:qwidth="150" ext:qtip="' + msgs.executeBatch + '">' + msgs.executeBatch + '</a>';
                                    if ( ! Ext.isIE7) {
                                        html += '&nbsp;';
                                    }
                                    if (Ext.isIE7) {
                                        html += '</td>';
                                    }
                                }
                                if (OrangeLeap.allowDelete) {
                                    if (Ext.isIE7) {
                                        html += '<td>';
                                    }
                                    html += '<a href="javascript:void(0)" class="deleteLink" id="delete-link-' + record.id +
                                            '" ext:qwidth="150" ext:qtip="' + msgs.removeBatch + '">' + msgs.removeBatch + '</a>';
                                    if (Ext.isIE7) {
                                        html += '</td>';
                                    }
                                }
                                if ((OrangeLeap.allowExecute || OrangeLeap.allowDelete) && Ext.isIE7) {
                                    html += '</tr></table>';
                                }
                            }
                            return html;
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
        Ext.state.Manager.set('showBatchStatus', showBatchStatusVal);
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
        sm: new Ext.grid.RowSelectionModel({ singleSelect: true }),
        viewConfig: { autoFill: true },
        height: 600,
        width: 780,
        frame: true,
        header: true,
        title: msgs.batchList,
        loadMask: true,
        listeners: {
            rowdblclick: function(grid, rowIndex, evt) {
                removeGridRowHighlighting();
                grid.getView().onRowSelect(rowIndex);
                if (OrangeLeap.allowCreate) {
                    var rec = grid.getSelectionModel().getSelected();
                    if (rec.get('executed')) {
                        reviewBatchId = rec.get('id');
                        showModal(reviewBatchWin);
                    }
                    else if (rec.get('currentlyExecuting')) {
                        Ext.MessageBox.show({ title: msgs.info, icon: Ext.MessageBox.INFO,
                            buttons: Ext.MessageBox.OK, msg: msgs.batchExecutionInProgressView });
                    }
                    else {
                        if (combo.getValue() == 'errors') {
                            errorBatchId = rec.get('id');
                            showModal(errorBatchWin);
                        }
                        else {
                            editBatchWin.batchID = rec.get('id');
                            editBatchWin.forTouchPoints = rec.get('forTouchPoints');
                            showModal(editBatchWin);
                        }
                    }
                }
            },
            click: function(event) {
                hideMarkers();
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

    var prevShowBatchStatus = Ext.state.Manager.get('showBatchStatus');
    if ( ! prevShowBatchStatus) {
        prevShowBatchStatus = 'open';
    }

    if (OrangeLeap.allowCreate) {
        var toolbar = grid.getTopToolbar();
        toolbar.addSpacer();
        toolbar.addSeparator();
        toolbar.addSpacer();
        toolbar.addButton({ text: msgs.addNew, tooltip: msgs.addNewBatch,
            iconCls:'add', id: 'addButton', ref: '../addButton', disabled: prevShowBatchStatus != 'open',
            handler: function() {
                editBatchWin.batchID = null;
                editBatchWin.forTouchPoints = false;
                showModal(editBatchWin);
            }
        });
    }

    var sortDir = 'DESC';
    var sortProp = prevShowBatchStatus == 'executed' ? 'executedDate' : 'createDate';
    
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

    store.load( { params: { showBatchStatus: prevShowBatchStatus, start: pageStart, limit: pageLimit, sort: sortProp, dir: sortDir },
        callback: function(rec, options, success) {
            combo.setValue(prevShowBatchStatus);
            var thisView = grid.getView();
            if (thisView.prevScrollState) {
                thisView.restoreScroll(thisView.prevScrollState);
            }
        }
    });

    function executeBatch(target) {
        if (OrangeLeap.allowExecute) {
            var targetBatchId = target.id;
            if (targetBatchId) {
                targetBatchId = targetBatchId.replace('execute-link-', '');
                var rec = store.getById(targetBatchId);
                // Disallow executes of already executed batches
                if (rec.get('executed')) {
                    Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                        buttons: Ext.MessageBox.OK,
                        msg: msgs.cannotExecuteExecutedBatch });
                }
                else if (rec.get('currentlyExecuting')) {
                    Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                        buttons: Ext.MessageBox.OK,
                        msg: msgs.cannotExecuteExecutingBatch});
                }
                else {
                    Ext.Msg.show({
                        title: msgs.askExecuteBatch,
                        msg: String.format(msgs.areYouSureExecuteBatch, targetBatchId),
                        buttons: Ext.Msg.OKCANCEL,
                        icon: Ext.MessageBox.WARNING,
                        fn: function(btn, text) {
                            if (btn == "ok") {
                                var recToExecute = store.getById(targetBatchId);
                                if (recToExecute) {
                                    Ext.get('batchList').mask(msgs.executingBatch);

                                    Ext.Ajax.request({
                                        url: 'executeBatch.json',
                                        method: 'POST',
                                        params: { 'batchId': targetBatchId },
                                        timeout: 14400000, // timeout to execute batch is 4 hours (4h * 60m * 60s * 1000ms) 
                                        success: function(response, options) {
                                            Ext.get('batchList').unmask();
                                            var returnObj = Ext.decode(response.responseText);
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
                                                        if (combo.getValue() == 'errors') {
                                                            // if on the errors view, reload the store to show the created error batch
                                                            store.reload();
                                                        }
                                                    }
                                                    else {
                                                        showExecutedMarker(targetBatchId);
                                                    }
                                                }
                                            }
                                        },
                                        failure: function(response, options) {
                                            Ext.get('batchList').unmask();
                                            Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                                                buttons: Ext.MessageBox.OK,
                                                msg: msgs.errorBatchExecute });
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    function showExecutedMarker(batchId) {
        if ( ! batchId || ! Ext.isNumber(batchId)) {
            $('#executedMarker').text(msgs.executed);
        }
        else {
            $('#executedMarker').text(String.format(msgs.executedBatchId, batchId));
        }
        $('#executedMarker').show();
        setTimeout(function() {
            $("#executedMarker").hide();
        }, 10000);
    }

    function hideMarkers() {
        $('#executedMarker').hide();
        $('#savedMarker').hide();
        $('#deletedMarker').hide();
    }

    function deleteBatch(target) {
        if (OrangeLeap.allowDelete) {
            var targetBatchId = target.id;
            if (targetBatchId) {
                targetBatchId = targetBatchId.replace('delete-link-', '');
                var rec = store.getById(targetBatchId);
                // Disallow deletes of executed batches
                if (rec.get('executed')) {
                    Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                        buttons: Ext.MessageBox.OK,
                        msg: msgs.cannotDeleteExecutedBatch });
                }
                else if (rec.get('currentlyExecuting')) {
                    Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                        buttons: Ext.MessageBox.OK,
                        msg: msgs.cannotDeleteExecutingBatch});
                }
                else {
                    Ext.Msg.show({
                        title: msgs.askDeleteBatch,
                        msg: String.format(msgs.areYouSureDeleteBatch, targetBatchId),
                        buttons: Ext.Msg.OKCANCEL,
                        icon: Ext.MessageBox.WARNING,
                        fn: function(btn, text) {
                            if (btn == "ok") {
                                Ext.get('batchList').mask(msgs.deletingBatch);
                                Ext.Ajax.request({
                                    url: 'deleteBatch.json',
                                    method: 'POST',
                                    params: { 'batchId': targetBatchId },
                                    success: function(response, options) {
                                        store.remove(rec);
                                        Ext.get('batchList').unmask();
                                        $('#deletedMarker').text(String.format(msgs.deletedBatchId, targetBatchId));
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

    function removeGridRowHighlighting() {
        $('#batchList .' + grid.getView().selectedRowClass).each(function() {
            $(this).removeClass(grid.getView().selectedRowClass); // remove previous row highlighting
        });
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* The following are the for the create/edit batch modal window */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    function showModal(win) {
        if (OrangeLeap.allowCreate) {
            if (win.rendered) {
                // If the modal has already been rendered, make sure step1 is the first one shown
                var panel = win.groupTabPanel;
                panel.setActiveGroup(0);
                var firstItem = panel.items.items[0];
                firstItem.setActiveTab(firstItem.items.items[0]);
            }
            win.show(win);
        }
    }

    // custom toolbar for Edit Batch window to invoke loadTab - this effectively overrides what the 'refresh' button action is on the toolbar
    OrangeLeap.EditBatchWinToolbar = Ext.extend(Ext.PagingToolbar, {
        doLoad: function(start) {
            var o = { }, pn = this.getParams();
            o[pn.start] = start;
            o[pn.limit] = this.pageSize;
            if (this.fireEvent('beforechange', this, o) !== false) {
                loadTab(editBatchWin.groupTabPanel, editBatchWin.groupTabPanel.activeGroup, editBatchWin.groupTabPanel.activeGroup, start);
            }
        }
    });

    var flowExecutionKey = null;
    var accessibleSteps = ['step1Grp'];

    /* Following code to the end is for the edit/add batch modal */
    function determineRenderer(obj, value, metaData, record) {
		var hdr = '';
		if (obj.dataIndex && record.fields.map && record.fields.map[obj.dataIndex]) {
			if (record.fields.map[obj.dataIndex].header) {
				hdr = record.fields.map[obj.dataIndex].header;
			}
			if (record.fields.map[obj.dataIndex].type == 'boolean') {
				return OrangeLeap.booleanRenderer(value, metaData, record);
			}
		}
		return '<span ext:qtitle="' + hdr + '"ext:qwidth="250" ext:qtip="' + value + '">' + value + '</span>';
    }

    function checkFieldCriteriaValid(form) {
        var thisStore = form.store;
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

    function showErrorMsg(errorMsg) {
        Ext.MessageBox.show.defer(1, Ext.MessageBox,
            [{ title: msgs.error, icon: Ext.MessageBox.ERROR,
            buttons: Ext.MessageBox.OK,
            msg: errorMsg}]); // use defer so that the msgBox will appear above the batchWin
    }

    function invalidateAccessibleSteps(invalidStepName) {
        function deleteStep(stepName) {
            var index = accessibleSteps.indexOf(stepName);
            if (index > -1) {
                delete accessibleSteps[index];
            }
        }
        if (invalidStepName == 'step1Grp') {
            deleteStep('step2Grp');
        }
        if (invalidStepName == 'step1Grp' || invalidStepName == 'step2Grp') {
            deleteStep('step3Grp');
        }
        if (invalidStepName == 'step1Grp' || invalidStepName == 'step2Grp' || invalidStepName == 'step3Grp') {
            deleteStep('step4Grp');
        }
        if (invalidStepName == 'step1Grp' || invalidStepName == 'step2Grp' || invalidStepName == 'step3Grp' || invalidStepName == 'step4Grp') {
            deleteStep('step5Grp');
        }
    }

    function checkConditions(groupTabPanel, groupToShow, currentGroup) {
        var isValid = true;
        
        // First check if the current group's conditions have been fulfilled
        if (currentGroup && currentGroup.mainItem.id &&
            groupToShow && groupToShow.mainItem.id &&
            groupToShow.mainItem.id != currentGroup.mainItem.id &&
            accessibleSteps.indexOf(groupToShow.mainItem.id) == -1) {
            if (currentGroup.mainItem.id == 'step1Grp') {
                var batchType = getBatchTypeValue();
                var criteriaFieldsVal = Ext.getCmp('criteriaFields').getValue();
                if ( ! batchType || Ext.isEmpty(batchType) || ! criteriaFieldsVal || Ext.isEmpty(criteriaFieldsVal)) {
                    isValid = false;
                    step1Form.nextButton.disable();
                    invalidateAccessibleSteps('step1Grp');
                    showErrorMsg(msgs.mustDoStep1);
                }
                else {
                    step1Form.nextButton.enable();
                }
            }
            else if (currentGroup.mainItem.id == 'step2Grp') {
                if ( ! hasPickedRows()) {
                    isValid = false;
                    invalidateAccessibleSteps('step2Grp');
                    showErrorMsg(msgs.mustDoStep2);
                }
                else if (accessibleSteps.indexOf('step3Grp') == -1) {
                    accessibleSteps[accessibleSteps.length] = 'step3Grp';
                }
            }
            else if (currentGroup.mainItem.id == 'step3Grp') {
                if (step3Store.getCount() == 0) {
                    isValid = false;
                    invalidateAccessibleSteps('step3Grp');
                    showErrorMsg(msgs.noUpdatableRows);
                }
                else if (accessibleSteps.indexOf('step4Grp') == -1) {
                    accessibleSteps[accessibleSteps.length] = 'step4Grp';
                }
            }
            else if (currentGroup.mainItem.id == 'step4Grp') {
                if (! isForTouchPoints()) {
	                if (step4UpdatableFieldsForm.store.getCount() == 0 || ! checkFieldCriteriaValid(step4UpdatableFieldsForm)) {
						isValid = false;
						invalidateAccessibleSteps('step4Grp');
						showErrorMsg(msgs.mustDoStep4FieldUpdateCriteria);
					}
					else if (accessibleSteps.indexOf('step5Grp') == -1) {
						accessibleSteps[accessibleSteps.length] = 'step5Grp';
					}
				}
                else if (Ext.isEmpty(Ext.getCmp('entryType').getValue())) {
                    isValid = false;
                    invalidateAccessibleSteps('step4Grp');
                    showErrorMsg(msgs.mustDoStep4TouchPointFields);
                }
            }
        }
		if (isValid && currentGroup && currentGroup.mainItem.id) {
            if (currentGroup.mainItem.id == 'step1Grp') {
                if (isBatchTypeChanged) {
                    isBatchTypeChanged = false;
					resetEditSteps2Thru5(); // If the new batch type is different from the old batch type, they have to repeat steps 2-5 again
                }
                if (isFieldCriteriaDifferent) {
                    isFieldCriteriaDifferent = false;
					resetEditSteps3Thru5();
                }
				if ( ! isForTouchPoints()) {
					setupEditWizardForFieldCriteria();
				}
				else {
					setupEditWizardForTouchPoints();

					// For touch points, not allowed to go to Step 5
					if (groupToShow && groupToShow.mainItem.id &&
						groupToShow.mainItem.id == 'step5Grp') {
						isValid = false;						
					}
				}
				if (accessibleSteps.indexOf('step2Grp') == -1) {
					accessibleSteps[accessibleSteps.length] = 'step2Grp';
				}
			}
		}

        // Then check if all previous conditions for the group to show have been fulfilled
        if (isValid) {
            if (accessibleSteps.indexOf(groupToShow.mainItem.id) == -1) { // the step requested (e.g. step 5) is not available, so find which step (e.g. step 2) is missing
                isValid = false;
                if (accessibleSteps.indexOf('step4Grp') > -1) {
                    if (isForTouchPoints()) {
                        showErrorMsg(msgs.mustDoStep4TouchPointFields);
                    }
                    else {
	                    showErrorMsg(msgs.mustDoStep4FieldUpdateCriteria);
                    }
                }
                else if (accessibleSteps.indexOf('step3Grp') > -1) {
                    showErrorMsg(msgs.mustDoStep3);
                }
                else if (accessibleSteps.indexOf('step2Grp') > -1) {
                    showErrorMsg(msgs.mustDoStep2);
                }
                else if (accessibleSteps.indexOf('step1Grp') > -1) {
                    showErrorMsg(msgs.mustDoStep1);
                }
            }
        }
        return isValid;
    }

    function setupEditWizardForTouchPoints() {
		$('#step3MsgSpan').text(msgs.followingHaveTouchPoints);
		$('#step4Num').next('.stepTxt').text(msgs.createTouchPointFields);
		$('#step5Num').parents('.x-grouptabs-main').addClass('noDisplay');
    }

    function setupEditWizardForFieldCriteria() {
		$('#step3MsgSpan').text(msgs.followingBeModified);
		$('#step4Num').next('.stepTxt').text(msgs.createFieldUpdateCriteria);
		$('#step5Num').parents('.x-grouptabs-main').removeClass('noDisplay');
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

    function isForTouchPoints() {
        return Ext.getCmp('criteriaFields').getValue() == 'touchPoint';
    }

    function findStepId(stepName) {
        // 0 -> step 2
        // 1 -> step 3
        // 2 -> step 4
        // 3 -> step 5
        var stepNum = parseInt(stepName.replace(/step/gi, '').replace(/Grp/gi, ''), 10) - 2;
        return $( $('#step1Grp').parent('div').siblings().get(stepNum) ).attr('id');
    }

    function maskStep(stepName, maskText) {
        var id = findStepId(stepName);
        if (id) {
            Ext.get(id).mask(maskText);
        }
    }

    function unmaskStep(stepName) {
        var id = findStepId(stepName);
        if (id) {
            Ext.get(id).unmask();
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

    function initTabSaveParams(groupTabPanel, newGroup, currentGroup) {
        var saveParams = {};
        if (currentGroup) {
            if (currentGroup.mainItem.id == 'step1Grp') {
                saveParams = { 'batchType': getBatchTypeValue(), 'batchDesc': Ext.getCmp('batchDesc').getValue(),
                    'criteriaFields': Ext.getCmp('criteriaFields').getValue(), 'previousStep': 'step1Grp' };
                $('#step1Num').addClass('complete');
            }
            else if (currentGroup.mainItem.id == 'step2Grp') {
                var theseSegs = findPickedSegmentations();
                saveParams = { 'pickedIds': theseSegs.pickedIds.toString(), 'notPickedIds': theseSegs.notPickedIds.toString(), 'previousStep': 'step2Grp' };
                $('#step2Num').addClass('complete');
            }
            else if (currentGroup.mainItem.id == 'step3Grp') {
                // nothing to save for step3 - view only
                saveParams = { 'previousStep': 'step3Grp' };
                $('#step3Num').addClass('complete');
            }
            else if (currentGroup.mainItem.id == 'step4Grp') {
                if ( ! isForTouchPoints()) {
                    findUpdateFields(step4UpdatableFieldsForm.store, saveParams);
                }
                else {
                    findTouchPointFields(step4TouchPointFieldsForm.getForm(), saveParams);
                }
                saveParams['previousStep'] = 'step4Grp';
                $('#step4Num').addClass('complete');
            }
            else if (currentGroup.mainItem.id == 'step5Grp') {
                // nothing to save for step5 - view only
                saveParams = { 'previousStep': 'step5Grp' };
                $('#step5Num').addClass('complete');
            }
        }
        return saveParams;
    }

    function findUpdateFields(store, saveParams) {
        var dataItems = store.data.items;
        for (var x = 0; x < dataItems.length; x++) {
            var value = dataItems[x].data.value;
            if (Ext.isDate(value)) {
                value = value.dateFormat('Y-m-d H:i:s');
            }
            saveParams['param-' + dataItems[x].data.name] = value;
        }
    }

    function findTouchPointFields(form, saveParams) {
		var obj = form.getFieldValues();
		if (obj) {
			for (var key in obj) {
				var val = obj[key];
				if (Ext.isArray(val)) {
					val = val.join(); 
				}
				else if (form.items.map[key].xtype == 'datefield' && ! Ext.isEmpty(val)) {
					val = new Date(val).format('m-d-Y'); // format the date value into MM/dd/yyyy format
				}
				saveParams['param-' + key] = val;
			}
		}
    }

    function loadTab(groupTabPanel, newGroup, currentGroup, startNum) {
        if ( ! startNum) {
            startNum = 0;
        }
        var saveParams = initTabSaveParams(groupTabPanel, newGroup, currentGroup);
        var params = {};
        if (newGroup.mainItem.id == 'step1Grp') {
            editBatchWin.setTitle(msgs.manageBatch + ": " + msgs.step1Tip);
            maskStep1Form();

            var step1LoadParams = { 'batchId': editBatchWin.batchID, '_eventId_step1': 'step1', 'execution': getFlowExecutionKey() };
            jQuery.extend(params, step1LoadParams, saveParams); // copy properties from step1LoadParams & saveParams to params
            
            step1Form.getForm().load({
                'url': 'doBatch.htm',
                'params': params,
                'success': function(form, action) {
                    flowExecutionKey = action.result.flowExecutionKey;
                    accessibleSteps = action.result.accessibleSteps;
                    unmaskStep1Form();
                    setTimeout(function() {
                        var elem = Ext.getCmp('batchDesc');
                        if (elem && elem.el) {
                            elem.el.focus();
                        }
                    }, 900);
                },
                'failure': function(form, action) {
                    unmaskStep1Form();
                    Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                        buttons: Ext.MessageBox.OK,
                        msg: msgs.errorStep1 });
                }
            });
        }
        else if (newGroup.mainItem.id == 'step2Grp') {
            var step2LoadParams = { '_eventId_step2': 'step2', 'execution': getFlowExecutionKey(), 'start': startNum, 'limit': step2PageSize, 'sort': 'lastDt', 'dir': 'DESC' };
            jQuery.extend(params, step2LoadParams, saveParams); // copy properties from step2LoadParams & saveParams to params

            step2Store.load({ 'params': params });
            editBatchWin.setTitle(msgs.manageBatch + ": " + msgs.step2Tip);
        }
        else if (newGroup.mainItem.id == 'step3Grp') {
            var step3LoadParams = { '_eventId_step3': 'step3', 'execution': getFlowExecutionKey(), 'start': startNum, 'limit': step3Bar.pageSize };
            jQuery.extend(params, step3LoadParams, saveParams); // copy properties from step3LoadParams & saveParams to params

            step3Store.load({ 'params': params });
            editBatchWin.setTitle(msgs.manageBatch + ": " + msgs.step3Tip);
        }
        else if (newGroup.mainItem.id == 'step4Grp') {
            var step4LoadParams = { '_eventId_step4': 'step4', 'execution': getFlowExecutionKey() };
            jQuery.extend(params, step4LoadParams, saveParams); // copy properties from step4LoadParams & saveParams to params

			if ( ! isForTouchPoints()) {
                step4UpdatableFieldsStore.load({ 'params': params });
			}
			else {
                maskStep('step4Grp', msgs.loading);
				step4TouchPointFieldsForm.getForm().load({
					'url': 'doBatch.htm',
					'params': params,
					'success': function(form, action) {
						flowExecutionKey = action.result.flowExecutionKey;
						accessibleSteps = action.result.accessibleSteps;
		                unmaskStep('step4Grp');
						setTimeout(function() {
							OrangeLeap.extFocusFirstFormElem(step4TouchPointFieldsForm.getForm());
						}, 300);
					},
					'failure': function(form, action) {
                        unmaskStep('step4Grp');
						Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
							buttons: Ext.MessageBox.OK,
							msg: msgs.errorStep4 });
					}
				});
			}
            editBatchWin.setTitle(msgs.manageBatch + ": " + msgs.step4Tip);
        }
        else if (newGroup.mainItem.id == 'step5Grp') {
            var step5LoadParams = { '_eventId_step5': 'step5', 'execution': getFlowExecutionKey(), 'start': startNum, 'limit': step5Bar.pageSize, 'sort': 'id', 'dir': 'ASC' };
            jQuery.extend(params, step5LoadParams, saveParams); // copy properties from step5LoadParams & saveParams to params

            step5Store.load({ 'params': params });
            editBatchWin.setTitle(msgs.manageBatch + ": " + msgs.step5Tip);
        }
    }

	var isBatchTypeChanged = false;
	var isFieldCriteriaDifferent = false;

	var commonEditFormConfig = {
        baseCls: 'x-plain',
        labelAlign: 'right',
        margins: '10 0',
        ctCls: 'wizard',
        monitorValid: true,
        layoutConfig: { labelSeparator: '' },
        buttonAlign: 'center'
	};

    var step1Form = new Ext.form.FormPanel(jQuery.extend({
        formId: 'step1Form',
        buttons: [
            {
                text: msgs.next,
                cls: 'saveButton',
                ref: '../nextButton',
                formBind: true,
                disabledClass: 'disabledButton',
                disabled: true,
                handler: function(button, event) {
                    var panel = editBatchWin.groupTabPanel;
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
                    cancelEditBatch();
                }
            }
        ],
        items: [
            // Textarea for description is 1st
            {
                fieldLabel: msgs.description, name: 'batchDesc', id: 'batchDesc', xtype: 'textarea',
                maxLength: 255, height: 60, width: 500, grow: true, growMin: 60, growMax: 400,
                listeners: {
                    'focus': OrangeLeap.extElementFocus,
                    'blur': OrangeLeap.extElementBlur,
                    scope: this
                }
            },
            // Combobox for type is 2nd
            {
                fieldLabel: '<span class="required">*</span>' + msgs.batchType, name: 'batchType', id: 'batchType', xtype: 'combo',
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
                    'focus': OrangeLeap.extElementFocus,
                    'blur': OrangeLeap.extElementBlur,
                    'change': function(field, newVal, oldVal) {
                        // If the new batch type is different from the old batch type, they have to repeat steps 2-5 again
						isBatchTypeChanged = (newVal != oldVal); // TODO
                    },
                    scope: this
                }
            },
            // Combo for either Touch Point or Gift/Adjusted Gift/Constituent, etc Criteria 3rd
            {
                fieldLabel: '<span class="required">*</span>' + msgs.criteriaFields,
                name: 'criteriaFields', id: 'criteriaFields', xtype: 'combo',
                store: new Ext.data.ArrayStore({
                    fields: [
                        'value',
                        'desc'
                    ],
                    data: [['notTouchPoint', msgs.batchTypeFields], ['touchPoint', msgs.touchPointFields]]
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
                    'focus': OrangeLeap.extElementFocus,
                    'blur': OrangeLeap.extElementBlur,
                    'change': function(field, newVal, oldVal) {
                        isFieldCriteriaDifferent = (newVal != oldVal); // TODO
                    },
                    scope: this
                }
            }
        ]
    }, commonEditFormConfig));

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
                maskStep('step2Grp', msgs.loadingSegmentations);
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
                unmaskStep('step2Grp');
            },
            'exception': function(misc) {
                unmaskStep('step2Grp');
                Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                    buttons: Ext.MessageBox.OK,
                    msg: msgs.errorStep2 });
            }
        }
    });
    step2Store.proxy.on('load', function(proxy, txn, options) {
        flowExecutionKey = txn.reader.jsonData.flowExecutionKey; // update the flowExecutionKey generated by spring web flow
        pickedSegmentationsCount = txn.reader.jsonData.pickedSegmentationsCount;
        accessibleSteps = txn.reader.jsonData.accessibleSteps;
    });

    function hasPickedRows() {
        return pickedSegmentationsCount > 0;
    }

    var step2Bar = new OrangeLeap.EditBatchWinToolbar({
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
                    invalidateAccessibleSteps('step2Grp');
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
            autoFill: true,
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
                    var panel = editBatchWin.groupTabPanel;
                    if (panel.setActiveGroup(0)) {
                        var firstItem = panel.items.items[0];
                        firstItem.setActiveTab(firstItem.items.items[0]);
                    }
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
                    var panel = editBatchWin.groupTabPanel;
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
                    cancelEditBatch();
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
                                return determineRenderer(this, value, metaData, record);
                            }
                        };
                    }
                }
                step3Form.reconfigure(store, new Ext.grid.ColumnModel(cols));
                step3Bar.bindStore(store, true);
            },
            'beforeload': function(store, options) {
                maskStep('step3Grp', msgs.loadingRows);
            },
            'load': function(store, records, options) {
                if (records.length > 0) {
                    step3Form.nextButton.enable();  // Only enable next if there are rows available to select
                }
                else {
                    step3Form.nextButton.disable();
                }
                unmaskStep('step3Grp');
            },
            'exception': function(misc) {
                unmaskStep('step3Grp');
                Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                    buttons: Ext.MessageBox.OK,
                    msg: msgs.errorStep3 });
            }
        }
    });
    step3Store.proxy.on('load', function(proxy, txn, options) {
        flowExecutionKey = txn.reader.jsonData.flowExecutionKey; // update the flowExecutionKey generated by spring web flow
        accessibleSteps = txn.reader.jsonData.accessibleSteps;
    });

    var step3Bar = new OrangeLeap.EditBatchWinToolbar({
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
            '<span id=\"step3MsgSpan\">' + ( ( ! isForTouchPoints()) ? msgs.followingBeModified : msgs.followingHaveTouchPoints) + '</span>'
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
            autoFill: true,
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
                    var panel = editBatchWin.groupTabPanel;
                    if (panel.setActiveGroup(1)) {
                        var firstItem = panel.items.items[1];
                        firstItem.setActiveTab(firstItem.items.items[0]);
                    }
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
                    var panel = editBatchWin.groupTabPanel;
                    if (panel.setActiveGroup(3)) { // check that all required fields entered and activeGroup is actually set to 3
                        var firstItem = panel.items.items[3];
                        firstItem.setActiveTab(firstItem.items.items[0].items.items[getStep4TabItemNumber()]);
                        $('#step3Num').addClass('complete');
                    }
                }
            },
            {
                text: msgs.cancel,
                cls: 'button',
                ref: '../closeButton',
                handler: function(button, event) {
                    cancelEditBatch();
                }
            }
        ],
        buttonAlign: 'center',
        tbar: step3Toolbar
    });
    step3Form.on('rowdblclick', function(grid, rowIndex, event) {
        var batchTypeVal = getBatchTypeValue();
        var criteriaFieldsVal = Ext.getCmp('criteriaFields').getValue();
        var record = step3Store.getAt(rowIndex);
        if (batchTypeVal && record && criteriaFieldsVal) {
            // open window to view record
            var thisUrl;
            if ('touchPoint' == criteriaFieldsVal) {
				thisUrl = 'constituent.htm?constituentId=' + record.get('id');
            }
            else {
				thisUrl = batchTypeVal + '.htm?' + batchTypeVal + 'Id=' + record.get('id') +
									  (record.get('constituentId') ? '&constituentId=' + record.get('constituentId') : '');
            }
            window.open(thisUrl, batchTypeVal + 'Win');
        }
    });

    function getStep4TabItemNumber()  {
		return ( (! isForTouchPoints()) ? 0 : 1);
    }

	/* Common configuration for both step 4 edit forms */
    var commonStep4FormConfig = {
        buttonAlign: 'center'
    };

    function showFormItem(elem, elems, index) {
        Ext.getCmp(elem.dom.id).show();
        var $elem = $('#' + OrangeLeap.escapeIdCharacters(elem.dom.id));
        $elem.parents('div.x-form-item').removeClass('x-hide-label');
        $elem.prev('label.x-form-item-label').removeClass('x-hide-label');
    }

    function hideFormItem(elem, elems, index) {
        Ext.getCmp(elem.dom.id).hide();
        var $elem = $('#' + OrangeLeap.escapeIdCharacters(elem.dom.id));
        $elem.parents('div.x-form-item').addClass('x-hide-label');
        $elem.parents('label.x-form-item-label').addClass('x-hide-label');
    }

    var step4TouchPointFieldsForm = new Ext.form.FormPanel(jQuery.extend({
        formId: 'step4TouchPointFieldsForm',
        ctCls: 'wizard',
        buttons: [
            {
                text: msgs.previous,
                cls: 'button',
                ref: '../prevButton',
                formBind: true,
                disabledClass: 'disabledButton',
                handler: function(button, event) {
                    var panel = editBatchWin.groupTabPanel;
                    if (panel.setActiveGroup(2)) { // check that all required fields entered and activeGroup is actually set to 2
                        var firstItem = panel.items.items[2];
                        firstItem.setActiveTab(firstItem.items.items[0]);
                    }
                }
            },
            {
                text: msgs.save,
                cls: 'saveButton',
                ref: '../saveButton',
                formBind: true,
                disabledClass: 'disabledButton',
                handler: function(button, event) {
                    saveBatch('doBatch.htm', getFlowExecutionKey(), cancelEditBatch, 'open');
                }
            },
            {
                text: msgs.cancel,
                cls: 'button',
                ref: '../closeButton',
                handler: function(button, event) {
                    cancelEditBatch();
                }
            }
        ],
		items: [ // TODO: replace with dynamically created fields
            {
                fieldLabel: '<span class="required">*</span>' + msgs.entryType, name: 'entryType', id: 'entryType', xtype: 'combo',
                displayField: 'displayVal',
                valueField: 'itemName',
                typeAhead: false,
                mode: 'local',
                forceSelection: true,
                triggerAction: 'all',
                emptyText: ' ',
                selectOnFocus: true,
                minListWidth: 500,
                width: 500,
                listeners: {
                    'focus': OrangeLeap.extElementFocus,
                    'blur': OrangeLeap.extElementBlur,
                    'beforeselect': function(combo, record, index) {
                        var newVal = record.get('itemName');
                        if (newVal == 'Mail' || newVal == 'MailingLabel' || newVal == 'Email' || newVal == 'Call' || newVal == 'Other') {
                            Ext.select(".ea-template").each(showFormItem);
                        }
                        else {
                            Ext.select(".ea-template").each(hideFormItem);
                        }
                        if (newVal == 'Mail' || newVal == 'MailingLabel' || newVal == 'Email' || newVal == 'Call') {
                            Ext.select(".ea-correspondence").each(showFormItem);
                        }
                        else {
                            Ext.select(".ea-correspondence").each(hideFormItem);
                        }
                        if (newVal == 'Note') {
                            Ext.select(".ea-noteType").each(showFormItem);
                        }
                        else {
                            Ext.select(".ea-noteType").each(hideFormItem);
                        }
                        if (newVal == 'Event') {
                            Ext.select(".ea-event").each(showFormItem);
                        }
                        else {
                            Ext.select(".ea-event").each(hideFormItem);
                        }
                    },
                    scope: this
                }
            },
            {
                fieldLabel: msgs.correspondenceFor, name: 'customFieldMap[correspondenceFor]', id: 'customFieldMap[correspondenceFor]', xtype: 'combo',
                cls: 'ea-correspondence',
                store: new Ext.data.ArrayStore({
                    fields: [
                        'itemName',
                        'displayVal'
                    ],
                    data: [['primary', msgs.primary], ['all', msgs.all]]
                }),
                displayField: 'displayVal',
                valueField: 'itemName',
                typeAhead: false,
                hidden: true,
                hideParent: true,
                hideLabel: true,
                mode: 'local',
                forceSelection: true,
                triggerAction: 'all',
                emptyText: ' ',
                selectOnFocus: true,
                minListWidth: 500,
                width: 500,
                listeners: {
                    'focus': OrangeLeap.extElementFocus,
                    'blur': OrangeLeap.extElementBlur,
                    'change': function(field, newVal, oldVal) {
                    },
                    scope: this
                }
            },
            {
                fieldLabel: msgs.noteType, name: 'customFieldMap[noteType]', id: 'customFieldMap[noteType]', xtype: 'combo',
                cls: 'ea-noteType',
                displayField: 'displayVal',
                valueField: 'itemName',
                hidden: true,
                hideParent: true,
                hideLabel: true,
                typeAhead: false,
                mode: 'local',
                forceSelection: true,
                triggerAction: 'all',
                emptyText: ' ',
                selectOnFocus: true,
                minListWidth: 500,
                width: 500,
                listeners: {
                    'focus': OrangeLeap.extElementFocus,
                    'blur': OrangeLeap.extElementBlur,
                    'change': function(field, newVal, oldVal) {
                    },
                    scope: this
                }
            },
            {
                fieldLabel: msgs.event, name: 'customFieldMap[event]', id: 'customFieldMap[event]', xtype: 'combo',
                cls: 'ea-event',
                displayField: 'displayVal',
                valueField: 'itemName',
                typeAhead: false,
                mode: 'local',
                hidden: true,
                hideParent: true,
                hideLabel: true,
                forceSelection: true,
                triggerAction: 'all',
                emptyText: ' ',
                selectOnFocus: true,
                minListWidth: 500,
                width: 500,
                listeners: {
                    'focus': OrangeLeap.extElementFocus,
                    'blur': OrangeLeap.extElementBlur,
                    'change': function(field, newVal, oldVal) {
                    },
                    scope: this
                }
            },
            {
                fieldLabel: msgs.eventParticipation, name: 'customFieldMap[eventParticipation]', id: 'customFieldMap[eventParticipation]', xtype: 'combo',
                cls: 'ea-event',
                displayField: 'displayVal',
                valueField: 'itemName',
                typeAhead: false,
                mode: 'local',
                hidden: true,
                hideParent: true,
                hideLabel: true,
                forceSelection: true,
                triggerAction: 'all',
                emptyText: ' ',
                selectOnFocus: true,
                minListWidth: 500,
                width: 500,
                listeners: {
                    'focus': OrangeLeap.extElementFocus,
                    'blur': OrangeLeap.extElementBlur,
                    'change': function(field, newVal, oldVal) {
                    },
                    scope: this
                }
            },
            {
                fieldLabel: msgs.eventDate, name: 'customFieldMap[eventDate]', id: 'customFieldMap[eventDate]', xtype: 'datefield',
                cls: 'ea-event',
                hidden: true,
                hideParent: true,
                hideLabel: true,
                width: 500,
                listeners: {
                    'focus': OrangeLeap.extElementFocus,
                    'blur': OrangeLeap.extElementBlur,
                    scope: this
                }
            },
            {
                fieldLabel: msgs.eventLocation, name: 'customFieldMap[eventLocation]', id: 'customFieldMap[eventLocation]', xtype: 'textfield',
                cls: 'ea-event',
                hidden: true,
                hideParent: true,
                hideLabel: true,
                width: 500,
                listeners: {
                    'focus': OrangeLeap.extElementFocus,
                    'blur': OrangeLeap.extElementBlur,
                    scope: this
                }
            },
            {
                fieldLabel: msgs.template, name: 'customFieldMap[template]', id: 'customFieldMap[template]', xtype: 'textfield',
                hidden: true,
                hideParent: true,
                hideLabel: true,
                width: 500,
                cls: 'ea-template',
                listeners: {
                    'focus': OrangeLeap.extElementFocus,
                    'blur': OrangeLeap.extElementBlur,
                    scope: this
                }
            },
            {
                fieldLabel: msgs.recordDate, name: 'recordDate', id: 'recordDate', xtype: 'datefield',
                width: 500,
                listeners: {
                    'focus': OrangeLeap.extElementFocus,
                    'blur': OrangeLeap.extElementBlur,
                    scope: this
                }
            },
			{
				fieldLabel: msgs.assignedTo, name: 'customFieldMap[assignedTo]',
				id: 'customFieldMap[assignedTo]',
				fieldDef: 'communicationHistory.customFieldMap[assignedTo]',
				width: 498,
				xtype: 'querylookup'
			},
            {
                fieldLabel: msgs.comments, name: 'comments', id: 'comments', xtype: 'textarea',
                height: 60, width: 500, grow: true, growMin: 60, growMax: 400,
                listeners: {
                    'focus': OrangeLeap.extElementFocus,
                    'blur': OrangeLeap.extElementBlur,
                    scope: this
                }
            }
		]        
	}, commonStep4FormConfig, commonEditFormConfig));

	step4TouchPointFieldsForm.getForm().on('beforeaction', function(form, action) {
		if (action.type == Ext.form.Action.Load.prototype.type) {
			var originalHandleResponse = action.handleResponse;
			/* Override the original Ext.form.Action.Load handleResponse function to invoke loadTouchPointFormFields function first */
			action.handleResponse = function(response) {
				loadTouchPointFormFields(response);
				return originalHandleResponse.apply(step4TouchPointFieldsForm, arguments);
			};
		}
	});

	function loadTouchPointFormFields(response) {
		var obj = Ext.decode(response.responseText);
		if (obj && obj.data) {
			for (var key in obj.data) {
				var cmp = Ext.getCmp(key);
				if (cmp) {
					var picklistData = obj[key + '-Picklist'];
					if (picklistData) { // look for picklist data to update
						cmp.store = new Ext.data.JsonStore({
							'fields': [ 'displayVal', 'itemName', 'refVal' ],
							'data': picklistData
						});
					}
					var queryLookupData = obj[key + '-QueryLookup'];
					if (queryLookupData) {
						cmp.referenceType = queryLookupData.referenceType;
						cmp.displayValue = queryLookupData.displayValue; 
					}
				}
			}
		}
	}

    function loadUpdatableFields(form, picklistData, store, records, options) {
        var len = records.length;
        var newPropertyNames = {};
        var newCustomEditors = {};
        var newCustomRenderers = {};
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
                newCustomEditors[recName] = form.colModel.editors['date'];
                initValues[recName] = new Date();
            }
            else if (recType == 'checkbox') {
                newCustomEditors[recName] = form.colModel.editors['boolean'];
                initValues[recName] = true;
            }
            else if (recType == 'number' || recType == 'percentage') {
                newCustomEditors[recName] = form.colModel.editors['number'];
                initValues[recName] = parseInt(0, 10);
            }
            else if (recType == 'picklist') {    // TODO: multi_picklist, code, code_other, query_lookup, query_lookup_other
                var myPicklist = picklistData[recName + '-Picklist'];
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
                    newCustomRenderers[recName] = function(val, meta, r) {
                        var returnVal = val;
                        var aStore = this.grid.customEditors[r.get('name')].field.store;
                        var index = aStore.find('itemName', val);
                        if (index > -1) {
                            returnVal = aStore.getAt(index).get('displayVal');
                        }
                        if (Ext.isEmpty(returnVal)) {
                            // No value so highlight this cell as in an error state
                            meta.css += ' x-form-invalid';
                            meta.attr = 'ext:qtip="' + msgs.valueRequired + '"; ext:qclass="x-form-invalid-tip"';
                        }
                        else {
                            meta.css = '';
                            meta.attr = 'ext:qtip=""';
                        }
                        return Ext.util.Format.htmlEncode(returnVal);
                    };
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
        form.propertyNames = newPropertyNames;
        form.customEditors = newCustomEditors;
        form.customRenderers = newCustomRenderers;
        form.setSource(newSource);
    }

    var step4UpdatableFieldsStore = new Ext.data.JsonStore({
        url: 'doBatch.htm',
        autoLoad: false,
        autoSave: false,
        totalProperty: 'totalRows',
        root: 'rows',
        listeners: {
            'load': function(store, records, options) {
                loadUpdatableFields(step4UpdatableFieldsForm, step4Picklists, store, records, options);
                unmaskStep('step4Grp');
            },
            'beforeload': function(store, options) {
                maskStep('step4Grp', msgs.loading);
            },
            'exception': function(misc) {
                unmaskStep('step4Grp');
                Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                    buttons: Ext.MessageBox.OK,
                    msg: msgs.errorStep4 });
            }
        },
        fields: [
            {name: 'name', type: 'string'},
            {name: 'desc', type: 'string'},
            {name: 'type', type: 'string'},
            {name: 'value', type: 'string'},
            {name: 'selected', type: 'boolean'}
        ]
    });

    var step4Picklists = {};

    function proxyLoadUpdatableFields(obj, txn) {
        if (txn.reader.jsonData && txn.reader.jsonData.rows) {
            var rows = txn.reader.jsonData.rows;
            var len = rows.length;

            // Setup custom editors, if any
            for (var x = 0; x < len; x++) {
                if (rows[x].type == 'picklist') {
                    var name = rows[x].name;
                    var picklistNameKey = name + '-Picklist'; // get the JSON itemName/displayVal data for the picklist
                    if (txn.reader.jsonData[picklistNameKey]) {
                        obj[picklistNameKey] = txn.reader.jsonData[picklistNameKey];
                    }
                }
            }
        }
    }

    step4UpdatableFieldsStore.proxy.on('load', function(proxy, txn, options) {
        flowExecutionKey = txn.reader.jsonData.flowExecutionKey; // update the flowExecutionKey generated by spring web flow
        accessibleSteps = txn.reader.jsonData.accessibleSteps;
        proxyLoadUpdatableFields(step4Picklists, txn);
    });

    var step4UpdatableFieldsForm = new OrangeLeap.DynamicPropertyGrid(jQuery.extend({
        updatableFieldsStore: step4UpdatableFieldsStore,
        id: 'step4UpdatableFieldsForm',
        width: 726,
        height: 468,
        loadMask: true,
        header: false,
        frame: false,
        border: false,
        forceLayout: true,
        cls: 'grp',
        propertyNames: { },
        source: { },
        customEditors: { },
        buttons: [
            {
                text: msgs.previous,
                cls: 'button',
                ref: '../prevButton',
                formBind: true,
                disabledClass: 'disabledButton',
                handler: function(button, event) {
                    var panel = editBatchWin.groupTabPanel;
                    if (panel.setActiveGroup(2)) { // check that all required fields entered and activeGroup is actually set to 2
                        var firstItem = panel.items.items[2];
                        firstItem.setActiveTab(firstItem.items.items[0]);
                    }
                }
            },
            {
                text: msgs.next,
                cls: 'saveButton',
                ref: '../nextButton',
                formBind: true,
                disabledClass: 'disabledButton',
                handler: function(button, event) {
                    var panel = editBatchWin.groupTabPanel;
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
                    cancelEditBatch();
                }
            }
        ]
    }, commonStep4FormConfig));

    var checkStep4EnableButton = function(store) {
        if (store.getCount() == 0 || ! checkFieldCriteriaValid(step4UpdatableFieldsForm)) {
            step4UpdatableFieldsForm.nextButton.disable();
        }
        else {
            step4UpdatableFieldsForm.nextButton.enable();
        }
    };
    step4UpdatableFieldsForm.store.addListener({
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
                maskStep('step5Grp', msgs.loadingRows);
            },
            'load': function(store, records, options) {
                unmaskStep('step5Grp');
            },
            'exception': function(misc) {
                unmaskStep('step5Grp');
                Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                    buttons: Ext.MessageBox.OK,
                    msg: msgs.errorStep5 });
            },
            'metachange': function(store, meta) {
                comparisonMetaChange(step5Form, step5Bar, store, meta);
            }
        }
    });

    function comparisonMetaChange(form, bar, store, meta) {
        var cols = [];
        var fields = meta.fields;
        for (var x = 0; x < fields.length; x++) {
            var name = fields[x].name;
            if (name && name != 'constituentId' && name != 'id') {
                cols[cols.length] = {
                    header: fields[x].header, dataIndex: name, sortable: (name != 'type'),
                    renderer: function(value, metaData, record, rowIndex, colIndex, store) {
						return determineRenderer(this, value, metaData, record);
                    }
                };
            }
        }
        form.reconfigure(store, new Ext.grid.ColumnModel(cols));
        bar.bindStore(store, true);
    }

    step5Store.proxy.on('load', function(proxy, txn, options) {
        flowExecutionKey = txn.reader.jsonData.flowExecutionKey; // update the flowExecutionKey generated by spring web flow
        accessibleSteps = txn.reader.jsonData.accessibleSteps;
    });

    var step5Bar = new OrangeLeap.EditBatchWinToolbar({
        pageSize: 20,
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

    var step5RowSelect = new Ext.grid.RowSelectionModel({ listeners: {
        'beforerowselect': function(selModel, rowIndex, keepExisting, record) {
            return false;
        } }
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
        viewConfig: { autoFill: true },
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
                    var panel = editBatchWin.groupTabPanel;
                    if (panel.setActiveGroup(3)) {
                        var thisItem = panel.items.items[3];
                        thisItem.setActiveTab(thisItem.items.items[0].items.items[getStep4TabItemNumber()]);
                    }
                }
            },
            {
                text: msgs.save,
                cls: 'saveButton',
                ref: '../saveButton',
                formBind: true,
                disabledClass: 'disabledButton',
                handler: function(button, event) {
                    saveBatch('doBatch.htm', getFlowExecutionKey(), cancelEditBatch, 'open');
                }
            },
            {
                text: msgs.cancel,
                cls: 'button',
                ref: '../closeButton',
                handler: function(button, event) {
                    cancelEditBatch();
                }
            }
        ],
        buttonAlign: 'center',
        tbar: step5Toolbar
    });

    function saveBatch(saveUrl, executionKey, cancelFunc, batchStatusToShow) {
        Ext.Ajax.request({
            url: saveUrl,
            method: 'POST',
            params: { '_eventId_save': 'save', 'execution': executionKey },
            success: function(response, options) {
                var responseText = response.responseText;
                // reload the main batch window to see the new batch
                store.reload({
                    // to see the new batch, we have to reload the desired status (open or error) with CreateDate in desc order
                    params: { showBatchStatus: batchStatusToShow, start: 0, limit: 100, sort: 'createDate', dir: 'DESC' },
                    callback: function() {
                        var obj = Ext.decode(responseText);
                        if (obj && obj.batchId) {
                            removeGridRowHighlighting();
                            var recIndex = store.indexOfId(obj.batchId);
                            if (recIndex > -1) {
                                grid.getView().onRowSelect(recIndex);
                            }
                            $('#savedMarker').text(String.format(msgs.savedBatchId, obj.batchId));
                            $("#savedMarker").show();
                            setTimeout(function() {
                                $("#savedMarker").hide();
                            }, 10000);
                        }
                        else {
                            $("#savedMarker").show();
                            setTimeout(function() {
                                $("#savedMarker").hide();
                            }, 10000);
                        }
                    }
                });
                cancelFunc(); // tell the server side to end the flow
            },
            failure: function(response, options) {
                Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                    buttons: Ext.MessageBox.OK,
                    msg: msgs.errorSave });
            }
        });
    }

    function cancelEditBatch() {
        Ext.Ajax.request({
            url: 'doBatch.htm',
            method: 'POST',
            params: { '_eventId_cancel': 'cancel', 'execution': getFlowExecutionKey() } 
        });
        editBatchWin.hide(editBatchWin);
    }

    var editBatchWin = new Ext.Window({
        title: msgs.manageBatch,
        layout: 'fit',
        width: 875,
        height: 500,
        cls: 'win',
        id: 'editBatchWin',
        modal: true,
        closable: false,
        closeAction: 'hide',
        listeners: {
            'show': function(win) {
				if (win.forTouchPoints) {
                     setupEditWizardForTouchPoints();
				}
				else {
					setupEditWizardForFieldCriteria();
				}
                if (win.batchID && Ext.isNumber(win.batchID) && win.batchID > 0) {
                    $('#step1Num').addClass('complete');
                    $('#step2Num').addClass('complete');
                    $('#step3Num').addClass('complete');
                    $('#step4Num').addClass('complete');

                    if ( ! win.forTouchPoints) {
	                    $('#step5Num').addClass('complete');
                    }
                }
            },
            'beforeshow': function() {
                $('#editBatchWin').bind('keydown', function(e) {
                    hideEditOnEscape(e);
                });
            },
            'beforehide': function() {
                resetEditBatchWin();
                $('#editBatchWin').unbind('keydown', hideEditOnEscape);
            }
        },
        items:[{
             xtype: 'grouptabpanel',
             tabWidth: 135,
             activeGroup: 0,
             ref: '../groupTabPanel',
             layoutOnTabChange: true,
             listeners: {
                 'beforegroupchange': checkConditions,
                 'groupchange': loadTab
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
                         cls: 'grp',
                         title: msgs.step2Title,
                         tabTip: msgs.step2Tip,
                         items: [ step2Form ]
                     }]
                 },
                 {
                     items: [{
                         id: 'step3Grp',
                         cls: 'grp',
                         title: msgs.step3Title,
                         tabTip: msgs.step3Tip,
                         items: [ step3Form ]
                     }]
                 },
                 {
                     items: [{
                         id: 'step4Grp',
                         cls: 'grp',
                         title: msgs.step4Title,
                         tabTip: msgs.step4Tip,
                         items: [ step4UpdatableFieldsForm, step4TouchPointFieldsForm ]
                     }]
                 },
                 {
                     items: [{
                         id: 'step5Grp',
                         cls: 'grp',
                         title: msgs.step5Title,
                         tabTip: msgs.step5Tip,
                         items: [ step5Form ]
                     }]
                 }
             ]
         }]
    });

	/* onStripMouseDown function is overridden to set the correct active tab for step 4 */ 
    editBatchWin.items.items[0].onStripMouseDown = function(e) {
        if (e.button != 0) {
            return;
        }
        e.preventDefault();
        var t = this.findTargets(e);
        if (t.expand) {
            this.toggleGroup(t.el);
        }
        else if (t.item) {
            if (t.isGroup) {
                if (t.item.items.items[0].id == 'step4Grp') {
	                t.item.setActiveTab(t.item.items.items[0].items.items[getStep4TabItemNumber()]);
                }
                else {
	                t.item.setActiveTab(t.item.getMainItem());
                }
            }
            else {
                t.item.ownerCt.setActiveTab(t.item);
            }
        }
    };

    var hideEditOnEscape = function(e) {
        if (e.keyCode == 27) {
            editBatchWin.hide();
        }
    };

    function resetEditBatchWin() {
        // When the batchWin is hidden, clear out all set store objects, reset CSS classes, etc, for re-use for another batch
        flowExecutionKey = null; // on hide, clear out the flow execution key in order to start a new flow next time
        editBatchWin.groupTabPanel.items.items[0].activeTab = null;
        editBatchWin.groupTabPanel.strip.select('li.x-grouptabs-strip-active', true).removeClass('x-grouptabs-strip-active');
        editBatchWin.groupTabPanel.activeGroup = null;
        $('#step1Num').removeClass('complete');
        Ext.getCmp('batchDesc').setRawValue(''); // reset the batchDesc to empty string; use 'setRawValue()' to bypass form validation
        Ext.getCmp('batchType').setRawValue('gift'); // reset the batchDesc to 'gift'; use 'setRawValue()' to bypass form validation
        Ext.getCmp('criteriaFields').setRawValue('notTouchPoint'); // reset the criteriaFields to 'notTouchPoint'; use 'setRawValue()' to bypass form validation
        invalidateAccessibleSteps('step1Grp');
        setupEditWizardForFieldCriteria();
        resetEditSteps2Thru5();
    }

    function resetEditSteps2Thru5() {
        pickedSegmentationsCount = 0;
        $('#step2Num').removeClass('complete');
        step2Store.removeAll();
        invalidateAccessibleSteps('step1Grp');
        resetEditSteps3Thru5();
    }

    function resetEditSteps3Thru5() {
        $('#step3Num').removeClass('complete');
        $('#step4Num').removeClass('complete');
        $('#step5Num').removeClass('complete');
        step3Store.removeAll();
        step4UpdatableFieldsStore.removeAll();
        step4UpdatableFieldsForm.store.removeAll();
        step5Store.removeAll();
        invalidateAccessibleSteps('step2Grp');
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* The following below is for the read-only batch window */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    var reviewStep1Form = new Ext.form.FormPanel({
        baseCls: 'x-plain',
        labelAlign: 'right',
        margins: '10 0',
        formId: 'reviewStep1Form',
        ctCls: 'wizard',
        layoutConfig: {
            labelSeparator: ''
        },
        buttons: [
            {
                text: msgs.next,
                cls: 'saveButton',
                ref: '../nextButton',
                formBind: true,
                handler: function(button, event) {
                    var panel = reviewBatchWin.groupTabPanel;
                    if (panel.setActiveGroup(1)) {
                        var firstItem = panel.items.items[1];
                        firstItem.setActiveTab(firstItem.items.items[0]);
                    }
                }
            },
            {
                text: msgs.close,
                cls: 'button',
                ref: '../closeButton',
                handler: function(button, event) {
                    closeReviewBatch();
                }
            }
        ],
        buttonAlign: 'center',
        items: [
            {
                fieldLabel: msgs.description, name: 'reviewBatchDesc', id: 'reviewBatchDesc', xtype: 'displayfield', cls: 'displayElem',
                height: 60, width: 500
            },
            {
                fieldLabel: msgs.batchType, name: 'reviewBatchType', id: 'reviewBatchType', xtype: 'displayfield', cls: 'displayElem',
                height: 60, width: 500
            },
            {
                name: 'hiddenBatchType', id: 'hiddenBatchType', xtype: 'hidden'
            }
        ]
    });

    var reviewStep2Store = new OrangeLeap.ListStore({
        url: 'reviewBatch.htm',
        reader: new Ext.data.JsonReader(),
        root: 'rows',
        totalProperty: 'totalRows',
        remoteSort: false,
        sortInfo: {field: 'name', direction: 'ASC'},
        fields: [
            {name: 'name', mapping: 'name', type: 'string'},
            {name: 'value', mapping: 'value', type: 'auto'}
        ],
        listeners: {
            'beforeload': function(store, options) {
                Ext.get(findStep2ReviewParentId()).mask(msgs.loadingRows);
            },
            'load': function(store, records, options) {
                Ext.get(findStep2ReviewParentId()).unmask();
            },
            'exception': function(misc) {
                Ext.get(findStep2ReviewParentId()).unmask();
                Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                    buttons: Ext.MessageBox.OK,
                    msg: msgs.errorStep2 });
            }
        }
    });

    function findStep2ReviewParentId() {
        return $( $('#step1Review').parent('div').siblings().get(0) ).attr('id');        
    }

    var reviewStep2Grid = new Ext.grid.GridPanel({
        columns: [
            { header: msgs.name, dataIndex: 'name', sortable: true, width: 200 },
            { header: msgs.value, dataIndex: 'value', sortable: true, width: 520 }
        ],
        width: 726,
        height: 468,
        header: false,
        frame: false,
        border: false,
        stateId: 'reviewStep2List',
        stateEvents: ['columnmove', 'columnresize', 'sortchange', 'bodyscroll'],
        stateful: true,
        store: reviewStep2Store,
        selModel: new Ext.grid.RowSelectionModel({
            singleSelect: false,
            listeners: {
                'beforerowselect': function() {
                    return false; 
                }
            }
        }),
        buttons: [
            {
                text: msgs.previous,
                cls: 'button',
                ref: '../prevButton',
                handler: function(button, event) {
                    var panel = reviewBatchWin.groupTabPanel;
                    if (panel.setActiveGroup(0)) {
                        var thisItem = panel.items.items[0];
                        thisItem.setActiveTab(thisItem.items.items[0]);
                    }
                }
            },
            {
                text: msgs.next,
                cls: 'saveButton',
                ref: '../nextButton',
                formBind: true,
                handler: function(button, event) {
                    var panel = reviewBatchWin.groupTabPanel;
                    if (panel.setActiveGroup(2)) {
                        var firstItem = panel.items.items[2];
                        firstItem.setActiveTab(firstItem.items.items[0]);
                    }
                }
            },
            {
                text: msgs.close,
                cls: 'button',
                ref: '../closeButton',
                handler: function(button, event) {
                    closeReviewBatch();
                }
            }
        ],
        buttonAlign: 'center'
    });

    var reviewStep3Store = new OrangeLeap.ListStore({
        url: 'reviewBatch.htm',
        reader: new Ext.data.JsonReader(),
        root: 'rows',
        totalProperty: 'totalRows',
        remoteSort: true,
        sortInfo: {field: 'id', direction: 'ASC'},
        fields: [
            {name: 'id', mapping: 'id', type: 'int'}
        ],
        listeners: {
            'beforeload': function(store, options) {
                Ext.get(findStep3ReviewParentId()).mask(msgs.loadingRows);
            },
            'load': function(store, records, options) {
                Ext.get(findStep3ReviewParentId()).unmask();
            },
            'exception': function(misc) {
                Ext.get(findStep3ReviewParentId()).unmask();
                Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                    buttons: Ext.MessageBox.OK,
                    msg: msgs.errorStep3 });
            }
        }
    });

    function findStep3ReviewParentId() {
        return $( $('#step1Review').parent('div').siblings().get(1) ).attr('id');
    }

    reviewStep3Store.on('metachange', function(store, meta) {
        var cols = [];
        var fields = meta.fields;
        for (var x = 0; x < fields.length; x++) {
            var name = fields[x].name;
            if (name && name != 'constituentId') {
                cols[cols.length] = {
                    header: fields[x].header, dataIndex: name, sortable: (name != 'type'),
                    renderer: function(value, metaData, record, rowIndex, colIndex, store) {
						return determineRenderer(this, value, metaData, record);
                    }
                };
            }
        }
        reviewStep3Form.reconfigure(store, new Ext.grid.ColumnModel(cols));
        reviewStep3Bar.bindStore(store, true);
    });
    reviewStep3Store.proxy.on('load', function(proxy, txn, options) {
        reviewFlowExecutionKey = txn.reader.jsonData.flowExecutionKey; // update the flowExecutionKey generated by spring web flow
    });

    // custom toolbar for Review Batch window to invoke loadTab - this effectively overrides what the 'refresh' button action is on the toolbar
    OrangeLeap.ReviewBatchWinToolbar = Ext.extend(Ext.PagingToolbar, {
        doLoad: function(start) {
            var o = { }, pn = this.getParams();
            o[pn.start] = start;
            o[pn.limit] = this.pageSize;
            if (this.fireEvent('beforechange', this, o) !== false) {
                loadReviewTab(reviewBatchWin.groupTabPanel, reviewBatchWin.groupTabPanel.activeGroup, reviewBatchWin.groupTabPanel.activeGroup, start);
            }
        }
    });

    var reviewStep3Bar = new OrangeLeap.ReviewBatchWinToolbar({
        pageSize: 50,
        stateEvents: ['change'],
        stateId: 'reviewStep3Bar',
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
        store: reviewStep3Store,
        displayInfo: true,
        displayMsg: msgs.displayMsg,
        emptyMsg: msgs.emptyMsg
    });

    var reviewStep3Toolbar = new Ext.Toolbar({
        items: [
            msgs.followingRowsModified
        ]
    });
    reviewStep3Toolbar.on('afterlayout', function(tb){
        tb.el.child('.x-toolbar-right').remove();
        var t = tb.el.child('.x-toolbar-left');
        t.removeClass('x-toolbar-left');
        t = tb.el.child('.x-toolbar-ct');
        t.setStyle('width', 'auto');
        t.wrap({tag: 'center'});
    }, null, {single: true});

    var reviewStep3Form = new Ext.grid.GridPanel({
        stateId: 'reviewStep3List',
        stateEvents: ['columnmove', 'columnresize', 'sortchange', 'bodyscroll'],
        stateful: true,
        store: reviewStep3Store,
        bbar: reviewStep3Bar,
        stripeRows: false,
        width: 726,
        height: 468,
        loadMask: true,
        header: false,
        frame: false,
        border: false,
        selModel: new Ext.grid.RowSelectionModel(),
        viewConfig: {
            autoFill: true,
            emptyText: msgs.noRowsUpdated
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
                handler: function(button, event) {
                    var panel = reviewBatchWin.groupTabPanel;
                    if (panel.setActiveGroup(1)) {
                        var thisItem = panel.items.items[1];
                        thisItem.setActiveTab(thisItem.items.items[0]);
                    }
                }
            },
            {
                text: msgs.close,
                cls: 'button',
                ref: '../closeButton',
                handler: function(button, event) {
                    closeReviewBatch();
                }
            }
        ],
        buttonAlign: 'center',
        tbar: reviewStep3Toolbar,
        listeners: {
            'rowdblclick': function(grid, rowIndex, event) {
                var thisType = Ext.getCmp('hiddenBatchType').getValue();
                var record = reviewStep3Store.getAt(rowIndex);
                if (thisType && record) {
                    // open window to view record
                    var thisUrl = thisType + '.htm?' + thisType + 'Id=' + record.get('id') +
                                          (record.get('constituentId') ? '&constituentId=' + record.get('constituentId') : '');
                    window.open(thisUrl, 'review' + thisType + 'Win');
                }
            }
        }
    });

    var reviewBatchId = null;
    var reviewFlowExecutionKey = null;

    function closeReviewBatch() {
        Ext.Ajax.request({
            url: 'reviewBatch.htm',
            method: 'POST',
            params: { '_eventId_close': 'close', 'execution': reviewFlowExecutionKey }
        });
        reviewBatchWin.hide(reviewBatchWin);
    }

    function loadReviewTab(groupTabPanel, newGroup, currentGroup, startNum) {
        if ( ! startNum) {
            startNum = 0;
        }
        if (newGroup.mainItem.id == 'step1Review') {
            reviewBatchWin.setTitle(msgs.reviewBatch + ": " + msgs.step1Tip);
            maskReviewStep1();

            reviewStep1Form.getForm().load({
                'url': 'reviewBatch.htm',
                'params': { 'batchId': reviewBatchId, '_eventId_reviewStep1': 'reviewStep1', 'execution': reviewFlowExecutionKey },
                'success': function(form, action) {
                    reviewFlowExecutionKey = action.result.flowExecutionKey;
                    unmaskReviewStep1();
                },
                'failure': function(form, action) {
                    unmaskReviewStep1();
                    Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                        buttons: Ext.MessageBox.OK,
                        msg: msgs.errorStep1 });
                }
            });
        }
        else if (newGroup.mainItem.id == 'step2Review') {
            reviewStep2Store.load({ 'params': { '_eventId_reviewStep2': 'reviewStep2', 
                'execution': reviewFlowExecutionKey, 'sort': 'name', 'dir': 'ASC' }
            });
            reviewBatchWin.setTitle(msgs.reviewBatch + ": " + msgs.step2Tip);
        }
        else if (newGroup.mainItem.id == 'step3Review') {
            reviewStep3Store.load({ 'params': { '_eventId_reviewStep3': 'reviewStep3',
                'execution': reviewFlowExecutionKey, 'start': startNum, 'limit': reviewStep3Bar.pageSize,
                'sort': 'id', 'dir': 'ASC' }
            });
            reviewBatchWin.setTitle(msgs.reviewBatch + ": " + msgs.step3Tip);
        }
    }

    function maskReviewStep1() {
        var step1ReviewDivId = $('#step1Review').parent('div').attr('id');
        if (step1ReviewDivId) {
            Ext.get(step1ReviewDivId).mask(msgs.loading);
        }
    }

    function unmaskReviewStep1() {
        var step1ReviewDivId = $('#step1Review').parent('div').attr('id');
        if (step1ReviewDivId) {
            Ext.get(step1ReviewDivId).unmask();
        }
    }

    var reviewBatchWin = new Ext.Window({
        title: msgs.reviewBatch,
        layout: 'fit',
        width: 875,
        height: 500,
        cls: 'win',
        id: 'reviewBatchWin',
        modal: true,
        closable: false,
        closeAction: 'hide',
        listeners: {
            'beforeshow': function() {
                $('#reviewBatchWin').bind('keydown', function(e) {
                    hideReviewOnEscape(e);
                });
            },
            'beforehide': function() {
                resetReviewBatchWin();
                $('#reviewBatchWin').unbind('keydown', hideReviewOnEscape);
            }
        },
        items:[{
             xtype: 'grouptabpanel',
             tabWidth: 135,
             activeGroup: 0,
             ref: '../groupTabPanel',
             layoutOnTabChange: true,
             listeners: {
                 'groupchange': loadReviewTab
             },
             items: [
                 {
                     layoutOnTabChange: true,
                     items: [{
                         id: 'step1Review',
                         title: msgs.reviewStep1Title,
                         tabTip: msgs.step1Tip,
                         style: 'padding: 20px 40px;',
                         items: [ reviewStep1Form ]
                     }],
                     listeners: {
                         'afterrender': maskReviewStep1
                     }
                 },
                 {
                     layoutOnTabChange: true,
                     items: [{
                         id: 'step2Review',
                         cls: 'grp',
                         title: msgs.reviewStep2Title,
                         tabTip: msgs.step2Tip,
                         items: [ reviewStep2Grid ]
                     }]
                 },
                 {
                     layoutOnTabChange: true,
                     items: [{
                         id: 'step3Review',
                         cls: 'grp',
                         title: msgs.reviewStep3Title,
                         tabTip: msgs.step3Tip,
                         items: [ reviewStep3Form ]
                     }]
                 }
             ]
         }]
    });

    var hideReviewOnEscape = function(e) {
        if (e.keyCode == 27) {
            reviewBatchWin.hide();
        }
    };

    function resetReviewBatchWin() {
        // When the batchWin is hidden, clear out all set store objects, reset CSS classes, etc, for re-use for another batch
        reviewFlowExecutionKey = null; // on hide, clear out the flow execution key in order to start a new flow next time
        reviewBatchWin.groupTabPanel.items.items[0].activeTab = null;
        reviewBatchWin.groupTabPanel.strip.select('li.x-grouptabs-strip-active', true).removeClass('x-grouptabs-strip-active');
        reviewBatchWin.groupTabPanel.activeGroup = null;
        reviewStep2Store.removeAll();
        reviewStep3Store.removeAll();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* The following below is for the errors-only batch window */
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    var errorStep1Form = new Ext.form.FormPanel({
        baseCls: 'x-plain',
        labelAlign: 'right',
        margins: '10 0',
        formId: 'errorStep1Form',
        ctCls: 'wizard',
        layoutConfig: {
            labelSeparator: ''
        },
        buttons: [
            {
                text: msgs.next,
                cls: 'saveButton',
                ref: '../nextButton',
                formBind: true,
                handler: function(button, event) {
                    var panel = errorBatchWin.groupTabPanel;
                    if (panel.setActiveGroup(1)) {
                        var firstItem = panel.items.items[1];
                        firstItem.setActiveTab(firstItem.items.items[0]);
                    }
                }
            },
            {
                text: msgs.cancel,
                cls: 'button',
                ref: '../closeButton',
                handler: function(button, event) {
                    cancelErrorBatch(); 
                }
            }
        ],
        buttonAlign: 'center',
        items: [
            {
                fieldLabel: msgs.description, name: 'errorBatchDesc', id: 'errorBatchDesc', xtype: 'textarea',
                maxLength: 255, height: 60, width: 500, grow: true, growMin: 60, growMax: 400,
                listeners: {
                    'focus': OrangeLeap.extElementFocus,
                    'blur': OrangeLeap.extElementBlur,
                    scope: this
                }
            },
            {
                fieldLabel: msgs.batchType, name: 'errorBatchType', id: 'errorBatchType', xtype: 'displayfield', cls: 'displayElem',
                height: 60, width: 500
            },
            {
                name: 'hiddenErrorBatchType', id: 'hiddenErrorBatchType', xtype: 'hidden'
            }
        ]
    });

    function findStepErrorParentId(stepNum) {
        return $( $('#step1Error').parent('div').siblings().get(stepNum - 2) ).attr('id');
    }

    var errorStep2Store = new OrangeLeap.ListStore({
        url: 'errorBatch.htm',
        reader: new Ext.data.JsonReader(),
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
                var fields = meta.fields;
                for (var x = 0; x < fields.length; x++) {
                    var name = fields[x].name;
                    if (name && name.indexOf('ConstituentId') == -1) {
                        cols[cols.length] = {
                            header: fields[x].header, dataIndex: name, sortable: true,
                            renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                                return determineRenderer(this, value, metaData, record);
                            }
                        };
                    }
                }
                errorStep2Form.reconfigure(store, new Ext.grid.ColumnModel(cols));
                errorStep2Bar.bindStore(store, true);
            },
            'beforeload': function(store, options) {
                Ext.get(findStepErrorParentId(2)).mask(msgs.loadingRows);
            },
            'load': function(store, records, options) {
                if (records.length > 0) {
                    errorStep2Form.nextButton.enable();  // Only enable next if there are rows available to select
                }
                else {
                    errorStep2Form.nextButton.disable();
                }
                Ext.get(findStepErrorParentId(2)).unmask();
            },
            'exception': function(misc) {
                Ext.get(findStepErrorParentId(2)).unmask();
                Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                    buttons: Ext.MessageBox.OK,
                    msg: msgs.errorStep2 });
            }
        }
    });
    errorStep2Store.proxy.on('load', function(proxy, txn, options) {
        errorFlowExecutionKey = txn.reader.jsonData.flowExecutionKey; // update the flowExecutionKey generated by spring web flow
    });

    // custom toolbar for Error Batch window to invoke loadTab - this effectively overrides what the 'refresh' button action is on the toolbar
    OrangeLeap.ErrorBatchWinToolbar = Ext.extend(Ext.PagingToolbar, {
        doLoad: function(start) {
            var o = { }, pn = this.getParams();
            o[pn.start] = start;
            o[pn.limit] = this.pageSize;
            if (this.fireEvent('beforechange', this, o) !== false) {
                loadErrorTab(errorBatchWin.groupTabPanel, errorBatchWin.groupTabPanel.activeGroup, errorBatchWin.groupTabPanel.activeGroup, start);
            }
        }
    });

    var errorStep2Bar = new OrangeLeap.ErrorBatchWinToolbar({
        pageSize: 50,
        stateEvents: ['change'],
        stateId: 'errorStep2Bar',
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
        store: errorStep2Store,
        displayInfo: true,
        displayMsg: msgs.displayMsg,
        emptyMsg: msgs.emptyMsg
    });

    var errorStep2Toolbar = new Ext.Toolbar({
        items: [
            msgs.rowsExecutedErrors
        ]
    });
    errorStep2Toolbar.on('afterlayout', function(tb){
        tb.el.child('.x-toolbar-right').remove();
        var t = tb.el.child('.x-toolbar-left');
        t.removeClass('x-toolbar-left');
        t = tb.el.child('.x-toolbar-ct');
        t.setStyle('width', 'auto');
        t.wrap({tag: 'center'});
    }, null, {single: true});

    var errorStep2Form = new Ext.grid.GridPanel({
        stateId: 'errorStep2List',
        stateEvents: ['columnmove', 'columnresize', 'sortchange', 'bodyscroll'],
        stateful: true,
        store: errorStep2Store,
        bbar: errorStep2Bar,
        width: 726,
        height: 468,
        forceLayout: true,
        viewConfig: {
            autoFill: true,
            emptyText: msgs.noRowsForErrorBatch
        },
        header: false,
        frame: false,
        border: false,
        selModel: new Ext.grid.RowSelectionModel(),
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
                    var panel = errorBatchWin.groupTabPanel;
                    if (panel.setActiveGroup(0)) {
                        var firstItem = panel.items.items[0];
                        firstItem.setActiveTab(firstItem.items.items[0]);
                    }
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
                    var panel = errorBatchWin.groupTabPanel;
                    if (panel.setActiveGroup(2)) {
                        var firstItem = panel.items.items[2];
                        firstItem.setActiveTab(firstItem.items.items[0]);
                    }
                }
            },
            {
                text: msgs.cancel,
                cls: 'button',
                ref: '../closeButton',
                handler: function(button, event) {
                    cancelErrorBatch();
                }
            }
        ],
        buttonAlign: 'center',
        tbar: errorStep2Toolbar,
        listeners: {
            'rowdblclick': function(grid, rowIndex, event) {
                var batchType = Ext.getCmp('hiddenErrorBatchType').getValue();
                var record = errorStep2Store.getAt(rowIndex);
                if (batchType && record) {
                    // open window to view record
                    var thisUrl = batchType + '.htm?' + batchType + 'Id=' + record.get(batchType + 'Id') +
                                          (record.get(batchType + 'ConstituentId') ? '&constituentId=' + record.get(batchType + 'ConstituentId') : '');
                    window.open(thisUrl, batchType + 'Win');
                }
            }
        }
    });

    var errorStep3UpdatableFieldsStore = new Ext.data.JsonStore({
        url: 'errorBatch.htm',
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
                loadUpdatableFields(errorStep3Form, errorStep3Picklists, store, records, options);
                Ext.get(findStepErrorParentId(3)).unmask();
            },
            'beforeload': function(store, options) {
                Ext.get(findStepErrorParentId(3)).mask(msgs.loading);
            },
            'exception': function(misc) {
                Ext.get(findStepErrorParentId(3)).unmask();
                Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                    buttons: Ext.MessageBox.OK,
                    msg: msgs.errorStep3 });
            }
        }
    });
    var errorStep3Picklists = {};
    errorStep3UpdatableFieldsStore.proxy.on('load', function(proxy, txn, options) {
        errorFlowExecutionKey = txn.reader.jsonData.flowExecutionKey; // update the flowExecutionKey generated by spring web flow
        proxyLoadUpdatableFields(errorStep3Picklists, txn);
    });
    var errorStep3Form = new OrangeLeap.DynamicPropertyGrid({
        width: 726,
        height: 468,
        loadMask: true,
        header: false,
        frame: false,
        border: false,
        forceLayout: true,
        propertyNames: { },
        source: { },
        viewConfig : { autoFill: true },
        updatableFieldsStore: errorStep3UpdatableFieldsStore,
        customEditors: { },
        buttons: [
            {
                text: msgs.previous,
                cls: 'button',
                ref: '../prevButton',
                formBind: true,
                disabledClass: 'disabledButton',
                handler: function(button, event) {
                    var panel = errorBatchWin.groupTabPanel;
                    if (panel.setActiveGroup(1)) {
                        var firstItem = panel.items.items[1];
                        firstItem.setActiveTab(firstItem.items.items[0]);
                    }
                }
            },
            {
                text: msgs.next,
                cls: 'saveButton',
                ref: '../nextButton',
                formBind: true,
                disabledClass: 'disabledButton',
                handler: function(button, event) {
                    var panel = errorBatchWin.groupTabPanel;
                    if (panel.setActiveGroup(3)) {
                        var firstItem = panel.items.items[3];
                        firstItem.setActiveTab(firstItem.items.items[0].items.items[getStep4TabItemNumber()]);
                    }
                }
            },
            {
                text: msgs.cancel,
                cls: 'button',
                ref: '../closeButton',
                handler: function(button, event) {
                    cancelErrorBatch(); 
                }
            }
        ],
        buttonAlign: 'center'
    });
    var checkErrorStep3EnableButton = function(store) {
        if (store.getCount() == 0 || ! checkFieldCriteriaValid(errorStep3Form)) {
            errorStep3Form.nextButton.disable();
        }
        else {
            errorStep3Form.nextButton.enable();
        }
    };
    errorStep3Form.store.addListener({
        'add': checkErrorStep3EnableButton,
        'update': checkErrorStep3EnableButton,
        'remove': checkErrorStep3EnableButton
    });
    var errorStep4Store = new OrangeLeap.ListStore({
        url: 'errorBatch.htm',
        reader: new Ext.data.JsonReader(),
        root: 'rows',
        totalProperty: 'totalRows',
        remoteSort: true,
        sortInfo: {field: 'id', direction: 'ASC'},
        fields: [
            {name: 'id', mapping: 'id', type: 'int'}
        ],
        listeners: {
            'beforeload': function(store, options) {
                Ext.get(findStepErrorParentId(4)).mask(msgs.loadingRows);
            },
            'load': function(store, records, options) {
                Ext.get(findStepErrorParentId(4)).unmask();
            },
            'exception': function(misc) {
                Ext.get(findStepErrorParentId(4)).unmask();
                Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                    buttons: Ext.MessageBox.OK,
                    msg: msgs.errorStep4 });
            },
            'metachange': function(store, meta) {
                comparisonMetaChange(errorStep4Form, errorStep4Bar, store, meta);
            }
        }
    });
    errorStep4Store.proxy.on('load', function(proxy, txn, options) {
        errorFlowExecutionKey = txn.reader.jsonData.flowExecutionKey; // update the flowExecutionKey generated by spring web flow
    });

    var errorStep4Bar = new OrangeLeap.ErrorBatchWinToolbar({
        pageSize: 50,
        stateEvents: ['change'],
        stateId: 'errorStep4Bar',
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
        store: errorStep4Store,
        displayInfo: true,
        displayMsg: msgs.displayMsg,
        emptyMsg: msgs.emptyMsg
    });
    var errorStep4RowSelect = new Ext.grid.RowSelectionModel({ listeners: {
        'beforerowselect': function(selModel, rowIndex, keepExisting, record) {
            return false;
        } }
    });

    var errorStep4Toolbar = new Ext.Toolbar({
        items: [
            msgs.followingChangesApplied
        ]
    });
    errorStep4Toolbar.on('afterlayout', function(tb){
        tb.el.child('.x-toolbar-right').remove();
        var t = tb.el.child('.x-toolbar-left');
        t.removeClass('x-toolbar-left');
        t = tb.el.child('.x-toolbar-ct');
        t.setStyle('width', 'auto');
        t.wrap({tag: 'center'});
    }, null, {single: true});

    var errorStep4Form = new Ext.grid.GridPanel({
        stateId: 'errorStep4List',
        stateEvents: ['columnmove', 'columnresize', 'sortchange', 'bodyscroll'],
        stateful: true,
        store: errorStep4Store,
        bbar: errorStep4Bar,
        stripeRows: false,
        width: 726,
        height: 468,
        loadMask: true,
        header: false,
        frame: false,
        border: false,
        selModel: errorStep4RowSelect,
        viewConfig: { autoFill: true },
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
                    var panel = errorBatchWin.groupTabPanel;
                    if (panel.setActiveGroup(2)) {
                        var thisItem = panel.items.items[2];
                        thisItem.setActiveTab(thisItem.items.items[0]);
                    }
                }
            },
            {
                text: msgs.save,
                cls: 'saveButton',
                ref: '../saveButton',
                formBind: true,
                disabledClass: 'disabledButton',
                handler: function(button, event) {
                    saveBatch('errorBatch.htm', errorFlowExecutionKey, cancelErrorBatch, 'errors');
                }
            },
            {
                text: msgs.cancel,
                cls: 'button',
                ref: '../closeButton',
                handler: function(button, event) {
                    cancelErrorBatch(); 
                }
            }
        ],
        buttonAlign: 'center',
        tbar: errorStep4Toolbar
    });

    var errorBatchId = null;
    var errorFlowExecutionKey = null;

    function cancelErrorBatch() {
        Ext.Ajax.request({
            url: 'errorBatch.htm',
            method: 'POST',
            params: { '_eventId_cancel': 'cancel', 'execution': errorFlowExecutionKey }
        });
        errorBatchWin.hide(errorBatchWin);
    }

    function checkConditionsForErrorBatch(groupTabPanel, groupToShow, currentGroup) {
        var isValid = true;
        if (currentGroup && currentGroup.mainItem.id &&
            groupToShow && groupToShow.mainItem.id &&
            groupToShow.mainItem.id != currentGroup.mainItem.id &&
            currentGroup.mainItem.id == 'step3Error' &&
            (errorStep3Form.store.getCount() == 0 || ! checkFieldCriteriaValid(errorStep3Form))) {
            isValid = false;
            showErrorMsg(msgs.mustDoStep3Error);
        }
        return isValid;
    }

    var errorBatchWin = new Ext.Window({
        title: msgs.manageBatch,
        layout: 'fit',
        width: 875,
        height: 500,
        cls: 'win',
        id: 'errorBatchWin',
        modal: true,
        closable: false,
        closeAction: 'hide',
        listeners: {
            'beforeshow': function() {
                $('#errorBatchWin').bind('keydown', function(e) {
                    hideErrorOnEscape(e);
                });
            },
            'beforehide': function() {
                resetErrorBatchWin();
                $('#errorBatchWin').unbind('keydown', hideErrorOnEscape);
            }
        },
        items:[{
             xtype: 'grouptabpanel',
             tabWidth: 135,
             activeGroup: 0,
             ref: '../groupTabPanel',
             layoutOnTabChange: true,
             listeners: {
                 'beforegroupchange': checkConditionsForErrorBatch,
                 'groupchange': loadErrorTab
             },
             items: [
                 {
                     layoutOnTabChange: true,
                     items: [{
                         id: 'step1Error',
                         title: msgs.step1ErrorTitle,
                         tabTip: msgs.step1Tip,
                         style: 'padding: 20px 40px;',
                         items: [ errorStep1Form ]
                     }],
                     listeners: {
                         'afterrender': maskErrorStep1Form
                     }
                 },
                 {
                     layoutOnTabChange: true,
                     items: [{
                         id: 'step2Error',
                         cls: 'grp',
                         title: msgs.step2ErrorTitle,
                         tabTip: msgs.step2Tip,
                         items: [ errorStep2Form ]
                     }]
                 },
                 {
                     items: [{
                         id: 'step3Error',
                         cls: 'grp',
                         title: msgs.step3ErrorTitle,
                         tabTip: msgs.step3Tip,
                         items: [ errorStep3Form ]
                     }]
                 },
                 {
                     items: [{
                         id: 'step4Error',
                         cls: 'grp',
                         title: msgs.step4ErrorTitle,
                         tabTip: msgs.step4Tip,
                         items: [ errorStep4Form ]
                     }]
                 }
             ]
         }]
    });

    var hideErrorOnEscape = function(e) {
        if (e.keyCode == 27) {
            errorBatchWin.hide();
        }
    };

    function resetErrorBatchWin() {
        // When the batchWin is hidden, clear out all set store objects, reset CSS classes, etc, for re-use for another batch
        errorFlowExecutionKey = null; // on hide, clear out the flow execution key in order to start a new flow next time
        errorBatchWin.groupTabPanel.items.items[0].activeTab = null;
        errorBatchWin.groupTabPanel.strip.select('li.x-grouptabs-strip-active', true).removeClass('x-grouptabs-strip-active');
        errorBatchWin.groupTabPanel.activeGroup = null;
        Ext.getCmp('errorBatchDesc').setRawValue(''); // reset the batchDesc to empty string; use 'setRawValue()' to bypass form validation
        errorStep2Store.removeAll();
        errorStep3Form.store.removeAll();
        errorStep3UpdatableFieldsStore.removeAll();
        errorStep4Store.removeAll();
    }

    function initErrorTabSaveParams(groupTabPanel, newGroup, currentGroup) {
        var saveParams = {};
        if (currentGroup) {
            if (currentGroup.mainItem.id == 'step1Error') {
                saveParams = { 'batchDesc': Ext.getCmp('errorBatchDesc').getValue(), 'previousStep': 'step1Error' };
            }
            else if (currentGroup.mainItem.id == 'step2Error') {
                // nothing to save for step2 - view only
                saveParams = { 'previousStep': 'step2Error' };
            }
            else if (currentGroup.mainItem.id == 'step3Error') {
                findUpdateFields(errorStep3Form.store, saveParams);
                saveParams['previousStep'] = 'step3Error';
            }
            else if (currentGroup.mainItem.id == 'step4Error') {
                // nothing to save for step4 - view only
                saveParams = { 'previousStep': 'step4Error' };
            }
        }
        return saveParams;
    }

    function loadErrorTab(groupTabPanel, newGroup, currentGroup, startNum) {
        if ( ! startNum) {
            startNum = 0;
        }
        var saveParams = initErrorTabSaveParams(groupTabPanel, newGroup, currentGroup);
        var params = {};

        if (newGroup.mainItem.id == 'step1Error') {
            errorBatchWin.setTitle(msgs.manageBatch + ": " + msgs.step1Tip);
            maskErrorStep1Form(); 

            jQuery.extend(params, { 'batchId': errorBatchId, '_eventId_errorStep1': 'errorStep1', 'execution': errorFlowExecutionKey },
                    saveParams); // copy properties from {} & saveParams to params

            errorStep1Form.getForm().load({
                'url': 'errorBatch.htm',
                'params': params,
                'success': function(form, action) {
                    errorFlowExecutionKey = action.result.flowExecutionKey;
                    unmaskErrorStep1Form(); 
                },
                'failure': function(form, action) {
                    unmaskErrorStep1Form();
                    Ext.MessageBox.show({ title: msgs.error, icon: Ext.MessageBox.ERROR,
                        buttons: Ext.MessageBox.OK,
                        msg: msgs.errorStep1 });
                }
            });
        }
        else if (newGroup.mainItem.id == 'step2Error') {
            jQuery.extend(params, { '_eventId_errorStep2': 'errorStep2', 'execution': errorFlowExecutionKey, 'start': startNum, 'limit': errorStep2Bar.pageSize }, saveParams);
            errorStep2Store.load({ 'params': params });
            errorBatchWin.setTitle(msgs.manageBatch + ": " + msgs.step2Tip);
        }
        else if (newGroup.mainItem.id == 'step3Error') {
            jQuery.extend(params, { '_eventId_errorStep3': 'errorStep3', 'execution': errorFlowExecutionKey }, saveParams);
            errorStep3UpdatableFieldsStore.load({ 'params': params });
            errorBatchWin.setTitle(msgs.manageBatch + ": " + msgs.step3Tip);
        }
        else if (newGroup.mainItem.id == 'step4Error') {
            jQuery.extend(params, { '_eventId_errorStep4': 'errorStep4', 'execution': errorFlowExecutionKey, 'start': startNum,
                'limit': errorStep4Bar.pageSize, 'sort': 'id', 'dir': 'ASC' }, saveParams);
            errorStep4Store.load({ 'params': params });
            errorBatchWin.setTitle(msgs.manageBatch + ": " + msgs.step4Tip);
        }
    }

    function maskErrorStep1Form() {
        var step1ErrorDivId = $('#step1Error').parent('div').attr('id');
        if (step1ErrorDivId) {
            Ext.get(step1ErrorDivId).mask(msgs.loading);
        }
    }

    function unmaskErrorStep1Form() {
        var step1ErrorDivId = $('#step1Error').parent('div').attr('id');
        if (step1ErrorDivId) {
            Ext.get(step1ErrorDivId).unmask();
        }
    }
});


