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

    private String baseSql = "SELECT result1.ENT_YEAR, result1.CLASS_NUM, result1.NO, result1.NAME, result1.SUBJECT_NAME, NO1, POINT1, NO2, POINT2 FROM (SELECT s.ENT_YEAR, s.CLASS_NUM, s.NO, s.NAME, sub.NAME AS SUBJECT_NAME, t.NO AS NO1, COALESCE(t.POINT, 0) AS POINT1 FROM STUDENT s JOIN SUBJECT sub ON sub.SCHOOL_CD = s.SCHOOL_CD LEFT JOIN TEST t ON s.NO = t.STUDENT_NO AND sub.CD = t.SUBJECT_CD WHERE s.SCHOOL_CD = ? AND s.ENT_YEAR = ? AND s.CLASS_NUM = ? AND sub.CD = ? AND t.NO = 1) as result1 JOIN (SELECT s.ENT_YEAR, s.CLASS_NUM, s.NO, s.NAME, sub.NAME AS SUBJECT_NAME, t.NO AS NO2, COALESCE(t.POINT, 0) AS POINT2 FROM STUDENT s JOIN SUBJECT sub ON sub.SCHOOL_CD = s.SCHOOL_CD LEFT JOIN TEST t ON s.NO = t.STUDENT_NO AND sub.CD = t.SUBJECT_CD WHERE s.SCHOOL_CD = ? AND s.ENT_YEAR = ? AND s.CLASS_NUM = ? AND sub.CD = ? AND t.NO = 2) as result2 ON result1.NO = result2.NO";

	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
		//リストを初期化
		List<TestListSubject> list = new ArrayList<>();
			try{
				//リザルトセットを全権走査
				while (rSet.next()) {
					//成績インスタンスを初期化
					TestListSubject tlsub = new TestListSubject();
					//成績インスタンスに検索結果をセット
					tlsub.setStudentNo(rSet.getString("no"));
					tlsub.setStudentName(rSet.getString("name"));
					tlsub.setEntYear(rSet.getInt("ent_year"));
					tlsub.setClassNum(rSet.getString("class_num"));
					System.out.println("student");
					System.out.println(rSet.getInt("no1"));
					System.out.println(rSet.getInt("point1"));
					System.out.println(rSet.getInt("no2"));
					System.out.println(rSet.getInt("point2"));
					tlsub.putPoint(rSet.getInt("no1"), rSet.getInt("point1"));
					tlsub.putPoint(rSet.getInt("no2"), rSet.getInt("point2"));
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
		//SQL文のソート
		String order = " order by result1.no asc";


		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + order);
			//ステートメントにデータをバインド
			statement.setString(1, school.getCd());
			statement.setInt(2, entYear);
			statement.setString(3, classNum);
			statement.setString(4, subject.getSubjectCode());
			statement.setString(5, school.getCd());
			statement.setInt(6, entYear);
			statement.setString(7, classNum);
			statement.setString(8, subject.getSubjectCode());
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

	public List<TestListSubject> filter(, School school) throws Exception{

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
		//SQL文のソート
		String order = " order by result1.no asc";


		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + order);
			//ステートメントにデータをバインド
			statement.setString(1, school.getCd());
			statement.setInt(2, entYear);
			statement.setString(3, classNum);
			statement.setString(4, subject.getSubjectCode());
			statement.setString(5, school.getCd());
			statement.setInt(6, entYear);
			statement.setString(7, classNum);
			statement.setString(8, subject.getSubjectCode());
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
