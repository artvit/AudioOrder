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
    <title><fmt:message key="tracks.title"/></title>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/resource/css/background.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/resource/js/clickable-row.js"></script>
</head>
<body>
<%@ include file="../WEB-INF/jspf/menu.jspf" %>
<div class="container" role="main">
    <div class="page-header">
        <h1><fmt:message key="tracks.title"/></h1>
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
            <form method="get" action="${pageContext.request.contextPath}/tracks">
                <input type="hidden" name="command" value="track-search">
                <div class="row">
                    <div class="col-sm-3">
                        <%@ include file="../WEB-INF/jspf/genre.jspf" %>
                    </div>
                    <div class="col-sm-6">
                        <div class="input-group">
                            <label for="track" class="sr-only">Track</label>
                            <input name="search" id="track" type="text" class="form-control" value="${search}" placeholder="<fmt:message key="tracks.search"/>">
                            <span class="input-group-btn">
                                <button class="btn btn-default" type="submit"><fmt:message key="tracks.search"/></button>
                            </span>
                        </div>
                    </div>
                </div>
                <c:if test="${not empty results}">
                    <div>
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
                            <c:forEach var="track" items="${results}">
                                <tr class="clickable-row" data-href="${pageContext.request.contextPath}/tracks?id=${track.trackId}&command=track-info">
                                    <td><c:out value="${track.artist.name}"/></td>
                                    <td><c:out value="${track.title}"/></td>
                                    <td><c:out value="${track.genre}"/></td>
                                    <td><ctl:duration value="${track.duration}"/></td>
                                    <td>$${track.cost}</td>
                                    <td>
                                        <c:if test="${not track.bought}">
                                            <c:choose>
                                                <c:when test="${not cart.contains(track)}">
                                                    <a href="${pageContext.request.contextPath}/tracks?command=add-track-cart&id=${track.trackId}"
                                                       class="btn btn-xs btn-success btn-block" >
                                                        <span class="glyphicon glyphicon-plus"></span> <fmt:message key="cart.add"/>
                                                    </a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="${pageContext.request.contextPath}/tracks?command=delete-track-cart&id=${track.trackId}"
                                                       class="btn btn-xs btn-danger btn-block" >
                                                        <span class="glyphicon glyphicon-minus"></span> <fmt:message key="cart.remove"/>
                                                    </a>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                    <div>
                        <ctl:pagination page="${page}" total="${numOfPages}"/>
                    </div>
                </c:if>
            </form>
        </div>
    </div>
</div>
</body>
</html>
