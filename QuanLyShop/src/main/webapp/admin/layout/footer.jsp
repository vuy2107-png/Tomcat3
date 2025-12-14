<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<footer class="sticky-footer bg-white">
    <div class="container my-auto">
        <div class="copyright text-center my-auto">
            <span>Copyright &copy; Shop Clothes 2024</span>
        </div>
    </div>
</footer>

<%-- Logout Modal (Dùng khi nhấp Đăng xuất trên Header) --%>
<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Sẵn sàng đăng xuất?</h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">Chọn "Đăng xuất" bên dưới nếu bạn đã sẵn sàng kết thúc phiên hiện tại.</div>
            <div class="modal-footer">
                <button class="btn btn-secondary" type="button" data-dismiss="modal">Hủy</button>
                <%-- Thay đổi link Đăng xuất tại đây --%>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            </div>
        </div>
    </div>
</div>

<%-- Nơi thêm các file JS chung của Admin (Ví dụ: jQuery, Bootstrap JS) --%>
<%-- Ví dụ: --%>
<script src="${pageContext.request.contextPath}/assets/vendor/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>