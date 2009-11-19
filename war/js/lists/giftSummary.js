Ext.namespace('GiftSummary');

Ext.onReady(function() {

    Ext.QuickTips.init();

    var baseParams = {};

    baseParams.id = $('#id').val();

    var header = 'Gift Summary List';

    GiftSummary.store = new Ext.data.JsonStore({
        url: 'giftSummary.json',
        totalProperty: 'totalRows',
        root: 'rows',
        fields: [
            {name: 'id', mapping: 'id', type: 'int'},
            {name: 'attribute', mapping: 'attribute', type: 'string'},
            {name: 'series', mapping: 'series', type: 'string'},
            {name: 'startDate', mapping: 'startDate', type: 'date', dateFormat: 'Y-m-d H:i:s'},
            {name: 'endDate', mapping: 'endDate', type: 'date', dateFormat: 'Y-m-d H:i:s'},
            {name: 'currencycode', mapping: 'currencycode', type: 'string'},
            {name: 'count', mapping: 'count', type: 'int'},
            {name: 'sum', mapping: 'sum', type: 'float'},
            {name: 'min', mapping: 'min', type: 'float'},
            {name: 'max', mapping: 'max', type: 'float'},
            {name: 'avg', mapping: 'avg', type: 'float'}
        ],
        sortInfo:{field: 'id', direction: "ASC"},
        remoteSort: true,
        baseParams: baseParams
    });

    GiftSummary.pagingBar = new Ext.PagingToolbar({
        pageSize: 1000,
        store: GiftSummary.store,
        displayInfo: true,
        //displayMsg: 'Displaying list {0} - {1} of {2}',
        emptyMsg: "No items to display"
    });

    GiftSummary.grid = new Ext.grid.GridPanel({

        store: GiftSummary.store,
        columns: [
            {header: 'Statistic', width: 65, dataIndex: 'attribute', sortable: false},
            {header: 'Time Period', width: 65, dataIndex: 'series', sortable: false},
            {header: 'Start Date', width: 100, dataIndex: 'startDate', sortable: false, renderer: Ext.util.Format.dateRenderer('m-d-y')},
            {header: 'End Date', width: 100, dataIndex: 'endDate', sortable: false, renderer: Ext.util.Format.dateRenderer('m-d-y')},
            {header: 'Currency Code', width: 65, dataIndex: 'currencycode', sortable: false},
            {header: 'Count', width: 65, dataIndex: 'count', sortable: false, renderer: OrangeLeap.amountRenderer },
            {header: 'Total', width: 65, dataIndex: 'sum', sortable: false, renderer: OrangeLeap.amountRenderer },
            {header: 'Average', width: 65, dataIndex: 'avg', sortable: false, renderer: OrangeLeap.amountRenderer },
            {header: 'Min', width: 65, dataIndex: 'min', sortable: false, renderer: OrangeLeap.amountRenderer },
            {header: 'Max', width: 65, dataIndex: 'max', sortable: false, renderer: OrangeLeap.amountRenderer }
        ],
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        viewConfig: {
            forceFit: true
        },
        height:530,
        width: 760,
        frame: true,
        header: true,
        title: header,
        loadMask: true,
        bbar: GiftSummary.pagingBar,
        renderTo: 'giftSummaryGrid'
    });

    GiftSummary.store.load({params: {start: 0, limit: 1000, sort: 'id', dir: 'ASC'}});

});






