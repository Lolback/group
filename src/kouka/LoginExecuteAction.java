package kouka;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class LoginExecuteAction extends Action {
	public void execute(
			HttpServletRequest request, HttpServletResponse response
		) throws Exception {

			HttpSession session=request.getSession();

			String id="";
			String password="";
			TeacherDao dao=new TeacherDao();

			id = request.getParameter("id");
			password = request.getParameter("password");

			Teacher current_teacher = dao.login(id,  password);

			if (current_teacher!=null) {
				session.setAttribute("current_teacher", current_teacher);
				request.getRequestDispatcher("login_success.jsp").forward(request, response);
				return;
			}

			request.getRequestDispatcher("login_error.jsp").forward(request, response);
	}

}
