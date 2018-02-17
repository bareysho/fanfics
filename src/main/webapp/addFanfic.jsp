<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <title>Create fanfic</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
    <meta name="format-detection" content="telephone=no">

    <link href="http://fonts.googleapis.com/css?family=Roboto:400,300,700,900|Roboto+Condensed:400,300,700"
          rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="resources/css/demo.css">
    <link rel="stylesheet" href="resources/css/dropify.min.css">
    <link href="resources/css/create.css" rel="stylesheet">
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.js"></script>
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script>

</head>

<body>

<div class="container">
    <div class="row">
        <div style="width: 60%; margin: 35px auto;">
            <h2>Create fanfic </h2>
            <hr>

            <form:form id="addFanfic" action="/addFanfic" modelAttribute="fanficForm" enctype="multipart/form-data"
                       method="post">

                <div class="form-group">
                    <label for="fanficName"><h4>Title:</h4></label>
                    <form:input style="height: 35px" type="text" path="fanficName" class="form-control" id="fanficName"
                                placeholder="Input title"/>
                </div>

                <div class="form-group">
                    <label for="fanficGenre"><h4>Genre:</h4></label>
                    <form:select path="genreId" class="form-control" id="fanficGenre">
                        <option>1</option>
                        <option>2</option>
                        <option>3</option>
                        <option>4</option>
                    </form:select>
                </div>

                <div class="form-group">
                    <label for="input-file-now"><h4>Image:</h4></label>
                    <input type="file" name="files" id="input-file-now" class="dropify"/>
                    <br/>
                </div>

                <div class="form-group">
                    <label for="description"><h4>Description:</h4></label>
                    <form:textarea class="form-control" path="description" rows="5" id="description"></form:textarea>
                </div>

                <div class="form-group">
                    <button class="btn btn-lg btn-primary btn-block" id="js-upload-submit">Create</button>
                </div>

            </form:form>
        </div>
    </div>
</div>

<script src="resources/js/dropify.min.js"></script>
<script>
    $(document).ready(function () {
        // Basic
        $('.dropify').dropify();
        // Translated
        $('.dropify-fr').dropify({
            messages: {
                default: 'Glissez-déposez un fichier ici ou cliquez',
                replace: 'Glissez-déposez un fichier ou cliquez pour remplacer',
                remove: 'Supprimer',
                error: 'Désolé, le fichier trop volumineux'
            }
        });
        // Used events
        var drEvent = $('#input-file-events').dropify();
        drEvent.on('dropify.beforeClear', function (event, element) {
            return confirm("Do you really want to delete \"" + element.file.name + "\" ?");
        });
        drEvent.on('dropify.afterClear', function (event, element) {
            alert('File deleted');
        });
        drEvent.on('dropify.errors', function (event, element) {
            console.log('Has Errors');
        });
        var drDestroy = $('#input-file-to-destroy').dropify();
        drDestroy = drDestroy.data('dropify')
        $('#toggleDropify').on('click', function (e) {
            e.preventDefault();
            if (drDestroy.isDropified()) {
                drDestroy.destroy();
            } else {
                drDestroy.init();
            }
        })
    });
</script>

</body>
</html>