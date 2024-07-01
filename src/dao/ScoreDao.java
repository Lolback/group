package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Score;

public class ScoreDao extends Dao {

    public Score getByStudentNoAndSubjectCode(String studentNo, String subjectCode) throws Exception {
        Score score = null;

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM SCORE WHERE STUDENT_NO = ? AND SUBJECT_CODE = ?");
        statement.setString(1, studentNo);
        statement.setString(2, subjectCode);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            score = new Score();
            score.setStudentNo(resultSet.getString("STUDENT_NO"));
            score.setSubjectCode(resultSet.getString("SUBJECT_CODE"));
            score.setClassNum(resultSet.getString("CLASS_NUM"));
            score.setPoint(resultSet.getString("POINT"));
        }

        statement.close();
        connection.close();

        return score;
    }

    public boolean delete(String studentNo, String subjectCode) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM SCORE WHERE STUDENT_NO = ? AND SUBJECT_CODE = ?");
        statement.setString(1, studentNo);
        statement.setString(2, subjectCode);
        int rowsAffected = statement.executeUpdate();

        statement.close();
        connection.close();

        return rowsAffected > 0;
    }

    public List<Score> filter(School school) throws Exception {
        List<Score> list = new ArrayList<>();

        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM SCORE WHERE SCHOOL_CD = ? ORDER BY STUDENT_NO ASC, SUBJECT_CODE ASC");
        statement.setString(1, school.getCd());
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            Score score = new Score();
            score.setStudentNo(resultSet.getString("STUDENT_NO"));
            score.setSubjectCode(resultSet.getString("SUBJECT_CODE"));
            score.setClassNum(resultSet.getString("CLASS_NUM"));
            score.setPoint(resultSet.getString("POINT"));
            list.add(score);
        }

        statement.close();
        connection.close();

        return list;
    }

    public boolean save(Score score) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            Score old = getByStudentNoAndSubjectCode(score.getStudentNo(), score.getSubjectCode());
            if (old == null) {
                statement = connection.prepareStatement(
                        "INSERT INTO SCORE (STUDENT_NO, SUBJECT_CODE, CLASS_NUM, POINT) VALUES (?, ?, ?, ?)");
                statement.setString(1, score.getStudentNo());
                statement.setString(2, score.getSubjectCode());
                statement.setString(3, score.getClassNum());
                statement.setString(4, score.getPoint());
            } else {
                statement = connection.prepareStatement(
                        "UPDATE SCORE SET CLASS_NUM = ?, POINT = ? WHERE STUDENT_NO = ? AND SUBJECT_CODE = ?");
                statement.setString(1, score.getClassNum());
                statement.setString(2, score.getPoint());
                statement.setString(3, score.getStudentNo());
                statement.setString(4, score.getSubjectCode());
            }

            count = statement.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        return count > 0;
    }
}
