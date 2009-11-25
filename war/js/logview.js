Ext.namespace('LogView');

Ext.onReady(function() {


    Ext.QuickTips.init();

    var baseParams = {};


    var header = 'Log History';
    var siteName = Ext.get('auditSiteName');
    if(siteName) {
        header = siteName.dom.innerHTML + ' ' + header;

    }

    LogView.store = new Ext.data.JsonStore({
        url: 'logView.json',
        totalProperty: 'totalRows',                           
        root: 'rows',
        fields: [
            {name: 'id', mapping: 'id', type: 'int'},
            {name: 'createdate', mapping: 'createdate', type: 'date', dateFormat: 'Y-m-d H:i:s'},
            {name: 'context', mapping: 'context', type: 'string'},
            {name: 'message', mapping: 'message', type: 'string'},
        ],
        sortInfo:{field: 'id', direction: "DESC"},
        remoteSort: true,
        baseParams: baseParams
    });

    LogView.pagingBar = new Ext.PagingToolbar({
        pageSize: 100,
        store: LogView.store,
        displayInfo: true,
        displayMsg: 'Displaying log events {0} - {1} of {2}',
        emptyMsg: "No log history to display"
    });

    LogView.grid = new Ext.grid.GridPanel({

        store: LogView.store,
        columns: [
            {header: 'Id', width: 30, dataIndex: 'id', sortable: true},
            {header: 'Date', width: 60, dataIndex: 'createdate', sortable: false, renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
            {header: 'User', width: 40, dataIndex: 'context', sortable: false},
            {header: 'Message', width: 250,  dataIndex: 'message', sortable: false, renderer: LogView.descriptionRenderer}
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
        bbar: LogView.pagingBar,
        renderTo: 'logViewGrid'
    });

    LogView.store.load({params: {start: 0, limit: 100, sort: 'id', dir: 'DESC'}});

});

LogView.descriptionRenderer = function(v, meta, record) {
       return '<span ext:qtitle="Event Description" ext:qwidth="250" ext:qtip="' + v + '">' + v + '</span>';
};






