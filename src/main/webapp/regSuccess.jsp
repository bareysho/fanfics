<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registration successful</title>
    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container">
    <h2>Dear, ${pageContext.request.userPrincipal.name} You registered successfully, nut you need to confirm
        email. </h2>

    <h3>We sent you a confirmation mail. Follow the link from your email to confirm registration</h3>
    <form method="POST" action="${contextPath}/sendEmailConfirmation">
        <button type="submit">Resend</button>
    </form>
</div>


</body>
</html>
