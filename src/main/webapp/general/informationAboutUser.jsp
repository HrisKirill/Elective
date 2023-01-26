<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 14.11.2022
  Time: 12:53
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="m" uri="/WEB-INF/custom.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/informationAboutStudent.css">
</head>
<body>
<m:now/>
<jsp:include page="/locale/locale.jsp"/>

<p style="text-align:right"><a href="${pageContext.request.contextPath}/Logout"><fmt:message key="sign.out"/> </a></p>
<div id="nutri-header">
    <h1><fmt:message key="user.information"/></h1>
</div>
<form action="${pageContext.request.contextPath}/UserInfo" method="POST">
    <div class="row">
        <div class="primary col">
            <table>
                <tbody>
                <tr>
                    <td><fmt:message key="user.login"/></td>
                    <td>
                        <ul>
                            <li>${currentUser.getLogin()}</li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="user.lastName"/></td>
                    <td>
                        <ul>
                            <li>${currentUser.getLastName()}</li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="user.firstName"/></td>
                    <td>
                        <ul>
                            <li>${currentUser.getFirstName()}</li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="user.email"/></td>
                    <td>
                        <ul>
                            <li>${currentUser.getEmail()}</li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td><fmt:message key="user.role"/></td>
                    <td>
                        <ul>
                            <li>${currentUser.getRole().name()}</li>
                        </ul>
                    </td>
                </tr>
                </tbody>
            </table>
            <c:choose>
                <c:when test="${currentUser.getRole().name().equalsIgnoreCase('student')}">
                    <input name="changePage" type='button' value='<fmt:message key="user.more"/>'
                           onclick="document.location.href='${pageContext.request.contextPath}/Student/CourseListForStudent'"/>
                </c:when>
                <c:when test="${currentUser.getRole().name().equalsIgnoreCase('sysadmin')}">
                    <input name="changePage" type='button' value='<fmt:message key="user.more"/>'
                           onclick="document.location.href='${pageContext.request.contextPath}/Admin'"/>
                </c:when>
                <c:otherwise>
                    <input name="changePage" type='button' value='<fmt:message key="user.more"/>'
                           onclick="document.location.href='${pageContext.request.contextPath}/Teacher/TableOfCoursesForTeacher'"/>
                </c:otherwise>
            </c:choose>
        </div>


        <div class="secondary col">
            <c:if test="${joinCourseList.size() != 0
                        && currentUser.getRole().name().equalsIgnoreCase('student')}">
            <table>
                <caption><fmt:message key="joinTheCourse"/></caption>
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="course.title"/></th>
                    <th scope="col"><fmt:message key="course.duration"/></th>
                    <th scope="col"><fmt:message key="course.startDate"/></th>
                    <th scope="col"><fmt:message key="course.endDate"/></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="course" items="${joinCourseList}">
                    <tr>
                        <td data-label="<fmt:message key="course.title"/>">${course.getTitle()}</td>
                        <td data-label="<fmt:message key="course.duration"/>">${course.getDuration()}</td>
                        <td data-label="<fmt:message key="course.startDate"/>">${course.getStartDate()}</td>
                        <td data-label="<fmt:message key="course.endDate"/>">${course.getEndDate()}</td>
                        <td>
                            <button name="joinId" value="${course.getId()}"><fmt:message key="joinTheCourse"/></button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <table>
                    <c:choose>
                        <c:when test="${currentPageForUserInfo != 1}">
                            <td>
                                <button name="previous" value="${currentPageForUserInfo - 1}"><fmt:message
                                        key="page.previous"/></button>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td>
                                <button disabled><fmt:message key="page.previous"/></button>
                            </td>
                        </c:otherwise>
                    </c:choose>

                    <c:forEach begin="1" end="${noOfPagesForUserInfo}" var="i">
                        <c:if test="${currentPageForUserInfo eq i}">
                            <td>${i} of ${noOfPagesForUserInfo}</td>
                        </c:if>
                    </c:forEach>

                    <c:choose>
                        <c:when test="${currentPageForUserInfo lt noOfPagesForUserInfo}">
                            <td>
                                <button name="next" value="${currentPageForUserInfo + 1}"><fmt:message
                                        key="page.next"/></button>
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
        </div>
        </c:if>
    </div>
</form>
</body>
</html>