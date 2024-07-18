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
import bean.Subject;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListAction extends Action {

	//07.03中本晴輝
	//成績参照作成開始
	//07.08
	//一応完成、ただ機能の検証およびsql文の作成はできていない
	//07.18
	//全然わからん
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		HttpSession session = request.getSession();//セッション
		//Teacher teacher = (Teacher)session.getAttribute("user");
		Subject subject = new Subject();
		TestListSubject testsub = new TestListSubject();
		String name = testsub.getStudentName();
		System.out.print(name);

		//daoをそれぞれ実装
		ClassNumDao cNumDao = new ClassNumDao();//クラス番号Daoを初期化
		TestListSubjectDao tlsubDao = new TestListSubjectDao(); //成績Daoを初期化

		String entYearStr="2022";//選択された入学年度
		String classNum="A";//選択されたクラス番号
		String slSubjectName="kokugo";//選択された科目
		String slNum="1" ;//選択されたテストの回数

		int entYear = 0;//入学年度

		List<TestListSubject> tlsub = null;//成績リスト
		List<Student> students = null;//学生リスト
		LocalDate todaysDate = LocalDate.now();//LocalDateインスタンス
		int year = todaysDate.getYear();//現在の年を取得


		Map<String, String> errors = new HashMap<>();//エラーメッセージ
		//Schoolをインスタンス化
		//クラス番号取得のためなので変更の可能性あり
		School school = new School();


		//テスト用に全てのデータを仮で入れる

		//リクエストパラメータの取得 2
		//DBからデータ取得 3
		//ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		//List<String> list = cNumDao.filter(teacher.getSchool());

		//6/12現在はログイン機能を付けないため学生Daoから直接全てのクラス番号の一覧を取得
		List<ClassNum> list = cNumDao.filter(school);

		//TestListSubjectDaoからfilterメソッドで学校コード、入学年度、クラス番号、科目を指定

		if (entYear != 0 && !classNum.equals("0")) {
			//入学年度、クラス番号、科目、学校コードを指定する
			tlsub = tlsubDao.filter(entYear, classNum, subject, school);
		//ビジネスロジック 4
		if (entYearStr != null) {
			//数値に変換
			entYear = Integer.parseInt(entYearStr);
		}
		//リストを初期化
		List<Integer> entYearSet = new ArrayList<>();
		//10年前から1年後まで年をリストに追加
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}

		//レスポンス値をセット 6
		//リクエストに入学年度をセット
		request.setAttribute("f1", entYear);
		//リクエストにクラス番号をセット
		request.setAttribute("f2", classNum);

		//リクエストに学生リストをセット
		request.setAttribute("students", students);
		//リクエストにデータをセット
		request.setAttribute("class_num_set", list);
		request.setAttribute("ent_year_set", entYearSet);
		request.setAttribute("slSubject", slSubjectName);
		request.setAttribute("slNum", slNum);

		//JSPへフォワード
		request.getRequestDispatcher("score_view.jsp").forward(request, response);
	}
	}
}
