Ext.namespace('Contacts');

Ext.onReady(function() {
    var pId = /constituentId=(\d+)/g.exec(document.location.search);
    if (pId) {	
	    Ext.BLANK_IMAGE_URL = "js/extjs/resources/images/default/s.gif";
	    Ext.form.Field.prototype.msgTarget = 'side';
	    Ext.QuickTips.init();
	    Contacts.constituentId = pId[1];

        /******************************************************
         * Common Renderers                                   *
         ******************************************************/

        var addressRenderer = function(val, meta, record) {
            return Contacts.addressTemplate.apply(record.data);
        };

        var phoneRenderer = function(val, meta, record) {
            return Contacts.phoneTemplate.apply(record.data);
        };

        var emailRenderer = function(val, meta, record) {
            return Contacts.emailTemplate.apply(record.data);
        };

        // renders a Star in the first column if the element is the
        // primary entity
        var primaryRenderer = function(val, meta, record) {
            if (record.data.primary) {
                meta.css = "yellow-star";
                meta.attr = "ext:qtip='Preferred'";
            }
        };

        // renders a green ball in the second column is the element
        // is active, otherwise a gray gall
        var activeRenderer = function(val, meta, record) {
            if (record.data.active) {
                meta.css = "green-dot";
                meta.attr = "ext:qtip='Active'";
            } else {
                meta.css = "grey-dot";
                meta.attr = "ext:qtip='Inactive'";
            }
        };

	    /******************************************************
	     * Configuration for the Address Information          *
	     ******************************************************/
	    Contacts.addressStore = new Ext.data.JsonStore({
	        fields:[
	            {name: 'id', type: 'int'},
	            {name: 'street1'},
	            {name: 'street2'},
	            {name: 'city'},
	            {name: 'state'},
	            {name: 'zip'},
	            {name: 'comment'},
	            {name: 'active', type: 'boolean'},
	            {name: 'primary', type: 'boolean'}
	        ],
	        sortInfo:{field: 'primary', direction: "DESC"},
	        url: 'addresses.json',
	        root: 'data'
	    });
	
	    Contacts.addressGrid = new Ext.grid.GridPanel({
	        header: true,
	        title: 'Mailing Addresses',
	        store: Contacts.addressStore,
	        columns:[
	            {header: 'Primary', width: 20, renderer: primaryRenderer},
	            {header: 'Active', width: 20, renderer: activeRenderer},
	            {id: 'streetAddress', header: 'Address', renderer: addressRenderer}
	        ],
	        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
	        autoExpandColumn: "streetAddress",
	        frame: false,
	        border: true,
	        iconCls: 'address-book',
	        listeners: {
	            rowdblclick: function(grid, row, evt) {
	                var rec = Contacts.addressGrid.getSelectionModel().getSelected();
	                // used for easter-egg mode
	                Contacts.win.hide();
	                Ext.get(document.body).mask();
	                window.location.href = "addressManager.htm?constituentId=" + Contacts.constituentId;
	            }
	        },
	        hideHeaders: true
	    });
	
	
	
	    Contacts.addressTemplate = new Ext.XTemplate(
	            '<p><strong  ext:qtitle="Comment" ext:qwidth="150" ext:qtip="{comment}">{street1}</strong></p>',
	            '<tpl if="street2 != null"><p>{street2}</p></tpl>',
	            '<p>{city},{state} {zip}</p>');
	    Contacts.addressTemplate.compile();
	
	
	
	    /******************************************************
	     * Configuration for the Phone Information            *
	     ******************************************************/
	    Contacts.phoneStore = new Ext.data.JsonStore({
	        fields: [
	            {name: 'id', type: 'int'},
	            {name: 'type'},
	            {name: 'number'},
	            {name: 'comment'},
	            {name: 'active'},
	            {name: 'primary'}
	        ],
	        sortInfo:{field: 'primary', direction: "DESC"},
	        url: 'phones.json',
	        root: 'data'
	
	    });
	
	    Contacts.phoneGrid = new Ext.grid.GridPanel({
	        header: true,
	        title: 'Phone Numbers',
	        store: Contacts.phoneStore,
	        columns:[
	            {width: 20, sortable: true, renderer: primaryRenderer},
	            {width: 20, sortable: true, renderer: activeRenderer},
	            {id: 'phoneNumbers', renderer: phoneRenderer}
	        ],
	        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
	        autoExpandColumn: "phoneNumbers",
	        frame: false,
	        border: true,
	        iconCls: 'telephone',
	        listeners: {
	            rowdblclick: function(grid, row, evt) {
	                var rec = Contacts.phoneGrid.getSelectionModel().getSelected();
	                // used for easter-egg mode
	                Contacts.win.hide();
	                Ext.get(document.body).mask();
	                window.location.href = "phoneManager.htm?constituentId=" + Contacts.constituentId;
	            }
	        },
	        hideHeaders: true
	    });
	
	    Contacts.phoneTemplate = new Ext.XTemplate(
	            '<p class="left"><strong>{type}</strong></p>',
	            '<p class="left x-indented"><code ext:qtitle="Comment" ext:qwidth="150" ext:qtip="{comment}">{number}</code></p>');
	    Contacts.phoneTemplate.compile();
	
	
	
	    /******************************************************
	     * Configuration for the Email Information            *
	     ******************************************************/
	    Contacts.emailStore = new Ext.data.JsonStore({
	        fields: [
	            {name: 'id', type: 'int'},
	            {name: 'type'},
	            {name: 'address'},
	            {name: 'comment'},
	            {name: 'active'},
	            {name: 'primary'}
	        ],
	        sortInfo:{field: 'primary', direction: "DESC"},
	        url: 'emails.json',
	        root: 'data'
	    });
	
	    Contacts.emailGrid = new Ext.grid.GridPanel({
	        header: true,
	        title: 'Email Addresses',
	        store: Contacts.emailStore,
	        columns:[
	            {width: 20, sortable: true, renderer: primaryRenderer},
	            {width: 20, sortable: true, renderer: activeRenderer},
	            {id: 'emailAddresses', renderer: emailRenderer}
	        ],
	        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
	        autoExpandColumn: "emailAddresses",
	        frame: false,
	        border: true,
	        iconCls: 'email',
	        listeners: {
	            rowdblclick: function(grid, row, evt) {
	                var rec = Contacts.emailGrid.getSelectionModel().getSelected();
	                // used for easter-egg mode
	                Contacts.win.hide();
	                Ext.get(document.body).mask();
	                window.location.href = "emailManager.htm?constituentId=" + Contacts.constituentId;
	            }
	        },
	        hideHeaders: true,
	        enableHdMenu: false
	    });
	
	
	
	    Contacts.emailTemplate = new Ext.XTemplate(
	            '<p class="left"><strong>{type}</strong></p>',
	            '<p class="left x-indented"><code ext:qtitle="Comment" ext:qwidth="150" ext:qtip="{comment}">{address}<code></p>');
	    Contacts.emailTemplate.compile();
	
	
	
	
	    /******************************************************
	     * Configuration for the Accordion Panel              *
	     ******************************************************/
	    Contacts.accordionPanel = new Ext.Panel({
	        layout: 'accordion',
	        header: false,
	        id: 'contactInfoPanel',
	        //width: 300,
	        //height: 350,
	        //renderTo: 'contactInfoPanel',
	        items:[Contacts.addressGrid, Contacts.phoneGrid, Contacts.emailGrid],
	        bbar: [{xtype: 'tbtext', text: 'Double click to edit data'}]
	    });
	
	    Contacts.win = new Ext.Window({
	        layout: 'fit',
	        width: 350,
	        height: 400,
	        title: 'Contact Information',
	        closeAction: 'hide',
	        items: [Contacts.accordionPanel],
            cls: 'ext-widget'
	    });
	
	    Ext.get('constituentTitle').on('dblclick',function() {Contacts.win.show();});
	    
	    // Now load all the stores
	    Contacts.addressStore.load({params: {id: Contacts.constituentId}});
	    Contacts.phoneStore.load({params: {id: Contacts.constituentId}});
	    Contacts.emailStore.load({params: {id: Contacts.constituentId}});
	}

});
