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

	public Subject get(String cd, School school) throws Exception{

		//科目インスタンスを初期化
		Subject subject = new Subject();
		//データベースへのコネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from subject where school_cd=? and cd=?");
			//プリペアードステートメントにデータをバインド
			statement.setString(1, school.getCd());
			statement.setString(2, cd);
			//プリペアードステートメントを実行
			ResultSet rSet =statement.executeQuery();

			if (rSet.next()) {
				//リザルトセットが存在する場合
				//科目インスタンスに検索結果をセット
				subject.setSchoolCode(rSet.getString("school_cd"));
				subject.setSubjectCode(rSet.getString("cd"));
				subject.setSubjectName(rSet.getString("name"));
			} else {
				//リザルトセットが存在しない場合
				//学生インスタンスにnullをセット
				subject = null;
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
		return subject;
	}

    public List<Subject> filter(School School) throws SQLException {
        Subject subject = null;
        List<Subject> list = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM SUBJECT WHERE SCHOOL_CD = ?");
            statement.setString(1, School.getCd());
            resultSet = statement.executeQuery();

			//リストへの格納処理を実行
			list = postFilter(resultSet);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

	private List<Subject> postFilter(ResultSet rSet) throws Exception {
		//リストを初期化
		List<Subject> list = new ArrayList<>();
			try{
				//リザルトセットを全権走査
				while (rSet.next()) {
					//学生インスタンスを初期化
					Subject subject = new Subject();
					//学生インスタンスに検索結果をセット
					subject.setSchoolCode(rSet.getString("SCHOOL_CD"));
					subject.setSubjectName(rSet.getString("NAME"));
					subject.setSubjectCode(rSet.getString("CD"));
					//リストに追加
					list.add(subject);
				}
			} catch (SQLException | NullPointerException e) {
				e.printStackTrace();
			}
			return list;
	}

    public Subject getBySubjectCode(String subjectCode) throws SQLException {
        Subject subject = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM SUBJECT WHERE CD = ?");
            statement.setString(1, subjectCode);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                subject = new Subject();
                subject.setSubjectCode(resultSet.getString("CD"));
                subject.setSchoolCode(resultSet.getString("SCHOOL_CD"));
                subject.setSubjectName(resultSet.getString("NAME"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return subject;
    }

    public boolean delete(String subjectCode, String schoolCode) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;

        try {
            connection = getConnection();
            statement = connection.prepareStatement("DELETE FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?");
            statement.setString(1, subjectCode);
            statement.setString(2, schoolCode);
            int rowsAffected = statement.executeUpdate();
            success = rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            connection = getConnection();
            statement = connection.prepareStatement("DELETE FROM TEST WHERE SUBJECT_CD = ? AND SCHOOL_CD = ?");
            statement.setString(1, subjectCode);
            statement.setString(2, schoolCode);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return success;
    }

    public List<Subject> getAllSubjects(String schoolCode) throws SQLException {
        List<Subject> subjects = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM SUBJECT WHERE SCHOOL_CD = ?");
            statement.setString(1, schoolCode);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Subject subject = new Subject();
                subject.setSubjectCode(resultSet.getString("CD"));
                subject.setSchoolCode(resultSet.getString("SCHOOL_CD"));
                subject.setSubjectName(resultSet.getString("NAME"));
                subjects.add(subject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return subjects;
    }

    public String getStudentSchoolCode() throws SQLException {
        String schoolCode = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT DISTINCT SCHOOL_CD FROM STUDENT");
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                schoolCode = resultSet.getString("SCHOOL_CD");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return schoolCode;
    }
}