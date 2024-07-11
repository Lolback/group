package kouka;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import dao.StudentDao;
import tool.Action;

public class StudentDeleteAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		HttpSession session = request.getSession();//セッション

		String no = request.getParameter("no");
		Student student = new Student();//学生
		StudentDao sDao = new StudentDao();//学生Dao
		Map<String, String> errors = new HashMap<>();//エラーメッセージ

		System.out.println(no);

		student = sDao.get(no);

		System.out.println(student.getName());

		request.setAttribute("no", student.getNo());
		request.setAttribute("classNum", student.getClassNum());
		request.setAttribute("name", student.getName());
		request.setAttribute("entYear", student.getEntYear());
		request.setAttribute("isAttend", student.getIsAttend());
		request.setAttribute("school", student.getSchool());

		//JSPへフォワード
		request.getRequestDispatcher("student_delete.jsp").forward(request, response);
	}

}
