<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Mượn Sách</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f4f4f4; }
        h2 { text-align: center; color: #333; }
        .form-container { width: 450px; margin: 50px auto; padding: 30px; background: white; border: 1px solid #ddd; border-radius: 8px; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); }
        .form-group { margin-bottom: 15px; display: flex; align-items: center; }
        .form-group label { width: 150px; font-weight: bold; color: #555; }
        .form-group input, .form-group select { flex-grow: 1; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; background-color: #f9f9f9; }
        .form-group input[readonly] { background-color: #eee; color: #777; }
        .btn-group { text-align: center; margin-top: 30px; }
        .btn-group button { padding: 10px 20px; margin: 0 10px; cursor: pointer; border: none; border-radius: 4px; font-weight: bold; transition: background-color 0.3s; }
        .btn-muon { background-color: #4CAF50; color: white; }
        .btn-muon:hover { background-color: #45a049; }
        .btn-huy { background-color: #f44336; color: white; }
        .btn-huy:hover { background-color: #da190b; }
    </style>
</head>

<body>
<div class="form-container">
    <h2>Mượn Sách</h2>

    <c:set var="s" value="${requestScope.sachDuocChon}"/>
    <c:set var="hocSinhList" value="${requestScope.hocSinhList}"/>
    <c:set var="maMuonSach" value="MS-XXXX"/>

    <form action="muonSach" method="post">
        <input type="hidden" name="maSach" value="${s.maSach}">

        <div class="form-group">
            <label>Mã mượn sách:</label>
            <input type="text" value="${maMuonSach}" readonly>
        </div>

        <div class="form-group">
            <label>Tên sách:</label>
            <input type="text" value="${s.tenSach}" readonly>
        </div>

        <div class="form-group">
            <label for="maHocSinh">Tên học sinh:</label>
            <select id="maHocSinh" name="maHocSinh" required>
                <c:forEach items="${hocSinhList}" var="hs">
                    <option value="${hs.maHocSinh}">${hs.hoTen} - ${hs.lop}</option>
                </c:forEach>
            </select>
        </div>

        <jsp:useBean id="currentDate" class="java.util.Date" />
        <div class="form-group">
            <label>Ngày mượn sách:</label>
            <input type="text" value="<fmt:formatDate value="${currentDate}" pattern="yyyy-MM-dd"/>" readonly>
        </div>

        <div class="form-group">
            <label for="ngayTra">Ngày trả sách:</label>
            <input type="date" id="ngayTra" name="ngayTra" placeholder="YYYY-MM-DD">
        </div>

        <div class="btn-group">
            <button type="submit" class="btn-muon">Mượn sách</button>
            <button type="button" class="btn-huy" onclick="window.location.href='sach'">Hủy</button>
        </div>
    </form>
</div>
</body>
</html>