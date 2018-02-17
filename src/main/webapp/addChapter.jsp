<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Create fanfic</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="format-detection" content="telephone=no">

    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>

    <!-- include summernote css/js -->
    <link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote.css" rel="stylesheet">
    <script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.9/summernote.js"></script>
</head>

<body>

<div class="container">
    <div class="row">
        <div style="width: 70%; margin: 35px auto;">
            <h2>Add chapter </h2>
            <hr>

            <form:form action="/addChapter" modelAttribute="chapterForm" method="post">

                <div class="form-group">
                    <label for="chapterName"><h4>Title:</h4></label>
                    <form:input type="text" path="chapterName" class="form-control" id="chapterName"
                                placeholder="Input title"/>
                </div>

                <div class="form-group">
                    <label for="summernote"><h4>Content:</h4></label>
                    <form:textarea rows="15" id="summernote" path="text" name="editordata"></form:textarea>
                </div>

                <div class="form-group">
                    <button type="submit" class="btn btn-lg btn-primary btn-block" id="js-upload-submit">Add</button>
                </div>
            </form:form>


            <script>
                $(document).ready(function () {
                    $('#summernote').summernote({
                        placeholder: 'Enter text ...',
                        height: 400
                    });
                });
            </script>
        </div>
    </div>
</div>

</body>
</html>