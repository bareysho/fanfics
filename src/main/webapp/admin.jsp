<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Welcome</title>

    <link href="resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/css/common.css" rel="stylesheet">
</head>
<body>

<div class="container1">
    <form id="logoutForm" method="POST" action="${contextPath}/logout">

    </form>

    <h2 style="text-align: center; vertical-align: middle;">Welcome, ${pageContext.request.userPrincipal.name}</h2>
</div>

<div class="container">
    <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
        <div class="btn-group mr-2" role="group" aria-label="First group">
            <button type="submit" id='blockBtn' form="tableForm" class="btn btn-secondary" name="action" value="ban"
                    disabled>Ban
            </button>
            <button type="submit" id='unblockBtn' form="tableForm" class="btn btn-secondary" name="action" value="unban"
                    disabled>Unban
            </button>
            <button type="submit" form="tableForm" class="btn btn-secondary" name="action" value="delete" disabled>
                Delete
            </button>
            <button type="submit" class="btn btn-secondary" onclick="document.forms['logoutForm'].submit()">Logout
            </button>
        </div>
    </div>

</div>

<div class="container">
    <table class="table table-hover">

        <thead>

        <th>Select</th>
        <th>Username</th>
        <th>Enabled</th>
        <th>Banned</th>

        </thead>
        <tbody>
        <form id="tableForm" method="POST" action="${contextPath}/ReturnCheckedUsers">
            <c:forEach items="${users}" var="user">
                <tr>
                    <td><input type="checkbox" name="checkthis" value="${user.id}"/></td>
                    <td>${user.username}</td>
                    <td name="status">${user.enabled}
                        <input type="checkbox" hidden name="isBaned"
                               <c:if test="${user.enabled}">checked=checked</c:if>/>
                    </td>
                    <td>${user.banned}</td>
                </tr>
            </c:forEach>
        </form>

        </tbody>
    </table>
</div>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>

<script type="text/javascript">
    var checkboxes = $("input[name='checkthis']"),
        banValues = $("input[name='isBaned']"),
        submitButt = $("button[name='action']");

    checkboxes.click(function () {
        submitButt.attr("disabled", !checkboxes.is(":checked"));
        var num = 0;
        jQuery.each(checkboxes, function () {
            if (checkboxes[num].checked === true && banValues[num].checked === true) {
                $("button[id='blockBtn']").attr("disabled", true);
            }
            if (checkboxes[num].checked === true && banValues[num].checked === false) {
                $("button[id='unblockBtn']").attr("disabled", true);
            }
            num++;
        });
    });
</script>

<script type="text/css" src="resources/css/bootstrap.min.css"></script>
<script type="text/css" src="resources/js/bootstrap.min.js"></script>

</body>
</html>