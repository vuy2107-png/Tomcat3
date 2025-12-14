<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h2>Thêm Sản phẩm Mới</h2>

<form method="POST" action="${pageContext.request.contextPath}/admin/products">
    <input type="hidden" name="action" value="insert">

    <table>
        <tr>
            <td><label for="name">Tên Sản phẩm:</label></td>
            <td><input type="text" id="name" name="name" required></td>
        </tr>
        <tr>
            <td><label for="price">Giá:</label></td>
            <td><input type="number" id="price" name="price" step="0.01" required></td>
        </tr>
        <tr>
            <td><label for="quantity">Số lượng:</label></td>
            <td><input type="number" id="quantity" name="quantity" required></td>
        </tr>
        <tr>
            <td><label for="description">Mô tả:</label></td>
            <td><textarea id="description" name="description"></textarea></td>
        </tr>
        <tr>
            <td><label for="image_url">URL Ảnh (tạm thời):</label></td>
            <td><input type="text" id="image_url" name="image_url"></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Lưu Sản phẩm">
                <a href="${pageContext.request.contextPath}/admin/products">Hủy</a>
            </td>
        </tr>
    </table>
</form>