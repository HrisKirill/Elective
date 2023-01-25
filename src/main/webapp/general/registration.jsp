<%--
  Created by IntelliJ IDEA.
  User: khrystoforov-k
  Date: 03.02.22
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/addAndEdit.css">
    <script src="https://www.google.com/recaptcha/api.js"></script>
</head>
<body>
<jsp:include page="/locale/locale.jsp"/>

<form action="${pageContext.request.contextPath}/Registration" method="POST">
    <div class="container">
        <h1><fmt:message key="sign.up"/></h1>
        <hr>
        <label for="login"><b><fmt:message key="user.login"/></b>
            <tags:notEmptyError value="${loginSpellingError}"/>
            <tags:notEmptyError value="${loginExistsError}"/></label>
        <input type="text" name="login" id="login"
               value="${incorrectUserLogin}"
               title="<fmt:message key="title.login"/>" required>


        <label for="lastName"><b><fmt:message key="user.lastName"/></b>
            <tags:notEmptyError value="${lastNameSpellingError}"/></label>
        <input type="text" name="lastName" id="lastName"
               value="${incorrectUserLastName}"
               title="<fmt:message key="title.lastName"/>" required>


        <label for="firstName"><b><fmt:message key="user.firstName"/></b>
            <tags:notEmptyError value="${firstNameSpellingError}"/></label>
        <input type="text" name="firstName" id="firstName"
               value="${incorrectUserFirstName}"
               title="<fmt:message key="title.firstName"/>" required>


        <label for="email"><b><fmt:message key="user.email"/></b>
            <tags:notEmptyError value="${emailSpellingError}"/>
            <tags:notEmptyError value="${emailExistsError}"/></label>
        <input type="email" name="email" id="email"
               value="${incorrectUserEmail}" required>


        <label for="psw"><b><fmt:message key="user.password"/></b>
            <tags:notEmptyError value="${passwordSpellingError}"/>
            <tags:notEmptyError value="${passwordsAreNotEqualsError}"/></label>
        <input type="password" name="psw" id="psw"
               title="<fmt:message key="title.password"/>"
               value="${incorrectUserPassword}" required>


        <label for="psw-repeat"><b><fmt:message key="user.repPassword"/></b>
            <tags:notEmptyError value="${passwordAgainSpellingError}"/>
            <tags:notEmptyError value="${passwordsAreNotEqualsError}"/></label>
        <input type="password" name="psw-repeat" id="psw-repeat"
               title="<fmt:message key="title.password"/>"
               value="${incorrectUserAgainPassword}"
               required>

        <label for="role"><b><fmt:message key="user.role"/></b></label>
        <input type="text" value="Student" name="role" id="role" readonly>
        <hr>

        <td>
            <tags:notEmptyError value="${captchaError}"/>
            <div class="g-recaptcha"
                 data-sitekey="6LfkMc8eAAAAAPc1-Lfnmo_MaNFENasnznRr6k-P"></div>
        </td>

        <button type="reset" class="registerbtn" onclick="document.location='/Login'"><fmt:message
                key="go.back"/></button>
        <button type="submit" class="registerbtn"><fmt:message key="sign.up"/></button>
    </div>

</form>
</body>
</html>
