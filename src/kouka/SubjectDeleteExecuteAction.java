package kouka;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.SubjectDao;
import tool.Action;
public class SubjectDeleteExecuteAction extends Action {
   @Override
   public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
       HttpSession session = request.getSession(); // セッション
       Teacher teacher = new Teacher();
       teacher = (Teacher) session.getAttribute("current_teacher");
       String schoolCode = teacher.getSchool().getCd();
       String subjectCode = request.getParameter("subjectCode");
       SubjectDao subjectDao = new SubjectDao(); // 科目Dao
       Map<String, String> errors = new HashMap<>(); // エラーメッセージ
       try {
           // 科目の削除処理
           boolean success = subjectDao.delete(subjectCode, schoolCode);
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
       } catch (Exception e) {
           errors.put("systemError", "システムエラーが発生しました。");
           request.setAttribute("errors", errors);
           request.getRequestDispatcher("error.jsp").forward(request, response);
       }
   }
}