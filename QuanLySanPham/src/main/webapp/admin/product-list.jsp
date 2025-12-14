<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- Cần thêm FMT để định dạng giá tiền --%>

<h2>Danh sách Sản phẩm</h2>

<p>
    <%-- Nút Thêm Mới --%>
    <a href="${pageContext.request.contextPath}/admin/products?action=new" style="padding: 10px; background-color: green; color: white; text-decoration: none;">
        + Thêm Sản phẩm Mới
    </a>
</p>

<%-- Hiển thị thông báo (nếu có) --%>
<c:if test="${not empty param.message}">
    <p style="color: green; font-weight: bold;">${param.message}</p>
</c:if>


<table border="1" style="margin-top: 15px;">
    <thead>
    <tr>
        <th>ID</th>
        <th>Tên Sản phẩm</th>
        <th>Giá</th>
        <th>Số lượng</th>
        <th>Mô tả</th>
        <th>Ảnh</th>
        <th>Hành động</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="p" items="${products}">
        <tr>
                <%-- [KHẮC PHỤC] HIỂN THỊ DỮ LIỆU SẢN PHẨM --%>
            <td>${p.id}</td>
            <td>${p.name}</td>
            <td><fmt:formatNumber value="${p.price}" type="currency" currencySymbol="VND"/></td>
            <td>${p.quantity}</td>
            <td>${p.description}</td>
            <td>${p.imageUrl}</td>

            <td>
                    <%-- [KHẮC PHỤC] Link SỬA: Trỏ về AdminProductServlet (/admin/products) --%>
                <c:url var="editLink" value="/admin/products">
                    <c:param name="action" value="edit"/>
                    <c:param name="id" value="${p.id}"/>
                </c:url>
                <a href="${editLink}">Sửa</a> |

                    <%-- [KHẮC PHỤC] Link XÓA: Trỏ về AdminProductServlet (/admin/products) --%>
                <c:url var="deleteLink" value="/admin/products">
                    <c:param name="action" value="delete"/>
                    <c:param name="id" value="${p.id}"/>
                </c:url>
                <a href="${deleteLink}"
                   onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm ${p.name} này không?');">
                    Xóa
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>