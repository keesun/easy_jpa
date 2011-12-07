<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="spring" %>
<%@ page session="false" %>
<html>
<head>
	<title>Product</title>
</head>
<body>
<h3>Fetures</h3>
<ul>
    <li><a href="/">Menu only</a></li>
    <li><a href="/data">Create data</a></li>
    <li><a href="/category">Load all category</a></li>
    <li><a href="/evict">Evict category cache</a></li>
    <li><a href="/evictall">Evict all caches</a></li>
    <li><a href="/product/with/all">Load all product and all references</a></li>
    <li><a href="/product/with/detail">Load all product with only productDetails</a></li>
    <li><a href="/product/with/category">Load all product with only categrories</a></li>
</ul>

<h3>Products</h3>
<c:forEach items="${prdList}" var="p">
  <li>${p.name} | ${p.category.name} | ${p.exceptionalCategory.name} | ${p.productDetails.details} | ${p.productInfo.info}</li>
</c:forEach>

</body>
</html>
