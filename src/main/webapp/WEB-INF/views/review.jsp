<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>view</title>

    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <style>
        body{
            background-color: #B6B6B6;
        }
        .contact{
            padding: 4%;
            height: 400px;
        }
        .blue{
            background: #428bca;
            padding: 4%;
            border-top-left-radius: 0.5rem;
            border-bottom-left-radius: 0.5rem;
        }
        .contact-info{
            color: #fff;
            margin-top:10%;
            margin-bottom: 15%;
        }
        .contact-info img{
            margin-bottom: 15%;
        }
        .contact-info h2{
            margin-bottom: 20%;
        }
        .white{
            background: #fff;
            padding: 3%;
            border-top-right-radius: 0.5rem;
            border-bottom-right-radius: 0.5rem;
        }
        .contact-form label{
            font-weight:600;
        }
        .contact-form button{
            background: #25274d;
            color: #fff;
            font-weight: 600;
            width: 25%;
        }
        .contact-form button:focus{
            box-shadow:none;
        }
    </style>
</head>
<body>
<div class="container contact">
    <div class="row">
        <div class="col-md-6 blue">
            <div class="contact-info">
                <h2>Review</h2>
                <div class="row">
                    <div class="col-sm-8">
                        <form action="write" method="post" class="form-inline">
                            <div style="display: none"><input type="text" name="user_id" value=${user_id} /></div>
                            <select name="select" class="form-control" style="float:left">
                                <c:forEach items="${list}" var="list">
                                    <option value="<c:out value="${list.get(0)}"/>,<c:out value="${list.get(1)}"/>"><c:out value="${list.get(0)}"/> [<c:out value="${list.get(1)}"/>]</option>
                                </c:forEach>
                            </select>
                            <button type="submit" class="btn btn-default form-inline" style="margin-top:30px;"> 리뷰 등록 </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6 white">
            <div class="contact-form">
                <c:forEach items="${reviews}" var="review">
                    <div class="card mt-3 tab-card">
                        <div class="tab-content">
                            <div class="tab-pane fade show active p-3" role="tabpanel" aria-labelledby="one-tab"
                                 onclick=location.href="/view?review_id=${review.review_id}">
                                [${review.netflix_title}]
                                    ${review.review_title}
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

</body>
</html>