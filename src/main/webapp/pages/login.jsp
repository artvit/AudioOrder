<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.i18n"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><fmt:message key="login.title"/></title>
    <link href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="../resource/css/login.css" rel="stylesheet">
    <link href="../resource/css/background.css" rel="stylesheet">
</head>
<body>
<%@ include file="../WEB-INF/jspf/menu.jspf" %>
<div class="container" role="main">
    <div class="panel panel-default">
        <div class="panel-body">
            <form method="post" action="login" class="form-login">
                <input type="hidden" name="command" value="login">
                <h2 class="form-login-heading"><fmt:message key="login.title"/></h2>
                <c:if test="${not empty message}">
                    <div class="panel panel-danger">
                        <div class="panel-heading">
                            <h3 class="panel-title"><fmt:message key="login.error.title"/></h3>
                        </div>
                        <div class="panel-body">
                            <c:out value="${message}"/>
                        </div>
                    </div>
                </c:if>
                <label for="inputLogin" class="sr-only"><fmt:message key="login.login"/></label>
                <input name="login" type="text" id="inputLogin" class="form-control" placeholder="<fmt:message key="login.login"/>" required autofocus>
                <label for="inputPassword" class="sr-only"><fmt:message key="login.password"/></label>
                <input name="password" type="password" id="inputPassword" class="form-control" placeholder="<fmt:message key="login.password"/>" required>
                <button class="btn btn-lg btn-success btn-block" type="submit"><fmt:message key="login.button.login"/></button>
                <a class="btn btn-lg btn-primary btn-block" href="${pageContext.request.contextPath}/registration"><fmt:message key="login.button.regitration"/></a>
            </form>
        </div>
    </div>
</div>
</body>
</html>
