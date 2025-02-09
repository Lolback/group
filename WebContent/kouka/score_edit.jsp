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
<title>成績編集</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<h1 class="toptitle">得点管理システム</h1>
<h2 class="subtitle">成績編集</h2>
<%
    String scoreCode = request.getParameter("scoreCode");

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        Context initContext = new InitialContext();
        DataSource ds = (DataSource)initContext.lookup("java:/comp/env/jdbc/kouka");

        conn = ds.getConnection();

        String sql = "SELECT NO, CLASS_NUM, SUBJECT_CD, POINT FROM TEST WHERE SUBJECT_CD = ?";

        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, scoreCode);

        rs = pstmt.executeQuery();

        if (rs.next()) {
            String no = rs.getString("NO");
            String subjectCode = rs.getString("SUBJECT_CD");
            String classNum = rs.getString("CLASS_NUM");
            String point = rs.getString("POINT");
%>
            <form action="score_update.jsp" method="post">
                <input type="hidden" name="originalScoreCode" value="<%= scoreCode %>">
                <p>学生番号: <input type="text" name="no" value="<%= no %>"></p>
                <p>科目コード: <input type="text" name="subjectCode" value="<%= subjectCode %>"></p>
                <p>クラス: <input type="text" name="classNum" value="<%= classNum %>"></p>
                <p>点数: <input type="text" name="point" value="<%= point %>"></p>
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
<a href="student_score.jsp">成績一覧に戻る</a>
</body>
</html>

<%@include file="../footer.html" %>