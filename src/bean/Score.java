package bean;

public class Score {
    private String studentNo; // 学生番号
    private String subjectCode; // 科目コード
    private String classNum; // クラス番号
    private String point; // 点数

    // Getter and Setter for studentNo
    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    // Getter and Setter for subjectCode
    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    // Getter and Setter for classNum
    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    // Getter and Setter for point
    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
