package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

public class ClassNumDao extends Dao{

	private String baseSql = "SELECT CLASS_NUM FROM CLASS_NUM where SCHOOL_CD=?" ;
	/*
	 * 必要な機能
	 * filterメソッドを作成し学校コードをもとにクラス番号を取得する
	 *中身は今後変更の可能性あり
	 */

	public List<ClassNum> filter(School school) throws Exception{

		//リストを初期化
		List<ClassNum> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文の条件
		String order = "order by class_num asc";

		ClassNum classnum =new ClassNum();

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql+ order);
			//プリペアードステートメントに学校コードをバインド
			statement.setInt(1, classnum.getSchoolCd());
			//プリペアードステートメントを実行
			rSet = statement.executeQuery();
			//リストへの格納処理を実行
			while (rSet.next()) {
				classnum.setClassNum(rSet.getInt("class_num"));
				list.add(classnum);
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
		if (statement != null) {
			try {
				connection.close();
			} catch (SQLException sqle) {
				throw sqle;
			}
		}
		return list;
	}

}