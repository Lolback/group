<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    String studentNo = request.getParameter("no");
    String subject = request.getParameter("subject");
    String pointStr = request.getParameter("point");

    if (studentNo == null || studentNo.isEmpty() || subject == null || subject.isEmpty() || pointStr == null || pointStr.isEmpty()) {
        out.println("<p>すべてのフィールドを入力してください。</p>");
        return;
    }

    int point;
    try {
        point = Integer.parseInt(pointStr);
    } catch (NumberFormatException e) {
        out.println("<p>点数は数値で入力してください。</p>");
        return;
    }

    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
        Context initContext = new InitialContext();
        DataSource ds = (DataSource)initContext.lookup("java:/comp/env/jdbc/kouka");
        conn = ds.getConnection();
        conn.setAutoCommit(false);

        String sql = "UPDATE TEST SET POINT = ? WHERE STUDENT_NO = ? AND SUBJECT_CD = (SELECT CD FROM SUBJECT WHERE NAME = ?)";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, point);
        pstmt.setString(2, studentNo);
        pstmt.setString(3, subject);

        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            conn.commit();
            request.getSession().setAttribute("updateMessage", "更新が完了しました。");
            response.sendRedirect("score_results.jsp");
        } else {
            conn.rollback();
            out.println("<p>更新に失敗しました。</p>");
        }
    } catch (SQLException e) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        e.printStackTrace();
        out.println("<p>SQLエラーが発生しました: " + e.getMessage() + "</p>");
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
