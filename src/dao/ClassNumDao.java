package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

public class ClassNumDao extends Dao {

    private String baseSql = "SELECT CLASS_NUM FROM CLASS_NUM where SCHOOL_CD=?";

    /*
     * 必要な機能
     * filterメソッドを作成し学校コードをもとにクラス番号を取得する
     * 中身は今後変更の可能性あり
     */

    public List<ClassNum> filter(School school) throws Exception {

        // リストを初期化
        List<ClassNum> list = new ArrayList<>();
        // コネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;
        // リザルトセット
        ResultSet rSet = null;
        // SQL文の条件
        String order = " order by class_num asc";

        try {
            // プリペアードステートメントにSQL文をセット
            statement = connection.prepareStatement(baseSql + order);
            // プリペアードステートメントに学校コードをバインド
            statement.setString(1, school.getCd()); // 修正：学校コードを文字列として設定
            // プリペアードステートメントを実行
            rSet = statement.executeQuery();
            // リストへの格納処理を実行
            while (rSet.next()) {
                ClassNum classnum = new ClassNum(); // 修正：毎回新しいインスタンスを作成
                classnum.setClassNum(rSet.getInt("class_num"));
                list.add(classnum);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            // リソースを閉じる
            if (rSet != null) {
                try {
                    rSet.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
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
        return list;
    }
}
