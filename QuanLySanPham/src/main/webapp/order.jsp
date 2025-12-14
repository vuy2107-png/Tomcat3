<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Đơn hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h3>Đặt hàng</h3>

    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
        <a href="product" class="btn btn-primary">Tiếp tục mua sắm</a>
    </c:if>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
        <a href="cart?action=view" class="btn btn-warning">Quay lại giỏ hàng</a>
    </c:if>

    <c:if test="${empty success && empty error}">
        <form action="order" method="post">
            <p>Bạn có muốn đặt hàng tất cả sản phẩm trong giỏ không?</p>
            <button type="submit" class="btn btn-success">Đặt hàng</button>
            <a href="cart?action=view" class="btn btn-secondary">Quay lại giỏ hàng</a>
        </form>
    </c:if>
</div>
<!-- Footer -->
<footer class="bg-dark text-white text-center py-3" style="position: fixed; bottom: 0; width: 100%; z-index: 1000;">
    <div class="container">
        © 2025 Shop Online. All rights reserved. |
        <a href="#" class="text-white text-decoration-none">Liên hệ</a> |
        <a href="#" class="text-white text-decoration-none">Facebook</a> |
        <a href="#" class="text-white text-decoration-none">Instagram</a>
    </div>
</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>