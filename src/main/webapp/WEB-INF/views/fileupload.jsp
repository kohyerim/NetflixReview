<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>History upload</title>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <style>
        /* layout.css Style */
        .upload-drop-zone {
            height: 200px;
            border-width: 2px;
            margin-bottom: 20px;
        }

        /* skin.css Style*/
        .upload-drop-zone {
            color: #ccc;
            border-style: dashed;
            border-color: #ccc;
            line-height: 200px;
            text-align: center
        }
        .upload-drop-zone.drop {
            color: #222;
            border-color: #222;
        }
    </style>
    <script>
        + function($) {
            'use strict';

            // UPLOAD CLASS DEFINITION
            var uploadForm = document.getElementById('js-upload-form');

            var startUpload = function(files) {
                console.log(files)
            }

            uploadForm.addEventListener('submit', function(e) {
                var uploadFiles = document.getElementById('js-upload-files').files;
                e.preventDefault()

                startUpload(uploadFiles)
            })

        }(jQuery);
    </script>
</head>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading"><strong>Upload Netflix History</strong></div>
        <div class="panel-body">

            <!-- Standar Form -->
            <h4>${name}님 안녕하세요</h4>
            <form action="/upload" method="post" enctype="multipart/form-data" id="js-upload-form">
                <div style="display: none"><input type="text" name="user_id" value=${user_id} /></div>
                <div class="form-inline">
                    <div class="form-group">
                        <input type="file" name="file" id="js-upload-files">
                    </div>
                    <button type="submit" class="btn btn-sm btn-primary" id="js-upload-submit">업로드</button>
                </div>
            </form>
        </div>
    </div>
</div> <!-- /container -->
</html>