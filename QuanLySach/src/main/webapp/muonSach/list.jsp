<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Thống kê sách đang cho mượn</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h2 { text-align: center; color: #333; }
        .search-container { width: 85%; margin: 20px auto 10px; text-align: right; }
        .search-container input[type="text"] { padding: 8px; border: 1px solid #ccc; border-radius: 4px; }
        .search-container button { padding: 8px 15px; background-color: #5cb85c; color: white; border: none; border-radius: 4px; cursor: pointer; }
        table { border-collapse: collapse; width: 85%; margin: 0 auto; border: 1px solid black; }
        th, td { border: 1px solid black; padding: 8px; text-align: center; }
        th { background-color: #f2f2f2; font-weight: bold; }
        td:nth-child(2), td:nth-child(4) { text-align: left; }
        .tra-button { background-color: #f0ad4e; color: white; border: none; padding: 5px 10px; cursor: pointer; border-radius: 2px; font-size: 14px; }
        .tra-button:hover { background-color: #ec971f; }
    </style>
    <script>
        function confirmTraSach(maMuonSachInt) {
            var confirmed = confirm("Bạn có chắc chắn muốn trả sách này không?");
            if (confirmed) {
                // Sử dụng form ẩn để gửi POST request cho hành động Trả sách
                var form = document.createElement('form');
                form.method = 'POST';
                form.action = 'muonSach';

                var actionField = document.createElement('input');
                actionField.type = 'hidden';
                actionField.name = 'action';
                actionField.value = 'traSach';
                form.appendChild(actionField);

                var idField = document.createElement('input');
                idField.type = 'hidden';
                idField.name = 'maMuonSachInt';
                idField.value = maMuonSachInt;
                form.appendChild(idField);

                document.body.appendChild(form);
                form.submit();
            }
        }
    </script>
</head>

<body>
<h2>Thống kê sách đang cho mượn</h2>

<div class="search-container">
    <form action="muonSach" method="GET">
        <input type="text" name="keyword" placeholder="Nhập tên sách hoặc học sinh"
               value="${requestScope.keyword != null ? requestScope.keyword : ''}">
        <button type="submit">Tìm kiếm</button>
    </form>
</div>

<table>
    <tr>
        <th>Mã mượn sách</th>
        <th>Tên sách</th>
        <th>Tác giả</th>
        <th>Tên học sinh</th>
        <th>Lớp</th>
        <th>Ngày mượn</th>
        <th>Ngày trả dự kiến</th>
        <th></th>
    </tr>

    <c:forEach items="${requestScope.listMuon}" var="t">
        <tr>
            <td>${t.maMuonSach}</td>
            <td>${t.tenSach}</td>
            <td>${t.tacGia}</td>
            <td>${t.hoTen}</td>
            <td>${t.lop}</td>
            <td><fmt:formatDate value="${t.ngayMuon}" pattern="dd/MM/yyyy"/></td>
            <td>
                <c:if test="${not empty t.ngayTra}">
                    <fmt:formatDate value="${t.ngayTra}" pattern="dd/MM/yyyy"/>
                </c:if>
                <c:if test="${empty t.ngayTra}">
                    N/A
                </c:if>
            </td>
            <td>
                <button type="button" class="tra-button"
                        onclick="confirmTraSach(${t.maMuonSachInt})">
                    Trả sách
                </button>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>