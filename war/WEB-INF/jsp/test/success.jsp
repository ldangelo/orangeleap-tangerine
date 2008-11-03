<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
</head>

<body>
<h1>SUCCESS PAGE</h1>




	<table id="myTable" class="tablesorter" cellspacing="0" cellpadding="0">
		<thead>
			<tr>
			    <th>&nbsp;</th>
			    <th>Account #</th>
			    <th>Last Name</th>
			    <th>First Name</th>
			    <th>Org Name</th>
			    <th>Address</th>
			    <th>City</th>
			    <th>State</th>
			    <th>Zip</th>
			</tr>
			</thead>
		<tbody>
			<c:forEach items="${personList}" var="person">
				<tr>
					<td><a href="person.htm?personId=${person.id}">Edit</a></td>
				    <td>707</td>
				    <td>${person.lastName}</td>
				    <td>${person.firstName}</td>
				    <td>${person.organizationName}</td>
				    <td>${person.addressMap.home.addressLine1}</td>
				    <td>${person.addressMap.home.city}</td>
				    <td>${person.addressMap.home.stateProvince}</td>
				    <td>${person.addressMap.home.postalCode}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</body>
</html>