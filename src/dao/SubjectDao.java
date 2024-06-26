package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}
