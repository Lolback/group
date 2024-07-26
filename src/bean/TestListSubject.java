package bean;

import java.util.Map;

public class TestListSubject {

	//TestListSubjectのbean
	private int entYear;
	private String studentNo;
	private String studentName;
	private String classNum;
	private int testno;
	private String no;
	private String name;
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTestNo() {
		return testno;
	}

	public void setTestNo(int no) {
		this.testno = no;
	}
	private Map<Integer, Integer> points;

	public TestListSubject() {

	}

	public int getEntYear() {
		return entYear;
	}
	public void setEntYear(int entYear) {
		this.entYear = entYear;
	}

	public String getStudentNo() {
		return studentNo;
	}
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getClassNum() {
		return classNum;
	}
	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public Map<Integer, Integer> getPoints() {
		return points;
	}
	public void setPoints(Map<Integer, Integer> points) {
		this.points = points;
	}

	public String getPoint(int key) {
		String Key = String.valueOf(key);
		return Key;
	}
	public void putPoint(int key, int value) {
		points.put(key, value);

	}



}
