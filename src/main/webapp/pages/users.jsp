<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.i18n"/>
<c:set var="lastpage" scope="session" value="${requestScope['javax.servlet.forward.request_uri']}"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><fmt:message key="users.title"/></title>
    <link href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="../resource/css/background.css" rel="stylesheet">
</head>
<body>
<%@ include file="../WEB-INF/jspf/menu.jspf" %>
<div class="container" role="main">
    <div class="page-header">
        <h1><fmt:message key="users.title"/></h1>
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
            <form method="get" action="${pageContext.request.contextPath}/tracks">
                <input type="hidden" name="command" value="track-search">
                <div class="row">
                    <div class="col-sm-6">
                        <div class="input-group">
                            <label for="client" class="sr-only">Client</label>
                            <input name="search" id="client" type="text" class="form-control" value="${search}" placeholder="<fmt:message key="users.search"/>">
                            <span class="input-group-btn">
                                <button class="btn btn-default" type="submit"><fmt:message key="users.search"/></button>
                            </span>
                        </div>
                    </div>
                </div>
                <c:if test="${not empty results}">
                    <div>
                        <table class="table table-striped table-hover">
                            <thead>
                            <tr>
                                <th><fmt:message key="users.user.login"/></th>
                                <th><fmt:message key="users.user.email"/></th>
                                <th><fmt:message key="users.user.balance"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="user" items="${results}">
                            <tr>
                                <td>${user.login}</td>
                                <td>${user.email}</td>
                                <td>$${user.balance}</td>

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

