<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <h3 class="text-center">Đăng nhập</h3>
            <form action="login" method="post">
                <div class="mb-3">
                    <label>Email</label>
                    <input type="email" name="email" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label>Mật khẩu</label>
                    <input type="password" name="password" class="form-control" required>
                </div>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>
                <button class="btn btn-primary w-100">Đăng nhập</button>
            </form>
            <p class="text-center mt-2">Chưa có tài khoản? <a href="register.jsp">Đăng ký</a></p>
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