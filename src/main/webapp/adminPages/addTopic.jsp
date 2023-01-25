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
</head>
<body>
<jsp:include page="/locale/locale.jsp"/>

<form action="${pageContext.request.contextPath}/Admin/AddTopic" method="POST">
    <div class="container">
        <h1><fmt:message key="add.topic"/></h1>
        <hr>
        <label for="title"><b><fmt:message key="course.title"/></b>
            <tags:notEmptyError value="${titleSpellingError}"/>
            <tags:notEmptyError value="${titleExistsError}"/></label>
        <input type="text" name="title" id="title" value="${incorrectCourseTitle}" required>

        <button type="reset" class="registerbtn" onclick="document.location='/Admin'"><fmt:message
                key="go.back"/></button>
        <button type="submit" class="registerbtn"><fmt:message key="add"/></button>
    </div>

</form>
</body>
</html>
