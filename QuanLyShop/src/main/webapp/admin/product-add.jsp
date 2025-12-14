<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h2>Thêm sản phẩm</h2>

<form method="post" action="${pageContext.request.contextPath}/admin/products?action=add">

    Tên:<br>
    <input type="text" name="name" required minlength="2" maxlength="100"><br><br>

    Giá:<br>
    <input type="number" name="price" required min="1000"><br><br>

    Số lượng:<br>
    <input type="number" name="quantity" required min="1"><br><br>

    Mô tả:<br>
    <textarea name="description" rows="4" cols="50"></textarea><br><br>

    <button type="submit">Lưu sản phẩm mới</button>
</form>