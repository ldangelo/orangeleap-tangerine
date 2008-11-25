<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div id="footer">
	<div class="container">
		<p style="margin-bottom:0" class="legal">
		Last login <c:out value="${lastLoginDate}"/>
		</p>
		<p class="legal">
            <% int year = 1900 + new java.util.Date().getYear(); %>
			(800) 562-5150 | &#169; Copyright <%= (year > 2008 ? "2008-" : "") + year %> MPower Open. All Rights Reserved. |
			<a href="#">Articles</a> |
			<a href="#">Resources</a>
		</p>
	</div>
</div>