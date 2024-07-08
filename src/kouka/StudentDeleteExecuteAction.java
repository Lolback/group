package kouka;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import dao.StudentDao;
import tool.Action;

public class StudentDeleteExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		HttpSession session = request.getSession();//セッション

		String no = "";
		Student student = new Student();//学生
		StudentDao sDao = new StudentDao();//学生Dao
		//ClassNumDao cNumDao = new ClassNumDao();//クラス番号Daoを初期化
		Map<String, String> errors = new HashMap<>();//エラーメッセージ

		no = request.getParameter("no");

		student.setNo(no);

		sDao.delete(student);

		//JSPへフォワード
		request.getRequestDispatcher("student_delete_success.jsp").forward(request, response);
	}

}
