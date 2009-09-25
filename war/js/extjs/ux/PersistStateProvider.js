Ext.namespace('Ext.ux','Ext.ux.state');
Ext.ux.state.PersistStateProvider = function(config) {
	Ext.ux.state.PersistStateProvider.superclass.constructor.call(this);
    Persist.remove('cookie');
	this.store = new Persist.Store('pstore');
	Ext.apply(this, config);
};
Ext.extend(Ext.ux.state.PersistStateProvider, Ext.state.Provider, {
	set: function(name, value) {
		if (typeof value == "undefined" || value === null) {
			this.clear(name);
			return;
		}

		var val = this.encodeValue(value);
		this.store.set(name, val);
		this.fireEvent("statechange", this, name, value);
    },

	get: function(name, defaultValue) {
		var val = null;
		this.store.get(name, function(k, v) {
			if (k) {
				val = v;
			}
		});
		return this.decodeValue(val);
	},

    clear: function(name) {
		this.store.remove(name);
		this.fireEvent("statechange", this, name, null);
	}
});
