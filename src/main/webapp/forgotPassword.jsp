<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="bean" class="Service.ServiceBean" scope="session" />
<jsp:useBean id="message" class="Service.MessageBean" scope="session" />

<html>
<head>
    <title>Password retreival</title>
    <link href="Bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .well {
            margin-top: 100px;
        }
    </style>
</head>

<body>

<div class="container">
    <div class="row">
        <div class="well span4 offset4">
            <legend>Password retrieval</legend>

            <form method="post" action="passChange" accept-charset="UTF-8">

                <c:if test="${not empty message.errorMessage}">
                    <div class="alert alert-error">
                        <a class="close" data-dismiss="alert" href="#">x</a> ${message.errorMessage}
                    </div>
                    <% message.setErrorMessage(null); %>
                </c:if>
                <input type="text" name="email" class="span4" placeholder="E-Mail">
                <input type="password" name="secret" class="span4" placeholder="Secret phrase">
                <label class="radio">
                    <input type = "radio" name = "status" value = "Passenger">  I am a Passenger <br>
                    <input type = "radio" name = "status" value = "Employee">  I am an Employee <br>
                </label>
                <br>
                <button type="submit" name="submit" class="btn btn-success btn-block">Get new password</button>
            </form>
        </div>
    </div>
</div>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="Bootstrap/js/bootstrap.min.js"></script>
</body>
</html>

