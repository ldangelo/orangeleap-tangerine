$(document).ready(function() {
	(function() {
		$("li.side:has(.picklist), li.side:has(.multiPicklist)", "ul").each(Picklist.buildPicklistTree);
		Picklist.cascadeElementsRoot();
        Picklist.hideShowSections();
	})();
	
    // initialize the dialog used for password changes
    var pwdChg = new PasswordChange();
    pwdChg.init();

	$.ajaxSetup({
		timeout: 30000, // 30 seconds before the request times out
		error: function(xhr, errorType, exception) {
			if (errorType == "timeout")	{
                Ext.MessageBox.show({ title: 'Error', icon: Ext.MessageBox.ERROR,
                    buttons: Ext.MessageBox.OK,
                    msg: 'The server was not available.  Please try again or contact your administrator if this issue continues.'});
			}
//			else if (xhr.status == 508 || errorType == "error") {
//				Ext.MessageBox.alert('Error', "An unexpected error has occurred and has been logged.  Please try again or contact your administrator if this issue continues.");
//			}
		}
	});
	
	$(".picklist", "form").bind("change", Picklist.togglePicklist);
	
	$("table.tablesorter tbody td input").focus(function() {
		$(this).parents("tr:first").addClass("focused");
	}).blur(function() {
		$(this).parents("tr:first").removeClass("focused");
	});

	$("#searchForm #typeSearch").bind("change", function() {
        var thisType = $(this).val();
		if (thisType == "gifts") {
			$("#searchForm").attr("action", "giftSearch.htm");	
		}
        else if (thisType == "fullText") {
            $("#searchForm").attr("action", "fullTextSearch.htm");
        }
		else {
			// assume people search
			$("#searchForm").attr("action", "constituentSearch.htm");	
		}
	});
	$("ul.formFields li input, ul.formFields li select").change(function() {
       	$("#savedMarker").fadeOut("slow");
	});

	$(".cluetip[rel]").cluetip({
		cluetipClass:'default',
		showTitle: false,
		dropShadow: false,
		waitImage: false,
		fx: {
        	open:'fadeIn'
		},
        ajaxSettings: {
            type: 'POST'
        }
	});
	
	$("span.secondary:has('a.active')").each(function(){
		$(this).addClass("active").prev(".groupHeader").addClass("groupActive");
	});
	
	$("div.navGroup:not(:has('a.active'))").hoverIntent({
		sensitivity: 7,
		interval: 100,
		over: function(){
			$(this).find("span.secondary").filter(":hidden").slideDown();
		},
		out: function() {},
		timeout: 1000
	 });
	 
	 $("div.accountOptions").hoverIntent({
		sensitivity: 7,
		interval: 100,
		timeout: 1000,
		over: function() {},
		out: function(){
			$(this).find("span.secondary:not('.active')").filter(":visible").slideUp();
		}
	 });
			 
	 $(".primaryNav li ul").each(function(){
	 	var btnWidth = $(this).prev("a").outerWidth() + 20;
	 	var selfWidth = $(this).outerWidth();
	 	if (btnWidth > selfWidth) {
	 		$(this).width(btnWidth);
	 	}
	 });
	 
	 $(".primaryNav li:has(ul)").hover(function(){
			$(this).find("ul").show().prev("a").addClass("bactive");
		},function(){
			$(this).find("ul").hide().prev("a").removeClass("bactive");
		});

	$("input.number, input.percentage").numeric();

	$('#dialog').jqm({overlay: 50, onShow: OrangeLeap.centerDialog}).jqDrag('.dragHandle');

	$(".picklistItemFilters :input").bind("keyup change",function(){
		var queryString = $(".picklistItemFilters :input").serialize();
		$(".picklistItemList").load("picklistItemHelper.htm?view=table&"+queryString);
	});

	$("input.code").each(function() {
		var $elem = $(this);
		if (typeof $elem.attr("autocomplete") == "undefined") {
			Lookup.codeAutoComplete($elem);
		}
	});

	Date.format = 'mm/dd/yyyy';
	
	$(":input, select, textbox").bind("focus", function(event) {
		if ($.browser.msie) {
			$(this).addClass("focused");
		}
		var target = $(this); 
		if ($(this).parent("div.lookupWrapper").length) { 
			target = $(this).parent("div.lookupWrapper");
		}
		target.prevAll("label.desc").addClass("inFocus");
	});
	$(":input, select, textbox").bind("blur", function() {
		if ($.browser.msie) {
			$(this).removeClass("focused");
		}
		var target = $(this); 
		if ($(this).parent("div.lookupWrapper").length) { 
			target = $(this).parent("div.lookupWrapper");
		}
		target.prevAll("label.desc").removeClass("inFocus");
	});
    $('input.encrypted').focus(function(event) {
        var $elem = $(this);
        if ($elem.val().indexOf('****') == 0) {
            // force selection of the whole value if masked
            $elem.select();
        }
    });
    $('input.encrypted').keydown(function(event) {
        var $elem = $(this);
        /* if the user tries to change a masked encrypted value, wipe out the existing value to make them type in the whole field again */
        if ($elem.val().indexOf('****') == 0 &&
                (event.keyCode == Ext.EventObject.BACKSPACE || event.keyCode == Ext.EventObject.DELETE ||
                     event.keyCode == 32 || // space
                     event.keyCode == 188 || // ,
                     event.keyCode == 109 || // -
                     event.keyCode == 61 || // +
                     event.keyCode == 59 || // ;
                    (event.keyCode >= 190 && event.keyCode <= 192) || // . / `
                    (event.keyCode >= 219 && event.keyCode <= 222) || // [ ] \ '
                    (event.keyCode >= Ext.EventObject.ZERO && event.keyCode <= Ext.EventObject.NINE) ||
                    (event.keyCode >= Ext.EventObject.A && event.keyCode <= Ext.EventObject.Z) ||
                    (event.keyCode >= Ext.EventObject.NUM_ZERO && event.keyCode <= Ext.EventObject.NUM_NINE))) {
            $elem.val('');
        }
    });

    $("div.mainForm form").submit(function() {
        var submitted = false;
        var $form = $(this);
        if ($form.hasClass('disableForm') && !submitted) {
            submitted = true;
            $("div.mainForm form :button, div.mainForm form :submit, div.mainForm form :reset").each(function() {
                var $elem = $(this);
                $elem.unbind("click");
                $elem.addClass("disabledButton");
                $elem.removeClass("button");
                $elem.removeClass("saveButton");
                $elem.click(function() {
                    return false;
                });
                $elem.focus(function() {
                    $(this).blur();
                });
            });
            if ($.browser.msie) {
                // IE will automatically force the form to return false unless we set a timer
                setTimeout(function() {
                    $("div.mainForm form").submit(function() {
                        return false;
                    });
                }, 100);
            }
            else {
                $("div.mainForm form").submit(function() {
                    return false;
                });
            }
        }
        return true;
    });
	
	(function focusFirst() {
		var formLen = document.forms.length;
		if (formLen > 0) {
			var isFound = false;
			for (var y = 0; y < formLen; y++) {
				var thisForm = document.forms[y];
				if (thisForm.id != "searchForm") {
                    var $firstElem = $("[tabindex=1]");
                    if ($firstElem.length) {
                        $firstElem.focus();
                    }
                    else {
                        var $elems = $("input, select, textarea, a.lookupLink, a.hideText", $(thisForm));
                        if ($elems && $elems.length > 0) {
                            var elemLen = $elems.length;
                            for (var x = 0; x < elemLen; x++) {
                                var thisElem = $elems[x];
                                if (!thisElem.disabled) {
                                    if (thisElem.tagName.toLowerCase() == "input") {
                                        if (thisElem.type != "hidden" && thisElem.type != "submit" && thisElem.type != "button" && thisElem.type != "reset") {
                                            try {
                                                thisElem.focus();
                                            }
                                            catch (exception) {}
                                            isFound = true;
                                            break;
                                        }
                                    }
                                    else {
                                        try {
                                            thisElem.focus();
                                        }
                                        catch (exception) {}
                                        isFound = true;
                                        break;
                                    }
                                }
                            }
                            if (isFound) {
                                break;
                            }
                        }
                    }
				}		
			}
		}
	})();
});

Ext.onReady(function() {
    // these expandCollapseSection/$('h4.formSectionHeader') functions need to be in Ext.onReady
    // instead of $(document).ready to ensure the Ext.state.Manager has the right provider setup 
    function expandCollapseSection($elem, isCollapsed) {
        var $columns = $elem.nextAll('.formFields');
        if ( ! $columns.length) {
            $columns = $elem.parent().find('.column .formFields');
        }
        $columns.each(function() {
            if (isCollapsed) {
                // if already collapsed, expand
                $(this).show();
            }
            else {
                $(this).hide();
            }
        });
        if (isCollapsed) {
            $elem.removeClass('collapsed');
        }
        else {
            $elem.addClass('collapsed');
        }
    }

    $('h4.formSectionHeader').click(function() {
        var $elem = $(this);
        var isCollapsed = $elem.hasClass('collapsed');
        expandCollapseSection($elem, isCollapsed);
        var thisId = $elem.parent().attr('id');
        Ext.state.Manager.set(thisId, ! isCollapsed); // negate isCollapsed because expandCollapseSection() will toggle it
    });
    $('h4.formSectionHeader').each(function() {
        // done onLoad
        var $elem = $(this);
        var thisId = $elem.parent().attr('id');
        var isCollapsed = Ext.state.Manager.get(thisId);
        if (isCollapsed !== undefined) {
            expandCollapseSection($elem, ! isCollapsed); // negate isCollapsed because expandCollapseSection() will toggle it
        }
    });
});

function saveInPlace(elem, baseUrl) {
	var queryString = $(elem).parent().parent().find("input").serialize();
	$.ajax({
		type: "POST",
		url: baseUrl,
		data: queryString,
		success: function(html){
			$(elem).parent().parent().html(html);
			return false;
		},
		error: function(xhr) {
			if (xhr.status == 508) {
				Ext.MessageBox.alert('Error', "The code could not be saved. Please ensure that the code has a unique value.");
				return false;
			}
		}
	});
	return false;
};
function newInPlace(elem, baseUrl) {
	var queryString = $(elem).parent().parent().find("input").serialize();
	$.ajax({
		type: "POST",
		url: baseUrl,
		data: queryString,
		success: function(html){
			var newRow=document.createElement("tr");
			$(newRow).html(html);
			$(".justAdded table").append(newRow);
			return false;
		},
		error: function(xhr) {
			if (xhr.status == 508) {
				Ext.MessageBox.alert('Error', "The code could not be saved. Please ensure that the code has a unique value.");
				return false;
			}
		}
	});
	return false;
};
function editInPlace(elem) {
	$(elem).parent().parent().load($(elem).attr("href"));
	return false;
};

var GenericCustomizer = {	
	addNewRow : function() {
		var $newRow = $("table.customFields tr:last", "form").clone(false);
		var i = $newRow.attr("rowindex");
		var j = parseInt(i, 10) + 1;
		$newRow.attr("rowindex", j);
		$newRow.find("input").each(function() {
				var $field = $(this);
				$field.attr('name', $field.attr('name').replace(new RegExp("\\[\\d+\\]","g"), "[" + j + "]"));
				$field.attr('id', $field.attr('id').replace(new RegExp("\\-\\d+\\-","g"), "-" + j + "-"));
				$field.val("");
				$field.attr("style", "");
				$field.attr("class", "");
				$field.removeAttr("readonly");
			});
		$newRow.find("div.lookupWrapper div.lookupField div.queryLookupOption").remove();
		$("table.customFields", "form").append($newRow);
	}
};
var Picklist = {	
	rootTrees: {},
	
	/** When the document is ready, build the tree(s) of items that cascade each other */
	buildPicklistTree: function() {
		var $parentNode = $(this);
		var tree = Picklist.getTree($parentNode);
		
		if (tree.children.length == 0) { // assume no children will be discovered each time we traverse all the picklists
			if (tree.parents.length == 0) {
				tree.isRoot = true; // assume this is the root, will be reset in 'setParentForChild' if not
			}
			var $recursiveTargets = null;
	
			var $picklists = $parentNode.filter(".picklist, .multiPicklist"); // either THIS is a picklist
			var $nested = $parentNode.find(".picklist, .multiPicklist"); // or it COULD have immediate descendent picklists
			$picklists = $picklists.add($nested); // these are all the children of this node
			$picklists.each(function() {
				var $myPicklist = $(this);
				var selectors = $myPicklist.attr("references");
				var thisPicklistId = $myPicklist.attr("id");
				if (thisPicklistId.indexOf("div-") == 0) {
					thisPicklistId = thisPicklistId.substring(4);
				}
				var selectedRefs = jQuery.trim($("div#selectedRef-" + thisPicklistId).text());
				if (selectors && $.trim(selectors) != '') {
					var $targets = $(selectors, "form"); 
					if ($targets) {
						$targets.each(function() {
							var $myTarget = $(this);
							var optionSelected = $myTarget.is(selectedRefs);
							var parentChildSet = false;
							parentChildSet |= Picklist.setChildForParent($parentNode, $myTarget);
							parentChildSet |= Picklist.setParentForChild($myTarget, $parentNode, optionSelected);
							if (parentChildSet) {
								// To prevent the same node from being evaluated again and again, check if a change was made; if so, add to recursive targets, else, no need to recursively evaluate further
								$recursiveTargets = $recursiveTargets ? $recursiveTargets.add($myTarget) : $myTarget;
							}
						});
					}
				}
			});	
			if (tree.isRoot) {
				var thisId = $parentNode.attr("id");
				tree.isSelected = true;
				Picklist.rootTrees[thisId] = tree;				
			}				
			if ($recursiveTargets) {
				$recursiveTargets.each(Picklist.buildPicklistTree);
			}
		}
	},
	
	setChildForParent: function($parentNode, $childNode) {
		var parentTree = Picklist.getTree($parentNode);
		var childId = $childNode.attr("id");

		var parentChildSet = false;
		if (!parentTree.isExistingChild(childId)) {
			parentTree.children.push($childNode);
			parentTree.childIds[childId] = true;
			parentChildSet = true;
		}
		return parentChildSet;
	},
		
	setParentForChild: function($childNode, $parentNode, optionSelected) {
		var childTree = Picklist.getTree($childNode);
		var parentId = $parentNode.attr("id");
		
		var parentChildSet = false;
		if (!childTree.isExistingParent(parentId)) {
			childTree.parents.push($parentNode);
			childTree.parentIds[parentId] = true;
			childTree.isRoot = false;
			
			var thisId = $childNode.attr("id");
			delete Picklist.rootTrees[thisId];
			parentChildSet = true;
		}
		Picklist.setIsSelected(childTree, optionSelected, $parentNode, parentId);
		return parentChildSet;
	},
	
	setIsSelected: function(childTree, optionSelected, $parentNode, parentId) {
		if (childTree.isSelected != optionSelected) {
			if (childTree.parents.length == 1) {
				if (childTree.parentIds[parentId]) {
					// If the parent of this child points to the child more than once, use an OR condition
					childTree.isSelected = childTree.isSelected | optionSelected;
				}
				else {
					childTree.isSelected = optionSelected;
				} 
			}
			else {
				/* Try to determine if THIS parentNode is also a child of the childNode's OTHER parentNodes; 
				 * if it is a child and THIS parentNode is selected, override the previous 'optionSelected' value with the one from THIS parentNode;
				 * otherwise, if THIS parentNode is not a child of the childNode's OTHER parentNodes, use an OR condition
				 */
				var isChild = Picklist.isChildOf(childTree.parents, parentId);
				if (isChild) {
					var parentTree = Picklist.getTree($parentNode);
					if (parentTree.isSelected) {
						childTree.isSelected = optionSelected; 
					}
				}
				else {
					childTree.isSelected = childTree.isSelected | optionSelected;
				}
			}
		}
	},
	
	isChildOf: function(parents, parentId) {
		var theParentTree = null;
		var parentsParents = new Array();
		for (var i = 0, len = parents.length; i < len; i++) {
			var $myParent = $(parents[i]);
			if ($myParent.attr("id") != parentId) {
				var parentTree = Picklist.getTree($myParent);
				if (parentTree.isExistingChild(parentId)) {
					theParentTree = parentTree;
					break;
				}
				else {
					parentsParents.push(parentTree.parents);
				}
			}
		}
		if (theParentTree == null && parentsParents.length > 0) {
			theParentTree = Picklist.isChildOf(parentsParents);
		}
		return theParentTree;
	},
	
	getTree: function($elem) {
		var tree = $elem.data("tree");
		if (!tree) {
			tree = { node: $elem, 
				parents: new Array(), 
				children: new Array(), 
				parentIds: new Object(), 
				childIds: new Object(), 
				isRoot: false, 
				isSelected: false, 
				isAParentSelected: function() {
					var pSel = false;
					for (var i = 0, len = this.parents.length; i < len; i++) {
						if ($(this.parents[i]).data("tree").isSelected) {
							pSel = true;
							break;
						}
					}
					return pSel;
				},
				isExistingParent: function(aParentId) {
					return this.parentIds[aParentId];
				},
				isExistingChild: function(id) {
					return this.childIds[id];
				}
			};
			$elem.data("tree", tree);
		}
		return tree;
	},
	
	isMultiPicklist: function($elem) {
		return $elem.hasClass("multiPicklist");
	},
	
	findOptions: function($elem) {
		var $options = null;
		var isMultiPicklist = Picklist.isMultiPicklist($elem);
		if (isMultiPicklist) {
			$options = $elem.children("div.multiPicklistOption");
		}
		else {
			$options = $elem.find("option");
		}
		return $options;
	},
	
	getCascaders: function() {
		return { shown: null, hidden: null, ids: {} };
	},
	
	hideShowCascaders: function(cascaders) {
		if (cascaders.hidden) { 
			cascaders.hidden.hide(); 
		}
		if (cascaders.shown) { 
			cascaders.shown.show(); 
		}
	},
		
	cascadeElementsRoot: function () {
		var cascaders = Picklist.getCascaders();
		for (var treeId in Picklist.rootTrees) {
			var tree = Picklist.rootTrees[treeId];
			var $elem = tree.node;

			cascaders.shown = cascaders.shown ? cascaders.shown.add($elem) : $elem; // all root elements are expected to be visible
			cascaders = Picklist.cascadeElementsChildren($(tree.children), cascaders);												
		}
		Picklist.hideShowCascaders(cascaders);		
	},
	
	cascadeElementsChildren: function($children, cascaders) {
		var $nextLevelChildren = null;
		if ($children) {
			$children.each(function() {
				var $child = $(this);
				var tree = Picklist.getTree($child); 
				if (tree.isSelected && tree.isAParentSelected()) { // if the child node is selected, check the parent(s) to make sure at least one is selected
					cascaders.shown = cascaders.shown ? cascaders.shown.add($child) : $child;
				}
				else {
					cascaders.hidden = cascaders.hidden ? cascaders.hidden.add($child) : $child;
				}
				$nextLevelChildren = $nextLevelChildren ? $nextLevelChildren.add($(tree.children)): $(tree.children);
			});
			cascaders = Picklist.cascadeElementsChildren($nextLevelChildren, cascaders);
		}
		return cascaders;
	},
	
	togglePicklist: function() {
		var $elem = $(this);

		var $containerElem = $elem.parents("li.side"); // get this picklist's container element
		var tree = Picklist.getTree($containerElem);
		tree.isSelected = true;
		
		var cascaders = Picklist.getCascaders();
		cascaders.shown = cascaders.shown ? cascaders.shown.add($elem) : $elem;
		
		var pickedSelector = "";
		var isMultiPicklist = Picklist.isMultiPicklist($elem);
		if (isMultiPicklist) {
			var $options = Picklist.findOptions($elem);
			$options.each(function() {
				var $optElem = $(this);
				if ($optElem.is(":visible")) {
					var myRef = $optElem.attr('reference');
					pickedSelector += myRef + ",";
				}
			});
			if (pickedSelector && pickedSelector.length > 0) {
				pickedSelector = pickedSelector.substring(0, pickedSelector.length - 1);
			}
		}
		else {
			var aIndex = this.selectedIndex ? this.selectedIndex : 0;
			pickedSelector = $(this.options[aIndex]).attr("reference");
			if (!pickedSelector) {
				pickedSelector = "";
			}
		}
		for (var y = 0, len = tree.children.length; y < len; y++) {
			var $child = $(tree.children[y]);
			var isPicked =  $child.is(pickedSelector);
			var childTree = Picklist.getTree($child);
			var id = $child.attr("id");
			
			if (isPicked != childTree.isSelected) {
				var theParentTree = Picklist.isChildOf(tree.children, id);
				if (theParentTree && theParentTree.isSelected) {
					var innerSelect = theParentTree.node.find(".picklist");
					var $innerPicklist = $(innerSelect);
					var isInnerMultiPicklist = Picklist.isMultiPicklist($innerPicklist);
					var $innerPicklistOptions = Picklist.findOptions($innerPicklist);
					
					var innerPicked = false;					
					if (isInnerMultiPicklist) {
						$innerPicklistOptions.each(function() {
							var $innerOptElem = $(this);
							if ($innerOptElem.is(":visible")) {
								var innerRef = $innerOptElem.attr('reference');
								innerPicked |= $child.is(innerRef);
							}
						});
					}
					else {
						var select = innerSelect.get(0);
						var index = select.selectedIndex ? select.selectedIndex : 0;
						var innerRef = $(select.options[select.selectedIndex]).attr("reference");
						innerPicked |= $child.is(innerRef);
					}
					childTree.isSelected = innerPicked;
				}
				else {
					childTree.isSelected = isPicked;
				}
			}
		}
   		cascaders = Picklist.cascadeElementsChildren($(tree.children), cascaders);
		Picklist.hideShowCascaders(cascaders);
        Picklist.hideShowSections();
	},
	
	setSelectedAddressPhoneByValue: function($select, value) {
		var $liElem = $select.parents("li.side");
		var tree = Picklist.getTree($liElem);
		
		// If no numeric ID selected, use "none"
		if (isNaN(parseInt(value, 10)) || !$select.containsOption(value)) {
			value = "none";
		}

		var $options = Picklist.findOptions($select);
		
		$options.each(function(index) {
			var $optElem = $(this);
			var optionSelected = false;
			if (value == $optElem.val()) {
				optionSelected = true;
				$optElem.attr("selected", "true");
				$select.get(0).selectedIndex = index;
			}
			var selector = $optElem.attr('reference');
			if (selector != null && selector.length) {
				var $targets = $(selector, "form");
				$targets.each(function() {
					var $myTarget = $(this);
					Picklist.getTree($myTarget).isSelected = optionSelected;
				});
			}
		});	
		return $select;
	},

    hideShowSections: function() {
        $("div.columns").each(function() {
            var $columnsElem = $(this);
            if ($columnsElem.parents("tbody[id^='grid']").length == 0) { // do not hide columns in a grid
                var singleColumns = $("div.column", $columnsElem);

                var allHidden = true;
                for (var x = 0; x < singleColumns.length; x++) {
                    var $singleColElem = $(singleColumns[x]);
                    if ( !$singleColElem.hasClass("noDisplay") && $singleColElem.css("display") != "none") {
                        var liElems = $("li.side", $singleColElem);

                        if (allHidden && liElems) {
                            for (var y = 0; y < liElems.length; y++) {
                                var $thisLiElem = $(liElems[y]);
                                if ( !$thisLiElem.hasClass("noDisplay") && $thisLiElem.css("display") != "none") {
                                    allHidden = false;
                                    break;
                                }
                            }
                        }
                    }
                    if (!allHidden) {
                        break;
                    }
                }
                if (allHidden) {
                    $columnsElem.addClass("noDisplay");
                }
                else {
                    $columnsElem.removeClass("noDisplay");
                }
            }
        });
    }
};
var OrangeLeap = {

	customFieldSeparator: String.fromCharCode(167),

	amountRenderer: function(val, meta, record) {
		if (val && OrangeLeap.isNum(val)) {
			return val.toFixed(2);
		}
		return '0.00';
	},

    booleanRenderer: function(val, meta, record) {
        if (val) {
            meta.css = 'green-dot';
        }
        else {
            meta.css = 'grey-dot';
        }
    },
	
	expandCollapse: function(elem) {
		$elem = $(elem);
		if ($elem.hasClass("plus")) {
			$elem.removeClass("plus").addClass("minus");
			var $parent = $elem.parent().parent();
			$parent.removeClass("collapsed").addClass("expanded");
			$parent.next(".hiddenRow").removeClass("noDisplay");
		}
		else {
			$elem.removeClass("minus").addClass("plus");
			var $parent = $elem.parent().parent();
			$parent.removeClass("expanded").addClass("collapsed");
			$parent.next(".hiddenRow").addClass("noDisplay");
		}
		return false;
	},
	
	changeIdsNamesIE: function($newRow, index) {
		$("input, select, textbox", $newRow).each(function() {
			var $elem = $(this);
			var thisId = $elem.attr("id");
			$elem.attr("id", thisId.replace(new RegExp("\\-0\\-","g"), "-" + index + "-").replace(new RegExp("tangDummy\\-", "g"), ""));
			var thisName = $elem.attr("name");
			$elem.attr("name", thisName.replace(new RegExp("\\-0\\-","g"), "-" + index + "-").replace(new RegExp("tangDummy\\-", "g"), ""));
			var otherFieldAttr = $elem.attr("otherFieldId");
			if (otherFieldAttr) {
				$elem.attr("otherFieldId", otherFieldAttr.replace(new RegExp("\\-0\\-","g"), "-" + index + "-").replace(new RegExp("tangDummy\\-", "g"), ""));
			}
			var additionalFieldAttr = $elem.attr("additionalFieldId");
			if (additionalFieldAttr) {
				$elem.attr("additionalFieldId", additionalFieldAttr.replace(new RegExp("\\-0\\-","g"), "-" + index + "-").replace(new RegExp("tangDummy\\-", "g"), ""));
			}
		});
		$("a.treeNodeLink", $newRow).each(function() {
			var $elem = $(this);
			$elem.attr("rowIndex", index);
		});
		$("tr.hiddenRow li, tr.hiddenRow div", $newRow).each(function() {
			var $elem = $(this);
			var thisId = $elem.attr("id");
			$elem.attr("id", thisId.replace(new RegExp("\\-0\\-","g"), "-" + index + "-").replace(new RegExp("tangDummy\\-", "g"), ""));
		});
		$("tr.hiddenRow label", $newRow).each(function() {
			var $elem = $(this);
			var thisId = $elem.attr("for");
			$elem.attr("for", thisId.replace(new RegExp("\\-0\\-","g"), "-" + index + "-").replace(new RegExp("tangDummy\\-", "g"), ""));
		});
	},
	
	gotoUrl: function(url) {
		window.location.href = url;
	},
	
	confirmGoToUrl: function(url, msg) {
		if (confirm(msg)) {
			this.gotoUrl(url);
		}
	},
	
	centerDialog: function($hash) {
		var $dialog = $hash.w;
		var x = "-" + ($dialog.width() / 2) + "px";
		var y = "-" + ($dialog.height() / 2) + "px";
		$dialog.css("margin-left", x);
		$dialog.css("margin-top", y);
		$dialog.show();
	},
	
	isNum: function(val) {
		return (isNaN(parseFloat(val)) == false);
	},
	
	isFloat: function(value) {
	   if (isNaN(value) || value.toString().indexOf(".") < 0) {
		   return false;
	   }
	   else {
	      if (parseFloat(value)) {
	    	  return true;
	      } 
	      else {
	    	  return false;
	      }
	   }
	},
	
	truncateFloat: function(val) {
		if (OrangeLeap.isFloat(val)) {
			val = val.toFixed(2);
		}
		return val;
	},
		
	hideShowRecognition: function($anonymousElem, recognitionSelector) {
		if ($anonymousElem.attr("checked")) {
			$(recognitionSelector).hide();
		} 
		else {
			$(recognitionSelector).show();
		}
	},
	
	escapeIdCharacters: function(idString) {
		return idString.replace(".", "\\.").replace("[", "\\[").replace("]", "\\]"); // for jQuery selection, escape common characters
	},

    extElementFocus: function(fld) {
		$('#' + fld.getId()).parents('div.x-form-element').prev('label').addClass('inFocus');
	},

	extElementBlur: function(fld) {
		$('#' + fld.getId()).parents('div.x-form-element').prev('label').removeClass('inFocus');
	},

	extFocusFirstFormElem: function(form) {
		var item = form.items.find(function(item) {
			if (item instanceof Ext.form.Field && !item.hidden && !item.disabled) {
				item.focus(false, 50); // delayed focus by 50 ms
				return true;
			}
			return false;
		});
	},

	extGridColExpand: function(grid) {
		grid.view.fitColumns(true, true); // force columns to expand
		grid.view.updateHeaders();
	},

    getQueryParams: function() {
        var argList = new Object();

        if (window.location != null && window.location.search.length > 1) {
            var urlParms = window.location.search.substring(1);
            var argPairs = urlParms.split('&');

            for(var i = 0; i < argPairs.length; i++) {
                var pos = argPairs[i].indexOf('=')

                if (pos == -1) {
                    continue;
                }
                else {
                    var argName = argPairs[i].substring(0, pos);
                    var argVal = argPairs[i].substring(pos + 1);

                    if (argVal.indexOf('+') != -1) {
                        argVal = argVal.replace(/\+/g, ' ');
                    }
                    argList[argName] = unescape(argVal);
                }
            }
        }
        return argList;
    }
};
var Lookup = {
	lookupCaller: null,
	
	hiliteAndFade: function($elem) {
		$elem.animate({ backgroundColor: "yellow"}, "fast").animate({backgroundColor: "#FFF"}, "fast");
	},
	
	removeNbsp: function(itemSelected) {
		return itemSelected.extra[0].replace("&nbsp;", "");
	},
	
	codeAutoComplete: function($elem) {
		$elem.autocomplete("codeHelper.htm?view=autoComplete&type=" + $elem.attr("codeType"), {
			delay: 10,
			minChars: 0,
			max: 20,
			formatItem: Lookup.formatItem,
			showValueAndDesc: true,
			displayValuePrefix: { hidden: "hidden-", display: "display-" },
			hideDescription: true,
			loadingClass: ''
		});
		$elem.result(this.codeAutoCompleteCallback);
	},
		
	formatItem: function(row) {
		var thisCode = row[0];
		var thisDesc = (row[1] && $.trim(row[1].toString()) != '' && $.trim(row[1].toString()) != '&nbsp;' ? "<span> - " + row[1].toString().replace("&nbsp;", "") + "</span>" : "");
		
		return thisCode + thisDesc;
	},
		
	codeAutoCompleteCallback: function(resultObj, data, val, $input) {
		$("#" + $input.attr("otherFieldId")).val("");
	},
	
	loadCodePopup: function(elem, showOtherField) {
		var $elem = $(elem).siblings(':text');
		var lookupType = $elem.attr("lookup");
		this.lookupCaller = $elem;

		if (!showOtherField) {
			showOtherField = false;
		}
		$.ajax({
			type: "GET",
			url: "codeHelper.htm",
			data: "type=" + lookupType + "&showOtherField=" + showOtherField,
			success: function(html){
				$("#dialog").html(html);
				Lookup.commonBindings();
				Lookup.singleCommonBindings();
				Lookup.singleCodeLookupBindings();
				Lookup.clickEventHandler();
				$("#dialog").jqmShow();
			}
		});
		return false;
	},
	
	loadCodeAdditionalPopup: function(elem) {
		var $elem = $(elem);
		var lookup = $elem.attr("lookup");
		this.lookupCaller = $elem.siblings(".lookupScrollContainer").children(".multiLookupField");		
		var queryString = this.serializeCodeAdditional(this.lookupCaller.siblings("input[type=hidden]"));
		$.ajax({
			type: "GET",
			url: "codeHelperAdditional.htm",
			data: "type=" + lookup + "&" + queryString,
			success: function(html){
				$("#dialog").html(html);
				Lookup.commonBindings();
				Lookup.multiCommonBindings();
				Lookup.multiCodeLookupBindings();
				Lookup.clickEventHandler();
				$("#dialog").jqmShow();
			}
		});
	},
	
	serializeCodeAdditional: function($codeElem) {
		var queryString = "selectedCodes=" + escape($codeElem.val());
		var additionalElemId = $codeElem.attr("additionalFieldId");
		if (additionalElemId) {
			queryString += "&additionalCodes=" + escape($("#" + additionalElemId).val());
		}
		return queryString;
	},
	
	singleCodeLookupBindings: function() {
		$("form.codeForm #doneButton", $("#dialog")).bind("click", function() {
			Lookup.useSingleCode();
			$("#dialog").jqmHide();	
		});
		$("form.codeForm #searchText", $("#dialog")).bind("keyup", function(event){
			if (event.keyCode == 13) { // return key
				Lookup.doQuery(Lookup.getSingleCodeData);
			}
		});
		$("form.codeForm #findButton", $("#dialog")).bind("click", function(){
			Lookup.doQuery(Lookup.getSingleCodeData);
		});		
	},
	
	multiCodeLookupBindings: function() {
		$("form.codeForm #doneButton", $("#dialog")).bind("click", function() {
			Lookup.useMultipleCodes();
			$("#dialog").jqmHide();	
		});
		$("form.codeForm #searchText", $("#dialog")).bind("keyup", function(event){
			if (event.keyCode == 13) { // return key
				Lookup.doMultiQuery(Lookup.getMultiCodeData);
			}
		});
		$("form.codeForm #findButton", $("#dialog")).bind("click", function(){
			Lookup.doMultiQuery(Lookup.getMultiCodeData);
		});		
	},
	
	getSingleCodeData: function() {
		var obj = { view: "resultsOnly", type: $("div.modalSearch input#type").val() };
		obj[$("#searchOption").val()] = Lookup.checkCodeValue();
		return obj;
	},
	
	getMultiCodeData: function() {
		var selectedCodesStr = "";
		$("ul#selectedOptions li :checkbox").each(function() {
			selectedCodesStr += $(this).attr("id") + OrangeLeap.customFieldSeparator;
		});
		if (selectedCodesStr.length > 0) {
			selectedCodesStr = selectedCodesStr.substring(0, selectedCodesStr.length - 1); // remove the last customFieldSeparator
		}
		var obj = { view: "resultsOnly", type: $("div.modalSearch input#type").val(), selectedCodes: selectedCodesStr };
		obj[$("#searchOption").val()] = Lookup.checkCodeValue();
		return obj;
	},

	checkCodeValue: function() {
		var $searchTextElem = $("#searchText");
		var val = $searchTextElem.val();
		if (val == $searchTextElem.attr('defaultValue')) {
			val = '';
		}
		return val;
	},
	
	useSingleCode: function() {
		var $elem = $('#codeResultsUl input[name=option]:checked');
		var itemName = $elem.val();
		var displayvalue = $elem.attr("displayvalue");
		
		if (itemName) {
			$("#" + Lookup.lookupCaller.attr("otherFieldId")).val("");
			Lookup.lookupCaller.siblings("input:hidden").val(itemName);
			Lookup.lookupCaller.val(displayvalue);
			Lookup.lookupCaller.vkfade(true);
		}
		else {
			var $optionElem = $("div.modalContent input#otherOptionText");
			var typedVal = $optionElem.val();
			if (typedVal != $optionElem.attr("defaultValue")) {
				Lookup.lookupCaller.siblings("input:hidden").val("");
				$("#" + Lookup.lookupCaller.attr("otherFieldId")).val(typedVal);
				Lookup.lookupCaller.val(typedVal);
				Lookup.lookupCaller.vkfade(true);
			}
		}
	},
	
	useMultipleCodes: function() {
		var codesStr = "";
		var descriptions = new Array();
		var codes = new Array();
		$("ul#selectedOptions :checkbox", $("#dialog")).each(function() {
			var $chkboxElem = $(this);
			var thisCode = $chkboxElem.val();
			codesStr += thisCode + OrangeLeap.customFieldSeparator;
			codes[codes.length] = thisCode;
			descriptions[descriptions.length] = $chkboxElem.attr("displayvalue");
		});
		codesStr = (codesStr.length > 0 ? codesStr.substring(0, codesStr.length - 1) : codesStr); // remove the trailing customFieldSeparator

		var $hiddenElem = Lookup.lookupCaller.parent().children("input[type=hidden]").eq(0);
		$hiddenElem.val(codesStr);
		
		Lookup.lookupCaller.children("div.multiCodeOption").remove();
		
		var $toBeCloned = Lookup.lookupCaller.parent().find("div.clone");
		
		for (var x = descriptions.length - 1; x >= 0; x--) {
			var $cloned = $toBeCloned.clone(true);
			$cloned.attr("id", "option-" + codes[x]);
			$cloned.attr("code", codes[x]);
			$cloned.find("span").text(descriptions[x]);			
			$cloned.removeClass("clone").removeClass("noDisplay");
			$cloned.prependTo(Lookup.lookupCaller);
			$cloned.vkfade(true);
		} 
		
		/* Get the additional free-form values */
		var $additionalOptionsContainerElem = Lookup.lookupCaller.children("div.additionalOptions");
		if ($additionalOptionsContainerElem) {
			$additionalOptionsContainerElem.children().remove();
			var additionalCodesValues = null;
			$("#additionalCodesText", $("#dialog")).each(function() {
				var val = $(this).val();
				if (val) {
					additionalCodesValues = val.split("\n");
				}
			});
			if (additionalCodesValues) {
				var additionalCodesStr = "";
				for (var x = additionalCodesValues.length - 1; x >= 0; x--) {
					if (jQuery.trim(additionalCodesValues[x]) != "") {
						var $cloned = $toBeCloned.clone(true);
						$cloned.attr("id", "additional-" + Math.floor((Math.random() * 1000) + (Math.random() * 100)));
						$cloned.attr("code", additionalCodesValues[x]);
						$cloned.find("span").text(additionalCodesValues[x]);			
						$cloned.removeClass("clone").removeClass("noDisplay");
						$cloned.prependTo($additionalOptionsContainerElem);
						$cloned.vkfade(true);
						additionalCodesStr += additionalCodesValues[x] + OrangeLeap.customFieldSeparator; 
					}
				}
				if (additionalCodesStr.length > 0) {
					additionalCodesStr = additionalCodesStr.substring(0, additionalCodesStr.length - 1); // truncate the last customFieldSeparator
				}
				var additionalFieldId = $hiddenElem.attr("additionalFieldId");
				if (additionalFieldId) {
					$("#" + additionalFieldId).val(additionalCodesStr);
				} 
			}
		}
		$("#dialog").jqmHide();					
	},
	
	deleteCode: function(elem) {
		var $elem = $(elem);
		var $parent = $(elem).parent();
		var $hiddenElem = null;
		if ($parent.attr("id").indexOf("additional-") == 0) {
			var additionalFieldId = $parent.parent().parent().parent().children("input[type=hidden]").attr("additionalFieldId");
			if (additionalFieldId) {
				$hiddenElem = $("#" + additionalFieldId);
			}
		}
		else {
			$hiddenElem = $parent.parent().parent().children("input[type=hidden]");
		}
		
		var selectedCode = $parent.attr("code");
		$hiddenElem.each(function() {
			var $hiddenElem = $(this);
			Lookup.removeSelectedVal($hiddenElem, selectedCode);
		});
		 
		$parent.fadeOut("fast", function() {
			$(this).remove();
		});
	},
	
	loadQueryLookup: function(elem, showOtherField, url) {
		if (!showOtherField) {
			showOtherField = false;
		}
		this.lookupCaller = $(elem).parent();		
		var fieldDef = $(elem).attr("fieldDef");
		if (!url) {
			url = "queryLookup.htm";
		}
		$.get(url, "fieldDef=" + fieldDef + "&showOtherField=" + showOtherField, function(html, textStatus) {
			var $dialogElem = $("#dialog");
			if ($dialogElem) {
				$dialogElem.html(html);
				Lookup.commonBindings();
				Lookup.singleCommonBindings();
				Lookup.queryLookupBindings();
				$dialogElem.jqmShow();
			}
		});
	},
	
	loadRelationshipQueryLookup: function(elem) {
		this.loadQueryLookup(elem, null, "relationshipQueryLookup.htm");
	},

	commonBindings: function() {
		$("input.defaultText", $("#dialog")).bind("focus", function() {
			var $elem = $(this);
			if ($elem.val() == $elem.attr("defaultValue")) {
				$elem.removeClass("defaultText");
				$elem.val("");
			}
		});
		$("input.defaultText", $("#dialog")).bind("blur", function() {
			var $elem = $(this);
			if ($elem.val() == "") {
				$elem.addClass("defaultText");
				$elem.val($elem.attr("defaultValue"));
			}
		});
		$("div.otherOptionDiv input.defaultText", $("#dialog")).bind("keyup", function(event) {
			if (event.keyCode == 13) { // return key
				$("div.modalContent input#doneButton", $("#dialog")).click();
			}
		});
	},
	
	singleCommonBindings: function() {
		$("div.modalContent input#cancelButton", $("#dialog")).bind("click", function() {
			$("#dialog").jqmHide();					
		});
		$("div.modalContent form", $("#dialog")).bind("submit", function() {
			return false;
		});
		$("div.modalContent").keydown(function(event) {
			if (event.keyCode == 27) {
				$("#dialog").jqmHide();					
			}
		});
	},
	
	queryLookupBindings: function() {
		$("#queryLookupForm #doneButton").bind("click", function() {
			Lookup.useQueryLookup();
			$("#dialog").jqmHide();	
		});
		$("#queryLookupForm input[type=text]").bind("keyup", function(event) {
			if (event.keyCode == 13) { // return key
				Lookup.doQuery(Lookup.getQueryData);
			}
		});
		$("#queryLookupForm #findButton").bind("click", function() {
			Lookup.doQuery(Lookup.getQueryData);
		});		
	},

	getQueryFormCriteria: function(formId, obj) {
		var $form = $(formId);
		var thisVal = $form.find('#searchOption').val();
		obj['searchOption'] = thisVal;
		if (thisVal == 'fullText') {
			var $textField = $form.find('#fullText');
			var thisVal = $textField.val();
			if (thisVal == $textField.attr('defaultValue')) {
				thisVal =  '';  // use empty string if no value entered
			}
			obj['fullText'] = thisVal;
		}
		else {
			var $acctNumElem = $form.find('#accountNumber');
			var acctNumVal = $acctNumElem.val();
			if (acctNumVal != $acctNumElem.attr('defaultValue')) {
				obj['accountNumber'] = acctNumVal;
			}

			var fieldClass = thisVal + 'Entry';
			$form.find('.' + fieldClass).each(function() {
				var $elem = $(this);
				var thisVal = $elem.val();
				if (thisVal != $elem.attr('defaultValue')) {
					obj[$elem.attr('name')] = thisVal;
				}
			});		
		}
		return obj;
	},

	getQueryData: function() {
		var obj = { view: "resultsOnly",
			fieldDef: $("div.modalSearch input#fieldDef").val()
		};
		return Lookup.getQueryFormCriteria('#queryLookupForm', obj);
	},
	
	clickEventHandler: function() {
		var $prevElem = null;
		$("div.modalContent ul.queryUl", $("div.modalContentWrapper")).bind("click", function(event) {
			var $target = $(event.target);
			if ($target.is(":radio")) { 				
				if ($prevElem) {
					$prevElem.parent("li").removeClass("selected");
				} 
				if ($target.get(0).checked) {
					$target.parent("li").addClass("selected"); 
				}
				else {
					$target.parent("li").removeClass("selected"); 
				}
				$prevElem = $target;
			}
		});		
		$("#queryResultsDiv").removeClass("noDisplay");
	},
	
	doQuery: function(dataFunction) {
		var url = $("div.modalContent form").attr("action");
		Lookup.showWaitIndicator();
		$("#queryResultsDiv").load(url, dataFunction(), function() {
			Lookup.hideWaitIndicator();
			Lookup.clickEventHandler();
		});
	},
	
	loadMultiQueryLookup: function(elem) {
		var $elem = $(elem);
		this.lookupCaller = $elem.siblings(".lookupScrollContainer").children(".multiLookupField");		
		var fieldDef = $elem.attr("fieldDef");
		var queryString = this.serializeMultiQueryLookup(this.lookupCaller.children("div.multiQueryLookupOption"));
		$.ajax({
			type: "GET",
			url: "multiQueryLookup.htm",
			data: queryString + "&fieldDef=" + fieldDef,
			success: function(html){
				$("#dialog").html(html);
				Lookup.commonBindings();
				Lookup.multiCommonBindings();
				Lookup.multiQueryLookupBindings();
				$("#dialog").jqmShow();
			}
		});
	},

	loadTreeView: function(elem) {
		var $elem = $(elem);
		var fieldDef = $elem.attr("fieldDef");
		var constituentId = $elem.attr("constituentId");
		var divid = $elem.attr("divid");
		$elem.hide();
		var treeurl = "constituentHeirarchy.json?memberConstituentId=" + constituentId + "&fieldDef=" + fieldDef;
		
		var tree = new Ext.tree.TreePanel({
	        el:divid,
	        height:200,
	        width:250,
		    useArrows:true,
		    autoScroll:true,
		    animate:true,
		    containerScroll: true,

		    loader: new Ext.tree.TreeLoader({
		    	  dataUrl:treeurl, 
		    	  requestMethod: 'GET'
		    }),
		 
		    root:  new Ext.tree.AsyncTreeNode({
		        text: 'Organization',
		        draggable:false,
		        disabled: true,
		        expandable: false,
		        expanded: true,
		        id:'0'  
		    })
		});
		
	    tree.render();
	    tree.root.expand();
	},
	
	/* For previously selected options, create a query string from the attribute 'selectedIds' on each text box.  The queryString is the format selectedIds=selectedName|selectedId^selectedName|selectedId2^... */
	serializeMultiQueryLookup: function(options) {
		var queryString = "selectedIds=";
		$(options).each(function() {
			var $elem = $(this);
			queryString += escape($elem.attr("id").replace("lookup-", "")) + "|" + escape($elem.attr("selectedId")) + "^";
		});
		return queryString;
	},
	
	loadMultiPicklist: function(elem, showAdditionalField) {
		if (!showAdditionalField) {
			showAdditionalField = false;
		}
		this.lookupCaller = $(elem).siblings(".lookupScrollContainer").children(".multiLookupField");
		var queryString = this.serializeMultiPicklist(this.lookupCaller.children(), showAdditionalField);

		$.ajax({
			type: "POST",
			url: "multiPicklist.htm",
			data: queryString + "&showAdditionalField=" + showAdditionalField,
			success: function(html){
				$("#dialog").html(html);
				Lookup.commonBindings();
				Lookup.multiCommonBindings();
				Lookup.multiPicklistBindings();
				$("#dialog").jqmShow();
			}
		});
	},	
	
	/* For previously selected options, create a query string in the format selectedOptions=code1|displayValue1|reference1|selected1^code2|displayValue2|reference2|selected2^ ... */
	serializeMultiPicklist: function(options, showAdditionalField) {
		var queryString = "selectedOptions=";
		$(options).each(function() {
			var $elem = $(this);
			if ($elem.hasClass("multiPicklistOption") && $elem.hasClass("clone") == false) {
				queryString += escape($elem.attr("selectedId")) + "|" + escape($.trim($elem.text())) + "|" + escape($elem.attr("reference")) + "|" + ($elem.css("display") == "none" ? "false" : "true") + "^";
			}
			else if ($elem.attr("type") == "hidden") { // the assumption is that the hidden fields will be AFTER all the option fields
				queryString += "&" + $elem.serialize();
			}
		});
		if (showAdditionalField) {
			var additionalFieldId = Lookup.lookupCaller.parent().children("input[type=hidden]").attr("additionalFieldId");
			if (additionalFieldId) {
				queryString += "&additionalFieldOptions=" + escape($("#" + additionalFieldId).val());
			}				
		}
		return queryString;
	},
	
	multiPicklistBindings: function() {
		$("#multiPicklistForm #doneButton").bind("click", function() {
			var idsStr = "";
			var selectedNames = new Object();
			$("ul#selectedOptions li", $("#dialog")).each(function() {
				var $chkBox = $(this).children("input[type=checkbox]").eq(0);
				var thisId = $chkBox.attr("id");
				idsStr += thisId + OrangeLeap.customFieldSeparator;
				selectedNames[$.trim($chkBox.attr("name"))] = true;
			});
			idsStr = (idsStr.length > 0 ? idsStr.substring(0, idsStr.length - 1) : idsStr); // remove the trailing customFieldSeparator

			var $hiddenElem = Lookup.lookupCaller.parent().children("input[type=hidden]");
			$hiddenElem.val(idsStr);
			
			Lookup.lookupCaller.children("div.multiPicklistOption").each(function() {
				if (selectedNames[$.trim($(this).attr("selectedId"))] === true) {
					$(this).css("display", "").vkfade(true);
				}
				else {
					$(this).css("display", "none");
				}
			});

			var additionalOptions = null;
			$("#additionalOptionsText").each(function() {
				var val = $(this).val();
				if (jQuery.trim(val) != "") {
					additionalOptions = val.split("\n");
				}
			});
			/* Remove the old options */
			var $additionalOptionsContainer = Lookup.lookupCaller.children("div.additionalOptions");
			$additionalOptionsContainer.children().each(function() {
				$(this).remove();
			});
			var hiddenAdditionalOptionsVal = "";
			if (additionalOptions != null) {
				var $cloneBase = Lookup.lookupCaller.children("div.clone").eq(0);
				for (var z = additionalOptions.length - 1; z >= 0; z--) {
					var newOption = jQuery.trim(additionalOptions[z]);
					if (newOption != "") {
						var $newClone = $cloneBase.clone(false);
						$newClone.attr("id", "div-" + Math.floor((Math.random() * 1000) + (Math.random() * 100)));
						$newClone.removeClass("clone").removeClass("noDisplay");
						$newClone.removeAttr("style");
						$newClone.find("span").text(newOption);
						$newClone.prependTo($additionalOptionsContainer);
						$newClone.vkfade(true);
						hiddenAdditionalOptionsVal += newOption + OrangeLeap.customFieldSeparator; 
					}
				}
			}
			if (hiddenAdditionalOptionsVal.length > 0) {
				hiddenAdditionalOptionsVal = hiddenAdditionalOptionsVal.substring(0, hiddenAdditionalOptionsVal.length - 1); // truncate the last customFieldSeparator
			}
			var additionalHiddenElemId = $hiddenElem.attr("additionalFieldId");
			if (additionalHiddenElemId) {
				$("#" + additionalHiddenElemId).val(hiddenAdditionalOptionsVal);
			}
			$(Lookup.lookupCaller).each(Picklist.togglePicklist);
			$("#dialog").jqmHide();	
		});			
	},
	
	useQueryLookup: function() {
		var $elem = $('#queryResultsUl input[name=option]:checked');
		var selectedVal = $elem.val();
		
		if (selectedVal) {
			Lookup.lookupCaller.parent().children(":hidden").eq(0).val(selectedVal);
			var displayVal = $elem.attr('displayvalue');
			Lookup.lookupCaller.children("div.queryLookupOption").remove();
			var $cloned = Lookup.lookupCaller.parent().find("div.clone").clone(true);
			$("#" + Lookup.lookupCaller.parent().children(":hidden").attr("otherFieldId")).val("");
			$cloned.attr("selectedId", selectedVal);
			$cloned.attr("id", "lookup-" + selectedVal);
			var $popLink = $cloned.find("a[target='_blank']");
			$popLink.attr("href", $elem.siblings("a[target='_blank']").attr("href"));
			$popLink.text(displayVal);
			$cloned.removeClass("clone").removeClass("noDisplay");
			$cloned.prependTo(Lookup.lookupCaller);
			$cloned.vkfade(true);
		}
		else {
			var $optionElem = $("div.modalContent input#otherOptionText");
			var typedVal = $optionElem.val();
			if (typedVal != $optionElem.attr("defaultValue") && ! Ext.isEmpty(typedVal)) {
				Lookup.lookupCaller.children("div.queryLookupOption").remove();
				Lookup.lookupCaller.parent().children(":hidden").eq(0).val(""); // reset the corresponding field to nothing
				var $cloned = Lookup.lookupCaller.parent().find("div.clone").clone(true);
				$("#" + Lookup.lookupCaller.parent().children(":hidden").attr("otherFieldId")).val(typedVal);
				$cloned.attr("id", "lookup-" + typedVal);
				var $popLink = $cloned.find("a[target='_blank']");
				$popLink.remove();
				$cloned.find("span").text(typedVal);
				$cloned.removeClass("clone").removeClass("noDisplay");
				$cloned.prependTo(Lookup.lookupCaller);
				$cloned.vkfade(true);
			}
		}
	},
	
	/** Deprecated, to be removed */
	setCodeValue: function(val, text) {
		this.lookupCaller.val(val);
		$('#dialog').jqmHide();
		this.lookupCaller = null;
		return false;
	},
	
	getMultiQueryData: function() {
		var selectedIdsStr = "";
		$("ul#selectedOptions li :checkbox").each(function() {
			selectedIdsStr += $(this).attr("displayvalue") + "|" + $(this).attr("id") + "^";
		});
		if (selectedIdsStr.length > 0) {
			selectedIdsStr = selectedIdsStr.substring(0, selectedIdsStr.length - 1); // remove the last '^'
		}
		var obj = {
			fieldDef: $("#fieldDef").val(),
			selectedIds: selectedIdsStr
		};
		return Lookup.getQueryFormCriteria('#multiQueryLookupForm', obj);
	},	
	
	doMultiQuery: function(dataFunction) {
		var url = $("div.modalContent form").attr("action");
		Lookup.showWaitIndicator();
		$("#availableOptions").load(url, dataFunction(), Lookup.hideWaitIndicator);
	},
	
	multiQueryLookupBindings: function() {		
		$("#multiQueryLookupForm input[type=text]").bind("keyup", function(event) {
			if (event.keyCode == 13) { // return key
				Lookup.doMultiQuery(Lookup.getMultiQueryData);
			}
		});
		$("#multiQueryLookupForm #findButton").bind("click", function() {
			Lookup.doMultiQuery(Lookup.getMultiQueryData);
		});		
		$("#multiQueryLookupForm #doneButton").bind("click", function() {
			var idsStr = "";
			var names = new Array();
			var ids = new Array();
			var hrefs = new Array();
			$("ul#selectedOptions :checkbox", $("#dialog")).each(function() {
				var $chkboxElem = $(this);
				var thisId = $chkboxElem.attr("id");
				idsStr += thisId + OrangeLeap.customFieldSeparator;
				ids[ids.length] = thisId;
				names[names.length] = $.trim($chkboxElem.attr('displayValue'));
				hrefs[hrefs.length] = $chkboxElem.siblings("a[href]").attr("href");
			});
			idsStr = (idsStr.length > 0 ? idsStr.substring(0, idsStr.length - 1) : idsStr); // remove the trailing customFieldSeparator

			Lookup.lookupCaller.parent().children("input[type=hidden]").eq(0).val(idsStr);
			
			Lookup.lookupCaller.children("div.multiQueryLookupOption").remove();
			
			var $toBeCloned = Lookup.lookupCaller.parent().find("div.clone");
			
			for (var x = names.length - 1; x >= 0; x--) {
				var $cloned = $toBeCloned.clone(true);
				var $popLink = $cloned.find("a[target='_blank']");
				$cloned.attr("id", "lookup-" + names[x]);
				$cloned.attr("selectedId", ids[x]);
				$popLink.attr("href", hrefs[x]);
				$popLink.text(names[x]);
				
				$cloned.removeClass("clone").removeClass("noDisplay");
				$cloned.prependTo(Lookup.lookupCaller);
				$cloned.vkfade(true);
			} 
			$("#dialog").jqmHide();					
		});		
	},
	
	multiCommonBindings: function() {
		$("table.multiSelect thead input[type=checkbox]", $("#dialog")).bind("click", function() {
			var isChecked = $(this).attr("checked");
			var tdOrder = $(this).attr("selection") === "available" ? "first" : "last";
			var isFirst = true;
			$("table.multiSelect tbody td:" + tdOrder + " li").each(function() {
				$target = $(this);
				var optionType = ($target.parent("ul").attr("id") === "selectedOptions" ? "available" : "selected");
				var $clone = MultiSelect.moveOption($target, optionType);
				if (isFirst) {
					var ulElem = $("ul#" + optionType + "Options").get(0);
					ulElem.scrollTop = ulElem.scrollHeight - $clone.height();
					isFirst = false;
				}
			});
		});
		$("table.multiSelect tbody", $("#dialog")).bind("click", function(event) {
			var $target = $(event.target);
			if ($target.is("input[type=checkbox]") && $target.attr("checked")) { 
				$target = $target.parent("li");
				var optionType = ($target.parent("ul").attr("id") === "selectedOptions" ? "available" : "selected");
				var $clone = MultiSelect.moveOption($target, optionType);
				var ulElem = $("ul#" + optionType + "Options").get(0);
				ulElem.scrollTop = ulElem.scrollHeight - $clone.height();
			}
		});
		$("div.modalContent form", $("#dialog")).bind("submit", function() {
			return false;
		});
		$("div.modalContent").keydown(function(event) {
			if (event.keyCode == 27) {
				$("#dialog").jqmHide();					
			}
		});
		$("div.modalContent input#cancelButton", $("#dialog")).bind("click", function() {
			$("#dialog").jqmHide();					
		});
	},
	
	deleteOption: function(elem) {
		var $parent = $(elem).parent();
		var $hiddenElems = $parent.parent().parent().children("input[type=hidden]");
		
		$hiddenElems.each(function() {
			var $hiddenElem = $(this);
			var otherFieldId = $hiddenElem.attr("otherFieldId");
			if (otherFieldId) { 
				$("#" + otherFieldId).val("");
			}
			var selectedId = $parent.attr("selectedId");
			Lookup.removeSelectedVal($hiddenElem, selectedId);
		});
		 
		$parent.fadeOut("fast", function() {
			if ($(this).hasClass("multiQueryLookupOption") || $(this).hasClass("queryLookupOption")) {
				// For query lookups, remove the node
				$(this).remove();
			}
			else {
				// For multi-picklists, don't remove, just hide
				$(this).css("display", "none");
				$parent.parent("div.multiPicklist").each(Picklist.togglePicklist);
			}
		});
	},

	deleteAdditionalOption: function(elem) {
		var $elem = $(elem);
		var optionText = jQuery.trim($elem.siblings("span").eq(0).text());
		if (optionText) {
			var $parentElem = $elem.parent();
			var additionalFieldId = $parentElem.parent().parent().parent().children("input[type=hidden]").attr("additionalFieldId");
			if (additionalFieldId) {
				var $additionalFieldElem = $("#" + additionalFieldId);
				Lookup.removeSelectedVal($additionalFieldElem, optionText);
				
				$parentElem.fadeOut("fast", function() {
					$(this).remove();
				});
			}
		}
	},
	
	deleteAssociation: function(elem) {
		var $parent = $(elem).parent();
		var $hiddenElems = $parent.parent().parent().children("input[type=hidden]");
		
		$hiddenElems.each(function() {
			var $hiddenElem = $(this);
			var selectedId = $parent.attr("selectedId");
			Lookup.removeSelectedVal($hiddenElem, selectedId);
		});
		 
		$parent.parents("li.side").fadeOut("fast", function() {
			$(this).addClass("noDisplay");
		});
	},
	
	removeSelectedVal: function($elem, valueToCompare, separator) {
		if ( ! separator) {
			separator = OrangeLeap.customFieldSeparator;
		}
		var vals = $elem.val().split(separator);
		var valsLen = vals.length;
		var newVals = "";
		for (var x = 0; x < valsLen; x++) {
			if (vals[x] != valueToCompare) {
				newVals += vals[x] + separator;
			}
		}
		if (newVals.length > 0) {
			newVals = newVals.substring(0, newVals.length - 1);
		}
		$elem.val(newVals);
	},
	
	showWaitIndicator: function() {
		$("div.modalSearch input[type=text]:visible:last", "div.modalContent form").addClass("showWait");
	},
	
	hideWaitIndicator: function() {
		$("div.modalSearch input[type=text]:visible:last", "div.modalContent form").removeClass("showWait");
	}
};
var MultiSelect = {
	moveOption: function(optionElem, thisOptionType) {
		var prevOptionType = (thisOptionType === "selected" ? "available" : "selected");
		var $clone = $(optionElem).clone();
		this.uncheck($clone);
		$(optionElem).remove();
		$("input#" + prevOptionType + "All").removeAttr("checked");
		$clone.appendTo("ul#" + thisOptionType + "Options");
		$clone.vkfade();
		return $clone;
	},
		
	check: function(elem) {
		elem.children("input[type=checkbox]").eq(0).attr("checked", "true");
	},
	
	uncheck: function(elem) {
		elem.children("input[type=checkbox]").eq(0).removeAttr("checked");
	}
};
// Create a placeholder console object in case Firebug is not present.
if (typeof console == "undefined" || typeof console.log == "undefined") console = { log: function() {} };
// Initialize the console (workaround for current Firebug defect)
console.log();

// Class for handling password change
function PasswordChange() {

    var that = this;

    this.init = function() {
        // add a click handler to the Change Password link
        Ext.fly('sec-changepwd').on('click',function(){that.passwordWindow.show(); return false;});

        that.passwordForm = new Ext.form.FormPanel({
            labelWidth: 120,
            frame:true,
            bodyStyle:'padding:10px',
            width: 370,
            itemCls: 'spaced',
            defaults: {width: 180},
            defaultType: 'textfield',
            monitorValid: true,
            keys: [{key : [10,13],
                    fn: function() { if (that.passwordForm.getForm().isValid()) that.submit();}
            }],
            items: [
                {fieldLabel: 'New Password', name: 'password', allowBlank:false, inputType: 'password', minLength: 3},
                {fieldLabel: 'Confirm Password', name: 'confirm', allowBlank:false, inputType: 'password', minLength: 3,
                    invalidText: 'Value does not match the New Password',validator: that.passwordValidator}
            ],
            buttons: [{text: 'Change Password', type: 'submit', formBind: true, handler: that.submit},
            {text: 'Cancel', handler: function() {that.passwordWindow.hide(); that.passwordForm.getForm().reset();}}]
        });

        that.passwordWindow = new Ext.Window({
            layout:'fit',
            height: 170,
            title: 'Change Password',
            closeAction:'hide',
            modal: true,
            resizable: false,
            items: that.passwordForm
        });

        that.passwordWindow.on('show', function(win){
            var form = that.passwordForm.getForm();
            form.findField('password').focus(false,600);
        });
    };

    this.submit = function() {

        that.passwordWindow.hide();
        var form = that.passwordForm.getForm();
        // form validation won't allow submit unless the new and confirm values match,
        // so we only need to grab the new one
        var newpass = form.findField('password').getValue();
        form.reset();
        

        Ext.Ajax.request({
            url: 'changePassword.json',
            params: {password: newpass},
            callback: function(options, success, response) {
                var obj = Ext.decode(response.responseText);

                if (obj.success === true) {
                    Ext.MessageBox.alert('Success', 'Your password was changed');
                } else {
                    Ext.MessageBox.alert('Password Change Failed', obj.error);
                }
            }
        });

    };

    this.passwordValidator = function(val) {
        var pass = that.passwordForm.getForm().findField('password').getValue();
        return (val === pass);
    };
}

Ext.ns('OrangeLeap');

OrangeLeap.BulkSaveStore = function(config){
 	OrangeLeap.BulkSaveStore.superclass.constructor.call(this, config);
};

Ext.extend(OrangeLeap.BulkSaveStore, Ext.data.JsonStore, {
    removeInvalid: function(rs) {
        for (var i = rs.length-1; i >= 0; i--) {
            if (!rs[i].isValid()) { // splice-off any !isValid real records
                rs.splice(i,1);
            }
        }
    },

    /* Save only modified records */
    save: function() {
        var rs = [].concat(this.getModifiedRecords());
        this.removeInvalid(rs);
        this.doTransaction('update', rs);
        return true;
    },

    /* Save all records, regardless if they are modified or not */
    saveAll: function() {
        var rs = [].concat(this.data.items);
        this.removeInvalid(rs);
        var txn = rs.length > 0 ? 'update' : 'destroy';
        this.doTransaction(txn, rs);
        return true;
    }
});

OrangeLeap.OrderedBulkSaveStore = function(config){
 	OrangeLeap.OrderedBulkSaveStore.superclass.constructor.call(this, config);
};

Ext.extend(OrangeLeap.OrderedBulkSaveStore, OrangeLeap.BulkSaveStore, {
    save: function() {
        this.suspendEvents();
        if (this.data && this.data.items) {
            var items = this.data.items;
            var len = items.length;
            for (var x = 0; x < len; x++) {
                var nextIndex = x + 1;
                if (items[x].get('f') != nextIndex) {
                    items[x].set('f', nextIndex);   // Update the itemOrder if necessary
                }
            }
        }
        var rs = [].concat(this.getModifiedRecords());
        var rsIds  = {};
        for (var i = rs.length-1; i >= 0; i--) {
            if (!rs[i].isValid() || rsIds[rs[i].id]) { // splice-off any !isValid or duplicate records
                rs.splice(i,1);
            }
            else {
                rsIds[rs[i].id] = rs[i];
            }
        }
        this.resumeEvents();
        this.doTransaction('update', rs);
        return true;
    }
});


OrangeLeap.GroupingBulkSaveStore = function(config){
 	OrangeLeap.GroupingBulkSaveStore.superclass.constructor.call(this, config);
};

Ext.extend(OrangeLeap.GroupingBulkSaveStore, Ext.data.GroupingStore, {
    removeInvalid: function(rs) {
        for (var i = rs.length-1; i >= 0; i--) {
            if (!rs[i].isValid()) { // splice-off any !isValid real records
                rs.splice(i,1);
            }
        }
    },

    /* Save only modified records */
    save: function() {
        var rs = [].concat(this.getModifiedRecords());
        this.removeInvalid(rs);
        this.doTransaction('update', rs);
        return true;
    },

    /* Save all records, regardless if they are modified or not */
    saveAll: function() {
        var rs = [].concat(this.data.items);
        this.removeInvalid(rs);
        var txn = rs.length > 0 ? 'update' : 'destroy';
        this.doTransaction(txn, rs);
        return true;
    }
});
