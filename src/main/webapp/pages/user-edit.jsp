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
    <title><c:out value="${user.login}"/></title>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/resource/css/theme.min.css" rel="stylesheet"/>
</head>
<body>
<%@ include file="../WEB-INF/jspf/menu.jspf" %>
<div class="container" role="main">
    <div class="page-header">
        <h1><c:out value="${user.login}"/></h1>
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
            <div>
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th><fmt:message key="bonus.genre"/></th>
                        <th><fmt:message key="bonus.after"/></th>
                        <th><fmt:message key="bonus.before"/></th>
                        <th><fmt:message key="bonus.bonusValue"/></th>
                        <th><fmt:message key="bonus.sale"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="bonus" items="${results}">
                    <tr class="clickable-row" data-href="${pageContext.request.contextPath}/tracks?id=${track.trackId}&command=track-info">
                        <td><c:out value="${bonus.genre}"/></td>
                        <td><c:if test="${bonus.yearAfter > 0}">${bonus.yearAfter}</c:if></td>
                        <td><c:if test="${bonus.yearBefore > 0}">${bonus.yearBefore}</c:if></td>
                        <td><c:if test="${bonus.bonusValue > 0}">$<c:out value="${bonus.bonusValue}"/></c:if></td>
                        <td><c:if test="${bonus.sale > 0}"><c:out value="${bonus.sale}"/>%</c:if></td>
                    </tr>
                    </c:forEach>
                </table>
            </div>
            <div class="row">
                <div class="col-lg-5">
                    <div class="panel panel-primary">
                        <div class="panel-body">
                            <form action="${pageContext.request.contextPath}/clients" method="post">
                                <input type="hidden" name="command" value="bonus-add">
                                <input type="hidden" name="id" value="${id}">
                                <div class="form-group">
                                    <label for="genre" class="control-label"><fmt:message key="track.genre"/></label>
                                    <select class="form-control" id="genre" name="genre">
                                        <c:forEach var="genreVar" items="${genres}">
                                            <option value="${genreVar.name()}" <c:if test="${genreVar == genre}">selected="selected"</c:if>><c:out value="${genreVar}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="date-from" class="control-label"><fmt:message key="bonus.after"/></label>
                                    <input name="after" type="number" min="1901" max="2155" id="date-from" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label for="date-to" class="control-label"><fmt:message key="bonus.before"/></label>
                                    <input name="before" type="number" min="1901" max="2155" id="date-to" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label for="bonus" class="control-label"><fmt:message key="bonus.bonusValue"/></label>
                                    <div class="input-group">
                                        <span class="input-group-addon" id="dollar">$</span>
                                        <input name="bonusValue" type="number" step="0.1" min="0" id="bonus" class="form-control" value="10" aria-describedby="dollar">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="sale" class="control-label"><fmt:message key="bonus.sale"/></label>
                                    <div class="input-group">
                                        <input name="sale" type="number" step="0.1" min="0" max="100" id="sale" class="form-control" value="10">
                                        <span class="input-group-addon">%</span>
                                    </div>
                                </div>
                                <button class="btn btn-lg btn-success" type="submit"><fmt:message key="user.button.addbonus"/></button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
