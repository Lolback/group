package kouka;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Score;
import dao.ScoreDao;
import tool.Action;

public class ScoreUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String studentNo = request.getParameter("studentNo");
        String subjectCode = request.getParameter("subjectCode");
        String point = request.getParameter("point");
        String entYear = request.getParameter("entYear");
        String classNum = request.getParameter("classNum");
        String name = request.getParameter("name");

        Score score = new Score();
        score.setStudentNo(studentNo);
        score.setSubjectCode(subjectCode);
        score.setPoint(point);
        score.setEntYear(entYear);
        score.setClassNum(classNum);
        score.setName(name);

        ScoreDao scoreDao = new ScoreDao();
        boolean saved = scoreDao.save(score);

        if (saved) {
            // 成功時の処理
            response.sendRedirect("score_update_success.jsp");
        } else {

            request.setAttribute("errorMessage", "成績の更新に失敗しました。");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
