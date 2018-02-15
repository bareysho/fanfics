<%--
  Created by IntelliJ IDEA.
  User: bareysho
  Date: 13.02.2018
  Time: 21:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <h2>User Information</h2>

        <form class="form-horizontal">
            <fieldset>

                <!-- Text input-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="textinput">Username</label>
                    <div class="col-md-4">
                        <input id="textinput" name="textinput" placeholder="Username" class="form-control input-md" required="" value="${username}" type="text">

                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label" for="textinput">Email</label>
                    <div class="col-md-4">
                        <input id="textinput" name="textinput" placeholder="Email" class="form-control input-md" required="" value="${email}" type="text">

                    </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="textinput">Password</label>
                    <div class="col-md-4">
                        <input id="textinput" name="textinput" placeholder="Password" class="form-control input-md" required="" type="text">

                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-4 control-label" for="textinput">Confirm password</label>
                    <div class="col-md-4">
                        <input id="textinput" name="textinput" placeholder="Confirm password" class="form-control input-md" required="" type="text">

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


        <h2>User fanfics</h2>
    </div>
</div>
</body>
</html>
