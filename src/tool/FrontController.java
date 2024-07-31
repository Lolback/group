package tool;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;

/**
 * Servlet implementation class FrontController
 */

@WebServlet("*.action")
public class FrontController extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException {

        HttpSession session = req.getSession(); // セッション
        Teacher teacher = new Teacher();
        teacher = (Teacher) session.getAttribute("current_teacher");

		try{

			String path = "kouka/Login.action";

			if (teacher != null || req.getServletPath().substring(1).equals("kouka/LoginExecute.action")) {
				path = req.getServletPath().substring(1);
			}

			String name = path.replace(".a","A").replace('/', '.');

			System.out.println("★ servlet path ->" +req.getServletPath());
			System.out.println("★ class name ->" + name);

			Action action = (Action) Class.forName(name).getDeclaredConstructor().newInstance();

			action.execute(req, res);

		}catch(Exception e){
			e.printStackTrace();

			req.getRequestDispatcher("/error.jsp").forward(req, res);
		}
	}

	protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException {
		doGet(req,res);
	}

}