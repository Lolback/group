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

import bean.ClassNum;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import tool.Action;


public class ScoreListSubjectExecuteAction  extends Action{
	   @Override
	    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {


	        String entYearStr = request.getParameter("academicYear");
	        String classNum = request.getParameter("class");
	        String subjectCode = request.getParameter("subject");
	        int resultCount = 0;
	        System.out.println(entYearStr);
	        System.out.println(classNum);
	        System.out.println(subjectCode);


	        //いったん持ってくる
		   HttpSession session = request.getSession();
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        Teacher teacher = new Teacher();
	        teacher = (Teacher) session.getAttribute("current_teacher");
	        int entYear = 0; // 入学年度
	        ClassNumDao cNumDao = new ClassNumDao(); // クラス番号Daoを初期化
            List<TestListSubject> testList = new ArrayList<>();
            List<Student> studentList = new ArrayList<>();
	        try {
	            Context initContext = new InitialContext();
	            DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/kouka");
	            conn = ds.getConnection();
	            entYear = Integer.parseInt(entYearStr);

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

	            // SQLの組み立て
	            String sql = "SELECT s.ENT_YEAR, s.CLASS_NUM, s.NO, s.NAME, sub.NAME AS SUBJECT_NAME, " +
	                         "COALESCE(t.POINT, 0) AS POINT, t.NO as TESTNO " +
	                         "FROM STUDENT s " +
	                         "JOIN SUBJECT sub ON sub.SCHOOL_CD = s.SCHOOL_CD " +
	                         "LEFT JOIN TEST t ON s.NO = t.STUDENT_NO AND sub.CD = t.SUBJECT_CD " +
	                         "WHERE s.SCHOOL_CD = ? AND s.ENT_YEAR = ? AND s.CLASS_NUM = ? AND sub.CD = ?";


	            pstmt = conn.prepareStatement(sql);

	            // パラメータの設定
	            pstmt.setString(1, teacher.getSchool().getCd());
	            pstmt.setInt(2, entYear);
	            pstmt.setString(3, classNum);
	            pstmt.setString(4, subjectCode);

	            rs = pstmt.executeQuery();


	            while (rs.next()) {
	                resultCount++;
	                Student student = new Student();
	                TestListSubject tlsub = new TestListSubject();
	                //tlsub.setEntYear(rs.getInt("ENT_YEAR"));
	                //tlsub.setClassNum(rs.getString("CLASS_NUM"));
	                //tlsub.setNo(rs.getString("NO"));
	                //tlsub.setName(rs.getString("NAME"));
	                //tlsub.setTestNo(rs.getInt("TESTNO"));
	                student.setEntYear(rs.getInt("ENT_YEAR"));
	                student.setClassNum(rs.getString("CLASS_NUM"));
	                student.setNo(rs.getString("NO"));
	                student.setName(rs.getString("NAME"));
	                //student.setTestNo(rs.getInt("TESTNO"));



	                Subject subject = new Subject();
	                subject.setSubjectName(rs.getString("SUBJECT_NAME"));
	                // subject.setCode(rs.getString("SUBJECT_CD")); // もし必要ならば追加

	                // ここでPOINTをStudentオブジェクトに追加する必要がある場合は、student.setPoint(...)を実装する

	                studentList.add(student);

	            }
	            String stuNameList = rs.getString("NAME");
	            //クラス番号候補
	            // ログインユーザーの学校コードをもとにクラス番号の一覧を取得
	            List<ClassNum> classNumList = cNumDao.filter(teacher.getSchool());

	            // リストを初期化
	            List<Integer> classNumSet = new ArrayList<>();
	            // クラス番号をリストに追加
	            for (int i = 0; i <= classNumList.size() - 1; i++) {
	            	classNumSet.add(classNumList.get(i).getClassNum());
	            }
	            System.out.println(testList);
	            System.out.println(stuNameList);
	            System.out.println(classNumSet);
	            System.out.println(studentList);
	            request.setAttribute("ent_year_set", entYear);
	            request.setAttribute("class_num_set", classNumSet);
	            request.setAttribute("students", studentList);
	            request.setAttribute("resultCount", resultCount);

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

