<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("UTF-8");

	String schoolCode = request.getParameter("schoolCode");
    String subjectCode = request.getParameter("subjectCode");
    String subjectName = request.getParameter("subjectName");

    String errorMessage = "";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        Context initContext = new InitialContext();
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
            pstmt.setString(1, schoolCode);
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
%>
