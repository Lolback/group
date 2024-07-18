package kouka;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Score;
import tool.Action;

public class ScoreUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(); // セッション

        String studentNo = request.getParameter("no");
        String subject = request.getParameter("subject");
        String point = request.getParameter("point");

        // 成績情報を設定
        Score score = new Score();
        score.setStudentNo(studentNo);
        score.setSubjectCode(subject);
        score.setPoint(point);

        // リクエスト属性に設定
        request.setAttribute("score", score);

        // JSPへフォワード
        request.getRequestDispatcher("updateScore.jsp").forward(request, response);
    }
}
