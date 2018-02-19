<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Profile</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>

    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h2>User Information</h2>
        </div>

        <div class="col-md-12">
            <div class="col-md-3">
                <img width="100%" src="https://www.filecluster.com/howto/wp-content/uploads/2014/07/User-Default.jpg">
            </div>

            <div class="col-md-8">
                <form class="form-horizontal">
                    <fieldset>

                        <!-- Text input-->
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="username">Username</label>
                            <div class="col-md-4">
                                <input id="username" name="textinput" placeholder="Username"
                                       class="form-control input-md"
                                       required="" value="${username}" type="text">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label" for="email">Email</label>
                            <div class="col-md-4">
                                <input id="email" name="textinput" placeholder="Email" class="form-control input-md"
                                       required=""
                                       value="${email}" type="text">

                            </div>
                        </div>

                        <!-- Text input-->
                        <div class="form-group">
                            <label class="col-md-4 control-label" for="password">Password</label>
                            <div class="col-md-4">
                                <input id="password" name="textinput" placeholder="Password"
                                       class="form-control input-md"
                                       required="" type="text">

                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label" for="confpassword">Confirm password</label>
                            <div class="col-md-4">
                                <input id="confpassword" name="textinput" placeholder="Confirm password"
                                       class="form-control input-md" required="" type="text">

                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-md-4 control-label" for="save"></label>
                            <div class="col-md-8">
                                <button id="save" name="save" class="btn btn-success">Save</button>
                            </div>
                        </div>

                    </fieldset>
                </form>
            </div>
        </div>


        <div class="col-md-12">
            <h2>User fanfics</h2>
            <table class="table table-hover">
                <thead>

                <th>Name</th>
                <th>Image</th>
                <th>Genre</th>
                <th>Description</th>

                </thead>
                <tbody>

                <c:forEach items="${userFanfics}" var="fanfic">
                    <tr>
                            <%--<td><input type="checkbox" name="checkthis" value="${user.id}"/></td>--%>
                        <td>${fanfic.fanficName}</td>
                        <td><img style="width: 200px;max-height: 180px;" src="${fanfic.image}"></td>
                        <td>${fanfic.genreId}</td>
                        <td>${fanfic.description}</td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
