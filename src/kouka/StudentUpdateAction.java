package kouka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		HttpSession session = request.getSession();//セッション

		String no = request.getParameter("no");
		Student student = new Student();//学生
		StudentDao sDao = new StudentDao();//学生Dao
        ClassNumDao cNumDao = new ClassNumDao(); // クラス番号Daoを初期化
        Teacher teacher = new Teacher();
        teacher = (Teacher) session.getAttribute("current_teacher");
		Map<String, String> errors = new HashMap<>();//エラーメッセージ

		student = sDao.get(no);

        //クラス番号候補
        List<ClassNum> classNumList = cNumDao.filter(teacher.getSchool());
        // リストを初期化
        List<Integer> classNumSet = new ArrayList<>();
        // クラス番号をリストに追加
        for (int i = 0; i <= classNumList.size() - 1; i++) {
        	classNumSet.add(classNumList.get(i).getClassNum());
        }

		request.setAttribute("no", student.getNo());
		request.setAttribute("classNum", student.getClassNum());
		request.setAttribute("name", student.getName());
		request.setAttribute("entYear", student.getEntYear());
		request.setAttribute("isAttend", student.getIsAttend());
		request.setAttribute("school", student.getSchool());
        request.setAttribute("class_num_set", classNumSet);

		//JSPへフォワード
		request.getRequestDispatcher("student_update.jsp").forward(request, response);
	}

}
