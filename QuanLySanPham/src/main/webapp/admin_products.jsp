<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<jsp:include page="admin_header.jsp"/>

<div class="container mt-4">
    <h3 class="mb-3">🛍️ Quản lý sản phẩm</h3>
    <a href="${pageContext.request.contextPath}/admin/products?action=create" class="btn btn-success mb-3">+ Thêm sản phẩm</a>

    <table class="table table-bordered table-striped align-middle">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Tên sản phẩm</th>
            <th>Giá</th>
            <th>Số lượng</th>
            <th>Mô tả</th>
            <th>Thao tác</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="p" items="${products}">
            <tr>
                <td>${p.id}</td>
                <td>${p.name}</td>
                <td>${p.price}</td>
                <td>${p.quantity}</td>
                <td>${p.description}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/products?action=edit&id=${p.id}"
                       class="btn btn-warning btn-sm">Sửa</a>
                    <form action="${pageContext.request.contextPath}/admin/products" method="post" style="display:inline"
                          onsubmit="return confirm('Xóa sản phẩm này?')">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${p.id}">
                        <button type="submit" class="btn btn-danger btn-sm">Xóa</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>