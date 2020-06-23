<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Review</title>

</head>
<body>
<h1>Hello ${name}</h1><br>
<form action="test">
    <select>
        <c:forEach items="${list}" var="list">
            <option value="<c:out value="${list.get(0)}"/>,<c:out value="${list.get(1)}"/>"><c:out value="${list.get(0)}"/> [<c:out value="${list.get(1)}"/>]</option>
        </c:forEach>
    </select>
    <input type="submit" value="리뷰 등록">
</form>
</body>
</html>