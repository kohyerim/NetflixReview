<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Netflix Review</title>
</head>
<body>
    <h1> Hello ${name}</h1>
    <form action="/upload?id=${id}" method="post" enctype="multipart/form-data">
        <input type="file" name="file" />
        <input type="submit" value="file upload"/>
    </form>
</body>
</html>