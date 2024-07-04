<!DOCTYPE html>
<html>
<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>検索結果</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<h1 class="subtitle">検索結果</h1>
<table border="1">
<tr>
<th>入学年度</th>
<th>クラス</th>
<th>学生番号</th>
<th>回数</th>
</tr>
<%
    String entYear = request.getParameter("f1");
    String classNum = request.getParameter("f2");
    String subjectCd = request.getParameter("f3");
    String times = request.getParameter("f4");

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        Context initContext = new InitialContext();
        Context envContext = (Context)initContext.lookup("java:/comp/env");
        DataSource ds = (DataSource)envContext.lookup("jdbc/kouka");

        conn = ds.getConnection();

        String sql = "SELECT * FROM STUDENT WHERE ENT_YEAR = ? AND CLASS_NUM = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, entYear);
        pstmt.setString(2, classNum);
        rs = pstmt.executeQuery();

        while (rs.next()) {
%>
<tr>
<td><%= rs.getString("ENT_YEAR") %></td>
<td><%= rs.getString("CLASS_NUM") %></td>
<td><%= rs.getString("NO") %></td>
<td><%= rs.getString("NAME") %></td>
<td><%= rs.getString("IS_ATTEND") %></td>
<td><%= rs.getString("SCHOOL_CD") %></td>
</tr>
<%
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
%>
</table>
</body>
</html>
