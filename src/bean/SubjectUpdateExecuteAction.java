package bean;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(); // セッション

        String subjectCode = ""; // 科目コード
        String schoolCode = ""; // 学校コード
        String subjectName = ""; // 科目名
        Subject subject = new Subject(); // 科目
        SubjectDao subjectDao = new SubjectDao(); // 科目Dao
        Map<String, String> errors = new HashMap<>(); // エラーメッセージ

        // リクエストパラメータの取得
        subjectCode = request.getParameter("subjectCode");
        schoolCode = request.getParameter("schoolCode");
        subjectName = request.getParameter("subjectName");

        // ビジネスロジック
        subject.setSubjectCode(subjectCode);
        subject.setSchoolCode(schoolCode);
        subject.setSubjectName(subjectName);

        // 科目の更新処理
        boolean success = subjectDao.update(subject);

        if (success) {
            // 更新成功時の処理
            request.getRequestDispatcher("subject_update_success.jsp").forward(request, response);
        } else {
            // 更新失敗時の処理
            errors.put("updateFailed", "科目の更新に失敗しました。");
            request.setAttribute("errors", errors);
            // エラーを表示するJSPへフォワード
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

}
