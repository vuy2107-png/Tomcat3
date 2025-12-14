<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<h2>Danh sách Đơn hàng</h2>

<%-- Hiển thị thông báo (nếu có) --%>
<c:if test="${not empty param.message}">
    <p style="color: green; font-weight: bold;">${param.message}</p>
</c:if>

<table border="1" style="margin-top: 15px; width: 100%; border-collapse: collapse;">
    <thead>
    <tr style="background-color: #f2f2f2;">
        <th>ID</th>
        <th>Người đặt</th>
        <th>Ngày đặt</th>
        <th>Tổng tiền</th>
        <th>Trạng thái</th>
        <th>Địa chỉ giao hàng</th>
        <th>Hành động</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="o" items="${orders}">
        <tr>
            <td>${o.id}</td>

                <%-- ĐÃ KHẮC PHỤC LỖI 500: Sử dụng thuộc tính đã được gán userName --%>
            <td>${o.userName}</td>

            <td><fmt:formatDate value="${o.orderDate}" pattern="dd/MM/yyyy HH:mm"/></td>

                <%-- Định dạng tổng tiền --%>
            <td>
                <fmt:formatNumber value="${o.totalAmount}" type="currency" currencySymbol="VND"/>
            </td>

                <%-- Hiển thị Trạng thái (thêm màu sắc) --%>
            <td>
                <span style="font-weight: bold; color:
                <c:choose>
                <c:when test="${o.status eq 'Delivered'}">green</c:when>
                <c:when test="${o.status eq 'Pending'}">orange</c:when>
                <c:when test="${o.status eq 'Cancelled'}">red</c:when>
                <c:when test="${o.status eq 'Processing'}">blue</c:when>
                <c:otherwise>gray</c:otherwise>
                </c:choose>
                        ">${o.status}</span>
            </td>

            <td>${o.shippingAddress}</td>

            <td>
                    <%-- [ĐÃ SỬA LỖI ĐƯỜNG DẪN] action="view" để đồng bộ với AdminOrderServlet --%>
                <c:url var="detailsLink" value="/admin/orders">
                    <c:param name="action" value="view"/>
                    <c:param name="id" value="${o.id}"/>
                </c:url>
                <a href="${detailsLink}">Chi tiết</a> |

                    <%-- THÊM LINK SỬA TRẠNG THÁI TỔNG QUÁT HƠN (Chuyển qua form hoặc modal) --%>
                <c:url var="editLink" value="/admin/orders">
                    <c:param name="action" value="edit"/>
                    <c:param name="id" value="${o.id}"/>
                </c:url>
                <a href="${editLink}">Sửa TT</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>