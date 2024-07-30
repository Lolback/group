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

public class StudentCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		HttpSession session = request.getSession();//セッション
        Teacher teacher = new Teacher();
        teacher = (Teacher) session.getAttribute("current_teacher");
        School school = teacher.getSchool();

		String entYearStr="";//入力された入学年度
		String no="";//入力された学生番号
		String classNum="";//入力されたクラス番号
		String name="";//入力された学生番号
		String isAttendStr="";//入力された在学フラグ
		int entYear = 0;//入が宇年度
		boolean isAttend = false;//在学フラグ
		Student student = new Student();//学生
		StudentDao sDao = new StudentDao();//学生Dao
		Map<String, String> errors = new HashMap<>();//エラーメッセージ

		//リクエストパラメータの取得 2
		entYearStr = request.getParameter("f1");
		no = request.getParameter("f2");
		classNum = request.getParameter("f3");
		name = request.getParameter("f4");
		isAttendStr = request.getParameter("f5");

		//ビジネスロジック 4
		if (entYearStr != null) {
			//数値に変換
			entYear = Integer.parseInt(entYearStr);
		}
		if (isAttendStr != null) {
			//在学フラグを立てる
			isAttend = true;
		}

		student.setEntYear(entYear);
		student.setNo(no);
		student.setClassNum(classNum);
		student.setName(name);
		student.setIsAttend(isAttend);
		student.setSchool(teacher.getSchool());

		//重複検知
		Student duplicateStudent = sDao.get(no, school);
		if (duplicateStudent != null) {
			request.setAttribute("duplicate_flag", true);
			request.getRequestDispatcher("StudentCreate.action").forward(request, response);
			return;
		}

		sDao.save(student, school);

		//JSPへフォワード
		request.getRequestDispatcher("student_add_success.jsp").forward(request, response);
	}

}
