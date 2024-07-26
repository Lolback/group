

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import dao.Dao;

public class SubjectDao extends Dao {

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

    public Connection getConnection() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public boolean delete(String subjectCode) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;

        try {
            connection = getConnection();
            statement = connection.prepareStatement("DELETE FROM SUBJECT WHERE CD = ?");
            statement.setString(1, subjectCode);
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

        return success;
    }

    public List<Subject> getAllSubjects() throws SQLException {
        List<Subject> subjects = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM SUBJECT");
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
