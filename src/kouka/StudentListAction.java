package kouka;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentListAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(); // セッション
        Teacher teacher = new Teacher();
        teacher = (Teacher) session.getAttribute("current_teacher");

        String entYearStr = ""; // 入力された入学年度
        String classNum = ""; // 入力されたクラス番号
        String isAttendStr = ""; // 入力された在学フラグ
        String filterFlagStr = ""; // 入力された検索フラグ
        boolean filterFlag = false; // 検索フラグ
        int entYear = 0; // 入学年度
        boolean isAttend = false; // 在学フラグ
        List<Student> students = null; // 学生リスト
        LocalDate todaysDate = LocalDate.now(); // LocalDateインスタンス
        int year = todaysDate.getYear(); // 現在の年を取得
        StudentDao sDao = new StudentDao(); // 学生Dao
        ClassNumDao cNumDao = new ClassNumDao(); // クラス番号Daoを初期化
        Map<String, String> errors = new HashMap<>(); // エラーメッセージ
        // Schoolをインスタンス化
        // クラス番号取得のためなので変更の可能性あり
        School school = new School();

        // リクエストパラメータの取得 2
        entYearStr = request.getParameter("f1");
        classNum = request.getParameter("f2");
        isAttendStr = request.getParameter("f3");

        // リクエストパラメータの検証と変換
        if (entYearStr != null && !entYearStr.isEmpty()) {
            entYear = Integer.parseInt(entYearStr);
        	filterFlag = true;
        }
        if (isAttendStr != null && isAttendStr.equals("t")) {
            isAttend = true;
        	filterFlag = true;
        }
        if (classNum != null && entYearStr.isEmpty()) {
            errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
            request.setAttribute("errors", errors);
            // 全学生情報を取得
            students = sDao.filter(teacher.getSchool(), isAttend);
        }


        // ログインユーザーの学校コードをもとにクラス番号の一覧を取得
        List<ClassNum> classNumList = cNumDao.filter(teacher.getSchool());

        if (filterFlag == false) {
        	students = sDao.getAll(teacher.getSchool());
        } else {
            if (entYear != 0 && !classNum.equals("")) {
                // 入学年度とクラス番号を指定
                students = sDao.filter(teacher.getSchool(), entYear, classNum, isAttend);
            } else if (entYear != 0 && classNum.equals("")) {
                // 入学年度のみ指定
                students = sDao.filter(teacher.getSchool(), entYear, isAttend);
            } else if (entYear == 0 && (classNum == null || classNum.equals("0"))) {
                // 指定なしの場合
                // 全学生情報を取得
                students = sDao.filter(teacher.getSchool(), isAttend);
            } else {
                errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
                request.setAttribute("errors", errors);
                // 全学生情報を取得
                students = sDao.filter(teacher.getSchool(), isAttend);
            }
        }

        //日付候補
        // リストを初期化
        List<Integer> entYearSet = new ArrayList<>();
        // 10年前から1年後まで年をリストに追加
        for (int i = year - 10; i <= year + 10; i++) {
            entYearSet.add(i);
        }

        //クラス番号候補
        // リストを初期化
        List<Integer> classNumSet = new ArrayList<>();
        // クラス番号をリストに追加
        for (int i = 0; i <= classNumList.size() - 1; i++) {
        	classNumSet.add(classNumList.get(i).getClassNum());
        }

        // レスポンス値をセット 6
        // リクエストに入学年度をセット
        request.setAttribute("f1", entYear);
        // リクエストにクラス番号をセット
        request.setAttribute("f2", classNum);
        // 在学フラグが送信されていた場合
        request.setAttribute("f3", isAttendStr);
        // リクエストに学生リストをセット
        request.setAttribute("students", students);
        // リクエストにデータをセット
        request.setAttribute("class_num_set", classNumSet);
        request.setAttribute("ent_year_set", entYearSet);

        // JSPへフォワード
        request.getRequestDispatcher("student_list.jsp").forward(request, response);
    }
}
