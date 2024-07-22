package kouka;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/ScoreCreateAction")
public class ScoreCreateAction extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // フォームからパラメータを取得
        String entYear = request.getParameter("entYear");
        String classNum = request.getParameter("classNum");
        String no = request.getParameter("no");
        String name = request.getParameter("name");
        String subject = request.getParameter("subject");
        String point = request.getParameter("point");

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // JNDIからDataSourceを取得
            Context initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/kouka");

            // データベース接続の確立
            conn = ds.getConnection();

            // データベースに挿入するSQL文
            String sql = "INSERT INTO TEST (ENT_YEAR, CLASS_NUM, STUDENT_NO, NAME, SUBJECT_CD, POINT) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";

            // PreparedStatementを作成してパラメータをセット
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, entYear);
            pstmt.setString(2, classNum);
            pstmt.setString(3, no);
            pstmt.setString(4, name);
            pstmt.setString(5, subject);
            pstmt.setString(6, point);

            // SQLを実行し、挿入を行う
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                // 挿入成功時の処理
                String updateMessage = "成績が正常に登録されました。";
                request.getSession().setAttribute("updateMessage", updateMessage);
            } else {
                // 挿入失敗時の処理
                String updateMessage = "成績の登録に失敗しました。";
                request.getSession().setAttribute("updateMessage", updateMessage);
            }

        } catch (Exception e) {
            e.printStackTrace();
            // エラー時の処理
            String updateMessage = "エラーが発生しました: " + e.getMessage();
            request.getSession().setAttribute("updateMessage", updateMessage);
        } finally {
            // リソースの解放
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // 成績登録画面にリダイレクト
            response.sendRedirect("score_add.jsp");
        }
    }
}
