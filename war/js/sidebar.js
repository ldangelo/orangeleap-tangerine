Ext.namespace('MPower');

Ext.onReady(function() {

    Ext.BLANK_IMAGE_URL = 'js/extjs/resources/images/default/s.gif';
    Ext.QuickTips.init();

    MPower.accountStore = new Ext.data.JsonStore({
        fields:[
            {name: 'id', type: 'int'},
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

    MPower.accountStore.addListener('load', function() {
        Ext.get('sbAllAccounts').update('(' + MPower.accountStore.getCount() +')');

    });


    MPower.accountGrid = new Ext.grid.GridPanel({
        header: false,
        store: MPower.accountStore,
        columns:[
            {id: 'nameColumn', header: 'First', width: 100, sortable: true, dataIndex: 'first', align: 'left'},
            {header: 'Last', width: 100, sortable: true, dataIndex: 'last', align: 'left'},
            {header: 'Organization', width: 100, sortable: true, dataIndex: 'orgName', align: 'left'},
            {header: 'Gifts', width: 55, sortable: true, dataIndex: 'gifts', align: 'right'},
            {header: 'Total', width: 80, sortable: true, dataIndex: 'amount', align: 'right', renderer: Ext.util.Format.usMoney},
            {header: 'Major', width: 65, sortable: true, dataIndex: 'majorDonor', renderer: MPower.majorDonorRenderer}            
        ],
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        autoExpandColumn: "nameColumn",
        frame: false,
        border: false,
        listeners: {
            rowdblclick: function(grid, row, evt) {
                var rec = grid.getSelectionModel().getSelected();
                MPower.win.hide();
                Ext.get(document.body).mask('Loading ' + rec.data.first + ' ' + rec.data.last);
                window.location.href = "person.htm?personId=" + rec.data.id;
            }
        },
        bbar: ['Double click a row to view details']
    });

    MPower.win = new Ext.Window({
        title: 'My Accounts',
        layout: 'fit',
        width: 540,
        height: 400,
        buttons: [{text: 'Close', handler: function() {MPower.win.hide();}}],
        buttonAlign: 'center',
        modal: true,
        closeAction: 'hide'
    });

    MPower.win.add(MPower.accountGrid);

    Ext.get('sbAllAccountsLink').on('click', function(){MPower.win.show(this)});

    MPower.accountStore.load();

});

MPower.majorDonorRenderer = function(val, meta, record) {

    if (record.data.majorDonor) {
        meta.css = 'green-dot';
        meta.attr = 'ext:qtip="Major Donor"';
    } else {
        meta.css = 'grey-dot';
        meta.attr = 'ext:qtip="Normal Donor"';
    }
};

MPower.lapsedDonorRenderer = function(val, meta, record) {

    if (record.data.lapsedDonor) {
        meta.css = 'red-dot';
        meta.attr = 'ext:qtip="Lapsed Donor"';
    } else {
        meta.css = 'grey-dot';
        meta.attr = 'ext:qtip="Current Donor"';
    }
};
