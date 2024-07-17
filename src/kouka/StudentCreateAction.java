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

import tool.Action;

public class StudentCreateAction extends Action {


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		HttpSession session = request.getSession();//セッション

		Map<String, String> errors = new HashMap<>();//エラーメッセージ

		List<Integer> entYearCandidates = new ArrayList<Integer>();

		LocalDateTime nowDate = LocalDateTime.now();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy");
		String nowDateString = dateFormatter.format(nowDate);
		int nowDateInt = Integer.parseInt(nowDateString);

		for(int i = nowDateInt - 10; i < nowDateInt + 10; i++){
			entYearCandidates.add(i);
		}

		request.setAttribute("ent_year_candidates", entYearCandidates);

		//JSPへフォワード
		request.getRequestDispatcher("student_add.jsp").forward(request, response);
	}

}
