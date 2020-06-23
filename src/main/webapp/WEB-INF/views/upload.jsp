<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>upload</title>
</head>
<body>
<form name="hiddenForm" action="/review" method="post">
    <input type="hidden" name="id" value=${id}>
    <input type="hidden" name="path" value=${path}>
</form>
<script>
    document.hiddenForm.submit();
</script>
</body>
</html>