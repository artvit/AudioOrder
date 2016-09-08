<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.i18n"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><fmt:message key="tracks.title"/></title>
    <link href="../webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="../webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="../webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="../resource/css/registration.css.css" rel="stylesheet">
    <link href="../resource/css/background.css" rel="stylesheet">
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
                    <div class="col-sm-6">
                        <div class="input-group">
                            <label for="track" class="sr-only">Track</label>
                            <input name="track" id="track" type="text" class="form-control" placeholder="<fmt:message key="tracks.search"/>">
                            <span class="input-group-btn">
                            <button class="btn btn-default" type="submit"><fmt:message key="tracks.search"/></button>
                        </span>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <%@ include file="../WEB-INF/jspf/genre.jspf" %>
                    </div>
                </div>
                <c:if test="${not empty results}">
                    <div>
                        <table class="table table-striped table-hover">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Artist</th>
                                <th>Name</th>
                                <th>Duration</th>
                                <th>Cost</th>
                                <th></th>
                                <th></th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="track" items="${results}">
                                <tr>
                                    <td>2</td>
                                    <td>Sting</td>
                                    <td>Shape of my heart l</td>
                                    <td>4:33</td>
                                    <td>$0.99</td>
                                    <td><button class="btn btn-xs btn-success" href="edittrack.html">
                                        <span class="glyphicon glyphicon-plus"></span>
                                    </button></td>
                                    <td><button class="btn btn-xs btn-primary" href="edittrack.html">
                                        <span class="glyphicon glyphicon-pencil"></span>
                                    </button></td>
                                    <td><button class="btn btn-xs btn-danger" href="edittrack.html">
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </button></td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                    <div>
                        <ctl:pagination page="${page}" total="${numOfPages}"/>
                    </div>
                </c:if>
                <div>
                    <ctl:pagination page="${page}" total="${numOfPages}"/>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
