<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="menu">
	<ul>
		<li><a href="<c:url value="/calculator.jsp"/>">Calculator (Stateless Session Bean Client)</a></li>
		<li><a href="<c:url value="/shopping_cart.jsp"/>">Shopping Cart (Stateful Session Bean Client)</a></li>
	</ul>
</div>
