package kouka;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class StudentDeleteExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		HttpSession session = request.getSession();//セッション
        Teacher teacher = new Teacher();
        teacher = (Teacher) session.getAttribute("current_teacher");
        School school = teacher.getSchool();

		String no = "";
		StudentDao sDao = new StudentDao();//学生Dao
		Map<String, String> errors = new HashMap<>();//エラーメッセージ

		no = request.getParameter("no");

		sDao.delete(no, school);

		//JSPへフォワード
		request.getRequestDispatcher("student_delete_success.jsp").forward(request, response);
	}

}
