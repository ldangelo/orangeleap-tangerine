$(document).ready(function() {
	$("a.treeNodeLink").bind("click", function(event) {
		return OrangeLeap.expandCollapse(this);
	});
});
