Ext.override(Ext.grid.PropertyColumnModel, {
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
        return Ext.util.Format.htmlEncode(rv);
    }
});
Ext.override(Ext.grid.PropertyGrid, {
    initComponent : function() {
        this.customRenderers = this.customRenderers || {};
        this.customEditors = this.customEditors || {};
        this.lastEditRow = null;
        var store = new Ext.grid.PropertyStore(this);
        this.propStore = store;
        var cm = new Ext.grid.PropertyColumnModel(this, store);
        store.store.sort('name', 'ASC');
        this.addEvents(
            'beforepropertychange',
            'propertychange'
        );
        this.cm = cm;
        this.ds = store.store;
        Ext.grid.PropertyGrid.superclass.initComponent.call(this);
        this.mon(this.selModel, 'beforecellselect', function(sm, rowIndex, colIndex){
            if (colIndex === 0){
                this.startEditing.defer(200, this, [rowIndex, 1]);
                return false;
            }
        }, this);
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
    }
});