 /**
  * @class Ext.ux.dd.GridDropTarget
  * @extends Ext.dd.DropTarget
  * @since 05/13/08
  * @author Eliezer Reis
  * @msn eliezerreis@hotmail.com
  */
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

		var sm = this.grid.getSelectionModel();
        if (sm)
            sm.selectRecords(data.selections);

        this.grid.getView().refresh();
        return true;
    },

    notifyOver: function(dd, e, data){
        return this.dropAllowed;
    }
});