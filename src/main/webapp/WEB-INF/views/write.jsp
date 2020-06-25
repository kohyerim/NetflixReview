<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>write</title>

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
            background: #ff9b00;
            padding: 4%;
            border-top-left-radius: 0.5rem;
            border-bottom-left-radius: 0.5rem;
        }
        .contact-info{
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
</head>
<body>
<div class="container contact">
    <div class="row">
        <div class="col-md-3">
            <div class="contact-info">
                <h2>Write Review</h2>
                <h4>write a comment on your watching</h4>
            </div>
        </div>
        <div class="col-md-9">
            <div class="contact-form">
                <form action="/register" method="post">
                    <input style="display: none" type="text" name="user_id" value="${user_id}">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="netflix_title">Netflix Title</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="netflix_title" value="${title}" name="netflix_title" readonly>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="date">Date</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="date" value="${date}" name="date" readonly>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="name">Author</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="name" value="${name}" name="name" readonly>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="review_title">Review Title</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="review_title" placeholder="Enter Review Title" name="review_title">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="comment">Comment</label>
                        <div class="col-sm-10">
                            <textarea class="form-control" rows="5" id="comment" name="comment"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="stars">Stars</label>
                        <div class="col-sm-10">
                            <select name="stars" id="stars">
                                <option value="1">★☆☆☆☆</option>
                                <option value="2">★★☆☆☆</option>
                                <option value="3">★★★☆☆</option>
                                <option value="4">★★★★☆</option>
                                <option value="5" selected>★★★★★</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-default">저장</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>