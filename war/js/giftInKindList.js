Ext.namespace('GiftInKindList');

Ext.onReady(function() {

    Ext.QuickTips.init();

    var personId = /personId=(\d+)/g.exec(document.location.search);
    var baseParams = {};

    if(personId) {
        baseParams.personId = personId[1];
    }

    var header = 'Gift In Kind List';

    GiftInKindList.store = new Ext.data.JsonStore({
        url: 'giftInKindList.json',
        totalProperty: 'totalRows',
        root: 'rows',
        fields: [
            {name: 'id', mapping: 'id', type: 'int'},
            {name: 'date', mapping: 'date', type: 'date', dateFormat: 'Y-m-d'},
            {name: 'personId', mapping: 'personId', type: 'string'},
            {name: 'fairmarketvalue', mapping: 'fairmarketvalue', type: 'float'},
            {name: 'currencycode', mapping: 'currencycode', type: 'string'},
            {name: 'donationdate', mapping: 'donationdate', type: 'date', dateFormat: 'Y-m-d'},
            {name: 'motivationcode', mapping: 'motivationcode', type: 'string'}
        ],
        sortInfo:{field: 'date', direction: "DESC"},
        remoteSort: true,
        baseParams: baseParams
    });

    GiftInKindList.pagingBar = new Ext.PagingToolbar({
        pageSize: 100,
        store: GiftInKindList.store,
        displayInfo: true,
        displayMsg: 'Displaying gift in kind list {0} - {1} of {2}',
        emptyMsg: "No gifts in kind to display"
    });

    GiftInKindList.grid = new Ext.grid.GridPanel({

        store: GiftInKindList.store,
        columns: [
            {header: '', width: 65, dataIndex: 'id', sortable: true, renderer: GiftInKindList.entityViewRenderer},
            {header: 'Transaction Date', width: 100, dataIndex: 'date', sortable: true, renderer: Ext.util.Format.dateRenderer('m-d-y')},
            {header: 'Fair Market Value', width: 65, dataIndex: 'fairmarketvalue', sortable: true},
            {header: 'Currency Code', width: 65, dataIndex: 'currencycode', sortable: true},
            //{header: 'Donation Date', width: 100, dataIndex: 'donationdate', sortable: true, renderer: Ext.util.Format.dateRenderer('m-d-y')},
            {header: 'Motivation Code', width: 65, dataIndex: 'motivationcode', sortable: true}
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
        bbar: GiftInKindList.pagingBar,
        renderTo: 'giftInKindGrid'
    });

    GiftInKindList.store.load({params: {start: 0, limit: 100, sort: 'date', dir: 'DESC'}});

});

GiftInKindList.entityViewRenderer = function(val, meta, record) {
	   return '<a href="javascript:GiftInKindList.navigate(' + record.data.id + ')" title="View">View</a>';
};

GiftInKindList.navigate = function(id) {
	    var rec = GiftInKindList.grid.getSelectionModel().getSelected();
	    GiftInKindList.grid.getGridEl().mask('Loading Record');
        window.location.href='giftInKind.htm?giftInKindId=' + id + '&personId=' + rec.data.personId;
};





