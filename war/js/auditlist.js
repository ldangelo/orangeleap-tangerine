Ext.namespace('Audit');

Ext.onReady(function() {

    Ext.QuickTips.init();

    var objectType = /object=(\w+)/g.exec(document.location.search);
    var objectId = /id=(\d+)/g.exec(document.location.search);
    var baseParams = {};

    if(objectType) {
        baseParams.object = objectType[1];
    }

    if(objectId) {
        baseParams.id = objectId[1];
    }

    Audit.store = new Ext.data.JsonStore({
        url: 'auditList.json',
        totalProperty: 'totalRows',
        root: 'rows',
        fields: [
            {name: 'id', mapping: 'id', type: 'int'},
            {name: 'date', mapping: 'date', type: 'date', dateFormat: 'Y-m-d H:i:s'},
            {name: 'user', mapping: 'user', type: 'string'},
            {name: 'type', mapping: 'type', type: 'string'},
            {name: 'description', mapping: 'description', type: 'string'},
            {name: 'objectType', mapping: 'objectType', type: 'string'},
            {name: 'objectId', mapping: 'objectId', type: 'int'},
            {name: 'personId', mapping: 'personId', type: 'int'}
        ],
        sortInfo:{field: 'date', direction: "DESC"},
        remoteSort: true,
        baseParams: baseParams
    });

    Audit.pagingBar = new Ext.PagingToolbar({
        pageSize: 100,
        store: Audit.store,
        displayInfo: true,
        displayMsg: 'Displaying audit events {0} - {1} of {2}',
        emptyMsg: "No audit history to display"
    });


    Audit.grid = new Ext.grid.GridPanel({

        store: Audit.store,
        columns: [
            {header: 'Date', widht: 100, dataIndex: 'date', sortable: true, renderer: Ext.util.Format.dateRenderer('m-d-y g:ia')},
            {header: 'User', width: 65, dataIndex: 'user', sortable: true},
            {header: 'Type', width: 65, dataIndex: 'type', sortable: true},
            {header: 'Description', width: 200,  dataIndex: 'description', sortable: true, renderer: Audit.descriptionRenderer},
            {header: 'Entity Type', width: 70,  dataIndex: 'objectType', sortable: true},
            {header: 'Entity ID', width: 50, align: 'right', dataIndex: 'objectId', sortable: true},
            {header: 'Current', width: 55, sortable: false, menuDisabled: true, fixed: true, renderer: Audit.entityViewRenderer},
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
        title: 'Audit History',
        loadMask: true,
        listeners: {
            rowdblclick: function(grid, row, evt) {
                var rec = grid.getSelectionModel().getSelected();
                Audit.navigate(rec.data.objectId);
            }
        },
        bbar: Audit.pagingBar,
        renderTo: 'auditHistoryGrid'
    });

    Audit.store.load({params: {start: 0, limit: 100, sort: 'date', dir: 'DESC'}});

});

Audit.descriptionRenderer = function(v, meta, record) {
    return '<span ext:qtitle="Event Description" ext:qwidth="250" ext:qtip="' + v + '">' + v + '</span>';
};

Audit.entityViewRenderer = function(val, meta, record) {
    return '<a href="javascript:Audit.navigate(' + record.data.objectId + ')" title="View">View</a>';
};

Audit.navigate = function(id) {
    var rec = Audit.grid.getSelectionModel().getSelected();
    Ext.get(document.body).mask('Loading Entity');

    switch(rec.data.objectType) {
        case 'person':
            window.location.href = 'person.htm?personId=' + id;
            break;
        case 'gift':
            window.location.href= 'giftView.htm?giftId=' + id + '&personId=' + rec.data.personId;
            break;
        case 'address':
            window.location.href= 'addressManager.htm?personId=' + rec.data.personId;
            break;
        case 'paymentsource':
            window.location.href= 'paymentManager.htm?personId=' + rec.data.personId;
            break;
        case 'email':
            window.location.href= 'emailManager.htm?personId=' + rec.data.personId;
            break;
        case 'phone':
            window.location.href= 'phoneManager.htm?personId=' + rec.data.personId;
            break;
        case 'recurring gift':
            window.location.href='recurringGift.htm?commitmentId=' + id + '&personId=' + rec.data.personId;
            break;
        case 'pledge':
            window.location.href='pledgeList.htm?personId=' + rec.data.personId + '&type=pledge';
            break;
        default:
            window.location.href= 'person.htm?personid=' + rec.data.personId;
    }

    return false;
};



