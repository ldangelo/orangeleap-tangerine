<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="jqmWindow" id="dialog">
	<div class="titleBar">
		<h4 class="dragHandle">Lookup</h4>
		<img class="jqmClose" src="images/closeButton.gif">
	</div>
		<div class="modalContent">
			<input value="" size="18" id="lookup1" />
			<input value="" size="18" id="lookup2" />
			<input type="submit" value="Go" />
			<table class="lookupTable" cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>City</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>Bob</td>
						<td>Smith</td>
						<td>Dallas</td>
					</tr>
					<tr>
						<td>Jane</td>
						<td>Doe</td>
						<td>Texarkana</td>
					</tr>
					<tr>
						<td>Sarah</td>
						<td>Smiley</td>
						<td>Ft. Worth</td>
					</tr>
				</tbody>
			</table>
		</div>
</div>