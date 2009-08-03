Ext.namespace('PhoneList');
Ext.namespace('OrangeLeap.communication');

Ext.onReady(function() {

    Ext.QuickTips.init();

    var constituentId = /constituentId=(\d+)/g.exec(document.location.search);
    var baseParams = {};

    if(constituentId) {
        baseParams.constituentId = constituentId[1];
    }

    var header = 'Phone List';

    PhoneList.store = new Ext.data.JsonStore({
        url: 'phoneList.json',
        totalProperty: 'totalRows',
        root: 'rows',
        fields: [
            {name: 'id', mapping: 'id', type: 'int'},
            {name: 'constituentId', mapping: 'constituentId', type: 'string'},
            {name: 'number', mapping: 'number', type: 'string'},
            {name: 'provider', mapping: 'provider', type: 'string'},
            {name: 'sms', mapping: 'sms', type: 'string'},
            {name: 'receiveCorrespondence', mapping: 'receiveCorrespondence', type: 'boolean'},
            {name: 'receiveCorrespondenceText', mapping: 'receiveCorrespondenceText', type: 'boolean'},
            {name: 'current', mapping: 'current', type: 'boolean'},
            {name: 'primary', mapping: 'primary', type: 'boolean'},
            {name: 'active', mapping: 'active', type: 'boolean'}
        ],
        sortInfo:{field: 'number', direction: "ASC"},
        remoteSort: true,
        baseParams: baseParams
    });

    PhoneList.pagingBar = new Ext.PagingToolbar({
        pageSize: 300,
        store: PhoneList.store,
        displayInfo: true,
        displayMsg: 'Displaying phone list {0} - {1} of {2}',
        emptyMsg: "No phones to display"
    });

    PhoneList.grid = new Ext.grid.GridPanel({

        store: PhoneList.store,
        columns: [
            {header: '', width: 30, dataIndex: 'id', sortable: false, menuDisabled: true, renderer: PhoneList.entityViewRenderer},
            {header: 'Num', width: 50, tooltip: 'Phone Number', dataIndex: 'number', sortable: true },
            {header: 'Provider', width: 70, tooltip: 'Provider', dataIndex: 'provider', sortable: true},
            {header: 'SMS', width: 60, tooltip: 'SMS', dataIndex: 'sms', sortable: true},
            {header: 'Rec Call Corr', tooltip: 'Receive Call Correspondence', width: 20, align: 'center', dataIndex: 'receiveCorrespondence', sortable: true, renderer: OrangeLeap.communication.receiveCorrespondenceRenderer},
            {header: 'Rec SMS Corr', tooltip: 'Receive SMS Correspondence', width: 20, align: 'center', dataIndex: 'receiveCorrespondenceText', sortable: true, renderer: OrangeLeap.communication.receiveCorrespondenceTextRenderer},
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
        autoExpandColumn: 'number',
        frame: true,
        header: true,
        title: header,
        loadMask: true,
        bbar: PhoneList.pagingBar,
        renderTo: 'phoneListGrid'
    });

    PhoneList.store.load({params: {start: 0, limit: 300, sort: 'number', dir: 'ASC'}});

});

PhoneList.entityViewRenderer = function(val, meta, record) {
	   return '<a href="javascript:PhoneList.navigate(' + record.data.id + ')" title="View">View</a>';
};

PhoneList.navigate = function(id) {
	var rec = PhoneList.grid.getSelectionModel().getSelected();
	PhoneList.grid.getGridEl().mask('Loading Record');
	
    window.location.href= 'phoneManagerEdit.htm?constituentId=' + rec.data.constituentId + "&phoneId=" + rec.data.id;
	return false;
};

OrangeLeap.communication.receiveCorrespondenceRenderer = function(val, meta, record) {
    if (record.data.receiveCorrespondence) {
        meta.css = 'green-dot';
        meta.attr = 'ext:qtip="Receive Call Correspondence"';
    }
    else {
        meta.css = 'grey-dot';
        meta.attr = 'ext:qtip="Cannot Receive Call Correspondence"';
    }
};

OrangeLeap.communication.receiveCorrespondenceTextRenderer = function(val, meta, record) {
    if (record.data.receiveCorrespondenceText) {
        meta.css = 'green-dot';
        meta.attr = 'ext:qtip="Receive SMS Correspondence"';
    }
    else {
        meta.css = 'grey-dot';
        meta.attr = 'ext:qtip="Cannot Receive SMS Correspondence"';
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
