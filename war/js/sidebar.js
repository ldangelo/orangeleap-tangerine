Ext.namespace('MPower');

Ext.onReady(function() {

    Ext.BLANK_IMAGE_URL = 'js/extjs/resources/images/default/s.gif';
    Ext.QuickTips.init();

    MPower.accountStore = new Ext.data.JsonStore({
        fields:[
            {name: 'id', type: 'int'},
            {name: 'first', type: 'string'},
            {name: 'last', type: 'string'},
            {name: 'majorDonor', type: 'boolean'},
            {name: 'lapsedDonor', type: 'boolean'}
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
            {id: 'nameColumn', header: 'First', width: 100, sortable: true, dataIndex: 'first'},
            {header: 'Last', width: 100, sortable: true, dataIndex: 'last'},
            {header: 'Major', width: 70, sortable: true, dataIndex: 'majorDonor', renderer: MPower.majorDonorRenderer},
            {header: 'Lapsed', width: 70, sortable: true, dataIndex: 'lapsedDonor', renderer: MPower.lapsedDonorRenderer}
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
        width: 350,
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
        meta.attr = 'ext:qtip="Inactive Account"';
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
