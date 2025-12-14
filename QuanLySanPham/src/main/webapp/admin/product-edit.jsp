<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Chỉnh sửa Sản phẩm: ${requestScope.product.name}</h2>

<c:set var="product" value="${requestScope.product}"/>

<form method="POST" action="${pageContext.request.contextPath}/admin/products">
    <input type="hidden" name="action" value="update">

    <input type="hidden" name="id" value="${product.id}">

    <table>
        <tr>
            <td><label for="name">Tên Sản phẩm:</label></td>
            <td><input type="text" id="name" name="name" value="${product.name}" required></td>
        </tr>
        <tr>
            <td><label for="price">Giá:</label></td>
            <td><input type="number" id="price" name="price" step="0.01" value="${product.price}" required></td>
        </tr>
        <tr>
            <td><label for="quantity">Số lượng:</label></td>
            <td><input type="number" id="quantity" name="quantity" value="${product.quantity}" required></td>
        </tr>
        <tr>
            <td><label for="description">Mô tả:</label></td>
            <td><textarea id="description" name="description">${product.description}</textarea></td>
        </tr>
        <tr>
            <td><label for="image_url">URL Ảnh (tạm thời):</label></td>
            <td><input type="text" id="image_url" name="image_url" value="${product.imageUrl}"></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Cập nhật Sản phẩm">
                <a href="${pageContext.request.contextPath}/admin/products">Hủy bỏ</a>
            </td>
        </tr>
    </table>
</form>