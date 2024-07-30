package kouka;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class StudentDeleteAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		HttpSession session = request.getSession();//セッション
        Teacher teacher = new Teacher();
        teacher = (Teacher) session.getAttribute("current_teacher");
        School school = teacher.getSchool();

		String no = request.getParameter("no");
		Student student = new Student();//学生
		StudentDao sDao = new StudentDao();//学生Dao
		Map<String, String> errors = new HashMap<>();//エラーメッセージ

		student = sDao.get(no, school);
		System.out.println(student.getNo());
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
