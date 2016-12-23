<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<c:set var="locale" scope="session" value="ru_RU"/>--%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.i18n"/>
<c:set var="lastpage" scope="session" value="${pageContext.request.contextPath}/index.jsp"/>
<html>
<head>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resource/img/favicon.ico">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>AudioOrder</title>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/resource/css/theme.min.css" rel="stylesheet"/>
</head>
<body>
<%@ include file="WEB-INF/jspf/menu.jspf" %>
<div class="container" role="main">
    <div class="jumbotron">
        <h1><fmt:message key="index.title"/></h1>
        <p>
            <fmt:message key="index.text"/>
        </p>
    </div>
</div>
</body>
</html>
