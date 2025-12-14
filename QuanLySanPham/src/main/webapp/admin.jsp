<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.User" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang quản trị</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<nav class="navbar navbar-dark bg-dark mb-4">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">👑 Quản trị Shop</a>
        <div class="text-white">
            Xin chào, <b><%= user.getName() %></b> |
            <a href="login?logout=1" class="text-white">Đăng xuất</a>
        </div>
    </div>
</nav>

<div class="container">
    <h2 class="mb-4">Bảng điều khiển Admin</h2>

    <div class="row">
        <div class="col-md-4">
            <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-primary w-100 mb-3">🛍️ Quản lý sản phẩm</a>
        </div>
        <div class="col-md-4">
            <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-warning w-100 mb-3">👤 Danh sách người dùng</a>
        </div>
        <div class="col-md-4">
            <a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-success w-100 mb-3">📦 Theo dõi đơn hàng</a>
        </div>
    </div>
</div>
</body>
</html>