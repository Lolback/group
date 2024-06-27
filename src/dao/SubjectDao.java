package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {

    public Subject getBySubjectCode(String subjectCode) throws Exception {
        Subject subject = null;

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM SUBJECT WHERE CD = ?");
        statement.setString(1, subjectCode);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            subject = new Subject();
            subject.setSubjectCode(resultSet.getString("CD"));
            subject.setSchoolCode(resultSet.getString("SCHOOL_CD"));
            subject.setSubjectName(resultSet.getString("NAME"));
        }

        statement.close();
        connection.close();

        return subject;
    }

    public boolean delete(String subjectCode) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM SUBJECT WHERE CD = ?");
        statement.setString(1, subjectCode);
        int rowsAffected = statement.executeUpdate();

        statement.close();
        connection.close();

        return rowsAffected > 0;
    }

    // その他の必要なメソッド...
    //6/27 中本晴輝 filter,saveメソッドを仮作成、ちょっとよくわかんねえ
    //filterメソッド
    public List<Subject> filter(School school) throws Exception{
		//リストを初期化
		List<Subject> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = connection.prepareStatement("SELECT * FROM SUBJECT WHERE SCHOOL_CD = ?");
		//リザルトセット
		ResultSet rSet = null;
		//SQL文の条件
		String order = "order by cd asc";

	try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(statement  + order);
			//プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			//プリペアードステートメントを実行
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

    //saveメソッドを作成
    //6/27 中本晴輝
	public boolean save(Subject subject) throws Exception{
		//コネクションを確立
		Connection connection = getConnection();

		//プリペアードステートメント
		PreparedStatement statement = null;
		//実行件数

		int count = 0;

		try {
			//データベースから科目を取得
			Subject old = getBySubjectCode(subject.getSubjectCode());
			if (old == null) {
				//科目が存在しなかった場合
				//プリペアードステートメントにINSERT文をセット
				statement = connection.prepareStatement(
						"insert into subject(school_cd, cd, name) values(?, ?, ?)");
				//プリペアードステートメントに値をバインド
				statement.setString(1, "1");
				statement.setString(2, subject.getSubjectCode());
				statement.setString(3, subject.getSubjectName()); //仮
			} else {
				//科目が存在した場合
				//プリペアードステートメントにUPDATE文をセット
				statement = connection
						.prepareStatement("update subject set  cd=?, name=? where school_cd=?");
				//プリペアードステートメントに値をバインド
				statement.setString(1, subject.getSubjectCode());
				statement.setString(2, subject.getSubjectName());
				statement.setString(3, subject.getSchoolCode());
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
}
