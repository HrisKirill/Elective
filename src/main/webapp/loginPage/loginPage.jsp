<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 11.11.2022
  Time: 19:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/loginPage.css">
</head>
<body>
<jsp:include page="/locale/locale.jsp"/>

<div class="login-box">
    <h2><fmt:message key="user.login"/></h2>
    <form action="${pageContext.request.contextPath}/Login" method="POST">
        <tags:notEmptyError value="${authenticationError}"/>
        <div class="user-box">
            <input type="text" name="login" id="login" value="${incorrectSignInLogin}" required="">
            <label for="login" class="required"><fmt:message key="user.userName"/></label>
        </div>

        <div class="user-box">
            <input type="password" name="password" id="password" value="${incorrectSignInPassword}" required="">
            <label for="password" class="required"><fmt:message key="user.password"/></label>
        </div>

        <div class="button-form">
            <input type="submit" name="loginButton" id="submit" value="<fmt:message key="sign.in"/>" class="button"/>

            <div id="register">
                <a onclick="document.location='/Registration'"><fmt:message key="sign.up"/></a>
            </div>
        </div>
    </form>
</div>
</body>
</html>
