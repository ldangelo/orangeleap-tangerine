Ext.namespace('Sidebar');

Ext.onReady(function() {

    Ext.BLANK_IMAGE_URL = 'js/extjs/resources/images/default/s.gif';
    Ext.QuickTips.init();

    Sidebar.accountStore = new Ext.data.JsonStore({
        fields:[
            {name: 'id', type: 'int'},
            {name: 'accountNumber', type: 'string'},
            {name: 'first', type: 'string'},
            {name: 'last', type: 'string'},
            {name: 'orgName', type: 'string'},
            {name: 'majorDonor', type: 'boolean'},
            {name: 'lapsedDonor', type: 'boolean'},
            {name: 'gifts', type: 'int'},
            {name: 'amount', type: 'float'}
        ],
        sortInfo:{field: 'last', direction: 'ASC'},
        url: 'myAccounts.json',
        root: 'data'
    });

    Sidebar.accountStore.addListener('load', function() {
        Ext.get('sbAllAccounts').update('(' + Sidebar.accountStore.getCount() +')');

    });


    Sidebar.accountGrid = new Ext.grid.GridPanel({
        header: false,
        store: Sidebar.accountStore,
        columns:[
            {id: 'nameColumn', header: 'Account', width: 100, sortable: true, dataIndex: 'accountNumber', align: 'left'},
            {header: 'First', width: 100, sortable: true, dataIndex: 'first', align: 'left'},
            {header: 'Last', width: 100, sortable: true, dataIndex: 'last', align: 'left'},
            {header: 'Organization', width: 100, sortable: true, dataIndex: 'orgName', align: 'left'},
            {header: 'Gifts', width: 55, sortable: true, dataIndex: 'gifts', align: 'right'},
            {header: 'Total', width: 80, sortable: true, dataIndex: 'amount', align: 'right', renderer: Ext.util.Format.usMoney},
            {header: 'Major', width: 65, sortable: true, dataIndex: 'majorDonor', renderer: Sidebar.majorDonorRenderer}
        ],
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        autoExpandColumn: "nameColumn",
        frame: false,
        border: false,
        listeners: {
            rowdblclick: function(grid, row, evt) {
                var rec = grid.getSelectionModel().getSelected();
                Sidebar.win.hide(this);
                Ext.get(document.body).mask('Loading ' + rec.data.first + ' ' + rec.data.last);
                window.location.href = "constituent.htm?constituentId=" + rec.data.id;
            }
        },
        bbar: ['Double click a row to view details']
    });

    Sidebar.win = new Ext.Window({
        title: 'My Accounts',
        layout: 'fit',
        width: 650,
        height: 400,
        buttons: [{text: 'Close', handler: function() { Sidebar.win.hide(this); }}],
        buttonAlign: 'center',
        modal: true,
        closable: false,
        closeAction: 'hide'
    });

    Sidebar.win.add(Sidebar.accountGrid);

    Ext.get('sbAllAccountsLink').on('click', function() { Sidebar.win.show(this); });

    Sidebar.accountStore.load();

});

Sidebar.majorDonorRenderer = function(val, meta, record) {

    if (record.data.majorDonor) {
        meta.css = 'green-dot';
        meta.attr = 'ext:qtip="Major Donor"';
    } else {
        meta.css = 'grey-dot';
        meta.attr = 'ext:qtip="Normal Donor"';
    }
};

Sidebar.lapsedDonorRenderer = function(val, meta, record) {

    if (record.data.lapsedDonor) {
        meta.css = 'red-dot';
        meta.attr = 'ext:qtip="Lapsed Donor"';
    } else {
        meta.css = 'grey-dot';
        meta.attr = 'ext:qtip="Current Donor"';
    }
};
