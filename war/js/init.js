// This script ensures the blank image URL for ExtJS points to the local s.gif

Ext.BLANK_IMAGE_URL = 'js/extjs/resources/images/default/s.gif';

Ext.onReady(function() {
    if (Ext.isIE6 || Ext.isIE7) {
        // No state manager support for IE browsers before 8
    }
    else {
        Ext.state.Manager.setProvider(new Ext.ux.state.PersistStateProvider());
    }
});


