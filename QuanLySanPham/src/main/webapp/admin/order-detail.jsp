<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="dao.ProductDAO" %>
<%@ page import="model.Product" %>
<%@ page import="java.util.Optional" %>

<h2>Chi tiết Đơn hàng #${orderId}</h2>

<p><a href="${pageContext.request.contextPath}/admin/orders">
    &leftarrow; Quay lại Danh sách Đơn hàng
</a></p>

<table border="1" style="margin-top: 15px; width: 80%; border-collapse: collapse;">
    <thead>
    <tr style="background-color: #e6e6fa;">
        <th>Sản phẩm ID</th>
        <th>Tên Sản phẩm</th>
        <th>Số lượng</th>
        <th>Giá tại thời điểm đặt</th>
        <th>Thành tiền</th>
    </tr>
    </thead>
    <tbody>

    <%-- KHỞI TẠO DAO CHO TOÀN BỘ TRANG (GIẢI QUYẾT LỖI SCRIPTLET) --%>
    <%! private final ProductDAO productDao = new ProductDAO(); %>
    <c:set var="grandTotal" value="${0}"/>

    <c:forEach var="detail" items="${orderDetails}">

        <%-- SỬA LỖI TẠI ĐÂY: Dùng EL để truy cập trực tiếp thuộc tính --%>
        <%-- <c:set var="productOptional" value="<%= productDao.getProductById(detail.getProductId()) %>"/> --%>

        <%-- CÁCH ĐÚNG: TRUYỀN GIÁ TRỊ TỪ JSTL VÀO SCRIPTLET --%>
        <%
            // Lấy giá trị productId an toàn từ JSTL object 'detail'
            int productId = ((model.OrderDetail) pageContext.getAttribute("detail")).getProductId();

            // Gọi DAO bằng biến Java
            Optional<model.Product> productOptional = productDao.getProductById(productId);

            // Đặt lại kết quả vào biến context để JSTL có thể truy cập
            pageContext.setAttribute("productOptional", productOptional);
        %>

        <c:set var="productName" value="${productOptional.isPresent() ? productOptional.get().getName() : 'Không tìm thấy'}"/>

        <%-- Các dòng tính toán này đã đúng vì nó dùng JSTL --%>
        <c:set var="subTotal" value="${detail.quantity * detail.price}"/>
        <c:set var="grandTotal" value="${grandTotal + subTotal}"/>

        <tr>
            <td>${detail.productId}</td>
            <td>${productName}</td>
            <td>${detail.quantity}</td>
            <td><fmt:formatNumber value="${detail.price}" type="currency" currencySymbol="VND"/></td>
            <td><fmt:formatNumber value="${subTotal}" type="currency" currencySymbol="VND"/></td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr style="font-weight: bold; background-color: #f0f0f0;">
        <td colspan="4" style="text-align: right;">TỔNG CỘNG:</td>
        <td><fmt:formatNumber value="${grandTotal}" type="currency" currencySymbol="VND"/></td>
    </tr>
    </tfoot>
</table>

<h3 style="margin-top: 30px;">Cập nhật Trạng thái Đơn hàng</h3>
<c:if test="${not empty param.message}">
    <p style="color: green; font-weight: bold;">${param.message}</p>
</c:if>

<form action="${pageContext.request.contextPath}/admin/orders" method="post" style="padding: 15px; border: 1px solid #ccc; width: 300px;">
    <input type="hidden" name="action" value="update_status">
    <input type="hidden" name="id" value="${orderId}">

    <label for="status">Trạng thái:</label>
    <select name="status" id="status" required>
        <option value="Pending">Pending (Chờ xử lý)</option>
        <option value="Processing">Processing (Đang xử lý)</option>
        <option value="Shipped">Shipped (Đã giao)</option>
        <option value="Delivered">Delivered (Hoàn thành)</option>
        <option value="Cancelled">Cancelled (Hủy)</option>
    </select>

    <button type="submit" style="margin-top: 10px; padding: 5px 10px;">Cập nhật</button>
</form>