package kouka;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import tool.Action;
public class SubjectCreateExecuteAction extends Action {
   @Override
   public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
       String subjectCode = request.getParameter("subjectCode");
       String subjectName = request.getParameter("subjectName");
       String errorMessage = "";
       Connection conn = null;
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       try {
           InitialContext initContext = new InitialContext();
           DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/kouka");
           conn = ds.getConnection();
           // 科目コードの重複チェック
           String checkSql = "SELECT COUNT(*) FROM SUBJECT WHERE CD = ?";
           pstmt = conn.prepareStatement(checkSql);
           pstmt.setString(1, subjectCode);
           rs = pstmt.executeQuery();
           rs.next();
           int count = rs.getInt(1);
           if (count > 0) {
               errorMessage = "科目コードが重複しています。";
           } else {
               // 科目を追加
               String insertSql = "INSERT INTO SUBJECT (SCHOOL_CD, CD, NAME) VALUES (?, ?, ?)";
               pstmt = conn.prepareStatement(insertSql);
               pstmt.setString(1, "SCHOOL_CODE"); // 必要に応じて修正
               pstmt.setString(2, subjectCode);
               pstmt.setString(3, subjectName);
               int result = pstmt.executeUpdate();
               if (result > 0) {
                   response.sendRedirect("subject.jsp");
               } else {
                   errorMessage = "科目の追加に失敗しました。";
               }
           }
       } catch (Exception e) {
           e.printStackTrace();
           errorMessage = "エラーが発生しました: " + e.getMessage();
       } finally {
           if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
           if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
           if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
       }
       if (!errorMessage.isEmpty()) {
           request.setAttribute("errorMessage", errorMessage);
           RequestDispatcher dispatcher = request.getRequestDispatcher("subject_add.jsp");
           dispatcher.forward(request, response);
       }
   }
}
