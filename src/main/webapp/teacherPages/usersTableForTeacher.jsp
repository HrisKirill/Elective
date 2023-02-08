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
    <c:choose>
        <c:when test="${userMap.size() > 0}">
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

                            <c:choose>
                                <c:when test="${currentDate.compareTo(course.getEndDate()) <= 0 &&
                                 currentDate.compareTo(course.getStartDate()) >= 0}">
                                    <button name="editMark" value="${userEntry.getKey().getId()}"><fmt:message
                                            key="edit"/></button>
                                </c:when>
                                <c:otherwise>
                                    <button name="editMark" value="${userEntry.getKey().getId()}" disabled><fmt:message
                                            key="edit"/></button>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <table>
                    <c:choose>
                        <c:when test="${currentPageStudentsForTeacher != 1}">
                            <td>
                                <button name="previous" value="${currentPageStudentsForTeacher - 1}"><fmt:message
                                        key="page.previous"/></button>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td>
                                <button disabled><fmt:message key="page.previous"/></button>
                            </td>
                        </c:otherwise>
                    </c:choose>

                    <c:forEach begin="1" end="${noOfPagesStudentsForTeacher}" var="i">
                        <c:if test="${currentPageStudentsForTeacher eq i}">
                            <td>${i} of ${noOfPagesStudentsForTeacher}</td>
                        </c:if>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${currentPageStudentsForTeacher lt noOfPagesStudentsForTeacher}">
                            <td>
                                <button name="next" value="${currentPageStudentsForTeacher + 1}"><fmt:message key="page.next"/></button>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td>
                                <button disabled><fmt:message key="page.next"/></button>
                            </td>
                        </c:otherwise>
                    </c:choose>
                </table>
            </table>
            </table>
        </c:when>
        <c:otherwise>
            <h2 style="text-align: center"><fmt:message key="empty.table"/></h2>
        </c:otherwise>
    </c:choose>
</form>
</body>
</html>
