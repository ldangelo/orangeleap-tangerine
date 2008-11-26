<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div id="pager" class="pager">
	<form>
		<img class="first" src="images/icons/first.png"/>
		<img class="prev" src="images/icons/prev.png"/>
		<span class="pagedisplay"></span>
		<img class="next" src="images/icons/next.png"/>
		<img class="last" src="images/icons/last.png"/>
		<select class="pagesize">
			<option selected="selected"  value="10">10</option>
			<option value="20">20</option>
			<option value="30">30</option>
			<option value="40">40</option>
		</select>
	</form>
</div>