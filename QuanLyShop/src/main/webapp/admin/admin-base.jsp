<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><c:out value="${title}" default="Admin Dashboard"/></title>
    <%-- Thêm các file CSS chung của Admin tại đây --%>
</head>
<body>
<div id="wrapper">
    <%-- Lỗi ở đây: Nên đặt các file layout trong thư mục con --%>
    <jsp:include page="layout/sidebar.jsp"/>

    <div id="content-wrapper">
        <jsp:include page="layout/header.jsp"/>

        <div class="container-fluid">
            <%-- Nơi nội dung động (product-list, user-list) được chèn vào --%>
            <jsp:include page="${viewPath}"/>
        </div>

        <jsp:include page="layout/footer.jsp"/>
    </div>
</div>
</body>
</html>