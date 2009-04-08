Ext.namespace('PledgeList');

Ext.onReady(function() {

    Ext.QuickTips.init();

    var personId = /personId=(\d+)/g.exec(document.location.search);
    var baseParams = {};

    if(personId) {
        baseParams.personId = personId[1];
    }

    var header = 'Pledge List';

    PledgeList.store = new Ext.data.JsonStore({
        url: 'pledgeList.json',
        totalProperty: 'totalRows',
        root: 'rows',
        fields: [
            {name: 'id', mapping: 'id', type: 'int'},
            {name: 'personId', mapping: 'personId', type: 'string'},
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

    PledgeList.pagingBar = new Ext.PagingToolbar({
        pageSize: 100,
        store: PledgeList.store,
        displayInfo: true,
        displayMsg: 'Displaying pledge list {0} - {1} of {2}',
        emptyMsg: "No pledges to display"
    });
	
    PledgeList.grid = new Ext.grid.GridPanel({

        store: PledgeList.store,
        columns: [
            {header: '', width: 65, dataIndex: 'id', sortable: true, renderer: PledgeList.entityViewRenderer},
            {header: 'Status', width: 100, dataIndex: 'status', sortable: true},
            {header: 'Amount Per Gift', width: 65, dataIndex: 'amountpergift', sortable: true},
            {header: 'Amount Total', width: 65, dataIndex: 'amounttotal', sortable: true},
            {header: 'Amount Paid', width: 65, dataIndex: 'amountpaid', sortable: false},
            {header: 'Amount Remaining', width: 65, dataIndex: 'amountremaining', sortable: false}
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
        bbar: PledgeList.pagingBar,
        renderTo: 'pledgeListGrid'
    });
    
    PledgeList.store.load({params: {start: 0, limit: 100, sort: 'status', dir: 'DESC'}});

});

PledgeList.entityViewRenderer = function(val, meta, record) {
	   return '<a href="javascript:PledgeList.navigate(' + record.data.id + ')" title="View">View</a>';
};

PledgeList.navigate = function(id) {
	    var rec = PledgeList.grid.getSelectionModel().getSelected();
	    PledgeList.grid.getGridEl().mask('Loading Record');
        window.location.href='pledge.htm?pledgeId=' + id + '&personId=' + rec.data.personId;
};





