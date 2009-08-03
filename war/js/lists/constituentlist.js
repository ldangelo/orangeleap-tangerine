Ext.namespace('OrangeLeap.constituent');

Ext.onReady(function(){

    Ext.QuickTips.init();

     OrangeLeap.constituent.store = new Ext.data.JsonStore({
    	url: 'constituentList.json',
        totalProperty: 'totalRows',
    	root: 'rows',
        fields: [
            {name: 'id', mapping: 'id', type: 'int'},
            {name: 'accountNumber', mapping: 'accountNumber', type: 'string'},
            {name: 'first', mapping: 'first', type: 'string'},
            {name: 'last', mapping: 'last', type: 'string'},
            {name: 'organization', mapping: 'organization', type: 'string'},
            {name: 'majorDonor', mapping: 'majorDonor', type: 'boolean'},
            {name: 'lapsedDonor', mapping: 'lapsedDonor', type: 'boolean'}
        ],
    	sortInfo:{field: 'id', direction: "ASC"},
         remoteSort: true
    });

    OrangeLeap.constituent.pagingBar = new Ext.PagingToolbar({
        pageSize: 100,
        store: OrangeLeap.constituent.store,
        displayInfo: true,
        displayMsg: 'Displaying constituents {0} - {1} of {2}',
        emptyMsg: "No constituents to display"
    });


    OrangeLeap.constituent.grid = new Ext.grid.GridPanel({

        store: OrangeLeap.constituent.store,
        columns: [
            {header: 'View', width: 40, sortable: false, menuDisabled: true, fixed: true, renderer: OrangeLeap.constituent.constituentViewRenderer},
            {header: 'Account', width: 60, dataIndex: 'accountNumber', sortable: true, align: 'right'},
            {header: 'First Name', width: 90, dataIndex: 'first', sortable: true},
            {header: 'Last Name', width: 90, dataIndex: 'last', sortable: true},
            {header: 'Organization', width: 120, dataIndex: 'organization', sortable: true},
            {header: 'Major', tooltip: 'Major Donors', width: 55, align: 'center', dataIndex: 'majorDonor', sortable: true, renderer: OrangeLeap.constituent.majorDonorRenderer},
            {header: 'Lapsed', tooltip: 'Lapsed Donors',width: 55, align: 'center', dataIndex: 'lapsedDonor', sortable: true, renderer: OrangeLeap.constituent.lapsedDonorRenderer}
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
                window.location.href = "constituent.htm?constituentId=" + rec.data.id;
            }
        },
        bbar: OrangeLeap.constituent.pagingBar,
		renderTo: 'constituentListGrid'
	    //tools: [{id: 'refresh', qtip: 'Refresh list', handler: function(){OrangeLeap.constituent.store.reload();}}]
    });

    OrangeLeap.constituent.store.load({params: {start: 0, limit: 100}});

});

OrangeLeap.constituent.constituentViewRenderer = function(val, meta, record) {
    return '<a href="javascript:OrangeLeap.constituent.navigate(' + record.data.id + ')" title="View Constituent">View</a>';
//    return '<a href="constituent.htm?constituentId=' + record.data.id + '">View</a>';
};

OrangeLeap.constituent.navigate = function(id) {
    var rec = OrangeLeap.constituent.grid.getSelectionModel().getSelected();
    OrangeLeap.constituent.grid.getEl().mask('Loading ' + rec.data.first + ' ' + rec.data.last);
    window.location.href = "constituent.htm?constituentId=" + id;
    return false;
}

OrangeLeap.constituent.majorDonorRenderer = function(val, meta, record) {

    if (record.data.majorDonor) {
        meta.css = 'green-dot';
        meta.attr = 'ext:qtip="Major Donor"';
    } else {
        meta.css = 'grey-dot';
        meta.attr = 'ext:qtip="Normal Donor"';
    }
};

OrangeLeap.constituent.lapsedDonorRenderer = function(val, meta, record) {

    if (record.data.lapsedDonor) {
        meta.css = 'red-dot';
        meta.attr = 'ext:qtip="Lapsed Donor"';
    } else {
        meta.css = 'grey-dot';
        meta.attr = 'ext:qtip="Current Donor"';
    }
};


