Ext.ns('OrangeLeap');

OrangeLeap.QueryLookup = Ext.extend(Ext.form.Field, {
	fieldDef: null,
	name: null,
	entityAttributes: '',
	referenceType: null,
	constituentId: null,
	displayValue: null,
	width: null,

    initComponent : function(){
        OrangeLeap.QueryLookup.superclass.initComponent.call(this);
//        this.addEvents(
//            "update"
//        );
    },

    onRender: function(ct, position) {
		var tpl = new Ext.Template('<div class="lookupWrapper" style="' + (Ext.isNumber(this.width) ? 'width: ' + this.width + 'px' : '') + '">',
			'<div class="lookupField {entityAttributes}">',
				'<div id="lookup-{value}" selectedId="{value}" class="queryLookupOption">',
					'<span>',
					'</span>',
				'</div>',
				'<a title="Lookup" alt="Lookup" class="hideText" fieldDef="{fieldDef}" onclick="Lookup.loadQueryLookup(this)" href="javascript:void(0)">Lookup</a>',
			'</div>',
			'<input type="hidden" name="{name}" id="{id}" value="{value}" />',
			'<div class="queryLookupOption noDisplay clone">',
				'<span>',
					'<a target="_blank" href=""/>',
				'</span>',
				'<a class="deleteOption" onclick="Lookup.deleteOption(this)" href="javascript:void(0)">',
					'<img title="Remove This Option" alt="Remove This Option" src="images/icons/deleteRow.png"/>',
				'</a>',
			'</div>',
		'</div>');

		var tplArgs = {
			'id': this.id, 'name': this.name, 'fieldDef': this.fieldDef, 'entityAttributes': this.entityAttributes,
			'referenceType': this.referenceType, 'constituentId': this.constituentId, 'value': this.value,
			'displayValue': this.displayValue
		};
		this.el = position ? tpl.insertBefore(position, tplArgs, true)
        	: tpl.append(ct, tplArgs, true);
    },

    setValue: function(value) {
        if ( ! Ext.isEmpty(value) && ! Ext.isEmpty(this.displayValue) && ! Ext.isEmpty(this.referenceType)) {
            var $elem = $('#' + OrangeLeap.escapeIdCharacters(this.id));
            var $queryLookupOptionEl = $elem.prev('.lookupField').children('.queryLookupOption');
            
            if ($queryLookupOptionEl.children('a.deleteOption').length == 0) {
				$queryLookupOptionEl.append('<a class="deleteOption" onclick="Lookup.deleteOption(this)" href="javascript:void(0)">' +
					'<img title="Remove This Option" alt="Remove This Option" src="images/icons/deleteRow.png"/>' +
					'</a>');
			}
			
			$queryLookupOptionEl.children('span').html('<a target="_blank" href="' + this.referenceType + '.htm?id=' + value +
				'">' + this.displayValue + '</a>');

			$elem.val(value);

//			'<a target="_blank" href="' + this.referenceType + '.htm?id=' + value +
//			(Ext.isNumber(this.constituentId) ? '&constituentId=' + this.constituentId : '') + '">' + this.displayValue +
//			'</a>' : ''),
        }
		else {
			var $elem = $('#' + OrangeLeap.escapeIdCharacters(this.id));
            var $queryLookupOptionEl = $elem.prev('.lookupField').children('.queryLookupOption');
			$queryLookupOptionEl.children('a.deleteOption').remove();
			$queryLookupOptionEl.children('span').empty();
			$elem.val('');
		}
		OrangeLeap.QueryLookup.superclass.setValue.call(this, value);
    },

    getValue: function() {
		return $('#' + OrangeLeap.escapeIdCharacters(this.id)).val();        
    }/*,

    markInvalid: function() {

    },

    clearInvalid: function() {
    
    },

    isValid: function(preventMark) {
    
    }*/
});     
Ext.reg('querylookup', OrangeLeap.QueryLookup);