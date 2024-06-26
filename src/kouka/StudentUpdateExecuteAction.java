package kouka;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		HttpSession session = request.getSession();//セッション

		String classNum="";//入力されたクラス番号
		String name="";//入力された学生番号
		String isAttendStr="";//入力された在学フラグ
		int entYear = 0;//入が宇年度
		boolean isAttend = false;//在学フラグ
		Student student = new Student();//学生
		StudentDao sDao = new StudentDao();//学生Dao
		//ClassNumDao cNumDao = new ClassNumDao();//クラス番号Daoを初期化
		Map<String, String> errors = new HashMap<>();//エラーメッセージ

		//リクエストパラメータの取得 2
		classNum = request.getParameter("f3");
		name = request.getParameter("f4");
		isAttendStr = request.getParameter("f5");

		//ビジネスロジック 4
		if (isAttendStr != null) {
			//在学フラグを立てる
			isAttend = true;
		}

		student.setClassNum(classNum);
		student.setName(name);
		student.setIsAttend(isAttend);

		sDao.save(student);

		//JSPへフォワード
		request.getRequestDispatcher("student_list.jsp").forward(request, response);
	}

}
