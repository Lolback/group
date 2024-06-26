<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@include file="../background.html" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>科目管理</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <h2 class="subtitle">科目管理</h2>
    <%@ include file="sidebar.jsp" %>
    <a href="subject_add.jsp">新規登録</a>

    <table border="1">
        <tr>
            <th>学校コード</th>
            <th>科目コード</th>
            <th>科目名</th>
        </tr>
        <%
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;

            try {
                Context initContext = new InitialContext();
                DataSource ds = (DataSource)initContext.lookup("java:/comp/env/jdbc/kouka");

                conn = ds.getConnection();

                String sql = "SELECT SCHOOL_CD, CD, NAME FROM SUBJECT";

                pstmt = conn.prepareStatement(sql);

                rs = pstmt.executeQuery();

                while (rs.next()) {
                    String schoolCode = rs.getString("SCHOOL_CD");
                    String subjectCode = rs.getString("CD");
                    String subjectName = rs.getString("NAME");
        %>
                    <tr>
                        <td><%= schoolCode %></td>
                        <td><%= subjectCode %></td>
                        <td><%= subjectName %></td>
                        <td><a href="subject_edit.jsp?subjectCode=<%= subjectCode %>">変更</a></td>
                        <td><a href="subject_delete.jsp?subjectCode=<%= subjectCode %>">削除</a></td>
                    </tr>
        <%
                }
            } catch (Exception e) {
                e.printStackTrace();
                out.println("<p>エラーが発生しました: " + e.getMessage() + "</p>");
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
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
    </table>
</body>
</html>
<%@include file="../footer.html" %>