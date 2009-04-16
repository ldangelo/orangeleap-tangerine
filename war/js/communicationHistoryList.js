Ext.namespace('CommunicationHistory');

Ext.onReady(function() {

    Ext.QuickTips.init();

    var personId = /personId=(\d+)/g.exec(document.location.search);
    var baseParams = {};

    if(personId) {
        baseParams.personId = personId[1];
    }

    var header = 'Touch Point History';


    CommunicationHistory.store = new Ext.data.JsonStore({
        url: 'communicationHistoryList.json',
        totalProperty: 'totalRows',
        root: 'rows',
        fields: [
            {name: 'id', mapping: 'id', type: 'int'},
            {name: 'date', mapping: 'date', type: 'date', dateFormat: 'Y-m-d'},
            {name: 'personId', mapping: 'personId', type: 'string'},
            {name: 'type', mapping: 'type', type: 'string'},
            {name: 'comments', mapping: 'comments', type: 'string'}
        ],
        sortInfo:{field: 'date', direction: "DESC"},
        remoteSort: true,
        baseParams: baseParams
    });

    CommunicationHistory.pagingBar = new Ext.PagingToolbar({
        pageSize: 100,
        store: CommunicationHistory.store,
        displayInfo: true,
        displayMsg: 'Displaying touch point history {0} - {1} of {2}',
        emptyMsg: "No touch point history to display"
    });


    CommunicationHistory.grid = new Ext.grid.GridPanel({

        store: CommunicationHistory.store,
        columns: [
            {header: '', width: 65, dataIndex: 'id', sortable: true, renderer: CommunicationHistory.entityViewRenderer},
            {header: 'Date', widht: 100, dataIndex: 'date', sortable: true, renderer: Ext.util.Format.dateRenderer('m-d-y')},
            {header: 'Type', width: 65, dataIndex: 'type', sortable: true},
            {header: 'Comments', width: 200,  dataIndex: 'comments', sortable: true, renderer: CommunicationHistory.descriptionRenderer}
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
        bbar: CommunicationHistory.pagingBar,
        renderTo: 'communicationHistoryGrid'
    });

    CommunicationHistory.store.load({params: {start: 0, limit: 100, sort: 'date', dir: 'DESC'}});

});

CommunicationHistory.descriptionRenderer = function(v, meta, record) {
    return '<span ext:qtitle="Comments" ext:qwidth="250" ext:qtip="' + v + '">' + v + '</span>';
};

CommunicationHistory.entityViewRenderer = function(val, meta, record) {
	   return '<a href="javascript:CommunicationHistory.navigate(' + record.data.id + ')" title="View">View</a>';
};

CommunicationHistory.navigate = function(id) {
	    var rec = CommunicationHistory.grid.getSelectionModel().getSelected();
	    CommunicationHistory.grid.getGridEl().mask('Loading Record');
        window.location.href='communicationHistoryView.htm?communicationHistoryId=' + id + '&personId=' + rec.data.personId;
};





