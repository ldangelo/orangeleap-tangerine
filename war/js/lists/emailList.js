Ext.namespace('EmailList');
Ext.namespace('OrangeLeap.communication');

Ext.onReady(function() {

    Ext.QuickTips.init();

    var constituentId = /constituentId=(\d+)/g.exec(document.location.search);
    var baseParams = {};

    if(constituentId) {
        baseParams.constituentId = constituentId[1];
    }

    var header = 'Email List';

    EmailList.store = new Ext.data.JsonStore({
        url: 'emailList.json',
        totalProperty: 'totalRows',
        root: 'rows',
        fields: [
            {name: 'id', mapping: 'id', type: 'int'},
            {name: 'constituentId', mapping: 'constituentId', type: 'string'},
            {name: 'emailAddress', mapping: 'emailAddress', type: 'string'},
            {name: 'emailDisplay', mapping: 'emailDisplay', type: 'string'},
            {name: 'sms', mapping: 'sms', type: 'string'},
            {name: 'receiveCorrespondence', mapping: 'receiveCorrespondence', type: 'boolean'},
            {name: 'receiveCorrespondenceText', mapping: 'receiveCorrespondenceText', type: 'boolean'},
            {name: 'current', mapping: 'current', type: 'boolean'},
            {name: 'primary', mapping: 'primary', type: 'boolean'},
            {name: 'active', mapping: 'active', type: 'boolean'}
        ],
        sortInfo:{field: 'emailAddress', direction: "ASC"},
        remoteSort: true,
        baseParams: baseParams
    });

    EmailList.pagingBar = new Ext.PagingToolbar({
        pageSize: 300,
        store: EmailList.store,
        displayInfo: true,
        displayMsg: 'Displaying email list {0} - {1} of {2}',
        emptyMsg: "No emails to display"
    });

    EmailList.grid = new Ext.grid.GridPanel({

        store: EmailList.store,
        columns: [
            {header: '', width: 30, dataIndex: 'id', sortable: false, menuDisabled: true, renderer: EmailList.entityViewRenderer},
            {header: 'Email', width: 50, tooltip: 'Email Address', dataIndex: 'emailAddress', sortable: true },
            {header: 'Display', width: 70, tooltip: 'emailDisplay', dataIndex: 'emailDisplay', sortable: true},
            {header: 'Rec Corr', tooltip: 'Receive Correspondence', width: 20, align: 'center', dataIndex: 'receiveCorrespondence', sortable: true, renderer: OrangeLeap.communication.receiveCorrespondenceRenderer},
            {header: 'Curr', tooltip: 'Current', width: 20, align: 'center', dataIndex: 'current', sortable: true, renderer: OrangeLeap.communication.currentRenderer},
            {header: 'Prim', tooltip: 'Primary', width: 20, align: 'center', dataIndex: 'primary', sortable: true, renderer: OrangeLeap.communication.primaryRenderer},
            {header: 'Act', tooltip: 'Active', width: 20, align: 'center', dataIndex: 'active', sortable: true, renderer: OrangeLeap.communication.activeRenderer}
        ],
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        viewConfig: {
            forceFit: true
        },
        height:530,
        width: 760,
        autoExpandColumn: 'emailAddress',
        frame: true,
        header: true,
        title: header,
        loadMask: true,
        bbar: EmailList.pagingBar,
        renderTo: 'emailListGrid'
    });

    EmailList.store.load({params: {start: 0, limit: 300, sort: 'emailAddress', dir: 'ASC'}});

});

EmailList.entityViewRenderer = function(val, meta, record) {
	   return '<a href="javascript:EmailList.navigate(' + record.data.id + ')" title="View">View</a>';
};

EmailList.navigate = function(id) {
	var rec = EmailList.grid.getSelectionModel().getSelected();
	EmailList.grid.getGridEl().mask('Loading Record');
	
    window.location.href= 'emailManagerEdit.htm?constituentId=' + rec.data.constituentId + "&emailId=" + rec.data.id;
	return false;
};

OrangeLeap.communication.receiveCorrespondenceRenderer = function(val, meta, record) {
    if (record.data.receiveCorrespondence) {
        meta.css = 'green-dot';
        meta.attr = 'ext:qtip="Receive Correspondence"';
    }
    else {
        meta.css = 'grey-dot';
        meta.attr = 'ext:qtip="Cannot Receive Correspondence"';
    }
};

OrangeLeap.communication.currentRenderer = function(val, meta, record) {
    if (record.data.current) {
        meta.css = 'green-dot';
        meta.attr = 'ext:qtip="Current"';
    }
    else {
        meta.css = 'grey-dot';
        meta.attr = 'ext:qtip="Not Current"';
    }
};

OrangeLeap.communication.primaryRenderer = function(val, meta, record) {
    if (record.data.primary) {
        meta.css = 'green-dot';
        meta.attr = 'ext:qtip="Primary"';
    }
    else {
        meta.css = 'grey-dot';
        meta.attr = 'ext:qtip="Not Primary"';
    }
};

OrangeLeap.communication.activeRenderer = function(val, meta, record) {
    if (record.data.active) {
        meta.css = 'green-dot';
        meta.attr = 'ext:qtip="Active"';
    }
    else {
        meta.css = 'grey-dot';
        meta.attr = 'ext:qtip="Inactive"';
    }
};
