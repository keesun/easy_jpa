<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="spring" %>
<%@ page session="false" %>
<html>
<head>
	<title>Product</title>
</head>
<body>

<h3>Products</h3>
<c:forEach items="${prdList}" var="p">
  <li>${p.name} | ${p.category.name} | ${p.exceptionalCategory.name}</li>
</c:forEach>

</body>
</html>
