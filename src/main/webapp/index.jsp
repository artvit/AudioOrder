<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>AudioOrder</title>
    <link href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="../resource/css/login.css" rel="stylesheet">
    <link href="../resource/css/background.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-inverse navbar-static-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#collapsing-navbar" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="index.jsp">AudioOrder</a>
        </div>
        <div class="collapse navbar-collapse" id="collapsing-navbar">
            <ul class="nav navbar-nav">
                <li><a href="tracks.html"><span class="glyphicon glyphicon-music"></span> Tracks</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${not empty sessionScope.login}">
                    <li><c:out value="${sessionScope.login}"/></li>
                    <li><a href="pages/login.jsp">Logout</a></li>
                </c:if>
                <c:if test="${empty sessionScope.login}">
                    <li><a href="pages/login.jsp">Login</a></li>
                    <li><a href="pages/registration.jsp">Sign up</a></li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>
<div class="container" role="main">
    <div class="jumbotron">
        <h1>Audio track order</h1>
        <p>
            On this site you can buy audio tracks
        </p>
    </div>
</div>
</body>
</html>
