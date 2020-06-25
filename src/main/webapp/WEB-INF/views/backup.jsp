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
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <style>
        /***
Bootstrap4 Card with Tabs by @mdeuerlein
***/

        body {
            background-color: #f7f8f9;
        }

        .card {
            background-color: #ffffff;
            border: 1px solid rgba(0, 34, 51, 0.1);
            box-shadow: 2px 4px 10px 0 rgba(0, 34, 51, 0.05), 2px 4px 10px 0 rgba(0, 34, 51, 0.05);
            border-radius: 0.15rem;
        }

        /* Tabs Card */

        .tab-card {
            border:1px solid #eee;
        }

        .tab-card-header {
            background:none;
        }
        /* Default mode */
        .tab-card-header > .nav-tabs {
            border: none;
            margin: 0px;
        }
        .tab-card-header > .nav-tabs > li {
            margin-right: 2px;
        }
        .tab-card-header > .nav-tabs > li > a {
            border: 0;
            border-bottom:2px solid transparent;
            margin-right: 0;
            color: #737373;
            padding: 2px 15px;
        }

        .tab-card-header > .nav-tabs > li > a.show {
            border-bottom:2px solid #007bff;
            color: #007bff;
        }
        .tab-card-header > .nav-tabs > li > a:hover {
            color: #007bff;
        }

        .tab-card-header > .tab-content {
            padding-bottom: 0;
        }
    </style>
    <script>
        $(document).ready(function() {
            $('.multiselect-ui').multiselect({
                onChange: function(option, checked) {
                    // Get selected options.
                    var selectedOptions = $('.multiselect-ui option:selected');

                    if (selectedOptions.length >= 4) {
                        // Disable all other checkboxes.
                        var nonSelectedOptions = $('.multiselect-ui option').filter(function() {
                            return !$(this).is(':selected');
                        });

                        nonSelectedOptions.each(function() {
                            var input = $('input[value="' + $(this).val() + '"]');
                            input.prop('disabled', true);
                            input.parent('li').addClass('disabled');
                        });
                    }
                    else {
                        // Enable all checkboxes.
                        $('.multiselect-ui option').each(function() {
                            var input = $('input[value="' + $(this).val() + '"]');
                            input.prop('disabled', false);
                            input.parent('li').addClass('disabled');
                        });
                    }
                }
            });
        });
    </script>
</head>
<body>
<div class="container contact">
    <div class="row" style="margin-top:20px">
        <div class="col-sm-10">
            <form action="write" class="form-inline" method="post">
                <div style="display: none"><input type="text" name="user_id" value=${user_id} /></div>
                <div>
                    <select name="select" class="form-control">
                        <c:forEach items="${list}" var="list">
                            <option value="<c:out value="${list.get(0)}"/>,<c:out value="${list.get(1)}"/>"><c:out value="${list.get(0)}"/> [<c:out value="${list.get(1)}"/>]</option>
                        </c:forEach>
                    </select>
                </div>
                <div style="margin-left:30px;">
                    <input type="submit" value="리뷰 등록">
                </div>
            </form>
        </div>
    </div>

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

</body>
</html>