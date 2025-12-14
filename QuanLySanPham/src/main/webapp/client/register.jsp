<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng ký Tài khoản</title>
</head>
<body>

<h1>Đăng ký Tài khoản Mới</h1>

<%-- Hiển thị thông báo lỗi nếu có --%>
<c:if test="${not empty requestScope.error}">
    <p style="color: red; font-weight: bold;">${requestScope.error}</p>
</c:if>

<form action="${pageContext.request.contextPath}/register" method="post">

    <label for="name">Họ và Tên:</label><br>
    <%-- Giữ lại giá trị cũ nếu đăng ký thất bại --%>
    <input type="text" id="name" name="name" required value="${requestScope.name}"><br><br>

    <label for="email">Email:</label><br>
    <%-- Giữ lại giá trị cũ nếu đăng ký thất bại --%>
    <input type="email" id="email" name="email" required value="${requestScope.email}"><br><br>

    <label for="password">Mật khẩu:</label><br>
    <input type="password" id="password" name="password" required><br><br>

    <button type="submit">Đăng ký</button>
</form>

<p>
    Đã có tài khoản? <a href="${pageContext.request.contextPath}/login">Đăng nhập ngay</a>
</p>

</body>
</html>