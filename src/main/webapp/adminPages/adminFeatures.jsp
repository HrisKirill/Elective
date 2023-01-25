<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 17.12.2022
  Time: 11:19
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
<p style="text-align:right"><a href="${pageContext.request.contextPath}/Logout"><fmt:message key="sign.out"/></a></p>
<form action="${pageContext.request.contextPath}/Admin" method="POST">
    <table>
        <tags:caption value="user.admin.features"/>
        <thead>
        <tr>
            <th scope="col"><fmt:message key="features"/></th>
            <th scope="col"><fmt:message key="choice"/></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td><fmt:message key="user.admin.registerTeacher"/></td>
            <td>
                <input type='button' value='<fmt:message key="choose"/>'
                       onclick="document.location.href='${pageContext.request.contextPath}/Admin/TableOfTeachers'"/>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="user.admin.ADECourse"/></td>
            <td>
                <input type='button' value='<fmt:message key="choose"/>'
                       onclick="document.location.href='${pageContext.request.contextPath}/Admin/TableOfCourses'"/>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="user.admin.BUStudent"/></td>
            <td>
                <input type='button' value='<fmt:message key="choose"/>'
                       onclick="document.location.href='${pageContext.request.contextPath}/Admin/ChangeStatus'"/>
            </td>
        </tr>
        <tr>
            <td><fmt:message key="add.topic"/></td>
            <td>
                <label for="topics"><b><fmt:message key="all.topics"/></b></label>
                <select name="topics" id="topics">
                    <option selected></option>
                    <c:forEach var="topic" items="${topicList}">
                        <option value="${topic.getId()}">${topic.getTitle()}</option>
                    </c:forEach>
                </select>
                <input type='button' value='<fmt:message key="add"/>'
                       onclick="document.location.href='${pageContext.request.contextPath}/Admin/AddTopic'"/>
            </td>
        </tr>
        </tbody>
    </table>
</form>
</body>
</html>
