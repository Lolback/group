package kouka;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;


public class ScoreListSubjectExecuteAction  extends Action{
	   @Override
	    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		   HttpSession session = request.getSession();


	        String entYearStr = request.getParameter("academicYear");
	        int entYear = Integer.parseInt(entYearStr);
	        String classNum = request.getParameter("class");
		    Teacher teacher = new Teacher();
		    teacher = (Teacher) session.getAttribute("current_teacher");
		    School school = new School();
		    school = teacher.getSchool();
		    SubjectDao subjectDao = new SubjectDao();
		    Subject subject = subjectDao.get(request.getParameter("subject"), school);

	        int resultCount = 0;

	        entYear = Integer.parseInt(entYearStr);

	        ClassNumDao cNumDao = new ClassNumDao(); // クラス番号Daoを初期化
	        TestListSubjectDao tlsubdao = new TestListSubjectDao(); //成績参照daoを初期化

	        List<TestListSubject> testList = new ArrayList<>();
	        testList = tlsubdao.filter(entYear, classNum, subject, school);

	        //いったん持ってくる
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;

            List<Student> studentList = new ArrayList<>();
	        try {
	            Context initContext = new InitialContext();
	            DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/kouka");
	            conn = ds.getConnection();

	            // リクエストパラメータの取得

	 		   /*
	 		    * 検索に必要な項目
	 		    * 入学年度
	 		    * クラス番号
	 		    * 学生番号
	 		    * 学生名
	 		    * 第一回点数
	 		    * 第二回点数
	 		    * */


	        } catch (Exception e) {
	            e.printStackTrace();
	            // エラー処理を追加する場合はここに記述
	        } finally {
	            if (rs != null) {
	                try {
	                    rs.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	            if (pstmt != null) {
	                try {
	                    pstmt.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	            if (conn != null) {
	                try {
	                    conn.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	        }

	        //データ検索の処理
	        //後々エラー文も追記
	        request.getRequestDispatcher("score_result.jsp");
	    }
}

