<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h2>Sửa sản phẩm</h2>

<form method="post" action="${pageContext.request.contextPath}/admin/products?action=edit">

    <input type="hidden" name="id" value="${product.id}">

    Tên:<br>
    <input type="text" name="name" value="${product.name}" required minlength="2" maxlength="100"><br><br>

    Giá:<br>
    <input type="number" name="price" value="${product.price}" required min="1000"><br><br>

    Số lượng:<br>
    <input type="number" name="quantity" value="${product.quantity}" required min="1"><br><br>

    Mô tả:<br>
    <textarea name="description" rows="4" cols="50">${product.description}</textarea><br><br>

    <button type="submit">Cập nhật sản phẩm</button>
</form>