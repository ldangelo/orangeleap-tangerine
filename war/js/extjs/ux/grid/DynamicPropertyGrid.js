Ext.ns('OrangeLeap');

OrangeLeap.DynamicPropertyColumnModel = function(grid, store){
    var g = Ext.grid,
        f = Ext.form;

    this.grid = grid;
    OrangeLeap.DynamicPropertyColumnModel.superclass.constructor.call(this, [
        { header: this.nameText, width: 50, sortable: true, dataIndex: 'name', id: 'name', menuDisabled: true },
        { header: this.valueText, width: 50, resizable: false, dataIndex: 'value', id: 'value', menuDisabled: true },
        { header: ' ', width: 25, menuDisabled: true, fixed: true, dataIndex: 'name', id: 'deleteLink' }
    ]);
    this.store = store;

    this.editors = {
        'date' : new g.GridEditor(new f.DateField({selectOnFocus:true})),
        'string' : new g.GridEditor(new f.TextField({selectOnFocus:true})),
        'number' : new g.GridEditor(new f.NumberField({selectOnFocus:true, style:'text-align:left;'})),
        'boolean' : new g.GridEditor(new f.Field({
            autoCreate: {tag: 'select', children: [
                {tag: 'option', value: 'true', html: 'true'},
                {tag: 'option', value: 'false', html: 'false'}
            ]},
            getValue : function(){
                return this.el.value == 'true';
            }
        }))
    };
    this.renderCellDelegate = this.renderCell.createDelegate(this);
    this.renderPropDelegate = this.renderProp.createDelegate(this);
    this.renderDeleteFunc = function(name, dom, rec, rowIndex, colCount, store) {
        return '<a href="javascript:void(0)" class="deleteLink" id="delete-link-' + rec.id + '" title="Remove Criteria">Remove</a>';
    }
};

Ext.extend(OrangeLeap.DynamicPropertyColumnModel, Ext.grid.ColumnModel, {
    nameText : 'Name',
    valueText : 'Value',
    dateFormat : 'm/j/Y',

    // private
    renderDate : function(dateVal){
        return dateVal.dateFormat(this.dateFormat);
    },

    // private
    renderBool : function(bVal){
        return bVal ? 'true' : 'false';
    },

    // private
    renderProp : function(v){
        return this.getPropertyName(v);
    },

    renderCell : function(val, meta, r) {
        var renderer = this.grid.customRenderers[r.get('name')];
        if (renderer) {
            return renderer.apply(this, arguments);
        }
        var rv = val;
        if (Ext.isDate(val)) {
            rv = this.renderDate(val);
        }
        else if (typeof val == 'boolean') {
            rv = this.renderBool(val);
        }
        if (Ext.isEmpty(val)) {
            // No value so highlight this cell as in an error state
            meta.css += ' x-form-invalid';
            meta.attr = 'ext:qtip="A value is required"; ext:qclass="x-form-invalid-tip"'; // TODO: use message bundle
        }
        else {
            meta.css = '';
            meta.attr = 'ext:qtip=""';
        }
        return Ext.util.Format.htmlEncode(rv);
    },

    // private
    getPropertyName : function(name){
        var pn = this.grid.propertyNames;
        return pn && pn[name] ? pn[name] : name;
    },

    // private
    getRenderer : function(col){
        var f = this.renderPropDelegate;
        if (col == 1) {
            f = this.renderCellDelegate;
        }
        else if (col == 2) {
            f = this.renderDeleteFunc;
        }
        return f;
    },

    isCellEditable : function(colIndex, rowIndex) {
        var isEditable = false;
        if (colIndex == 0 || colIndex == 1) {
            isEditable = true;
        }
        return isEditable;
    },

    getCellEditor : function(colIndex, rowIndex) {
        if (colIndex == 0) {
            var thisStore = this.store.store;
            var thisGrid = this.grid;
            Ext.override(Ext.form.ComboBox, {
                onViewClick : function(doFocus) {
                    // Don't allow previously selected items in the combo box to be selected again (for another row)
                    var index = this.view.getSelectedIndexes()[0];
                    var r = this.store.getAt(index);
                    var sel = thisStore.find('name', r.get('name'));
                    if (sel > -1) {
                        return;
                    }
                    r.set('selected', false);
                    if (r) {
                        this.onSelect(r, index);
                    }
                    if (doFocus !== false){
                        this.el.focus();
                    }
                }
            });
            var combo = new Ext.form.ComboBox({
                name: 'category',
                allowBlank: false,
                store: thisGrid.updatableFieldsStore,
                displayField: 'desc',
                valueField: 'name',
                typeAhead: true,
                mode: 'local',
                triggerAction: 'all',
                selectOnFocus: true,
                forceSelection: true,
                stateId: 'combo',
                stateful: true,
                stateEvents: ['select'],
                tpl: '<tpl for=".">' +
                    '<div ext:qtip="{desc}" class="x-combo-list-item ' +
                        '<tpl if="selected === true">' +
                            'x-combo-list-item-unsel' +
                        '</tpl>' +
                    '">{desc}</div>' +
                    '</tpl>'
            });
            combo.on('beforeselect', function(comboBox, record, index) {
                var prevVal = comboBox.getValue();
                if (prevVal) {
                    var ind = this.store.find('name', prevVal);
                    if (ind > -1) {
                        var r = this.store.getAt(ind);
                        r.set('selected', false); // reset the other previously selected record to not selected
                    }
                }
            });
            combo.on('select', function(comboBox, comboBoxRecord, index) {
                comboBoxRecord.set('selected', true);
                var rec = thisStore.getAt(rowIndex);
                rec.set('value', ''); // clear the old value and set focus on the value column to allow the user to pick a value
                thisGrid.startEditing.defer(200, thisGrid, [rowIndex, 1]);
            });
            return new Ext.grid.GridEditor(combo);
        }
        else if (colIndex == 1) {
            var rec = this.store.store.getAt(rowIndex);
            var name = rec.get('name');
            if (this.grid.customEditors[name]) {
                return this.grid.customEditors[name];
            }
            return this.editors.string;
        }
    },

    // inherit docs
    destroy : function(){
        OrangeLeap.DynamicPropertyColumnModel.superclass.destroy.call(this);
        for (var ed in this.editors){
            Ext.destroy(ed);
        }
    }
});

OrangeLeap.DynamicPropertyGrid = function(config) {
    this.updatableFieldsStore = config.updatableFieldsStore;
 	OrangeLeap.DynamicPropertyGrid.superclass.constructor.call(this, config);

    var myself = this;
    this.updatableFieldsStore.on('load', function(rec, options) {
        if (rec && myself.getStore().getCount() != rec.data.length) {
            myself.addCriteriaButton.enable();
        }
        else {
            myself.addCriteriaButton.disable();
        }
    });
};

Ext.extend(OrangeLeap.DynamicPropertyGrid, Ext.grid.EditorGridPanel, {
    enableColumnMove: false,
    stripeRows: false,
    trackMouseOver: false,
    clicksToEdit: 1,
    enableHdMenu: false,
    viewConfig: {
        forceFit:true
    },

    initComponent : function() {
        this.customRenderers = this.customRenderers || {};
        this.customEditors = this.customEditors || {};
        this.lastEditRow = null;
        var store = new Ext.grid.PropertyStore(this);
        this.propStore = store;
        var cm = new OrangeLeap.DynamicPropertyColumnModel(this, store);
        store.store.sort('name', 'ASC');
        this.addEvents(
            'beforepropertychange',
            'propertychange'
        );
        this.cm = cm;
        this.ds = store.store;
        var thisGrid = this;

        this.tbar = [{
                text: 'Add Criteria',
                tooltip: 'Add Criteria to Update Field Value',
                iconCls: 'add',
                id: 'addCriteriaButton',
                ref: '../addCriteriaButton',
                handler: function() {
                    var thisStore = thisGrid.getStore();
                    var newRec = new thisStore.recordType({
                        name: '',
                        value: ''
                    });
                    thisGrid.stopEditing();
                    var nextIndex = thisStore.getCount();
                    thisStore.add(newRec);
                    thisGrid.startEditing(nextIndex, 0);
                    if ((nextIndex + 1) == thisGrid.updatableFieldsStore.getCount()) {
                        this.disable();
                    }
                    else {
                        this.enable();
                    }
                }
            }
        ];

        OrangeLeap.DynamicPropertyGrid.superclass.initComponent.call(this);

        this.mon(this, 'click', function(event){
            var target = event.getTarget('a.deleteLink');
            if (target) {
                event.stopPropagation();
                var id = target.id;
                if (id) {
                    id = id.replace('delete-link-', '');
                    var rec = this.store.getById(id);
                    var fldRecIndex = this.updatableFieldsStore.find('name', rec.get('name'));
                    if (fldRecIndex > -1) {
                        var fldRec = this.updatableFieldsStore.getAt(fldRecIndex);
                        fldRec.set('selected', false);
                    }
                    this.store.remove(rec);
                    if (this.store.getCount() != this.updatableFieldsStore.getCount()) {
                        this.addCriteriaButton.enable();
                    }
                }
            }
        }, this);
        this.mon(this, 'keydown', function(event) {
            if (e.getKey() == e.A && e.altKey) {
                Ext.getCmp('addCriteriaButton').handler();
            }
        });
    },
    
    setProperty: function(property, value) {
        this.propStore.source[property] = value;
        var r = this.propStore.store.getById(property);
        if (r) {
            r.set('value', value);
        }
        else{
            r = new Ext.grid.PropertyRecord({name: property, value: value}, property);
            this.propStore.store.add(r);
        }
    },

    removeProperty: function(property) {
        delete this.propStore.source[property];
        var r = this.propStore.store.getById(property);
        if (r) {
            this.propStore.store.remove(r);
        }
    },

    // private
    onRender : function(){
        OrangeLeap.DynamicPropertyGrid.superclass.onRender.apply(this, arguments);
        this.getGridEl().addClass('x-props-grid');
    },

    // private
    afterRender: function(){
        OrangeLeap.DynamicPropertyGrid.superclass.afterRender.apply(this, arguments);
        if (this.source){
            this.setSource(this.source);
        }
    },

    setSource : function(source){
        this.propStore.setSource(source);
    },

    getSource : function(){
        return this.propStore.getSource();
    }
});