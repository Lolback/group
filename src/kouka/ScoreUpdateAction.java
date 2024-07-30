package kouka;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class ScoreUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(); // セッション
        TestDao testDao = new TestDao();
        StudentDao studentDao = new StudentDao();
        SubjectDao subjectDao = new SubjectDao();

        Teacher teacher = new Teacher();
        teacher = (Teacher) session.getAttribute("current_teacher");
        School school = teacher.getSchool();

        List<Test> testList = new ArrayList<>();

        String[] pointList = request.getParameterValues("point");
        String[] studentNoList = request.getParameterValues("student_no");
        String[] subjectCdList = request.getParameterValues("subject_cd");
        String[] noList = request.getParameterValues("num");
        Integer resultCount = Integer.parseInt(request.getParameter("result_count"));

        for (int i = 0; i < resultCount; i++) {
        	Test currentTest = testDao.get(studentDao.get(studentNoList[i], school), subjectDao.get(subjectCdList[i], school), school, Integer.parseInt(noList[i]));
    		currentTest.setPoint(Integer.parseInt(pointList[i]));
    		System.out.println(pointList[i]);
        	testList.add(currentTest);
        }

        //更新
        testDao.save(testList);
        // JSPへフォワード
        request.getRequestDispatcher("score_update-out.jsp").forward(request, response);
    }
}
