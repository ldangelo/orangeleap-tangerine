Ext.namespace('OrangeLeap.person');

Ext.onReady(function(){

    Ext.QuickTips.init();

     OrangeLeap.person.store = new Ext.data.JsonStore({
    	url: 'personList.json',
        totalProperty: 'totalRows',
    	root: 'rows',
        fields: [
            {name: 'id', mapping: 'id', type: 'int'},
            {name: 'first', mapping: 'first', type: 'string'},
            {name: 'last', mapping: 'last', type: 'string'},
            {name: 'organization', mapping: 'organization', type: 'string'},
            {name: 'majorDonor', mapping: 'majorDonor', type: 'boolean'},
            {name: 'lapsedDonor', mapping: 'lapsedDonor', type: 'boolean'}
        ],
    	sortInfo:{field: 'id', direction: "ASC"},
         remoteSort: true
    });

    OrangeLeap.person.pagingBar = new Ext.PagingToolbar({
        pageSize: 100,
        store: OrangeLeap.person.store,
        displayInfo: true,
        displayMsg: 'Displaying constituents {0} - {1} of {2}',
        emptyMsg: "No constituents to display"
    });


    OrangeLeap.person.grid = new Ext.grid.GridPanel({

        store: OrangeLeap.person.store,
        columns: [
            {header: 'View', width: 40, sortable: false, menuDisabled: true, fixed: true, renderer: OrangeLeap.person.personViewRenderer},
            {header: 'ID', width: 40, dataIndex: 'id', sortable: true, align: 'right'},
            {header: 'First Name', widht: 90, dataIndex: 'first', sortable: true},
            {header: 'Last Name', width: 90, dataIndex: 'last', sortable: true},
            {header: 'Organization', width: 120, dataIndex: 'organization', sortable: true},
            {header: 'Major', tooltip: 'Major Donors', width: 55, align: 'center', dataIndex: 'majorDonor', sortable: true, renderer: OrangeLeap.person.majorDonorRenderer},
            {header: 'Lapsed', tooltip: 'Lapsed Donors',width: 55, align: 'center', dataIndex: 'lapsedDonor', sortable: true, renderer: OrangeLeap.person.lapsedDonorRenderer}
        ],
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		viewConfig: {
			forceFit: true
        },
        height:585,
        width: 760,
        autoExpandColumn: 'organization',
        frame: true,
		header: true,
        title: 'All Constituents',
		loadMask: true,
        listeners: {
            rowdblclick: function(grid, row, evt) {
                var rec = grid.getSelectionModel().getSelected();
                Ext.get(document.body).mask('Loading ' + rec.data.first + ' ' + rec.data.last);
                window.location.href = "person.htm?personId=" + rec.data.id;
            }
        },
        bbar: OrangeLeap.person.pagingBar,
		renderTo: 'personListGrid'
	    //tools: [{id: 'refresh', qtip: 'Refresh list', handler: function(){OrangeLeap.person.store.reload();}}]
    });

    OrangeLeap.person.store.load({params: {start: 0, limit: 100}});

});

OrangeLeap.person.personViewRenderer = function(val, meta, record) {
    return '<a href="javascript:OrangeLeap.person.navigate(' + record.data.id + ')" title="View Person">View</a>';
//    return '<a href="person.htm?personId=' + record.data.id + '">View</a>';
};

OrangeLeap.person.navigate = function(id) {
    var rec = OrangeLeap.person.grid.getSelectionModel().getSelected();
    OrangeLeap.person.grid.getEl().mask('Loading ' + rec.data.first + ' ' + rec.data.last);
    window.location.href = "person.htm?personId=" + id;
    return false;
}

OrangeLeap.person.majorDonorRenderer = function(val, meta, record) {

    if (record.data.majorDonor) {
        meta.css = 'green-dot';
        meta.attr = 'ext:qtip="Major Donor"';
    } else {
        meta.css = 'grey-dot';
        meta.attr = 'ext:qtip="Normal Donor"';
    }
};

OrangeLeap.person.lapsedDonorRenderer = function(val, meta, record) {

    if (record.data.lapsedDonor) {
        meta.css = 'red-dot';
        meta.attr = 'ext:qtip="Lapsed Donor"';
    } else {
        meta.css = 'grey-dot';
        meta.attr = 'ext:qtip="Current Donor"';
    }
};


