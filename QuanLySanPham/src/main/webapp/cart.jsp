<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h2>Giỏ hàng của bạn</h2>

<c:if test="${empty cartItems}">
    <p>Giỏ hàng của bạn đang trống.</p>
    <p><a href="${pageContext.request.contextPath}/">Tiếp tục mua sắm</a></p>
</c:if>

<c:if test="${not empty cartItems}">
    <table border="1">
        <thead>
        <tr>
            <th>Sản phẩm</th>
            <th>Giá</th>
            <th>Số lượng</th>
            <th>Thành tiền</th>
            <th>Xóa</th>
        </tr>
        </thead>
        <tbody>
        <c:set var="total" value="0"/>
        <c:forEach var="item" items="${cartItems}">
            <tr>
                <td>${item.product.name}</td>
                <td><fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="VND"/></td>
                <td>
                        ${item.quantity}
                        <%-- Thêm form update ở đây --%>
                </td>
                <td><fmt:formatNumber value="${item.subTotal}" type="currency" currencySymbol="VND"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/remove-from-cart?productId=${item.product.id}">Xóa</a>
                </td>
            </tr>
            <c:set var="total" value="${total + item.subTotal}"/>
        </c:forEach>
        </tbody>
        <tfoot>
        <tr>
            <td colspan="3" align="right"><strong>Tổng cộng:</strong></td>
            <td><strong><fmt:formatNumber value="${total}" type="currency" currencySymbol="VND"/></strong></td>
            <td></td>
        </tr>
        </tfoot>
    </table>

    <p style="margin-top: 20px;">
            <%-- Nút Checkout sẽ chuyển hướng đến Servlet xử lý thanh toán --%>
        <a href="${pageContext.request.contextPath}/checkout" style="padding: 10px; background-color: blue; color: white; text-decoration: none;">
            Tiến hành Thanh toán
        </a>
    </p>
</c:if>