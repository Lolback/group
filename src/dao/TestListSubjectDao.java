package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao{
	private String baseSql = "select * from test where school_cd=?" ;



	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
		//リストを初期化
		List<TestListSubject> list = new ArrayList<>();
			try{
				//リザルトセットを全権走査
				while (rSet.next()) {
					//成績インスタンスを初期化
					TestListSubject tlsub = new TestListSubject();
					//成績インスタンスに検索結果をセット
					tlsub.setStudentNo(rSet.getString("studentNo"));
					tlsub.setStudentName(rSet.getString("studentName"));
					tlsub.setEntYear(rSet.getInt("ent_year"));
					tlsub.setClassNum(rSet.getString("class_num"));
					tlsub.putPoint(rSet.getInt("key"), rSet.getInt("value"));
					//リストに追加
					list.add(tlsub);
				}
			} catch (SQLException | NullPointerException e) {
				e.printStackTrace();
			}
			return list;
	}


	//07.03中本晴輝
	//恐らくfilterメソッド完成
	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception{

		//入学年度、クラス番号、科目を指定してデータを取り出す
		//リストを初期化
		List<TestListSubject> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文の条件
		String condition = "and ent_year=? and class_num=? and subject_cd=?";
		//SQL文のソート
		String order = "order by no asc";


		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + order);
			//ステートメントに入学年度をバインド
			statement.setInt(1, entYear);
			//プリペアードステートメントに学校コードをバインド
			statement.setString(2, school.getCd());
			//プリペアードステートメントにクラス番号をバインド
			statement.setString(3, classNum);
			//プリペアードステートメントに科目をバインド
			statement.setString(4, subject.getSubjectName());
			//プライベートステートメントを実行
			rSet = statement.executeQuery();
			//リストへの格納処理を実行
			list = postFilter(rSet);
		} catch (Exception e) {
			throw e;
		} finally {
			//プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			//コネクションを閉じる
			if (connection !=null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return list;
	}
	}
