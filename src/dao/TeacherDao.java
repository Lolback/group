package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Teacher;

public class TeacherDao extends Dao {

	private String baseSql = "select * from teacher where id=?" ;



	public Teacher get(String id) throws Exception{

		//教師インスタンスを初期化
		Teacher teacher = new Teacher();
		//データベースへのコネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from teacher where id=?");
			//プリペアードステートメントに教師番号をバインド
			statement.setString(1, id);
			//プリペアードステートメントを実行
			ResultSet rSet =statement.executeQuery();

			//学校Daoを初期化
			SchoolDao schoolDao = new SchoolDao();

			if (rSet.next()) {
				//リザルトセットが存在する場合
				//教師インスタンスに検索結果をセット
				teacher.setId(rSet.getString("id"));
				teacher.setPassword(rSet.getString("password"));
				teacher.setName(rSet.getString("name"));
				//学校フィールドには学校コードで検索した学校インスタンスをセット
				teacher.setSchool(schoolDao.get(rSet.getString("school_cd")));
			} else {
				//リザルトセットが存在しない場合
				//教師インスタンスにnullをセット
				teacher = null;
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
		return teacher;
	}



	public Teacher login(String id, String password) throws Exception{

		//教師インスタンスを初期化
		Teacher teacher = new Teacher();

		//値をもとに教師を取得
		teacher = get(id);


		//該当する教師が存在していた場合
		if (teacher != null) {
			//パスワードを比較
			if (password.equals(teacher.getPassword())) {
				return teacher;
			}
		}

		//いずれかの条件に合致しなかった場合
		teacher = null;

		return teacher;
	}
}
