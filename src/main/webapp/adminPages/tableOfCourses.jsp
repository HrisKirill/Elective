<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 17.11.2022
  Time: 15:34
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/tableForUser.css">
</head>
<body>
<jsp:include page="/locale/locale.jsp"/>

<a href="${pageContext.request.contextPath}/Admin"><fmt:message key="go.back"/></a>
<p style="text-align:right"><a href="${pageContext.request.contextPath}/Logout"> <fmt:message key="sign.out"/> </a></p>
<a href="${pageContext.request.contextPath}/Admin/AddCourse"><fmt:message key="add.course"/></a>
<form action="${pageContext.request.contextPath}/Admin/TableOfCourses" method="POST">
    <c:choose>
        <c:when test="${coursesForAdmin.size() > 0}">
            <table>
                <tags:caption value="courses"/>
                <thead>
                <select name="sort">
                    <option value="sortByTitle"><fmt:message key="sort.title"/></option>
                    <option value="sortByDuration"><fmt:message key="sort.duration"/></option>
                    <option value="sortByNumberOfStudents"><fmt:message key="sort.numberOfStudents"/></option>
                </select>

                <button name="ok"><fmt:message key="accept"/></button>

                <tr>
                    <th scope="col"><fmt:message key="course.title"/></th>
                    <th scope="col"><fmt:message key="course.duration"/></th>
                    <th scope="col"><fmt:message key="course.startDate"/></th>
                    <th scope="col"><fmt:message key="course.endDate"/></th>
                    <th scope="col"><fmt:message key="actions"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="course" items="${coursesForAdmin}">
                    <tr>
                        <td data-label="<fmt:message key="course.title"/>">${course.getTitle()}</td>
                        <td data-label="<fmt:message key="course.duration"/>">${course.getDuration()}</td>
                        <td data-label="<fmt:message key="course.startDate"/>">${course.getStartDate()}</td>
                        <td data-label="<fmt:message key="course.endDate"/>">${course.getEndDate()}</td>
                        <td>
                            <c:choose>
                                <c:when test="${currentDate.compareTo(course.getEndDate()) == 0 ||
                    currentDate.compareTo(course.getEndDate()) > 0}">
                                    <button name="editCourseId" value="${course.getId()}" disabled><fmt:message
                                            key="edit"/></button>
                                </c:when>
                                <c:otherwise>
                                    <button name="editCourseId" value="${course.getId()}"><fmt:message
                                            key="edit"/></button>
                                </c:otherwise>
                            </c:choose>

                            <button name="deleteCourseId" value="${course.getId()}"
                                    onclick="return confirm('<fmt:message key="confirm"/>')"><fmt:message key="delete"/>
                            </button>
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
