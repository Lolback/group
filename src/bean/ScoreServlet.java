package kouka;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.StudentDao;

@WebServlet("/ScoreServlet")
public class ScoreServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        List<Student> students = (List<Student>) session.getAttribute("students");

        if (students != null) {
            StudentDao sDao = new StudentDao();
            for (Student student : students) {
                String scoreStr = request.getParameter("score_" + student.getId());
                if (scoreStr != null && !scoreStr.isEmpty()) {
                    int score = Integer.parseInt(scoreStr);
                    student.setScore(score);
                    sDao.updateScore(student);
                }
            }
        }

        response.sendRedirect("student_list.jsp");
    }
}
