package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Score;

public class ScoreDao extends Dao {

    public Score getByStudentNoAndSubjectCode(String studentNo, String subjectCode) throws Exception {
        Score score = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.prepareStatement("SELECT * FROM TEST WHERE STUDENT_NO = ? AND SUBJECT_CODE = ?");
            statement.setString(1, studentNo);
            statement.setString(2, subjectCode);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                score = new Score();
                score.setStudentNo(resultSet.getString("STUDENT_NO"));
                score.setSubjectCode(resultSet.getString("SUBJECT_CODE"));
                score.setPoint(resultSet.getString("POINT"));
            }
        } finally {
            closeResources(connection, statement, resultSet);
        }

        return score;
    }

    public boolean delete(String studentNo, String subjectCode) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        int rowsAffected = 0;

        try {
            connection = getConnection();
            statement = connection.prepareStatement("DELETE FROM TEST WHERE STUDENT_NO = ? AND SUBJECT_CODE = ?");
            statement.setString(1, studentNo);
            statement.setString(2, subjectCode);
            rowsAffected = statement.executeUpdate();
        } finally {
            closeResources(connection, statement, null);
        }

        return rowsAffected > 0;
    }

    public boolean save(Score score) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;

        try {
            connection = getConnection();
            Score existingScore = getByStudentNoAndSubjectCode(score.getStudentNo(), score.getSubjectCode());
            if (existingScore == null) {
                statement = connection.prepareStatement(
                        "INSERT INTO TEST (STUDENT_NO, SUBJECT_CODE, POINT) VALUES (?, ?, ?)");
                statement.setString(1, score.getStudentNo());
                statement.setString(2, score.getSubjectCode());
                statement.setString(3, score.getPoint());
            } else {
                statement = connection.prepareStatement(
                        "UPDATE TEST SET POINT = ? WHERE STUDENT_NO = ? AND SUBJECT_CODE = ?");
                statement.setString(1, score.getPoint());
                statement.setString(2, score.getStudentNo());
                statement.setString(3, score.getSubjectCode());
            }

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
            }
        } finally {
            closeResources(connection, statement, null);
        }

        return success;
    }

    private void closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
