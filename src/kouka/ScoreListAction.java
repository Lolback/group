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

import bean.Student;
import bean.Subject;
import bean.Teacher;
import tool.Action;

public class ScoreListAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        // Teacher teacher = (Teacher)session.getAttribute("user");
        // 仮実装のため、教師情報をダミーで設定
        Teacher teacher = new Teacher();

        String entYearStr = ""; // 入学年度
        String classNum = ""; // クラス番号
        String subjectCode = ""; // 科目コード
        int entYear = 0; // 入学年度
        List<Student> students = null; // 学生リスト
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int resultCount = 0;

        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/kouka");
            conn = ds.getConnection();

            // リクエストパラメータの取得
            entYearStr = request.getParameter("academicYear");
            classNum = request.getParameter("class");
            subjectCode = request.getParameter("subject");

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
            }

            // クラス番号が指定されている場合、条件を追加
            if (classNum != null && !classNum.isEmpty()) {
                sql += " AND s.CLASS_NUM = ?";
            }

            // 科目コードが指定されている場合、条件を追加
            if (subjectCode != null && !subjectCode.isEmpty()) {
                sql += " AND sub.CD = ?";
            }

            pstmt = conn.prepareStatement(sql);

            // パラメータの設定
            pstmt.setString(1, teacher.getSchool().getCd());
            int paramIndex = 2;

            if (entYear != 0) {
                pstmt.setInt(paramIndex++, entYear);
            }
            if (classNum != null && !classNum.isEmpty()) {
                pstmt.setString(paramIndex++, classNum);
            }
            if (subjectCode != null && !subjectCode.isEmpty()) {
                pstmt.setString(paramIndex++, subjectCode);
            }

            rs = pstmt.executeQuery();

            List<Student> studentList = new ArrayList<>();

            while (rs.next()) {
                resultCount++;
                Student student = new Student();
                student.setEntYear(rs.getInt("ENT_YEAR"));
                student.setClassNum(rs.getString("CLASS_NUM"));
                student.setNo(rs.getString("NO"));
                student.setName(rs.getString("NAME"));

                Subject subject = new Subject();
                subject.setSubjectName(rs.getString("SUBJECT_NAME"));
                // subject.setCode(rs.getString("SUBJECT_CD")); // もし必要ならば追加

                // ここでPOINTをStudentオブジェクトに追加する必要がある場合は、student.setPoint(...)を実装する

                studentList.add(student);
            }

            // リクエストにデータをセット
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

        // JSPへフォワード
        request.getRequestDispatcher("score_view.jsp").forward(request, response);
    }
}
