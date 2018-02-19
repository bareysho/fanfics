<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <title>Title</title>

    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>
</head>
<body>
Название
<div>${currentFanfic.fanficName}</div>
Описание
<div>${currentFanfic.description}</div>
Картинка
<div><img src="${currentFanfic.image}"></div>

<div>
    Содержание:
    <ul>
        <c:forEach items="${fanficChapters}" var="chapter">
            <a href="#">
                <li>${chapter.chapterName}</li>
            </a>
        </c:forEach>
    </ul>
</div>
</body>
</html>
