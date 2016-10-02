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
    <title><fmt:message key="cart.title"/></title>
    <link href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/resource/css/background.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/resource/js/clickable-row.js"></script>
</head>
<body>
<%@ include file="../WEB-INF/jspf/menu.jspf" %>
<div class="container" role="main">
    <div class="page-header">
        <h1><fmt:message key="cart.title"/></h1>
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
            <form method="get" action="${pageContext.request.contextPath}/tracks">
                <c:choose>
                    <c:when test="${not empty cart}">
                        <div>
                            <c:set var="total" value="${0}"/>
                            <table class="table table-striped table-hover">
                                <thead>
                                <tr>
                                    <th><fmt:message key="track.artist"/></th>
                                    <th><fmt:message key="track.title"/></th>
                                    <th><fmt:message key="track.genre"/></th>
                                    <th><fmt:message key="track.duration"/></th>
                                    <th><fmt:message key="track.cost"/></th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="track" items="${cart}">
                                <tr class="clickable-row"
                                    data-href="${pageContext.request.contextPath}/tracks?id=${track.trackId}&command=track-info">
                                    <td>${track.artist.name}</td>
                                    <td>${track.title}</td>
                                    <td><c:out value="${track.genre}"/></td>
                                    <td><ctl:duration value="${track.duration}"/></td>
                                    <td>$${track.cost}</td>
                                    <c:set var="total" value="${total + track.cost}"/>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/tracks?command=delete-track-cart&id=${track.trackId}"
                                           class="btn btn-xs btn-danger">
                                            <span class="glyphicon glyphicon-minus"></span> <fmt:message
                                                key="cart.remove"/>
                                        </a>
                                    </td>
                                </tr>
                                </c:forEach>
                                <tfoot>
                                <tr>
                                    <th></th>
                                    <th></th>
                                    <th></th>
                                    <th><fmt:message key="cart.total"/>:</th>
                                    <th>$<c:out value="${total}"/></th>
                                    <th></th>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div style="font-size: x-large; opacity: 0.7; text-align: center">
                            <fmt:message key="cart.empty"/>
                        </div>
                    </c:otherwise>
                </c:choose>
            </form>
        </div>
    </div>
</div>
</body>
</html>
