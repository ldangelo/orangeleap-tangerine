Ext.namespace('PostbatchGiftList');

Ext.onReady(function() {

    Ext.QuickTips.init();

    var baseParams = {};

    baseParams.id = $('#id').val();

    var header = 'Selection List';

    PostbatchGiftList.store = new Ext.data.JsonStore({
        url: 'postbatchGiftList.json',
        totalProperty: 'totalRows',
        root: 'rows',
        fields: [
            {name: 'id', mapping: 'id', type: 'int'},
            {name: 'constituentId', mapping: 'constituent.id', type: 'int'},
            {name: 'createdate', mapping: 'createdate', type: 'date', dateFormat: 'Y-m-d'},
            {name: 'donationdate', mapping: 'donationdate', type: 'date', dateFormat: 'Y-m-d'},
            {name: 'amount', mapping: 'amount', type: 'float'},
            {name: 'currencycode', mapping: 'currencycode', type: 'string'},
            {name: 'paymenttype', mapping: 'paymenttype', type: 'string'},
            {name: 'status', mapping: 'status', type: 'string'},
            {name: 'source', mapping: 'source', type: 'string'}
        ],
        sortInfo:{field: 'donationdate', direction: "DESC"},
        remoteSort: true,
        baseParams: baseParams
    });

    PostbatchGiftList.pagingBar = new Ext.PagingToolbar({
        pageSize: 100,
        store: PostbatchGiftList.store,
        displayInfo: true,
        displayMsg: 'Displaying list {0} - {1} of {2}',
        emptyMsg: "No items to display"
    });

    PostbatchGiftList.grid = new Ext.grid.GridPanel({

        store: PostbatchGiftList.store,
        columns: [
            {header: '', width: 65, dataIndex: 'id', sortable: false, menuDisabled: true, renderer: PostbatchGiftList.entityViewRenderer},
            {header: 'Create Date', width: 100, dataIndex: 'createdate', sortable: true, renderer: Ext.util.Format.dateRenderer('m-d-y')},
            {header: 'Donation Date', width: 100, dataIndex: 'donationdate', sortable: true, renderer: Ext.util.Format.dateRenderer('m-d-y')},
            {header: 'Amount', width: 65, dataIndex: 'amount', sortable: true, renderer: OrangeLeap.amountRenderer },
            {header: 'Currency Code', width: 65, dataIndex: 'currencycode', sortable: true},
            {header: 'Pay Method', width: 65, dataIndex: 'paymenttype', sortable: true},
            {header: 'Status', width: 65, dataIndex: 'status', sortable: true},
            {header: 'Source', width: 65, dataIndex: 'source', sortable: false}
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
        bbar: PostbatchGiftList.pagingBar,
        renderTo: 'postbatchGiftsGrid'
    });

    PostbatchGiftList.store.load({params: {start: 0, limit: 100, sort: 'donationdate', dir: 'DESC'}});

});

PostbatchGiftList.entityViewRenderer = function(val, meta, record) {
	return '<a href="javascript:PostbatchGiftList.navigate(' + record.data.id + ')" title="View">View</a>';
};

 
// TODO - make work for adjusted gifts also..
PostbatchGiftList.navigate = function(id) {
	var rec = PostbatchGiftList.grid.getSelectionModel().getSelected();
	PostbatchGiftList.grid.getGridEl().mask('Loading Record');
	window.location.href='giftView.htm?giftId=' + id + '&constituentId=' + rec.data.constituentId;
};





