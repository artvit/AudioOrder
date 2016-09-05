<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.i18n"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><fmt:message key="registration.title"/></title>
    <link href="../webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="../webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="../webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="../resource/css/registration.css" rel="stylesheet">
    <link href="../resource/css/background.css" rel="stylesheet">
</head>
<body>
<%@ include file="../WEB-INF/jspf/menu.jspf" %>
<div class="container" role="main">
    <div class="page-header">
        <h1><fmt:message key="registration.title"/></h1>
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
            <c:if test="${not empty message}">
                <div class="panel panel-danger">
                    <div class="panel-heading">
                        <h3 class="panel-title"><fmt:message key="registration.error.title"/></h3>
                    </div>
                    <div class="panel-body">
                        <c:out value="${message}"/>
                    </div>
                </div>
            </c:if>
            <form method="post" action="registration" class="form-registration">
                <input type="hidden" name="command" value="registration">
                <div class="form-group">
                    <label for="inputLogin" class="control-label"><fmt:message key="registration.login"/></label>
                    <input name="login" value="${login}" type="text" userId="inputLogin" class="form-control" placeholder="<fmt:message key="registration.login"/>" required autofocus>
                </div>
                <div class="form-group">
                    <label for="inputEmail" class="control-label"><fmt:message key="registration.email"/></label>
                    <input name="email" value="${email}" type="email" userId="inputEmail" class="form-control" placeholder="<fmt:message key="registration.email"/>" required>
                </div>
                <div class="form-group">
                    <label for="inputPassword" class="control-label"><fmt:message key="registration.password"/></label>
                    <input name="password" value="${password}" type="password" userId="inputPassword" class="form-control" placeholder="<fmt:message key="registration.password"/>" required>
                </div>
                <div class="form-group">
                    <label for="inputPasswordConfirm" class="control-label"><fmt:message key="registration.password-confirm"/></label>
                    <input name="passwordConfirm" value="${passwordConfirm}" type="password" userId="inputPasswordConfirm" class="form-control" placeholder="<fmt:message key="registration.password-confirm"/>" required>
                </div>
                <button class="btn btn-lg btn-success" type="submit"><fmt:message key="registration.button"/></button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
