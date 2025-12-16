package controller;

import dao.DBConnection;
import dao.HocSinhDAO;
import dao.SachDAO;
import dao.TheMuonSachDAO;
import model.Sach;
import model.TheMuonSach;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet("/muonSach")
public class TheMuonSachServlet extends HttpServlet {

    private SachDAO sachDAO = new SachDAO();
    private HocSinhDAO hocSinhDAO = new HocSinhDAO();
    private TheMuonSachDAO dao = new TheMuonSachDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String maSachParam = request.getParameter("maSach");

        // --- LOGIC MƯỢN SÁCH (Chuyển đến form.jsp) ---
        if (maSachParam != null) {
            try {
                int maSach = Integer.parseInt(maSachParam);
                Sach sachDuocChon = sachDAO.findById(maSach);

                // Kiểm tra số lượng sách (Yêu cầu 2: Lỗi mượn hết)
                if (sachDuocChon != null && sachDuocChon.getSoLuong() <= 0) {
                    request.setAttribute("listSach", sachDAO.findAll()); // Tải lại danh sách sách
                    request.setAttribute("error", "Cuốn sách này tạm thời đã cho mượn hết, vui lòng chọn sách khác");
                    request.getRequestDispatcher("sach/list.jsp").forward(request, response);
                    return;
                }

                request.setAttribute("sachDuocChon", sachDuocChon);
                request.setAttribute("hocSinhList", hocSinhDAO.findAll());
                request.getRequestDispatcher("muonSach/form.jsp").forward(request, response);
                return;
            } catch (NumberFormatException e) {
                response.sendRedirect("sach"); // Nếu maSach không hợp lệ
                return;
            }
        }

        // --- LOGIC THỐNG KÊ (Chuyển đến list.jsp) ---
        String keyword = request.getParameter("keyword");
        request.setAttribute("listMuon", dao.findBorrowed(keyword));
        request.setAttribute("keyword", keyword);

        request.getRequestDispatcher("muonSach/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        Connection conn = null;
        String action = request.getParameter("action");

        try {
            // --- LOGIC TRẢ SÁCH (Yêu cầu 3) ---
            if ("traSach".equals(action)) {
                int maMuonSach = Integer.parseInt(request.getParameter("maMuonSachInt"));

                conn = DBConnection.getTransactionConnection();

                int maSach = dao.traSach(maMuonSach, conn); // 1. Cập nhật thẻ mượn

                if (maSach != -1) {
                    sachDAO.tangSoLuong(maSach, conn); // 2. Tăng số lượng sách
                }

                conn.commit();

                response.sendRedirect("muonSach"); // Quay lại trang thống kê
                return;
            }

            // --- LOGIC MƯỢN SÁCH (Yêu cầu 2) ---
            else {
                int maSach = Integer.parseInt(request.getParameter("maSach"));
                int maHocSinh = Integer.parseInt(request.getParameter("maHocSinh"));
                String ngayTraParam = request.getParameter("ngayTra");

                conn = DBConnection.getTransactionConnection();

                TheMuonSach t = new TheMuonSach(
                        maSach,
                        maHocSinh,
                        true,
                        new Date(System.currentTimeMillis()),
                        ngayTraParam != null && !ngayTraParam.isEmpty() ? Date.valueOf(ngayTraParam) : null
                );

                dao.insert(t, conn);
                sachDAO.giamSoLuong(maSach, conn);
                conn.commit();

                response.sendRedirect("sach");
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dữ liệu nhập không hợp lệ (Mã/Số lượng).");
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi giao dịch, thao tác thất bại.");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}