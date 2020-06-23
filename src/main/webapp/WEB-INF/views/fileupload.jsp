<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Netflix Review</title>
    <style>
        .idinput{
            display :none;
        }
    </style>
</head>
<body>
    <h1> Hello ${name}</h1>
    <h3> please upload your file </h3>
    <form action="/upload" method="post" enctype="multipart/form-data">
        <div class="idinput"><input type="text" name="id" value=${id} /></div>
        <input type="file" name="file" />
        <input type="submit" value="file upload"/>
    </form>
</body>
</html>