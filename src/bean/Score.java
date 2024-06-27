package bean;

public class Score {
    private String academicYear;
    private String className;
    private String subject;
    private String times;
    private int score;

    // academicYear のゲッターとセッター
    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    // className のゲッターとセッター
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    // subject のゲッターとセッター
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    // times のゲッターとセッター
    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    // score のゲッターとセッター
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
