<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Registration</title>
</head>

<body>
<div>
    <form:form method="POST" modelAttribute="personForm">
        <h2>Registration</h2>
        <div>
            <form:input type="text" path="username" placeholder="Username" autofocus="true"></form:input>
            <form:errors path="username"></form:errors>${usernameError}
        </div>
        <div>
            <form:input type="password" path="password" placeholder="Password"></form:input>
        </div>
        <div>
            <form:input type="password" path="passwordConfirm" placeholder="Confirm your password"></form:input>
            <form:errors path="password"></form:errors>${passwordError}
        </div>
        <div>
            <form:input type="text" path="name" placeholder="Name" autofocus="true"></form:input>
        </div>
        <div>
            <form:input type="text" path="surname" placeholder="Surname" autofocus="true"></form:input>
        </div>
        <div>
            <form:input type="text" path="email" placeholder="Email" autofocus="true"></form:input>
        </div>

        <button type="submit">Log In</button>
        <br>
    </form:form>
    <br>
    <a href="/">Main Page</a>

</div>
</body>
</html>
</html>