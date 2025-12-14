<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Sửa sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">
<h3>Sửa sản phẩm</h3>

<form action="${pageContext.request.contextPath}/admin/products" method="post">
    <!-- ID ẩn để xác định là update -->
    <input type="hidden" name="id" value="${product.id}">

    <div class="mb-3">
        <label class="form-label">Tên sản phẩm</label>
        <input type="text" name="name" class="form-control" value="${product.name}" required>
    </div>
    <div class="mb-3">
        <label class="form-label">Giá</label>
        <input type="number" step="0.01" name="price" class="form-control" value="${product.price}" required>
    </div>
    <div class="mb-3">
        <label class="form-label">Số lượng</label>
        <input type="number" name="quantity" class="form-control" value="${product.quantity}" required>
    </div>
    <div class="mb-3">
        <label class="form-label">Mô tả</label>
        <textarea name="description" class="form-control">${product.description}</textarea>
    </div>

    <button type="submit" class="btn btn-success">Cập nhật</button>
    <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-secondary">Quay lại</a>
</form>
</body>
</html>