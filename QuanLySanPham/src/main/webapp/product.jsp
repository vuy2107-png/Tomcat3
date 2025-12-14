<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    model.User user = (model.User) session.getAttribute("user");
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark shadow-sm" style="background-color: #222;">
    <div class="container-fluid">
        <a class="navbar-brand fw-bold text-warning" href="product" style="font-size: 1.5rem;">
            🛍️ Shop<span class="text-light">Online</span>
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
            <ul class="navbar-nav align-items-center">
                <c:if test="${not empty user}">
                    <li class="nav-item me-3 text-light">
                        <b>👋 Xin chào, <span class="text-warning">${user.name}</span></b>
                    </li>
                    <li class="nav-item me-3">
                        <a class="nav-link text-warning fw-semibold" href="cart?action=view">🛒 Giỏ hàng</a>
                    </li>
                    <li class="nav-item">
                        <a class="btn btn-outline-warning btn-sm fw-semibold"
                           href="login?logout=1"
                           onclick="return confirm('Đăng xuất?')">
                            Đăng xuất
                        </a>
                    </li>
                </c:if>

                <c:if test="${empty user}">
                    <li class="nav-item">
                        <a class="btn btn-warning fw-semibold text-dark" href="login">Đăng nhập</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>


<div class="container mt-4">
    <c:if test="${param.success == 'added'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ✅ Sản phẩm đã được thêm vào giỏ hàng!
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <h3 class="mb-3">Danh sách sản phẩm</h3>
    <form action="product" method="get" class="d-flex mb-4">
        <input type="hidden" name="action" value="search">
        <input type="text" name="keyword" class="form-control me-2" placeholder="Tìm sản phẩm...">
        <button type="submit" class="btn btn-outline-primary" style="width: 120px">Tìm kiếm</button>
    </form>

    <div class="row">
        <c:forEach var="p" items="${products}">
            <div class="col-md-4 mb-3">
                <div class="card h-100 shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">${p.name}</h5>
                        <p class="card-text">${p.description}</p>
                        <p><strong>${p.price}</strong> VND</p>
                        <p>Số lượng: ${p.quantity}</p>

                        <!-- Form POST thêm vào giỏ -->
                        <form action="cart" method="post" class="mb-2">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="id" value="${p.id}">
                            <button type="submit" class="btn btn-primary w-100"
                                    <c:if test="${p.quantity == 0}">disabled</c:if>>
                                <c:choose>
                                    <c:when test="${p.quantity == 0}">Hết hàng</c:when>
                                    <c:otherwise>Thêm vào giỏ</c:otherwise>
                                </c:choose>
                            </button>
                        </form>
                        <a href="product?action=view&id=${p.id}" class="btn btn-success btn-sm w-100">Xem chi tiết</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<!-- Footer -->
<footer class="bg-light text-center text-lg-start mt-5">
    <div class="container p-4">
        <div class="row">
            <div class="col-lg-6 col-md-12 mb-4">
                <h5 class="text-uppercase">Shop Online</h5>
                <p>
                    Shop mình cần tuyển người MUA, không yêu cầu bằng cấp, chỉ cần có tiền là được ạ.
                </p>
            </div>
            <div class="col-lg-3 col-md-6 mb-4">
                <h5 class="text-uppercase">Liên hệ</h5>
                <ul class="list-unstyled mb-0">
                    <li>📞 0123-456-789</li>
                    <li>✉️ support@shop.com</li>
                    <li>🏠 123 Đường ABC, TP.HN</li>
                </ul>
            </div>
            <div class="col-lg-3 col-md-6 mb-4">
                <h5 class="text-uppercase">Mạng xã hội</h5>
                <ul class="list-unstyled mb-0">
                    <li><a href="#" class="text-dark">Facebook</a></li>
                    <li><a href="#" class="text-dark">Instagram</a></li>
                    <li><a href="#" class="text-dark">Twitter</a></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="text-center p-3 bg-dark text-white">
        © 2025 Shop Online. All rights reserved.
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>