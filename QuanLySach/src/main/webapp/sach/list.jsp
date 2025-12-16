<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Danh sách sách</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h2 { text-align: center; color: #333; }
        table { border-collapse: collapse; width: 85%; margin: 20px auto; border: 1px solid black; }
        th, td { border: 1px solid black; padding: 8px; text-align: center; }
        th { background-color: #f2f2f2; font-weight: bold; }
        td:nth-child(2), td:nth-child(5) { text-align: left; }
        .muon-button { background-color: #007bff; color: white; border: none; padding: 5px 10px; cursor: pointer; border-radius: 2px; font-size: 14px; }
        .muon-button:hover { background-color: #0056b3; }
        .error-message { color: red; text-align: center; margin-bottom: 15px; font-weight: bold; border: 1px solid red; padding: 10px; width: 50%; margin: 15px auto; background-color: #ffeaea;}
    </style>
</head>

<body>
<h2 style="text-align:center;">Danh sách sách</h2>

<c:if test="${not empty error}">
    <div class="error-message">
            ${error}
    </div>
</c:if>

<table>
    <tr>
        <th>Mã sách</th>
        <th>Tên sách</th>
        <th>Tác giả</th>
        <th>Số lượng</th>
        <th>Mô tả</th>
        <th></th>
    </tr>

    <c:forEach items="${listSach}" var="s">
        <tr>
            <td>${s.maSach}</td>
            <td>${s.tenSach}</td>
            <td>${s.tacGia}</td>
            <td>${s.soLuong}</td>
            <td>${s.moTa}</td>
            <td>
                <c:choose>
                    <c:when test="${s.soLuong > 0}">
                        <form action="muonSach" method="get">
                            <input type="hidden" name="maSach" value="${s.maSach}">
                            <button type="submit" class="muon-button">Mượn</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <span style="color: gray; font-size: 0.9em;">Hết sách</span>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>