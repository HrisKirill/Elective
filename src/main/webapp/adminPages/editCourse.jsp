<%--
  Created by IntelliJ IDEA.
  User: khrystoforov-k
  Date: 03.02.22
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/addAndEdit.css">
    <link rel="stylesheet" type="text/css" href="../css/editMark.css">
</head>
<body>
<jsp:include page="/locale/locale.jsp"/>

<form action="${pageContext.request.contextPath}/Admin/EditCourse" method="POST">
    <div class="container">
        <h1><fmt:message key="edit"/></h1>
        <hr>
        <label for="title"><b><fmt:message key="course.title"/></b></label>
        <input type="text" name="title" id="title"
               value="${editCourse.getTitle()}" readonly>

        <div>
            <label for="duration"><b><fmt:message key="course.duration"/></b> <tags:notEmptyError value="${durationSpellingError}"/>
                <%--${errorDuration}--%></label>
            <input type="number" name="duration" id="duration"
                   min="0" step="15" value="${editCourse.getDuration()}" required>
            <span class="validity"></span>
        </div>

        <div>
            <label for="startDate"><b><fmt:message key="course.startDate"/></b> <tags:notEmptyError value="${startDateSpellingError}"/>
                <%--${errorDate}--%></label>
            <c:choose>
                <c:when test="${currentDate.compareTo(editCourse.getStartDate()) > 0 ||
                currentDate.compareTo(editCourse.getStartDate()) == 0}">
                    <input type="date" name="startDate" id="startDate"
                           value="${editCourse.getStartDate()}"
                           readonly>
                </c:when>
                <c:when test="${currentDate.compareTo(editCourse.getStartDate())< 0}">
                    <input type="date" name="startDate" id="startDate"
                           min="${currentDate}"
                           value="${editCourse.getStartDate()}">
                </c:when>
            </c:choose>
        </div>
        <div>
            <label for="endDate"><b><fmt:message key="course.endDate"/></b> <tags:notEmptyError value="${endDateSpellingError}"/>
                <tags:notEmptyError value="${endDateBeforeStartError}"/>
                <%--${errorDate}--%></label>
            <input type="date" name="endDate" id="endDate"
                   min="${currentDate}"
                   value="${editCourse.getEndDate()}" required>
        </div>

        <div>
            <label for="topics"><b><fmt:message key="addToTopic"/></b></label>
            <select name="topics" id="topics">
                <option value="None"><fmt:message key="choose"/></option>
                <c:forEach var="topic" items="${topicList}">
                    <option value="${topic.getId()}">${topic.getTitle()}</option>
                </c:forEach>
            </select>
        </div>
        <button type="reset" class="registerbtn" onclick="document.location='/Admin/TableOfCourses'"><fmt:message key="go.back"/></button>
        <button type="submit" class="registerbtn"><fmt:message key="edit"/></button>
    </div>
</form>
</body>
</html>
