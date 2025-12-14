<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng ký</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-5">
            <h3 class="text-center">Đăng ký</h3>
            <form action="register" method="post">
                <div class="mb-3"><label>Họ tên</label><input class="form-control" name="name" required></div>
                <div class="mb-3"><label>Email</label><input type="email" class="form-control" name="email" required></div>
                <div class="mb-3"><label>Mật khẩu</label><input type="password" class="form-control" name="password" required></div>
                <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
                <button class="btn btn-success w-100">Đăng ký</button>
            </form>
            <p class="text-center mt-2">Đã có tài khoản? <a href="login">Đăng nhập</a></p>
        </div>
    </div>
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