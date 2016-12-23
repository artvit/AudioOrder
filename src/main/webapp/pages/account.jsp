<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.i18n"/>
<c:set var="lastpage" scope="session" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<c:if test="${not empty requestScope['javax.servlet.forward.query_string']}">
    <c:set var="lastpage" scope="session" value="${lastpage}?${requestScope['javax.servlet.forward.query_string']}"/>
</c:if>
<html>
<head>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resource/img/favicon.ico">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${login}"/></title>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <<link href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/resource/css/theme.min.css" rel="stylesheet"/>
    <script src="${pageContext.request.contextPath}/resource/js/clickable-row.js"></script>
</head>
<body>
<%@ include file="../WEB-INF/jspf/menu.jspf" %>
<div class="container" role="main">
    <div class="page-header">
        <h1><c:out value="${login}"/></h1>
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
            <ul class="nav nav-tabs">
                <li role="presentation" <c:if test="${section == 'tracks'}">class="active"</c:if>><a href="${pageContext.request.contextPath}/account?command=account-tracks"><fmt:message key="account.section.tracks"/></a></li>
                <li role="presentation" <c:if test="${section == 'bonuses'}">class="active"</c:if>><a href="${pageContext.request.contextPath}/account?command=account-bonuses"><fmt:message key="account.section.bonuses"/></a></li>
            </ul>
            <c:if test="${section == 'tracks'}">
                <%@ include file="../WEB-INF/jspf/account-tracks.jspf" %>
            </c:if>
            <c:if test="${section == 'bonuses'}">
                <%@ include file="../WEB-INF/jspf/account-bonuses.jspf" %>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>