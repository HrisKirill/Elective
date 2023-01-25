<%--
  Created by IntelliJ IDEA.
  User: khrystoforov-k
  Date: 03.02.22
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/addAndEdit.css">
</head>
<body>
<jsp:include page="/locale/locale.jsp"/>

<form action="${pageContext.request.contextPath}/Admin/TeacherRegistration" method="POST">
    <div class="container">
        <h1><fmt:message key="register"/></h1>
        <hr>
        <label for="login"><b><fmt:message key="user.login"/></b>
            <tags:notEmptyError value="${loginSpellingError}"/>
            <tags:notEmptyError value="${loginExistsError}"/></label>
        <input type="text" name="login" id="login"
               title="<fmt:message key="title.login"/>"
               value="${incorrectUserLogin}"
               required>


        <label for="lastName"><b><fmt:message key="user.lastName"/></b>
            <tags:notEmptyError value="${lastNameSpellingError}"/></label>
        <input type="text" name="lastName" id="lastName"
               title="<fmt:message key="title.login"/>"
               value="${incorrectUserLastName}"
               required>


        <label for="firstName"><b><fmt:message key="user.firstName"/></b>
            <tags:notEmptyError value="${firstNameSpellingError}"/></label>
        <input type="text" name="firstName" id="firstName"
               title="<fmt:message key="title.firstName"/>"
               value="${incorrectUserFirstName}"
               required>


        <label for="email"><b><fmt:message key="user.email"/></b>
            <tags:notEmptyError value="${emailSpellingError}"/>
            <tags:notEmptyError value="${emailExistsError}"/></label>
        <input type="email" name="email" id="email"
               value="${incorrectUserEmail}"
               required>


        <label for="psw"><b><fmt:message key="user.password"/></b>
            <tags:notEmptyError value="${passwordSpellingError}"/>
            <tags:notEmptyError value="${passwordsAreNotEqualsError}"/></label>
        <input type="password" name="psw" id="psw"
               title="<fmt:message key="title.password"/>"
               value="${incorrectUserPassword}"
               required>


        <label for="psw-repeat"><b><fmt:message key="user.repPassword"/></b>
            <tags:notEmptyError value="${passwordAgainSpellingError}"/>
            <tags:notEmptyError value="${passwordsAreNotEqualsError}"/></label>
        <input type="password" name="psw-repeat" id="psw-repeat"
               title="<fmt:message key="title.password"/>"
               value="${incorrectUserAgainPassword}"
               required>

        <label for="setCourseForTeacher"><b><fmt:message key="course"/></b> </label>
        <select name="setCourseForTeacher" id="setCourseForTeacher" required>
            <option value="None" selected><fmt:message key="choose"/></option>
            <c:forEach var="course" items="${coursesWithoutTeachersList}">
                <option value="${course.getId()}">${course.getTitle()}</option>
            </c:forEach>
        </select>
        <p></p>
        <label for="role"><b><fmt:message key="user.role"/></b></label>
        <input type="text" value="Teacher" name="role" id="role" readonly>
        <hr>

        <button type="reset" class="registerbtn" onclick="document.location='/Admin/TableOfTeachers'"><fmt:message
                key="go.back"/></button>
        <button type="submit" class="registerbtn"><fmt:message key="register"/></button>
    </div>

</form>
</body>
</html>
