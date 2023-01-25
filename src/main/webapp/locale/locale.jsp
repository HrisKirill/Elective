<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 05.01.2023
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<fmt:setBundle basename="resources"/>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<form>
    <label style="text-align: right">
        <select name="locale" onchange='submit();'>
            <option value="en" ${sessionScope.locale eq 'en' ? 'selected' : ''}>
                <fmt:message key="en"/>
            </option>
            <option value="ua_UA" ${sessionScope.locale eq 'ua_UA' ? 'selected' : ''}>
                <fmt:message key="ua"/>
            </option>
        </select>
    </label>
</form>

