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
            {name: 'constituentid', mapping: 'constituentid', type: 'string'},
            {name: 'date', mapping: 'date', type: 'date', dateFormat: 'Y-m-d H:i:s'},
            {name: 'amount', mapping: 'amount', type: 'float'},
            {name: 'currencycode', mapping: 'currencycode', type: 'string'},
            {name: 'type', mapping: 'type', type: 'string'},
            {name: 'status', mapping: 'status', type: 'string'},
            {name: 'authcode', mapping: 'authcode', type: 'string'},
            {name: 'refnumber', mapping: 'refnumber', type: 'string'},
            {name: 'comments', mapping: 'comments', type: 'string'},
            {name: 'gifttype', mapping: 'gifttype', type: 'string'},
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
            {header: '', width: 30, dataIndex: 'id', sortable: false, menuDisabled: true, renderer: GiftList.entityViewRenderer},
            {header: 'Donation Dt', width: 80, dataIndex: 'date', sortable: true, renderer: Ext.util.Format.dateRenderer('m-d-y')},
            {header: 'Amount', width: 50, dataIndex: 'amount', sortable: true},
            {header: 'Curr', width: 40, dataIndex: 'currencycode', sortable: true},
            {header: 'Pay Type', width: 60, dataIndex: 'type', sortable: true},
            {header: 'Pay Status', width: 65, dataIndex: 'status', sortable: true},
            {header: 'Auth Code', width: 65, dataIndex: 'authcode', sortable: true},
            {header: 'Ref Num', width: 75, dataIndex: 'refnumber', sortable: true},
            {header: 'Comments', width: 200,  dataIndex: 'comments', sortable: true, renderer: GiftList.descriptionRenderer}
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
	
	switch (rec.data.gifttype) {
		case 'MONETARY':
		    window.location.href='giftView.htm?giftId=' + id + '&personId=' + rec.data.constituentid;
			break;
		case 'ADJUSTED':
			window.location.href='giftAdjustmentView.htm?adjustedGiftId=' + id + '&personId=' + rec.data.constituentid;
			break;
        default:
            window.location.href= 'person.htm?personid=' + rec.data.constituentid;
	}
	return false;
};





