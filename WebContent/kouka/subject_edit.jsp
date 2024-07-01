<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../background.html" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>科目編集</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<h1 class="toptitle">得点管理システム</h1>
    <h2 class="subtitle">科目編集</h2>
    <%
        String subjecteCod = request.getParameter("subjectCode");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource)initContext.lookup("java:/comp/env/jdbc/kouka");

            conn = ds.getConnection();

            String sql = "SELECT SCHOOL_CD, CD, NAME FROM SUBJECT WHERE CD = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, subjectCode);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                String schoolCode = rs.getString("SCHOOL_CD");
                String subjectName = rs.getString("NAME");
    %>
                <form action="subject_update.jsp" method="post">
                    <input type="hidden" name="originalSubjectCode" value="<%= subjectCode %>">
                    <p>学校コード: <input type="text" name="schoolCode" value="<%= schoolCode %>"></p>
                    <p>科目コード: <input type="text" name="subjectCode" value="<%= subjectCode %>"></p>
                    <p>科目名: <input type="text" name="subjectName" value="<%= subjectName %>"></p>
                    <input type="submit" value="更新">
                </form>
    <%
            } else {
                out.println("<p>指定された科目が見つかりません。</p>");
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
    <a href="subject.jsp">科目一覧に戻る</a>
</body>
</html>

<%@include file="../footer.html" %>