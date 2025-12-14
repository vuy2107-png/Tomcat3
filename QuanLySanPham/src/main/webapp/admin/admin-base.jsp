<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><c:out value="${title}" default="Admin Dashboard"/></title>
    <%-- Thêm CSS của bạn tại đây --%>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; }
        #wrapper { display: flex; min-height: 100vh; }
        .sidebar { width: 250px; background-color: #333; color: white; padding: 20px; }
        #content-wrapper { flex-grow: 1; display: flex; flex-direction: column; }
        .header { padding: 15px; background-color: #f8f9fa; border-bottom: 1px solid #ddd; }
        .main-content { padding: 20px; flex-grow: 1; }
        .footer { padding: 10px; background-color: #f1f1f1; text-align: center; font-size: 0.8em; }
    </style>
</head>
<body>
<div id="wrapper">
    <%-- Sidebar (Menu) --%>
    <jsp:include page="layout/sidebar.jsp"/>

    <div id="content-wrapper">
        <%-- Header --%>
        <jsp:include page="layout/header.jsp"/>

        <div class="main-content">
            <%-- Nội dung động được chèn vào --%>
            <jsp:include page="${viewPath}"/>
        </div>

        <%-- Footer --%>
        <jsp:include page="layout/footer.jsp"/>
    </div>
</div>
</body>
</html>