<%--
&lt;%&ndash;
  Created by IntelliJ IDEA.
  User: User
  Date: 12.01.2023
  Time: 14:24
  To change this template use File | Settings | File Templates.
&ndash;%&gt;
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/errorHandler.css">
</head>
<body>
<jsp:include page="/locale/locale.jsp"/>
<form action="${pageContext.request.contextPath}/Error" method="POST">
    <div id="main">
        <div class="fof">
            <h1>${statusCode}</h1>
            <p>
                <button name="goBack"><fmt:message key="go.back"/></button>
            </p>
        </div>

    </div>
</form>
</body>
</html>
--%>
