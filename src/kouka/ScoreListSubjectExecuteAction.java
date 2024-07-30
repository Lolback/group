package kouka;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import bean.ClassNum;
import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestDao;
import dao.TestListSubjectDao;
import tool.Action;


public class ScoreListSubjectExecuteAction  extends Action{
	   @Override
	    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		   HttpSession session = request.getSession();


	        String entYearStr = request.getParameter("academicYear");
	        int entYear = Integer.parseInt(entYearStr);
	        String classNum = request.getParameter("class");
	        String subjectCode = request.getParameter("subject");
	        String times = ""; request.getParameter("no");
		    Teacher teacher = new Teacher();
		    teacher = (Teacher) session.getAttribute("current_teacher");
		    School school = new School();
		    school = teacher.getSchool();
		    SubjectDao subjectDao = new SubjectDao();
	        TestDao testDao = new TestDao();
		    Subject subject = subjectDao.get(request.getParameter("subject"), school);
	        boolean filterFlag = false;

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

	            // SQLの組み立て
	            String sql = "SELECT s.ENT_YEAR, s.CLASS_NUM, s.NO, s.NAME, sub.NAME AS SUBJECT_NAME, " +
	                         "COALESCE(t.POINT, 0) AS POINT " +
	                         "FROM STUDENT s " +
	                         "JOIN SUBJECT sub ON sub.SCHOOL_CD = s.SCHOOL_CD " +
	                         "LEFT JOIN TEST t ON s.NO = t.STUDENT_NO AND sub.CD = t.SUBJECT_CD " +
	                         "WHERE s.SCHOOL_CD = ?";

	            // 入学年度が指定されている場合、条件を追加
	            if (entYearStr != null && !entYearStr.isEmpty()) {
	                entYear = Integer.parseInt(entYearStr);
	                sql += " AND s.ENT_YEAR = ?";
	                filterFlag = true;
	            }

	            // クラス番号が指定されている場合、条件を追加
	            if (classNum != null && !classNum.isEmpty()) {
	                sql += " AND s.CLASS_NUM = ?";
	                filterFlag = true;
	            }

	            // 科目コードが指定されている場合、条件を追加
	            if (subjectCode != null && !subjectCode.isEmpty()) {
	                sql += " AND sub.CD = ?";
	                filterFlag = true;
	            }

	            // 回数が指定されている場合、条件を追加
	            if (times != null && !times.isEmpty()) {
	                sql += " AND t.No = ?";
	                filterFlag = true;
	            }

	            pstmt = conn.prepareStatement(sql);

	            // パラメータの設定
	            pstmt.setString(1, teacher.getSchool().getCd());
	            int paramIndex = 2;

	            if (entYear != 0) {
	                pstmt.setInt(paramIndex, entYear);
	                paramIndex++;
	            }
	            if (classNum != null && !classNum.isEmpty()) {
	                pstmt.setString(paramIndex, classNum);
	                paramIndex++;
	            }
	            if (subjectCode != null && !subjectCode.isEmpty()) {
	                pstmt.setString(paramIndex, subjectCode);
	                paramIndex++;
	            }
	            if (times != null && !times.isEmpty()) {
	                pstmt.setString(paramIndex, times);
	            }

	            rs = pstmt.executeQuery();

	            while (rs.next()) {
	                resultCount++;
	                Student student = new Student();
	                student.setEntYear(rs.getInt("ENT_YEAR"));
	                student.setClassNum(rs.getString("CLASS_NUM"));
	                student.setNo(rs.getString("NO"));
	                student.setName(rs.getString("NAME"));

	                subject.setSubjectName(rs.getString("SUBJECT_NAME"));

	                studentList.add(student);
	            }

	            //日付候補
	            // リストを初期化
	            List<Integer> entYearSet = new ArrayList<>();
	            LocalDate todaysDate = LocalDate.now(); // LocalDateインスタンス
	            int year = todaysDate.getYear(); // 現在の年を取得
	            // 10年前から10年後まで年をリストに追加
	            for (int i = year - 10; i <= year + 10; i++) {
	                entYearSet.add(i);
	            }

	            //クラス番号候補
	            // ログインユーザーの学校コードをもとにクラス番号の一覧を取得
	            List<ClassNum> classNumList = cNumDao.filter(teacher.getSchool());
	            // リストを初期化
	            List<Integer> classNumSet = new ArrayList<>();
	            // クラス番号をリストに追加
	            for (int i = 0; i <= classNumList.size() - 1; i++) {
	            	classNumSet.add(classNumList.get(i).getClassNum());
	            }

	            //科目候補
	            // ログインユーザーの学校コードをもとに科目の一覧を取得
	            List<Subject> subjectList = subjectDao.getAllSubjects(teacher.getSchool().getCd());
	            // リストを初期化
	            List<String> subjectCdSet = new ArrayList<>();
	            List<String> subjectNameSet = new ArrayList<>();
	            // 科目をリストに追加
	            for (int i = 0; i <= subjectList.size() - 1; i++) {
	            	subjectCdSet.add(subjectList.get(i).getSubjectCode());
	            	subjectNameSet.add(subjectList.get(i).getSubjectName());
	            }

	            if (filterFlag == true) {
	                testList = tlsubdao.filter(entYear, classNum, subjectDao.get(subjectCode, school), school);
	            }

	            // リクエストにデータをセット
	            request.setAttribute("ent_year_set", entYearSet);
	            request.setAttribute("class_num_set", classNumSet);
	            request.setAttribute("subject_cd_set", subjectCdSet);
	            request.setAttribute("subject_name_set", subjectNameSet);
	            request.setAttribute("students", studentList);
	            request.setAttribute("tests", testList);
	            request.setAttribute("resultCount", resultCount);
	            request.setAttribute("filterFlag", filterFlag);
	            request.setAttribute("academicYear", entYearStr);
	            request.setAttribute("class", classNum);
	            request.setAttribute("subject", subjectCode);
	            request.setAttribute("times", times);


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


	        // JSPへフォワード
	        request.getRequestDispatcher("student_score.jsp").forward(request, response);
	    }
}

