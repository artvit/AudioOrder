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
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><fmt:message key="payment.title"/></title>
    <link href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/resource/css/background.css" rel="stylesheet">
</head>
<body>
<%@ include file="../WEB-INF/jspf/menu.jspf" %>
<div class="container" role="main">
    <div class="page-header">
        <h1><fmt:message key="payment.title"/></h1>
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
            <form action="${pageContext.request.contextPath}/payment" method="post">
                <input type="hidden" name="command" value="payment">
                <div>
                    <table class="table table-striped table-hover">
                        <thead>
                        <tr>
                            <th></th>
                            <th><fmt:message key="bonus.genre"/></th>
                            <th><fmt:message key="bonus.after"/></th>
                            <th><fmt:message key="bonus.before"/></th>
                            <th><fmt:message key="bonus.bonusValue"/></th>
                            <th><fmt:message key="bonus.sale"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="bonus" items="${results}">
                        <tr class="clickable-row"
                            data-href="${pageContext.request.contextPath}/tracks?id=${track.trackId}&command=track-info">
                            <td><input type="checkbox" name="bonus" value="${bonus.bonusId}"></td>
                            <td><c:out value="${bonus.genre}"/></td>
                            <td><c:if test="${bonus.yearAfter > 0}">${bonus.yearAfter}</c:if></td>
                            <td><c:if test="${bonus.yearBefore > 0}">${bonus.yearBefore}</c:if></td>
                            <td><c:if test="${bonus.bonusValue > 0}">$<c:out value="${bonus.bonusValue}"/></c:if></td>
                            <td><c:if test="${bonus.sale > 0}"><c:out value="${bonus.sale}"/>%</c:if></td>
                        </tr>
                        </c:forEach>
                    </table>
                </div>
                <button type="submit" class="btn btn-lg btn-success"><fmt:message key="payment.bonus.use"/></button>
            </form>
        </div>
    </div>
</div>
</body>
</html>