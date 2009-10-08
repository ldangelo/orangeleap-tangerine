Ext.override(Ext.data.Store, {
	insert : function(index, records){
		records = [].concat(records);
		var snapshotIx;
		if(this.snapshot){
			snapshotIx = index ? this.snapshot.indexOf(this.getAt(index - 1)) + 1 : 0;
		}
		for(var i = 0, len = records.length; i < len; i++){
			this.data.insert(index, records[i]);
			if(this.snapshot){
				this.snapshot.insert(snapshotIx, records[i]);
			}
			records[i].join(this);
		}
		this.fireEvent("add", this, records, index);
	},
	getById : function(id){
		return (this.snapshot || this.data).key(id);
	}
}); 

Ext.ns('Ext.ux.dd');

Ext.ux.dd.GridDropTarget = function(grid, config){
 	Ext.ux.dd.GridDropTarget.superclass.constructor.call(this, grid, config);
};

Ext.extend(Ext.ux.dd.GridDropTarget, Ext.dd.DropTarget, {
    notifyDrop: function(dd, e, data){
        var t = e.getTarget();
        var rindex = this.grid.view.findRowIndex(t);
        var ds = this.grid.getStore();

        for (i = 0; i < data.selections.length; i++) {
            ds.remove(ds.getById(data.selections[i].id));
        }
        ds.insert(rindex, data.selections);

        for (var x = 0; x < ds.data.length; x++) {
            var rec = ds.getAt(x);
            rec.set('itemOrder', x + 1);
        }

		var sm = this.grid.getSelectionModel();
        if (sm) {
            sm.selectRecords(data.selections);
        }
        this.grid.getView().refresh();
        return true;
    },

    notifyOver: function(dd, e, data){
        return this.dropAllowed;
    }
});
