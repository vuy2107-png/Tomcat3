<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Danh sách khách hàng</title>
    <style>
        table {
            border-collapse: collapse;
            width: 700px;
            margin: 40px auto;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: left;
        }
        th {
            background: #f2f2f2;
            font-weight: bold;
        }
        img {
            width: 80px;
            height: auto;
        }
        h2 {
            text-align: center;
        }
    </style>
</head>

<body>

<h2>Danh sách khách hàng</h2>

<table>
    <tr>
        <th>Tên</th>
        <th>Ngày sinh</th>
        <th>Địa chỉ</th>
        <th>Ảnh</th>
    </tr>

    <c:forEach var="c" items="${list}">
        <tr>
            <td>${c.name}</td>
            <td>${c.birthday}</td>
            <td>${c.address}</td>
            <td><img src="${c.image}"></td>
        </tr>
    </c:forEach>

</table>

</body>
</html>
