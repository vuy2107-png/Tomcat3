<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

    <%-- Tên dự án --%>
    <a class="sidebar-brand d-flex align-items-center justify-content-center" href="${pageContext.request.contextPath}/admin/products">
        <div class="sidebar-brand-icon rotate-n-15">
            <i class="fas fa-tshirt"></i>
        </div>
        <div class="sidebar-brand-text mx-3">Shop Clothes Admin</div>
    </a>

    <%-- Divider --%>
    <hr class="sidebar-divider my-0">

    <%-- Item Dashboard --%>
    <li class="nav-item active">
        <a class="nav-link" href="${pageContext.request.contextPath}/admin/products">
            <i class="fas fa-fw fa-tachometer-alt"></i>
            <span>Dashboard</span></a>
    </li>

    <%-- Divider --%>
    <hr class="sidebar-divider">

    <%-- Heading --%>
    <div class="sidebar-heading">
        Quản lý Dữ liệu
    </div>

    <%-- Quản lý Sản phẩm --%>
    <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/admin/products">
            <i class="fas fa-fw fa-boxes"></i>
            <span>Sản phẩm</span>
        </a>
    </li>

    <%-- Quản lý Đơn hàng --%>
    <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/admin/orders">
            <i class="fas fa-fw fa-receipt"></i>
            <span>Đơn hàng</span>
        </a>
    </li>

    <%-- Quản lý Người dùng --%>
    <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/admin/users">
            <i class="fas fa-fw fa-users"></i>
            <span>Người dùng</span>
        </a>
    </li>

    <%-- Divider --%>
    <hr class="sidebar-divider d-none d-md-block">

    <%-- Sidebar Toggler (Dùng cho mobile) --%>
    <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
    </div>

</ul>