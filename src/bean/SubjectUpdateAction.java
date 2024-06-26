package bean;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(); // セッション

        String subjectCode = request.getParameter("subjectCode");
        Subject subject = new Subject(); // 科目
        SubjectDao subjectDao = new SubjectDao(); // 科目Dao
        Map<String, String> errors = new HashMap<>(); // エラーメッセージ

        subject = subjectDao.getBySubjectCode(subjectCode);

        if (subject != null) {
            request.setAttribute("subjectCode", subject.getSubjectCode());
            request.setAttribute("schoolCode", subject.getSchoolCode());
            request.setAttribute("subjectName", subject.getSubjectName());

            // JSPへフォワード
            request.getRequestDispatcher("subject_update.jsp").forward(request, response);
        } else {
            // 科目が見つからない場合のエラーハンドリング
            errors.put("notFound", "指定された科目が見つかりません。");
            request.setAttribute("errors", errors);
            // エラーを表示するJSPへフォワード
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

}
