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
<head><link rel="shortcut icon" href="${pageContext.request.contextPath}/resource/img/favicon.ico">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><fmt:message key="payment.title"/></title>
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/webjars/jquery/3.0.0/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath}/resource/css/theme.min.css" rel="stylesheet"/>
    <style>
        #expired .form-control {
            width: 50%;
        }
    </style>
</head>
<body>
<%@ include file="../WEB-INF/jspf/menu.jspf" %>
<div class="container" role="main">
    <div class="page-header">
        <h1><fmt:message key="payment.title"/></h1>
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
            <div>
                <h2><fmt:message key="cart.total"/>: ${total}</h2>
            </div>
            <form action="${pageContext.request.contextPath}/payment" method="post">
                <input type="hidden" name="command" value="payment-complete">
                <div class="form-group">
                    <label for="number" class="control-label"><fmt:message key="payment.card.number"/></label>
                    <input name="number" type="text" id="number" class="form-control" maxlength="16" minlength="16"
                           placeholder="<fmt:message key="payment.card.number"/>" required autofocus>
                </div>
                <div id="expired" class="form-group">
                    <label for="minutes" class="control-label"><fmt:message key="payment.card.expired"/></label>
                    <div class="input-group">
                        <input type="number" class="form-control" id="minutes"
                               name="month" min="0" max="12" required>
                        <input type="number" class="form-control" id="seconds"
                               name="year" min="2016" max="2100" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="owner" class="control-label"><fmt:message key="payment.card.owner"/></label>
                    <input name="owner" type="text" id="owner" class="form-control" placeholder="<fmt:message key="payment.card.owner"/>" required>
                </div>
                <button type="submit" class="btn btn-lg btn-success"><fmt:message key="payment.button.pay"/></button>
            </form>
        </div>
    </div>
</div>
</body>
</html>