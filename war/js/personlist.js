Ext.namespace('MPower.person');

Ext.onReady(function(){

    Ext.QuickTips.init();

     MPower.person.store = new Ext.data.JsonStore({
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

    MPower.person.pagingBar = new Ext.PagingToolbar({
        pageSize: 100,
        store: MPower.person.store,
        displayInfo: true,
        displayMsg: 'Displaying constituents {0} - {1} of {2}',
        emptyMsg: "No constituents to display"
    });


    MPower.person.grid = new Ext.grid.GridPanel({

        store: MPower.person.store,
        columns: [
            {header: 'View', width: 40, sortable: false, menuDisabled: true, fixed: true, renderer: MPower.person.personViewRenderer},
            {header: 'ID', width: 40, dataIndex: 'id', sortable: true, align: 'right'},
            {header: 'First Name', widht: 90, dataIndex: 'first', sortable: true},
            {header: 'Last Name', width: 90, dataIndex: 'last', sortable: true},
            {header: 'Organization', width: 120, dataIndex: 'organization', sortable: true},
            {header: 'Major', tooltip: 'Major Donors', width: 45, align: 'center', dataIndex: 'majorDonor', sortable: true, renderer: MPower.person.majorDonorRenderer},
            {header: 'Lapsed', tooltip: 'Lapsed Donors',width: 45, align: 'center', dataIndex: 'lapsedDonor', sortable: true, renderer: MPower.person.lapsedDonorRenderer}
        ],
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		viewConfig: {
			forceFit: true
        },
        height:500,
        width: 650,
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
        bbar: MPower.person.pagingBar,
		renderTo: 'personListGrid'
	    //tools: [{id: 'refresh', qtip: 'Refresh list', handler: function(){MPower.person.store.reload();}}]
    });

    MPower.person.store.load({params: {start: 0, limit: 100}});

});

MPower.person.personViewRenderer = function(val, meta, record) {
    return '<a href="javascript:MPower.person.navigate(' + record.data.id + ')" title="View Person">View</a>';
//    return '<a href="person.htm?personId=' + record.data.id + '">View</a>';
};

MPower.person.navigate = function(id) {
    var rec = MPower.person.grid.getSelectionModel().getSelected();
    Ext.get(document.body).mask('Loading ' + rec.data.first + ' ' + rec.data.last);
    window.location.href = "person.htm?personId=" + id;
    return false;
}

MPower.person.majorDonorRenderer = function(val, meta, record) {

    if (record.data.majorDonor) {
        meta.css = 'green-dot';
        meta.attr = 'ext:qtip="Major Donor"';
    } else {
        meta.css = 'grey-dot';
        meta.attr = 'ext:qtip="Normal Donor"';
    }
};

MPower.person.lapsedDonorRenderer = function(val, meta, record) {

    if (record.data.lapsedDonor) {
        meta.css = 'red-dot';
        meta.attr = 'ext:qtip="Lapsed Donor"';
    } else {
        meta.css = 'grey-dot';
        meta.attr = 'ext:qtip="Current Donor"';
    }
};


