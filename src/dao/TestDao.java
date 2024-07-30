package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {

	private String baseSql = "select * from test join student on student.no = test.student_no where" ;



	public Test get(Student student, Subject subject, School school, int no) throws Exception{

		//データベースへのコネクションを確立
		Connection connection = getConnection();
		//テストインスタンスを初期化
		Test test = new Test();
		//プリペアードステートメント
		PreparedStatement statement = null;

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from test where student_no=? and subject_cd=? and school_cd=? and no=?");
			//プリペアードステートメントにデータをバインド
			statement.setString(1, student.getNo());
			statement.setString(2, subject.getSubjectCode());
			statement.setString(3, school.getCd());
			statement.setInt(4, no);
			//プリペアードステートメントを実行
			ResultSet rSet =statement.executeQuery();

			//Daoを初期化
			SchoolDao schoolDao = new SchoolDao();
			StudentDao studentDao = new StudentDao();
			SubjectDao subjectDao = new SubjectDao();

			if (rSet.next()) {
				//リザルトセットが存在する場合
				//テストインスタンスに検索結果をセット
				test.setClassNum(rSet.getString("class_num"));
				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
				test.setPoint(rSet.getInt("point"));
				test.setSchool(schoolDao.get(rSet.getString("school_cd")));
				test.setStudent(studentDao.get(rSet.getString("student_no"), school));
				test.setSubject(subjectDao.get(rSet.getString("subject_cd"), schoolDao.get(rSet.getString("school_cd"))));
			} else {
				//リザルトセットが存在しない場合
				//テストインスタンスにnullをセット
				test = null;
			}
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
		}
			//コネクションを閉じる
			if (connection != null) {
				try{
				connection.close();
			} catch (SQLException sqle) {
				throw sqle;
			}
		}
		return test;
	}



	public List<Test> getAll(School school) throws Exception{
		//リストを初期化
		List<Test> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文の条件
		String condition = " test.school_cd=?";
		//SQL文のソート
		String order = " order by no asc";

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + order);
			//プリペアードステートメントにデータをバインド
			statement.setString(1, school.getCd());
			//プライベートステートメントを実行
			rSet = statement.executeQuery();
			//リストへの格納処理を実行
			list = postFilter(rSet, school);
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



	private List<Test> postFilter(ResultSet rSet, School school) throws Exception {
		//リストを初期化
		List<Test> list = new ArrayList<>();


		//Daoを初期化
		SchoolDao schoolDao = new SchoolDao();
		StudentDao studentDao = new StudentDao();
		SubjectDao subjectDao = new SubjectDao();

			try{
				//リザルトセットを全権走査
				while (rSet.next()) {
					//テストインスタンスを初期化
					Test test = new Test();
					//テストインスタンスに検索結果をセット
					test.setClassNum(rSet.getString("class_num"));
					test.setNo(rSet.getInt("no"));
					test.setPoint(rSet.getInt("point"));
					test.setPoint(rSet.getInt("point"));
					test.setSchool(school);
					test.setStudent(studentDao.get(rSet.getString("student_no"), school));
					test.setSubject(subjectDao.get(rSet.getString("subject_cd"), school));
					//リストに追加
					list.add(test);
				}
			} catch (SQLException | NullPointerException e) {
				e.printStackTrace();
			}
			return list;
	}



	public List<Test> filter(School school, int entYear, String classNum, Subject subject, int num) throws Exception{
		//リストを初期化
		List<Test> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文の条件
		String condition = " test.school_cd=? and test.class_num=? and student.ent_year=? and test.subject_cd=? and test.no=?";
		//SQL文のソート
		String order = " order by no asc";

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + order);
			//プリペアードステートメントにデータをバインド
			statement.setString(1, school.getCd());
			statement.setString(2, classNum);
			statement.setInt(3, entYear);
			statement.setString(4, subject.getSubjectCode());
			statement.setInt(5, num);
			//プライベートステートメントを実行
			rSet = statement.executeQuery();
			//リストへの格納処理を実行
			list = postFilter(rSet, school);
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


	public boolean save(List<Test> list) throws Exception{
		//コネクションを確立
		Connection connection = getConnection();

		//プリペアードステートメント
		PreparedStatement statement = null;
		//実行件数

		int count = 0;

		try {

			for (int i = 0; i < list.size(); i++) {

				Test currentTest = list.get(i);
				//保存
				save(currentTest, connection);
			}

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
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		if (count > 0) {
			//実行件数が1件以上ある場合
			return true;
		} else {
			//実行件数が0件の場合
			return false;
		}
	}

	public boolean save(Test test, Connection connection) throws Exception{

		//プリペアードステートメント
		PreparedStatement statement = null;
		//実行件数

		int count = 0;

		try {

			//データベースからテストを取得
			Test old = get(test.getStudent(), test.getSubject(), test.getSchool(), test.getNo());
			if (old == null) {
				//テストが存在しなかった場合
				//プリペアードステートメントにINSERT文をセット
				statement = connection.prepareStatement(
						"insert into test(student_no, subject_cd, school_cd, no, point, class_num) values(?, ?, ?, ?, ?, ?)");
				//プリペアードステートメントに値をバインド
				statement.setString(1, test.getStudent().getNo());
				statement.setString(2, test.getSubject().getSubjectCode());
				statement.setString(3, test.getSchool().getCd());
				statement.setInt(4, test.getNo());
				statement.setInt(5, test.getPoint());
				statement.setString(6, test.getClassNum());
			} else {
				//テストが存在した場合
				//プリペアードステートメントにUPDATE文をセット
				statement = connection
						.prepareStatement("update test set point=? where student_no=? and subject_cd=? and no=? and class_num=?");
				//プリペアードステートメントに値をバインド
				statement.setInt(1, test.getPoint());
				statement.setString(2, test.getStudent().getNo());
				statement.setString(3, test.getSubject().getSubjectCode());
				statement.setInt(4, test.getNo());
				statement.setString(5, test.getClassNum());
			}

			//プリペアードステートメントを実行
			count = statement.executeUpdate();

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
		}

		if (count > 0) {
			//実行件数が1件以上ある場合
			return true;
		} else {
			//実行件数が0件の場合
			return false;
		}
	}

	public boolean delete(List<Test> list) throws Exception{
		//コネクションを確立
		Connection connection = getConnection();

		//プリペアードステートメント
		PreparedStatement statement = null;
		//実行件数

		int count = 0;

		try {

			for (int i = 0; i < list.size(); i++) {

				Test currentTest = list.get(i);
				//削除
				delete(currentTest, connection);
			}

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
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		if (count > 0) {
			//実行件数が1件以上ある場合
			return true;
		} else {
			//実行件数が0件の場合
			return false;
		}
	}

	public void delete(Test test, Connection connection) throws Exception{

		//プリペアードステートメント
		PreparedStatement statement = null;

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("delete from test where student_no=?, subject_cd=?, no=?, class_num=?");
			//プリペアードステートメントにデータをバインド
			statement.setString(1, test.getStudent().getNo());
			statement.setString(2, test.getSubject().getSubjectCode());
			statement.setInt(3, test.getNo());
			statement.setString(4, test.getClassNum());
			//プリペアードステートメントを実行
			int rSet =statement.executeUpdate();

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
		}
	}
}
