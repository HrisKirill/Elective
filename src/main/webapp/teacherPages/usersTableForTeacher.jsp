<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 13.12.2022
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/tableForUser.css">
</head>
<body>
<jsp:include page="/locale/locale.jsp"/>

<a href="${pageContext.request.contextPath}/Teacher/TableOfCoursesForTeacher"><fmt:message key="go.back"/></a>
<p style="text-align:right"><a href="${pageContext.request.contextPath}/Logout"> <fmt:message key="sign.out"/> </a></p>
<form action="${pageContext.request.contextPath}/Teacher/UsersTableForTeacher" method="POST">
    <table>
        <tags:caption value="users"/>
        <thead>
        <tr>
            <th scope="col"><fmt:message key="course.title"/></th>
            <th scope="col"><fmt:message key="user.lastName"/></th>
            <th scope="col"><fmt:message key="user.firstName"/></th>
            <th scope="col"><fmt:message key="user.login"/></th>
            <th scope="col"><fmt:message key="user.email"/></th>
            <th scope="col"><fmt:message key="course.mark"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="userEntry" items="${userMap.entrySet()}">
            <tr>
                <td data-label="<fmt:message key="course.title"/>">${course.getTitle()}</td>
                <td data-label="<fmt:message key="user.lastName"/>">${userEntry.getKey().getLastName()}</td>
                <td data-label="<fmt:message key="user.firstName"/>">${userEntry.getKey().getFirstName()}</td>
                <td data-label="<fmt:message key="user.login"/>">${userEntry.getKey().getLogin()}</td>
                <td data-label="<fmt:message key="user.email"/>">${userEntry.getKey().getEmail()}</td>
                <td data-label="<fmt:message key="course.mark"/>">
                        ${userEntry.getValue()}
                    <button name="editMark" value="${userEntry.getKey().getId()}"><fmt:message key="edit"/></button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</form>
</body>
</html>
