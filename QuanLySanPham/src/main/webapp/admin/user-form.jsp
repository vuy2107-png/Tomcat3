<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="user" value="${requestScope.user}"/>
<c:set var="action" value="${empty user.id ? 'insert' : 'update'}"/>

<h2>${requestScope.title}</h2>

<form method="POST" action="${pageContext.request.contextPath}/admin/users">
    <input type="hidden" name="action" value="${action}">

    <c:if test="${not empty user.id}">
        <input type="hidden" name="id" value="${user.id}">
    </c:if>

    <table>
        <tr>
            <td><label for="name">Tên:</label></td>
            <td><input type="text" id="name" name="name" value="${user.name}" required></td>
        </tr>
        <tr>
            <td><label for="email">Email:</label></td>
            <td><input type="email" id="email" name="email" value="${user.email}" required></td>
        </tr>
        <tr>
            <td><label for="password">Mật khẩu:</label></td>
            <td>
                <input type="password" id="password" name="password"
                       <c:if test="${action eq 'insert'}">required</c:if>
                       placeholder="<c:if test="${action eq 'update'}">Để trống nếu không muốn đổi</c:if>"
                >
            </td>
        </tr>
        <tr>
            <td><label for="role">Quyền (Role):</label></td>
            <td>
                <select id="role" name="role" required>
                    <option value="user" <c:if test="${user.role eq 'user'}">selected</c:if>>User</option>
                    <option value="admin" <c:if test="${user.role eq 'admin'}">selected</c:if>>Admin</option>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Lưu Người dùng">
                <a href="${pageContext.request.contextPath}/admin/users">Hủy</a>
            </td>
        </tr>
    </table>
</form>