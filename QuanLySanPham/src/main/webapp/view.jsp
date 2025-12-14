<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Chi tiết sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .product-card {
            max-width: 500px;
            margin: 30px auto;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            border-radius: 10px;
        }
        .product-card img {
            max-height: 300px;
            object-fit: cover;
            border-top-left-radius: 10px;
            border-top-right-radius: 10px;
        }
        .product-info p {
            font-size: 1.1rem;
        }
        .btn-back {
            margin-top: 20px;
        }
    </style>
</head>
<body class="bg-light">
<div class="card product-card">
    <div class="card-body product-info">
        <h3 class="card-title mb-3">${product.name}</h3>
        <p><strong>Giá:</strong> ${product.price} VND</p>
        <p><strong>Số lượng:</strong> ${product.quantity}</p>
        <p><strong>Mô tả:</strong> ${product.description}</p>

        <a href="product" class="btn btn-secondary btn-back">← Quay lại danh sách</a>
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