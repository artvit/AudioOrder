<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registration</title>
    <link href="../webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="../webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="../webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="../resource/css/registration.css.css" rel="stylesheet">
    <link href="../resource/css/background.css" rel="stylesheet">
</head>
<body>
<%@ include file="../WEB-INF/jspf/menu.jspf" %>
<div class="container" role="main">
    <div class="page-header">
        <h1>Registration</h1>
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
            <form class="form-registration">
                <div class="form-group">
                    <label for="inputLogin" class="control-label">Login</label>
                    <input type="text" id="inputLogin" class="form-control" placeholder="Login" required autofocus>
                </div>
                <div class="form-group">
                    <label for="inputEmail" class="control-label">E-mail</label>
                    <input type="email" id="inputEmail" class="form-control" placeholder="Email" required autofocus>
                </div>
                <div class="form-group">
                    <label for="inputPassword" class="control-label">Password</label>
                    <input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
                </div>
                <div class="form-group">
                    <label for="inputPasswordConfirm" class="control-label">Password confirmation</label>
                    <input type="password" id="inputPasswordConfirm" class="form-control" placeholder="Password confirmation" required>
                </div>
                <button class="btn btn-lg btn-success" type="submit">Sign in</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
