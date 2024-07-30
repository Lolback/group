package kouka;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListStudentAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();

        Teacher teacher = new Teacher();

        String entYearStr = ""; // 入学年度
        String classNum = ""; // クラス番号
        String subjectCode = ""; // 科目コード
        String times = ""; // 回数
        int entYear = 0; // 入学年度
        StudentDao studentDao = new StudentDao();
        ClassNumDao cNumDao = new ClassNumDao(); // クラス番号Daoを初期化
        SubjectDao subjectDao = new SubjectDao(); // 科目Daoを初期化
        TestListSubjectDao testDao = new TestListSubjectDao(); // テストDaoを初期化
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean filterFlag = true;
        int resultCount = 0;

        teacher = (Teacher) session.getAttribute("current_teacher");
        School school = teacher.getSchool();

        try {
            // リクエストパラメータの取得
            String student_no = request.getParameter("student_no");

            List<Student> studentList = new ArrayList<>();

            Student student = studentDao.get(student_no, school);
            studentList.add(student);

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

            List<TestListSubject> testList = new ArrayList<>();
            testList = testDao.filter(student, school);

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
