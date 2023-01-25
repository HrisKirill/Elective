<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 19.12.2022
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<html>
<body>
<jsp:include page="/locale/locale.jsp"/>

<form action="${pageContext.request.contextPath}/Admin/AssignmentOfTheCourse" method="POST">
    <p><a href="${pageContext.request.contextPath}/Admin/TableOfTeachers"><fmt:message key="go.back"/></a></p>
    <label for="editCourseForTeacher"><b><fmt:message key="course"/></b> </label>
    <select name="editCourseForTeacher" id="editCourseForTeacher" required>
        <option value="None" selected><fmt:message key="choose"/></option>
        <c:forEach var="course" items="${coursesWithoutTeachersList}">
            <option value="${course.getId()}">${course.getTitle()}</option>
        </c:forEach>
    </select>
    <button type="submit" ><fmt:message key="submit"/></button>
</form>
</body>
</html>
