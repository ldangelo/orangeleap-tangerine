Ext.ns('OrangeLeap');
OrangeLeap.RowGrip = function(config){
    Ext.apply(this, config);
    if (this.rowspan){
        this.renderer = this.renderer.createDelegate(this);
    }
};

OrangeLeap.RowGrip.prototype = {
    /**
     * @cfg {String} header Any valid text or HTML fragment to display in the header cell for the row
     * number column (defaults to '').
     */
    header: "",
    /**
     * @cfg {Number} width The default width in pixels of the row number column (defaults to 23).
     */
    width: 15,
    /**
     * @cfg {Boolean} sortable True if the row number column is sortable (defaults to false).
     * @hide
     */
    sortable: false,

    // private
    fixed:true,
    menuDisabled:true,
    dataIndex: '',
    id: 'grip',
    rowspan: undefined,
    tooltip: 'Click and Hold to Drag Row',

    // private
    renderer : function(v, p, record, rowIndex){
        return "<div class='rowGrip'></div>";
    }
};