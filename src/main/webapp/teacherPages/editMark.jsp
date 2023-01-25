<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 15.12.2022
  Time: 13:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/editMark.css">
</head>
<body>
<jsp:include page="/locale/locale.jsp"/>

<a href="${pageContext.request.contextPath}/Teacher/UsersTableForTeacher"><fmt:message key="go.back"/></a>
<form action="${pageContext.request.contextPath}/Teacher/EditMark" method="POST">
    <div>
        <label for="marks"><fmt:message
                key="enterMark"/> ${userToEdit.getLastName()} ${userToEdit.getFirstName()}:</label>
        <input id="marks" type="number" name="marks" min="0" max="100" required>
        <span class="validity"></span>
    </div>
    <div>
        <input type="submit" value="<fmt:message key="submit"/>">
    </div>
</form>
</body>
</html>
