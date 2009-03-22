Ext.namespace('PaymentHistory');

Ext.onReady(function() {

    Ext.QuickTips.init();

    var personId = /personId=(\d+)/g.exec(document.location.search);
    var baseParams = {};

    if(personId) {
        baseParams.personId = personId[1];
    }

    var header = 'Payment History';


    PaymentHistory.store = new Ext.data.JsonStore({
        url: 'paymentHistoryList.json',
        totalProperty: 'totalRows',
        root: 'rows',
        fields: [
            {name: 'id', mapping: 'id', type: 'int'},
            {name: 'date', mapping: 'date', type: 'date', dateFormat: 'Y-m-d H:i:s'},
            {name: 'personId', mapping: 'personId', type: 'string'},
            {name: 'type', mapping: 'type', type: 'string'},
            {name: 'paymentType', mapping: 'paymentType', type: 'string'},
            {name: 'description', mapping: 'description', type: 'string'},
            {name: 'amount', mapping: 'amount', type: 'float'},
            {name: 'currencyCode', mapping: 'currencyCode', type: 'string'},
        ],
        sortInfo:{field: 'date', direction: "DESC"},
        remoteSort: true,
        baseParams: baseParams
    });

    PaymentHistory.pagingBar = new Ext.PagingToolbar({
        pageSize: 100,
        store: PaymentHistory.store,
        displayInfo: true,
        displayMsg: 'Displaying payment history {0} - {1} of {2}',
        emptyMsg: "No payment history to display"
    });


    PaymentHistory.grid = new Ext.grid.GridPanel({

        store: PaymentHistory.store,
        columns: [
            {header: 'Id', width: 65, dataIndex: 'id', sortable: true},
            {header: 'Date', widht: 100, dataIndex: 'date', sortable: true, renderer: Ext.util.Format.dateRenderer('m-d-y g:ia')},
            {header: 'Payment Type', width: 65, dataIndex: 'paymentType', sortable: true},
            {header: 'Amount', width: 65, dataIndex: 'amount', sortable: true},
            {header: 'Currency', width: 65, dataIndex: 'currencyCode', sortable: true},
            {header: 'Description', width: 200,  dataIndex: 'description', sortable: true, renderer: PaymentHistory.descriptionRenderer},
        ],
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        viewConfig: {
            forceFit: true
        },
        height:530,
        width: 760,
        autoExpandColumn: 'description',
        frame: true,
        header: true,
        title: header,
        loadMask: true,
        bbar: PaymentHistory.pagingBar,
        renderTo: 'paymentHistoryGrid'
    });

    PaymentHistory.store.load({params: {start: 0, limit: 100, sort: 'date', dir: 'DESC'}});

});

PaymentHistory.descriptionRenderer = function(v, meta, record) {
    return '<span ext:qtitle="Description" ext:qwidth="250" ext:qtip="' + v + '">' + v + '</span>';
};




