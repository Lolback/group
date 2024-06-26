package bean;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/score")
public class Score extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/scoreManagement.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String academicYear = request.getParameter("academicYear");
        String className = request.getParameter("class");
        String subject = request.getParameter("subject");
        String times = request.getParameter("times");

        // 検索結果をデータベースから取得（仮のデータとして固定値を使用）
        // 実際にはここでデータベースアクセスを行い、条件に合致するデータを取得する
        String result = "検索結果：入学年度=" + academicYear + ", クラス=" + className + ", 科目=" + subject + ", 回数=" + times;

        // 検索結果をリクエスト属性に設定
        request.setAttribute("result", result);

        // 結果ページに転送
        request.getRequestDispatcher("/WEB-INF/score.jsp").forward(request, response);
    }
}

