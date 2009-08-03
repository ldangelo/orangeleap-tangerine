Ext.namespace('RecurringGiftList');

Ext.onReady(function() {

    Ext.QuickTips.init();

    var constituentId = /constituentId=(\d+)/g.exec(document.location.search);
    var baseParams = {};

    if(constituentId) {
        baseParams.constituentId = constituentId[1];
    }

    var header = 'Recurring Gift List';

    RecurringGiftList.store = new Ext.data.JsonStore({
        url: 'recurringGiftList.json',
        totalProperty: 'totalRows',
        root: 'rows',
        fields: [
            {name: 'id', mapping: 'id', type: 'int'},
            {name: 'startdate', mapping: 'startdate', type: 'date', dateFormat: 'Y-m-d H:i:s'},
            {name: 'enddate', mapping: 'enddate', type: 'date', dateFormat: 'Y-m-d H:i:s'},
            {name: 'activate', mapping: 'activate', type: 'boolean'},
            {name: 'constituentId', mapping: 'constituentId', type: 'string'},
            {name: 'status', mapping: 'status', type: 'string'},
            {name: 'amountpergift', mapping: 'amountpergift', type: 'float'},
            {name: 'amounttotal', mapping: 'amounttotal', type: 'float'},
            {name: 'amountpaid', mapping: 'amountpaid', type: 'float'},
            {name: 'amountremaining', mapping: 'amountremaining', type: 'float'}
        ],
        sortInfo:{field: 'status', direction: "DESC"},
        remoteSort: true,
        baseParams: baseParams
    });

    RecurringGiftList.pagingBar = new Ext.PagingToolbar({
        pageSize: 100,
        store: RecurringGiftList.store,
        displayInfo: true,
        displayMsg: 'Displaying recurring gift list {0} - {1} of {2}',
        emptyMsg: "No recurring gifts to display"
    });
	
    RecurringGiftList.grid = new Ext.grid.GridPanel({

        store: RecurringGiftList.store,
        columns: [
            {header: '', width: 65, dataIndex: 'id', sortable: false, menuDisabled: true, renderer: RecurringGiftList.entityViewRenderer},
            {header: 'Start Dt', width: 80, dataIndex: 'startdate', sortable: true, renderer: Ext.util.Format.dateRenderer('m-d-y')},
            {header: 'End Dt', width: 80, dataIndex: 'enddate', sortable: true, renderer: Ext.util.Format.dateRenderer('m-d-y')},
            {header: 'Status', width: 100, dataIndex: 'status', sortable: true},
            {header: 'Activate', tooltip: 'Activate', width: 55, align: 'center', dataIndex: 'activate', sortable: true, renderer: RecurringGiftList.activateRenderer }, 
            {header: 'Amt Per Gift', tooltip: 'Amount Per Gift', width: 65, dataIndex: 'amountpergift', sortable: true, renderer: OrangeLeap.amountRenderer },
            {header: 'Amt Total', tooltip: 'Amount Total', width: 65, dataIndex: 'amounttotal', sortable: true, renderer: OrangeLeap.amountRenderer },
            {header: 'Amt Paid', tooltip: 'Amount Paid', width: 65, dataIndex: 'amountpaid', sortable: true, renderer: OrangeLeap.amountRenderer },
            {header: 'Amt Remaining', tooltip: 'Amount Remaining', width: 65, dataIndex: 'amountremaining', sortable: true, renderer: OrangeLeap.amountRenderer }
        ],
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        viewConfig: {
            forceFit: true
        },
        height:530,
        width: 760,
        frame: true,
        header: true,
        title: header,
        loadMask: true,
        bbar: RecurringGiftList.pagingBar,
        renderTo: 'recurringGiftListGrid'
    });
    
    RecurringGiftList.store.load({params: {start: 0, limit: 100, sort: 'status', dir: 'DESC'}});

});

RecurringGiftList.entityViewRenderer = function(val, meta, record) {
	   return '<a href="javascript:RecurringGiftList.navigate(' + record.data.id + ')" title="View">View</a>';
};

RecurringGiftList.activateRenderer = function(val, meta, record) {
    if (record.data.activate) {
        meta.css = 'green-dot';
        meta.attr = 'ext:qtip="Activated"';
    } 
    else {
        meta.css = 'grey-dot';
        meta.attr = 'ext:qtip="Not Activated"';
    }
};

RecurringGiftList.navigate = function(id) {
	    var rec = RecurringGiftList.grid.getSelectionModel().getSelected();
	    RecurringGiftList.grid.getGridEl().mask('Loading Record');
        window.location.href='recurringGift.htm?recurringGiftId=' + id + '&constituentId=' + rec.data.constituentId;
};





