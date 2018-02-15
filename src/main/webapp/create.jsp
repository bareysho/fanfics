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

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

    <link href="http://fonts.googleapis.com/css?family=Roboto:400,300,700,900|Roboto+Condensed:400,300,700"
          rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="resources/css/demo.css">
    <link rel="stylesheet" href="resources/css/dropify.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <link rel="apple-touch-icon" href="//mindmup.s3.amazonaws.com/lib/img/apple-touch-icon.png">
    <link href="resources/css/prettify.css" rel="stylesheet">
    <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.no-icons.min.css"
          rel="stylesheet">
    <%--<link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-responsive.min.css" rel="stylesheet">--%>
    <link href="//netdna.bootstrapcdn.com/font-awesome/3.0.2/css/font-awesome.css" rel="stylesheet">
    <script id="twitter-wjs" src="https://platform.twitter.com/widgets.js"></script>
    <script id="facebook-jssdk" src="https://connect.facebook.net/en_GB/all.js#xfbml=1"></script>
    <script src="resources/js/jquery.hotkeys.js"></script>
    <script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
    <script src="resources/js/prettify.js"></script>
    <link href="resources/css/create.css" rel="stylesheet">
    <script src="resources/js/bootstrap-wysiwyg.js"></script>
    <script type="text/javascript" charset="utf-8" async=""
            src="https://platform.twitter.com/js/button.5f64a1a5864e1229f84c8defd65341b4.js"></script>
</head>

<body style="zoom: 1;">

<div class="container">
    <div class="row">
        <div style="width: 60%; margin: 35px auto;">
            <h2>Create fanfic </h2>
            <hr>

            <form:form id="addFanfic" action="/addFanfic" modelAttribute="fanficForm" enctype="multipart/form-data" method="post">

                <div class="form-group">
                    <label for="fanficName"><h4>Title:</h4></label>
                    <form:input style="height: 35px" type="text" path="name" class="form-control" id="fanficName" placeholder="Input title"/>
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
                    <button class="btn btn-lg btn-primary btn-block" id="js-upload-submit">Save</button>
                </div>

            </form:form>

            <div id="alerts"></div>
            <div class="btn-toolbar" data-role="editor-toolbar" data-target="#editor">
                <div class="btn-group">
                    <a class="btn dropdown-toggle" data-toggle="dropdown" title="" data-original-title="Font"><i
                            class="icon-font"></i><b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a data-edit="fontName Serif" style="font-family:'Serif'">Serif</a></li>
                        <li><a data-edit="fontName Sans" style="font-family:'Sans'">Sans</a></li>
                        <li><a data-edit="fontName Arial" style="font-family:'Arial'">Arial</a></li>
                        <li><a data-edit="fontName Arial Black" style="font-family:'Arial Black'">Arial Black</a></li>
                        <li><a data-edit="fontName Courier" style="font-family:'Courier'">Courier</a></li>
                        <li><a data-edit="fontName Courier New" style="font-family:'Courier New'">Courier New</a></li>
                        <li><a data-edit="fontName Comic Sans MS" style="font-family:'Comic Sans MS'">Comic Sans MS</a>
                        </li>
                        <li><a data-edit="fontName Helvetica" style="font-family:'Helvetica'">Helvetica</a></li>
                        <li><a data-edit="fontName Impact" style="font-family:'Impact'">Impact</a></li>
                        <li><a data-edit="fontName Lucida Grande" style="font-family:'Lucida Grande'">Lucida Grande</a>
                        </li>
                        <li><a data-edit="fontName Lucida Sans" style="font-family:'Lucida Sans'">Lucida Sans</a></li>
                        <li><a data-edit="fontName Tahoma" style="font-family:'Tahoma'">Tahoma</a></li>
                        <li><a data-edit="fontName Times" style="font-family:'Times'">Times</a></li>
                        <li><a data-edit="fontName Times New Roman" style="font-family:'Times New Roman'">Times New
                            Roman</a></li>
                        <li><a data-edit="fontName Verdana" style="font-family:'Verdana'">Verdana</a></li>
                    </ul>
                </div>
                <div class="btn-group">
                    <a class="btn dropdown-toggle" data-toggle="dropdown" title="" data-original-title="Font Size"><i
                            class="icon-text-height"></i>&nbsp;<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a data-edit="fontSize 5"><font size="5">Huge</font></a></li>
                        <li><a data-edit="fontSize 3"><font size="3">Normal</font></a></li>
                        <li><a data-edit="fontSize 1"><font size="1">Small</font></a></li>
                    </ul>
                </div>
                <div class="btn-group">
                    <a class="btn" data-edit="bold" title="" data-original-title="Bold (Ctrl/Cmd+B)"><i
                            class="icon-bold"></i></a>
                    <a class="btn" data-edit="italic" title="" data-original-title="Italic (Ctrl/Cmd+I)"><i
                            class="icon-italic"></i></a>
                    <a class="btn" data-edit="strikethrough" title="" data-original-title="Strikethrough"><i
                            class="icon-strikethrough"></i></a>
                    <a class="btn" data-edit="underline" title="" data-original-title="Underline (Ctrl/Cmd+U)"><i
                            class="icon-underline"></i></a>
                </div>
                <div class="btn-group">
                    <a class="btn" data-edit="insertunorderedlist" title="" data-original-title="Bullet list"><i
                            class="icon-list-ul"></i></a>
                    <a class="btn" data-edit="insertorderedlist" title="" data-original-title="Number list"><i
                            class="icon-list-ol"></i></a>
                    <a class="btn" data-edit="outdent" title="" data-original-title="Reduce indent (Shift+Tab)"><i
                            class="icon-indent-left"></i></a>
                    <a class="btn" data-edit="indent" title="" data-original-title="Indent (Tab)"><i
                            class="icon-indent-right"></i></a>
                </div>
                <div class="btn-group">
                    <a class="btn btn-info" data-edit="justifyleft" title=""
                       data-original-title="Align Left (Ctrl/Cmd+L)"><i class="icon-align-left"></i></a>
                    <a class="btn" data-edit="justifycenter" title="" data-original-title="Center (Ctrl/Cmd+E)"><i
                            class="icon-align-center"></i></a>
                    <a class="btn" data-edit="justifyright" title="" data-original-title="Align Right (Ctrl/Cmd+R)"><i
                            class="icon-align-right"></i></a>
                    <a class="btn" data-edit="justifyfull" title="" data-original-title="Justify (Ctrl/Cmd+J)"><i
                            class="icon-align-justify"></i></a>
                </div>
                <div class="btn-group">
                    <a class="btn dropdown-toggle" data-toggle="dropdown" title="" data-original-title="Hyperlink"><i
                            class="icon-link"></i></a>
                    <div class="dropdown-menu input-append">
                        <input class="span2" placeholder="URL" type="text" data-edit="createLink">
                        <button class="btn" type="button">Add</button>
                    </div>
                    <a class="btn" data-edit="unlink" title="" data-original-title="Remove Hyperlink"><i
                            class="icon-cut"></i></a>

                </div>

                <div class="btn-group">
                    <a class="btn" title="" id="pictureBtn"
                       data-original-title="Insert picture (or just drag &amp; drop)"><i class="icon-picture"></i></a>
                    <input type="file" data-role="magic-overlay" data-target="#pictureBtn" data-edit="insertImage"
                           style="opacity: 0; position: absolute; top: 0px; left: 0px; width: 37px; height: 30px;">
                </div>
                <div class="btn-group">
                    <a class="btn" data-edit="undo" title="" data-original-title="Undo (Ctrl/Cmd+Z)"><i
                            class="icon-undo"></i></a>
                    <a class="btn" data-edit="redo" title="" data-original-title="Redo (Ctrl/Cmd+Y)"><i
                            class="icon-repeat"></i></a>
                </div>
                <input type="text" data-edit="inserttext" id="voiceBtn" x-webkit-speech="" style="display: none;">
            </div>
            <div id="editor" contenteditable="true">

            </div>


        </div>
    </div>
</div>


<script>
    $(function () {
        function initToolbarBootstrapBindings() {
            var fonts = ['Serif', 'Sans', 'Arial', 'Arial Black', 'Courier',
                    'Courier New', 'Comic Sans MS', 'Helvetica', 'Impact', 'Lucida Grande', 'Lucida Sans', 'Tahoma', 'Times',
                    'Times New Roman', 'Verdana'],
                fontTarget = $('[title=Font]').siblings('.dropdown-menu');
            $.each(fonts, function (idx, fontName) {
                fontTarget.append($('<li><a data-edit="fontName ' + fontName + '" style="font-family:\'' + fontName + '\'">' + fontName + '</a></li>'));
            });
            $('a[title]').tooltip({container: 'body'});
            $('.dropdown-menu input').click(function () {
                return false;
            })
                .change(function () {
                    $(this).parent('.dropdown-menu').siblings('.dropdown-toggle').dropdown('toggle');
                })
                .keydown('esc', function () {
                    this.value = '';
                    $(this).change();
                });

            $('[data-role=magic-overlay]').each(function () {
                var overlay = $(this), target = $(overlay.data('target'));
                overlay.css('opacity', 0).css('position', 'absolute').offset(target.offset()).width(target.outerWidth()).height(target.outerHeight());
            });
            if ("onwebkitspeechchange" in document.createElement("input")) {
                var editorOffset = $('#editor').offset();
                $('#voiceBtn').css('position', 'absolute').offset({
                    top: editorOffset.top,
                    left: editorOffset.left + $('#editor').innerWidth() - 35
                });
            } else {
                $('#voiceBtn').hide();
            }
        };

        function showErrorAlert(reason, detail) {
            var msg = '';
            if (reason === 'unsupported-file-type') {
                msg = "Unsupported format " + detail;
            }
            else {
                console.log("error uploading file", reason, detail);
            }
            $('<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>' +
                '<strong>File upload error</strong> ' + msg + ' </div>').prependTo('#alerts');
        };
        initToolbarBootstrapBindings();
        $('#editor').wysiwyg({fileUploadError: showErrorAlert});
        window.prettyPrint && prettyPrint();
    });
</script>
<script>
    (function (i, s, o, g, r, a, m) {
        i['GoogleAnalyticsObject'] = r;
        i[r] = i[r] || function () {
            (i[r].q = i[r].q || []).push(arguments)
        }, i[r].l = 1 * new Date();
        a = s.createElement(o),
            m = s.getElementsByTagName(o)[0];
        a.async = 1;
        a.src = g;
        m.parentNode.insertBefore(a, m)
    })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');
    ga('create', 'UA-37452180-6', 'github.io');
    ga('send', 'pageview');
</script>
<script>
    (function (d, s, id) {
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) return;
        js = d.createElement(s);
        js.id = id;
        js.src = "https://connect.facebook.net/en_GB/all.js#xfbml=1";
        fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));
</script>
<script>
    !function (d, s, id) {
        var js, fjs = d.getElementsByTagName(s)[0];
        if (!d.getElementById(id)) {
            js = d.createElement(s);
            js.id = id;
            js.src = "https://platform.twitter.com/widgets.js";
            fjs.parentNode.insertBefore(js, fjs);
        }
    }(document, "script", "twitter-wjs");
</script>
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