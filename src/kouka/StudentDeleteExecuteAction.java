package kouka;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.StudentDao;
import tool.Action;

public class StudentDeleteExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	throws Exception{

		String no = "";
		StudentDao sDao = new StudentDao();//学生Dao
		Map<String, String> errors = new HashMap<>();//エラーメッセージ

		no = request.getParameter("no");

		sDao.delete(no);

		//JSPへフォワード
		request.getRequestDispatcher("student_delete_success.jsp").forward(request, response);
	}

}
