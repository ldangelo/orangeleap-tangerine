Ext.namespace('GiftSummary');

Ext.onReady(function() {

    Ext.QuickTips.init();

    var objectId = /constituentId=(\d+)/g.exec(document.location.search);
    var baseParams = {};

    if(objectId) {
        baseParams.constituentId = objectId[1];
    }

    

    
    var header = 'Gift Summary';

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
            {name: 'currencyCode', mapping: 'currencyCode', type: 'string'},
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
            {header: 'Statistic', width: 200, dataIndex: 'attribute', sortable: false},
            {header: 'Time Period', width: 100, dataIndex: 'series', sortable: false},
            {header: 'Start Date', width: 65, dataIndex: 'startDate', sortable: false, renderer: Ext.util.Format.dateRenderer('m-d-y')},
            {header: 'End Date', width: 65, dataIndex: 'endDate', sortable: false, renderer: Ext.util.Format.dateRenderer('m-d-y')},
            {header: 'Currency', width: 65, dataIndex: 'currencyCode', sortable: false},
            {header: 'Count', width: 45, dataIndex: 'count', sortable: false},
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

    
    var tabs = new Ext.TabPanel({
    	renderTo: 'giftSummaryTab',
    	activeTab : 0,
    	deferredRender: false,
    	autoTabs : true,
    	items : [
    	         {
    	            title:'Hard Gifts',
    	            html : '',
    	            attributeList : '|First Gift|Last Gift|Largest Gift|Gift Amount|'
    	         },
    	         {
      	            title:'Soft Gifts',
    	            html : '',
        	        attributeList : '|First Soft Gift|Last Soft Gift|Largest Soft Gift|Soft Gift Amount|'
      	         },
    	         {
      	            title:'Pledges',
    	            html : '',
            	    attributeList : '|Projected Pledge Amount|'
      	         },
    	         {
      	            title:'Recurring Gifts',
    	            html : '',
            	    attributeList : '|Projected Recurring Gift Amount|'
      	         },
    	         {
       	            title:'Gifts In Kind',
    	            html : '',
            	    attributeList : '|First Gift In Kind|Last Gift In Kind|Largest Gift In Kind|Gift In Kind Amount|'
       	         },
    	         {
        	        title:'Overall',
     	            html : '',
             	    attributeList : '|All Actuals|All Projected|'
        	     },
       	         {
       	            title:'All',
    	            html : '',
        	        attributeList : ''
       	         }
    	         ]
    	});
    
    tabs.addListener( 'tabchange', function (activePanel) {
		GiftSummary.store.load({params: {start: 0, limit: 1000, sort: 'id', dir: 'ASC', attributeList: this.activeTab.attributeList }});
    });

    GiftSummary.store.load({params: {start: 0, limit: 1000, sort: 'id', dir: 'ASC', attributeList: '|First Gift|Last Gift|Gift Amount|' }});
    

});






