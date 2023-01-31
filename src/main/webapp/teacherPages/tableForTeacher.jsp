<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 17.11.2022
  Time: 15:34
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/tableForUser.css">
</head>
<body>
<jsp:include page="/locale/locale.jsp"/>

<a href="${pageContext.request.contextPath}/UserInfo"><fmt:message key="go.back"/></a>
<p style="text-align:right"><a href="${pageContext.request.contextPath}/Logout"> <fmt:message key="sign.out"/> </a></p>
<form action="${pageContext.request.contextPath}/Teacher/TableOfCoursesForTeacher" method="POST">
    <c:choose>
        <c:when test="${courseMap.size() > 0}">
            <table>
                <tags:caption value="courses"/>
                <thead>
                <select name="select">
                    <option value="all"><fmt:message key="selection.all"/></option>
                    <option value="doNotStart"><fmt:message key="listOfCourses.doNotStart"/></option>
                    <option value="stillOngoing"><fmt:message key="listOfCourses.stillOngoing"/></option>
                    <option value="completedCourses"><fmt:message key="listOfCourses.completedCourses"/></option>
                </select>

                <button name="ok"><fmt:message key="accept"/></button>
                <a href="${pageContext.request.contextPath}/Teacher/Journal">Download journal</a>
                <tr>
                    <th scope="col"><fmt:message key="course.title"/></th>
                    <th scope="col"><fmt:message key="course.duration"/></th>
                    <th scope="col"><fmt:message key="course.startDate"/></th>
                    <th scope="col"><fmt:message key="course.endDate"/></th>
                    <th scope="col"><fmt:message key="showStudents"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="entry" items="${courseMap.entrySet()}">
                    <tr>
                        <td data-label="<fmt:message key="course.title"/>">${entry.getKey().getTitle()}</td>
                        <td data-label="<fmt:message key="course.duration"/>">${entry.getKey().getDuration()}</td>
                        <td data-label="<fmt:message key="course.startDate"/>">${entry.getKey().getStartDate()}</td>
                        <td data-label="<fmt:message key="course.endDate"/>">${entry.getKey().getEndDate()}</td>
                        <td>
                            <button id="showStudents" name="showStudents" value="${entry.getKey().getId()}">
                                <fmt:message key="show"/></button>
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
                            <td>${i} of ${noOfPages}</td>
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
