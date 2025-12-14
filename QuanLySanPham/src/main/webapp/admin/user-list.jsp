<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h2>Danh sách Người dùng</h2>

<p>
    <%-- Link THÊM MỚI đã đúng --%>
    <a href="${pageContext.request.contextPath}/admin/users?action=new" style="padding: 10px; background-color: green; color: white; text-decoration: none;">
        + Thêm Người dùng Mới
    </a>
</p>

<c:if test="${not empty param.message}">
    <p style="color: green; font-weight: bold;">${param.message}</p>
</c:if>

<table border="1" style="margin-top: 15px;">
    <thead>
    <tr>
        <th>ID</th>
        <th>Tên</th>
        <th>Email</th>
        <th>Quyền (Role)</th>
        <th>Hành động</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="u" items="${users}">
        <tr>
            <td>${u.id}</td>
            <td>${u.name}</td>
            <td>${u.email}</td>
            <td>${u.role}</td>
            <td>
                    <%-- Link SỬA: Sửa value thành /admin/users --%>
                <c:url var="editLink" value="/admin/users">
                    <c:param name="action" value="edit"/>
                    <c:param name="id" value="${u.id}"/>
                </c:url>
                <a href="${editLink}">Sửa</a> |

                    <%-- Link XÓA: Sửa value thành /admin/users --%>
                <c:url var="deleteLink" value="/admin/users">
                    <c:param name="action" value="delete"/>
                    <c:param name="id" value="${u.id}"/>
                </c:url>
                <a href="${deleteLink}"
                   onclick="return confirm('Bạn có chắc chắn muốn xóa người dùng ${u.email} này không?');">
                    Xóa
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>