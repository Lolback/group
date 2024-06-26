package kouka;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.SubjectDao;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(); // セッション

        String subjectCode = request.getParameter("subjectCode");
        SubjectDao subjectDao = new SubjectDao(); // 科目Dao
        Map<String, String> errors = new HashMap<>(); // エラーメッセージ

        // 科目の削除処理
        boolean success = subjectDao.delete(subjectCode);

        if (success) {
            // 削除成功時の処理
            request.getRequestDispatcher("subject_delete_success.jsp").forward(request, response);
        } else {
            // 削除失敗時の処理
            errors.put("deleteFailed", "科目の削除に失敗しました。");
            request.setAttribute("errors", errors);
            // エラーを表示するJSPへフォワード
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
