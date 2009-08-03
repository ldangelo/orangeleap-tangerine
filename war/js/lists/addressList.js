Ext.namespace('AddressList');
Ext.namespace('OrangeLeap.communication');

Ext.onReady(function() {

    Ext.QuickTips.init();

    var constituentId = /constituentId=(\d+)/g.exec(document.location.search);
    var baseParams = {};

    if(constituentId) {
        baseParams.constituentId = constituentId[1];
    }

    var header = 'Address List';

    AddressList.store = new Ext.data.JsonStore({
        url: 'addressList.json',
        totalProperty: 'totalRows',
        root: 'rows',
        fields: [
            {name: 'id', mapping: 'id', type: 'int'},
            {name: 'constituentId', mapping: 'constituentId', type: 'string'},
            {name: 'addressLine1', mapping: 'addressLine1', type: 'string'},
            {name: 'city', mapping: 'city', type: 'string'},
            {name: 'stateProvince', mapping: 'stateProvince', type: 'string'},
            {name: 'postalCode', mapping: 'postalCode', type: 'string'},
            {name: 'country', mapping: 'country', type: 'string'},
            {name: 'receiveCorrespondence', mapping: 'receiveCorrespondence', type: 'boolean'},
            {name: 'current', mapping: 'current', type: 'boolean'},
            {name: 'primary', mapping: 'primary', type: 'boolean'},
            {name: 'active', mapping: 'active', type: 'boolean'}
        ],
        sortInfo:{field: 'addressLine1', direction: "ASC"},
        remoteSort: true,
        baseParams: baseParams
    });

    AddressList.pagingBar = new Ext.PagingToolbar({
        pageSize: 300,
        store: AddressList.store,
        displayInfo: true,
        displayMsg: 'Displaying address list {0} - {1} of {2}',
        emptyMsg: "No addresses to display"
    });

    AddressList.grid = new Ext.grid.GridPanel({

        store: AddressList.store,
        columns: [
            {header: '', width: 30, dataIndex: 'id', sortable: false, menuDisabled: true, renderer: AddressList.entityViewRenderer},
            {header: 'Addr', width: 80, tooltip: 'Address Line 1', dataIndex: 'addressLine1', sortable: true },
            {header: 'City', width: 50, tooltip: 'City', dataIndex: 'city', sortable: true },
            {header: 'State', width: 40, tooltip: 'State/Province', dataIndex: 'stateProvince', sortable: true},
            {header: 'Zip Code', width: 30, tooltip: 'Zip/Postal Code', dataIndex: 'postalCode', sortable: true},
            {header: 'Country', width: 30, tooltip: 'Country', dataIndex: 'country', sortable: true},
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
        autoExpandColumn: 'addressLine1',
        frame: true,
        header: true,
        title: header,
        loadMask: true,
        bbar: AddressList.pagingBar,
        renderTo: 'addressListGrid'
    });

    AddressList.store.load({params: {start: 0, limit: 300, sort: 'addressLine1', dir: 'ASC'}});

});

AddressList.entityViewRenderer = function(val, meta, record) {
	   return '<a href="javascript:AddressList.navigate(' + record.data.id + ')" title="View">View</a>';
};

AddressList.navigate = function(id) {
	var rec = AddressList.grid.getSelectionModel().getSelected();
	AddressList.grid.getGridEl().mask('Loading Record');
	
    window.location.href= 'addressManagerEdit.htm?constituentId=' + rec.data.constituentId + "&addressId=" + rec.data.id;
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
