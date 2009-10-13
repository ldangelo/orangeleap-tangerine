$(function() {
	$("table.customFieldTbl a.treeNodeLink").bind("click", function(event) {
		return OrangeLeap.expandCollapse(this);
	});

	var fnQueryLookup = Lookup.useQueryLookup;
	Lookup.useQueryLookup = function() {
		var row = Lookup.lookupCaller.parent().parent().parent("tr");
		var $row = $(row);
		fnQueryLookup();
		
		var $thisField = Lookup.lookupCaller.parent().children(":hidden").eq(0);
		if ($thisField.val()) {
			if ($row.parent("tbody").attr("hasCustomizations") == "true") {
				$row.children("td").children(".treeNodeLink").removeClass("noDisplay");
			}
			$row.children("td").children(".deleteButton").removeClass("noDisplay");
			
			if ($row.nextAll("tr.row").length == 0) {
				Relationships.cloneRow($row.parent("tbody"));
			}
			var startDtElem = $("#" + OrangeLeap.escapeIdCharacters($thisField.attr("id").replace("fldVal", "startDt")));
			if ($.trim(startDtElem.val()) == '') {
				startDtElem.val(new Date().asString());
			}
		}
	};
	
	var fnDeleteOption = Lookup.deleteOption;
	Lookup.deleteOption = function(elem) {
		fnDeleteOption(elem);
		
		if ($(elem).parents("tbody").attr("hasCustomizations") == "true") {
			$(elem).parents("tr.row").children("td").children(".treeNodeLink").addClass("noDisplay").removeClass("minus").addClass("plus");
			$(elem).parents("tr.row").next("tr.hiddenRow").addClass("noDisplay");
		}
	};
	
	$("input.date").each(function() {
		var df = new Ext.form.DateField({
	        applyTo: this.id,
	        id: name + "-wrapper",
	        format: ('m/d/Y')
	    });
	});
});

var Relationships = function() {
	return {
		deleteRow: function(link) {
			var $row = $(link).parent().parent("tr");
			if ($("tr.collapsed, tr.expanded", $row.parent("tbody")).length > 1) {
				$row.next(".hiddenRow").fadeOut("fast", function() {
					$(this).remove();
				});
				$row.fadeOut("fast", function() {
					$(this).remove();
				});
			}
			else {
				Ext.MessageBox.alert('Error', "Sorry, you cannot delete that row since it's the only remaining row.")
			}
		},
		
		cloneRow: function(tbody) {
			var $tbody = $(tbody);
			var nextIndex = parseInt($tbody.attr("nextCustomFieldIndex"), 10) + 1;
			$tbody.attr("nextCustomFieldIndex", nextIndex);
			
			var fieldName = $tbody.attr("fieldName")
			var selectorFieldName = OrangeLeap.escapeIdCharacters(fieldName);
		
			var $newRow = $("#" + selectorFieldName + "-cloneRow").clone(true);
			$newRow.addClass("row").attr("id", "");
			$newRow.children("td").children("div").children(".treeNodeLink").attr("rowIndex", nextIndex);
			
			var newFldValId = "fldVal-" + nextIndex + "-" + fieldName;
			$newRow.children("td").children(".lookupWrapper").children("input[type=hidden]").attr("id", newFldValId).attr("name", newFldValId);
			
			var newStartDtId = "startDt-" + nextIndex + "-" + fieldName; 
			$newRow.children("td").children("#" + selectorFieldName + "-clone-startDate").attr("id", newStartDtId).attr("name", newStartDtId).addClass("date");
			
			var newEndDtId = "endDt-" + nextIndex + "-" + fieldName; 
			$newRow.children("td").children("#" + selectorFieldName + "-clone-endDate").attr("id", newEndDtId).attr("name", newEndDtId).addClass("date");
			
			$newRow.children("td").children(".lookupWrapper").children(".lookupField").children(".hideText").attr("fieldDef", $tbody.attr("relationshipType"));

			$tbody.append($newRow);

			var $newHiddenRow = $("#" + selectorFieldName + "-hiddenCloneRow").clone(true);
			$newHiddenRow.attr("id", "");
			$newHiddenRow.find("input").each(function() {
				var $elem = $(this);
				$elem.attr("name", $elem.attr("name").replace("clone", nextIndex));
				$elem.attr("id", $elem.attr("id").replace("clone", nextIndex));
			});
			$tbody.append($newHiddenRow);
			
			$newRow.removeClass("noDisplay"); 
			
			$("input.date", $newRow).each(function() {
				var df = new Ext.form.DateField({
 			        applyTo: this.id,
  			        id: name + "-wrapper",
  			        format: ('m/d/Y')
			    });
			});
		}
	};
}();
