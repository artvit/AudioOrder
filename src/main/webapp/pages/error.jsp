<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.i18n"/>
<html>
<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/resource/img/favicon.ico">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><fmt:message key="error.title"/></title>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/resource/css/theme.min.css" rel="stylesheet"/>
</head>
<body>
<%@ include file="../WEB-INF/jspf/menu.jspf" %>
<div class="container" role="main">
    <div class="page-header">
        <h1><fmt:message key="error.title"/></h1>
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
            <div align="center"><h2>
                <c:choose>
                    <c:when test="${not empty message}">
                        <c:out value="${message}"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="error.message"/>
                    </c:otherwise>
                </c:choose>
            </h2></div>
        </div>
    </div>
</div>
</body>
</html>
