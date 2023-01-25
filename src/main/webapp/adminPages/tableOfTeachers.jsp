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
<p style="text-align:right"><a href="${pageContext.request.contextPath}/Logout"><fmt:message key="sign.out"/></a></p>
<a href="${pageContext.request.contextPath}/Admin/TeacherRegistration"><fmt:message key="sign.up.teacher"/></a>
<form action="${pageContext.request.contextPath}/Admin/TableOfTeachers" method="POST">
    <c:choose>
        <c:when test="${teachers.size() > 0}">
            <table>
                <tags:caption value="user.teachers"/>
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="user.lastName"/></th>
                    <th scope="col"><fmt:message key="user.firstName"/></th>
                    <th scope="col"><fmt:message key="user.login"/></th>
                    <th scope="col"><fmt:message key="user.email"/></th>
                    <th scope="col"><fmt:message key="edit"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="teacher" items="${teachers}">
                    <tr>
                        <td data-label="<fmt:message key="user.lastName"/>">${teacher.getLastName()}</td>
                        <td data-label="<fmt:message key="user.firstName"/>">${teacher.getFirstName()}</td>
                        <td data-label="<fmt:message key="user.login"/>">${teacher.getLogin()}</td>
                        <td data-label="<fmt:message key="user.email"/>">${teacher.getEmail()}</td>
                        <td>
                            <button id="editTeacher" value="${teacher.getId()}"
                                    name="editTeacher"><fmt:message key="set.course"/></button>
                        </td>
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
