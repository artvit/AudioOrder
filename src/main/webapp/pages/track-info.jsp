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
    <title>${track.artist.name} - ${track.title}</title>
    <link href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/resource/css/background.css" rel="stylesheet">
</head>
<body>
<%@ include file="../WEB-INF/jspf/menu.jspf" %>
<div class="container" role="main">
    <div class="page-header">
        <h1>${track.artist.name} - ${track.title}</h1>
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
            <div style="font-size: larger">
                <fmt:message key="tracks.track.artist"/>: ${track.artist.name}
                <br>
                <fmt:message key="tracks.track.title"/>: ${track.title}
                <br>
                <fmt:message key="tracks.track.duration"/>: <ctl:duration value="${track.duration}"/>
                <br>
                <fmt:message key="tracks.track.year"/>: ${track.releasedYear}
                <br>
                <fmt:message key="tracks.track.cost"/>: $${track.cost}
            </div>
            <div>
                <c:choose>
                    <c:when test="${not cart.contains(track)}">
                        <a href="${pageContext.request.contextPath}/tracks?command=add-track-cart&id=${track.trackId}"
                           class="btn btn-lg btn-success" >
                            <span class="glyphicon glyphicon-plus"></span> <fmt:message key="cart.add"/>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/tracks?command=delete-track-cart&id=${track.trackId}"
                           class="btn btn-sm btn-danger" >
                            <span class="glyphicon glyphicon-minus"></span> <fmt:message key="cart.remove"/>
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
            <div>
                <c:if test="${not empty feedback}">
                    <h2><fmt:message key="trackinfo.feedback"/></h2>
                    <div>
                        <c:forEach var="comment" items="${feedback}">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <div class="panel-title pull-left">
                                        ${comment.user.login}
                                    </div>
                                    <c:if test="${sessionScope.role == 'ADMIN'}">
                                    <div class="panel-title pull-right">
                                        <a href="${pageContext.request.contextPath}/tracks?command=delete-comment&id=${comment.commentId}" class="btn btn-sm btn-danger"><span class="glyphicon glyphicon-remove"></span> <fmt:message key="trackinfo.comment.delete"/></a>
                                    </div>
                                    </c:if>
                                    <div class="clearfix"></div>
                                </div>
                                <div class="panel-body">
                                    ${comment.text}
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <div>
                        <ctl:pagination page="${page}" total="${numOfPages}"/>
                    </div>
                </c:if>
                <c:if test="${(sessionScope.role == 'ADMIN') or sessionScope.role == 'USER'}">
                    <div class="panel panel-success">
                        <div class="panel-body">
                            <form action="${pageContext.request.contextPath}/tracks" method="post">
                                <input type="hidden" name="command" value="add-comment">
                                <input type="hidden" name="id" value="${track.trackId}">
                                <div class="form-group">
                                    <label for="comment"><fmt:message key="trackinfo.comment"/>:</label>
                                    <textarea name="text" class="form-control" rows="5" id="comment"></textarea>
                                </div>
                                <button type="submit" class="btn btn-success"><fmt:message key="trackinfo.submit.comment"/></button>
                            </form>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>
