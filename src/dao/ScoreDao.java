package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.h2.engine.Database;

import bean.Score;

public class ScoreDao {
    public List<Score> searchScores(String academicYear, String className, String subject, String times) {
        List<Score> scores = new ArrayList<>();
        try (Connection con = Database.getConnection()) {
            String sql = "SELECT * FROM scores WHERE academic_year = ? AND class = ? AND subject = ? AND times = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, academicYear);
            ps.setString(2, className);
            ps.setString(3, subject);
            ps.setString(4, times);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Score score = new Score();
                score.setAcademicYear(rs.getString("academic_year"));
                score.setClassName(rs.getString("class"));
                score.setSubject(rs.getString("subject"));
                score.setTimes(rs.getString("times"));
                score.setScore(rs.getInt("score"));
                scores.add(score);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scores;
    }
}
