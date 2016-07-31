<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Success</title>
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
<c:if test="${not empty results}">
    <div class="container">
        <h1>Success</h1>
        <table class="table table-striped">
            <thead>
            <tr>
                <td>#</td>
                <td>Bank</td>
                <td>Country</td>
                <td>First Name</td>
                <td>Last Name</td>
                <td>Amount</td>
                <td>Profitability</td>
                <td>Date</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${results}" var="item">
                <tr>
                    <td>${item.depositId}</td>
                    <td>${item.bank}</td>
                    <td>${item.country}</td>
                    <td>${item.depositorFirstName}</td>
                    <td>${item.depositorLastName}</td>
                    <td>${item.amount}</td>
                    <td>${item.profitability}</td>
                    <td><fmt:formatDate type="date" dateStyle="short" value="${item.time.time}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</c:if>
</body>
</html>
