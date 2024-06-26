package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.School;

public class SchoolDao extends Dao{

	private String baseSql = "select * from school where school_cd=?" ;

	public School get(String school_cd) throws Exception{
		//学生インスタンスを初期化
		School school = new School();
		//データベースへのコネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from school where school_cd=?");
			//プリペアードステートメントに学生番号をバインド
			statement.setString(1, school_cd);
			//プリペアードステートメントを実行
			ResultSet rSet =statement.executeQuery();

			if (rSet.next()) {
				//リザルトセットが存在する場合
				//学生インスタンスに検索結果をセット
				school.setCd(rSet.getString("school_cd"));
				school.setName(rSet.getString("school_name"));
			} else {
				//リザルトセットが存在しない場合
				//学生インスタンスにnullをセット
				school = null;
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
		return school;
	}
}
