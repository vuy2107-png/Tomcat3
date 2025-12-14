<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.CartItem" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Giỏ hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h3>Giỏ hàng của bạn</h3>

    <c:if test="${empty cart}">
        <p>Giỏ hàng trống.</p>
        <a href="${pageContext.request.contextPath}/product" class="btn btn-primary">Tiếp tục mua sắm</a>
    </c:if>

    <c:if test="${not empty cart}">
        <form action="${pageContext.request.contextPath}/cart" method="get">
            <input type="hidden" name="action" value="update">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Sản phẩm</th>
                    <th>Giá</th>
                    <th>Số lượng</th>
                    <th>Tổng</th>
                    <th>Hành động</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="totalPrice" value="0" scope="page"/>
                <c:forEach var="ci" items="${cart}">
                    <tr>
                        <td>${ci.product.name}</td>
                        <td>${ci.product.price}</td>
                        <td>
                            <input type="number" name="quantity_${ci.product.id}" value="${ci.quantity}" min="1" max="${ci.product.quantity}" class="form-control" style="width:80px;">
                        </td>
                        <td>
                                ${ci.product.price * ci.quantity}
                            <c:set var="totalPrice" value="${totalPrice + (ci.product.price * ci.quantity)}"/>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/cart?action=remove&id=${ci.product.id}" class="btn btn-danger btn-sm" onclick="return confirm('Xóa sản phẩm?')">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <th colspan="3" class="text-end">Tổng tiền:</th>
                    <th colspan="2">${totalPrice} VND</th>
                </tr>
                </tfoot>
            </table>
            <button type="submit" class="btn btn-success">Cập nhật giỏ hàng</button>
            <a href="${pageContext.request.contextPath}/product" class="btn btn-primary">Tiếp tục mua sắm</a>
            <a href="${pageContext.request.contextPath}/order" class="btn btn-warning">Đặt hàng</a>
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