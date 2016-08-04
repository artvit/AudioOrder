<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
    <link href="../webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="../webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="../webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="../resource/css/login.css" rel="stylesheet">
    <link href="../resource/css/background.css" rel="stylesheet">
</head>
<body>
<%@ include file="../WEB-INF/jspf/menu.jspf" %>
<div class="container" role="main">
    <div class="panel panel-default">
        <div class="panel-body">
            <form method="post" action="controller" class="form-login">
                <input type="hidden" name="command" value="login">
                <h2 class="form-login-heading">Login</h2>
                <c:if test="${not empty message}">
                    <div class="panel panel-danger">
                        <div class="panel-heading">
                            <h3 class="panel-title">Error</h3>
                        </div>
                        <div class="panel-body">
                            <c:out value="${message}"/>
                        </div>
                    </div>
                </c:if>
                <label for="inputLogin" class="sr-only">Login</label>
                <input name="login" type="text" id="inputLogin" class="form-control" placeholder="Login" required autofocus>
                <label for="inputPassword" class="sr-only">Password</label>
                <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
                <button class="btn btn-lg btn-success btn-block" type="submit">Sign in</button>
                <a class="btn btn-lg btn-primary btn-block" href="registration.html">Registration</a>
            </form>
        </div>
    </div>
</div>
</body>
</html>
