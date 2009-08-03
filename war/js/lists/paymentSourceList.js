Ext.namespace('PaymentSourceList');
Ext.namespace('OrangeLeap.communication');

Ext.onReady(function() {

    Ext.QuickTips.init();

    var constituentId = /constituentId=(\d+)/g.exec(document.location.search);
    var baseParams = {};

    if(constituentId) {
        baseParams.constituentId = constituentId[1];
    }

    var header = 'Payment Method List';

    PaymentSourceList.store = new Ext.data.JsonStore({
        url: 'paymentSourceList.json',
        totalProperty: 'totalRows',
        root: 'rows',
        fields: [
            {name: 'id', mapping: 'id', type: 'int'},
            {name: 'constituentId', mapping: 'constituentId', type: 'string'},
            {name: 'paymentProfile', mapping: 'paymentProfile', type: 'string'},
            {name: 'paymentType', mapping: 'paymentType', type: 'string'},
            {name: 'holderName', mapping: 'holderName', type: 'string'},
            {name: 'creditCardType', mapping: 'creditCardType', type: 'string'},
            {name: 'creditCardExpiration', mapping: 'creditCardExpiration', type: 'string'},
            {name: 'routingNumber', mapping: 'routingNumber', type: 'string'},
            {name: 'lastFourDigits', mapping: 'lastFourDigits', type: 'int'},
            {name: 'active', mapping: 'active', type: 'boolean'}
        ],
        sortInfo:{field: 'paymentProfile', direction: "ASC"},
        remoteSort: true,
        baseParams: baseParams
    });

    PaymentSourceList.pagingBar = new Ext.PagingToolbar({
        pageSize: 300,
        store: PaymentSourceList.store,
        displayInfo: true,
        displayMsg: 'Displaying payment methods list {0} - {1} of {2}',
        emptyMsg: "No payment methods to display"
    });

    PaymentSourceList.grid = new Ext.grid.GridPanel({

        store: PaymentSourceList.store,
        columns: [
            {header: '', width: 30, dataIndex: 'id', sortable: false, menuDisabled: true, renderer: PaymentSourceList.entityViewRenderer},
            {header: 'Profile', width: 55, tooltip: 'Payment Profile', dataIndex: 'paymentProfile', sortable: true },
            {header: 'Type', width: 30, tooltip: 'Payment Type', dataIndex: 'paymentType', sortable: true},
            {header: 'Holder', width: 60, tooltip: 'Holder Name', dataIndex: 'holderName', sortable: true},
            {header: 'Cred Type', tooltip: 'Credit Card Type', width: 40, align: 'center', dataIndex: 'creditCardType', sortable: true},
            {header: 'Cred Exp', tooltip: 'Credit Card Expiration', width: 30, align: 'center', dataIndex: 'creditCardExpiration', sortable: true},
            {header: 'Rout Num', tooltip: 'Routing Number', width: 30, align: 'center', dataIndex: 'routingNumber', sortable: true},
            {header: 'Last 4', tooltip: 'Last 4 Digits', width: 20, align: 'center', dataIndex: 'lastFourDigits', sortable: true},
            {header: 'Act', tooltip: 'Active', width: 20, align: 'center', dataIndex: 'active', sortable: true, renderer: OrangeLeap.communication.activeRenderer}
        ],
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        viewConfig: {
            forceFit: true
        },
        height: 530,
        width: 760,
        autoExpandColumn: 'holderName',
        frame: true,
        header: true,
        title: header,
        loadMask: true,
        bbar: PaymentSourceList.pagingBar,
        renderTo: 'paymentSourceListGrid'
    });

    PaymentSourceList.store.load({params: {start: 0, limit: 300, sort: 'paymentProfile', dir: 'ASC'}});

});

PaymentSourceList.entityViewRenderer = function(val, meta, record) {
	   return '<a href="javascript:PaymentSourceList.navigate(' + record.data.id + ')" title="View">View</a>';
};

PaymentSourceList.navigate = function(id) {
	var rec = PaymentSourceList.grid.getSelectionModel().getSelected();
	PaymentSourceList.grid.getGridEl().mask('Loading Record');
	
    window.location.href= 'paymentManagerEdit.htm?constituentId=' + rec.data.constituentId + "&paymentSourceId=" + rec.data.id;
	return false;
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
