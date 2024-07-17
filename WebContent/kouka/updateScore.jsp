<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    String studentNo = request.getParameter("no");
    Integer newPoint = Integer.parseInt(request.getParameter("point"));

    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
        Context initContext = new InitialContext();
        DataSource ds = (DataSource)initContext.lookup("java:/comp/env/jdbc/kouka");
        conn = ds.getConnection();

        String sql = "UPDATE TEST SET POINT = ? WHERE STUDENT_NO = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, newPoint);
        pstmt.setString(2, studentNo);

        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            // 更新成功時の処理
            conn.commit(); // コミットを忘れないようにしましょう
            conn.setAutoCommit(true); // オートコミットを再設定（必要に応じて）

            // 更新が完了しましたというメッセージをセッションに保存
            request.getSession().setAttribute("updateMessage", "更新が完了しました。");

            // リダイレクト
            response.sendRedirect("score_results.jsp"); // 更新後に検索結果ページにリダイレクト
        } else {
            // 更新失敗時の処理（任意）
            out.println("<p>更新できませんでした。</p>");
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("<p>エラーが発生しました: " + e.getMessage() + "</p>");
    } finally {
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
    }
%>
