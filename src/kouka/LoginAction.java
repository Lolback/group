package kouka;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class LoginAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(); // セッション

        // JSPへフォワード
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
