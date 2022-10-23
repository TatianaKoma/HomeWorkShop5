<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Welcome</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/style.css"/>
</head>
<body>
<h1>Welcome</h1>
<h2>${message}</h2>


<a href="${pageContext.request.contextPath}/getPersons">Person</a>
<br>
<a href="${pageContext.request.contextPath}/getShops">Shop</a>
<br>
<a href="${pageContext.request.contextPath}/getCarts">Cart</a>
<br>
<a href="${pageContext.request.contextPath}/getProducts">Product</a>
</body>

</html>
