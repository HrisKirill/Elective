<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 21.12.2022
  Time: 21:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/tableForUser.css">
</head>
<body>
<jsp:include page="/locale/locale.jsp"/>

<a href="${pageContext.request.contextPath}/Admin"><fmt:message key="go.back"/></a>
<p style="text-align:right"><a href="${pageContext.request.contextPath}/Logout"><fmt:message key="sign.out"/> </a></p>
<form action="${pageContext.request.contextPath}/Admin/ChangeStatus" method="POST">
    <c:choose>
        <c:when test="${students.size() > 0}">
            <table>
                <tags:caption value="user.students"/>
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="user.lastName"/></th>
                    <th scope="col"><fmt:message key="user.firstName"/></th>
                    <th scope="col"><fmt:message key="user.login"/></th>
                    <th scope="col"><fmt:message key="user.email"/></th>
                    <th scope="col"><fmt:message key="user.status"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="student" items="${students}">
                    <tr>
                        <td data-label="<fmt:message key="user.lastName"/>">${student.getLastName()}</td>
                        <td data-label="<fmt:message key="user.firstName"/>">${student.getFirstName()}</td>
                        <td data-label="<fmt:message key="user.login"/>">${student.getLogin()}</td>
                        <td data-label="<fmt:message key="user.email"/>">${student.getEmail()}</td>
                        <c:choose>
                            <c:when test="${student.getStatus().name().equalsIgnoreCase('unblocked')}">
                                <td data-label="<fmt:message key="user.status"/>">
                                    <button name="statusStudentId" value="${student.getId()}"><fmt:message
                                            key="user.unblocked"/></button>
                                    <button disabled><fmt:message key="user.blocked"/></button>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td data-label="Status">
                                    <button disabled><fmt:message key="user.unblocked"/></button>
                                    <button name="statusStudentId" value="${student.getId()}"><fmt:message
                                            key="user.blocked"/></button>
                                </td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                </c:forEach>
                </tbody>

                <table>
                    <c:choose>
                        <c:when test="${currentPage != 1}">
                            <td>
                                <button name="previous" value="${currentPage - 1}"><fmt:message
                                        key="page.previous"/></button>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td>
                                <button disabled><fmt:message key="page.previous"/></button>
                            </td>
                        </c:otherwise>
                    </c:choose>

                    <c:forEach begin="1" end="${noOfPages}" var="i">
                        <c:if test="${currentPage eq i}">
                            <td>${i}</td>
                        </c:if>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${currentPage lt noOfPages}">
                            <td>
                                <button name="next" value="${currentPage + 1}"><fmt:message key="page.next"/></button>
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
        </c:when>
        <c:otherwise>
            <h2 style="text-align: center"><fmt:message key="empty.table"/></h2>
        </c:otherwise>
    </c:choose>
</form>
</body>
</html>
