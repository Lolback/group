package kouka;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class StudentCreateAction extends Action {


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		HttpSession session = request.getSession();//セッション

        ClassNumDao cNumDao = new ClassNumDao(); // クラス番号Daoを初期化
        Teacher teacher = new Teacher();
        teacher = (Teacher) session.getAttribute("current_teacher");

		Map<String, String> errors = new HashMap<>();//エラーメッセージ

		List<Integer> entYearCandidates = new ArrayList<Integer>();

		LocalDateTime nowDate = LocalDateTime.now();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy");
		String nowDateString = dateFormatter.format(nowDate);
		int nowDateInt = Integer.parseInt(nowDateString);

		for(int i = nowDateInt - 10; i < nowDateInt + 10; i++){
			entYearCandidates.add(i);
		}

        //クラス番号候補
        List<ClassNum> classNumList = cNumDao.filter(teacher.getSchool());
        // リストを初期化
        List<Integer> classNumSet = new ArrayList<>();
        // クラス番号をリストに追加
        for (int i = 0; i <= classNumList.size() - 1; i++) {
        	classNumSet.add(classNumList.get(i).getClassNum());
        }

        //重複フラグ
        try {
        	boolean duplicate_flag = (boolean)request.getAttribute("duplicate_flag");
        } catch(Exception e) {
        	request.setAttribute("duplicate_flag", false);
        }

		request.setAttribute("ent_year_set", entYearCandidates);
        request.setAttribute("class_num_set", classNumSet);

		//JSPへフォワード
		request.getRequestDispatcher("student_add.jsp").forward(request, response);
	}

}
