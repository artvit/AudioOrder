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
    <title><fmt:message key="track.add.title"/></title>
    <link href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="webjars/jquery-ui/1.12.1/jquery-ui.min.css" rel="stylesheet">
    <script src="webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="webjars/jquery-ui/1.12.1/jquery-ui.min.js"></script>
    <link href="${pageContext.request.contextPath}/resource/css/background.css" rel="stylesheet">
    <style>
        #duration .form-control {
            width: 50%;
        }
    </style>
</head>
<body>
<%@ include file="../WEB-INF/jspf/menu.jspf" %>
<div class="container" role="main">
    <div class="page-header">
        <h1><fmt:message key="track.add.title"/></h1>
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
            <c:if test="${not empty message}">
                <div class="panel panel-danger">
                    <div class="panel-heading">
                        <h3 class="panel-title"><fmt:message key="error.title"/></h3>
                    </div>
                    <div class="panel-body">
                        <c:out value="${message}"/>
                    </div>
                </div>
            </c:if>
            <form method="post" action="${pageContext.request.contextPath}/addtrack" enctype="multipart/form-data" class="form-registration">
                <input type="hidden" name="command" value="track-add">
                <div class="form-group">
                    <label for="artist" class="control-label"><fmt:message key="track.artist"/></label>
                    <input name="artist" type="text" id="artist" class="form-control" value="${artist}" placeholder="<fmt:message key="track.artist"/>" required autofocus>
                </div>
                <div class="form-group">
                    <label for="title" class="control-label"><fmt:message key="track.title"/></label>
                    <input name="title" type="text" id="title" class="form-control" value="${title}" placeholder="<fmt:message key="track.title"/>" required>
                </div>
                <div class="form-group">
                    <div class="row">
                        <div class="col-sm-2">
                            <label for="genre" class="control-label">Genre</label>
                            <select class="form-control" id="genre" name="genre">
                                <c:forEach var="genreVar" items="${genres}">
                                    <option value="${genreVar.name()}" <c:if test="${genreVar == genre}">selected="selected"</c:if>><c:out value="${genreVar}"/></option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-2">
                            <label for="year" class="control-label"><fmt:message key="track.year"/></label>
                            <input name="year" type="number" id="year" min="1901" max="2155" class="form-control" value="${year}" placeholder="<fmt:message key="track.year"/>">
                        </div>
                        <div class="col-sm-3">
                            <label for="cost" class="control-label"><fmt:message key="track.cost"/></label>
                            <div class="input-group">
                                <span class="input-group-addon" id="sizing-addon2">$</span>
                                <input name="cost" type="number" min="0" step="0.1" id="cost" class="form-control" value="${cost}" placeholder="<fmt:message key="track.cost"/>">
                            </div>
                        </div>
                    </div>
                </div>
                <div id="duration" class="form-group">
                    <label for="minutes" class="control-label"><fmt:message key="track.duration"/></label>
                    <div class="input-group">
                        <input type="number" class="form-control" id="minutes"
                               name="minutes" value="${minutes}"
                               placeholder="<fmt:message key="track.duration.minutes"/>" min="0">
                        <input type="number" class="form-control" id="seconds"
                               name="seconds" value="${seconds}"
                               placeholder="<fmt:message key="track.duration.seconds"/>" min="0" max="59" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="file" class="control-label"><fmt:message key="track.add.file"/></label>
                    <input name="file" type="file" id="file" class="form-control" placeholder="<fmt:message key="track.file"/>" required>
                </div>
                <div class="form-group">
                    <button class="btn btn-lg btn-success" type="submit"><fmt:message key="track.add.button"/></button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
