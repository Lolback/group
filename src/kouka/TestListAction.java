package kouka;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ClassNum;
import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class TestListAction extends Action {

	//07.03中本晴輝
	//成績参照作成開始
	//07.08
	//一応完成、ただ機能の検証およびsql文の作成はできていない
	//07.18
	//全然わからん
	//7.22
	//executeにDBから取り出した入学年度、クラス、科目のデータをscore_viewへ送信する機能を作成する
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		//Teacher teacher = (Teacher)session.getAttribute("user");
        Teacher teacher = new Teacher();
		Subject subject = new Subject();


		//daoをそれぞれ実装
		ClassNumDao cNumDao = new ClassNumDao();//クラス番号Daoを初期化

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

		//入学年度リストを初期化
		List<Integer> entYearSet = new ArrayList<>();
		//10年前から1年後まで年をリストに追加
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}

		//レスポンス値をセット 6
		//リクエストに入学年度をセット
		request.setAttribute("f1", entYearSet);
		//リクエストにクラス番号をセット
		request.setAttribute("f2", list);
		//リクエストに科目データをセット



		//JSPへフォワード
		request.getRequestDispatcher("score_view.jsp").forward(request, response);
	}
}
