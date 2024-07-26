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
import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

public class ScoreManagementAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
       HttpSession session = request.getSession();

       Teacher teacher = new Teacher();

       String entYearStr = ""; // 入学年度
       String classNum = ""; // クラス番号
       String subjectCode = ""; // 科目コード
       String times = ""; // 回数
       String studentNumber = ""; // 学生番号
       int entYear = 0; //入学年度
       List<Student> students = null; // 学生リスト
       ClassNumDao cNumDao = new ClassNumDao(); // クラス番号Daoを初期化
       SubjectDao subjectDao = new SubjectDao(); // 科目Daoを初期化
       Connection conn = null;
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       boolean filterFlag = false;
       int resultCount = 0;
       teacher = (Teacher) session.getAttribute("current_teacher");
       try {
           Context initContext = new InitialContext();
           DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/kouka");
           conn = ds.getConnection();
           String sql = "SELECT s.ENT_YEAR, s.CLASS_NUM, s.NO, s.NAME, sub.NAME AS SUBJECT_NAME " +
                        "FROM STUDENT s " +
                        "JOIN SUBJECT sub ON sub.SCHOOL_CD = s.SCHOOL_CD " +
                        "WHERE s.SCHOOL_CD = ?";
           if (entYearStr != null && !entYearStr.isEmpty() && !entYearStr.equals("---")) {
               sql += " AND s.ENT_YEAR = ?";
           }
           if (classNum != null && !classNum.isEmpty() && !classNum.equals("---")) {
               sql += " AND s.CLASS_NUM = ?";
           }
           if (subjectCode != null && !subjectCode.isEmpty() && !subjectCode.equals("---")) {
               sql += " AND sub.CD = ?";
           }
           if (studentNumber != null && !studentNumber.isEmpty()) {
               sql += " AND s.NO = ?";
           }
           pstmt = conn.prepareStatement(sql);
           int paramIndex = 1;
           pstmt.setString(paramIndex++, "school_code"); // Replace with actual school code
           if (entYearStr != null && !entYearStr.isEmpty() && !entYearStr.equals("---")) {
               pstmt.setInt(paramIndex++, Integer.parseInt(entYearStr));
           }
           if (classNum != null && !classNum.isEmpty() && !classNum.equals("---")) {
               pstmt.setString(paramIndex++, classNum);
           }
           if (subjectCode != null && !subjectCode.isEmpty() && !subjectCode.equals("---")) {
               pstmt.setString(paramIndex++, subjectCode);
           }
           if (studentNumber != null && !studentNumber.isEmpty()) {
               pstmt.setString(paramIndex++, studentNumber);
           }
           rs = pstmt.executeQuery();
           List<Student> studentList = new ArrayList<>();

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
           List<Subject> subjectList = subjectDao.filter(teacher.getSchool());
           // リストを初期化
           List<String> subjectCdSet = new ArrayList<>();
           List<String> subjectNameSet = new ArrayList<>();
           // 科目をリストに追加
           for (int i = 0; i <= subjectList.size() - 1; i++) {
           	subjectCdSet.add(subjectList.get(i).getSubjectCode());
           	subjectNameSet.add(subjectList.get(i).getSubjectName());
           }

           // リクエストにデータをセット
           request.setAttribute("ent_year_set", entYearSet);
           request.setAttribute("class_num_set", classNumSet);
           request.setAttribute("subject_cd_set", subjectCdSet);
           request.setAttribute("subject_name_set", subjectNameSet);
           request.setAttribute("students", studentList);
           request.setAttribute("resultCount", resultCount);
           request.setAttribute("filterFlag", filterFlag);
       } catch (Exception e) {
           e.printStackTrace();
           request.setAttribute("errorMessage", "エラーが発生しました");
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
//       request.setAttribute("students".studentList):
       request.getRequestDispatcher("score_result.jsp").forward(request, response);
   }
}