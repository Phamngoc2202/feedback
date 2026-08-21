package controller;

import dao.FeedbackFormDAO;
import model.FeedbackForm;
import model.Semester;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "FeedbackFormController", urlPatterns = {"/admin/feedback-forms"})
public class FeedbackFormController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FeedbackFormDAO dao = new FeedbackFormDAO();
        String action = request.getParameter("action");

        // Xử lý Bật/Tắt trạng thái form
        if ("toggle".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                int currentStatus = Integer.parseInt(request.getParameter("status"));
                dao.toggleStatus(id, currentStatus);
                response.sendRedirect(request.getContextPath() + "/admin/feedback-forms");
                return;
            } catch (Exception e) { }
        }

        // Tải danh sách
        List<FeedbackForm> fList = dao.getAllForms();
        List<Semester> sList = dao.getAllSemesters();
        
        request.setAttribute("formList", fList);
        request.setAttribute("semesterList", sList);
        request.getRequestDispatcher("/admin/feedback_form_manager.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String title = request.getParameter("title");
        int semesterId = Integer.parseInt(request.getParameter("semesterId"));
        Date startDate = Date.valueOf(request.getParameter("startDate"));
        Date endDate = Date.valueOf(request.getParameter("endDate"));
        
        FeedbackFormDAO dao = new FeedbackFormDAO();
        // Mặc định tạo form mới sẽ ở trạng thái Active (true)
        FeedbackForm newForm = new FeedbackForm(0, title, semesterId, "", startDate, endDate, true);
        dao.insertForm(newForm);

        response.sendRedirect(request.getContextPath() + "/admin/feedback-forms");
    }
}