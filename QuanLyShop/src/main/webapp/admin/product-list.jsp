<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- Đảm bảo bạn đã khai báo fmt ở đây --%>

<html>
<head>
    <title>Danh sách sản phẩm</title>
</head>
<body>

<h2>Danh sách sản phẩm</h2>

<a href="${pageContext.request.contextPath}/admin/products?action=add">
    ➕ Thêm sản phẩm
</a>

<table border="1" cellpadding="10" cellspacing="0">
    <tr>
        <th>ID</th>
        <th>Tên</th>
        <th>Giá</th>
        <th>Số lượng</th>
        <th>Mô tả</th>
        <th>Hành động</th>
    </tr>

    <c:forEach var="p" items="${products}">
        <tr>
            <td>${p.id}</td>
            <td>${p.name}</td>
            <td>
                    <%-- FORMAT GIÁ TIỀN CHO DỄ ĐỌC (VD: 100.000) --%>
                <fmt:formatNumber value="${p.price}" type="number" pattern="#,###"/> đ
            </td>
            <td>${p.quantity}</td>
            <td>${p.description}</td>
            <td>
                    <%-- SỬ DỤNG c:url CHO LINK SỬA --%>
                <c:url var="editLink" value="/admin/products">
                    <c:param name="action" value="edit"/>
                    <c:param name="id" value="${p.id}"/>
                </c:url>
                <a href="${editLink}">Sửa</a>

                |

                    <%-- SỬ DỤNG c:url VÀ THÊM CONFIRM CHO LINK XÓA --%>
                <c:url var="deleteLink" value="/admin/products">
                    <c:param name="action" value="delete"/>
                    <c:param name="id" value="${p.id}"/>
                </c:url>
                <a href="${deleteLink}"
                   onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm ${p.name} (ID: ${p.id}) này không?');">
                    Xóa
                </a>
            </td>
        </tr>
    </c:forEach>

</table>

</body>
</html>