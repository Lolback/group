package kouka;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class LogoutAction extends Action {
	public void execute(
			HttpServletRequest request, HttpServletResponse response
		) throws Exception {

			HttpSession session=request.getSession();



			if (session.getAttribute("current_teacher")!=null) {
				session.removeAttribute("current_teacher");
				request.getRequestDispatcher("logout-out.jsp").forward(request, response);
				return;
			}

			request.getRequestDispatcher("logout-error.jsp").forward(request, response);
	}

}