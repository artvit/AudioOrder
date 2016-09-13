<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.i18n"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><fmt:message key="track.add.title"/></title>
    <link href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="../resource/css/background.css" rel="stylesheet">
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
                        <h3 class="panel-title"><fmt:message key="registration.error.title"/></h3>
                    </div>
                    <div class="panel-body">
                        <c:out value="${message}"/>
                    </div>
                </div>
            </c:if>
            <form method="post" action="${pageContext.request.contextPath}/addtrack" enctype="multipart/form-data" class="form-registration">
                <input type="hidden" name="command" value="track-add">
                <div class="form-group">
                    <label for="artist" class="control-label"><fmt:message key="tracks.track.artist"/></label>
                    <input name="artist" type="text" id="artist" class="form-control" value="${artist}" placeholder="<fmt:message key="tracks.track.artist"/>" required autofocus>
                </div>
                <div class="form-group">
                    <label for="title" class="control-label"><fmt:message key="tracks.track.title"/></label>
                    <input name="title" type="text" id="title" class="form-control" value="${title}" placeholder="<fmt:message key="tracks.track.title"/>"  required>
                </div>
                <div class="form-group">
                    <label for="genre" class="control-label">Genre</label>
                    <select class="form-control" id="genre" name="genre">
                        <c:forEach var="genreVar" items="${genres}">
                            <option value="${genreVar.name()}" <c:if test="${genreVar == genre}">selected="selected"</c:if>><c:out value="${genreVar}"/></option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="year" class="control-label"><fmt:message key="tracks.track.year"/></label>
                    <input type="text" id="year" class="form-control" value="${year}" placeholder="<fmt:message key="tracks.track.year"/>">
                </div>
                <div class="form-group">
                    <label for="file" class="control-label"><fmt:message key="track.add.file"/></label>
                    <input type="file" id="file" class="form-control" placeholder="File" required>
                </div>
                <button class="btn btn-lg btn-success" type="submit"><fmt:message key="track.add.button"/></button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
