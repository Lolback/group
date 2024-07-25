package bean;

public class Score {
    private String studentNo; // 学生番号
    private String subjectCode; // 科目コード
    private String classNum; // クラス番号

    private String schoolCode; // 学校コード
    private String point; // 点数
    private String number; // 回数
    private String entYear; // 入学年度
    private String name; // 名前

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

	public String getEntYear() {
		return entYear;
	}

	public void setEntYear(String entYear) {
		this.entYear = entYear;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
