package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends Dao {

	private String baseSql = "select * from student where school_cd=?" ;



	public Student get(String no, School school) throws Exception{

		//学生インスタンスを初期化
		Student student = new Student();
		//データベースへのコネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from student where no=? and school_cd=?");
			//プリペアードステートメントに学生番号をバインド
			statement.setString(1, no);
			statement.setString(2, school.getCd());
			//プリペアードステートメントを実行
			ResultSet rSet =statement.executeQuery();

			//学校Daoを初期化
			SchoolDao schoolDao = new SchoolDao();

			if (rSet.next()) {
				//リザルトセットが存在する場合
				//学生インスタンスに検索結果をセット
				student.setNo(rSet.getString("no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(Integer.parseInt(rSet.getString("ent_year")));
				student.setClassNum(rSet.getString("class_num"));
				student.setIsAttend(rSet.getBoolean("is_attend"));
				//学校フィールドには学校コードで検索した学校インスタンスをセット
				student.setSchool(schoolDao.get(rSet.getString("school_cd")));
			} else {
				//リザルトセットが存在しない場合
				//学生インスタンスにnullをセット
				student = null;
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
		return student;
	}


	private List<Student> postFilter(ResultSet rSet, School school) throws Exception {
		//リストを初期化
		List<Student> list = new ArrayList<>();
			try{
				//リザルトセットを全権走査
				while (rSet.next()) {
					//学生インスタンスを初期化
					Student student = new Student();
					//学生インスタンスに検索結果をセット
					student.setNo(rSet.getString("no"));
					student.setName(rSet.getString("name"));
					student.setEntYear(rSet.getInt("ent_year"));
					student.setClassNum(rSet.getString("class_num"));
					student.setIsAttend(rSet.getBoolean("is_attend"));
					student.setSchool(school);
					//リストに追加
					list.add(student);
				}
			} catch (SQLException | NullPointerException e) {
				e.printStackTrace();
			}
			return list;
	}

	public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception{
		//リストを初期化
		List<Student> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文の条件
		String condition = " and ent_year=? and class_num=?";
		//SQL文のソート
		String order = " order by no asc";

		//SQL文の在学フラグ条件
		String conditionIsAttend = "";
		//在学フラグがtrueの場合
		if (isAttend == true) {
			conditionIsAttend =" and is_attend=true";
		} else {
			conditionIsAttend = " and is_attend=false";
		}

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
			//プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			//プリペアードステートメントに入学年度をバインド
			statement.setInt(2, entYear);
			//プリペアードステートメントにクラス番号をバインド
			statement.setString(3, classNum);
			//プライベートステートメントを実行
			rSet = statement.executeQuery();
			//リストへの格納処理を実行
			list = postFilter(rSet, school);
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
			//コネクションを閉じる
			if (connection !=null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return list;
	}


	public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception{
		//リストを初期化
		List<Student> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文の条件
		String condition = " and ent_year=?";
		//SQL文のソート
		String order = " order by no asc";

		//SQL文の在学フラグ
		String conditionIsAttend = "";
		//在学フラグがtrueだった場合
		if (isAttend == true) {
			conditionIsAttend = " and is_attend=true";
		} else {
			conditionIsAttend = " and is_attend=false";
		}

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
			//プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			//プリペアードステートメントに入学年度をバインド
			statement.setInt(2, entYear);
			//プリペアードステートメントを実行
			rSet = statement.executeQuery();
			//リストへの格納処理を実行
			list = postFilter(rSet, school);
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
			//コネクションを閉じる
			if (connection != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return list;
	}
	public List<Student> filter(School school, boolean isAttend) throws Exception{
		//リストを初期化
		List<Student> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文の条件
		String order = " order by no asc";

		//SQL文の在学フラグ
		String conditionIsAttend = "";
		//在学フラグがtrueの場合
		if (isAttend == true) {
			conditionIsAttend = " and is_attend=true";
		} else {
			conditionIsAttend = " and is_attend=false";
		}

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + conditionIsAttend + order);
			//プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			//プリペアードステートメントを実行
			rSet = statement.executeQuery();
			//リストへの格納処理を実行
			list = postFilter(rSet, school);
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
		if (statement != null) {
			try {
				connection.close();
			} catch (SQLException sqle) {
				throw sqle;
			}
		}
		return list;
	}

	public boolean save(Student student, School school) throws Exception{
		//コネクションを確立
		Connection connection = getConnection();

		//プリペアードステートメント
		PreparedStatement statement = null;
		//実行件数

		int count = 0;

		try {

			//データベースから学生を取得
			Student old = get(student.getNo(), school);
			if (old == null) {
				//学生が存在しなかった場合
				//プリペアードステートメントにINSERT文をセット
				statement = connection.prepareStatement(
						"insert into student(no, name, ent_year, class_num, is_attend, school_cd) values(?, ?, ?, ?, ?, ?)");
				//プリペアードステートメントに値をバインド
				statement.setString(1, student.getNo());
				statement.setString(2, student.getName());
				statement.setInt(3, student.getEntYear());
				statement.setString(4, student.getClassNum());
				statement.setBoolean(5, student.getIsAttend());
				statement.setString(6, student.getSchool().getCd());
			} else {
				//学生が存在した場合
				//プリペアードステートメントにUPDATE文をセット
				statement = connection
						.prepareStatement("update student set name=?, class_num=?, is_attend=? where no=?");
				//プリペアードステートメントに値をバインド
				statement.setString(1, student.getName());
				statement.setString(2, student.getClassNum());
				statement.setBoolean(3, student.getIsAttend());
				statement.setString(4, student.getNo());
			}

			//プリペアードステートメントを実行
			count = statement.executeUpdate();

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
			//コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		if (count > 0) {
			//実行件数が1件以上ある場合
			return true;
		} else {
			//実行件数が0件の場合
			return false;
		}
	}

	public void delete(String no, School school) throws Exception{

        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;

        try {
            connection = getConnection();
            statement = connection.prepareStatement("DELETE FROM STUDENT WHERE NO = ? AND SCHOOL_CD = ?");
            statement.setString(1, no);
            statement.setString(2, school.getCd());
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
            statement = connection.prepareStatement("DELETE FROM TEST WHERE STUDENT_NO = ? AND SCHOOL_CD = ?");
            statement.setString(1, no);
            statement.setString(2, school.getCd());
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
	}

	public List<Student> getAll(School school) throws Exception{
		//リストを初期化
		List<Student> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//リザルトセット
		ResultSet rSet = null;
		//SQL文のソート
		String order = " order by class_num asc, no asc";

		try {
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + order);
			//プリペアードステートメントに学校コードをバインド
			statement.setString(1, school.getCd());
			//プライベートステートメントを実行
			rSet = statement.executeQuery();
			//リストへの格納処理を実行
			list = postFilter(rSet, school);
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
			//コネクションを閉じる
			if (connection !=null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return list;
	}
}
