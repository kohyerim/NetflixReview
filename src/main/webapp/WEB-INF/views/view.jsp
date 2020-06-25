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
        .col-md-3{
            background: #428bca;
            padding: 4%;
            border-top-left-radius: 0.5rem;
            border-bottom-left-radius: 0.5rem;
        }
        .contact-info{
            color:#fff;
            margin-top:10%;
        }
        .contact-info img{
            margin-bottom: 15%;
        }
        .contact-info h2{
            margin-bottom: 10%;
        }
        .col-md-9{
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
    <script>
        function getStars(num) {
            switch (num) {
                case 1 :
                    document.write("<h4>★☆☆☆☆</h4>");
                    break;
                case 2 :
                    document.write("<h4>★★☆☆☆</h4>");
                    break;
                case 3 :
                    document.write("<h4>★★★☆☆</h4>");
                    break;
                case 4 :
                    document.write("<h4>★★★★☆</h4>");
                    break;
                case 5 :
                    document.write("<h4>★★★★★</h4>");
                    break;
                default :
                    return null;
            }
        }
    </script>
</head>
<body>
<div class="container contact">
    <div class="row">
        <div class="col-md-3">
            <div class="contact-info">
                <h2>Review</h2>
                <h4></h4>
            </div>
        </div>
        <div class="col-md-9">
            <div class="contact-form">
                <div class="form-group">
                    <label class="control-label col-sm-2" for=>Netflix Title</label>
                    <div class="col-sm-10">
                        <table class="table table-bordered">
                            <tr>
                                <td>
                                    ${title}
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2">Date</label>
                    <div class="col-sm-10">
                        <table class="table table-bordered">
                            <tr>
                                <td>
                                    ${date}
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2">Author</label>
                    <div class="col-sm-10">
                        <table class="table table-bordered">
                            <tr>
                                <td>
                                    ${name}
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-3">Review Title</label>
                    <div class="col-sm-10">
                        <table class="table table-bordered">
                            <tr>
                                <td>
                                    ${review_title}
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2">Comment</label>
                    <div class="col-sm-10">
                        <table class="table table-bordered">
                            <tr>
                                <td>
                                    ${comment}
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2">Stars</label>
                    <div class="col-sm-10">
                        <script>getStars(${stars});</script>
                    </div>
                </div>
            </div>
            <div class="contact-form">
                <div class="col-sm-20">
                    <form action="/edit" method="post">
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <input style="display: none" type="text" name="review_id" value="${review_id}">
                                <button type="submit" class="btn btn-default">수정</button>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-sm-10">
                    <form action="/delete" method="get">
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-20">
                                <input style="display: none" type="text" name="review_id" value="${review_id}">
                                <button type="submit" class="btn btn-default">삭제</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>