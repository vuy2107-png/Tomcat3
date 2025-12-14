<%@ page import="model.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null || !"admin".equals(user.getRole())) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>

<nav class="navbar navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/admin">👑 Admin Panel</a>
        <div>
            <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-outline-light btn-sm me-2">Sản phẩm</a>
            <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-outline-light btn-sm me-2">Người dùng</a>
            <a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-outline-light btn-sm me-2">Đơn hàng</a>
            <a href="${pageContext.request.contextPath}/login?logout=1" class="btn btn-danger btn-sm">Đăng xuất</a>
        </div>
    </div>
</nav>