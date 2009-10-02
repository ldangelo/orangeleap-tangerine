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
//        var group = this.grid.view.findGroup(t)
        var ds = this.grid.getStore();

        for (i = 0; i < data.selections.length; i++) {
            ds.remove(ds.getById(data.selections[i].id));
//            data.selections[i].data[ds.groupField] = this.grid.view.getGroupFieldValue(group.id);
        }
//        var lastRowOfGroup = this.grid.view.findLastRowOfGroup(group) ;
//        if (rindex === false) {
//            rindex = lastRowOfGroup + 1;
//            this.grid.view.toggleGroup(group.id);
//        }
        ds.insert(rindex, data.selections);

		var sm = this.grid.getSelectionModel();
        if (sm)
            sm.selectRecords(data.selections);

        return true;
    },

    notifyOver: function(dd, e, data){
        var t = e.getTarget();
        var rowIndex = this.grid.view.findRowIndex(t);
        if (rowIndex === false) {
//            if(!this.grid.view.findGroup(t)){
//                return this.dropNotAllowed;
//            }
        }
        return this.dropAllowed;
    }
});