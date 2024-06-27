package bean;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ScoreDao;
import tool.Action;

public class ScoreUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();

        String academicYear = request.getParameter("academicYear");
        String className = request.getParameter("class");
        String subject = request.getParameter("subject");
        String times = request.getParameter("times");
        int score = Integer.parseInt(request.getParameter("score"));

        ScoreDao scoreDao = new ScoreDao();
        boolean success = scoreDao.updateScore(academicYear, className, subject, times, score);

        if (success) {
            request.getRequestDispatcher("score_update_success.jsp").forward(request, response);
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("updateFailed", "成績の更新に失敗しました。");
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
