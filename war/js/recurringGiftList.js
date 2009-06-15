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
            {header: 'Status', width: 100, dataIndex: 'status', sortable: true},
            {header: 'Amount Per Gift', width: 65, dataIndex: 'amountpergift', sortable: true},
            {header: 'Amount Total', width: 65, dataIndex: 'amounttotal', sortable: true},
            {header: 'Amount Paid', width: 65, dataIndex: 'amountpaid', sortable: true},
            {header: 'Amount Remaining', width: 65, dataIndex: 'amountremaining', sortable: true}
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

RecurringGiftList.navigate = function(id) {
	    var rec = RecurringGiftList.grid.getSelectionModel().getSelected();
	    RecurringGiftList.grid.getGridEl().mask('Loading Record');
        window.location.href='recurringGift.htm?recurringGiftId=' + id + '&constituentId=' + rec.data.constituentId;
};





