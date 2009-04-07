Ext.namespace('GiftList');

Ext.onReady(function() {

    Ext.QuickTips.init();

    var personId = /personId=(\d+)/g.exec(document.location.search);
    var baseParams = {};

    if(personId) {
        baseParams.personId = personId[1];
    }

    var header = 'Gift List';

    GiftList.store = new Ext.data.JsonStore({
        url: 'giftList.json',
        totalProperty: 'totalRows',
        root: 'rows',
        fields: [
            {name: 'id', mapping: 'id', type: 'int'},
            {name: 'date', mapping: 'date', type: 'date', dateFormat: 'Y-m-d'},
            {name: 'personId', mapping: 'personId', type: 'string'},
            {name: 'amount', mapping: 'amount', type: 'float'},
            {name: 'comments', mapping: 'comments', type: 'string'},
            {name: 'authcode', mapping: 'authcode', type: 'string'}
        ],
        sortInfo:{field: 'date', direction: "DESC"},
        remoteSort: true,
        baseParams: baseParams
    });

    GiftList.pagingBar = new Ext.PagingToolbar({
        pageSize: 100,
        store: GiftList.store,
        displayInfo: true,
        displayMsg: 'Displaying gift list {0} - {1} of {2}',
        emptyMsg: "No gifts to display"
    });

    GiftList.grid = new Ext.grid.GridPanel({

        store: GiftList.store,
        columns: [
            {header: '', width: 65, dataIndex: 'id', sortable: true, renderer: GiftList.entityViewRenderer},
            {header: 'Transaction Date', width: 100, dataIndex: 'date', sortable: true, renderer: Ext.util.Format.dateRenderer('m-d-y')},
            {header: 'Reference Number', width: 65, dataIndex: 'id', sortable: true},
            {header: 'Amount', width: 65, dataIndex: 'amount', sortable: true},
            {header: 'Comments', width: 200,  dataIndex: 'comments', sortable: true, renderer: GiftList.descriptionRenderer},
            {header: 'Auth Code', width: 65, dataIndex: 'authcode', sortable: true}
        ],
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        viewConfig: {
            forceFit: true
        },
        height:530,
        width: 760,
        autoExpandColumn: 'comments',
        frame: true,
        header: true,
        title: header,
        loadMask: true,
        bbar: GiftList.pagingBar,
        renderTo: 'giftsGrid'
    });

    GiftList.store.load({params: {start: 0, limit: 100, sort: 'date', dir: 'DESC'}});

});

GiftList.descriptionRenderer = function(v, meta, record) {
    return '<span ext:qtitle="Comments" ext:qwidth="250" ext:qtip="' + v + '">' + v + '</span>';
};

GiftList.entityViewRenderer = function(val, meta, record) {
	   return '<a href="javascript:GiftList.navigate(' + record.data.id + ')" title="View">View</a>';
};

GiftList.navigate = function(id) {
	    var rec = GiftList.grid.getSelectionModel().getSelected();
	    GiftList.grid.getGridEl().mask('Loading Record');
        window.location.href='giftView.htm?giftId=' + id + '&personId=' + rec.data.personId;
};





